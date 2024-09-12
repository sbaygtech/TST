package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSPaymentResultHistoryPOM;
import cb.ext.dbs.pom.DBSPaymentResultHistoryPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class PaymentResultHistory {
	
	public void create() throws CBException {
		DBSPaymentResultHistoryPOM pom = DBSPaymentResultHistoryPOM.newDBSPaymentResultHistoryPOM();
		DBSPaymentResultHistoryPOMData data = DBSPaymentResultHistoryPOMData.newInstance();
		
		try {
			data.createdDate = CBSystem.getInstance().getProcessDate();
			data.createdTime = CBSystem.getInstance().getCurrentTime();
			data.masterOid = getMasterOid();
			data.paidAmount = getPaidAmount();
			data.sequenceNo = getSequenceNo();
			data.processType = getProcessType();
			
			pom.setDBSPaymentResultHistoryPOMData(data);
			
			pom.create();
		}
		finally {
			if(pom != null) {
				pom.close();
			}
		}
	}
		
	private CBDate createdDate;
	private CBTime createdTime;
	private long masterOid;
	private long sequenceNo;
	private CBCurrency paidAmount;
	private int processType;
	
	public CBDate getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(CBDate createdDate) {
		this.createdDate = createdDate;
	}
	
	public CBTime getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(CBTime createdTime) {
		this.createdTime = createdTime;
	}
	
	public long getMasterOid() {
		return masterOid;
	}
	
	public void setMasterOid(long masterOid) {
		this.masterOid = masterOid;
	}
	
	public long getSequenceNo() {
		return sequenceNo;
	}
	
	public void setSequenceNo(long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public CBCurrency getPaidAmount() {
		return paidAmount;
	}
	
	public void setPaidAmount(CBCurrency paidAmount) {
		this.paidAmount = paidAmount;
	}

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}
	

}
