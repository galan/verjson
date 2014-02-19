package de.galan.verjson.example.v2;

import de.galan.verjson.adapter.GenericSubclassAdapter;
import de.galan.verjson.transformation.Versions;


/**
 * Versions for second version
 * 
 * @author daniel
 */
public class Example2Versions extends Versions {

	@Override
	public void configure() {
		GenericSubclassAdapter<Example2Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example2SubA.class);
		subClassAdapter.registerType("subb", Example2SubB.class);
		registerTypeAdapter(Example2Sub.class, subClassAdapter);
		add(new Example2Version());
	}

}
