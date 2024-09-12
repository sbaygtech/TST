package cb.ext.dbs.generic;

import static cb.ext.dbs.bean.DealerService.setDealerInternalCustomerCodeLongVersion;
import static cb.ext.dbs.generic.CompanyDebtFileMasterService.getUnPaidWaitingInvoiceTotals;
import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;
import static cb.ext.dbs.util.EMailUtility.getEMailForErrorMessages;
import static cb.ext.dbs.util.EMailUtility.sendMail;

import java.util.List;
import java.util.Vector;

import cb.ext.dbs.bean.DealerService;
import cb.ext.dbs.bean.DebtFileMasterService;
import cb.ext.dbs.bean.DebtFileService;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.DebtFileInvoiceCurrencyType;
import cb.ext.dbs.constants.DebtFileRecordStatu;
import cb.ext.dbs.constants.DebtFileResultReportHeadingConstants;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.constants.ProcessExchangeRateCodeType;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyAccount;
import cb.ext.dbs.data.CompanyDebtFileDetailExceptionParam;
import cb.ext.dbs.data.CompanyDebtFileResultEMail;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.data.CompanyDebtMasterFile;
import cb.ext.dbs.data.CompanyDebtMasterProcessExceptionParam;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.CurrencyRate;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.DebtFileRecordType;
import cb.ext.dbs.pom.DBSCompanyDebtFileHeaderPOM;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.CustomerUtility;
import cb.ext.dbs.util.EMailUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBatchSubmitter;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class LoadDebtFileGeneric  implements  CBBagKeys {

	public static boolean checkInputs(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail ,CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany ) throws CBException {

		boolean errorFound = false;
		
		String recordType = "";
		
		try {
			isRecordTypeValid(debtFileDetail,detailExceptionPrm,detailExceptionCompany);
			isDueDateValid (debtFileDetail, detailExceptionPrm ,detailExceptionCompany );
			isFileCurrencyEqualsRecordCurrency(debtFileHeader, debtFileDetail, detailExceptionPrm, detailExceptionCompany);
			isFileCurrencyInternalCodeEqualsRecordCurrencyInternalCode(debtFileHeader, debtFileDetail, detailExceptionPrm,  detailExceptionCompany);
			isDueDateExpire(debtFileHeader, debtFileDetail, detailExceptionPrm, detailExceptionCompany);
			checkMaturityDate(debtFileHeader, debtFileDetail, detailExceptionPrm, detailExceptionCompany);
			checkDealerExistence(debtFileHeader, debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			checkInvoiceNoLength( debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			checkInvoiceNoEmpty(debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			isActiveControlDealer(debtFileHeader,debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			isActiveControlCompany(debtFileHeader,debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			checkCompanyAndDealerEqual(debtFileHeader,debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			checkGreaterThanZero(debtFileDetail, detailExceptionPrm ,detailExceptionCompany);
			
			recordType = debtFileDetail.getRecordType();
			
			if (!(recordType.equals(DebtFileRecordType.cancel.value()) || recordType.equals(DebtFileRecordType.cancelPayment.value()))) {
				isDealerLimitEnough(debtFileHeader, debtFileDetail, detailExceptionPrm, detailExceptionCompany);
			}
			

		} catch (CBException ex) {

			if(detailExceptionPrm.getMethodCode().equals("00")){
				detailExceptionPrm.setMethodCode("07");
			}
			
			detailExceptionPrm = detailExceptionPrm.get();
			
			errorFound = true;
			
			debtFileDetail.setProcessStatu(DebtFileRecordStatu.error.value());
			debtFileDetail.setProcessExplanation("Kayýt Hata Aldý");

		}
		finally {

			if (errorFound) {
				debtFileDetail.setProcessStatu(DebtFileRecordStatu.error.value());
				debtFileDetail.setProcessExplanation("Kayýt Hata Aldý");
			}
		
		}

		return errorFound;

	}
	
	private static void checkDealerExistence(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany) throws CBException {

		String dealerInnerCode = DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID());

		if (!Dealer.isDealerExistsByCompanyOIDInnerCustCode(debtFileHeader.getCompanyOID(), dealerInnerCode) &&  detailExceptionCompany.contains("07")) {
			detailExceptionPrm.setMethodCode("07");
			throw new CBException();
		}
	}

	private static boolean checkDealerTitle(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;

		String dealerTitle     = debtFileDetail.getCustomerTitle();
		String customerTitle   = "";
	
		String dealerInnerCode = DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID());
		
		Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(debtFileHeader.getCompanyOID(), dealerInnerCode);
		
		Customer customer = CustomerUtility.getCustomerInfo(dealer.getCustomerNumber());

		if(customer.getTitle().toString().length()>30){
			customerTitle = customer.getTitle().toString().substring(0,30);
		}
		if(!customerTitle.equals(dealerTitle)){
			debtFileDetail.setProcessExplanation(debtFileHeader.getCompanyOID() + " - " + dealerTitle + "müþteri adý farklý.");
			errorFound = true;
		}

		return errorFound;
	}
	
	private static void isRecordTypeValid(CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm,List<String > detailExceptionCompany) throws CBException {
				
		String recordType = debtFileDetail.getRecordType();
		if ((!recordType.equals(DebtFileRecordType.neww.value())) && (!recordType.equals(DebtFileRecordType.update.value()))
				&& (!recordType.equals(DebtFileRecordType.cancel.value())) && (!recordType.equals(DebtFileRecordType.cancelPayment.value()))
				&& detailExceptionCompany.contains("01")) {
			
			detailExceptionPrm.setMethodCode("01");

			throw new CBException();
		}

	}

	private static void isFileCurrencyInternalCodeEqualsRecordCurrencyInternalCode(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany) throws CBException {
		
/*
		String recordCurrInterCode = "";

		if (debtFileHeader.getFileCurrency().equals(CurrencyUtility.DEFAULT_CURRENCY)) {
			recordCurrInterCode = Constants.INTERNAL_TRY_CURRENCY_CODE;
		}
		else {
			recordCurrInterCode = Constants.INTERNAL_USD_CURRENCY_CODE;
		}

		String companyCurrencyInternalCode = debtFileDetail.getCustomerNo().substring(0, 7);

		if (!companyCurrencyInternalCode.equals(recordCurrInterCode)) {
			debtFileDetail.setProcessExplanation("Faturanýn Döviz Cinsi Kodu ile Firmanýn Döviz Cinsi Uyumsuzdur.");
			errorFound = true;
		}
*/
		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		if(debtFileDetail.getCurrency() != null){
			
			CompanyAccount companyAccount = new CompanyAccount().getCompanyAccount(company.getOID(), debtFileDetail.getCurrency());
			
			String dealerInnerCode = DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID());
			
			Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(company.getOID(), dealerInnerCode);

			DealerAccount dealerAccount = new DealerAccount().getDealerAccount(dealer.getOID(), debtFileDetail.getCurrency());
			
			if ((dealerAccount == null || companyAccount == null) && detailExceptionCompany.contains("04") ) {			
				detailExceptionPrm.setMethodCode("04");
				throw new CBException();
			}
		}
	
	
	}

	private static void isFileCurrencyEqualsRecordCurrency(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail,CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany ) throws CBException{
		
		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		if(debtFileDetail.getCurrency() != null){
				
				if (!debtFileDetail.getCurrency().equals(debtFileHeader.getFileCurrency()) && detailExceptionCompany.contains("03")&& company.getDebtFileSendType().equals(DebtFileInvoiceCurrencyType.LikeCurrencyCode.value()) && !CBSessionInfo.isInternet()) {
					detailExceptionPrm.setMethodCode("03");
					throw new CBException();
				}
		}
		

	}

	private static void isDueDateExpire(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail , CompanyDebtFileDetailExceptionParam detailExceptionPrm , List<String > detailExceptionCompany) throws CBException {

		CBDate fileDate = CBDateFactory.newCBDate(debtFileHeader.getFileDate());
		
		if (debtFileDetail.getDueDate().smallerThan(fileDate) && detailExceptionCompany.contains("05") && !debtFileDetail.getRecordType().equals(DebtFileRecordType.cancel.value())) {
			detailExceptionPrm.setMethodCode("05");
			throw new CBException();
		}

	}

	private static void isDueDateValid(CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {

		if (!GeneralUtility.isDateValid(debtFileDetail.getDueDate().toDBString()) && detailExceptionCompany.contains("02")) {
			detailExceptionPrm.setMethodCode("02");

			throw new CBException();
		}

	}

	private static void checkMaturityDate(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany) throws CBException {

		CBDate processDate = CBSystem.getInstance().getProcessDate();

		Company company = new Company().get(debtFileHeader.getCompanyOID());

		CBDate acceptableDueDate = processDate.add(company.getCompanyMaturityPeriod());

		if (debtFileDetail.getDueDate().greaterThan(acceptableDueDate) &&  detailExceptionCompany.contains("06")) {
			detailExceptionPrm.setMethodCode("06");
			throw new CBException();
		}

	}

	private static void isDealerLimitEnough(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm, List<String > detailExceptionCompany) throws CBException {


		CBCurrency dealerAvalTotalAmount = getDealerAvalTotalAmount(debtFileHeader, debtFileDetail);
		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		String exchangeRateBankType = GeneralUtility.getExchangeRateBankType(company.getExchangeRate());
		
		String recordType = debtFileDetail.getRecordType();
		
		CurrencyRate currencyRate = null;
		
		if(!company.isLoadedOverLimitDebt() && detailExceptionCompany.contains("08")){
			
			if (recordType.equals(DebtFileRecordType.update.value())) {
				
				CBCurrency existingInvoiceAmount = DebtFileMasterService.getMasterRecord(debtFileHeader, 
						                                                                      debtFileDetail).getInvoiceAmount();
				
				if (!isCurrencyTRY(debtFileDetail.getCurrency())) {

					CBCurrencyInfo curInfo = CBCurrencyInfo.getCurrencyInfo(debtFileDetail.getCurrency());
					CBCurrencyInfo curInfoTRY = CBCurrencyInfo.getDefaultCurrencyInfo();
				
					currencyRate = DebtFileService.getRemainingDebtAmount(CBDateFactory.newCBDate(debtFileHeader.getFileDate()), debtFileDetail.getCurrency(),existingInvoiceAmount, exchangeRateBankType, company.getExchangeRateType());
					
					if(company.getExchangeRateType().equals(ProcessExchangeRateCodeType.Sold.value())){
						existingInvoiceAmount = currencyRate.getBoughtAmount();
					}else {
						existingInvoiceAmount = currencyRate.getSoldAmount();
					}
				}
				
				dealerAvalTotalAmount = dealerAvalTotalAmount.add(existingInvoiceAmount);
			}

			CBCurrency invoiceAmount = debtFileDetail.getInvoiceAmount();

			if (!isCurrencyTRY(debtFileDetail.getCurrency())) {

				CBCurrencyInfo curInfo = CBCurrencyInfo.getCurrencyInfo(debtFileDetail.getCurrency());
				CBCurrencyInfo curInfoTRY = CBCurrencyInfo.getDefaultCurrencyInfo();
				
				currencyRate = DebtFileService.getRemainingDebtAmount(CBDateFactory.newCBDate(debtFileHeader.getFileDate()), debtFileDetail.getCurrency(),invoiceAmount, exchangeRateBankType, company.getExchangeRateType());
				
				if(exchangeRateBankType.equals(ProcessExchangeRateCodeType.Sold)){
					invoiceAmount = currencyRate.getBoughtAmount();
				}else {
					invoiceAmount = currencyRate.getSoldAmount();
				}
			}

			if (invoiceAmount.compareTo(dealerAvalTotalAmount) == 1) {
				
				detailExceptionPrm.setMethodCode("08");
				throw new CBException();
			}
		}

		

	}
	
	private static void checkInvoiceNoLength (CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {


		if ( debtFileDetail.getInvoiceNo().length()>16 && detailExceptionCompany.contains("14")) {
			detailExceptionPrm.setMethodCode("14");

			throw new CBException();
		}

	}
	
	private static void checkInvoiceNoEmpty (CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {


		if ( (debtFileDetail.getInvoiceNo().length()==0 || debtFileDetail.getInvoiceNo().equals("0"))&& detailExceptionCompany.contains("15")) {
			detailExceptionPrm.setMethodCode("15");

			throw new CBException();
		}

	}

	private static void isActiveControlDealer ( CompanyDebtFileHeader debtFileHeader ,CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {

		String dealerInnerCode = DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID());
		
		
		Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(debtFileHeader.getCompanyOID(), dealerInnerCode);
		
		if ( !dealer.isActive() && detailExceptionCompany.contains("16")) {
			detailExceptionPrm.setMethodCode("16");

			throw new CBException();
		}

	}
	
	
	private static void isActiveControlCompany ( CompanyDebtFileHeader debtFileHeader ,CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {

		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		if ( !company.isActive() && detailExceptionCompany.contains("17")) {
			detailExceptionPrm.setMethodCode("17");

			throw new CBException();
		}

	}
	
	private static void checkCompanyAndDealerEqual ( CompanyDebtFileHeader debtFileHeader ,CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {

		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		String dealerInnerCode = DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(), debtFileHeader.getCompanyOID());
		
		Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(debtFileHeader.getCompanyOID(), dealerInnerCode);
		
		if (company.getCustomerNumber()==dealer.getCustomerNumber() && detailExceptionCompany.contains("18")) {
			detailExceptionPrm.setMethodCode("18");

			throw new CBException();
		}

	}
	

	private static void checkGreaterThanZero ( CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {


		
		if ( debtFileDetail.getInvoiceAmount().compare(CBCurrency.ZERO) < 0 && detailExceptionCompany.contains("19")) {
			detailExceptionPrm.setMethodCode("19");

			throw new CBException();
		}

	}
	
	
	
	private static CBCurrency getDealerAvalTotalAmount(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		DealerAccount dealerAccount = null;
		WaitingDebt[] waitingDebt = null;
		CBCurrency totalWaitingDebt = CBCurrency.ZERO;
		
		String dealerInternalCustCode;
		Account account = new Account();
		CBCurrency usedAmountFromKMH = CBCurrency.ZERO;
		CBCurrency dealerAvalTotalAmount = CBCurrency.ZERO;
		
		Company company = new Company().get(debtFileHeader.getCompanyOID());
		
		CompanyLimitFileParam companyLimitFileParam = CompanyLimitFileParam.getCompanyLimitFileParam(company.getOID());

		Dealer dealer = new Dealer().getDealerByCompanyDealerCustomerNumber(debtFileHeader.getCompanyOID(), 
																				DealerService.getDealerInnerCode(debtFileDetail.getCustomerNo(),company.getOID() ));
		
	    Vector<DealerAccount> dealerAccounts = dealer.getAccountsOrderedBySpecialCurrency(CurrencyUtility.DEFAULT_CURRENCY, LimitAccountOption.valueOf(String.valueOf(companyLimitFileParam.getAccountOption())), false);

		
		waitingDebt = new WaitingDebt[dealerAccounts.size()];
		totalWaitingDebt = CBCurrency.ZERO;

		for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {

			dealerAccount = dealerAccounts.get(innerIndex);

			dealerInternalCustCode = setDealerInternalCustomerCodeLongVersion(dealer.getCompanyDealerCustomerCode(), dealerAccount.getCurrency());

			waitingDebt[innerIndex] = getUnPaidWaitingInvoiceTotals(debtFileHeader.getCompanyOID(), 
					                                                debtFileDetail.getDueDate(), 
					                                                dealer.getCompanyDealerCustomerCode(), dealerAccount.getCurrency());
			
			totalWaitingDebt = totalWaitingDebt.add(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount());

			if (isCurrencyTRY(dealerAccount.getCurrency())) {

				account = AccountUtility.getCurrentAccountInfo(dealerAccount.getAccountNo());

				usedAmountFromKMH = AccountUtility.getUsedAmountFromKMH(company, dealerAccount, account);

			}

		}
		
		dealerAvalTotalAmount = DealerService.getDealerAvaliableAmount(totalWaitingDebt, account, usedAmountFromKMH);

		return dealerAvalTotalAmount;
		
		
	}
	public static Boolean checkMasterFile(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailExceptionParam detailExceptionPrm ,List<String > detailExceptionCompany) throws CBException {

		boolean errorFound = false;

		try {
			CompanyDebtMasterFile companyDebtMasterFile =  DebtFileMasterService.getMasterRecordByCompanyidAndInvoiceNo(debtFileHeader, debtFileDetail);
			String recordType = debtFileDetail.getRecordType();

			if(companyDebtMasterFile != null) {
				if (recordType.equals(DebtFileRecordType.neww.value())) {				
					if((DebtRecordStatu.init.value().equals(companyDebtMasterFile.getProcessStatu()) || DebtRecordStatu.error.value().equals(companyDebtMasterFile.getProcessStatu())) 
							&& detailExceptionCompany.contains("09")) {
						detailExceptionPrm.setMethodCode("09");
						throw new CBException();
					}
					else if ((DebtRecordStatu.done.value().equals(companyDebtMasterFile.getProcessStatu()) || DebtRecordStatu.partialPayment.value().equals(companyDebtMasterFile.getProcessStatu()))  
							&& detailExceptionCompany.contains("22")) {
						detailExceptionPrm.setMethodCode("22");
						throw new CBException();
					}
				}
				else if (recordType.equals(DebtFileRecordType.update.value())) {				
					if (!DebtRecordStatu.init.value().equals(companyDebtMasterFile.getProcessStatu()) && !DebtRecordStatu.error.value().equals(companyDebtMasterFile.getProcessStatu())
							&& detailExceptionCompany.contains("11")) {
						detailExceptionPrm.setMethodCode("11");
						throw new CBException();
					}
				}
				else if (recordType.equals(DebtFileRecordType.cancel.value())) {
					if (!DebtRecordStatu.init.value().equals(companyDebtMasterFile.getProcessStatu()) && !DebtRecordStatu.error.value().equals(companyDebtMasterFile.getProcessStatu())
							&& detailExceptionCompany.contains("13")) {
						detailExceptionPrm.setMethodCode("13");
						throw new CBException();
					}
				}
				else if (recordType.equals(DebtFileRecordType.cancelPayment.value())) {
					if (!DebtRecordStatu.done.value().equals(companyDebtMasterFile.getProcessStatu()) && !DebtRecordStatu.partialPayment.value().equals(companyDebtMasterFile.getProcessStatu()) 
							&& detailExceptionCompany.contains("21")) {
						detailExceptionPrm.setMethodCode("21");
						throw new CBException();
					}
				}
			}
			else {
				if (recordType.equals(DebtFileRecordType.update.value())) {				
					if(detailExceptionCompany.contains("10")) {
						detailExceptionPrm.setMethodCode("10");
						throw new CBException();
					}
				}
				else if (recordType.equals(DebtFileRecordType.cancel.value())) {
					if(detailExceptionCompany.contains("12")) {
						detailExceptionPrm.setMethodCode("12");
						throw new CBException();
					}
				}
				else if (recordType.equals(DebtFileRecordType.cancelPayment.value())) {
					if (detailExceptionCompany.contains("20")) {
						detailExceptionPrm.setMethodCode("20");
						throw new CBException();
					}
				}
			}
		} 
		catch (CBException ex) {
			errorFound = true;
		}

		return errorFound;

	}
	public static boolean affectMasterFile(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail, CompanyDebtMasterProcessExceptionParam masterExceptionPrm , List<String >  masterExceptionCompany) throws CBException {

		long masterOID = -1L;
		
		boolean errorFound = false;

		try {

			String recordType = debtFileDetail.getRecordType();

			if (recordType.equals(DebtFileRecordType.neww.value())) {
				masterOID = DebtFileMasterService.insertMasterFile(debtFileHeader, debtFileDetail);
			}
			else if (recordType.equals(DebtFileRecordType.update.value())) {
				masterOID = DebtFileMasterService.updateMasterRecord(debtFileHeader, debtFileDetail);
			}
			else if (recordType.equals(DebtFileRecordType.cancel.value())) {
				masterOID = DebtFileMasterService.deleteMasterRecord(debtFileHeader, debtFileDetail ,   masterExceptionPrm ,   masterExceptionCompany);
			}
			else if (recordType.equals(DebtFileRecordType.cancelPayment.value())) {
				masterOID = DebtFileMasterService.updateCancelPaymentMasterRecord(debtFileHeader, debtFileDetail);
			}
			
			debtFileDetail.setMasterOID(masterOID);

		} catch (CBException ex) {

			String errorMessage = ex.getMessage();

			if (errorMessage.length() > 400) {
				errorMessage = errorMessage.substring(0, 400);
			}
			
			errorFound = true;

			
			debtFileDetail.setProcessExplanation(errorMessage);

		}

		return errorFound;

	}
	
	public static void prepareAndSendMailNotify(CompanyDebtFileHeader debtFileHeader) throws CBException {

		CBBag attachedDocBag = CBBagFactory.createBag();

		StringBuffer message = new StringBuffer();

		String attechedDocID = "-1";
		String mailDocID = "-1";
		String resultFileName = "";

		String toMail = "";
		String ccMail = "";

		Company company = new Company().get(debtFileHeader.getCompanyOID());
		CompanyDebtFileResultParam debtFileRsltPrm = new CompanyDebtFileResultParam().getCompanyDebtFileResultParam(debtFileHeader.getCompanyOID());

		Customer customer = CustomerUtility.getCustomerInfo(company.getCustomerNumber());

		resultFileName = debtFileRsltPrm.getFileCompanyCode() + debtFileHeader.getFileName();

		attachedDocBag = generateResultDocument(debtFileHeader.getOid(), resultFileName, customer.getTitle());

		if (attachedDocBag.existsBagKey(DOCUMENTID)) {
			attechedDocID = attachedDocBag.get(DOCUMENTID).toString();
		}

		toMail = combineToMailList(debtFileHeader.getCompanyOID());

		ccMail = EMailUtility.getEMailForNotification();
		
		String messageHeader = "DBS Borc dosyasý yuklendi. K.Kodu/Adý: " + company.getCompanyInternalCode() + "/" + customer.getTitle();

		message.append(messageHeader);
		message.append("\n\r");
		message.append("\n\r");
		message.append("Tarih: " + debtFileHeader.getInsertDate().toString("/"));
		message.append("\n\r");
		message.append("Saat: " + debtFileHeader.getInsertTime().getCurrentTime(":"));
		message.append("\n\r");
		message.append("\n\r");
		message.append(debtFileHeader.getFileName() + "  borc dosyasý sisteme yuklenmistir.");

		message.append("\n\r");
		message.append("Bilginize.");

		CBBag mailBag = EMailUtility.sendMailCCAttachment(message.toString(), messageHeader, toMail, attechedDocID, ccMail);

		if (mailBag.existsBagKey(DOCUMENTID)) {
			mailDocID = mailBag.get(DOCUMENTID).toString();
		}

		debtFileHeader.setDocumentID(attechedDocID);
		debtFileHeader.setMailID(mailDocID);

	}
	
	public static void submitReconDataJob(CompanyDebtFileHeader debtFileHeader) throws CBException {

		CBBag prmBag = CBBagFactory.createBag();
		CBBag srvBag = CBBagFactory.createBag();


		srvBag.put(NAME, "EXTD7134");
		srvBag.put(ACIKLAMA, "DBS Reconcilation File");
		srvBag.put(ONCELIK, "50");
			
			
		prmBag.put(HEADER, debtFileHeader.toString());
		srvBag.put(BAG, prmBag);

		CBBatchSubmitter.getInstance().submitJob(srvBag);
	
	}

	private static String combineToMailList(long companyOID) throws CBException {

		CompanyDebtFileResultEMail companyDebtFileResultEMail = new CompanyDebtFileResultEMail();
		Vector<CompanyDebtFileResultEMail> companyDebtFileResultEMailVector = new Vector<CompanyDebtFileResultEMail>();

		String toMail = "";

		companyDebtFileResultEMailVector = companyDebtFileResultEMail.getCompanyDebtFileResultEMail(companyOID);

		for (int index = 0; index < companyDebtFileResultEMailVector.size(); index++) {
			toMail = toMail + companyDebtFileResultEMailVector.get(index).getEmail() + ",";
		}

		toMail = toMail.substring(0, toMail.length() - 1);

		return toMail;
	}
	

	private static CBBag generateResultDocument(long headerOID, String fileName, String companyTitle) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag docDataBag = CBBagFactory.createBag();
		CBBag docBag = CBBagFactory.createBag();

		CBBag summaryBag = CBBagFactory.createBag();
		CBBag summaryReturnBag = CBBagFactory.createBag();
		CBBag summaryTotalBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {
		

			CBDate insertDate = CBSystem.getInstance().getProcessDate();
			CBTime insertTime = CBSystem.getInstance().getCurrentTime();
			String uploadDateTime = insertDate.toString() + "   -   " + insertTime.toString();
			
			

			CBCurrency totalAmount = CBCurrency.ZERO;
			int totalCount = 0;

			int index = 0;
			
		
			String fileReName = fileName.substring(0,fileName.length() - 4);

			docDataBag.put(VALUE1, companyTitle);
			docDataBag.put(VALUE2, uploadDateTime);
			docDataBag.put(VALUE3, fileReName);
			docDataBag.put(VALUE4, "-1");

			summaryBag.put(INDEX, index);

			summaryReturnBag = constructSummaryTable(DebtFileRecordType.neww.value(), 
													 DebtFileRecordStatu.done.value(), headerOID, 
													 DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_NEW, summaryBag);
			
			totalAmount = totalAmount.add(summaryBag.get(AMOUNT).toCBCurrency());
			totalCount += summaryBag.get(COUNT).toSimpleInt();
			summaryReturnBag.copyTo(docDataBag);

			summaryReturnBag = constructSummaryTable(DebtFileRecordType.update.value(), 
												     DebtFileRecordStatu.done.value(), headerOID, 
												     DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_UPDATE, summaryBag);
			
			totalAmount = totalAmount.add(summaryBag.get(AMOUNT).toCBCurrency());
			totalCount += summaryBag.get(COUNT).toSimpleInt();
			summaryReturnBag.copyTo(docDataBag);

			summaryReturnBag = constructSummaryTable(DebtFileRecordType.cancel.value(), 
													 DebtFileRecordStatu.done.value(), headerOID, 
													 DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_CANCEL, summaryBag);
			
			totalAmount = totalAmount.add(summaryBag.get(AMOUNT).toCBCurrency());
			totalCount += summaryBag.get(COUNT).toSimpleInt();
			summaryReturnBag.copyTo(docDataBag);

			summaryReturnBag = constructSummaryTable(DebtFileRecordType.mistake.value(), 
													 DebtFileRecordStatu.error.value(), headerOID, 
													 DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_MISTAKE, summaryBag);
			
			totalAmount = totalAmount.add(summaryBag.get(AMOUNT).toCBCurrency());
			totalCount += summaryBag.get(COUNT).toSimpleInt();
			summaryReturnBag.copyTo(docDataBag);
			
			
			summaryReturnBag = constructSummaryTable(DebtFileRecordType.cancelPayment.value(), 
													DebtFileRecordStatu.done.value(), headerOID, 
													DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_CANCEL_PAYMENT, summaryBag);

			
			CBCurrency amount1 = summaryBag.get(AMOUNT).toCBCurrency();
			int count1 = summaryBag.get(COUNT).toSimpleInt();

			if (amount1 == null) {
				amount1 = CBCurrency.ZERO;
			}
			if (summaryBag.get(COUNT) == null) {
				count1 = 0;
			}

			totalAmount = totalAmount.add(amount1);
			totalCount += count1;
			summaryReturnBag.copyTo(docDataBag);

			summaryTotalBag.put(INDEX, summaryBag.get(INDEX));
			summaryTotalBag.put(TOTALAMOUNT, totalAmount);
			summaryTotalBag.put(TOTALCOUNT, totalCount);

			summaryReturnBag = constructSummaryTable(DebtFileRecordType.total.value(), "", headerOID, 
													 DebtFileResultReportHeadingConstants.DEBT_FILE_HEADING_TYPE_TOTAL, summaryTotalBag);
			summaryReturnBag.copyTo(docDataBag);


			getMistakeRecords(headerOID, companyTitle).copyTo(docDataBag);


			docBag.put(CALLERSERVICENAME, "");
			docBag.put(DOKUMANKODU, CompanyConstants.DBS_COMPANY_DEBT_RESULT_DOCUMENTID);
			docBag.put(DOKUMANDATA, docDataBag);
			docBag.put(DOCFORMAT, "DOC");
			docBag.put(ACIKLAMA, "Borc Akýbet Dosyasý");
			docBag.put(FILENAME, "Rslt_" + fileReName);

			CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", docBag);

			outBag.put(DOCUMENTID, retbag.get(DOCUMENTID));

			return outBag;

		} finally {

	
			if (qe != null) {
				qe.close();
			}

		}

	}
	
	private static CBBag constructSummaryTable(String recordType, String processStatu, long headerOID, String processExplanation, CBBag summaryBag) throws CBException {

		CBQueryExecuter qe = null;

		CBBag docDataBag = CBBagFactory.createBag();

		CBCurrency amount = CBCurrency.ZERO;
		int count = 0;

		boolean recordFound = false;

		try {

			int index = summaryBag.get(INDEX).toSimpleInt();

			if (recordType.equals("T")) {

				docDataBag.put(TABLE1, index, VALUE5, processExplanation);
				docDataBag.put(TABLE1, index, VALUE6, "");
				docDataBag.put(TABLE1, index, VALUE7, summaryBag.get(TOTALAMOUNT));
				docDataBag.put(TABLE1, index, VALUE8, summaryBag.get(TOTALCOUNT));

			}
			else {

				if (processStatu.equals(DebtFileRecordStatu.done.value())) {
					qe = new CBQueryExecuter("EXT_DBS_GET_DEBT_DETAIL");
				}
				else {
					qe = new CBQueryExecuter("EXT_DBS_GET_DEBT_DETAIL_ERR");
				}

				qe.setParameter("HEADEROID", headerOID);
				qe.setParameter("PROCESSTATU", processStatu);

				if (!recordType.equals("")) {
					qe.setParameter("RECORDTYPE", recordType);
				}

				qe.executeQuery();

				while (qe.next()) {

					amount = qe.getCBCurrency("TOTALAMOUNT");
					count = qe.getInt("TOTALCOUNT");

					docDataBag.put(TABLE1, index, VALUE5, processExplanation);

					if (processStatu.equals(DebtFileRecordStatu.done.value())) {
						docDataBag.put(TABLE1, index, VALUE6, qe.getString("CURRENCY"));
					}
					else {
						docDataBag.put(TABLE1, index, VALUE6, "");
					}

					docDataBag.put(TABLE1, index, VALUE7, amount);
					docDataBag.put(TABLE1, index, VALUE8, count);

					index++;

					recordFound = true;

				}

				qe.close();

				if (!recordFound) {

					docDataBag.put(TABLE1, index, VALUE5, processExplanation);
					docDataBag.put(TABLE1, index, VALUE6, "");
					docDataBag.put(TABLE1, index, VALUE7, amount);
					docDataBag.put(TABLE1, index, VALUE8, count);

					index++;
				}

				summaryBag.put(COUNT, count);
				summaryBag.put(AMOUNT, amount);
				summaryBag.put(INDEX, index);

			}

			return docDataBag;

		} catch (CBException ex) {
			throw ex;
		} finally {

			if (qe != null) {
				qe.close();
			}
		}
	}
	
	private static CBBag getMistakeRecords(long headerOID, String companyTitle) throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();
		CBQueryExecuter qeMistake = null;

		try {

			int index = 0;

			qeMistake = new CBQueryExecuter("EXT_DBS_GET_DEBT_MISTAKE_DETAIL");
			qeMistake.setParameter("HEADEROID", headerOID);

			qeMistake.executeQuery();

			while (qeMistake.next()) {

				outBag.put(TABLE, index, RESULT1, qeMistake.getString("CUSTOMERNO"));
				outBag.put(TABLE, index, RESULT2, companyTitle);
				outBag.put(TABLE, index, RESULT3, qeMistake.getCBDate("DUEDATE"));
				outBag.put(TABLE, index, RESULT4, qeMistake.getString("INVOICENO"));
				outBag.put(TABLE, index, RESULT5, qeMistake.getCBCurrency("INVOICEAMOUNT"));
				outBag.put(TABLE, index, RESULT6, qeMistake.getString("PROCESSEXPLANATION"));
				outBag.put(TABLE, index, RESULT7, qeMistake.getString("EXCEPTIONDESCRIPTION"));

				index++;

			}
			return outBag;
		} finally {
			
			if (qeMistake != null) {
				qeMistake.close();
			}
			
		}
	}
	public static void prepareAndSendFileAlreadyLoadedMail(String fileName, CBDate insertDate, String fileDate, Company company) throws CBException {

		String email = getEMailForErrorMessages();

		String companyCode = company.getCompanyInternalCode();

		String messageHeader = companyCode + " kodlu firmanýn borc dosyasi yüklenirken hata oluþtu! Dosya Tarihi:" + fileDate;

		StringBuffer message = new StringBuffer();

		message.append(fileName + " dosyasi daha once sisteme yuklenmis.");
		message.append("\n\r");
		message.append("Kurum Kodu : " + companyCode);
		message.append("\n\r");
		message.append("Dosya tarihi : " + fileDate);
		message.append("\n\r");
		message.append("Islem tarih:" + insertDate.toDBString());
		message.append("\n\r");
		message.append("Dosya adý : " + fileName);
		message.append("\n\r");
		message.append("\n\r");
		message.append("Bilginize.");

		sendMail(message.toString(), messageHeader, email);

	}
	
	public static boolean fileAlreadyLoaded(String fileName, String companyCode) throws CBException {

		DBSCompanyDebtFileHeaderPOM pom = DBSCompanyDebtFileHeaderPOM.newDBSCompanyDebtFileHeaderPOM();

		boolean retVal = false;

		try {

			if (pom.readByFileNameAndCompanyCode(fileName, companyCode)) {
				retVal = true;
			}

			return retVal;

		} finally {

			if (pom != null) {
				pom.close();
			}
		}
	}

	
}
