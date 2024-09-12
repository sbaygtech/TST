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

public class DBSCompanyDebtFileResultParamPOM extends POM {
	public static class DBSCompanyDebtFileResultParamPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMPANY_DF_RSLT_DEF (OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMPANY_DF_RSLT_DEF SET STATUS=?, LASTUPDATED=?, MAILINGENABLED=?, FILECOMPANYCODE=?, TEXTFILEENABLED=?, COMPANYOID=?, TEXTFILETYPE=?, TEXTFILENAME=?, SENDDEBTFILELIST=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST FROM EXT.DBS_COMPANY_DF_RSLT_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST FROM EXT.DBS_COMPANY_DF_RSLT_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public final static String findCompaniesToSendWaitingDebtListSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST FROM EXT.DBS_COMPANY_DF_RSLT_DEF WHERE STATUS = 1 AND SENDDEBTFILELIST = 1 AND STATUS=1  ORDER BY OID ASC";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileResultParamPOMSql() {
		};
	}

	public static class DBSCompanyDebtFileResultParamPOMHistSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_COMPANY_DF_RSLT_DEF SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_COMPANY_DF_RSLT_DEF T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_COMPANY_DF_RSLT_DEF SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST,TRANSACTIONOID FROM HST.DBS_COMPANY_DF_RSLT_DEF WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST ,TRANSACTIONOID FROM HST.DBS_COMPANY_DF_RSLT_DEF WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findCompaniesToSendWaitingDebtListSql = " SELECT  OID, STATUS, LASTUPDATED, MAILINGENABLED, FILECOMPANYCODE, TEXTFILEENABLED, COMPANYOID, TEXTFILETYPE, TEXTFILENAME, SENDDEBTFILELIST ,TRANSACTIONOID FROM HST.DBS_COMPANY_DF_RSLT_DEF WHERE STATUS = 1 AND SENDDEBTFILELIST = 1 AND STATUS=1 AND TRANSACTIONOID = ?  ORDER BY OID ASC";

		public DBSCompanyDebtFileResultParamPOMHistSql() {
		};
	}

	static {
		DBSCompanyDebtFileResultParamPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileResultParamPOMSql.hash
				.put("COMPANYOID", "companyOID");
	}

	protected DBSCompanyDebtFileResultParamPOM() {
		super(1);
		dBSCompanyDebtFileResultParamPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMData.class;
	}

	protected DBSCompanyDebtFileResultParamPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileResultParamPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileResultParamPOMLevel] = DBSCompanyDebtFileResultParamPOMData.class;
	}

	protected int dBSCompanyDebtFileResultParamPOMLevel;
	private static final long dBSCompanyDebtFileResultParamPOMPID = 3000013121800047L;
	private static final String HIST_TABLE_NAME = "HST.DBS_COMPANY_DF_RSLT_DEF";

	public boolean readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyDebtFileResultParamPOMLevel, POMConstants.ONE, true,
				false);
	}

	public boolean readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyDebtFileResultParamPOMLevel, POMConstants.ONE, true,
				false);
	}

	public void readCompaniesToSendWaitingDebtList() throws CBException {
		Object[] params = {};
		Class[] paramTypes = {};
		load("findCompaniesToSendWaitingDebtListSql", params, paramTypes,
				dBSCompanyDebtFileResultParamPOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readCompaniesToSendWaitingDebtList(long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findCompaniesToSendWaitingDebtListSql", params, paramTypes,
				dBSCompanyDebtFileResultParamPOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyDebtFileResultParamPOM newDBSCompanyDebtFileResultParamPOM() {
		DBSCompanyDebtFileResultParamPOM pom = (DBSCompanyDebtFileResultParamPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileResultParamPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileResultParamPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileResultParamPOMData getDBSCompanyDebtFileResultParamPOMData() {
		return (DBSCompanyDebtFileResultParamPOMData) datas[dBSCompanyDebtFileResultParamPOMLevel];
	}

	public DBSCompanyDebtFileResultParamPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileResultParamPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyDebtFileResultParamPOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyDebtFileResultParamPOMPID;
	}

	public void setDBSCompanyDebtFileResultParamPOMData(
			DBSCompanyDebtFileResultParamPOMData data) {
		datas[dBSCompanyDebtFileResultParamPOMLevel] = data;
		isDirty[dBSCompanyDebtFileResultParamPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileResultParamPOMData(
			DBSCompanyDebtFileResultParamPOMData data) throws CBException {
		updateDBSCompanyDebtFileResultParamPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyDebtFileResultParamPOMData(
			DBSCompanyDebtFileResultParamPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyDebtFileResultParamPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyDebtFileResultParamPOMData(
			DBSCompanyDebtFileResultParamPOMData data) throws CBException {
		setDBSCompanyDebtFileResultParamPOMData(data);
		create();
	}
}
