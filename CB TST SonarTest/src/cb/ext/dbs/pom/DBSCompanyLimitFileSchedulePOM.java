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

public class DBSCompanyLimitFileSchedulePOM extends POM {
	public static class DBSCompanyLimitFileSchedulePOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_LIMITFILE_SCHEDULE (OID, STATUS, LASTUPDATED, COMPANYOID, TIME)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_LIMITFILE_SCHEDULE SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, TIME=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, TIME FROM EXT.DBS_LIMITFILE_SCHEDULE WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, TIME FROM EXT.DBS_LIMITFILE_SCHEDULE WHERE COMPANYOID = ? AND STATUS=1  ORDER BY TIME ASC";

		public DBSCompanyLimitFileSchedulePOMSql() {
		};
	}

	public static class DBSCompanyLimitFileSchedulePOMHistSql implements
			SqlStatements {

		public final static String insertSql = "insert into HST.DBS_LIMITFILE_SCHEDULE select  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate as start_date,null as end_date,1 as ACTIVE_FLAG,?  as transactionOId ,t1.*  from EXT.DBS_LIMITFILE_SCHEDULE t1 where oid = ? AND STATUS=1";
		public final static String updateSql = "update HST.DBS_LIMITFILE_SCHEDULE set ACTIVE_FLAG = 0,end_date=sysdate where oid = ? and ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, TIME,TRANSACTIONOID FROM HST.DBS_LIMITFILE_SCHEDULE WHERE OID = ? and TRANSACTIONOID < ? AND STATUS=1 order by lastupdated desc";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, TIME ,TRANSACTIONOID FROM HST.DBS_LIMITFILE_SCHEDULE WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ?  ORDER BY TIME ASC";

		public DBSCompanyLimitFileSchedulePOMHistSql() {
		};
	}

	protected DBSCompanyLimitFileSchedulePOM() {
		super(1);
		dBSCompanyLimitFileSchedulePOMLevel = 0;
		sqlClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMData.class;
	}

	protected DBSCompanyLimitFileSchedulePOM(int level) {
		super(level + 1);
		dBSCompanyLimitFileSchedulePOMLevel = level;
		sqlClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileSchedulePOMLevel] = DBSCompanyLimitFileSchedulePOMData.class;
	}

	protected int dBSCompanyLimitFileSchedulePOMLevel;
	private static final long dBSCompanyLimitFileSchedulePOMPID = 3000008665800038L;
	private static final String HIST_TABLE_NAME = "null.DBS_LIMITFILE_SCHEDULE";

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileSchedulePOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileSchedulePOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyLimitFileSchedulePOM newDBSCompanyLimitFileSchedulePOM() {
		DBSCompanyLimitFileSchedulePOM pom = (DBSCompanyLimitFileSchedulePOM) POMPool
				.getFreePOM(dBSCompanyLimitFileSchedulePOMPID);
		if (pom == null) {
			pom = new DBSCompanyLimitFileSchedulePOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyLimitFileSchedulePOMData getDBSCompanyLimitFileSchedulePOMData() {
		return (DBSCompanyLimitFileSchedulePOMData) datas[dBSCompanyLimitFileSchedulePOMLevel];
	}

	public DBSCompanyLimitFileSchedulePOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyLimitFileSchedulePOMData();
		return null;
	}

	public DBSCompanyLimitFileSchedulePOMData findByPrimaryKey(long oID,
			long historyDate) throws CBException {
		if (readByPrimaryKey(oID, historyDate))
			return getDBSCompanyLimitFileSchedulePOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyLimitFileSchedulePOMPID;
	}

	public void setDBSCompanyLimitFileSchedulePOMData(
			DBSCompanyLimitFileSchedulePOMData data) {
		datas[dBSCompanyLimitFileSchedulePOMLevel] = data;
		isDirty[dBSCompanyLimitFileSchedulePOMLevel] = true;
	}

	public void updateDBSCompanyLimitFileSchedulePOMData(
			DBSCompanyLimitFileSchedulePOMData data) throws CBException {
		updateDBSCompanyLimitFileSchedulePOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyLimitFileSchedulePOMData(
			DBSCompanyLimitFileSchedulePOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyLimitFileSchedulePOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyLimitFileSchedulePOMData(
			DBSCompanyLimitFileSchedulePOMData data) throws CBException {
		setDBSCompanyLimitFileSchedulePOMData(data);
		create();
	}
}
