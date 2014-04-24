package de.galan.verjson.step.validation;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.Lists;

import de.galan.verjson.step.Step;


/**
 * Creates a JSON Schema Validator to check json against it, using https://github.com/fge/json-schema-validator
 * 
 * @author daniel
 */
public class Validation implements Step {

	protected final static String LS = StandardSystemProperty.LINE_SEPARATOR.value();

	String description;
	JsonSchema schema; // thread-safe
	static JsonSchemaFactory factory; // cached


	public Validation(String schema) {
		this(schema, null);
	}


	public Validation(String schema, String description) {
		this.description = description;
		this.schema = create(schema);
	}


	public String getDescription() {
		return description;
	}


	protected String getDescriptionAppendable() {
		return isBlank(getDescription()) ? EMPTY : (" (" + getDescription() + ")");
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
		ProcessingReport report = null;
		try {
			report = getSchema().validate(node);
		}
		catch (Throwable ex) {
			throw new InvalidJsonException("Could not validate JSON against schema" + getDescriptionAppendable(), ex);
		}
		if (!report.isSuccess()) {

			StringBuilder builder = new StringBuilder();
			builder.append("Could not validate JSON against schema");
			builder.append(getDescriptionAppendable());
			builder.append(":");
			builder.append(LS);
			List<ProcessingMessage> messages = Lists.newArrayList(report);
			for (int i = 0; i < messages.size(); i++) {
				builder.append("- ");
				builder.append(messages.get(i).getMessage());
				builder.append(i == (messages.size() - 1) ? EMPTY : LS);
			}
			throw new InvalidJsonException(builder.toString());
		}
	}


	public JsonSchema create(String schemaString) {
		JsonSchema jsonSchema = null;
		try {
			JsonNode schemaNode = JsonLoader.fromString(schemaString);
			if (!getJsonSchemaFactory().getSyntaxValidator().schemaIsValid(schemaNode)) {
				throw new InvalidSchemaException("JSON Schema is invalid" + getDescriptionAppendable());
			}
			jsonSchema = getJsonSchemaFactory().getJsonSchema(schemaNode);
		}
		catch (NullPointerException | IOException | ProcessingException ex) {
			throw new InvalidSchemaException("JSON Schema could not be loaded" + getDescriptionAppendable(), ex);
		}
		return jsonSchema;
	}

}
