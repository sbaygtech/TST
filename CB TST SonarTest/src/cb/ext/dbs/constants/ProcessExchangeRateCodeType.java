package cb.ext.dbs.constants;

public enum ProcessExchangeRateCodeType {

	
	Bought("01"),
	Sold("02");
	
	private ProcessExchangeRateCodeType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	
	
}
