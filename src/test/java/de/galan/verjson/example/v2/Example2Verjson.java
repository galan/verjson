package de.galan.verjson.example.v2;

import de.galan.verjson.Verjson;
import de.galan.verjson.adapter.GenericSubclassAdapter;


/**
 * Verjson for second version
 * 
 * @author daniel
 */
public class Example2Verjson extends Verjson<Example2> {

	@Override
	protected void configure() {
		GenericSubclassAdapter<Example2Sub> subClassAdapter = new GenericSubclassAdapter<>();
		subClassAdapter.registerType("suba", Example2SubA.class);
		subClassAdapter.registerType("subb", Example2SubB.class);
		registerTypeAdapter(Example2Sub.class, subClassAdapter);
	}


	@Override
	protected Class<Example2> getValueClass() {
		return Example2.class;
	}


	@Override
	protected void checkFinished() {
		// avoid check in test
	}

}
