package cb.ext.dbs.tosyali;

import static cb.ext.dbs.bean.DealerService.setDealerInternalCustomerCodeLongVersion;
import static cb.ext.dbs.tosyali.CompanyDebtFileMasterService.getUnPaidWaitingInvoiceTotals;
import static cb.ext.dbs.util.CurrencyUtility.convertCurrencytoStr_EraseCommas;
import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;
import static cb.ext.dbs.util.GeneralUtility.paddingString;

import java.util.Vector;

import cb.esi.fltr.util.CBFile;
import cb.esi.fltr.util.CBFileFactory;
import cb.ext.dbs.bean.DealerService;
import cb.ext.dbs.constants.DatabaseConstants;
import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.dbsinterfaces.ILimitFile;
import cb.ext.dbs.tosyali.data.LimitFileGrandTotals;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.FileUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSystem;

public class SendLimitFile implements ILimitFile, CBBagKeys {

	public CBBag sendLimitFile(CBBag inBag)  throws CBException {
		
		CBBag outBag = CBBagFactory.createBag(); 

		CBDate fileDate = setFileDate(inBag);

		long companyID = inBag.get(COMPANYOID).toSimpleLong();

		Company company = getCompany(companyID);

		String outSequenceNumber = FileUtility.getTFTSequenceNumber();

		CBFile outgoingFile = CBFileFactory.newCBFile(outSequenceNumber, DatabaseConstants.TFT_DBS_LIMIT_FILE, "a");


		constructHeader(outgoingFile, fileDate, company.getCompanyInternalCode());

		LimitFileGrandTotals limitFileGrandTotals = constructDetail(outgoingFile, fileDate, company, null);
		
		constructTrailer(outgoingFile, limitFileGrandTotals);
		
		outgoingFile.cbFileFlush();
		
		String ftmFileName = "DBS_LIMIT_" + fileDate.toDBString() + "_" + outSequenceNumber + ".txt";
		String ftmTransferID = "DBS_SENT_LIMIT_FILE_" + company.getCustomerNumber();

		FileUtility.exportFile(outSequenceNumber, DatabaseConstants.TFT_DBS_LIMIT_FILE, ftmFileName, ftmTransferID);

		outBag.put(RC, true);

		return outBag;
	}
	
	private void writeToFile(CBFile outgoingFile, StringBuffer fileLine) throws CBException {
		outgoingFile.cbFileWrite(fileLine.toString());

		fileLine.delete(0, fileLine.length());

	}

	private void constructHeader(CBFile outgoingFile, CBDate fileDate, String companyInternalCode) throws CBException  {

		StringBuffer line = new StringBuffer();

		line.append("H");
		line.append(paddingString(companyInternalCode, 4, ' ', true));
		line.append(fileDate.toDBString());

		writeToFile(outgoingFile, line);

	}
	
	private void constructTrailer(CBFile outgoingFile, LimitFileGrandTotals limitFileGrandTotals) throws CBException  {

		StringBuffer line = new StringBuffer();

		line.append("T");
		line.append(paddingString(String.valueOf(limitFileGrandTotals.getTotalCustomerCount()), 10, '0', true));
		line.append(paddingString(convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalLimit()), 15, '0', true));
		line.append(paddingString(convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalDealerAvalTotalAmount()), 15, '0', true));
		line.append(paddingString(convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalGuaranteedInvoiceAmount()), 15, '0', true));
		line.append(paddingString(convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalNonGuaranteedInvoiceAmount()), 15, '0', true));
		line.append(paddingString(String.valueOf(limitFileGrandTotals.getGrandTotalWaitingInvoiceCount()), 18, '0', true));
		line.append(paddingString(convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalNonGuaranteedInvoiceAmount2()), 18, '0', true));
		

		writeToFile(outgoingFile, line);

	}

	public LimitFileGrandTotals constructDetail(CBFile outgoingFile, CBDate fileDate, Company company, Dealer specificDealer) throws CBException {

		StringBuffer line = new StringBuffer();

		Vector<Dealer> dealers = null;

		Dealer dealer = null;

		Vector<DealerAccount> dealerAccounts = null;
		DealerAccount dealerAccount = null;

		String dealerInternalCustCode;

		WaitingDebt[] waitingDebt = null;
		CBCurrency totalWaitingDebt = CBCurrency.ZERO;
		
		Account account = null;
		
		CBCurrency usedAmountFromKMH = CBCurrency.ZERO;
		CBCurrency dealerAvalTotalAmount = CBCurrency.ZERO;
		CBCurrency overdueNonGuaranteedTotalAmount = CBCurrency.ZERO;
		
		CBCurrency grandTotalLimit = CBCurrency.ZERO;
		CBCurrency grandTotalDealerAvalTotalAmount = CBCurrency.ZERO;
		CBCurrency totalGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
		CBCurrency grandTotalGuaranteedInvoiceAmount = CBCurrency.ZERO;
		CBCurrency totalNonGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
		CBCurrency grandTotalNonGuaranteedInvoiceAmount = CBCurrency.ZERO;
		
		int totalWaitingInvoiceCountByDealer = 0; 
		int grandTotalWaitingInvoiceCount = 0; 
		
		if (specificDealer == null) {
			dealers = company.getDealers(true);
		}
		else {
			dealers = new Vector<Dealer>();
			dealers.add(specificDealer);
		}
		
		
		CompanyLimitFileParam companyLimitFileParam = CompanyLimitFileParam.getCompanyLimitFileParam(company.getOID());

		for (int index = 0; index < dealers.size(); index++) {

			dealer = dealers.get(index);

			dealerAccounts = dealer.getAccountsOrderedBySpecialCurrency(CurrencyUtility.DEFAULT_CURRENCY, LimitAccountOption.valueOf(String.valueOf(companyLimitFileParam.getAccountOption())),false);
			
			waitingDebt = new WaitingDebt[dealerAccounts.size()];
			totalWaitingDebt = CBCurrency.ZERO;
			
			for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {

				dealerAccount = dealerAccounts.get(innerIndex);

				dealerInternalCustCode = setDealerInternalCustomerCodeLongVersion(dealer.getCompanyDealerCustomerCode(), 
						                                                          dealerAccount.getCurrency());
				
				waitingDebt[innerIndex] = getUnPaidWaitingInvoiceTotals(company.getOID(), fileDate, 
						                                                dealerInternalCustCode, dealerAccount.getCurrency());
				
				totalWaitingDebt =  totalWaitingDebt.add(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount());
			}	

			for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {

				dealerAccount = dealerAccounts.get(innerIndex);

				dealerInternalCustCode = setDealerInternalCustomerCodeLongVersion(dealer.getCompanyDealerCustomerCode(), 
						                                                          dealerAccount.getCurrency());

				if ( isCurrencyTRY(dealerAccount.getCurrency()) ) {
					
					account = AccountUtility.getCurrentAccountInfo(dealerAccount.getAccountNo());
				
					usedAmountFromKMH = AccountUtility.getUsedAmountFromKMH(company, dealerAccount, account);
				
					dealerAvalTotalAmount = DealerService.getDealerAvaliableAmount(totalWaitingDebt, account, usedAmountFromKMH);
					
				}	
				
				overdueNonGuaranteedTotalAmount = CBCurrency.ZERO;
				
				if (outgoingFile != null) {

					line.append("D");
					line.append(paddingString(dealerInternalCustCode, 16, ' ', true));
					line.append(paddingString(account.getTitle(), 30, ' ', true));
					line.append(paddingString(account.getBranchCode(), 5, ' ', true));
					line.append(paddingString(convertCurrencytoStr_EraseCommas(account.getLimitAmount()), 13, '0', true));
					line.append(paddingString(convertCurrencytoStr_EraseCommas(usedAmountFromKMH), 15, '0', true));
					line.append(paddingString(convertCurrencytoStr_EraseCommas(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount()), 15, '0', true));
					line.append(paddingString(convertCurrencytoStr_EraseCommas(waitingDebt[innerIndex].getNonGuaranteedTotalInvoiceAmount()), 15, '0', true));
					line.append(dealerAccount.getCurrency());
					line.append(paddingString(convertCurrencytoStr_EraseCommas(dealerAvalTotalAmount), 15, '0', true));
					line.append(paddingString(convertCurrencytoStr_EraseCommas(overdueNonGuaranteedTotalAmount), 15, '0', true));

					writeToFile(outgoingFile, line);
					
				}
				
				totalGuaranteedInvoiceAmountByDealer = totalGuaranteedInvoiceAmountByDealer.add(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount());
				totalWaitingInvoiceCountByDealer =  totalWaitingInvoiceCountByDealer + waitingDebt[innerIndex].getTotalWaitingInvoiceCount();

			}
			
			grandTotalLimit = grandTotalLimit.add(account.getLimitAmount());
			grandTotalDealerAvalTotalAmount = grandTotalDealerAvalTotalAmount.add(dealerAvalTotalAmount);

			grandTotalGuaranteedInvoiceAmount = grandTotalGuaranteedInvoiceAmount.add(totalGuaranteedInvoiceAmountByDealer);
			grandTotalNonGuaranteedInvoiceAmount = grandTotalNonGuaranteedInvoiceAmount.add(totalNonGuaranteedInvoiceAmountByDealer);
			
			grandTotalWaitingInvoiceCount = grandTotalWaitingInvoiceCount+ totalWaitingInvoiceCountByDealer;
			
			dealerAvalTotalAmount = CBCurrency.ZERO;
			totalGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
			totalNonGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
			totalWaitingInvoiceCountByDealer = 0;
			

		}

		return new LimitFileGrandTotals(dealers.size(), 
				                        grandTotalLimit, 
				                        grandTotalDealerAvalTotalAmount,
				                        grandTotalGuaranteedInvoiceAmount,
				                        grandTotalNonGuaranteedInvoiceAmount,
				                        grandTotalWaitingInvoiceCount,
				                        CBCurrency.ZERO,
				                        usedAmountFromKMH
				                       );

	}




	private CBDate setFileDate(CBBag inBag) throws CBException {

		CBDate headerDate = null;

		if (inBag.existsBagKey(DATE1)) {
			headerDate = inBag.get(DATE1).toCBDate();
		}
		else {
			headerDate = CBSystem.getInstance().getProcessDate();
		}

		return headerDate;

	}

	private Company getCompany(long companyID) throws CBException {
		return new Company().get(companyID);
	}
		
}

	
	


