package cb.ext.dbs.generic.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileDetailExceptionPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileDetailExceptionPOMData;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileDetailException implements CBBagKeys {
	public CompanyDebtFileDetailException() {
		
	}
	
	public CompanyDebtFileDetailException(long detailOID ) {
		setDetailOID(detailOID);
	}
	
	public CompanyDebtFileDetailException(long detailOID, String exceptionCode, String exceptionDescription ) {
		setDetailOID(detailOID);
		setExceptionCode(exceptionCode);
		setExceptionDescription(exceptionDescription);
	}
	
	
	public void add( ) throws CBException {
			
			DBSCompanyDebtFileDetailExceptionPOM      pom = DBSCompanyDebtFileDetailExceptionPOM.newDBSCompanyDebtFileDetailExceptionPOM();
			DBSCompanyDebtFileDetailExceptionPOMData data = DBSCompanyDebtFileDetailExceptionPOMData.newInstance();
	
			try {
	
				data = setPOMData(data);
	
				pom.setDBSCompanyDebtFileDetailExceptionPOMData(data);
				pom.create();
				
			
			}
	
			finally {
				if (pom != null) {
					pom.close();
				}
			}
			
	}
	
	private DBSCompanyDebtFileDetailExceptionPOMData setPOMData(DBSCompanyDebtFileDetailExceptionPOMData data) {
		data.detailOID            = getDetailOID();
		data.exceptionCode        = getExceptionCode();
		data.exceptionDescription = getExceptionDescription();

		return data;
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
	public long getDetailOID() {
		return detailOID;
	}

	public void setDetailOID(long detailOID) {
		this.detailOID = detailOID;
	}
	private long detailOID ;
	private long oid ;
	private String exceptionCode;
	private String exceptionDescription;

}
