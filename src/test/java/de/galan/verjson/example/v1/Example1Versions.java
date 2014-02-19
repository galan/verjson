package de.galan.verjson.example.v1;

import de.galan.verjson.adapter.GenericSubclassAdapter;
import de.galan.verjson.transformation.Versions;


/**
 * Versions for first version
 * 
 * @author daniel
 */
public class Example1Versions extends Versions {

	@Override
	public void configure() {
		GenericSubclassAdapter<Example1Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example1SubA.class);
		subClassAdapter.registerType("subb", Example1SubB.class);
		registerTypeAdapter(Example1Sub.class, subClassAdapter);
	}

}
