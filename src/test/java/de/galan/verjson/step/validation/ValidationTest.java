package de.galan.verjson.step.validation;

import static de.galan.commons.test.Tests.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.test.TestBean;
import de.galan.verjson.util.MetaWrapper;


/**
 * CUT Validation
 *
 * @author daniel
 */
public class ValidationTest extends AbstractTestParent {

	public Validation create(String schemaFile, String description) throws IOException {
		String schema = readFile(getClass(), schemaFile);
		return isNotEmpty(description) ? new Validation(schema, description) : new Validation(schema);
	}


	protected JsonNode readNode(String jsonFilename) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.readTree(readFile(getClass(), jsonFilename));
	}


	protected JsonNode createNode(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		MetaWrapper wrapper = new MetaWrapper(1L, null, obj, null);
		return mapper.valueToTree(wrapper);
	}


	@Test
	public void nullJson() throws Exception {
		Validation val = create("TestBean-schema-01.txt", "test");
		try {
			val.process(null);
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo("Could not validate JSON against schema (test)");
		}
	}


	@Test
	public void emptyJson() throws Exception {
		Validation val = create("TestBean-schema-01.txt", null);
		try {
			val.process(readNode("TestBean-json-empty.txt"));
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo(readFile(getClass(), "emptyJson-result.txt"));
		}
	}


	@Test
	public void simpleJson() throws Exception {
		TestBean bean = new TestBean().content("aaa").number(3L);
		Validation val = create("TestBean-schema-01.txt", "test");
		val.process(createNode(bean));
	}


	@Test
	public void invalidJson() throws Exception {
		TestBean bean = new TestBean().number(3L).unrecognized("blarg");
		Validation val = create("TestBean-schema-01.txt", "test");
		try {
			val.process(createNode(bean));
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo(readFile(getClass(), "invalidJson-result.txt"));
		}
	}


	@Test
	public void emptySchema() throws Exception {
		try {
			new Validation("", "empty");
			fail("schema should not be loaded");
		}
		catch (InvalidSchemaException ex) {
			assertThat(ex.getMessage()).isEqualTo("JSON Schema could not be loaded (empty)");
		}
	}


	@Test
	public void nullSchema() throws Exception {
		try {
			new Validation(null, "null");
			fail("schema should not be loaded");
		}
		catch (InvalidSchemaException ex) {
			assertThat(ex.getMessage()).isEqualTo("JSON Schema could not be loaded (null)");
		}
	}


	@Test
	public void invalidSchema() throws Exception {
		try {
			create("TestBean-schema-invalid.txt", null);
			fail("schema should not be loaded");
		}
		catch (InvalidSchemaException ex) {
			assertThat(ex.getMessage()).isEqualTo("JSON Schema is invalid");
		}
	}

}
