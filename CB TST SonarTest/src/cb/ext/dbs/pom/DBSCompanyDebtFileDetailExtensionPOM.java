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

public class DBSCompanyDebtFileDetailExtensionPOM extends POM {
	public static class DBSCompanyDebtFileDetailExtensionPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEBT_FILE_DETAIL_EXT (OID, STATUS, LASTUPDATED, DETAILOID, INVOICEDATE, DISCOUNTINTERESTAMOUNT, COLLECTINGAMOUNT, COLLECTINGCURRENCYCODE, DISCOUNTINTRSTCOLLAMOUNT, COLLECTINGEXCHANGERATE, INVOICENUMBER, TRANSACTIONDATE, PROVISIONREFNO, PARAMETER, INVOICETYPE, DEBTCURRENCYCODE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEBT_FILE_DETAIL_EXT SET STATUS=?, LASTUPDATED=?, DETAILOID=?, INVOICEDATE=?, DISCOUNTINTERESTAMOUNT=?, COLLECTINGAMOUNT=?, COLLECTINGCURRENCYCODE=?, DISCOUNTINTRSTCOLLAMOUNT=?, COLLECTINGEXCHANGERATE=?, INVOICENUMBER=?, TRANSACTIONDATE=?, PROVISIONREFNO=?, PARAMETER=?, INVOICETYPE=?, DEBTCURRENCYCODE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DETAILOID, INVOICEDATE, DISCOUNTINTERESTAMOUNT, COLLECTINGAMOUNT, COLLECTINGCURRENCYCODE, DISCOUNTINTRSTCOLLAMOUNT, COLLECTINGEXCHANGERATE, INVOICENUMBER, TRANSACTIONDATE, PROVISIONREFNO, PARAMETER, INVOICETYPE, DEBTCURRENCYCODE FROM EXT.DBS_DEBT_FILE_DETAIL_EXT WHERE OID = ? AND STATUS=1";

		public DBSCompanyDebtFileDetailExtensionPOMSql() {
		};
	}

	protected DBSCompanyDebtFileDetailExtensionPOM() {
		super(1);
		dBSCompanyDebtFileDetailExtensionPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileDetailExtensionPOMLevel] = DBSCompanyDebtFileDetailExtensionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailExtensionPOMLevel] = DBSCompanyDebtFileDetailExtensionPOMData.class;
	}

	protected DBSCompanyDebtFileDetailExtensionPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileDetailExtensionPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileDetailExtensionPOMLevel] = DBSCompanyDebtFileDetailExtensionPOMSql.class;
		pomDataClasses[dBSCompanyDebtFileDetailExtensionPOMLevel] = DBSCompanyDebtFileDetailExtensionPOMData.class;
	}

	protected int dBSCompanyDebtFileDetailExtensionPOMLevel;
	private static final long dBSCompanyDebtFileDetailExtensionPOMPID = 3000012345700099L;

	public static DBSCompanyDebtFileDetailExtensionPOM newDBSCompanyDebtFileDetailExtensionPOM() {
		DBSCompanyDebtFileDetailExtensionPOM pom = (DBSCompanyDebtFileDetailExtensionPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileDetailExtensionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileDetailExtensionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileDetailExtensionPOMData getDBSCompanyDebtFileDetailExtensionPOMData() {
		return (DBSCompanyDebtFileDetailExtensionPOMData) datas[dBSCompanyDebtFileDetailExtensionPOMLevel];
	}

	public DBSCompanyDebtFileDetailExtensionPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileDetailExtensionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyDebtFileDetailExtensionPOMPID;
	}

	public void setDBSCompanyDebtFileDetailExtensionPOMData(
			DBSCompanyDebtFileDetailExtensionPOMData data) {
		datas[dBSCompanyDebtFileDetailExtensionPOMLevel] = data;
		isDirty[dBSCompanyDebtFileDetailExtensionPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileDetailExtensionPOMData(
			DBSCompanyDebtFileDetailExtensionPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailExtensionPOMData(data);
		update();
	}

	public void createDBSCompanyDebtFileDetailExtensionPOMData(
			DBSCompanyDebtFileDetailExtensionPOMData data) throws CBException {
		setDBSCompanyDebtFileDetailExtensionPOMData(data);
		create();
	}
}
