package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSDealerUploadDocPOM;
import cb.ext.dbs.pom.DBSDealerUploadDocPOMData;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBTime;

public class DealerDocument {
	
	public DealerDocument() {
	}
	
	public DealerDocument(DBSDealerUploadDocPOMData data) {
		setFields(data);
	}

	public long add() throws CBException {

		DBSDealerUploadDocPOM pom = DBSDealerUploadDocPOM.newDBSDealerUploadDocPOM();
		DBSDealerUploadDocPOMData data = DBSDealerUploadDocPOMData.newInstance();

		try {

			setPomData(data);

			pom.setDBSDealerUploadDocPOMData(data);
			pom.create();

			return data.oID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public void deleteAllByDealerOID(long dealerOID) throws CBException {

		DBSDealerUploadDocPOM pom  = DBSDealerUploadDocPOM.newDBSDealerUploadDocPOM();

		try {
			
			pom.readByDealerOID(dealerOID);

			while ( pom.next() ) {
				pom.delete();
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private void setPomData(DBSDealerUploadDocPOMData data) {

		data.oID = getOID();
		data.documentID = getDocumentID();
		data.explanation = getExplanation();
		data.fileName = getFileName();
		data.uploadDate = getUploadDate();
		data.uploadTime = getUploadTime();
		data.uploader = getUploader();
		data.dealerOID = getDealerOID();

	}

	private void setFields(DBSDealerUploadDocPOMData data) {

		setOID(data.oID);
		setDocumentID(data.documentID);
		setExplanation(data.explanation);
		setFileName(data.fileName);
		setUploadDate(data.uploadDate);
		setUploadTime(data.uploadTime);
		setUploader(data.uploader);
		setDealerOID(data.dealerOID);
		


	}

	public long getDocumentID() {
		return documentID;
	}

	public void setDocumentID(long documentID) {
		this.documentID = documentID;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CBDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(CBDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public CBTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(CBTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public long getDealerOID() {
		return dealerOID;
	}

	public void setDealerOID(long dealerOID) {
		this.dealerOID = dealerOID;
	}

	public long getOID() {
		return oid;
	}

	public void setOID(long oid) {
		this.oid = oid;
	}

	private long documentID;
	private String explanation;
	private String fileName;
	private CBDate uploadDate;
	private CBTime uploadTime;
	private String uploader;
	private long dealerOID;
	
	private long    oid;

}
