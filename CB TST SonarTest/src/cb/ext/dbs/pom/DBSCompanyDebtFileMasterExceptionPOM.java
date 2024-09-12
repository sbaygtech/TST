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

public class DBSCompanyDebtFileMasterExceptionPOM extends POM {
	public static class DBSCompanyDebtFileMasterExceptionPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILE_MASTER_EXCEP (OID, STATUS, LASTUPDATED, MASTEROID, EXCEPTIONCODE, EXCEPTIONDESCRIPTION)  VALUES  (?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILE_MASTER_EXCEP SET STATUS=?, LASTUPDATED=?, MASTEROID=?, EXCEPTIONCODE=?, EXCEPTIONDESCRIPTION=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MASTEROID, EXCEPTIONCODE, EXCEPTIONDESCRIPTION FROM EXT.DBS_DEBTFILE_MASTER_EXCEP WHERE OID = ? AND STATUS=1";

		public DBSCompanyDebtFileMasterExceptionPOMSql() {
		};
	}

	protected DBSCompanyDebtFileMasterExceptionPOM() {
		super(1);
		dBSCompanyDebtFileMasterExceptionPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileMasterExceptionPOMLevel] = DBSCompanyDebtFileMasterExceptionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileMasterExceptionPOMLevel] = DBSCompanyDebtFileMasterExceptionPOMData.class;
	}

	protected DBSCompanyDebtFileMasterExceptionPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileMasterExceptionPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileMasterExceptionPOMLevel] = DBSCompanyDebtFileMasterExceptionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileMasterExceptionPOMLevel] = DBSCompanyDebtFileMasterExceptionPOMData.class;
	}

	protected int dBSCompanyDebtFileMasterExceptionPOMLevel;
	private static final long dBSCompanyDebtFileMasterExceptionPOMPID = 3000012253000126L;

	public static DBSCompanyDebtFileMasterExceptionPOM newDBSCompanyDebtFileMasterExceptionPOM() {
		DBSCompanyDebtFileMasterExceptionPOM pom = (DBSCompanyDebtFileMasterExceptionPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileMasterExceptionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileMasterExceptionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileMasterExceptionPOMData getDBSCompanyDebtFileMasterExceptionPOMData() {
		return (DBSCompanyDebtFileMasterExceptionPOMData) datas[dBSCompanyDebtFileMasterExceptionPOMLevel];
	}

	public DBSCompanyDebtFileMasterExceptionPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileMasterExceptionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileMasterExceptionPOMPID;
	}

	public void setDBSCompanyDebtFileMasterExceptionPOMData(
			DBSCompanyDebtFileMasterExceptionPOMData data) {
		datas[dBSCompanyDebtFileMasterExceptionPOMLevel] = data;
		isDirty[dBSCompanyDebtFileMasterExceptionPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileMasterExceptionPOMData(
			DBSCompanyDebtFileMasterExceptionPOMData data) throws CBException {
		setDBSCompanyDebtFileMasterExceptionPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileMasterExceptionPOMData(
			DBSCompanyDebtFileMasterExceptionPOMData data) throws CBException {
		setDBSCompanyDebtFileMasterExceptionPOMData(data);
		create();
	}
}
