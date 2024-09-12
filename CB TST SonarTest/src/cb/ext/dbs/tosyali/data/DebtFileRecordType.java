package cb.ext.dbs.tosyali.data;

public enum DebtFileRecordType {

	neww("Y"),
	cancel("I"),
	update("D"),
	mistake(""),
	total("T");
	
	private DebtFileRecordType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	

}
