package cb.ext.dbs.generic.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileDetailExtensionPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileDetailExtensionPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileDetailExtension {
	
	public CompanyDebtFileDetailExtension( ) {
		
	}

	public CompanyDebtFileDetailExtension( long detailOID) {
		setDetailOid(detailOID);
	}
	
	public void add() throws CBException {
		DBSCompanyDebtFileDetailExtensionPOM     pom  = DBSCompanyDebtFileDetailExtensionPOM.newDBSCompanyDebtFileDetailExtensionPOM();
		DBSCompanyDebtFileDetailExtensionPOMData data = DBSCompanyDebtFileDetailExtensionPOMData.newInstance();
		
		try {
			data.detailOID                = getDetailOid();
			data.invoiceDate              = getInvoiceDate();
			data.discountInterestAmount   = getDiscountInterestAmount();
			data.collectingAmount         = getCollectingAmount();
			data.collectingCurrencyCode   = getCollectingCurrencyCode();
			data.discountIntrstCollAmount = getDiscountIntrstCollectingAmount();
			data.collectingExchangeRate   = getCollectingExchangeRate();
			data.invoiceNumber            = getInvoiceNumber();
			data.transactionDate          = getTransactionDate();
			data.provisionRefNo           = getProvisionRefNo();
			data.parameter                = getParameter();
			data.invoiceType              = getInvoiceType();
			data.debtCurrencyCode         = getDebtCurrencyCode();
			
			pom.setDBSCompanyDebtFileDetailExtensionPOMData(data);
			pom.create();
			
			
		} 	
		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public long getDetailOid() {
		return detailOid;
	}
	public void setDetailOid(long detailOid) {
		this.detailOid = detailOid;
	}
	public CBDate getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(CBDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public CBCurrency getDiscountInterestAmount() {
		return discountInterestAmount;
	}
	public void setDiscountInterestAmount(CBCurrency discountInterestAmount) {
		this.discountInterestAmount = discountInterestAmount;
	}
	public CBCurrency getCollectingAmount() {
		return collectingAmount;
	}
	public void setCollectingAmount(CBCurrency collectingAmount) {
		this.collectingAmount = collectingAmount;
	}
	public String getCollectingCurrencyCode() {
		return collectingCurrencyCode;
	}
	public void setCollectingCurrencyCode(String collectingCurrencyCode) {
		this.collectingCurrencyCode = collectingCurrencyCode;
	}
	public CBCurrency getDiscountIntrstCollectingAmount() {
		return discountIntrstCollectingAmount;
	}
	public void setDiscountIntrstCollectingAmount(
			CBCurrency discountIntrstCollectingAmount) {
		this.discountIntrstCollectingAmount = discountIntrstCollectingAmount;
	}
	public CBCurrency getCollectingExchangeRate() {
		return collectingExchangeRate;
	}
	public void setCollectingExchangeRate(CBCurrency collectingExchangeRate) {
		this.collectingExchangeRate = collectingExchangeRate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public CBDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(CBDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getProvisionRefNo() {
		return provisionRefNo;
	}
	public void setProvisionRefNo(String provisionRefNo) {
		this.provisionRefNo = provisionRefNo;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getDebtCurrencyCode() {
		return debtCurrencyCode;
	}

	public void setDebtCurrencyCode(String debtCurrencyCode) {
		this.debtCurrencyCode = debtCurrencyCode;
	}
	
	private long oid ;
	private long detailOid ;
	private CBDate invoiceDate ;
	private CBCurrency discountInterestAmount ;
	private CBCurrency collectingAmount ;
	private String collectingCurrencyCode ;
	private CBCurrency discountIntrstCollectingAmount ;
	private CBCurrency  collectingExchangeRate;
	private String  invoiceNumber;
	private CBDate transactionDate;
	private String provisionRefNo;
	private String parameter;
	private String invoiceType;
	private String debtCurrencyCode;
}
