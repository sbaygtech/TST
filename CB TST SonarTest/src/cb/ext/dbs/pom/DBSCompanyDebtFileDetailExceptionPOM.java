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

public class DBSCompanyDebtFileDetailExceptionPOM extends POM {
	public static class DBSCompanyDebtFileDetailExceptionPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILE_DETAIL_EXCEP (OID, STATUS, LASTUPDATED, DETAILOID, EXCEPTIONCODE, EXCEPTIONDESCRIPTION)  VALUES  (?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILE_DETAIL_EXCEP SET STATUS=?, LASTUPDATED=?, DETAILOID=?, EXCEPTIONCODE=?, EXCEPTIONDESCRIPTION=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DETAILOID, EXCEPTIONCODE, EXCEPTIONDESCRIPTION FROM EXT.DBS_DEBTFILE_DETAIL_EXCEP WHERE OID = ? AND STATUS=1";

		public DBSCompanyDebtFileDetailExceptionPOMSql() {
		};
	}

	protected DBSCompanyDebtFileDetailExceptionPOM() {
		super(1);
		dBSCompanyDebtFileDetailExceptionPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileDetailExceptionPOMLevel] = DBSCompanyDebtFileDetailExceptionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailExceptionPOMLevel] = DBSCompanyDebtFileDetailExceptionPOMData.class;
	}

	protected DBSCompanyDebtFileDetailExceptionPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileDetailExceptionPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileDetailExceptionPOMLevel] = DBSCompanyDebtFileDetailExceptionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailExceptionPOMLevel] = DBSCompanyDebtFileDetailExceptionPOMData.class;
	}

	protected int dBSCompanyDebtFileDetailExceptionPOMLevel;
	private static final long dBSCompanyDebtFileDetailExceptionPOMPID = 3000012233900037L;

	public static DBSCompanyDebtFileDetailExceptionPOM newDBSCompanyDebtFileDetailExceptionPOM() {
		DBSCompanyDebtFileDetailExceptionPOM pom = (DBSCompanyDebtFileDetailExceptionPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileDetailExceptionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileDetailExceptionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileDetailExceptionPOMData getDBSCompanyDebtFileDetailExceptionPOMData() {
		return (DBSCompanyDebtFileDetailExceptionPOMData) datas[dBSCompanyDebtFileDetailExceptionPOMLevel];
	}

	public DBSCompanyDebtFileDetailExceptionPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileDetailExceptionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileDetailExceptionPOMPID;
	}

	public void setDBSCompanyDebtFileDetailExceptionPOMData(
			DBSCompanyDebtFileDetailExceptionPOMData data) {
		datas[dBSCompanyDebtFileDetailExceptionPOMLevel] = data;
		isDirty[dBSCompanyDebtFileDetailExceptionPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileDetailExceptionPOMData(
			DBSCompanyDebtFileDetailExceptionPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailExceptionPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileDetailExceptionPOMData(
			DBSCompanyDebtFileDetailExceptionPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailExceptionPOMData(data);
		create();
	}
}
