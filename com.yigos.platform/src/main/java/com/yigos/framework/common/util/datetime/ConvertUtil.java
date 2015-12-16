package com.yigos.framework.common.util.datetime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtil {
	public static final String NOW_TIME = "dd/MM/yyyy HH:mm:ss";
	public static final String NOW_DAY = "dd/MM/yyyy";
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static String date2String(Date d) {
		return date2String(d, null);
	}

	public static Date str2Date(String s) throws ParseException {
		return str2Date(s, null);
	}

	public static Timestamp str2Timestamp(String s) throws ParseException {
		return new Timestamp(str2Date(s, null).getTime());
	}

	public static String date2String(Date d, String partten) {
		if (partten == null) {
			return sdf.format(d);
		}
		return new SimpleDateFormat(partten).format(d);
	}

	public static Date str2Date(String s, String partten) throws ParseException {
		if (partten == null) {
			return sdf.parse(s);
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(partten);
		datetimeFormat.setLenient(false);

		return datetimeFormat.parse(s);
	}

	public static Timestamp str2Timestamp(String s, String partten)
			throws ParseException {
		return new Timestamp(str2Date(s, partten).getTime());
	}
}
