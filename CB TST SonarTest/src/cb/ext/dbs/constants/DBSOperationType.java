package cb.ext.dbs.constants;

public enum DBSOperationType {
	
	add("ADD"),
	update("UPDATE"),
	delete("DELETE");
	
	
	private DBSOperationType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;

}
