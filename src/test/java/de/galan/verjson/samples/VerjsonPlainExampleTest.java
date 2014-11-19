package de.galan.verjson.samples;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.samples.v3.Example3;
import de.galan.verjson.samples.v3.Example3Versions;


/**
 * Write and read plain JSON documents. Since the metadata is missing in this documents, the mapping has to be done by
 * the user.<br/>
 * Write plain JSON document (Version 3), read plain version 1-3 example.
 *
 * @author daniel
 */
public class VerjsonPlainExampleTest extends AbstractTestParent {

	private Verjson<Example3> v3;


	@Before
	public void before() {
		v3 = Verjson.create(Example3.class, new Example3Versions());
		ApplicationClock.setIso("2014-05-06T06:42:28Z");
	}


	@Test
	public void writeExample3() throws Exception {
		String written = v3.writePlain(Example3.createSampleV3());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example03-plain-v3.json"));
	}


	@Test
	public void writeExample2() throws Exception {
		String written = v3.writePlain(Example3.createSampleV2());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-example03-plain-v2.json"));
	}


	@Test
	public void readExample2() throws Exception {
		JsonNode node = v3.readTree(readFile(getClass(), "sample-example-plain-02.json"));
		Example3 read = v3.readPlain(node, 2L);
		assertThat(read).isEqualTo(Example3.createSampleV2());
	}


	@Test
	public void readExample1() throws Exception {
		JsonNode node = v3.readTree(readFile(getClass(), "sample-example-plain-01.json"));
		Example3 read = v3.readPlain(node, 1L);
		assertThat(read).isEqualTo(Example3.createSampleV1());
	}

}
