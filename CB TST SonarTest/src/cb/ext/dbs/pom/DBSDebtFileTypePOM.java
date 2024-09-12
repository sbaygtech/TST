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

public class DBSDebtFileTypePOM extends POM {
	public static class DBSDebtFileTypePOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILE_TYPE_PRM (OID, STATUS, LASTUPDATED, FILECODE, DEBTFILETYPE)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILE_TYPE_PRM SET STATUS=?, LASTUPDATED=?, FILECODE=?, DEBTFILETYPE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, FILECODE, DEBTFILETYPE FROM EXT.DBS_DEBTFILE_TYPE_PRM WHERE OID = ? AND STATUS=1";

		public DBSDebtFileTypePOMSql() {
		};
	}

	protected DBSDebtFileTypePOM() {
		super(1);
		dBSDebtFileTypePOMLevel = 0;
		sqlClasses[dBSDebtFileTypePOMLevel] = DBSDebtFileTypePOMSql.class;
		pomDataClasses[dBSDebtFileTypePOMLevel] = DBSDebtFileTypePOMData.class;
	}

	protected DBSDebtFileTypePOM(int level) {
		super(level + 1);
		dBSDebtFileTypePOMLevel = level;
		sqlClasses[dBSDebtFileTypePOMLevel] = DBSDebtFileTypePOMSql.class;
		pomDataClasses[dBSDebtFileTypePOMLevel] = DBSDebtFileTypePOMData.class;
	}

	protected int dBSDebtFileTypePOMLevel;
	private static final long dBSDebtFileTypePOMPID = 3000011868500069L;

	public static DBSDebtFileTypePOM newDBSDebtFileTypePOM() {
		DBSDebtFileTypePOM pom = (DBSDebtFileTypePOM) POMPool
				.getFreePOM(dBSDebtFileTypePOMPID);
		if (pom == null) {
			pom = new DBSDebtFileTypePOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDebtFileTypePOMData getDBSDebtFileTypePOMData() {
		return (DBSDebtFileTypePOMData) datas[dBSDebtFileTypePOMLevel];
	}

	public DBSDebtFileTypePOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDebtFileTypePOMData();
		return null;
	}

	public long getID() {
		return dBSDebtFileTypePOMPID;
	}

	public void setDBSDebtFileTypePOMData(DBSDebtFileTypePOMData data) {
		datas[dBSDebtFileTypePOMLevel] = data;
		isDirty[dBSDebtFileTypePOMLevel] = true;
	}

	public void updateDBSDebtFileTypePOMData(DBSDebtFileTypePOMData data)
			throws CBException {
		setDBSDebtFileTypePOMData(data);
		update();
	}

	public void createDBSDebtFileTypePOMData(DBSDebtFileTypePOMData data)
			throws CBException {
		setDBSDebtFileTypePOMData(data);
		create();
	}
}
