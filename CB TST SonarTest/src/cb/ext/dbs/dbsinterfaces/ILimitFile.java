package cb.ext.dbs.dbsinterfaces;

import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBException;

public interface ILimitFile  extends IDBS{
	
	CBBag sendLimitFile(CBBag inBag) throws CBException;

}
