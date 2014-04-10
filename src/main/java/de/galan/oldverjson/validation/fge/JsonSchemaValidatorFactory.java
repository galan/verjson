package de.galan.oldverjson.validation.fge;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import de.galan.oldverjson.validation.InvalidSchemaException;
import de.galan.oldverjson.validation.Validator;
import de.galan.oldverjson.validation.ValidatorFactory;


/**
 * Creates a JSON Schema Validator classes for https://github.com/fge/json-schema-validator
 * 
 * @author daniel
 */
public class JsonSchemaValidatorFactory implements ValidatorFactory {

	JsonSchemaFactory factory;


	public JsonSchemaValidatorFactory() {
		initialize();
	}


	protected void initialize() {
		factory = JsonSchemaFactory.byDefault();
	}


	@Override
	public Validator create(String schema, String description) {
		JsonSchema jsonSchema = null;
		try {
			JsonNode schemaNode = JsonLoader.fromString(schema);
			if (!factory.getSyntaxValidator().schemaIsValid(schemaNode)) {
				throw new InvalidSchemaException("JSON Schema is invalid(" + description + ")");
			}
			jsonSchema = factory.getJsonSchema(schemaNode);
		}
		catch (IOException | ProcessingException ex) {
			throw new InvalidSchemaException("JSON Schema could not be loaded (" + description + ")", ex);
		}
		return new JsonSchemaValidator(jsonSchema);
	}

}
