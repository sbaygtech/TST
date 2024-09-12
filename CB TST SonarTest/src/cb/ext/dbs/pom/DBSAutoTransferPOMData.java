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

public class DBSAutoTransferPOMData extends POMData {
	protected DBSAutoTransferPOMData() {
	}

	public String debitAccount;
	public String creditAccount;
	public CBDate accountingDate;
	public CBCurrency totalAmount;
	public long companyOID;
	public int totalCount;
	public long prodOperRefNo;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] { 16, 1, 15, 30, 30, 8, 25, 16, 5, 16 },
			new short[] { 0, 0, 0, 0, 0, 0, 2, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "debitAccount", "creditAccount", "accountingDate",
					"totalAmount", "companyOID", "totalCount", "prodOperRefNo" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, null, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000013389400011L;

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
		debitAccount = null;
		creditAccount = null;
		accountingDate = null;
		totalAmount = null;
		companyOID = 0;
		totalCount = 0;
		prodOperRefNo = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSAutoTransferPOMData pomOrigin = (DBSAutoTransferPOMData) POMDataCache.getInstance().getPOMData(iD, oID);
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
		DBSAutoTransferPOMData cPOMData = (DBSAutoTransferPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.debitAccount = debitAccount;
		cPOMData.creditAccount = creditAccount;
		if (accountingDate == null) {
			cPOMData.accountingDate = null;
		} else if (cPOMData.accountingDate == null) {
			cPOMData.accountingDate = (CBDate) accountingDate.clone();
		} else {
			accountingDate.copy(cPOMData.accountingDate);
		}
		if (totalAmount == null) {
			cPOMData.totalAmount = null;
		} else if (cPOMData.totalAmount == null) {
			cPOMData.totalAmount = (CBCurrency) totalAmount.clone();
		} else {
			totalAmount.copy(cPOMData.totalAmount);
		}
		cPOMData.companyOID = companyOID;
		cPOMData.totalCount = totalCount;
		cPOMData.prodOperRefNo = prodOperRefNo;
	}

	public Object clone() {
		DBSAutoTransferPOMData clonePOMData = DBSAutoTransferPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSAutoTransferPOMData newInstance() {
		DBSAutoTransferPOMData pomData = (DBSAutoTransferPOMData) POMDataPool.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSAutoTransferPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSAutoTransferPOMData newInstance(CBBag inBag) throws CBException {
		DBSAutoTransferPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSAutoTransferPOMData newInstance(CBRow row) throws CBException {
		DBSAutoTransferPOMData pomData = newInstance();
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
