package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSDealerAccountsPOM;
import cb.ext.dbs.pom.DBSDealerAccountsPOMData;
import cb.smg.general.utility.CBException;

public class DealerAccount {
	
	public DealerAccount() {
	}
	
	public DealerAccount(DBSDealerAccountsPOMData data) {
		
		setDealerAccount(data);
	}

	public String getDealerAccountNo(long dealerOID, String currency) throws CBException {
		
		
		return getDealerAccount(dealerOID, currency).getAccountNo();


	}
	
	public DealerAccount getDealerAccount(long dealerOID, String currency) throws CBException {

		DBSDealerAccountsPOM pom = DBSDealerAccountsPOM.newDBSDealerAccountsPOM();
		DBSDealerAccountsPOMData data = DBSDealerAccountsPOMData.newInstance();

		DealerAccount dealerAccount = null;

		try {

			if (pom.readByDealerOIDandCurrency(dealerOID, currency)) {

				data = pom.getDBSDealerAccountsPOMData();

				dealerAccount = new DealerAccount(data);

			}

			return dealerAccount;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	private void setDealerAccount(DBSDealerAccountsPOMData data) {
		
		setDealerOID(data.dealerOID);
		setCurrency(data.currency);
		setAccountNo(data.accountNo);
		setActive(data.active);

	}

	public void deleteAllByDealerOID(long dealerOID) throws CBException {

		DBSDealerAccountsPOM pom = DBSDealerAccountsPOM.newDBSDealerAccountsPOM();

		try {
			pom.readByDealerOID(dealerOID);

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

		DBSDealerAccountsPOM pom = DBSDealerAccountsPOM.newDBSDealerAccountsPOM();
		DBSDealerAccountsPOMData data = DBSDealerAccountsPOMData.newInstance();

		try {

			data.dealerOID = getDealerOID();
			data.currency = getCurrency();
			data.accountNo = getAccountNo();
			data.active = isActive();

			pom.setDBSDealerAccountsPOMData(data);
			pom.create();

			return data.oID;
			
		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public long getDealerOID() {
		return dealerOID;
	}

	public void setDealerOID(long dealerOID) {
		this.dealerOID = dealerOID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private long dealerOID;
	private String accountNo;
	private String currency;
	private boolean active;

}
