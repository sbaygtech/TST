package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyProcessFileParamPOM;
import cb.ext.dbs.pom.DBSCompanyProcessFileParamPOMData;
import cb.smg.general.utility.CBException;

	public class CompanyProcessFileParam {
	
		
		private static CompanyProcessFileParam setFields(DBSCompanyProcessFileParamPOMData data) {
	
			CompanyProcessFileParam companyProcessFileParam = new CompanyProcessFileParam();
	
			companyProcessFileParam.setCompanyOID(data.companyOID);
			companyProcessFileParam.setFileEnabled(data.fileEnabled);
			companyProcessFileParam.setMailingEnabled(data.mailingEnabled);
			companyProcessFileParam.setProcessFileName(data.processFileName);
	
			return companyProcessFileParam;
			
	}
		
		public static CompanyProcessFileParam getCompanyProcessFileParam(long companyOID) throws CBException {

			DBSCompanyProcessFileParamPOM     pom  = DBSCompanyProcessFileParamPOM.newDBSCompanyProcessFileParamPOM();
			DBSCompanyProcessFileParamPOMData data = DBSCompanyProcessFileParamPOMData.newInstance();

			try {

				if (pom.readByCompanyOID(companyOID)) {

					data = pom.getDBSCompanyProcessFileParamPOMData();
					CompanyProcessFileParam companyProcessFileParam = setFields(data);

					return companyProcessFileParam;
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
			
			DBSCompanyProcessFileParamPOM     pom  = DBSCompanyProcessFileParamPOM.newDBSCompanyProcessFileParamPOM();
			DBSCompanyProcessFileParamPOMData data = DBSCompanyProcessFileParamPOMData.newInstance();
			
			try {
				
				data = setPOMData(data);
				
				pom.setDBSCompanyProcessFileParamPOMData(data);
				pom.create();

				return data.oID;
				
			}
			finally {
				
				if (pom!=null) {
					pom.close();
				}
			}
			
		}
		
		
		private DBSCompanyProcessFileParamPOMData  setPOMData(DBSCompanyProcessFileParamPOMData data) {
			
			data.companyOID      = getCompanyOID();
			data.fileEnabled     = isFileEnabled();
			data.mailingEnabled  = isMailingEnabled();
			data.processFileName = getProcessFileName();
			
			return data;
			
		}
		
		public void updateByCompanyOID(long companyOID) throws CBException {
			
			DBSCompanyProcessFileParamPOM     pom  = DBSCompanyProcessFileParamPOM.newDBSCompanyProcessFileParamPOM();
			DBSCompanyProcessFileParamPOMData data = DBSCompanyProcessFileParamPOMData.newInstance();
			
			try {
				
				if (pom.readByCompanyOID(companyOID)) {
					
					data = pom.getDBSCompanyProcessFileParamPOMData();
					
					data = setPOMData(data);
					
					pom.setDBSCompanyProcessFileParamPOMData(data);
					pom.update();
					
					
					deleteProcessFileEMail(companyOID, data.mailingEnabled);
					
				}
			
				
			}
			finally {
				
				if (pom!=null) {
					pom.close();
				}
			}
			
		}
		
		
		public void deleteByCompanyOID(long companyOID) throws CBException {
			
			DBSCompanyProcessFileParamPOM     pom  = DBSCompanyProcessFileParamPOM.newDBSCompanyProcessFileParamPOM();
			DBSCompanyProcessFileParamPOMData data = DBSCompanyProcessFileParamPOMData.newInstance();
			
			try {
				
				if (pom.readByCompanyOID(companyOID)) {
					
					data = pom.getDBSCompanyProcessFileParamPOMData();
					pom.delete();
					
					
					deleteProcessFileEMail(companyOID, data.mailingEnabled);
				}
			
				
			}
			finally {
				
				if (pom!=null) {
					pom.close();
				}
			}
			
		}
		
		
		private void deleteProcessFileEMail(long companyOID, boolean mailingEnabled) throws CBException {
			
			if (mailingEnabled) {
				
				CompanyProcessFileEmail companyProcessFileEmail = new CompanyProcessFileEmail();
				companyProcessFileEmail.deleteAllByCompanyOID(companyOID);
			}
			
		}
		
	public boolean isMailingEnabled() {
		return mailingEnabled;
	}
	public void setMailingEnabled(boolean mailingEnabled) {
		this.mailingEnabled = mailingEnabled;
	}
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public boolean isFileEnabled() {
		return fileEnabled;
	}
	public void setFileEnabled(boolean fileEnabled) {
		this.fileEnabled = fileEnabled;
	}	
	public String getProcessFileName() {
		return processFileName;
	}

	public void setProcessFileName(String processFileName) {
		this.processFileName = processFileName;
	}

	private String processFileName;
	private boolean mailingEnabled;
	private boolean fileEnabled;
	private long companyOID;
}
