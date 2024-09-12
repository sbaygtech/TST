package cb.ext.dbs.batch;

import cb.ext.dbs.bean.DebtFileMasterService;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.CompanyAccount;
import cb.ext.dbs.pom.DBSAutoTransferPOM;
import cb.ext.dbs.pom.DBSAutoTransferPOMData;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.EMailUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDataSource;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;

public class TransferEODCompany extends CBBatchBase implements CBBagKeys {
	
	protected CBBag prepare(CBBag inBag) throws CBException {
		
		CBBag outBag =  CBBagFactory.createBag();
		
		CBQueryExecuter qe = null;
		
		try {
			
			CBDate processDate =  CBSystem.getInstance().getProcessDate();
			
			String tableName = inBag.get(TABLE).toString();

			CBDataSource.truncateTable(tableName);
			CBDataSource.dropIndex(tableName);
			
			
			qe = new CBQueryExecuter("EXT_DBS_INSERT_BCH_AUTOTRANSFER");
			qe.setParameter("TABLENAME", tableName.substring(4));
			qe.setParameter("ACCOUNTINGDATE", processDate);
			
			
			qe.executeQuery();
			
			
			CBDataSource.createIndex(tableName);
				

			outBag.put(RC, true);
	
			
		} catch (CBException ex) {
			throw ex;
		}
		
		finally{
			if (qe!=null) {
				qe.close();
			}
		}

		return outBag;

	}
	
	//@serviceName : EXT_DBS_TRANSFER_DEALER_COMPANY_PROCESS
	//@batchName   : EXTD7140
	//@bchName	   : BCH.DBS_TRSFR_DLR_COMPANY
	
	public static CBBag transferEODCompany(CBBag inBag) throws CBException{
		
		CBBag outBag =  CBBagFactory.createBag();
		CBBag dataBag =  CBBagFactory.createBag();
		CBBag transferBag = CBBagFactory.createBag();
			
		for (int i = 0; i < inBag.getSize(BAG); i++) {

			dataBag = inBag.get(BAG, i, -1).toBag();

			transferBag.put(COMPANYOID, dataBag.get(COMPANYOID).toSimpleLong());
			transferBag.put(CURRENCY, dataBag.get(CURRENCY).toString());
			transferBag.put(TOTALCOUNT, dataBag.get(TOTALCOUNT).toSimpleInt());
			transferBag.put(TOTALAMOUNT, dataBag.get(TOTALAMOUNT).toCBCurrency());

			transferFromBlockageToCurrentAccount(transferBag);

		}

		outBag.put(RC, true);	

		
		return outBag;
		
	}
	
	public static CBBag transferFromBlockageToCurrentAccount(CBBag inBag) throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();

		CBBag processBag = CBBagFactory.createBag();
		CBBag processResultBag = CBBagFactory.createBag();

		CBDate processDate = CBSystem.getInstance().getProcessDate();
		
		boolean errorFound = false;
		String errorMessage = "";
		
		try {
			
			
			
			long companyOID = inBag.get(COMPANYOID).toSimpleLong();

			CompanyAccount companyAccount = new CompanyAccount().getCompanyAccount(companyOID, inBag.get(CURRENCY).toString());

			inBag.copyTo(processBag);
			processBag.put(ACCOUNTNR, companyAccount.getBlockageAccountNo());
			processBag.put(ACCOUNTNR2, companyAccount.getAccountNo());

			CBSessionInfo.setIslemAnaBirimKodu(AccountUtility.getAccountBranchCode(companyAccount.getAccountNo()));

			processResultBag = CBCaller.call("EXT_DBS_TRANSFER_DEALER_COMPANY_MAIN", processBag);

			if ( isAccountingSuccessful(processResultBag.get(CODE1).toBoolean()) ) {

				long productOpRefNo = processResultBag.get(PRODUCTOPERATIONREFNO).toSimpleLong();

				DebtFileMasterService.updateDealerDebtRecordsAtCompanyCurrentAccount(productOpRefNo, processDate, companyOID);

				insertIntoCompanyTransferTable(productOpRefNo, processDate, companyOID, 
						                               inBag.get(TOTALCOUNT).toSimpleInt(), inBag.get(TOTALAMOUNT).toCBCurrency(), 
						                               companyAccount);

			}
			else {
				
				errorMessage = processResultBag.get(EXPLANATION).toString();
				errorFound = true;

			}

		} catch (CBException ex) {

			errorMessage = "Hata Kodu: " + ex.id + "\n\r" + ex.getMessage();
			errorFound = true;

		}
		
		if (errorFound) {
			
			CBBag mailBag = CBBagFactory.createBag();

			processBag.copyTo(mailBag);
			mailBag.put(PROCESSDATE, processDate);
			mailBag.put(MESSAGE, errorMessage);
			errorFound = true;

			prepareAndSendMail(mailBag);
			
		}

		return outBag;
	
}

	private static boolean isAccountingSuccessful(boolean result) throws CBException {
		return !result;
	}

	private static void insertIntoCompanyTransferTable(long productOpRefNo, CBDate processDate, long companyOID, 
															   int totalCount, CBCurrency totalAmount, 
															   CompanyAccount companyAccount ) throws CBException {
		
		DBSAutoTransferPOM pom = DBSAutoTransferPOM.newDBSAutoTransferPOM();
		DBSAutoTransferPOMData data = DBSAutoTransferPOMData.newInstance();
		
		try {

			data.prodOperRefNo = productOpRefNo;
			data.companyOID = companyOID;
			data.accountingDate = processDate;
			data.totalCount = totalCount;
			data.totalAmount = totalAmount;
			data.creditAccount = companyAccount.getBlockageAccountNo();
			data.debitAccount = companyAccount.getAccountNo();

			pom.setDBSAutoTransferPOMData(data);
			pom.create();
		
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
			
		}
		
	}

	
	
	public static void prepareAndSendMail(CBBag inBag) throws CBException {
	               
		StringBuffer message   = new StringBuffer ();
		
		String emailList = EMailUtility.getEMailForErrorMessages();
	                  
	    CBDate processDate = inBag.get(PROCESSDATE).toCBDate();
	    
	    String fromAccountNo = inBag.get(ACCOUNTNR).toString();
	    
		Account account = AccountUtility.getAccountInfo(fromAccountNo);			
					

	                
	    String messageHeader =  account.getTitle() + " icin Bloke Hesaptan Cari Hesaba Virman Sýrasýnda Hata Oldu! Tarih:" + processDate.toDBString();
	                
	                
	    message.append("Tarih:" + processDate.toDBString());
	    message.append("\n\r");
	    message.append("Paranýn Alýndýgý Hesap No:" + fromAccountNo);
	    message.append("\n\r");
	    message.append("Paranýn Transfer Edildiði Hesap No:" + inBag.get(ACCOUNTNR2).toString());
	    message.append("\n\r");
	    message.append("Transfer Miktarý:" + inBag.get(TOTALAMOUNT).toCBCurrency() + " " + inBag.get(CURRENCY).toString());
	    message.append("\n\r");
	    message.append("Islem Adet:" + inBag.get(TOTALCOUNT).toSimpleInt() );
	    message.append("\n\r");
	    message.append("\n\r");
	    message.append("\n\r");

	    message.append("Hata Aciklama:");
	    message.append("\n\r");
	    message.append("Hata Kodu:" + inBag.get(MESSAGE).toString());
	    message.append("\n\r");
	    message.append("\n\r");
	    message.append("\n\r");
	    message.append("Bilginize.");
	                
	    EMailUtility.sendMail(message.toString(), messageHeader, emailList);
	                
    }

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RC , true);
		
		return outBag;

	}

}
