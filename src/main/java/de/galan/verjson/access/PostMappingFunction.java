package de.galan.verjson.access;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface PostMappingFunction {

	public JsonNode postMapNode(JsonNode mappedNode, long version, String namespace, Date timestamp);

}
