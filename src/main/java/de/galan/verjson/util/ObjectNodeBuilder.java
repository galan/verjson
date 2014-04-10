package de.galan.verjson.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Json Helper
 * 
 * @author daniel
 */
public class ObjectNodeBuilder {

	public static ObjectNodeBuilder create() {
		return new ObjectNodeBuilder();
	}

	ObjectNode result = new ObjectNode(JsonNodeFactory.instance);


	public ObjectNodeBuilder put(String key, String value) {
		result.put(key, value);
		return this;
	}


	public ObjectNodeBuilder put(String key, Long value) {
		result.put(key, value);
		return this;
	}


	public ObjectNodeBuilder put(String key, JsonNode value) {
		result.put(key, value);
		return this;
	}


	public ObjectNode get() {
		return result;
	}

}
