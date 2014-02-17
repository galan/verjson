package de.galan.verjson.adapter;

import static de.galan.commons.time.DateDsl.*;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * Serializes/Deserializes Dates in form ISO8601
 * 
 * @author daniel
 */
public class GsonDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(from(src).toIso8601Utc());
	}


	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return from(dateIso(json.getAsString())).toDate();
	}

}
