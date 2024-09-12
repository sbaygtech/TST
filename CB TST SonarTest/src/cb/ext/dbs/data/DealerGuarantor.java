package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSDealerGuarantorPOM;
import cb.ext.dbs.pom.DBSDealerGuarantorPOMData;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;

public class DealerGuarantor {
	
	public void create(DealerGuarantor dealerGuarantor) throws CBException {
		DBSDealerGuarantorPOM pom = DBSDealerGuarantorPOM.newDBSDealerGuarantorPOM();
		DBSDealerGuarantorPOMData data = DBSDealerGuarantorPOMData.newInstance();
		
		try {
			data = prepareData(dealerGuarantor);
			pom.setDBSDealerGuarantorPOMData(data);
			pom.create();
		}
		finally {
			if(pom != null) {
				pom.close();
			}
		}
	}
	
	public void update(DealerGuarantor dealerGuarantor) throws CBException {
		DBSDealerGuarantorPOM pom = DBSDealerGuarantorPOM.newDBSDealerGuarantorPOM();
		DBSDealerGuarantorPOMData data = DBSDealerGuarantorPOMData.newInstance();
		
		try {
			data = prepareData(dealerGuarantor);
			pom.setDBSDealerGuarantorPOMData(data);
			pom.update();
		}
		finally {
			if(pom != null) {
				pom.close();
			}
		}
	}
	
	public DealerGuarantor getActiveDealerGuarantor(long companyOid, long dealerOid) throws CBException {
		DBSDealerGuarantorPOM pom = DBSDealerGuarantorPOM.newDBSDealerGuarantorPOM();
		DealerGuarantor dealerGuarantor = null;
		try {
			if(pom.readActiveDealerGuarantor(companyOid, dealerOid)) {
				DBSDealerGuarantorPOMData data = pom.getDBSDealerGuarantorPOMData();
				dealerGuarantor = setFields(data);
			}
		}
		finally {
			if(pom != null) {
				pom.close();
			}
		}
		
		return dealerGuarantor;
	}
	
	private static DBSDealerGuarantorPOMData prepareData(DealerGuarantor dealerGuarantor) {
		DBSDealerGuarantorPOMData data = DBSDealerGuarantorPOMData.newInstance();
		data.active = dealerGuarantor.getActive();
		data.companyOid = dealerGuarantor.getCompanyOid();
		data.dealerOid = dealerGuarantor.getDealerOid();
		data.finishDate = dealerGuarantor.getFinishDate();
		data.startDate = dealerGuarantor.getStartDate();
		
		return data;
	}
	
	private static DealerGuarantor setFields(DBSDealerGuarantorPOMData data) {
		DealerGuarantor dealerGuarantor = new DealerGuarantor();
		
		dealerGuarantor.setActive(data.active);
		dealerGuarantor.setCompanyOid(data.companyOid);
		dealerGuarantor.setDealerOid(data.dealerOid);
		dealerGuarantor.setFinishDate(data.finishDate);
		dealerGuarantor.setStartDate(data.startDate);
		
		return dealerGuarantor;
	}
	
	private long companyOid;
	private long dealerOid;
	private CBDate startDate;
	private CBDate finishDate;
	private int active;
	
	public long getCompanyOid() {
		return companyOid;
	}
	
	public void setCompanyOid(long companyOid) {
		this.companyOid = companyOid;
	}
	
	public long getDealerOid() {
		return dealerOid;
	}
	
	public void setDealerOid(long dealerOid) {
		this.dealerOid = dealerOid;
	}
	
	public CBDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(CBDate startDate) {
		this.startDate = startDate;
	}
	
	public CBDate getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(CBDate finishDate) {
		this.finishDate = finishDate;
	}
	
	public int getActive() {
		return active;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
}
