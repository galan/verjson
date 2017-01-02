package de.galan.verjson.provider;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 */
public interface FieldProvider {

	/** Incremental version */
	public long getVersion(JsonNode node);


	/** Namespace for the data object */
	public String getNamespace(JsonNode node);


	/** Timestamp when the object was serialized */
	public Date getTimestamp(JsonNode node);


	/** Actual payload */
	public JsonNode getData(JsonNode node);

}
