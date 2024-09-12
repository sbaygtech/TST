package cb.ext.dbs.data;

import cb.smg.general.utility.CBCurrency;

public class CompanyDebtMasterFile {
	
	public String getProcessStatu() {
		return processStatu;
	}
	public void setProcessStatu(String statu) {
		this.processStatu = statu;
	}
	public CBCurrency getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(CBCurrency invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	private String processStatu;
	private CBCurrency invoiceAmount;

}
