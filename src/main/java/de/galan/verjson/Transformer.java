package de.galan.verjson;

import com.google.gson.JsonElement;


/**
 * Transforms between versions
 * 
 * @author daniel
 */
public interface Transformer {

	public void transform(JsonElement element);


	public long getSourceVersion();

}
