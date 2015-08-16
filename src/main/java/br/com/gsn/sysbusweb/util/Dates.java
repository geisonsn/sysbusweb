package br.com.gsn.sysbusweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Dates {

	public static final String FORMAT_PT_BR_COMPLETE = "dd/MM/yyyy HH:mm:ss";
	
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date parse(String date, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(date);
	}
}
