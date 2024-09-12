package cb.ext.dbs.generic.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileMasterExceptionPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterExceptionPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileMasterException {

	public CompanyDebtFileMasterException() {
		
	}
	
	public CompanyDebtFileMasterException(long masterOID ) {
		setMasterOID(masterOID);
	}
	
	
	public void add( ) throws CBException {
			
			DBSCompanyDebtFileMasterExceptionPOM     pom  = DBSCompanyDebtFileMasterExceptionPOM.newDBSCompanyDebtFileMasterExceptionPOM();
			DBSCompanyDebtFileMasterExceptionPOMData data = DBSCompanyDebtFileMasterExceptionPOMData.newInstance();
	
			try {
	
				data = setPOMData(data);
	
				pom.setDBSCompanyDebtFileMasterExceptionPOMData(data);
				pom.create();
				
			
			}
	
			finally {
				if (pom != null) {
					pom.close();
				}
			}
			
	}
	
	private DBSCompanyDebtFileMasterExceptionPOMData setPOMData(DBSCompanyDebtFileMasterExceptionPOMData data) {
		data.masterOID            = getMasterOID();
		data.exceptionCode        = getExceptionCode();
		data.exceptionDescription = getExceptionDescription();

		return data;
	}
	
	public long getMasterOID() {
		return masterOID;
	}
	public void setMasterOID(long masterOID) {
		this.masterOID = masterOID;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
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
	private long masterOID ;
	private long oid ;
	private String exceptionCode;
	private String exceptionDescription;
	
}
