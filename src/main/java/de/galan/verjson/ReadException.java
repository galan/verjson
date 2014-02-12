package de.galan.verjson;

/**
 * Base class for read exceptions.
 * 
 * @author daniel
 */
public abstract class ReadException extends Exception {

	public ReadException(String message) {
		super(message);
	}

}
