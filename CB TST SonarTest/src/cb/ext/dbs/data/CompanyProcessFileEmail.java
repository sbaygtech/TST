package cb.ext.dbs.data;

import java.util.Vector;

import cb.ext.dbs.pom.DBSCompanyProcessFileEMailPOM;
import cb.ext.dbs.pom.DBSCompanyProcessFileEMailPOMData;
import cb.smg.general.utility.CBException;

public class CompanyProcessFileEmail {

	
	public Vector<CompanyProcessFileEmail> getCompanyProcessFileEMail(long companyOID)throws CBException{
		
		DBSCompanyProcessFileEMailPOM   pom     = DBSCompanyProcessFileEMailPOM.newDBSCompanyProcessFileEMailPOM();
		DBSCompanyProcessFileEMailPOMData data  = DBSCompanyProcessFileEMailPOMData.newInstance();
		
		CompanyProcessFileEmail CompanyProcessFileEmail = new CompanyProcessFileEmail();
		
		Vector<CompanyProcessFileEmail> CompanyProcessFileEmailVector = new Vector<CompanyProcessFileEmail>();
		
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyProcessFileEMailPOMData();
				
				CompanyProcessFileEmail = new CompanyProcessFileEmail();
				
				CompanyProcessFileEmail.setCompanyOID(companyOID);
				CompanyProcessFileEmail.setEmail(data.emailAddress);
				
				
				CompanyProcessFileEmailVector.add(CompanyProcessFileEmail);
				
			}
			
			
			return CompanyProcessFileEmailVector;
			
			
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
			
			DBSCompanyProcessFileEMailPOM     pom  = DBSCompanyProcessFileEMailPOM.newDBSCompanyProcessFileEMailPOM();
			
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
		
		DBSCompanyProcessFileEMailPOM   pom     = DBSCompanyProcessFileEMailPOM.newDBSCompanyProcessFileEMailPOM();
		DBSCompanyProcessFileEMailPOMData data  = DBSCompanyProcessFileEMailPOMData.newInstance();
		
		
		try {
			
			data.companyOID   = getCompanyOID();
			data.emailAddress = getEmail();
			
			
			pom.setDBSCompanyProcessFileEMailPOMData(data);
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
