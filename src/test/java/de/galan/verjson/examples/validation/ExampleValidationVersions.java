package de.galan.verjson.examples.validation;

import static de.galan.commons.test.Tests.*;

import java.io.IOException;

import de.galan.verjson.core.Versions;
import de.galan.verjson.examples.simple.Transformation1;
import de.galan.verjson.step.validation.Validation;


/**
 * Example Versions to demonstrate usage of Validation steps
 *
 * @author daniel
 */
public class ExampleValidationVersions extends Versions {

	@Override
	public void configure() {
		setIncludeTimestamp(false); // Keep examples short
		try {
			add(1L, new Validation(readFile(getClass(), "schema-version-01.json")));
			add(1L, new Transformation1());
			add(2L, new Validation(readFile(getClass(), "schema-version-02.json")));
		}
		catch (IOException ex) {
			throw new RuntimeException("Failed loading version files");
		}
	}

}
