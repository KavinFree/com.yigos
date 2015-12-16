package com.yigos.framework.common.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateTimeUtil {
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	public static String getSysTime() {
        Date date = Calendar.getInstance().getTime();
        return formatDate(date, yyyy_MM_dd_HH_mm_ss);
    }
	
	public static String formatDate(Date date, String targetformat) {
        return DateFormatUtils.format(date, targetformat);
    }
	
	public static Date toDate(String dateStr,String sourceformat) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceformat);
        return simpleDateFormat.parse(dateStr);
    }
	
	public static Date toDate(String dateStr)  throws ParseException{
        return toDate(dateStr, yyyy_MM_dd_HH_mm_ss);
    }
	
	public static String randomCode(int length) {
		char[] str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rd = new Random();
		String code = "";
		int temp = 0;
		for (int i = 0; i < length; i++) {
			temp = rd.nextInt(36);
			code += str[temp];
		}
		return code;
	}
	
	public static boolean isFormat(String date, String pattern){
		String eL = "";
		if(pattern.equals(yyyy_MM_dd)){
			eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		}else if(pattern.equals(yyyy_MM_dd_HH_mm_ss)){
			eL = "[0-9]{4}-[0-9]{2}-[0-9]{2} (([01][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]";
		}
		
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean dateFlag = m.matches();
		
		return dateFlag;
	}
}
