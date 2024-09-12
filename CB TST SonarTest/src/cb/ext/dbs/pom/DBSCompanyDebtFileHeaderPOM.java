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

public class DBSCompanyDebtFileHeaderPOM extends POM {
	public static class DBSCompanyDebtFileHeaderPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMP_DEBT_FILE_HEADER (OID, STATUS, LASTUPDATED, INSERTDATE, INSERTTIME, FILENAME, FILESEQUENCENUMBER, FILEDATE, COMPANYCODE, DOCUMENTID, MAILID, COMPANYOID, FILECURRENCY, DISTRICTCODE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMP_DEBT_FILE_HEADER SET STATUS=?, LASTUPDATED=?, INSERTDATE=?, INSERTTIME=?, FILENAME=?, FILESEQUENCENUMBER=?, FILEDATE=?, COMPANYCODE=?, DOCUMENTID=?, MAILID=?, COMPANYOID=?, FILECURRENCY=?, DISTRICTCODE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, INSERTDATE, INSERTTIME, FILENAME, FILESEQUENCENUMBER, FILEDATE, COMPANYCODE, DOCUMENTID, MAILID, COMPANYOID, FILECURRENCY, DISTRICTCODE FROM EXT.DBS_COMP_DEBT_FILE_HEADER WHERE OID = ? AND STATUS=1";
		public final static String findByFileDateandFileNameSql = " SELECT  OID, STATUS, LASTUPDATED, INSERTDATE, INSERTTIME, FILENAME, FILESEQUENCENUMBER, FILEDATE, COMPANYCODE, DOCUMENTID, MAILID, COMPANYOID, FILECURRENCY, DISTRICTCODE FROM EXT.DBS_COMP_DEBT_FILE_HEADER WHERE FILEDATE = ? AND FILENAME = ? AND COMPANYCODE = ? AND STATUS=1 ";
		public final static String findByFileNameAndCompanyCodeSql = " SELECT  OID, STATUS, LASTUPDATED, INSERTDATE, INSERTTIME, FILENAME, FILESEQUENCENUMBER, FILEDATE, COMPANYCODE, DOCUMENTID, MAILID, COMPANYOID, FILECURRENCY, DISTRICTCODE FROM EXT.DBS_COMP_DEBT_FILE_HEADER WHERE FILENAME = ? AND COMPANYCODE = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileHeaderPOMSql() {
		};
	}

	static {
		DBSCompanyDebtFileHeaderPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileHeaderPOMSql.hash.put("FILENAME", "fileName");
		DBSCompanyDebtFileHeaderPOMSql.hash.put("FILEDATE", "fileDate");
		DBSCompanyDebtFileHeaderPOMSql.hash.put("COMPANYCODE", "companyCode");
	}

	protected DBSCompanyDebtFileHeaderPOM() {
		super(1);
		dBSCompanyDebtFileHeaderPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileHeaderPOMLevel] = DBSCompanyDebtFileHeaderPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileHeaderPOMLevel] = DBSCompanyDebtFileHeaderPOMData.class;
	}

	protected DBSCompanyDebtFileHeaderPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileHeaderPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileHeaderPOMLevel] = DBSCompanyDebtFileHeaderPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileHeaderPOMLevel] = DBSCompanyDebtFileHeaderPOMData.class;
	}

	protected int dBSCompanyDebtFileHeaderPOMLevel;
	private static final long dBSCompanyDebtFileHeaderPOMPID = 3000012345700036L;

	public boolean readByFileDateandFileName(String fileDate, String fileName,
			String companyCode) throws CBException {
		Object[] params = { fileDate, fileName, companyCode };
		Class[] paramTypes = { String.class, String.class, String.class };
		return load("findByFileDateandFileNameSql", params, paramTypes,
				dBSCompanyDebtFileHeaderPOMLevel, POMConstants.ONE, true, false);
	}

	public boolean readByFileNameAndCompanyCode(String fileName,
			String companyCode) throws CBException {
		Object[] params = { fileName, companyCode };
		Class[] paramTypes = { String.class, String.class };
		return load("findByFileNameAndCompanyCodeSql", params, paramTypes,
				dBSCompanyDebtFileHeaderPOMLevel, POMConstants.ONE, true, false);
	}

	public static DBSCompanyDebtFileHeaderPOM newDBSCompanyDebtFileHeaderPOM() {
		DBSCompanyDebtFileHeaderPOM pom = (DBSCompanyDebtFileHeaderPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileHeaderPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileHeaderPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileHeaderPOMData getDBSCompanyDebtFileHeaderPOMData() {
		return (DBSCompanyDebtFileHeaderPOMData) datas[dBSCompanyDebtFileHeaderPOMLevel];
	}

	public DBSCompanyDebtFileHeaderPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileHeaderPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileHeaderPOMPID;
	}

	public void setDBSCompanyDebtFileHeaderPOMData(
			DBSCompanyDebtFileHeaderPOMData data) {
		datas[dBSCompanyDebtFileHeaderPOMLevel] = data;
		isDirty[dBSCompanyDebtFileHeaderPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileHeaderPOMData(
			DBSCompanyDebtFileHeaderPOMData data) throws CBException {
		setDBSCompanyDebtFileHeaderPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileHeaderPOMData(
			DBSCompanyDebtFileHeaderPOMData data) throws CBException {
		setDBSCompanyDebtFileHeaderPOMData(data);
		create();
	}
}
