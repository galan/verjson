package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import de.galan.commons.test.AbstractTestParent;
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
	public void compare() throws Exception {
		assertCompare(1L, validation, 1L, validation, 0);
		assertCompare(1L, transformation, 1L, transformation, 0);
		assertCompare(1L, validation, 1L, transformation, 1);
		assertCompare(1L, transformation, 1L, validation, -1);
	}


	@Test
	public void sortEmpty() throws Exception {
	}


	protected void assertSort(List<ProxyStep> steps, Class<? extends Step> expectedOrder) {
		Collections.sort(steps, psc);
		ArrayList<Class<? extends Step>> classes = Lists.newArrayList(Iterables.transform(steps, new Function<ProxyStep, Class<? extends Step>>() {

			@Override
			public Class<? extends Step> apply(ProxyStep input) {
				return input.getStep().getClass();
			}

		}));
		assertThat(classes).containsSequence(expectedOrder);
	}


	public void assertCompare(Long sourceVersion1, Step s1, Long sourceVersion2, Step s2, int expected) {
		assertThat(psc.compare(new ProxyStep(sourceVersion1, s1), new ProxyStep(sourceVersion2, s2))).isEqualTo(expected);
	}

}
