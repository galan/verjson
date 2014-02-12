package de.galan.verjson.core;

import static org.apache.commons.lang3.StringUtils.*;


/**
 * Nests the object to be serialized, adding meta information such as the current version number and namespace.
 * 
 * @author daniel
 */
public class MetaWrapper {

	/** Incremental version */
	private long version;
	/** Namespace for the data object */
	private String ns;
	/** Actual payload */
	private Object data;


	public MetaWrapper(long version, Object data) {
		this(version, null, data);
	}


	public MetaWrapper(long version, String namespace, Object data) {
		this.version = version;
		ns = namespace;
		this.data = data;
	}


	public long getVersion() {
		return version;
	}


	public String getNs() {
		return ns;
	}


	public boolean hasNs() {
		return isNotBlank(getNs());
	}


	public Object getDate() {
		return data;
	}

}
