package de.galan.verjson.example.v3;

import de.galan.verjson.core.Versions;
import de.galan.verjson.example.v2.Example2Transformation;


/**
 * Versions for third version
 * 
 * @author daniel
 */
public class Example3Versions extends Versions {

	@Override
	public void configure() {
		registerSubclass(Example3Sub.class, Example3SubA.class, "suba");
		registerSubclass(Example3Sub.class, Example3SubB.class, "subb");
		add(1L, new Example2Transformation());
		add(2L, new Example3Transformation());
	}

}
