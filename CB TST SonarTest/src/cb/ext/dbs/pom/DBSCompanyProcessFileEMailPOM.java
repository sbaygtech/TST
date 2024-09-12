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

public class DBSCompanyProcessFileEMailPOM extends POM {
	public static class DBSCompanyProcessFileEMailPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_PROCESS_FILE_EMAIL_DEF (OID, STATUS, LASTUPDATED, COMPANYOID, EMAILADDRESS)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_PROCESS_FILE_EMAIL_DEF SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, EMAILADDRESS=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, EMAILADDRESS FROM EXT.DBS_PROCESS_FILE_EMAIL_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, EMAILADDRESS FROM EXT.DBS_PROCESS_FILE_EMAIL_DEF WHERE COMPANYOID = ? AND STATUS=1 ";

		public DBSCompanyProcessFileEMailPOMSql() {
		};
	}

	protected DBSCompanyProcessFileEMailPOM() {
		super(1);
		dBSCompanyProcessFileEMailPOMLevel = 0;
		sqlClasses[dBSCompanyProcessFileEMailPOMLevel] = DBSCompanyProcessFileEMailPOMSql.class;
		pomDataClasses[dBSCompanyProcessFileEMailPOMLevel] = DBSCompanyProcessFileEMailPOMData.class;
	}

	protected DBSCompanyProcessFileEMailPOM(int level) {
		super(level + 1);
		dBSCompanyProcessFileEMailPOMLevel = level;
		sqlClasses[dBSCompanyProcessFileEMailPOMLevel] = DBSCompanyProcessFileEMailPOMSql.class;
		pomDataClasses[dBSCompanyProcessFileEMailPOMLevel] = DBSCompanyProcessFileEMailPOMData.class;
	}

	protected int dBSCompanyProcessFileEMailPOMLevel;
	private static final long dBSCompanyProcessFileEMailPOMPID = 3000012253000108L;

	public void readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyProcessFileEMailPOMLevel, POMConstants.MANY, true,
				false);
	}

	public static DBSCompanyProcessFileEMailPOM newDBSCompanyProcessFileEMailPOM() {
		DBSCompanyProcessFileEMailPOM pom = (DBSCompanyProcessFileEMailPOM) POMPool
				.getFreePOM(dBSCompanyProcessFileEMailPOMPID);
		if (pom == null) {
			pom = new DBSCompanyProcessFileEMailPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyProcessFileEMailPOMData getDBSCompanyProcessFileEMailPOMData() {
		return (DBSCompanyProcessFileEMailPOMData) datas[dBSCompanyProcessFileEMailPOMLevel];
	}

	public DBSCompanyProcessFileEMailPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyProcessFileEMailPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyProcessFileEMailPOMPID;
	}

	public void setDBSCompanyProcessFileEMailPOMData(
			DBSCompanyProcessFileEMailPOMData data) {
		datas[dBSCompanyProcessFileEMailPOMLevel] = data;
		isDirty[dBSCompanyProcessFileEMailPOMLevel] = true;
	}

	public void updateDBSCompanyProcessFileEMailPOMData(
			DBSCompanyProcessFileEMailPOMData data) throws CBException {
		setDBSCompanyProcessFileEMailPOMData(data);
		update();
	}

	public void createDBSCompanyProcessFileEMailPOMData(
			DBSCompanyProcessFileEMailPOMData data) throws CBException {
		setDBSCompanyProcessFileEMailPOMData(data);
		create();
	}
}
