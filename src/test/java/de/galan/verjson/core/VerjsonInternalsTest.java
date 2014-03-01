package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.util.TestBean;


/**
 * CUT Verjson > internals
 * 
 * @author daniel
 */
public class VerjsonInternalsTest extends AbstractTestParent {

	@Test
	public void nullVersionInContainer() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		v.appendVersion(null);
		assertThat(v.getHighestTargetVersion()).isEqualTo(1L);
		assertThat(v.getContainers()).isEmpty();
	}


	@Test(expected = IllegalArgumentException.class)
	public void invalidTargetVErsion() throws Exception {
		Verjson<TestBean> v = new Verjson<>(TestBean.class, null);
		v.appendVersion(new StubVersion(-2));
	}

}
