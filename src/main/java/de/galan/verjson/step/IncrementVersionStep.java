package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.util.MetaWrapper;


/**
 * Increments the version field by one.
 * 
 * @author daniel
 */
public class IncrementVersionStep implements Step {

	@Override
	public void process(JsonNode node) {
		Long version = MetaWrapper.getVersion(node);
		MetaWrapper.setVersion(node, version++);
	}

}
