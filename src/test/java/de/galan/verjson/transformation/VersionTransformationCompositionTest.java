package de.galan.verjson.transformation;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.google.gson.JsonElement;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.util.JsonObjBuilder;


/**
 * CUT VersionTransformationComposition
 * 
 * @author daniel
 */
public class VersionTransformationCompositionTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		VersionTransformationComposition vtc = new VersionTransformationComposition() {

			@Override
			public long getTargetVersion() {
				return 2L;
			}
		};

		JsonElement element = JsonObjBuilder.create().add("a", "b").get();
		DummyTransformation t1 = new DummyTransformation();
		DummyTransformation t2 = new DummyTransformation();
		vtc.add(t1).add(t2);
		vtc.transform(element);
		assertThat(t1.input).isEqualTo(element);
		assertThat(t2.input).isEqualTo(element);
	}

}


/** x */
class DummyTransformation extends AbstractTransformation {

	JsonElement input;


	@Override
	public void transform(JsonElement element) {
		input = element;
	}

}
