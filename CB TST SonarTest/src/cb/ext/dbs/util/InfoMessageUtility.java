package cb.ext.dbs.util;

import static cb.smg.bagkeys.CBBagKeys.INFO_MESSAGE_ID;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class InfoMessageUtility {
	
	public static CBBag putInfoMessage(int infoMessageID) throws CBException {

		return putInfoMessage(infoMessageID, null, null);

	}

	public static CBBag putInfoMessage(int infoMessageID, CBBag inBag, int...bagIndexes) throws CBException {
		
		CBBag infoBag = CBBagFactory.createBag();
		
		if (inBag!=null) {

			for (int bagIndex : bagIndexes) {
				infoBag.put(bagIndex, inBag.get(bagIndex));
			}
			
		}
		
		infoBag.put(INFO_MESSAGE_ID, infoMessageID);
		
		return infoBag;

	}

}
