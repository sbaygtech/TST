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

public class DBSCompanyDebtFileResultParamPOMData extends POMData {
	protected DBSCompanyDebtFileResultParamPOMData() {
	}

	public boolean mailingEnabled;
	public String fileCompanyCode;
	public boolean textFileEnabled;
	public long companyOID;
	public String textFileType;
	public String textFileName;
	public boolean sendDebtFileList;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 1, 10, 1, 16, 3, 400, 1 }, new short[] { 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 1, 0, 0, 1, 1, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID",
					"status", "lastUpdated", "mailingEnabled",
					"fileCompanyCode", "textFileEnabled", "companyOID",
					"textFileType", "textFileName", "sendDebtFileList" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0,
					0, 1, 1, 1, 1, 0, 0, 0 });
	private static final long iD = 3000013121800048L;

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
		mailingEnabled = false;
		fileCompanyCode = null;
		textFileEnabled = false;
		companyOID = 0;
		textFileType = null;
		textFileName = null;
		sendDebtFileList = false;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileResultParamPOMData pomOrigin = (DBSCompanyDebtFileResultParamPOMData) POMDataCache
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
		DBSCompanyDebtFileResultParamPOMData cPOMData = (DBSCompanyDebtFileResultParamPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.mailingEnabled = mailingEnabled;
		cPOMData.fileCompanyCode = fileCompanyCode;
		cPOMData.textFileEnabled = textFileEnabled;
		cPOMData.companyOID = companyOID;
		cPOMData.textFileType = textFileType;
		cPOMData.textFileName = textFileName;
		cPOMData.sendDebtFileList = sendDebtFileList;
	}

	public Object clone() {
		DBSCompanyDebtFileResultParamPOMData clonePOMData = DBSCompanyDebtFileResultParamPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileResultParamPOMData newInstance() {
		DBSCompanyDebtFileResultParamPOMData pomData = (DBSCompanyDebtFileResultParamPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileResultParamPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileResultParamPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyDebtFileResultParamPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileResultParamPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileResultParamPOMData pomData = newInstance();
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
