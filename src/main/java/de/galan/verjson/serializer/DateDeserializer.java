package de.galan.verjson.serializer;

import static de.galan.commons.time.DateDsl.*;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Deserializes java.util.Date from String ISO (yyyy-MM-dd'T'HH:mm:ss'Z') back to Date.
 * 
 * @author daniel
 */
public class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return dateIso(jp.getText());
	}

}
