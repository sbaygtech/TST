package cb.ext.dbs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFormatValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + 
	                                            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	static {

		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public EmailFormatValidator() {

	}

	public boolean validate(final String email) {

		matcher = pattern.matcher(email);

		return matcher.matches();

	}

	private static Pattern pattern;
	private Matcher matcher;

}
