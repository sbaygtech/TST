package cb.ext.dbs.data;

import java.util.ArrayList;
import java.util.List;

import cb.ext.dbs.pom.DBSComopanyMasterProcessFileExceptionPrmPOM;
import cb.ext.dbs.pom.DBSComopanyMasterProcessFileExceptionPrmPOMData;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBException;

public class CompanyDebtMasterProcessExceptionParam  implements CBBagKeys{

	
	public  CompanyDebtMasterProcessExceptionParam() {
		
	}
	public  CompanyDebtMasterProcessExceptionParam(long companyOID , String methodCode) {
		setCompanyOID(companyOID);
		setMethodCode(methodCode);
	}
	
	public  CompanyDebtMasterProcessExceptionParam(long companyOID ) {
		setCompanyOID(companyOID);
	}
	
	public  CompanyDebtMasterProcessExceptionParam(String  methodCode ) {
		setMethodCode(methodCode);
	}
	
	
	
	public CompanyDebtMasterProcessExceptionParam get() throws CBException {
		
		CompanyDebtMasterProcessExceptionParam masterExceptionPrm = null;
		

		DBSComopanyMasterProcessFileExceptionPrmPOM   pom  = DBSComopanyMasterProcessFileExceptionPrmPOM.newDBSComopanyMasterProcessFileExceptionPrmPOM();
		DBSComopanyMasterProcessFileExceptionPrmPOMData data = DBSComopanyMasterProcessFileExceptionPrmPOMData.newInstance();
		
		
		try {
			if(pom.readByCompanyOidAndMethodCodeActive(getCompanyOID(), getMethodCode(), 1)){
				
				data = pom.getDBSComopanyMasterProcessFileExceptionPrmPOMData();
				
				masterExceptionPrm = new CompanyDebtMasterProcessExceptionParam();

				setExceptionCode(data.exceptionCode);
				setExceptionDescription(data.exceptionDescription);
				setAdkExceptionDescription(data.adkDescription);
			}
			
			return masterExceptionPrm;
		
		} finally{
			if(pom != null)
				pom.close();
			
		}
		
	}
	
	public List<String> getAllCompanyException() throws CBException {
		
		List<String> masterExceptionPrmCompany = new ArrayList<String>();
		
		DBSComopanyMasterProcessFileExceptionPrmPOM   pom  = DBSComopanyMasterProcessFileExceptionPrmPOM.newDBSComopanyMasterProcessFileExceptionPrmPOM();
		DBSComopanyMasterProcessFileExceptionPrmPOMData data = DBSComopanyMasterProcessFileExceptionPrmPOMData.newInstance();
		
		
		try {
			
			pom.readByCompanyOIDAndActive(getCompanyOID(), 1);
			
		
			while (pom.next()){
				
				data = pom.getDBSComopanyMasterProcessFileExceptionPrmPOMData();
				
				masterExceptionPrmCompany.add(data.methodCode) ;
			}
			return masterExceptionPrmCompany;
		
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
	public String getAdkExceptionDescription() {
		return adkExceptionDescription;
	}
	public void setAdkExceptionDescription(String adkExceptionDescription) {
		this.adkExceptionDescription = adkExceptionDescription;
	}
	public String getMethodCode() {
		return methodCode;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}



	private long companyOID;
	private String exceptionCode;
	private String exceptionDescription;
	private boolean active;
	private String adkExceptionDescription;
	private String methodCode;
}
