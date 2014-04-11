package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public interface Step {

	public void process(JsonNode node);

}
