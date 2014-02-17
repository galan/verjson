package de.galan.verjson.v2;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import de.galan.verjson.transformation.AbstractVersion;
import de.galan.verjson.transformation.Transformation;


/**
 * Encapsulates multiple Transformation steps into one version.
 * 
 * @author daniel
 */
public abstract class VersionTransformationComposition extends AbstractVersion {

	private List<Transformation> steps;


	public VersionTransformationComposition() {
		steps = new ArrayList<>();
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


	public void addTransformationStep(Transformation step) {
		steps.add(step);
	}

}
