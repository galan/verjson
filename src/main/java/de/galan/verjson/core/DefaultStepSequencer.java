package de.galan.verjson.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.galan.verjson.step.IncrementVersionStep;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class DefaultStepSequencer implements StepSequencer {

	@Override
	public Map<Long, ? extends Step> sequence(ListMultimap<Long, Step> steps) {
		// Create Proxies
		List<ProxyStep> proxies = createProxies(steps);

		// Sort Proxies
		Collections.sort(proxies, new ProxyStepComparator());

		// create increments
		List<ProxyStep> proxiesIncrements = fillIncrements(proxies);

		// assign successors
		assignSuccessors(proxiesIncrements);

		// attach SourceVersions to Map
		Map<Long, ProxyStep> proxiesAttached = attachVersions(proxiesIncrements);

		return proxiesAttached;
	}


	protected Map<Long, ProxyStep> attachVersions(List<ProxyStep> proxies) {
		Map<Long, ProxyStep> result = Maps.newHashMap();
		Long lastVersion = 0L;
		for (ProxyStep proxy: proxies) {
			if (proxy.getSourceVersion() > lastVersion) {
				result.put(++lastVersion, proxy);
			}
		}
		return result;
	}


	protected void assignSuccessors(List<ProxyStep> proxies) {
		ProxyStep precessor = null;
		for (ProxyStep step: Lists.reverse(proxies)) {
			if (precessor != null) {
				step.setSuccessor(precessor);
			}
			precessor = step;
		}
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
