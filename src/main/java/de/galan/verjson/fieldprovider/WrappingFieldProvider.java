package de.galan.verjson.fieldprovider;

import static de.galan.commons.time.Instants.*;
import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.util.MetaWrapper;


/**
 * daniel should have written a comment here.
 */
public class WrappingFieldProvider implements FieldProvider {

	public static final String ID_VERSION = "$v";
	public static final String ID_NAMESPACE = "$ns";
	public static final String ID_DATA = "$d";
	public static final String ID_TIMESTAMP = "$ts";


	@Override
	public long getVersion(JsonNode node) {
		return obj(node).get(ID_VERSION).asLong();
	}


	@Override
	public String getNamespace(JsonNode node) {
		JsonNode nodeNs = obj(node).get(ID_NAMESPACE);
		return (nodeNs != null) ? nodeNs.asText() : null;
	}


	@Override
	public Date getTimestamp(JsonNode node) {
		String text = obj(node).get(ID_TIMESTAMP).asText();
		return isNotBlank(text) ? from(instantUtc(text)).toDate() : null;
	}


	@Override
	public JsonNode getData(JsonNode node) {
		return getObj(obj(node), MetaWrapper.ID_DATA);
	}

}
