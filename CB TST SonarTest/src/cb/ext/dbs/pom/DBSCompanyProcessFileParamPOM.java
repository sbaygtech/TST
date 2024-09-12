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

public class DBSCompanyProcessFileParamPOM extends POM {
	public static class DBSCompanyProcessFileParamPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_PROCESS_FILE_PRM_DEF (OID, STATUS, LASTUPDATED, COMPANYOID, FILEENABLED, MAILINGENABLED, PROCESSFILENAME)  VALUES  (?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_PROCESS_FILE_PRM_DEF SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, FILEENABLED=?, MAILINGENABLED=?, PROCESSFILENAME=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, FILEENABLED, MAILINGENABLED, PROCESSFILENAME FROM EXT.DBS_PROCESS_FILE_PRM_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, FILEENABLED, MAILINGENABLED, PROCESSFILENAME FROM EXT.DBS_PROCESS_FILE_PRM_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyProcessFileParamPOMSql() {
		};
	}

	static {
		DBSCompanyProcessFileParamPOMSql.hash = new java.util.Hashtable();
		DBSCompanyProcessFileParamPOMSql.hash.put("COMPANYOID", "companyOID");
	}

	protected DBSCompanyProcessFileParamPOM() {
		super(1);
		dBSCompanyProcessFileParamPOMLevel = 0;
		sqlClasses[dBSCompanyProcessFileParamPOMLevel] = DBSCompanyProcessFileParamPOMSql.class;
		pomDataClasses[dBSCompanyProcessFileParamPOMLevel] = DBSCompanyProcessFileParamPOMData.class;
	}

	protected DBSCompanyProcessFileParamPOM(int level) {
		super(level + 1);
		dBSCompanyProcessFileParamPOMLevel = level;
		sqlClasses[dBSCompanyProcessFileParamPOMLevel] = DBSCompanyProcessFileParamPOMSql.class;
		pomDataClasses[dBSCompanyProcessFileParamPOMLevel] = DBSCompanyProcessFileParamPOMData.class;
	}

	protected int dBSCompanyProcessFileParamPOMLevel;
	private static final long dBSCompanyProcessFileParamPOMPID = 3000012313300074L;

	public boolean readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyProcessFileParamPOMLevel, POMConstants.ONE, true,
				false);
	}

	public static DBSCompanyProcessFileParamPOM newDBSCompanyProcessFileParamPOM() {
		DBSCompanyProcessFileParamPOM pom = (DBSCompanyProcessFileParamPOM) POMPool
				.getFreePOM(dBSCompanyProcessFileParamPOMPID);
		if (pom == null) {
			pom = new DBSCompanyProcessFileParamPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyProcessFileParamPOMData getDBSCompanyProcessFileParamPOMData() {
		return (DBSCompanyProcessFileParamPOMData) datas[dBSCompanyProcessFileParamPOMLevel];
	}

	public DBSCompanyProcessFileParamPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyProcessFileParamPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyProcessFileParamPOMPID;
	}

	public void setDBSCompanyProcessFileParamPOMData(
			DBSCompanyProcessFileParamPOMData data) {
		datas[dBSCompanyProcessFileParamPOMLevel] = data;
		isDirty[dBSCompanyProcessFileParamPOMLevel] = true;
	}

	public void updateDBSCompanyProcessFileParamPOMData(
			DBSCompanyProcessFileParamPOMData data) throws CBException {
		setDBSCompanyProcessFileParamPOMData(data);
		update();
	}

	public void createDBSCompanyProcessFileParamPOMData(
			DBSCompanyProcessFileParamPOMData data) throws CBException {
		setDBSCompanyProcessFileParamPOMData(data);
		create();
	}
}
