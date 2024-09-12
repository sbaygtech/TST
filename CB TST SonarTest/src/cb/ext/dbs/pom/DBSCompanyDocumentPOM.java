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

public class DBSCompanyDocumentPOM extends POM {
	public static class DBSCompanyDocumentPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMPANY_DOCUMENT (OID, STATUS, LASTUPDATED, COMPANYOID, DOCUMENTCODE)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMPANY_DOCUMENT SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, DOCUMENTCODE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, DOCUMENTCODE FROM EXT.DBS_COMPANY_DOCUMENT WHERE OID = ? AND STATUS=1";
		public final static String findCompanyDocumentsSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, DOCUMENTCODE FROM EXT.DBS_COMPANY_DOCUMENT WHERE COMPANYOID = ? AND STATUS=1  ORDER BY DOCUMENTCODE ASC";

		public DBSCompanyDocumentPOMSql() {
		};
	}

	public static class DBSCompanyDocumentPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_COMPANY_DOCUMENT SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_COMPANY_DOCUMENT T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_COMPANY_DOCUMENT SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, DOCUMENTCODE,TRANSACTIONOID FROM HST.DBS_COMPANY_DOCUMENT WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findCompanyDocumentsSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, DOCUMENTCODE ,TRANSACTIONOID FROM HST.DBS_COMPANY_DOCUMENT WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ?  ORDER BY DOCUMENTCODE ASC";

		public DBSCompanyDocumentPOMHistSql() {
		};
	}

	protected DBSCompanyDocumentPOM() {
		super(1);
		dBSCompanyDocumentPOMLevel = 0;
		sqlClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMSql.class;
		sqlHistClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMHistSql.class;
		pomDataClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMData.class;
	}

	protected DBSCompanyDocumentPOM(int level) {
		super(level + 1);
		dBSCompanyDocumentPOMLevel = level;
		sqlClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMSql.class;
		sqlHistClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMHistSql.class;
		pomDataClasses[dBSCompanyDocumentPOMLevel] = DBSCompanyDocumentPOMData.class;
	}

	protected int dBSCompanyDocumentPOMLevel;
	private static final long dBSCompanyDocumentPOMPID = 3000013389400004L;
	private static final String HIST_TABLE_NAME = "HST.DBS_COMPANY_DOCUMENT";

	public void readCompanyDocuments(long companyOid) throws CBException {
		Object[] params = { new java.lang.Long(companyOid) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findCompanyDocumentsSql", params, paramTypes, dBSCompanyDocumentPOMLevel, POMConstants.MANY, true, false);
	}

	public void readCompanyDocuments(long companyOid, long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOid), new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findCompanyDocumentsSql", params, paramTypes, dBSCompanyDocumentPOMLevel, POMConstants.MANY, true, false);
	}

	public static DBSCompanyDocumentPOM newDBSCompanyDocumentPOM() {
		DBSCompanyDocumentPOM pom = (DBSCompanyDocumentPOM) POMPool.getFreePOM(dBSCompanyDocumentPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDocumentPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDocumentPOMData getDBSCompanyDocumentPOMData() {
		return (DBSCompanyDocumentPOMData) datas[dBSCompanyDocumentPOMLevel];
	}

	public DBSCompanyDocumentPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDocumentPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes, dBSCompanyDocumentPOMLevel, POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSCompanyDocumentPOMPID;
	}

	public void setDBSCompanyDocumentPOMData(DBSCompanyDocumentPOMData data) {
		datas[dBSCompanyDocumentPOMLevel] = data;
		isDirty[dBSCompanyDocumentPOMLevel] = true;
	}

	public void updateDBSCompanyDocumentPOMData(DBSCompanyDocumentPOMData data) throws CBException {
		updateDBSCompanyDocumentPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyDocumentPOMData(DBSCompanyDocumentPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyDocumentPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyDocumentPOMData(DBSCompanyDocumentPOMData data) throws CBException {
		setDBSCompanyDocumentPOMData(data);
		create();
	}
}
