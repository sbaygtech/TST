package cb.ext.dbs.data;

public class DealerAccountLimitDetail {
	private long dealerOid;
	private String limitAmount;
	private String remainingLimit;
	private String dealerTitle;
	
	public long getDealerOid() {
		return dealerOid;
	}
	
	public void setDealerOid(long dealerOid) {
		this.dealerOid = dealerOid;
	}
	
	public String getLimitAmount() {
		return limitAmount;
	}
	
	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	public String getRemainingLimit() {
		return remainingLimit;
	}
	
	public void setRemainingLimit(String remainingLimit) {
		this.remainingLimit = remainingLimit;
	}
	
	public String getDealerTitle() {
		return dealerTitle;
	}
	
	public void setDealerTitle(String dealerTitle) {
		this.dealerTitle = dealerTitle;
	}	
}
