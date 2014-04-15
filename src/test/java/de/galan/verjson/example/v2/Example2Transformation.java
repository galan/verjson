package de.galan.verjson.example.v2;

import static de.galan.verjson.util.Transformations.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.galan.verjson.step.transformation.Transformation;


/**
 * Sample transformer to migrate version 1 to version 2
 * 
 * @author daniel
 */
public class Example2Transformation extends Transformation {

	@Override
	protected void transform(JsonNode node) {
		ObjectNode obj = obj(node);
		// remove first
		remove(obj, "first");
		// rename second
		rename(obj, "second", "segundo");
		// keep third and fourth
		// convert fifth
		ArrayNode fifth = getArrayAndRemove(obj, "fifth");
		if (fifth != null) {
			StringBuilder builder = new StringBuilder();
			for (JsonNode elem: fifth) {
				builder.append(elem.asText());
			}
			obj.put("fifth", builder.toString());
		}
		// convert sixth
		ObjectNode sixth = getObjAndRemove(obj, "sixth");
		rename(sixth, "one", "uno");
		remove(sixth, "two");
		// rename SubB
		rename(obj(obj.get("subB")), "bbb", "ccc");
		obj.put("sixth", createArray(sixth));
	}

}
