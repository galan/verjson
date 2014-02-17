package de.galan.verjson.example.v3;

import de.galan.verjson.adapter.GenericSubclassAdapter;
import de.galan.verjson.transformation.Versions;


/**
 * Verjson for third version
 * 
 * @author daniel
 */
public class Example3Versions extends Versions {

	@Override
	public void configure() {
		GenericSubclassAdapter<Example3Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example3SubA.class);
		subClassAdapter.registerType("subb", Example3SubB.class);
		registerTypeAdapter(Example3Sub.class, subClassAdapter);
		add(new Example3Version());
	}

}
