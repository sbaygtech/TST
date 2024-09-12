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

public class DBSCompanyDebtFileMasterPOM extends POM {
	public static class DBSCompanyDebtFileMasterPOMSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO EXT.DBS_COMP_DEBT_FILE_MASTER (OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		public final static String updateSql = "UPDATE EXT.DBS_COMP_DEBT_FILE_MASTER SET STATUS=?, LASTUPDATED=?, CUSTOMERNO=?, CUSTOMERTITLE=?, DUEDATE=?, INVOICEAMOUNT=?, INVOICENO=?, CURRENCY=?, INSERTDATE=?, INSERTTIME=?, UPDATEDATE=?, UPDATETIME=?, DELETEDATE=?, DELETETIME=?, PROCESSSTATU=?, PROCESSEXPLANATION=?, PRODUCTOPREFNO=?, PROCESSDATE=?, ACCOUNTINGDATE=?, COMPANYID=?, PROCESSTIME=?, DEALEROID=?, TRANSFERED=?, TRANSFERPRODUCTOPREFNO=?, PAIDAMOUNT=?, CHANNELCODE=?, SEQUENCENO=?, INVOICEREFNO=?, INVOICETYPE=?, CANCELDATE=?, CANCELPRODUCTOPREFNO=?, CANCELAMOUNT=? WHERE OID = ? AND LASTUPDATED=?  AND STATUS=1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE OID = ? AND STATUS=1";
		public final static String findByCompanyOIDandInvoiceNoSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND STATUS=1 ";
		public final static String findByCompanyOIDandInvoiceNoActiveSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND PROCESSSTATU IN ('S00','S02') AND STATUS=1 ";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND STATUS=1 ";
		public final static String findByCompanyOIDandTransferedandAccountingDateSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND TRANSFERED = ? AND ACCOUNTINGDATE = ? AND STATUS=1 ";
		public final static String findByCompanyOIDAndInvoiceNoPaymentSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT FROM EXT.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND PROCESSSTATU IN ('S01','S04') AND STATUS=1 ";
		public static java.util.Hashtable hash;

		public DBSCompanyDebtFileMasterPOMSql() {
		};
	}

	public static class DBSCompanyDebtFileMasterPOMHistSql implements SqlStatements {

		public final static String insertSql = "INSERT INTO HST.DBS_COMP_DEBT_FILE_MASTER SELECT  SMG.EXTTERNAL_OID_SEQ.nextval,sysdate AS START_DATE,NULL AS END_DATE,1 AS ACTIVE_FLAG,?  AS TRANSACTIONOID ,T1.*  FROM EXT.DBS_COMP_DEBT_FILE_MASTER T1 WHERE OID = ? AND STATUS=1";
		public final static String updateSql = "UPDATE HST.DBS_COMP_DEBT_FILE_MASTER SET ACTIVE_FLAG = 0,END_DATE=sysdate WHERE OID = ? AND ACTIVE_FLAG = 1";
		public final static String findByPrimaryKeySql = "SELECT OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE OID = ? AND TRANSACTIONOID < ? AND STATUS=1 ORDER BY LASTUPDATED DESC";
		public final static String findByCompanyOIDandInvoiceNoSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT ,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDandInvoiceNoActiveSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT ,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND PROCESSSTATU IN ('S00','S02') AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT ,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDandTransferedandAccountingDateSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT ,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND TRANSFERED = ? AND ACCOUNTINGDATE = ? AND STATUS=1 AND TRANSACTIONOID = ? ";
		public final static String findByCompanyOIDAndInvoiceNoPaymentSql = " SELECT  OID, STATUS, LASTUPDATED, CUSTOMERNO, CUSTOMERTITLE, DUEDATE, INVOICEAMOUNT, INVOICENO, CURRENCY, INSERTDATE, INSERTTIME, UPDATEDATE, UPDATETIME, DELETEDATE, DELETETIME, PROCESSSTATU, PROCESSEXPLANATION, PRODUCTOPREFNO, PROCESSDATE, ACCOUNTINGDATE, COMPANYID, PROCESSTIME, DEALEROID, TRANSFERED, TRANSFERPRODUCTOPREFNO, PAIDAMOUNT, CHANNELCODE, SEQUENCENO, INVOICEREFNO, INVOICETYPE, CANCELDATE, CANCELPRODUCTOPREFNO, CANCELAMOUNT ,TRANSACTIONOID FROM HST.DBS_COMP_DEBT_FILE_MASTER WHERE COMPANYID = ? AND INVOICENO = ? AND PROCESSSTATU IN ('S01','S04') AND STATUS=1 AND TRANSACTIONOID = ? ";

		public DBSCompanyDebtFileMasterPOMHistSql() {
		};
	}

	static {
		DBSCompanyDebtFileMasterPOMSql.hash = new java.util.Hashtable();
		DBSCompanyDebtFileMasterPOMSql.hash.put("INVOICENO", "invoiceNo");
		DBSCompanyDebtFileMasterPOMSql.hash.put("PROCESSSTATU", "processStatu");
		DBSCompanyDebtFileMasterPOMSql.hash.put("COMPANYID", "companyID");
	}

	protected DBSCompanyDebtFileMasterPOM() {
		super(1);
		dBSCompanyDebtFileMasterPOMLevel = 0;
		sqlClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMData.class;
	}

	protected DBSCompanyDebtFileMasterPOM(int level) {
		super(level + 1);
		dBSCompanyDebtFileMasterPOMLevel = level;
		sqlClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMSql.class;
		sqlHistClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMHistSql.class;
		pomDataClasses[dBSCompanyDebtFileMasterPOMLevel] = DBSCompanyDebtFileMasterPOMData.class;
	}

	protected int dBSCompanyDebtFileMasterPOMLevel;
	private static final long dBSCompanyDebtFileMasterPOMPID = 3000013378800004L;
	private static final String HIST_TABLE_NAME = "HST.DBS_COMP_DEBT_FILE_MASTER";

	public boolean readByCompanyOIDandInvoiceNo(long companyID, String invoiceNo) throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOIDandInvoiceNoSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOIDandInvoiceNo(long companyID, String invoiceNo, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo, new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDandInvoiceNoSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOIDandInvoiceNoActive(long companyID, String invoiceNo) throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOIDandInvoiceNoActiveSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOIDandInvoiceNoActive(long companyID, String invoiceNo, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo, new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDandInvoiceNoActiveSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public void readByCompanyOID(long companyID) throws CBException {
		Object[] params = { new java.lang.Long(companyID) };
		Class[] paramTypes = { java.lang.Long.class };
		load("findByCompanyOIDSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOID(long companyID, long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyID), new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel, POMConstants.MANY, true,
				false);
	}

	public void readByCompanyOIDandTransferedandAccountingDate(long companyID, String transfered, CBDate accountingDate)
			throws CBException {
		Object[] params = { new java.lang.Long(companyID), transfered, accountingDate };
		Class[] paramTypes = { java.lang.Long.class, String.class, CBDate.class };
		load("findByCompanyOIDandTransferedandAccountingDateSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.MANY, true, false);
	}

	public void readByCompanyOIDandTransferedandAccountingDate(long companyID, String transfered, CBDate accountingDate,
			long historyTransactionOID) throws CBException {
		Object[] params = { new java.lang.Long(companyID), transfered, accountingDate,
				new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class, CBDate.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		load("findByCompanyOIDandTransferedandAccountingDateSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.MANY, true, false);
	}

	public boolean readByCompanyOIDAndInvoiceNoPayment(long companyID, String invoiceNo) throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo };
		Class[] paramTypes = { java.lang.Long.class, String.class };
		return load("findByCompanyOIDAndInvoiceNoPaymentSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public boolean readByCompanyOIDAndInvoiceNoPayment(long companyID, String invoiceNo, long historyTransactionOID)
			throws CBException {
		Object[] params = { new java.lang.Long(companyID), invoiceNo, new java.lang.Long(historyTransactionOID) };
		Class[] paramTypes = { java.lang.Long.class, String.class, java.lang.Long.class };
		setHistoryTransactionOID(historyTransactionOID);
		return load("findByCompanyOIDAndInvoiceNoPaymentSql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel,
				POMConstants.ONE, true, false);
	}

	public static DBSCompanyDebtFileMasterPOM newDBSCompanyDebtFileMasterPOM() {
		DBSCompanyDebtFileMasterPOM pom = (DBSCompanyDebtFileMasterPOM) POMPool
				.getFreePOM(dBSCompanyDebtFileMasterPOMPID);
		if (pom == null) {
			pom = new DBSCompanyDebtFileMasterPOM();
			POMPool.setPOMAsInUse(pom);
		}
		return pom;
	}

	public DBSCompanyDebtFileMasterPOMData getDBSCompanyDebtFileMasterPOMData() {
		return (DBSCompanyDebtFileMasterPOMData) datas[dBSCompanyDebtFileMasterPOMLevel];
	}

	public DBSCompanyDebtFileMasterPOMData findByPrimaryKey(long oID) throws CBException {
		if (readByPrimaryKey(oID))
			return getDBSCompanyDebtFileMasterPOMData();
		return null;
	}

	public void findByPrimaryKey(long oID, long historyDate) throws CBException {
		Object[] params = { new Long(oID), new Long(historyDate) };
		Class[] paramTypes = { Long.class, Long.class };
		setHistoryTransactionOID(historyDate);
		load("findByPrimaryKeySql", params, paramTypes, dBSCompanyDebtFileMasterPOMLevel, POMConstants.MANY, true,
				false);
	}

	public long getID() {
		return dBSCompanyDebtFileMasterPOMPID;
	}

	public void setDBSCompanyDebtFileMasterPOMData(DBSCompanyDebtFileMasterPOMData data) {
		datas[dBSCompanyDebtFileMasterPOMLevel] = data;
		isDirty[dBSCompanyDebtFileMasterPOMLevel] = true;
	}

	public void updateDBSCompanyDebtFileMasterPOMData(DBSCompanyDebtFileMasterPOMData data) throws CBException {
		updateDBSCompanyDebtFileMasterPOMData(data, true);
	}

	public String getHistoryTableName() {
		return HIST_TABLE_NAME;
	}

	public void updateDBSCompanyDebtFileMasterPOMData(DBSCompanyDebtFileMasterPOMData data, boolean updateHistory)
			throws CBException {
		if (updateHistory)
			updateHistory = data.isChangedForHistory();
		setDBSCompanyDebtFileMasterPOMData(data);
		update(updateHistory);
	}

	public void createDBSCompanyDebtFileMasterPOMData(DBSCompanyDebtFileMasterPOMData data) throws CBException {
		setDBSCompanyDebtFileMasterPOMData(data);
		create();
	}
}
