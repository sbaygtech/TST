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

public class DBSCompanyTotalFCDailyPOM extends POM {
	public static class DBSCompanyTotalFCDailyPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMPANY_TOTAL_FC_AMOUNT (OID, STATUS, LASTUPDATED, COMPANYOID, AMOUNT, PROCESSDATE, CURRENCY, PROCESSTIME, EXCHANGERATE, TRYAMOUNT, DEALEROID, MASTEROID, SEQUENCENO)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMPANY_TOTAL_FC_AMOUNT SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, AMOUNT=?, PROCESSDATE=?, CURRENCY=?, PROCESSTIME=?, EXCHANGERATE=?, TRYAMOUNT=?, DEALEROID=?, MASTEROID=?, SEQUENCENO=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, AMOUNT, PROCESSDATE, CURRENCY, PROCESSTIME, EXCHANGERATE, TRYAMOUNT, DEALEROID, MASTEROID, SEQUENCENO FROM EXT.DBS_COMPANY_TOTAL_FC_AMOUNT WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, AMOUNT, PROCESSDATE, CURRENCY, PROCESSTIME, EXCHANGERATE, TRYAMOUNT, DEALEROID, MASTEROID, SEQUENCENO FROM EXT.DBS_COMPANY_TOTAL_FC_AMOUNT WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyTotalFCDailyPOMSql() {
		};
	}

	static {
		DBSCompanyTotalFCDailyPOMSql.hash = new java.util.Hashtable();
		DBSCompanyTotalFCDailyPOMSql.hash.put("COMPANYOID", "companyOID");
	}

	protected DBSCompanyTotalFCDailyPOM() {
		super(1);
		dBSCompanyTotalFCDailyPOMLevel = 0;
		sqlClasses[dBSCompanyTotalFCDailyPOMLevel] = DBSCompanyTotalFCDailyPOMSql.class;
		pomDataClasses[dBSCompanyTotalFCDailyPOMLevel] = DBSCompanyTotalFCDailyPOMData.class;
	}

	protected DBSCompanyTotalFCDailyPOM(int level) {
		super(level + 1);
		dBSCompanyTotalFCDailyPOMLevel = level;
		sqlClasses[dBSCompanyTotalFCDailyPOMLevel] = DBSCompanyTotalFCDailyPOMSql.class;
		pomDataClasses[dBSCompanyTotalFCDailyPOMLevel] = DBSCompanyTotalFCDailyPOMData.class;
	}

	protected int dBSCompanyTotalFCDailyPOMLevel;
	private static final long dBSCompanyTotalFCDailyPOMPID = 3000010411000151L;

	public boolean readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyTotalFCDailyPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSCompanyTotalFCDailyPOM newDBSCompanyTotalFCDailyPOM() {
		DBSCompanyTotalFCDailyPOM pom = (DBSCompanyTotalFCDailyPOM) POMPool
				.getFreePOM(dBSCompanyTotalFCDailyPOMPID);
		if (pom == null) {
			pom = new DBSCompanyTotalFCDailyPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyTotalFCDailyPOMData getDBSCompanyTotalFCDailyPOMData() {
		return (DBSCompanyTotalFCDailyPOMData) datas[dBSCompanyTotalFCDailyPOMLevel];
	}

	public DBSCompanyTotalFCDailyPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyTotalFCDailyPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyTotalFCDailyPOMPID;
	}

	public void setDBSCompanyTotalFCDailyPOMData(
			DBSCompanyTotalFCDailyPOMData data) {
		datas[dBSCompanyTotalFCDailyPOMLevel] = data;
		isDirty[dBSCompanyTotalFCDailyPOMLevel] = true;
	}

	public void updateDBSCompanyTotalFCDailyPOMData(
			DBSCompanyTotalFCDailyPOMData data) throws CBException {
		setDBSCompanyTotalFCDailyPOMData(data);
		update();
	}

	public void createDBSCompanyTotalFCDailyPOMData(
			DBSCompanyTotalFCDailyPOMData data) throws CBException {
		setDBSCompanyTotalFCDailyPOMData(data);
		create();
	}
}
