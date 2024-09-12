package cb.ext.dbs.constants;

public enum DBSProcessType {
	PAYMENT(1), CANCEL_PAYMENT(2);
	
	private int type;
	
	private DBSProcessType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
