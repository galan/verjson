package de.galan.verjson.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;

import com.google.common.collect.ListMultimap;

import de.galan.oldverjson.transformation.Transformation;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class StepSequencer {

	public Map<Long, Step> sequence(ListMultimap<Long, Step> steps) {
		// Create Proxies
		List<ProxyStep> proxies = createProxies(steps);
		// Sort Proxies
		Collections.sort(proxies, new ProxyStepComparator());
		// Determine highest SourceVersion
		// create increments
		// assign successors
		// attach Sourceversions to Map
		return null;
	}


	protected List<ProxyStep> createProxies(ListMultimap<Long, Step> steps) {
		List<ProxyStep> list = Lists.newArrayList();
		for (Long sourceVersion: steps.keySet()) {
			for (Step step: steps.get(sourceVersion)) {
				list.add(new ProxyStep(sourceVersion, step));
			}
		}
		return list;
	}

}


/** x */
class ProxyStepComparator implements Comparator<ProxyStep> {

	List<Class<?>> order = Lists.newArrayList();


	public ProxyStepComparator() {
		order.add(Transformation.class);
		order.add(Validation.class);
	}


	@Override
	public int compare(ProxyStep s1, ProxyStep s2) {
		int result = ObjectUtils.compare(s1.getSourceVersion(), s1.getSourceVersion());
		if (result == 0) {
			Class<? extends Step> c1 = s1.getStep().getClass();
			Class<? extends Step> c2 = s2.getStep().getClass();
			if (!c1.equals(c2)) {

			}
		}
		return result;
	}

}
