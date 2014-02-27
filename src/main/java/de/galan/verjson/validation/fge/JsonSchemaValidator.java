package de.galan.verjson.validation.fge;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;

import de.galan.verjson.validation.Validator;


/**
 * daniel should have written a comment here.
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
	public boolean validate(String content) {
		boolean result = true;
		try {
			JsonNode jsonToValidate = JsonLoader.fromString(content);
			ProcessingReport report = getSchema().validate(jsonToValidate);
			for (ProcessingMessage message: report) {
				//result.addErrorMessage(message.getMessage());
				// TODO
				result = false;
			}
		}
		catch (IOException ex) {
			result = false;
			//result.addErrorMessage("Unable to parse json from content '" + content + "'");
		}
		catch (ProcessingException ex) {
			result = false;
			//result.addErrorMessage("Unable to validate json for type '" + type + "' and content '" + content + "'");
		}
		return result;
	}

}
