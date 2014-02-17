package de.galan.verjson.core;

/**
 * Version is newer then registered Transformers are able to process.
 * 
 * @author daniel
 */
public class VersionNotSupportedException extends ReadException {

	private long supported;
	private long required;


	public VersionNotSupportedException(long supported, long required) {
		super("Verjson only supports version '" + supported + "', required version is '" + required + "'.");
		this.supported = supported;
		this.required = required;
	}


	public long getVersionSupported() {
		return supported;
	}


	public long getVersionRequired() {
		return required;
	}

}
