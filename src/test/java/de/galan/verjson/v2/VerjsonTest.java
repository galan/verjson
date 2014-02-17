package de.galan.verjson.v2;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.transformation.Versions;


/**
 * CUT Verjson
 * 
 * @author daniel
 */
public class VerjsonTest extends AbstractTestParent {

	@Test
	public void noVersions() throws VersionNotSupportedException, NamespaceMismatchException {
		basicVersions(null);
	}


	@Test
	public void emptyVersions() throws VersionNotSupportedException, NamespaceMismatchException {
		basicVersions(new Versions());
	}


	protected void basicVersions(Versions versions) throws VersionNotSupportedException, NamespaceMismatchException {
		TestBean initialBean = new TestBean().content("hello").number(42L);
		Verjson<TestBean> v = Verjson.create(TestBean.class, versions);
		String output = v.write(initialBean);
		assertThat(output).isNotNull().isEqualTo("{\"$v\":1,\"$d\":{\"content\":\"hello\",\"number\":42}}");
		TestBean readBean = v.read(output);
		assertThat(readBean).isEqualTo(initialBean);
	}


	@Test(expected = NullPointerException.class)
	public void nullValueClass() throws Exception {
		new Verjson<>(null, null);
	}


	@Test(expected = NullPointerException.class)
	public void nullValueClassWithVersions() throws Exception {
		new Verjson<>(null, new Versions());
	}


	@Test
	public void testName() throws Exception {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
		// ...
	}

}
