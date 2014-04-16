package de.galan.verjson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchema;

import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class DummyValidation extends Validation {

	public DummyValidation(String schema, String description) {
		super(schema, description);
	}


	public DummyValidation(String schema) {
		super(schema);
	}


	@Override
	public void process(JsonNode node) {
		// nothing
	}


	@Override
	public JsonSchema create(String schemaString) {
		return null;
	}

}
