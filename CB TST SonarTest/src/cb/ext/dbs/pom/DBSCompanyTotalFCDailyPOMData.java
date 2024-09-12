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

public class DBSCompanyTotalFCDailyPOMData extends POMData {
	protected DBSCompanyTotalFCDailyPOMData() {
	}

	public long companyOID;
	public CBCurrency amount;
	public CBDate processDate;
	public String currency;
	public CBTime processTime;
	public CBCurrency exchangeRate;
	public CBCurrency tryAmount;
	public long dealerOID;
	public long masterOID;
	public long sequenceNo;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 25, 8, 10, 6, 25, 25, 16, 16, 16 }, new short[] { 0,
			0, 0, 0, 2, 0, 0, 0, 4, 4, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "companyOID",
					"amount", "processDate", "currency", "processTime",
					"exchangeRate", "tryAmount", "dealerOID", "masterOID",
					"sequenceNo" }, new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0 });
	private static final long iD = 3000010411000152L;

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
		amount = null;
		processDate = null;
		currency = null;
		processTime = null;
		exchangeRate = null;
		tryAmount = null;
		dealerOID = 0;
		masterOID = 0;
		sequenceNo = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyTotalFCDailyPOMData pomOrigin = (DBSCompanyTotalFCDailyPOMData) POMDataCache
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
		DBSCompanyTotalFCDailyPOMData cPOMData = (DBSCompanyTotalFCDailyPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.companyOID = companyOID;
		if (amount == null) {
			cPOMData.amount = null;
		} else if (cPOMData.amount == null) {
			cPOMData.amount = (CBCurrency) amount.clone();
		} else {
			amount.copy(cPOMData.amount);
		}
		if (processDate == null) {
			cPOMData.processDate = null;
		} else if (cPOMData.processDate == null) {
			cPOMData.processDate = (CBDate) processDate.clone();
		} else {
			processDate.copy(cPOMData.processDate);
		}
		cPOMData.currency = currency;
		if (processTime == null) {
			cPOMData.processTime = null;
		} else if (cPOMData.processTime == null) {
			cPOMData.processTime = (CBTime) processTime.clone();
		} else {
			processTime.copy(cPOMData.processTime);
		}
		if (exchangeRate == null) {
			cPOMData.exchangeRate = null;
		} else if (cPOMData.exchangeRate == null) {
			cPOMData.exchangeRate = (CBCurrency) exchangeRate.clone();
		} else {
			exchangeRate.copy(cPOMData.exchangeRate);
		}
		if (tryAmount == null) {
			cPOMData.tryAmount = null;
		} else if (cPOMData.tryAmount == null) {
			cPOMData.tryAmount = (CBCurrency) tryAmount.clone();
		} else {
			tryAmount.copy(cPOMData.tryAmount);
		}
		cPOMData.dealerOID = dealerOID;
		cPOMData.masterOID = masterOID;
		cPOMData.sequenceNo = sequenceNo;
	}

	public Object clone() {
		DBSCompanyTotalFCDailyPOMData clonePOMData = DBSCompanyTotalFCDailyPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyTotalFCDailyPOMData newInstance() {
		DBSCompanyTotalFCDailyPOMData pomData = (DBSCompanyTotalFCDailyPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyTotalFCDailyPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyTotalFCDailyPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyTotalFCDailyPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyTotalFCDailyPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyTotalFCDailyPOMData pomData = newInstance();
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
