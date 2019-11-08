package com.example.demo.exceptions;

public class InsufficentFundsException extends Throwable {
	public InsufficentFundsException(String error) {
		super(error);
	}
}
