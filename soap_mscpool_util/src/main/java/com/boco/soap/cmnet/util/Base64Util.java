package com.boco.soap.cmnet.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {
	
	private static final String UTF8 = "UTF-8";
	
	// 加密
	public static String encode(byte[] str) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(str);
	}
	
	// 加密
	public static String encode(String str) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(str.getBytes(UTF8));
	}

	// 解密
	public static String decode(String s) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(s), UTF8);
	}
}
