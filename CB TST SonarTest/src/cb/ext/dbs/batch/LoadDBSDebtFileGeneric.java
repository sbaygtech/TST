package cb.ext.dbs.batch;

import java.util.List;

import cb.ext.dbs.bean.DebtFileService;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DebtFileLocalCurrencyCodeType;
import cb.ext.dbs.constants.DebtFileRecordStatu;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileDetailExceptionParam;
import cb.ext.dbs.data.CompanyDebtFileRecordTypeParam;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.data.CompanyDebtMasterProcessExceptionParam;
import cb.ext.dbs.data.CompanyParameters;
import cb.ext.dbs.generic.LoadDebtFileGeneric;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileDetailException;
import cb.ext.dbs.generic.data.CompanyDebtFileDetailExtension;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.CompanyDebtFileTrailer;
import cb.ext.dbs.util.FileUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyFactory;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFactory;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSessionInfo;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class LoadDBSDebtFileGeneric  implements CBBagKeys  {

	
	//Service Name : CB_EXT_DBS_CONTROL_DEBT_FILE
	public static CBBag controlServiceDBSDebtFile(CBBag inBag) throws CBException {
		CBBag outBag   = CBBagFactory.createBag();
		CBBag paramBag = CBBagFactory.createBag();
		
		long sequenceNumber = 0L;
		int customerNumber  = -1;
		
		String fileName          = "";
		String strSequenceNumber = "";
		
		if(CBSessionInfo.isInternet()){
			
			strSequenceNumber = FileUtility.getTFTSequenceNumber();
			sequenceNumber = Long.valueOf(strSequenceNumber);
			customerNumber = inBag.get(CUSTOMERNUMBER).toSimpleInt();
			fileName       = "IB_"+ strSequenceNumber;
			
		}else {
			sequenceNumber = inBag.get(FT_SEQUENCE_NUMBER).toSimpleLong();
			customerNumber = getCustomerNumber(inBag.get(FT_TRANSFER_ID).toString());
			fileName       =  inBag.get(FT_FILE_NAME).toString();
		}
		
		CompanyDebtFileHeader debtFileHeader = preProcessHeaderTrailer(inBag, fileName , sequenceNumber , customerNumber);

		if (debtFileHeader != null) {
	
			paramBag.put(HEADER, debtFileHeader.toString());
		}
		else {
			paramBag.put(HEADER, "");
		}
		outBag.put(PARAMETERS, paramBag);
		outBag.put(RC, true);

		return outBag;


	}
	
	
	private static CompanyDebtFileHeader preProcessHeaderTrailer(CBBag inBag, String ftmFileName, long sequenceNumber, int customerNumber) throws CBException {

		
		CompanyDebtFileHeader debtFileHeader = null;

		Company company = new Company().getByCustomerNumber(customerNumber);
	
		//CompanyDebtFileTypeParam debtFileTypeParam = new CompanyDebtFileTypeParam().getCompanyDebtFileTypeParam(company.getOID());
		
		if(inBag.existsBagKey(FOOTER)) {
			debtFileHeader = processHeaderTrailer (inBag,ftmFileName ,sequenceNumber, company);
		}else {
			debtFileHeader = dummyProcessHeader(ftmFileName,  String.valueOf(sequenceNumber) ,  company);
		}
		return debtFileHeader;

		 
	}
	
	
	private static CompanyDebtFileHeader processHeaderTrailer(CBBag inBag, String ftmFileName, long sequenceNumber, Company company) throws CBException {

		
		CBBag headerBag   =  CBBagFactory.createBag();
		CBBag trailerBag  =  CBBagFactory.createBag();
		
		CompanyDebtFileHeader debtFileHeader = null;

		boolean headerFound = false;
		
		headerBag   =  inBag.get(HEADER).toBag();
		trailerBag  =  inBag.get(FOOTER).toBag();
		
		if ( headerBag.existsBagKey(RECORDSTATUS) && headerBag.get(RECORDSTATUS).toString().equals("H")) {

			headerFound = true;
			debtFileHeader = processHeader(headerBag , ftmFileName, String.valueOf(sequenceNumber), company);
			
			if(debtFileHeader!=null){
				processTrailer(debtFileHeader.getOid(), trailerBag);
			}

		}
		if (!headerFound) {
			 throwExceptionIfHeaderNotFound(inBag);
		}

		return debtFileHeader;
		
	}
	
	private static CompanyDebtFileHeader processHeader(CBBag headerBag, String fileName, String fileSequenceNumber, Company company) throws CBException {

		CBDate insertDate = CBSystem.getInstance().getProcessDate();
		CBTime insertTime = CBSystem.getInstance().getCurrentTime();

		String districtCode = "";
		String fileDate     = "";
				
		CompanyParameters params = new CompanyParameters();
		params.setCompanyOID(company.getOID());
		params.setKey("DATEFORMAT");
		
		String value = params.findCompanyParameterValue();
		
		if(value != null){
			if(value.equals("ddMMyyyy")){
				String dateStr = headerBag.get(DATE).toString();
				fileDate = dateStr.substring(4) + dateStr.substring(2, 4) + dateStr.substring(0, 2);
			}
		}
		else {
			fileDate = headerBag.get(DATE).toString();
		}
		
		String fileCurrency = null;
		
		if(headerBag.existsBagKey(DISTRICTCODE) ){
			districtCode = headerBag.get(DISTRICTCODE).toString().trim();
		}	
		
		if(headerBag.existsBagKey(CURRENCY) ){
			fileCurrency = headerBag.get(CURRENCY).toString();
		}
		
		CompanyDebtFileHeader debtFileHeader = null;

		if (!LoadDebtFileGeneric.fileAlreadyLoaded(fileName, company.getCompanyInternalCode())) {

			checkCompany(fileName, company);

			debtFileHeader = setCompanyDebtFileHeader(fileSequenceNumber, insertDate, insertTime, 
													  fileName, fileDate, company.getCompanyInternalCode(), fileCurrency, company.getOID(),districtCode);

			long oid = debtFileHeader.add();

			debtFileHeader.setOid(oid);

		}
		else {

			LoadDebtFileGeneric.prepareAndSendFileAlreadyLoadedMail(fileName, insertDate, fileDate, company);

		}

		return debtFileHeader;

	}
	
	
	private static CompanyDebtFileHeader dummyProcessHeader(String fileName, String fileSequenceNumber , Company company) throws CBException {

		CBDate insertDate = CBSystem.getInstance().getProcessDate();
		CBTime insertTime = CBSystem.getInstance().getCurrentTime();


		CBDate fileDate     = CBSystem.getInstance().getProcessDate();
		String fileCurrency = null;

		CompanyDebtFileHeader debtFileHeader = null;

		if (!LoadDebtFileGeneric.fileAlreadyLoaded(fileName, company.getCompanyInternalCode())) {

			checkCompany(fileName, company);

			debtFileHeader = setCompanyDebtFileDummyHeader(fileSequenceNumber, insertDate, insertTime, 
													  fileName, String.valueOf(fileDate.toDB()), company.getCompanyInternalCode(), fileCurrency, company.getOID());

			long oid = debtFileHeader.add();

			debtFileHeader.setOid(oid);

		}
		else {

			LoadDebtFileGeneric.prepareAndSendFileAlreadyLoadedMail(fileName, insertDate, String.valueOf(fileDate), company);

		}

		return debtFileHeader;

	}
	
	
	private static CompanyDebtFileHeader setCompanyDebtFileHeader(String fileSequenceNumber, 
		CBDate insertDate, CBTime insertTime, 
	    String fileName, String fileDate, 
	    String companyCode, String fileCurrency,	long companyOID , String districtCode) {

		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader();
		
		debtFileHeader.setInsertDate(insertDate);
		debtFileHeader.setInsertTime(insertTime);
		debtFileHeader.setFileName(fileName);
		debtFileHeader.setFileSequenceNumber(fileSequenceNumber);
		debtFileHeader.setFileDate(fileDate);
		debtFileHeader.setCompanyCode(companyCode);
		debtFileHeader.setCompanyOID(companyOID);
		debtFileHeader.setFileCurrency(fileCurrency);
		debtFileHeader.setDocumentID("-1");
		debtFileHeader.setMailID("-1");
		debtFileHeader.setDistrictCode(districtCode);
		
		return debtFileHeader;

	}
	
	private static CompanyDebtFileHeader setCompanyDebtFileDummyHeader(String fileSequenceNumber, 
			CBDate insertDate, CBTime insertTime, 
		    String fileName, String fileDate, 
		    String companyCode, String fileCurrency,	long companyOID) {

			CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader();
			
			debtFileHeader.setInsertDate(insertDate);
			debtFileHeader.setInsertTime(insertTime);
			debtFileHeader.setFileName(fileName);
			debtFileHeader.setFileSequenceNumber(fileSequenceNumber);
			debtFileHeader.setFileDate(fileDate);
			debtFileHeader.setCompanyCode(companyCode);
			debtFileHeader.setCompanyOID(companyOID);
			debtFileHeader.setFileCurrency(fileCurrency);
			debtFileHeader.setDocumentID("-1");
			debtFileHeader.setMailID("-1");
			
			return debtFileHeader;

		}
	
	
	private static void checkCompany(String fileName, Company company) throws CBException {

		boolean errorFound = false;
		String errorSpecificMessage = "";

		if (company == null) {
			errorFound = true;
			errorSpecificMessage = "  bulunamadý!  ";
		}
		else if (!company.isActive()) {
			errorFound = true;
			errorSpecificMessage = "  aktif degil.!   ";
		}

		if (errorFound) {

			CBBag exBag = CBBagFactory.createBag();
			String message = "Kurum Kodu : " + company.getCompanyInternalCode() + errorSpecificMessage + "Dosya Adý: " + fileName;
			exBag.put(NOTE, message);

			throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);

		}

	}
	
	
	private static void throwExceptionIfHeaderNotFound(CBBag inBag) throws CBException {

		String message = inBag.get(FT_FILE_NAME).toString() + "dosyasinda Header) bulunamadý !! seq.no : " + inBag.get(FT_SEQUENCE_NUMBER).toString();

		CBBag exBag = CBBagFactory.createBag();

		exBag.put(NOTE, message);

		throw new CBException(DBSExceptions.DBS_GENERAL_ERROR, exBag);

	}
	
	private static void processTrailer(long headerOID, CBBag trailerBag) throws CBException {

		
		CBCurrency invoiceTotalAmountNew    = CBCurrency.ZERO;
		CBCurrency invoiceTotalAmountCancel = CBCurrency.ZERO;
		CBCurrency invoiceTotalAmountUpdate = CBCurrency.ZERO;
		int invoiceTotalCountCancel         = 0;
		int invoiceTotalCountUpdate         = 0;
		int invoiceTotalCountNew            = Integer.parseInt(trailerBag.get(TOTALCOUNT).toString());
		
		if(trailerBag.existsBagKey(TOTALAMOUNT)){
			invoiceTotalAmountNew    = CBCurrency.valueOf(trailerBag.get(TOTALAMOUNT).toString());
		}
		if(trailerBag.existsBagKey(CANCELNUMBER)){
			invoiceTotalCountCancel         = Integer.parseInt(trailerBag.get(CANCELNUMBER).toString());
		}
		if(trailerBag.existsBagKey(CANCELLATIONPREMIUMAMOUNT)){
			invoiceTotalAmountCancel = CBCurrency.valueOf(trailerBag.get(CANCELLATIONPREMIUMAMOUNT).toString());
		}
		if(trailerBag.existsBagKey(UPDATENO)){
			invoiceTotalCountUpdate         = Integer.parseInt(trailerBag.get(UPDATENO).toString());
		}
		if(trailerBag.existsBagKey(TOTALAMOUNT1)){
			  invoiceTotalAmountUpdate = CBCurrency.valueOf(trailerBag.get(TOTALAMOUNT1).toString());
		}
	 
		CompanyDebtFileTrailer debtFileTrailer = new CompanyDebtFileTrailer(headerOID, 
														invoiceTotalCountNew, invoiceTotalAmountNew, 
														invoiceTotalCountCancel, invoiceTotalAmountCancel,
														invoiceTotalCountUpdate, invoiceTotalAmountUpdate);

		debtFileTrailer.add();

	}
	
	//Service Name : CB_EXT_DBS_LOAD_DEBT_FILE
	public static CBBag loadServiceDBSDebtFile(CBBag inBag) throws CBException {
		CBBag outBag = CBBagFactory.createBag();

		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());

		if(debtFileHeader!=null){
			outBag = processDetail(inBag , debtFileHeader);

		}
		return outBag;


	}
	
	public static CBBag processDetail(CBBag detailBag, CompanyDebtFileHeader debtFileHeader) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		String methodCode = "00";
		
		detailBag.put(COMPANYOID, debtFileHeader.getCompanyOID());
		
		CompanyDebtFileDetail          debtFileDetail           = parseDetailRow(detailBag);
		CompanyDebtFileDetailExtension debtFileDetailExtension  = parseDetailExtension(detailBag);
		
		CompanyDebtFileDetailExceptionParam detailExceptionPrm  = new CompanyDebtFileDetailExceptionParam(debtFileHeader.getCompanyOID() , methodCode);
		List<String >                  detailExceptionCompany   = new CompanyDebtFileDetailExceptionParam(debtFileHeader.getCompanyOID()).getAllCompanyException();
		CompanyDebtFileDetailException  	detailException     = new CompanyDebtFileDetailException();
	
		CompanyDebtMasterProcessExceptionParam masterExceptionPrm  = new CompanyDebtMasterProcessExceptionParam(debtFileHeader.getCompanyOID() , methodCode);
		List<String >                      masterExceptionCompany  = new CompanyDebtMasterProcessExceptionParam(debtFileHeader.getCompanyOID()).getAllCompanyException();

		boolean errorFound = LoadDebtFileGeneric.checkInputs(debtFileHeader, debtFileDetail,  detailExceptionPrm, detailExceptionCompany );

		if (!errorFound) {

			errorFound = LoadDebtFileGeneric.checkMasterFile(debtFileHeader, debtFileDetail , detailExceptionPrm, detailExceptionCompany);

			if (!errorFound) {

				errorFound = LoadDebtFileGeneric.affectMasterFile(debtFileHeader, debtFileDetail,masterExceptionPrm,  masterExceptionCompany);
				
				if (!errorFound) {
					debtFileDetail.setProcessStatu(DebtFileRecordStatu.done.value());
					debtFileDetail.setProcessExplanation("Kayýt Ýþlendi");
				}

			}

		}
		
		if (errorFound) {
			debtFileDetail.setProcessStatu(DebtFileRecordStatu.error.value());
			debtFileDetail.setProcessExplanation("Kayýt Hata Aldý");
		}
		

		detailExceptionPrm = detailExceptionPrm.get();
		detailException.setExceptionCode(detailExceptionPrm.getExceptionCode());

		if(CBSessionInfo.isADK()){
			detailException.setExceptionDescription(detailExceptionPrm.getAdkExceptionDescription());
		}else {
			detailException.setExceptionDescription(detailExceptionPrm.getExceptionDescription());
		}
		
		
		debtFileDetail.setSpecialExceptionCode(detailExceptionPrm.getExceptionCode());
		
		debtFileDetail.add(debtFileHeader , debtFileDetailExtension , detailException );
		
		
		outBag =DebtFileService.setOutputDebtDetailResult(debtFileDetail, detailException) ;
		return outBag;

	}
	
	public static CompanyDebtFileDetail parseDetailRow(CBBag detailBag) throws CBException {

		Company company = new Company().get(detailBag.get(COMPANYOID).toSimpleLong());
		
		String customerTitle = detailBag.get(CUSTOMERTITLE).toString();
		
		
		String strRecordType = "";
		
		CompanyDebtFileDetail         debtFileDetail           = new CompanyDebtFileDetail();
		
		debtFileDetail.setCustomerNo(detailBag.get(SPECIALCODE).toString());
		
		if(detailBag.get(CUSTOMERTITLE).toString().length()>100){
			customerTitle = customerTitle.substring(0,100);
		}
		
		debtFileDetail.setCustomerTitle(customerTitle);
		
		CompanyParameters params = new CompanyParameters();
		params.setCompanyOID(detailBag.get(COMPANYOID).toSimpleLong());
		params.setKey("DATEFORMAT");
		String value = params.findCompanyParameterValue();
		if(value != null){
			if(value.equals("ddMMyyyy")){
				String dateStr = detailBag.get(DUEDATE).toString();
				CBDate dueDate = CBDateFactory.newCBDate(
						Integer.valueOf(dateStr.substring(4)),
						Integer.valueOf(dateStr.substring(2, 4)),
						Integer.valueOf(dateStr.substring(0, 2)));
				debtFileDetail.setDueDate(dueDate);
			}
		}
		else {
			debtFileDetail.setDueDate(detailBag.get(DUEDATE).toCBDate());
		}
		
		debtFileDetail.setInvoiceNo(detailBag.get(INVOICENO).toString());
		debtFileDetail.setInvoiceAmount(CBCurrencyFactory.newCBCurrency(detailBag.get(AMOUNT).toString().trim().replace(",", ".")));
		
		if(detailBag.existsBagKey(CURRENCY) && detailBag.get(CURRENCY) != null){
			
			if( detailBag.get(CURRENCY).toString().equals(DebtFileLocalCurrencyCodeType.TL.toString()) &&  company.getLocalCurrencyCodeType().equals(DebtFileLocalCurrencyCodeType.TL.value())){
				
				debtFileDetail.setCurrency(DebtFileLocalCurrencyCodeType.TRY.toString());
			}else {
				
				debtFileDetail.setCurrency(detailBag.get(CURRENCY).toString());
			}
			
		}
		else {
			debtFileDetail.setCurrency(CBCurrencyInfo.getDefaultCurrencyInfo().getCurrencyCode());
		}
		
		if(detailBag.existsBagKey(REFERANSNO)) {
			debtFileDetail.setInvoiceRefNo(detailBag.get(REFERANSNO).toString());
		}
		
		if(detailBag.existsBagKey(TYPECODE)) {
			debtFileDetail.setInvoiceType(detailBag.get(TYPECODE).toString());
		}
		else {
			debtFileDetail.setInvoiceType("N");
		}
		
		CompanyDebtFileRecordTypeParam recordType = new CompanyDebtFileRecordTypeParam (detailBag.get(COMPANYOID).toSimpleLong(),detailBag.get(TYPE).toString().trim());
		
		strRecordType = recordType.getRecordType();
	
		debtFileDetail.setRecordType(strRecordType);
		
		return debtFileDetail;

	}


	public static CompanyDebtFileDetailExtension parseDetailExtension(CBBag detailBag)throws CBException {
		
		CompanyDebtFileDetailExtension debtFileDetailExtension = new CompanyDebtFileDetailExtension();
		
		if(detailBag.existsBagKey(DATE1)){
			debtFileDetailExtension.setInvoiceDate(detailBag.get(DATE1).toCBDate());
		}
		
		if(detailBag.existsBagKey(INTERESTAMOUNT)){
			debtFileDetailExtension.setDiscountInterestAmount(detailBag.get(INTERESTAMOUNT).toCBCurrency());
		}
		
		if(detailBag.existsBagKey(COLLECTIONAMOUNT)){
			debtFileDetailExtension.setCollectingAmount(detailBag.get(COLLECTIONAMOUNT).toCBCurrency());
		}
		
		if(detailBag.existsBagKey(EXCHANGECODE)){
			debtFileDetailExtension.setCollectingCurrencyCode(detailBag.get(EXCHANGECODE).toString());
		}
		if(detailBag.existsBagKey(INTERESTCOLLECTION)){
			debtFileDetailExtension.setDiscountIntrstCollectingAmount(detailBag.get(INTERESTCOLLECTION).toCBCurrency());
		}
		if(detailBag.existsBagKey(EXCHANGERATE)){
			debtFileDetailExtension.setCollectingExchangeRate(detailBag.get(EXCHANGERATE).toCBCurrency());
		}
		if(detailBag.existsBagKey(INVOICENUMBER)){
			debtFileDetailExtension.setInvoiceNumber(detailBag.get(INVOICENUMBER).toString());
		}
		if(detailBag.existsBagKey(TRANSACTIONDATE)){
			debtFileDetailExtension.setTransactionDate(detailBag.get(TRANSACTIONDATE).toCBDate());
		}
		if(detailBag.existsBagKey(PROVISIONNUMBER)){
			debtFileDetailExtension.setProvisionRefNo(detailBag.get(PROVISIONNUMBER).toString());
		}
		if(detailBag.existsBagKey(PARAMETER)){
			debtFileDetailExtension.setParameter(detailBag.get(PARAMETER).toString());
		}
		
		if(detailBag.existsBagKey(DATATYPE)){
			debtFileDetailExtension.setInvoiceType(detailBag.get(DATATYPE).toString());
		}
		
		if(detailBag.existsBagKey(CURRENCY)){
			
			debtFileDetailExtension.setDebtCurrencyCode(detailBag.get(CURRENCY).toString());
		}
		
		return debtFileDetailExtension;
		
	}
	
	
	
	
	//Service Name : CB_EXT_DBS_FINISH_DEBT_FILE
	public static CBBag finishServiceDBSDebtFile(CBBag inBag) throws CBException {
		
		CBBag outBag = CBBagFactory.createBag();
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(PARAMETERS).toBag().get(HEADER).toString());

		if (debtFileHeader != null) {

			LoadDebtFileGeneric.prepareAndSendMailNotify(debtFileHeader);

			debtFileHeader.updateForMailDocID();

		}
		

		if (isReconDataSendable(inBag)) {
			sendReconData(inBag);
		}

		outBag.put(RC, true);

		return outBag;

	}

	public static void sendReconData(CBBag inBag) throws CBException {
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(PARAMETERS).toBag().get(HEADER).toString());
		
		LoadDebtFileGeneric.submitReconDataJob(debtFileHeader);
		
		
	}
	
	
	public static boolean isReconDataSendable(CBBag inBag) throws CBException {

		boolean sendable = false;
		
		CompanyDebtFileHeader debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(PARAMETERS).toBag().get(HEADER).toString());
		
		if (debtFileHeader != null) {

			CompanyDebtFileResultParam debtFileRsltPrm = new CompanyDebtFileResultParam().getCompanyDebtFileResultParam(debtFileHeader.getCompanyOID());

			sendable =  debtFileRsltPrm.isDebtFileResultParamSendable();
			
		}
		
		return sendable;

	}

	private static int getCustomerNumber(String ftmID) {

		int index = ftmID.lastIndexOf("_");

		return Integer.parseInt(ftmID.substring((index + 1)));
	}
	
}
