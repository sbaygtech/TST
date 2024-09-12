package cb.ext.dbs.tosyali.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileHeaderPOM;
import static cb.ext.dbs.constants.GeneralConstants.COMMA;

import cb.ext.dbs.pom.DBSCompanyDebtFileHeaderPOMData;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBTime;

public class CompanyDebtFileHeader {
	
	public static CompanyDebtFileHeader get(long oid) throws CBException {
		
		DBSCompanyDebtFileHeaderPOM pom = DBSCompanyDebtFileHeaderPOM.newDBSCompanyDebtFileHeaderPOM();
		DBSCompanyDebtFileHeaderPOMData data = DBSCompanyDebtFileHeaderPOMData.newInstance();


		try {
			
			CompanyDebtFileHeader debtFileHeader = null;

			if (pom.readByPrimaryKey(oid)) {
				
				debtFileHeader = new CompanyDebtFileHeader();

				data = pom.getDBSCompanyDebtFileHeaderPOMData();
				
				debtFileHeader.setInsertDate(data.insertDate);
				debtFileHeader.setInsertTime(data.insertTime);
				debtFileHeader.setFileName(data.fileName);
				debtFileHeader.setFileSequenceNumber(data.fileSequenceNumber);
				debtFileHeader.setFileDate(data.fileDate);
				debtFileHeader.setCompanyCode(data.companyCode);
				debtFileHeader.setDocumentID(data.documentID);
				debtFileHeader.setMailID(data.mailID);
				debtFileHeader.setCompanyOID(data.companyOID);
				debtFileHeader.setFileCurrency(data.fileCurrency);
				debtFileHeader.setOid(data.oID);

				

			
			}
			
			return debtFileHeader;

		}
		finally {

			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public void updateForMailDocID() throws CBException{
		

		DBSCompanyDebtFileHeaderPOM pom = DBSCompanyDebtFileHeaderPOM.newDBSCompanyDebtFileHeaderPOM();
		DBSCompanyDebtFileHeaderPOMData data = DBSCompanyDebtFileHeaderPOMData.newInstance();


		try {

			if (pom.readByPrimaryKey(getOid())) {

				data = pom.getDBSCompanyDebtFileHeaderPOMData();

				data.documentID = getDocumentID();
				data.mailID = getMailID();

				pom.setDBSCompanyDebtFileHeaderPOMData(data);
				pom.update();
			}

		}
		finally {

			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public long add() throws CBException{
		
		DBSCompanyDebtFileHeaderPOM pom = DBSCompanyDebtFileHeaderPOM.newDBSCompanyDebtFileHeaderPOM();
		DBSCompanyDebtFileHeaderPOMData data = DBSCompanyDebtFileHeaderPOMData.newInstance();
		
		try {
			
			
			data.insertDate = getInsertDate();
			data.insertTime = getInsertTime();
			data.fileName = getFileName();
			data.fileSequenceNumber = getFileSequenceNumber();
			data.fileDate = getFileDate();
			data.companyCode = getCompanyCode();
			data.companyOID = getCompanyOID();
			data.fileCurrency = getFileCurrency();

			data.documentID = getDocumentID();
			data.mailID = getMailID();
			
			

			pom.setDBSCompanyDebtFileHeaderPOMData(data);
			pom.create();
			
			return data.oID;
			
		}
		finally {
			if (pom != null) {
				pom.close();
			}
		}
		
	}
	
	public String toString() {
		
		return getInsertDate().toDBString() + COMMA + 
		       getInsertTime() + COMMA + 
		       getFileName() + COMMA +
			   getFileSequenceNumber() + COMMA + 
			   getFileDate() + COMMA + 
			   getCompanyCode() +  COMMA + 
			   getDocumentID() + COMMA + 
			   getMailID() + COMMA +
			   getCompanyOID() + COMMA + 
			   getFileCurrency() + COMMA + 
			   getOid();
		
		
	}
	
	public CompanyDebtFileHeader fromString(String headerString) throws CBException{
		
		
		
		CompanyDebtFileHeader debtFileHeader = null;
		
		if (!headerString.equals("")) {
			
			debtFileHeader = new CompanyDebtFileHeader();
		
			String header[]  = headerString.split(COMMA);
			
			
			debtFileHeader.setInsertDate(CBDateFactory.newCBDate(header[0]));
			debtFileHeader.setInsertTime(new CBTime(header[1]));
			debtFileHeader.setFileName(header[2]);
			debtFileHeader.setFileSequenceNumber(header[3]);
			debtFileHeader.setFileDate(header[4]);
			debtFileHeader.setCompanyCode(header[5]);
			debtFileHeader.setDocumentID(header[6]);
			debtFileHeader.setMailID(header[7]);
			debtFileHeader.setCompanyOID(Long.parseLong(header[8]));
			debtFileHeader.setFileCurrency(header[9]);
			debtFileHeader.setOid(Long.parseLong(header[10]));
			
		}
		
		return debtFileHeader;
		
	}

	public CBDate getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(CBDate insertDate) {
		this.insertDate = insertDate;
	}
	public CBTime getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(CBTime insertTime) {
		this.insertTime = insertTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSequenceNumber() {
		return fileSequenceNumber;
	}
	public void setFileSequenceNumber(String fileSequenceNumber) {
		this.fileSequenceNumber = fileSequenceNumber;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getDocumentID() {
		return documentID;
	}
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}
	public long getCompanyOID() {
		return companyOID;
	}
	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}
	public String getFileCurrency() {
		return fileCurrency;
	}
	public void setFileCurrency(String fileCurrency) {
		this.fileCurrency = fileCurrency;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}

	private CBDate insertDate;
	private CBTime insertTime;
	private String fileName;
	private String fileSequenceNumber;
	private String fileDate;
	private String companyCode;
	private String documentID;
	private String mailID;
	private long companyOID;
	private String fileCurrency;
	private long   oid;
	
	
	

}
