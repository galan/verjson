package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class StepSequencerArrangeTest extends AbstractStepSequencerParent {

	@Test
	public void incEmpty() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList();
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results);
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
		assertIncrements(results, t(1), i(1), n(2));
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
		assertIncrements(results, i(1), t(2), i(2), n(3));
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
		assertIncrements(results, i(1), i(2), t(3), i(3), n(4));
	}


	@Test
	public void incV1T3() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1), t(3));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, v(1), i(1), i(2), t(3), i(3), n(4));
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
		assertIncrements(results, i(1), t(2), i(2), i(3), i(4), t(5), i(5), n(6));
	}


	@Test
	public void incComplex() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1), t(1), v(2), t(2), t(3), v(4), t(6));
		List<ProxyStep> results = ss.fillIncrements(proxies);
		assertIncrements(results, v(1), t(1), i(1), v(2), t(2), i(2), t(3), i(3), v(4), i(4), i(5), t(6), i(6), n(7));
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

}
