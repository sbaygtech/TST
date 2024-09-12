package cb.ext.dbs.generic.data;

public enum DebtFileRecordType {

	neww("Y"),
	cancel("I"),
	update("D"),
	mistake(""),
	total("T"),
	cancelPayment("S");
	
	private DebtFileRecordType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	

}
