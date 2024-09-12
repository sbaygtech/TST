package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyParameterPOM;
import cb.ext.dbs.pom.DBSCompanyParameterPOMData;
import cb.smg.general.utility.CBException;

public class CompanyParameters {
		
	public String findCompanyParameterValue() throws CBException {
		
		DBSCompanyParameterPOM pom = DBSCompanyParameterPOM.newDBSCompanyParameterPOM();
		DBSCompanyParameterPOMData  data = DBSCompanyParameterPOMData.newInstance();
		
		try {
			if(pom.readCompanyParameterValueByKey(getCompanyOID(), getKey())){
				data = pom.getDBSCompanyParameterPOMData();
				return data.value;
			}			
		} 
		finally{
			if(pom != null){
				pom.close();
			}
		}
		return null;
	}
	
	
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	private long companyOID ;
	private String key;
	private String value;
	
}
