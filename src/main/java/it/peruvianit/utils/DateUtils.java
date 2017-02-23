package it.peruvianit.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils {
	public static Long getCurrentTimeUTC(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();		
	}
	
	public static Date addSeconds(Date date, int seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		
		return calendar.getTime();
	}
}
