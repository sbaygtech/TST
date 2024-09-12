package cb.ext.dbs.util;

import static cb.ext.dbs.util.ExceptionUtility.throwException;
import static cb.ext.dbs.util.ExceptionUtility.throwExceptionNoParam;
import static cb.smg.bagkeys.CBBagKeys.CURRENCY;
import static cb.smg.bagkeys.CBBagKeys.CUSTOMERNUMBER;
import static cb.smg.bagkeys.CBBagKeys.TABLE1;
import static cb.smg.bagkeys.CBBagKeys.TABLE3;
import static cb.smg.bagkeys.CBBagKeys.VALUE1;
import cb.ext.dbs.constants.DBSExceptions;
import cb.ext.dbs.constants.DBSOperationType;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBException;

public class CompanyDealerValidations {

	public static void doMailValidation(CBBag inBag, int tableBagIndex, int emptyException, int inValidMailException, int mailLengthException) throws CBException {

		if (inBag.getSize(tableBagIndex) == 0) {
			throwExceptionNoParam(emptyException);
		}
		else {

			StringBuffer eMailFull = new StringBuffer();

			for (int index = 0; index < inBag.getSize(tableBagIndex); index++) {

				if (!EMailUtility.isEmailValid(inBag.get(tableBagIndex, index, VALUE1).toString())) {
					CBBag exBag = CBBagFactory.createBag();
					exBag.put(VALUE1, inBag.get(tableBagIndex, index, VALUE1).toString());
					throw new CBException(inValidMailException, exBag);
				}
				else {
					eMailFull = eMailFull.append(inBag.get(tableBagIndex, index, VALUE1).toString());
					eMailFull = eMailFull.append(";");
				}
			}

			if (eMailFull.length() > 200) {
				throwExceptionNoParam(mailLengthException);
			}

		}
	}

	public static void checkCustomerNumber(CBBag inBag) throws CBException {

		if (inBag.get(CUSTOMERNUMBER).toString().equals("")) {
			throwExceptionNoParam(DBSExceptions.CUSTOMER_EMPTY);
		}
	}

	public static void checkAccounts(CBBag inBag, int exceptionNo) throws CBException {

		if (inBag.getSize(TABLE3) == 0) {
			throwExceptionNoParam(exceptionNo);
		}

	}

	public static void checkCurrencyExists(CBBag inBag, String operation, String currency, int tableSize, int selectedNumber) throws CBException {

		for (int index = 0; index < tableSize; index++) {

			if ((operation.equals(DBSOperationType.add.value())) || ((operation.equals(DBSOperationType.update.value())) && (index != selectedNumber))) {

				if (inBag.get(TABLE1, index, CURRENCY).toString().equals(currency)) {
					throwException(DBSExceptions.ACCOUNT_CURRENCY_EXIST, inBag, CURRENCY);
				}

			}

		}
	}

}
