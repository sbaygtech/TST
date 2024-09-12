package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileDefinitionPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileDefinitionPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileTypeParam {

	
	public long updateByCompanyOID(long companyOID) throws CBException {

		DBSCompanyDebtFileDefinitionPOM     pom  = DBSCompanyDebtFileDefinitionPOM.newDBSCompanyDebtFileDefinitionPOM();
		DBSCompanyDebtFileDefinitionPOMData data = DBSCompanyDebtFileDefinitionPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyDebtFileDefinitionPOMData();
				
				data = setPOMData(data);


				pom.setDBSCompanyDebtFileDefinitionPOMData(data);
				pom.update();


			}

			return companyOID;

		}finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	private CompanyDebtFileTypeParam setFields(DBSCompanyDebtFileDefinitionPOMData data) {

		CompanyDebtFileTypeParam companyDebtFileTypeParam = new CompanyDebtFileTypeParam();

		companyDebtFileTypeParam.setCompanyOID(data.companyOID);
		companyDebtFileTypeParam.setCompanyDebtFileTypeCode(data.fileTypeCode);


		return companyDebtFileTypeParam;

	}
	
	public CompanyDebtFileTypeParam getCompanyDebtFileTypeParam(long companyOID) throws CBException {

		DBSCompanyDebtFileDefinitionPOM     pom  = DBSCompanyDebtFileDefinitionPOM.newDBSCompanyDebtFileDefinitionPOM();
		DBSCompanyDebtFileDefinitionPOMData data = DBSCompanyDebtFileDefinitionPOMData.newInstance();;

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyDebtFileDefinitionPOMData();
				CompanyDebtFileTypeParam companyDebtFileTypeParam = setFields(data);

				return companyDebtFileTypeParam;
			}
			else {
				return null;
			}
			
		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}
	
	public long add() throws CBException {

		DBSCompanyDebtFileDefinitionPOM pom = DBSCompanyDebtFileDefinitionPOM.newDBSCompanyDebtFileDefinitionPOM();
		DBSCompanyDebtFileDefinitionPOMData data = DBSCompanyDebtFileDefinitionPOMData.newInstance();

		try {

			data = setPOMData(data);

			pom.setDBSCompanyDebtFileDefinitionPOMData(data);
			pom.create();

			return data.oID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private DBSCompanyDebtFileDefinitionPOMData setPOMData(DBSCompanyDebtFileDefinitionPOMData data) {


		data.companyOID = getCompanyOID();
		data.fileTypeCode = getCompanyDebtFileTypeCode();
	

		return data;
	}
	
	public void deleteByCompanyOID(long companyOID) throws CBException {
		
		DBSCompanyDebtFileDefinitionPOM     pom  = DBSCompanyDebtFileDefinitionPOM.newDBSCompanyDebtFileDefinitionPOM();
		DBSCompanyDebtFileDefinitionPOMData data = DBSCompanyDebtFileDefinitionPOMData.newInstance();
		
		try {
			
			if (pom.readByCompanyOID(companyOID)) {
				
				data = pom.getDBSCompanyDebtFileDefinitionPOMData();
				pom.delete();
		
			}
		
			
		}
		finally {
			
			if (pom!=null) {
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
	public String getCompanyDebtFileTypeCode() {
		return companyDebtFileTypeCode;
	}
	public void setCompanyDebtFileTypeCode(String companyDebtFileTypeCode) {
		this.companyDebtFileTypeCode = companyDebtFileTypeCode;
	}
	public long companyOID;
	private String companyDebtFileTypeCode;
}
