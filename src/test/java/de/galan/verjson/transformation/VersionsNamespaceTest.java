package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.v2.NamespaceMismatchException;
import de.galan.verjson.v2.TestBean;
import de.galan.verjson.v2.Verjson;


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


	@Test(expected = NamespaceMismatchException.class)
	public void readWithButMissing() throws Exception {
		verjsonWith.read(JSON_WITHOUT_NS);
	}


	@Test(expected = NamespaceMismatchException.class)
	public void readWithoutButExists() throws Exception {
		verjsonWithout.read(JSON_WITH_NS);
	}

}
