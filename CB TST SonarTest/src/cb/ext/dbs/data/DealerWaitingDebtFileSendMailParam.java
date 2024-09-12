package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileResultParamPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileResultParamPOMData;
import cb.ext.dbs.pom.DBSSendDebtListMailToDealerParamPOM;
import cb.ext.dbs.pom.DBSSendDebtListMailToDealerParamPOMData;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBTime;

public class DealerWaitingDebtFileSendMailParam {
	
	private DealerWaitingDebtFileSendMailParam setFields(DBSSendDebtListMailToDealerParamPOMData data) {
		DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = new DealerWaitingDebtFileSendMailParam();

		dealerWaitingDebtFileSendMailParam.setBatchTime(data.batchTime);
		dealerWaitingDebtFileSendMailParam.setCompanyOID(data.companyOid);
		dealerWaitingDebtFileSendMailParam.setDayCount(data.dayCount);
		dealerWaitingDebtFileSendMailParam.setSendMailToDealer(data.sendMailToDealer);
		dealerWaitingDebtFileSendMailParam.setSendSmsToDealer(data.sendSmsToDealer);
		
		return dealerWaitingDebtFileSendMailParam;
	}

	public DealerWaitingDebtFileSendMailParam getDealerWaitingDebtFileSendMailParam(long companyOID) throws CBException {
		DBSSendDebtListMailToDealerParamPOM pom = DBSSendDebtListMailToDealerParamPOM.newDBSSendDebtListMailToDealerParamPOM();
		DBSSendDebtListMailToDealerParamPOMData data = DBSSendDebtListMailToDealerParamPOMData.newInstance();

		try {
			if (pom.readdByCompanyOID(companyOID)) {
				data = pom.getDBSSendDebtListMailToDealerParamPOMData();
				DealerWaitingDebtFileSendMailParam dealerWaitingDebtFileSendMailParam = setFields(data);

				return dealerWaitingDebtFileSendMailParam;
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
		DBSSendDebtListMailToDealerParamPOM pom = DBSSendDebtListMailToDealerParamPOM.newDBSSendDebtListMailToDealerParamPOM();
		DBSSendDebtListMailToDealerParamPOMData data = DBSSendDebtListMailToDealerParamPOMData.newInstance();

		try {
			data = setPOMData(data);
			pom.setDBSSendDebtListMailToDealerParamPOMData(data);
			pom.create();

			return data.oID;

		} finally {
			if (pom != null) {
				pom.close();
			}
		}
	}

	private DBSSendDebtListMailToDealerParamPOMData setPOMData(DBSSendDebtListMailToDealerParamPOMData data) {
		data.companyOid		  = getCompanyOID();
		data.batchTime		  = getBatchTime();
		data.dayCount		  = getDayCount();
		data.sendMailToDealer = isSendMailToDealer();
		data.sendSmsToDealer  = isSendSmsToDealer();
		
		return data;
	}

	public long updateByCompanyOID(long companyOID) throws CBException {
		DBSSendDebtListMailToDealerParamPOM pom = DBSSendDebtListMailToDealerParamPOM.newDBSSendDebtListMailToDealerParamPOM();
		DBSSendDebtListMailToDealerParamPOMData data = DBSSendDebtListMailToDealerParamPOMData.newInstance();

		try {
			if (pom.readdByCompanyOID(companyOID)) {
				data = pom.getDBSSendDebtListMailToDealerParamPOMData();
				data = setPOMData(data);
				pom.setDBSSendDebtListMailToDealerParamPOMData(data);
				pom.update();
			}
			else{
				data = setPOMData(data);
				pom.setDBSSendDebtListMailToDealerParamPOMData(data);
				pom.create();
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
			}
		} finally {

			if (pom != null) {
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
	
	public boolean isSendMailToDealer() {
		return sendMailToDealer;
	}
	
	public void setSendMailToDealer(boolean sendMailToDealer) {
		this.sendMailToDealer = sendMailToDealer;
	}
	
	public int getDayCount() {
		return dayCount;
	}
	
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	
	public CBTime getBatchTime() {
		return batchTime;
	}
	
	public void setBatchTime(CBTime batchTime) {
		this.batchTime = batchTime;
	}
	
	public boolean isSendSmsToDealer() {
		return sendSmsToDealer;
	}

	public void setSendSmsToDealer(boolean sendSmsToDealer) {
		this.sendSmsToDealer = sendSmsToDealer;
	}


	private long companyOID;
	private boolean sendMailToDealer;
	private boolean sendSmsToDealer;
	private int dayCount;
	private CBTime batchTime;

}
