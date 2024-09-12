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

public class DBSPaymentResultHistoryPOM extends POM {
	public static class DBSPaymentResultHistoryPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_PAYMENT_RESULT_HISTORY (OID, STATUS, LASTUPDATED, CREATEDDATE, CREATEDTIME, MASTEROID, SEQUENCENO, PAIDAMOUNT, PROCESSTYPE)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_PAYMENT_RESULT_HISTORY SET STATUS=?, LASTUPDATED=?, CREATEDDATE=?, CREATEDTIME=?, MASTEROID=?, SEQUENCENO=?, PAIDAMOUNT=?, PROCESSTYPE=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, CREATEDDATE, CREATEDTIME, MASTEROID, SEQUENCENO, PAIDAMOUNT, PROCESSTYPE FROM EXT.DBS_PAYMENT_RESULT_HISTORY WHERE OID = ? AND STATUS=1";

		public DBSPaymentResultHistoryPOMSql() {
		};
	}

	protected DBSPaymentResultHistoryPOM() {
		super(1);
		dBSPaymentResultHistoryPOMLevel = 0;
		sqlClasses[dBSPaymentResultHistoryPOMLevel] = DBSPaymentResultHistoryPOMSql.class;
		pomDataClasses[dBSPaymentResultHistoryPOMLevel] = DBSPaymentResultHistoryPOMData.class;
	}

	protected DBSPaymentResultHistoryPOM(int level) {
		super(level + 1);
		dBSPaymentResultHistoryPOMLevel = level;
		sqlClasses[dBSPaymentResultHistoryPOMLevel] = DBSPaymentResultHistoryPOMSql.class;
		pomDataClasses[dBSPaymentResultHistoryPOMLevel] = DBSPaymentResultHistoryPOMData.class;
	}

	protected int dBSPaymentResultHistoryPOMLevel;
	private static final long dBSPaymentResultHistoryPOMPID = 3000013343600001L;

	public static DBSPaymentResultHistoryPOM newDBSPaymentResultHistoryPOM() {
		DBSPaymentResultHistoryPOM pom = (DBSPaymentResultHistoryPOM) POMPool.getFreePOM(dBSPaymentResultHistoryPOMPID);
		if (pom == null) {
			pom = new DBSPaymentResultHistoryPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSPaymentResultHistoryPOMData getDBSPaymentResultHistoryPOMData() {
		return (DBSPaymentResultHistoryPOMData) datas[dBSPaymentResultHistoryPOMLevel];
	}

	public DBSPaymentResultHistoryPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSPaymentResultHistoryPOMData();
		return null;
	}

	public long getID() {
		return dBSPaymentResultHistoryPOMPID;
	}

	public void setDBSPaymentResultHistoryPOMData(DBSPaymentResultHistoryPOMData data) {
		datas[dBSPaymentResultHistoryPOMLevel] = data;
		isDirty[dBSPaymentResultHistoryPOMLevel] = true;
	}

	public void updateDBSPaymentResultHistoryPOMData(DBSPaymentResultHistoryPOMData data) throws CBException {
		setDBSPaymentResultHistoryPOMData(data);
		update();
	}

	public void createDBSPaymentResultHistoryPOMData(DBSPaymentResultHistoryPOMData data) throws CBException {
		setDBSPaymentResultHistoryPOMData(data);
		create();
	}
}
