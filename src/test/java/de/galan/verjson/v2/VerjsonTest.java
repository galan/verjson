package de.galan.verjson.v2;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class VerjsonTest extends AbstractTestParent {

	@Test
	public void noVersions() {
		Verjson<TestBean> v = Verjson.create(TestBean.class, null);
	}

}
