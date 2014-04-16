package de.galan.oldverjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.core.NamespaceMismatchException;
import de.galan.oldverjson.core.Verjson;
import de.galan.oldverjson.core.VersionNotSupportedException;
import de.galan.oldverjson.transformation.Versions;
import de.galan.verjson.test.TestBean;


/**
 * CUT Versions > Namespace
 * 
 * @author daniel
 */
public class VersionsNamespaceTest extends AbstractTestParent {

	private static final String JSON_WITH_NS = "{\"$v\":1,\"$ns\":\"myns\",\"$d\":{\"content\":\"Hello World\"}}";
	private static final String JSON_WITHOUT_NS = "{\"$v\":1,\"$d\":{\"content\":\"Hello World\"}}";

	Verjson<TestBean> verjsonWith = new Verjson<>(TestBean.class, new Versions("myns"));
	Verjson<TestBean> verjsonWithout = new Verjson<>(TestBean.class, new Versions());


	@Test
	public void createWith() throws Exception {
		String output = verjsonWith.write(new TestBean().content("Hello World"));
		assertThat(output).isEqualTo(JSON_WITH_NS);
	}


	@Test
	public void createWithout() throws Exception {
		String output = verjsonWithout.write(new TestBean().content("Hello World"));
		assertThat(output).isEqualTo(JSON_WITHOUT_NS);
	}


	@Test
	public void readWith() throws Exception {
		TestBean read = verjsonWith.read(JSON_WITH_NS);
		assertThat(read.content).isEqualTo("Hello World");
	}


	@Test
	public void readWithout() throws Exception {
		TestBean read = verjsonWithout.read(JSON_WITHOUT_NS);
		assertThat(read.content).isEqualTo("Hello World");
	}


	@Test
	public void readWithButMissing() throws VersionNotSupportedException {
		try {
			verjsonWith.read(JSON_WITHOUT_NS);
			fail("NamespaceMismatchException expected");
		}
		catch (NamespaceMismatchException nex) {
			assertThat(nex.getNamespaceDefined()).isEqualTo("myns");
			assertThat(nex.getNamespaceGiven()).isNull();
			assertThat(nex.getMessage()).isEqualTo("Verjson only supports namespace 'myns', element has no namespace");
		}
	}


	@Test
	public void readWithoutButExists() throws VersionNotSupportedException {
		try {
			verjsonWithout.read(JSON_WITH_NS);
			fail("NamespaceMismatchException expected");
		}
		catch (NamespaceMismatchException nex) {
			assertThat(nex.getNamespaceDefined()).isNull();
			assertThat(nex.getNamespaceGiven()).isEqualTo("myns");
			assertThat(nex.getMessage()).isEqualTo("Verjson only supports empty namespace, element has namespace 'myns'");
		}
	}


	@Test
	public void readWitDifferentNamespace() throws VersionNotSupportedException {
		Verjson<TestBean> verjsonWithDifferent = new Verjson<>(TestBean.class, new Versions("otherns"));
		try {
			verjsonWithDifferent.read(JSON_WITH_NS);
			fail("NamespaceMismatchException expected");
		}
		catch (NamespaceMismatchException nex) {
			assertThat(nex.getNamespaceDefined()).isEqualTo("otherns");
			assertThat(nex.getNamespaceGiven()).isEqualTo("myns");
			assertThat(nex.getMessage()).isEqualTo("Verjson only supports namespace 'otherns', element has namespace 'myns'");
		}
	}

}
