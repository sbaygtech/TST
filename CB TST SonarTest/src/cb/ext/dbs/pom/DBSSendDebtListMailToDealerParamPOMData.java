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

public class DBSSendDebtListMailToDealerParamPOMData extends POMData {
	protected DBSSendDebtListMailToDealerParamPOMData() {
	}

	public boolean sendMailToDealer;
	public int dayCount;
	public CBTime batchTime;
	public long companyOid;
	public boolean sendSmsToDealer;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 1, 2, 6, 16, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 1,
					1, 1, 0, 1 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "sendMailToDealer",
					"dayCount", "batchTime", "companyOid", "sendSmsToDealer" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0,
					0, 0, 0, 0 });
	private static final long iD = 3000013121800002L;

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
		sendMailToDealer = false;
		dayCount = 0;
		batchTime = null;
		companyOid = 0;
		sendSmsToDealer = false;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSSendDebtListMailToDealerParamPOMData pomOrigin = (DBSSendDebtListMailToDealerParamPOMData) POMDataCache
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

	public boolean isChangedForHistory() {
		return isChanged();
	}

	public void copy(POMData data) {
		DBSSendDebtListMailToDealerParamPOMData cPOMData = (DBSSendDebtListMailToDealerParamPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.sendMailToDealer = sendMailToDealer;
		cPOMData.dayCount = dayCount;
		if (batchTime == null) {
			cPOMData.batchTime = null;
		} else if (cPOMData.batchTime == null) {
			cPOMData.batchTime = (CBTime) batchTime.clone();
		} else {
			batchTime.copy(cPOMData.batchTime);
		}
		cPOMData.companyOid = companyOid;
		cPOMData.sendSmsToDealer = sendSmsToDealer;
	}

	public Object clone() {
		DBSSendDebtListMailToDealerParamPOMData clonePOMData = DBSSendDebtListMailToDealerParamPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSSendDebtListMailToDealerParamPOMData newInstance() {
		DBSSendDebtListMailToDealerParamPOMData pomData = (DBSSendDebtListMailToDealerParamPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSSendDebtListMailToDealerParamPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSSendDebtListMailToDealerParamPOMData newInstance(
			CBBag inBag) throws CBException {
		DBSSendDebtListMailToDealerParamPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSSendDebtListMailToDealerParamPOMData newInstance(CBRow row)
			throws CBException {
		DBSSendDebtListMailToDealerParamPOMData pomData = newInstance();
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
