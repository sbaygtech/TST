package cb.ext.dbs.util;

import static cb.ext.dbs.constants.DatabaseConstants.COLUMNNAME_COMPANYOID;
import static cb.ext.dbs.constants.DatabaseConstants.PRM_DBS_COMPANY_CLASSES;
import static cb.ext.dbs.util.GeneralUtility.getFromRefData;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.dbsinterfaces.IDBS;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class ClassUtility implements CBBagKeys {
	
	
	public static IDBS getClass(String className) throws CBException {
		
		return	(IDBS)getObject(className);
		
	}
	
	public static IDBS getClass(long companyOID, String returnColumn) throws CBException {
		
		return	(IDBS)getObject(companyOID, returnColumn);
		
	}
	
	private static Object getObject(long companyOID, String returnColumn) throws CBException {
		
		String className = getFromRefData(PRM_DBS_COMPANY_CLASSES, COLUMNNAME_COMPANYOID, companyOID, returnColumn);
		
		
		return getObject(className);
		
	}

	private static Object getObject(String className) throws CBException {
		
		try {
			
			Class<?> companyClass = Class.forName(className);
			Object companyInstance = companyClass.newInstance();
			return	companyInstance;
		
			
		} catch (Exception ex) {
			
			CBBag exBag =  CBBagFactory.createBag();
			exBag.put(CLASSNAME,className);
			exBag.put(MESSAGE,ex.getMessage());
			
			throw new CBException(DBSExceptions.CLASSNOT_FOUND, exBag);
			
		}
	}

}
