package cb.ext.dbs.pom;

/*
 * THIS FILE IS GENERATED AUTOMATICALLY, DO NOT MODIFY
 * If corresponding POM is needed to be changed,
 * Do modifications with POMGenerator, and regenerate.
 */

import cb.smg.pom.*;
import cb.smg.general.utility.*;
import cb.smg.businesstype.*;
import cb.smg.businesstype.*;

public class DBSCompanyLimitFileReportPOM extends POM {
	public static class DBSCompanyLimitFileReportPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_REPORT_LIMIT_FILE (OID, STATUS, LASTUPDATED, REPORTSEQUENCENUMBER, ACTIVE, COMPANYTITLE, DEALERTITLE, CUSTOMERNUMBER, COMPANYDEALERCUSCODE, TLACCOUNTNO, USDACCOUNTNO, CURRENCY, LIMIT, AVAILABLELIMIT, UNPAIDINVOICE, AVAILABLEDBS)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_REPORT_LIMIT_FILE SET STATUS=?, LASTUPDATED=?, REPORTSEQUENCENUMBER=?, ACTIVE=?, COMPANYTITLE=?, DEALERTITLE=?, CUSTOMERNUMBER=?, COMPANYDEALERCUSCODE=?, TLACCOUNTNO=?, USDACCOUNTNO=?, CURRENCY=?, LIMIT=?, AVAILABLELIMIT=?, UNPAIDINVOICE=?, AVAILABLEDBS=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, REPORTSEQUENCENUMBER, ACTIVE, COMPANYTITLE, DEALERTITLE, CUSTOMERNUMBER, COMPANYDEALERCUSCODE, TLACCOUNTNO, USDACCOUNTNO, CURRENCY, LIMIT, AVAILABLELIMIT, UNPAIDINVOICE, AVAILABLEDBS FROM EXT.DBS_REPORT_LIMIT_FILE WHERE OID = ? AND STATUS=1";

		public DBSCompanyLimitFileReportPOMSql() {
		};
	}

	protected DBSCompanyLimitFileReportPOM() {
		super(1);
		dBSCompanyLimitFileReportPOMLevel = 0;
		sqlClasses[dBSCompanyLimitFileReportPOMLevel] = DBSCompanyLimitFileReportPOMSql.class;
		pomDataClasses[dBSCompanyLimitFileReportPOMLevel] = DBSCompanyLimitFileReportPOMData.class;
	}

	protected DBSCompanyLimitFileReportPOM(int level) {
		super(level + 1);
		dBSCompanyLimitFileReportPOMLevel = level;
		sqlClasses[dBSCompanyLimitFileReportPOMLevel] = DBSCompanyLimitFileReportPOMSql.class;
		pomDataClasses[dBSCompanyLimitFileReportPOMLevel] = DBSCompanyLimitFileReportPOMData.class;
	}

	protected int dBSCompanyLimitFileReportPOMLevel;
	private static final long dBSCompanyLimitFileReportPOMPID = 3000011939500115L;

	public static DBSCompanyLimitFileReportPOM newDBSCompanyLimitFileReportPOM() {
		DBSCompanyLimitFileReportPOM pom = (DBSCompanyLimitFileReportPOM) POMPool
				.getFreePOM(dBSCompanyLimitFileReportPOMPID);
		if (pom == null) {
			pom = new DBSCompanyLimitFileReportPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyLimitFileReportPOMData getDBSCompanyLimitFileReportPOMData() {
		return (DBSCompanyLimitFileReportPOMData) datas[dBSCompanyLimitFileReportPOMLevel];
	}

	public DBSCompanyLimitFileReportPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyLimitFileReportPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyLimitFileReportPOMPID;
	}

	public void setDBSCompanyLimitFileReportPOMData(
			DBSCompanyLimitFileReportPOMData data) {
		datas[dBSCompanyLimitFileReportPOMLevel] = data;
		isDirty[dBSCompanyLimitFileReportPOMLevel] = true;
	}

	public void updateDBSCompanyLimitFileReportPOMData(
			DBSCompanyLimitFileReportPOMData data) throws CBException {
		setDBSCompanyLimitFileReportPOMData(data);
		update();
	}

	public void createDBSCompanyLimitFileReportPOMData(
			DBSCompanyLimitFileReportPOMData data) throws CBException {
		setDBSCompanyLimitFileReportPOMData(data);
		create();
	}
}
