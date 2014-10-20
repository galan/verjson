package de.galan.verjson.serializer;

import static de.galan.commons.time.Instants.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Deserializes String "yyyy-MM-dd'T'HH:mm:ss'Z'" back to java.time.ZonedDateTime.
 *
 * @author daniel
 */
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZONE_UTC);


	@Override
	public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(DTF.format(value));
	}


	@Override
	public Class<ZonedDateTime> handledType() {
		return ZonedDateTime.class;
	}

}
