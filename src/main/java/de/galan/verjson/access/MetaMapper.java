package de.galan.verjson.access;

import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface MetaMapper {

	public Function<JsonNode, Long> getVersionReader();


	public BiConsumer<JsonNode, Long> getVersionIncrementer();


	public Function<JsonNode, String> getNamespaceReader();


	public JsonNode postMapNode(JsonNode mappedNode, long version, String namespace, Date timestamp);


	public Function<JsonNode, JsonNode> getDataReader();

}
