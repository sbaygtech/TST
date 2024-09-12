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

public class DBSCompanyLimitFileReportPOMData extends POMData {
	protected DBSCompanyLimitFileReportPOMData() {
	}

	public String reportSequenceNumber;
	public boolean active;
	public String companyTitle;
	public String dealerTitle;
	public int customerNumber;
	public String companyDealerCusCode;
	public String tlAccountNo;
	public String usdAccountNo;
	public String currency;
	public CBCurrency limit;
	public CBCurrency availableLimit;
	public CBCurrency unPaidInvoice;
	public CBCurrency availableDBS;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(new short[] {
			16, 1, 15, 8, 1, 500, 500, 10, 50, 30, 30, 5, 25, 25, 25, 25 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2 },
			new short[] { 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new String[] { "oID", "status", "lastUpdated",
					"reportSequenceNumber", "active", "companyTitle",
					"dealerTitle", "customerNumber", "companyDealerCusCode",
					"tlAccountNo", "usdAccountNo", "currency", "limit",
					"availableLimit", "unPaidInvoice", "availableDBS" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	private static final long iD = 3000011939500116L;

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
		reportSequenceNumber = null;
		active = false;
		companyTitle = null;
		dealerTitle = null;
		customerNumber = 0;
		companyDealerCusCode = null;
		tlAccountNo = null;
		usdAccountNo = null;
		currency = null;
		limit = null;
		availableLimit = null;
		unPaidInvoice = null;
		availableDBS = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyLimitFileReportPOMData pomOrigin = (DBSCompanyLimitFileReportPOMData) POMDataCache
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
		DBSCompanyLimitFileReportPOMData cPOMData = (DBSCompanyLimitFileReportPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.reportSequenceNumber = reportSequenceNumber;
		cPOMData.active = active;
		cPOMData.companyTitle = companyTitle;
		cPOMData.dealerTitle = dealerTitle;
		cPOMData.customerNumber = customerNumber;
		cPOMData.companyDealerCusCode = companyDealerCusCode;
		cPOMData.tlAccountNo = tlAccountNo;
		cPOMData.usdAccountNo = usdAccountNo;
		cPOMData.currency = currency;
		if (limit == null) {
			cPOMData.limit = null;
		} else if (cPOMData.limit == null) {
			cPOMData.limit = (CBCurrency) limit.clone();
		} else {
			limit.copy(cPOMData.limit);
		}
		if (availableLimit == null) {
			cPOMData.availableLimit = null;
		} else if (cPOMData.availableLimit == null) {
			cPOMData.availableLimit = (CBCurrency) availableLimit.clone();
		} else {
			availableLimit.copy(cPOMData.availableLimit);
		}
		if (unPaidInvoice == null) {
			cPOMData.unPaidInvoice = null;
		} else if (cPOMData.unPaidInvoice == null) {
			cPOMData.unPaidInvoice = (CBCurrency) unPaidInvoice.clone();
		} else {
			unPaidInvoice.copy(cPOMData.unPaidInvoice);
		}
		if (availableDBS == null) {
			cPOMData.availableDBS = null;
		} else if (cPOMData.availableDBS == null) {
			cPOMData.availableDBS = (CBCurrency) availableDBS.clone();
		} else {
			availableDBS.copy(cPOMData.availableDBS);
		}
	}

	public Object clone() {
		DBSCompanyLimitFileReportPOMData clonePOMData = DBSCompanyLimitFileReportPOMData
				.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyLimitFileReportPOMData newInstance() {
		DBSCompanyLimitFileReportPOMData pomData = (DBSCompanyLimitFileReportPOMData) POMDataPool
				.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyLimitFileReportPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyLimitFileReportPOMData newInstance(CBBag inBag)
			throws CBException {
		DBSCompanyLimitFileReportPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyLimitFileReportPOMData newInstance(CBRow row)
			throws CBException {
		DBSCompanyLimitFileReportPOMData pomData = newInstance();
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
