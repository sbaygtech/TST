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

public class DBSCompanyDebtFileDefinitionPOMData extends POMData {
	protected DBSCompanyDebtFileDefinitionPOMData() {
	}

	public long companyOID;
	public String fileTypeCode;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 3 }, new short[] { 0, 0, 0, 0, 0 }, new short[] { 0,
			0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0 }, new String[] { "oID",
			"status", "lastUpdated", "companyOID", "fileTypeCode" },
			new short[] { 0, 0, 0, 0, 0 });
	private static final long iD = 3000011868500086L;

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
		fileTypeCode = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileDefinitionPOMData pomOrigin = (DBSCompanyDebtFileDefinitionPOMData) POMDataCache
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
		DBSCompanyDebtFileDefinitionPOMData cPOMData = (DBSCompanyDebtFileDefinitionPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOID = companyOID;
		cPOMData.fileTypeCode = fileTypeCode;
	}

	public Object clone() {
		DBSCompanyDebtFileDefinitionPOMData clonePOMData = DBSCompanyDebtFileDefinitionPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileDefinitionPOMData newInstance() {
		DBSCompanyDebtFileDefinitionPOMData pomData = (DBSCompanyDebtFileDefinitionPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileDefinitionPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileDefinitionPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyDebtFileDefinitionPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileDefinitionPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileDefinitionPOMData pomData = newInstance();
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
