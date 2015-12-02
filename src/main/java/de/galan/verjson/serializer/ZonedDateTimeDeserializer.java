package de.galan.verjson.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Deserializes java.time.ZonedDateTime from String "yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'" back to ZonedDateTime.
 *
 * @author daniel
 */
public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return ZonedDateTime.parse(jp.getText());
	}

}
