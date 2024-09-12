package cb.ext.dbs.data;

import java.util.Vector;

import cb.ext.dbs.pom.DBSCompanyLimitFileSchedulePOM;
import cb.ext.dbs.pom.DBSCompanyLimitFileSchedulePOMData;
import cb.smg.general.utility.CBException;

public class CompanyLimitFileSchedule {
	
	public Vector<CompanyLimitFileSchedule> getCompanyLimitFileSchedule(long companyOID)throws CBException{
		
		DBSCompanyLimitFileSchedulePOM     pom     = DBSCompanyLimitFileSchedulePOM.newDBSCompanyLimitFileSchedulePOM();
		DBSCompanyLimitFileSchedulePOMData data    = DBSCompanyLimitFileSchedulePOMData.newInstance();
		
		CompanyLimitFileSchedule companyLimitFileSchedule = new CompanyLimitFileSchedule();
		
		Vector<CompanyLimitFileSchedule> companyLimitFileScheduleVector = new Vector<CompanyLimitFileSchedule>();
		
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
			while (pom.next()) {
				
				data = pom.getDBSCompanyLimitFileSchedulePOMData();
				
				companyLimitFileSchedule = new CompanyLimitFileSchedule();
				
				companyLimitFileSchedule.setCompanyOID(companyOID);
				companyLimitFileSchedule.setTime(data.time);
				
				
				companyLimitFileScheduleVector.add(companyLimitFileSchedule);
			}
			
			
			return companyLimitFileScheduleVector;
			
			
		}
		finally {
			
			if (pom!=null) {
				pom.close();
			}
		}
		
	}
	
	public void deleteAllByCompanyOID(long companyOID) throws CBException {
		
		DBSCompanyLimitFileSchedulePOM     pom  =   DBSCompanyLimitFileSchedulePOM.newDBSCompanyLimitFileSchedulePOM();
		
		try {
			
			pom.readByCompanyOID(companyOID);
			
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
		
		DBSCompanyLimitFileSchedulePOM     pom  =   DBSCompanyLimitFileSchedulePOM.newDBSCompanyLimitFileSchedulePOM();
		DBSCompanyLimitFileSchedulePOMData data =   DBSCompanyLimitFileSchedulePOMData.newInstance();
		
		try {
			
			data.companyOID = getCompanyOID();
			data.time       = getTime();
			
			
			pom.setDBSCompanyLimitFileSchedulePOMData(data);
			pom.create();
			
			
			return data.oID;
			
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	private long companyOID;
	private String time;

}
