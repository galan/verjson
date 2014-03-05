package de.galan.verjson.validation.fge;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.validation.InvalidSchemaException;
import de.galan.verjson.validation.fge.JsonSchemaValidatorFactory;


/**
 * CUT JsonSchemaValidatorFactory
 * 
 * @author daniel
 */
public class JsonSchemaValidatorFactoryTest extends AbstractTestParent {

	private JsonSchemaValidatorFactory factory;


	@Before
	public void before() {
		factory = new JsonSchemaValidatorFactory();
	}


	@Test
	public void emptySchema() throws Exception {
		try {
			factory.create(readFile(getClass(), "TestBean-schema-empty.txt"), "test");
			fail("schema should not be loaded");
		}
		catch (InvalidSchemaException ex) {
			assertThat(ex.getMessage()).isEqualTo("JSON Schema could not be loaded (test)");
		}
	}

}
