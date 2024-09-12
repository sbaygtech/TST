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

public class DBSCompanyProcessDebtPaymentExtensionPOMData extends POMData {
	protected DBSCompanyProcessDebtPaymentExtensionPOMData() {
	}

	public long masterOID;
	public CBCurrency paymentAmount;
	public CBDate paymentDate;
	public CBTime paymentTime;
	public long productOperationRefNo;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 25, 8, 6, 16 },
			new short[] { 0, 0, 0, 0, 2, 0, 0, 0 }, new short[] { 0, 0, 0, 0,
					0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "masterOID",
					"paymentAmount", "paymentDate", "paymentTime",
					"productOperationRefNo" }, new short[] { 0, 0, 0, 0, 0, 0,
					0, 0 });
	private static final long iD = 3000011937800002L;

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
		masterOID = 0;
		paymentAmount = null;
		paymentDate = null;
		paymentTime = null;
		productOperationRefNo = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyProcessDebtPaymentExtensionPOMData pomOrigin = (DBSCompanyProcessDebtPaymentExtensionPOMData) POMDataCache
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
		DBSCompanyProcessDebtPaymentExtensionPOMData cPOMData = (DBSCompanyProcessDebtPaymentExtensionPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.masterOID = masterOID;
		if (paymentAmount == null) {
			cPOMData.paymentAmount = null;
		} else if (cPOMData.paymentAmount == null) {
			cPOMData.paymentAmount = (CBCurrency) paymentAmount.clone();
		} else {
			paymentAmount.copy(cPOMData.paymentAmount);
		}
		if (paymentDate == null) {
			cPOMData.paymentDate = null;
		} else if (cPOMData.paymentDate == null) {
			cPOMData.paymentDate = (CBDate) paymentDate.clone();
		} else {
			paymentDate.copy(cPOMData.paymentDate);
		}
		if (paymentTime == null) {
			cPOMData.paymentTime = null;
		} else if (cPOMData.paymentTime == null) {
			cPOMData.paymentTime = (CBTime) paymentTime.clone();
		} else {
			paymentTime.copy(cPOMData.paymentTime);
		}
		cPOMData.productOperationRefNo = productOperationRefNo;
	}

	public Object clone() {
		DBSCompanyProcessDebtPaymentExtensionPOMData clonePOMData = DBSCompanyProcessDebtPaymentExtensionPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyProcessDebtPaymentExtensionPOMData newInstance() {
		DBSCompanyProcessDebtPaymentExtensionPOMData pomData = (DBSCompanyProcessDebtPaymentExtensionPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyProcessDebtPaymentExtensionPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyProcessDebtPaymentExtensionPOMData newInstance(
			CBBag inBag) throws CBException {
		DBSCompanyProcessDebtPaymentExtensionPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyProcessDebtPaymentExtensionPOMData newInstance(
			CBRow row) throws CBException {
		DBSCompanyProcessDebtPaymentExtensionPOMData pomData = newInstance();
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
