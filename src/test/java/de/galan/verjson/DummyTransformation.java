package de.galan.verjson;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.transformation.Transformation;


/**
 * Dummy Transformation for use in testcases
 * 
 * @author daniel
 */
public class DummyTransformation extends Transformation {

	@Override
	protected void transform(JsonNode node) {
		//nada
	}

}
