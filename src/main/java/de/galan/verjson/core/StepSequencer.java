package de.galan.verjson.core;

import java.util.Map;

import com.google.common.collect.ListMultimap;

import de.galan.verjson.step.Step;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public interface StepSequencer {

	public Map<Long, ? extends Step> sequence(ListMultimap<Long, Step> steps);

}
