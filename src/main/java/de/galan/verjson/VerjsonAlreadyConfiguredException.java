package de.galan.verjson;

/**
 * Exception is thrown when the configuration for an instance of verjson is modified outside of the configure() method.
 * 
 * @author daniel
 */
public class VerjsonAlreadyConfiguredException extends RuntimeException {

	public VerjsonAlreadyConfiguredException() {
		super("Instance of Verjson is already configured");
	}

}
