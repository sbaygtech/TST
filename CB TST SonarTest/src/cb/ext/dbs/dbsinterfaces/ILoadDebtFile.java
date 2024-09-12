package cb.ext.dbs.dbsinterfaces;

import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBException;

public interface ILoadDebtFile extends IDBS{
	
	CBBag prepare(CBBag inBag) throws CBException;
	CBBag loadDebtFile(CBBag inBag) throws CBException;
	CBBag finish(CBBag inBag) throws CBException;
	void sendReconData(CBBag inBag) throws CBException;

}
