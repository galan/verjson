package de.galan.verjson.examples.validation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.IOReadException;
import de.galan.verjson.core.NamespaceMismatchException;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.core.VersionNotSupportedException;
import de.galan.verjson.test.TestBean;


/**
 * This example test shows how to use validators. We have two versions and two different JSON schemas. The
 * transformation itself does not have structural changes but semantical - the now required content-field will get
 * default content if empty.
 *
 * @author daniel
 */
public class ExampleValidationTest extends AbstractTestParent {

	private Verjson<TestBean> verjson;


	@Before
	public void before() {
		verjson = Verjson.create(TestBean.class, new ExampleValidationVersions());
	}


	@Test
	public void version2Valid() throws Exception {
		TestBean bean = new TestBean().content("abc").number(123L);
		String written = verjson.write(bean);
		// {"$v":2,"$d":{"content":"abc","number":123}}
		TestBean read = verjson.read(written);
		assertThat(read).isEqualTo(bean);
	}


	@Test
	public void version2Invalid() {
		TestBean bean = new TestBean().number(123L); // No content-field, but required in schema
		try {
			String written = verjson.write(bean);
			// {"$v":2,"$d":{"number":123}}
			verjson.read(written);
		}
		catch (JsonProcessingException | VersionNotSupportedException | NamespaceMismatchException | IOReadException ex) {
			fail("Not expected");
		}
		catch (RuntimeException ex) {
			assertThat(ex.getMessage()).isEqualTo("Could not validate JSON against schema:\n- object has missing required properties ([\"content\"])");
		}
	}

}
