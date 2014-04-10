package de.galan.oldverjson.core;

import com.google.gson.annotations.SerializedName;


/**
 * Nests the object to be serialized, adding meta information such as the current version number and namespace.
 * 
 * @author daniel
 */
public class MetaWrapper {

	public static final String ID_VERSION = "$v";
	public static final String ID_NAMESPACE = "$ns";
	public static final String ID_DATA = "$d";

	/** Incremental version */
	@SerializedName(ID_VERSION)
	private long version;
	/** Namespace for the data object */
	@SerializedName(ID_NAMESPACE)
	private String namespace;
	/** Actual payload */
	@SerializedName(ID_DATA)
	private Object data;


	public MetaWrapper(long version, Object data) {
		this(version, null, data);
	}


	public MetaWrapper(long version, String namespace, Object data) {
		this.version = version;
		this.namespace = namespace;
		this.data = data;
	}

}
