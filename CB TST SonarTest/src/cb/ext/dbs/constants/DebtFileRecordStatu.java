package cb.ext.dbs.constants;


public enum DebtFileRecordStatu {
	
	done("01"),
	error("02");
	
	
	private DebtFileRecordStatu(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	
	

}
