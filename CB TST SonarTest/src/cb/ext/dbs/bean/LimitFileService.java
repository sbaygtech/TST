package cb.ext.dbs.bean;

import cb.ext.dbs.data.CompanyLimitFileParam;
import cb.ext.dbs.data.CompanyReportLimitFile;
import cb.ext.dbs.util.FileUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBatchSubmitter;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBTime;

public class LimitFileService implements CBBagKeys {

	private static boolean isSendLimitFileEnable(long companyOID) throws CBException {

		CompanyLimitFileParam limitFileParam = new CompanyLimitFileParam();

		limitFileParam = limitFileParam.getCompanyLimitFileParam(companyOID);

		return limitFileParam.isDealerDefSentMail();

	}

	public static void sendLimitFile(long companyOID) throws CBException {

		if (isSendLimitFileEnable(companyOID)) {
			
			CBBag submitBag = CBBagFactory.createBag();
			
			submitBag.put(COMPANYOID, companyOID);
			
			submitJob(submitBag);
			
		}
	}

	public static CBBag submitJob(CBBag inBag) throws CBException {

		CBBag prmBag = CBBagFactory.createBag();
		CBBag srvBag = CBBagFactory.createBag();
		CBBag outBag = CBBagFactory.createBag();

		srvBag.put(NAME, "EXTD7135");
		srvBag.put(ACIKLAMA, "DBS Send Limit File");
		srvBag.put(ONCELIK, "50");

		if (inBag.existsBagKey(TIME1)) {

			CBTime startTime = inBag.get(TIME1).toCBTime();

			srvBag.put(BASLANGICSAATI, startTime.toString().substring(0, 4));
		}

		prmBag.put(COMPANYOID, inBag.get(COMPANYOID));

		srvBag.put(BAG, prmBag);

		CBBatchSubmitter.getInstance().submitJob(srvBag);

		return outBag;

	}
	
	public static String addReportLimitFile(CBBag inBag) throws CBException {

		CompanyReportLimitFile companyReportLimitFile = new CompanyReportLimitFile();

		String  sequenceNumber = FileUtility.getTFTSequenceNumber();
		
		for( int index=0 ; index<inBag.getSize(TABLE1);index ++){
			companyReportLimitFile.setReportSequenceNumber(sequenceNumber);
			companyReportLimitFile.setActive(inBag.get(TABLE1, index, ACTIVE).toBoolean());
			companyReportLimitFile.setCompanyTitle(inBag.get(TABLE1, index,COMPANYTITLE).toString());
			companyReportLimitFile.setDealerTitle(inBag.get(TABLE1, index,DEALER).toString());
			companyReportLimitFile.setCustomerNumber(inBag.get(TABLE1, index,CUSTOMERNUMBER).toSimpleInt());	
			companyReportLimitFile.setCompanyDealerCustomerCode(inBag.get(TABLE1, index,CODE1).toString());	
			companyReportLimitFile.setTlAccountNo(inBag.get(TABLE1, index,ACCOUNTNR2).toString());
			companyReportLimitFile.setUsdAccountNo(inBag.get(TABLE1, index,ACCOUNT_).toString());
			companyReportLimitFile.setCurrency(inBag.get(TABLE1, index,CURRENCY).toString());
			companyReportLimitFile.setLimit(inBag.get(TABLE1, index,AMOUNT).toCBCurrency());
			companyReportLimitFile.setAvailableLimit(inBag.get(TABLE1, index,AMOUNT1).toCBCurrency());
			companyReportLimitFile.setUnPaidInvoice(inBag.get(TABLE1, index,AMOUNT2).toCBCurrency());
			companyReportLimitFile.setAvailableDBS(inBag.get(TABLE1, index,AMOUNT3).toCBCurrency());
			
			sequenceNumber = companyReportLimitFile.add();
		}

		return  sequenceNumber;

	}

}
