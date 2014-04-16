package de.galan.verjson.util;

import static de.galan.verjson.util.Transformations.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.galan.commons.test.AbstractTestParent;


/**
 * 
 * @author daniel
 */
public class TransformationsTest extends AbstractTestParent {

	private ArrayNode newArray() {
		return new ArrayNode(JsonNodeFactory.instance);
	}


	private ObjectNode newObject() {
		return new ObjectNode(JsonNodeFactory.instance);
	}


	protected ObjectNodeBuilder createObj() {
		return ObjectNodeBuilder.create();
	}


	@Test
	public void objObj() throws Exception {
		assertThat(obj(null)).isNull();
		ObjectNode on = new ObjectNode(JsonNodeFactory.instance);
		assertThat(obj(on)).isEqualTo(newObject());
		assertThat(obj(on)).isNotEqualTo(newArray());
		on.put("aaa", "bbb");
		assertThat(obj(on)).isNotEqualTo(newObject());
	}


	@Test(expected = ClassCastException.class)
	public void objArray() throws Exception {
		obj(newArray());
	}


	@Test
	public void getObjNull() throws Exception {
		assertThat(getObj(null, null)).isNull();
		assertThat(getObj(null, "field")).isNull();
	}


	@Test
	public void getObjEmpty() throws Exception {
		assertThat(getObj(newObject(), null)).isNull();
	}


	@Test
	public void getObjNotFound() throws Exception {
		ObjectNode obj = newObject();
		obj.put("field1", "abc");
		assertThat(getObj(obj, null)).isNull();
	}


	@Test(expected = ClassCastException.class)
	public void getObjWrongType() throws Exception {
		ObjectNode obj = newObject();
		obj.put("field1", "abc");
		getObj(obj, "field1");
	}


	@Test
	public void testGetObj() throws Exception {
		ObjectNode obj = newObject();
		ObjectNode inner = newObject();
		inner.put("field1", "abc");
		obj.put("inner", inner);
		assertThat(getObj(obj, "inner")).isEqualTo(inner);
	}


	@Test
	public void getObjAndRemoveNull() throws Exception {
		assertThat(getObjAndRemove(null, null)).isNull();
		assertThat(getObjAndRemove(null, "")).isNull();
	}


	@Test
	public void getObjAndRemoveEmpty() throws Exception {
		ObjectNode f1 = newObject().put("aaa", "xxx");
		ObjectNode f2 = newObject().put("bbb", "yyy");
		ObjectNode obj = createObj().put("f1", f1).put("f2", f2).get();
		assertThat(getObjAndRemove(obj, "")).isNull();
		assertThat(obj.get("f1")).isEqualTo(f1);
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test
	public void getObjAndRemoveNonHit() throws Exception {
		ObjectNode f1 = createObj().put("aaa", "xxx").get();
		ObjectNode f2 = createObj().put("bbb", 2L).get();
		ObjectNode obj = createObj().put("f1", f1).put("f2", f2).get();
		assertThat(getObjAndRemove(obj, "f3")).isNull();
		assertThat(obj.get("f1")).isEqualTo(f1);
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test(expected = ClassCastException.class)
	public void getObjAndRemoveNoObj() throws Exception {
		getObjAndRemove(createObj().put("f1", "aaa").get(), "f1"); // JsonPrimitive
	}


	@Test
	public void getObjAndRemoveHit() throws Exception {
		ObjectNode f1 = createObj().put("aaa", "xxx").get();
		ObjectNode f2 = createObj().put("bbb", "yyy").get();
		ObjectNode obj = createObj().put("f1", f1).put("f2", f2).get();
		assertThat(getObjAndRemove(obj, "f1")).isEqualTo(f1);
		assertThat(obj.get("f1")).isNull();
		assertThat(obj.get("f2")).isEqualTo(f2);
	}


	@Test
	public void createArrayNull() throws Exception {
		ArrayNode array1 = createArray((JsonNode)null);
		assertThat(array1).isNull();

		ArrayNode array2 = createArray(false, (JsonNode)null);
		assertThat(array2).isNull();
	}


	@Test
	public void createArrayEmpty() throws Exception {
		ArrayNode array = createArray(true, (JsonNode)null);
		assertThat(array.size()).isEqualTo(0);
	}


	@Test
	public void testCreateArray() throws Exception {
		ObjectNode jo1 = createObj().put("a", "b").get();
		ObjectNode jo2 = createObj().put("c", "d").get();
		ArrayNode array = createArray(true, jo1, null, jo2);
		assertThat(array.size()).isEqualTo(2);
		assertThat(array).contains(jo1, jo2);
	}


	@Test
	public void arrayNull() throws Exception {
		assertThat(array(null)).isNull();
	}


	@Test(expected = ClassCastException.class)
	public void arrayWrongType() throws Exception {
		array(newObject());
	}


	@Test
	public void testArray() throws Exception {
		assertThat(array(newArray())).isEqualTo(newArray());
	}


	@Test
	public void getArrayNull() throws Exception {
		assertThat(getArray(null, null)).isNull();
		assertThat(getArray(null, "")).isNull();
		assertThat(getArray(null, "aa")).isNull();
		assertThat(getArray(newObject(), "aa")).isNull();
	}


	@Test(expected = ClassCastException.class)
	public void getArrayWrongType() throws Exception {
		assertThat(getArray(createObj().put("b", "text").get(), "b")).isNull();
	}


	@Test
	public void getArrayAndRemoveNull() throws Exception {
		assertThat(getArrayAndRemove(null, null)).isNull();
		assertThat(getArrayAndRemove(null, "")).isNull();
		assertThat(getArrayAndRemove(null, "abc")).isNull();
		assertThat(getArrayAndRemove(createObj().get(), null)).isNull();
		//assertThat(getArrayAndRemove(createObj().add("a", "b").get(), "a")).isNull();
		//assertThat(getArrayAndRemove(createObj().get(), null)).isNull();
	}


	@Test(expected = ClassCastException.class)
	public void getArrayAndRemoveWrongType() throws Exception {
		getArrayAndRemove(createObj().put("a", "b").get(), "a");
	}


	@Test
	public void testGetArrayAndRemove() throws Exception {
		ArrayNode array = newArray();
		array.add("a");
		ObjectNode obj = createObj().put("x", array).get();
		assertThat(obj.has("x")).isTrue();
		ArrayNode result = getArrayAndRemove(obj, "x");
		assertThat(obj.has("x")).isFalse();
		assertThat(result).isEqualTo(array);
	}


	@Test
	public void removeNull() throws Exception {
		remove(null, null);
		remove(null, "");
		remove(null, "a");
	}


	@Test
	public void testRemove() throws Exception {
		ObjectNode obj = createObj().put("a", "b").get();
		remove(obj, null);
		assertThat(obj.get("a")).isNotNull();
		remove(obj, "");
		assertThat(obj.get("a")).isNotNull();
		remove(obj, "x");
		assertThat(obj.get("a")).isNotNull();
		remove(obj, "a");
		assertThat(obj.get("a")).isNull();
	}


	@Test
	public void renameNull() throws Exception {
		rename(null, null, null);
		rename(null, "", null);
		rename(null, null, "");
		rename(null, "", "");
		rename(null, "x", null);
		rename(null, "", "x");
		rename(null, "x", "x");
	}


	@Test
	public void renameNothing() throws Exception {
		ObjectNode obj = createObj().put("a", "b").get();
		rename(obj, null, null);
		rename(obj, "", null);
		rename(obj, null, "");
		rename(obj, "", "");
		rename(obj, "x", null);
		rename(obj, "", "a");
		rename(obj, "x", "x");
		assertThat(obj.get("a").asText()).isEqualTo("b");
		assertThat(obj.size()).isEqualTo(1);
	}


	@Test
	public void testRename() throws Exception {
		ObjectNode obj = createObj().put("a", "b").get();
		rename(obj, "a", "y");
		assertThat(obj.get("y").asText()).isEqualTo("b");
		assertThat(obj.size()).isEqualTo(1);
	}


	@Test
	public void create() {
		new Transformations(); // code-coverage
	}

}
