package cb.ext.dbs.bean;

import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBException;

public class MethodCallerService implements CBBagKeys {
	
	private static CBBag callProductOperationService(CBBag inBag, String productCode, String productOpCode) throws CBException {
		CBBag paymentBag  = CBBagFactory.createBag();
	
		paymentBag.put(URUNKODU, productCode);
		paymentBag.put(URUNISLEMKODU, productOpCode); 
		
		inBag.copyTo(paymentBag);
	
		return  CBCaller.call("PRD_PRODUCT_OPERATION_START", paymentBag);
	}	

	//@serviceName : P_EXT_DBS_ADD_COMPANY
	public static CBBag companyAddMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "003"); 
		
	}	
	
	//@serviceName : P_EXT_DBS_UPDATE_COMPANY
	public static CBBag companyUpdateMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "004"); 
		
	
	}	
	
	//@serviceName : P_EXT_DBS_DELETE_COMPANY
	public static CBBag companyDeleteMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "005"); 
		
	
	}	
	
	//@serviceName : P_EXT_DBS_ADD_DEALER
	public static CBBag dealerAddMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "006");  
		
	
	}	
	
	//@serviceName : P_EXT_DBS_UPDATE_DEALER
	public static CBBag dealerUpdateMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "007"); 
		
	
	}	
	//@serviceName : P_EXT_DBS_DELETE_DEALER
	public static CBBag dealerDeleteMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "008"); 
	
	}	
	
	//@serviceName: EXT_DBS_PROCESS_MASTER_RECORD_MAIN
	public static CBBag processMasterFileMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "001"); 
		
	}
	
	//@serviceName : EXT_DBS_TRANSFER_DEALER_COMPANY_MAIN
	public static CBBag transferEODCompanyMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "002"); 
		
	
	}

	
	//serviceName : EXT_DBS_TRANSFER_FROM_KTH_MAIN
	public static CBBag transferKTHtoCurrentAccountMain(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "009"); 
		
	}	
	
	//@serviceName: EXT_DBS_MANUEL_CANCEL_DEBT_MAIN
	public static CBBag processCancelDebt(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "010"); 
		
	}
	
	//@serviceName: EXT_DBS_MANUEL_PAYMENT_DEBT_MAIN
	public static CBBag processPaymentDebt(CBBag inBag) throws CBException {
		
		return callProductOperationService(inBag, "910004", "011"); 
		
	}
	
	//@serviceName : EXT_PERSONAL_DBS_ADD_DEALER_MAIN
	public static CBBag personalDbsAddDealerMain(CBBag inBag) throws CBException {
		return callProductOperationService(inBag, "910004", "012");  
	}
	
	//@serviceName : EXT_PERSONAL_DBS_DELETE_DEALER_MAIN
	public static CBBag personalDbsDeleteDealerMain(CBBag inBag) throws CBException {
		return callProductOperationService(inBag, "910004", "013");  
	}
	
	//@serviceName : EXT_PERSONAL_DBS_CANCEL_PAYMENT_MAIN
	public static CBBag personalDBSCancelPaymentMain(CBBag inBag) throws CBException {
		return callProductOperationService(inBag, "910004", "014");  
	}
	
	//@serviceName : EXT_DBS_PERSONAL_PROCESS_MASTER_RECORD_MAIN
	public static CBBag personalDBSProcessMasterFileMain(CBBag inBag) throws CBException {
		return callProductOperationService(inBag, "910004", "015");  
	}
}
