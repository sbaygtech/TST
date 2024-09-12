package cb.ext.dbs.bean;

import static cb.ext.dbs.util.AccountUtility.depositeMoney;
import static cb.ext.dbs.util.AccountUtility.withdrawMoney;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.util.CurrencyUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.bagkeys.CBBagKeysMFSE;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;

public class TransferEODCompanyService implements CBBagKeys {
	
	//@serviceName : EXT_DBS_TRANSFER_DEALER_COMPANY
	public static CBBag transferEODCompany(CBBag inBag) throws CBException {
		
		return transferEODCompanyAccounting(inBag);
		
	}
	
	private static CBBag transferEODCompanyAccounting(CBBag inBag)	throws CBException {


		CBBag outBag  =  CBBagFactory.createBag();
		
		
		
		boolean errorFound = false;
		String  returnMessage = "";

		try {
			
			long  productOpRefNo    = CBSessionInfo.getCurrentUrunIslemRefNo();
		
			String currencyCode = inBag.get(CURRENCY).toString();			
			CBCurrency amount    = inBag.get(TOTALAMOUNT).toCBCurrency();

			String dealerAccountNo = inBag.get(ACCOUNTNR).toString();			
			String companyAccountNo = inBag.get(ACCOUNTNR2).toString();			
			
			
			CBCurrency baseAmount = CurrencyUtility.setBaseAmount(amount, currencyCode);
			
			
			CBBag explanationBag = getAccountTrnxExplanationBag();
			
			withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, dealerAccountNo, 
						  amount, baseAmount, currencyCode, 
					      explanationBag, productOpRefNo, true, false);
			
			depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, companyAccountNo,  
						  amount, baseAmount, currencyCode,
					      explanationBag, productOpRefNo);
		 	
		}
	 	
	 	catch (CBException ex) {
	 		
	 		returnMessage = ex.getMessage();
	 		errorFound = true;
			
	 	}
	 	finally{
			
			outBag.put(CODE1, errorFound);
			outBag.put(EXPLANATION, returnMessage);
			
		}
	 	
	 	return outBag;
	
	}

	private static CBBag getAccountTrnxExplanationBag() {
		CBBag explanationBag = CBBagFactory.createBag();
		explanationBag.put(ID, CompanyConstants.DBS_TRANSFER_DEALTER_TO_COMPANY_TRNX_EXPLANATION);
		return explanationBag;
	}

}
