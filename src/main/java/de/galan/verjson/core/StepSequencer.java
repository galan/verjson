package de.galan.verjson.core;

import java.util.Map;

import com.google.common.collect.ListMultimap;

import de.galan.verjson.step.Step;


/**
 * Processes the user-defined {@link Step}s to the final Map sourceVersion-to-ProxyStep. This way inputs are granted to
 * enter the correct Step, processing the rest of the additional Steps in a chain.
 * 
 * @author daniel
 */
public interface StepSequencer {

	/** Takes the user-registered {@link Step}s and creates a Map of SourceVersion:ProxyStep, including missed Steps. */
	public Map<Long, ProxyStep> sequence(ListMultimap<Long, Step> steps);

}
