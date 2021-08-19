package com.mycode.fruit.basket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Utility {
	public static final String CSV_PATTERN = "[^,]*(?:,[^,]*){3}"; // Regex to validate each line has 3 comma
	public static Pattern csvPattern = Pattern.compile(CSV_PATTERN,Pattern.MULTILINE);

	/**
	 * @param line
	 * @return
	 * This is an utility method to validate each line of the CSV file has correct format
	 */
	static boolean validateLineFormat(String line) {
		Matcher matcher = csvPattern.matcher(line);
		return (matcher.matches())?true:false;    
   }
	
	/**
	 * @param obj
	 * @return
	 * This is an utilty method to validate age-in-days column should be integer
	 */
	static boolean validateAgeInDays(String obj) {
		try {
			Integer.parseInt(obj);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
   }
}
