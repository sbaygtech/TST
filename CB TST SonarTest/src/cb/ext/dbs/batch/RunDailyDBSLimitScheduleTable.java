package cb.ext.dbs.batch;

import cb.ext.dbs.bean.LimitFileService;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBTime;

public class RunDailyDBSLimitScheduleTable extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		CBBag submitBag = CBBagFactory.createBag();

		long companyOID = 0L;
		CBTime processTime = null;

		CBQueryExecuter qe = null;

		try {

			qe = new CBQueryExecuter("EXT_DBS_GET_DAILY_LIMIT_SCHEDULE");
			qe.executeQuery();

			while (qe.next()) {

				companyOID = qe.getLong("COMPANYOID");

				processTime = qe.getCBTime("TIME");

				submitBag.put(COMPANYOID, companyOID);
				submitBag.put(TIME1, processTime);

				LimitFileService.submitJob(submitBag);

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

	// @serviceName : EXT_DBS_RUN_DAILY_LIMIT_SCHEDULE
	// bchTable     : BCH.DBS_DAILY_LIMIT_SCHEDULE
	// batchName    : EXTD7138
	public CBBag runDailyDBSLimitScheduleTable(CBBag inBag) throws CBException {

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
