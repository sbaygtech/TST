package cb.ext.dbs.batch;

import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.generic.SendLimitFileGeneric;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class SendLimitFile extends CBBatchBase implements CBBagKeys {

	// @serviceName : EXT_DBS_SEND_LIMIT_FILE
	// bchTable     : BCH.DBS_LIMIT_FILE
	// batchName    : EXTD7135
	public static CBBag sendLimitFile(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(RC, true);

		return outBag;
	}

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag   = CBBagFactory.createBag();

		long companyID = inBag.get(COMPANYOID).toSimpleLong();

		Company company = new Company().get(companyID);

		CompanyLimitFileParam  companyLimitFileParam = CompanyLimitFileParam.getCompanyLimitFileParam(companyID);
	

		if(companyLimitFileParam.isMailingEnabled()){
			
			SendLimitFileGeneric.sendMailLimitFile (inBag , company);
			
		}
		
		outBag.put(RC, true);
		return outBag;
	}

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag = SendLimitFileGeneric.sendLimitFileGeneric(inBag);
		
		
		outBag.put(RC, true);
		return outBag;

	}


}
