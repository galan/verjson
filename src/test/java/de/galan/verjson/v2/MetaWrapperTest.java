package de.galan.verjson.v2;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT MetaWrapper
 * 
 * @author daniel
 */
public class MetaWrapperTest extends AbstractTestParent {

	@Test
	public void simpleGsonName() throws Exception {
		MetaWrapper mw = new MetaWrapper(1L, "string");
		Gson gson = new GsonBuilder().create();
		assertEquals("{\"$v\":1,\"$d\":\"string\"}", gson.toJson(mw));
	}


	@Test
	public void fullGsonName() throws Exception {
		TestBean bean = new TestBean();
		bean.content = "hello";
		bean.value = 42L;
		MetaWrapper mw = new MetaWrapper(1L, "my-ns", bean);
		Gson gson = new GsonBuilder().create();
		assertEquals("{\"$v\":1,\"$ns\":\"my-ns\",\"$d\":{\"content\":\"hello\",\"value\":42}}", gson.toJson(mw));
	}
}