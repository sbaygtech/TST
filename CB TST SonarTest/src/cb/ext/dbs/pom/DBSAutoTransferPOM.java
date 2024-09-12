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

public class DBSAutoTransferPOM extends POM {
	public static class DBSAutoTransferPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_EOD_COMPANY_TRNSFR (OID, STATUS, LASTUPDATED, DEBITACCOUNT, CREDITACCOUNT, ACCOUNTINGDATE, TOTALAMOUNT, COMPANYOID, TOTALCOUNT, PRODOPERREFNO)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_EOD_COMPANY_TRNSFR SET STATUS=?, LASTUPDATED=?, DEBITACCOUNT=?, CREDITACCOUNT=?, ACCOUNTINGDATE=?, TOTALAMOUNT=?, COMPANYOID=?, TOTALCOUNT=?, PRODOPERREFNO=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DEBITACCOUNT, CREDITACCOUNT, ACCOUNTINGDATE, TOTALAMOUNT, COMPANYOID, TOTALCOUNT, PRODOPERREFNO FROM EXT.DBS_EOD_COMPANY_TRNSFR WHERE OID = ? AND STATUS=1";

		public DBSAutoTransferPOMSql() {
		};
	}

	protected DBSAutoTransferPOM() {
		super(1);
		dBSAutoTransferPOMLevel = 0;
		sqlClasses[dBSAutoTransferPOMLevel] = DBSAutoTransferPOMSql.class;
		pomDataClasses[dBSAutoTransferPOMLevel] = DBSAutoTransferPOMData.class;
	}

	protected DBSAutoTransferPOM(int level) {
		super(level + 1);
		dBSAutoTransferPOMLevel = level;
		sqlClasses[dBSAutoTransferPOMLevel] = DBSAutoTransferPOMSql.class;
		pomDataClasses[dBSAutoTransferPOMLevel] = DBSAutoTransferPOMData.class;
	}

	protected int dBSAutoTransferPOMLevel;
	private static final long dBSAutoTransferPOMPID = 3000013389400010L;

	public static DBSAutoTransferPOM newDBSAutoTransferPOM() {
		DBSAutoTransferPOM pom = (DBSAutoTransferPOM) POMPool.getFreePOM(dBSAutoTransferPOMPID);
		if (pom == null) {
			pom = new DBSAutoTransferPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSAutoTransferPOMData getDBSAutoTransferPOMData() {
		return (DBSAutoTransferPOMData) datas[dBSAutoTransferPOMLevel];
	}

	public DBSAutoTransferPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSAutoTransferPOMData();
		return null;
	}

	public long getID() {
		return dBSAutoTransferPOMPID;
	}

	public void setDBSAutoTransferPOMData(DBSAutoTransferPOMData data) {
		datas[dBSAutoTransferPOMLevel] = data;
		isDirty[dBSAutoTransferPOMLevel] = true;
	}

	public void updateDBSAutoTransferPOMData(DBSAutoTransferPOMData data) throws CBException {
		setDBSAutoTransferPOMData(data);
		update();
	}

	public void createDBSAutoTransferPOMData(DBSAutoTransferPOMData data) throws CBException {
		setDBSAutoTransferPOMData(data);
		create();
	}
}
