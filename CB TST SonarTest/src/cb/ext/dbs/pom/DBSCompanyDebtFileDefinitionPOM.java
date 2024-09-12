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

public class DBSCompanyDebtFileDefinitionPOM extends POM {
	public static class DBSCompanyDebtFileDefinitionPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBTFILE_DEF (OID, STATUS, LASTUPDATED, COMPANYOID, FILETYPECODE)  VALUES  (?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBTFILE_DEF SET STATUS=?, LASTUPDATED=?, COMPANYOID=?, FILETYPECODE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, COMPANYOID, FILETYPECODE FROM EXT.DBS_DEBTFILE_DEF WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, COMPANYOID, FILETYPECODE FROM EXT.DBS_DEBTFILE_DEF WHERE COMPANYOID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileDefinitionPOMSql() {
		};
	}

	static {
		DBSCompanyDebtFileDefinitionPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileDefinitionPOMSql.hash.put("COMPANYOID", "companyOID");
	}

	protected DBSCompanyDebtFileDefinitionPOM() {
		super(1);
		dBSCompanyDebtFileDefinitionPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileDefinitionPOMLevel] = DBSCompanyDebtFileDefinitionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDefinitionPOMLevel] = DBSCompanyDebtFileDefinitionPOMData.class;
	}

	protected DBSCompanyDebtFileDefinitionPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileDefinitionPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileDefinitionPOMLevel] = DBSCompanyDebtFileDefinitionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDefinitionPOMLevel] = DBSCompanyDebtFileDefinitionPOMData.class;
	}

	protected int dBSCompanyDebtFileDefinitionPOMLevel;
	private static final long dBSCompanyDebtFileDefinitionPOMPID = 3000011868500085L;

	public boolean readByCompanyOID(long companyOID) throws CBException {
		Object[] params = { new java.lang.Long(companyOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findByCompanyOIDSql", params, paramTypes,
				dBSCompanyDebtFileDefinitionPOMLevel, POMConstants.ONE, true,
				false);
	}

	public static DBSCompanyDebtFileDefinitionPOM newDBSCompanyDebtFileDefinitionPOM() {
		DBSCompanyDebtFileDefinitionPOM pom = (DBSCompanyDebtFileDefinitionPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileDefinitionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileDefinitionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileDefinitionPOMData getDBSCompanyDebtFileDefinitionPOMData() {
		return (DBSCompanyDebtFileDefinitionPOMData) datas[dBSCompanyDebtFileDefinitionPOMLevel];
	}

	public DBSCompanyDebtFileDefinitionPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileDefinitionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileDefinitionPOMPID;
	}

	public void setDBSCompanyDebtFileDefinitionPOMData(
			DBSCompanyDebtFileDefinitionPOMData data) {
		datas[dBSCompanyDebtFileDefinitionPOMLevel] = data;
		isDirty[dBSCompanyDebtFileDefinitionPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileDefinitionPOMData(
			DBSCompanyDebtFileDefinitionPOMData data) throws CBException {
		setDBSCompanyDebtFileDefinitionPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileDefinitionPOMData(
			DBSCompanyDebtFileDefinitionPOMData data) throws CBException {
		setDBSCompanyDebtFileDefinitionPOMData(data);
		create();
	}
}
