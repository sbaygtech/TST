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

public class DBSDealerUploadDocPOMData extends POMData {
	protected DBSDealerUploadDocPOMData() {
	}

	public long documentID;
	public String explanation;
	public String fileName;
	public CBDate uploadDate;
	public CBTime uploadTime;
	public String uploader;
	public long dealerOID;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 4000, 4000, 8, 6, 400, 16 }, new short[] { 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID", "status",
					"lastUpdated", "documentID", "explanation", "fileName",
					"uploadDate", "uploadTime", "uploader", "dealerOID" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000010389500054L;

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
		documentID = 0;
		explanation = null;
		fileName = null;
		uploadDate = null;
		uploadTime = null;
		uploader = null;
		dealerOID = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSDealerUploadDocPOMData pomOrigin = (DBSDealerUploadDocPOMData) POMDataCache
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
		DBSDealerUploadDocPOMData cPOMData = (DBSDealerUploadDocPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.documentID = documentID;
		cPOMData.explanation = explanation;
		cPOMData.fileName = fileName;
		if (uploadDate == null) {
			cPOMData.uploadDate = null;
		} else if (cPOMData.uploadDate == null) {
			cPOMData.uploadDate = (CBDate) uploadDate.clone();
		} else {
			uploadDate.copy(cPOMData.uploadDate);
		}
		if (uploadTime == null) {
			cPOMData.uploadTime = null;
		} else if (cPOMData.uploadTime == null) {
			cPOMData.uploadTime = (CBTime) uploadTime.clone();
		} else {
			uploadTime.copy(cPOMData.uploadTime);
		}
		cPOMData.uploader = uploader;
		cPOMData.dealerOID = dealerOID;
	}

	public Object clone() {
		DBSDealerUploadDocPOMData clonePOMData = DBSDealerUploadDocPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSDealerUploadDocPOMData newInstance() {
		DBSDealerUploadDocPOMData pomData = (DBSDealerUploadDocPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSDealerUploadDocPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSDealerUploadDocPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSDealerUploadDocPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSDealerUploadDocPOMData newInstance(CBRow row)
			throws CBException {
		DBSDealerUploadDocPOMData pomData = newInstance();
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
