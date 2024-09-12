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

public class DBSPaymentResultHistoryPOMData extends POMData {
	protected DBSPaymentResultHistoryPOMData() {
	}

	public CBDate createdDate;
	public CBTime createdTime;
	public long masterOid;
	public long sequenceNo;
	public CBCurrency paidAmount;
	public int processType;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] { 16, 1, 15, 8, 6, 16, 16, 25, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 2, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "createdDate", "createdTime", "masterOid", "sequenceNo",
					"paidAmount", "processType" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, null, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000013343600002L;

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
		createdDate = null;
		createdTime = null;
		masterOid = 0;
		sequenceNo = 0;
		paidAmount = null;
		processType = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSPaymentResultHistoryPOMData pomOrigin = (DBSPaymentResultHistoryPOMData) POMDataCache.getInstance()
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
		DBSPaymentResultHistoryPOMData cPOMData = (DBSPaymentResultHistoryPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		if (createdDate == null) {
			cPOMData.createdDate = null;
		} else if (cPOMData.createdDate == null) {
			cPOMData.createdDate = (CBDate) createdDate.clone();
		} else {
			createdDate.copy(cPOMData.createdDate);
		}
		if (createdTime == null) {
			cPOMData.createdTime = null;
		} else if (cPOMData.createdTime == null) {
			cPOMData.createdTime = (CBTime) createdTime.clone();
		} else {
			createdTime.copy(cPOMData.createdTime);
		}
		cPOMData.masterOid = masterOid;
		cPOMData.sequenceNo = sequenceNo;
		if (paidAmount == null) {
			cPOMData.paidAmount = null;
		} else if (cPOMData.paidAmount == null) {
			cPOMData.paidAmount = (CBCurrency) paidAmount.clone();
		} else {
			paidAmount.copy(cPOMData.paidAmount);
		}
		cPOMData.processType = processType;
	}

	public Object clone() {
		DBSPaymentResultHistoryPOMData clonePOMData = DBSPaymentResultHistoryPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSPaymentResultHistoryPOMData newInstance() {
		DBSPaymentResultHistoryPOMData pomData = (DBSPaymentResultHistoryPOMData) POMDataPool.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSPaymentResultHistoryPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSPaymentResultHistoryPOMData newInstance(CBBag inBag) throws CBException {
		DBSPaymentResultHistoryPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSPaymentResultHistoryPOMData newInstance(CBRow row) throws CBException {
		DBSPaymentResultHistoryPOMData pomData = newInstance();
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
