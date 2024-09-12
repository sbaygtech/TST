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

public class DBSDealerKTHTransferPOM extends POM {
	public static class DBSDealerKTHTransferPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_KTH_TRANSFER (OID, STATUS, LASTUPDATED, DEBITACCOUNT, CREDITACCOUNT, TRANSFERAMOUNT, PROCESSDATE, PROCESSTIME, CURRENCY, PRODUCTOPREFNO)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_KTH_TRANSFER SET STATUS=?, LASTUPDATED=?, DEBITACCOUNT=?, CREDITACCOUNT=?, TRANSFERAMOUNT=?, PROCESSDATE=?, PROCESSTIME=?, CURRENCY=?, PRODUCTOPREFNO=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DEBITACCOUNT, CREDITACCOUNT, TRANSFERAMOUNT, PROCESSDATE, PROCESSTIME, CURRENCY, PRODUCTOPREFNO FROM EXT.DBS_KTH_TRANSFER WHERE OID = ? AND STATUS=1";

		public DBSDealerKTHTransferPOMSql() {
		};
	}

	protected DBSDealerKTHTransferPOM() {
		super(1);
		dBSDealerKTHTransferPOMLevel = 0;
		sqlClasses[dBSDealerKTHTransferPOMLevel] = DBSDealerKTHTransferPOMSql.class;
		pomDataClasses[dBSDealerKTHTransferPOMLevel] = DBSDealerKTHTransferPOMData.class;
	}

	protected DBSDealerKTHTransferPOM(int level) {
		super(level + 1);
		dBSDealerKTHTransferPOMLevel = level;
		sqlClasses[dBSDealerKTHTransferPOMLevel] = DBSDealerKTHTransferPOMSql.class;
		pomDataClasses[dBSDealerKTHTransferPOMLevel] = DBSDealerKTHTransferPOMData.class;
	}

	protected int dBSDealerKTHTransferPOMLevel;
	private static final long dBSDealerKTHTransferPOMPID = 3000010411000104L;

	public static DBSDealerKTHTransferPOM newDBSDealerKTHTransferPOM() {
		DBSDealerKTHTransferPOM pom = (DBSDealerKTHTransferPOM) POMPool
				.getFreePOM(dBSDealerKTHTransferPOMPID);
		if (pom == null) {
			pom = new DBSDealerKTHTransferPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDealerKTHTransferPOMData getDBSDealerKTHTransferPOMData() {
		return (DBSDealerKTHTransferPOMData) datas[dBSDealerKTHTransferPOMLevel];
	}

	public DBSDealerKTHTransferPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDealerKTHTransferPOMData();
		return null;
	}

	public long getID() {
		return dBSDealerKTHTransferPOMPID;
	}

	public void setDBSDealerKTHTransferPOMData(DBSDealerKTHTransferPOMData data) {
		datas[dBSDealerKTHTransferPOMLevel] = data;
		isDirty[dBSDealerKTHTransferPOMLevel] = true;
	}

	public void updateDBSDealerKTHTransferPOMData(
			DBSDealerKTHTransferPOMData data) throws CBException {
		setDBSDealerKTHTransferPOMData(data);
		update();
	}

	public void createDBSDealerKTHTransferPOMData(
			DBSDealerKTHTransferPOMData data) throws CBException {
		setDBSDealerKTHTransferPOMData(data);
		create();
	}
}
