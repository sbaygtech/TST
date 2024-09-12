package cb.ext.dbs.data;

import java.util.Vector;
import cb.ext.dbs.pom.DBSCompanyDebtFileResultEMailPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileResultEMailPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileResultEMail {

	public Vector<CompanyDebtFileResultEMail> getCompanyDebtFileResultEMail(long companyOID)throws CBException{
		
		DBSCompanyDebtFileResultEMailPOM     pom     = DBSCompanyDebtFileResultEMailPOM.newDBSCompanyDebtFileResultEMailPOM();
		DBSCompanyDebtFileResultEMailPOMData data    = DBSCompanyDebtFileResultEMailPOMData.newInstance();
		
		CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();
		
		Vector<CompanyDebtFileResultEMail> companyDebtFileResultEMailVector = new Vector<CompanyDebtFileResultEMail>();
		
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyDebtFileResultEMailPOMData();
				
				companyDebtFileResultEMail = new CompanyDebtFileResultEMail();
				
				companyDebtFileResultEMail.setCompanyOID(companyOID);
				companyDebtFileResultEMail.setEmail(data.email);
				
				
				companyDebtFileResultEMailVector.add(companyDebtFileResultEMail);
				
			}
			
			
			return companyDebtFileResultEMailVector;
			
			
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
		
		DBSCompanyDebtFileResultEMailPOM     pom  = DBSCompanyDebtFileResultEMailPOM.newDBSCompanyDebtFileResultEMailPOM();
		
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
		
		DBSCompanyDebtFileResultEMailPOM     pom  = DBSCompanyDebtFileResultEMailPOM.newDBSCompanyDebtFileResultEMailPOM();
		DBSCompanyDebtFileResultEMailPOMData data = DBSCompanyDebtFileResultEMailPOMData.newInstance();
		
		try {
			
			data.companyOID = getCompanyOID();
			data.email      = getEmail();
			
			
			pom.setDBSCompanyDebtFileResultEMailPOMData(data);
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
