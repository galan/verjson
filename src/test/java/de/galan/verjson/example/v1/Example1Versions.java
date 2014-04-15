package de.galan.verjson.example.v1;

import de.galan.verjson.core.Versions;


/**
 * Versions for first version
 * 
 * @author daniel
 */
public class Example1Versions extends Versions {

	@Override
	public void configure() {
		registerSubclass(Example1Sub.class, Example1SubA.class, "suba");
		registerSubclass(Example1Sub.class, Example1SubB.class, "subb");
	}

}
