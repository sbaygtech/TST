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

public class DBSCompanyAccountPOM extends POM {
	public static class DBSCompanyAccountPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMPANY_ACCOUNTS (OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMPANY_ACCOUNTS SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, CURRENCY=?, BLOCKAGEACCOUNTNO=?, ACCOUNTNO=?, BLOCKAGEDAYCOUNT=?, ACTIVE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE FROM EXT.DBS_COMPANY_ACCOUNTS WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE FROM EXT.DBS_COMPANY_ACCOUNTS WHERE COMPANYOID = ? AND STATUS=1 ";
		public final static String findByCompanyOIDandCurrencySql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE FROM EXT.DBS_COMPANY_ACCOUNTS WHERE COMPANYOID = ? AND CURRENCY = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyAccountPOMSql() {
		};
	}

	public static class DBSCompanyAccountPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_COMPANY_ACCOUNTS SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_COMPANY_ACCOUNTS T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_COMPANY_ACCOUNTS SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE,TRANSACTIONOID FROM HST.DBS_COMPANY_ACCOUNTS WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE ,TRANSACTIONOID FROM HST.DBS_COMPANY_ACCOUNTS WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDandCurrencySql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, CURRENCY, BLOCKAGEACCOUNTNO, ACCOUNTNO, BLOCKAGEDAYCOUNT, ACTIVE ,TRANSACTIONOID FROM HST.DBS_COMPANY_ACCOUNTS WHERE COMPANYOID = ? AND CURRENCY = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSCompanyAccountPOMHistSql() {
		};
	}

	static {
		DBSCompanyAccountPOMSql.hash = new java.util.Hashtable();
		DBSCompanyAccountPOMSql.hash.put("COMPANYOID", "companyOID");
		DBSCompanyAccountPOMSql.hash.put("CURRENCY", "currency");
	}

	protected DBSCompanyAccountPOM() {
		super(1);
		dBSCompanyAccountPOMLevel = 0;
		sqlClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMSql.class;
		sqlHistClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMHistSql.class;
		pomDataClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMData.class;
	}

	protected DBSCompanyAccountPOM(int level) {
		super(level + 1);
		dBSCompanyAccountPOMLevel = level;
		sqlClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMSql.class;
		sqlHistClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMHistSql.class;
		pomDataClasses[dBSCompanyAccountPOMLevel] = DBSCompanyAccountPOMData.class;
	}

	protected int dBSCompanyAccountPOMLevel;
	private static final long dBSCompanyAccountPOMPID = 3000009557800018L;
	private static final String HIST_TABLE_NAME = "HST.DBS_COMPANY_ACCOUNTS";

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyAccountPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyAccountPOMLevel, POMConstants.MANY, true, false);
	}

	public boolean readByCompanyOIDandCurrency(long companyOID, String currency)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID), currency };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOIDandCurrencySql", params, paramTypes,
				dBSCompanyAccountPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOIDandCurrency(long companyOID,
			String currency, long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID), currency,
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDandCurrencySql", params, paramTypes,
				dBSCompanyAccountPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSCompanyAccountPOM newDBSCompanyAccountPOM() {
		DBSCompanyAccountPOM pom = (DBSCompanyAccountPOM) POMPool
				.getFreePOM(dBSCompanyAccountPOMPID);
		if (pom == null) {
			pom = new DBSCompanyAccountPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyAccountPOMData getDBSCompanyAccountPOMData() {
		return (DBSCompanyAccountPOMData) datas[dBSCompanyAccountPOMLevel];
	}

	public DBSCompanyAccountPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyAccountPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSCompanyAccountPOMLevel, POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSCompanyAccountPOMPID;
	}

	public void setDBSCompanyAccountPOMData(DBSCompanyAccountPOMData data) {
		datas[dBSCompanyAccountPOMLevel] = data;
		isDirty[dBSCompanyAccountPOMLevel] = true;
	}

	public void updateDBSCompanyAccountPOMData(DBSCompanyAccountPOMData data)
			throws CBException {
		updateDBSCompanyAccountPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyAccountPOMData(DBSCompanyAccountPOMData data,
			boolean updateHistory) throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyAccountPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyAccountPOMData(DBSCompanyAccountPOMData data)
			throws CBException {
		setDBSCompanyAccountPOMData(data);
		create();
	}
}
