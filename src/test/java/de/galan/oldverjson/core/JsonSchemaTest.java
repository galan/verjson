package de.galan.oldverjson.core;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.transformation.Versions;
import de.galan.oldverjson.util.MockValidator;
import de.galan.oldverjson.util.MockVersion;
import de.galan.verjson.test.TestBean;


/**
 * Test validation
 * 
 * @author daniel
 */
public class JsonSchemaTest extends AbstractTestParent {

	@Test
	public void schemaOnVersion1() throws Exception {
		Versions versions = new Versions();
		versions.registerInitialSchema(readFile(JsonSchemaTest.class, "TestBean-schema-01.json"));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).hasSize(0);
		assertThat(verjson.initialValidator).isNotNull();
		MockValidator validator = new MockValidator();
		verjson.initialValidator = validator;
		TestBean bean = new TestBean().content("abc").number(123L);
		String written = verjson.write(bean);
		TestBean read = verjson.read(written);
		assertThat(read).isEqualTo(bean);
		assertThat(validator.getLastContent()).isNotEmpty();
	}


	@Test
	public void schemaOnVersion1And2() throws Exception {
		Versions versions = new Versions();
		versions.registerInitialSchema(readFile(JsonSchemaTest.class, "TestBean-schema-01.json"));
		MockVersion version2 = new MockVersion(2L);
		versions.add(version2);
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).hasSize(1);
		assertThat(verjson.initialValidator).isNotNull();
		MockValidator validator1 = new MockValidator();
		verjson.initialValidator = validator1;
		MockValidator validator2 = new MockValidator();
		verjson.getContainers().get(1L).setValidator(validator2);
		TestBean bean = new TestBean().content("abc").number(123L);
		String written = verjson.write(bean);
		TestBean read = verjson.read(written);
		assertThat(read).isEqualTo(bean);
		assertThat(validator1.getLastContent()).isNull();
		//assertThat(validator2.getLastContent()).isNotEmpty();
	}

}
