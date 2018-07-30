package com.boco.soap.cmnet.util;

import java.util.HashMap;
import java.util.Map;

public class Numbers {

	static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	static final Map<Character, Integer> digitMap = new HashMap<>();

	static {
		for (int i = 0; i < digits.length; i++) {
			digitMap.put(digits[i], (int) i);
		}
	}

	/**
	 * 支持的最大进制数
	 */
	public static final int MAX_RADIX = digits.length;

	/**
	 * 支持的最小进制数
	 */
	public static final int MIN_RADIX = 2;

	private Numbers() {
		//
	}

	/**
	 * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
	 * 
	 * @param i
	 * @param radix
	 * @return
	 */
	public static String toString(long i, int radix) {
		long tmpI = i;
		int tmpRadix = radix;
		if (tmpRadix < MIN_RADIX || tmpRadix > MAX_RADIX)
			tmpRadix = 10;
		if (tmpRadix == 10)
			return Long.toString(tmpI);

		final int size = 65;
		int charPos = 64;

		char[] buf = new char[size];
		boolean negative = tmpI < 0;

		if (!negative) {
			tmpI = -tmpI;
		}

		while (tmpI <= -tmpRadix) {
			buf[charPos--] = digits[(int) (-(tmpI % tmpRadix))];
			tmpI = tmpI / tmpRadix;
		}
		buf[charPos] = digits[(int) (-tmpI)];

		if (negative) {
			buf[--charPos] = '-';
		}

		return new String(buf, charPos, size - charPos);
	}

	static NumberFormatException forInputString(String s) {
		return new NumberFormatException("For input string: \"" + s + "\"");
	}

	static void preConfirm(String s, int radix) {
		if (s == null) {
			throw new NumberFormatException("null");
		}

		if (radix < MIN_RADIX) {
			throw new NumberFormatException("radix " + radix + " less than Numbers.MIN_RADIX");
		}
		if (radix > MAX_RADIX) {
			throw new NumberFormatException("radix " + radix + " greater than Numbers.MAX_RADIX");
		}
	}

	/**
	 * 将字符串转换为长整型数字
	 * 
	 * @param s
	 *            数字字符串
	 * @param radix
	 *            进制数
	 * @return
	 */
	public static long toNumber(String s, int radix) {
		preConfirm(s, radix);

		long result = 0;
		boolean negative = false;
		int i = 0;
		int len = s.length();
		long limit = -Long.MAX_VALUE;

		if (len > 0) {
			char firstChar = s.charAt(0);
			if (firstChar < '0') {
				if (firstChar == '-') {
					negative = true;
					limit = Long.MIN_VALUE;
				} else if (firstChar != '+')
					throw forInputString(s);

				if (len == 1) {
					throw forInputString(s);
				}
				i++;
			}
			result = handleWhile(result, s, limit, radix, i, len);
		} else {
			throw forInputString(s);
		}
		return negative ? result : -result;
	}

	static long handleWhile(long result, String s, long limit, int radix, int i, int len) {
		long tmpResult = result;
		Integer digit;
		while (i < len) {
			digit = digitMap.get(s.charAt(i++));
			if (digit == null) {
				throw forInputString(s);
			}
			if (digit < 0) {
				throw forInputString(s);
			}
			if (tmpResult < limit / radix) {
				throw forInputString(s);
			}
			tmpResult *= radix;
			if (tmpResult < limit + digit) {
				throw forInputString(s);
			}
			tmpResult -= digit;
		}
		return tmpResult;
	}
}
