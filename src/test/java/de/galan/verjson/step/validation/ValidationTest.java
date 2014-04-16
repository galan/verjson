package de.galan.verjson.step.validation;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class ValidationTest extends AbstractTestParent {

	public Validation create(String schemaFile, String description) throws IOException {
		return new Validation(readFile(getClass(), schemaFile), description);
	}


	protected JsonNode readNode(String jsonFilename) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(readFile(getClass(), jsonFilename));
	}


	@Test
	public void emptyJson() throws Exception {
		Validation val = create("TestBean-schema-01.txt", "test");
		try {
			val.process(readNode("TestBean-json-empty.txt"));
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo(
				"Could not validate JSON against schema:\n- object has missing required properties ([\"content\",\"number\"])");
		}
	}
	/*

	@Test
	public void simpleJSsn() throws Exception {
		TestBean bean = new TestBean().content("aaa").number(3L);
		Validator validator = create("TestBean-schema-01.txt", "test");
		String json = new Gson().toJson(bean);
		validator.validate(json);
	}


	@Test
	public void emptyJson() throws Exception {
		Validator validator = create("TestBean-schema-01.txt", "test");
		try {
			validator.validate(readFile(getClass(), "TestBean-json-empty.txt"));
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo("Could not validate JSON against schema");
		}
	}


	@Test
	public void invalidJson() throws Exception {
		TestBean bean = new TestBean().number(3L);
		Validator validator = create("TestBean-schema-01.txt", "test");
		String json = new Gson().toJson(bean);
		try {
			validator.validate(json);
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo(readFile(getClass(), "invalidJson.txt"));
		}
	}


	@Test
	public void brokenJson() throws Exception {
		TestBean bean = new TestBean().number(3L);
		Validator validator = create("TestBean-schema-01.txt", "test");
		String json = new Gson().toJson(bean).replace("}", "");
		try {
			validator.validate(json);
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo("Could not validate JSON against schema");
		}
	}
	*/

}
