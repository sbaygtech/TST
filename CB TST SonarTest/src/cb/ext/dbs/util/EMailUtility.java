package cb.ext.dbs.util;

import static cb.ext.dbs.util.GeneralUtility.getFromSystemParameter;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBException;

public class EMailUtility implements CBBagKeys {

		
	public static boolean isEmailValid(String email) {
		EmailFormatValidator validator = new EmailFormatValidator();
		
		return validator.validate(email);
	
	}
	
	
	public static String getEMailForNotification() throws CBException {
		return getFromSystemParameter("DBS_MAIL_LIST");

	}

	public static String getEMailForErrorMessages() throws CBException {
		return getFromSystemParameter("DBS_MAIL_LIST_HATA");

	}
	
	public static String getEMailForFCNotification() throws CBException {
		return getFromSystemParameter("DBS_DEBTFILE_NOTIFICATION");

	}

	public static CBBag sendMail(String message, String subject, String toMailAdress) throws CBException {
		return sendMailCCAttachment(message, subject, toMailAdress, "-1", "");
	}

	public static CBBag sendMailCCAttachment(String message, String subject, String toMailAdress, String attachedID, String ccMailAddress) throws CBException {

		CBBag bagData = CBBagFactory.createBag();
		CBBag mailBag = CBBagFactory.createBag();

		bagData.put(MESSAGE, message);

		if (!attachedID.equals("-1")) {
			mailBag.put(ATTACHED, attachedID);
		}

		mailBag.put(DOKUMANKODU, "0392");

		mailBag.put(DOCDESTINATION, toMailAdress);
		if (!ccMailAddress.equals("")) {

			mailBag.put(CC, ccMailAddress);
		}

		mailBag.put(DOCMEDIATYPE, "I");
		mailBag.put(ACIKLAMA, subject);
		mailBag.put(DOCFORMAT, "TXT");
		mailBag.put(CALLERSERVICENAME, EMailUtility.class.getName());
		mailBag.put(DOKUMANDATA, bagData);

		CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", mailBag);

		return retbag;
	}

	public static CBBag callGenerateDocument(String subject, String fileName, CBBag dataBag) throws CBException {
		CBBag docBag     = CBBagFactory.createBag();
		
		dataBag.put(TABLELIST, 0, TABLENAME, "TABLE");
        dataBag.put(TABLELIST, 0, TABLEID, "TABLE");
		
		docBag.put(CALLERSERVICENAME, EMailUtility.class.getName());
		docBag.put(DOCUMENTCODE, "4904");
		docBag.put(DOKUMANDATA, dataBag);
		docBag.put(DOCFORMAT, "XLSX");
		docBag.put(TEMPLATENAME, "RP_DBS_WAITING_DEBT_LIST.xlsx");
		docBag.put(FILENAME, fileName);
		
		CBBag retbag = CBCaller.call("URN_DOCMANAGER_GENERATEDOCUMENT", docBag);
		
		return retbag;
	}
}
