package com.boco.soap.cmnet.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.security.MessageDigest;

public class MD5Util {
	
	private static Logger log = Logger.getLogger(MD5Util.class);
	
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/* public static final StandardPasswordEncoder passEncoder = new StandardPasswordEncoder();*/

	/**
	 * 生成加密后的密码
	 * 
	 * @param password
	 * @return
	 */
	public static String generate(String password){
		return md5Pwd(password);
	}

	/**
	 * 验证加密后的密码与源密码是否匹配
	 * @param password
	 * @param md5Password
	 * @return
	 */
	public static boolean verify(String password, String md5Password){
		return md5Pwd(password).equals(md5Password);
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	public static String md5Pwd(String pwd){
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(pwd.getBytes());
			byte[] tmp = md5.digest();
			String md5password = byteArrayToHexString(tmp);
			return md5password;
		}catch(Exception e){
			log.error("",e);
			return null;
		}
	}
	
	private static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (int i = 0; i < b.length; ++i) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

}
