package de.galan.verjson.validation.fge;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import de.galan.verjson.validation.InvalidSchemaException;
import de.galan.verjson.validation.Validator;
import de.galan.verjson.validation.ValidatorFactory;


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
		catch (IOException ex) {
			throw new InvalidSchemaException("JSON Schema could not be loaded (" + description + ")", ex);
		}
		catch (ProcessingException ex) {
			throw new InvalidSchemaException("JSON Schema could not be processed(" + description + ")", ex);
		}
		return new JsonSchemaValidator(jsonSchema);
	}

}
