package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT EmptyVersion
 * 
 * @author daniel
 */
public class EmptyVersionTest extends AbstractTestParent {

	@Test
	public void emptyVersion() throws Exception {
		EmptyVersion ev = new EmptyVersion(42L);
		assertThat(ev.getSchema()).isNull();
		assertThat(ev.getTargetVersion()).isEqualTo(42L);
		ev.transform(null); // no exception etc. should happen
	}

}
