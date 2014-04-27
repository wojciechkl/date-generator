package com.klicki.generators;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class Utils {

	public static boolean isWorkDay(DateTime date){
		return date.getDayOfWeek() != DateTimeConstants.SATURDAY && date.getDayOfWeek() != DateTimeConstants.SUNDAY;
	}

}
