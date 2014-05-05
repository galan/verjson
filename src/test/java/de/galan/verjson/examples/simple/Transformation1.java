package de.galan.verjson.examples.simple;

import static de.galan.verjson.util.Transformations.*;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.transformation.Transformation;


/**
 * Sample from the README.md
 *
 * @author daniel
 */
public class Transformation1 extends Transformation {

	@Override
	protected void transform(JsonNode node) {
		obj(node).put("texts", createArray(remove(obj(node), "text")));
		rename(obj(node), "number", "counter");
	}

}
