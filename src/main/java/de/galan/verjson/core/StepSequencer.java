package de.galan.verjson.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.google.common.collect.ListMultimap;

import de.galan.verjson.step.Step;


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
