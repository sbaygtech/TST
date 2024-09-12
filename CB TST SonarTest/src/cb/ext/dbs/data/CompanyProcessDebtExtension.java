package cb.ext.dbs.data;

import cb.ext.dbs.pom.DBSCompanyProcessDebtPaymentExtensionPOM;
import cb.ext.dbs.pom.DBSCompanyProcessDebtPaymentExtensionPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBSystem;

public class CompanyProcessDebtExtension {

	
	public CompanyProcessDebtExtension() {

	}
	
	
	
	public void  add() throws CBException {

		DBSCompanyProcessDebtPaymentExtensionPOM      pom   = DBSCompanyProcessDebtPaymentExtensionPOM.newDBSCompanyProcessDebtPaymentExtensionPOM();
		DBSCompanyProcessDebtPaymentExtensionPOMData  data  = DBSCompanyProcessDebtPaymentExtensionPOMData.newInstance();

		try {


			data.masterOID             = getMasterOID();
			data.paymentAmount         = getPaymetAmount();
			data.productOperationRefNo = getProductOperationRefNo();
			data.paymentDate           = CBSystem.getInstance().getProcessDate();
			data.paymentTime           = CBSystem.getInstance().getCurrentTime();
	

			pom.setDBSCompanyProcessDebtPaymentExtensionPOMData(data);
			pom.create();



		} finally {

			if (pom != null) {
				pom.close();
			}
		}

	}

	
	
	public long getMasterOID() {
		return masterOID;
	}


	public void setMasterOID(long masterOID) {
		this.masterOID = masterOID;
	}


	public long getProductOperationRefNo() {
		return productOperationRefNo;
	}


	public void setProductOperationRefNo(long productOperationRefNo) {
		this.productOperationRefNo = productOperationRefNo;
	}


	public CBCurrency getPaymetAmount() {
		return paymetAmount;
	}


	public void setPaymetAmount(CBCurrency paymetAmount) {
		this.paymetAmount = paymetAmount;
	}


	private  long masterOID;
	private  long productOperationRefNo;
	

	private  CBCurrency paymetAmount;
}


