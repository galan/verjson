package de.galan.oldverjson.adapter;

import static de.galan.commons.test.Tests.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.adapter.GenericSubclassAdapter;
import de.galan.oldverjson.core.Verjson;
import de.galan.oldverjson.example.v1.Example1Sub;
import de.galan.oldverjson.example.v1.Example1SubA;
import de.galan.oldverjson.example.v1.Example1SubB;
import de.galan.oldverjson.transformation.Versions;


/**
 * CUT GenericSubclassAdapter
 * 
 * @author daniel
 */
public class GenericSubclassAdapterTest extends AbstractTestParent {

	private DummyBean bean;
	private Example1SubA subA;


	@Before
	public void before() {
		bean = new DummyBean();
		bean.aaa = "dummy";
		subA = new Example1SubA();
		subA.parent = "pA";
		subA.aaa = "AAA";
		List<Example1Sub> list = new ArrayList<>();
		list.add(subA);
		bean.bbb = list;
	}


	@Test
	public void subclassInCollection() throws Exception {
		Verjson<DummyBean> v = new Verjson<>(DummyBean.class, new SubclassArrayVersions(null));
		String written = v.write(bean);
		assertThat(written).isEqualTo(readFile(getClass(), "subclass-01.json"));
		DummyBean read = v.read(written);
		assertThat(read.aaa).isEqualTo("dummy");
		assertThat(read.bbb).containsExactly(subA);
	}


	@Test
	public void userDefinedIdentifier() throws Exception {
		Verjson<DummyBean> v = new Verjson<>(DummyBean.class, new SubclassArrayVersions("_meh"));
		String written = v.write(bean);
		assertThat(written).isEqualTo(readFile(getClass(), "subclass-02.json"));
		DummyBean read = v.read(written);
		assertThat(read.aaa).isEqualTo("dummy");
		assertThat(read.bbb).containsExactly(subA);
	}

}


/** x */
class DummyBean {

	String aaa;
	List<Example1Sub> bbb;

}


/** x */
class SubclassArrayVersions extends Versions {

	private String id;


	public SubclassArrayVersions(String id) {
		this.id = id;
	}


	@Override
	public void configure() {
		GenericSubclassAdapter<Example1Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example1SubA.class);
		subClassAdapter.registerType("subb", Example1SubB.class);
		if (isNotBlank(id)) {
			subClassAdapter.setIdentifierName(id);
		}
		registerTypeAdapter(Example1Sub.class, subClassAdapter);
	}

}
