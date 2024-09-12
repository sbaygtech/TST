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

public class DBSCompanyDebtFileHeaderPOMData extends POMData {
	protected DBSCompanyDebtFileHeaderPOMData() {
	}

	public CBDate insertDate;
	public CBTime insertTime;
	public String fileName;
	public String fileSequenceNumber;
	public String fileDate;
	public String companyCode;
	public String documentID;
	public String mailID;
	public long companyOID;
	public String fileCurrency;
	public String districtCode;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 8, 6, 60, 8, 8, 100, 30, 30, 16, 5, 20 }, new short[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, new short[] { 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID", "status",
			"lastUpdated", "insertDate", "insertTime", "fileName",
			"fileSequenceNumber", "fileDate", "companyCode", "documentID",
			"mailID", "companyOID", "fileCurrency", "districtCode" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000012345700037L;

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
		insertDate = null;
		insertTime = null;
		fileName = null;
		fileSequenceNumber = null;
		fileDate = null;
		companyCode = null;
		documentID = null;
		mailID = null;
		companyOID = 0;
		fileCurrency = null;
		districtCode = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileHeaderPOMData pomOrigin = (DBSCompanyDebtFileHeaderPOMData) POMDataCache
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
		DBSCompanyDebtFileHeaderPOMData cPOMData = (DBSCompanyDebtFileHeaderPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		if (insertDate == null) {
			cPOMData.insertDate = null;
		} else if (cPOMData.insertDate == null) {
			cPOMData.insertDate = (CBDate) insertDate.clone();
		} else {
			insertDate.copy(cPOMData.insertDate);
		}
		if (insertTime == null) {
			cPOMData.insertTime = null;
		} else if (cPOMData.insertTime == null) {
			cPOMData.insertTime = (CBTime) insertTime.clone();
		} else {
			insertTime.copy(cPOMData.insertTime);
		}
		cPOMData.fileName = fileName;
		cPOMData.fileSequenceNumber = fileSequenceNumber;
		cPOMData.fileDate = fileDate;
		cPOMData.companyCode = companyCode;
		cPOMData.documentID = documentID;
		cPOMData.mailID = mailID;
		cPOMData.companyOID = companyOID;
		cPOMData.fileCurrency = fileCurrency;
		cPOMData.districtCode = districtCode;
	}

	public Object clone() {
		DBSCompanyDebtFileHeaderPOMData clonePOMData = DBSCompanyDebtFileHeaderPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileHeaderPOMData newInstance() {
		DBSCompanyDebtFileHeaderPOMData pomData = (DBSCompanyDebtFileHeaderPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileHeaderPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileHeaderPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyDebtFileHeaderPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileHeaderPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileHeaderPOMData pomData = newInstance();
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
