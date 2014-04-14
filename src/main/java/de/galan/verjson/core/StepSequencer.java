package de.galan.verjson.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.google.common.collect.ListMultimap;

import de.galan.verjson.step.IncrementVersionStep;
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
		//ProxyStep lastProxy = proxies.get(proxies.size() - 1);
		//Long highestSourceVersion = lastProxy.getSourceVersion();

		// create increments
		List<ProxyStep> proxiesIncrements = fillIncrements(proxies);

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


	protected List<ProxyStep> fillIncrements(List<ProxyStep> proxies) {
		List<ProxyStep> result = Lists.newArrayList();
		if (!proxies.isEmpty()) {
			Long lastSourceVersion = 1L;
			boolean increment = false;
			for (ProxyStep proxy: proxies) {
				while(lastSourceVersion < proxy.getSourceVersion()) {
					// add increments
					ProxyStep incProxy = new ProxyStep(lastSourceVersion, new IncrementVersionStep());
					result.add(incProxy);
					lastSourceVersion++;
				}
				//lastSourceVersion = proxy.getSourceVersion();
				increment = !Validation.class.isAssignableFrom(proxy.getStep().getClass());
				result.add(proxy);
			}
			if (increment) {
				// add increment
				ProxyStep incProxy = new ProxyStep(lastSourceVersion, new IncrementVersionStep());
				result.add(incProxy);
			}
		}
		return result;
	}

}
