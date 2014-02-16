package de.galan.verjson.v2;

import static org.apache.commons.lang3.StringUtils.*;

import com.google.gson.annotations.SerializedName;


/**
 * Nests the object to be serialized, adding meta information such as the current version number and namespace.
 * 
 * @author daniel
 */
public class MetaWrapper {

	/** Incremental version */
	@SerializedName("$v")
	private long version;
	/** Namespace for the data object */
	@SerializedName("$ns")
	private String namespace;
	/** Actual payload */
	@SerializedName("$d")
	private Object data;


	public MetaWrapper(long version, Object data) {
		this(version, null, data);
	}


	public MetaWrapper(long version, String namespace, Object data) {
		this.version = version;
		this.namespace = namespace;
		this.data = data;
	}


	public long getVersion() {
		return version;
	}


	public String getNamespace() {
		return namespace;
	}


	public boolean hasNs() {
		return isNotBlank(getNamespace());
	}


	public Object getDate() {
		return data;
	}

}
