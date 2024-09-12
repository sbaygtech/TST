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

public class DBSCompanyDebtProcessForeignCurConversionPOM extends POM {
	public static class DBSCompanyDebtProcessForeignCurConversionPOMSql
			implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBT_PRO_FOREIGN_CUR_CON (OID, STATUS, LASTUPDATED, MASTEROID, TRANSACTIONAMOUNT, TRANSACTIONCURRENCYCODE, TRANSACTIONRATE, SOURCEAMOUNT, SOURCECURRENCYCODE, PROCESSDATE, PROCESSTIME, INVOICEAMOUNT, INVOICECURRENCYCODE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBT_PRO_FOREIGN_CUR_CON SET STATUS=?, LASTUPDATED=?, MASTEROID=?, TRANSACTIONAMOUNT=?, TRANSACTIONCURRENCYCODE=?, TRANSACTIONRATE=?, SOURCEAMOUNT=?, SOURCECURRENCYCODE=?, PROCESSDATE=?, PROCESSTIME=?, INVOICEAMOUNT=?, INVOICECURRENCYCODE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MASTEROID, TRANSACTIONAMOUNT, TRANSACTIONCURRENCYCODE, TRANSACTIONRATE, SOURCEAMOUNT, SOURCECURRENCYCODE, PROCESSDATE, PROCESSTIME, INVOICEAMOUNT, INVOICECURRENCYCODE FROM EXT.DBS_DEBT_PRO_FOREIGN_CUR_CON WHERE OID = ? AND STATUS=1";

		public DBSCompanyDebtProcessForeignCurConversionPOMSql() {
		};
	}

	protected DBSCompanyDebtProcessForeignCurConversionPOM() {
		super(1);
		dBSCompanyDebtProcessForeignCurConversionPOMLevel = 0;
		sqlClasses[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = DBSCompanyDebtProcessForeignCurConversionPOMSql.class;
		pomDataClasses[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = DBSCompanyDebtProcessForeignCurConversionPOMData.class;
	}

	protected DBSCompanyDebtProcessForeignCurConversionPOM(int level) {
		super(level + 1);
		dBSCompanyDebtProcessForeignCurConversionPOMLevel = level;
		sqlClasses[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = DBSCompanyDebtProcessForeignCurConversionPOMSql.class;
		pomDataClasses[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = DBSCompanyDebtProcessForeignCurConversionPOMData.class;
	}

	protected int dBSCompanyDebtProcessForeignCurConversionPOMLevel;
	private static final long dBSCompanyDebtProcessForeignCurConversionPOMPID = 3000013118300001L;

	public static DBSCompanyDebtProcessForeignCurConversionPOM newDBSCompanyDebtProcessForeignCurConversionPOM() {
		DBSCompanyDebtProcessForeignCurConversionPOM pom = (DBSCompanyDebtProcessForeignCurConversionPOM) POMPool
				.getFreePOM(dBSCompanyDebtProcessForeignCurConversionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtProcessForeignCurConversionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtProcessForeignCurConversionPOMData getDBSCompanyDebtProcessForeignCurConversionPOMData() {
		return (DBSCompanyDebtProcessForeignCurConversionPOMData) datas[dBSCompanyDebtProcessForeignCurConversionPOMLevel];
	}

	public DBSCompanyDebtProcessForeignCurConversionPOMData findByPrimaryKey(
			long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtProcessForeignCurConversionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtProcessForeignCurConversionPOMPID;
	}

	public void setDBSCompanyDebtProcessForeignCurConversionPOMData(
			DBSCompanyDebtProcessForeignCurConversionPOMData data) {
		datas[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = data;
		isDirty[dBSCompanyDebtProcessForeignCurConversionPOMLevel] = true;
	}

	public void updateDBSCompanyDebtProcessForeignCurConversionPOMData(
			DBSCompanyDebtProcessForeignCurConversionPOMData data)
			throws CBException {
		setDBSCompanyDebtProcessForeignCurConversionPOMData(data);
		update();
	}

	public void createDBSCompanyDebtProcessForeignCurConversionPOMData(
			DBSCompanyDebtProcessForeignCurConversionPOMData data)
			throws CBException {
		setDBSCompanyDebtProcessForeignCurConversionPOMData(data);
		create();
	}
}
