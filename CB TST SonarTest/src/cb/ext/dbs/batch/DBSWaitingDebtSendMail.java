package cb.ext.dbs.batch;

import static cb.ext.dbs.util.CustomerUtility.getCustomerInfo;
import static cb.ext.dbs.util.CustomerUtility.getCustomerMobilePhone;
import static cb.ext.dbs.util.CustomerUtility.getCustomerPreferedMail;

import java.util.Vector;

import cb.ext.dbs.bean.DebtFileMasterService;
import cb.ext.dbs.constants.GeneralConstants;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.EMailUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBundles;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBMultiLang;
import cb.smg.general.utility.CBSystem;

public class DBSWaitingDebtSendMail extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();
		CBBag mailBag = CBBagFactory.createBag();
		CBDate dueDate = CBSystem.getInstance().getProcessDate();
		
		int day = inBag.get(DAY).toSimpleInt();
		int sendSms = inBag.get(SMSDELIVERY).toSimpleInt();
		int sendEmail = inBag.get(EMAILDELIVERY).toSimpleInt();
		dueDate = dueDate.add(day);
		
		Vector<Dealer> dealerList = new Company().getDealers(inBag.get(COMPANYOID).toSimpleLong());
		
		for(Dealer dealer : dealerList) {
			try{
				mailBag = getDealerDebtDetailByDueDate(dealer, dueDate);
				if(mailBag.getSize(TABLE) > 0) {
					if(sendEmail == 1) {
						prepareAndSendMailWithAttachment(mailBag, dealer, dueDate);
					}
					if(sendSms == 1) {
						prepareAndSendSms(mailBag, dealer, dueDate);
					}
				}
			}
			catch (Exception e) {
				
			}
		}
		
		outBag.put(RC, true);
		return outBag;
	}
	
	public static CBBag getDealerDebtDetailByDueDate(Dealer dealer, CBDate dueDate) throws CBException {
		CBBag outBag = CBBagFactory.createBag();		
		Company company = new Company().get(dealer.getCompanyOID());
		DebtFileMasterService.getDealerWaitingDebtList(company, dealer, dueDate).copyTo(outBag);
		
		return outBag;
	}
	
	public static void prepareAndSendMailWithAttachment(CBBag inBag, Dealer dealer, CBDate dueDate) throws CBException {

		String toMailAddress = getCustomerPreferedMail(dealer.getCustomerNumber());
		
		if (isEmailFound(toMailAddress)) {
			String fileName = GeneralConstants.FILE_NAME + dueDate;
			CBBag docBag = EMailUtility.callGenerateDocument(GeneralConstants.DOCUMENT_EXPLANATION, fileName, inBag);
			String documentId = docBag.get(DOCUMENTID).toString();
						
			CBBag paramBag = CBBagFactory.createBag();
			paramBag.put(COMPANYCODE, dealer.companyDealerCustomerCode);
			paramBag.put(COMPANYTITLE, getCustomerInfo(dealer.getCustomerNumber()).getTitle());
			paramBag.put(DUEDATE, dueDate.toString("/"));
			
			String message = CBBundles.getInstance().getString("RB_WAITING_DEBT_MAIL_TO_COMPANY_BODY", CBMultiLang.getDefaultLanguage().getLocale(),paramBag);
			message = message.replace("<br>", "\n");
			String subject = CBBundles.getInstance().getString("RB_WAITING_DEBT_MAIL_TO_COMPANY_SUBJECT", CBMultiLang.getDefaultLanguage().getLocale(),paramBag);
			
			String ccMailAddress = CBSystem.getInstance().getSystemParameter("DBS_WAITING_DEBT_MAIL").toString();
			
			EMailUtility.sendMailCCAttachment(message.toString(), subject, toMailAddress, documentId, ccMailAddress);

		}
	}
	
	public static void prepareAndSendSms(CBBag inBag, Dealer dealer, CBDate dueDate) throws CBException {
		String phoneNumber = getCustomerMobilePhone(dealer.getCustomerNumber());
		
		if(phoneNumber != null && phoneNumber.trim().length() > 0) {
			CBBag paramBag = CBBagFactory.createBag();
			paramBag.put(COMPANYNAME, inBag.get(COMPANYNAME).toString());
			paramBag.put(DUEDATE, dueDate.toString("/"));
			paramBag.put(INVOICEAMOUNT, inBag.get(INVOICEAMOUNT).toCBCurrency());
			
			DealerAccount dealerAccount = new DealerAccount();
			String account = dealerAccount.getDealerAccountNo(dealer.getOID(), CurrencyUtility.DEFAULT_CURRENCY);
			paramBag.put(ACCOUNTNR, account);
			String smsMessageText = CBBundles.getInstance().getString("RB_WAITING_DEBT_SEND_SMS_TO_DEALER", CBMultiLang.getDefaultLanguage().getLocale(),paramBag);
			smsMessageText = smsMessageText.replace("<br>", "\n");
			
			CBBag smsBag = CBBagFactory.createBag();
			
			smsBag.put(CBBagKeys.OPERATION, "SENDSMS");
			smsBag.put(CBBagKeys.MESSAGETYPE, Constants.SMS_MESSAGE_TYPE_VALUE);
			smsBag.put(CBBagKeys.MESSAGETEXT, smsMessageText);
			smsBag.put(CBBagKeys.MOBILEPHONE, phoneNumber);
			CBCaller.call("ADK_SEND_SMS", smsBag);
		}
		
	}

	private static boolean isEmailFound(String email) {
		return !email.equals("");
	}

	// @serviceName : EXT_DBS_SEND_WAITING_DEBT_MAIL_TO_DEALER
	// @batchName   : EXTD7199
	// @table 		: BCH.DBS_WAIT_DEBT_SEND_MAIL
	public static CBBag sendDBSWaitingDebtMailToDealer(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		outBag.put(RC, true);
		return outBag;
	}
	
	protected CBBag finish(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		outBag.put(RC, true);
		return outBag;
	}
}
