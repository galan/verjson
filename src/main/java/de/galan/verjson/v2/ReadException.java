package de.galan.verjson.v2;

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
