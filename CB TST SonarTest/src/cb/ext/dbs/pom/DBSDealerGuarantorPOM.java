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

public class DBSDealerGuarantorPOM extends POM {
	public static class DBSDealerGuarantorPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEALER_GUARANTOR (OID, STATUS, LASTUPDATED, COMPANYOID, DEALEROID, STARTDATE, FINISHDATE, ACTIVE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEALER_GUARANTOR SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, DEALEROID=?, STARTDATE=?, FINISHDATE=?, ACTIVE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, DEALEROID, STARTDATE, FINISHDATE, ACTIVE FROM EXT.DBS_DEALER_GUARANTOR WHERE OID = ? AND STATUS=1";
		public final static String findActiveDealerGuarantorSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, DEALEROID, STARTDATE, FINISHDATE, ACTIVE FROM EXT.DBS_DEALER_GUARANTOR WHERE COMPANYOID = ? AND DEALEROID = ? AND ACTIVE = 1 AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSDealerGuarantorPOMSql() {
		};
	}

	public static class DBSDealerGuarantorPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEALER_GUARANTOR SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEALER_GUARANTOR T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEALER_GUARANTOR SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, DEALEROID, STARTDATE, FINISHDATE, ACTIVE,TRANSACTIONOID FROM HST.DBS_DEALER_GUARANTOR WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findActiveDealerGuarantorSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, DEALEROID, STARTDATE, FINISHDATE, ACTIVE ,TRANSACTIONOID FROM HST.DBS_DEALER_GUARANTOR WHERE COMPANYOID = ? AND DEALEROID = ? AND ACTIVE = 1 AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSDealerGuarantorPOMHistSql() {
		};
	}

	static {
		DBSDealerGuarantorPOMSql.hash = new java.util.Hashtable();
		DBSDealerGuarantorPOMSql.hash.put("ACTIVE", "active");
		DBSDealerGuarantorPOMSql.hash.put("COMPANYOID", "companyOid");
		DBSDealerGuarantorPOMSql.hash.put("DEALEROID", "dealerOid");
	}

	protected DBSDealerGuarantorPOM() {
		super(1);
		dBSDealerGuarantorPOMLevel = 0;
		sqlClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMSql.class;
		sqlHistClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMHistSql.class;
		pomDataClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMData.class;
	}

	protected DBSDealerGuarantorPOM(int level) {
		super(level + 1);
		dBSDealerGuarantorPOMLevel = level;
		sqlClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMSql.class;
		sqlHistClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMHistSql.class;
		pomDataClasses[dBSDealerGuarantorPOMLevel] = DBSDealerGuarantorPOMData.class;
	}

	protected int dBSDealerGuarantorPOMLevel;
	private static final long dBSDealerGuarantorPOMPID = 3000013179000035L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEALER_GUARANTOR";

	public boolean readActiveDealerGuarantor(long companyOid, long dealerOid)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOid),
				new java.lang.Long(dealerOid) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		return load("findActiveDealerGuarantorSql", params, paramTypes,
				dBSDealerGuarantorPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readActiveDealerGuarantor(long companyOid, long dealerOid,
			long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOid),
				new java.lang.Long(dealerOid),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class,
				java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findActiveDealerGuarantorSql", params, paramTypes,
				dBSDealerGuarantorPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSDealerGuarantorPOM newDBSDealerGuarantorPOM() {
		DBSDealerGuarantorPOM pom = (DBSDealerGuarantorPOM) POMPool
				.getFreePOM(dBSDealerGuarantorPOMPID);
		if (pom == null) {
			pom = new DBSDealerGuarantorPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDealerGuarantorPOMData getDBSDealerGuarantorPOMData() {
		return (DBSDealerGuarantorPOMData) datas[dBSDealerGuarantorPOMLevel];
	}

	public DBSDealerGuarantorPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDealerGuarantorPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSDealerGuarantorPOMLevel, POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSDealerGuarantorPOMPID;
	}

	public void setDBSDealerGuarantorPOMData(DBSDealerGuarantorPOMData data) {
		datas[dBSDealerGuarantorPOMLevel] = data;
		isDirty[dBSDealerGuarantorPOMLevel] = true;
	}

	public void updateDBSDealerGuarantorPOMData(DBSDealerGuarantorPOMData data)
			throws CBException {
		updateDBSDealerGuarantorPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSDealerGuarantorPOMData(DBSDealerGuarantorPOMData data,
			boolean updateHistory) throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSDealerGuarantorPOMData(data);
		update(updateHistory);
	}

	public void createDBSDealerGuarantorPOMData(DBSDealerGuarantorPOMData data)
			throws CBException {
		setDBSDealerGuarantorPOMData(data);
		create();
	}
}
