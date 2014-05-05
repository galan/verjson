package de.galan.verjson.examples.simple;

import de.galan.verjson.core.Versions;


/**
 * Sample from the README.md
 *
 * @author daniel
 */
public class ExampleBeanVersions extends Versions {

	@Override
	public void configure() {
		add(1L, new Transformation1());
	}

}
