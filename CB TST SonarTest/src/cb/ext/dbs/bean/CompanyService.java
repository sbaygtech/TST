package cb.ext.dbs.bean;

import static cb.ext.dbs.util.CompanyDealerValidations.checkAccounts;
import static cb.ext.dbs.util.CompanyDealerValidations.checkCurrencyExists;
import static cb.ext.dbs.util.CompanyDealerValidations.checkCustomerNumber;
import static cb.ext.dbs.util.CompanyDealerValidations.doMailValidation;
import static cb.ext.dbs.util.ExceptionUtility.throwException;
import static cb.ext.dbs.util.ExceptionUtility.throwExceptionNoParam;
import static cb.ext.dbs.util.InfoMessageUtility.putInfoMessage;

import java.util.Hashtable;
import java.util.Vector;

import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DBSOperationType;
import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyAccount;
import cb.ext.dbs.data.CompanyDebtFileResultEMail;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.data.CompanyDebtFileTypeParam;
import cb.ext.dbs.data.CompanyDocument;
import cb.ext.dbs.data.CompanyLimitFileEMail;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.CompanyLimitFileSchedule;
import cb.ext.dbs.data.CompanyProcessFileEmail;
import cb.ext.dbs.data.CompanyProcessFileParam;
import cb.ext.dbs.data.CompanyResultFileSchedule;
import cb.ext.dbs.data.DealerWaitingDebtFileSendMailParam;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.util.CustomerUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBReferenceData;
import cb.smg.general.utility.CBTable;
import cb.smg.general.utility.CBTime;

public class CompanyService implements CBBagKeys {

	/*
	 * private static String generateInternalCompanyCode() throws CBException {
	 * 
	 * String internalCompanyCode = "";
	 * 
	 * internalCompanyCode =
	 * Util.generateNo(CompanyConstants.DBS_COMPANY_INT_CODE);
	 * 
	 * return internalCompanyCode;
	 * 
	 * }
	 */

	// @serviceName: EXT_DBS_HANDLE_COMPANY_ACCOUNT
	public static CBBag handleCompanyAccounts(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		String operation = inBag.get(OPERATION).toString();

		String currency = inBag.get(CURRENCY).toString();

		String blokageAccountNo = inBag.get(ACCOUNTNR).toString();

		String accountNo = inBag.get(ACCOUNTNR2).toString();

		int selectedNumber = inBag.get(SELECTNUMBER).toSimpleInt();

		int tableSize = inBag.getSize(TABLE1);

		if ((operation.equals(DBSOperationType.add.value())) || (operation.equals(DBSOperationType.update.value()))) {

			if (currency.equals("")) {
				throwExceptionNoParam(DBSExceptions.ACCOUNT_CURRENCY_EMPTY);
			}
			else if ((!blokageAccountNo.equals("")) && (inBag.get(VALUE3).toSimpleInt() == 0)) {
				throwExceptionNoParam(DBSExceptions.COMPANYACCOUNT_BLOKAGE_DAY_EMPTY);
			}
			else if ((blokageAccountNo.equals("")) && (inBag.get(VALUE3).toSimpleInt() > 0)) {
				throwExceptionNoParam(DBSExceptions.BLOKAGEACCOUNT_EMPTY);
			}
			else if (accountNo.equals("")) {
				throwExceptionNoParam(DBSExceptions.CURRENTACCOUNT_EMPTY);
			}
			else {

				int blockageDayCount = inBag.get(VALUE3).toSimpleInt();

				if (blockageDayCount == 0) {
					blokageAccountNo = null;
				}

				checkCurrencyExists(inBag, operation, currency, tableSize, selectedNumber);

				inBag.copyTo(outBag);

				if (operation.equals(DBSOperationType.add.value())) {

					outBag.put(TABLE1, tableSize, OID, tableSize + 1);
					outBag.put(TABLE1, tableSize, CURRENCY, currency);
					outBag.put(TABLE1, tableSize, ACCOUNTNR, blokageAccountNo);
					outBag.put(TABLE1, tableSize, ACCOUNTNR2, accountNo);
					outBag.put(TABLE1, tableSize, VALUE3, blockageDayCount);

				}
				else {

					outBag.put(TABLE1, selectedNumber, CURRENCY, currency);
					outBag.put(TABLE1, selectedNumber, ACCOUNTNR, blokageAccountNo);
					outBag.put(TABLE1, selectedNumber, ACCOUNTNR2, accountNo);
					outBag.put(TABLE1, selectedNumber, VALUE3, blockageDayCount);

				}

			}

		}
		else {

			int counter = 0;

			for (int index = 0; index < tableSize; index++) {

				if (index != selectedNumber) {

					outBag.put(TABLE1, counter, OID, counter + 1);
					outBag.put(TABLE1, counter, CURRENCY, inBag.get(TABLE1, index, CURRENCY));
					outBag.put(TABLE1, counter, ACCOUNTNR, inBag.get(TABLE1, index, ACCOUNTNR));
					outBag.put(TABLE1, counter, ACCOUNTNR2, inBag.get(TABLE1, index, ACCOUNTNR2));
					outBag.put(TABLE1, counter, VALUE3, inBag.get(TABLE1, index, VALUE3));

					counter++;

				}

			}

		}

		return outBag;

	}

	/* @serviceName: EXT_DBS_ARRANGE_COMPANY_LIMITFILE_SCHEDULE */
	public static CBBag arrangeCompanyLimitFileSchedule(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		String time = "";

		int finalIndex = 0;

		if (inBag.getSize(TABLE2) > 0) {

			for (int index = 0; index < inBag.getSize(TABLE2); index++) {

				time = inBag.get(TABLE2, index, TIME).toString();

				if (time.equals("")) {

				}
				else if (!time.substring(4, 6).equals("00")) {
					throwExceptionNoParam(DBSExceptions.LIMITFILE_SECOND_00);
				}
				else {

					int hour = Integer.parseInt(time.substring(0, 2));
					int min = Integer.parseInt(time.substring(2, 4));

					if ((hour > 23) || (min > 60)) {

						throwExceptionNoParam(DBSExceptions.LIMITFILE_TIME_INVALID);

					}
					else {

						outBag.put(TABLE2, finalIndex, TIME, time);
						++finalIndex;
					}

				}

			}

			CBTable bagTable = outBag.getCBTable(TABLE2, true);
			bagTable.sortTable(TIME, true);

		}

		return outBag;

	}
	
	/* @serviceName: EXT_DBS_ARRANGE_COMPANY_RESULTFILE_SCHEDULE */
	public static CBBag arrangeCompanyResultFileSchedule(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		String time = "";


		if (inBag.getSize(TABLE6) > 0) {

			for (int index = 0; index < inBag.getSize(TABLE6); index++) {

				time = inBag.get(TABLE6, index, TIME).toString();

				if (time.equals("")) {

				}
				else if (!time.substring(4, 6).equals("00")) {
					throwExceptionNoParam(DBSExceptions.RESULTFILE_SECOND_00);
				}
				else {

					int hour = Integer.parseInt(time.substring(0, 2));
					int min = Integer.parseInt(time.substring(2, 4));

					if ((hour > 23) || (min > 60)) {

						throwExceptionNoParam(DBSExceptions.RESULTFILE_TIME_INVALID);

					}
					else {

						outBag.put(TABLE6, index, TIME, time);
						outBag.put(TABLE6, index, LIMITCODE, inBag.get(TABLE6, index, LIMITCODE));
					}

				}

			}

			CBTable bagTable = outBag.getCBTable(TABLE6, true);
			bagTable.sortTable(TIME, true);

		}

		return outBag;

	}

	/* @serviceName: EXT_DBS_CHECK_COMPANY_INPUT */
	public static CBBag checkCompanyInputs(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		checkCustomerNumber(inBag);

		checkCompanyCode(inBag);

		String operation = inBag.get(OPERATION).toString();

		if ((operation.equals(DBSOperationType.add.value())) || (operation.equals(DBSOperationType.add.value()))) {

			if (operation.equals(DBSOperationType.add.value())) {

				checkCustomerNumberUniqueness(inBag);
			}

			checkKMHMethodNameEmpty(inBag);

			checkMaturityPeriod(inBag);

			checkDayCount(inBag);

			if(inBag.get(COMPANYTYPE).toString().equals("1")) {
				checkMaturityGapType(inBag);

				checkTotalLimitAmount(inBag);
			}
		}

		checkDebtFileResultDefinitions(inBag);

		checkLimitFileDefinitions(inBag);

		checkCompanyAccounts(inBag);
		
		checkProcessFileDefinitions(inBag);

		outBag.put(CODE1, 1);

		return outBag;

	}

	private static void checkLimitFileDefinitions(CBBag inBag) throws CBException {

		boolean textEnabled = inBag.get(VALUE5).toBoolean();
		boolean emailEnabled = inBag.get(VALUE6).toBoolean();

		if (emailEnabled) {

			checkLimitFileSchedule(inBag);

			if (emailEnabled) {

				doMailValidation(inBag, CBBagKeys.TABLE4, 
										DBSExceptions.LIMITFILE_MAIL_EMPTY, 
						                DBSExceptions.LIMITFILE_MAIL_INVALID, 
						                DBSExceptions.LIMITFILE_MAIL_LENGTH_ERROR);

			}

		}

	}

	private static void checkDebtFileResultDefinitions(CBBag inBag) throws CBException {

		boolean emailEnabled = inBag.get(VALUE1).toBoolean();
		boolean sendWaitingDebtList = inBag.get(VALUE12).toBoolean();

		if (emailEnabled || sendWaitingDebtList) {

			if(emailEnabled) {
				checkDebtFileCompanyCode(inBag);
			}

			doMailValidation(inBag, CBBagKeys.TABLE1, 
									DBSExceptions.DEBTFILERESULT_MAIL_EMPTY, 
									DBSExceptions.DEBTFILERESULT_MAIL_INVALID, 
									DBSExceptions.DEBTFILERESULT_MAIL_LENGTH_ERROR);

		}
	}

	private static void checkCustomerNumberUniqueness(CBBag inBag) throws CBException {

		int bagIndex = CUSTOMERNUMBER;

		if (Company.isCustomerExists(inBag.get(bagIndex).toSimpleInt())) {

			throwException(DBSExceptions.CUSTOMERNUMBER_NOT_UNIQUE, inBag, bagIndex);

		}
	}

	private static void checkMaturityPeriod(CBBag inBag) throws CBException {

		int bagIndex = PERIOD;

		if (inBag.get(bagIndex).toString().equals("")) {
			throwException(DBSExceptions.COMPANYMATURITYPERIOD_EMPTY, inBag, bagIndex);
		}

	}

	private static void checkDayCount(CBBag inBag) throws CBException {

		int bagIndex = VALUE0;

		if (inBag.get(bagIndex).toString().equals("")) {
			throwException(DBSExceptions.COMPANYDAYCOUNT_EMPTY, inBag, bagIndex);
		}

	}

	private static void checkMaturityGapType(CBBag inBag) throws CBException {

		int bagIndex = TYPE1;

		if (inBag.get(bagIndex).toString().equals("")) {
			throwException(DBSExceptions.MATURITY_GAPTYPE_EMPTY, inBag, bagIndex);
		}

	}

	private static void checkCompanyCode(CBBag inBag) throws CBException {

		if (inBag.get(CODE1).toString().equals("")) {

			throwExceptionNoParam(DBSExceptions.COMPANYCODE_EMPTY);

		}
	}

	private static void checkDebtFileCompanyCode(CBBag inBag) throws CBException {

		if (inBag.get(VALUE2).toString().equals("")) {
			throwExceptionNoParam(DBSExceptions.DEBTFILERESULT_COMPANYCODE_EMPTY);
		}

	}

	private static void checkCompanyAccounts(CBBag inBag) throws CBException {

		checkAccounts(inBag, DBSExceptions.COMPANYACCOUNT_NOT_SET);

	}

	private static void checkLimitFileSchedule(CBBag inBag) throws CBException {
		if (inBag.getSize(TABLE2) == 0) {
			throwExceptionNoParam(DBSExceptions.LIMITFILE_NO_SCHEDULE);
		}
	}
	
	private static void checkResultFileSchedule(CBBag inBag) throws CBException {
		if (inBag.getSize(TABLE6) == 0) {
			throwExceptionNoParam(DBSExceptions.RESULTFILE_NO_SCHEDULE);
		}
	}

	private static void checkTotalLimitAmount(CBBag inBag) throws CBException {

		CBCurrency companyTotalRiskAmount = getCompanyTotalRiskAmount(inBag);

		if (inBag.get(LIMITAMOUNT).toCBCurrency().compare(companyTotalRiskAmount) < 1) {
			throwException(DBSExceptions.COMPANY_TOTAL_LIMIT_AMOUNT_NOT_ENOUGH, inBag, LIMITAMOUNT);
		}

	}

	private static void checkKMHMethodNameEmpty(CBBag inBag) throws CBException {

		int bagIndex = KMHMETOD_NAME;

		if (inBag.get(bagIndex).toString().equals("")) {
			throwException(DBSExceptions.KMHMETODNAME_EMPTY, inBag, bagIndex);
		}

	}

	private static CBCurrency getCompanyTotalRiskAmount(CBBag inBag) throws CBException {

		CBBag limitBag = CBBagFactory.createBag();

		limitBag.put(KMHMETODKODU, inBag.get(KMHMETOD_NAME));
		limitBag = CBCaller.call("CUF_DBSCOMPANY_TOTAL_RISKAMOUNT", limitBag);

		return limitBag.get(RISK1).toCBCurrency();

	}

	/* @serviceName: EXT_DBS_LIST_COMPANY */
	public static CBBag listCompany(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		Integer dbsType = null;
		String customerNumber = null;
		
		if(inBag.existsBagKey(COMPANYTYPE)) {
			dbsType = inBag.get(COMPANYTYPE).toSimpleInt();
		}

		if (inBag.existsBagKey(CUSTOMERNUMBER)) {
			customerNumber = inBag.get(CUSTOMERNUMBER).toString();
		}
		
		outBag = listCompany(customerNumber, dbsType);
		
		if (outBag.getSize(TABLE1) == 0) {
			outBag = putInfoMessage(DBSExceptions.COMPANY_LIST_NOT_FOUND);
		}

		return outBag;

	}
	
	private static CBBag listCompany(String customerNumber, Integer dbsType) throws CBException{
		CBBag outBag = CBBagFactory.createBag();
    	CBQueryExecuter qe  = null ;
    	
        try {
        	qe = new CBQueryExecuter("EXT_DBS_GET_COMPANY_LIST");
        	
        	if(customerNumber != null && customerNumber.trim().length() != 0) {
        		qe.setParameter("CUSTOMERNUMBER", Integer.valueOf(customerNumber.trim()).intValue());
        	}
        	if(dbsType != null && dbsType != 0) {
        		qe.setParameter("COMPANYTYPE", dbsType.intValue());
        	}
        	
          	qe.executeQuery();
          	int index = 0;
      	    while (qe.next()) {
      			outBag.put(TABLE1, index, OID, qe.getLong("OID"));
      			outBag.put(TABLE1, index, CUSTOMERNUMBER, qe.getInt("CUSTOMERNUMBER"));
      			outBag.put(TABLE1, index, CODE1, qe.getString("COMPANYINTERNALCODE"));
      			outBag.put(TABLE1, index, TITLE,  qe.getString("TITLE"));
      			outBag.put(TABLE1, index, ACTIVE,  qe.getInt("ACTIVE") == 1 ? true : false);
      			outBag.put(TABLE1, index, COMPANYTYPE,  qe.getInt("DBSTYPE"));
      			
      			index++;
      	    }
		} 
        finally {
			if(qe!=null) {
				qe.close();
			}
		}
        
        return  outBag;
	}

	/* @serviceName: EXT_DBS_LIST_COMPANY_FOR_COMBO */
	public static CBBag listCompaniesForComboBox(CBBag inBag) throws CBException {
		inBag.put(TYPE, "COMBOBOX");
		return listCompanies(inBag);

	}
	
	private static CBBag listCompany(String customerNumber) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company();
		
		company = company.getByCustomerNumber(Integer.parseInt(customerNumber));
		
		if (company != null) {
			outBag = setListCompanyOutputFields(company, 0);
		}

		return outBag;

	}

	private static CBBag listCompanies(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company();

		Vector<Company> companies = null;
		
		if(inBag.existsBagKey(FUTUREPAYMENT) && inBag.get(FUTUREPAYMENT).toString().equals("Y")) {
			companies = company.manuelPaymentBeforeDueDateList();
		}
		else if(inBag.existsBagKey(COMPANYTYPE)) {
			companies = company.listByDbsType(inBag.get(COMPANYTYPE).toSimpleInt());
		}
		else {
			companies = company.list();
		}

		for (int index = 0; index < companies.size(); index++) {

			company = companies.get(index);

			if (!inBag.existsBagKey(TYPE)) {
				setListCompanyOutputFields(company, index).copyTo(outBag);
			}
			else {
				setListCompanyOutputFieldsForComboBox(company, index).copyTo(outBag);
			}

		}

		return outBag;
	}
	
	private static CBBag setListCompanyOutputFieldsForComboBox(Company company, int index) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		int customerNumber = company.getCustomerNumber();

		outBag.put(ASSET, index, -1, company.getCompanyInternalCode() + " / " + CustomerUtility.getCustomerInfo(customerNumber).getTitle());

		outBag.put(ASSET_NAME, index, -1, company.getOID());

		return outBag;
	}

	private static CBBag setListCompanyOutputFields(Company company, int index) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		int customerNumber = company.getCustomerNumber();

		outBag.put(TABLE1, index, OID, company.getOID());

		outBag.put(TABLE1, index, CUSTOMERNUMBER, customerNumber);
		outBag.put(TABLE1, index, CODE1, company.getCompanyInternalCode());
		outBag.put(TABLE1, index, TITLE, CustomerUtility.getCustomerInfo(customerNumber).getTitle());
		outBag.put(TABLE1, index, ACTIVE, company.isActive());
		outBag.put(TABLE1, index, COMPANYTYPE, company.getDbsType());
		
		return outBag;
	}

	// @serviceName: EXT_DBS_GET_COMPANY_DETAIL
	public static CBBag getCompanyDetail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company();

		CompanyDebtFileResultParam companyDebtFileResultParam = new CompanyDebtFileResultParam();
		
		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = new DealerWaitingDebtFileSendMailParam();

		CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();
		Vector<CompanyDebtFileResultEMail> companyDebtFileResultEMailVector = new Vector<CompanyDebtFileResultEMail>();

		CompanyLimitFileParam companyLimitFileParam = new CompanyLimitFileParam();

		CompanyLimitFileEMail companyLimitFileEMail = new CompanyLimitFileEMail();
		Vector<CompanyLimitFileEMail> companyLimitFileEMailVector = new Vector<CompanyLimitFileEMail>();

		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();
		Vector<CompanyLimitFileSchedule> companyLimitFileScheduleVector = new Vector<CompanyLimitFileSchedule>();
		
		CompanyResultFileSchedule companyResultFileSchedule = new CompanyResultFileSchedule();
		Vector<CompanyResultFileSchedule> companyResultFileScheduleVector = new Vector<CompanyResultFileSchedule>();
 
		Vector<CompanyAccount> companyAccountVector = new Vector<CompanyAccount>();
		
		CompanyDebtFileTypeParam companyDebtFileTypeParam = new CompanyDebtFileTypeParam();
		
		CompanyProcessFileParam companyProcessFileParam = new CompanyProcessFileParam();
		
		CompanyProcessFileEmail  companyProcessFileEmail = new CompanyProcessFileEmail();
		Vector<CompanyProcessFileEmail> companyProcessFileEmailVector = new Vector<CompanyProcessFileEmail>();
		
		int customerNumber = inBag.get(CUSTOMERNUMBER).toSimpleInt();

		company = company.getByCustomerNumber(customerNumber);

		long companyOID = company.getOID();

		companyDebtFileResultParam = companyDebtFileResultParam.getCompanyDebtFileResultParam(companyOID);
		
		dealerWaitingDebtFileSendMailParam = dealerWaitingDebtFileSendMailParam.getDealerWaitingDebtFileSendMailParam(companyOID);
		
		companyLimitFileParam = companyLimitFileParam.getCompanyLimitFileParam(companyOID);

		companyLimitFileScheduleVector = companyLimitFileSchedule.getCompanyLimitFileSchedule(companyOID);
		
		companyAccountVector = company.getCompanyAccounts();
		
		companyDebtFileTypeParam =  companyDebtFileTypeParam.getCompanyDebtFileTypeParam(companyOID);

		outBag = fillOutBagWithCompany(company);
		
		companyProcessFileParam = companyProcessFileParam.getCompanyProcessFileParam(companyOID);
		
		companyResultFileScheduleVector = companyResultFileSchedule.getCompanyResultFileSchedule(companyOID);
		
		fillOutBagWithCompanyDebtFileResultParam(companyDebtFileResultParam).copyTo(outBag);
		
		fillOutBagWithDealerWaitingDebtFileSendMailParam(dealerWaitingDebtFileSendMailParam).copyTo(outBag);

		if (companyDebtFileResultParam.isMailingEnabled()) {
			companyDebtFileResultEMailVector = companyDebtFileResultEMail.getCompanyDebtFileResultEMail(companyOID);
			fillOutBagWithCompanyDebtFileResultEMail(companyDebtFileResultEMailVector).copyTo(outBag);
		}

		fillOutBagWithCompanyLimitFileParam(companyLimitFileParam).copyTo(outBag);

		if (companyLimitFileParam.isMailingEnabled()) {
			companyLimitFileEMailVector = companyLimitFileEMail.getCompanyLimitFileEMail(companyOID);
			fillOutBagWithCompanyLimitFileEMail(companyLimitFileEMailVector).copyTo(outBag);
		}

		fillOutBagWithCompanyLimitFileSchedule(companyLimitFileScheduleVector).copyTo(outBag);

		fillOutBagWithCompanyAccount(companyAccountVector).copyTo(outBag);
		fillOutBagWithCompanyDebtFileTypeParam(companyDebtFileTypeParam).copyTo(outBag);
		
		fillOutBagWithCompanyProcessFileParam(companyProcessFileParam).copyTo(outBag);
		
		if(companyProcessFileParam.isMailingEnabled()){
			companyProcessFileEmailVector = companyProcessFileEmail.getCompanyProcessFileEMail(companyOID);
			fillOutBagWithCompanyProcessFileEMail(companyProcessFileEmailVector).copyTo(outBag);
		}
		
		fillOutBagWithCompanyResultFileSchedule(companyResultFileScheduleVector).copyTo(outBag);

		if(Constants.PERSONEL_DBS_TYPE ==  company.getDbsType()) {
			CompanyDocument companyDocumet = new CompanyDocument();
			Vector<CompanyDocument> companyDocumentVector = new Vector<CompanyDocument>();
			
			companyDocumentVector = companyDocumet.getCompanyDocumentsByCompanyOid(companyOID);
			fillOutBagWithCompanyDocument(companyDocumentVector).copyTo(outBag);
		}
		
		return outBag;

	}
	
	private static CBBag fillOutBagWithCompanyDocument(Vector<CompanyDocument> companyDocumentVector) {
		CBBag outBag = CBBagFactory.createBag();
		
		CBReferenceData refData = new CBReferenceData("PRM_DBS_DOCUMENT_TYPE_LIST");
		Vector refDataList = refData.getPrmList();
		
		for(int i = 0; i < refDataList.size(); i++) {
			Hashtable hashTable = (Hashtable)refDataList.get(i);
			int code = Integer.valueOf(hashTable.get("CODE").toString());
			boolean select = false;
			for (int index = 0; index < companyDocumentVector.size(); index++) {
				if(code == companyDocumentVector.get(index).getDocumentCode()) {
					select = true;
					break;
				}
			}
			outBag.put(TABLE7, i, SELECT, select);
			outBag.put(TABLE7, i, CODE, code);
		}
		
		return outBag;
	}
	
	private static CBBag fillOutBagWithCompanyLimitFileEMail(Vector<CompanyLimitFileEMail> companyLimitFileEMailVector) {

		CBBag outBag = CBBagFactory.createBag();

		for (int index = 0; index < companyLimitFileEMailVector.size(); index++) {
			outBag.put(TABLE4, index, VALUE1, companyLimitFileEMailVector.get(index).getEmail());
		}

		return outBag;
	}

	private static CBBag fillOutBagWithCompanyLimitFileParam(CompanyLimitFileParam companyLimitFileParam) {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(VALUE5   , companyLimitFileParam.isTextFileEnabled());
		outBag.put(VALUE6   , companyLimitFileParam.isMailingEnabled());
		outBag.put(VALUE8   , companyLimitFileParam.isDealerDefSentMail());
		outBag.put(VALUE11  , Integer.valueOf(companyLimitFileParam.getAccountOption().value()));
		outBag.put(FILEPATH , companyLimitFileParam.getLimitFileName());

		return outBag;
	}

	private static CBBag fillOutBagWithCompanyDebtFileResultParam(CompanyDebtFileResultParam companyDebtFileResultParam) {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(VALUE4   , companyDebtFileResultParam.isTextFileEnabled());
		outBag.put(VALUE1   , companyDebtFileResultParam.isMailingEnabled());
		outBag.put(VALUE2   , companyDebtFileResultParam.getFileCompanyCode());
		outBag.put(TYPE2     , companyDebtFileResultParam.getTextFileType());
		outBag.put(FILENAME , companyDebtFileResultParam.getTextFileName());
		outBag.put(FILENAME , companyDebtFileResultParam.getTextFileName());
		outBag.put(VALUE12	, companyDebtFileResultParam.isSendWaitingDebtList());
		
		return outBag;
	}
	
	private static CBBag fillOutBagWithDealerWaitingDebtFileSendMailParam(DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam) {
		CBBag outBag = CBBagFactory.createBag();

		if(dealerWaitingDebtFileSendMailParam != null) {
			outBag.put(DAY, dealerWaitingDebtFileSendMailParam.getDayCount());
			outBag.put(DATETIME, dealerWaitingDebtFileSendMailParam.getBatchTime());
			outBag.put(EMAILDELIVERY, dealerWaitingDebtFileSendMailParam.isSendMailToDealer());
			outBag.put(SMSDELIVERY, dealerWaitingDebtFileSendMailParam.isSendSmsToDealer());
		}
		
		return outBag;
	}

	private static CBBag fillOutBagWithCompanyAccount(Vector<CompanyAccount> companyAccountVector) {

		CBBag outBag = CBBagFactory.createBag();

		CompanyAccount companyAccount = null;

		for (int index = 0; index < companyAccountVector.size(); index++) {

			companyAccount = companyAccountVector.get(index);

			outBag.put(TABLE3, index, OID, index + 1);
			outBag.put(TABLE3, index, CURRENCY, companyAccount.getCurrency());
			outBag.put(TABLE3, index, ACCOUNTNR, companyAccount.getBlockageAccountNo());
			outBag.put(TABLE3, index, ACCOUNTNR2, companyAccount.getAccountNo());
			outBag.put(TABLE3, index, VALUE3, companyAccount.getBlockageDayCount());
		}

		return outBag;
	}

	private static CBBag fillOutBagWithCompanyLimitFileSchedule(Vector<CompanyLimitFileSchedule> companyLimitFileScheduleVector) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		for (int index = 0; index < companyLimitFileScheduleVector.size(); index++) {
			outBag.put(TABLE2, index, TIME, new CBTime(companyLimitFileScheduleVector.get(index).getTime()));
		}

		return outBag;

	}

	private static CBBag fillOutBagWithCompanyDebtFileResultEMail(Vector<CompanyDebtFileResultEMail> companyDebtFileResultEMailVector) {

		CBBag outBag = CBBagFactory.createBag();

		for (int index = 0; index < companyDebtFileResultEMailVector.size(); index++) {
			outBag.put(TABLE1, index, VALUE1, companyDebtFileResultEMailVector.get(index).getEmail());
		}

		return outBag;
	}

	public static CBBag fillOutBagWithCompany(Company company) {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(OID            	 , company.getOID());
		outBag.put(ACTIVE            , company.isActive());
		outBag.put(CODE1             , company.getCompanyInternalCode());
		outBag.put(CUSTOMERNUMBER    , company.getCustomerNumber());
		outBag.put(METHOD            , company.getWorkingMethod());
		outBag.put(KMHMETOD_NAME     , company.getKMHMethodName());
		outBag.put(LIMITAMOUNT       , company.getCompanyTotalLimitAmount());
		outBag.put(PERIOD            , company.getCompanyMaturityPeriod());
		outBag.put(VALUE0            , company.getCompanyProcessDayCount());
		outBag.put(TYPE1             , company.getCompanyLimitGapType());
		outBag.put(PARTIALCOLLECTION , company.isPartialPayable());
		outBag.put(AMOUNTUPPERLIMIT	 , company.isLoadedOverLimitDebt());
		outBag.put(FUTUREPAYMENT	 , company.isManuelPaymentBeforeDueDate());
		outBag.put(PROCESSTYPE       , company.getCurrencyProcessMethod());
		outBag.put(EXCHANGERATE      , company.getExchangeRate());
		outBag.put(EXCHANGETYPE      , company.getExchangeRateType());
		outBag.put(FORMATCODE        , company.getDealerCodeFormatType());
		outBag.put(CURRENCYTYPE      , company.getLocalCurrencyCodeType());
		outBag.put(SENDINGTYPECODE   , company.getDebtFileSendType());
		outBag.put(COMPANYNAME		 , company.getCompanyName());
		outBag.put(DEALER			 , company.isDealerWithSameCustomerNumber());
		outBag.put(GUARANTOR1   	 , company.isGuarantorship());
		outBag.put(GUARANTOR2		 , company.getGuarantorshipWaitingTime());
		outBag.put(GUARANTOR3		 , company.getKmhLimitRate());
		outBag.put(COMPANYTYPE		 , company.getDbsType());
		
		return outBag;
	}
	
	private static CBBag fillOutBagWithCompanyDebtFileTypeParam(CompanyDebtFileTypeParam companyDebtFileTypeParam) {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(FILETYPE , companyDebtFileTypeParam.getCompanyDebtFileTypeCode());
	
		return outBag;
	}


	/* @serviceName: EXT_DBS_ADD_COMPANY */
	public static CBBag addCompany(CBBag inBag) throws CBException {

		Company company = setCompanyFields(inBag);

		long companyOID = company.add();

		addCompanyDebtFileResultParam(inBag, companyOID);
		
		addDealerWaitingDebtFileSendMailParam(inBag, companyOID);

		addCompanyLimitFileDefinitions(inBag, companyOID);

		addCompanyAccounts(inBag, companyOID);
		
		addCompanyDebtFileType(inBag, companyOID);
		
		addCompanyProcessFileDefinitions (inBag , companyOID);
		
		if(Constants.PERSONEL_DBS_TYPE ==  company.getDbsType()) {
			addCompanyDocuments(inBag, companyOID);
		}

		return putInfoMessage(DBSExceptions.COMPANY_DEFINITION_DONE);

	}

	private static void addCompanyAccounts(CBBag inBag, long companyOID) throws CBException {

		CompanyAccount companyAccount = new CompanyAccount();

		for (int index = 0; index < inBag.getSize(TABLE3); index++) {

			companyAccount = setCompanyAccountFields(inBag, companyOID, index);

			companyAccount.add();
		}

	}

	private static void addCompanyLimitFileDefinitions(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileParam companyLimitFileParam = setCompanyLimitFileParam(inBag, companyOID);

		companyLimitFileParam.add();

		if (companyLimitFileParam.isMailingEnabled()) {
			addCompanyLimitFileEMail(inBag, companyOID);
		}

		addCompanyLimitFileSchedule(inBag, companyOID);

	}

	private static void addCompanyLimitFileEMail(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileEMail companyLimitFileEMail = new CompanyLimitFileEMail();

		for (int index = 0; index < inBag.getSize(TABLE4); index++) {

			companyLimitFileEMail = setCompanyLimitFileEMail(inBag, companyOID, index);

			companyLimitFileEMail.add();
		}

	}

	private static CompanyLimitFileEMail setCompanyLimitFileEMail(CBBag inBag, long companyOID, int index) throws CBException {

		CompanyLimitFileEMail companyLimitFileEMail = new CompanyLimitFileEMail();

		companyLimitFileEMail.setCompanyOID(companyOID);
		companyLimitFileEMail.setEmail(inBag.get(TABLE4, index, VALUE1).toString());

		return companyLimitFileEMail;

	}

	private static CompanyLimitFileParam setCompanyLimitFileParam(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileParam companyLimitFileParam = new CompanyLimitFileParam();

		companyLimitFileParam.setCompanyOID(companyOID);
		companyLimitFileParam.setTextFileEnabled(inBag.get(VALUE5).toBoolean());
		companyLimitFileParam.setMailingEnabled(inBag.get(VALUE6).toBoolean());
		companyLimitFileParam.setDealerDefSentMail(inBag.get(VALUE8).toBoolean());
		companyLimitFileParam.setAccountOption(LimitAccountOption.fromString(inBag.get(VALUE11).toString()));
		companyLimitFileParam.setLimitFileName(inBag.get(FILEPATH).toString());

		return companyLimitFileParam;
	}

	private static void addCompanyLimitFileSchedule(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();

		for (int index = 0; index < inBag.getSize(TABLE2); index++) {

			companyLimitFileSchedule = setCompanyLimitFileSchedule(inBag, companyOID, index);

			companyLimitFileSchedule.add();
		}

	}

	private static void addCompanyDebtFileResultParam(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileResultParam companyDebtFileResultParam = setCompanyDebtFileResultParam(inBag, companyOID);

		companyDebtFileResultParam.add();

		if (companyDebtFileResultParam.isMailingEnabled()) {
			addCompanyDebtFileResultEMail(inBag, companyOID);
		}

	}
	
	private static void addDealerWaitingDebtFileSendMailParam(CBBag inBag, long companyOID) throws CBException {
		
		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = setDealerWaitingDebtFileSendMailParam(inBag, companyOID);
		
		dealerWaitingDebtFileSendMailParam.add();
		
	}
	
	private static void addCompanyDebtFileType(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileTypeParam companyDebtFileTypeParam = setCompanyDebtFileTypeParam(inBag, companyOID);
		companyDebtFileTypeParam.add();

	}

	private static DealerWaitingDebtFileSendMailParam setDealerWaitingDebtFileSendMailParam(CBBag inBag, long companyOID) throws CBException {

		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = new DealerWaitingDebtFileSendMailParam();

		dealerWaitingDebtFileSendMailParam.setCompanyOID(companyOID);
		dealerWaitingDebtFileSendMailParam.setBatchTime(inBag.get(DATETIME).toCBTime());
		dealerWaitingDebtFileSendMailParam.setDayCount(inBag.get(DAY).toSimpleInt());
		dealerWaitingDebtFileSendMailParam.setSendMailToDealer(inBag.get(EMAILDELIVERY).toBoolean());
		dealerWaitingDebtFileSendMailParam.setSendSmsToDealer(inBag.get(SMSDELIVERY).toBoolean());
		
		return dealerWaitingDebtFileSendMailParam;
	}
	
	private static CompanyDebtFileResultParam setCompanyDebtFileResultParam(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileResultParam companyDebtFileResultParam = new CompanyDebtFileResultParam();

		companyDebtFileResultParam.setCompanyOID(companyOID);
		companyDebtFileResultParam.setTextFileEnabled(inBag.get(VALUE4).toBoolean());
		companyDebtFileResultParam.setMailingEnabled(inBag.get(VALUE1).toBoolean());
		companyDebtFileResultParam.setFileCompanyCode(inBag.get(VALUE2).toString());
		companyDebtFileResultParam.setTextFileType(inBag.get(TYPE2).toString());
		companyDebtFileResultParam.setTextFileName(inBag.get(FILENAME).toString());
		companyDebtFileResultParam.setSendWaitingDebtList(inBag.get(VALUE12).toBoolean());
		
		return companyDebtFileResultParam;
	}

	private static CompanyDebtFileTypeParam setCompanyDebtFileTypeParam(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileTypeParam companyDebtFileTypeParam = new CompanyDebtFileTypeParam();

		companyDebtFileTypeParam.setCompanyOID(companyOID);
		companyDebtFileTypeParam.setCompanyDebtFileTypeCode(inBag.get(FILETYPE).toString());

		return companyDebtFileTypeParam;
	}
	
	private static void addCompanyDebtFileResultEMail(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();

		for (int index = 0; index < inBag.getSize(TABLE1); index++) {

			companyDebtFileResultEMail = setCompanyDebtFileResultEMail(inBag, companyOID, index);

			companyDebtFileResultEMail.add();
		}

	}

	private static CompanyDebtFileResultEMail setCompanyDebtFileResultEMail(CBBag inBag, long companyOID, int index) throws CBException {

		CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();

		companyDebtFileResultEMail.setCompanyOID(companyOID);
		companyDebtFileResultEMail.setEmail(inBag.get(TABLE1, index, VALUE1).toString());

		return companyDebtFileResultEMail;

	}

	private static CompanyLimitFileSchedule setCompanyLimitFileSchedule(CBBag inBag, long companyOID, int index) throws CBException {

		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();

		companyLimitFileSchedule.setCompanyOID(companyOID);
		companyLimitFileSchedule.setTime(inBag.get(TABLE2, index, TIME).toString());

		return companyLimitFileSchedule;

	}

	private static CompanyAccount setCompanyAccountFields(CBBag inBag, long companyOID, int index) throws CBException {

		CompanyAccount companyAccount = new CompanyAccount();

		companyAccount.setCompanyOID(companyOID);
		companyAccount.setCurrency(inBag.get(TABLE3, index, CURRENCY).toString());
		companyAccount.setBlockageAccountNo(inBag.get(TABLE3, index, ACCOUNTNR).toString());
		companyAccount.setAccountNo(inBag.get(TABLE3, index, ACCOUNTNR2).toString());
		companyAccount.setBlockageDayCount(inBag.get(TABLE3, index, VALUE3).toSimpleInt());
		companyAccount.setActive(true);

		return companyAccount;

	}

	private static Company setCompanyFields(CBBag inBag) throws CBException {

		Company company = new Company();

		company.setActive(inBag.get(ACTIVE).toBoolean());
		company.setCompanyInternalCode(inBag.get(CODE1).toString());
		company.setCustomerNumber(inBag.get(CUSTOMERNUMBER).toSimpleInt());
		company.setWorkingMethod(inBag.get(METHOD).toSimpleInt());
		company.setKMHMethodName(inBag.get(KMHMETOD_NAME).toString());
		company.setCompanyTotalLimitAmount(inBag.get(LIMITAMOUNT).toCBCurrency());
		company.setCompanyMaturityPeriod(inBag.get(PERIOD).toSimpleInt());
		company.setCompanyProcessDayCount(inBag.get(VALUE0).toSimpleInt());
		company.setCompanyLimitGapType(inBag.get(TYPE1).toSimpleInt());
		company.setPartialPayable(inBag.get(PARTIALCOLLECTION).toBoolean());
		company.setCurrencyProcessMethod(inBag.get(PROCESSTYPE).toString());
		company.setExchangeRate(inBag.get(EXCHANGERATE).toString());
		company.setExchangeRateType(inBag.get(EXCHANGETYPE).toString());
		company.setDealerCodeFormatType(inBag.get(FORMATCODE).toString());
		company.setLocalCurrencyCodeType(inBag.get(CURRENCYTYPE).toString());
		company.setDebtFileSendType(inBag.get(SENDINGTYPECODE).toString());
		company.setLoadedOverLimitDebt(inBag.get(AMOUNTUPPERLIMIT).toBoolean());
		company.setManuelPaymentBeforeDueDate(inBag.get(FUTUREPAYMENT).toBoolean());
		company.setCompanyName(inBag.get(COMPANYNAME).toString());
		company.setDealerWithSameCustomerNumber(inBag.get(DEALER).toBoolean());
		company.setGuarantorship(inBag.get(GUARANTOR1).toBoolean());
		company.setGuarantorshipWaitingTime(inBag.get(GUARANTOR2).toCBCurrency());
		company.setKmhLimitRate(inBag.get(GUARANTOR3).toCBCurrency());		
		company.setDbsType(inBag.get(COMPANYTYPE).toSimpleInt());
		
		return company;
	}

	/* @serviceName: EXT_DBS_UPDATE_COMPANY */
	public static CBBag updateCompany(CBBag inBag) throws CBException {

		Company company = new Company();

		int customerNumber = inBag.get(CUSTOMERNUMBER).toSimpleInt();

		company = setCompanyFields(inBag);

		long companyOID = company.update(customerNumber);

		updateCompanyDebtFileResultParam(inBag, companyOID);
		
		updateDealerWaitingDebtFileSendMailParam(inBag, companyOID);

		updateCompanyLimitFileParam(inBag, companyOID);

		updateCompanyLimitFileSchedule(inBag, companyOID);

		updateCompanyAccounts(inBag, companyOID);
		
		updateCompanyDebtFileTypeParam(inBag, companyOID);
		
		updateCompanyProcessFileParam(inBag, companyOID);
		
		if(Constants.PERSONEL_DBS_TYPE == company.getDbsType()) {
			updateCompanyDocuments(inBag, companyOID);
		}

		return putInfoMessage(DBSExceptions.COMPANY_UPDATE_DONE);

	}
	
	private static void updateCompanyDocuments(CBBag inBag, long companyOID) throws CBException {
		CompanyDocument companyDocument = new CompanyDocument();
		
		companyDocument.deleteByCompanyOid(companyOID);
		addCompanyDocuments(inBag, companyOID);
	}

	private static void updateCompanyAccounts(CBBag inBag, long companyOID) throws CBException {

		CompanyAccount companyAccount = new CompanyAccount();

		companyAccount.deleteAllByCompanyOID(companyOID);
		addCompanyAccounts(inBag, companyOID);
	}

	private static void updateCompanyLimitFileSchedule(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();

		companyLimitFileSchedule.deleteAllByCompanyOID(companyOID);

		addCompanyLimitFileSchedule(inBag, companyOID);

	}

	private static void updateCompanyLimitFileParam(CBBag inBag, long companyOID) throws CBException {

		CompanyLimitFileParam companyLimitFileParam = setCompanyLimitFileParam(inBag, companyOID);

		companyLimitFileParam.updateByCompanyOID(companyOID);

		if (companyLimitFileParam.isMailingEnabled()) {
			addCompanyLimitFileEMail(inBag, companyOID);
		}

	}

	private static void updateCompanyDebtFileResultParam(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileResultParam companyDebtFileResultParam = setCompanyDebtFileResultParam(inBag, companyOID);

		companyDebtFileResultParam.updateByCompanyOID(companyOID);

		if (companyDebtFileResultParam.isMailingEnabled()) {
			addCompanyDebtFileResultEMail(inBag, companyOID);
		}
	}
	
	private static void updateDealerWaitingDebtFileSendMailParam(CBBag inBag, long companyOID) throws CBException {

		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = setDealerWaitingDebtFileSendMailParam(inBag, companyOID);

		dealerWaitingDebtFileSendMailParam.updateByCompanyOID(companyOID);
	}
	
	private static void updateCompanyDebtFileTypeParam(CBBag inBag, long companyOID) throws CBException {

		CompanyDebtFileTypeParam companyDebtFileTypeParam = setCompanyDebtFileTypeParam(inBag, companyOID);

		companyDebtFileTypeParam.updateByCompanyOID(companyOID);

	}

	/* @serviceName: EXT_DBS_DELETE_COMPANY */
	public static CBBag deleteCompany(CBBag inBag) throws CBException {

		Company company = new Company();

		CompanyDebtFileResultParam companyDebtFileResultParam = new CompanyDebtFileResultParam();
		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = new DealerWaitingDebtFileSendMailParam();

		CompanyLimitFileParam companyLimitFileParam = new CompanyLimitFileParam();
		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();
		CompanyResultFileSchedule companyResultFileSchedule = new CompanyResultFileSchedule();
		
		
		CompanyDebtFileTypeParam companyDebtFileTypeParam = new CompanyDebtFileTypeParam();
		
		CompanyProcessFileParam companyProcessFileParam = new CompanyProcessFileParam();
 
		CompanyAccount companyAccount = new CompanyAccount();

		int customerNumber = inBag.get(CUSTOMERNUMBER).toSimpleInt();

		company = company.getByCustomerNumber(customerNumber);

		checkCompanyDeletable(company);

		company.delete();

		long companyOID = company.getOID();

		companyDebtFileResultParam.deleteByCompanyOID(companyOID);
		
		dealerWaitingDebtFileSendMailParam.deleteByCompanyOID(companyOID);

		companyLimitFileParam.deleteByCompanyOID(companyOID);

		companyLimitFileSchedule.deleteAllByCompanyOID(companyOID);
		
		companyResultFileSchedule.deleteAllByCompanyOID(companyOID);

		companyAccount.deleteAllByCompanyOID(companyOID);
		
		companyDebtFileTypeParam.deleteByCompanyOID(companyOID);
		
		companyProcessFileParam.deleteByCompanyOID(companyOID);
		
		
		if(Constants.PERSONEL_DBS_TYPE ==  company.getDbsType()) {
			CompanyDocument compayDocument = new CompanyDocument();
			compayDocument.deleteByCompanyOid(companyOID);
		}
		
		
		return putInfoMessage(DBSExceptions.COMPANY_DELETE_DONE);

	}

	private static void checkCompanyDeletable(Company company) throws CBException {

		if (company.getDealers(false).size() > 0) {
			throwExceptionNoParam(DBSExceptions.COMPANY_CANT_DELETE_WITHOUT_DEALER);
		}

	}

	// serviceName : EXT_DBS_GET_COMPANY_TOTAL_LIMIT
	public static CBBag getCompanyTotalLimit(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company().getByKMHMethodName(inBag.get(KMHMETOD_NAME).toString());
		
		CBCurrency companyTotalLimit = CBCurrency.ZERO;
		
		if (company != null) {

			companyTotalLimit = company.getCompanyTotalLimitAmount();
		}	

		outBag.put(LIMITAMOUNT, companyTotalLimit);

		return outBag;
	}
	
	
	private static void addCompanyProcessFileDefinitions(CBBag inBag, long companyOID) throws CBException {

		CompanyProcessFileParam companyProcessFileParam = setCompanyProcessFileParam(inBag, companyOID);

		companyProcessFileParam.add();

		if (companyProcessFileParam.isMailingEnabled()) {
			addCompanyProcessFileEMail(inBag, companyOID);
		}
		
		addCompanyResultFileSchedule(inBag, companyOID);

	}
	
	private static void addCompanyDocuments(CBBag inBag, long companyOid) throws CBException {
		CompanyDocument companyDocument = new CompanyDocument();
		
		for (int index = 0; index < inBag.getSize(TABLE7); index++) {
			if(inBag.get(TABLE7, index, SELECT).toBoolean()) {
				companyDocument.setCompanyOid(companyOid);
				companyDocument.setDocumentCode(inBag.get(TABLE7, index, CODE).toSimpleInt());
				
				companyDocument.addCompanyDocument();
			}
		}
	}
	
	
	private static CompanyProcessFileParam setCompanyProcessFileParam(CBBag inBag, long companyOID) throws CBException {

		CompanyProcessFileParam companyProcessFileParam = new CompanyProcessFileParam();

		companyProcessFileParam.setCompanyOID(companyOID);
		companyProcessFileParam.setFileEnabled(inBag.get(VALUE9).toBoolean());
		companyProcessFileParam.setMailingEnabled(inBag.get(VALUE10).toBoolean());
		companyProcessFileParam.setProcessFileName(inBag.get(FILE_NAME).toString());


		return companyProcessFileParam;
	}
	
	private static void addCompanyProcessFileEMail(CBBag inBag, long companyOID) throws CBException {

		CompanyProcessFileEmail companyProcessFileEmail = new CompanyProcessFileEmail();

		for (int index = 0; index < inBag.getSize(TABLE5); index++) {

			companyProcessFileEmail = setCompanyProcessFileEMail(inBag, companyOID, index);

			companyProcessFileEmail.add();
		}

	}
	
	private static void addCompanyResultFileSchedule(CBBag inBag, long companyOid) throws CBException {

		CompanyResultFileSchedule companyResultFileSchedule = new CompanyResultFileSchedule();

		for (int index = 0; index < inBag.getSize(TABLE6); index++) {

			companyResultFileSchedule = setCompanyResultFileSchedule(inBag, companyOid, index);

			companyResultFileSchedule.add();
		}

	}
	
	private static CompanyProcessFileEmail setCompanyProcessFileEMail(CBBag inBag, long companyOID, int index) throws CBException {

		CompanyProcessFileEmail companyProcessFileEmail = new CompanyProcessFileEmail();

		companyProcessFileEmail.setCompanyOID(companyOID);
		companyProcessFileEmail.setEmail(inBag.get(TABLE5, index, VALUE1).toString());

		return companyProcessFileEmail;

	}
	
	private static CompanyResultFileSchedule setCompanyResultFileSchedule(CBBag inBag, long companyOid, int index) throws CBException {

		CompanyResultFileSchedule companyResultFileSchedule = new CompanyResultFileSchedule();

		companyResultFileSchedule.setCompanyOid(companyOid);
		companyResultFileSchedule.setTime(inBag.get(TABLE6, index, TIME).toString());
		companyResultFileSchedule.setUseLimit(inBag.get(TABLE6, index, LIMITCODE).toBoolean());
		
		return companyResultFileSchedule;

	}
	
	private static void updateCompanyProcessFileParam(CBBag inBag, long companyOID) throws CBException {

		CompanyProcessFileParam companyProcessFileParam = setCompanyProcessFileParam(inBag, companyOID);

		companyProcessFileParam.updateByCompanyOID(companyOID);

		if (companyProcessFileParam.isMailingEnabled()) {
			addCompanyProcessFileEMail(inBag, companyOID);
		}
		
		CompanyResultFileSchedule companyResultFileSchedule = new CompanyResultFileSchedule();
		companyResultFileSchedule.deleteAllByCompanyOID(companyOID);

		addCompanyResultFileSchedule(inBag, companyOID);
	}


	private static void checkProcessFileDefinitions(CBBag inBag) throws CBException {

		boolean textEnabled = inBag.get(VALUE9).toBoolean();
		boolean emailEnabled = inBag.get(VALUE10).toBoolean();

		if (emailEnabled) {

			doMailValidation(inBag, CBBagKeys.TABLE5, 
									DBSExceptions.PROCESSFILE_MAIL_EMPTY, 
					                DBSExceptions.PROCESSFILE_MAIL_INVALID, 
					                DBSExceptions.PROCESSFILE_MAIL_LENGTH_ERROR);

		

		}
		
		checkResultFileSchedule(inBag);
	}
	
	private static CBBag fillOutBagWithCompanyProcessFileParam(CompanyProcessFileParam companyProcessFileParam) {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(VALUE9    , companyProcessFileParam.isFileEnabled());
		outBag.put(VALUE10   , companyProcessFileParam.isMailingEnabled());
		outBag.put(FILE_NAME , companyProcessFileParam.getProcessFileName());
		
		return outBag;
	}
	
	private static CBBag fillOutBagWithCompanyProcessFileEMail(Vector<CompanyProcessFileEmail> companyProcessFileEmailVector) {

		CBBag outBag = CBBagFactory.createBag();

		for (int index = 0; index < companyProcessFileEmailVector.size(); index++) {
			outBag.put(TABLE5, index, VALUE1, companyProcessFileEmailVector.get(index).getEmail());
		}

		return outBag;
	}

	private static CBBag fillOutBagWithCompanyResultFileSchedule(Vector<CompanyResultFileSchedule> companyResultFileScheduleVector) {

		CBBag outBag = CBBagFactory.createBag();

		for (int index = 0; index < companyResultFileScheduleVector.size(); index++) {
			outBag.put(TABLE6, index, TIME, companyResultFileScheduleVector.get(index).getTime());
			outBag.put(TABLE6, index, LIMITCODE, companyResultFileScheduleVector.get(index).isUseLimit());
		}

		return outBag;
	}
	
	/**
	 * EXT_DBS_GET_DOCUMENT_TYPE_LIST
	 */
	public static CBBag getDocumentTypeList(CBBag inBag) {
		CBBag outBag = CBBagFactory.createBag();
		
		CBReferenceData refData = new CBReferenceData("PRM_DBS_DOCUMENT_TYPE_LIST");
		Vector refDataList = refData.getPrmList();
		
		for(int i = 0; i < refDataList.size(); i++) {
			Hashtable hashTable = (Hashtable)refDataList.get(i);
			int code = Integer.valueOf(hashTable.get("CODE").toString());
			outBag.put(TABLE7, i, SELECT, false);
			outBag.put(TABLE7, i, CODE, code);
		}
		
		return outBag;
	}

}
