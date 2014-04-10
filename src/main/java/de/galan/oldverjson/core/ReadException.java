package de.galan.oldverjson.core;

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
