package de.galan.oldverjson.transformation;

import com.google.gson.JsonElement;


/**
 * A step where a JsonElement will be modified to represent the next version.
 * 
 * @author daniel
 */
public interface Transformation {

	/**
	 * Modifications that are required to change the JsonElement from a previous (source)version to the current
	 * (target)version
	 */
	public void transform(JsonElement element);

}
