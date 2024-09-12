package cb.ext.dbs.tosyali;

import static cb.ext.dbs.bean.DealerService.setDealerInternalCustomerCodeLongVersion;
import static cb.ext.dbs.generic.CompanyDebtFileMasterService.getUnPaidWaitingInvoiceTotals;
import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;
import static cb.ext.dbs.util.EMailUtility.getEMailForErrorMessages;
import static cb.ext.dbs.util.EMailUtility.sendMail;

import java.util.Vector;

import cb.ext.dbs.bean.DealerService;
import cb.ext.dbs.bean.DebtFileMasterService;
import cb.ext.dbs.generic.CompanyDebtFileMasterService;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DebtFileRecordStatu;
import cb.ext.dbs.constants.DebtFileResultReportHeadingConstants;
import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileResultEMail;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.dbsinterfaces.ILoadDebtFile;
import cb.ext.dbs.pom.DBSCompanyDebtFileHeaderPOM;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.CompanyDebtFileTrailer;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.generic.data.DebtFileRecordType;
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
import cb.smg.general.utility.CBCurrencyFactory;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDataSource;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class LoadDebtFile implements ILoadDebtFile, CBBagKeys {

	public CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		long sequenceNumber = inBag.get(FT_SEQUENCE_NUMBER).toSimpleLong();
		String tableName = inBag.get(TABLE).toString();

		CBDataSource.truncateTable(tableName);

		CompanyDebtFileHeader debtFileHeader = processHeaderTrailer(inBag, inBag.get(FT_FILE_NAME).toString(), sequenceNumber);

		if (debtFileHeader != null) {
			insertIntoBCH(tableName, sequenceNumber);
			outBag.put(HEADER, debtFileHeader.toString());
		}
		else {
			outBag.put(HEADER, "");
		}

		outBag.put(RC, true);

		return outBag;

	}

	private CompanyDebtFileHeader processHeaderTrailer(CBBag inBag, String ftmFileName, long sequenceNumber) throws CBException {

		CBQueryExecuter qe = null;
		CompanyDebtFileHeader debtFileHeader = null;

		boolean headerFound = false;
		boolean abortParsing = false;

		try {

			String fileRow;

			qe = new CBQueryExecuter("EXT_GET_TFT_DBS_DEBT_FILE_HEADER");

			qe.setParameter("TRANSFERSEQUENCE", sequenceNumber);
			qe.executeQuery();

			while ((qe.next()) && (!abortParsing)) {

				fileRow = qe.getString("TB_CONTENT");

				if (fileRow.substring(0, 1).equals("H")) {

					debtFileHeader = processHeader(fileRow, ftmFileName, String.valueOf(sequenceNumber));

					if (debtFileHeader == null) {
						abortParsing = true;
					}

					headerFound = true;

				}
				else if (headerFound) {
					processTrailer(debtFileHeader.getOid(), fileRow);
				}
			}

			if (!headerFound) {
				throwExceptionIfHeaderNotFound(inBag);
			}

			return debtFileHeader;

		} finally {

			if (qe != null) {
				qe.close();
			}

		}
	}

	private void insertIntoBCH(String tableName, long sequenceNumber) throws CBException {

		CBQueryExecuter qe = null;

		try {

			CBDataSource.dropIndex(tableName);

			qe = new CBQueryExecuter("EXT_DBS_INSERT_BCH_DEBT_FILE");
			qe.setParameter("TABLENAME", tableName.substring(4));
			qe.setParameter("TRANSFERSEQUENCE", sequenceNumber);

			qe.executeQuery();

			CBDataSource.createIndex(tableName);

		} finally {

			if (qe != null) {
				qe.close();
			}

		}

	}

	private void throwExceptionIfHeaderNotFound(CBBag inBag) throws CBException {

		String message = inBag.get(FT_FILE_NAME).toString() + "dosyasinda Header) bulunamadý !! seq.no : " + inBag.get(FT_SEQUENCE_NUMBER).toString();

		CBBag exBag = CBBagFactory.createBag();

		exBag.put(NOTE, message);

		throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);

	}

	private CompanyDebtFileHeader processHeader(String fileRow, String fileName, String fileSequenceNumber) throws CBException {

		CBDate insertDate = CBSystem.getInstance().getProcessDate();
		CBTime insertTime = CBSystem.getInstance().getCurrentTime();

		String fileDate = fileRow.substring(5, 13);
		String companyCode = fileRow.substring(1, 5);
		String fileCurrency = fileRow.substring(13, 16);

		Company company = new Company().getByInternalCode(companyCode);

		CompanyDebtFileHeader debtFileHeader = null;

		if (!fileAlreadyLoaded(fileName, companyCode)) {

			checkCompany(fileName, companyCode, company);

			debtFileHeader = setCompanyDebtFileHeader(fileSequenceNumber, insertDate, insertTime, 
													  fileName, fileDate, companyCode, fileCurrency, company.getOID());

			long oid = debtFileHeader.add();

			debtFileHeader.setOid(oid);

		}
		else {

			prepareAndSendFileAlreadyLoadedMail(fileName, insertDate, fileDate, company);

		}

		return debtFileHeader;

	}

	private void processTrailer(long headerOID, String fileRow) throws CBException {

		int invoiceTotalCountNew = Integer.parseInt(fileRow.substring(2, 8));
		CBCurrency invoiceTotalAmountNew = CBCurrency.valueOf(fileRow.substring(8, 23));
		int invoiceTotalCountCancel = Integer.parseInt(fileRow.substring(23, 30));
		CBCurrency invoiceTotalAmountCancel = CBCurrency.valueOf(fileRow.substring(30, 45));
		int invoiceTotalCountUpdate = Integer.parseInt(fileRow.substring(45, 52));
		CBCurrency invoiceTotalAmountUpdate = CBCurrency.valueOf(fileRow.substring(52, 67));

		CompanyDebtFileTrailer debtFileTrailer = new CompanyDebtFileTrailer(headerOID, 
														invoiceTotalCountNew, invoiceTotalAmountNew, 
														invoiceTotalCountCancel, invoiceTotalAmountCancel,
														invoiceTotalCountUpdate, invoiceTotalAmountUpdate);

		debtFileTrailer.add();

	}

	private void prepareAndSendFileAlreadyLoadedMail(String fileName, CBDate insertDate, String fileDate, Company company) throws CBException {

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

	private void checkCompany(String fileName, String companyCode, Company company) throws CBException {

		boolean errorFound = false;
		String errorSpecificMessage = "";

		if (company == null) {
			errorFound = true;
			errorSpecificMessage = "  bulunamadý!  ";
		}
		else if (!company.isActive()) {
			errorFound = true;
			errorSpecificMessage = "  aktif degil.!   ";
		}

		if (errorFound) {

			CBBag exBag = CBBagFactory.createBag();
			String message = "Kurum Kodu : " + company.getCompanyInternalCode() + errorSpecificMessage + "Dosya Adý: " + fileName;
			exBag.put(NOTE, message);

			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);

		}

	}

	private CompanyDebtFileHeader setCompanyDebtFileHeader(String fileSequenceNumber, 
														   CBDate insertDate, CBTime insertTime, 
														   String fileName, String fileDate, 
														   String companyCode, String fileCurrency,	long companyOID) {

		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader();

		debtFileHeader.setInsertDate(insertDate);
		debtFileHeader.setInsertTime(insertTime);
		debtFileHeader.setFileName(fileName);
		debtFileHeader.setFileSequenceNumber(fileSequenceNumber);
		debtFileHeader.setFileDate(fileDate);
		debtFileHeader.setCompanyCode(companyCode);
		debtFileHeader.setCompanyOID(companyOID);
		debtFileHeader.setFileCurrency(fileCurrency);
		debtFileHeader.setDocumentID("-1");
		debtFileHeader.setMailID("-1");

		return debtFileHeader;

	}

	private boolean fileAlreadyLoaded(String fileName, String companyCode) throws CBException {

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

	public CBBag loadDebtFile(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag dataBag = CBBagFactory.createBag();

		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());

		for (int i = 0; i < inBag.getSize(BAG); i++) {

			dataBag = inBag.get(BAG, i, -1).toBag();

			processDetail(dataBag.get(VALUE1).toString(), debtFileHeader);

		}

		outBag.put(RC, true);

		return outBag;
	}

	public static void processDetail(String fileRow, CompanyDebtFileHeader debtFileHeader) throws CBException {

		CompanyDebtFileDetail debtFileDetail = parseDetailRow(fileRow);

		boolean errorFound = checkInputs(debtFileHeader, debtFileDetail);

		if (!errorFound) {

			errorFound = checkMasterFile(debtFileHeader, debtFileDetail);

			if (!errorFound) {

				errorFound = affectMasterFile(debtFileHeader, debtFileDetail);
				
				if (!errorFound) {
					debtFileDetail.setProcessStatu(DebtFileRecordStatu.done.value());
					debtFileDetail.setProcessExplanation("Kayýt Ýþlendi");
				}

			}

		}
		
		if (errorFound) {
			debtFileDetail.setProcessStatu(DebtFileRecordStatu.error.value());
		}
		
		//debtFileDetail.add(debtFileHeader);

	}

	private static boolean affectMasterFile(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

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
				//masterOID = DebtFileMasterService.deleteMasterRecord(debtFileHeader, debtFileDetail);
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

	private static boolean checkMasterFile(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;

		try {

			String recordType = debtFileDetail.getRecordType();

			if (recordType.equals(DebtFileRecordType.neww.value())) {

				if (CompanyDebtFileMasterService.isMasterRecordProccessable(debtFileHeader, debtFileDetail, recordType)) {
					debtFileDetail.setProcessExplanation("Insert: Bu kayýt zaten var! " + 
														 "CompanyID: " + debtFileHeader.getCompanyOID() + 
														 " Invoice No: " + debtFileDetail.getInvoiceNo());
					errorFound = true;
				}

			}
			else if (recordType.equals(DebtFileRecordType.update.value())) {

				String processStatu = DebtFileMasterService.getMasterRecord(debtFileHeader, debtFileDetail).getProcessStatu();

				if (processStatu.equals("")) {
					debtFileDetail.setProcessExplanation("Update: Kayýt Bulunamadý.");
					errorFound = true;
				}
				else if (!CompanyDebtFileMasterService.isMasterRecordUpdatable(processStatu)) {
					debtFileDetail.setProcessExplanation("Update: Kaydýn statusu uygun deðil. " + 
														 "CompanyID: " + debtFileHeader.getCompanyOID() + 
														 " Invoice No: " + debtFileDetail.getInvoiceNo() + 
														 " Process Statu: " + processStatu);
					errorFound = true;

				}

			}
			else if (recordType.equals(DebtFileRecordType.cancel.value())) {

				String processStatu = DebtFileMasterService.getMasterRecord(debtFileHeader, debtFileDetail).getProcessStatu();

				if (processStatu.equals("")) {
					debtFileDetail.setProcessExplanation("Update: Kayýt Bulunamadý.");
					errorFound = true;

				}
				else if (!CompanyDebtFileMasterService.isMasterRecordUpdatable(processStatu)) {
					
					debtFileDetail.setProcessExplanation("Delete: Kaydýn statusu uygun deðil. " + 
							                             "CompanyID: " + debtFileHeader.getCompanyOID() + 
							                             " Invoice No: " + debtFileDetail.getInvoiceNo() + 
							                             " Process Statu: " + processStatu);
					errorFound = true;

				}

			}
			
			
		} catch (CBException ex) {

			String errorMessage = ex.getMessage();

			if (errorMessage.length() > 400) {
				errorMessage = errorMessage.substring(0, 400);
			}

			debtFileDetail.setProcessExplanation(errorMessage);

			errorFound = true;

		}
		finally {
			
		}

		return errorFound;

	}

	private static boolean checkInputs(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;

		try {

			errorFound = isRecordTypeValid(debtFileDetail);

			if (!errorFound) {

				errorFound = isDueDateValid(debtFileDetail);

				if (!errorFound) {

					errorFound = isFileCurrencyEqualsRecordCurrency(debtFileHeader, debtFileDetail);

					if (!errorFound) {

						errorFound = isFileCurrencyInternalCodeEqualsRecordCurrencyInternalCode(debtFileHeader, debtFileDetail);

						if (!errorFound) {

							errorFound = isDueDateExpire(debtFileHeader, debtFileDetail);

							if (!errorFound) {

								errorFound = checkMaturityDate(debtFileHeader, debtFileDetail);

								if (!errorFound) {

									errorFound = checkDealerExistence(debtFileHeader, debtFileDetail);

									if (!errorFound) {
										
										String recordType = debtFileDetail.getRecordType();

										if (!recordType.equals(DebtFileRecordType.cancel.value())) {

											errorFound = isDealerLimitEnough(debtFileHeader, debtFileDetail);
											
										}	
										
									}

								}

							}

						}

					}

				}

			}


		} catch (CBException ex) {

			String errorMessage = ex.getMessage();

			if (errorMessage.length() > 400) {
				errorMessage = errorMessage.substring(0, 400);
			}

			debtFileDetail.setProcessExplanation(errorMessage);

			errorFound = true;

		}
		finally {

			if (errorFound) {
				debtFileDetail.setProcessStatu(DebtFileRecordStatu.error.value());
			}
		
		}

		return errorFound;

	}

	private static boolean isDealerLimitEnough(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;
		

		CBCurrency dealerAvalTotalAmount = getDealerAvalTotalAmount(debtFileHeader, debtFileDetail);
		
		String recordType = debtFileDetail.getRecordType();

		if (recordType.equals(DebtFileRecordType.update.value())) {
			
			CBCurrency existingInvoiceAmount = DebtFileMasterService.getMasterRecord(debtFileHeader, 
					                                                                      debtFileDetail).getInvoiceAmount();
			
			if (!isCurrencyTRY(debtFileDetail.getCurrency())) {

				CBCurrencyInfo curInfo = CBCurrencyInfo.getCurrencyInfo(debtFileDetail.getCurrency());

			//	existingInvoiceAmount = CurrencyUtility.getCurrencyRate(curInfo, existingInvoiceAmount, CBDateFactory.newCBDate(debtFileHeader.getFileDate())).getBoughtAmount();
			}
			
			dealerAvalTotalAmount = dealerAvalTotalAmount.add(existingInvoiceAmount);
		}

		CBCurrency invoiceAmount = debtFileDetail.getInvoiceAmount();

		if (!isCurrencyTRY(debtFileDetail.getCurrency())) {

			CBCurrencyInfo curInfo = CBCurrencyInfo.getCurrencyInfo(debtFileDetail.getCurrency());

		//	invoiceAmount = CurrencyUtility.getCurrencyRate(curInfo, invoiceAmount, CBDateFactory.newCBDate(debtFileHeader.getFileDate())).getBoughtAmount();
		}

		if (invoiceAmount.compareTo(dealerAvalTotalAmount) == 1) {

			debtFileDetail.setProcessExplanation("Fatura Tutarý Bayi nin Limit Boþluðundan fazladýr.");
			errorFound = true;
		}

		
		return errorFound;
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
				                                                            debtFileDetail.getCustomerNo().substring(7, 16));
		
		Vector<DealerAccount> dealerAccounts = dealer.getAccountsOrderedBySpecialCurrency(CurrencyUtility.DEFAULT_CURRENCY, LimitAccountOption.valueOf(String.valueOf(companyLimitFileParam.getAccountOption())), false);

		
		waitingDebt = new WaitingDebt[dealerAccounts.size()];
		totalWaitingDebt = CBCurrency.ZERO;

		for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {

			dealerAccount = dealerAccounts.get(innerIndex);

			dealerInternalCustCode = setDealerInternalCustomerCodeLongVersion(dealer.getCompanyDealerCustomerCode(), dealerAccount.getCurrency());

			waitingDebt[innerIndex] = getUnPaidWaitingInvoiceTotals(debtFileHeader.getCompanyOID(), 
					                                                debtFileDetail.getDueDate(), 
					                                                dealerInternalCustCode, dealerAccount.getCurrency());
			
			totalWaitingDebt = totalWaitingDebt.add(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount());

			if (isCurrencyTRY(dealerAccount.getCurrency())) {

				account = AccountUtility.getCurrentAccountInfo(dealerAccount.getAccountNo());

				usedAmountFromKMH = AccountUtility.getUsedAmountFromKMH(company, dealerAccount, account);

			}

		}
		
		dealerAvalTotalAmount = DealerService.getDealerAvaliableAmount(totalWaitingDebt, account, usedAmountFromKMH);

		return dealerAvalTotalAmount;
		
		
	}

	private static boolean checkDealerExistence(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;

		String dealerInnerCode = debtFileDetail.getCustomerNo().substring(7, 16);

		if (!Dealer.isDealerExistsByCompanyOIDInnerCustCode(debtFileHeader.getCompanyOID(), dealerInnerCode)) {
			debtFileDetail.setProcessExplanation(debtFileHeader.getCompanyOID() + " - " + dealerInnerCode + " kodlu bayii bulunamadý!");
			errorFound = true;
		}
		return errorFound;
	}

	private static boolean isRecordTypeValid(CompanyDebtFileDetail debtFileDetail) {

		boolean errorFound = false;

		String recordType = debtFileDetail.getRecordType();
		if ((!recordType.equals(DebtFileRecordType.neww.value())) && (!recordType.equals(DebtFileRecordType.update.value()))
				&& (!recordType.equals(DebtFileRecordType.cancel.value()))) {

			debtFileDetail.setProcessExplanation("Kayýt Tipi hatalý.");
			errorFound = true;
		}

		return errorFound;
	}

	private static boolean isFileCurrencyInternalCodeEqualsRecordCurrencyInternalCode(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) {

		boolean errorFound = false;

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

		return errorFound;
	}

	private static boolean isFileCurrencyEqualsRecordCurrency(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) {

		boolean errorFound = false;

		if (!debtFileDetail.getCurrency().equals(debtFileHeader.getFileCurrency())) {
			debtFileDetail.setProcessExplanation("Dosyanýn Döviz Cinsi ile Faturanýn Döviz Cinsi Uyumsuzdur.");
			errorFound = true;

		}
		return errorFound;
	}

	private static boolean isDueDateExpire(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) {

		boolean errorFound = false;

		CBDate fileDate = CBDateFactory.newCBDate(debtFileHeader.getFileDate());

		if (debtFileDetail.getDueDate().smallerThan(fileDate)) {
			debtFileDetail.setProcessExplanation("Vade Tarihi dosya tarihinden küçük.");
			errorFound = true;

		}

		return errorFound;

	}

	private static boolean isDueDateValid(CompanyDebtFileDetail debtFileDetail) {

		boolean errorFound = false;

		if (!GeneralUtility.isDateValid(debtFileDetail.getDueDate().toDBString())) {
			debtFileDetail.setProcessExplanation("Vade Trh bilgisi hatalý.");
			errorFound = true;
		}

		return errorFound;

	}

	private static boolean checkMaturityDate(CompanyDebtFileHeader debtFileHeader, CompanyDebtFileDetail debtFileDetail) throws CBException {

		boolean errorFound = false;

		CBDate processDate = CBSystem.getInstance().getProcessDate();

		Company company = new Company().get(debtFileHeader.getCompanyOID());

		CBDate acceptableDueDate = processDate.add(company.getCompanyMaturityPeriod());

		if (debtFileDetail.getDueDate().greaterThan(acceptableDueDate)) {
			debtFileDetail.setProcessExplanation("Borcun Vade Tarihi Firmanýn Geçerli Vade Tarihinden ileridedir.");
			errorFound = true;
		}

		return errorFound;
	}

	private static CompanyDebtFileDetail parseDetailRow(String fileRow) throws CBException {

		CompanyDebtFileDetail debtFileDetail = new CompanyDebtFileDetail();

		int index = 1;

		debtFileDetail.setCustomerNo(fileRow.substring(index, index + 16).trim());
		index = index + 16;

		debtFileDetail.setCustomerTitle(fileRow.substring(index, index + 30).trim());
		index = index + 30;

		debtFileDetail.setDueDate(CBDateFactory.newCBDate(fileRow.substring(index, index + 8).trim()));
		index = index + 8;

		debtFileDetail.setRecordType(fileRow.substring(index, index + 1).trim());
		index = index + 1;

		debtFileDetail.setInvoiceNo(fileRow.substring(index, index + 15).trim());
		index = index + 15;

		debtFileDetail.setInvoiceAmount(CBCurrencyFactory.newCBCurrency(fileRow.substring(index, index + 15).trim()));
		index = index + 15;

		debtFileDetail.setCurrency(fileRow.substring(index, index + 3).trim());

		return debtFileDetail;

	}

	public CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());

		if (debtFileHeader != null) {

			prepareAndSendMailNotify(debtFileHeader);

			debtFileHeader.updateForMailDocID();

		}
		
		return outBag;
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
				outBag.put(TABLE, index, RESULT4, qeMistake.getLong("INVOICENO"));
				outBag.put(TABLE, index, RESULT5, qeMistake.getCBCurrency("INVOICEAMOUNT"));
				outBag.put(TABLE, index, RESULT6, qeMistake.getString("PROCESSEXPLANATION"));
				outBag.put(TABLE, index, RESULT7, "Hatalý Kayýt");

				index++;

			}
			return outBag;
		} finally {
			
			if (qeMistake != null) {
				qeMistake.close();
			}
			
		}
	}

	private static void prepareAndSendMailNotify(CompanyDebtFileHeader debtFileHeader) throws CBException {

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

	public static String combineToMailList(long companyOID) throws CBException {

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

	public void sendReconData(CBBag inBag) throws CBException {
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());
		
		submitReconDataJob(debtFileHeader);
		
		
	}
}
