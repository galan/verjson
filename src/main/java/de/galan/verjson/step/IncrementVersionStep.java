package de.galan.verjson.step;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.access.MetaMapper;


/**
 * Increments the version field by one.
 *
 * @author daniel
 */
public class IncrementVersionStep implements Step {

	@Override
	public void process(JsonNode node, MetaMapper metaMapper) {
		Long version = metaMapper.getVersionReader().apply(node);
		metaMapper.getVersionIncrementer().accept(node, version++);
	}

}
