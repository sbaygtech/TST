package cb.ext.dbs.constants;

public enum CompanyDealerCodeFormat {
    Standard("01"),
    Special("02");
	
	private CompanyDealerCodeFormat(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;

}
