package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.DummyTransformation;
import de.galan.verjson.step.IncrementVersionStep;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class StepSequencerTest extends AbstractTestParent {

	StepSequencer ss;
	Validation validate;
	Transformation transform;
	IncrementVersionStep increment;


	@Before
	public void before() {
		ss = new StepSequencer();
		validate = new Validation("");
		transform = new DummyTransformation();
		increment = new IncrementVersionStep();
	}


	@Test
	public void incV1() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, v(1));
	}


	@Test
	public void incT1() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(t(1));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, t(1), i(1));
	}


	@Test
	public void incV2() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(2));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), v(2));
	}


	@Test
	public void incT2() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(t(2));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), t(2), i(2));
	}


	@Test
	public void incV3() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(3));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), i(2), v(3));
	}


	@Test
	public void incT3() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(t(3));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), i(2), t(3), i(3));
	}


	@Test
	public void incV1T3() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1), t(3));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, v(1), i(1), i(2), t(3), i(3));
	}


	@Test
	public void incT2V5() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(t(2), v(5));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), t(2), i(2), i(3), i(4), v(5));
	}


	@Test
	public void incT2T5() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(t(2), t(5));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, i(1), t(2), i(2), i(3), i(4), t(5), i(5));
	}


	@Test
	public void incComplex() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1), t(1), v(2), t(2), t(3), v(4), t(6));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, v(1), t(1), i(1), v(2), t(2), i(2), t(3), i(3), v(4), i(4), i(5), t(6), i(6));
	}


	protected void assertIncrements(List<ProxyStep> results, ProxyStep... proxies) {
		assertThat(results).hasSameSizeAs(proxies);
		for (int i = 0; i < results.size(); i++) {
			ProxyStep r = results.get(i);
			ProxyStep e = proxies[i];
			assertThat(e.getStep().getClass()).isAssignableFrom(r.getStep().getClass());
			assertThat(e.getSourceVersion()).isEqualTo(r.getSourceVersion());
		}
	}


	protected ProxyStep p(int sourceVersion, Step step) {
		return new ProxyStep(Integer.valueOf(sourceVersion).longValue(), step);
	}


	protected ProxyStep t(int sourceVersion) {
		return p(sourceVersion, transform);
	}


	protected ProxyStep v(int sourceVersion) {
		return p(sourceVersion, validate);
	}


	protected ProxyStep i(int sourceVersion) {
		return p(sourceVersion, increment);
	}

}
