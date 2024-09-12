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

public class DBSCompanyPOMData extends POMData {
	protected DBSCompanyPOMData() {
	}

	public boolean active;
	public String companyInternalCode;
	public int customerNumber;
	public int workingMethod;
	public String insertUser;
	public CBDate insertDate;
	public CBTime insertTime;
	public String updUser;
	public CBDate updDate;
	public CBTime updTime;
	public String KMHMethodName;
	public CBCurrency companyTotalLimit;
	public int companyMaturityPeriod;
	public int companyProcessDayCount;
	public int companyLimitGapType;
	public boolean partialPayment;
	public String currencyProcessMethod;
	public String exchangeRate;
	public String exchangeRateType;
	public String dealerCodeFormatType;
	public String localCurrencyCodeType;
	public String debtFileSendType;
	public boolean loadedOverLimitDebt;
	public boolean manuelPaymentBeforeDueDate;
	public String companyName;
	public boolean addDealerWithSameCustomerNumber;
	public boolean guarantorship;
	public CBCurrency guarantorshipWaitingTime;
	public CBCurrency kmhLimitRate;
	public int dbsType;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 1, 10, 10, 1, 10, 8, 6, 10, 8, 6, 50, 25, 4, 5, 1, 1, 2,
			2, 2, 2, 2, 2, 1, 1, 50, 1, 1, 2, 5, 1 }, new short[] { 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 2, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, new short[] {
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, new String[] {
					"oID", "status", "lastUpdated", "active",
					"companyInternalCode", "customerNumber", "workingMethod",
					"insertUser", "insertDate", "insertTime", "updUser",
					"updDate", "updTime", "KMHMethodName", "companyTotalLimit",
					"companyMaturityPeriod", "companyProcessDayCount",
					"companyLimitGapType", "partialPayment",
					"currencyProcessMethod", "exchangeRate",
					"exchangeRateType", "dealerCodeFormatType",
					"localCurrencyCodeType", "debtFileSendType",
					"loadedOverLimitDebt", "manuelPaymentBeforeDueDate",
					"companyName", "addDealerWithSameCustomerNumber",
					"guarantorship", "guarantorshipWaitingTime",
					"kmhLimitRate", "dbsType" }, new short[] { 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0 }, new short[] { 0, 0, 0, 1, 1, 1,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000013147800113L;

	public CBBag toBag() throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		return toBag(outBag);
	}

	public CBBag toBag(CBBag outBag) throws CBException {
		outBag.put(CBBagKeys.OID, oID);
		outBag.put(CBBagKeys.STATUS, status);
		outBag.put(CBBagKeys.LASTUPDATED, lastUpdated);
		outBag.put(CBBagKeys.ACTIVE, active);
		outBag.put(CBBagKeys.CODE1, companyInternalCode);
		outBag.put(CBBagKeys.CUSTOMERNUMBER, customerNumber);
		outBag.put(CBBagKeys.METHOD, workingMethod);
		outBag.put(CBBagKeys.KMHMETOD_NAME, KMHMethodName);
		outBag.put(CBBagKeys.LIMITAMOUNT, companyTotalLimit);
		outBag.put(CBBagKeys.PERIOD, companyMaturityPeriod);
		outBag.put(CBBagKeys.VALUE0, companyProcessDayCount);
		outBag.put(CBBagKeys.TYPE1, companyLimitGapType);
		outBag.put(CBBagKeys.PARTIALCOLLECTION, partialPayment);
		return outBag;
	}

	public CBRow toBag(CBRow row) throws CBException {
		row.addColumn(CBBagKeys.OID, oID);
		row.addColumn(CBBagKeys.STATUS, status);
		row.addColumn(CBBagKeys.LASTUPDATED, lastUpdated);
		row.addColumn(CBBagKeys.ACTIVE, active);
		row.addColumn(CBBagKeys.CODE1, companyInternalCode);
		row.addColumn(CBBagKeys.CUSTOMERNUMBER, customerNumber);
		row.addColumn(CBBagKeys.METHOD, workingMethod);
		row.addColumn(CBBagKeys.KMHMETOD_NAME, KMHMethodName);
		row.addColumn(CBBagKeys.LIMITAMOUNT, companyTotalLimit);
		row.addColumn(CBBagKeys.PERIOD, companyMaturityPeriod);
		row.addColumn(CBBagKeys.VALUE0, companyProcessDayCount);
		row.addColumn(CBBagKeys.TYPE1, companyLimitGapType);
		row.addColumn(CBBagKeys.PARTIALCOLLECTION, partialPayment);
		return row;
	}

	public void setBag(CBBag inBag) throws CBException {
		if (inBag.existsBagKey(CBBagKeys.ACTIVE))
			active = inBag.get(CBBagKeys.ACTIVE).toBoolean();
		if (inBag.existsBagKey(CBBagKeys.CODE1))
			companyInternalCode = inBag.get(CBBagKeys.CODE1).toString();
		if (inBag.existsBagKey(CBBagKeys.CUSTOMERNUMBER))
			customerNumber = inBag.get(CBBagKeys.CUSTOMERNUMBER).toSimpleInt();
		if (inBag.existsBagKey(CBBagKeys.METHOD))
			workingMethod = inBag.get(CBBagKeys.METHOD).toSimpleInt();
		if (inBag.existsBagKey(CBBagKeys.KMHMETOD_NAME))
			KMHMethodName = inBag.get(CBBagKeys.KMHMETOD_NAME).toString();
		if (inBag.existsBagKey(CBBagKeys.LIMITAMOUNT))
			companyTotalLimit = inBag.get(CBBagKeys.LIMITAMOUNT).toCBCurrency();
		if (inBag.existsBagKey(CBBagKeys.PERIOD))
			companyMaturityPeriod = inBag.get(CBBagKeys.PERIOD).toSimpleInt();
		if (inBag.existsBagKey(CBBagKeys.VALUE0))
			companyProcessDayCount = inBag.get(CBBagKeys.VALUE0).toSimpleInt();
		if (inBag.existsBagKey(CBBagKeys.TYPE1))
			companyLimitGapType = inBag.get(CBBagKeys.TYPE1).toSimpleInt();
		if (inBag.existsBagKey(CBBagKeys.PARTIALCOLLECTION))
			partialPayment = inBag.get(CBBagKeys.PARTIALCOLLECTION).toBoolean();
	}

	public void setBag(CBRow row) throws CBException {
		if (row.existsBagKey(CBBagKeys.ACTIVE))
			active = row.get(CBBagKeys.ACTIVE).toBoolean();
		if (row.existsBagKey(CBBagKeys.CODE1))
			companyInternalCode = row.get(CBBagKeys.CODE1).toString();
		if (row.existsBagKey(CBBagKeys.CUSTOMERNUMBER))
			customerNumber = row.get(CBBagKeys.CUSTOMERNUMBER).toSimpleInt();
		if (row.existsBagKey(CBBagKeys.METHOD))
			workingMethod = row.get(CBBagKeys.METHOD).toSimpleInt();
		if (row.existsBagKey(CBBagKeys.KMHMETOD_NAME))
			KMHMethodName = row.get(CBBagKeys.KMHMETOD_NAME).toString();
		if (row.existsBagKey(CBBagKeys.LIMITAMOUNT))
			companyTotalLimit = row.get(CBBagKeys.LIMITAMOUNT).toCBCurrency();
		if (row.existsBagKey(CBBagKeys.PERIOD))
			companyMaturityPeriod = row.get(CBBagKeys.PERIOD).toSimpleInt();
		if (row.existsBagKey(CBBagKeys.VALUE0))
			companyProcessDayCount = row.get(CBBagKeys.VALUE0).toSimpleInt();
		if (row.existsBagKey(CBBagKeys.TYPE1))
			companyLimitGapType = row.get(CBBagKeys.TYPE1).toSimpleInt();
		if (row.existsBagKey(CBBagKeys.PARTIALCOLLECTION))
			partialPayment = row.get(CBBagKeys.PARTIALCOLLECTION).toBoolean();
	}

	protected void clear() {
		super.clear();
		active = false;
		companyInternalCode = null;
		customerNumber = 0;
		workingMethod = 0;
		insertUser = null;
		insertDate = null;
		insertTime = null;
		updUser = null;
		updDate = null;
		updTime = null;
		KMHMethodName = null;
		companyTotalLimit = null;
		companyMaturityPeriod = 0;
		companyProcessDayCount = 0;
		companyLimitGapType = 0;
		partialPayment = false;
		currencyProcessMethod = null;
		exchangeRate = null;
		exchangeRateType = null;
		dealerCodeFormatType = null;
		localCurrencyCodeType = null;
		debtFileSendType = null;
		loadedOverLimitDebt = false;
		manuelPaymentBeforeDueDate = false;
		companyName = null;
		addDealerWithSameCustomerNumber = false;
		guarantorship = false;
		guarantorshipWaitingTime = null;
		kmhLimitRate = null;
		dbsType = 0;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyPOMData pomOrigin = (DBSCompanyPOMData) POMDataCache
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
				if (isDifferent(pomOrigin.companyInternalCode,
						companyInternalCode))
					return true;
				break;
			case CBBagKeys.CUSTOMERNUMBER:
				if (pomOrigin.customerNumber != customerNumber)
					return true;
				break;
			case CBBagKeys.METHOD:
				if (pomOrigin.workingMethod != workingMethod)
					return true;
				break;
			case CBBagKeys.KMHMETOD_NAME:
				if (isDifferent(pomOrigin.KMHMethodName, KMHMethodName))
					return true;
				break;
			case CBBagKeys.LIMITAMOUNT:
				if (isDifferent(pomOrigin.companyTotalLimit, companyTotalLimit))
					return true;
				break;
			case CBBagKeys.PERIOD:
				if (pomOrigin.companyMaturityPeriod != companyMaturityPeriod)
					return true;
				break;
			case CBBagKeys.VALUE0:
				if (pomOrigin.companyProcessDayCount != companyProcessDayCount)
					return true;
				break;
			case CBBagKeys.TYPE1:
				if (pomOrigin.companyLimitGapType != companyLimitGapType)
					return true;
				break;
			case CBBagKeys.PARTIALCOLLECTION:
				if (pomOrigin.partialPayment != partialPayment)
					return true;
				break;
			}
		}
		return false;
	}

	public boolean isChangedForHistory() {
		return isChanged(CBBagKeys.ACTIVE, CBBagKeys.CODE1,
				CBBagKeys.CUSTOMERNUMBER, CBBagKeys.METHOD);
	}

	public void copy(POMData data) {
		DBSCompanyPOMData cPOMData = (DBSCompanyPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.active = active;
		cPOMData.companyInternalCode = companyInternalCode;
		cPOMData.customerNumber = customerNumber;
		cPOMData.workingMethod = workingMethod;
		cPOMData.insertUser = insertUser;
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
		cPOMData.updUser = updUser;
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
		cPOMData.KMHMethodName = KMHMethodName;
		if (companyTotalLimit == null) {
			cPOMData.companyTotalLimit = null;
		} else if (cPOMData.companyTotalLimit == null) {
			cPOMData.companyTotalLimit = (CBCurrency) companyTotalLimit.clone();
		} else {
			companyTotalLimit.copy(cPOMData.companyTotalLimit);
		}
		cPOMData.companyMaturityPeriod = companyMaturityPeriod;
		cPOMData.companyProcessDayCount = companyProcessDayCount;
		cPOMData.companyLimitGapType = companyLimitGapType;
		cPOMData.partialPayment = partialPayment;
		cPOMData.currencyProcessMethod = currencyProcessMethod;
		cPOMData.exchangeRate = exchangeRate;
		cPOMData.exchangeRateType = exchangeRateType;
		cPOMData.dealerCodeFormatType = dealerCodeFormatType;
		cPOMData.localCurrencyCodeType = localCurrencyCodeType;
		cPOMData.debtFileSendType = debtFileSendType;
		cPOMData.loadedOverLimitDebt = loadedOverLimitDebt;
		cPOMData.manuelPaymentBeforeDueDate = manuelPaymentBeforeDueDate;
		cPOMData.companyName = companyName;
		cPOMData.addDealerWithSameCustomerNumber = addDealerWithSameCustomerNumber;
		cPOMData.guarantorship = guarantorship;
		if (guarantorshipWaitingTime == null) {
			cPOMData.guarantorshipWaitingTime = null;
		} else if (cPOMData.guarantorshipWaitingTime == null) {
			cPOMData.guarantorshipWaitingTime = (CBCurrency) guarantorshipWaitingTime
					.clone();
		} else {
			guarantorshipWaitingTime.copy(cPOMData.guarantorshipWaitingTime);
		}
		if (kmhLimitRate == null) {
			cPOMData.kmhLimitRate = null;
		} else if (cPOMData.kmhLimitRate == null) {
			cPOMData.kmhLimitRate = (CBCurrency) kmhLimitRate.clone();
		} else {
			kmhLimitRate.copy(cPOMData.kmhLimitRate);
		}
		cPOMData.dbsType = dbsType;
	}

	public Object clone() {
		DBSCompanyPOMData clonePOMData = DBSCompanyPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyPOMData newInstance() {
		DBSCompanyPOMData pomData = (DBSCompanyPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyPOMData newInstance(CBBag inBag) throws CBException {
		DBSCompanyPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyPOMData newInstance(CBRow row) throws CBException {
		DBSCompanyPOMData pomData = newInstance();
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
