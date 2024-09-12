package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileRecordTypeParamPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileRecordTypeParamPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileRecordTypeParam {
	
	public  CompanyDebtFileRecordTypeParam() {
		
	}
	
	public  CompanyDebtFileRecordTypeParam(long companyOID, String companyRecordType) {
		setCompanyOID(companyOID);
		setCompanyRecordType(companyRecordType);
	}
	
	public  String  getRecordType() throws CBException {
		String recordType = "" ;
		DBSCompanyDebtFileRecordTypeParamPOM       pom = DBSCompanyDebtFileRecordTypeParamPOM.newDBSCompanyDebtFileRecordTypeParamPOM();
		DBSCompanyDebtFileRecordTypeParamPOMData  data = DBSCompanyDebtFileRecordTypeParamPOMData.newInstance();
		
		try {
			
			if(pom.readByCompanyOIDAndRecordType(getCompanyOID(), getCompanyRecordType())){
				
				data = pom.getDBSCompanyDebtFileRecordTypeParamPOMData();
				
				recordType = data.mapRecordType;
				
			}else {
				recordType = getCompanyRecordType();
			}
			
			return recordType;
			
		} finally{
			if(pom != null){
				pom.close();
			}
		}
		

	}
	
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public String getMappingRecordType() {
		return mappingRecordType;
	}
	public void setMappingRecordType(String mappingRecordType) {
		this.mappingRecordType = mappingRecordType;
	}
	public String getCompanyRecordType() {
		return companyRecordType;
	}
	public void setCompanyRecordType(String companyRecordType) {
		this.companyRecordType = companyRecordType;
	}
	private long companyOID ;
	private String mappingRecordType ;
	private String companyRecordType ;
	
}
