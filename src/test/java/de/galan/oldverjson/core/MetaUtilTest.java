package de.galan.oldverjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.core.MetaUtil;


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
