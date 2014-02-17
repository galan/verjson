package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.google.gson.JsonElement;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT AbstractVersionTest
 * 
 * @author daniel
 */
public class AbstractVersionTest extends AbstractTestParent {

	@Test
	public void test() throws Exception {
		AbstractVersion av = new AbstractVersion() {

			@Override
			public void transform(JsonElement element) {
				// nada
			}


			@Override
			public long getTargetVersion() {
				return 2L;
			}
		};
		assertThat(av.getSchema()).isNull();
		assertThat(av.getTargetVersion()).isEqualTo(2L);
	}

}
