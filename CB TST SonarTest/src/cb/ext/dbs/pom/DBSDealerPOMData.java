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

public class DBSDealerPOMData extends POMData {
	protected DBSDealerPOMData() {
	}

	public boolean active;
	public long companyOID;
	public int customerNumber;
	public CBDate insertDate;
	public CBTime insertTime;
	public String insertUser;
	public CBDate updDate;
	public CBTime updTime;
	public String updUser;
	public String companyDealerCustomerCode;
	public long productRefNo;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 1, 16, 10, 8, 6, 10, 8, 6, 10, 40, 14 }, new short[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID", "status",
			"lastUpdated", "active", "companyOID", "customerNumber",
			"insertDate", "insertTime", "insertUser", "updDate", "updTime",
			"updUser", "companyDealerCustomerCode", "productRefNo" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 });
	private static final long iD = 3000013147800027L;

	public CBBag toBag() throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		return toBag(outBag);
	}

	public CBBag toBag(CBBag outBag) throws CBException {
		outBag.put(CBBagKeys.OID, oID);
		outBag.put(CBBagKeys.STATUS, status);
		outBag.put(CBBagKeys.LASTUPDATED, lastUpdated);
		outBag.put(CBBagKeys.ACTIVE, active);
		outBag.put(CBBagKeys.CODE1, companyDealerCustomerCode);
		return outBag;
	}

	public CBRow toBag(CBRow row) throws CBException {
		row.addColumn(CBBagKeys.OID, oID);
		row.addColumn(CBBagKeys.STATUS, status);
		row.addColumn(CBBagKeys.LASTUPDATED, lastUpdated);
		row.addColumn(CBBagKeys.ACTIVE, active);
		row.addColumn(CBBagKeys.CODE1, companyDealerCustomerCode);
		return row;
	}

	public void setBag(CBBag inBag) throws CBException {
		if (inBag.existsBagKey(CBBagKeys.ACTIVE))
			active = inBag.get(CBBagKeys.ACTIVE).toBoolean();
		if (inBag.existsBagKey(CBBagKeys.CODE1))
			companyDealerCustomerCode = inBag.get(CBBagKeys.CODE1).toString();
	}

	public void setBag(CBRow row) throws CBException {
		if (row.existsBagKey(CBBagKeys.ACTIVE))
			active = row.get(CBBagKeys.ACTIVE).toBoolean();
		if (row.existsBagKey(CBBagKeys.CODE1))
			companyDealerCustomerCode = row.get(CBBagKeys.CODE1).toString();
	}

	protected void clear() {
		super.clear();
		active = false;
		companyOID = 0;
		customerNumber = 0;
		insertDate = null;
		insertTime = null;
		insertUser = null;
		updDate = null;
		updTime = null;
		updUser = null;
		companyDealerCustomerCode = null;
		productRefNo = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSDealerPOMData pomOrigin = (DBSDealerPOMData) POMDataCache
				.getInstance().getPOMData(iD, oID);
		for (int bagkey : arBagKeys) {
			switch (bagkey) {
			case CBBagKeys.STATUS:
				if (pomOrigin.status != status)
					return true;
				break;
			case CBBagKeys.ACTIVE:
				if (pomOrigin.active != active)
					return true;
				break;
			case CBBagKeys.CODE1:
				if (isDifferent(pomOrigin.companyDealerCustomerCode,
						companyDealerCustomerCode))
					return true;
				break;
			}
		}
		return false;
	}

	public boolean isChangedForHistory() {
		return isChanged(CBBagKeys.ACTIVE, CBBagKeys.CODE1);
	}

	public void copy(POMData data) {
		DBSDealerPOMData cPOMData = (DBSDealerPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.active = active;
		cPOMData.companyOID = companyOID;
		cPOMData.customerNumber = customerNumber;
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
		cPOMData.insertUser = insertUser;
		if (updDate == null) {
			cPOMData.updDate = null;
		} else if (cPOMData.updDate == null) {
			cPOMData.updDate = (CBDate) updDate.clone();
		} else {
			updDate.copy(cPOMData.updDate);
		}
		if (updTime == null) {
			cPOMData.updTime = null;
		} else if (cPOMData.updTime == null) {
			cPOMData.updTime = (CBTime) updTime.clone();
		} else {
			updTime.copy(cPOMData.updTime);
		}
		cPOMData.updUser = updUser;
		cPOMData.companyDealerCustomerCode = companyDealerCustomerCode;
		cPOMData.productRefNo = productRefNo;
	}

	public Object clone() {
		DBSDealerPOMData clonePOMData = DBSDealerPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSDealerPOMData newInstance() {
		DBSDealerPOMData pomData = (DBSDealerPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSDealerPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSDealerPOMData newInstance(CBBag inBag) throws CBException {
		DBSDealerPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSDealerPOMData newInstance(CBRow row) throws CBException {
		DBSDealerPOMData pomData = newInstance();
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
