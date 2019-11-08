package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

	@Test
	void encode() {
		String password = "helloWorld";
		String encoded = CryptoUtils.encode(password);
		assertNotNull(encoded);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
	}

	@Test
	void getCards() {
		String password = "joytotheworld";
		String encoded;
		encoded = CryptoUtils.encode(password);
		System.out.println(CryptoUtils.matches("joytotheworld", encoded));
		System.out.println(CryptoUtils.matches("joytotheworld", "$2a$10$Cco/NYwCgYWfraRkVRROHe5zxCOw9P3zV/EHcWVmjcXhq8suDxfNy"));
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));
		encoded = CryptoUtils.encode(password);
		System.out.println(encoded);
		assertTrue(CryptoUtils.matches(password, encoded));

	}
}