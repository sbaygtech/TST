package cb.ext.dbs.pom;

/*
 * THIS FILE IS GENERATED AUTOMATICALLY, DO NOT MODIFY
 * If corresponding POMData is needed to be changed,
 * Do modifications with POMGenerator, and regenerate.
 */

import cb.smg.pom.*;
import cb.smg.general.utility.*;
import cb.smg.businesstype.*;
import cb.smg.bagkeys.CBBagKeys;

public class DBSCompanyDebtProcessForeignCurConversionPOMData extends POMData {
	protected DBSCompanyDebtProcessForeignCurConversionPOMData() {
	}

	public long masterOID;
	public CBCurrency transactionAmount;
	public String transactionCurrencyCode;
	public CBCurrency transactionRate;
	public CBCurrency sourceAmount;
	public String sourceCurrencyCode;
	public CBDate processDate;
	public CBTime processTime;
	public CBCurrency invoiceAmount;
	public String invoiceCurrencyCode;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 25, 3, 25, 25, 3, 8, 6, 25, 3 }, new short[] { 0, 0,
			0, 0, 2, 0, 2, 2, 0, 0, 0, 2, 0 }, new short[] { 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0 }, new String[] { "oID", "status", "lastUpdated",
			"masterOID", "transactionAmount", "transactionCurrencyCode",
			"transactionRate", "sourceAmount", "sourceCurrencyCode",
			"processDate", "processTime", "invoiceAmount",
			"invoiceCurrencyCode" }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0 });
	private static final long iD = 3000013118300002L;

	public CBBag toBag() throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		return toBag(outBag);
	}

	public CBBag toBag(CBBag outBag) throws CBException {
		outBag.put(CBBagKeys.OID, oID);
		outBag.put(CBBagKeys.STATUS, status);
		outBag.put(CBBagKeys.LASTUPDATED, lastUpdated);
		return outBag;
	}

	public CBRow toBag(CBRow row) throws CBException {
		row.addColumn(CBBagKeys.OID, oID);
		row.addColumn(CBBagKeys.STATUS, status);
		row.addColumn(CBBagKeys.LASTUPDATED, lastUpdated);
		return row;
	}

	public void setBag(CBBag inBag) throws CBException {
	}

	public void setBag(CBRow row) throws CBException {
	}

	protected void clear() {
		super.clear();
		masterOID = 0;
		transactionAmount = null;
		transactionCurrencyCode = null;
		transactionRate = null;
		sourceAmount = null;
		sourceCurrencyCode = null;
		processDate = null;
		processTime = null;
		invoiceAmount = null;
		invoiceCurrencyCode = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtProcessForeignCurConversionPOMData pomOrigin = (DBSCompanyDebtProcessForeignCurConversionPOMData) POMDataCache
				.getInstance().getPOMData(iD, oID);
		for (int bagkey : arBagKeys) {
			switch (bagkey) {
			case CBBagKeys.STATUS:
				if (pomOrigin.status != status)
					return true;
				break;
			}
		}
		return false;
	}

	public void copy(POMData data) {
		DBSCompanyDebtProcessForeignCurConversionPOMData cPOMData = (DBSCompanyDebtProcessForeignCurConversionPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.masterOID = masterOID;
		if (transactionAmount == null) {
			cPOMData.transactionAmount = null;
		} else if (cPOMData.transactionAmount == null) {
			cPOMData.transactionAmount = (CBCurrency) transactionAmount.clone();
		} else {
			transactionAmount.copy(cPOMData.transactionAmount);
		}
		cPOMData.transactionCurrencyCode = transactionCurrencyCode;
		if (transactionRate == null) {
			cPOMData.transactionRate = null;
		} else if (cPOMData.transactionRate == null) {
			cPOMData.transactionRate = (CBCurrency) transactionRate.clone();
		} else {
			transactionRate.copy(cPOMData.transactionRate);
		}
		if (sourceAmount == null) {
			cPOMData.sourceAmount = null;
		} else if (cPOMData.sourceAmount == null) {
			cPOMData.sourceAmount = (CBCurrency) sourceAmount.clone();
		} else {
			sourceAmount.copy(cPOMData.sourceAmount);
		}
		cPOMData.sourceCurrencyCode = sourceCurrencyCode;
		if (processDate == null) {
			cPOMData.processDate = null;
		} else if (cPOMData.processDate == null) {
			cPOMData.processDate = (CBDate) processDate.clone();
		} else {
			processDate.copy(cPOMData.processDate);
		}
		if (processTime == null) {
			cPOMData.processTime = null;
		} else if (cPOMData.processTime == null) {
			cPOMData.processTime = (CBTime) processTime.clone();
		} else {
			processTime.copy(cPOMData.processTime);
		}
		if (invoiceAmount == null) {
			cPOMData.invoiceAmount = null;
		} else if (cPOMData.invoiceAmount == null) {
			cPOMData.invoiceAmount = (CBCurrency) invoiceAmount.clone();
		} else {
			invoiceAmount.copy(cPOMData.invoiceAmount);
		}
		cPOMData.invoiceCurrencyCode = invoiceCurrencyCode;
	}

	public Object clone() {
		DBSCompanyDebtProcessForeignCurConversionPOMData clonePOMData = DBSCompanyDebtProcessForeignCurConversionPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtProcessForeignCurConversionPOMData newInstance() {
		DBSCompanyDebtProcessForeignCurConversionPOMData pomData = (DBSCompanyDebtProcessForeignCurConversionPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtProcessForeignCurConversionPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtProcessForeignCurConversionPOMData newInstance(
			CBBag inBag) throws CBException {
		DBSCompanyDebtProcessForeignCurConversionPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtProcessForeignCurConversionPOMData newInstance(
			CBRow row) throws CBException {
		DBSCompanyDebtProcessForeignCurConversionPOMData pomData = newInstance();
		pomData.setBag(row);
		return pomData;
	}

	public long getID() {
		return iD;
	}

	public POMDataFieldInfo getFieldInfo() {
		return fieldInfo;
	}
}
