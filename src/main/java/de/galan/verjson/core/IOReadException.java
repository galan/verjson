package de.galan.verjson.core;

/**
 * Failed reading json-input
 * 
 * @author daniel
 */
public class IOReadException extends ReadException {

	public IOReadException(String message, Throwable cause) {
		super(message, cause);
	}

}
