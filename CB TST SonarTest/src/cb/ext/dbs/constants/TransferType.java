package cb.ext.dbs.constants;


public enum TransferType {
	
	init("0"),
	atBlockageAccount("1"),
	atCurrentAccount("2");
	
	
	private TransferType(String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	private String value;
	
	

}
