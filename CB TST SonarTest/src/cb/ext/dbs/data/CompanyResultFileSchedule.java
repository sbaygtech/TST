package cb.ext.dbs.data;

import java.util.Vector;

import cb.ext.dbs.pom.DBSCompanyResultFileSchedulePOM;
import cb.ext.dbs.pom.DBSCompanyResultFileSchedulePOMData;
import cb.smg.general.utility.CBException;

public class CompanyResultFileSchedule {
	public Vector<CompanyResultFileSchedule> getCompanyResultFileSchedule(long companyOid)throws CBException{
		DBSCompanyResultFileSchedulePOM     pom  =   DBSCompanyResultFileSchedulePOM.newDBSCompanyResultFileSchedulePOM();
		DBSCompanyResultFileSchedulePOMData data =   DBSCompanyResultFileSchedulePOMData.newInstance();
		
		CompanyResultFileSchedule companyResultFileSchedule;
		Vector<CompanyResultFileSchedule> companyResultFileScheduleVector = new Vector<CompanyResultFileSchedule>();
		try {
			
			pom.readByCompanyOid(companyOid);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyResultFileSchedulePOMData();
				
				companyResultFileSchedule = new CompanyResultFileSchedule();
				companyResultFileSchedule.setCompanyOid(companyOid);
				companyResultFileSchedule.setTime(data.time);
				companyResultFileSchedule.setUseLimit(data.useLimit);
				companyResultFileScheduleVector.add(companyResultFileSchedule);
			}
			
			return companyResultFileScheduleVector;			
		}
		finally {
			if (pom!=null) {
				pom.close();
			}
		}
	}
	
	public void deleteAllByCompanyOID(long companyOid) throws CBException {
		DBSCompanyResultFileSchedulePOM     pom  =   DBSCompanyResultFileSchedulePOM.newDBSCompanyResultFileSchedulePOM();
		
		try {
			pom.readByCompanyOid(companyOid);
			
			while (pom.next()) {
				pom.delete();
			}
		}
		finally {
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public long add() throws CBException {
		DBSCompanyResultFileSchedulePOM     pom  =   DBSCompanyResultFileSchedulePOM.newDBSCompanyResultFileSchedulePOM();
		DBSCompanyResultFileSchedulePOMData data =   DBSCompanyResultFileSchedulePOMData.newInstance();
		
		try {
			data.companyOid = getCompanyOid();
			data.time       = getTime();
			data.useLimit	= isUseLimit();
			
			pom.setDBSCompanyResultFileSchedulePOMData(data);
			pom.create();
			
			return data.oID;
			
		}
		finally {
			
			if (pom!=null) {
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
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean isUseLimit() {
		return useLimit;
	}

	public void setUseLimit(boolean useLimit) {
		this.useLimit = useLimit;
	}
	
	private long companyOid;
	private String time;
	private boolean useLimit;

}
