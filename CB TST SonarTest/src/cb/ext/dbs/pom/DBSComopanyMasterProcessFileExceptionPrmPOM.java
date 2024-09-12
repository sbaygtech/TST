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

public class DBSComopanyMasterProcessFileExceptionPrmPOM extends POM {
	public static class DBSComopanyMasterProcessFileExceptionPrmPOMSql
			implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_MASTER_PROC_EXCEP_PRM (OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_MASTER_PROC_EXCEP_PRM SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, METHODCODE=?, EXCEPTIONCODE=?, EXCEPTIONDESCRIPTION=?, ACTIVE=?, ADKDESCRIPTION=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_MASTER_PROC_EXCEP_PRM WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDAndActiveSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_MASTER_PROC_EXCEP_PRM WHERE COMPANYOID = ? AND ACTIVE = ? AND STATUS=1 ";
		public final static String findByCompanyOidAndMethodCodeActiveSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_MASTER_PROC_EXCEP_PRM WHERE COMPANYOID = ? AND METHODCODE = ? AND ACTIVE = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSComopanyMasterProcessFileExceptionPrmPOMSql() {
		};
	}

	static {
		DBSComopanyMasterProcessFileExceptionPrmPOMSql.hash = new java.util.Hashtable();
		DBSComopanyMasterProcessFileExceptionPrmPOMSql.hash.put("METHODCODE",
				"methodCode");
		DBSComopanyMasterProcessFileExceptionPrmPOMSql.hash.put("ACTIVE",
				"active");
		DBSComopanyMasterProcessFileExceptionPrmPOMSql.hash.put("COMPANYOID",
				"companyOID");
	}

	protected DBSComopanyMasterProcessFileExceptionPrmPOM() {
		super(1);
		dBSComopanyMasterProcessFileExceptionPrmPOMLevel = 0;
		sqlClasses[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = DBSComopanyMasterProcessFileExceptionPrmPOMSql.class;
		pomDataClasses[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = DBSComopanyMasterProcessFileExceptionPrmPOMData.class;
	}

	protected DBSComopanyMasterProcessFileExceptionPrmPOM(int level) {
		super(level + 1);
		dBSComopanyMasterProcessFileExceptionPrmPOMLevel = level;
		sqlClasses[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = DBSComopanyMasterProcessFileExceptionPrmPOMSql.class;
		pomDataClasses[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = DBSComopanyMasterProcessFileExceptionPrmPOMData.class;
	}

	protected int dBSComopanyMasterProcessFileExceptionPrmPOMLevel;
	private static final long dBSComopanyMasterProcessFileExceptionPrmPOMPID = 3000012345700013L;

	public void readByCompanyOIDAndActive(long companyOID, int active)
			throws CBException {
		Object[] params = { new java.lang.Long(companyOID),
				new java.lang.Integer(active) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Integer.class };
		load("findByCompanyOIDAndActiveSql", params, paramTypes,
				dBSComopanyMasterProcessFileExceptionPrmPOMLevel,
				POMConstants.MANY, true, false);
	}

	public boolean readByCompanyOidAndMethodCodeActive(long companyOID,
			String methodCode, int active) throws CBException {
		Object[] params = { new java.lang.Long(companyOID), methodCode,
				new java.lang.Integer(active) };
		Class[] paramTypes = { java.lang.Long.class, String.class,
				java.lang.Integer.class };
		return load("findByCompanyOidAndMethodCodeActiveSql", params,
				paramTypes, dBSComopanyMasterProcessFileExceptionPrmPOMLevel,
				POMConstants.ONE, true, false);
	}

	public static DBSComopanyMasterProcessFileExceptionPrmPOM newDBSComopanyMasterProcessFileExceptionPrmPOM() {
		DBSComopanyMasterProcessFileExceptionPrmPOM pom = (DBSComopanyMasterProcessFileExceptionPrmPOM) POMPool
				.getFreePOM(dBSComopanyMasterProcessFileExceptionPrmPOMPID);
		if (pom == null) {
			pom = new DBSComopanyMasterProcessFileExceptionPrmPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSComopanyMasterProcessFileExceptionPrmPOMData getDBSComopanyMasterProcessFileExceptionPrmPOMData() {
		return (DBSComopanyMasterProcessFileExceptionPrmPOMData) datas[dBSComopanyMasterProcessFileExceptionPrmPOMLevel];
	}

	public DBSComopanyMasterProcessFileExceptionPrmPOMData findByPrimaryKey(
			long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSComopanyMasterProcessFileExceptionPrmPOMData();
		return null;
	}

	public long getID() {
		return dBSComopanyMasterProcessFileExceptionPrmPOMPID;
	}

	public void setDBSComopanyMasterProcessFileExceptionPrmPOMData(
			DBSComopanyMasterProcessFileExceptionPrmPOMData data) {
		datas[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = data;
		isDirty[dBSComopanyMasterProcessFileExceptionPrmPOMLevel] = true;
	}

	public void updateDBSComopanyMasterProcessFileExceptionPrmPOMData(
			DBSComopanyMasterProcessFileExceptionPrmPOMData data)
			throws CBException {
		setDBSComopanyMasterProcessFileExceptionPrmPOMData(data);
		update();
	}

	public void createDBSComopanyMasterProcessFileExceptionPrmPOMData(
			DBSComopanyMasterProcessFileExceptionPrmPOMData data)
			throws CBException {
		setDBSComopanyMasterProcessFileExceptionPrmPOMData(data);
		create();
	}
}
