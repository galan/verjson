package de.galan.verjson.examples.validation;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.test.TestBean;


/**
 * daniel should have written a comment here.
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
	public void testName() throws Exception {
		verjson = Verjson.create(TestBean.class, null);
		//TODO
	}

}
