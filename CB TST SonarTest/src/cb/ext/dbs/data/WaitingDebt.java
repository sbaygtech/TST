package cb.ext.dbs.data;

import cb.smg.general.utility.CBCurrency;

public class WaitingDebt {
	
	public WaitingDebt() {
	}
	
	
	public WaitingDebt(CBCurrency guaranteedTotalInvoiceAmount, int totalWaitingInvoiceCount) {
		setGuaranteedTotalInvoiceAmount(guaranteedTotalInvoiceAmount);
		setTotalWaitingInvoiceCount(totalWaitingInvoiceCount);
		setNonGuaranteedTotalInvoiceAmount(CBCurrency.ZERO);
	}
	public CBCurrency getGuaranteedTotalInvoiceAmount() {
		return guaranteedTotalInvoiceAmount;
	}
	private void setGuaranteedTotalInvoiceAmount(CBCurrency guaranteedTotalInvoiceAmount) {
		this.guaranteedTotalInvoiceAmount = guaranteedTotalInvoiceAmount;
	}
	public int getTotalWaitingInvoiceCount() {
		return totalWaitingInvoiceCount;
	}
	private void setTotalWaitingInvoiceCount(int totalWaitingInvoiceCount) {
		this.totalWaitingInvoiceCount = totalWaitingInvoiceCount;
	}
	
	
	public CBCurrency getNonGuaranteedTotalInvoiceAmount() {
		return nonGuaranteedTotalInvoiceAmount;
	}


	public void setNonGuaranteedTotalInvoiceAmount(CBCurrency nonGuaranteedTotalInvoiceAmount) {
		this.nonGuaranteedTotalInvoiceAmount = nonGuaranteedTotalInvoiceAmount;
	}


	private CBCurrency guaranteedTotalInvoiceAmount;
	private CBCurrency nonGuaranteedTotalInvoiceAmount;
	private int totalWaitingInvoiceCount;

}
