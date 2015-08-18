package com.superum.api.group;

import com.superum.exception.DatabaseException;

/**
 * <pre>
 * This exception is thrown when the a request for a Group fails because the id used in the request
 * does not refer to any Groups
 * </pre>
 */
public class GroupNotFoundException extends DatabaseException {

	public GroupNotFoundException() {
		super();
	}

	public GroupNotFoundException(String message) {
		super(message);
	}

	public GroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupNotFoundException(Throwable cause) {
		super(cause);
	}
	
}