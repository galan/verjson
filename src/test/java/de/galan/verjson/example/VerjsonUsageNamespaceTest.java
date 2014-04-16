package de.galan.verjson.example;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.NamespaceMismatchException;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.example.v1.Example1;
import de.galan.verjson.example.v1.Example1Versions;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class VerjsonUsageNamespaceTest extends AbstractTestParent {

	private final static String NS_DEFINED = "nejmzpahjz";

	private Verjson<Example1> verjson;


	@Before
	public void before() {
		Example1Versions versions = new Example1Versions();
		versions.setNamespace(NS_DEFINED);
		verjson = Verjson.create(Example1.class, versions);
	}


	@Test
	public void writeNamespace() throws Exception {
		String written = verjson.write(Example1.createSample());
		JsonFluentAssert.assertThatJson(written).isEqualTo(readFile(getClass(), "sample-namespace.json"));
	}


	@Test
	public void readNamespace() throws Exception {
		Example1 read = verjson.read(readFile(getClass(), "sample-namespace.json"));
		assertThat(read).isEqualTo(Example1.createSample());
	}


	@Test
	public void readMissingNamespace() throws Exception {
		try {
			verjson.read(readFile(getClass(), "sample-namespace-missing.json"));
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
			verjson.read(readFile(getClass(), "sample-namespace-different.json"));
		}
		catch (NamespaceMismatchException ex) {
			assertThat(ex.getNamespaceDefined()).isEqualTo(NS_DEFINED);
			assertThat(ex.getNamespaceGiven()).isEqualTo("diff");
			assertThat(ex.getMessage()).isEqualTo("Verjson only supports namespace 'nejmzpahjz', element has namespace 'diff'");
		}
	}

}
