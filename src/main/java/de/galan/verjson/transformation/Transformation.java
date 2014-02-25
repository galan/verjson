package de.galan.verjson.transformation;

import com.google.gson.JsonElement;


/**
 * A step where a JsonElement will be modified to represent the next version.
 * 
 * @author daniel
 */
public interface Transformation {

	/** Modifies a JsonElement from a previous Version to the next version. */
	public void transform(JsonElement element);

}
