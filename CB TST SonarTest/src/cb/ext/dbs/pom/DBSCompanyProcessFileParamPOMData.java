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

public class DBSCompanyProcessFileParamPOMData extends POMData {
	protected DBSCompanyProcessFileParamPOMData() {
	}

	public long companyOID;
	public boolean fileEnabled;
	public boolean mailingEnabled;
	public String processFileName;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 1, 1, 100 }, new short[] { 0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 1 }, new short[] { 0, 0, 0, 0, 0,
					0, 0 }, new String[] { "oID", "status", "lastUpdated",
					"companyOID", "fileEnabled", "mailingEnabled",
					"processFileName" }, new short[] { 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000012313300075L;

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
		fileEnabled = false;
		mailingEnabled = false;
		processFileName = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyProcessFileParamPOMData pomOrigin = (DBSCompanyProcessFileParamPOMData) POMDataCache
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
		DBSCompanyProcessFileParamPOMData cPOMData = (DBSCompanyProcessFileParamPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOID = companyOID;
		cPOMData.fileEnabled = fileEnabled;
		cPOMData.mailingEnabled = mailingEnabled;
		cPOMData.processFileName = processFileName;
	}

	public Object clone() {
		DBSCompanyProcessFileParamPOMData clonePOMData = DBSCompanyProcessFileParamPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyProcessFileParamPOMData newInstance() {
		DBSCompanyProcessFileParamPOMData pomData = (DBSCompanyProcessFileParamPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyProcessFileParamPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyProcessFileParamPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyProcessFileParamPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyProcessFileParamPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyProcessFileParamPOMData pomData = newInstance();
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
