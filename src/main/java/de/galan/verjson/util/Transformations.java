package de.galan.verjson.util;

import static org.apache.commons.lang3.StringUtils.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Provides common helpful methods for transforming JsonNodes.
 * 
 * @author daniel
 */
public class Transformations {

	/** Returns the given node as ObjectNode (cast). */
	public static ObjectNode obj(JsonNode node) {
		return node != null ? (ObjectNode)node : null;
	}


	/** Returns the field from a ObjectNode as ObjectNode */
	public static ObjectNode getObj(ObjectNode obj, String fieldName) {
		return obj != null ? obj(obj.get(fieldName)) : null;
	}


	/** Removes the field from a ObjectNode and returns it as ObjectNode */
	public static ObjectNode getObjAndRemove(ObjectNode obj, String fieldName) {
		ObjectNode result = null;
		if (obj != null) {
			result = obj(remove(obj, fieldName));
		}
		return result;
	}


	/** Creates a ArrayNode from the given elements, returns null if no elements are provided */
	public static ArrayNode createArray(JsonNode... nodes) {
		return createArray(false, nodes);
	}


	/**
	 * Creates an ArrayNode from the given nodes. Returns an empty ArrayNode if no elements are provided and
	 * fallbackToEmptyArray is true, null if false.
	 */
	public static ArrayNode createArray(boolean fallbackToEmptyArray, JsonNode... nodes) {
		ArrayNode array = null;
		for (JsonNode element: nodes) {
			if (element != null) {
				if (array == null) {
					array = new ArrayNode(JsonNodeFactory.instance);
				}
				array.add(element);
			}
		}
		if ((array == null) && fallbackToEmptyArray) {
			array = new ArrayNode(JsonNodeFactory.instance);
		}
		return array;
	}


	/** Returns the given element as ArrayNode (cast). */
	public static ArrayNode array(JsonNode node) {
		return node != null ? (ArrayNode)node : null;
	}


	/** Returns the field from a ObjectNode as ArrayNode */
	public static ArrayNode getArray(ObjectNode obj, String fieldName) {
		return obj == null ? null : array(obj.get(fieldName));
	}


	/** Removes the field from a ObjectNode and returns it as ArrayNode */
	public static ArrayNode getArrayAndRemove(ObjectNode obj, String fieldName) {
		ArrayNode result = null;
		if (obj != null) {
			result = array(remove(obj, fieldName));
		}
		return result;
	}


	/** Removes the field with the given name from the given ObjectNode */
	public static JsonNode remove(ObjectNode obj, String fieldName) {
		JsonNode result = null;
		if (obj != null) {
			result = obj.remove(fieldName);
		}
		return result;
	}


	/** Renames a field in a ObjectNode from the oldFieldName to the newFieldName */
	public static void rename(ObjectNode obj, String oldFieldName, String newFieldName) {
		if (obj != null && isNotBlank(oldFieldName) && isNotBlank(newFieldName)) {
			JsonNode node = remove(obj, oldFieldName);
			if (node != null) {
				obj.put(newFieldName, node);
			}
		}
	}

}
