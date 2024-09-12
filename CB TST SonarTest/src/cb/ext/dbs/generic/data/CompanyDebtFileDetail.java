package cb.ext.dbs.generic.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileDetailPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileDetailPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileDetail {
	
	public void add(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetailExtension detailExtension, CompanyDebtFileDetailException detailException) throws CBException {
		
		DBSCompanyDebtFileDetailPOM pom = DBSCompanyDebtFileDetailPOM	.newDBSCompanyDebtFileDetailPOM();
		DBSCompanyDebtFileDetailPOMData data = DBSCompanyDebtFileDetailPOMData	.newInstance();

		try {

			data.customerNo           = getCustomerNo();
			data.customerTitle        = getCustomerTitle();
			data.dueDate              = getDueDate();
			data.recordType           = getRecordType();
			data.invoiceNo            = getInvoiceNo();
			data.invoiceAmount        = getInvoiceAmount();
			data.currency             = getCurrency();
			data.fileOID              = debtFileHeader.getOid();
			data.processStatu         = getProcessStatu();
			data.processExplanation   = getProcessExplanation();
			data.masterOID            = getMasterOID();
			data.specialExceptionCode = getSpecialExceptionCode();
			data.invoiceRefNo 		  = getInvoiceRefNo();
			data.invoiceType		  = getInvoiceType();

			pom.setDBSCompanyDebtFileDetailPOMData(data);
			pom.create();
			
			detailExtension .setDetailOid(data.oID);
			detailExtension.add();
			
			detailException = new CompanyDebtFileDetailException (data.oID , detailException.getExceptionCode(), detailException.getExceptionDescription());
			detailException.add();	
	
		}

		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public CompanyDebtFileDetail getCompanyDebtFileDetailByMasterOid(long masterOid) throws CBException {
		DBSCompanyDebtFileDetailPOM pom = DBSCompanyDebtFileDetailPOM.newDBSCompanyDebtFileDetailPOM();
		DBSCompanyDebtFileDetailPOMData data = DBSCompanyDebtFileDetailPOMData.newInstance();

		CompanyDebtFileDetail companyDebtFileDetail = null;
		
		try {
			if(pom.readDebtFileDetailByMasterOid(masterOid)) {
				data = pom.getDBSCompanyDebtFileDetailPOMData();
				companyDebtFileDetail = setFields(data);
			}
			
			return companyDebtFileDetail;
		}

		finally {
			if (pom != null) {
				pom.close();
			}
		}
	}
	
	private CompanyDebtFileDetail setFields(DBSCompanyDebtFileDetailPOMData data) {
		CompanyDebtFileDetail companyDebtFileDetail = new CompanyDebtFileDetail();
		
		companyDebtFileDetail.setCurrency(data.currency);
		companyDebtFileDetail.setCustomerNo(data.customerNo);
		companyDebtFileDetail.setCustomerTitle(data.customerTitle);
		companyDebtFileDetail.setDueDate(data.dueDate);
		companyDebtFileDetail.setFileOID(data.fileOID);
		companyDebtFileDetail.setInvoiceAmount(data.invoiceAmount);
		companyDebtFileDetail.setInvoiceNo(data.invoiceNo);
		companyDebtFileDetail.setInvoiceRefNo(data.invoiceRefNo);
		companyDebtFileDetail.setInvoiceType(data.invoiceType);
		companyDebtFileDetail.setMasterOID(data.masterOID);
		companyDebtFileDetail.setProcessExplanation(data.processExplanation);
		companyDebtFileDetail.setProcessStatu(data.processStatu);
		companyDebtFileDetail.setRecordType(data.recordType);
		companyDebtFileDetail.setSpecialExceptionCode(data.specialExceptionCode);
		
		return companyDebtFileDetail;
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
	public String getSpecialExceptionCode() {
		return specialExceptionCode;
	}
	
	public void setSpecialExceptionCode(String specialExceptionCode) {
		this.specialExceptionCode = specialExceptionCode;
	}
	
	public String getInvoiceRefNo() {
		return invoiceRefNo;
	}
	
	public void setInvoiceRefNo(String invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}
	
	public String getInvoiceType() {
		return invoiceType;
	}
	
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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
	private String specialExceptionCode;
	private String invoiceRefNo;
	private String invoiceType;

}
