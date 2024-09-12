package cb.ext.dbs.data;

import java.util.Vector;
import cb.ext.dbs.pom.DBSCompanyLimitFileEMailPOM;
import cb.ext.dbs.pom.DBSCompanyLimitFileEMailPOMData;
import cb.smg.general.utility.CBException;

public class CompanyLimitFileEMail {

	public Vector<CompanyLimitFileEMail> getCompanyLimitFileEMail(long companyOID)throws CBException{
		
		DBSCompanyLimitFileEMailPOM     pom     = DBSCompanyLimitFileEMailPOM.newDBSCompanyLimitFileEMailPOM();
		DBSCompanyLimitFileEMailPOMData data    = DBSCompanyLimitFileEMailPOMData.newInstance();
		
		CompanyLimitFileEMail CompanyLimitFileEMail = new CompanyLimitFileEMail();
		
		Vector<CompanyLimitFileEMail> CompanyLimitFileEMailVector = new Vector<CompanyLimitFileEMail>();
		
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyLimitFileEMailPOMData();
				
				CompanyLimitFileEMail = new CompanyLimitFileEMail();
				
				CompanyLimitFileEMail.setCompanyOID(companyOID);
				CompanyLimitFileEMail.setEmail(data.email);
				
				
				CompanyLimitFileEMailVector.add(CompanyLimitFileEMail);
				
			}
			
			
			return CompanyLimitFileEMailVector;
			
			
		}
		catch (CBException ex) {
			throw ex;
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public void deleteAllByCompanyOID(long companyOID) throws CBException {
		
		DBSCompanyLimitFileEMailPOM     pom  = DBSCompanyLimitFileEMailPOM.newDBSCompanyLimitFileEMailPOM();
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				pom.delete();
			}
			
			
		}
		catch (CBException ex) {
			throw ex;
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public long add() throws CBException {
		
		DBSCompanyLimitFileEMailPOM     pom  = DBSCompanyLimitFileEMailPOM.newDBSCompanyLimitFileEMailPOM();
		DBSCompanyLimitFileEMailPOMData data = DBSCompanyLimitFileEMailPOMData.newInstance();
		
		try {
			
			data.companyOID = getCompanyOID();
			data.email      = getEmail();
			
			
			pom.setDBSCompanyLimitFileEMailPOMData(data);
			pom.create();
			
			
			return data.oID;
			
		}
		catch (CBException ex) {
			throw ex;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	private long   companyOID;
	private String email;

}
