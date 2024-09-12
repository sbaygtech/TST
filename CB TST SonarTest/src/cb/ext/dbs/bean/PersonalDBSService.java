package cb.ext.dbs.bean;

import static cb.ext.dbs.util.ExceptionUtility.throwExceptionNoParam;

import cb.ext.dbs.batch.ProcessDBSDebtFile;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DebtFileRecordStatu;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.ObservationConstants;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileDetailExceptionParam;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileDetailException;
import cb.ext.dbs.generic.data.CompanyDebtFileDetailExtension;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.util.CustomerUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;

public class PersonalDBSService implements CBBagKeys {
	
	/** @Service EXT_PERSONAL_DBS_DELETE_DEALER*/
	/** COMPANYOID, DEALER_CODE, INVOICEAMOUNT, INVOICENO */
	public static CBBag personalDbsDeleteDealer(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBBag resultBag = CBBagFactory.createBag();
		int deletedDebt = 0;
		
		long companyOid = inBag.get(COMPANYOID).toSimpleLong();
		Company company = new Company();
		company = company.get(companyOid);
		
		if(company == null) {
			throw new CBException(DBSExceptions.PERSONEL_DBS_COMPANY_NOT_FOUND, null);
		}
		
		if(!company.isActive()) {
			throw new CBException(DBSExceptions.PERSONEL_DBS_COMPANY_NOT_ACTIVE, null);
		}
		
		String dealerCustomerCode = inBag.get(DEALER_CODE).toString();
		Dealer dealer = new Dealer();
		dealer = dealer.getDealerByCompanyOidDealerCustomerNumber(companyOid, dealerCustomerCode);
		
		if(dealer == null) {
			throw new CBException(DBSExceptions.PERSONEL_DBS_DEALER_NOT_FOUND, null);
		}
		
		if(!dealer.isActive()) {
			throw new CBException(DBSExceptions.PERSONEL_DBS_DEALER_NOT_ACTIVE, null);
		}
			
		Customer customer = CustomerUtility.getCustomerInfo(dealer.getCustomerNumber());
		inBag.put(DEALER_CODE, dealer.getCompanyDealerCustomerCode());
		inBag.put(CUSTOMERTITLE, customer.getTitle());
		
		if(inBag.existsBagKey(INVOICENO) && inBag.existsBagKey(INVOICEAMOUNT) && inBag.get(INVOICEAMOUNT).toCBCurrency().isGreaterThanZero()) {
			long masterOid = processDetail(inBag);
			CBDate processDate = CBSystem.getInstance().getProcessDate();
			long sequenceNo = Long.valueOf(GeneralUtility.generateNo(CompanyConstants.DBS_COMPANY_FC_BATCH_CODE));
			
			resultBag = ProcessDBSDebtFile.processMasterRecord(masterOid, processDate, sequenceNo, true);
		}
		
		if(resultBag.existsBagKey(RESULT) && resultBag.get(RESULT).toBoolean()) {			
			dealer.setActive(false);
			dealer.update();
			
			deletedDebt = DebtFileMasterService.deleteDebtByDealerOid(dealer.getOID());
		}
		else {
			deletedDebt = 1;
		}
		
		outBag.put(DELETED, deletedDebt);
		
		return outBag;
	}
	
	private static long processDetail(CBBag inBag) throws CBException {
		String methodCode = "00";
		
		long companyOid = inBag.get(COMPANYOID).toSimpleLong();
		String dealerCode = inBag.get(DEALER_CODE).toString();
		String customerTitle = inBag.get(CUSTOMERTITLE).toString();
		CBCurrency invoiceAmount = inBag.get(INVOICEAMOUNT).toCBCurrency();
		String invoiceNo = inBag.get(INVOICENO).toString();
		
		CompanyDebtFileDetailExtension debtFileDetailExtension  = new CompanyDebtFileDetailExtension();
		CompanyDebtFileDetailExceptionParam detailExceptionPrm  = new CompanyDebtFileDetailExceptionParam(companyOid, methodCode);
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader();
		debtFileHeader.setCompanyOID(companyOid);
		debtFileHeader.setOid(-1);
		
		CompanyDebtFileDetail debtFileDetail = new CompanyDebtFileDetail();
		debtFileDetail.setCustomerNo(dealerCode);
		debtFileDetail.setCustomerTitle(customerTitle);
		debtFileDetail.setDueDate(CBSystem.getInstance().getProcessDate());
		debtFileDetail.setInvoiceAmount(invoiceAmount);
		debtFileDetail.setInvoiceNo(invoiceNo);
		debtFileDetail.setCurrency(CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode());
		debtFileDetail.setInvoiceType("N");
		debtFileDetail.setRecordType("Y");
		
		long masterOid = DebtFileMasterService.insertMasterFile(debtFileHeader, debtFileDetail);
		debtFileDetail.setMasterOID(masterOid);
		
		detailExceptionPrm = detailExceptionPrm.get();
		CompanyDebtFileDetailException detailException = new CompanyDebtFileDetailException();
		
		detailException.setExceptionCode(detailExceptionPrm.getExceptionCode());
		detailException.setExceptionDescription(detailExceptionPrm.getExceptionDescription());
		
		debtFileDetail.setProcessStatu(DebtFileRecordStatu.done.value());
		debtFileDetail.setProcessExplanation("Kayýt Ýþlendi");
		
		debtFileDetail.add(debtFileHeader , debtFileDetailExtension , detailException );
			
		return masterOid;

	}
		
	/** @service EXT_IS_PERSONEL_DBS_DEALER_EXISTS */
	public static CBBag isDealerExists(CBBag inBag) throws  CBException {
		CBBag outBag = CBBagFactory.createBag();
		
		String companyCode = inBag.get(COMPANYCODE).toString();
		String dealerCustomerCode = inBag.get(DEALER_CODE).toString();
		

    	CBQueryExecuter qe  = null ;
    	
        try {
        	qe = new CBQueryExecuter("EXT_IS_PERSONEL_DBS_DEALER_EXISTS");
        	qe.setParameter("COMPANYCODE", companyCode);
        	qe.setParameter("DEALER_CODE", dealerCustomerCode);
        	
          	qe.executeQuery();
          
          	if(qe.next()) {
	          	int count = qe.getInt("COUNT");
	          	
	          	if(count == 0) {
	          		outBag.put(EXISTSORNOT, 0);
	          	}
	          	else {
	          		outBag.put(EXISTSORNOT, 1);
	          	}
          	}
          	else {
          		outBag.put(EXISTSORNOT, 0);
          	}
		} 
        finally {
			if(qe!=null) {
				qe.close();
			}
		}
        
        return  outBag;		
	}
	
	/** @serviceName: EXT_PERSONAL_DBS_GET_COMPANY_DETAIL */
	public static CBBag getPersonalDbsCompanyDetail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company();

		String customerCode = inBag.get(COMPANYCODE).toString();
		company = company.getByInternalCode(customerCode);
	
		if(company != null) {
			outBag = CompanyService.fillOutBagWithCompany(company);
		}
		else {
			throwExceptionNoParam(DBSExceptions.COMPANY_LIST_NOT_FOUND);
		}
		
		return outBag;
	}
	
	/** @throws CBException 
	 * @serviceName: EXT_PERSONAL_DBS_CANCEL_PAYMENT */
	public static CBBag personalDBSCancelPayment(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(PRODUCTOPERATIONREFNO, inBag.get(REFNO));
		outBag.put(CANCELLATIONOPERATIONREFERENCE, inBag.get(PRODUCTOPERATIONREFNO).toSimpleLong());
				
		return outBag;
	}
	
	/** @service EXT_DBS_PERSONAL_GET_PAID_DEBT */
	public static CBBag getPersonalDbsPaidDebt(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {
			qe = new CBQueryExecuter("EXT_DBS_PERSONAL_GET_PAID_DEBT", true);
			
			qe.setParameter("DEALER_CODE", inBag.get(DEALER_CODE).toString());
			qe.setParameter("COMPANYCODE", inBag.get(COMPANYCODE).toString());
			qe.setParameter("INVOICENO", inBag.get(INVOICENO).toString());
			
			qe.executeQuery();
	
			int index = 0;

			while (qe.next()) {
				outBag.put(TABLE, index, OID , qe.getLong("OID"));
				outBag.put(TABLE, index, COMPANYCODE, qe.getString("COMPANYINTERNALCODE"));
				outBag.put(TABLE, index, DEALER_CODE, qe.getString("CUSTOMERNO"));
				outBag.put(TABLE, index, CUSTOMERNUMBER, qe.getString("CUSTOMERNUMBER"));
				outBag.put(TABLE, index, CUSTOMERTITLE, qe.getString("CUSTOMERTITLE"));
				outBag.put(TABLE, index, TITLE, qe.getString("TITLE"));
				outBag.put(TABLE, index, AMOUNT, qe.getCBCurrency("INVOICEAMOUNT"));
				outBag.put(TABLE, index, PAIDAMOUNT, qe.getCBCurrency("PAIDAMOUNT"));
				outBag.put(TABLE, index, REMAININGAMOUNT, qe.getCBCurrency("INVOICEAMOUNT").subtract(qe.getCBCurrency("PAIDAMOUNT")));
				outBag.put(TABLE, index, INVOICENO, qe.getString("INVOICENO"));
				outBag.put(TABLE, index, CURRENCY, qe.getString("CURRENCY"));
				outBag.put(TABLE, index, DUEDATE, qe.getCBDate("DUEDATE"));
				outBag.put(TABLE, index, PROCESSDATE, qe.getCBDate("PROCESSDATE"));
				outBag.put(TABLE, index, APPLICATIONDATE, qe.getCBDate("APPLICATIONDATE"));
				outBag.put(TABLE, index, PRODUCTOPERATIONREFNO, qe.getString("PRODUCTOPREFNO"));
				outBag.put(TABLE, index, ACCOUNTNR, qe.getString("ACCOUNTNO"));
				outBag.put(TABLE, index, DATE2, qe.getCBDate("INSERTDATE"));
				outBag.put(TABLE, index, TYPE, qe.getString("INVOICETYPE"));
				outBag.put(TABLE, index, STATE, qe.getString("STATE"));
				outBag.put(TABLE, index, APPLICATIONDETAIL, qe.getString("EXPLANATION"));
				
				index++;
			}
			
			return outBag;
		}

		finally {
			if (qe != null) {
				qe.close();
			}
		}
	}
	
	// @serviceName : EXT_DBS_PERSONAL_GET_INVOICE_PAYMENT_DETAIL
	public static CBBag getPersonalDbsDealerPaymentDetail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		String processStatu = "";
		String processCode  = "";
		String processExplanation = "";

		String nokProcessExplanation = "";

		try {
			qe = new CBQueryExecuter("EXT_DBS_PERSONAL_GET_INVOICE_PAYMENT_DETAIL", true);
			
			if (inBag.existsBagKey(DEALER_CODE) && (!inBag.get(DEALER_CODE).toString().equals(""))) {
				qe.setParameter("DEALER_CODE", inBag.get(DEALER_CODE).toString());
			}
			
			if (inBag.existsBagKey(CUSTOMERNUMBER) && (!inBag.get(CUSTOMERNUMBER).toString().equals(""))) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
			
			if (inBag.existsBagKey(COMPANYCODE) && (!inBag.get(COMPANYCODE).toString().equals(""))) {
				qe.setParameter("COMPANYCODE", inBag.get(COMPANYCODE).toString());
			}
			
			if (inBag.existsBagKey(STATE) && (!inBag.get(STATE).toString().equals(""))) {
				qe.setParameter("STATE", inBag.get(STATE).toString());
			}
			
			if(inBag.existsBagKey(STATUS)) {
				if (!inBag.get(STATUS).toString().equals("")) {
					processCode = inBag.get(STATUS).toString();
				}
		
				if(inBag.get(STATUS).toString().equals(DebtRecordStatu.init.value()) || inBag.get(STATUS).toString().equals(DebtRecordStatu.done.value())){
					processCode = processCode + "," + DebtRecordStatu.partialPayment.value();
				}
				qe.setParameter("PROCESSSTATU", GeneralUtility.parseList(processCode));
			}
			
			if (inBag.existsBagKey(INVOICENO) && (!inBag.get(INVOICENO).toString().equals(""))) {
				qe.setParameter("INVOICENO", inBag.get(INVOICENO).toString());
			}
			
			if (inBag.existsBagKey(DATE1) && (!inBag.get(DATE1).toString().equals(""))) {
				qe.setParameter("DATE1", inBag.get(DATE1).toCBDate());
			}

			if (inBag.existsBagKey(DATE2) && (!inBag.get(DATE2).toString().equals(""))) {
				qe.setParameter("DATE2", inBag.get(DATE2).toCBDate());
			}
			
			if (inBag.existsBagKey(DATE3) && (!inBag.get(DATE3).toString().equals(""))) {
				qe.setParameter("DATE3", inBag.get(DATE3).toCBDate());
			}

			if (inBag.existsBagKey(DATE4) && (!inBag.get(DATE4).toString().equals(""))) {
				qe.setParameter("DATE4", inBag.get(DATE4).toCBDate());
			}

			if (inBag.existsBagKey(DATE) && (!inBag.get(DATE).toString().equals(""))) {
				qe.setParameter("DATE", inBag.get(DATE).toCBDate());
			}

			if (inBag.existsBagKey(DATE_) && (!inBag.get(DATE_).toString().equals(""))) {
				qe.setParameter("DATE_", inBag.get(DATE_).toCBDate());
			}
			
			if(inBag.existsBagKey(ROWTABLE_ROWSIZE)) {
				qe.setRowsInPage(inBag.get(ROWTABLE_ROWSIZE).toSimpleInt());
			}
		
			qe.executeQuery();

			int index = 0;
			
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
				outBag.put(TABLE3, index, COMPANYCODE, qe.getString("COMPANYINTERNALCODE"));
				outBag.put(TABLE3, index, NATIONALIDENTITYNO, qe.getString("CUSTOMERNO"));
				outBag.put(TABLE3, index, CUSTOMERNUMBER, qe.getString("CUSTOMERNUMBER"));
				outBag.put(TABLE3, index, CUSTOMERTITLE, qe.getString("CUSTOMERTITLE"));
				outBag.put(TABLE3, index, TITLE, qe.getString("TITLE"));
				outBag.put(TABLE3, index, AMOUNT, qe.getCBCurrency("INVOICEAMOUNT"));
				outBag.put(TABLE3, index, PAIDAMOUNT, qe.getCBCurrency("PAIDAMOUNT"));
				outBag.put(TABLE3, index, REMAININGAMOUNT, qe.getCBCurrency("INVOICEAMOUNT").subtract(qe.getCBCurrency("PAIDAMOUNT")));
				outBag.put(TABLE3, index, BILLNUMBER, qe.getString("INVOICENO"));
				outBag.put(TABLE3, index, CURRENCY, qe.getString("CURRENCY"));
				outBag.put(TABLE3, index, DUEDATE, qe.getCBDate("DUEDATE"));
				outBag.put(TABLE3, index, PROCESSDATE, qe.getCBDate("PROCESSDATE"));
				outBag.put(TABLE3, index, APPLICATIONDATE, qe.getCBDate("APPLICATIONDATE"));
				outBag.put(TABLE3, index, STATUS, processExplanation);
				outBag.put(TABLE3, index, EXPLANATION, nokProcessExplanation);
				outBag.put(TABLE3, index, PRODUCTOPERATIONREFNO, qe.getString("PRODUCTOPREFNO"));
				outBag.put(TABLE3, index, ACCOUNTNR, qe.getString("ACCOUNTNO"));
				outBag.put(TABLE3, index, PROCESSTYPE, processStatu);
				outBag.put(TABLE3, index, DATE2, qe.getCBDate("INSERTDATE"));
				outBag.put(TABLE3, index, TYPE, qe.getString("INVOICETYPE"));
				outBag.put(TABLE3, index, STATE, qe.getString("STATE"));
				
				index++;
			}

			if (qe.nextPageExists()) {
				outBag.put(NEXT, "1");
			}
			else {
				outBag.put(NEXT, "0");
			}
				
			return outBag;
		}

		finally {
			if (qe != null) {
				qe.close();
			}
		}

	}
}
