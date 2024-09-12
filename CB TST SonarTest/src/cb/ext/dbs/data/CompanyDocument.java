package cb.ext.dbs.data;

import java.util.Vector;

import cb.ext.dbs.pom.DBSCompanyDocumentPOM;
import cb.ext.dbs.pom.DBSCompanyDocumentPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDocument {
	private long companyOid;
	private int documentCode;
	
	public void addCompanyDocument() throws CBException {
		DBSCompanyDocumentPOM pom = DBSCompanyDocumentPOM.newDBSCompanyDocumentPOM();
		DBSCompanyDocumentPOMData data = DBSCompanyDocumentPOMData.newInstance();
		
		try{
			data.companyOid = getCompanyOid();
			data.documentCode = getDocumentCode();
			
			pom.setDBSCompanyDocumentPOMData(data);
			
			pom.create();
			
		}
		finally{
			if(pom != null) {
				pom.close();
			}
		}
	}
	
	public Vector<CompanyDocument> getCompanyDocumentsByCompanyOid(long companyOid) throws CBException {
		DBSCompanyDocumentPOM pom = DBSCompanyDocumentPOM.newDBSCompanyDocumentPOM();
		DBSCompanyDocumentPOMData data = DBSCompanyDocumentPOMData.newInstance();
		
		Vector<CompanyDocument> companyDocumentVector = new Vector<CompanyDocument>();
		CompanyDocument companyDocument;
		try{
			pom.readCompanyDocuments(companyOid);
			while(pom.next()) {
				data = pom.getDBSCompanyDocumentPOMData();
				companyDocument = new CompanyDocument();
				companyDocument.setCompanyOid(data.companyOid);
				companyDocument.setDocumentCode(data.documentCode);
				
				companyDocumentVector.add(companyDocument);
			}
		}
		finally {
			if(pom != null) {
				pom.close();
			}
		}
		
		return companyDocumentVector;
	}
	
	public void deleteByCompanyOid(long companyOid) throws CBException {
		DBSCompanyDocumentPOM pom = DBSCompanyDocumentPOM.newDBSCompanyDocumentPOM();
		
		try{
			pom.readCompanyDocuments(companyOid);
			
			while(pom.next()) {
				pom.delete();
			}
		}
		finally{
			if(pom != null) {
				pom.close();
			}
		}
	}

	public long getCompanyOid() {
		return companyOid;
	}

	public void setCompanyOid(long companyOid) {
		this.companyOid = companyOid;
	}

	public int getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(int documentCode) {
		this.documentCode = documentCode;
	}

}
