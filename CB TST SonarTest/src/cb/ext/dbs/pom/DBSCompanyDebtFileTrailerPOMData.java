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

public class DBSCompanyDebtFileTrailerPOMData extends POMData {
	protected DBSCompanyDebtFileTrailerPOMData() {
	}

	public int invoiceTotalCountNew;
	public CBCurrency invoiceTotalAmountNew;
	public long invoiceTotalCountCancel;
	public CBCurrency invoiceTotalAmountCancel;
	public long invoiceTotalCountUpdate;
	public CBCurrency invoiceTotalAmountUpdate;
	public long headerOID;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 7, 25, 7, 25, 7, 25, 16 }, new short[] { 0, 0, 0, 0, 2,
			0, 2, 0, 2, 0 }, new short[] { 0, 0, 0, 0, 1, 1, 1, 1, 1, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID",
					"status", "lastUpdated", "invoiceTotalCountNew",
					"invoiceTotalAmountNew", "invoiceTotalCountCancel",
					"invoiceTotalAmountCancel", "invoiceTotalCountUpdate",
					"invoiceTotalAmountUpdate", "headerOID" }, new short[] { 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000013271000002L;

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
		invoiceTotalCountNew = 0;
		invoiceTotalAmountNew = null;
		invoiceTotalCountCancel = 0;
		invoiceTotalAmountCancel = null;
		invoiceTotalCountUpdate = 0;
		invoiceTotalAmountUpdate = null;
		headerOID = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileTrailerPOMData pomOrigin = (DBSCompanyDebtFileTrailerPOMData) POMDataCache
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
		DBSCompanyDebtFileTrailerPOMData cPOMData = (DBSCompanyDebtFileTrailerPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.invoiceTotalCountNew = invoiceTotalCountNew;
		if (invoiceTotalAmountNew == null) {
			cPOMData.invoiceTotalAmountNew = null;
		} else if (cPOMData.invoiceTotalAmountNew == null) {
			cPOMData.invoiceTotalAmountNew = (CBCurrency) invoiceTotalAmountNew
					.clone();
		} else {
			invoiceTotalAmountNew.copy(cPOMData.invoiceTotalAmountNew);
		}
		cPOMData.invoiceTotalCountCancel = invoiceTotalCountCancel;
		if (invoiceTotalAmountCancel == null) {
			cPOMData.invoiceTotalAmountCancel = null;
		} else if (cPOMData.invoiceTotalAmountCancel == null) {
			cPOMData.invoiceTotalAmountCancel = (CBCurrency) invoiceTotalAmountCancel
					.clone();
		} else {
			invoiceTotalAmountCancel.copy(cPOMData.invoiceTotalAmountCancel);
		}
		cPOMData.invoiceTotalCountUpdate = invoiceTotalCountUpdate;
		if (invoiceTotalAmountUpdate == null) {
			cPOMData.invoiceTotalAmountUpdate = null;
		} else if (cPOMData.invoiceTotalAmountUpdate == null) {
			cPOMData.invoiceTotalAmountUpdate = (CBCurrency) invoiceTotalAmountUpdate
					.clone();
		} else {
			invoiceTotalAmountUpdate.copy(cPOMData.invoiceTotalAmountUpdate);
		}
		cPOMData.headerOID = headerOID;
	}

	public Object clone() {
		DBSCompanyDebtFileTrailerPOMData clonePOMData = DBSCompanyDebtFileTrailerPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileTrailerPOMData newInstance() {
		DBSCompanyDebtFileTrailerPOMData pomData = (DBSCompanyDebtFileTrailerPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileTrailerPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileTrailerPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyDebtFileTrailerPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileTrailerPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileTrailerPOMData pomData = newInstance();
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
