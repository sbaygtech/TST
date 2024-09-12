package cb.ext.dbs.bean;

import cb.ext.dbs.util.CurrencyUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBDateFormatter;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;

public class ExternalServices implements CBBagKeys {

	// @serviceName : EXT_DBS_GET_DEALER_PRODUCTREFNO_COMISSION
	public static CBBag getDealerProductRefNo(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		try {

			long productRefNo = -1L;

			qe = new CBQueryExecuter("EXT_DBS_GET_PRODUCTREF_COMMISSION");
			qe.setParameter("CUSTOMEROID", inBag.get(CUSTOMEROID));
			qe.executeQuery();

			if (qe.next()) {
				productRefNo = qe.getLong("PRODUCTREFNO");
			}

			outBag.put(PRODUCTREFNO , productRefNo);
			outBag.put(MATURITYDATE , CBDateFactory.newCBDate("20991231"));

			return outBag;

		} finally {
			if (qe != null) {
				qe.close();
			}
		}

	}

	// @serviceName : EXT_DBS_GET_DEALER_ACCOUNT_INF_IB
	public static CBBag getDealerInfoForIB(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		CBQueryExecuter qe = null;

		int index = 0;

		try {

			qe = new CBQueryExecuter("EXT_DBS_GET_DEALER_ACCOUNT_DETAIL_IB");

			if (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {

				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
			qe.executeQuery();

			while (qe.next()) {

				outBag.put(TABLE1, index, ACCOUNTNR, qe.getString("ACCOUNTNO"));
				outBag.put(TABLE1, index, OID, qe.getLong("DEALEROID"));
				outBag.put(TABLE1, index, TITLE, qe.getString("TITLE"));

				index++;

			}

			return outBag;

		} finally {
			if (qe != null) {
				qe.close();
			}
		}

	}
	
	
	//@serviceName : EXT_DBS_COMPANY_DEALER_DETAIL_FOR_IB
	public static CBBag getCompanyDealerInfoForIB (CBBag inBag) throws CBException{
		
		CBBag outBag 	 = CBBagFactory.createBag();
		
		CBBag accountDetailBag = CBBagFactory.createBag();
		
		CBQueryExecuter qe = null;
		
		long companyOID = 0L;
		long dealerOID = 0L;
		
		String companyDealerCusCode = "";
		
		int index = 0;
		
		boolean screenMode = true;
		
		try {

			qe = new CBQueryExecuter("EXT_QR_DBS_COMPANY_DEALER_INFO_FOR_IB");

			if (inBag.existsBagKey(COMPANYOID)&&!inBag.get(COMPANYOID).toString().equals("")) {
				qe.setParameter("COMPANYOID" , inBag.get(COMPANYOID).toSimpleLong());
			}
			
			if (inBag.existsBagKey(BRANCHCODE) && !inBag.get(BRANCHCODE).toString().equals("")) {
				qe.setParameter("ORGANIZATION", inBag.get(BRANCHCODE).toString());
			}
			
			if (inBag.existsBagKey(CUSTOMERNUMBER) && !inBag.get(CUSTOMERNUMBER).toString().equals("")) {
				qe.setParameter("CUSTOMERNUMBER", inBag.get(CUSTOMERNUMBER).toString());
			}
			
			qe.executeQuery();

			
			while (qe.next()) {
				
				companyOID = qe.getLong("COMPANYOID");
				dealerOID = qe.getLong("OID");
				
				companyDealerCusCode = qe.getString("COMPANYDEALERCUSCODE");
				
				accountDetailBag.put(COMPANYOID, companyOID);
				accountDetailBag.put(OID  ,  dealerOID);
				accountDetailBag.put(CODE1,  companyDealerCusCode);
				
				
				try {
					
					accountDetailBag = DBSObservationService.getDealerAccountDetail(accountDetailBag, screenMode);
					
					outBag.put(TABLE1, index, ACCOUNTNR2, accountDetailBag.get(ACCOUNTNR2));
					outBag.put(TABLE1, index, ACCOUNT_, accountDetailBag.get(ACCOUNT_));
					outBag.put(TABLE1, index, AMOUNT, accountDetailBag.get(AMOUNT));
					outBag.put(TABLE1, index, AMOUNT1, accountDetailBag.get(AMOUNT1));
					outBag.put(TABLE1, index, AMOUNT2, accountDetailBag.get(AMOUNT2));
					outBag.put(TABLE1, index, AMOUNT3, accountDetailBag.get(AMOUNT3));
				
				}
				catch (CBException ex){
					
					outBag.put(TABLE1, index, ACCOUNTNR2, "");
					outBag.put(TABLE1, index, ACCOUNT_, "");
					outBag.put(TABLE1, index, AMOUNT, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT1, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT2, CBCurrency.ZERO);
					outBag.put(TABLE1, index, AMOUNT3, CBCurrency.ZERO);
					outBag.put(TABLE1, index, DEALER, "");
				}
				
				
				
				outBag.put(TABLE1, index, OID, dealerOID);
				outBag.put(TABLE1, index, ACTIVE, qe.getString("ACTIVE"));
				outBag.put(TABLE1, index, CUSTOMERNUMBER, qe.getString("CUSTOMERNUMBER"));
				outBag.put(TABLE1, index, CODE1, companyDealerCusCode);
				outBag.put(TABLE1, index, COMPANYTITLE, qe.getString("TITLE"));
				outBag.put(TABLE1, index, CURRENCY, CurrencyUtility.DEFAULT_CURRENCY);
				outBag.put(TABLE1, index, DEALER, qe.getString("TITLE"));
				outBag.put(TABLE1, index, TITLE2     , qe.getString("COMPANYTITLE"));
				outBag.put(TABLE1, index, CUSTOMERNR , qe.getString("COMPANYCUSTNO"));
				
				
				index++;
				
			}


			return outBag;
		}
		finally{
			
			if (qe!=null) {
				qe.close();
			}
			
		}

	}

}
