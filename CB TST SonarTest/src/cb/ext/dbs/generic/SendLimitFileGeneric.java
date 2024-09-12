package cb.ext.dbs.generic;

import static cb.ext.dbs.bean.DealerService.setDealerInternalCustomerCodeLongVersion;
import static cb.ext.dbs.generic.CompanyDebtFileMasterService.getUnPaidWaitingInvoiceTotals;
import static cb.ext.dbs.util.CurrencyUtility.convertCurrencytoStr_EraseCommas;
import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;

import java.util.Vector;

import cb.esi.fltr.util.bean.FileParser;
import cb.ext.dbs.bean.DBSObservationService;
import cb.ext.dbs.bean.DealerService;
import cb.ext.dbs.constants.DatabaseConstants;
import cb.ext.dbs.constants.LimitAccountOption;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyLimitFileEMail;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.CompanyParameters;
import cb.ext.dbs.data.Customer;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.generic.data.LimitFileGrandTotals;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.CustomerUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBundles;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFormatter;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBMultiLang;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;

import com.sun.corba.se.impl.util.Utility;

public class SendLimitFileGeneric implements CBBagKeys {

	public static CBBag sendLimitFileGeneric(CBBag inBag)  throws CBException {
		
		CBBag outBag    = CBBagFactory.createBag(); 
		CBBag reportBag = CBBagFactory.createBag();

		long companyID = inBag.get(COMPANYOID).toSimpleLong();

		Company company = getCompany(companyID);
		
		CompanyLimitFileParam  companyLimitFileParam = CompanyLimitFileParam.getCompanyLimitFileParam(companyID);
	

		if(companyLimitFileParam.isMailingEnabled()){
			
			reportBag.put(COMPANYOID, company.getOID());
			outBag = reportLimitFileQuery (reportBag);
			
		}
		
		
		if (companyLimitFileParam.isTextFileEnabled()) {
			
			sendFtpLimitTxtFile(inBag , company, companyLimitFileParam.getLimitFileName());
		}
	
		
		inBag.copyTo(outBag);
		outBag.put(RC, true);

		return outBag;
	}
	
	
	public static void sendMailLimitFile(CBBag inBag , Company company) throws CBException {

		CompanyLimitFileEMail companyLimitFileEMail = new CompanyLimitFileEMail();
		Vector<CompanyLimitFileEMail> companyLimitFileEMailVector = new Vector<CompanyLimitFileEMail>();
		companyLimitFileEMailVector = companyLimitFileEMail.getCompanyLimitFileEMail(company.getOID());
		
		String emailAddress  = "";
		String attachedDocID = "-1";
		String subject       = "";
		String fileName      = "";
		String message       = "";
		
		CBQueryExecuter qe  = null;
		
		CBDate fileDate = setFileDate(inBag);
		CBDateFormatter df = new CBDateFormatter("dd/MM/yyyy");	
		
		CBBag docBag    = CBBagFactory.createBag();
		CBBag queryBag  = CBBagFactory.createBag();
		CBBag prmBag    = CBBagFactory.createBag();
		
		Customer customer = CustomerUtility.getCustomerInfo(company.getCustomerNumber());
		
		try {
			qe =  new CBQueryExecuter("EXT_DBS_REPORT_LIMIT_FILE");
			qe.setParameter("SEQUENCENUMBER" , inBag.get(SEQUENCE_NUMBER).toString());
			qe.executeQuery();
			
			qe.getQueryAndParameters(queryBag , true );
			
			for (int index = 0; index < companyLimitFileEMailVector.size(); index++) {
				
				emailAddress = companyLimitFileEMailVector.get(index).getEmail();
				
			    subject  = "Fibabanka DBS Limit Dosyasý K.Kodu/Adý:" +company.getCompanyInternalCode()+"/"+customer.getTitle();
			    fileName = "DBS Limit Dosyasý_" + fileDate ;
			    
			    prmBag.put(CODE  , company.getCompanyInternalCode());
			    prmBag.put(TITLE , customer.getTitle());
			    prmBag.put(DATE1 , df.format(fileDate));
			    prmBag.put(TIME1 , CBSystem.getInstance().getCurrentTime().getCurrentTime(":"));
			    
			    message  = CBBundles.getInstance().getString("RB_DBS_LIMIT_MAIL_CONTENT", CBMultiLang.getDefaultLanguage().getLocale(),prmBag);
				message = message.replaceAll("<br>", "\n");
		
				
				docBag.put(CALLERSERVICENAME, "");
		        docBag.put(DOKUMANKODU, "9158");
		        docBag.put(DOKUMANDATA, queryBag);
		        docBag.put(DOCFORMAT, "");
		        docBag.put(ACIKLAMA, subject);
		        docBag.put(FILENAME, fileName);
	 
		        CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", docBag);
		        
		        attachedDocID = retbag.get(DOCUMENTID).toString();
		       
		        sendMail(message, subject, emailAddress, attachedDocID);
			}
			
		} finally {
			if(qe != null){
				qe.close();
			}
		}
		
		
	

	}
	



	



	public static CBBag sendMail(String message, String subject , String emailAddress , String attachedID) throws CBException {
		
		CBBag bagData = CBBagFactory.createBag();
		CBBag mailBag = CBBagFactory.createBag();
		
		bagData.put(MESSAGE, message);
		
		if (!attachedID.equals("-1")) {
		   mailBag.put(ATTACHED, attachedID);
	    }
	
		mailBag.put(DOKUMANKODU, "0392");
		mailBag.put(DOCDESTINATION, emailAddress);
		mailBag.put(DOCMEDIATYPE, "I");
		mailBag.put(ACIKLAMA, subject);
		mailBag.put(DOCFORMAT, "TXT");
		mailBag.put(CALLERSERVICENAME, Utility.class.getName());
		mailBag.put(DOKUMANDATA, bagData);
		
		mailBag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", mailBag);
		
		return mailBag;
		
	}


	
	private static Company getCompany(long companyID) throws CBException {
		return new Company().get(companyID);
	}
	
	
	
	public static CBBag reportLimitFileQuery(CBBag inBag ) throws CBException {
		CBBag fileBag = CBBagFactory.createBag();

		inBag.put(REPORTCODE, "1");
		
		fileBag = DBSObservationService.observeDealerLimits(inBag);

		return fileBag;

	}
	
	
	private static void sendFtpLimitTxtFile(CBBag inBag , Company company, String fileName) throws CBException {

		CBBag headerBag   = CBBagFactory.createBag();
		CBBag detailBag   = CBBagFactory.createBag();
		CBBag trailerBag  = CBBagFactory.createBag();
		
		FileParser parser = FileParser.getParser(DatabaseConstants.LIMIT_OUT_FILE + company.getCustomerNumber());
		
		String sequenceNumber = parser.openFile(DatabaseConstants.TFT_DBS_LIMIT_FILE);
		String ftmFileName    = "";

		CBDate fileDate = setFileDate(inBag);
		
		
		if (parser.isHeaderExists()) {
		
			headerBag = constructHeader(fileDate, company);
			parser.writeHeader(headerBag);
			
		}	
		
		LimitFileGrandTotals limitFileGrandTotals  = constructDetail( parser , fileDate,  company, null, false);
	
		if (parser.isFooterExists()) {
			trailerBag = constructTrailer (limitFileGrandTotals);
			parser.writeFooter(trailerBag);
		}
		 
		ftmFileName = GeneralUtility.genericFileName( fileName ,fileDate, CBSystem.getInstance().getCurrentTime(),sequenceNumber, "", "") +".txt";

		parser.exportFile(sequenceNumber, ftmFileName, DatabaseConstants.LIMIT_OUT_FILE+ + company.getCustomerNumber(), DatabaseConstants.TFT_DBS_LIMIT_FILE);

	}
	
	private static CBDate setFileDate(CBBag inBag) throws CBException {

		CBDate headerDate = null;

		if (inBag.existsBagKey(DATE1)) {
			headerDate = inBag.get(DATE1).toCBDate();
		}
		else {
			headerDate = CBSystem.getInstance().getProcessDate();
		}

		return headerDate;

	}
	
	
	private static CBBag constructHeader( CBDate fileDate, Company company) throws CBException  {
		
		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RECORDSTATUS , DatabaseConstants.FILE_HEADER_INDICATOR);
		outBag.put(COMPANYCODE  , company.getCompanyInternalCode());
		
		CompanyParameters params = new CompanyParameters();
		params.setCompanyOID(company.getOID());
		params.setKey("DATEFORMAT");
		String value = params.findCompanyParameterValue();
		if(value != null){
			if(value.equals("ddMMyyyy")){
				String dateStr = fileDate.toString();
				outBag.put(DATE, dateStr);
			}
			else{
				outBag.put(DATE, fileDate);
			}
		}
		else {
			outBag.put(DATE, fileDate);
		}
		
		outBag.put(BANKCODE		, Constants.FB_BANK_CODE);
		outBag.put(BANKNAME		, Constants.FB_BANK_NAME);
		outBag.put(COMPANYNAME	, company.getCompanyName());
		outBag.put(ACIKLAMA		, "");
		outBag.put(EUR_BUYSELL	, "00000000.000000");
		outBag.put(ENDOFDATA, "\r");
		
		return outBag;
		
	}
	
	public static LimitFileGrandTotals constructDetail(FileParser parser ,CBDate fileDate, Company company, Dealer specificDealer, boolean screenMode) throws CBException {
		
		CBBag detailBag  = CBBagFactory.createBag();

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
		
		CBCurrency grandTotalUsedAmountFromKMH = CBCurrency.ZERO;
		CBCurrency grandTotalLimit = CBCurrency.ZERO;
		CBCurrency grandTotalDealerAvalTotalAmount = CBCurrency.ZERO;
		CBCurrency totalGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
		CBCurrency grandTotalGuaranteedInvoiceAmount = CBCurrency.ZERO;
		CBCurrency totalNonGuaranteedInvoiceAmountByDealer = CBCurrency.ZERO;
		CBCurrency grandTotalNonGuaranteedInvoiceAmount = CBCurrency.ZERO;
		CBCurrency remainingLimit                       = CBCurrency.ZERO;
		CBCurrency availableLimit                       = CBCurrency.ZERO;
		
		String remainingLimitSign = "";
		String availableLimitSign = "";
		
		
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
		int rowCount = 0;
		
		for (int index = 0; index < dealers.size(); index++) {

			dealer = dealers.get(index);

			dealerAccounts = dealer.getAccountsOrderedBySpecialCurrency(CurrencyUtility.DEFAULT_CURRENCY, 
					                     companyLimitFileParam.getAccountOption(), true);
			
			waitingDebt = new WaitingDebt[dealerAccounts.size()];
			totalWaitingDebt = CBCurrency.ZERO;
			
			for (int innerIndex = 0; innerIndex < dealerAccounts.size(); innerIndex++) {

				dealerAccount = dealerAccounts.get(innerIndex);

				dealerInternalCustCode = setDealerInternalCustomerCodeLongVersion(dealer.getCompanyDealerCustomerCode(), 
						                                                          dealerAccount.getCurrency());
				
				waitingDebt[innerIndex] = getUnPaidWaitingInvoiceTotals(company.getOID(), fileDate, 
						dealer.getCompanyDealerCustomerCode(), dealerAccount.getCurrency());
				
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
				
				if (parser != null) {
					
					remainingLimit = account.getLimitAmount().subtract(usedAmountFromKMH).subtract(totalWaitingDebt) ;
					availableLimit = account.getLimitAmount().subtract(usedAmountFromKMH);
					
					
					if(remainingLimit.compareTo(CBCurrency.ZERO)>=0){
						remainingLimitSign = DatabaseConstants.CURRENCY_POSITIVE_SIGN;
					}else{
						remainingLimit = remainingLimit.negate();
						remainingLimitSign = DatabaseConstants.CURRENCY_NEGATIVE_SIGN;
					}
					
					if(availableLimit.compareTo(CBCurrency.ZERO)>=0){
						availableLimitSign = DatabaseConstants.CURRENCY_POSITIVE_SIGN;
					}else {
						
						availableLimit = availableLimit.negate();
						availableLimitSign = DatabaseConstants.CURRENCY_NEGATIVE_SIGN;
					}
					
					if(companyLimitFileParam.getAccountOption().equals(LimitAccountOption.All)){

						detailBag.put(RECORDSTATUS      , DatabaseConstants.FILE_DETAIL_INDICATOR);
						detailBag.put(SPECIALCODE       , dealerInternalCustCode);
						detailBag.put(ACCOUNTTITLE      , account.getTitle());
						detailBag.put(ACCOUNTBRANCHCODE , account.getBranchCode());
						detailBag.put(ACCOUNTNR			, account.getAccountNo());
						detailBag.put(LIMITAMOUNT       , convertCurrencytoStr_EraseCommas(account.getLimitAmount()));
						detailBag.put(LIMITUSED         , convertCurrencytoStr_EraseCommas(usedAmountFromKMH));
						detailBag.put(LIMITSUM	    	, convertCurrencytoStr_EraseCommas(account.getLimitAmount().subtract(usedAmountFromKMH)));
						detailBag.put(INVOICEAMOUNT     , convertCurrencytoStr_EraseCommas(waitingDebt[innerIndex].getGuaranteedTotalInvoiceAmount()));
						detailBag.put(AMOUNT            , convertCurrencytoStr_EraseCommas(waitingDebt[innerIndex].getNonGuaranteedTotalInvoiceAmount()));
						detailBag.put(CURRENCY          , dealerAccount.getCurrency());  
						detailBag.put(AVAILABLELIMIT    , convertCurrencytoStr_EraseCommas(dealerAvalTotalAmount));
						detailBag.put(AVAILABLE_AMOUNT  , convertCurrencytoStr_EraseCommas(availableLimit));
						detailBag.put(AMOUNT2           , convertCurrencytoStr_EraseCommas(overdueNonGuaranteedTotalAmount));
						
						detailBag.put(DEALER_CODE       , dealer.getCompanyDealerCustomerCode());
						detailBag.put(REMAININGLIMIT    , convertCurrencytoStr_EraseCommas(remainingLimit));
						detailBag.put(VALUE1            , remainingLimitSign );
						detailBag.put(VALUE2            , availableLimitSign );
						detailBag.put(TOTALCOUNT        , String.valueOf(waitingDebt[innerIndex].getTotalWaitingInvoiceCount()));
						detailBag.put(ACIKLAMA			, "");
						detailBag.put(ENDOFDATA			, "\r");
						
						parser.writeDetail(detailBag);
					
						rowCount++;
					}
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
			
			grandTotalUsedAmountFromKMH = grandTotalUsedAmountFromKMH.add(usedAmountFromKMH);

			if(companyLimitFileParam.getAccountOption().equals(LimitAccountOption.Only_TRY) && parser != null){
				detailBag.put(DEALER_CODE       , dealer.getCompanyDealerCustomerCode());
				detailBag.put(LIMITAMOUNT       , convertCurrencytoStr_EraseCommas(account.getLimitAmount()));
				detailBag.put(REMAININGLIMIT    , convertCurrencytoStr_EraseCommas(remainingLimit));
				detailBag.put(AVAILABLELIMIT    , convertCurrencytoStr_EraseCommas(availableLimit));
				detailBag.put(TOTALCOUNT        , String.valueOf(totalWaitingInvoiceCountByDealer));
				detailBag.put(INVOICEAMOUNT     , convertCurrencytoStr_EraseCommas(totalWaitingDebt));
				detailBag.put(VALUE1            , remainingLimitSign );
				detailBag.put(VALUE2            , availableLimitSign );
				parser.writeDetail(detailBag);
				detailBag.clear();
				
				rowCount++;
			}
			
			totalWaitingInvoiceCountByDealer = 0;
			
		}

		return new  LimitFileGrandTotals(rowCount,
										dealers.size(), 
				                        grandTotalLimit, 
				                        grandTotalDealerAvalTotalAmount,
				                        grandTotalGuaranteedInvoiceAmount,
				                        grandTotalNonGuaranteedInvoiceAmount,
				                        grandTotalWaitingInvoiceCount,
				                        CBCurrency.ZERO,
				                        grandTotalUsedAmountFromKMH
				                       );

		
	}
	
	
	
	private static CBBag constructTrailer(LimitFileGrandTotals limitFileGrandTotals) throws CBException  {

		CBBag trailerBag = CBBagFactory.createBag();
		
		
		trailerBag.put(RECORDSTATUS     , DatabaseConstants.FILE_TRAILER_INDICATOR);
		trailerBag.put(ROWCOUNT			, String.valueOf(limitFileGrandTotals.getRowCount()));
		trailerBag.put(CUSTOMERVALUE    , String.valueOf(limitFileGrandTotals.getTotalCustomerCount()));
		trailerBag.put(LIMITAMOUNT      , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalLimit()));
		trailerBag.put(AVAILABLE_AMOUNT , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalDealerAvalTotalAmount()));
		trailerBag.put(INVOICEAMOUNT    , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalGuaranteedInvoiceAmount()));
		trailerBag.put(AMOUNT           , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalNonGuaranteedInvoiceAmount()));
		trailerBag.put(TOTALCOUNT       , String.valueOf(limitFileGrandTotals.getGrandTotalWaitingInvoiceCount()));
		trailerBag.put(AMOUNT2          , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalNonGuaranteedInvoiceAmount2()));
		trailerBag.put(LIMITUSED	    , convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalUsedAmountFromKMH()));
		trailerBag.put(LIMITSUM	    	, convertCurrencytoStr_EraseCommas(limitFileGrandTotals.getGrandTotalLimit().subtract(limitFileGrandTotals.getGrandTotalUsedAmountFromKMH())));
		trailerBag.put(ACIKLAMA			, "");
		trailerBag.put(ENDOFDATA		, "\r");
		
		return trailerBag;
	

	}
	
	
}
