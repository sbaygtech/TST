package cb.ext.dbs.batch;

import static cb.ext.dbs.util.CustomerUtility.getCustomerInfo;
import static cb.ext.dbs.util.CustomerUtility.getCustomerPreferedMail;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.EMailUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBDataSource;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;

public class SendDealerDebtMail extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {

			CBDate processDate = CBSystem.getInstance().getProcessDate();

			String tableName = inBag.get(TABLE).toString();

			CBDataSource.truncateTable(tableName);
			CBDataSource.dropIndex(tableName);

			qe = new CBQueryExecuter("EXT_DBS_INSERT_BCH_DEALER_FUTURE_DEBT");
			qe.setParameter("TABLENAME", tableName.substring(4));
			qe.setParameter("DUEDATE", processDate);

			qe.executeQuery();

			CBDataSource.createIndex(tableName);

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

	// @serviceName : EXT_DBS_SEND_DEALER_DEBT_MAIL
	// @bchName     : BCH.DBS_SEND_DEALER_MAIL
	// @batchName   : EXTD7141
	public static CBBag sendDBSMailDealer(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag dataBag = CBBagFactory.createBag();


		for (int index = 0; index < inBag.getSize(BAG); index++) {

			dataBag = inBag.get(BAG, index, -1).toBag();

			getDealerDebtDetail(dataBag.get(OID).toSimpleLong());

		}

		return outBag;

	}

	public static void getDealerDebtDetail(long dealerOID) throws CBException {

		CBBag mailBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {
			
			int index = 0;
			
			String currency;
			
			Dealer dealer = new Dealer().getDealer(dealerOID);
			
			CBDate processDate = CBSystem.getInstance().getProcessDate();
			
			
			qe = new CBQueryExecuter("EXT_DBS_GET_DEALER_DEBT_DETAIL");
			qe.setParameter("DUEDATE", processDate);
			qe.setParameter("DEALEROID", dealerOID);
			qe.executeQuery();

			

			while (qe.next()) {
				
				currency = qe.getString("CURRENCY");
				
				mailBag.put(TABLE1, index, OID, dealerOID);
				mailBag.put(TABLE1, index, CURRENCY, currency);
				mailBag.put(TABLE1, index, COUNT, qe.getInt("COUNT"));				
				mailBag.put(TABLE1, index, AMOUNT, qe.getCBCurrency("AMOUNT"));
				
				index++;

				

			}

			if (isDealerHasDebt(mailBag.getSize(TABLE1))) {
				prepareAndSendMail(dealer, mailBag);
			}
	


		} finally {
			
			if (qe != null) {
				qe.close();
			}
			
		}
	}

	private static boolean isDealerHasDebt(int dealerDebtSize) {
		return dealerDebtSize > 0;
	}

	public static void prepareAndSendMail(Dealer dealer, CBBag inBag) throws CBException {

		String email = getCustomerPreferedMail(dealer.getCustomerNumber());

		if (isEmailFound(email)) {
			
			CBDate processDate = CBDateFactory.newCBDate();

			String shortCode = "";
			
			String messageHeader = "";
			
			StringBuffer message = new StringBuffer();

	
			Company company = new Company().get(dealer.getCompanyOID());

			String companyTitle = getCustomerInfo(company.getCustomerNumber()).getTitle();
			
			

			messageHeader = "Fibabanka A.Þ  DBS Fatura Bildirimi . Tarih : " + processDate;
			
			message.append("\n\r");
			message.append("\n\r");

			message.append("Sayýn Yetkili,");
			message.append("\n\r");
			message.append("\n\r");
			message.append(companyTitle + " DBS Projesi Kapsamýnda " + processDate + " Tarihli fatura ödemeleriniz aþaðýda yer almaktadýr. ");
			message.append("\n\r");
			message.append("Firma Adý : " + companyTitle);
			message.append("\n\r");
			message.append("Bayi Kodu : " + dealer.getCompanyDealerCustomerCode());
			message.append("\n\r");
			
			for (int index = 0; index < inBag.getSize(TABLE1); index++) {

				shortCode = getCurrencyShortCodeForMail(inBag.get(TABLE1, index, CURRENCY).toString());
				
				message.append(shortCode + " Fatura Adedi : " + inBag.get(TABLE1, index, COUNT).toSimpleInt());
				message.append("\n\r");
				message.append(shortCode + " Fatura Tutari : " + inBag.get(TABLE1, index, AMOUNT).toCBCurrency());

			}

			message.append("\n\r");
			message.append("\n\r");
			message.append("Saygýlarýmýzla");
			message.append("\n\r");
			message.append("Fibabanka Nakit Yönetimi");

			
			EMailUtility.sendMail(message.toString(), messageHeader, email);

		}


	}
	
	private static String getCurrencyShortCodeForMail(String currency) {
		
		String shortCode = "YP";
		
		if (CurrencyUtility.isCurrencyTRY(currency)) {
			shortCode = "TL";
		}
		
		return shortCode;
		
	}

	private static boolean isEmailFound(String email) {
		return !email.equals("");
	}

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(RC, true);

		return outBag;

	}

}
