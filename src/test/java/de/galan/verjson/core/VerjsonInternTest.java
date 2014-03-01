package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Verjson > internals
 * 
 * @author daniel
 */
public class VerjsonInternTest extends AbstractTestParent {

	@Test
	public void nullVersionInContainer() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		v.appendVersion(null);
		assertThat(v.getHighestTargetVersion()).isEqualTo(1L);
		assertThat(v.getContainers()).isEmpty();
	}


	@Test(expected = IllegalArgumentException.class)
	public void invalidTargetVersion() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		v.appendVersion(new StubVersion(-2));
	}


	@Test
	public void isContainerPredecessor2() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		VersionContainer vc2 = new VersionContainer(new StubVersion(2L), "");
		VersionContainer vc3 = new VersionContainer(new StubVersion(2L), "");
		VersionContainer vc4 = new VersionContainer(new StubVersion(2L), "");
		assertThat(v.isContainerPredecessor(vc2, 1L, null)).isFalse();
		assertThat(v.isContainerPredecessor(vc2, 2L, null)).isTrue();
		assertThat(v.isContainerPredecessor(vc2, 3L, null)).isTrue();

		assertThat(v.isContainerPredecessor(vc2, 2L, vc3)).isFalse();
		assertThat(v.isContainerPredecessor(vc2, 2L, vc4)).isFalse();
		assertThat(v.isContainerPredecessor(vc2, 3L, vc3)).isFalse();
		assertThat(v.isContainerPredecessor(vc2, 3L, vc4)).isFalse();
	}


	@Test
	public void isContainerPredecessor3() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		VersionContainer vc2 = new VersionContainer(new StubVersion(2L), "");
		VersionContainer vc3 = new VersionContainer(new StubVersion(2L), "");
		VersionContainer vc4 = new VersionContainer(new StubVersion(2L), "");
		assertThat(v.isContainerPredecessor(vc3, 1L, null)).isFalse();
		//assertThat(v.isContainerPredecessor(vc3, 2L, null)).isTrue();
		assertThat(v.isContainerPredecessor(vc3, 3L, null)).isTrue();

		assertThat(v.isContainerPredecessor(vc3, 2L, vc3)).isFalse();
		assertThat(v.isContainerPredecessor(vc3, 2L, vc4)).isFalse();
		assertThat(v.isContainerPredecessor(vc3, 3L, vc3)).isFalse();
		assertThat(v.isContainerPredecessor(vc3, 3L, vc4)).isFalse();
	}

}
