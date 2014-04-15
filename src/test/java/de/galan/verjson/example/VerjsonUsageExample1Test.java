package de.galan.verjson.example;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.example.v1.Example1;
import de.galan.verjson.example.v1.Example1Versions;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class VerjsonUsageExample1Test extends AbstractTestParent {

	private Verjson<Example1> v1;


	@Before
	public void before() {
		v1 = Verjson.create(Example1.class, new Example1Versions());
	}


	@Test
	public void writeExample1() throws Exception {
		String written = v1.write(Example1.createSample());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example01.json"));
	}


	@Test
	public void readExample1() throws Exception {
		Example1 read = v1.read(readFile(getClass(), "sample-example01.json"));
		assertThat(read).isEqualTo(Example1.createSample());
	}

}
