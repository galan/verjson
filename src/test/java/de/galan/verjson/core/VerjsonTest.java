package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.test.TestBean;


/**
 * CUT Verjson
 * 
 * @author daniel
 */
public class VerjsonTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, null);
		assertThat(verjson.getHighestSourceVersion()).isEqualTo(1L);
	}

}
