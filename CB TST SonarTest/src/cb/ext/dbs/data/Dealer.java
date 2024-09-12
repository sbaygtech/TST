package cb.ext.dbs.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.pom.DBSDealerAccountsPOM;
import cb.ext.dbs.pom.DBSDealerAccountsPOMData;
import cb.ext.dbs.pom.DBSDealerPOM;
import cb.ext.dbs.pom.DBSDealerPOMData;
import cb.ext.dbs.pom.DBSDealerUploadDocPOM;
import cb.ext.dbs.pom.DBSDealerUploadDocPOMData;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class Dealer {
	
	public Dealer() {
		
	}
	
	public Dealer(DBSDealerPOMData data) {
		
		setDealer(this, data);
		
	}
	
	public Vector<DealerDocument> getDealerDocuments(long dealerOID) throws CBException {

		DBSDealerUploadDocPOM pom = DBSDealerUploadDocPOM.newDBSDealerUploadDocPOM();
		DBSDealerUploadDocPOMData data = DBSDealerUploadDocPOMData.newInstance();

		DealerDocument dealerDocument = new DealerDocument();

		Vector<DealerDocument> dealerDocumentVector = new Vector<DealerDocument>();

		try {

			pom.readByDealerOID(dealerOID);

			while (pom.next()) {

				data = pom.getDBSDealerUploadDocPOMData();

				dealerDocument = new DealerDocument(data);

				dealerDocumentVector.add(dealerDocument);

			}

			return dealerDocumentVector;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public Vector<DealerAccount> getAccounts() throws CBException {
		return getAccounts(getOID());
	}
	
	private Vector<DealerAccount> getAccounts(long dealerOID) throws CBException {

		DBSDealerAccountsPOM pom = DBSDealerAccountsPOM.newDBSDealerAccountsPOM();
		DBSDealerAccountsPOMData data = DBSDealerAccountsPOMData.newInstance();

		DealerAccount dealerAccount = new DealerAccount();

		Vector<DealerAccount> dealerAccountVector = new Vector<DealerAccount>();

		try {

			pom.readByDealerOID(dealerOID);

			while (pom.next()) {

				data = pom.getDBSDealerAccountsPOMData();

				dealerAccount = new DealerAccount(data);

				dealerAccountVector.add(dealerAccount);

			}

			return dealerAccountVector;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	public Vector<DealerAccount> getAccountsOrderedBySpecialCurrency(String currency, LimitAccountOption accountOption, boolean screenMode) throws CBException {
		
		return getAccountsOrderedBySpecialCurrency(getOID(), currency, accountOption, screenMode);
	}
	
	private Vector<DealerAccount> getAccountsOrderedBySpecialCurrency(long dealerOID, String currency, LimitAccountOption accountOption,boolean screenMode) throws CBException {

		DealerAccount dealerAccount = new DealerAccount();

		Vector<DealerAccount> dealerAccountVector = null;

		Vector<DealerAccount> dealerAccountVectorFirst = new Vector<DealerAccount>();
		Vector<DealerAccount> dealerAccountVectorRest = new Vector<DealerAccount>();

		dealerAccountVector = getAccounts(dealerOID);

		for (int index = 0; index < dealerAccountVector.size(); index++) {

			dealerAccount = dealerAccountVector.get(index);

			if (dealerAccount.getCurrency().equals(currency)) {
				dealerAccountVectorFirst.add(dealerAccount);
			}
			else {
				
				if (LimitAccountOption.All.value().equals(accountOption) || screenMode || CBSessionInfo.isInternet()) {
					dealerAccountVectorRest.add(dealerAccount);
				}
				
			}

		}

		for (int index = 0; index < dealerAccountVectorRest.size(); index++) {
			dealerAccountVectorFirst.add(dealerAccountVectorRest.get(index));
		}

		return dealerAccountVectorFirst;

	}
	
	public Dealer getDealerByCompanyDealerCustomerNumber(long companyID ,String dealercustomerNumber)throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		Dealer dealer = null;
		
		try {
			
			if (pom.readByCompanyDealerCustCode(companyID , dealercustomerNumber)) {
				
				data = pom.getDBSDealerPOMData();
				
				dealer = setFields(data);

			}
			
			
			return dealer;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public Dealer getDealerByCompanyOidDealerCustomerNumber(long compantOid ,String dealercustomerNumber)throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		Dealer dealer = null;
		
		try {
			if (pom.readByCompanyOidAndDealerCustomerCode(compantOid , dealercustomerNumber)) {
				data = pom.getDBSDealerPOMData();
				dealer = setFields(data);
			}
			return dealer;
		}
		finally {
			if (pom!=null) {
				pom.close();
			}
		}
	}
	
	public List<Dealer> getDealerByCompanyOid(long companyOid)throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		Dealer dealer = null;
		List<Dealer> dealerList = new ArrayList<Dealer>();
		
		try {
			pom.readByCompanyOID(companyOid);
			
			while(pom.next()) {
				data = pom.getDBSDealerPOMData();
				dealer = setFields(data);
				dealerList.add(dealer);
			}
		}
		finally {
			if (pom!=null) {
				pom.close();
			}
		}
		return dealerList;
	}
	
	public List<Dealer> getDealerByCustomerNumberAndCompanyOID(long companyOID, int customerNumber)throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		Dealer dealer = null;
		List<Dealer> dealerList = new ArrayList<Dealer>();
		
		try {
			pom.readByCustomerNumberandCompanyOID(customerNumber, companyOID);
			
			while(pom.next()) {
				data = pom.getDBSDealerPOMData();
				dealer = setFields(data);
				dealerList.add(dealer);
			}
		}
		finally {
			if (pom!=null) {
				pom.close();
			}
		}
		return dealerList;
		
	}
	public Dealer getDealer(long oid)throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		Dealer dealer = null;
		
		try {
			
			if (pom.readByPrimaryKey(oid)) {
				
				data = pom.getDBSDealerPOMData();
				
				dealer =  setFields(data);

			}
			
			return dealer;
		}

		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}

	private Dealer setFields(DBSDealerPOMData data) {
		
		Dealer dealer = new Dealer();
		
		setDealer(dealer,data);
		
		return dealer;
	}
	
	private void setDealer(Dealer dealer, DBSDealerPOMData data) {
		
		dealer.setOID(data.oID);
		dealer.setActive(data.active);
		dealer.setCustomerNumber(data.customerNumber);
		dealer.setCompanyDealerCustomerCode(data.companyDealerCustomerCode);
		dealer.setProductRefNo(data.productRefNo);
		dealer.setCompanyOID(data.companyOID);
		
	}
	
	public static boolean isDealerExists(long companyOID, int customerNumber) throws CBException {
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		
		try {
			pom.readByCustomerNumberandCompanyOID(customerNumber, companyOID);
			if (pom.getSize() > 0) {
				return true;
			}
		} 
		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
		return false;
	}
	
	public static boolean isDealerExistsByCompanyOIDInnerCustCode(long companyOID, String dealerInnerCustomerNumber) throws CBException {

		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();

		try {
			return pom.readByCompanyDealerCustCode(companyOID, dealerInnerCustomerNumber);
		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	


	public long add() throws CBException{
		
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data = DBSDealerPOMData.newInstance();
		
		try {
			
			setFieldsForInsert(data);
			
			pom.setDBSDealerPOMData(data);
			pom.create();

			return data.oID;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}

	private void setFieldsForInsert(DBSDealerPOMData data) throws CBException {
		data.active            = isActive();
		data.companyOID        = getCompanyOID();     
		data.customerNumber    = getCustomerNumber();
		data.companyDealerCustomerCode = getCompanyDealerCustomerCode();
		data.productRefNo 	   = getProductRefNo();
		
		
		data.insertUser = CBSessionInfo.getKullaniciKodu();
		data.insertDate = CBSystem.getInstance().getProcessDate();
		data.insertTime = CBSystem.getInstance().getCurrentTime();
		data.updUser    = ".";
		data.updDate    = CBDateFactory.newCBDate("19000101");
		data.updTime    = new CBTime("000000");
	}
	
	public long update() throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		

		try {
			
			long dealerOID = -1L;
			
			if (pom.readByPrimaryKey(getOID())) {
				
				data = pom.getDBSDealerPOMData();
				
				setFieldsForUpdate(data);
				
				pom.setDBSDealerPOMData(data);
				pom.update();
				
				dealerOID =  data.oID;
				
			}
			
			return dealerOID;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}

	private void setFieldsForUpdate(DBSDealerPOMData data) {
		
		data.active  = isActive();
		
		data.companyDealerCustomerCode = getCompanyDealerCustomerCode();

		data.updUser = CBSessionInfo.getKullaniciKodu();
		data.updDate = CBSystem.getInstance().getProcessDate();
		data.updTime = CBSystem.getInstance().getCurrentTime();
		
	}
	
	public long delete() throws CBException{
		
		DBSDealerPOM     pom     = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data    = DBSDealerPOMData.newInstance();
		
		try {
			
			long dealerOID = -1L;
			
			if (pom.readByPrimaryKey(getOID())) {
				
				data = pom.getDBSDealerPOMData();
				
				pom.delete();
				
				dealerOID = data.oID;
			}
			
			return dealerOID;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}		
	}
	
	public static Vector<Dealer> getAllDealers() throws CBException {

		DBSDealerPOM pom = DBSDealerPOM.newDBSDealerPOM();
		DBSDealerPOMData data = DBSDealerPOMData.newInstance();

		Vector<Dealer> vectorDealer = new Vector<Dealer>();
		Dealer dealer = new Dealer();

		try {
			
		
			pom.readByAllRecords();
		
			while (pom.next()) {

				data = pom.getDBSDealerPOMData();

				dealer = new Dealer(data);
				vectorDealer.add(dealer);					
			}

			return vectorDealer;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	
		
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public int getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	public CBDate getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(CBDate insertDate) {
		this.insertDate = insertDate;
	}
	public CBTime getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(CBTime insertTime) {
		this.insertTime = insertTime;
	}
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public CBDate getUpdDate() {
		return updDate;
	}
	public void setUpdDate(CBDate updDate) {
		this.updDate = updDate;
	}
	public CBTime getUpdTime() {
		return updTime;
	}
	public void setUpdTime(CBTime updTime) {
		this.updTime = updTime;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	
	public String getCompanyDealerCustomerCode() {
		return companyDealerCustomerCode;
	}
	public void setCompanyDealerCustomerCode(String companyDealerCustomerCode) {
		this.companyDealerCustomerCode = companyDealerCustomerCode;
	}
	
	public long getOID() {
		return oid;
	}
	public void setOID(long oid) {
		this.oid = oid;
	}
	
	
	public long getProductRefNo() {
		return productRefNo;
	}
	public void setProductRefNo(long productRefNo) {
		this.productRefNo = productRefNo;
	}
	
	private  boolean active;
	private  long companyOID;
	private  int customerNumber;
	private  long productRefNo;
	
	public String companyDealerCustomerCode;
	


	private  CBDate insertDate;
	private  CBTime insertTime;
	private  String insertUser;
	
	private  CBDate updDate;
	private  CBTime updTime;
	private  String updUser;
	
	private long    oid;

}
