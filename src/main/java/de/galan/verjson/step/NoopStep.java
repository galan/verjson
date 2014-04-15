package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class NoopStep implements Step {

	@Override
	public void process(JsonNode node) {
		// nothing
	}

}
