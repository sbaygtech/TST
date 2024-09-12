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

public class DBSCompanyLimitFileParamPOM extends POM {
	public static class DBSCompanyLimitFileParamPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_LIMITFILE_DEF (OID, STATUS, LASTUPDATED, MAILINGENABLED, TEXTFILEENABLED, DEALERDEFSENTMAIL, COMPANYOID, ACCOUNTOPTION, LIMITFILENAME)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_LIMITFILE_DEF SET STATUS=?, LASTUPDATED=?, MAILINGENABLED=?, TEXTFILEENABLED=?, DEALERDEFSENTMAIL=?, COMPANYOID=?, ACCOUNTOPTION=?, LIMITFILENAME=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MAILINGENABLED, TEXTFILEENABLED, DEALERDEFSENTMAIL, COMPANYOID, ACCOUNTOPTION, LIMITFILENAME FROM EXT.DBS_LIMITFILE_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, TEXTFILEENABLED, DEALERDEFSENTMAIL, COMPANYOID, ACCOUNTOPTION, LIMITFILENAME FROM EXT.DBS_LIMITFILE_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyLimitFileParamPOMSql() {
		};
	}

	public static class DBSCompanyLimitFileParamPOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_LIMITFILE_DEF SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_LIMITFILE_DEF T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_LIMITFILE_DEF SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MAILINGENABLED, TEXTFILEENABLED, DEALERDEFSENTMAIL, COMPANYOID, ACCOUNTOPTION, LIMITFILENAME,TRANSACTIONOID FROM HST.DBS_LIMITFILE_DEF WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, TEXTFILEENABLED, DEALERDEFSENTMAIL, COMPANYOID, ACCOUNTOPTION, LIMITFILENAME ,TRANSACTIONOID FROM HST.DBS_LIMITFILE_DEF WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSCompanyLimitFileParamPOMHistSql() {
		};
	}

	static {
		DBSCompanyLimitFileParamPOMSql.hash = new java.util.Hashtable();
		DBSCompanyLimitFileParamPOMSql.hash.put("COMPANYOID", "companyOID");
	}

	protected DBSCompanyLimitFileParamPOM() {
		super(1);
		dBSCompanyLimitFileParamPOMLevel = 0;
		sqlClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMData.class;
	}

	protected DBSCompanyLimitFileParamPOM(int level) {
		super(level + 1);
		dBSCompanyLimitFileParamPOMLevel = level;
		sqlClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMSql.class;
		sqlHistClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMHistSql.class;
		pomDataClasses[dBSCompanyLimitFileParamPOMLevel] = DBSCompanyLimitFileParamPOMData.class;
	}

	protected int dBSCompanyLimitFileParamPOMLevel;
	private static final long dBSCompanyLimitFileParamPOMPID = 3000012345700147L;
	private static final String HIST_TABLE_NAME = "HST.DBS_LIMITFILE_DEF";

	public boolean readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileParamPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyLimitFileParamPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSCompanyLimitFileParamPOM newDBSCompanyLimitFileParamPOM() {
		DBSCompanyLimitFileParamPOM pom = (DBSCompanyLimitFileParamPOM) POMPool
				.getFreePOM(dBSCompanyLimitFileParamPOMPID);
		if (pom == null) {
			pom = new DBSCompanyLimitFileParamPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyLimitFileParamPOMData getDBSCompanyLimitFileParamPOMData() {
		return (DBSCompanyLimitFileParamPOMData) datas[dBSCompanyLimitFileParamPOMLevel];
	}

	public DBSCompanyLimitFileParamPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyLimitFileParamPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyLimitFileParamPOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyLimitFileParamPOMPID;
	}

	public void setDBSCompanyLimitFileParamPOMData(
			DBSCompanyLimitFileParamPOMData data) {
		datas[dBSCompanyLimitFileParamPOMLevel] = data;
		isDirty[dBSCompanyLimitFileParamPOMLevel] = true;
	}

	public void updateDBSCompanyLimitFileParamPOMData(
			DBSCompanyLimitFileParamPOMData data) throws CBException {
		updateDBSCompanyLimitFileParamPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyLimitFileParamPOMData(
			DBSCompanyLimitFileParamPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyLimitFileParamPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyLimitFileParamPOMData(
			DBSCompanyLimitFileParamPOMData data) throws CBException {
		setDBSCompanyLimitFileParamPOMData(data);
		create();
	}
}
