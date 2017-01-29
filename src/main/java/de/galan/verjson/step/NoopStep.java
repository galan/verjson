package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.fieldprovider.FieldProvider;


/**
 * Step that does nothing.
 *
 * @author daniel
 */
public class NoopStep implements Step {

	@Override
	public void process(JsonNode node, FieldProvider fieldProvider) {
		// nothing
	}

}
