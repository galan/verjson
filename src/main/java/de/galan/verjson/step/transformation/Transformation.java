package de.galan.verjson.step.transformation;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.Step;
import de.galan.verjson.util.MetaWrapper;


/**
 * Transforms a serialized object from one version to the next, using the underlying json representation. Static helper
 * methods are available in the Transformations class.
 * 
 * @author daniel
 */
public abstract class Transformation implements Step {

	@Override
	public void process(JsonNode node) {
		transform(MetaWrapper.getData(node));
	}


	/** Transformation instructions to migrate to the next version */
	protected abstract void transform(JsonNode node);

}
