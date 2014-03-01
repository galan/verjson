package de.galan.verjson.validation.fge;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import de.galan.verjson.validation.Validator;
import de.galan.verjson.validation.ValidatorFactory;


/**
 * daniel should have written a comment here.
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
	public Validator create(String schema) {
		JsonSchema jsonSchema = null;
		try {
			JsonNode schemaNode = JsonLoader.fromString(schema);
			if (!factory.getSyntaxValidator().schemaIsValid(schemaNode)) {
				// TODO throw
			}
			jsonSchema = factory.getJsonSchema(schemaNode);
		}
		catch (IOException ex) {
			//result.addErrorMessage("Unable to load schema for type '" + type + "' from file '" + schemaFile + "'");
		}
		catch (ProcessingException ex) {
			//result.addErrorMessage("Unable to process schema for type '" + type + "'");
		}
		return new JsonSchemaValidator(jsonSchema);
	}

}
