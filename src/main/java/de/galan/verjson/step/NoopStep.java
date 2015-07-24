package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.access.MetaMapper;


/**
 * Step that does nothing.
 *
 * @author daniel
 */
public class NoopStep implements Step {

	@Override
	public void process(JsonNode node, MetaMapper metaMapper) {
		// nothing
	}

}
