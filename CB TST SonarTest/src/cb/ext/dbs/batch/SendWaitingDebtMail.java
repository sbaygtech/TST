package cb.ext.dbs.batch;

import java.util.List;

import cb.ext.dbs.bean.DebtFileMasterService;
import cb.ext.dbs.constants.GeneralConstants;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.tosyali.LoadDebtFile;
import cb.ext.dbs.util.EMailUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.batch.CBBatchBase;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBBundles;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBMultiLang;
import cb.smg.general.utility.CBSystem;

public class SendWaitingDebtMail extends CBBatchBase implements CBBagKeys {
	
	protected CBBag prepare(CBBag inBag) throws CBException {
		CBBag outBag   = CBBagFactory.createBag();
		
		List<CompanyDebtFileResultParam> companyDebtFileResultParamList =  new CompanyDebtFileResultParam().getCompanyDebtFileResultToSendWaitingDebtList();
		
		for(CompanyDebtFileResultParam companyDebtFileResultParam : companyDebtFileResultParamList){
			Company company = new Company().get(companyDebtFileResultParam.getCompanyOID());
			CBBag mailBag = CBBagFactory.createBag();
			CBDate processDate = CBSystem.getInstance().getProcessDate();
			
			DebtFileMasterService.getDealerWaitingDebtList(company, null, null).copyTo(mailBag);
			String fileName = GeneralConstants.FILE_NAME + CBSystem.getInstance().getProcessDate();
			
			CBBag docBag = EMailUtility.callGenerateDocument(GeneralConstants.DOCUMENT_EXPLANATION, fileName, mailBag);
			String documentId = docBag.get(DOCUMENTID).toString();
						
			CBBag paramBag = CBBagFactory.createBag();
			paramBag.put(COMPANYCODE, company.getCompanyInternalCode());
			paramBag.put(COMPANYTITLE, company.getCompanyName());
			paramBag.put(DUEDATE, processDate.toString("/"));
			
			String message = CBBundles.getInstance().getString("RB_WAITING_DEBT_MAIL_TO_COMPANY_BODY", CBMultiLang.getDefaultLanguage().getLocale(),paramBag);
			message = message.replace("<br>", "\n");
			String subject = CBBundles.getInstance().getString("RB_WAITING_DEBT_MAIL_TO_COMPANY_SUBJECT", CBMultiLang.getDefaultLanguage().getLocale(),paramBag);
			
			String ccMailAddress = CBSystem.getInstance().getSystemParameter("DBS_WAITING_DEBT_MAIL").toString();
			String toMailAddress = LoadDebtFile.combineToMailList(company.getOID());
			
			EMailUtility.sendMailCCAttachment(message, subject, toMailAddress, documentId, ccMailAddress);
		}
		
		outBag.put(RC, true);
		return outBag;
		
	}
	
	// @serviceName : EXT_DBS_SEND_WAITING_DEBT_LIST_TO_COMPANY
	// batchName    : EXTD7198
	public static CBBag sendWaitingDebtListToCompany(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RC, true);
		
		return outBag;
	}

	protected CBBag finish(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();
		outBag.put(RC, true);

		return outBag;
	}
}
