package cb.ext.dbs.util;

import cb.ext.dbs.data.CurrencyRate;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;

public class CurrencyUtility implements CBBagKeys{
	
	public final static String DEFAULT_CURRENCY ;
	
	static {
		DEFAULT_CURRENCY = CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode();
	}
	
	public static boolean isCurrencyTRY(String dealerAcccountCurrency) {
		
		
		return isCurrencyEqual(dealerAcccountCurrency, DEFAULT_CURRENCY);
	}
	
	public static boolean isCurrencyEqual(String dealerAcccountCurrency, String defaultCurrency) {
		return dealerAcccountCurrency.equals(defaultCurrency);
	}
	
	public static  String  convertCurrencytoStr(CBCurrency amount) {
		
		return amount.toFormatedString(2).replaceAll(",",""); 
		
	}
	
	public static  String  convertCurrencytoStr_EraseCommas(CBCurrency amount) {
		
		return amount.toFormatedString(2).replaceAll(",",""); 
		
	}

    public static CBCurrency getBaseAmount (CBCurrencyInfo boughtCurrency, CBCurrency boughtAmount) throws CBException {
        

   	 String soldCurrencyCode = CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode();        

        if (boughtCurrency.isDefaultCurrency())
              return boughtAmount;

        CBBag rateBag = CBBagFactory.createBag();
        
        rateBag.put(BANKTYPE, "R");
        rateBag.put(BOUGHTCURRENCYUNIT, boughtCurrency.getCurrencyCode());
        rateBag.put(BOUGHTAMOUNT, boughtAmount);
        rateBag.put(SOLDFOREIGNCURRENCY, soldCurrencyCode);
        rateBag.put(SOLDAMOUNT, CBCurrency.ZERO);
        rateBag = CBCaller.call("TRSY_CALCULATE_BRANCHRATEOPERATION", rateBag);

        return rateBag.get(SOLDAMOUNT).toCBCurrency().round(boughtCurrency);
    }

	public static CurrencyRate getCurrencyRate(CBCurrencyInfo soldCurrency, CBCurrency soldAmount, CBCurrencyInfo boughtCurrency ,CBCurrency boughtAmount,CBDate headerDate, String exchangeRate) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		
		CBBag rateBag = CBBagFactory.createBag();


		rateBag.put(BANKTYPE            , exchangeRate);
		rateBag.put(BOUGHTCURRENCYUNIT  , boughtCurrency.getCurrencyCode());
		rateBag.put(BOUGHTAMOUNT        , boughtAmount);
		rateBag.put(SOLDFOREIGNCURRENCY , soldCurrency.getCurrencyCode());
		rateBag.put(SOLDAMOUNT          , soldAmount);
		rateBag = CBCaller.call("RATES_CALCULATE_FOREIGN_EXCHANGE", rateBag);
		
		return new CurrencyRate(soldCurrency,  rateBag.get(SOLDAMOUNT).toCBCurrency(), 
				                rateBag.get(BOUGHTAMOUNT).toCBCurrency(), 
				                rateBag.get(EXCHANGERATE).toCBCurrency());
		
		

	}
	
	
	public static CBCurrency setBaseAmount(CBCurrency invoiceAmount, String currencyCode) throws CBException {
		
		CBCurrency baseAmount = invoiceAmount;
		
		if (CurrencyUtility.isCurrencyTRY(currencyCode)) {
		
			CBCurrencyInfo curInfo =  CBCurrencyInfo.getCurrencyInfo(CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode());
			baseAmount = CurrencyUtility.getBaseAmount(curInfo, invoiceAmount);
			
		}
		
		return baseAmount;
		
	}

}
