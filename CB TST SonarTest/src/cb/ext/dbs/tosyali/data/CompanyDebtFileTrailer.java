package cb.ext.dbs.tosyali.data;

import cb.ext.dbs.pom.DBSCompanyDebtFileTrailerPOM;
import cb.ext.dbs.pom.DBSCompanyDebtFileTrailerPOMData;
import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBException;

public class CompanyDebtFileTrailer {
	
	public CompanyDebtFileTrailer(long headerOID,
								  int invoiceTotalCountNew, 
								  CBCurrency invoiceTotalAmountNew, 
								  long invoiceTotalCountCancel, 
								  CBCurrency invoiceTotalAmountCancel, 
								  long invoiceTotalCountUpdate,
								  CBCurrency invoiceTotalAmountUpdate) {
		
		setHeaderOID(headerOID);
		setInvoiceTotalCountNew(invoiceTotalCountNew);
		setInvoiceTotalAmountNew(invoiceTotalAmountNew);
		setInvoiceTotalCountCancel(invoiceTotalCountCancel);
		setInvoiceTotalAmountCancel(invoiceTotalAmountCancel);
		setInvoiceTotalCountUpdate(invoiceTotalCountUpdate);
		setInvoiceTotalAmountUpdate(invoiceTotalAmountUpdate);
		
	}

	public void add() throws CBException{
		
		DBSCompanyDebtFileTrailerPOM pom = DBSCompanyDebtFileTrailerPOM.newDBSCompanyDebtFileTrailerPOM();
		DBSCompanyDebtFileTrailerPOMData data = DBSCompanyDebtFileTrailerPOMData.newInstance();


		try {

			data.headerOID = getHeaderOID();
			data.invoiceTotalCountNew = getInvoiceTotalCountNew();
			data.invoiceTotalAmountNew = getInvoiceTotalAmountNew();
			data.invoiceTotalCountCancel = getInvoiceTotalCountCancel();
			data.invoiceTotalAmountCancel = getInvoiceTotalAmountCancel();
			data.invoiceTotalCountUpdate = getInvoiceTotalCountUpdate();
			data.invoiceTotalAmountUpdate = getInvoiceTotalAmountUpdate();

			pom.setDBSCompanyDebtFileTrailerPOMData(data);
			pom.create();

	
		} finally {

			if (pom != null) {
				pom.close();
			}
		}
		
	}

	public int getInvoiceTotalCountNew() {
		return invoiceTotalCountNew;
	}
	public void setInvoiceTotalCountNew(int invoiceTotalCountNew) {
		this.invoiceTotalCountNew = invoiceTotalCountNew;
	}
	public CBCurrency getInvoiceTotalAmountNew() {
		return invoiceTotalAmountNew;
	}
	public void setInvoiceTotalAmountNew(CBCurrency invoiceTotalAmountNew) {
		this.invoiceTotalAmountNew = invoiceTotalAmountNew;
	}
	public long getInvoiceTotalCountCancel() {
		return invoiceTotalCountCancel;
	}
	public void setInvoiceTotalCountCancel(long invoiceTotalCountCancel) {
		this.invoiceTotalCountCancel = invoiceTotalCountCancel;
	}
	public CBCurrency getInvoiceTotalAmountCancel() {
		return invoiceTotalAmountCancel;
	}
	public void setInvoiceTotalAmountCancel(CBCurrency invoiceTotalAmountCancel) {
		this.invoiceTotalAmountCancel = invoiceTotalAmountCancel;
	}
	public long getInvoiceTotalCountUpdate() {
		return invoiceTotalCountUpdate;
	}
	public void setInvoiceTotalCountUpdate(long invoiceTotalCountUpdate) {
		this.invoiceTotalCountUpdate = invoiceTotalCountUpdate;
	}
	public CBCurrency getInvoiceTotalAmountUpdate() {
		return invoiceTotalAmountUpdate;
	}
	public void setInvoiceTotalAmountUpdate(CBCurrency invoiceTotalAmountUpdate) {
		this.invoiceTotalAmountUpdate = invoiceTotalAmountUpdate;
	}
	public long getHeaderOID() {
		return headerOID;
	}
	public void setHeaderOID(long headerOID) {
		this.headerOID = headerOID;
	}

	private int invoiceTotalCountNew;
	private CBCurrency invoiceTotalAmountNew;
	private long invoiceTotalCountCancel;
	private CBCurrency invoiceTotalAmountCancel;
	private long invoiceTotalCountUpdate;
	private CBCurrency invoiceTotalAmountUpdate;
	private long headerOID;
	

}
