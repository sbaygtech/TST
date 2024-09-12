package cb.ext.dbs.util;

import cb.ext.dbs.data.Customer;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;

public class CustomerUtility implements CBBagKeys {

	public static String getCustomerPreferedMail(int customerNumber) throws CBException {

		CBBag mailBag = CBBagFactory.createBag();

		String email = "";
		

		mailBag.put(MUSTERIOID,  CustomerUtility.getCustomerInfo(customerNumber).getOid());
		mailBag = CBCaller.call("MUSTERI_INTERNETLERIGETIR", mailBag);

		int customerMailCount = mailBag.getSize(INTERNET);
		boolean found = false;

		for (int index = 0; ((index < customerMailCount) && (!found)); index++) {

			if (mailBag.get(INTERNET, index, PREFEREDADDRESS).toSimpleInt() == 1) {

				email = mailBag.get(INTERNET, index, ADDRESS).toString();
				found = true;
			}

		}

		return email;
	}
	
	public static Customer getCustomerInfo(String customerNumber) throws CBException {
		
		CBQueryExecuter qe = null;

		Customer customer = new Customer();

		try {

			qe = new CBQueryExecuter("EXT_DBS_GET_CUSTOMER_INFO");
			qe.setParameter("CUSTOMERNUMBER", customerNumber);

			qe.executeQuery();

			if (qe.next()) {

				customer.setTitle(qe.getString("TITLE"));
				customer.setOrganization(qe.getString("ORGANIZATION"));
				customer.setOid(qe.getLong("OID"));
				customer.setVkn(qe.getString("TAXNO"));

			}

			return customer;

		} finally {

			if (qe != null) {
				qe.close();
			}
		}
	}

	public static Customer getCustomerInfo(int customerNumber) throws CBException {

		return getCustomerInfo(String.valueOf(customerNumber));

	}
	
	public static String getCustomerMobilePhone(int customerNumber) throws CBException {
		StringBuffer telefon = new StringBuffer();
		
		CBBag telephoneBag = CBBagFactory.createBag();
		telephoneBag.put(CBBagKeys.MUSTERIOID, CustomerUtility.getCustomerInfo(customerNumber).getOid());
        telephoneBag.put(CBBagKeys.TELEFONTIPI, "MP");
        
        CBCaller.call("MUSTERI_TELEFONLARITIPINEGOREGETIR", telephoneBag).copyTo(telephoneBag);
        
        if (telephoneBag.getSize(CBBagKeys.TELEPHONE) > 0) {
              telefon.append(telephoneBag.get(CBBagKeys.TELEPHONE, 0, CBBagKeys.FIELDCODE));
              telefon.append(telephoneBag.get(CBBagKeys.TELEPHONE, 0, CBBagKeys.TELEPHONENO));
              
        }
        
       return telefon.toString();
	}

}
