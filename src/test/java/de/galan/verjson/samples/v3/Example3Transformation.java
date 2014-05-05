package de.galan.verjson.samples.v3;

import static de.galan.verjson.util.Transformations.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.galan.verjson.step.transformation.Transformation;


/**
 * Sample transformer to migrate version 2 to version 3
 * 
 * @author daniel
 */
public class Example3Transformation extends Transformation {

	@Override
	protected void transform(JsonNode node) {
		ObjectNode obj = obj(node);
		rename(obj, "empty", "filled");
		// rename SubB
		rename(obj(obj.get("subB")), "ccc", "bbb");
	}

}
