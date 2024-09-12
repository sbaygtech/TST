package cb.ext.dbs.constants;

public enum DebtResultFileDetailType {
	
	waiting("01"),
	file("02");
	
	private DebtResultFileDetailType(String detailType) {
		this.detailType = detailType;
	}
	
	public String value() { 
		return this.detailType;
	}
	private String detailType;
}
