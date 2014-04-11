package de.galan.verjson;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.ProxyStep;
import de.galan.verjson.core.ProxyStepComparator;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ProxyStepComparatorTest extends AbstractTestParent {

	ProxyStepComparator psc;
	Validation validation;
	Transformation transformation;


	@Before
	public void before() {
		psc = new ProxyStepComparator();
		validation = new Validation(null);
		transformation = new Transformation() {

			@Override
			protected void transform(JsonNode node) {
				// nada
			}

		};
	}


	@Test
	public void testName() throws Exception {
		assertPsc(1L, validation, 1L, validation, 0);
	}


	public void assertPsc(Long sourceVersion1, Step s1, Long sourceVersion2, Step s2, int expected) {
		assertThat(psc.compare(new ProxyStep(sourceVersion1, s1), new ProxyStep(sourceVersion2, s2))).isEqualTo(expected);
	}

}
