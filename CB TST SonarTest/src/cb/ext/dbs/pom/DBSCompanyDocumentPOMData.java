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

public class DBSCompanyDocumentPOMData extends POMData {
	protected DBSCompanyDocumentPOMData() {
	}

	public long companyOid;
	public int documentCode;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] { 16, 1, 15, 16, 2 },
			new short[] { 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "companyOid", "documentCode" },
			new short[] { 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 });
	private static final long iD = 3000013389400005L;

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
		documentCode = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDocumentPOMData pomOrigin = (DBSCompanyDocumentPOMData) POMDataCache.getInstance().getPOMData(iD,
				oID);
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
		DBSCompanyDocumentPOMData cPOMData = (DBSCompanyDocumentPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOid = companyOid;
		cPOMData.documentCode = documentCode;
	}

	public Object clone() {
		DBSCompanyDocumentPOMData clonePOMData = DBSCompanyDocumentPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDocumentPOMData newInstance() {
		DBSCompanyDocumentPOMData pomData = (DBSCompanyDocumentPOMData) POMDataPool.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDocumentPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDocumentPOMData newInstance(CBBag inBag) throws CBException {
		DBSCompanyDocumentPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDocumentPOMData newInstance(CBRow row) throws CBException {
		DBSCompanyDocumentPOMData pomData = newInstance();
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
