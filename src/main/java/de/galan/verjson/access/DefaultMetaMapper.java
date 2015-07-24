package de.galan.verjson.access;

import static de.galan.commons.time.Instants.*;
import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Date;
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


	@Override
	public Function<JsonNode, Long> getVersionReader() {
		return (node) -> obj(node).get(ID_VERSION).asLong();
	}


	@Override
	public BiConsumer<JsonNode, Long> getVersionIncrementer() {
		return (node, version) -> obj(node).put(ID_VERSION, version);
	}


	@Override
	public Function<JsonNode, String> getNamespaceReader() {
		return (node) -> {
			JsonNode nodeNs = obj(node).get(ID_NAMESPACE);
			return (nodeNs != null) ? nodeNs.asText() : null;
		};
	}


	@Override
	public JsonNode postMapNode(JsonNode mappedNode, long version, String namespace, Date timestamp) {
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
	}


	@Override
	public Function<JsonNode, JsonNode> getDataReader() {
		return (node) -> getObj(obj(node), ID_DATA);
	}

}
