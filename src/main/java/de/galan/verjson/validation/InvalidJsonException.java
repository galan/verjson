package de.galan.verjson.validation;

/**
 * JSON does not validate agains JSON Schema.
 * 
 * @author daniel
 */
public class InvalidJsonException extends RuntimeException {

	public InvalidJsonException(String message, Throwable cause) {
		super(message, cause);
	}


	public InvalidJsonException(String message) {
		super(message);
	}

}
