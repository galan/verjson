package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.example.v2.Example2Version;


/**
 * CUT VersionContainer
 * 
 * @author daniel
 */
public class VersionContainerTest extends AbstractTestParent {

	@Test
	public void toStringTest() throws Exception {
		VersionContainer vc = new VersionContainer(new Example2Version(), "Example2");
		assertThat(vc.toString()).isEqualTo("VersionContainer{targetVersion=2}");
	}

}