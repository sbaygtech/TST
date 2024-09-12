package cb.ext.dbs.constants;

public enum LimitAccountOption {

	    All("0"),
	    Only_TRY("1");
		
		private LimitAccountOption(String value) {
			this.value = value;
		}
		
		public String value() { 
			return this.value;
		}
		
		
		public static LimitAccountOption fromString(String value) {
		    for (LimitAccountOption v : LimitAccountOption.values()) {
		      if (v.value.equalsIgnoreCase(value)) {
		        return v;
		      }
		    }
		    return LimitAccountOption.All;
		  }
		
		private String value;

	
}
