package de.galan.verjson.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * Json Helper
 * 
 * @author daniel
 */
public class JsonObjBuilder {

	public static JsonObjBuilder create() {
		return new JsonObjBuilder();
	}

	JsonObject result = new JsonObject();


	public JsonObjBuilder add(String key, String value) {
		result.addProperty(key, value);
		return this;
	}


	public JsonObjBuilder add(String key, Long value) {
		result.addProperty(key, value);
		return this;
	}


	public JsonObjBuilder add(String key, JsonElement value) {
		result.add(key, value);
		return this;
	}


	public JsonObject get() {
		return result;
	}

}
