package cb.ext.dbs.util;

import cb.ext.dbs.data.Account;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.DealerAccount;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;

public class AccountUtility implements CBBagKeys {
	
	public static CBBag withdrawMoney(int templateName, String accountNo, CBCurrency amount, CBCurrency baseAmount, String currencyCode, CBBag explanationBag, long productOpRefNo, boolean usedLimit, boolean createCredit) throws CBException {

		CBBag accBag = CBBagFactory.createBag();

		accBag.put(TEMPLATENAME1, templateName);
		accBag.put(ACCOUNTNR, accountNo);
		accBag.put(SUM, amount);
		accBag.put(BASEAMOUNT, baseAmount);
		accBag.put(PRODUCTOPERATIONREFNO, productOpRefNo);
		accBag.put(VALUEEXCHANGERATECODE, currencyCode);
		accBag.put(EXPLANATIONCODE, explanationBag);
		accBag.put(NOTEXPIRED, 1);
		
		if(!usedLimit) {
			accBag.put(KMHUSECONTROL, usedLimit);
		}

		if(createCredit) {
			accBag.put(CREDITREFTYPE, "TZ");
		}
		
		return CBCaller.call("HESAP_PARA_CEK", accBag);
	}

	public static CBBag depositeMoney(int templateName, String accountNo, CBCurrency amount, CBCurrency baseAmount, String currencyCode, CBBag explanationBag, long productOpRefNo) throws CBException {

		CBBag accBag = CBBagFactory.createBag();

		accBag.put(SABLONADI, templateName);
		accBag.put(PRODUCTOPERATIONREFNO, productOpRefNo);
		accBag.put(HESAPNO, accountNo);
		accBag.put(SUBEKODU, CBSessionInfo.getIslemAnaBirimKodu());
		accBag.put(TUTAR, amount);
		accBag.put(BASETUTAR, baseAmount);
		accBag.put(KIYMETDOVIZKODU, currencyCode);
		accBag.put(ACIKLAMAKODU, explanationBag);
		accBag.put(ISLEMVALORTARIHI, CBSystem.getInstance().getProcessDate());

		return CBCaller.call("HESAP_PARA_YATIR", accBag);

	}

	public static Account getAccountInfo(String accountNo) throws CBException {

		CBBag accountBag = CBBagFactory.createBag();

		accountBag.put(ACCOUNTNR, accountNo);

		accountBag = CBCaller.call("HESAP_HESAPBILGILERIGETIR", accountBag);

		return new Account(accountNo,
				   accountBag.get(CUSTOMERTITLE).toString(), 
				   "", 
				   accountBag.get(BRANCHCODE).toString(), 
				   accountBag.get(LIMITTUTARI).toCBCurrency(),
				   accountBag.get(KULLANILABILIRBAKIYE).toCBCurrency(),
				   accountBag.get(LIMITSIZKULLANILABILIRBAKIYE).toCBCurrency());
		

	}

	public static String getAccountBranchCode(String accountNo) throws CBException {

		return getAccountInfo(accountNo).getBranchCode();

	}

	public static CBCurrency getUsedAmountFromKMH(Company company, DealerAccount dealerAccount, Account account) throws CBException {

		CBCurrency dailyLimitExtraCalcAmt = AccountUtility.getAccountKMHExtraInfo(dealerAccount.getAccountNo(), company.getCompanyLimitGapType());

		return account.getLimitAmount().subtract(dailyLimitExtraCalcAmt);
	}

	public static CBCurrency getAccountKMHExtraInfo(String accountNo, int companyLimitGapType) throws CBException {

		CBBag callBag = CBBagFactory.createBag();

		CBCurrency dailyLimitExtraCalculationAmount = CBCurrency.ZERO;

		callBag.put(CONTROL, companyLimitGapType);
		callBag.put(HESAPNO, accountNo);
		callBag = CBCaller.call("KMH_HESAP_LIMITBOSLUK_GETIR", callBag);

		if (callBag.existsBagKey(LIMITUSTU)) {
			dailyLimitExtraCalculationAmount = callBag.get(LIMITUSTU).toCBCurrency();
		}

		return dailyLimitExtraCalculationAmount;

	}

	public static Account getCurrentAccountInfo(String accountNo) throws CBException {

		CBBag accountBag = CBBagFactory.createBag();

		accountBag.put(ACCOUNTNR, accountNo);

		accountBag = CBCaller.call("VADESIZHESAP_HESAPGETIR", accountBag);

		return new Account(accountNo,
						   accountBag.get(CUSTOMERTITLE).toString(), 
						   accountBag.get(BRANCHNAME_).toString(), 
						   accountBag.get(BRANCHCODE).toString(), 
						   accountBag.get(LIMITTUTARI).toCBCurrency(),
						   accountBag.get(KULLANILABILIRBAKIYE).toCBCurrency(),
						   accountBag.get(LIMITLESSUSEABLEBALANCE).toCBCurrency());

	}

}
