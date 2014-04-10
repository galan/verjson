package de.galan.oldverjson.validation.fge;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;

import de.galan.oldverjson.validation.InvalidJsonException;
import de.galan.oldverjson.validation.Validator;


/**
 * Validates a schema using https://github.com/fge/json-schema-validator
 * 
 * @author daniel
 */
public class JsonSchemaValidator implements Validator {

	private JsonSchema schema;


	public JsonSchemaValidator(JsonSchema schema) {
		this.schema = schema;
	}


	protected JsonSchema getSchema() {
		return schema;
	}


	@Override
	public void validate(String content) {
		try {
			JsonNode jsonToValidate = JsonLoader.fromString(content);
			ProcessingReport report = getSchema().validate(jsonToValidate);
			//for (ProcessingMessage message: report) {
			//}
			if (!report.isSuccess()) {
				throw new InvalidJsonException(report.toString());
			}
		}
		catch (IOException | ProcessingException ex) {
			throw new InvalidJsonException("Could not validate JSON against schema", ex);
		}
	}

}
