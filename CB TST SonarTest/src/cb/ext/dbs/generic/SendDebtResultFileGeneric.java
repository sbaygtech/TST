package cb.ext.dbs.generic;

import static cb.ext.dbs.util.GeneralUtility.paddingString;
import cb.esi.fltr.util.bean.FileParser;
import cb.ext.dbs.constants.CompanyConstants;
import cb.ext.dbs.constants.DatabaseConstants;
import cb.ext.dbs.constants.DebtFileLocalCurrencyCodeType;
import cb.ext.dbs.constants.DebtResultFileDetailType;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyDebtFileResultParam;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;

public class SendDebtResultFileGeneric  implements CBBagKeys{

	public static CBBag debtResultFileOutService(CBBag inBag) throws CBException {
		
		CBBag outBag     = CBBagFactory.createBag();
		CBBag headerBag  = CBBagFactory.createBag();
		CBBag detailBag  = CBBagFactory.createBag();
		CBBag trailerBag = CBBagFactory.createBag();
		
		String ftmFileName = "";
		String fileSeqNumber  = "";
		
		CompanyDebtFileHeader debtFileHeader = setDebtFileHeader(inBag);
		
		CBDate fileDate = setFileDate(inBag);
		
		Company  company = new Company().get(debtFileHeader.getCompanyOID());

		CompanyDebtFileResultParam debtFileRsltPrm = new CompanyDebtFileResultParam().getCompanyDebtFileResultParam(debtFileHeader.getCompanyOID());
		
		FileParser parser = FileParser.getParser(DatabaseConstants.DEBT_RESULT_OUT_FILE + company.getCustomerNumber());
		String sequenceNumber = parser.openFile(DatabaseConstants.TFT_DBS_RECON_FILE);


		if(debtFileRsltPrm.getTextFileType().equals(DebtResultFileDetailType.waiting.value())){
	
		    fileSeqNumber = GeneralUtility.generateNo(CompanyConstants.DBS_COMPANY_RECONFILE_CODE + "_" + 
	                company.getCustomerNumber() + "_" + 
	                debtFileHeader.getFileDate()
	               );
			
			headerBag  = constructHeaderWaitingData(debtFileHeader);
			parser.writeHeader(headerBag);
			
			detailBag  = constructDetailWaitingData(parser,debtFileHeader.getCompanyOID());
			
			trailerBag = constructTrailerWaitingData(detailBag);
			parser.writeFooter(trailerBag);
		
			
		}else if(debtFileRsltPrm.getTextFileType().equals(DebtResultFileDetailType.file.value())){
			
			headerBag  = constructHeaderFileData(debtFileHeader);
			parser.writeHeader(headerBag);
			
			detailBag  = constructDetailFileData(parser,debtFileHeader.getOid(), company.getLocalCurrencyCodeType());
			
			trailerBag = constructTrailerFileData(detailBag);
			parser.writeFooter(trailerBag);
			
			
			
		}
	
		ftmFileName = GeneralUtility.genericFileName( debtFileRsltPrm.getTextFileName() ,fileDate, CBSystem.getInstance().getCurrentTime(),sequenceNumber, fileSeqNumber, "") +".txt";
		
		parser.exportFile(sequenceNumber, ftmFileName, DatabaseConstants.DEBT_RESULT_OUT_FILE+ + company.getCustomerNumber(), DatabaseConstants.TFT_DBS_RECON_FILE);

		
		return  outBag;

	}	
	
	
	private static CompanyDebtFileHeader setDebtFileHeader(CBBag inBag) throws CBException {

		CompanyDebtFileHeader debtFileHeader = null;
		if (inBag.existsBagKey(HEADER)) {
			debtFileHeader = new CompanyDebtFileHeader().fromString(inBag.get(HEADER).toString());
		}
		else {
			debtFileHeader = CompanyDebtFileHeader.get(inBag.get(OID).toSimpleLong());
		}

		return debtFileHeader;
	}
	
	private static CBBag constructHeaderWaitingData(CompanyDebtFileHeader debtFileHeader) {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RECORDSTATUS , DatabaseConstants.FILE_HEADER_INDICATOR);
		outBag.put(DATE         , debtFileHeader.getInsertDate().toDBString());
		outBag.put(COMPANYCODE  , paddingString(debtFileHeader.getCompanyCode(), 4, ' ', true));
		
		return outBag;
	}
	
	private static CBBag constructTrailerWaitingData(CBBag inBag) throws  CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RECORDSTATUS , DatabaseConstants.FILE_TRAILER_INDICATOR);
		outBag.put(TOTALCOUNT   , paddingString(String.valueOf(inBag.get(TOTALCOUNT).toSimpleInt()), 7, '0', true));
		outBag.put(TOTALAMOUNT  , paddingString(inBag.get(TOTALAMOUNT).toString(), 15, '0', true));
		
		return outBag;
	}
	
	private static CBBag constructHeaderFileData(CompanyDebtFileHeader debtFileHeader) {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RECORDSTATUS , DatabaseConstants.FILE_HEADER_INDICATOR);
		outBag.put(COMPANYCODE  , paddingString(debtFileHeader.getCompanyCode(), 9, ' ', true));
		outBag.put(DISTRICTCODE , debtFileHeader.getDistrictCode());
		outBag.put(DATE         , debtFileHeader.getInsertDate().toDBString());
		outBag.put(BANKCODE		, Constants.FB_BANK_CODE);
		outBag.put(BANKNAME		, Constants.FB_BANK_NAME);
		
		return outBag;
	}
	
	private static CBBag constructTrailerFileData(CBBag inBag) throws CBException {

		CBBag outBag = CBBagFactory.createBag();
		
		outBag.put(RECORDSTATUS , DatabaseConstants.FILE_TRAILER_INDICATOR);
		outBag.put(TOTALCOUNT   , paddingString(String.valueOf(inBag.get(TOTALCOUNT).toSimpleInt()), 7, '0', true));
		
		return outBag;
	}


	private static CBBag constructDetailWaitingData(FileParser parser ,long companyOID) throws CBException {

		CBQueryExecuter qe = null;
		CBBag  detailBag  = CBBagFactory.createBag();
		CBBag  trailerBag = CBBagFactory.createBag();

		try {

			int recordCount = 0;

			CBCurrency invoiceAmount = CBCurrency.ZERO;
			CBCurrency totalAmount   = CBCurrency.ZERO;

			qe = new CBQueryExecuter("EXT_DBS_GET_MASTER_DETAIL");
			qe.setParameter("COMPANYID", companyOID);

			qe.executeQuery();

			while (qe.next()) {

				invoiceAmount = qe.getCBCurrency("INVOICEAMOUNT");
				
				detailBag.put(RECORDSTATUS  , DatabaseConstants.FILE_DETAIL_INDICATOR);
				detailBag.put(SPECIALCODE   , paddingString(qe.getString("CUSTOMERNO"), 16, ' ', true));
				detailBag.put(CUSTOMERTITLE , paddingString(qe.getString("CUSTOMERTITLE"), 30, ' ', false));
				detailBag.put(INVOICENO     , paddingString(qe.getString("INVOICENO"), 15, ' ', false));
				detailBag.put(AMOUNT        , paddingString(invoiceAmount.toString(), 15, '0', true));
				detailBag.put(CURRENCY      , qe.getString("CURRENCY"));

				totalAmount = totalAmount.add(invoiceAmount);

				recordCount++;

				
				parser.writeDetail(detailBag);
			
				detailBag.clear();
			}
			
			trailerBag.put(TOTALCOUNT  , recordCount);
			trailerBag.put(TOTALAMOUNT , totalAmount);
			
			return trailerBag;

		} finally {

			if (qe != null) {
				qe.close();
			}

		}

	}

	
	private static CBBag constructDetailFileData(FileParser parser ,long headerOID , String currencyCodeType) throws CBException {

		CBQueryExecuter qe = null;
		CBBag  detailBag  = CBBagFactory.createBag();
		CBBag  trailerBag = CBBagFactory.createBag();

		try {

			int recordCount = 0;

			CBCurrency invoiceAmount = CBCurrency.ZERO;
			CBCurrency totalAmount   = CBCurrency.ZERO;

			qe = new CBQueryExecuter("EXT_DBS_GET_FILE_RESULT_DETAIL");
			qe.setParameter("HEADEROID", headerOID);

			qe.executeQuery();

			while (qe.next()) {

				invoiceAmount = qe.getCBCurrency("INVOICEAMOUNT");
				
				detailBag.put(RECORDSTATUS       , DatabaseConstants.FILE_DETAIL_INDICATOR);
				detailBag.put(SPECIALCODE        , paddingString(qe.getString("CUSTOMERNO"), 25, ' ', false));
				detailBag.put(CUSTOMERTITLE      , paddingString(qe.getString("CUSTOMERTITLE"), 30, ' ', false));
				detailBag.put(INVOICENO          , paddingString(qe.getString("INVOICENO"), 16, ' ', false));
				
				if(qe.getCBDate("INVOICEDATE")!=null){
					detailBag.put(DATE1              , paddingString(qe.getCBDate("INVOICEDATE").toDBString(), 8, ' ', false));
				}else {
					detailBag.put(DATE1              , paddingString("", 8, ' ', false));
				}
				
				if(qe.getCBCurrency("DISCOUNTINTERESTAMOUNT")!= null){
					detailBag.put(INTERESTAMOUNT     , paddingString( qe.getCBCurrency("DISCOUNTINTERESTAMOUNT").setScale(2).toString(), 18, '0', true));
				}else {
					detailBag.put(INTERESTAMOUNT     , paddingString( CBCurrency.ZERO.setScale(2).toString(), 18, '0', true));
				}
			
				
				if(qe.getCBCurrency("COLLECTINGAMOUNT")!= null){
					detailBag.put(COLLECTIONAMOUNT   , paddingString( qe.getCBCurrency("COLLECTINGAMOUNT").setScale(2).toString(), 18, '0', true));
				}else {
					detailBag.put(COLLECTIONAMOUNT   , paddingString( CBCurrency.ZERO.setScale(2).toString(), 18, '0', true));
				}
				
				if(qe.getString("COLLECTINGCURRENCYCODE")!= null){
					detailBag.put(EXCHANGECODE       , qe.getString("COLLECTINGCURRENCYCODE").toString());
				}else {
					detailBag.put(EXCHANGECODE       , "");
				}
				
				if(qe.getCBCurrency("DISCOUNTINTRSTCOLLAMOUNT") != null){
					detailBag.put(INTERESTCOLLECTION , paddingString( qe.getCBCurrency("DISCOUNTINTRSTCOLLAMOUNT").setScale(2).toString(), 18, '0', true));
				}else {
					detailBag.put(INTERESTCOLLECTION , paddingString( CBCurrency.ZERO.setScale(2).toString(), 18, '0', true));
				}
			
				if(qe.getCBCurrency("COLLECTINGEXCHANGERATE") != null){
					detailBag.put(EXCHANGERATE       , paddingString( qe.getCBCurrency("COLLECTINGEXCHANGERATE").setScale(2).toString(), 18, '0', true));
				}else {
					detailBag.put(EXCHANGERATE       , paddingString( CBCurrency.ZERO.setScale(2).toString(), 18, '0', true));
				}
				
				if(qe.getString("INVOICENUMBER") != null){
					detailBag.put(INVOICENUMBER      , paddingString(qe.getString("INVOICENUMBER"), 16, ' ', false));
				}else {
					detailBag.put(INVOICENUMBER      , paddingString("", 16, ' ', false));
				}
				
				if(qe.getCBDate("TRANSACTIONDATE") != null){
					detailBag.put(TRANSACTIONDATE    , paddingString(qe.getCBDate("TRANSACTIONDATE").toDBString(), 8, ' ', false));
				}else {
					detailBag.put(TRANSACTIONDATE    , paddingString("", 8, ' ', false));
				}
				
				if(qe.getString("PROVISIONREFNO") != null){
					detailBag.put(PROVISIONNUMBER    , paddingString(qe.getString("PROVISIONREFNO"), 20, ' ', false));
				}else {
					detailBag.put(PROVISIONNUMBER    , paddingString("", 20, ' ', false));
				}
				

				if(qe.getString("PARAMETER") != null){
					detailBag.put(PARAMETER          , paddingString(qe.getString("PARAMETER"), 50, ' ', false));
				}else {
					detailBag.put(PARAMETER          , paddingString("", 50, ' ', false));
				}
			
				if(qe.getString("INVOICETYPE") != null){
					detailBag.put(DATATYPE           , qe.getString("INVOICETYPE").toString());
				}else {
					detailBag.put(DATATYPE           , "");
				}
			
				if(qe.getString("EXCEPTIONCODE") != null){
					detailBag.put(TYPE               , qe.getString("EXCEPTIONCODE").toString());
				}else {
					detailBag.put(TYPE               , "");
				}
				
				if(qe.getString("INVOICEREFNO") != null) {
					detailBag.put(REFERENCENO        , qe.getString("INVOICEREFNO").toString());
				}
				else {
					detailBag.put(REFERENCENO        , "");
				}
		
				detailBag.put(DUEDATE            , paddingString(qe.getCBDate("DUEDATE").toDBString(), 8, ' ', false));
				detailBag.put(AMOUNT             , paddingString(invoiceAmount.setScale(2).toString(), 18, '0', true));
				
				
				if( currencyCodeType.equals(DebtFileLocalCurrencyCodeType.TL.value())){
					detailBag.put(CURRENCY           , paddingString(qe.getString("DEBTCURRENCYCODE"),3,' ',false));
					detailBag.put(EXCHANGECODE       , paddingString(qe.getString("DEBTCURRENCYCODE"),3,' ',false));

				}else {
					detailBag.put(CURRENCY           , qe.getString("CURRENCY"));
					detailBag.put(EXCHANGECODE       , qe.getString("CURRENCY"));
				}
				
				detailBag.put(EUR_BUYSELL	, "00000000.000000");
				
				totalAmount = totalAmount.add(invoiceAmount);

				recordCount++;

				
				parser.writeDetail(detailBag);
			
				detailBag.clear();
			}
			
			trailerBag.put(TOTALCOUNT  , recordCount);
			trailerBag.put(TOTALAMOUNT , totalAmount);
			
			return trailerBag;

		} finally {

			if (qe != null) {
				qe.close();
			}

		}

	}

	private static CBDate setFileDate(CBBag inBag) throws CBException {

		CBDate headerDate = null;

		if (inBag.existsBagKey(DATE1)) {
			headerDate = inBag.get(DATE1).toCBDate();
		}
		else {
			headerDate = CBSystem.getInstance().getProcessDate();
		}

		return headerDate;

	}


}
