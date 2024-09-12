package cb.ext.dbs.generic.data;

import cb.ext.dbs.pom.DBSCompanyDebtProcessForeignCurConversionPOM;
import cb.ext.dbs.pom.DBSCompanyDebtProcessForeignCurConversionPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class CompanyDebtFileProcessForeignCurConversion {

	public CompanyDebtFileProcessForeignCurConversion() {
		
	}
	
	
	public void add( ) throws CBException {
		
		DBSCompanyDebtProcessForeignCurConversionPOM     pom  = DBSCompanyDebtProcessForeignCurConversionPOM.newDBSCompanyDebtProcessForeignCurConversionPOM();
		DBSCompanyDebtProcessForeignCurConversionPOMData data = DBSCompanyDebtProcessForeignCurConversionPOMData.newInstance();

		try {

			data = setPOMData(data);

			pom.setDBSCompanyDebtProcessForeignCurConversionPOMData(data);
			pom.create();
			
		
		}

		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
	}

	private DBSCompanyDebtProcessForeignCurConversionPOMData setPOMData(DBSCompanyDebtProcessForeignCurConversionPOMData data) {
		data.masterOID               = getMasterOID();
		data.transactionAmount       = getTransactionAmount();
		data.transactionCurrencyCode = getTransactionCurrencyCode();
		data.transactionRate         = getTransactionRate();
		data.sourceAmount            = getSourceAmount();
		data.sourceCurrencyCode      = getSourceCurrencyCode();
		data.invoiceAmount           = getInvoiceAmount();
		data.invoiceCurrencyCode     = getInvoiceCurrencyCode();
		data.processDate             = getProcessDate();
		data.processTime             = CBSystem.getInstance().getCurrentTime();
	
		return data;
	}
	
	public long getMasterOID() {
		return masterOID;
	}
	public void setMasterOID(long masterOID) {
		this.masterOID = masterOID;
	}
	public CBCurrency getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(CBCurrency transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrencyCode() {
		return transactionCurrencyCode;
	}
	public void setTransactionCurrencyCode(String transactionCurrencyCode) {
		this.transactionCurrencyCode = transactionCurrencyCode;
	}
	public CBCurrency getTransactionRate() {
		return transactionRate;
	}
	public void setTransactionRate(CBCurrency transactionRate) {
		this.transactionRate = transactionRate;
	}
	public CBCurrency getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(CBCurrency sourceAmount) {
		this.sourceAmount = sourceAmount;
	}
	public String getSourceCurrencyCode() {
		return sourceCurrencyCode;
	}
	public void setSourceCurrencyCode(String sourceCurrencyCode) {
		this.sourceCurrencyCode = sourceCurrencyCode;
	}
	public CBDate getProcessDate() {
		return processDate;
	}
	public void setProcessDate(CBDate processDate) {
		this.processDate = processDate;
	}
	public CBTime getProcessTime() {
		return processTime;
	}
	public void setProcessTime(CBTime processTime) {
		this.processTime = processTime;
	}
	public CBCurrency getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(CBCurrency invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getInvoiceCurrencyCode() {
		return invoiceCurrencyCode;
	}
	public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
		this.invoiceCurrencyCode = invoiceCurrencyCode;
	}

	long masterOID;
	CBCurrency transactionAmount;
	String transactionCurrencyCode;
	CBCurrency transactionRate ;
	CBCurrency sourceAmount;
	String sourceCurrencyCode;
	CBDate processDate;
	CBTime processTime;
	CBCurrency invoiceAmount;
	String invoiceCurrencyCode;
}
