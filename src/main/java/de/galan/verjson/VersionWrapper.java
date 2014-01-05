package de.galan.verjson;

/**
 * Nests the object to be serialized, adding the current version number.
 * 
 * @author daniel
 */
public class VersionWrapper {

	private long version;
	private Object data;


	public VersionWrapper(long version, Object data) {
		this.version = version;
		this.data = data;
	}


	public long getVersion() {
		return version;
	}


	public Object getDate() {
		return data;
	}

}
