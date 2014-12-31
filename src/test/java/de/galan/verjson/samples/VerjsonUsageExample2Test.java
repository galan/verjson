package de.galan.verjson.samples;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.samples.v2.Example2;
import de.galan.verjson.samples.v2.Example2Versions;


/**
 * Write Version 2, Read Version 1-2 Example
 *
 * @author daniel
 */
public class VerjsonUsageExample2Test extends AbstractTestParent {

	private Verjson<Example2> v2;


	@Before
	public void before() {
		v2 = Verjson.create(Example2.class, new Example2Versions());
		ApplicationClock.setUtc("2014-05-06T06:42:28Z");
	}


	@Test
	public void writeExample2() throws Exception {
		String written = v2.write(Example2.createSample());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example02.json"));
	}


	@Test
	public void readExample2() throws Exception {
		Example2 read = v2.read(readFile(getClass(), "sample-example02.json"));
		assertThat(read).isEqualTo(Example2.createSample());
	}


	@Test
	public void readExample1() throws Exception {
		Example2 read = v2.read(readFile(getClass(), "sample-example01.json"));
		assertThat(read).isEqualTo(Example2.createSampleV1());
	}

}
