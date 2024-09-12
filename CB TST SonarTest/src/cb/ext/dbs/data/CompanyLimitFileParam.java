package cb.ext.dbs.data;

import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.pom.DBSCompanyLimitFileParamPOM;
import cb.ext.dbs.pom.DBSCompanyLimitFileParamPOMData;
import cb.smg.general.utility.CBException;

public class CompanyLimitFileParam {

	private static CompanyLimitFileParam setFields(DBSCompanyLimitFileParamPOMData data) {

		CompanyLimitFileParam CompanyLimitFileParam = new CompanyLimitFileParam();
		

		CompanyLimitFileParam.setCompanyOID(data.companyOID);
		CompanyLimitFileParam.setTextFileEnabled(data.textFileEnabled);
		CompanyLimitFileParam.setMailingEnabled(data.mailingEnabled);
		CompanyLimitFileParam.setDealerDefSentMail(data.dealerDefSentMail);
		CompanyLimitFileParam.setAccountOption(LimitAccountOption.fromString(String.valueOf(data.accountOption)));
		CompanyLimitFileParam.setLimitFileName(data.limitFileName);

		return CompanyLimitFileParam;

	}

	public static CompanyLimitFileParam getCompanyLimitFileParam(long companyOID) throws CBException {

		DBSCompanyLimitFileParamPOM pom = DBSCompanyLimitFileParamPOM.newDBSCompanyLimitFileParamPOM();
		DBSCompanyLimitFileParamPOMData data = DBSCompanyLimitFileParamPOMData.newInstance();
		

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyLimitFileParamPOMData();
				CompanyLimitFileParam CompanyLimitFileParam = setFields(data);

				return CompanyLimitFileParam;
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

		DBSCompanyLimitFileParamPOM pom = DBSCompanyLimitFileParamPOM.newDBSCompanyLimitFileParamPOM();
		DBSCompanyLimitFileParamPOMData data = DBSCompanyLimitFileParamPOMData.newInstance();

		try {

			data = setPOMData(data);

			pom.setDBSCompanyLimitFileParamPOMData(data);
			pom.create();

			return data.oID;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private DBSCompanyLimitFileParamPOMData setPOMData(DBSCompanyLimitFileParamPOMData data) {

		data.companyOID        = getCompanyOID();
		data.textFileEnabled   = isTextFileEnabled();
		data.mailingEnabled    = isMailingEnabled();
		data.dealerDefSentMail = isDealerDefSentMail();
		data.accountOption     = Integer.valueOf(getAccountOption().value());
		data.limitFileName     = getLimitFileName();

		return data;

	}

	public void updateByCompanyOID(long companyOID) throws CBException {

		DBSCompanyLimitFileParamPOM pom = DBSCompanyLimitFileParamPOM.newDBSCompanyLimitFileParamPOM();
		DBSCompanyLimitFileParamPOMData data = DBSCompanyLimitFileParamPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyLimitFileParamPOMData();

				data = setPOMData(data);

				pom.setDBSCompanyLimitFileParamPOMData(data);
				pom.update();

				deleteLimitFileEMail(companyOID, data.mailingEnabled);

			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	private void deleteLimitFileEMail(long companyOID, boolean mailingEnabled) throws CBException {

		if (mailingEnabled) {

			CompanyLimitFileEMail companyLimitFileEMail = new CompanyLimitFileEMail();
			companyLimitFileEMail.deleteAllByCompanyOID(companyOID);
		}

	}

	public void deleteByCompanyOID(long companyOID) throws CBException {

		DBSCompanyLimitFileParamPOM pom = DBSCompanyLimitFileParamPOM.newDBSCompanyLimitFileParamPOM();
		DBSCompanyLimitFileParamPOMData data = DBSCompanyLimitFileParamPOMData.newInstance();

		try {

			if (pom.readByCompanyOID(companyOID)) {

				data = pom.getDBSCompanyLimitFileParamPOMData();
				pom.delete();

				deleteLimitFileEMail(companyOID, data.mailingEnabled);
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	public boolean isMailingEnabled() {
		return mailingEnabled;
	}

	public void setMailingEnabled(boolean mailingEnabled) {
		this.mailingEnabled = mailingEnabled;
	}

	public boolean isDealerDefSentMail() {
		return dealerDefSentMail;
	}

	public void setDealerDefSentMail(boolean dealerDefSentMail) {
		this.dealerDefSentMail = dealerDefSentMail;
	}

	public boolean isTextFileEnabled() {
		return textFileEnabled;
	}

	public void setTextFileEnabled(boolean textFileEnabled) {
		this.textFileEnabled = textFileEnabled;
	}

	public long getCompanyOID() {
		return companyOID;
	}

	public void setCompanyOID(long companyOID) {
		this.companyOID = companyOID;
	}

	public LimitAccountOption getAccountOption() {
		return accountOption;
	}

	public void setAccountOption(LimitAccountOption accountOption) {
		this.accountOption = accountOption;
	}
	public String getLimitFileName() {
		return limitFileName;
	}

	public void setLimitFileName(String limitFileName) {
		this.limitFileName = limitFileName;
	}


	private boolean textFileEnabled;
	private boolean mailingEnabled;
	private boolean dealerDefSentMail;
	private long companyOID;
	private LimitAccountOption accountOption;
	private String limitFileName;

}
