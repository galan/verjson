package de.galan.verjson.util;

/**
 * Base class for read exceptions.
 */
public abstract class ReadException extends Exception {

	public ReadException(String message) {
		super(message);
	}


	public ReadException(String message, Throwable cause) {
		super(message, cause);
	}

}
