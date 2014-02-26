package de.galan.verjson.transformation;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;


/**
 * Encapsulates multiple Transformation steps into one version. This can be helpful if you want to break your
 * Transformations steps into multiple classes.
 * 
 * @author daniel
 */
public abstract class VersionTransformationComposition extends AbstractVersion {

	private List<Transformation> steps;


	public VersionTransformationComposition() {
		steps = Lists.newArrayList();
		configure();
	}


	protected void configure() {
		// can be overriden
	}


	@Override
	public void transform(JsonElement element) {
		for (Transformation step: steps) {
			step.transform(element);
		}
	}


	/** Adds a Transformation step, can be used fluent. */
	public VersionTransformationComposition add(Transformation step) {
		steps.add(step);
		return this;
	}

}
