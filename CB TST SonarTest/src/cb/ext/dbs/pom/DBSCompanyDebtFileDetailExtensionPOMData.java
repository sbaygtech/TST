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

public class DBSCompanyDebtFileDetailExtensionPOMData extends POMData {
	protected DBSCompanyDebtFileDetailExtensionPOMData() {
	}

	public long detailOID;
	public CBDate invoiceDate;
	public CBCurrency discountInterestAmount;
	public CBCurrency collectingAmount;
	public String collectingCurrencyCode;
	public CBCurrency discountIntrstCollAmount;
	public CBCurrency collectingExchangeRate;
	public String invoiceNumber;
	public CBDate transactionDate;
	public String provisionRefNo;
	public String parameter;
	public String invoiceType;
	public String debtCurrencyCode;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 16, 8, 25, 25, 3, 25, 25, 16, 8, 20, 50, 1, 3 },
			new short[] { 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated", "detailOID",
					"invoiceDate", "discountInterestAmount",
					"collectingAmount", "collectingCurrencyCode",
					"discountIntrstCollAmount", "collectingExchangeRate",
					"invoiceNumber", "transactionDate", "provisionRefNo",
					"parameter", "invoiceType", "debtCurrencyCode" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000012345700100L;

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
		detailOID = 0;
		invoiceDate = null;
		discountInterestAmount = null;
		collectingAmount = null;
		collectingCurrencyCode = null;
		discountIntrstCollAmount = null;
		collectingExchangeRate = null;
		invoiceNumber = null;
		transactionDate = null;
		provisionRefNo = null;
		parameter = null;
		invoiceType = null;
		debtCurrencyCode = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileDetailExtensionPOMData pomOrigin = (DBSCompanyDebtFileDetailExtensionPOMData) POMDataCache
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
		DBSCompanyDebtFileDetailExtensionPOMData cPOMData = (DBSCompanyDebtFileDetailExtensionPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.detailOID = detailOID;
		if (invoiceDate == null) {
			cPOMData.invoiceDate = null;
		} else if (cPOMData.invoiceDate == null) {
			cPOMData.invoiceDate = (CBDate) invoiceDate.clone();
		} else {
			invoiceDate.copy(cPOMData.invoiceDate);
		}
		if (discountInterestAmount == null) {
			cPOMData.discountInterestAmount = null;
		} else if (cPOMData.discountInterestAmount == null) {
			cPOMData.discountInterestAmount = (CBCurrency) discountInterestAmount
					.clone();
		} else {
			discountInterestAmount.copy(cPOMData.discountInterestAmount);
		}
		if (collectingAmount == null) {
			cPOMData.collectingAmount = null;
		} else if (cPOMData.collectingAmount == null) {
			cPOMData.collectingAmount = (CBCurrency) collectingAmount.clone();
		} else {
			collectingAmount.copy(cPOMData.collectingAmount);
		}
		cPOMData.collectingCurrencyCode = collectingCurrencyCode;
		if (discountIntrstCollAmount == null) {
			cPOMData.discountIntrstCollAmount = null;
		} else if (cPOMData.discountIntrstCollAmount == null) {
			cPOMData.discountIntrstCollAmount = (CBCurrency) discountIntrstCollAmount
					.clone();
		} else {
			discountIntrstCollAmount.copy(cPOMData.discountIntrstCollAmount);
		}
		if (collectingExchangeRate == null) {
			cPOMData.collectingExchangeRate = null;
		} else if (cPOMData.collectingExchangeRate == null) {
			cPOMData.collectingExchangeRate = (CBCurrency) collectingExchangeRate
					.clone();
		} else {
			collectingExchangeRate.copy(cPOMData.collectingExchangeRate);
		}
		cPOMData.invoiceNumber = invoiceNumber;
		if (transactionDate == null) {
			cPOMData.transactionDate = null;
		} else if (cPOMData.transactionDate == null) {
			cPOMData.transactionDate = (CBDate) transactionDate.clone();
		} else {
			transactionDate.copy(cPOMData.transactionDate);
		}
		cPOMData.provisionRefNo = provisionRefNo;
		cPOMData.parameter = parameter;
		cPOMData.invoiceType = invoiceType;
		cPOMData.debtCurrencyCode = debtCurrencyCode;
	}

	public Object clone() {
		DBSCompanyDebtFileDetailExtensionPOMData clonePOMData = DBSCompanyDebtFileDetailExtensionPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileDetailExtensionPOMData newInstance() {
		DBSCompanyDebtFileDetailExtensionPOMData pomData = (DBSCompanyDebtFileDetailExtensionPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileDetailExtensionPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileDetailExtensionPOMData newInstance(
			CBBag inBag) throws CBException {
		DBSCompanyDebtFileDetailExtensionPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileDetailExtensionPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyDebtFileDetailExtensionPOMData pomData = newInstance();
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
