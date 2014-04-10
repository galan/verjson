package de.galan.oldverjson.validation;

/**
 * Schema is invalid or Validator could not be created
 * 
 * @author daniel
 */
public class InvalidSchemaException extends RuntimeException {

	public InvalidSchemaException(String message, Throwable cause) {
		super(message, cause);
	}


	public InvalidSchemaException(String message) {
		super(message);
	}

}
