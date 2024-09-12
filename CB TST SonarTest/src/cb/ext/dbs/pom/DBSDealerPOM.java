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

public class DBSDealerPOM extends POM {
	public static class DBSDealerPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEALER_DEF (OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEALER_DEF SET STATUS=?, LASTUPDATED=?, ACTIVE=?, COMPANYOID=?, CUSTOMERNUMBER=?, INSERTDATE=?, INSERTTIME=?, INSERTUSER=?, UPDDATE=?, UPDTIME=?, UPDUSER=?, COMPANYDEALERCUSCODE=?, PRODUCTREFNO=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public final static String findByCompanyDealerCustCodeSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE COMPANYOID = ? AND COMPANYDEALERCUSCODE = ? AND STATUS=1 ";
		public final static String findByCustomerNumberandCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE CUSTOMERNUMBER = ? AND COMPANYOID = ? AND STATUS=1 ";
		public final static String findByCompanyOIDAndActiveSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE COMPANYOID = ? AND ACTIVE = ? AND STATUS=1 ";
		public final static String findByAllRecordsSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE STATUS=1  ORDER BY CUSTOMERNUMBER";
		public final static String findByCompanyOidAndDealerCustomerCodeSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE COMPANYOID = ? AND COMPANYDEALERCUSCODE = ? AND STATUS=1 ";
		public final static String findDealersByCompanyOidSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO FROM EXT.DBS_DEALER_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSDealerPOMSql() {
		};
	}

	public static class DBSDealerPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEALER_DEF SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEALER_DEF T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEALER_DEF SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyDealerCustCodeSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE COMPANYOID = ? AND COMPANYDEALERCUSCODE = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCustomerNumberandCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE CUSTOMERNUMBER = ? AND COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDAndActiveSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE COMPANYOID = ? AND ACTIVE = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByAllRecordsSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE STATUS=1 AND TRANSACTIONOID = ?  ORDER BY CUSTOMERNUMBER";
		public final static String findByCompanyOidAndDealerCustomerCodeSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE COMPANYOID = ? AND COMPANYDEALERCUSCODE = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findDealersByCompanyOidSql = " SELECT  OID, STATUS, LASTUPDATED, ACTIVE, COMPANYOID, CUSTOMERNUMBER, INSERTDATE, INSERTTIME, INSERTUSER, UPDDATE, UPDTIME, UPDUSER, COMPANYDEALERCUSCODE, PRODUCTREFNO ,TRANSACTIONOID FROM HST.DBS_DEALER_DEF WHERE COMPANYOID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSDealerPOMHistSql() {
		};
	}

	static {
		DBSDealerPOMSql.hash = new java.util.Hashtable();
		DBSDealerPOMSql.hash.put("COMPANYDEALERCUSCODE",
				"companyDealerCustomerCode");
		DBSDealerPOMSql.hash.put("COMPANYOID", "companyOID");
	}

	protected DBSDealerPOM() {
		super(1);
		dBSDealerPOMLevel = 0;
		sqlClasses[dBSDealerPOMLevel] = DBSDealerPOMSql.class;
		sqlHistClasses[dBSDealerPOMLevel] = DBSDealerPOMHistSql.class;
		pomDataClasses[dBSDealerPOMLevel] = DBSDealerPOMData.class;
	}

	protected DBSDealerPOM(int level) {
		super(level + 1);
		dBSDealerPOMLevel = level;
		sqlClasses[dBSDealerPOMLevel] = DBSDealerPOMSql.class;
		sqlHistClasses[dBSDealerPOMLevel] = DBSDealerPOMHistSql.class;
		pomDataClasses[dBSDealerPOMLevel] = DBSDealerPOMData.class;
	}

	protected int dBSDealerPOMLevel;
	private static final long dBSDealerPOMPID = 3000013147800026L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEALER_DEF";

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes, dBSDealerPOMLevel,
				POMConstants.MANY, true, false);
	}

	public void readByCompanyOID(long companyOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes, dBSDealerPOMLevel,
				POMConstants.MANY, true, false);
	}

	public boolean readByCompanyDealerCustCode(long companyOID,
			String companyDealerCustomerCode) throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				companyDealerCustomerCode };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyDealerCustCodeSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readByCompanyDealerCustCode(long companyOID,
			String companyDealerCustomerCode, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				companyDealerCustomerCode,
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyDealerCustCodeSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.ONE, true, false);
	}

	public void readByCustomerNumberandCompanyOID(int customerNumber,
			long companyOID) throws CBException {
		Object[] params = { new java.lang.Integer(customerNumber),
				new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Integer.class, java.lang.Long.class };
		load("findByCustomerNumberandCompanyOIDSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByCustomerNumberandCompanyOID(int customerNumber,
			long companyOID, long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Integer(customerNumber),
				new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Integer.class, java.lang.Long.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCustomerNumberandCompanyOIDSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByCompanyOIDAndActive(long companyOID, boolean active)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID), active };
		Class[] paramTypes = { java.lang.Long.class, boolean.class };
		load("findByCompanyOIDAndActiveSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByCompanyOIDAndActive(long companyOID, boolean active,
			long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID), active,
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, boolean.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDAndActiveSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByAllRecords() throws CBException {
		Object[] params = {};
		Class[] paramTypes = {};
		load("findByAllRecordsSql", params, paramTypes, dBSDealerPOMLevel,
				POMConstants.MANY, true, false);
	}

	public void readByAllRecords(long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByAllRecordsSql", params, paramTypes, dBSDealerPOMLevel,
				POMConstants.MANY, true, false);
	}

	public boolean readByCompanyOidAndDealerCustomerCode(long companyOID,
			String companyDealerCustomerCode) throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				companyDealerCustomerCode };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOidAndDealerCustomerCodeSql", params,
				paramTypes, dBSDealerPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOidAndDealerCustomerCode(long companyOID,
			String companyDealerCustomerCode, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				companyDealerCustomerCode,
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOidAndDealerCustomerCodeSql", params,
				paramTypes, dBSDealerPOMLevel, POMConstants.ONE, true, false);
	}

	public void readDealersByCompanyOid(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findDealersByCompanyOidSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public void readDealersByCompanyOid(long companyOID,
			long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findDealersByCompanyOidSql", params, paramTypes,
				dBSDealerPOMLevel, POMConstants.MANY, true, false);
	}

	public static DBSDealerPOM newDBSDealerPOM() {
		DBSDealerPOM pom = (DBSDealerPOM) POMPool.getFreePOM(dBSDealerPOMPID);
		if (pom == null) {
			pom = new DBSDealerPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDealerPOMData getDBSDealerPOMData() {
		return (DBSDealerPOMData) datas[dBSDealerPOMLevel];
	}

	public DBSDealerPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDealerPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes, dBSDealerPOMLevel,
				POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSDealerPOMPID;
	}

	public void setDBSDealerPOMData(DBSDealerPOMData data) {
		datas[dBSDealerPOMLevel] = data;
		isDirty[dBSDealerPOMLevel] = true;
	}

	public void updateDBSDealerPOMData(DBSDealerPOMData data)
			throws CBException {
		updateDBSDealerPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSDealerPOMData(DBSDealerPOMData data,
			boolean updateHistory) throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSDealerPOMData(data);
		update(updateHistory);
	}

	public void createDBSDealerPOMData(DBSDealerPOMData data)
			throws CBException {
		setDBSDealerPOMData(data);
		create();
	}
}
