package de.galan.verjson.example.v1;

import de.galan.verjson.GenericSubclassAdapter;
import de.galan.verjson.Verjson;


/**
 * Verjson for first version
 * 
 * @author daniel
 */
public class Example1Verjson extends Verjson<Example1> {

	@Override
	protected void configure() {
		GenericSubclassAdapter<Example1Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example1SubA.class);
		subClassAdapter.registerType("subb", Example1SubB.class);
		registerTypeAdapter(Example1Sub.class, subClassAdapter);
	}


	@Override
	protected Class<Example1> getValueClass() {
		return Example1.class;
	}

}
