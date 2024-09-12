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

public class DBSCompanyResultFileSchedulePOM extends POM {
	public static class DBSCompanyResultFileSchedulePOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_RESULTFILE_SCHEDULE (OID, STATUS, LASTUPDATED, COMPANYOID, TIME, USELIMIT)  VALUES  (?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_RESULTFILE_SCHEDULE SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, TIME=?, USELIMIT=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, TIME, USELIMIT FROM EXT.DBS_RESULTFILE_SCHEDULE WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOidSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, TIME, USELIMIT FROM EXT.DBS_RESULTFILE_SCHEDULE WHERE COMPANYOID = ? AND STATUS=1  ORDER BY TIME ASC";

		public DBSCompanyResultFileSchedulePOMSql() {
		};
	}

	public static class DBSCompanyResultFileSchedulePOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_RESULTFILE_SCHEDULE SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_RESULTFILE_SCHEDULE T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_RESULTFILE_SCHEDULE SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, TIME, USELIMIT,TRANSACTIONOID FROM HST.DBS_RESULTFILE_SCHEDULE WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOidSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, TIME, USELIMIT ,TRANSACTIONOID FROM HST.DBS_RESULTFILE_SCHEDULE WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ?  ORDER BY TIME ASC";

		public DBSCompanyResultFileSchedulePOMHistSql() {
		};
	}

	protected DBSCompanyResultFileSchedulePOM() {
		super(1);
		dBSCompanyResultFileSchedulePOMLevel = 0;
		sqlClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMSql.class;
		sqlHistClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMHistSql.class;
		pomDataClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMData.class;
	}

	protected DBSCompanyResultFileSchedulePOM(int level) {
		super(level + 1);
		dBSCompanyResultFileSchedulePOMLevel = level;
		sqlClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMSql.class;
		sqlHistClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMHistSql.class;
		pomDataClasses[dBSCompanyResultFileSchedulePOMLevel] = DBSCompanyResultFileSchedulePOMData.class;
	}

	protected int dBSCompanyResultFileSchedulePOMLevel;
	private static final long dBSCompanyResultFileSchedulePOMPID = 3000012870000018L;
	private static final String HIST_TABLE_NAME = "HST.DBS_RESULTFILE_SCHEDULE";

	public void readByCompanyOid(long companyOid) throws CBException {
		Object[] params = { new java.lang.Long(companyOid) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOidSql", params, paramTypes,
				dBSCompanyResultFileSchedulePOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOid(long companyOid, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOid),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOidSql", params, paramTypes,
				dBSCompanyResultFileSchedulePOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyResultFileSchedulePOM newDBSCompanyResultFileSchedulePOM() {
		DBSCompanyResultFileSchedulePOM pom = (DBSCompanyResultFileSchedulePOM) POMPool
				.getFreePOM(dBSCompanyResultFileSchedulePOMPID);
		if (pom == null) {
			pom = new DBSCompanyResultFileSchedulePOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyResultFileSchedulePOMData getDBSCompanyResultFileSchedulePOMData() {
		return (DBSCompanyResultFileSchedulePOMData) datas[dBSCompanyResultFileSchedulePOMLevel];
	}

	public DBSCompanyResultFileSchedulePOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyResultFileSchedulePOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyResultFileSchedulePOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyResultFileSchedulePOMPID;
	}

	public void setDBSCompanyResultFileSchedulePOMData(
			DBSCompanyResultFileSchedulePOMData data) {
		datas[dBSCompanyResultFileSchedulePOMLevel] = data;
		isDirty[dBSCompanyResultFileSchedulePOMLevel] = true;
	}

	public void updateDBSCompanyResultFileSchedulePOMData(
			DBSCompanyResultFileSchedulePOMData data) throws CBException {
		updateDBSCompanyResultFileSchedulePOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyResultFileSchedulePOMData(
			DBSCompanyResultFileSchedulePOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyResultFileSchedulePOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyResultFileSchedulePOMData(
			DBSCompanyResultFileSchedulePOMData data) throws CBException {
		setDBSCompanyResultFileSchedulePOMData(data);
		create();
	}
}
