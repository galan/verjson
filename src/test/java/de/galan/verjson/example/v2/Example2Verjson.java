package de.galan.verjson.example.v2;

import de.galan.verjson.adapter.GenericSubclassAdapter;
import de.galan.verjson.core.Verjson;


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
		appendTransformer(new Example2Transformer());
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
