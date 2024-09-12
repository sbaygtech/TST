package cb.ext.dbs.batch;

import static cb.ext.dbs.constants.DatabaseConstants.COLUMNNAME_LOADDEBTFILECLASS;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.dbsinterfaces.ILoadDebtFile;
import cb.ext.dbs.tosyali.data.CompanyDebtFileHeader;
import cb.ext.dbs.util.ClassUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBatchSubmitter;
import cb.smg.general.utility.CBException;

public class LoadDBSDebtFile extends CBBatchBase implements CBBagKeys {

	protected CBBag prepare(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();

		int customerNumber = getCustomerNumber(inBag.get(FT_TRANSFER_ID).toString());
		Company company = new Company().getByCustomerNumber(customerNumber);

		ILoadDebtFile iLoadDebtFile = (ILoadDebtFile) ClassUtility.getClass(company.getOID(), COLUMNNAME_LOADDEBTFILECLASS);
		outBag = iLoadDebtFile.prepare(inBag);

		outBag.put(TYPENAME, iLoadDebtFile.getClass().getName());

		return outBag;

	}

	private int getCustomerNumber(String ftmID) {

		int index = ftmID.lastIndexOf("_");

		return Integer.parseInt(ftmID.substring((index + 1)));
	}

	// @serviceName : EXT_DBS_LOAD_DEBT_FILE
	// bchTable : DBS_DEBTFILE
	// batchName : EXTD7132
	public static CBBag loadDBSDebtFile(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		ILoadDebtFile iLoadDebtFile = (ILoadDebtFile) ClassUtility.getClass(inBag.get(TYPENAME).toString());

		outBag = iLoadDebtFile.loadDebtFile(inBag);

		outBag.put(RC, true);

		return outBag;
	}

	protected CBBag finish(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		ILoadDebtFile iLoadDebtFile = (ILoadDebtFile) ClassUtility.getClass(inBag.get(TYPENAME).toString());

		outBag = iLoadDebtFile.finish(inBag);

		if (isReconDataSendable(inBag)) {
			iLoadDebtFile.sendReconData(inBag);
		}

		outBag.put(RC, true);

		return outBag;

	}

	public boolean isReconDataSendable(CBBag inBag) throws CBException {

		boolean sendable = false;
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());
		
		if (debtFileHeader != null) {

			CompanyDebtFileResultParam debtFileRsltPrm = new CompanyDebtFileResultParam().getCompanyDebtFileResultParam(debtFileHeader.getCompanyOID());

			sendable =  debtFileRsltPrm.isDebtFileResultParamSendable();
			
		}
		
		return sendable;

	}

	// @serviceName : EXT_DBS_LOAD_DEBT_FILE_SUBMIT
	public static CBBag submitJob(CBBag inBag) throws CBException {

		CBBag prmBag = CBBagFactory.createBag();
		CBBag srvBag = CBBagFactory.createBag();
		CBBag outBag = CBBagFactory.createBag();

		try {

			srvBag.put(NAME, "EXTD7132");
			srvBag.put(ACIKLAMA, " DBS Load and Process Company Debt File");
			srvBag.put(ONCELIK, "50");

			prmBag.put(FT_SEQUENCE_NUMBER, inBag.get(FT_SEQUENCE_NUMBER).toString());
			prmBag.put(FT_TABLE_NAME, inBag.get(FT_TABLE_NAME));
			prmBag.put(FT_FILE_NAME, inBag.get(FT_FILE_NAME));
			prmBag.put(FT_TRANSFER_ID, inBag.get(FT_TRANSFER_ID));

			srvBag.put(BAG, prmBag);

			CBBatchSubmitter.getInstance().submitJob(srvBag);

			return outBag;

		} catch (CBException CBe) {
			throw CBe;
		}
	}

}