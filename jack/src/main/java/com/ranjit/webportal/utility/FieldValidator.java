package com.einfochips.webportal.utility;

import java.util.List;

/**
 * This class provides common field validator functions to be used across application.
 * 
 * @author asra.shaikh
 *
 */
public class FieldValidator {
	private FieldValidator() {
	}

	/**
	 * This method is used to check whether given string is null or empty.
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isNull(String field) {
		boolean flag = false;
		if ((field == null) || (field.trim().isEmpty())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to check whether a given list is null.
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNull(List<?> list) {
		return (list == null) || (list.isEmpty());
	}

	/**
	 * To get field value if it is not null.
	 * 
	 * @param field
	 * @return
	 */
	public static String getNonNullString(String field) {
		String str = field;
		if ((field == null) || (field.trim().isEmpty())) {
			str = "";
		}
		return str;
	}
}
