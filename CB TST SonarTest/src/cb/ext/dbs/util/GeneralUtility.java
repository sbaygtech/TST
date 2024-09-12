package cb.ext.dbs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.StringTokenizer;

import cb.smg.bagkeys.CBBagKeys;
import cb.smg.general.utility.CBBag;
import cb.smg.general.utility.CBBagFactory;
import cb.smg.general.utility.CBCaller;
import cb.smg.general.utility.CBDate;
import cb.smg.general.utility.CBException;
import cb.smg.general.utility.CBReferenceData;
import cb.smg.general.utility.CBSystem;
import cb.smg.general.utility.CBTime;

public class GeneralUtility implements CBBagKeys {

	final static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	public static boolean isDateValid(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String getFromSystemParameter(String key) throws CBException {
		return CBSystem.getInstance().getSystemParameter(key).toString();
	}

	public static String paddingString(String s, int len, char c, boolean paddingLeft) {

		if (s.length() > len)
			return s.substring(0, len);

		int add = len - s.length();
		StringBuffer str = new StringBuffer(s);
		char[] ch = new char[add];
		Arrays.fill(ch, c);
		if (paddingLeft) {
			str.insert(0, ch);

		}
		else {
			str.append(ch);
		}
		return str.toString();
	}

	public static String getFromRefData(String tableName, String columnName, long value, String returnColumn) throws CBException {

		return getFromRefData(tableName, columnName, String.valueOf(value), returnColumn);

	}

	public static String getFromRefData(String tableName, String columnName, String value, String returnColumn) throws CBException {

		CBReferenceData refData = new CBReferenceData(tableName);
		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		hashTable.put(columnName, String.valueOf(value));

		return refData.getValue(CBSystem.getInstance().getDate(), hashTable, returnColumn);

	}

	public static String generateNo(String numeratorType) throws CBException {

		CBBag numeratorBag = CBBagFactory.createBag();

		String referansNo = "";

		try {

			numeratorBag.put(TIP, numeratorType);
			referansNo = cb.smg.general.utility.CBCaller.call("NUMARA_GETIR_YENI", numeratorBag).get(NUMARA).toString();

		} catch (cb.smg.general.utility.CBException ce) {

			if (!(ce.id == 1111))
				throw ce;

			numeratorBag.clear();
			numeratorBag.put(TIP, numeratorType);
			numeratorBag.put(BASLANGICNO, "1");
			numeratorBag.put(BITISNO, "9999999999");
			numeratorBag.put(INCREMENTVALUE, "1");
			numeratorBag.put(MAXLENGTH, "10");
			numeratorBag.put(NUMARA, "1");
			numeratorBag.put(RESET, "1");

			CBCaller.call("NUMARA_YARAT", numeratorBag);

			numeratorBag.clear();
			numeratorBag.put(TIP, numeratorType);

			referansNo = cb.smg.general.utility.CBCaller.call("NUMARA_GETIR_YENI", numeratorBag).get(NUMARA).toString();

		}

		return referansNo;

	}
	
	public static String getExchangeRateBankType(String code) {
			
			String bankType = "R";
			
			try {
				
				CBReferenceData rd = new CBReferenceData("PRM_DBS_EXCHANGE_RATE");
				Hashtable rdBag = new Hashtable();
				
				rdBag.put("CODE", code);
				bankType =  rd.getValue(CBSystem.getInstance().getDate(), rdBag, "BANKTYPE");
				
				if (bankType==null) {
					bankType = "R";
				}
				
				
			} catch(Exception e) {
		
			}
			
			return bankType;
			
		}
	
	
	public static String genericFileName(String fileNameFormat, CBDate fileDate,CBTime fileTime , String sequenceNumber, String fileSequenceNo, String batchRunTime) {
		
		String fileName = fileNameFormat;
		
		fileName = fileName.replace("@", fileDate.toDBString().substring(2));//2 HANE YYMMDD
		fileName = fileName.replace("#", fileDate.toDBString());//TARÝH
		fileName = fileName.replace("?", sequenceNumber);//FTPSIRANO
		fileName = fileName.replace(";", fileTime.toString());//SAAT
		fileName = fileName.replace("+", fileSequenceNo);//DOSYA SIRA NO
		fileName = fileName.replace("=", batchRunTime);//BATCH CALISMA SAATI
		
		return fileName;
		
	}
	
	public static String[] parseList(String listStr) {
		StringTokenizer st = new StringTokenizer(listStr, ",");
		String[] parsedList = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			parsedList[i] = st.nextToken();
			i++;
		}
		return parsedList;
	}

}
