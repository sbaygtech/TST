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

public class DBSDealerGuarantorPOMData extends POMData {
	protected DBSDealerGuarantorPOMData() {
	}

	public long companyOid;
	public long dealerOid;
	public CBDate startDate;
	public CBDate finishDate;
	public int active;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 16, 8, 8, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0,
					0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "companyOid",
					"dealerOid", "startDate", "finishDate", "active" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0,
					0, 0, 0, 0 });
	private static final long iD = 3000013179000036L;

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
		companyOid = 0;
		dealerOid = 0;
		startDate = null;
		finishDate = null;
		active = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSDealerGuarantorPOMData pomOrigin = (DBSDealerGuarantorPOMData) POMDataCache
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
		DBSDealerGuarantorPOMData cPOMData = (DBSDealerGuarantorPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOid = companyOid;
		cPOMData.dealerOid = dealerOid;
		if (startDate == null) {
			cPOMData.startDate = null;
		} else if (cPOMData.startDate == null) {
			cPOMData.startDate = (CBDate) startDate.clone();
		} else {
			startDate.copy(cPOMData.startDate);
		}
		if (finishDate == null) {
			cPOMData.finishDate = null;
		} else if (cPOMData.finishDate == null) {
			cPOMData.finishDate = (CBDate) finishDate.clone();
		} else {
			finishDate.copy(cPOMData.finishDate);
		}
		cPOMData.active = active;
	}

	public Object clone() {
		DBSDealerGuarantorPOMData clonePOMData = DBSDealerGuarantorPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSDealerGuarantorPOMData newInstance() {
		DBSDealerGuarantorPOMData pomData = (DBSDealerGuarantorPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSDealerGuarantorPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSDealerGuarantorPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSDealerGuarantorPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSDealerGuarantorPOMData newInstance(CBRow row)
			throws CBException {
		DBSDealerGuarantorPOMData pomData = newInstance();
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
