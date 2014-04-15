package de.galan.verjson.example;

import static de.galan.commons.test.Tests.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.core.Verjson;
import de.galan.oldverjson.example.v3.Example3;
import de.galan.oldverjson.example.v3.Example3VersionsOrder;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class VerjsonUsageOrderTest extends AbstractTestParent {

	private Verjson<Example3> v3;


	@Before
	public void before() {
		v3 = Verjson.create(Example3.class, new Example3VersionsOrder());
	}


	@Test
	public void writeExample3() throws Exception {
		String written = v3.write(Example3.createSampleV3());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example03-v3.json"));
	}


	@Test
	public void writeExample2() throws Exception {
		String written = v3.write(Example3.createSampleV2());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example03-v2.json"));
	}


	@Test
	public void readExample2() throws Exception {
		Example3 read = v3.read(readFile(getClass(), "sample-example02.json"));
		JsonFluentAssert.assertThatJson(read).isEqualTo(Example3.createSampleV2());
	}


	@Test
	public void readExample1() throws Exception {
		Example3 read = v3.read(readFile(getClass(), "sample-example01.json"));
		JsonFluentAssert.assertThatJson(read).isEqualTo(Example3.createSampleV1());
	}

}
