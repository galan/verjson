package de.galan.verjson.serializer;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Deserializes java.time.Instant from String "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" back to Instant.
 *
 * @author daniel
 */
public class InstantDeserializer extends JsonDeserializer<Instant> {

	@Override
	public Instant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return Instant.parse(jp.getText());
	}

}
