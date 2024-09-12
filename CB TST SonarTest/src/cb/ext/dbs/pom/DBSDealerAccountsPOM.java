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

public class DBSDealerAccountsPOM extends POM {
	public static class DBSDealerAccountsPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEALER_ACCOUNTS (OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE)  VALUES  (?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEALER_ACCOUNTS SET STATUS=?, LASTUPDATED=?, DEALEROID=?, ACCOUNTNO=?, CURRENCY=?, ACTIVE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE FROM EXT.DBS_DEALER_ACCOUNTS WHERE OID = ? AND STATUS=1";
		public final static String findByDealerOIDSql = " SELECT  OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE FROM EXT.DBS_DEALER_ACCOUNTS WHERE DEALEROID = ? AND STATUS=1 ";
		public final static String findByDealerOIDandCurrencySql = " SELECT  OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE FROM EXT.DBS_DEALER_ACCOUNTS WHERE DEALEROID = ? AND CURRENCY = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSDealerAccountsPOMSql() {
		};
	}

	public static class DBSDealerAccountsPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEALER_ACCOUNTS SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEALER_ACCOUNTS T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEALER_ACCOUNTS SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE,TRANSACTIONOID FROM HST.DBS_DEALER_ACCOUNTS WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByDealerOIDSql = " SELECT  OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE ,TRANSACTIONOID FROM HST.DBS_DEALER_ACCOUNTS WHERE DEALEROID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByDealerOIDandCurrencySql = " SELECT  OID, STATUS, LASTUPDATED, DEALEROID, ACCOUNTNO, CURRENCY, ACTIVE ,TRANSACTIONOID FROM HST.DBS_DEALER_ACCOUNTS WHERE DEALEROID = ? AND CURRENCY = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSDealerAccountsPOMHistSql() {
		};
	}

	static {
		DBSDealerAccountsPOMSql.hash = new java.util.Hashtable();
		DBSDealerAccountsPOMSql.hash.put("CURRENCY", "currency");
		DBSDealerAccountsPOMSql.hash.put("DEALEROID", "dealerOID");
	}

	protected DBSDealerAccountsPOM() {
		super(1);
		dBSDealerAccountsPOMLevel = 0;
		sqlClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMSql.class;
		sqlHistClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMHistSql.class;
		pomDataClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMData.class;
	}

	protected DBSDealerAccountsPOM(int level) {
		super(level + 1);
		dBSDealerAccountsPOMLevel = level;
		sqlClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMSql.class;
		sqlHistClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMHistSql.class;
		pomDataClasses[dBSDealerAccountsPOMLevel] = DBSDealerAccountsPOMData.class;
	}

	protected int dBSDealerAccountsPOMLevel;
	private static final long dBSDealerAccountsPOMPID = 3000013389400007L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEALER_ACCOUNTS";

	public void readByDealerOID(long dealerOID) throws CBException {
		Object[] params = { new java.lang.Long(dealerOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByDealerOIDSql", params, paramTypes, dBSDealerAccountsPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByDealerOID(long dealerOID, long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(dealerOID), new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByDealerOIDSql", params, paramTypes, dBSDealerAccountsPOMLevel, POMConstants.MANY, true, false);
	}

	public boolean readByDealerOIDandCurrency(long dealerOID, String currency) throws CBException {
		Object[] params = { new java.lang.Long(dealerOID), currency };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByDealerOIDandCurrencySql", params, paramTypes, dBSDealerAccountsPOMLevel, POMConstants.ONE,
				true, false);
	}

	public boolean readByDealerOIDandCurrency(long dealerOID, String currency, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(dealerOID), currency, new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByDealerOIDandCurrencySql", params, paramTypes, dBSDealerAccountsPOMLevel, POMConstants.ONE,
				true, false);
	}

	public static DBSDealerAccountsPOM newDBSDealerAccountsPOM() {
		DBSDealerAccountsPOM pom = (DBSDealerAccountsPOM) POMPool.getFreePOM(dBSDealerAccountsPOMPID);
		if (pom == null) {
			pom = new DBSDealerAccountsPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDealerAccountsPOMData getDBSDealerAccountsPOMData() {
		return (DBSDealerAccountsPOMData) datas[dBSDealerAccountsPOMLevel];
	}

	public DBSDealerAccountsPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDealerAccountsPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes, dBSDealerAccountsPOMLevel, POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSDealerAccountsPOMPID;
	}

	public void setDBSDealerAccountsPOMData(DBSDealerAccountsPOMData data) {
		datas[dBSDealerAccountsPOMLevel] = data;
		isDirty[dBSDealerAccountsPOMLevel] = true;
	}

	public void updateDBSDealerAccountsPOMData(DBSDealerAccountsPOMData data) throws CBException {
		updateDBSDealerAccountsPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSDealerAccountsPOMData(DBSDealerAccountsPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSDealerAccountsPOMData(data);
		update(updateHistory);
	}

	public void createDBSDealerAccountsPOMData(DBSDealerAccountsPOMData data) throws CBException {
		setDBSDealerAccountsPOMData(data);
		create();
	}
}
