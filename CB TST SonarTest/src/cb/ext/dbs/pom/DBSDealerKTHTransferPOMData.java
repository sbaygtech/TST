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

public class DBSDealerKTHTransferPOMData extends POMData {
	protected DBSDealerKTHTransferPOMData() {
	}

	public String debitAccount;
	public String creditAccount;
	public CBCurrency transferAmount;
	public CBDate processDate;
	public CBTime processTime;
	public String currency;
	public long productOpRefNo;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 15, 15, 25, 8, 6, 10, 16 }, new short[] { 0, 0, 0, 0, 0,
			2, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new String[] { "oID",
					"status", "lastUpdated", "debitAccount", "creditAccount",
					"transferAmount", "processDate", "processTime", "currency",
					"productOpRefNo" }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0 });
	private static final long iD = 3000010411000105L;

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
		transferAmount = null;
		processDate = null;
		processTime = null;
		currency = null;
		productOpRefNo = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSDealerKTHTransferPOMData pomOrigin = (DBSDealerKTHTransferPOMData) POMDataCache
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
		DBSDealerKTHTransferPOMData cPOMData = (DBSDealerKTHTransferPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.debitAccount = debitAccount;
		cPOMData.creditAccount = creditAccount;
		if (transferAmount == null) {
			cPOMData.transferAmount = null;
		} else if (cPOMData.transferAmount == null) {
			cPOMData.transferAmount = (CBCurrency) transferAmount.clone();
		} else {
			transferAmount.copy(cPOMData.transferAmount);
		}
		if (processDate == null) {
			cPOMData.processDate = null;
		} else if (cPOMData.processDate == null) {
			cPOMData.processDate = (CBDate) processDate.clone();
		} else {
			processDate.copy(cPOMData.processDate);
		}
		if (processTime == null) {
			cPOMData.processTime = null;
		} else if (cPOMData.processTime == null) {
			cPOMData.processTime = (CBTime) processTime.clone();
		} else {
			processTime.copy(cPOMData.processTime);
		}
		cPOMData.currency = currency;
		cPOMData.productOpRefNo = productOpRefNo;
	}

	public Object clone() {
		DBSDealerKTHTransferPOMData clonePOMData = DBSDealerKTHTransferPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSDealerKTHTransferPOMData newInstance() {
		DBSDealerKTHTransferPOMData pomData = (DBSDealerKTHTransferPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSDealerKTHTransferPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSDealerKTHTransferPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSDealerKTHTransferPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSDealerKTHTransferPOMData newInstance(CBRow row)
			throws CBException {
		DBSDealerKTHTransferPOMData pomData = newInstance();
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
