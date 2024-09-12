package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyLimitFileReportPOM;
import cb.ext.dbs.pom.DBSCompanyLimitFileReportPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;

public class CompanyReportLimitFile {
	
	public CompanyReportLimitFile() {

	}
	
	public String add() throws CBException{
		
		
		DBSCompanyLimitFileReportPOM     pom  = DBSCompanyLimitFileReportPOM.newDBSCompanyLimitFileReportPOM();
		DBSCompanyLimitFileReportPOMData data = DBSCompanyLimitFileReportPOMData.newInstance();
		
		try {
			
			setFieldsForInsert(data);
			
			pom.setDBSCompanyLimitFileReportPOMData(data);
			pom.create();

			return data.reportSequenceNumber;
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	private void setFieldsForInsert(DBSCompanyLimitFileReportPOMData data) throws CBException {
		
		data.reportSequenceNumber = getReportSequenceNumber();     
		data.active               = isActive();     
		data.companyTitle         = getCompanyTitle();
		data.dealerTitle          = getDealerTitle();
		data.customerNumber       = getCustomerNumber();
		data.companyDealerCusCode = getCompanyDealerCustomerCode();
		data.tlAccountNo          = getTlAccountNo();
		data.usdAccountNo         = getUsdAccountNo();
		data.currency             = getCurrency();
		data.limit                = getLimit();
		data.availableLimit       = getAvailableLimit();
		data.unPaidInvoice        = getUnPaidInvoice();
		data.availableDBS         = getAvailableDBS();

	}
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getReportSequenceNumber() {
		return reportSequenceNumber;
	}
	public void setReportSequenceNumber(String reportSequenceNumber) {
		this.reportSequenceNumber = reportSequenceNumber;
	}
	public String getCompanyTitle() {
		return companyTitle;
	}
	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
	public String getDealerTitle() {
		return dealerTitle;
	}
	public void setDealerTitle(String dealerTitle) {
		this.dealerTitle = dealerTitle;
	}
	public int getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCompanyDealerCustomerCode() {
		return companyDealerCustomerCode;
	}
	public void setCompanyDealerCustomerCode(String companyDealerCustomerCode) {
		this.companyDealerCustomerCode = companyDealerCustomerCode;
	}
	public String getTlAccountNo() {
		return tlAccountNo;
	}
	public void setTlAccountNo(String tlAccountNo) {
		this.tlAccountNo = tlAccountNo;
	}
	public String getUsdAccountNo() {
		return usdAccountNo;
	}
	public void setUsdAccountNo(String usdAccountNo) {
		this.usdAccountNo = usdAccountNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public CBCurrency getLimit() {
		return limit;
	}
	public void setLimit(CBCurrency limit) {
		this.limit = limit;
	}
	public CBCurrency getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(CBCurrency availableLimit) {
		this.availableLimit = availableLimit;
	}
	public CBCurrency getUnPaidInvoice() {
		return unPaidInvoice;
	}
	public void setUnPaidInvoice(CBCurrency unPaidInvoice) {
		this.unPaidInvoice = unPaidInvoice;
	}
	public CBCurrency getAvailableDBS() {
		return availableDBS;
	}
	public void setAvailableDBS(CBCurrency availableDBS) {
		this.availableDBS = availableDBS;
	}
	private boolean isActive;
	private String reportSequenceNumber;
	private String companyTitle;
	private String dealerTitle;
	private int customerNumber;
	private String companyDealerCustomerCode;
	private String tlAccountNo;
	private String usdAccountNo;
	private String currency;
	private CBCurrency limit;
	private CBCurrency availableLimit;
	private CBCurrency unPaidInvoice;
	private CBCurrency availableDBS;
	
}

