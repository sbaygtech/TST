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

public class DBSCompanyLimitFileSchedulePOMData extends POMData {
	private DBSCompanyLimitFileSchedulePOMData() {
	}

	public long companyOID;
	public String time;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 6 }, new short[] { 0, 0, 0, 0, 0 }, new short[] { 0,
			0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 }, new String[] { "oID",
			"status", "lastUpdated", "companyOID", "time" }, new short[] { 0,
			0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 });
	private static final long iD = 3000008665800039L;

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
		companyOID = 0;
		time = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		if (data1 == null && data2 == null)
			return false;
		if (data1 != null && data2 == null)
			return true;
		if (data1 == null && data2 != null)
			return true;
		return !data1.equals(data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyLimitFileSchedulePOMData pomOrigin = (DBSCompanyLimitFileSchedulePOMData) POMDataCache
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
		DBSCompanyLimitFileSchedulePOMData cPOMData = (DBSCompanyLimitFileSchedulePOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOID = companyOID;
		cPOMData.time = time;
	}

	public Object clone() {
		DBSCompanyLimitFileSchedulePOMData clonePOMData = DBSCompanyLimitFileSchedulePOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyLimitFileSchedulePOMData newInstance() {
		DBSCompanyLimitFileSchedulePOMData pomData = (DBSCompanyLimitFileSchedulePOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyLimitFileSchedulePOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyLimitFileSchedulePOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyLimitFileSchedulePOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyLimitFileSchedulePOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyLimitFileSchedulePOMData pomData = newInstance();
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
