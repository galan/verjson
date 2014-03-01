package de.galan.verjson.core;

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
	public void nullVersion() throws Exception {
		Versions versions = new Versions().add(null);
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getHighestTargetVersion()).isEqualTo(1L);
	}


	@Test
	public void versionNotSupportedException2() throws NamespaceMismatchException {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
		try {
			v.read("{\"$v\":2,\"$d\":{\"content\":\"hello\",\"number\":42}}");
			fail("Version should not be supported");
		}
		catch (VersionNotSupportedException ex) {
			assertThat(ex.getMessage()).isEqualTo("Verjson<TestBean> only supports version '1', required version is '2'");
			assertThat(ex.getVersionSupported()).isEqualTo(1L);
			assertThat(ex.getVersionRequired()).isEqualTo(2L);
		}
	}


	@Test
	public void versionNotSupportedException3() throws NamespaceMismatchException {
		Versions versions = new Versions().add(new StubVersion(2L));
		Verjson<TestBean> v = Verjson.create(TestBean.class, versions);
		try {
			v.read("{\"$v\":3,\"$d\":{\"content\":\"hello\",\"number\":42}}");
			fail("Version should not be supported");
		}
		catch (VersionNotSupportedException ex) {
			assertThat(ex.getMessage()).isEqualTo("Verjson<TestBean> only supports version '2', required version is '3'");
			assertThat(ex.getVersionSupported()).isEqualTo(2L);
			assertThat(ex.getVersionRequired()).isEqualTo(3L);
		}
	}


	@Test
	public void deserializeNullObject() throws Exception {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
		String output = v.write(null);
		assertThat(output).isEqualTo("{\"$v\":1}");
		TestBean deserialized = v.read(output);
		assertThat(deserialized).isNull();
	}


	@Test
	public void deserializeNullObjectFallback() throws Exception {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
		TestBean bean = v.read("{}"); // equals version 1
		assertThat(bean).isNull();
	}


	@Test(expected = NullPointerException.class)
	public void deserializeNull() throws Exception {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
		v.read(null);
	}


	@Test
	public void versionAddedTwice() throws Exception {
		Versions versions = new Versions().add(new StubVersion(2L)).add(new StubVersion(2L));
		Verjson<TestBean> v = Verjson.create(TestBean.class, versions);
		//TODO?
	}

}
