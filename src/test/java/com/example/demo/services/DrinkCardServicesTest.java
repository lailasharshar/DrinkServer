package com.example.demo.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkCardServicesTest {

	@Test
	void getVolume() {
		Double pointRatio=2.0;
		Double volumeRatio=1.0;

		Double val = DrinkCardServices.getVolume(20L, pointRatio, volumeRatio);
		assertEquals(val, 10.0, 0.00001);
	}

	@Test
	void getPoints() {
		Double pointRatio=2.0;
		Double volumeRatio=1.0;

		Long val = DrinkCardServices.getPoints(4.0, pointRatio, volumeRatio);
		assertEquals(val, 8);
	}
}