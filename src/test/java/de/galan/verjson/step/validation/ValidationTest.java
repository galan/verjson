package de.galan.verjson.step.validation;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.test.TestBean;


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
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.readTree(readFile(getClass(), jsonFilename));
	}


	protected JsonNode createNode(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.valueToTree(obj);
	}


	@Test
	public void nullJson() throws Exception {
		Validation val = create("TestBean-schema-01.txt", "test");
		try {
			val.process(null);
			fail("should be invalid");
		}
		catch (InvalidJsonException ex) {
			assertThat(ex.getMessage()).isEqualTo("Could not validate JSON against schema");
		}
	}


	@Test
	public void emptyJson() throws Exception {
		Validation val = create("TestBean-schema-01.txt", "test");
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

}
