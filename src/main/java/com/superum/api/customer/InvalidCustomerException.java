package com.superum.api.customer;

import com.superum.api.exception.InvalidRequestException;

/**
 * This exception is thrown when an invalid request is made, specifically when the format of customer
 * does not match any of the use cases
 */
public class InvalidCustomerException extends InvalidRequestException {

	public InvalidCustomerException() {
		super();
	}

	public InvalidCustomerException(String message) {
		super(message);
	}

	public InvalidCustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCustomerException(Throwable cause) {
		super(cause);
	}
	
}
