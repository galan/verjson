package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Versions
 * 
 * @author daniel
 */
public class VersionsTest extends AbstractTestParent {

	@Test
	public void createEmptyNoNamespace() throws Exception {
		Versions v = new Versions();
		assertThat(v.getDeserializer()).isEmpty();
		assertThat(v.getSerializer()).isEmpty();
		assertThat(v.getNamespace()).isNull();
		assertThat(v.getRegisteredSubclasses().size()).isZero();
		assertThat(v.getSteps().size()).isZero();
	}


	@Test
	public void createEmptyWithNoNamespace() throws Exception {
		Versions v = new Versions("aaa");
		assertThat(v.getDeserializer()).isEmpty();
		assertThat(v.getSerializer()).isEmpty();
		assertThat(v.getNamespace()).isEqualTo("aaa");
		assertThat(v.getRegisteredSubclasses().size()).isZero();
		assertThat(v.getSteps().size()).isZero();
	}

}
