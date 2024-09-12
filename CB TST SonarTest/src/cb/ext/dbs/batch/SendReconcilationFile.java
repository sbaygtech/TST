package cb.ext.dbs.batch;

import cb.ext.dbs.generic.SendDebtResultFileGeneric;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class SendReconcilationFile extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
	
		SendDebtResultFileGeneric.debtResultFileOutService(inBag);
	
		outBag.put(RC, true);
		return outBag;

	}

	// @serviceName : EXT_DBS_SEND_RECON_FILE
	// bchTable     : BCH.DBS_RECON_FILE
	// batchName    : EXTD7134

	public CBBag sendReconFile(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(RC, true);

		return outBag;

	}

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		outBag.put(RC, true);

		return outBag;

	}

}
