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

public class DBSCompanyDebtFileRecordTypeParamPOM extends POM {
	public static class DBSCompanyDebtFileRecordTypeParamPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILE_RECORDTYPE_PRM (OID, STATUS, LASTUPDATED, COMPANYOID, MAPRECORDTYPE, COMPANYRECORDTYPE)  VALUES  (?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILE_RECORDTYPE_PRM SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, MAPRECORDTYPE=?, COMPANYRECORDTYPE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, MAPRECORDTYPE, COMPANYRECORDTYPE FROM EXT.DBS_DEBTFILE_RECORDTYPE_PRM WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDAndRecordTypeSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, MAPRECORDTYPE, COMPANYRECORDTYPE FROM EXT.DBS_DEBTFILE_RECORDTYPE_PRM WHERE COMPANYOID = ? AND COMPANYRECORDTYPE = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileRecordTypeParamPOMSql() {
		};
	}

	static {
		DBSCompanyDebtFileRecordTypeParamPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileRecordTypeParamPOMSql.hash.put("COMPANYRECORDTYPE",
				"companyRecordType");
		DBSCompanyDebtFileRecordTypeParamPOMSql.hash.put("COMPANYOID",
				"companyOID");
	}

	protected DBSCompanyDebtFileRecordTypeParamPOM() {
		super(1);
		dBSCompanyDebtFileRecordTypeParamPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileRecordTypeParamPOMLevel] = DBSCompanyDebtFileRecordTypeParamPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileRecordTypeParamPOMLevel] = DBSCompanyDebtFileRecordTypeParamPOMData.class;
	}

	protected DBSCompanyDebtFileRecordTypeParamPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileRecordTypeParamPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileRecordTypeParamPOMLevel] = DBSCompanyDebtFileRecordTypeParamPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileRecordTypeParamPOMLevel] = DBSCompanyDebtFileRecordTypeParamPOMData.class;
	}

	protected int dBSCompanyDebtFileRecordTypeParamPOMLevel;
	private static final long dBSCompanyDebtFileRecordTypeParamPOMPID = 3000012193700021L;

	public boolean readByCompanyOIDAndRecordType(long companyOID,
			String companyRecordType) throws CBException {
		Object[] params = { new java.lang.Long(companyOID), companyRecordType };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOIDAndRecordTypeSql", params, paramTypes,
				dBSCompanyDebtFileRecordTypeParamPOMLevel, POMConstants.ONE,
				true, false);
	}

	public static DBSCompanyDebtFileRecordTypeParamPOM newDBSCompanyDebtFileRecordTypeParamPOM() {
		DBSCompanyDebtFileRecordTypeParamPOM pom = (DBSCompanyDebtFileRecordTypeParamPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileRecordTypeParamPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileRecordTypeParamPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileRecordTypeParamPOMData getDBSCompanyDebtFileRecordTypeParamPOMData() {
		return (DBSCompanyDebtFileRecordTypeParamPOMData) datas[dBSCompanyDebtFileRecordTypeParamPOMLevel];
	}

	public DBSCompanyDebtFileRecordTypeParamPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileRecordTypeParamPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileRecordTypeParamPOMPID;
	}

	public void setDBSCompanyDebtFileRecordTypeParamPOMData(
			DBSCompanyDebtFileRecordTypeParamPOMData data) {
		datas[dBSCompanyDebtFileRecordTypeParamPOMLevel] = data;
		isDirty[dBSCompanyDebtFileRecordTypeParamPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileRecordTypeParamPOMData(
			DBSCompanyDebtFileRecordTypeParamPOMData data) throws CBException {
		setDBSCompanyDebtFileRecordTypeParamPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileRecordTypeParamPOMData(
			DBSCompanyDebtFileRecordTypeParamPOMData data) throws CBException {
		setDBSCompanyDebtFileRecordTypeParamPOMData(data);
		create();
	}
}
