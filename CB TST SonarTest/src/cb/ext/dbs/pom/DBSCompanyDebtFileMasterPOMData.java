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

public class DBSCompanyDebtFileMasterPOMData extends POMData {
	protected DBSCompanyDebtFileMasterPOMData() {
	}

	public String customerNo;
	public String customerTitle;
	public CBDate dueDate;
	public CBCurrency invoiceAmount;
	public String invoiceNo;
	public String currency;
	public CBDate insertDate;
	public CBTime insertTime;
	public CBDate updateDate;
	public CBTime updateTime;
	public CBDate deleteDate;
	public CBTime deleteTime;
	public String processStatu;
	public String processExplanation;
	public long productOpRefNo;
	public CBDate processDate;
	public CBDate accountingDate;
	public long companyID;
	public CBTime processTime;
	public long dealerOID;
	public String transfered;
	public long transferProductOpRefNo;
	public CBCurrency paidAmount;
	public String channelCode;
	public long sequenceNo;
	public String invoiceRefNo;
	public String invoiceType;
	public CBDate cancelDate;
	public long cancelProductOpRefNo;
	public CBCurrency cancelAmount;
	static final POMDataFieldInfo fieldInfo = new POMDataFieldInfo(
			new short[] { 16, 1, 15, 40, 100, 8, 25, 16, 3, 8, 6, 8, 6, 8, 6, 5, 4000, 16, 8, 8, 16, 6, 16, 2, 16, 25,
					5, 16, 20, 1, 8, 16, 25 },
			new short[] { 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
					0, 2 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1,
					1, 1 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0 },
			new String[] { "oID", "status", "lastUpdated", "customerNo", "customerTitle", "dueDate", "invoiceAmount",
					"invoiceNo", "currency", "insertDate", "insertTime", "updateDate", "updateTime", "deleteDate",
					"deleteTime", "processStatu", "processExplanation", "productOpRefNo", "processDate",
					"accountingDate", "companyID", "processTime", "dealerOID", "transfered", "transferProductOpRefNo",
					"paidAmount", "channelCode", "sequenceNo", "invoiceRefNo", "invoiceType", "cancelDate",
					"cancelProductOpRefNo", "cancelAmount" },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0 },
			new short[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0 });
	private static final long iD = 3000013378800005L;

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
		customerNo = null;
		customerTitle = null;
		dueDate = null;
		invoiceAmount = null;
		invoiceNo = null;
		currency = null;
		insertDate = null;
		insertTime = null;
		updateDate = null;
		updateTime = null;
		deleteDate = null;
		deleteTime = null;
		processStatu = null;
		processExplanation = null;
		productOpRefNo = 0;
		processDate = null;
		accountingDate = null;
		companyID = 0;
		processTime = null;
		dealerOID = 0;
		transfered = null;
		transferProductOpRefNo = 0;
		paidAmount = null;
		channelCode = null;
		sequenceNo = 0;
		invoiceRefNo = null;
		invoiceType = null;
		cancelDate = null;
		cancelProductOpRefNo = 0;
		cancelAmount = null;
	}

	public static boolean isDifferent(Object data1, Object data2) {
		return cb.smg.pom.POMData.isDifferent(data1, data2);
	}

	public boolean isChanged(Integer... arBagKeys) {
		DBSCompanyDebtFileMasterPOMData pomOrigin = (DBSCompanyDebtFileMasterPOMData) POMDataCache.getInstance()
				.getPOMData(iD, oID);
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
		DBSCompanyDebtFileMasterPOMData cPOMData = (DBSCompanyDebtFileMasterPOMData) data;
		cPOMData.oID = oID;
		cPOMData.status = status;
		cPOMData.lastUpdated = lastUpdated;
		cPOMData.customerNo = customerNo;
		cPOMData.customerTitle = customerTitle;
		if (dueDate == null) {
			cPOMData.dueDate = null;
		} else if (cPOMData.dueDate == null) {
			cPOMData.dueDate = (CBDate) dueDate.clone();
		} else {
			dueDate.copy(cPOMData.dueDate);
		}
		if (invoiceAmount == null) {
			cPOMData.invoiceAmount = null;
		} else if (cPOMData.invoiceAmount == null) {
			cPOMData.invoiceAmount = (CBCurrency) invoiceAmount.clone();
		} else {
			invoiceAmount.copy(cPOMData.invoiceAmount);
		}
		cPOMData.invoiceNo = invoiceNo;
		cPOMData.currency = currency;
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
		if (updateDate == null) {
			cPOMData.updateDate = null;
		} else if (cPOMData.updateDate == null) {
			cPOMData.updateDate = (CBDate) updateDate.clone();
		} else {
			updateDate.copy(cPOMData.updateDate);
		}
		if (updateTime == null) {
			cPOMData.updateTime = null;
		} else if (cPOMData.updateTime == null) {
			cPOMData.updateTime = (CBTime) updateTime.clone();
		} else {
			updateTime.copy(cPOMData.updateTime);
		}
		if (deleteDate == null) {
			cPOMData.deleteDate = null;
		} else if (cPOMData.deleteDate == null) {
			cPOMData.deleteDate = (CBDate) deleteDate.clone();
		} else {
			deleteDate.copy(cPOMData.deleteDate);
		}
		if (deleteTime == null) {
			cPOMData.deleteTime = null;
		} else if (cPOMData.deleteTime == null) {
			cPOMData.deleteTime = (CBTime) deleteTime.clone();
		} else {
			deleteTime.copy(cPOMData.deleteTime);
		}
		cPOMData.processStatu = processStatu;
		cPOMData.processExplanation = processExplanation;
		cPOMData.productOpRefNo = productOpRefNo;
		if (processDate == null) {
			cPOMData.processDate = null;
		} else if (cPOMData.processDate == null) {
			cPOMData.processDate = (CBDate) processDate.clone();
		} else {
			processDate.copy(cPOMData.processDate);
		}
		if (accountingDate == null) {
			cPOMData.accountingDate = null;
		} else if (cPOMData.accountingDate == null) {
			cPOMData.accountingDate = (CBDate) accountingDate.clone();
		} else {
			accountingDate.copy(cPOMData.accountingDate);
		}
		cPOMData.companyID = companyID;
		if (processTime == null) {
			cPOMData.processTime = null;
		} else if (cPOMData.processTime == null) {
			cPOMData.processTime = (CBTime) processTime.clone();
		} else {
			processTime.copy(cPOMData.processTime);
		}
		cPOMData.dealerOID = dealerOID;
		cPOMData.transfered = transfered;
		cPOMData.transferProductOpRefNo = transferProductOpRefNo;
		if (paidAmount == null) {
			cPOMData.paidAmount = null;
		} else if (cPOMData.paidAmount == null) {
			cPOMData.paidAmount = (CBCurrency) paidAmount.clone();
		} else {
			paidAmount.copy(cPOMData.paidAmount);
		}
		cPOMData.channelCode = channelCode;
		cPOMData.sequenceNo = sequenceNo;
		cPOMData.invoiceRefNo = invoiceRefNo;
		cPOMData.invoiceType = invoiceType;
		if (cancelDate == null) {
			cPOMData.cancelDate = null;
		} else if (cPOMData.cancelDate == null) {
			cPOMData.cancelDate = (CBDate) cancelDate.clone();
		} else {
			cancelDate.copy(cPOMData.cancelDate);
		}
		cPOMData.cancelProductOpRefNo = cancelProductOpRefNo;
		if (cancelAmount == null) {
			cPOMData.cancelAmount = null;
		} else if (cPOMData.cancelAmount == null) {
			cPOMData.cancelAmount = (CBCurrency) cancelAmount.clone();
		} else {
			cancelAmount.copy(cPOMData.cancelAmount);
		}
	}

	public Object clone() {
		DBSCompanyDebtFileMasterPOMData clonePOMData = DBSCompanyDebtFileMasterPOMData.newInstance();
		copy(clonePOMData);
		return clonePOMData;
	}

	public static DBSCompanyDebtFileMasterPOMData newInstance() {
		DBSCompanyDebtFileMasterPOMData pomData = (DBSCompanyDebtFileMasterPOMData) POMDataPool.getFreePOMData(iD);
		if (pomData == null) {
			pomData = new DBSCompanyDebtFileMasterPOMData();
			POMDataPool.setPOMDataAsInUse(pomData);
		}
		return pomData;
	}

	public static DBSCompanyDebtFileMasterPOMData newInstance(CBBag inBag) throws CBException {
		DBSCompanyDebtFileMasterPOMData pomData = newInstance();
		pomData.setBag(inBag);
		return pomData;
	}

	public static DBSCompanyDebtFileMasterPOMData newInstance(CBRow row) throws CBException {
		DBSCompanyDebtFileMasterPOMData pomData = newInstance();
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
