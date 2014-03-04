package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.transformation.EmptyVersion;
import de.galan.verjson.transformation.Version;
import de.galan.verjson.transformation.Versions;
import de.galan.verjson.util.TestBean;


/**
 * CUT Verjson > fillVersionGaps
 * 
 * @author daniel
 */
public class VersionGapTest extends AbstractTestParent {

	@Test
	public void gaps() throws Exception {
		// Notation: [targetversion]->[successor]
		// Original data only has three Version elements: [2]->[3]->[6]
		// Empty elements have to be filled up, if input element has missing version (where no transformation takes place).
		// [2]->[3]->[6] + [1*]->[2] + [4*]->[6] + [5*]->[6]
		Versions versions = new Versions().add(new StubVersion(2L)).add(new StubVersion(3L)).add(new StubVersion(6L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L, 3L, 4L, 5L, 6L);
		assertVersion(verjson, 1L, EmptyVersion.class, 2L);
		assertVersion(verjson, 2L, StubVersion.class, 3L);
		assertVersion(verjson, 3L, StubVersion.class, 6L);
		assertVersion(verjson, 4L, EmptyVersion.class, 6L);
		assertVersion(verjson, 5L, EmptyVersion.class, 6L);
		assertVersion(verjson, 6L, StubVersion.class, null);
	}


	@Test
	public void startingWithGap() throws Exception {
		// [3]->[5]
		// [1*]->[3] + [2*]->[3] + [3]->[5] [4*]->[5]
		Versions versions = new Versions().add(new StubVersion(3L)).add(new StubVersion(5L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L, 3L, 4L, 5L);
		assertVersion(verjson, 1L, EmptyVersion.class, 3L);
		assertVersion(verjson, 2L, EmptyVersion.class, 3L);
		assertVersion(verjson, 3L, StubVersion.class, 5L);
		assertVersion(verjson, 4L, EmptyVersion.class, 5L);
		assertVersion(verjson, 5L, StubVersion.class, null);
	}


	@Test
	public void noGaps() throws Exception {
		// [2]->[3]->[4]
		Versions versions = new Versions().add(new StubVersion(2L)).add(new StubVersion(3L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L, 3L);
		assertVersion(verjson, 1L, EmptyVersion.class, 2L);
		assertVersion(verjson, 2L, StubVersion.class, 3L);
		assertVersion(verjson, 3L, StubVersion.class, null);
	}


	@Test
	public void noVersion() throws Exception {
		// [1]
		Versions versions = new Versions();
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).isEmpty();
	}


	protected void assertVersion(Verjson<TestBean> verjson, long targetVersion, Class<? extends Version> expectedVersionClass, Long successorTargetVersion) {
		assertThat(verjson.getContainers().get(targetVersion).getSourceVersion()).isEqualTo(targetVersion - 1L);
		assertThat(verjson.getContainers().get(targetVersion).getTargetVersion()).isEqualTo(targetVersion);
		assertThat(verjson.getContainers().get(targetVersion).getVersion().getClass()).isEqualTo(expectedVersionClass);
		if (successorTargetVersion != null) {
			assertThat(verjson.getContainers().get(targetVersion).getSuccessor().getSourceVersion()).isEqualTo(successorTargetVersion - 1);
			assertThat(verjson.getContainers().get(targetVersion).getSuccessor().getTargetVersion()).isEqualTo(successorTargetVersion);
		}
		else {
			assertThat(verjson.getContainers().get(targetVersion).getSuccessor()).isNull();
		}
	}

}
