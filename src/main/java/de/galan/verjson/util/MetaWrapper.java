package de.galan.verjson.util;

import static de.galan.verjson.util.Transformations.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


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
	@JsonProperty(ID_VERSION)
	private long version;
	/** Namespace for the data object */
	//@JsonInclude(Include.NON_NULL)
	@JsonProperty(ID_NAMESPACE)
	private String namespace;
	/** Actual payload */
	@JsonProperty(ID_DATA)
	private Object data;


	public MetaWrapper(long version, String namespace, Object data) {
		this.version = version;
		this.namespace = namespace;
		this.data = data;
	}


	public static JsonNode getData(JsonNode node) {
		return getObj(obj(node), MetaWrapper.ID_DATA);
	}


	public static String getNamespace(JsonNode node) {
		JsonNode nodeNs = obj(node).get(ID_NAMESPACE);
		return (nodeNs != null) ? nodeNs.asText() : null;
	}


	public static Long getVersion(JsonNode node) {
		return obj(node).get(ID_VERSION).asLong();
	}


	public static void setVersion(JsonNode node, Long version) {
		obj(node).put(ID_VERSION, version);
	}

}
