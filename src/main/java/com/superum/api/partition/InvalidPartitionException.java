package com.superum.api.partition;

import com.superum.api.exception.InvalidRequestException;

/**
 * This exception is thrown when an invalid request is made, specifically when the format of partition
 * does not match any of the use cases
 */
public class InvalidPartitionException extends InvalidRequestException {

	public InvalidPartitionException() {
		super();
	}

	public InvalidPartitionException(String message) {
		super(message);
	}

	public InvalidPartitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPartitionException(Throwable cause) {
		super(cause);
	}
	
}