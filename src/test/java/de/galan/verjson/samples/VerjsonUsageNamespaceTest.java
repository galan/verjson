package de.galan.verjson.samples;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.test.FixedDateSupplier;
import de.galan.commons.time.DateDsl;
import de.galan.verjson.core.NamespaceMismatchException;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.samples.v1.Example1;
import de.galan.verjson.samples.v1.Example1Versions;


/**
 * Test namespace variations
 *
 * @author daniel
 */
public class VerjsonUsageNamespaceTest extends AbstractTestParent {

	private final static String NS_DEFINED = "nejmzpahjz";

	private Verjson<Example1> verjsonNs;
	private Verjson<Example1> verjsonNoNs;


	@Before
	public void before() {
		Example1Versions versions = new Example1Versions();
		versions.setNamespace(NS_DEFINED);
		verjsonNs = Verjson.create(Example1.class, versions);
		verjsonNoNs = Verjson.create(Example1.class, new Example1Versions());
		DateDsl.setDateSupplier(new FixedDateSupplier("2014-05-06T06:42:28Z", true));
	}


	@Test
	public void writeNamespace() throws Exception {
		String written = verjsonNs.write(Example1.createSample());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-namespace.json"));
	}


	@Test
	public void readNamespace() throws Exception {
		Example1 read = verjsonNs.read(readFile(getClass(), "sample-namespace.json"));
		assertThat(read).isEqualTo(Example1.createSample());
	}


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

}
