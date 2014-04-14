package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.DummyTransformation;
import de.galan.verjson.OtherStep;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * CUT ProxyStepComparator
 *
 * @author daniel
 */
public class ProxyStepComparatorTest extends AbstractTestParent {

	ProxyStepComparator psc;
	Validation validation;
	Transformation transformation;
	OtherStep other;


	@Before
	public void before() {
		psc = new ProxyStepComparator();
		validation = new Validation(null);
		transformation = new DummyTransformation();
		other = new OtherStep();
	}


	@Test
	public void compareSameVersion() throws Exception {
		assertCompare(1L, validation, 1L, validation, 0);
		assertCompare(1L, transformation, 1L, transformation, 0);
		assertCompare(1L, validation, 1L, transformation, -1);
		assertCompare(1L, transformation, 1L, validation, 1);

		assertCompare(1L, validation, 1L, other, -1);
		assertCompare(1L, transformation, 1L, other, -1);
		assertCompare(1L, other, 1L, other, 0);
		assertCompare(1L, other, 1L, validation, 1);
		assertCompare(1L, other, 1L, transformation, 1);
	}


	@Test
	public void compareDifferentVersion() throws Exception {
		assertCompare(1L, validation, 2L, validation, -1);
		assertCompare(2L, validation, 1L, validation, 1);
		assertCompare(1L, transformation, 2L, transformation, -1);
		assertCompare(2L, transformation, 1L, transformation, 1);
		assertCompare(1L, validation, 2L, transformation, -1);
		assertCompare(2L, validation, 1L, transformation, 1);
		assertCompare(1L, transformation, 2L, validation, -1);
		assertCompare(2L, transformation, 1L, validation, 1);
	}


	public void assertCompare(Long sourceVersion1, Step s1, Long sourceVersion2, Step s2, int expected) {
		assertThat(psc.compare(new ProxyStep(sourceVersion1, s1), new ProxyStep(sourceVersion2, s2))).isEqualTo(expected);
	}


	@Test
	public void sortEmpty() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		List<ProxyStep> expected = Lists.newArrayList();
		assertSort(steps, expected);
	}


	@Test
	public void sortV1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, validation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));

		assertSort(steps, expected);
	}


	@Test
	public void sortV3() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(3L, validation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(3L, validation));

		assertSort(steps, expected);
	}


	@Test
	public void sortT1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortT3() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(3L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(3L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortTwoV1T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, validation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortTwoT1V1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, validation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV1T1T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, validation));
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeT1V1T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, validation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeT1T1V1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, validation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV1T1T2() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, validation));
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(2L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(2L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV1T2T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, validation));
		steps.add(new ProxyStep(2L, transformation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, validation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(2L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV2T1T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(2L, validation));
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(2L, validation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV2T2T1() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(2L, validation));
		steps.add(new ProxyStep(2L, transformation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(2L, validation));
		expected.add(new ProxyStep(2L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortThreeV2T2T2() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(2L, validation));
		steps.add(new ProxyStep(2L, transformation));
		steps.add(new ProxyStep(2L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(2L, validation));
		expected.add(new ProxyStep(2L, transformation));
		expected.add(new ProxyStep(2L, transformation));

		assertSort(steps, expected);
	}


	@Test
	public void sortMany() throws Exception {
		List<ProxyStep> steps = Lists.newArrayList();
		steps.add(new ProxyStep(1L, transformation));
		steps.add(new ProxyStep(1L, transformation));

		List<ProxyStep> expected = Lists.newArrayList();
		expected.add(new ProxyStep(1L, transformation));
		expected.add(new ProxyStep(1L, transformation));

		assertSort(steps, expected);
	}


	protected void assertSort(List<ProxyStep> steps, List<ProxyStep> expected) {
		Collections.sort(steps, psc);
		assertThat(steps).hasSameSizeAs(expected);
		for (int i = 0; i < steps.size(); i++) {
			assertThat(steps.get(i).getSourceVersion()).isEqualTo(expected.get(i).getSourceVersion());
			assertThat(steps.get(i).getStep().getClass()).isAssignableFrom(expected.get(i).getStep().getClass());
		}

	}

}
