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

public class DBSCompanyProcessDebtPaymentExtensionPOM extends POM {
	public static class DBSCompanyProcessDebtPaymentExtensionPOMSql implements
			SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_CAMP_DEBT_PYMT_EXT (OID, STATUS, LASTUPDATED, MASTEROID, PAYMENTAMOUNT, PAYMENTDATE, PAYMENTTIME, PRODUCTOPERATIONREFNO)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_CAMP_DEBT_PYMT_EXT SET STATUS=?, LASTUPDATED=?, MASTEROID=?, PAYMENTAMOUNT=?, PAYMENTDATE=?, PAYMENTTIME=?, PRODUCTOPERATIONREFNO=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, MASTEROID, PAYMENTAMOUNT, PAYMENTDATE, PAYMENTTIME, PRODUCTOPERATIONREFNO FROM EXT.DBS_CAMP_DEBT_PYMT_EXT WHERE OID = ? AND STATUS=1";

		public DBSCompanyProcessDebtPaymentExtensionPOMSql() {
		};
	}

	protected DBSCompanyProcessDebtPaymentExtensionPOM() {
		super(1);
		dBSCompanyProcessDebtPaymentExtensionPOMLevel = 0;
		sqlClasses[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = DBSCompanyProcessDebtPaymentExtensionPOMSql.class;
		pomDataClasses[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = DBSCompanyProcessDebtPaymentExtensionPOMData.class;
	}

	protected DBSCompanyProcessDebtPaymentExtensionPOM(int level) {
		super(level + 1);
		dBSCompanyProcessDebtPaymentExtensionPOMLevel = level;
		sqlClasses[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = DBSCompanyProcessDebtPaymentExtensionPOMSql.class;
		pomDataClasses[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = DBSCompanyProcessDebtPaymentExtensionPOMData.class;
	}

	protected int dBSCompanyProcessDebtPaymentExtensionPOMLevel;
	private static final long dBSCompanyProcessDebtPaymentExtensionPOMPID = 3000011937800001L;

	public static DBSCompanyProcessDebtPaymentExtensionPOM newDBSCompanyProcessDebtPaymentExtensionPOM() {
		DBSCompanyProcessDebtPaymentExtensionPOM pom = (DBSCompanyProcessDebtPaymentExtensionPOM) POMPool
				.getFreePOM(dBSCompanyProcessDebtPaymentExtensionPOMPID);
		if (pom == null) {
			pom = new DBSCompanyProcessDebtPaymentExtensionPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyProcessDebtPaymentExtensionPOMData getDBSCompanyProcessDebtPaymentExtensionPOMData() {
		return (DBSCompanyProcessDebtPaymentExtensionPOMData) datas[dBSCompanyProcessDebtPaymentExtensionPOMLevel];
	}

	public DBSCompanyProcessDebtPaymentExtensionPOMData findByPrimaryKey(
			long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyProcessDebtPaymentExtensionPOMData();
		return null;
	}

	public long getID() {
		return dBSCompanyProcessDebtPaymentExtensionPOMPID;
	}

	public void setDBSCompanyProcessDebtPaymentExtensionPOMData(
			DBSCompanyProcessDebtPaymentExtensionPOMData data) {
		datas[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = data;
		isDirty[dBSCompanyProcessDebtPaymentExtensionPOMLevel] = true;
	}

	public void updateDBSCompanyProcessDebtPaymentExtensionPOMData(
			DBSCompanyProcessDebtPaymentExtensionPOMData data)
			throws CBException {
		setDBSCompanyProcessDebtPaymentExtensionPOMData(data);
		update();
	}

	public void createDBSCompanyProcessDebtPaymentExtensionPOMData(
			DBSCompanyProcessDebtPaymentExtensionPOMData data)
			throws CBException {
		setDBSCompanyProcessDebtPaymentExtensionPOMData(data);
		create();
	}
}
