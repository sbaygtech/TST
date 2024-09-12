package cb.ext.dbs.util;

import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class ExceptionUtility {

	public static void throwExceptionNoParam(int exceptionNo) throws CBException {

		throwException(exceptionNo, null, null);

	}

	public static void throwException(int exceptionNo, CBBag inBag, int... bagIndexes) throws CBException {
		
		CBBag exBag = CBBagFactory.createBag();

		if (inBag != null) {
			
			for (int bagIndex : bagIndexes) {
				exBag.put(bagIndex, inBag.get(bagIndex));
			}
			
		}

		throw new CBException(exceptionNo, exBag);
		

	}
	
}
