package com.web.heritage.security;

public class RandomKey {

	public static String create(int length) {
		char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		int idx = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			idx = (int)(charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소수점제거)
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}

}
