package cb.ext.dbs.constants;

public enum DebtFileInvoiceCurrencyType {

	
    UnlikeCurrencyCode("01"),
    LikeCurrencyCode("02");
	
	private DebtFileInvoiceCurrencyType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
}
