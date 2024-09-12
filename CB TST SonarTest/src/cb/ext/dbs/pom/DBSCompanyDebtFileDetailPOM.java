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

public class DBSCompanyDebtFileDetailPOM extends POM {
	public static class DBSCompanyDebtFileDetailPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMP_DEBT_FILE_DETAIL (OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, RECORDTYPE, INVOICENO, INVOICEAMOUNT, CURRENCY, PROCESSSTATU, PROCESSEXPLANATION, FILEOID, MASTEROID, SPECIALEXCEPTIONCODE, INVOICEREFNO, INVOICETYPE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMP_DEBT_FILE_DETAIL SET STATUS=?, LASTUPDATED=?, CUSTOMERNO=?, CUSTOMERTITLE=?, DUEDATE=?, RECORDTYPE=?, INVOICENO=?, INVOICEAMOUNT=?, CURRENCY=?, PROCESSSTATU=?, PROCESSEXPLANATION=?, FILEOID=?, MASTEROID=?, SPECIALEXCEPTIONCODE=?, INVOICEREFNO=?, INVOICETYPE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, RECORDTYPE, INVOICENO, INVOICEAMOUNT, CURRENCY, PROCESSSTATU, PROCESSEXPLANATION, FILEOID, MASTEROID, SPECIALEXCEPTIONCODE, INVOICEREFNO, INVOICETYPE FROM EXT.DBS_COMP_DEBT_FILE_DETAIL WHERE OID = ? AND STATUS=1";
		public final static String findDebtFileDetailByMasterOidSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, RECORDTYPE, INVOICENO, INVOICEAMOUNT, CURRENCY, PROCESSSTATU, PROCESSEXPLANATION, FILEOID, MASTEROID, SPECIALEXCEPTIONCODE, INVOICEREFNO, INVOICETYPE FROM EXT.DBS_COMP_DEBT_FILE_DETAIL WHERE MASTEROID = ? AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileDetailPOMSql() {
		};
	}

	static {
		DBSCompanyDebtFileDetailPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileDetailPOMSql.hash.put("MASTEROID", "masterOID");
	}

	protected DBSCompanyDebtFileDetailPOM() {
		super(1);
		dBSCompanyDebtFileDetailPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileDetailPOMLevel] = DBSCompanyDebtFileDetailPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailPOMLevel] = DBSCompanyDebtFileDetailPOMData.class;
	}

	protected DBSCompanyDebtFileDetailPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileDetailPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileDetailPOMLevel] = DBSCompanyDebtFileDetailPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailPOMLevel] = DBSCompanyDebtFileDetailPOMData.class;
	}

	protected int dBSCompanyDebtFileDetailPOMLevel;
	private static final long dBSCompanyDebtFileDetailPOMPID = 3000013389400001L;

	public boolean readDebtFileDetailByMasterOid(long masterOID) throws CBException {
		Object[] params = { new java.lang.Long(masterOID) };
		Class[] paramTypes = { java.lang.Long.class };
		return load("findDebtFileDetailByMasterOidSql", params, paramTypes, dBSCompanyDebtFileDetailPOMLevel,
				POMConstants.ONE, true, false);
	}

	public static DBSCompanyDebtFileDetailPOM newDBSCompanyDebtFileDetailPOM() {
		DBSCompanyDebtFileDetailPOM pom = (DBSCompanyDebtFileDetailPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileDetailPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileDetailPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileDetailPOMData getDBSCompanyDebtFileDetailPOMData() {
		return (DBSCompanyDebtFileDetailPOMData) datas[dBSCompanyDebtFileDetailPOMLevel];
	}

	public DBSCompanyDebtFileDetailPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileDetailPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileDetailPOMPID;
	}

	public void setDBSCompanyDebtFileDetailPOMData(DBSCompanyDebtFileDetailPOMData data) {
		datas[dBSCompanyDebtFileDetailPOMLevel] = data;
		isDirty[dBSCompanyDebtFileDetailPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileDetailPOMData(DBSCompanyDebtFileDetailPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileDetailPOMData(DBSCompanyDebtFileDetailPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailPOMData(data);
		create();
	}
}
