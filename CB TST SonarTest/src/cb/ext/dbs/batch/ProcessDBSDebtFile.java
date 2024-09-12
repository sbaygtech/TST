package cb.ext.dbs.batch;

import static cb.ext.dbs.util.ExceptionUtility.throwExceptionNoParam;

import java.util.List;

import cb.ext.dbs.bean.DebtFileService;
import cb.ext.dbs.bean.LimitFileService;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.CompanyDealerCodeFormat;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DBSProcessType;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.ProcessMasterFileConstants;
import cb.ext.dbs.constants.TransferType;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyAccount;
import cb.ext.dbs.data.CompanyDebtMasterProcessExceptionParam;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.PaymentResultHistory;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileMasterException;
import cb.ext.dbs.generic.data.DebtFileRecordType;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOMData;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CustomerUtility;
import cb.ext.dbs.util.EMailUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDataSource;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class ProcessDBSDebtFile extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {

			CBDate processDate = CBSystem.getInstance().getProcessDate();

			String tableName = inBag.get(TABLE).toString();

			CBDataSource.truncateTable(tableName);
			CBDataSource.dropIndex(tableName);

			qe = new CBQueryExecuter("EXT_DBS_INSERT_BCH_MASTER_FILE");
			
			qe.setParameter("TABLENAME", tableName.substring(4));
			qe.setParameter("DUEDATE", processDate);
			qe.setParameter("DUEDATE2", processDate);
			qe.setParameter("COMPANYOID", inBag.get(COMPANYOID).toSimpleLong());
			
			qe.executeQuery();

			CBDataSource.createIndex(tableName);

			outBag.put(PROCESSDATE, processDate.toDBString());
			outBag.put(NUMBER0, GeneralUtility.generateNo(CompanyConstants.DBS_COMPANY_FC_BATCH_CODE));
			outBag.put(LIMITUSED, inBag.get(LIMITUSED).toBoolean());
			outBag.put(COMPANYOID, inBag.get(COMPANYOID));
			if(inBag.existsBagKey(TIME1)) {
				outBag.put(TIME1, inBag.get(TIME1));
			}
			
			outBag.put(RC, true);

			return outBag;

		} catch (CBException ex) {
			throw ex;
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
	}

	// @serviceName : EXT_DBS_PROCESS_MASTER_FILE
	// bchTable     : BCH.DBS_INSERT_MASTER_OID
	// batchName    : EXTD7133

	public static CBBag processingMasterFile(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag dataBag = CBBagFactory.createBag();

		for (int i = 0; i < inBag.getSize(BAG); i++) {

			dataBag = inBag.get(BAG, i, -1).toBag();

			processMasterRecord(dataBag.get(VALUE1).toSimpleLong(), inBag.get(PROCESSDATE).toCBDate(), inBag.get(NUMBER0).toSimpleLong(), inBag.get(LIMITUSED).toBoolean());

		}

		outBag.put(RC, true);

		return outBag;
	}
	
	private static boolean checkCompany (Company company, DBSCompanyDebtFileMasterPOMData data, CompanyDebtMasterProcessExceptionParam masterExceptionPrm , List<String >  masterExceptionCompany) {
		
		boolean errorFound = false;
		
		if (company == null && masterExceptionCompany.contains("08")) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_COMPANY_NOT_FOUND;
			masterExceptionPrm.setMethodCode("08");
			errorFound = true;
		}
		else if (!company.isActive() && masterExceptionCompany.contains("09")) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_COMPANY_INACTIVE;
			errorFound = true;
			masterExceptionPrm.setMethodCode("09");
		}
		
		return errorFound;
	}
	
	private static boolean checkDealer (Dealer dealer, DBSCompanyDebtFileMasterPOMData data , CompanyDebtMasterProcessExceptionParam masterExceptionPrm , List<String >  masterExceptionCompany) {
		
		boolean errorFound = false;
		
		if (dealer == null && masterExceptionCompany.contains("10")) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_DEALER_NOT_FOUND;
			errorFound = true;
			masterExceptionPrm.setMethodCode("10");
		}
		else if (!dealer.isActive() && masterExceptionCompany.contains("11") ) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_DEALER_INACTIVE;
			errorFound = true;
			masterExceptionPrm.setMethodCode("11");
		}
		
		
		return errorFound;
	}

	public static CBBag processMasterRecord(long masterRecordID, CBDate processDate, long sequenceNumber, boolean useLimit) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBBag processBag = CBBagFactory.createBag();
		CBBag processBagResult = CBBagFactory.createBag();

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		String methodCode = "00";

		String message = "";
		
		boolean errorFound = false;
		boolean isPayment = true;
		
		
		CompanyDebtMasterProcessExceptionParam masterExceptionPrm = null;
		List<String >                      masterExceptionCompany = null;
		CompanyDebtFileMasterException  	masterException       = new CompanyDebtFileMasterException();
		try {
			
			if (pom.readByPrimaryKey(masterRecordID)) {
		
				data = pom.getDBSCompanyDebtFileMasterPOMData();
	
				Company company = new Company().get(data.companyID);
	
			    masterExceptionPrm      = new CompanyDebtMasterProcessExceptionParam(data.companyID , methodCode);
			    masterExceptionCompany  = new CompanyDebtMasterProcessExceptionParam(data.companyID).getAllCompanyException();

			 //   masterExceptionPrm.setCompanyOID(data.companyID);
			    
				errorFound = checkCompany(company, data,  masterExceptionPrm , masterExceptionCompany);
				
				if (!errorFound) {

					Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(data.companyID, getDealerInternalCustomerNo(data.customerNo, data.companyID, data.channelCode));
					
					errorFound = checkDealer(dealer, data,  masterExceptionPrm , masterExceptionCompany);
					
					if (!errorFound) {

						CompanyAccount companyAccount = new CompanyAccount().getCompanyAccount(data.companyID, data.currency);
						DealerAccount dealerAccount = new DealerAccount().getDealerAccount(dealer.getOID(), data.currency);
						
						errorFound = checkAccounts(data, companyAccount,dealerAccount,  masterExceptionPrm , masterExceptionCompany);
						
						
						if (!errorFound) {

							CBSessionInfo.setIslemAnaBirimKodu(AccountUtility.getAccountBranchCode(companyAccount.getAccountNo()));
	
							processBag = setProcessBag(masterRecordID, processDate, sequenceNumber, data, companyAccount, dealerAccount, company, useLimit);
							
							if(company.getDbsType() == 1) {
								processBagResult = CBCaller.call("EXT_DBS_PROCESS_MASTER_RECORD_MAIN", processBag);
							}
							else {
								CompanyDebtFileDetail companyDebtFileDetail = new CompanyDebtFileDetail();
								companyDebtFileDetail = companyDebtFileDetail.getCompanyDebtFileDetailByMasterOid(data.getOID());
								
								if(companyDebtFileDetail != null &&  DebtFileRecordType.cancelPayment.value().equals(companyDebtFileDetail.getRecordType())) {
									isPayment = false;
									
									CBBag cancelBag = CBBagFactory.createBag();
									cancelBag.put(DEALER_CODE, dealer.getCompanyDealerCustomerCode());
									cancelBag.put(COMPANYCODE, company.getCompanyInternalCode());
									cancelBag.put(INVOICENO, companyDebtFileDetail.getInvoiceNo());
									
									processBagResult = CBCaller.call("EXT_DBS_PERSONAL_GET_PAID_DEBT", cancelBag);
									
									if(processBagResult.getSize(TABLE) == 0) {
										throwExceptionNoParam(DBSExceptions.PERSONEL_DBS_PAID_DEBT_NOT_FOUND);
									}
									else if(processBagResult.getSize(TABLE) > 1) {
										throwExceptionNoParam(DBSExceptions.PERSONEL_DBS_INVALID_PAID_DEBT);
									}
									
									if(!processBagResult.get(TABLE, 0, PAIDAMOUNT).toCBCurrency().equals(data.invoiceAmount)) {
										throwExceptionNoParam(DBSExceptions.PERSONEL_DBS_PAYMENT_AMOUNT_NOT_EQUAL_CANCEL_AMOUNT);
									}
									
									cancelBag.put(REFNO, processBagResult.get(TABLE, 0, PRODUCTOPERATIONREFNO));
									
									processBagResult = CBCaller.call("EXT_PERSONAL_DBS_CANCEL_PAYMENT_MAIN", cancelBag);
								}
								else {
									
									processBag.put(DEALER_CODE, dealer.getCompanyDealerCustomerCode());
									Customer customer = CustomerUtility.getCustomerInfo(dealer.getCustomerNumber());
									processBag.put(TITLE, customer.getTitle());
									processBag.put(DUEDATE, data.dueDate);
									
									processBagResult = CBCaller.call("EXT_DBS_PERSONAL_PROCESS_MASTER_RECORD_MAIN", processBag);
								}								
							}
							
							if(isPayment) {
								if (processBagResult.get(CODE1).toString().equals(DebtRecordStatu.done.value()) || ( processBagResult.get(CODE1).toString().equals(DebtRecordStatu.partialPayment.value()) && processBag.get(AMOUNT).toCBCurrency().compare(CBCurrency.ZERO)!=0)  ) {
									
									if (companyAccount.getBlockageDayCount()>0) {
										data.transfered = TransferType.atBlockageAccount.value();
									}
									else {
										data.transfered = TransferType.atCurrentAccount.value();
									}
									
									data.accountingDate     = setAccountingDate(processDate, companyAccount.getBlockageDayCount());
									data.processStatu       = processBagResult.get(CODE1).toString();
								
									if(processBagResult.get(CODE1).toString().equals(DebtRecordStatu.done.value())){
										data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_OK;
									}else {
										data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_PARTIAL;
									}
							
									data.processDate        = processDate;
									data.processTime        = CBSystem.getInstance().getCurrentTime();
									data.productOpRefNo     = processBagResult.get(PRODUCTOPERATIONREFNO).toSimpleLong();
									data.paidAmount         = data.paidAmount.add(processBag.get(AMOUNT).toCBCurrency());
									data.sequenceNo			= sequenceNumber;
		
									pom.setDBSCompanyDebtFileMasterPOMData(data);
									pom.update();
									
									createPaymentResultHistory(data, processBag.get(AMOUNT).toCBCurrency(), DBSProcessType.PAYMENT.getType());
		
									if(company.isPartialPayable() && processBag.get(AMOUNT).toCBCurrency().compare(CBCurrency.ZERO)!=0 ){
										DebtFileService.addCompanyProcessDebtExtension (processBag, masterRecordID,processBagResult.get(PRODUCTOPERATIONREFNO).toSimpleLong() );
									}
								}
								else {
		
									message = handleErrorMessage(processBagResult.get(EXPLANATION).toString());
		
									data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_NOT_OK;
									
									errorFound = true;
									masterExceptionPrm.setMethodCode("02");
		
								}
							}
							else {
								data.cancelDate = CBSystem.getInstance().getProcessDate();
								data.cancelAmount = data.invoiceAmount;
								data.cancelProductOpRefNo = processBagResult.get(CANCELLATIONOPERATIONREFERENCE).toSimpleLong();
								data.processStatu = DebtRecordStatu.cancelPayment.value();
								data.updateDate = CBSystem.getInstance().getProcessDate();
								data.updateTime = CBSystem.getInstance().getCurrentTime();
								data.sequenceNo = sequenceNumber;
								
								pom.setDBSCompanyDebtFileMasterPOMData(data);

								pom.update();
								
								createPaymentResultHistory(data, data.invoiceAmount, DBSProcessType.CANCEL_PAYMENT.getType());
							}
						}
					}
				}
			}

		} catch (CBException ex) {
			
			message = handleErrorMessage(ex.getMessage());
			
			data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_NOT_OK;

			errorFound = true;
			masterExceptionPrm.setMethodCode("02");
			
		} finally {
			
			if (errorFound) {
				data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_NOT_OK;
				data.processStatu = DebtRecordStatu.error.value();
				data.processDate = processDate;
				data.processTime = CBSystem.getInstance().getCurrentTime();
				data.sequenceNo = sequenceNumber;
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);
				pom.update();
				
				if(isPayment) {
					createPaymentResultHistory(data, CBCurrency.ZERO, DBSProcessType.PAYMENT.getType());
				}
				else {					
					createPaymentResultHistory(data, CBCurrency.ZERO, DBSProcessType.CANCEL_PAYMENT.getType());
				}
			}
			else {
				outBag.put(RESULT, true);
			}
			
			masterExceptionPrm.get();
			
			masterException.setExceptionCode(masterExceptionPrm.getExceptionCode());
			masterException.setMasterOID(data.oID);
			
			if(CBSessionInfo.isADK()){
				masterException.setExceptionDescription(masterExceptionPrm.getAdkExceptionDescription());
			}else {
				masterException.setExceptionDescription(masterExceptionPrm.getExceptionDescription());
			}
			
			masterException.add();
			
			if (pom != null) {
				pom.close();
			}
			
		}
		return outBag;

	}

	private static CBDate setAccountingDate(CBDate processDate, int blockageDayCount) throws CBException {
		
		CBDate accountingDate = processDate.add(blockageDayCount);
		
		if (accountingDate.isHoliday()) {
			accountingDate = accountingDate.getNextWorkingDate();
		}
		
		return accountingDate;
	}

	private static boolean checkAccounts(DBSCompanyDebtFileMasterPOMData data, CompanyAccount companyAccount, DealerAccount dealerAccount, CompanyDebtMasterProcessExceptionParam masterExceptionPrm , List<String >  masterExceptionCompany) throws CBException {
	
		boolean errorFound = false;
		
		CBBag hesapBag = CBBagFactory.createBag();
		CBBag inBag    = CBBagFactory.createBag();
		
		if (companyAccount == null && masterExceptionCompany.contains("12")) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_COMPANY_ACCOUNT_NOT_FOUND;
			errorFound = true;
			masterExceptionPrm.setMethodCode("12");
		}
		else if (dealerAccount == null &&  masterExceptionCompany.contains("05")) {
			//data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_DEALER_ACCOUNT_NOT_FOUND;
			errorFound = true;
			masterExceptionPrm.setMethodCode("05");
		}
		
		try {
			
			if(!errorFound && masterExceptionCompany.contains("06") ){
				inBag.put(ACCOUNTNR , companyAccount.getAccountNo());
				
				hesapBag = CBCaller.call("HESAP_HESAPBILGILERIGETIR",inBag);
				
				if(hesapBag.existsBagKey(ACCOUNTSTATUSCODE) && hesapBag.get(ACCOUNTSTATUSCODE).toString().equals("0")){
					errorFound = true;
					masterExceptionPrm.setMethodCode("06");
				}
			}

		
		} catch (Exception e) {
			errorFound = true;
			masterExceptionPrm.setMethodCode("12");
		}
		
		try {
			
			if(!errorFound && masterExceptionCompany.contains("04")){
				
				inBag.clear();
				inBag.put(ACCOUNTNR , dealerAccount.getAccountNo());
				
				hesapBag = CBCaller.call("HESAP_HESAPBILGILERIGETIR",inBag);
				
				if(hesapBag.existsBagKey(ACCOUNTSTATUSCODE) && hesapBag.get(ACCOUNTSTATUSCODE).toString().equals("0")){
					errorFound = true;
					masterExceptionPrm.setMethodCode("04");
				}
			}

		
		} catch (Exception e) {
			errorFound = true;
			masterExceptionPrm.setMethodCode("05");
		}
	
		
		return errorFound;
		
	}

	private static String handleErrorMessage(String message) throws CBException {
		
		if (message.length() > 4000) {
			message = message.substring(0, 4000);
		}
		return message;
	}

	private static CBBag setProcessBag(long masterRecordID, CBDate processDate, long sequenceNumber, DBSCompanyDebtFileMasterPOMData data, 
			                           CompanyAccount companyAccount, DealerAccount dealerAccount ,Company company, boolean useLimit) throws CBException {
		
		CBBag processBag = CBBagFactory.createBag();
		
		CBCurrency transactionAmount = CBCurrency.ZERO;
		
		if(company.isPartialPayable()){
			
			Account dealerAccountInfo = AccountUtility.getAccountInfo(dealerAccount.getAccountNo());
			
			transactionAmount = data.invoiceAmount.subtract(data.paidAmount);

			if(DebtFileService.isAccountBalanceNotEnough(dealerAccountInfo, transactionAmount, useLimit) < 0){
				if(useLimit) {
					transactionAmount = dealerAccountInfo.getAvalBalance();
				}
				else {
					transactionAmount = dealerAccountInfo.getBalanceWithoutLimit();
				}
				processBag.put(PARTIALFOUND , true);
			}
		}
		else {
			transactionAmount = data.invoiceAmount;
		}
		
		processBag.put(PROCESSDATE , processDate);
		processBag.put(NUMBER0     , sequenceNumber);
		processBag.put(OID         , data.dealerOID);
		processBag.put(MASTEROID   , masterRecordID);
		processBag.put(COMPANYOID  , data.companyID);
		processBag.put(AMOUNT      , transactionAmount);
		processBag.put(CURRENCY    , data.currency);
		processBag.put(INVOICENO   , data.invoiceNo);
		processBag.put(ACCOUNTNR   , dealerAccount.getAccountNo());
		processBag.put(INVOICEAMOUNT , data.invoiceAmount);
		
		if (companyAccount.getBlockageDayCount()>0) {
			processBag.put(ACCOUNTNR2, companyAccount.getBlockageAccountNo());
		}
		else {
			processBag.put(ACCOUNTNR2, companyAccount.getAccountNo());
		}
		
		processBag.put(LIMITUSED  , useLimit);
		
		return processBag;
	}

	private static String  getDealerInternalCustomerNo(String customerNo, long companyOID, String channelCode) throws CBException {
		
		String dealerInternalCustomerNo  = "";
		
		Company company = new Company().get(companyOID);
		
		
		if(company.getDealerCodeFormatType().equals(CompanyDealerCodeFormat.Special.value()) &&  !channelCode.toString().equals("003") ){
			dealerInternalCustomerNo = customerNo.substring(7);
		}else {
			dealerInternalCustomerNo = customerNo;
		}
		
		return dealerInternalCustomerNo;
	}

	public static CBBag prepareAndSendMail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		CBDate processDate = CBSystem.getInstance().getProcessDate();

		String emailList = EMailUtility.getEMailForFCNotification();
		String ccList = EMailUtility.getEMailForNotification();

		StringBuffer message = new StringBuffer();


		String messageHeader = "DBS Projesi kapsamýnda gerçekleþtirilen Döviz Satýþ  Ýþlemleri / Tarih : " + processDate.toDBString();


		message.append("DBS Projesi kapsamýnda aþaðýda detaylarý bulunan döviz satýþ iþlemleri gerçekleþmiþtir.");
		message.append("\n\r");
		message.append("\n\r");

		for (int index = 0; index < inBag.getSize(TABLE1); index++) {

			message.append("Proje Adý : " + inBag.get(TABLE1, index, TITLE).toString());
			message.append("\n\r");
			message.append("Döviz Cinsi : " + inBag.get(TABLE1, index, CURRENCY).toString());
			message.append("\n\r");
			message.append("Satýlan Döviz Miktarý : " + inBag.get(TABLE1, index, AMOUNT).toCBCurrency());
			message.append("\n\r");
			message.append("Banka Kuru : " + inBag.get(TABLE1, index, EXCHANGERATE).toCBCurrency());
			message.append("\n\r");
			message.append("\n\r");
			message.append("\n\r");
			message.append("\n\r");

		}

		message.append("------------------------------------------------------");
		message.append("\n\r");
		message.append("\n\r");
		message.append("Toplam Döviz Satýþ Miktarý : " + inBag.get(TOTALAMOUNT));
		message.append("\n\r");
		message.append("\n\r");
		message.append("Ýþlemin TL Karþýlýðý : " + inBag.get(TOTALAMOUNT1));
		message.append("\n\r");
		message.append("\n\r");
		message.append("Bilginize.");

		
		EMailUtility.sendMailCCAttachment(message.toString(), messageHeader, emailList.toString(), "-", ccList);

		outBag.put(RC, true);
		return outBag;

	}

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		prepareFCOperationMail_and_SendMail(inBag);
		
		processResultNotification (inBag);
	
		outBag.put(RC, true);
		
		return outBag;
	}

		
	private CBBag prepareFCOperationMail_and_SendMail(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBBag mailBag = CBBagFactory.createBag();
		

		CBQueryExecuter qe = null;

		long companyOID = inBag.get(COMPANYOID).toSimpleLong();
		long previousCompanyOID = -1L;
		
		CBCurrency totalAmount = CBCurrency.ZERO;
		CBCurrency totalTRYAmount = CBCurrency.ZERO;
		CBCurrency grandTotalUSDAmount = CBCurrency.ZERO;
		CBCurrency grandTotalTRYAmount = CBCurrency.ZERO;
		
		try {
			
			int index = 0;
			
			Company company = new Company();
			
			Customer customer = null;

			CBDate processDate = inBag.get(PROCESSDATE).toCBDate();

			qe = new CBQueryExecuter("EXT_DBS_GET_TOTAL_FC_SELL_BY_COMPANYOID");
			qe.setParameter("SEQUENCENO", inBag.get(NUMBER0).toSimpleLong());
			qe.setParameter("COMPANYOID", companyOID);
			
			
			qe.executeQuery();

			while (qe.next()) {
				company = company.get(companyOID);
				customer = CustomerUtility.getCustomerInfo(company.getCustomerNumber());
				
				
				if ( (qe.getLong("COMPANYOID") != previousCompanyOID) && (previousCompanyOID != -1L) ) {	
					
					if (mailBag.getSize(TABLE1)>0) {

						mailBag.put(TOTALAMOUNT, grandTotalUSDAmount);
						mailBag.put(TOTALAMOUNT1, grandTotalTRYAmount);
	
						prepareAndSendMail(mailBag);
						
						grandTotalUSDAmount = CBCurrency.ZERO;
						grandTotalTRYAmount = CBCurrency.ZERO;
						
						index = 0;
						mailBag.clear();
						
						sendLimitFileService(companyOID);
					}
					
					

				}
				
				if ( (qe.getLong("COMPANYOID") == previousCompanyOID) || (previousCompanyOID == -1L) ) {
					
				
					totalAmount = qe.getCBCurrency("TOTALAMOUNT");
					totalTRYAmount = qe.getCBCurrency("TOTALTRYAMOUNT");
	
	
					mailBag.put(TABLE1, index, CURRENCY, qe.getString("CURRENCY").toString());
					mailBag.put(TABLE1, index, PROCESSDATE, processDate);
					mailBag.put(TABLE1, index, AMOUNT, qe.getCBCurrency("TOTALAMOUNT"));
					mailBag.put(TABLE1, index, TITLE, customer.getTitle());
					mailBag.put(TABLE1, index, EXCHANGERATE, qe.getCBCurrency("EXCHANGERATE"));
					mailBag.put(TABLE1, index, AMOUNT1, qe.getCBCurrency("TOTALTRYAMOUNT"));
	
					grandTotalUSDAmount = grandTotalUSDAmount.add(totalAmount);
					grandTotalTRYAmount = grandTotalTRYAmount.add(totalTRYAmount);
	
					index++;
					
					previousCompanyOID = qe.getLong("COMPANYOID");
				}	

			}
			
			if (mailBag.getSize(TABLE1)>0) {

				mailBag.put(TOTALAMOUNT, grandTotalUSDAmount);
				mailBag.put(TOTALAMOUNT1, grandTotalTRYAmount);

				prepareAndSendMail(mailBag);
				
				sendLimitFileService(companyOID);
			}

			return outBag;

	
		} finally {

			if (qe != null) {
				qe.close();
			}
		

		}
	}

	
	private CBBag processResultNotification(CBBag inBag) throws CBException {
		
		CBBag outBag     = CBBagFactory.createBag();
		CBBag processBag = CBBagFactory.createBag();
		

		CBQueryExecuter qe = null;


		try {
			
			CBDate processDate = inBag.get(PROCESSDATE).toCBDate();
			long sequenceNo = inBag.get(NUMBER0).toSimpleLong();

			qe = new CBQueryExecuter("EXT_DBS_GET_COMPANY_PROCESS_RESULT_NOTIFICATION");
			
			qe.setParameter("DATE1", processDate);
			qe.setParameter("COMPANYOID", inBag.get(COMPANYOID).toSimpleLong());
			qe.setParameter("SEQUENCENO", sequenceNo);
			qe.executeQuery();
			
			while (qe.next()) {

				processBag.put(COMPANYOID , qe.getLong("COMPANYID"));
				processBag.put(PROCESSDATE, processDate);
				processBag.put(NUMBER0, sequenceNo);
				
				if(inBag.existsBagKey(TIME1)) {
					processBag.put(TIME1, inBag.get(TIME1));
				}
				
				ProcessResultFileGeneric.processResultFileOutService(processBag);

			}


			return outBag;

		} finally {

			if (qe != null) {
				qe.close();
			}
		

		}
	}
	
	private void sendLimitFileService(long companyOID) throws CBException {
		
		CBBag submitBag = CBBagFactory.createBag();
		
		submitBag.put(COMPANYOID, companyOID);
		LimitFileService.submitJob(submitBag);
		
	}
	
	// @serviceName : EXT_DBS_MANUEL_PAYMENT_DEBT
	public static CBBag manuelPaymentDBSDebt(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBDate processDate = CBDateFactory.newCBDate();
		CBBag processBag = CBBagFactory.createBag();
		CBBag processBagResult = CBBagFactory.createBag();

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();
		long sequenceNumber = Long.valueOf(GeneralUtility.generateNo(CompanyConstants.DBS_COMPANY_FC_BATCH_CODE));
		
		String methodCode = "00";
		boolean errorFound = false;
		String message = "";
		
		CompanyDebtMasterProcessExceptionParam masterExceptionPrm = null;
		List<String> masterExceptionCompany = null;
		
		try {
			if (pom.readByPrimaryKey(inBag.get(OID).toSimpleLong())) {
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				Company company = new Company().get(data.companyID);
				
			    masterExceptionPrm      = new CompanyDebtMasterProcessExceptionParam(data.companyID , methodCode);
			    masterExceptionCompany  = new CompanyDebtMasterProcessExceptionParam(data.companyID).getAllCompanyException();

				errorFound = checkCompany(company, data,  masterExceptionPrm , masterExceptionCompany);
				
				if (!errorFound) {
					Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(data.companyID, getDealerInternalCustomerNo(data.customerNo, data.companyID, data.channelCode));
					
					errorFound = checkDealer(dealer, data,  masterExceptionPrm , masterExceptionCompany);
				
					if (!errorFound) {
						CompanyAccount companyAccount = new CompanyAccount().getCompanyAccount(data.companyID, data.currency);
						DealerAccount dealerAccount = new DealerAccount().getDealerAccount(dealer.getOID(), data.currency);
						
						errorFound = checkAccounts(data, companyAccount,dealerAccount,  masterExceptionPrm , masterExceptionCompany);
												
						if (!errorFound) {
							CBSessionInfo.setIslemAnaBirimKodu(AccountUtility.getAccountBranchCode(companyAccount.getAccountNo()));
							
							processBag = setProcessBag(data.oID, processDate, sequenceNumber, data, companyAccount, dealerAccount, company, true);
							processBagResult = CBCaller.call("EXT_DBS_PROCESS_MASTER_RECORD", processBag);
							processBagResult.copyTo(outBag);
							
							if (processBagResult.get(CODE1).toString().equals(DebtRecordStatu.done.value()) || ( processBagResult.get(CODE1).toString().equals(DebtRecordStatu.partialPayment.value()) && processBag.get(AMOUNT).toCBCurrency().compare(CBCurrency.ZERO)!=0)  ) {
								
								if (companyAccount.getBlockageDayCount()>0) {
									data.transfered = TransferType.atBlockageAccount.value();
								}
								else {
									data.transfered = TransferType.atCurrentAccount.value();
								}
								
								data.accountingDate     = setAccountingDate(processDate, companyAccount.getBlockageDayCount());
								data.processStatu       = processBagResult.get(CODE1).toString();
							
								if(processBagResult.get(CODE1).toString().equals(DebtRecordStatu.done.value())){
									data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_OK;
								}else {
									data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_PARTIAL;
								}
						
								data.processDate        = processDate;
								data.processTime        = CBSystem.getInstance().getCurrentTime();
								data.productOpRefNo     = inBag.get(PRODUCTOPERATIONREFNO).toSimpleLong();
								data.paidAmount         = data.paidAmount.add(processBag.get(AMOUNT).toCBCurrency());
								data.sequenceNo 		= sequenceNumber;
	
								pom.setDBSCompanyDebtFileMasterPOMData(data);
								pom.update();
								
								createPaymentResultHistory(data, processBag.get(AMOUNT).toCBCurrency(), DBSProcessType.PAYMENT.getType());
	
								if(company.isPartialPayable() && processBag.get(AMOUNT).toCBCurrency().compare(CBCurrency.ZERO)!=0 ){
									DebtFileService.addCompanyProcessDebtExtension (processBag, data.oID, inBag.get(PRODUCTOPERATIONREFNO).toSimpleLong() );
								}
							}
							else {
								errorFound = true;
								message = "DBS fatura ödemesi gerçekleþtirilemedi!";
							}
						}
					}
				}
			}
			else{
				errorFound = true;
				message = "DBS fatura bulunamadý!";
			}
		} catch (CBException ex) {
			errorFound = true;
			masterExceptionPrm.setMethodCode("02");
			masterExceptionPrm.get();
			
			CBBag exBag = CBBagFactory.createBag();
			message = masterExceptionPrm.getExceptionDescription();
			exBag.put(NOTE, message);
			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);
		} finally {
			
			if (errorFound) {
				data.processExplanation = ProcessMasterFileConstants.DBS_MASTER_FILE_PROCESS_EXPLANATION_NOT_OK;
				data.processStatu = DebtRecordStatu.error.value();
				data.processDate = processDate;
				data.processTime = CBSystem.getInstance().getCurrentTime();
				data.sequenceNo = sequenceNumber;
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);
				pom.update();
				
				createPaymentResultHistory(data, CBCurrency.ZERO, DBSProcessType.PAYMENT.getType());
			}
			
			if (pom != null) {
				pom.close();
			}
		}
		
		if(errorFound) {
			CBBag exBag = CBBagFactory.createBag();
			exBag.put(NOTE, message);
			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);
		}
	
		outBag.put(RC, true);

		return outBag;
	}
	
	private static void createPaymentResultHistory(DBSCompanyDebtFileMasterPOMData data, CBCurrency paidAmount, int processType) throws CBException{
		PaymentResultHistory paymentResultHistory = new PaymentResultHistory();
		paymentResultHistory.setMasterOid(data.oID);
		paymentResultHistory.setPaidAmount(paidAmount);
		paymentResultHistory.setSequenceNo(data.sequenceNo);
		paymentResultHistory.setProcessType(processType);
		
		paymentResultHistory.create();
	}

	// @serviceName : EXT_DBS_MANUEL_CANCEL_DEBT
	public static CBBag manuelCancelDBSDebt(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		boolean errorFound = false;
		String message = "";
		
		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();;
		
		try {
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();
				
			if (pom.readByPrimaryKey(inBag.get(OID).toSimpleLong())) {
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				if(data.status == 1 && (DebtRecordStatu.init.value().equals(data.processStatu) || DebtRecordStatu.error.value().equals(data.processStatu))) {
					data.deleteDate = trxDate;
					data.deleteTime = trxTime;
					data.processStatu = DebtRecordStatu.deleteByFile.value();
					
					pom.setDBSCompanyDebtFileMasterPOMData(data);
					pom.update();
				}
				else {
					errorFound = true;
					message = "DBS iptal edilmek istenen fatura bekleyen veya ödenemeyen durumunda olmalý!";
				}
			}
			else{
				errorFound = true;
				message = "DBS fatura bulunamadý!";
			}
		}
		catch (Exception e) {
			CBBag exBag = CBBagFactory.createBag();
			message = "DBS fatura iptal edilemedi! " + e.getMessage();
			exBag.put(NOTE, message);
			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);
		}
		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
		if(errorFound) {
			CBBag exBag = CBBagFactory.createBag();
			exBag.put(NOTE, message);
			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);
		}
		
		return outBag;
	}

}