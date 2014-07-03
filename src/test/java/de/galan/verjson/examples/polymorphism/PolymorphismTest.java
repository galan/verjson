package de.galan.verjson.examples.polymorphism;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;


/**
 * Demonstrates working with polymorh types.
 *
 * @author daniel
 */
public class PolymorphismTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		Bean bean = new Bean();
		ChildB childB = new ChildB();
		childB.valueFromParent = "parentB";
		childB.valueFromB = "valueB";
		bean.parent = childB;

		//TODO
		Verjson<Bean> verjson = Verjson.create(Bean.class, new PolymorphismVersions());
		String written = verjson.write(bean);
		Bean read = verjson.read(written);
		assertThat(read).isNotNull();
	}

}
