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

public class DBSSendDebtListMailToDealerParamPOM extends POM {
	public static class DBSSendDebtListMailToDealerParamPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEALER_SND_MAIL_PRM (OID, STATUS, LASTUPDATED, SENDMAILTODEALER, DAYCOUNT, BATCHTIME, COMPANYOID, SENDSMSTODEALER)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEALER_SND_MAIL_PRM SET STATUS=?, LASTUPDATED=?, SENDMAILTODEALER=?, DAYCOUNT=?, BATCHTIME=?, COMPANYOID=?, SENDSMSTODEALER=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, SENDMAILTODEALER, DAYCOUNT, BATCHTIME, COMPANYOID, SENDSMSTODEALER FROM EXT.DBS_DEALER_SND_MAIL_PRM WHERE OID = ? AND STATUS=1";
		public final static String readByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, SENDMAILTODEALER, DAYCOUNT, BATCHTIME, COMPANYOID, SENDSMSTODEALER FROM EXT.DBS_DEALER_SND_MAIL_PRM WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSSendDebtListMailToDealerParamPOMSql() {
		};
	}

	public static class DBSSendDebtListMailToDealerParamPOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEALER_SND_MAIL_PRM SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEALER_SND_MAIL_PRM T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEALER_SND_MAIL_PRM SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, SENDMAILTODEALER, DAYCOUNT, BATCHTIME, COMPANYOID, SENDSMSTODEALER,TRANSACTIONOID FROM HST.DBS_DEALER_SND_MAIL_PRM WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String readByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, SENDMAILTODEALER, DAYCOUNT, BATCHTIME, COMPANYOID, SENDSMSTODEALER ,TRANSACTIONOID FROM HST.DBS_DEALER_SND_MAIL_PRM WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSSendDebtListMailToDealerParamPOMHistSql() {
		};
	}

	static {
		DBSSendDebtListMailToDealerParamPOMSql.hash = new java.util.Hashtable();
		DBSSendDebtListMailToDealerParamPOMSql.hash.put("COMPANYOID",
				"companyOid");
	}

	protected DBSSendDebtListMailToDealerParamPOM() {
		super(1);
		dBSSendDebtListMailToDealerParamPOMLevel = 0;
		sqlClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMSql.class;
		sqlHistClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMHistSql.class;
		pomDataClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMData.class;
	}

	protected DBSSendDebtListMailToDealerParamPOM(int level) {
		super(level + 1);
		dBSSendDebtListMailToDealerParamPOMLevel = level;
		sqlClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMSql.class;
		sqlHistClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMHistSql.class;
		pomDataClasses[dBSSendDebtListMailToDealerParamPOMLevel] = DBSSendDebtListMailToDealerParamPOMData.class;
	}

	protected int dBSSendDebtListMailToDealerParamPOMLevel;
	private static final long dBSSendDebtListMailToDealerParamPOMPID = 3000013121800001L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEALER_SND_MAIL_PRM";

	public boolean readdByCompanyOID(long companyOid) throws CBException {
		Object[] params = { new java.lang.Long(companyOid) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("readByCompanyOIDSql", params, paramTypes,
				dBSSendDebtListMailToDealerParamPOMLevel, POMConstants.ONE,
				true, false);
	}

	public boolean readdByCompanyOID(long companyOid, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOid),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("readByCompanyOIDSql", params, paramTypes,
				dBSSendDebtListMailToDealerParamPOMLevel, POMConstants.ONE,
				true, false);
	}

	public static DBSSendDebtListMailToDealerParamPOM newDBSSendDebtListMailToDealerParamPOM() {
		DBSSendDebtListMailToDealerParamPOM pom = (DBSSendDebtListMailToDealerParamPOM) POMPool
				.getFreePOM(dBSSendDebtListMailToDealerParamPOMPID);
		if (pom == null) {
			pom = new DBSSendDebtListMailToDealerParamPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSSendDebtListMailToDealerParamPOMData getDBSSendDebtListMailToDealerParamPOMData() {
		return (DBSSendDebtListMailToDealerParamPOMData) datas[dBSSendDebtListMailToDealerParamPOMLevel];
	}

	public DBSSendDebtListMailToDealerParamPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSSendDebtListMailToDealerParamPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSSendDebtListMailToDealerParamPOMLevel, POMConstants.MANY,
				true, false);
	}

	public long getID() {
		return dBSSendDebtListMailToDealerParamPOMPID;
	}

	public void setDBSSendDebtListMailToDealerParamPOMData(
			DBSSendDebtListMailToDealerParamPOMData data) {
		datas[dBSSendDebtListMailToDealerParamPOMLevel] = data;
		isDirty[dBSSendDebtListMailToDealerParamPOMLevel] = true;
	}

	public void updateDBSSendDebtListMailToDealerParamPOMData(
			DBSSendDebtListMailToDealerParamPOMData data) throws CBException {
		updateDBSSendDebtListMailToDealerParamPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSSendDebtListMailToDealerParamPOMData(
			DBSSendDebtListMailToDealerParamPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSSendDebtListMailToDealerParamPOMData(data);
		update(updateHistory);
	}

	public void createDBSSendDebtListMailToDealerParamPOMData(
			DBSSendDebtListMailToDealerParamPOMData data) throws CBException {
		setDBSSendDebtListMailToDealerParamPOMData(data);
		create();
	}
}
