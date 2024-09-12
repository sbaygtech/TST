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

public class DBSCompanyParameterPOM extends POM {
	public static class DBSCompanyParameterPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMPANY_PARAMETERS (OID, STATUS, LASTUPDATED, COMPANYOID, KEY, VALUE)  VALUES  (?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMPANY_PARAMETERS SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, KEY=?, VALUE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, KEY, VALUE FROM EXT.DBS_COMPANY_PARAMETERS WHERE OID = ? AND STATUS=1";
		public final static String findCompanyParameterValueByKeySql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, KEY, VALUE FROM EXT.DBS_COMPANY_PARAMETERS WHERE COMPANYOID = ? AND KEY = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyParameterPOMSql() {
		};
	}

	static {
		DBSCompanyParameterPOMSql.hash = new java.util.Hashtable();
		DBSCompanyParameterPOMSql.hash.put("KEY", "key");
		DBSCompanyParameterPOMSql.hash.put("COMPANYOID", "companyOid");
	}

	protected DBSCompanyParameterPOM() {
		super(1);
		dBSCompanyParameterPOMLevel = 0;
		sqlClasses[dBSCompanyParameterPOMLevel] = DBSCompanyParameterPOMSql.class;
		pomDataClasses[dBSCompanyParameterPOMLevel] = DBSCompanyParameterPOMData.class;
	}

	protected DBSCompanyParameterPOM(int level) {
		super(level + 1);
		dBSCompanyParameterPOMLevel = level;
		sqlClasses[dBSCompanyParameterPOMLevel] = DBSCompanyParameterPOMSql.class;
		pomDataClasses[dBSCompanyParameterPOMLevel] = DBSCompanyParameterPOMData.class;
	}

	protected int dBSCompanyParameterPOMLevel;
	private static final long dBSCompanyParameterPOMPID = 3000012664600017L;

	public boolean readCompanyParameterValueByKey(long companyOid, String key)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOid), key };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findCompanyParameterValueByKeySql", params, paramTypes,
				dBSCompanyParameterPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSCompanyParameterPOM newDBSCompanyParameterPOM() {
		DBSCompanyParameterPOM pom = (DBSCompanyParameterPOM) POMPool
				.getFreePOM(dBSCompanyParameterPOMPID);
		if (pom == null) {
			pom = new DBSCompanyParameterPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyParameterPOMData getDBSCompanyParameterPOMData() {
		return (DBSCompanyParameterPOMData) datas[dBSCompanyParameterPOMLevel];
	}

	public DBSCompanyParameterPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyParameterPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyParameterPOMPID;
	}

	public void setDBSCompanyParameterPOMData(DBSCompanyParameterPOMData data) {
		datas[dBSCompanyParameterPOMLevel] = data;
		isDirty[dBSCompanyParameterPOMLevel] = true;
	}

	public void updateDBSCompanyParameterPOMData(DBSCompanyParameterPOMData data)
			throws CBException {
		setDBSCompanyParameterPOMData(data);
		update();
	}

	public void createDBSCompanyParameterPOMData(DBSCompanyParameterPOMData data)
			throws CBException {
		setDBSCompanyParameterPOMData(data);
		create();
	}
}
