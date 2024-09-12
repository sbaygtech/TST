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

public class DBSCompanyDebtFileResultEMailPOM extends POM {
	public static class DBSCompanyDebtFileResultEMailPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILERESULT_EMAIL (OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILERESULT_EMAIL SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, EMAIL=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL FROM EXT.DBS_DEBTFILERESULT_EMAIL WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL FROM EXT.DBS_DEBTFILERESULT_EMAIL WHERE COMPANYOID = ? AND STATUS=1 ";

		public DBSCompanyDebtFileResultEMailPOMSql() {
		};
	}

	public static class DBSCompanyDebtFileResultEMailPOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEBTFILERESULT_EMAIL SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEBTFILERESULT_EMAIL T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEBTFILERESULT_EMAIL SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL,TRANSACTIONOID FROM HST.DBS_DEBTFILERESULT_EMAIL WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, EMAIL ,TRANSACTIONOID FROM HST.DBS_DEBTFILERESULT_EMAIL WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSCompanyDebtFileResultEMailPOMHistSql() {
		};
	}

	protected DBSCompanyDebtFileResultEMailPOM() {
		super(1);
		dBSCompanyDebtFileResultEMailPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMData.class;
	}

	protected DBSCompanyDebtFileResultEMailPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileResultEMailPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileResultEMailPOMLevel] = DBSCompanyDebtFileResultEMailPOMData.class;
	}

	protected int dBSCompanyDebtFileResultEMailPOMLevel;
	private static final long dBSCompanyDebtFileResultEMailPOMPID = 3000010314400067L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEBTFILERESULT_EMAIL";

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyDebtFileResultEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyDebtFileResultEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyDebtFileResultEMailPOM newDBSCompanyDebtFileResultEMailPOM() {
		DBSCompanyDebtFileResultEMailPOM pom = (DBSCompanyDebtFileResultEMailPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileResultEMailPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileResultEMailPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileResultEMailPOMData getDBSCompanyDebtFileResultEMailPOMData() {
		return (DBSCompanyDebtFileResultEMailPOMData) datas[dBSCompanyDebtFileResultEMailPOMLevel];
	}

	public DBSCompanyDebtFileResultEMailPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileResultEMailPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyDebtFileResultEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyDebtFileResultEMailPOMPID;
	}

	public void setDBSCompanyDebtFileResultEMailPOMData(
			DBSCompanyDebtFileResultEMailPOMData data) {
		datas[dBSCompanyDebtFileResultEMailPOMLevel] = data;
		isDirty[dBSCompanyDebtFileResultEMailPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileResultEMailPOMData(
			DBSCompanyDebtFileResultEMailPOMData data) throws CBException {
		updateDBSCompanyDebtFileResultEMailPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyDebtFileResultEMailPOMData(
			DBSCompanyDebtFileResultEMailPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyDebtFileResultEMailPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyDebtFileResultEMailPOMData(
			DBSCompanyDebtFileResultEMailPOMData data) throws CBException {
		setDBSCompanyDebtFileResultEMailPOMData(data);
		create();
	}
}
