package cb.ext.dbs.data;

import java.util.Vector;

import cb.ext.dbs.pom.DBSCompanyAccountPOM;
import cb.ext.dbs.pom.DBSCompanyAccountPOMData;
import cb.ext.dbs.pom.DBSCompanyPOM;
import cb.ext.dbs.pom.DBSCompanyPOMData;
import cb.ext.dbs.pom.DBSDealerPOM;
import cb.ext.dbs.pom.DBSDealerPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

/**
 * 
 * @author Mustafa Unlu
 * @version 1.0 20140714
 * 
 *          This class is abstraction of DBS company
 */
public class Company {

	public Company() {

	}

	public static boolean isCustomerExists(int customerNumber) throws CBException {

		DBSCompanyPOM dBSCompanyPOM = DBSCompanyPOM.newDBSCompanyPOM();

		try {
			return dBSCompanyPOM.readByCustomerNo(customerNumber);
		} finally {

			if (dBSCompanyPOM != null) {
				dBSCompanyPOM.close();
			}
		}

	}
	
	public Vector<CompanyAccount> getCompanyAccounts()throws CBException{
		
		return getCompanyAccounts(getOID());
		
	}
	
public Vector<CompanyAccount> getCompanyAccounts(long companyOID)throws CBException{
		
		DBSCompanyAccountPOM     pom     = DBSCompanyAccountPOM.newDBSCompanyAccountPOM();
		DBSCompanyAccountPOMData data    = DBSCompanyAccountPOMData.newInstance();
		
		CompanyAccount companyAccount = new CompanyAccount(); 
		
		
		Vector<CompanyAccount> companyAccountVector =  new Vector<CompanyAccount>();
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyAccountPOMData();
				
				companyAccount = new CompanyAccount(data); 
				
				companyAccountVector.add(companyAccount);
				
			}
			
			
			return companyAccountVector;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public Vector<Dealer> getDealers(long companyOID) throws CBException {

		return getDealers(companyOID, false);

	}

	public Vector<Dealer> getDealers(boolean activeFlag) throws CBException {

		return getDealers(getOID(), activeFlag);

	}

	public Vector<Dealer> getDealers(long companyOID, boolean activeFlag) throws CBException {

		DBSDealerPOM pom = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data = DBSDealerPOMData.newInstance();

		Vector<Dealer> vectorDealer = new Vector<Dealer>();
		Dealer dealer = new Dealer();

		try {
			
		
			pom.readByCompanyOID(companyOID);
		
			while (pom.next()) {

				data = pom.getDBSDealerPOMData();
				
				if ( (activeFlag && data.active) || (!activeFlag) ) 
				{
					dealer = new Dealer(data);
					vectorDealer.add(dealer);					
				}
			}

			return vectorDealer;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public Company get(long oid) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			if (pom.readByPrimaryKey(oid)) {

				data = pom.getDBSCompanyPOMData();

				Company company = setFields(data);

				return company;

			}
			else {
				return null;
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public Company getByInternalCode(String internalCode) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			if (pom.readByInternalCode(internalCode)) {

				data = pom.getDBSCompanyPOMData();

				Company company = setFields(data);

				return company;

			}
			else {
				return null;
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public Company getByKMHMethodName(String kmhMethodName) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			if (pom.readByKMHMethodName(kmhMethodName)) {

				data = pom.getDBSCompanyPOMData();

				Company company = setFields(data);

				return company;

			}
			else {
				return null;
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public Company getByCustomerNumber(int customerNumber) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			if (pom.readByCustomerNo(customerNumber)) {

				data = pom.getDBSCompanyPOMData();
				Company company = setFields(data);

				return company;
			}
			else {
				return null;
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	public Company getByCustomerNumberAndDBBSType(int customerNumber, int dbsType) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			if (pom.readByCustomerNoAndDBSType(customerNumber, dbsType)) {

				data = pom.getDBSCompanyPOMData();
				Company company = setFields(data);

				return company;
			}
			else {
				return null;
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private Company setFields(DBSCompanyPOMData data) {

		Company company = new Company();

		company.setOID(data.oID);
		company.setActive(data.active);
		company.setCompanyInternalCode(data.companyInternalCode);
		company.setCustomerNumber(data.customerNumber);
		company.setWorkingMethod(data.workingMethod);
		company.setKMHMethodName(data.KMHMethodName);
		company.setCompanyTotalLimitAmount(data.companyTotalLimit);
		company.setCompanyMaturityPeriod(data.companyMaturityPeriod);
		company.setCompanyProcessDayCount(data.companyProcessDayCount);
		company.setCompanyLimitGapType(data.companyLimitGapType);
		company.setPartialPayable(data.partialPayment);
		company.setLoadedOverLimitDebt(data.loadedOverLimitDebt);
		company.setManuelPaymentBeforeDueDate(data.manuelPaymentBeforeDueDate);
		company.setDealerWithSameCustomerNumber(data.addDealerWithSameCustomerNumber);
		company.setCurrencyProcessMethod(data.currencyProcessMethod);
		company.setExchangeRate(data.exchangeRate);
		company.setExchangeRateType(data.exchangeRateType);
        company.setDealerCodeFormatType(data.dealerCodeFormatType);
        company.setLocalCurrencyCodeType(data.localCurrencyCodeType);
        company.setDebtFileSendType(data.debtFileSendType);
        company.setCompanyName(data.companyName);
        company.setGuarantorship(data.guarantorship);
        company.setGuarantorshipWaitingTime(data.guarantorshipWaitingTime);
        company.setKmhLimitRate(data.kmhLimitRate);
        company.setDbsType(data.dbsType);
        
		return company;
	}

	public Vector<Company> list() throws CBException {

		Vector<Company> companies = new Vector<Company>();

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			pom.readByAllRecords();

			while (pom.next()) {

				data = pom.getDBSCompanyPOMData();
				companies.add(setFields(data));

			}

			return companies;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	public Vector<Company> listByDbsType(int dbsType) throws CBException {

		Vector<Company> companies = new Vector<Company>();

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {

			pom.readByDbsType(dbsType);

			while (pom.next()) {

				data = pom.getDBSCompanyPOMData();
				companies.add(setFields(data));

			}

			return companies;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	public Vector<Company> manuelPaymentBeforeDueDateList() throws CBException {
		Vector<Company> companies = new Vector<Company>();
		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		try {
			pom.readManuelPaymentBeforeDueDate();
			
			while (pom.next()) {
				data = pom.getDBSCompanyPOMData();
				companies.add(setFields(data));
			}
			return companies;
		} 
		finally {
			if (pom != null) {
				pom.close();
			}
		}
	}

	public long add() throws CBException {

		DBSCompanyPOM dBSCompanyPOM = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData dBSCompanyPOMData = DBSCompanyPOMData.newInstance();

		try {

			dBSCompanyPOMData.active                 = isActive();
			dBSCompanyPOMData.companyInternalCode    = getCompanyInternalCode();
			dBSCompanyPOMData.customerNumber         = getCustomerNumber();
			dBSCompanyPOMData.workingMethod          = getWorkingMethod();
			dBSCompanyPOMData.KMHMethodName          = getKMHMethodName();
			dBSCompanyPOMData.companyTotalLimit      = getCompanyTotalLimitAmount();
			dBSCompanyPOMData.companyMaturityPeriod  = getCompanyMaturityPeriod();
			dBSCompanyPOMData.companyProcessDayCount = getCompanyProcessDayCount();
			dBSCompanyPOMData.companyLimitGapType    = getCompanyLimitGapType();
			dBSCompanyPOMData.partialPayment         = isPartialPayable();
			dBSCompanyPOMData.loadedOverLimitDebt 	 = isLoadedOverLimitDebt();
			dBSCompanyPOMData.manuelPaymentBeforeDueDate = isManuelPaymentBeforeDueDate();
			dBSCompanyPOMData.addDealerWithSameCustomerNumber = isDealerWithSameCustomerNumber();
			dBSCompanyPOMData.currencyProcessMethod  = getCurrencyProcessMethod();
			dBSCompanyPOMData.exchangeRate           = getExchangeRate();
			dBSCompanyPOMData.exchangeRateType       = getExchangeRateType();
			dBSCompanyPOMData.dealerCodeFormatType   = getDealerCodeFormatType();
			dBSCompanyPOMData.localCurrencyCodeType  = getLocalCurrencyCodeType();
			dBSCompanyPOMData.debtFileSendType       = getDebtFileSendType();
			dBSCompanyPOMData.companyName			 = getCompanyName(); 
			
			dBSCompanyPOMData.guarantorship 		 	= isGuarantorship();
			dBSCompanyPOMData.guarantorshipWaitingTime 	= getGuarantorshipWaitingTime();
			dBSCompanyPOMData.kmhLimitRate 				= getKmhLimitRate();
			dBSCompanyPOMData.dbsType					= getDbsType();
			
			dBSCompanyPOMData.insertUser = CBSessionInfo.getKullaniciKodu();
			dBSCompanyPOMData.insertDate = CBSystem.getInstance().getProcessDate();
			dBSCompanyPOMData.insertTime = CBSystem.getInstance().getCurrentTime();
			dBSCompanyPOMData.updUser    = ".";
			dBSCompanyPOMData.updDate    = CBDateFactory.newCBDate("19000101");
			dBSCompanyPOMData.updTime    = new CBTime("000000");

			dBSCompanyPOM.setDBSCompanyPOMData(dBSCompanyPOMData);
			dBSCompanyPOM.create();

			return dBSCompanyPOMData.oID;

		} finally {

			if (dBSCompanyPOM != null) {
				dBSCompanyPOM.close();
			}
		}

	}

	public long update(int customerNumber) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		long companyOID = -1L;
		try {

			if (pom.readByCustomerNo(customerNumber)) {

				data = pom.getDBSCompanyPOMData();

				data.active                 = isActive();
				data.companyInternalCode    = getCompanyInternalCode();
				data.workingMethod          = getWorkingMethod();
				data.KMHMethodName          = getKMHMethodName();
				data.companyTotalLimit      = getCompanyTotalLimitAmount();
				data.companyMaturityPeriod  = getCompanyMaturityPeriod();
				data.companyProcessDayCount = getCompanyProcessDayCount();
				data.companyLimitGapType    = getCompanyLimitGapType();
				data.partialPayment         = isPartialPayable;
				data.loadedOverLimitDebt 	= isLoadedOverLimitDebt();
				data.manuelPaymentBeforeDueDate	= isManuelPaymentBeforeDueDate();
				data.addDealerWithSameCustomerNumber = isDealerWithSameCustomerNumber();
				data.currencyProcessMethod  = getCurrencyProcessMethod();
				data.exchangeRate           = getExchangeRate();
				data.exchangeRateType       = getExchangeRateType();
				data.dealerCodeFormatType   = getDealerCodeFormatType();
				data.localCurrencyCodeType  = getLocalCurrencyCodeType();
				data.debtFileSendType       = getDebtFileSendType();
				data.companyName			= getCompanyName();

				data.updUser = CBSessionInfo.getKullaniciKodu();
				data.updDate = CBSystem.getInstance().getProcessDate();
				data.updTime = CBSystem.getInstance().getCurrentTime();
				
				data.guarantorship 				= isGuarantorship();
				data.guarantorshipWaitingTime 	= getGuarantorshipWaitingTime();
				data.kmhLimitRate 				= getKmhLimitRate();
				data.dbsType 					= getDbsType();
				
				pom.setDBSCompanyPOMData(data);
				pom.update();

				companyOID = data.oID;

			}

			return companyOID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public long deleteByCustomerNumber(int customerNumber) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();
		DBSCompanyPOMData data = DBSCompanyPOMData.newInstance();

		long companyOID = -1L;

		try {

			if (pom.readByCustomerNo(customerNumber)) {

				data = pom.getDBSCompanyPOMData();
				pom.delete();

				companyOID = data.oID;

			}

			return companyOID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public void delete(long companyOID) throws CBException {

		DBSCompanyPOM pom = DBSCompanyPOM.newDBSCompanyPOM();

		try {

			if (pom.readByPrimaryKey(companyOID)) {
				
				pom.delete();

			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public void delete() throws CBException {

		delete(getOID());

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCompanyInternalCode() {
		return companyInternalCode;
	}

	public void setCompanyInternalCode(String companyInternalCode) {
		this.companyInternalCode = companyInternalCode;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public int getWorkingMethod() {

		return workingMethod;
	}

	public void setWorkingMethod(int workingMethod) {

		this.workingMethod = workingMethod;

	}

	public long getOID() {
		return this.oid;
	}

	public void setOID(long oid) {
		this.oid = oid;
	}

	public String getKMHMethodName() {
		return kMHMethodName;
	}

	public void setKMHMethodName(String KMHMethodName) {
		this.kMHMethodName = KMHMethodName;
	}

	public CBCurrency getCompanyTotalLimitAmount() {
		return companyTotalLimitAmount;
	}

	public void setCompanyTotalLimitAmount(CBCurrency companyTotalLimitAmount) {
		this.companyTotalLimitAmount = companyTotalLimitAmount;
	}

	public int getCompanyMaturityPeriod() {

		return companyMaturityPeriod;
	}

	public void setCompanyMaturityPeriod(int companyMaturityPeriod) {

		this.companyMaturityPeriod = companyMaturityPeriod;

	}

	public int getCompanyProcessDayCount() {

		return companyProcessDayCount;
	}

	public void setCompanyProcessDayCount(int companyProcessDayCount) {

		this.companyProcessDayCount = companyProcessDayCount;

	}

	public int getCompanyLimitGapType() {

		return companyLimitGapType;
	}

	public void setCompanyLimitGapType(int companyLimitGapType) {

		this.companyLimitGapType = companyLimitGapType;

	}

	public boolean isPartialPayable() {
		return isPartialPayable;
	}

	public void setPartialPayable(boolean isPartialPayable) {
		this.isPartialPayable = isPartialPayable;
	}

	public String getCurrencyProcessMethod() {
		return currencyProcessMethod;
	}

	public void setCurrencyProcessMethod(String currencyProcessMethod) {
		this.currencyProcessMethod = currencyProcessMethod;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getExchangeRateType() {
		return exchangeRateType;
	}

	public void setExchangeRateType(String exchangeRateType) {
		this.exchangeRateType = exchangeRateType;
	}

	public String getDealerCodeFormatType() {
		return dealerCodeFormatType;
	}

	public void setDealerCodeFormatType(String dealerCodeFormatType) {
		this.dealerCodeFormatType = dealerCodeFormatType;
	}

	public String getLocalCurrencyCodeType() {
		return localCurrencyCodeType;
	}

	public void setLocalCurrencyCodeType(String localCurrencyCodeType) {
		this.localCurrencyCodeType = localCurrencyCodeType;
	}

	public String getDebtFileSendType() {
		return debtFileSendType;
	}

	public void setDebtFileSendType(String debtFileSendType) {
		this.debtFileSendType = debtFileSendType;
	}
	
	public boolean isLoadedOverLimitDebt() {
		return loadedOverLimitDebt;
	}

	public void setLoadedOverLimitDebt(boolean loadedOverLimitDebt) {
		this.loadedOverLimitDebt = loadedOverLimitDebt;
	}

	public boolean isManuelPaymentBeforeDueDate() {
		return isManuelPaymentBeforeDueDate;
	}

	public void setManuelPaymentBeforeDueDate(boolean isManuelPaymentBeforeDueDate) {
		this.isManuelPaymentBeforeDueDate = isManuelPaymentBeforeDueDate;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public boolean isDealerWithSameCustomerNumber() {
		return dealerWithSameCustomerNumber;
	}

	public void setDealerWithSameCustomerNumber(boolean dealerWithSameCustomerNumber) {
		this.dealerWithSameCustomerNumber = dealerWithSameCustomerNumber;
	}
		
	public boolean isGuarantorship() {
		return guarantorship;
	}

	public void setGuarantorship(boolean guarantorship) {
		this.guarantorship = guarantorship;
	}

	public CBCurrency getGuarantorshipWaitingTime() {
		return guarantorshipWaitingTime;
	}

	public void setGuarantorshipWaitingTime(CBCurrency guarantorshipWaitingTime) {
		this.guarantorshipWaitingTime = guarantorshipWaitingTime;
	}

	public CBCurrency getKmhLimitRate() {
		return kmhLimitRate;
	}

	public void setKmhLimitRate(CBCurrency kmhLimitRate) {
		this.kmhLimitRate = kmhLimitRate;
	}

	public int getDbsType() {
		return dbsType;
	}

	public void setDbsType(int dbsType) {
		this.dbsType = dbsType;
	}


	/* Fields for Basic Info */
	private long oid;

	private boolean active;
	private String companyInternalCode;
	private int customerNumber;

	/* Fields for Working Method */

	private int workingMethod;

	/* Collection features */

	private String kMHMethodName;
	private CBCurrency companyTotalLimitAmount;
	private int companyMaturityPeriod;
	private int companyProcessDayCount;
	private int companyLimitGapType;

	private boolean isPartialPayable;
	private boolean loadedOverLimitDebt;
	private boolean isManuelPaymentBeforeDueDate;
	private boolean dealerWithSameCustomerNumber;
	private String companyName;

	private String currencyProcessMethod;	
	private String exchangeRate;
	private String exchangeRateType;
	
	private String dealerCodeFormatType;
	private String localCurrencyCodeType;
	private String debtFileSendType;

	private boolean guarantorship;
	private CBCurrency guarantorshipWaitingTime;
	private CBCurrency kmhLimitRate;
	private int dbsType;
	
}
