package de.galan.verjson.examples.validation;

import de.galan.verjson.core.Versions;
import de.galan.verjson.examples.simple.Transformation1;
import de.galan.verjson.step.validation.Validation;


/**
 * Sample from the README.md
 *
 * @author daniel
 */
public class ExampleValidationVersions extends Versions {

	@Override
	public void configure() {
		add(1L, new Validation(""));
		add(1L, new Transformation1());
		add(2L, new Validation(""));
	}

}
