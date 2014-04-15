package de.galan.verjson.step.validation;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import de.galan.verjson.step.Step;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class Validation implements Step {

	JsonSchema schema; // thread-safe
	static JsonSchemaFactory factory; // cached


	public Validation(String schema) {
		this(schema, null);
	}


	public Validation(String schema, String description) {
		this.schema = create(schema, description);
	}


	protected static synchronized JsonSchemaFactory getFactory() {
		if (factory == null) {
			factory = JsonSchemaFactory.byDefault();
		}
		return factory;
	}


	protected JsonSchemaFactory getJsonSchemaFactory() {
		return getFactory();
	}


	@Override
	public void process(JsonNode node) {
		validate(node);
	}


	protected JsonSchema getSchema() {
		return schema;
	}


	public void validate(JsonNode node) {
		try {
			ProcessingReport report = getSchema().validate(node);
			//for (ProcessingMessage message: report) {
			//}
			if (!report.isSuccess()) {
				throw new InvalidJsonException(report.toString());
			}
		}
		catch (ProcessingException ex) {
			throw new InvalidJsonException("Could not validate JSON against schema", ex);
		}
	}


	public JsonSchema create(String schemaString, String description) {
		JsonSchema jsonSchema = null;
		try {
			JsonNode schemaNode = JsonLoader.fromString(schemaString);
			if (!getJsonSchemaFactory().getSyntaxValidator().schemaIsValid(schemaNode)) {
				throw new InvalidSchemaException("JSON Schema is invalid (" + description + ")");
			}
			jsonSchema = getJsonSchemaFactory().getJsonSchema(schemaNode);
		}
		catch (IOException | ProcessingException ex) {
			throw new InvalidSchemaException("JSON Schema could not be loaded (" + description + ")", ex);
		}
		return jsonSchema;
	}

}
