package com.yigos.framework.common.util;

import java.security.MessageDigest;

public class MD5Util {
	
	/**
	 * 加密
	 * @param str
	 * @return String
	 * @author kavin
	 * @since 2013年5月10日
	 */
	public static String crypt(String str) {
		if (str == null) {
			return "";
		}

		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(str.getBytes("UTF-8"));
			byte[] hash = md.digest();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
					        + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}

		return hexString.toString();
	}
	
	public static void main(String[] args){
		System.out.println(crypt("admin"));
	}
	
}