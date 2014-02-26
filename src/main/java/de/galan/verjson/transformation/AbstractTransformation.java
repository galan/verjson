package de.galan.verjson.transformation;

import static org.apache.commons.lang3.StringUtils.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * Provides common helpful methods fro transforming JsonElements.
 * 
 * @author daniel
 */
public abstract class AbstractTransformation implements Transformation {

	/** Returns the given element as JsonObject (calls getAsJsonObject()). */
	protected JsonObject obj(JsonElement element) {
		return element != null ? element.getAsJsonObject() : null;
	}


	/** Returns the field from a JsonObject as JsonObject */
	protected JsonObject getObj(JsonObject obj, String fieldName) {
		return obj == null ? null : obj(obj.get(fieldName));
	}


	/** Removes the field from a JsonObject and returns it as JsonObject */
	protected JsonObject getObjAndRemove(JsonObject obj, String fieldName) {
		JsonObject result = null;
		if (obj != null) {
			result = getObj(obj, fieldName);
			remove(obj, fieldName);
		}
		return result;
	}


	/** Creates a JsonArray from the given elements, returns null if no elements are provided */
	protected JsonArray createArray(JsonElement... elements) {
		return createArray(false, elements);
	}


	/**
	 * Creates a JsonArray from the given elements. Returns an empty JsonArray if no elements are provided and
	 * fallbackToEmptyArray is true, null if false.
	 */
	protected JsonArray createArray(boolean fallbackToEmptyArray, JsonElement... elements) {
		JsonArray array = null;
		for (JsonElement element: elements) {
			if (element != null) {
				if (array == null) {
					array = new JsonArray();
				}
				array.add(element);
			}
		}
		if ((array == null) && fallbackToEmptyArray) {
			array = new JsonArray();
		}
		return array;
	}


	/** Returns the given element as JsonArray (calls getAsJsonArray()). */
	protected JsonArray array(JsonElement element) {
		return element != null ? element.getAsJsonArray() : null;
	}


	/** Returns the field from a JsonObject as JsonArray */
	protected JsonArray getArray(JsonObject obj, String fieldName) {
		return obj == null ? null : array(obj.get(fieldName));
	}


	/** Removes the field from a JsonObject and returns it as JsonArray */
	protected JsonArray getArrayAndRemove(JsonObject obj, String fieldName) {
		JsonArray result = null;
		if (obj != null) {
			result = getArray(obj, fieldName);
			remove(obj, fieldName);
		}
		return result;
	}


	/** Removes the field with the given name from the given JsonObject */
	protected void remove(JsonObject obj, String fieldNameToRemove) {
		if (obj != null) {
			obj.remove(fieldNameToRemove);
		}
	}


	/** Renames a field in a JsonObject from the oldFieldName to the newFieldName */
	protected void rename(JsonObject obj, String oldFieldName, String newFieldName) {
		if (obj != null && isNotBlank(oldFieldName) && isNotBlank(newFieldName)) {
			JsonElement elem = obj.get(oldFieldName);
			if (elem != null) {
				obj.remove(oldFieldName);
				obj.add(newFieldName, elem);
			}
		}
	}

}
