package cb.ext.dbs.bean;

import static cb.ext.dbs.util.AccountUtility.depositeMoney;
import static cb.ext.dbs.util.AccountUtility.withdrawMoney;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.pom.DBSDealerKTHTransferPOM;
import cb.ext.dbs.pom.DBSDealerKTHTransferPOMData;
import cb.ext.dbs.util.CurrencyUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.bagkeys.CBBagKeysMFSE;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;

public class KTHTransferService implements CBBagKeys {

	// @serviceName : EXT_DBS_TRANSFER_FROM_KTH
	public static CBBag transferKTHtoCurrentAccount(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		doTransferFromKTH(inBag);

		outBag.put(INFO_MESSAGE_ID, DBSExceptions.DEALER_KTH_TRANSFER_DONE);

		return outBag;

	}

	public static void doTransferFromKTH(CBBag inBag) throws CBException {

			long productOpRefNo = CBSessionInfo.getCurrentUrunIslemRefNo();
			
			String debitAccountNo = inBag.get(ACCOUNTNR).toString();
			String creditAccountNo = inBag.get(ACCOUNTNR2).toString();
			
			String currencyCode = inBag.get(CURRENCY).toString();
			
			CBCurrency amount = inBag.get(AMOUNT).toCBCurrency();

			CBCurrency baseAmount = CurrencyUtility.setBaseAmount(amount, currencyCode);

			
			CBBag explanationBag = getAccountTrnxExplanationBag();
			
			withdrawMoney(CBBagKeysMFSE.MFSE_DEBIT, debitAccountNo, 
					  amount, baseAmount, currencyCode, 
				      explanationBag, productOpRefNo, true, false);
		
			depositeMoney(CBBagKeysMFSE.MFSE_CREDIT, creditAccountNo,  
					  amount, baseAmount, currencyCode,
				      explanationBag, productOpRefNo);
			


			insertIntoDealerKTHTransferTable(productOpRefNo, currencyCode, amount, debitAccountNo, creditAccountNo);


	}

	private static void insertIntoDealerKTHTransferTable(long productOpRefNo,
														 String currencyCode, CBCurrency amount, 
														 String debitAccount, String creditAccount) throws CBException {
		
		DBSDealerKTHTransferPOM pom = DBSDealerKTHTransferPOM.newDBSDealerKTHTransferPOM();
		DBSDealerKTHTransferPOMData data = DBSDealerKTHTransferPOMData.newInstance();

		try {
			
			data.productOpRefNo = productOpRefNo;
			data.processDate = CBSystem.getInstance().getProcessDate();
			data.processTime = CBSystem.getInstance().getCurrentTime();
			data.transferAmount = amount;
			data.currency = currencyCode;
			data.debitAccount = debitAccount;
			data.creditAccount = creditAccount;

			pom.setDBSDealerKTHTransferPOMData(data);
			pom.create();
			
		} finally {

			if (pom != null) {
				pom.close();
			}

		}	
	}

	private static CBBag getAccountTrnxExplanationBag() {
		CBBag explanationBag = CBBagFactory.createBag();
		explanationBag.put(ID, CompanyConstants.DBS_DEALER_TRANSFER_FROM_KTH_TRNX);
		return explanationBag;
	}
}
