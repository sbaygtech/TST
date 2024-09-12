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

public class DBSCompanyLimitFileEMailPOM extends POM {
	public static class DBSCompanyLimitFileEMailPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_LIMITFILE_EMAIL (OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_LIMITFILE_EMAIL SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, EMAIL=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL FROM EXT.DBS_LIMITFILE_EMAIL WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL FROM EXT.DBS_LIMITFILE_EMAIL WHERE COMPANYOID = ? AND STATUS=1 ";

		public DBSCompanyLimitFileEMailPOMSql() {
		};
	}

	public static class DBSCompanyLimitFileEMailPOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_LIMITFILE_EMAIL SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_LIMITFILE_EMAIL T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_LIMITFILE_EMAIL SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL,TRANSACTIONOID FROM HST.DBS_LIMITFILE_EMAIL WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL ,TRANSACTIONOID FROM HST.DBS_LIMITFILE_EMAIL WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSCompanyLimitFileEMailPOMHistSql() {
		};
	}

	protected DBSCompanyLimitFileEMailPOM() {
		super(1);
		dBSCompanyLimitFileEMailPOMLevel = 0;
		sqlClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMData.class;
	}

	protected DBSCompanyLimitFileEMailPOM(int level) {
		super(level + 1);
		dBSCompanyLimitFileEMailPOMLevel = level;
		sqlClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileEMailPOMLevel] = DBSCompanyLimitFileEMailPOMData.class;
	}

	protected int dBSCompanyLimitFileEMailPOMLevel;
	private static final long dBSCompanyLimitFileEMailPOMPID = 3000010329800043L;
	private static final String HIST_TABLE_NAME = "HST.DBS_LIMITFILE_EMAIL";

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyLimitFileEMailPOM newDBSCompanyLimitFileEMailPOM() {
		DBSCompanyLimitFileEMailPOM pom = (DBSCompanyLimitFileEMailPOM) POMPool
				.getFreePOM(dBSCompanyLimitFileEMailPOMPID);
		if (pom == null) {
			pom = new DBSCompanyLimitFileEMailPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyLimitFileEMailPOMData getDBSCompanyLimitFileEMailPOMData() {
		return (DBSCompanyLimitFileEMailPOMData) datas[dBSCompanyLimitFileEMailPOMLevel];
	}

	public DBSCompanyLimitFileEMailPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyLimitFileEMailPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyLimitFileEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyLimitFileEMailPOMPID;
	}

	public void setDBSCompanyLimitFileEMailPOMData(
			DBSCompanyLimitFileEMailPOMData data) {
		datas[dBSCompanyLimitFileEMailPOMLevel] = data;
		isDirty[dBSCompanyLimitFileEMailPOMLevel] = true;
	}

	public void updateDBSCompanyLimitFileEMailPOMData(
			DBSCompanyLimitFileEMailPOMData data) throws CBException {
		updateDBSCompanyLimitFileEMailPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyLimitFileEMailPOMData(
			DBSCompanyLimitFileEMailPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyLimitFileEMailPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyLimitFileEMailPOMData(
			DBSCompanyLimitFileEMailPOMData data) throws CBException {
		setDBSCompanyLimitFileEMailPOMData(data);
		create();
	}
}
