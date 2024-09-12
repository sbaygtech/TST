package cb.ext.dbs.generic;

import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;

import cb.ext.dbs.bean.DebtFileService;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.constants.ProcessExchangeRateCodeType;
import cb.ext.dbs.data.Company;
import cb.ext.dbs.data.CurrencyRate;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.generic.data.CompanyDebtFileDetail;
import cb.ext.dbs.generic.data.CompanyDebtFileHeader;
import cb.ext.dbs.generic.data.DebtFileRecordType;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOMData;
import cb.ext.dbs.util.GeneralUtility;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;

public class CompanyDebtFileMasterService {
	
	public static boolean isMasterRecordProccessable(CompanyDebtFileHeader debtFileHeader, 
			                                          CompanyDebtFileDetail debtFileDetail, String operationType) throws CBException {

		boolean result = false;

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		try {

			if (pom.readByCompanyOIDandInvoiceNoActive(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {

				data = pom.getDBSCompanyDebtFileMasterPOMData();

				if (operationType.equals(DebtFileRecordType.neww.value())) {

					if ((data.processStatu.equals(DebtRecordStatu.init.value())) || (data.processStatu.equals(DebtRecordStatu.deleteByFile.value()))) 				
					{
						result = true;
					}
				}
				else if (operationType.equals(DebtFileRecordType.update.value())) {

					if ((data.processStatu.equals(DebtRecordStatu.init.value())) || (data.processStatu.equals(DebtRecordStatu.error.value()))) {
						result = true;
					}
				}
			}

			return result;

		} finally {

			if (pom != null) {
				pom.close();
			}

		}
	}
	
	public static boolean isMasterRecordUpdatable(String processStatu) throws CBException {

		boolean result = false;
		
		if ( (processStatu.equals(DebtRecordStatu.init.value())) || 
		      (processStatu.equals(DebtRecordStatu.error.value()))
		    ) {
			result = true;
		}
		
		return result;

	}

	public static WaitingDebt getUnPaidWaitingInvoiceTotals(long companyID, CBDate fileDate, 
			                                                String dealerCustomerCode, 
			                                                String dealerAcccountCurrency) throws CBException {

		CBQueryExecuter qe        = null;
		CurrencyRate currencyRate = null;

		try {

			CBCurrency guaranteedTotalInvoiceAmount = CBCurrency.ZERO;
			int totalWaitingInvoiceCount = 0;

			Company company = new Company().get(companyID);
			
			String exchangeRateBankType = GeneralUtility.getExchangeRateBankType(company.getExchangeRate());
			
			qe = new CBQueryExecuter("EXT_DBS_GET_TOTALAMT_UNPAID_INVOICE");
			qe.setParameter("DATE1", fileDate);
			qe.setParameter("CUSTOMERNO","%"+dealerCustomerCode);
			qe.setParameter("CURRENCY", dealerAcccountCurrency);
			qe.setParameter("COMPANYOID", companyID);

			qe.executeQuery();

			if (qe.next()) {

				totalWaitingInvoiceCount = qe.getInt("TOTALCOUNT");

				guaranteedTotalInvoiceAmount = qe.getCBCurrency("TOTALAMOUNT");

				if ((!guaranteedTotalInvoiceAmount.equals(CBCurrency.ZERO)) && (!isCurrencyTRY(dealerAcccountCurrency))) {
					
					currencyRate = DebtFileService.getRemainingDebtAmount(fileDate, dealerAcccountCurrency,guaranteedTotalInvoiceAmount, exchangeRateBankType, company.getExchangeRateType());
					
					if(company.getExchangeRateType().equals(ProcessExchangeRateCodeType.Sold.value())){
						guaranteedTotalInvoiceAmount = currencyRate.getBoughtAmount();
					}else {
						guaranteedTotalInvoiceAmount = currencyRate.getSoldAmount();
					}
					
				}

			}

			return new WaitingDebt(guaranteedTotalInvoiceAmount, totalWaitingInvoiceCount);

		} finally {

			if (qe != null) {
				qe.close();
			}
		}

	}
	
}
