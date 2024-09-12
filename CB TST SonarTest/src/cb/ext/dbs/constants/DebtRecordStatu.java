package cb.ext.dbs.constants;


public enum DebtRecordStatu {
	
	init("S00"),
	done("S01"),
	error("S02"),
	deleteByFile("S03"),
	partialPayment("S04"),
	cancelPayment("S05");
	
	private DebtRecordStatu(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	
	

}
