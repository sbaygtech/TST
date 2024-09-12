package cb.ext.dbs.bean;

import static cb.ext.dbs.util.AccountUtility.depositeMoney;
import static cb.ext.dbs.util.AccountUtility.withdrawMoney;
import static cb.ext.dbs.util.CurrencyUtility.DEFAULT_CURRENCY;

import java.util.List;

import cb.ext.dbs.batch.LoadDBSDebtFileGeneric;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.CurrencyProcessMethod;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DebtFileRecordStatu;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.ProcessExchangeRateCodeType;
import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyAccount;
import cb.ext.dbs.data.CompanyDebtFileDetailExceptionParam;
import cb.ext.dbs.data.CompanyProcessDebtExtension;
import cb.ext.dbs.data.CurrencyRate;
import cb.ext.dbs.data.Dealer;
import cb.ext.dbs.data.DealerAccount;
import cb.ext.dbs.data.DealerGuarantor;
import cb.ext.dbs.generic.LoadDebtFileGeneric;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileDetailException;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.CompanyDebtFileProcessForeignCurConversion;
import cb.ext.dbs.pom.DBSCompanyTotalFCDailyPOM;
import cb.ext.dbs.pom.DBSCompanyTotalFCDailyPOMData;
import cb.ext.dbs.util.AccountUtility;
import cb.ext.dbs.util.CurrencyUtility;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.bagkeys.CBBagKeysMFSE;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBatchSubmitter;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class DebtFileService implements CBBagKeys {
	
	private static CBBag doPersoanalDbsMasterFileAccounting(CBBag inBag)	throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBBag explanationBag = CBBagFactory.createBag();
		
		boolean errorFound = false;
		String  returnMessage = "";
		
		try {
			long  productOpRefNo = CBSessionInfo.getCurrentUrunIslemRefNo();
			
			explanationBag = getAccountTrnxExplanationBagForPersonalDBS(inBag); // TODO açýklama farklý istiyorlar
			
			Account dealerAccount = AccountUtility.getAccountInfo(inBag.get(ACCOUNTNR).toString());
			Account companyAccount = AccountUtility.getAccountInfo(inBag.get(ACCOUNTNR2).toString());
			
			CBDate processDate = inBag.get(PROCESSDATE).toCBDate();
			
			CBCurrency invoiceAmount = inBag.get(AMOUNT).toCBCurrency();
			String     currencyCode  =  inBag.get(CURRENCY).toString();	
			boolean usedLimit = inBag.get(LIMITUSED).toBoolean();
		
			CBCurrency baseAmount;
			
			long companyOid = inBag.get(COMPANYOID).toSimpleLong();
			long dealerOid = inBag.get(OID).toSimpleLong();
			Company company = new Company().get(companyOid);
	
			if(company.isPartialPayable()){
				// TODO þuan için parçalý ödeme bireysel dbs'de yok
			}
			else {
				// Garantörlüðü varsa
				if(company.isGuarantorship()) {
					Dealer dealer = new Dealer();
					dealer = dealer.getDealer(inBag.get(OID).toSimpleLong());
					
					DealerGuarantor dealerGuarantor = new DealerGuarantor().getActiveDealerGuarantor(companyOid, dealerOid);
					
					if(dealerGuarantor == null) {
						if(isAccountBalanceNotEnough(dealerAccount, invoiceAmount, usedLimit) < 0) { // kanutakipte ise tz açýyorum kanuni takipte deðilse ödeme
							dealerGuarantor = new DealerGuarantor();
							dealerGuarantor.setActive(1);
							dealerGuarantor.setCompanyOid(companyOid);
							dealerGuarantor.setDealerOid(dealerOid);
							dealerGuarantor.setStartDate(processDate);
							dealerGuarantor.setFinishDate(processDate.add(CBDate.MONTH, company.getGuarantorshipWaitingTime().intValue()));
							dealerGuarantor.create(dealerGuarantor);
													
							baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
							
							withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, true);
							
							depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
									  invoiceAmount, baseAmount, currencyCode,
									  explanationBag, productOpRefNo);
						}
						else {
							baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
							
							withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, false);
							
							depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
									  invoiceAmount, baseAmount, currencyCode,
									  explanationBag, productOpRefNo);
						}
					}
					else {
						if(dealerGuarantor.getFinishDate().gEqualTo(processDate)) {
							
							if(isAccountBalanceNotEnough(dealerAccount, invoiceAmount, usedLimit) < 0) {
								
								baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
								
								withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, true);
								
								depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
										  invoiceAmount, baseAmount, currencyCode,
										  explanationBag, productOpRefNo);
							}
							else {
								dealerGuarantor.setActive(0);
								dealerGuarantor.update(dealerGuarantor);
								
								baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
								
								withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, false);
								
								depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
										  invoiceAmount, baseAmount, currencyCode,
										  explanationBag, productOpRefNo);
							}
						}
						else {
							CBBag errorBag = CBBagFactory.createBag();
							errorBag.put(IDENTITY, dealer.getCompanyDealerCustomerCode());
							throw new CBException(DBSExceptions.PERSONEL_DBS_GUARANTORSHIP_EXPIRED, errorBag);
						}
					}
				}
				else {
					baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
										
					withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, false);
					
					depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
							  invoiceAmount, baseAmount, currencyCode,
							  explanationBag, productOpRefNo);
				}
			}
			
			outBag.put(TITLE         , dealerAccount.getTitle());
			outBag.put(INVOICENO   , inBag.get(INVOICENO));
			
		} 
		catch (CBException ex) {
			errorFound = true;
			returnMessage = ex.getMessage();
		}
		finally{
			if (errorFound) {
				outBag.put(CODE1 , DebtRecordStatu.error.value());
			}
			else {
				outBag.put(CODE1 , DebtRecordStatu.done.value());
				
				if(inBag.existsBagKey(PARTIALFOUND) && inBag.get(PARTIALFOUND).toBoolean()){
					outBag.put(CODE1 , DebtRecordStatu.partialPayment.value());
				}
			}
			
			outBag.put(EXPLANATION , returnMessage);
		}
		
	 	return outBag;
	}
	
	private static CBBag doMasterFileAccounting(CBBag inBag)	throws CBException {
		

		CBBag outBag = CBBagFactory.createBag();
		CBBag explanationBag = CBBagFactory.createBag();
		

		boolean errorFound = false;
		String  returnMessage = "";
		
		try {
			
			long  productOpRefNo = CBSessionInfo.getCurrentUrunIslemRefNo();
			
		
			explanationBag = getAccountTrnxExplanationBag(inBag);
				 
			
			Account dealerAccount = AccountUtility.getAccountInfo(inBag.get(ACCOUNTNR).toString());
			Account companyAccount = AccountUtility.getAccountInfo(inBag.get(ACCOUNTNR2).toString());
			
			
			CBDate processDate = inBag.get(PROCESSDATE).toCBDate();
			
			CBCurrency invoiceAmount = inBag.get(AMOUNT).toCBCurrency();
			String     currencyCode  =  inBag.get(CURRENCY).toString();	
			boolean usedLimit = inBag.get(LIMITUSED).toBoolean();
		
			
			CBCurrency baseAmount;
			
			long companyOID = inBag.get(COMPANYOID).toSimpleLong();
			
			Company company = new Company().get(companyOID);

			String exchangeRateBankType =GeneralUtility.getExchangeRateBankType(company.getExchangeRate());
	/*	
			if ( (isAccountBalanceNotEnough(dealerAccount.getAvalBalance(), invoiceAmount) < 0) && 
				 (!CurrencyUtility.isCurrencyTRY(currencyCode)) && !company.isPartialPayable()
			   ) {
				
				long dealerOID = inBag.get(OID).toSimpleLong();
				long masterOID = inBag.get(MASTEROID).toSimpleLong();
				long sequenceNumber = inBag.get(NUMBER0).toSimpleLong();
				
				Account dealerTRYAccount = AccountUtility.getAccountInfo(new DealerAccount().getDealerAccountNo(dealerOID, DEFAULT_CURRENCY));
				
				CBCurrency remainingDebtAmount = invoiceAmount.subtract(dealerAccount.getAvalBalance());
				
				CurrencyRate currencyRate = getRemainingDebtAmountTRY(processDate, currencyCode, remainingDebtAmount, exchangeRateBankType);
				CBCurrency remainingDebtAmountTRY = currencyRate.getBoughtAmount();
								
				baseAmount = remainingDebtAmountTRY;
					
				
				withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT1, dealerTRYAccount.getAccountNo(), 
						      remainingDebtAmountTRY, baseAmount, CurrencyUtility.DEFAULT_CURRENCY, 
						      explanationBag, productOpRefNo);
				
				depositeMoney(CBBagKeysMFSE.MFSE_CREDIT1, dealerAccount.getAccountNo(),  
							  remainingDebtAmount, baseAmount, currencyCode,
							  explanationBag, productOpRefNo);

				

				
				
				baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
				
				withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), 
							  invoiceAmount, baseAmount, currencyCode, 
							  explanationBag, productOpRefNo);
		
				depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
						  invoiceAmount, baseAmount, currencyCode,
						  explanationBag, productOpRefNo);


				
				insertIntoFCDailyTable(processDate,sequenceNumber, 
						               companyOID, dealerOID, masterOID,
						               currencyCode, remainingDebtAmount, remainingDebtAmountTRY , currencyRate);
				
				
				
				outBag.put(AMOUNT , remainingDebtAmount);
				outBag.put(ACCOUNTNR , dealerTRYAccount.getAccountNo());
				outBag.put(ACCOUNTNR2, inBag.get(ACCOUNTNR));
				outBag.put(TITLE , dealerAccount.getTitle());
				outBag.put(RATE , currencyRate.getRate());	
				outBag.put(CURRENCYINPUT , 1);
				
					

				
			}
			else  {
				
				baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
				
				withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo);
				
				
				depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
						  invoiceAmount, baseAmount, currencyCode,
						  explanationBag, productOpRefNo);
				
			}
			
		*/
			
			
			if(company.isPartialPayable()){
				baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
				
				if(usedLimit) {
					if(dealerAccount.getAvalBalance().compare(baseAmount) < 0 ){
						baseAmount = dealerAccount.getAvalBalance();
					}
				}
				else {
					if(dealerAccount.getBalanceWithoutLimit().compare(baseAmount) < 0 ){
						baseAmount = dealerAccount.getBalanceWithoutLimit();
					}
				}
				
				withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, false);
				
				depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
						  invoiceAmount, baseAmount, currencyCode,
						  explanationBag, productOpRefNo);
			}else {
				
				if((isAccountBalanceNotEnough(dealerAccount, invoiceAmount, usedLimit) < 0) && (!CurrencyUtility.isCurrencyTRY(currencyCode)) ){
					
					long dealerOID = inBag.get(OID).toSimpleLong();
					long masterOID = inBag.get(MASTEROID).toSimpleLong();
					long sequenceNumber = inBag.get(NUMBER0).toSimpleLong();
					
					Account dealerTRYAccount          = null;
					Account companyTRYAccount         = null;
					CBCurrency remainingDebtAmount    = null;
					CurrencyRate currencyRate         = null;
					CBCurrency remainingDebtAmountTRY = null;
					
					if(company.getCurrencyProcessMethod().equals(CurrencyProcessMethod.ForeignCurrencyFixed.value())){
						
						dealerTRYAccount       = AccountUtility.getAccountInfo(new DealerAccount().getDealerAccountNo(dealerOID, DEFAULT_CURRENCY));
						if(usedLimit) {
							remainingDebtAmount    = invoiceAmount.subtract(dealerAccount.getAvalBalance());
						}
						else {
							remainingDebtAmount    = invoiceAmount.subtract(dealerAccount.getBalanceWithoutLimit());
						}
						
						currencyRate           = getRemainingDebtAmount(processDate, currencyCode, remainingDebtAmount, exchangeRateBankType, company.getExchangeRateType());
						remainingDebtAmountTRY = currencyRate.getSoldAmount();
						
						baseAmount = remainingDebtAmountTRY;
						
						
						withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT1, dealerTRYAccount.getAccountNo(), 
								      remainingDebtAmountTRY, baseAmount, CurrencyUtility.DEFAULT_CURRENCY, 
								      explanationBag, productOpRefNo, usedLimit, false);
						
						depositeMoney(CBBagKeysMFSE.MFSE_CREDIT1, dealerAccount.getAccountNo(),  
									remainingDebtAmountTRY, baseAmount, currencyCode,
									  explanationBag, productOpRefNo);

						
						baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
						
						withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), 
									  invoiceAmount, baseAmount, currencyCode, 
									  explanationBag, productOpRefNo, usedLimit, false);
				
						depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
								  invoiceAmount, baseAmount, currencyCode,
								  explanationBag, productOpRefNo);

						
						
					}else if (company.getCurrencyProcessMethod().equals(CurrencyProcessMethod.ForeignCurrencyVariable.value())) {
					
						dealerTRYAccount       = AccountUtility.getAccountInfo(new DealerAccount().getDealerAccountNo(dealerOID, DEFAULT_CURRENCY));
						companyTRYAccount      = AccountUtility.getAccountInfo(new CompanyAccount().getCompanyAccountNo(companyOID, currencyCode));
						
						currencyRate           = getRemainingDebtAmount(processDate, currencyCode, invoiceAmount, exchangeRateBankType, company.getExchangeRateType());
						remainingDebtAmountTRY = currencyRate.getSoldAmount();
						
						
						
						baseAmount = remainingDebtAmountTRY;
						
						
						withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT1, dealerTRYAccount.getAccountNo(), 
								      remainingDebtAmountTRY, baseAmount, CurrencyUtility.DEFAULT_CURRENCY, 
								      explanationBag, productOpRefNo, usedLimit, false);
						
						depositeMoney(CBBagKeysMFSE.MFSE_CREDIT1, companyTRYAccount.getAccountNo(),  
									  remainingDebtAmountTRY, baseAmount, CurrencyUtility.DEFAULT_CURRENCY,
									  explanationBag, productOpRefNo);
						
						

						saveProcessForeignCurrencyConvertionInfo (masterOID,remainingDebtAmountTRY ,CurrencyUtility.DEFAULT_CURRENCY, currencyRate.getRate(), invoiceAmount, currencyCode ,invoiceAmount, currencyCode);
						
					}else if (company.getCurrencyProcessMethod().equals(CurrencyProcessMethod.ForeignPartialCurrencyVariable.value())) {
						dealerTRYAccount       = AccountUtility.getAccountInfo(new DealerAccount().getDealerAccountNo(dealerOID, DEFAULT_CURRENCY));
						companyTRYAccount      = AccountUtility.getAccountInfo(new CompanyAccount().getCompanyBlockageAccountNo(companyOID, DEFAULT_CURRENCY));
						
						if(usedLimit) {
							baseAmount = dealerAccount.getAvalBalance();
						}
						else {
							baseAmount = dealerAccount.getBalanceWithoutLimit();
						}
										
						withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), 
								dealerAccount.getAvalBalance(), baseAmount, currencyCode, 
									  explanationBag, productOpRefNo, usedLimit, false);
				
						depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
								dealerAccount.getAvalBalance(), baseAmount, currencyCode,
								  explanationBag, productOpRefNo);
		
						saveProcessForeignCurrencyConvertionInfo (masterOID,dealerAccount.getAvalBalance() ,currencyCode , CBCurrency.ZERO, dealerAccount.getAvalBalance() ,currencyCode ,invoiceAmount, currencyCode);
						
						if(usedLimit) {
							remainingDebtAmount    = invoiceAmount.subtract(dealerAccount.getAvalBalance());
						}
						else {
							remainingDebtAmount    = invoiceAmount.subtract(dealerAccount.getBalanceWithoutLimit());
						}
						
						currencyRate           = getRemainingDebtAmount(processDate, currencyCode, remainingDebtAmount, exchangeRateBankType, company.getExchangeRateType());
						remainingDebtAmountTRY = currencyRate.getBoughtAmount();
						
						
						
						
						baseAmount = currencyRate.getBoughtAmount();
						
						
						withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT1, dealerTRYAccount.getAccountNo(), 
								      remainingDebtAmountTRY , baseAmount, CurrencyUtility.DEFAULT_CURRENCY, 
								      explanationBag, productOpRefNo, usedLimit, false);
						
						depositeMoney(CBBagKeysMFSE.MFSE_CREDIT1, companyTRYAccount.getAccountNo(),  
								      remainingDebtAmountTRY, baseAmount, CurrencyUtility.DEFAULT_CURRENCY,
									  explanationBag, productOpRefNo);
						
						
						saveProcessForeignCurrencyConvertionInfo (masterOID,remainingDebtAmountTRY ,CurrencyUtility.DEFAULT_CURRENCY , currencyRate.getRate(), remainingDebtAmount ,currencyCode ,invoiceAmount, currencyCode);
						
					}
					
					insertIntoFCDailyTable(processDate,sequenceNumber, 
				               companyOID, dealerOID, masterOID,
				               currencyCode, remainingDebtAmount, remainingDebtAmountTRY , currencyRate);
		
		
		
					outBag.put(AMOUNT        , remainingDebtAmount);
					outBag.put(ACCOUNTNR     , dealerTRYAccount.getAccountNo());
					outBag.put(ACCOUNTNR2    , inBag.get(ACCOUNTNR));
					outBag.put(RATE          , currencyRate.getRate());	
					outBag.put(CURRENCYINPUT , 1);
		
					
				}else {
					
					baseAmount = CurrencyUtility.setBaseAmount(invoiceAmount, currencyCode);
										
					withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccount.getAccountNo(), invoiceAmount, baseAmount, currencyCode, explanationBag, productOpRefNo, usedLimit, false);
					
					
					depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccount.getAccountNo(),  
							  invoiceAmount, baseAmount, currencyCode,
							  explanationBag, productOpRefNo);
					
				}
				
			
				
			}
		
			outBag.put(TITLE         , dealerAccount.getTitle());
			outBag.put(INVOICENO   , inBag.get(INVOICENO));
			
		} catch (CBException ex) {
			
			errorFound = true;
			
			returnMessage = ex.getMessage();
			
		}
		
		finally{
			
			if (errorFound) {
				outBag.put(CODE1 , DebtRecordStatu.error.value());
			}
			else {
				outBag.put(CODE1 , DebtRecordStatu.done.value());
				
				if(inBag.existsBagKey(PARTIALFOUND) && inBag.get(PARTIALFOUND).toBoolean()){
					outBag.put(CODE1 , DebtRecordStatu.partialPayment.value());
				}
			}
			
			outBag.put(EXPLANATION , returnMessage);
			
	
			
		}
		
	 	return outBag;
	 	
	}

	private static void insertIntoFCDailyTable(CBDate headerDate, long sequenceNumber, 
			                                   long companyOID, long dealerOID, long masterOID, 
			                                   String currencyCode, CBCurrency remainingDebt,CBCurrency remainingDebtAmountTRY,
			                                   CurrencyRate currencyRate) throws CBException {
		
		DBSCompanyTotalFCDailyPOM      pom = DBSCompanyTotalFCDailyPOM.newDBSCompanyTotalFCDailyPOM();
		DBSCompanyTotalFCDailyPOMData data = DBSCompanyTotalFCDailyPOMData.newInstance();
		
		try {
			
			data.sequenceNo = sequenceNumber;
			data.processDate = headerDate;
			data.processTime = CBSystem.getInstance().getCurrentTime();
			
			data.companyOID = companyOID;
			data.dealerOID = dealerOID;
			data.masterOID = masterOID;
			
			
			data.currency  = currencyCode;
			data.amount = remainingDebt;
			data.tryAmount = remainingDebtAmountTRY;
			data.exchangeRate = currencyRate.getRate();
			
			pom.setDBSCompanyTotalFCDailyPOMData(data);
			pom.create();

			
		}
		finally {
			
			if (pom != null) {
				pom.close();
			}
			
		}
		
	}

	public static CurrencyRate getRemainingDebtAmount(CBDate headerDate, String currencyCode, CBCurrency remainingDebtUSD, String exchangeRate, String  exchangeRateType ) throws CBException {
		
		CBCurrencyInfo currencyCodeInfoUSD  = CBCurrencyInfo.getCurrencyInfo(currencyCode);
		CBCurrencyInfo  defaultCurrencyCode = CBCurrencyInfo.getDefaultCurrencyInfo();
		
		CBCurrency soldAmount   = CBCurrency.ZERO;
		CBCurrency boughtAmount = CBCurrency.ZERO;
		
		CBCurrencyInfo soldCurrency   = null;
		CBCurrencyInfo boughtCurrency = null;
		
		if(exchangeRateType.equals(ProcessExchangeRateCodeType.Sold.value())){
			soldAmount   = remainingDebtUSD ;
			soldCurrency = currencyCodeInfoUSD;
			
			boughtAmount   = CBCurrency.ZERO;
			boughtCurrency = defaultCurrencyCode;
			
		} else {
			boughtAmount   = remainingDebtUSD ;
			boughtCurrency = currencyCodeInfoUSD;
			
			soldAmount   = CBCurrency.ZERO;
			soldCurrency = defaultCurrencyCode;
		}
		
		CurrencyRate currencyRate= CurrencyUtility.getCurrencyRate( soldCurrency,  soldAmount,  boughtCurrency , boughtAmount, headerDate, exchangeRate);
		
		return currencyRate;
	}

	public static int isAccountBalanceNotEnough(Account dealerAccount, CBCurrency invoiceAmount, boolean useLimit) throws CBException {
		CBCurrency usableBalance = CBCurrency.ZERO;
		
		if(useLimit) {
			usableBalance = dealerAccount.getAvalBalance();
		}
		else {
			usableBalance = dealerAccount.getBalanceWithoutLimit();
		}
		
		return usableBalance.compare(invoiceAmount);
	}

	private static CBBag getAccountTrnxExplanationBag(CBBag inBag) throws CBException {
		
		CBBag explanationBag = CBBagFactory.createBag();
		
		explanationBag.put(INVOICENO, inBag.get(INVOICENO));
		explanationBag.put(ID, CompanyConstants.DBS_DEALER_DEBTFILE_ACCOUNT_TRNX_EXPLANATION);
		
		return explanationBag;
		
	}
	
	private static CBBag getAccountTrnxExplanationBagForPersonalDBS(CBBag inBag) throws CBException {
		CBBag explanationBag = CBBagFactory.createBag();
		
		explanationBag.put(DEALER_CODE, inBag.get(DEALER_CODE));
		explanationBag.put(TITLE, inBag.get(TITLE));
		explanationBag.put(DUEDATE, inBag.get(DUEDATE).toCBDate().toString("/"));
		explanationBag.put(ID, CompanyConstants.PERSONAL_DBS_DEALER_DEBTFILE_ACCOUNT_TRNX_EXPLANATION);
				
		return explanationBag;
	}
	
	//@serviceName: EXT_DBS_PROCESS_MASTER_RECORD
	public static CBBag processMasterFile(CBBag inBag) throws CBException {
		
		return doMasterFileAccounting(inBag);

	}	
	
	//@serviceName: EXT_DBS_PERSONAL_PROCESS_MASTER_RECORD
	public static CBBag personalDbsProcessMasterFile(CBBag inBag) throws CBException {
		
		return doPersoanalDbsMasterFileAccounting(inBag);

	}
	
    // @ServiceName : CB_EXT_DBS_GET_DEBT_FILE_TYPE
	public static CBBag getDBSDebtFileType (CBBag inBag) throws CBException {
        
    	CBBag outBag        = CBBagFactory.createBag();
    	CBQueryExecuter qe  = null ;
    	
        int responseIndex = 0;
 
        try {
        	  qe = new CBQueryExecuter("EXT_DBS_QR_GET_DEBT_FILE_TYPE");
          	qe.executeQuery();
      	    

      	    while (qe.next())
      	    {
      	            outBag.put(ASSET      , responseIndex , -1 , qe.getString("DEBTFILETYPE"));
      	            outBag.put(ASSET_NAME , responseIndex , -1 , qe.getString("FILECODE"));
      	            responseIndex++;
      	    }
      	      
      	    return  outBag;
		} finally{
			if(qe!=null){
				qe.close();
			}
		}
	      
  }
  
	
	public static void addCompanyProcessDebtExtension(CBBag inBag, long masterOID, long productOperationRefNo) throws CBException {

		CompanyProcessDebtExtension companyProcessDebtExtension = setCompanyProcessDebtExtension(inBag, masterOID, productOperationRefNo);
		companyProcessDebtExtension.add();

	}
	
	private static CompanyProcessDebtExtension setCompanyProcessDebtExtension(CBBag inBag, long masterOID ,long productOperationRefNo ) throws CBException {

		CompanyProcessDebtExtension companyProcessDebtExtension = new CompanyProcessDebtExtension();

		companyProcessDebtExtension.setMasterOID(masterOID);
		companyProcessDebtExtension.setPaymetAmount(inBag.get(AMOUNT).toCBCurrency());
		companyProcessDebtExtension.setProductOperationRefNo(productOperationRefNo);

		return companyProcessDebtExtension;
	}
	
	
	public static CBBag setOutputDebtDetailResult( CompanyDebtFileDetail debtFileDetail, CompanyDebtFileDetailException detailException  ) throws CBException {
        CBBag outBag = CBBagFactory.createBag();

        outBag.put(EXPLANATIONCODE , debtFileDetail.getProcessStatu());
        outBag.put(EXPLANATION     , debtFileDetail.getProcessExplanation());
        outBag.put(CODE            , detailException.getExceptionCode());
        outBag.put(DESCRIPTION     , detailException.getExceptionDescription());
        
        return outBag;
	}
	

	//@serviceName: EXT_DBS_DEBT_INVOICE_DETAIL_CHECK_FOR_IB
	public static CBBag debtInvoiceDetailCheck (CBBag inBag) throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();

		int  customerNumber = inBag.get(CUSTOMERNUMBER).toSimpleInt();
		
		String methodCode = "00";
		
		boolean errorFound =false;
		 
		Company company = new Company().getByCustomerNumber(customerNumber);
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader(company.getOID());
		
		debtFileHeader.setFileCurrency(inBag.get(CURRENCY).toString());
		
		
		inBag.put(COMPANYOID, company.getOID());
		
		CompanyDebtFileDetail debtFileDetail = LoadDBSDebtFileGeneric.parseDetailRow(inBag);
		
		CompanyDebtFileDetailExceptionParam detailExceptionPrm = new CompanyDebtFileDetailExceptionParam(company.getOID(), methodCode );
		List<String >                  detailExceptionCompany  = new CompanyDebtFileDetailExceptionParam(company.getOID()).getAllCompanyException();
		
		CompanyDebtFileDetailException  	detailException     = new CompanyDebtFileDetailException();
		
		errorFound = LoadDebtFileGeneric.checkInputs(debtFileHeader, debtFileDetail,  detailExceptionPrm ,detailExceptionCompany);
		
		if(!errorFound){
			errorFound = LoadDebtFileGeneric.checkMasterFile(debtFileHeader, debtFileDetail , detailExceptionPrm, detailExceptionCompany);
		}
		
		
		if (!errorFound) {
			debtFileDetail.setProcessStatu(DebtFileRecordStatu.done.value());
			debtFileDetail.setProcessExplanation("Kayýt Ýþlendi");
		}
		
		detailExceptionPrm = detailExceptionPrm.get();
		
		detailException.setExceptionCode(detailExceptionPrm.getExceptionCode());

		if(CBSessionInfo.isADK()){
			detailException.setExceptionDescription(detailExceptionPrm.getAdkExceptionDescription());
		}else {
			detailException.setExceptionDescription(detailExceptionPrm.getExceptionDescription());
		}
		
		outBag = setOutputDebtDetailResult(debtFileDetail, detailException );
		
		return outBag;

	}
	
	public static CBBag submitJob(CBBag inBag) throws CBException {

		CBBag prmBag = CBBagFactory.createBag();
		CBBag srvBag = CBBagFactory.createBag();
		CBBag outBag = CBBagFactory.createBag();

		srvBag.put(NAME, "EXTD7133");
		srvBag.put(ACIKLAMA, "DBS Process Debt");
		srvBag.put(ONCELIK, "50");

		CBTime startTime = inBag.get(TIME1).toCBTime();
		srvBag.put(BASLANGICSAATI, startTime.toString().substring(0, 4));
		
		prmBag.put(COMPANYOID, inBag.get(COMPANYOID));
		prmBag.put(LIMITUSED, inBag.get(LIMITUSED));
		prmBag.put(TIME1, inBag.get(TIME1));
		
		srvBag.put(BAG, prmBag);

		CBBatchSubmitter.getInstance().submitJob(srvBag);

		return outBag;

	}
	
	private static void saveProcessForeignCurrencyConvertionInfo(long masterOID, CBCurrency transactionAmount, String taransactionCurrencyCode,
			 													CBCurrency rate, CBCurrency sourceAmount, String sourceCurencyCode, CBCurrency invoiceAmount, String invoiceCurency) throws CBException {

		CompanyDebtFileProcessForeignCurConversion processForeignCurConversion  = new CompanyDebtFileProcessForeignCurConversion();
		
		CBBag inBag = CBBagFactory.createBag();
		
		inBag.put(TRANSACTIONAMOUNT , transactionAmount);
		inBag.put(EXCHANGECODE      , taransactionCurrencyCode);
		inBag.put(RATE              , rate);
		inBag.put(AMOUNT            , sourceAmount);
		inBag.put(EXCHANGECODE2     , sourceCurencyCode);
		inBag.put(INVOICEAMOUNT     , invoiceAmount);
		inBag.put(CURRENCY          , invoiceCurency);
		
		processForeignCurConversion = setProcessForeignCurConversionInfo(inBag,masterOID );
		processForeignCurConversion.add();
	}


	private static CompanyDebtFileProcessForeignCurConversion setProcessForeignCurConversionInfo(CBBag inBag, long masterOID) throws CBException {
	
		CompanyDebtFileProcessForeignCurConversion companyDebtFileProcessForeignCurConversion = new CompanyDebtFileProcessForeignCurConversion();
		
		companyDebtFileProcessForeignCurConversion.setMasterOID(masterOID);
		companyDebtFileProcessForeignCurConversion.setTransactionAmount(inBag.get(TRANSACTIONAMOUNT).toCBCurrency());
		companyDebtFileProcessForeignCurConversion.setTransactionCurrencyCode(inBag.get(EXCHANGECODE).toString());
		companyDebtFileProcessForeignCurConversion.setTransactionRate(inBag.get(RATE).toCBCurrency());
		companyDebtFileProcessForeignCurConversion.setSourceAmount(inBag.get(AMOUNT).toCBCurrency());
		companyDebtFileProcessForeignCurConversion.setSourceCurrencyCode(inBag.get(EXCHANGECODE2).toString());
		companyDebtFileProcessForeignCurConversion.setInvoiceAmount(inBag.get(INVOICEAMOUNT).toCBCurrency());
		companyDebtFileProcessForeignCurConversion.setInvoiceCurrencyCode(inBag.get(CURRENCY).toString());
		companyDebtFileProcessForeignCurConversion.setProcessDate(CBSystem.getInstance().getProcessDate());
		companyDebtFileProcessForeignCurConversion.setProcessTime(CBSystem.getInstance().getCurrentTime());
		
		
		return companyDebtFileProcessForeignCurConversion;
	
	}
	
}
