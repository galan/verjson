package de.galan.oldverjson.validation.fge;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.validation.InvalidJsonException;
import de.galan.oldverjson.validation.Validator;
import de.galan.verjson.test.TestBean;


/**
 * CUT JsonSchemaValidator
 * 
 * @author daniel
 */
public class JsonSchemaValidatorTest extends AbstractTestParent {

	public Validator create(String schema, String description) throws IOException {
		JsonSchemaValidatorFactory factory = new JsonSchemaValidatorFactory();
		return factory.create(readFile(getClass(), schema), description);
	}


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


	@Ignore
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

}
