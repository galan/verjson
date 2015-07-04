package de.galan.verjson.serializer;

import static de.galan.commons.time.Instants.*;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Deserializes String "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" back to java.time.Instant.
 *
 * @author daniel
 */
public class InstantSerializer extends JsonSerializer<Instant> {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZONE_UTC);


	@Override
	public void serialize(Instant value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(DTF.format(value));
	}


	@Override
	public Class<Instant> handledType() {
		return Instant.class;
	}

}
