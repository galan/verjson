package de.galan.verjson.examples.simple;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;


/**
 * Note: I "cheated" in this example, it is not necessary to copy and/or rename the models (ExampleBean1/ExampleBean2).
 * This is done to keep seperate versions inside the same package and have a clear seperation for demonstration
 * purposes.
 *
 * @author daniel
 */
public class ExampleBeanTest extends AbstractTestParent {

	private Verjson<ExampleBean1> verjson1;
	private Verjson<ExampleBean2> verjson2;


	@Before
	public void before() {
		verjson1 = Verjson.create(ExampleBean1.class, null);
		verjson2 = Verjson.create(ExampleBean2.class, new ExampleBeanVersions());
	}


	@Test
	public void exampleBean1() throws Exception {
		ExampleBean1 bean1 = new ExampleBean1();
		bean1.text = "Hello";
		bean1.number = 42L;

		String serializedBean1 = verjson1.write(bean1);
		ExampleBean1 deserializedBean1 = verjson1.read(serializedBean1);

		assertThat(deserializedBean1.text).isEqualTo("Hello");
		assertThat(deserializedBean1.number).isEqualTo(42L);
	}


	@Test
	public void exampleBean2() throws Exception {
		ExampleBean2 bean2 = new ExampleBean2();
		bean2.texts = Lists.newArrayList("Hello");
		bean2.counter = 42L;

		String serializedBean2 = verjson2.write(bean2);
		ExampleBean2 deserializedBean2 = verjson2.read(serializedBean2);

		assertThat(deserializedBean2.texts).containsSequence("Hello");
		assertThat(deserializedBean2.counter).isEqualTo(42L);
	}


	@Test
	public void exampleBean1To2() throws Exception {
		ExampleBean1 bean1 = new ExampleBean1();
		bean1.text = "Hello";
		bean1.number = 42L;

		String serializedBean1 = verjson1.write(bean1);
		ExampleBean2 deserializedBean2 = verjson2.read(serializedBean1);

		assertThat(deserializedBean2.texts).containsSequence("Hello");
		assertThat(deserializedBean2.counter).isEqualTo(42L);
	}

}
