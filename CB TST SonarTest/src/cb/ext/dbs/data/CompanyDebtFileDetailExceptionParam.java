package cb.ext.dbs.data;

import java.util.ArrayList;
import java.util.List;

import cb.ext.dbs.pom.DBSCompanyDebtFileResultFileExceptionPrmPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileResultFileExceptionPrmPOMData;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileDetailExceptionParam  implements CBBagKeys {

	
	public  CompanyDebtFileDetailExceptionParam() {
	
	}
	public  CompanyDebtFileDetailExceptionParam(long companyOID , String methodCode) {
		setCompanyOID(companyOID);
		setMethodCode(methodCode);
	}
	
	public  CompanyDebtFileDetailExceptionParam(long companyOID ) {
		setCompanyOID(companyOID);
	}
	
	public  CompanyDebtFileDetailExceptionParam(String  methodCode ) {
		setMethodCode(methodCode);
	}
	
	public  CompanyDebtFileDetailExceptionParam(DBSCompanyDebtFileResultFileExceptionPrmPOMData data) {
		setExceptionCode(data.exceptionCode);
		setExceptionDescription(data.exceptionDescription);
		setAdkExceptionDescription(data.adkDescription);
	}
	
	
	public CompanyDebtFileDetailExceptionParam get() throws CBException {
		
		CompanyDebtFileDetailExceptionParam detailExceptionPrm = null;
		
		DBSCompanyDebtFileResultFileExceptionPrmPOM      pom = DBSCompanyDebtFileResultFileExceptionPrmPOM.newDBSCompanyDebtFileResultFileExceptionPrmPOM();
		DBSCompanyDebtFileResultFileExceptionPrmPOMData data = DBSCompanyDebtFileResultFileExceptionPrmPOMData.newInstance();
		
		
		try {
			
			 pom.readByCompanyOidAndMethodCodeActive(getCompanyOID(), getMethodCode());
			
			 if(pom.next()){
				
				data = pom.getDBSCompanyDebtFileResultFileExceptionPrmPOMData();

				detailExceptionPrm = new CompanyDebtFileDetailExceptionParam(data);
			}
			
			return detailExceptionPrm;
		
		} finally{
			if(pom != null)
				pom.close();
			
		}
		
	}
	
	
	public List<String> getAllCompanyException() throws CBException {
		
		List<String> detailExceptionPrmCompany = new ArrayList<String>();
		
		DBSCompanyDebtFileResultFileExceptionPrmPOM      pom = DBSCompanyDebtFileResultFileExceptionPrmPOM.newDBSCompanyDebtFileResultFileExceptionPrmPOM();
		DBSCompanyDebtFileResultFileExceptionPrmPOMData data = DBSCompanyDebtFileResultFileExceptionPrmPOMData.newInstance();
		
		
		try {
			pom.readByCompanyOIDAndActive(getCompanyOID());
			
			while (pom.next()){
				data = pom.getDBSCompanyDebtFileResultFileExceptionPrmPOMData();
				
				detailExceptionPrmCompany.add(data.methodCode) ;
			}
			return detailExceptionPrmCompany;
		
		} finally{
			if(pom != null)
				pom.close();
			
		}
		
	}
	
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionDescription() {
		return exceptionDescription;
	}
	public void setExceptionDescription(String exceptionDescription) {
		this.exceptionDescription = exceptionDescription;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getMethodCode() {
		return methodCode;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	public String getAdkExceptionDescription() {
		return adkExceptionDescription;
	}
	public void setAdkExceptionDescription(String adkExceptionDescription) {
		this.adkExceptionDescription = adkExceptionDescription;
	}
	private long companyOID;
	private String exceptionCode;
	private String exceptionDescription;
	private boolean active;
	private String adkExceptionDescription;
	private String methodCode;
	
	
}
