package de.galan.verjson.core;

import de.galan.verjson.util.ReadException;

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
