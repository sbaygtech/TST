package cb.ext.dbs.data;

import cb.smg.general.utility.CBCurrency;
import cb.smg.general.utility.CBCurrencyInfo;

public class CurrencyRate {
	
	
	public CurrencyRate(CBCurrencyInfo soldCurrency, CBCurrency soldAmount, CBCurrency boughtAmount, CBCurrency rate) {
		
		setSoldCurrency(soldCurrency);
		setSoldAmount(soldAmount);
		setBoughtAmount(boughtAmount);
		setRate(rate);
	}
	public CBCurrency getSoldAmount() {
		return soldAmount;
	}
	public void setSoldAmount(CBCurrency soldAmount) {
		this.soldAmount = soldAmount;
	}
	public CBCurrency getBoughtAmount() {
		return boughtAmount;
	}
	public void setBoughtAmount(CBCurrency boughtAmount) {
		this.boughtAmount = boughtAmount;
	}
	public CBCurrency getRate() {
		return rate;
	}
	public void setRate(CBCurrency rate) {
		this.rate = rate;
	}
	
	public CBCurrencyInfo getSoldCurrency() {
		return soldCurrency;
	}
	public void setSoldCurrency(CBCurrencyInfo soldCurrency) {
		this.soldCurrency = soldCurrency;
	}



	CBCurrencyInfo soldCurrency;
	CBCurrency soldAmount;
	CBCurrency boughtAmount;
	CBCurrency rate;

}
