package cb.ext.dbs.bean;

import static cb.ext.dbs.util.CompanyDealerValidations.checkCurrencyExists;
import static cb.ext.dbs.util.CurrencyUtility.convertCurrencytoStr_EraseCommas;
import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;
import static cb.ext.dbs.util.ExceptionUtility.throwException;
import static cb.ext.dbs.util.ExceptionUtility.throwExceptionNoParam;
import static cb.ext.dbs.util.InfoMessageUtility.putInfoMessage;

import java.util.List;
import java.util.Vector;

import cb.ext.dbs.constants.CompanyDealerCodeFormat;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DBSOperationType;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.DealerAccountLimitDetail;
import cb.ext.dbs.data.DealerDocument;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.tosyali.data.Constants;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CompanyDealerValidations;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.CustomerUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBatchSubmitter;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class DealerService implements CBBagKeys {
	
	public static CBCurrency getDealerAvaliableAmount(CBCurrency totalWaitingDebt, Account account, CBCurrency usedAmountFromKMH) throws CBException {
		
		CBCurrency sumOfUsedBalanceAndGrandTotalGuaranteedInvoice = usedAmountFromKMH.add(totalWaitingDebt);

		return account.getLimitAmount().subtract(sumOfUsedBalanceAndGrandTotalGuaranteedInvoice);
		
	}
	
	public static CBCurrency getDealerAvaliableAmount(WaitingDebt waitingDebt, Account account, CBCurrency usedAmountFromKMH) throws CBException {
		
		return getDealerAvaliableAmount(waitingDebt.getGuaranteedTotalInvoiceAmount(), account, usedAmountFromKMH);
		
		
	}

	/* @serviceName: EXT_DBS_DEALER_DISTINCT_ACCOUNTS */
	public static CBBag getDealerDistinctAccounts(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		int index = 0;

		try {
			
			qe = new CBQueryExecuter("EXT_DBS_GET_DEALER_CURRENCIES");

			if (inBag.existsBagKey(COMPANYOID) && !inBag.get(COMPANYOID).toString().equals("0")) {
				qe.setParameter("COMPANYOID", inBag.get(COMPANYOID).toSimpleLong());
			}
			if (inBag.existsBagKey(FUTUREPAYMENT) && inBag.get(FUTUREPAYMENT).toString().equals("Y")) {
				qe.setParameter("FUTUREPAYMENT", 1);
			}

			qe.executeQuery();

			while (qe.next()) {

				outBag.put(ASSET, index, -1, qe.getString("CURRENCY"));
				outBag.put(ASSET_NAME, index, -1, qe.getString("CURRENCY"));

				index++;

			}

			return outBag;
			
		} finally {

			if (qe != null) {
				qe.close();
			}

		}

	}

	/* @serviceName: EXT_DBS_LIST_DEALER_FOR_COMBO */
	public static CBBag listDealerForComboBox(CBBag inBag) throws CBException {
	
		CBBag outBag = CBBagFactory.createBag();

		if ((inBag.existsBagKey(COMPANYOID)) && !inBag.get(COMPANYOID).toString().equals("")) {

			outBag = listDealers(inBag, "COMBOBOX");
		}
		else {
			outBag = listAllDealers();
		}

		return outBag;

	}
	
	private static CBBag listAllDealers() throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();
		
		Vector<Dealer> dealers = Dealer.getAllDealers();
		
		Dealer dealer = null;
		
		for (int index = 0; index < dealers.size(); index++) {

			dealer = dealers.get(index);
			
			setListDealerOutputFieldsForComboBox(dealer, index).copyTo(outBag);

		}

		return outBag;
		
		
	}

	/* @serviceName: EXT_DBS_LIST_DEALER */
	public static CBBag listDealer(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag = listDealers(inBag, null);

		return outBag;

	}

	private static CBBag listDealers(CBBag inBag, String callingType) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		long companyOID = inBag.get(COMPANYOID).toSimpleLong();
		
		Company company = new Company().get(companyOID);
		
		Customer customer  = CustomerUtility.getCustomerInfo(company.getCustomerNumber());

		if(!inBag.existsBagKey(COMPANYNAME)){
			inBag.put(COMPANYNAME, customer.getTitle());
		}

		if ((inBag.existsBagKey(CUSTOMERNUMBER)) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {
			outBag = listDealerByCompanyOidAndCustomerNumber(companyOID, inBag.get(CUSTOMERNUMBER).toString(), callingType);
		}
		else {
			outBag = listDealersByCompanyOID(companyOID, callingType);
		}

		if (!isCalledFromComboBox(callingType)) {
			if (outBag.getSize(TABLE1) == 0) {
				outBag = putInfoMessage(DBSExceptions.DEALER_LIST_NOT_FOUND, inBag, COMPANYNAME);
			}
		}

		return outBag;
	}

	private static boolean isCalledFromComboBox(String callingType) {
		return callingType != null;
	}

	private static CBBag listDealersByCompanyOID(long companyOID, String callingType) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Company company = new Company();
		Dealer dealer = null;

		Vector<Dealer> dealers = company.getDealers(companyOID);

		for (int index = 0; index < dealers.size(); index++) {

			dealer = dealers.get(index);
			
			if (isCalledFromComboBox(callingType)) {
				setListDealerOutputFieldsForComboBox(dealer, index).copyTo(outBag);
			}
			else {
				setListDealerOutputFields(dealer, index).copyTo(outBag);
			}

		}

		return outBag;
	}

	private static CBBag listDealerByCompanyOidAndCustomerNumber(long companyOID, String customerNumber, String callingType) throws CBException {
		CBBag outBag = CBBagFactory.createBag();

		List<Dealer> dealerList = new Dealer().getDealerByCustomerNumberAndCompanyOID(companyOID, Integer.parseInt(customerNumber));

		int i = 0;
		
		for(Dealer dealer : dealerList) {
			if (isCalledFromComboBox(callingType)) {
				setListDealerOutputFields(dealer, i).copyTo(outBag);
			}
			else {
				setListDealerOutputFieldsForComboBox(dealer, i).copyTo(outBag);
			}
			i++;
		}

		return outBag;
	}

	private static CBBag setListDealerOutputFields(Dealer dealer, int index) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		Customer customer  =CustomerUtility.getCustomerInfo(dealer.getCustomerNumber());

		outBag.put(TABLE1, index, OID            , dealer.getOID());
		outBag.put(TABLE1, index, CUSTOMERNUMBER , dealer.getCustomerNumber());
		outBag.put(TABLE1, index, TITLE          , customer.getTitle());
		outBag.put(TABLE1, index, ACTIVE         , dealer.isActive());
		outBag.put(TABLE1, index, VKN            , customer.getVkn());

		return outBag;
	}
	
	private static CBBag setListDealerOutputFieldsForComboBox(Dealer dealer, int index) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		int customerNumber = dealer.getCustomerNumber();

		outBag.put(ASSET, index, -1, customerNumber + " / " + CustomerUtility.getCustomerInfo(customerNumber).getTitle());

		outBag.put(ASSET_NAME, index, -1, dealer.getOID());

		return outBag;
	}


	// @serviceName: EXT_DBS_HANDLE_DEALER_ACCOUNT
	public static CBBag handleDealerAccounts(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		String operation = inBag.get(OPERATION).toString();

		String currency = inBag.get(CURRENCY).toString();

		String accountNo = inBag.get(ACCOUNTNR).toString();

		int tableSize = inBag.getSize(TABLE1);

		int selectedNumber = inBag.get(SELECTNUMBER).toSimpleInt();

		if ((operation.equals(DBSOperationType.add.value())) || (operation.equals(DBSOperationType.update.value()))) {

			if (currency.equals("")) {
				throwExceptionNoParam(DBSExceptions.ACCOUNT_CURRENCY_EMPTY);
			}
			else if (accountNo.equals("")) {
				throwExceptionNoParam(DBSExceptions.CURRENTACCOUNT_EMPTY);
			}
			else {

				checkIsDealerAccountKMH(inBag, currency);

				checkCurrencyExists(inBag, operation, currency, tableSize, selectedNumber);

				inBag.copyTo(outBag);

				if (operation.equals(DBSOperationType.add.value())) {

					outBag.put(TABLE1, tableSize, OID, tableSize + 1);
					outBag.put(TABLE1, tableSize, CURRENCY, currency);
					outBag.put(TABLE1, tableSize, ACCOUNTNR, accountNo);

				}
				else {

					outBag.put(TABLE1, selectedNumber, CURRENCY, currency);
					outBag.put(TABLE1, selectedNumber, ACCOUNTNR, accountNo);

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

					counter++;

				}

			}

		}

		return outBag;

	}

	private static void checkIsDealerAccountKMH(CBBag inBag, String currency) throws CBException {

		if (currency.equals(CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode())) {

			long companyOID = inBag.get(COMPANYOID).toSimpleLong();

			Company company = new Company();

			company = company.get(companyOID);

			String kMHMethodName = company.getKMHMethodName();

			if (!kMHMethodName.equals(inBag.get(KMHMETODKODU).toString())) {
				throwException(DBSExceptions.DEALER_ACCOUNT_KTH_NOT, inBag, KMHMETODKODU);
			}

		}
	}

	/* @serviceName: EXT_DBS_CHECK_DEALER_INPUT */
	public static CBBag checkDealerInputs(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CompanyDealerValidations.checkCustomerNumber(inBag);

		checkDealerCustomerNumber(inBag);

		String operation = inBag.get(OPERATION).toString();

		if (operation.equals(DBSOperationType.add.value())) {

			checkDealerUniqueness(inBag);

			checkDealerInnerCustNoUniqueness(inBag);

		}
		else if (operation.equals(DBSOperationType.update.value())) {

			checkDealerInnerCustNoUniquenessForUpdate(inBag);

		}

		checkDealerAccounts(inBag);

		if (!checkDealerTRYAccountDefinitionExists(inBag)) {
			throwExceptionNoParam(DBSExceptions.DEALER_CURRENCY_TYPE_NOT_TRY);
		}

		outBag.put(CODE1, 1);

		return outBag;

	}

	private static void checkDealerInnerCustNoUniquenessForUpdate(CBBag inBag) throws CBException {

		Dealer dealer = new Dealer();
		dealer = dealer.getDealer(inBag.get(VALUE1).toSimpleLong());

		if (!dealer.getCompanyDealerCustomerCode().equals(inBag.get(CODE2).toString())) {
			checkDealerInnerCustNoUniqueness(inBag);
		}
	}

	private static boolean checkDealerTRYAccountDefinitionExists(CBBag inBag) throws CBException {

		boolean foundTryCurrency = false;

		String defaultCurrency = CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode();

		for (int index = 0; ((index < inBag.getSize(TABLE3)) && (!foundTryCurrency)); index++) {

			if (inBag.get(TABLE3, index, CURRENCY).toString().equals(defaultCurrency)) {
				foundTryCurrency = true;
			}

		}
		return foundTryCurrency;
	}

	private static void checkDealerAccounts(CBBag inBag) throws CBException {

		CompanyDealerValidations.checkAccounts(inBag, DBSExceptions.DEALER_ACCOUNT_NOT_SET);

	}

	private static void checkDealerInnerCustNoUniqueness(CBBag inBag) throws CBException {

		if (Dealer.isDealerExistsByCompanyOIDInnerCustCode(inBag.get(COMPANYOID).toSimpleLong(), inBag.get(CODE2).toString())) {
			throwException(DBSExceptions.COMPANYDEALERCUSTOMERNUM_NOT_UNIQUE, inBag, CODE2);
		}
	}

	private static void checkDealerUniqueness(CBBag inBag) throws CBException {

		Company company = new Company();
		company = company.get(inBag.get(COMPANYOID).toSimpleLong());
		
		if(!company.isDealerWithSameCustomerNumber()) {
			if (Dealer.isDealerExists(inBag.get(COMPANYOID).toSimpleLong(), inBag.get(CUSTOMERNUMBER).toSimpleInt())) {
				throwException(DBSExceptions.DEALER_NOT_UNIQUE, inBag, new int[] { CODE4, CUSTOMERNUMBER });
			}
		}
	}

	private static void checkDealerCustomerNumber(CBBag inBag) throws CBException {

		if (inBag.get(CODE2).toString().equals("")) {
			throwExceptionNoParam(DBSExceptions.COMPANYDEALERCUSTOMERNUM_EMPTY);
		}
	}

	// @serviceName: EXT_DBS_GET_DEALER_DETAIL
	public static CBBag getDealerDetail(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		Dealer dealer = new Dealer();

		Vector<DealerAccount> dealerAccountVector = new Vector<DealerAccount>();
		Vector<DealerDocument> dealerDocumentVector = new Vector<DealerDocument>();

		long dealerOID = inBag.get(VALUE1).toSimpleLong();

		dealer = dealer.getDealer(dealerOID);

		fillOutBagWithDealer(dealer).copyTo(outBag);

		dealerAccountVector = dealer.getAccounts();
		fillOutBagWithDealerAccounts(dealerAccountVector).copyTo(outBag);


		dealerDocumentVector = dealer.getDealerDocuments(dealerOID);
		fillOutBagWithDealerDocuments(dealerDocumentVector).copyTo(outBag);

		return outBag;

	}

	private static CBBag fillOutBagWithDealerDocuments(Vector<DealerDocument> dealerDocumentVector) {
		CBBag outBag = CBBagFactory.createBag();
		
		DealerDocument dealerDocument = null;

		for (int index = 0; index < dealerDocumentVector.size(); index++) {
			
			dealerDocument = dealerDocumentVector.get(index);
			
			outBag.put(TABLE6, index, OID, dealerDocument.getOID() );
			outBag.put(TABLE6, index, DOCUMENTID, dealerDocument.getDocumentID());
			outBag.put(TABLE6, index, FILENAME, dealerDocument.getFileName());
			outBag.put(TABLE6, index, EXPLANATION, dealerDocument.getExplanation());
			outBag.put(TABLE6, index, USERNAME, dealerDocument.getUploader());
			outBag.put(TABLE6, index, DATE1, dealerDocument.getUploadDate());
			outBag.put(TABLE6, index, TIMECONTROL, dealerDocument.getUploadTime());
		}
		
		return outBag;
	}

	private static CBBag fillOutBagWithDealerAccounts(Vector<DealerAccount> dealerAccountVector) {

		CBBag outBag = CBBagFactory.createBag();

		DealerAccount dealerAccount = null;

		for (int index = 0; index < dealerAccountVector.size(); index++) {

			dealerAccount = dealerAccountVector.get(index);

			outBag.put(TABLE3, index, OID, index + 1);
			outBag.put(TABLE3, index, CURRENCY, dealerAccount.getCurrency());
			outBag.put(TABLE3, index, ACCOUNTNR, dealerAccount.getAccountNo());
		}

		return outBag;

	}

	private static CBBag fillOutBagWithDealer(Dealer dealer) {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(ACTIVE, dealer.isActive());
		outBag.put(CUSTOMERNUMBER, dealer.getCustomerNumber());
		outBag.put(CODE2, dealer.getCompanyDealerCustomerCode());

		return outBag;
	}

	/* @serviceName: EXT_DBS_ADD_DEALER */
	public static CBBag addDealer(CBBag inBag) throws CBException {

		Dealer dealer = setDealerFieldsForInsert(inBag);

		long dealerOID = dealer.add();

		addDealerAccounts(inBag, dealerOID);

		addDealerDocuments(inBag, dealerOID);

		LimitFileService.sendLimitFile(inBag.get(COMPANYOID).toSimpleLong());

		return putInfoMessage(DBSExceptions.DEALER_DEFINITION_DONE);

	}

	private static void addDealerDocuments(CBBag inBag, long dealerOID) throws CBException {

		CBDate processDate = CBSystem.getInstance().getProcessDate();
		CBTime processTime = CBSystem.getInstance().getCurrentTime();

		DealerDocument dealerDocument = new DealerDocument();

		for (int index = 0; index < inBag.getSize(TABLE6); index++) {

			dealerDocument.setDealerOID(dealerOID);
			dealerDocument.setDocumentID(inBag.get(TABLE6, index, DOCUMENTID).toSimpleLong());
			dealerDocument.setExplanation(inBag.get(TABLE6, index, EXPLANATION).toString());
			dealerDocument.setFileName(inBag.get(TABLE6, index, FILENAME).toString());
			dealerDocument.setUploadDate(processDate);
			dealerDocument.setUploadTime(processTime);
			dealerDocument.setUploader(CBSessionInfo.getKullaniciKodu());

			dealerDocument.add();

		}

	}

	private static void updateDealerDocuments(CBBag inBag, long dealerOID) throws CBException {

		deleteDealerDocuments(dealerOID);

		addDealerDocuments(inBag, dealerOID);
	}

	private static void deleteDealerDocuments(long dealerOID) throws CBException {

		DealerDocument dealerDocument = new DealerDocument();
		dealerDocument.deleteAllByDealerOID(dealerOID);
	}

	private static void updateDealerAccounts(CBBag inBag, long dealerOID) throws CBException {

		deleteDealerAccounts(dealerOID);

		addDealerAccounts(inBag, dealerOID);
	}

	private static void deleteDealerAccounts(long dealerOID) throws CBException {

		DealerAccount dealerAccount = new DealerAccount();
		dealerAccount.deleteAllByDealerOID(dealerOID);

	}

	private static void addDealerAccounts(CBBag inBag, long dealerOID) throws CBException {

		DealerAccount dealerAccount = new DealerAccount();

		for (int index = 0; index < inBag.getSize(TABLE3); index++) {

			dealerAccount.setDealerOID(dealerOID);
			dealerAccount.setCurrency(inBag.get(TABLE3, index, CURRENCY).toString());
			dealerAccount.setAccountNo(inBag.get(TABLE3, index, ACCOUNTNR).toString());
			dealerAccount.setActive(true);

			dealerAccount.add();

		}
	}

	private static Dealer setDealerFields(CBBag inBag) throws CBException {

		Dealer dealer = new Dealer();

		dealer.setActive(true);
		dealer.setCompanyOID(inBag.get(COMPANYOID).toSimpleLong());
		dealer.setCustomerNumber(inBag.get(CUSTOMERNUMBER).toSimpleInt());
		dealer.setCompanyDealerCustomerCode(inBag.get(CODE2).toString());
	

		return dealer;
	}
	
	private static Dealer setDealerFieldsForInsert(CBBag inBag) throws CBException {

		
		
		Dealer dealer = setDealerFields(inBag);
		dealer.setProductRefNo(CBSessionInfo.getCurrentUrunRefNo());
		return dealer;
	}

	private static Dealer setDealerFieldsForUpdate(CBBag inBag) throws CBException {

		Dealer dealer = setDealerFields(inBag);
		dealer.setOID(inBag.get(VALUE1).toSimpleLong());
		dealer.setActive(inBag.get(ACTIVE).toBoolean());

		return dealer;
	}

	/* @serviceName: EXT_DBS_UPDATE_DEALER */
	public static CBBag updateDealer(CBBag inBag) throws CBException {

		Dealer dealer = setDealerFieldsForUpdate(inBag);

		long dealerOID = dealer.update();

		updateDealerAccounts(inBag, dealerOID);

		updateDealerDocuments(inBag, dealerOID);

		return putInfoMessage(DBSExceptions.DEALER_UPDATE_DONE);

	}

	/* @serviceName: EXT_DBS_DELETE_DEALER */
	public static CBBag deleteDealer(CBBag inBag) throws CBException {

		Dealer dealer = setDealerFieldsForUpdate(inBag);

		long dealerOID = dealer.delete();

		deleteDealerAccounts(dealerOID);

		deleteDealerDocuments(dealerOID);

		return putInfoMessage(DBSExceptions.DEALER_DELETE_DONE);

	}
	
	public static String setDealerInternalCustomerCodeLongVersion(String companyDealerCustomerCode, String dealerAcccountCurrency) {

		String dealerInternalCustomerCode;

		if (isCurrencyTRY(dealerAcccountCurrency)) {

			dealerInternalCustomerCode = Constants.INTERNAL_TRY_CURRENCY_CODE + companyDealerCustomerCode;
		}
		else {
			dealerInternalCustomerCode = Constants.INTERNAL_USD_CURRENCY_CODE + companyDealerCustomerCode;
		}

		return dealerInternalCustomerCode;
	}

	// @serviceName : EXT_DBS_GET_DEALER_INFO
	public static CBBag getDealerCustomerInfo(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		Dealer dealer = new Dealer().getDealer(inBag.get(OID).toSimpleLong());
		
		outBag.put(CUSTOMERNUMBER, dealer.getCustomerNumber());
		
		return outBag;

	}

	public static String  getDealerInnerCode(String  customerNumber, long companyOID) throws CBException {

		String dealerInnerCode ="";
		
		Company company = new Company().get(companyOID);
		
		if(CBSessionInfo.isInternet() || company.getDealerCodeFormatType().equals(CompanyDealerCodeFormat.Standard.value())){
			dealerInnerCode =customerNumber;
		}else {
			dealerInnerCode =customerNumber.substring(7,16).trim();
		}
		
		return dealerInnerCode;
	}
	
	/* @serviceName: EXT_DBS_DEALER_LIST_FOR_COMBOBOX */
	public static CBBag getDealerListForComboBox(CBBag inBag) throws CBException {
	
		CBBag outBag = CBBagFactory.createBag();

		if ((inBag.existsBagKey(COMPANYOID) && !inBag.get(COMPANYOID).toString().equals(""))
				|| (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals(""))
				|| (inBag.existsBagKey(BRANCHCODE) && !inBag.get(BRANCHCODE).toString().equals(""))){

			outBag = listDealers(inBag);
		}
		
		return outBag;

	}
	
	private static CBBag listDealers(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBQueryExecuter qe = null;
		int index = 0;
		
		try {
			qe = new CBQueryExecuter("EXT_QR_DBS_COMPANY_DEALER_INFO_FOR_IB");
	
			if (inBag.existsBagKey(COMPANYOID)&&!inBag.get(COMPANYOID).toString().equals("")) {
				qe.setParameter("COMPANYOID" , inBag.get(COMPANYOID).toSimpleLong());
			}
			
			if (inBag.existsBagKey(BRANCHCODE) && !inBag.get(BRANCHCODE).toString().equals("")) {
				qe.setParameter("ORGANIZATION", inBag.get(BRANCHCODE).toString());
			}
			
			if (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
			
			if (inBag.existsBagKey(FUTUREPAYMENT) && inBag.get(FUTUREPAYMENT).toString().equals("Y")) {
				qe.setParameter("FUTUREPAYMENT", 1);
			}
			
			qe.executeQuery();
    	
	    	while(qe.next()) {
				long cutomerNumber = qe.getLong("CUSTOMERNUMBER");
				long dealerOid = qe.getLong("OID");
				String customerTitle = qe.getString("TITLE") ;
				setListDealerOutputFieldsForComboBox(cutomerNumber, dealerOid, customerTitle, index).copyTo(outBag);
				index++;
			}
		}
		finally{
			if (qe!=null) {
				qe.close();
			}
		}
		return outBag;
	}
	
	private static CBBag setListDealerOutputFieldsForComboBox(long customerNumber, long dealerOid, String customerTitle, int index) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		outBag.put(ASSET, index, -1, customerNumber + " / " + customerTitle);
		outBag.put(ASSET_NAME, index, -1, dealerOid);
		
		return outBag;
	}
	
	public static DealerAccountLimitDetail getDealerAccountLimitDetail(Company company, Dealer dealer) throws CBException {
		Account account = null;
		CBCurrency usedAmountFromKMH = null;
		CBCurrency remainingLimit = null;
		DealerAccountLimitDetail dealerAccountLimitDetail = new DealerAccountLimitDetail();
		
		Vector<DealerAccount> dealerAccounts = dealer.getAccountsOrderedBySpecialCurrency(CurrencyUtility.DEFAULT_CURRENCY, 
					                     null, true);
		
		for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {
			DealerAccount dealerAccount = dealerAccounts.get(innerIndex);
			
			if (isCurrencyTRY(dealerAccount.getCurrency())) {
				account = AccountUtility.getCurrentAccountInfo(dealerAccount.getAccountNo());
				usedAmountFromKMH = AccountUtility.getUsedAmountFromKMH(company, dealerAccount, account);
			}	
			
			remainingLimit = account.getLimitAmount().subtract(usedAmountFromKMH);
		}
		
		dealerAccountLimitDetail.setDealerOid(dealer.getOID());
		dealerAccountLimitDetail.setLimitAmount(convertCurrencytoStr_EraseCommas(account.getLimitAmount()));
		dealerAccountLimitDetail.setRemainingLimit(convertCurrencytoStr_EraseCommas(remainingLimit));
						
		return dealerAccountLimitDetail;			
	}
	
	public static CBBag submitJob(CBBag inBag) throws CBException {
		CBBag prmBag = CBBagFactory.createBag();
		CBBag srvBag = CBBagFactory.createBag();
		CBBag outBag = CBBagFactory.createBag();

		
	
		srvBag.put(NAME, "EXTD7199");
		srvBag.put(ACIKLAMA, "DBS Send Waiting Debt Mail And Sms To Dealers");
		srvBag.put(ONCELIK, "50");

		if (inBag.existsBagKey(BATCHPROCESSDATE)) {
			CBTime startTime = inBag.get(BATCHPROCESSDATE).toCBTime();
			srvBag.put(BASLANGICSAATI, startTime.toString().substring(0, 4));
		}

		prmBag.put(COMPANYOID, inBag.get(COMPANYOID));
		prmBag.put(DAY, inBag.get(DAY));
		prmBag.put(SMSDELIVERY, inBag.get(SMSDELIVERY));
		prmBag.put(EMAILDELIVERY, inBag.get(EMAILDELIVERY));
		
		srvBag.put(BAG, prmBag);

		CBBatchSubmitter.getInstance().submitJob(srvBag);

		return outBag;
	}
	
}