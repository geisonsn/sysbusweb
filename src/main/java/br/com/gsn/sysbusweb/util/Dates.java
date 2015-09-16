package br.com.gsn.sysbusweb.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public final class Dates {

	public static final String FORMAT_PT_BR_DATE_HOUR = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMAT_PT_BR_DATE = "dd/MM/yyyy";
	public static final String FORMAT_PT_BR_HOUR = "HH:mm";
	
	public static String format(Date date, String format) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date parse(String date, String format) throws ParseException {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(format).parse(date);
	}
	
	public static Date parse(Timestamp timesStamp, String format) {
		
		if (timesStamp == null) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timesStamp.getTime());
		
		return c.getTime();
	}
	
	public static String format(Timestamp timesStamp, String format) {
		Date date = parse(timesStamp, format);
		return format(date, format);
	}
	
	
}
