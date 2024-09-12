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

public class DBSCompanyDebtFileResultFileExceptionPrmPOM extends POM {
	public static class DBSCompanyDebtFileResultFileExceptionPrmPOMSql
			implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBT_RSLT_EXCEPTION_PRM (OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBT_RSLT_EXCEPTION_PRM SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, METHODCODE=?, EXCEPTIONCODE=?, EXCEPTIONDESCRIPTION=?, ACTIVE=?, ADKDESCRIPTION=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_DEBT_RSLT_EXCEPTION_PRM WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOidAndMethodCodeActiveSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_DEBT_RSLT_EXCEPTION_PRM WHERE COMPANYOID = ? AND METHODCODE = ? AND ACTIVE = 1 AND STATUS=1 ";
		public final static String findByCompanyOIDAndActiveSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, METHODCODE, EXCEPTIONCODE, EXCEPTIONDESCRIPTION, ACTIVE, ADKDESCRIPTION FROM EXT.DBS_DEBT_RSLT_EXCEPTION_PRM WHERE COMPANYOID = ? AND ACTIVE = 1 AND STATUS=1 ";

		public DBSCompanyDebtFileResultFileExceptionPrmPOMSql() {
		};
	}

	protected DBSCompanyDebtFileResultFileExceptionPrmPOM() {
		super(1);
		dBSCompanyDebtFileResultFileExceptionPrmPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = DBSCompanyDebtFileResultFileExceptionPrmPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = DBSCompanyDebtFileResultFileExceptionPrmPOMData.class;
	}

	protected DBSCompanyDebtFileResultFileExceptionPrmPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileResultFileExceptionPrmPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = DBSCompanyDebtFileResultFileExceptionPrmPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = DBSCompanyDebtFileResultFileExceptionPrmPOMData.class;
	}

	protected int dBSCompanyDebtFileResultFileExceptionPrmPOMLevel;
	private static final long dBSCompanyDebtFileResultFileExceptionPrmPOMPID = 3000012345700001L;

	public void readByCompanyOidAndMethodCodeActive(long companyOID,
			String methodCode) throws CBException {
		Object[] params = { new java.lang.Long(companyOID), methodCode };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		load("findByCompanyOidAndMethodCodeActiveSql", params, paramTypes,
				dBSCompanyDebtFileResultFileExceptionPrmPOMLevel,
				POMConstants.MANY, true, false);
	}

	public void readByCompanyOIDAndActive(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDAndActiveSql", params, paramTypes,
				dBSCompanyDebtFileResultFileExceptionPrmPOMLevel,
				POMConstants.MANY, true, false);
	}

	public static DBSCompanyDebtFileResultFileExceptionPrmPOM newDBSCompanyDebtFileResultFileExceptionPrmPOM() {
		DBSCompanyDebtFileResultFileExceptionPrmPOM pom = (DBSCompanyDebtFileResultFileExceptionPrmPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileResultFileExceptionPrmPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileResultFileExceptionPrmPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileResultFileExceptionPrmPOMData getDBSCompanyDebtFileResultFileExceptionPrmPOMData() {
		return (DBSCompanyDebtFileResultFileExceptionPrmPOMData) datas[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel];
	}

	public DBSCompanyDebtFileResultFileExceptionPrmPOMData findByPrimaryKey(
			long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileResultFileExceptionPrmPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileResultFileExceptionPrmPOMPID;
	}

	public void setDBSCompanyDebtFileResultFileExceptionPrmPOMData(
			DBSCompanyDebtFileResultFileExceptionPrmPOMData data) {
		datas[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = data;
		isDirty[dBSCompanyDebtFileResultFileExceptionPrmPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileResultFileExceptionPrmPOMData(
			DBSCompanyDebtFileResultFileExceptionPrmPOMData data)
			throws CBException {
		setDBSCompanyDebtFileResultFileExceptionPrmPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileResultFileExceptionPrmPOMData(
			DBSCompanyDebtFileResultFileExceptionPrmPOMData data)
			throws CBException {
		setDBSCompanyDebtFileResultFileExceptionPrmPOMData(data);
		create();
	}
}
