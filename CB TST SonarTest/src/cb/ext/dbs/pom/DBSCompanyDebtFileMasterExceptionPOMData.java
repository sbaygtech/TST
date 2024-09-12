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

public class DBSCompanyDebtFileMasterExceptionPOMData extends POMData {
	protected DBSCompanyDebtFileMasterExceptionPOMData() {
	}

	public long masterOID;
	public String exceptionCode;
	public String exceptionDescription;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 5, 500 }, new short[] { 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "masterOID",
					"exceptionCode", "exceptionDescription" }, new short[] { 0,
					0, 0, 0, 0, 0 });
	private static final long iD = 3000012253000127L;

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
		exceptionCode = null;
		exceptionDescription = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileMasterExceptionPOMData pomOrigin = (DBSCompanyDebtFileMasterExceptionPOMData) POMDataCache
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
		DBSCompanyDebtFileMasterExceptionPOMData cPOMData = (DBSCompanyDebtFileMasterExceptionPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.masterOID = masterOID;
		cPOMData.exceptionCode = exceptionCode;
		cPOMData.exceptionDescription = exceptionDescription;
	}

	public Object clone() {
		DBSCompanyDebtFileMasterExceptionPOMData clonePOMData = DBSCompanyDebtFileMasterExceptionPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileMasterExceptionPOMData newInstance() {
		DBSCompanyDebtFileMasterExceptionPOMData pomData = (DBSCompanyDebtFileMasterExceptionPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileMasterExceptionPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileMasterExceptionPOMData newInstance(
			CBBag inBag) throws CBException {
		DBSCompanyDebtFileMasterExceptionPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileMasterExceptionPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileMasterExceptionPOMData pomData = newInstance();
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
