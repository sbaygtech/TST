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

public class DBSDealerUploadDocPOM extends POM {
	public static class DBSDealerUploadDocPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_DEALER_UPLOADED_DOC (OID, STATUS, LASTUPDATED, DOCUMENTID, EXPLANATION, FILENAME, UPLOADDATE, UPLOADTIME, UPLOADER, DEALEROID)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_DEALER_UPLOADED_DOC SET STATUS=?, LASTUPDATED=?, DOCUMENTID=?, EXPLANATION=?, FILENAME=?, UPLOADDATE=?, UPLOADTIME=?, UPLOADER=?, DEALEROID=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DOCUMENTID, EXPLANATION, FILENAME, UPLOADDATE, UPLOADTIME, UPLOADER, DEALEROID FROM EXT.DBS_DEALER_UPLOADED_DOC WHERE OID = ? AND STATUS=1";
		public final static String findByDealerOIDSql = " SELECT  OID, STATUS, LASTUPDATED, DOCUMENTID, EXPLANATION, FILENAME, UPLOADDATE, UPLOADTIME, UPLOADER, DEALEROID FROM EXT.DBS_DEALER_UPLOADED_DOC WHERE DEALEROID = ? AND STATUS=1 ";

		public DBSDealerUploadDocPOMSql() {
		};
	}

	public static class DBSDealerUploadDocPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_DEALER_UPLOADED_DOC SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_DEALER_UPLOADED_DOC T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_DEALER_UPLOADED_DOC SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, DOCUMENTID, EXPLANATION, FILENAME, UPLOADDATE, UPLOADTIME, UPLOADER, DEALEROID,TRANSACTIONOID FROM HST.DBS_DEALER_UPLOADED_DOC WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByDealerOIDSql = " SELECT  OID, STATUS, LASTUPDATED, DOCUMENTID, EXPLANATION, FILENAME, UPLOADDATE, UPLOADTIME, UPLOADER, DEALEROID ,TRANSACTIONOID FROM HST.DBS_DEALER_UPLOADED_DOC WHERE DEALEROID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSDealerUploadDocPOMHistSql() {
		};
	}

	protected DBSDealerUploadDocPOM() {
		super(1);
		dBSDealerUploadDocPOMLevel = 0;
		sqlClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMSql.class;
		sqlHistClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMHistSql.class;
		pomDataClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMData.class;
	}

	protected DBSDealerUploadDocPOM(int level) {
		super(level + 1);
		dBSDealerUploadDocPOMLevel = level;
		sqlClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMSql.class;
		sqlHistClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMHistSql.class;
		pomDataClasses[dBSDealerUploadDocPOMLevel] = DBSDealerUploadDocPOMData.class;
	}

	protected int dBSDealerUploadDocPOMLevel;
	private static final long dBSDealerUploadDocPOMPID = 3000010389500053L;
	private static final String HIST_TABLE_NAME = "HST.DBS_DEALER_UPLOADED_DOC";

	public void readByDealerOID(long dealerOID) throws CBException {
		Object[] params = { new java.lang.Long(dealerOID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByDealerOIDSql", params, paramTypes,
				dBSDealerUploadDocPOMLevel, POMConstants.MANY, true, false);
	}

	public void readByDealerOID(long dealerOID, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(dealerOID),
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByDealerOIDSql", params, paramTypes,
				dBSDealerUploadDocPOMLevel, POMConstants.MANY, true, false);
	}

	public static DBSDealerUploadDocPOM newDBSDealerUploadDocPOM() {
		DBSDealerUploadDocPOM pom = (DBSDealerUploadDocPOM) POMPool
				.getFreePOM(dBSDealerUploadDocPOMPID);
		if (pom == null) {
			pom = new DBSDealerUploadDocPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSDealerUploadDocPOMData getDBSDealerUploadDocPOMData() {
		return (DBSDealerUploadDocPOMData) datas[dBSDealerUploadDocPOMLevel];
	}

	public DBSDealerUploadDocPOMData findByPrimaryKey(long oID)
			throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSDealerUploadDocPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes,
				dBSDealerUploadDocPOMLevel, POMConstants.MANY, true, false);
	}

	public long getID() {
		return dBSDealerUploadDocPOMPID;
	}

	public void setDBSDealerUploadDocPOMData(DBSDealerUploadDocPOMData data) {
		datas[dBSDealerUploadDocPOMLevel] = data;
		isDirty[dBSDealerUploadDocPOMLevel] = true;
	}

	public void updateDBSDealerUploadDocPOMData(DBSDealerUploadDocPOMData data)
			throws CBException {
		updateDBSDealerUploadDocPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSDealerUploadDocPOMData(DBSDealerUploadDocPOMData data,
			boolean updateHistory) throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSDealerUploadDocPOMData(data);
		update(updateHistory);
	}

	public void createDBSDealerUploadDocPOMData(DBSDealerUploadDocPOMData data)
			throws CBException {
		setDBSDealerUploadDocPOMData(data);
		create();
	}
}
