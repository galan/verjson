package de.galan.verjson.transformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public abstract class AbstractTransformation implements Transformation {

	protected JsonObject obj(JsonElement element) {
		return element != null ? element.getAsJsonObject() : null;
	}


	protected JsonObject getObj(JsonObject obj, String fieldName) {
		return obj == null ? null : obj(obj.get(fieldName));
	}


	protected JsonObject getObjAndRemove(JsonObject obj, String fieldName) {
		JsonObject result = null;
		if (obj != null) {
			result = getObj(obj, fieldName);
			remove(obj, fieldName);
		}
		return result;
	}


	protected JsonArray createArray(JsonElement... elements) {
		JsonArray array = new JsonArray();
		for (JsonElement element: elements) {
			array.add(element);
		}
		return array;
	}


	protected JsonArray array(JsonElement element) {
		return element != null ? element.getAsJsonArray() : null;
	}


	protected JsonArray getArray(JsonObject obj, String fieldName) {
		return obj == null ? null : array(obj.get(fieldName));
	}


	protected JsonArray getArrayAndRemove(JsonObject obj, String fieldName) {
		JsonArray result = null;
		if (obj != null) {
			result = getArray(obj, fieldName);
			remove(obj, fieldName);
		}
		return result;
	}


	protected void remove(JsonObject obj, String fieldNameToRemove) {
		obj.remove(fieldNameToRemove);
	}


	protected void rename(JsonObject obj, String oldFieldName, String newFieldName) {
		JsonElement elem = obj.get(oldFieldName);
		obj.remove(oldFieldName);
		obj.add(newFieldName, elem);
	}

}
