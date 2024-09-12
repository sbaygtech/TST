package cb.ext.dbs.constants;

public enum DebtFileLocalCurrencyCodeType {

	    TL("01"),
	    TRY("02");
		
	private DebtFileLocalCurrencyCodeType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
}
