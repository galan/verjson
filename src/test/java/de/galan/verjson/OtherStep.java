package de.galan.verjson;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.Step;


/**
 * Dummy Step for use in testcases
 * 
 * @author daniel
 */
public class OtherStep implements Step {

	@Override
	public void process(JsonNode node) {
		//nada
	}

}
