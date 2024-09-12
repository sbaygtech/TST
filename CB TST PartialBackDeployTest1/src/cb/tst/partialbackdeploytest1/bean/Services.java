package cb.tst.partialbackdeploytest1.bean;

import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSMGExceptions;

public class Services implements CBBagKeys {

	/**
	 * @servicename URN_PARTIALBACKDEPLOYTEST1_LIST_ALL_TABLES
	 * @param inBag
	 * @return
	 * @throws CBException
	 */
	public static CBBag listAllTables(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		CBQueryExecuter queryExecuter = null;
		try {
			queryExecuter = new CBQueryExecuter("QE_URN_PARTIALBACKDEPLOYTEST1_LIST_ALL_TABLES");
			if (inBag.existsBagKey(OWNER)) {
				queryExecuter.setParameter("OWNER", inBag.get(OWNER).toString());
			}
			queryExecuter.executeQuery();
			for (int i = 0; queryExecuter.next(); i++) {
				outBag.put(TABLE, i, OWNER, queryExecuter.getString("OWNER"));
				outBag.put(TABLE, i, TABLENAME, queryExecuter.getString("TABLE_NAME"));
				if (i == 110) {
					break;
				}
			}
		} catch (CBException e) {
			throw e;
		} catch (Exception e) {
			CBBag eBag = CBBagFactory.createBag();
			eBag.put(NOTE, e.toString() + "Services.listAllTables");
			throw new CBException(CBSMGExceptions.SYSTEM_ERROR, eBag);
		} finally {
			if (queryExecuter != null) {
				queryExecuter.close();
			}
		}
		return outBag;
	}
}
