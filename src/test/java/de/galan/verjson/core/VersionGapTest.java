package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.google.gson.JsonElement;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.transformation.AbstractVersion;
import de.galan.verjson.transformation.EmptyVersion;
import de.galan.verjson.transformation.Version;
import de.galan.verjson.transformation.Versions;


/**
 * CUT Verjson > fillVersionGaps
 * 
 * @author daniel
 */
public class VersionGapTest extends AbstractTestParent {

	@Test
	public void gaps() throws Exception {
		// Notation: [sourceversion]->[successor]
		// Original data only has three Version elements: [1]->[2]->[5]
		// Empty elements have to be filled up, if input element has missing version (where no transformation takes place).
		// [1]->[2]->[5]  [3*]->[5]  [4*]->[5]
		Versions versions = new Versions().add(new StubVersion(2L)).add(new StubVersion(3L)).add(new StubVersion(6L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L, 3L, 4L, 5L);
		assertVersion(verjson, 1L, StubVersion.class, 2L);
		assertVersion(verjson, 2L, StubVersion.class, 5L);
		assertVersion(verjson, 3L, EmptyVersion.class, 5L);
		assertVersion(verjson, 4L, EmptyVersion.class, 5L);
		assertVersion(verjson, 5L, StubVersion.class, null);
	}


	@Test
	public void startingWithGap() throws Exception {
		// [2]->[4]
		// [1*]->[2] [2]->[4] [3*]->[4]
		Versions versions = new Versions().add(new StubVersion(3L)).add(new StubVersion(5L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L, 3L, 4L);
		assertVersion(verjson, 1L, EmptyVersion.class, 2L);
		assertVersion(verjson, 2L, StubVersion.class, 4L);
		assertVersion(verjson, 3L, EmptyVersion.class, 4L);
		assertVersion(verjson, 4L, StubVersion.class, null);
	}


	@Test
	public void noGaps() throws Exception {
		// [1]->[2]->[3]
		Versions versions = new Versions().add(new StubVersion(2L)).add(new StubVersion(3L));
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).containsKeys(1L, 2L);
		assertVersion(verjson, 1L, StubVersion.class, 2L);
		assertVersion(verjson, 2L, StubVersion.class, null);
	}


	@Test
	public void noVersion() throws Exception {
		// [1]
		Versions versions = new Versions();
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, versions);
		assertThat(verjson.getContainers()).isEmpty();
	}


	protected void assertVersion(Verjson<TestBean> verjson, long sourceVersion, Class<? extends Version> expectedVersionClass, Long successorSourceVersion) {
		assertThat(verjson.getContainers().get(sourceVersion).getSourceVersion()).isEqualTo(sourceVersion);
		assertThat(verjson.getContainers().get(sourceVersion).getTargetVersion()).isEqualTo(sourceVersion + 1L);
		assertThat(verjson.getContainers().get(sourceVersion).getVersion().getClass()).isEqualTo(expectedVersionClass);
		if (successorSourceVersion != null) {
			assertThat(verjson.getContainers().get(sourceVersion).getSuccessor().getSourceVersion()).isEqualTo(successorSourceVersion);
			assertThat(verjson.getContainers().get(sourceVersion).getSuccessor().getTargetVersion()).isEqualTo(successorSourceVersion + 1L);
		}
		else {
			assertThat(verjson.getContainers().get(sourceVersion).getSuccessor()).isNull();
		}
	}

}


/** Dummy Version */
class StubVersion extends AbstractVersion {

	long targetversion;


	public StubVersion(long targetversion) {
		this.targetversion = targetversion;
	}


	@Override
	public long getTargetVersion() {
		return targetversion;
	}


	@Override
	public void transform(JsonElement element) {
		// nada
	}

}
