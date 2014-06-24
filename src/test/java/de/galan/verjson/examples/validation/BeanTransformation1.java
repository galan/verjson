package de.galan.verjson.examples.validation;

import static de.galan.verjson.util.Transformations.*;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.verjson.step.transformation.Transformation;


/**
 * Transformation does not change the model structural, but will change semantical now required field to default value
 * if not present.
 *
 * @author daniel
 */
public class BeanTransformation1 extends Transformation {

	@Override
	protected void transform(JsonNode node) {
		if (!obj(node).has("content")) {
			obj(node).put("content", "default");
		}
	}

}
