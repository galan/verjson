package de.galan.verjson.serializer;

import static de.galan.commons.time.Instants.*;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Serializes java.util.Date to String ISO (yyyy-MM-dd'T'HH:mm:ss.SSS'Z').
 *
 * @author daniel
 */
public class DateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(from(value).toStringUtc());
	}


	@Override
	public Class<Date> handledType() {
		return Date.class;
	}

}
