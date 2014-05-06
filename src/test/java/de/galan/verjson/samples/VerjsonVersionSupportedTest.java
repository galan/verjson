package de.galan.verjson.samples;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.test.FixedDateSupplier;
import de.galan.commons.time.DateDsl;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.core.VersionNotSupportedException;
import de.galan.verjson.core.Versions;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.test.TestBean;


/**
 * Test handling of unsupported versions
 *
 * @author daniel
 */
public class VerjsonVersionSupportedTest extends AbstractTestParent {

	private Verjson<TestBean> verjson1;
	private Verjson<TestBean> verjson2;
	private TestBean bean;


	@Before
	public void before() {
		verjson1 = Verjson.create(TestBean.class, null);
		verjson2 = Verjson.create(TestBean.class, new TestBeanVersion());
		bean = new TestBean().content("aaa").number(666L);
		DateDsl.setDateSupplier(new FixedDateSupplier("2014-05-06T06:42:28Z", true));
	}


	@Test
	public void writeVersion1() throws Exception {
		String written = verjson1.write(bean);
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-version-1.json"));
	}


	@Test
	public void writeVersion2() throws Exception {
		String written = verjson2.write(bean);
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-version-2.json"));
	}


	@Test
	public void readVersion1With1() throws Exception {
		TestBean read = verjson1.read(readFile(getClass(), "sample-version-1.json"));
		assertThat(read).isEqualTo(bean);
	}


	@Test
	public void readVersion2With2() throws Exception {
		TestBean read = verjson2.read(readFile(getClass(), "sample-version-2.json"));
		assertThat(read).isEqualTo(bean);
	}


	@Test
	public void readVersion1With2() throws Exception {
		TestBean read = verjson2.read(readFile(getClass(), "sample-version-1.json"));
		assertThat(read).isEqualTo(bean);
	}


	@Test
	public void readVersion2With1() throws Exception {
		try {
			verjson1.read(readFile(getClass(), "sample-version-2.json"));
		}
		catch (VersionNotSupportedException ex) {
			assertThat(ex.getVersionRequired()).isEqualTo(2L);
			assertThat(ex.getVersionSupported()).isEqualTo(1L);
			assertThat(ex.getMessage()).isEqualTo("Verjson<TestBean> only supports version '1', required version is '2'");
		}
	}

	/*
	@Test
	public void readMissingNamespace() throws Exception {
		try {
			verjsonNs.read(readFile(getClass(), "sample-namespace-missing.json"));
		}
		catch (NamespaceMismatchException ex) {
			assertThat(ex.getNamespaceDefined()).isEqualTo(NS_DEFINED);
			assertThat(ex.getNamespaceGiven()).isNull();
			assertThat(ex.getMessage()).isEqualTo("Verjson only supports namespace 'nejmzpahjz', element has no namespace");
		}
	}


	@Test
	public void readDifferentNamespace() throws Exception {
		try {
			verjsonNs.read(readFile(getClass(), "sample-namespace-different.json"));
		}
		catch (NamespaceMismatchException ex) {
			assertThat(ex.getNamespaceDefined()).isEqualTo(NS_DEFINED);
			assertThat(ex.getNamespaceGiven()).isEqualTo("diff");
			assertThat(ex.getMessage()).isEqualTo("Verjson only supports namespace 'nejmzpahjz', element has namespace 'diff'");
		}
	}


	@Test
	public void readNoNamespace() throws Exception {
		try {
			verjsonNoNs.read(readFile(getClass(), "sample-namespace.json"));
		}
		catch (NamespaceMismatchException ex) {
			assertThat(ex.getNamespaceDefined()).isNull();
			assertThat(ex.getNamespaceGiven()).isEqualTo(NS_DEFINED);
			assertThat(ex.getMessage()).isEqualTo("Verjson only supports empty namespace, element has namespace 'nejmzpahjz'");
		}
	}
	 */

}


/** Test */
class TestBeanVersion extends Versions {

	@Override
	public void configure() {
		add(1L, new Transformation() {

			@Override
			protected void transform(JsonNode node) {
				// nothing
			}
		});
	}

}
