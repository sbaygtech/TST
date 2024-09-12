package cb.ext.dbs.batch;

import cb.esi.fltr.util.bean.FileParser;
import cb.ext.dbs.constants.DatabaseConstants;
import cb.ext.dbs.constants.DebtFileLocalCurrencyCodeType;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CompanyProcessFileParam;
import cb.ext.dbs.generic.data.Constants;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBDateFormatter;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

	public class ProcessResultFileGeneric  implements CBBagKeys{
		
		//@serviceName: EXT_DBS_PROCESS_RESULT_FILE_OUT
		public static CBBag processResultFileOutService(CBBag inBag) throws CBException {
			
			CBBag outBag    = CBBagFactory.createBag();
	
			long companyOID = inBag.get(COMPANYOID).toSimpleLong();
	
			Company company = getCompany(companyOID);
	
			CompanyProcessFileParam  companyProcessFileParam = CompanyProcessFileParam.getCompanyProcessFileParam(companyOID);
	
			
			if(companyProcessFileParam.isFileEnabled()){
				sendFtpProcessResultTxtFile(inBag , company, companyProcessFileParam);
			}
	
			inBag.copyTo(outBag);
			outBag.put(RC, true);
	
			return outBag;

		}

		private static Company getCompany(long companyID) throws CBException {
			return new Company().get(companyID);
		}
		
		
		
		private static void sendFtpProcessResultTxtFile(CBBag inBag , Company company, CompanyProcessFileParam  companyProcessFileParam) throws CBException {

			CBBag headerBag   = CBBagFactory.createBag();
			CBBag detailBag   = CBBagFactory.createBag();
			CBBag trailerBag  = CBBagFactory.createBag();
			
			String ftmFileName  = "" ;
			
			FileParser parser = FileParser.getParser(DatabaseConstants.PROCESS_RESULT_OUT_FILE + company.getCustomerNumber());
			
			String sequenceNumber = parser.openFile(DatabaseConstants.TFT_DBS_PROCESS_RESULT_FILE);
		

			CBDate processDate = setFileDate(inBag);
			
			if(parser.isHeaderExists()) {
				headerBag = constructHeader(processDate, company);
				parser.writeHeader(headerBag);
			}
			
			long processSequenceNo = inBag.get(NUMBER0).toSimpleLong();
			
			detailBag = constructDetail( parser , processDate,  company.getOID(), processSequenceNo);
		
			if(parser.isFooterExists()) {
				trailerBag = constructTrailer (detailBag);
				parser.writeFooter(trailerBag);
			}
			
			String batchRunTime = "";
			if(inBag.existsBagKey(TIME1)) {
				CBTime startTime = inBag.get(TIME1).toCBTime();
				batchRunTime = startTime.toString().substring(0, 4);
			}
			else{
				batchRunTime = CBSystem.getInstance().getCurrentTime().toString().substring(0, 4);
			}
	
			ftmFileName = GeneralUtility.genericFileName( companyProcessFileParam.getProcessFileName() ,processDate, CBSystem.getInstance().getCurrentTime(),sequenceNumber, "", batchRunTime) +".txt";

			parser.exportFile(sequenceNumber, ftmFileName, DatabaseConstants.PROCESS_RESULT_OUT_FILE+ + company.getCustomerNumber(), DatabaseConstants.TFT_DBS_PROCESS_RESULT_FILE);

		}
		
		
		private static CBDate setFileDate(CBBag inBag) throws CBException {

			CBDate headerDate = null;

			if (inBag.existsBagKey(PROCESSDATE)) {
				headerDate = inBag.get(PROCESSDATE).toCBDate();
			}
			else {
				headerDate = CBSystem.getInstance().getProcessDate();
			}

			return headerDate;

		}
		
		
		private static CBBag constructHeader( CBDate fileDate , Company company ) {

			CBBag outBag = CBBagFactory.createBag();
			
			outBag.put(RECORDSTATUS , DatabaseConstants.FILE_HEADER_INDICATOR);
			outBag.put(COMPANYCODE  , company.getCompanyInternalCode());
			outBag.put(DISTRICTCODE , "");
			outBag.put(BANKCODE		, Constants.FB_BANK_CODE);
			outBag.put(BANKNAME		, Constants.FB_BANK_NAME);
			outBag.put(DATE         , fileDate);
			outBag.put(ENDOFDATA, "\r");
			
			return outBag;
		}
		
		
		private static CBBag constructTrailer(CBBag inBag) throws CBException {

			CBBag outBag = CBBagFactory.createBag();
			
			outBag.put(RECORDSTATUS , DatabaseConstants.FILE_TRAILER_INDICATOR);
			outBag.put(TOTALCOUNT   , String.valueOf(inBag.get(TOTALCOUNT).toSimpleInt()));
			outBag.put(ENDOFDATA, "\r");
			
			return outBag;
		}
		
		private static CBBag constructDetail (FileParser parser ,CBDate processDate,long companyID, long sequenceNo) throws CBException {

			CBQueryExecuter qe = null;
			CBBag  detailBag  = CBBagFactory.createBag();
			CBBag  trailerBag = CBBagFactory.createBag();
		

			try {

				Company company = new Company().get(companyID);
				
				int recordCount = 0;

				CBCurrency invoiceAmount = CBCurrency.ZERO;
				CBCurrency paidAmount = CBCurrency.ZERO;
				CBCurrency totalAmount   = CBCurrency.ZERO;
				CBCurrency totalPaidAmount   = CBCurrency.ZERO;
				
				String currencyCode = "";
				
				qe = new CBQueryExecuter("EXT_DBS_GET_PROCESS_MASTER_DETAIL");
				qe.setParameter("DATE1"      , processDate);
				qe.setParameter("COMPANYOID" , companyID);
				qe.setParameter("SEQUENCENO" , sequenceNo);
				qe.executeQuery();
				
				while (qe.next()) {
					
					
					currencyCode  = qe.getString("CURRENCY_CODE");
					invoiceAmount = qe.getCBCurrency("TRANSACTION_AMOUNT");
					paidAmount    = qe.getCBCurrency("PAIDAMOUNT1");
					
					
					if( company.getLocalCurrencyCodeType().equals(DebtFileLocalCurrencyCodeType.TL.value()) && currencyCode.equals(DebtFileLocalCurrencyCodeType.TRY.toString())){
						currencyCode = DebtFileLocalCurrencyCodeType.TL.toString();

					}
				
					detailBag.put(RECORDSTATUS       , DatabaseConstants.FILE_DETAIL_INDICATOR);
					detailBag.put(SPECIALCODE        , qe.getString("CUSTOMERNO"));
					detailBag.put(CUSTOMERTITLE      , qe.getString("CUSTOMERTITLE"));
					detailBag.put(INVOICENO          , qe.getString("INVOICENO"));
					detailBag.put(DATE1              , qe.getCBDate("PROCESSDATE").toDBString());
					detailBag.put(DUEDATE            , qe.getCBDate("DUEDATE").toDBString());
					detailBag.put(AMOUNT             , amountSetScale(invoiceAmount).toString());
					detailBag.put(CURRENCY           , currencyCode);				
					detailBag.put(INTERESTAMOUNT     , amountSetScale(CBCurrency.ZERO));
					detailBag.put(COLLECTIONAMOUNT   , amountSetScale(invoiceAmount).toString());
					detailBag.put(EXCHANGECODE       , currencyCode);
					detailBag.put(INTERESTCOLLECTION , amountSetScale(CBCurrency.ZERO));
					detailBag.put(EXCHANGERATE       , amountSetScale(CBCurrency.ZERO));
					detailBag.put(INVOICENUMBER      , "");
					detailBag.put(TRANSACTIONDATE    , processDate.toDBString());
					detailBag.put(PROVISIONNUMBER    , "");
					detailBag.put(PARAMETER          , "");
					detailBag.put(DATATYPE           , "B");
					detailBag.put(TYPE               , qe.getString("EXCEPTIONCODE").toString());
					
					detailBag.put(TYPECODE 			 , qe.getString("INVOICETYPE"));
					detailBag.put(REFERANSNO		 , qe.getString("INVOICEREFNO"));										
					detailBag.put(PRODUCTREFNO		 , qe.getString("PRODUCTOPREFNO"));
					
					detailBag.put(BANKCODE			 , "");
					detailBag.put(ACIKLAMA			 , "");
					detailBag.put(PAIDAMOUNT         , paidAmount.toString());
					detailBag.put(AMOUNT1         	 , paidAmount.toFormatedString(2).replaceAll(",",""));
					detailBag.put(ENDOFDATA, "\r");
					detailBag.put(EUR_BUYSELL	, "00000000.000000");
					detailBag.put(PARAMETERTYPE, "T");
					
					CBDateFormatter df = new CBDateFormatter("dd/MM/yyyy");	
					detailBag.put(DATE2, df.format(qe.getCBDate("PROCESSDATE")));
					
					String statu = qe.getString("PROCESSSTATU");
					if(DebtRecordStatu.done.value().equals(statu) || DebtRecordStatu.partialPayment.value().equals(statu)) {
						detailBag.put(PAYMENTSTATUS  , "1");
					}
					else {
						detailBag.put(PAYMENTSTATUS  , "2");
					}
					
					totalAmount = totalAmount.add(invoiceAmount);
					totalPaidAmount = totalAmount.add(paidAmount);
					
					recordCount++;

					
					parser.writeDetail(detailBag);
				
					detailBag.clear();
				}
				
				trailerBag.put(TOTALCOUNT  , recordCount);
				trailerBag.put(TOTALAMOUNT , totalAmount);
				trailerBag.put(TOTALAMOUNT1 , totalPaidAmount);
				return trailerBag;

			} finally {

				if (qe != null) {
					qe.close();
				}

			}

		}

		
		
		public static CBCurrency  amountSetScale (CBCurrency  amount) throws CBException {
					
			if(!amount.isDecimal()){
				amount = amount.setScale(2);
			}
	
			return amount;
		}

		
		
	}
	
	
	
