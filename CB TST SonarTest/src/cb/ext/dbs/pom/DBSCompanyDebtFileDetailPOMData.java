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

public class DBSCompanyDebtFileDetailPOMData extends POMData {
	protected DBSCompanyDebtFileDetailPOMData() {
	}

	public String customerNo;
	public String customerTitle;
	public CBDate dueDate;
	public String recordType;
	public String invoiceNo;
	public CBCurrency invoiceAmount;
	public String currency;
	public String processStatu;
	public String processExplanation;
	public long fileOID;
	public long masterOID;
	public String specialExceptionCode;
	public String invoiceRefNo;
	public String invoiceType;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(
			new short[] { 16, 1, 15, 40, 100, 8, 1, 16, 25, 3, 5, 400, 16, 16, 5, 20, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "customerNo", "customerTitle", "dueDate", "recordType",
					"invoiceNo", "invoiceAmount", "currency", "processStatu", "processExplanation", "fileOID",
					"masterOID", "specialExceptionCode", "invoiceRefNo", "invoiceType" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, null,
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000013389400002L;

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
		customerNo = null;
		customerTitle = null;
		dueDate = null;
		recordType = null;
		invoiceNo = null;
		invoiceAmount = null;
		currency = null;
		processStatu = null;
		processExplanation = null;
		fileOID = 0;
		masterOID = 0;
		specialExceptionCode = null;
		invoiceRefNo = null;
		invoiceType = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileDetailPOMData pomOrigin = (DBSCompanyDebtFileDetailPOMData) POMDataCache.getInstance()
				.getPOMData(iD, oID);
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
		DBSCompanyDebtFileDetailPOMData cPOMData = (DBSCompanyDebtFileDetailPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.customerNo = customerNo;
		cPOMData.customerTitle = customerTitle;
		if (dueDate == null) {
			cPOMData.dueDate = null;
		} else if (cPOMData.dueDate == null) {
			cPOMData.dueDate = (CBDate) dueDate.clone();
		} else {
			dueDate.copy(cPOMData.dueDate);
		}
		cPOMData.recordType = recordType;
		cPOMData.invoiceNo = invoiceNo;
		if (invoiceAmount == null) {
			cPOMData.invoiceAmount = null;
		} else if (cPOMData.invoiceAmount == null) {
			cPOMData.invoiceAmount = (CBCurrency) invoiceAmount.clone();
		} else {
			invoiceAmount.copy(cPOMData.invoiceAmount);
		}
		cPOMData.currency = currency;
		cPOMData.processStatu = processStatu;
		cPOMData.processExplanation = processExplanation;
		cPOMData.fileOID = fileOID;
		cPOMData.masterOID = masterOID;
		cPOMData.specialExceptionCode = specialExceptionCode;
		cPOMData.invoiceRefNo = invoiceRefNo;
		cPOMData.invoiceType = invoiceType;
	}

	public Object clone() {
		DBSCompanyDebtFileDetailPOMData clonePOMData = DBSCompanyDebtFileDetailPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileDetailPOMData newInstance() {
		DBSCompanyDebtFileDetailPOMData pomData = (DBSCompanyDebtFileDetailPOMData) POMDataPool.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileDetailPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileDetailPOMData newInstance(CBBag inBag) throws CBException {
		DBSCompanyDebtFileDetailPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileDetailPOMData newInstance(CBRow row) throws CBException {
		DBSCompanyDebtFileDetailPOMData pomData = newInstance();
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
