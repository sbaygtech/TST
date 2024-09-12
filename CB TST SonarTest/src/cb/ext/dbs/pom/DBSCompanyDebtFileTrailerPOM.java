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

public class DBSCompanyDebtFileTrailerPOM extends POM {
	public static class DBSCompanyDebtFileTrailerPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMP_DEBT_FILE_TRAILER (OID, STATUS, LASTUPDATED, INVOICETOTALCOUNTNEW, INVOICETOTALAMOUNTNEW, INVOICETOTALCOUNTCANCEL, INVOICETOTALAMOUNTCANCEL, INVOICETOTALCOUNTUPDATE, INVOICETOTALAMOUNTUPDATE, HEADEROID)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMP_DEBT_FILE_TRAILER SET STATUS=?, LASTUPDATED=?, INVOICETOTALCOUNTNEW=?, INVOICETOTALAMOUNTNEW=?, INVOICETOTALCOUNTCANCEL=?, INVOICETOTALAMOUNTCANCEL=?, INVOICETOTALCOUNTUPDATE=?, INVOICETOTALAMOUNTUPDATE=?, HEADEROID=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, INVOICETOTALCOUNTNEW, INVOICETOTALAMOUNTNEW, INVOICETOTALCOUNTCANCEL, INVOICETOTALAMOUNTCANCEL, INVOICETOTALCOUNTUPDATE, INVOICETOTALAMOUNTUPDATE, HEADEROID FROM EXT.DBS_COMP_DEBT_FILE_TRAILER WHERE OID = ? AND STATUS=1";

		public DBSCompanyDebtFileTrailerPOMSql() {
		};
	}

	protected DBSCompanyDebtFileTrailerPOM() {
		super(1);
		dBSCompanyDebtFileTrailerPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileTrailerPOMLevel] = DBSCompanyDebtFileTrailerPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileTrailerPOMLevel] = DBSCompanyDebtFileTrailerPOMData.class;
	}

	protected DBSCompanyDebtFileTrailerPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileTrailerPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileTrailerPOMLevel] = DBSCompanyDebtFileTrailerPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileTrailerPOMLevel] = DBSCompanyDebtFileTrailerPOMData.class;
	}

	protected int dBSCompanyDebtFileTrailerPOMLevel;
	private static final long dBSCompanyDebtFileTrailerPOMPID = 3000013271000001L;

	public static DBSCompanyDebtFileTrailerPOM newDBSCompanyDebtFileTrailerPOM() {
		DBSCompanyDebtFileTrailerPOM pom = (DBSCompanyDebtFileTrailerPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileTrailerPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileTrailerPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileTrailerPOMData getDBSCompanyDebtFileTrailerPOMData() {
		return (DBSCompanyDebtFileTrailerPOMData) datas[dBSCompanyDebtFileTrailerPOMLevel];
	}

	public DBSCompanyDebtFileTrailerPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileTrailerPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileTrailerPOMPID;
	}

	public void setDBSCompanyDebtFileTrailerPOMData(
			DBSCompanyDebtFileTrailerPOMData data) {
		datas[dBSCompanyDebtFileTrailerPOMLevel] = data;
		isDirty[dBSCompanyDebtFileTrailerPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileTrailerPOMData(
			DBSCompanyDebtFileTrailerPOMData data) throws CBException {
		setDBSCompanyDebtFileTrailerPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileTrailerPOMData(
			DBSCompanyDebtFileTrailerPOMData data) throws CBException {
		setDBSCompanyDebtFileTrailerPOMData(data);
		create();
	}
}
