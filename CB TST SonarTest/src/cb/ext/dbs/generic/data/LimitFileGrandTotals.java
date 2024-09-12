package cb.ext.dbs.generic.data;

import cb.smg.general.utility.CBCurrency;

public class LimitFileGrandTotals {
	
	public LimitFileGrandTotals() {
	}
	
	public LimitFileGrandTotals(int rowCount,
								int totalCustomerCount, 
			                    CBCurrency grandTotalLimit, 
			                    CBCurrency grandTotalDealerAvalTotalAmount,
			                    CBCurrency grandTotalGuaranteedInvoiceAmount,
			                    CBCurrency grandTotalNonGuaranteedInvoiceAmount,
			                    int grandTotalWaitingInvoiceCount,
			                    CBCurrency grandTotalNonGuaranteedInvoiceAmount2,
			                    CBCurrency grandTotalUsedAmountFromKMH
			                   ) {
		
		setRowCount(rowCount);
		setTotalCustomerCount(totalCustomerCount);
		setGrandTotalLimit(grandTotalLimit);
		setGrandTotalDealerAvalTotalAmount(grandTotalDealerAvalTotalAmount);
		setGrandTotalGuaranteedInvoiceAmount(grandTotalGuaranteedInvoiceAmount);
		setGrandTotalNonGuaranteedInvoiceAmount(grandTotalNonGuaranteedInvoiceAmount);
		setGrandTotalWaitingInvoiceCount(grandTotalWaitingInvoiceCount);
		setGrandTotalNonGuaranteedInvoiceAmount2(grandTotalNonGuaranteedInvoiceAmount2);
		setGrandTotalUsedAmountFromKMH(grandTotalUsedAmountFromKMH);
	}
	
	public int getTotalCustomerCount() {
		return totalCustomerCount;
	}

	public void setTotalCustomerCount(int totalCustomerCount) {
		this.totalCustomerCount = totalCustomerCount;
	}
	
    public CBCurrency getGrandTotalLimit() {
		return grandTotalLimit;
	}

	public void setGrandTotalLimit(CBCurrency grandTotalLimit) {
		this.grandTotalLimit = grandTotalLimit;
	}
	
	public CBCurrency getGrandTotalDealerAvalTotalAmount() {
		return grandTotalDealerAvalTotalAmount;
	}

	public void setGrandTotalDealerAvalTotalAmount(CBCurrency grandTotalDealerAvalTotalAmount) {
		this.grandTotalDealerAvalTotalAmount = grandTotalDealerAvalTotalAmount;
	}
	
	public CBCurrency getGrandTotalGuaranteedInvoiceAmount() {
		return grandTotalGuaranteedInvoiceAmount;
	}

	public void setGrandTotalGuaranteedInvoiceAmount(CBCurrency grandTotalGuaranteedInvoiceAmount) {
		this.grandTotalGuaranteedInvoiceAmount = grandTotalGuaranteedInvoiceAmount;
	}
	
	public CBCurrency getGrandTotalNonGuaranteedInvoiceAmount() {
		return grandTotalNonGuaranteedInvoiceAmount;
	}

	public void setGrandTotalNonGuaranteedInvoiceAmount(CBCurrency grandTotalNonGuaranteedInvoiceAmount) {
		this.grandTotalNonGuaranteedInvoiceAmount = grandTotalNonGuaranteedInvoiceAmount;
	}
	
	public int getGrandTotalWaitingInvoiceCount() {
		return grandTotalWaitingInvoiceCount;
	}

	public void setGrandTotalWaitingInvoiceCount(int grandTotalWaitingInvoiceCount) {
		this.grandTotalWaitingInvoiceCount = grandTotalWaitingInvoiceCount;
	}
	
	public CBCurrency getGrandTotalNonGuaranteedInvoiceAmount2() {
		return grandTotalNonGuaranteedInvoiceAmoun2;
	}

	public void setGrandTotalNonGuaranteedInvoiceAmount2(CBCurrency grandTotalNonGuaranteedInvoiceAmoun2) {
		this.grandTotalNonGuaranteedInvoiceAmoun2 = grandTotalNonGuaranteedInvoiceAmoun2;
	}
	
	public CBCurrency getGrandTotalNonGuaranteedInvoiceAmoun2() {
		return grandTotalNonGuaranteedInvoiceAmoun2;
	}

	public void setGrandTotalNonGuaranteedInvoiceAmoun2(CBCurrency grandTotalNonGuaranteedInvoiceAmoun2) {
		this.grandTotalNonGuaranteedInvoiceAmoun2 = grandTotalNonGuaranteedInvoiceAmoun2;
	}
	
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public CBCurrency getGrandTotalUsedAmountFromKMH() {
		return grandTotalUsedAmountFromKMH;
	}

	public void setGrandTotalUsedAmountFromKMH(CBCurrency grandTotalUsedAmountFromKMH) {
		this.grandTotalUsedAmountFromKMH = grandTotalUsedAmountFromKMH;
	}
	
	private int rowCount;
	private int totalCustomerCount;
	private CBCurrency grandTotalLimit;
	private CBCurrency grandTotalDealerAvalTotalAmount;
	private CBCurrency grandTotalGuaranteedInvoiceAmount;
	private CBCurrency grandTotalNonGuaranteedInvoiceAmount;
	private int grandTotalWaitingInvoiceCount;
	private CBCurrency grandTotalNonGuaranteedInvoiceAmoun2;
	private CBCurrency grandTotalUsedAmountFromKMH;
	
}
