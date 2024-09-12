package cb.ext.dbs.tosyali;

import static cb.ext.dbs.util.CurrencyUtility.isCurrencyTRY;
import cb.ext.dbs.constants.DebtRecordStatu;
import cb.ext.dbs.data.WaitingDebt;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileMasterPOMData;
import cb.ext.dbs.tosyali.data.CompanyDebtFileDetail;
import cb.ext.dbs.tosyali.data.CompanyDebtFileHeader;
import cb.ext.dbs.tosyali.data.DebtFileRecordType;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBQueryExecuter;

public class CompanyDebtFileMasterService {
	
	private static boolean isMasterRecordProccessable(CompanyDebtFileHeader debtFileHeader, 
			                                          CompanyDebtFileDetail debtFileDetail, String operationType) throws CBException {

		boolean result = false;

		DBSCompanyDebtFileMasterPOM pom = DBSCompanyDebtFileMasterPOM.newDBSCompanyDebtFileMasterPOM();
		DBSCompanyDebtFileMasterPOMData data = DBSCompanyDebtFileMasterPOMData.newInstance();

		try {

			if (pom.readByCompanyOIDandInvoiceNoActive(debtFileHeader.getCompanyOID(), debtFileDetail.getInvoiceNo())) {

				data = pom.getDBSCompanyDebtFileMasterPOMData();

				if (operationType.equals(DebtFileRecordType.neww.value())) {

					if (data.processStatu.equals(DebtRecordStatu.deleteByFile.value())) {
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

	public static boolean isMasterRecordDeleted(CompanyDebtFileHeader debtFileHeader, 
			                                    CompanyDebtFileDetail debtFileDetail, String operationType) throws CBException {

		return isMasterRecordProccessable(debtFileHeader, debtFileDetail, operationType);

	}

	public static boolean isMasterRecordUpdatable(CompanyDebtFileHeader debtFileHeader, 
			                                      CompanyDebtFileDetail debtFileDetail, String operationType) throws CBException {

		return isMasterRecordProccessable(debtFileHeader, debtFileDetail, operationType);

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
			                                                String dealerInternalCustomerCode, 
			                                                String dealerAcccountCurrency) throws CBException {

		CBQueryExecuter qe = null;

		try {

			CBCurrency guaranteedTotalInvoiceAmount = CBCurrency.ZERO;
			int totalWaitingInvoiceCount = 0;

			qe = new CBQueryExecuter("EXT_DBS_GET_TOTALAMT_UNPAID_INVOICE");
			qe.setParameter("DATE1"      , fileDate);
			qe.setParameter("CUSTOMERNO" , "%"+ dealerInternalCustomerCode);
			qe.setParameter("CURRENCY"   , dealerAcccountCurrency);
			qe.setParameter("COMPANYOID" , companyID);

			qe.executeQuery();

			if (qe.next()) {

				totalWaitingInvoiceCount = qe.getInt("TOTALCOUNT");

				guaranteedTotalInvoiceAmount = qe.getCBCurrency("TOTALAMOUNT");

				if ((!guaranteedTotalInvoiceAmount.equals(CBCurrency.ZERO)) && (!isCurrencyTRY(dealerAcccountCurrency))) {

					CBCurrencyInfo dealerAccountCurInfo = CBCurrencyInfo.getCurrencyInfo(dealerAcccountCurrency);

					//guaranteedTotalInvoiceAmount = CurrencyUtility.getCurrencyRate(dealerAccountCurInfo, guaranteedTotalInvoiceAmount, fileDate).getBoughtAmount();

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
