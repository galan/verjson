package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Type;

import org.junit.Test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.util.MockVersions;
import de.galan.verjson.util.TestBean;


/**
 * CUT Versions
 * 
 * @author daniel
 */
public class VersionsTest extends AbstractTestParent {

	@Test
	public void configureCalled() throws Exception {
		MockVersions versions = new MockVersions();
		new Verjson<>(TestBean.class, versions);
		assertThat(versions.isConfigured()).isTrue();
	}


	@Test
	public void registerTypeAdapter() throws Exception {
		Versions versions = new Versions();
		versions.registerTypeAdapter(String.class, new FakeAdapter());
		Verjson<TestBean> verjson = new Verjson<>(TestBean.class, versions);
		String output = verjson.write(new TestBean().content("Hello World"));
		assertThat(output).isEqualTo("{\"$v\":1,\"$d\":{\"content\":\"11\"}}");
		TestBean read = verjson.read(output);
		assertThat(read.content).isEqualTo("2");
	}

}


/** Fake */
class FakeAdapter implements JsonSerializer<String>, JsonDeserializer<String> {

	@Override
	public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive("" + src.length());
	}


	@Override
	public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return "" + json.getAsString().length();
	}

}
