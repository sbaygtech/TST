package cb.ext.dbs.batch;

import cb.ext.dbs.bean.DebtFileService;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBTime;

public class RunProcessDBSDebtScheduleTable extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag submitBag = CBBagFactory.createBag();

		long companyOID = 0L;
		CBTime processTime = null;
		int useLimit = 0;

		CBQueryExecuter qe = null;

		try {

			qe = new CBQueryExecuter("EXT_DBS_GET_PROCESS_DEBT_SCHEDULE");
			qe.executeQuery();

			while (qe.next()) {

				companyOID = qe.getLong("COMPANYOID");

				processTime = qe.getCBTime("TIME");
				useLimit = qe.getInt("USELIMIT");

				submitBag.put(COMPANYOID, companyOID);
				submitBag.put(TIME1, processTime);
				if(useLimit == 1) {
					submitBag.put(LIMITUSED, true);
				}
				else {
					submitBag.put(LIMITUSED, false);
				}
			
				DebtFileService.submitJob(submitBag);

			}

			outBag.put(RC, true);

			return outBag;

		}

		finally {
			if (qe != null) {
				qe.close();
			}
		}
	}

	// @serviceName : EXT_DBS_PROCESS_DEBT_SCHEDULE
	// bchTable     : BCH.DBS_PROCESS_DEBT_SCHEDULE
	// batchName    : EXTD7206
	public CBBag runDBSProcessDebtScheduleTable(CBBag inBag) throws CBException {

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
