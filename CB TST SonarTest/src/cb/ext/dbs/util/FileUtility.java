package cb.ext.dbs.util;

import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBOIDFactory;
import cb.smg.general.utility.CBSessionInfo;

public class FileUtility implements CBBagKeys {
	
    public static String getTFTSequenceNumber() throws CBException {
        CBBag oBag = CBBagFactory.createBag();
        oBag.put(CBBagKeys.UID, CBSessionInfo.getKullaniciKodu());
        oBag = CBCaller.call("FT_SEQUENCE_NUMBER_GETIR", oBag);
        return oBag.get(CBBagKeys.SEQUENCE_NUMBER).toString();
    }
	
	public static void exportFile(String outSequenceNumber, String tftTableName, String ftmFileName, String ftmTransferID) throws CBException {

		CBBag fileBag = CBBagFactory.createBag();

		fileBag.put(CTID, CBOIDFactory.getOID());
		fileBag.put(FCID, "000");
		fileBag.put(TCID, "021");
		fileBag.put(UID, tftTableName);
		fileBag.put(FT_FILE_NAME, ftmFileName);
		fileBag.put(FT_SEQUENCE_NUMBER, outSequenceNumber);
		fileBag.put(FT_TABLE_NAME, tftTableName);
		fileBag.put(FT_TRANSFER_ID, ftmTransferID);

		CBCaller.call("FT_EXPORT_FILE", fileBag);
	}

}
