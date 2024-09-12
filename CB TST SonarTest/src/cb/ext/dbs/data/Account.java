package cb.ext.dbs.data;

import cb.smg.general.utility.CBCurrency;

public class Account {

	public Account() {
	}

	public Account(String accountNo, String title, String branchName, String branchCode, CBCurrency limitAmount, CBCurrency avalBalance, CBCurrency limitWithouBalance) {
		setAccountNo(accountNo);
		setTitle(title);
		setBranchName(branchName);
		setBranchCode(branchCode);
		setLimitAmount(limitAmount);
		setAvalBalance(avalBalance);
		setBalanceWithoutLimit(limitWithouBalance);
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getBranchName() {
		return branchName;
	}

	private void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public CBCurrency getLimitAmount() {
		return limitAmount;
	}

	private void setLimitAmount(CBCurrency limitAmount) {
		this.limitAmount = limitAmount;
	}

	public CBCurrency getAvalBalance() {
		return avalBalance;
	}

	private void setAvalBalance(CBCurrency avalBalance) {
		this.avalBalance = avalBalance;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public CBCurrency getBalanceWithoutLimit() {
		return balanceWithoutLimit;
	}

	public void setBalanceWithoutLimit(CBCurrency balanceWithoutLimit) {
		this.balanceWithoutLimit = balanceWithoutLimit;
	}

	private String accountNo;
	private String title;
	private String branchName;
	private String branchCode;
	private CBCurrency limitAmount;
	private CBCurrency avalBalance;
	private CBCurrency balanceWithoutLimit;

}
