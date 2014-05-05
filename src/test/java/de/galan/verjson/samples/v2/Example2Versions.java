package de.galan.verjson.samples.v2;

import de.galan.verjson.core.Versions;


/**
 * Versions for second version
 * 
 * @author daniel
 */
public class Example2Versions extends Versions {

	@Override
	public void configure() {
		registerSubclass(Example2Sub.class, Example2SubA.class, "suba");
		registerSubclass(Example2Sub.class, Example2SubB.class, "subb");
		add(1L, new Example2Transformation());
	}

}
