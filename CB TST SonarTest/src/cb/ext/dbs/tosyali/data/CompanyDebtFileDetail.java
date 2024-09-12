package cb.ext.dbs.tosyali.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileDetailPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileDetailPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileDetail {
	
	public void add(CompanyDebtFileHeader debtFileHeader) throws CBException {
		
		DBSCompanyDebtFileDetailPOM pom = DBSCompanyDebtFileDetailPOM	.newDBSCompanyDebtFileDetailPOM();
		DBSCompanyDebtFileDetailPOMData data = DBSCompanyDebtFileDetailPOMData	.newInstance();

		try {

			data.customerNo = getCustomerNo();
			data.customerTitle = getCustomerTitle();
			data.dueDate = getDueDate();
			data.recordType = getRecordType();
			data.invoiceNo = getInvoiceNo();
			data.invoiceAmount = getInvoiceAmount();
			data.currency = getCurrency();
			data.fileOID = debtFileHeader.getOid();
			data.processStatu = getProcessStatu();
			data.processExplanation = getProcessExplanation();
			data.masterOID = getMasterOID();

			pom.setDBSCompanyDebtFileDetailPOMData(data);
			pom.create();
		}

		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerTitle() {
		return customerTitle;
	}
	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}
	public CBDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(CBDate dueDate) {
		this.dueDate = dueDate;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public CBCurrency getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(CBCurrency invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProcessStatu() {
		return processStatu;
	}
	public void setProcessStatu(String processStatu) {
		this.processStatu = processStatu;
	}
	public String getProcessExplanation() {
		return processExplanation;
	}
	public void setProcessExplanation(String processExplanation) {
		this.processExplanation = processExplanation;
	}
	public long getFileOID() {
		return fileOID;
	}
	public void setFileOID(long fileOID) {
		this.fileOID = fileOID;
	}
	public long getMasterOID() {
		return masterOID;
	}
	public void setMasterOID(long masterOID) {
		this.masterOID = masterOID;
	}
	
	private String customerNo;
	private String customerTitle;
	private CBDate dueDate;
	private String recordType;
	private String invoiceNo;
	private CBCurrency invoiceAmount;
	private String currency;
	private String processStatu;
	private String processExplanation;
	private long fileOID;
	private long masterOID;
	

}
