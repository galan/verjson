package de.galan.verjson.core;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.StrictAssertions.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.test.MyContainer;
import de.galan.verjson.test.TestBean;


/**
 * CUT Verjson - plugging external serializer/deserializer into Verjson
 *
 * @author daniel
 */
public class VerjsonSerializerTest extends AbstractTestParent {

	private Verjson<MyContainer> verjson;


	@Before
	public void before() throws Exception {
		Versions versions = new Versions();
		versions.registerSerializer(new TestBeanSerializer());
		versions.registerDeserializer(new TestBeanDeserializer());
		verjson = new Verjson<MyContainer>(MyContainer.class, versions);
	}


	@Test
	public void testWithoutMs() throws Exception {
		ApplicationClock.setUtc("2014-05-06T06:42:28Z");
		String written = verjson.write(new MyContainer(new TestBean().number(123L).content("abc")));
		assertThat(written).isEqualTo(readFile(getClass(), "serializer-written-01.json"));
	}


	@Test
	public void testWithMs() throws Exception {
		ApplicationClock.setUtc("2014-01-02T08:23:10.987Z");
		String written = verjson.write(new MyContainer(new TestBean().number(123L).content("abc")));
		assertThat(written).isEqualTo(readFile(getClass(), "serializer-written-02.json"));
	}

}


/** Test Serialzer */
class TestBeanSerializer extends JsonSerializer<TestBean> {

	@Override
	public void serialize(TestBean value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.number + ":" + value.content);
	}


	@Override
	public Class<TestBean> handledType() {
		return TestBean.class;
	}

}


/** Test Serialzer */
class TestBeanDeserializer extends JsonDeserializer<TestBean> {

	@Override
	public TestBean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String[] split = jp.getText().split(":");
		return new TestBean().number(Long.valueOf(split[0])).content(split[1]);
	}

}
