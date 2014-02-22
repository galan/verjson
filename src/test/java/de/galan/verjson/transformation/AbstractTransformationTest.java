package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT AbstractTransformation
 * 
 * @author daniel
 */
public class AbstractTransformationTest extends AbstractTestParent {

	private AbstractTransformation at;


	@Before
	public void before() {
		at = new AbstractTransformation() {

			@Override
			public void transform(JsonElement element) {
				// nada
			}
		};
	}


	@Test
	public void objObj() throws Exception {
		assertThat(at.obj(null)).isNull();
		JsonObject element = new JsonObject();
		assertThat(at.obj(element)).isEqualTo(new JsonObject());
		assertThat(at.obj(element)).isNotEqualTo(new JsonArray());
		element.addProperty("aaa", "bbb");
		assertThat(at.obj(element)).isNotEqualTo(new JsonObject());
	}


	@Test(expected = IllegalStateException.class)
	public void objArray() throws Exception {
		at.obj(new JsonArray());
	}


	@Test
	public void getObjNull() throws Exception {
		assertThat(at.getObj(null, null)).isNull();
		assertThat(at.getObj(null, "field")).isNull();
	}


	@Test
	public void getObjEmpty() throws Exception {
		assertThat(at.getObj(new JsonObject(), null)).isNull();
	}


	@Test
	public void getObjNotFound() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("field1", "abc");
		assertThat(at.getObj(obj, null)).isNull();
	}


	@Test(expected = IllegalStateException.class)
	public void getObjWrongType() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("field1", "abc");
		at.getObj(obj, "field1");
	}


	@Test
	public void getObj() throws Exception {
		JsonObject obj = new JsonObject();
		JsonObject inner = new JsonObject();
		inner.addProperty("field1", "abc");
		obj.add("inner", inner);
		assertThat(at.getObj(obj, "inner")).isEqualTo(inner);
	}


	@Test
	public void getObjAndRemoveNull() throws Exception {
		assertThat(at.getObjAndRemove(null, null)).isNull();
		assertThat(at.getObjAndRemove(null, "")).isNull();
	}


	@Test
	public void getObjAndRemoveEmpty() throws Exception {
		JsonObject f1 = createObj().add("aaa", "xxx").get();
		JsonObject f2 = createObj().add("bbb", "yyy").get();
		JsonObject obj = createObj().add("f1", f1).add("f2", f2).get();
		assertThat(at.getObjAndRemove(obj, "")).isNull();
		assertThat(obj.get("f1")).isEqualTo(f1);
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test
	public void getObjAndRemoveNonHit() throws Exception {
		JsonObject f1 = createObj().add("aaa", "xxx").get();
		JsonObject f2 = createObj().add("bbb", "yyy").get();
		JsonObject obj = createObj().add("f1", f1).add("f2", f2).get();
		assertThat(at.getObjAndRemove(obj, "f3")).isNull();
		assertThat(obj.get("f1")).isEqualTo(f1);
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test(expected = IllegalStateException.class)
	public void getObjAndRemoveNoObj() throws Exception {
		at.getObjAndRemove(createObj().add("f1", "aaa").get(), "f1"); // JsonPrimitive
	}


	@Test
	public void getObjAndRemoveHit() throws Exception {
		JsonObject f1 = createObj().add("aaa", "xxx").get();
		JsonObject f2 = createObj().add("bbb", "yyy").get();
		JsonObject obj = createObj().add("f1", f1).add("f2", f2).get();
		assertThat(at.getObjAndRemove(obj, "f1")).isEqualTo(f1);
		assertThat(obj.get("f1")).isNull();
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test
	public void createArrayNull() throws Exception {
		JsonArray array1 = at.createArray((JsonElement)null);
		assertThat(array1).isNull();

		JsonArray array2 = at.createArray(false, (JsonElement)null);
		assertThat(array2).isNull();
	}


	@Test
	public void createArrayEmpty() throws Exception {
		JsonArray array = at.createArray(true, (JsonElement)null);
		assertThat(array.size()).isEqualTo(0);
	}


	@Test
	public void createArray() throws Exception {
		JsonObject jo1 = createObj().add("a", "b").get();
		JsonObject jo2 = createObj().add("c", "d").get();
		JsonArray array = at.createArray(true, jo1, null, jo2);
		assertThat(array.size()).isEqualTo(2);
		assertThat(array).contains(jo1, jo2);
	}


	@Test
	public void arrayNull() throws Exception {
		assertThat(at.array(null)).isNull();
	}


	@Test(expected = IllegalStateException.class)
	public void arrayWrongType() throws Exception {
		at.array(new JsonObject());
	}


	@Test
	public void array() throws Exception {
		assertThat(at.array(new JsonArray())).isEqualTo(new JsonArray());
	}


	@Test
	public void getArrayNull() throws Exception {
		assertThat(at.getArray(null, null)).isNull();
		assertThat(at.getArray(null, "")).isNull();
		assertThat(at.getArray(null, "aa")).isNull();
		assertThat(at.getArray(new JsonObject(), "aa")).isNull();
	}


	@Test(expected = IllegalStateException.class)
	public void getArrayWrongType() throws Exception {
		assertThat(at.getArray(createObj().add("b", "text").get(), "b")).isNull();
	}


	@Test
	public void getArrayAndRemoveNull() throws Exception {
		assertThat(at.getArrayAndRemove(null, null)).isNull();
		assertThat(at.getArrayAndRemove(null, "")).isNull();
		assertThat(at.getArrayAndRemove(null, "abc")).isNull();
		assertThat(at.getArrayAndRemove(createObj().get(), null)).isNull();
		//assertThat(at.getArrayAndRemove(createObj().add("a", "b").get(), "a")).isNull();
		//assertThat(at.getArrayAndRemove(createObj().get(), null)).isNull();
	}


	@Test(expected = IllegalStateException.class)
	public void getArrayAndRemoveWrongType() throws Exception {
		at.getArrayAndRemove(createObj().add("a", "b").get(), "a");
	}


	@Test
	public void getArrayAndRemove() throws Exception {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive("a"));
		JsonObject obj = createObj().add("x", array).get();
		assertThat(obj.has("x")).isTrue();
		JsonArray result = at.getArrayAndRemove(obj, "x");
		assertThat(obj.has("x")).isFalse();
		assertThat(result).isEqualTo(array);
	}


	@Test
	public void removeNull() throws Exception {
		at.remove(null, null);
		at.remove(null, "");
		at.remove(null, "a");
	}


	@Test
	public void remove() throws Exception {
		JsonObject obj = createObj().add("a", "b").get();
		at.remove(obj, null);
		assertThat(obj.get("a")).isNotNull();
		at.remove(obj, "");
		assertThat(obj.get("a")).isNotNull();
		at.remove(obj, "x");
		assertThat(obj.get("a")).isNotNull();
		at.remove(obj, "a");
		assertThat(obj.get("a")).isNull();
	}


	@Test
	public void renameNull() throws Exception {
		at.rename(null, null, null);
		at.rename(null, "", null);
		at.rename(null, null, "");
		at.rename(null, "", "");
		at.rename(null, "x", null);
		at.rename(null, "", "x");
		at.rename(null, "x", "x");
	}


	@Test
	public void renameNothing() throws Exception {
		JsonObject obj = createObj().add("a", "b").get();
		at.rename(obj, null, null);
		at.rename(obj, "", null);
		at.rename(obj, null, "");
		at.rename(obj, "", "");
		at.rename(obj, "x", null);
		at.rename(obj, "", "a");
		at.rename(obj, "x", "x");
		assertThat(obj.get("a").getAsString()).isEqualTo("b");
		assertThat(obj.entrySet().size()).isEqualTo(1);
	}


	@Test
	public void rename() throws Exception {
		JsonObject obj = createObj().add("a", "b").get();
		at.rename(obj, "a", "y");
		assertThat(obj.get("y").getAsString()).isEqualTo("b");
		assertThat(obj.entrySet().size()).isEqualTo(1);
	}


	protected JsonObjBuilder createObj() {
		return JsonObjBuilder.create();
	}

}


/** Helper */
class JsonObjBuilder {

	public static JsonObjBuilder create() {
		return new JsonObjBuilder();
	}

	JsonObject result = new JsonObject();


	public JsonObjBuilder add(String key, String value) {
		result.addProperty(key, value);
		return this;
	}


	public JsonObjBuilder add(String key, Long value) {
		result.addProperty(key, value);
		return this;
	}


	public JsonObjBuilder add(String key, JsonElement value) {
		result.add(key, value);
		return this;
	}


	public JsonObject get() {
		return result;
	}

}
