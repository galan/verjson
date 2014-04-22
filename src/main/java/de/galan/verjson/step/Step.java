package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * A Step that performs an action on the root JsonNode (MetaWrapper). Currently only Validation and Transformation are
 * supported.
 * 
 * @author daniel
 */
public interface Step {

	/** Perform action on wrapped root node */
	public void process(JsonNode node);

}
