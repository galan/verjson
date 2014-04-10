package de.galan.oldverjson.example.v3;

import de.galan.oldverjson.adapter.GenericSubclassAdapter;
import de.galan.oldverjson.example.v2.Example2Version;
import de.galan.oldverjson.transformation.Versions;


/**
 * Versions for third version
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
		add(new Example2Version());
		add(new Example3Version());
	}

}
