package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoUtils {
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String encode(String textPassword) {
		return encoder.encode(textPassword);
	}

	public static boolean matches(String textPassword, String encoded) {
		return encoder.matches(textPassword, encoded);
	}
}
