package cb.ext.dbs.data;

import java.util.ArrayList;
import java.util.List;

import cb.ext.dbs.pom.DBSCompanyDebtFileResultParamPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileResultParamPOMData;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileResultParam {

	public CompanyDebtFileResultParam() {
	}
	
	public boolean isDebtFileResultParamSendable() {
		
		return  ( isTextFileEnabled()) ;
		
	}

	private CompanyDebtFileResultParam setFields(DBSCompanyDebtFileResultParamPOMData data) {

		CompanyDebtFileResultParam companyDebtFileResultParam = new CompanyDebtFileResultParam();

		companyDebtFileResultParam.setCompanyOID(data.companyOID);
		companyDebtFileResultParam.setTextFileEnabled(data.textFileEnabled);
		companyDebtFileResultParam.setMailingEnabled(data.mailingEnabled);
		companyDebtFileResultParam.setFileCompanyCode(data.fileCompanyCode);
		companyDebtFileResultParam.setTextFileType(data.textFileType);
		companyDebtFileResultParam.setTextFileName(data.textFileName);
		companyDebtFileResultParam.setSendWaitingDebtList(data.sendDebtFileList);
		
		return companyDebtFileResultParam;

	}

	public CompanyDebtFileResultParam getCompanyDebtFileResultParam(long companyOID) throws CBException {

		DBSCompanyDebtFileResultParamPOM pom = DBSCompanyDebtFileResultParamPOM.newDBSCompanyDebtFileResultParamPOM();
		DBSCompanyDebtFileResultParamPOMData data = DBSCompanyDebtFileResultParamPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyDebtFileResultParamPOMData();
				CompanyDebtFileResultParam companyDebtFileResultParam = setFields(data);

				return companyDebtFileResultParam;
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
	
	public List<CompanyDebtFileResultParam> getCompanyDebtFileResultToSendWaitingDebtList() throws CBException {
		List<CompanyDebtFileResultParam> list = new ArrayList<CompanyDebtFileResultParam>();
		DBSCompanyDebtFileResultParamPOM pom = DBSCompanyDebtFileResultParamPOM.newDBSCompanyDebtFileResultParamPOM();
		DBSCompanyDebtFileResultParamPOMData data = DBSCompanyDebtFileResultParamPOMData.newInstance();
			
		try {
			pom.readCompaniesToSendWaitingDebtList();
			while(pom.next()) {
				data = pom.getDBSCompanyDebtFileResultParamPOMData();
				CompanyDebtFileResultParam companyDebtFileResultParam = setFields(data);
				list.add(companyDebtFileResultParam);
			}
		} finally {
			if (pom != null) {
				pom.close();
			}
		}
		
		return list;
	}


	public long add() throws CBException {

		DBSCompanyDebtFileResultParamPOM pom = DBSCompanyDebtFileResultParamPOM.newDBSCompanyDebtFileResultParamPOM();
		DBSCompanyDebtFileResultParamPOMData data = DBSCompanyDebtFileResultParamPOMData.newInstance();

		try {

			data = setPOMData(data);

			pom.setDBSCompanyDebtFileResultParamPOMData(data);
			pom.create();

			return data.oID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private DBSCompanyDebtFileResultParamPOMData setPOMData(DBSCompanyDebtFileResultParamPOMData data) {


		data.companyOID      = getCompanyOID();
		data.textFileEnabled = isTextFileEnabled();
		data.mailingEnabled  = isMailingEnabled();
		data.fileCompanyCode = getFileCompanyCode();
		data.textFileType    = getTextFileType();
		data.textFileName    = getTextFileName();
		data.sendDebtFileList= isSendWaitingDebtList();
		return data;
	}

	public long updateByCompanyOID(long companyOID) throws CBException {

		DBSCompanyDebtFileResultParamPOM     pom  = DBSCompanyDebtFileResultParamPOM.newDBSCompanyDebtFileResultParamPOM();
		DBSCompanyDebtFileResultParamPOMData data = DBSCompanyDebtFileResultParamPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyDebtFileResultParamPOMData();
				
				data = setPOMData(data);


				pom.setDBSCompanyDebtFileResultParamPOMData(data);
				pom.update();

				deleteFileResultEMail(companyOID, data.mailingEnabled);

			}

			return companyOID;

		}finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public void deleteByCompanyOID(long companyOID) throws CBException {

		DBSCompanyDebtFileResultParamPOM pom = DBSCompanyDebtFileResultParamPOM.newDBSCompanyDebtFileResultParamPOM();
		DBSCompanyDebtFileResultParamPOMData data = DBSCompanyDebtFileResultParamPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {
				
				data = pom.getDBSCompanyDebtFileResultParamPOMData();
				pom.delete();

				deleteFileResultEMail(companyOID, data.mailingEnabled);
				
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private void deleteFileResultEMail(long companyOID, boolean mailingEnabled) throws CBException {
		
		if (mailingEnabled) {

			CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();
			companyDebtFileResultEMail.deleteAllByCompanyOID(companyOID);
		}
	}

	public long getCompanyOID() {
		return companyOID;
	}

	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}

	public boolean isTextFileEnabled() {
		return textFileEnabled;
	}

	public void setTextFileEnabled(boolean textFileEnabled) {
		this.textFileEnabled = textFileEnabled;
	}

	public boolean isMailingEnabled() {
		return mailingEnabled;
	}

	public void setMailingEnabled(boolean mailingEnabled) {
		this.mailingEnabled = mailingEnabled;
	}

	public String getFileCompanyCode() {
		return fileCompanyCode;
	}

	public void setFileCompanyCode(String fileCompanyCode) {
		this.fileCompanyCode = fileCompanyCode;
	}
	public String getTextFileType() {
		return textFileType;
	}

	public void setTextFileType(String textFileType) {
		this.textFileType = textFileType;
	}
	public String getTextFileName() {
		return textFileName;
	}

	public void setTextFileName(String textFileName) {
		this.textFileName = textFileName;
	}

	public boolean isSendWaitingDebtList() {
		return sendWaitingDebtList;
	}

	public void setSendWaitingDebtList(boolean sendWaitingDebtList) {
		this.sendWaitingDebtList = sendWaitingDebtList;
	}

	public long companyOID;
	private boolean textFileEnabled;
	private boolean mailingEnabled;
	private String fileCompanyCode;
	private String textFileType;
	private String textFileName;
	private boolean sendWaitingDebtList;
}
