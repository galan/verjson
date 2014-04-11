package de.galan.verjson.step.transformation;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.Step;
import de.galan.verjson.util.MetaWrapper;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public abstract class Transformation implements Step {

	@Override
	public void process(JsonNode node) {
		transform(MetaWrapper.getData(node));
	}


	protected abstract void transform(JsonNode node);

}
