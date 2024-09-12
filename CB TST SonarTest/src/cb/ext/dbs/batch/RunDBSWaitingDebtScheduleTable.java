package cb.ext.dbs.batch;

import cb.ext.dbs.bean.DealerService;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBTime;

public class RunDBSWaitingDebtScheduleTable extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBBag submitBag = CBBagFactory.createBag();

		long companyOID = 0L;
		int day = 0;
		int sendSms = 0;
		int sendMail = 0;
		CBTime processTime = null;
		CBQueryExecuter qe = null;

		try {
			qe = new CBQueryExecuter("EXT_DBS_GET_DAILY_WAITING_DEBT_SCHEDULE");
			qe.executeQuery();

			while (qe.next()) {
				companyOID = qe.getLong("COMPANYOID");
				day = qe.getInt("DAYCOUNT");
				processTime = qe.getCBTime("BATCHTIME");
				
				sendMail = qe.getInt("SENDMAILTODEALER");
				sendSms = qe.getInt("SENDSMSTODEALER");
				
				submitBag.put(COMPANYOID, companyOID);
				submitBag.put(DAY, day);
				submitBag.put(BATCHPROCESSDATE, processTime);
				submitBag.put(SMSDELIVERY, sendSms);
				submitBag.put(EMAILDELIVERY, sendMail);
				
				DealerService.submitJob(submitBag);
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

	// @serviceName : EXT_DBS_WAITING_DEBT_LIST_SCHEDULE
	// batchName    : EXTD7200 
	public static CBBag runDailyDBSWaitingDebtSendMailScheduleTable(CBBag inBag) throws CBException {
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
