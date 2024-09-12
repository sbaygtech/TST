package cb.ext.dbs.constants;

public enum CurrencyProcessMethod {

	    ForeignCurrencyFixed("01"),
		ForeignCurrencyVariable("02"),
		ForeignPartialCurrencyVariable("03");
		
		private CurrencyProcessMethod(String value) {
			this.value = value;
		}
		
		public String value() { 
			return this.value;
		}
		
		private String value;

	
}
