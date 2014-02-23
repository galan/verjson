package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT MetaUtil
 * 
 * @author daniel
 */
public class MetaUtilTest extends AbstractTestParent {

	@Test
	public void fullfillCoverage() throws Exception {
		assertThat(new MetaUtil()).isNotNull();
	}

}
