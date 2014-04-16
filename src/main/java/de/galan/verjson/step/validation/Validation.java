package de.galan.verjson.step.validation;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.Iterables;

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
			if (!report.isSuccess()) {
				String ls = StandardSystemProperty.LINE_SEPARATOR.value();

				Iterable<String> messages = Iterables.transform(report, new Function<ProcessingMessage, String>() {

					@Override
					public String apply(ProcessingMessage input) {
						return (input == null) ? null : input.getMessage();
					}
				});
				String messagesString = Joiner.on(ls + "- ").skipNulls().join(messages);

				StringBuilder builder = new StringBuilder();
				builder.append("Could not validate JSON against schema:");
				builder.append(ls);
				builder.append("- ");
				builder.append(messagesString);
				throw new InvalidJsonException(builder.toString());
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
