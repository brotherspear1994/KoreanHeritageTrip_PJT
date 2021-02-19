package com.web.heritage.security;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA512 {

	public static String sha(String msg, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		String hash = msg + salt;
		md.update(hash.getBytes());
		String hex = String.format("%0128x", new BigInteger(1, md.digest()));

		return hex;
	}
}
