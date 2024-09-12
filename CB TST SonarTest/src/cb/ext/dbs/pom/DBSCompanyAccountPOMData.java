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

public class DBSCompanyAccountPOMData extends POMData {
	protected DBSCompanyAccountPOMData() {
	}

	public long companyOID;
	public String currency;
	public String blockageAccountNo;
	public String accountNo;
	public int blockageDayCount;
	public boolean active;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 3, 30, 30, 2, 1 }, new short[] { 0, 0, 0, 0, 0, 0,
			0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 1, 0, 0, 0 }, new short[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID", "status",
			"lastUpdated", "companyOID", "currency", "blockageAccountNo",
			"accountNo", "blockageDayCount", "active" }, new short[] { 0, 0, 0,
			0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000009557800019L;

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
		currency = null;
		blockageAccountNo = null;
		accountNo = null;
		blockageDayCount = 0;
		active = false;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyAccountPOMData pomOrigin = (DBSCompanyAccountPOMData) POMDataCache
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
		DBSCompanyAccountPOMData cPOMData = (DBSCompanyAccountPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOID = companyOID;
		cPOMData.currency = currency;
		cPOMData.blockageAccountNo = blockageAccountNo;
		cPOMData.accountNo = accountNo;
		cPOMData.blockageDayCount = blockageDayCount;
		cPOMData.active = active;
	}

	public Object clone() {
		DBSCompanyAccountPOMData clonePOMData = DBSCompanyAccountPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyAccountPOMData newInstance() {
		DBSCompanyAccountPOMData pomData = (DBSCompanyAccountPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyAccountPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyAccountPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyAccountPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyAccountPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyAccountPOMData pomData = newInstance();
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
