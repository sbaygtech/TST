package cb.ext.dbs.bean;

import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.GeneralConstants;
import cb.ext.dbs.constants.ObservationConstants;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.generic.SendLimitFileGeneric;
import cb.ext.dbs.generic.data.LimitFileGrandTotals;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.CustomerUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;

public class DBSObservationService implements CBBagKeys {

	
		
	//@serviceName : EXT_DBS_OBSERVE_DEALER_ACCOUNT_DETAIL
	public static CBBag observeDealerLimits (CBBag inBag) throws CBException{
		
		CBBag outBag 	 = CBBagFactory.createBag();
		
		CBBag accountDetailBag = CBBagFactory.createBag();
		
		CBQueryExecuter qe = null;
		
		long companyOID = 0L;
		long dealerOID = 0L;
		
		String companyDealerCusCode = "";
		String reportSequenceNumber = "";
		
		int index = 0;
		
		boolean screenMode = true;
		
		try {

			qe = new CBQueryExecuter("EXT_QR_DBS_LIST_DEALER");

			if (inBag.existsBagKey(COMPANYOID)&&!inBag.get(COMPANYOID).toString().equals("")) {
				qe.setParameter("COMPANYOID" , inBag.get(COMPANYOID).toSimpleLong());
			}
			
			if (inBag.existsBagKey(BRANCHCODE) && !inBag.get(BRANCHCODE).toString().equals("")) {
				qe.setParameter("ORGANIZATION", inBag.get(BRANCHCODE).toString());
			}
			
			if (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
			
			qe.executeQuery();

			
			while (qe.next()) {
				
				companyOID = qe.getLong("COMPANYOID");
				dealerOID = qe.getLong("OID");
				
				companyDealerCusCode = qe.getString("COMPANYDEALERCUSCODE");
				
				accountDetailBag.put(COMPANYOID, companyOID);
				accountDetailBag.put(OID  ,  dealerOID);
				accountDetailBag.put(CODE1,  companyDealerCusCode);
				
				
				try {
					
					accountDetailBag = getDealerAccountDetail(accountDetailBag,	 screenMode );
					
					outBag.put(TABLE1, index, ACCOUNTNR2, accountDetailBag.get(ACCOUNTNR2));
					outBag.put(TABLE1, index, ACCOUNT_, accountDetailBag.get(ACCOUNT_));
					outBag.put(TABLE1, index, AMOUNT, accountDetailBag.get(AMOUNT));
					outBag.put(TABLE1, index, AMOUNT1, accountDetailBag.get(AMOUNT1));
					outBag.put(TABLE1, index, AMOUNT2, accountDetailBag.get(AMOUNT2));
					outBag.put(TABLE1, index, AMOUNT3, accountDetailBag.get(AMOUNT3));
					
				}
				catch (CBException ex){
					
					outBag.put(TABLE1, index, ACCOUNTNR2, "");
					outBag.put(TABLE1, index, ACCOUNT_, "");
					outBag.put(TABLE1, index, AMOUNT, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT1, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT2, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT3, CBCurrency.ZERO);
				}
				
				outBag.put(TABLE1, index, DEALER, qe.getString("TITLE"));
								
				outBag.put(TABLE1, index, OID, dealerOID);
				outBag.put(TABLE1, index, ACTIVE, qe.getString("ACTIVE"));
				outBag.put(TABLE1, index, CUSTOMERNUMBER, qe.getString("CUSTOMERNUMBER"));
				outBag.put(TABLE1, index, CODE1, companyDealerCusCode);
				
				outBag.put(TABLE1, index, COMPANYTITLE, qe.getString("COMPANYCUSTOMERTITLE"));
				outBag.put(TABLE1, index, CURRENCY, CurrencyUtility.DEFAULT_CURRENCY);
				outBag.put(TABLE1, index, LIMITAMOUNT , qe.getCBCurrency("COMPANYTOTALLIMIT"));
				
				index++;
				
			}

		
			if(inBag.existsBagKey(REPORTCODE) && inBag.get(REPORTCODE).toString().equals("1")){
				
				reportSequenceNumber = LimitFileService.addReportLimitFile(outBag);
				outBag.put(SEQUENCE_NUMBER , reportSequenceNumber);
			}
		
			return outBag;
		}
		finally{
			
			if (qe!=null) {
				qe.close();
			}
			
		}

	}
	
	public static CBBag getDealerAccountDetail (CBBag inBag, boolean screenMode) throws CBException{
		
		CBBag outBag =  CBBagFactory.createBag();
		
		long companyOID = inBag.get(COMPANYOID).toSimpleLong();
		long dealerOID = inBag.get(OID).toSimpleLong();
		
		Company company = new Company().get(companyOID);
		Dealer dealer = new Dealer().getDealer(dealerOID);
		
		DealerAccount dealerAccountTRY = new DealerAccount();
		DealerAccount dealerAccountUSD = new DealerAccount();

		
		dealerAccountTRY = dealerAccountTRY.getDealerAccount(dealer.getOID(), CurrencyUtility.DEFAULT_CURRENCY);
		dealerAccountUSD = dealerAccountUSD.getDealerAccount(dealer.getOID(), "USD");
		
		//SendLimitFile sendLimitFile = new SendLimitFile();
		//LimitFileGrandTotals limitFileGrandTotals = sendLimitFile.constructDetail(null, CBSystem.getInstance().getProcessDate(), company, dealer);
		
		LimitFileGrandTotals limitFileGrandTotals = SendLimitFileGeneric.constructDetail(null, CBSystem.getInstance().getProcessDate(), company, dealer, screenMode);
		
		outBag.put(ACCOUNTNR2, dealerAccountTRY.getAccountNo());
		if (dealerAccountUSD !=null) {
			outBag.put(ACCOUNT_, dealerAccountUSD.getAccountNo());
		}
		else {
			outBag.put(ACCOUNT_, GeneralConstants.EMPTY);
		}
		//outBag.put(DEALER, CustomerUtility.getCustomerInfo(dealer.getCustomerNumber()).getTitle());
		outBag.put(AMOUNT, limitFileGrandTotals.getGrandTotalLimit());
		outBag.put(AMOUNT1, limitFileGrandTotals.getGrandTotalUsedAmountFromKMH());
		outBag.put(AMOUNT2, limitFileGrandTotals.getGrandTotalGuaranteedInvoiceAmount());
		outBag.put(AMOUNT3, limitFileGrandTotals.getGrandTotalDealerAvalTotalAmount());

		
		return outBag;
			
	
	}
	

	// @serviceName :EXT_DBS_GET_PAYMENT_DETAIL
	public static CBBag getDealerPaymentDetail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		String processStatu = "";
		String processCode  = "";
		String processExplanation = "";

		String nokProcessExplanation = "";

		try {
			if ((inBag.existsBagKey(PRINT) && inBag.get(PRINT).toString().equals("Y")) || CBSessionInfo.isInternet()) {
				qe = new CBQueryExecuter("EXT_DBS_GET_INVOICE_PAYMENT_DETAIL");
			}
			else {
				qe = new CBQueryExecuter("EXT_DBS_GET_INVOICE_PAYMENT_DETAIL", true);
			}
			
			if(inBag.existsBagKey(FUTUREPAYMENT) && inBag.get(FUTUREPAYMENT).toString().equals("Y")) {
				if (inBag.existsBagKey(STATUS) && (!inBag.get(STATUS).toString().equals(""))) {
					qe.setParameter("PROCESSSTATU", inBag.get(STATUS).toString());
				}
				else {
					String[] processCodeArray = {DebtRecordStatu.init.value(), DebtRecordStatu.error.value(), DebtRecordStatu.partialPayment.value()};
					qe.setParameter("PROCESSSTATU", processCodeArray);
				}
			}
			else if(inBag.existsBagKey(STATUS) && !inBag.get(STATUS).toString().equals("")) {
				processCode = inBag.get(STATUS).toString();
				
				if(inBag.get(STATUS).toString().equals(DebtRecordStatu.init.value()) || inBag.get(STATUS).toString().equals(DebtRecordStatu.done.value())){
					processCode = processCode + "," + DebtRecordStatu.partialPayment.value();
				}
				qe.setParameter("PROCESSSTATU", GeneralUtility.parseList(processCode));
			}

			if (inBag.existsBagKey(DEALER) && (!inBag.get(DEALER).toString().equals(""))) {
				qe.setParameter("DEALEROID", inBag.get(DEALER).toSimpleLong());// inBag.get(CUSTOMERNUMBER).toString();
			}

			if (inBag.existsBagKey(CURRENCY) && (!inBag.get(CURRENCY).toString().equals(""))) {
				qe.setParameter("CURRENCY", inBag.get(CURRENCY).toString());
			}
			
			if (inBag.existsBagKey(DATE1) && (!inBag.get(DATE1).toString().equals(""))) {
				qe.setParameter("DATE1", inBag.get(DATE1).toCBDate());
			}

			if (inBag.existsBagKey(DATE2) && (!inBag.get(DATE2).toString().equals(""))) {
				qe.setParameter("DATE2", inBag.get(DATE2).toCBDate());
			}
			if (inBag.existsBagKey(VALUE1) && (!inBag.get(VALUE1).toString().equals(""))) {
				qe.setParameter("COMPANYID", inBag.get(VALUE1).toSimpleLong());
			}

			if (inBag.existsBagKey(DATE3) && (!inBag.get(DATE3).toString().equals(""))) {
				qe.setParameter("DATE3", inBag.get(DATE3).toCBDate());
			}

			if (inBag.existsBagKey(DATE4) && (!inBag.get(DATE4).toString().equals(""))) {
				qe.setParameter("DATE4", inBag.get(DATE4).toCBDate());
			}

			if (inBag.existsBagKey(CUSTOMERNR) && (!inBag.get(CUSTOMERNR).toString().equals(""))) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNR).toString());
			}
			if (inBag.existsBagKey(BRANCHCODE) && (!inBag.get(BRANCHCODE).toString().equals(""))) {
				qe.setParameter("BRANCHCODE", inBag.get(BRANCHCODE).toString());
			}
			if (inBag.existsBagKey(ACCOUNTNR) && (!inBag.get(ACCOUNTNR).toString().equals(""))) {
				qe.setParameter("ACCOUNTNR", inBag.get(ACCOUNTNR).toString());
			}
			if (inBag.existsBagKey(INVOICEAMOUNT) && (!inBag.get(INVOICEAMOUNT).toString().equals(""))) {
				qe.setParameter("INVOICEAMOUNT", inBag.get(INVOICEAMOUNT).toCBCurrency());
			}
			if (inBag.existsBagKey(INVOICENO) && (!inBag.get(INVOICENO).toString().equals(""))) {
				qe.setParameter("INVOICENO", inBag.get(INVOICENO).toString());
			}
			if (inBag.existsBagKey(TYPE) && (!inBag.get(TYPE).toString().equals(""))) {
				qe.setParameter("TYPE", inBag.get(TYPE).toString());
			}
			if (inBag.existsBagKey(DEALER_CODE) && (!inBag.get(DEALER_CODE).toString().equals(""))) {
				qe.setParameter("DEALER_CODE ", inBag.get(DEALER_CODE).toString());
			}
			if (inBag.existsBagKey(COMPANYCODE) && (!inBag.get(COMPANYCODE).toString().equals(""))) {
				qe.setParameter("COMPANYCODE ", inBag.get(COMPANYCODE).toString());
			}
			
			qe.executeQuery();

			int index = 0;

			if (inBag.existsBagKey(PRINT) && inBag.get(PRINT).toString().equals("Y")) {

				CBBag printBag = CBBagFactory.createBag();
				CBBag prmBag = CBBagFactory.createBag();
				
				qe.getQueryAndParameters(printBag, true);

				prmBag.put(CALLERSERVICENAME, "");
				prmBag.put(DOKUMANKODU, "0158");
				prmBag.put(DOKUMANDATA, printBag);
				prmBag.put(DOCFORMAT, "");
				prmBag.put(ACIKLAMA, "DBS Bayi Tahsilat Gözlem");

				CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", prmBag);
				outBag.put(DOCUMENTID, retbag.get(DOCUMENTID).toString());
				outBag.put(INFO_MESSAGE_ID, 240180);


			}
			else {

				if(inBag.existsBagKey(PAGENUMBER)){
					qe.move(inBag.get(PAGENUMBER).toSimpleInt());
				}

				while (qe.next()) {

					processStatu = qe.getString("PROCESSSTATU");
					nokProcessExplanation = "";

					if (processStatu.equals(DebtRecordStatu.init.value())) {

						processExplanation = ObservationConstants.DEBT_RECORD_STATU_INIT_TEXT;

					}
					else if (processStatu.equals(DebtRecordStatu.done.value())) {

						processExplanation = ObservationConstants.DEBT_RECORD_STATU_OK_TEXT;

					}
					else if (processStatu.equals(DebtRecordStatu.error.value())) {

						processExplanation = ObservationConstants.DEBT_RECORD_STATU_NOK_TEXT;
						nokProcessExplanation = qe.getString("PROCESSEXPLANATION");

					}
					else if (processStatu.equals(DebtRecordStatu.deleteByFile.value())) {

						processExplanation = ObservationConstants.DEBT_RECORD_STATU_DELETED_BY_FILE_TEXT;

					}else if (processStatu.equals(DebtRecordStatu.partialPayment.value())) {
						
						processExplanation = ObservationConstants.DEBT_RECORD_STATU_PARTIAL_PAYMENT;
					}

					outBag.put(TABLE3, index, OID , qe.getLong("OID"));
					outBag.put(TABLE3, index, CUSTOMERNUMBER, qe.getString("CUSTOMERNO"));
					outBag.put(TABLE3, index, CUSTOMERNR, qe.getString("CUSTOMERNUMBER"));
					outBag.put(TABLE3, index, COMPANYTITLE, qe.getString("COMPANYCUSTOMERTITLE"));
					outBag.put(TABLE3, index, DEALER, qe.getString("TITLE"));
					outBag.put(TABLE3, index, AMOUNT, qe.getCBCurrency("INVOICEAMOUNT"));
					outBag.put(TABLE3, index, PAIDAMOUNT, qe.getCBCurrency("PAIDAMOUNT"));
					outBag.put(TABLE3, index, REMAININGAMOUNT, qe.getCBCurrency("INVOICEAMOUNT").subtract(qe.getCBCurrency("PAIDAMOUNT")));
					outBag.put(TABLE3, index, INVOICENO, qe.getString("INVOICENO"));
					outBag.put(TABLE3, index, CURRENCY, qe.getString("CURRENCY"));
					outBag.put(TABLE3, index, DUEDATE, qe.getCBDate("DUEDATE"));
					outBag.put(TABLE3, index, DATE_, qe.getCBDate("PROCESSDATE"));
					outBag.put(TABLE3, index, STATUS, processExplanation);
					outBag.put(TABLE3, index, EXPLANATION, nokProcessExplanation);
					outBag.put(TABLE3, index, PRODUCTOPERATIONREFNO, qe.getString("PRODUCTOPREFNO"));
					outBag.put(TABLE3, index, ACCOUNTNR, qe.getString("ACCOUNTNO"));
					outBag.put(TABLE3, index, PROCESSTYPE, processStatu);
					outBag.put(TABLE3, index, DATE2, qe.getCBDate("INSERTDATE"));
					outBag.put(TABLE3, index, TYPE, qe.getString("INVOICETYPE"));
					index++;

				}

				if (qe.nextPageExists()) {
					outBag.put(NEXT, "1");
				}
				else {
					outBag.put(NEXT, "0");
				}
				
			}

			return outBag;

		}

		finally {
			if (qe != null) {
				qe.close();
			}
		}

	}
	
	// @serviceName : EXT_DBS_GET_PAYMENT_DETAIL_BY_OID
	public static CBBag getDealerPaymentDetailByOid(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBQueryExecuter qe = null;

		String processStatu = "";
		String processExplanation = "";
		String nokProcessExplanation = "";

		try {			
			qe = new CBQueryExecuter("EXT_DBS_GET_INVOICE_PAYMENT_DETAIL", true);
			qe.setParameter("OID", inBag.get(OID).toSimpleLong());
			qe.executeQuery();
			qe.next();

			processStatu = qe.getString("PROCESSSTATU");
			nokProcessExplanation = "";

			if (processStatu.equals(DebtRecordStatu.init.value())) {
				processExplanation = ObservationConstants.DEBT_RECORD_STATU_INIT_TEXT;
			}
			else if (processStatu.equals(DebtRecordStatu.done.value())) {
				processExplanation = ObservationConstants.DEBT_RECORD_STATU_OK_TEXT;
			}
			else if (processStatu.equals(DebtRecordStatu.error.value())) {
				processExplanation = ObservationConstants.DEBT_RECORD_STATU_NOK_TEXT;
				nokProcessExplanation = qe.getString("PROCESSEXPLANATION");
			}
			else if (processStatu.equals(DebtRecordStatu.deleteByFile.value())) {
				processExplanation = ObservationConstants.DEBT_RECORD_STATU_DELETED_BY_FILE_TEXT;
			}
			else if (processStatu.equals(DebtRecordStatu.partialPayment.value())) {
				processExplanation = ObservationConstants.DEBT_RECORD_STATU_PARTIAL_PAYMENT;
			}

			outBag.put(TABLE3, 0, OID , qe.getLong("OID"));
			outBag.put(TABLE3, 0, CUSTOMERNUMBER, qe.getString("CUSTOMERNO"));
			outBag.put(TABLE3, 0, CUSTOMERNR, qe.getString("CUSTOMERNUMBER"));
			outBag.put(TABLE3, 0, COMPANYTITLE, qe.getString("COMPANYCUSTOMERTITLE"));
			outBag.put(TABLE3, 0, DEALER, qe.getString("TITLE"));
			outBag.put(TABLE3, 0, AMOUNT, qe.getCBCurrency("INVOICEAMOUNT"));
			outBag.put(TABLE3, 0, PAIDAMOUNT, qe.getCBCurrency("PAIDAMOUNT"));
			outBag.put(TABLE3, 0, REMAININGAMOUNT, qe.getCBCurrency("INVOICEAMOUNT").subtract(qe.getCBCurrency("PAIDAMOUNT")));
			outBag.put(TABLE3, 0, INVOICENO, qe.getString("INVOICENO"));
			outBag.put(TABLE3, 0, CURRENCY, qe.getString("CURRENCY"));
			outBag.put(TABLE3, 0, DUEDATE, qe.getCBDate("DUEDATE"));
			outBag.put(TABLE3, 0, DATE_, qe.getCBDate("PROCESSDATE"));
			outBag.put(TABLE3, 0, STATUS, processExplanation);
			outBag.put(TABLE3, 0, EXPLANATION, nokProcessExplanation);
			outBag.put(TABLE3, 0, PRODUCTOPERATIONREFNO, qe.getString("PRODUCTOPREFNO"));
			outBag.put(TABLE3, 0, ACCOUNTNR, qe.getString("ACCOUNTNO"));
			outBag.put(TABLE3, 0, PROCESSTYPE, processStatu);
			outBag.put(TABLE3, 0, DATE2, qe.getCBDate("INSERTDATE"));
			outBag.put(TABLE3, 0, TYPE, qe.getCBDate("INVOICETYPE"));
			
			outBag.put(INVOICENO, qe.getString("INVOICENO"));
			outBag.put(AMOUNT, qe.getCBCurrency("AMOUNT"));
			outBag.put(CURRENCY, qe.getString("CURRENCY"));
			outBag.put(DUEDATE, qe.getCBDate("DUEDATE"));
			
			return outBag;
		}
		finally {
			if (qe != null) {
				qe.close();
			}
		}
	}
	
	/**@serviceName : EXT_DBS_OBSERVE_PERSONAL_DEALER */
	public static CBBag observePersonalDBSDealer (CBBag inBag) throws CBException{
		
		CBBag outBag 	 = CBBagFactory.createBag();	
		CBQueryExecuter qe = null;
		
		int index = 0;
		
		try {
			
			if ((inBag.existsBagKey(PRINT) && inBag.get(PRINT).toString().equals("Y")) || CBSessionInfo.isInternet()) {
				qe = new CBQueryExecuter("EXT_DBS_PERSONAL_DEALER_LIST");
			}
			else {
				qe = new CBQueryExecuter("EXT_DBS_PERSONAL_DEALER_LIST", true);
			}

			if (inBag.existsBagKey(COMPANYOID)&&!inBag.get(COMPANYOID).toString().equals("")) {
				qe.setParameter("COMPANYOID" , inBag.get(COMPANYOID).toSimpleLong());
			}
			
			if (inBag.existsBagKey(BRANCHCODE) && !inBag.get(BRANCHCODE).toString().equals("")) {
				qe.setParameter("ORGANIZATION", inBag.get(BRANCHCODE).toString());
			}
			
			if (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
						
			qe.executeQuery();
			
			if (inBag.existsBagKey(PRINT) && inBag.get(PRINT).toString().equals("Y")) {

				CBBag printBag = CBBagFactory.createBag();
				CBBag prmBag = CBBagFactory.createBag();
				
				printBag.put(QUERY,qe.getQueryString());

				prmBag.put(CALLERSERVICENAME, "");
				prmBag.put(DOKUMANKODU, "0158");
				prmBag.put(DOKUMANDATA, printBag);
				prmBag.put(DOCFORMAT, "");
				prmBag.put(ACIKLAMA, "DBS Bayi Tahsilat Gözlem");

				CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", prmBag);
				outBag.put(DOCUMENTID, retbag.get(DOCUMENTID).toString());
				outBag.put(INFO_MESSAGE_ID, 240180);
			}
			else {
				
				if(inBag.existsBagKey(PAGENUMBER)){
					qe.move(inBag.get(PAGENUMBER).toSimpleInt());
				}

				while (qe.next()) {
					
					outBag.put(TABLE1, index, ACTIVE, qe.getString("ACTIVE"));
					outBag.put(TABLE1, index, OID, qe.getLong("OID"));
					outBag.put(TABLE1, index, COMPANYOID, qe.getLong("COMPANYOID"));
					outBag.put(TABLE1, index, COMPANYNAME, qe.getString("COMPANYNAME"));
					outBag.put(TABLE1, index, DEALERADI, qe.getString("DEALERADI"));
					outBag.put(TABLE1, index, CUSTOMERNUMBER, qe.getString("COMPANYDEALERCUSCODE"));
					outBag.put(TABLE1, index, CUSTOMERNR, qe.getString("CUSTOMERNUMBER"));
					outBag.put(TABLE1, index, ORGANIZATIONNAME, qe.getString("ORGANIZASYONADI"));
					outBag.put(TABLE1, index, BRANCHCODE, qe.getString("ORGANIZATION"));
					
					index++;
				}
				
				if (qe.nextPageExists()) {
					outBag.put(NEXT, "1");
				}
				else {
					outBag.put(NEXT, "0");
				}
				
			}
			
			return outBag;
		}
		finally{
			if (qe!=null) {
				qe.close();
			}
		}
	}
}
