package de.galan.verjson.access;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * Access to the actual metadata in the json to read/write. Behaviour can be overwritten to allow different
 * datastructures, such as eg. a flat model, where the version is on the same level as the payload.
 *
 * @author daniel
 */
public interface MetaMapper {

	public Function<JsonNode, Long> getVersionReader();


	public void setVersionReader(Function<JsonNode, Long> versionReader);


	public BiConsumer<JsonNode, Long> getVersionIncrementer();


	public void setVersionIncrementer(BiConsumer<JsonNode, Long> versionIncrementer);


	public Function<JsonNode, String> getNamespaceReader();


	public void setNamespaceReader(Function<JsonNode, String> namespaceReader);


	public Function<JsonNode, JsonNode> getDataReader();


	public void setDataReader(Function<JsonNode, JsonNode> dataReader);


	public PostMappingFunction getPostMappingFunction();


	public void setPostMappingFunction(PostMappingFunction postMappingFunction);

}
