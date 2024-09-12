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

public class DBSCompanyLimitFileParamPOMData extends POMData {
	protected DBSCompanyLimitFileParamPOMData() {
	}

	public boolean mailingEnabled;
	public boolean textFileEnabled;
	public boolean dealerDefSentMail;
	public long companyOID;
	public int accountOption;
	public String limitFileName;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 1, 1, 1, 16, 3, 100 }, new short[] { 0, 0, 0, 0, 0, 0,
			0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 1 }, new short[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "mailingEnabled",
					"textFileEnabled", "dealerDefSentMail", "companyOID",
					"accountOption", "limitFileName" }, new short[] { 0, 0, 0,
					0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 1, 1, 1, 1, 0, 0 });
	private static final long iD = 3000012345700148L;

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
		textFileEnabled = false;
		dealerDefSentMail = false;
		companyOID = 0;
		accountOption = 0;
		limitFileName = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyLimitFileParamPOMData pomOrigin = (DBSCompanyLimitFileParamPOMData) POMDataCache
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
		DBSCompanyLimitFileParamPOMData cPOMData = (DBSCompanyLimitFileParamPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.mailingEnabled = mailingEnabled;
		cPOMData.textFileEnabled = textFileEnabled;
		cPOMData.dealerDefSentMail = dealerDefSentMail;
		cPOMData.companyOID = companyOID;
		cPOMData.accountOption = accountOption;
		cPOMData.limitFileName = limitFileName;
	}

	public Object clone() {
		DBSCompanyLimitFileParamPOMData clonePOMData = DBSCompanyLimitFileParamPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyLimitFileParamPOMData newInstance() {
		DBSCompanyLimitFileParamPOMData pomData = (DBSCompanyLimitFileParamPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyLimitFileParamPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyLimitFileParamPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyLimitFileParamPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyLimitFileParamPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyLimitFileParamPOMData pomData = newInstance();
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
