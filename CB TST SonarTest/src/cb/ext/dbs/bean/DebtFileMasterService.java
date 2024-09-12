package cb.ext.dbs.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.TransferType;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtMasterFile;
import cb.ext.dbs.data.CompanyDebtMasterProcessExceptionParam;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccountLimitDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.CompanyDebtFileMasterException;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOMData;
import cb.ext.dbs.util.CustomerUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBNumberGenerator;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class DebtFileMasterService implements CBBagKeys{
	
	public static void updateDealerDebtRecordsAtCompanyCurrentAccount(long productOpRefNo, CBDate processDate, long companyOID) throws CBException {

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		try {
			
			pom.readByCompanyOIDandTransferedandAccountingDate(companyOID, TransferType.atBlockageAccount.value(), processDate);

			while (pom.next()) {

				data = pom.getDBSCompanyDebtFileMasterPOMData();
				data.transfered = TransferType.atCurrentAccount.value();
				data.transferProductOpRefNo = productOpRefNo;

				pom.setDBSCompanyDebtFileMasterPOMData(data);
				pom.update();
			}

		} finally {

			if (pom != null) {
				pom.close();
			}
		}
	}
	
	public static long deleteMasterRecord(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail,  CompanyDebtMasterProcessExceptionParam masterExceptionPrm , List<String >  masterExceptionCompany) throws CBException {


		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();;

		try {
			
			CompanyDebtFileMasterException  	masterException        = new CompanyDebtFileMasterException();
			
			long masterOID = -1L;
			
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();

			if (pom.readByCompanyOIDandInvoiceNoActive(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {
				
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				data.deleteDate = trxDate;
				data.deleteTime = trxTime;
				data.processStatu = DebtRecordStatu.deleteByFile.value();
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);

				

				pom.update();
				
				masterOID = data.oID;
				
				
				if(masterExceptionCompany.contains("01")){
					
					masterExceptionPrm.setMethodCode("01");
					
					masterExceptionPrm.get();
					
					masterException.setExceptionCode(masterExceptionPrm.getExceptionCode());
					
					if(CBSessionInfo.isADK()){
						masterException.setExceptionDescription(masterExceptionPrm.getAdkExceptionDescription());
					}else {
						masterException.setExceptionDescription(masterExceptionPrm.getExceptionDescription());
					}
					
					masterException.add();
				}
			}
			
			return masterOID;

			

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static long updateMasterRecord(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {


		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();;

		try {
			
			long masterOID = -1L;
			
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();

			if (pom.readByCompanyOIDandInvoiceNoActive(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {
				
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				data.invoiceAmount = debtFileDetail.getInvoiceAmount();
				data.dueDate = debtFileDetail.getDueDate();
				data.updateDate = trxDate;
				data.updateTime = trxTime;
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);

				pom.update();
				
				masterOID = data.oID;
				
		
			}
			
			return masterOID;

			

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static long updateCancelPaymentMasterRecord(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {


		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();;

		try {
			
			long masterOID = -1L;
			
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();

			if (pom.readByCompanyOIDAndInvoiceNoPayment(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {
				
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				data.cancelAmount = debtFileDetail.getInvoiceAmount();
				data.dueDate = debtFileDetail.getDueDate();
				data.updateDate = trxDate;
				data.updateTime = trxTime;
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);

				pom.update();
				
				masterOID = data.oID;
				
		
			}
			
			return masterOID;

			

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static long insertMasterFile(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {
		
		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData	.newInstance();
		try {
			
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();
			
			CBDate defaultDate = CBDateFactory.newCBDate("19000101");
			CBTime defaultTime = new CBTime(0, 0, 0);
			
			Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(debtFileHeader.getCompanyOID(), 
					                                                            DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID()));
			
			data.customerNo = debtFileDetail.getCustomerNo();
			data.customerTitle = debtFileDetail.getCustomerTitle();
			data.dueDate = debtFileDetail.getDueDate();
			data.invoiceAmount = debtFileDetail.getInvoiceAmount();
			data.invoiceNo = debtFileDetail.getInvoiceNo();
			data.currency = debtFileDetail.getCurrency();
			data.insertDate = trxDate;
			data.insertTime = trxTime;
			data.updateDate = defaultDate;
			data.updateTime = defaultTime;
			data.deleteDate = defaultDate;
			data.deleteTime = defaultTime;
			data.processStatu = DebtRecordStatu.init.value();
			data.processExplanation = ".";
			data.productOpRefNo = -1L;
			data.processDate = defaultDate;
			data.processTime = defaultTime;
			data.accountingDate = defaultDate;
			data.companyID = debtFileHeader.getCompanyOID();
			data.dealerOID = dealer.getOID();
			data.transfered = TransferType.init.value();
			data.paidAmount = CBCurrency.ZERO;
			data.channelCode = CBSessionInfo.getKanalKodu();
			data.invoiceRefNo = debtFileDetail.getInvoiceRefNo();
			data.invoiceType = debtFileDetail.getInvoiceType();
			
			pom.setDBSCompanyDebtFileMasterPOMData(data);

			pom.create();

			return data.oID;
		}
		finally {
			
			if (pom != null) {
				pom.close();
			}
			
		}
	}
	
	public static CompanyDebtMasterFile getMasterRecord(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		CompanyDebtMasterFile companyDebtMasterFile = new CompanyDebtMasterFile();

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		try {

			if (pom.readByCompanyOIDandInvoiceNoActive(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {

				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				
				companyDebtMasterFile.setProcessStatu(data.processStatu);
				companyDebtMasterFile.setInvoiceAmount(data.invoiceAmount);
				

		
			}

			return companyDebtMasterFile;

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static CompanyDebtMasterFile getMasterRecordByCompanyidAndInvoiceNo(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		CompanyDebtMasterFile companyDebtMasterFile = null;

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		try {
			if(pom.readByCompanyOIDandInvoiceNo(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {
				companyDebtMasterFile = new CompanyDebtMasterFile();
				
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				companyDebtMasterFile.setProcessStatu(data.processStatu);
				companyDebtMasterFile.setInvoiceAmount(data.invoiceAmount);
			}

			return companyDebtMasterFile;

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static CBBag getDealerWaitingDebtList(Company company, Dealer dealer, CBDate dueDate) throws CBException{
		CBBag outBag = CBBagFactory.createBag();
		CBQueryExecuter qe = null;
		int index = 0;
		Map<Long, DealerAccountLimitDetail> dealerAccountLimitDetailMap = new HashMap<Long, DealerAccountLimitDetail>();
		
		try {			
			qe = new CBQueryExecuter("EXT_DBS_GET_WAITING_DEBT_LIST");
			qe.setParameter("COMPANYOID", company.getOID());
			
			if(dealer != null) {
				qe.setParameter("DEALEROID", dealer.getOID());
			}
			if(dueDate != null) {
				qe.setParameter("DUEDATE", dueDate);
			}
			qe.executeQuery();
			
			while (qe.next()) {
				long dealerOid = qe.getLong("OID");
			
				DealerAccountLimitDetail dealerAccountLimitDetail = null;
				if(dealerAccountLimitDetailMap.containsKey(dealerOid)) {
					dealerAccountLimitDetail = dealerAccountLimitDetailMap.get(dealerOid);
				}
				else {
					if(dealer == null) {
						dealer = new Dealer().getDealer(dealerOid);
					}
					dealerAccountLimitDetail = DealerService.getDealerAccountLimitDetail(company, dealer);
					Customer customer = CustomerUtility.getCustomerInfo(qe.getString("CUSTOMERNUMBER"));
					dealerAccountLimitDetail.setDealerTitle(customer.getTitle());
					dealerAccountLimitDetailMap.put(dealerOid, dealerAccountLimitDetail);
				}
				outBag.put(TABLE, index, COMPANYCODE, company.getCompanyInternalCode());
				outBag.put(TABLE, index, COMPANYNAME, qe.getString("COMPANYNAME"));
				outBag.put(TABLE, index, DEALER_CODE, qe.getString("COMPANYDEALERCUSCODE"));
				outBag.put(TABLE, index, CUSTOMERNAME, dealerAccountLimitDetail.getDealerTitle());
				outBag.put(TABLE, index, LIMITAMOUNT, dealerAccountLimitDetail.getLimitAmount());
				outBag.put(TABLE, index, REMAININGLIMIT, dealerAccountLimitDetail.getRemainingLimit());
				outBag.put(TABLE, index, INVOICENO, qe.getString("INVOICENO"));
				outBag.put(TABLE, index, INVOICEAMOUNT, qe.getString("INVOICEAMOUNT"));
				outBag.put(TABLE, index, DUEDATE, qe.getCBDate("DUEDATE").toString("/"));	
				index++;
			}
		} finally {
			if (qe != null) {
				qe.close();
			}
		}
		return outBag;
	}
	
	public static int deleteDebtByDealerOid(long dealerOid) throws CBException {
		CBQueryExecuter qe = null;
		int count = 0;
		try {
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();
			
			qe = new CBQueryExecuter("EXT_DBS_DELETE_NEW_AND_ERROR_DEBT");
			qe.setParameter("LASTUPDATED", CBNumberGenerator.newLastUpdatedNumber());
			qe.setParameter("DELETDATE", trxDate);
			qe.setParameter("DELETTIME", trxTime);
			qe.setParameter("UPDATEDATE", trxDate);
			qe.setParameter("UPDATETIME", trxTime);
			qe.setParameter("DEALEROID", dealerOid);
			
			count = qe.executeUpdate();
		} 
		finally {
			if (qe != null) {
				qe.close();
			}
		}
		return count;
	}
	
	public static void cancelPayment(long oid, CBCurrency cancelAmount, long cancelProductOpRefNo, long sequenceNumber) throws CBException {
		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();;

		try {
			CBDate trxDate = CBSystem.getInstance().getProcessDate();
			CBTime trxTime = CBSystem.getInstance().getCurrentTime();

			if (pom.readByPrimaryKey(oid)) {
				data = pom.getDBSCompanyDebtFileMasterPOMData();
				
				data.cancelDate = trxDate;
				data.cancelAmount = cancelAmount;
				data.cancelProductOpRefNo = cancelProductOpRefNo;
				data.processStatu = DebtRecordStatu.cancelPayment.value();
				data.updateDate = trxDate;
				data.updateTime = trxTime;
				data.sequenceNo = sequenceNumber;
				
				pom.setDBSCompanyDebtFileMasterPOMData(data);

				pom.update();
			}
		} 
		finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
}
