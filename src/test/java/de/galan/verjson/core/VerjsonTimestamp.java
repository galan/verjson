package de.galan.verjson.core;

import static de.galan.commons.test.Tests.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.test.TestBean;


/**
 * Test behaviour of timestamp in MetaWrapper,Version,Verjson
 *
 * @author daniel
 */
public class VerjsonTimestamp {

	private Versions versions;
	private TestBean bean;


	@Before
	public void before() {
		versions = new Versions();
		bean = new TestBean().content("abc");
		ApplicationClock.setIso("2014-05-12T07:09:48Z");
	}


	@Test
	public void includeTimestamp() throws Exception {
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		JsonFluentAssert.assertThatJson(verjson.write(bean)).isEqualTo(readFile(getClass(), "includeTimestamp-true.txt"));
	}


	@Test
	public void omitTimestamp() throws Exception {
		versions.setIncludeTimestamp(false);
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		JsonFluentAssert.assertThatJson(verjson.write(bean)).isEqualTo(readFile(getClass(), "includeTimestamp-false.txt"));
	}

}
