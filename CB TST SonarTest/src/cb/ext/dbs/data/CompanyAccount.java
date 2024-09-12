package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyAccountPOM;
import cb.ext.dbs.pom.DBSCompanyAccountPOMData;
import cb.smg.general.utility.CBException;

public class CompanyAccount {
	
	public CompanyAccount() {
		
	}
	
	public CompanyAccount(DBSCompanyAccountPOMData data) {
		setCompanyAccount(this, data);
	}

	private void setCompanyAccount(CompanyAccount companyAccount, DBSCompanyAccountPOMData data) {
		
		companyAccount.setCompanyOID(companyOID);
		companyAccount.setCurrency(data.currency);
		companyAccount.setBlockageAccountNo(data.blockageAccountNo);
		companyAccount.setAccountNo(data.accountNo);
		companyAccount.setBlockageDayCount(data.blockageDayCount);
		companyAccount.setActive(data.active);
		
	}

	public String getCompanyBlockageAccountNo(long companyOID, String currency) throws CBException {

		return getCompanyAccount(companyOID, currency).getBlockageAccountNo();

	}

	public String getCompanyAccountNo(long companyOID, String currency) throws CBException {

		return getCompanyAccount(companyOID, currency).getAccountNo();

	}
	
	public CompanyAccount getCompanyAccount(long companyOID, String currency) throws CBException {

		DBSCompanyAccountPOM pom = DBSCompanyAccountPOM.newDBSCompanyAccountPOM();
		DBSCompanyAccountPOMData data = DBSCompanyAccountPOMData.newInstance();

		CompanyAccount companyAccount = null;

		try {

			if (pom.readByCompanyOIDandCurrency(companyOID, currency)) {

				data = pom.getDBSCompanyAccountPOMData();
				
				companyAccount = new CompanyAccount(data); 

				

			}

			return companyAccount;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public void deleteAllByCompanyOID(long companyOID) throws CBException {

		DBSCompanyAccountPOM pom = DBSCompanyAccountPOM.newDBSCompanyAccountPOM();

		try {
			
			pom.readByCompanyOID(companyOID);

			while (pom.next()) {
				pom.delete();
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public long add() throws CBException {

		DBSCompanyAccountPOM pom = DBSCompanyAccountPOM.newDBSCompanyAccountPOM();
		DBSCompanyAccountPOMData data = DBSCompanyAccountPOMData.newInstance();

		try {

			setPOMData(data);

			pom.setDBSCompanyAccountPOMData(data);
			pom.create();

			return data.oID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private void setPOMData(DBSCompanyAccountPOMData data) {
		data.companyOID = getCompanyOID();
		data.currency = getCurrency();
		data.blockageAccountNo = getBlockageAccountNo();
		data.accountNo = getAccountNo();
		data.blockageDayCount = getBlockageDayCount();
		data.active = isActive();
	}

	public long getCompanyOID() {
		return companyOID;
	}

	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBlockageAccountNo() {
		return blockageAccountNo;
	}

	public void setBlockageAccountNo(String blockageAccountNo) {
		this.blockageAccountNo = blockageAccountNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getBlockageDayCount() {
		return blockageDayCount;
	}

	public void setBlockageDayCount(int blockageDayCount) {
		this.blockageDayCount = blockageDayCount;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private long companyOID;
	private String currency;
	private String blockageAccountNo;
	private String accountNo;
	private int blockageDayCount;
	private boolean active;

}
