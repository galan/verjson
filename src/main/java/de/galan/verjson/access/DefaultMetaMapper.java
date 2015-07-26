package de.galan.verjson.access;

import static de.galan.commons.time.Instants.*;
import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultMetaMapper implements MetaMapper {

	public static final String ID_VERSION = "$v";
	public static final String ID_NAMESPACE = "$ns";
	public static final String ID_DATA = "$d";
	public static final String ID_TIMESTAMP = "$ts";

	public static final Function<JsonNode, Long> DEFAULT_VERSION_READER = (node) -> obj(node).get(ID_VERSION).asLong();
	public static final BiConsumer<JsonNode, Long> DEFAULT_VERSION_INCREMENTER = (node, version) -> obj(node).put(ID_VERSION, version);
	public static final Function<JsonNode, String> DEFAULT_NAMESPACE_READER = (node) -> {
		JsonNode nodeNs = obj(node).get(ID_NAMESPACE);
		return (nodeNs != null) ? nodeNs.asText() : null;
	};
	public static final Function<JsonNode, JsonNode> DEFAULT_DATA_READER = (node) -> getObj(obj(node), ID_DATA);
	public static final PostMappingFunction DEFAULT_POSTMAPPING_FUNCTION = (mappedNode, version, namespace, timestamp) -> {
		ObjectNode wrapper = JsonNodeFactory.instance.objectNode();
		wrapper.put(ID_VERSION, version);
		if (isNotBlank(namespace)) {
			wrapper.put(ID_NAMESPACE, namespace);
		}
		if (timestamp != null) {
			wrapper.put(ID_TIMESTAMP, from(timestamp).toStringIsoUtc());
		}
		// wrap actual object
		wrapper.put(ID_DATA, mappedNode);
		return wrapper;
	};

	public Function<JsonNode, Long> versionReader = DEFAULT_VERSION_READER;
	public BiConsumer<JsonNode, Long> versionIncrementer = DEFAULT_VERSION_INCREMENTER;
	public Function<JsonNode, String> namespaceReader = DEFAULT_NAMESPACE_READER;
	public Function<JsonNode, JsonNode> dataReader = DEFAULT_DATA_READER;
	public PostMappingFunction postMappingFunction = DEFAULT_POSTMAPPING_FUNCTION;


	@Override
	public Function<JsonNode, Long> getVersionReader() {
		return versionReader;
	}


	@Override
	public void setVersionReader(Function<JsonNode, Long> versionReader) {
		this.versionReader = versionReader;
	}


	@Override
	public BiConsumer<JsonNode, Long> getVersionIncrementer() {
		return versionIncrementer;
	}


	@Override
	public void setVersionIncrementer(BiConsumer<JsonNode, Long> versionIncrementer) {
		this.versionIncrementer = versionIncrementer;
	}


	@Override
	public Function<JsonNode, String> getNamespaceReader() {
		return namespaceReader;
	}


	@Override
	public void setNamespaceReader(Function<JsonNode, String> namespaceReader) {
		this.namespaceReader = namespaceReader;
	}


	@Override
	public Function<JsonNode, JsonNode> getDataReader() {
		return dataReader;
	}


	@Override
	public void setDataReader(Function<JsonNode, JsonNode> dataReader) {
		this.dataReader = dataReader;
	}


	@Override
	public PostMappingFunction getPostMappingFunction() {
		return postMappingFunction;

	}


	@Override
	public void setPostMappingFunction(PostMappingFunction postMappingFunction) {
		this.postMappingFunction = postMappingFunction;
	}

}
