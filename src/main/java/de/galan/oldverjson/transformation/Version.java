package de.galan.oldverjson.transformation;

/**
 * A Version, representing the changes from a source version, and an optional JSON Schema (http://json-schema.org/).
 * 
 * @author daniel
 */
public interface Version extends Transformation {

	/** The version received when the transformation was applied. */
	public long getTargetVersion();


	/** Optonal JSON Schema where the transformed JsonElement is validated against, null if no validation is wanted. */
	public String getSchema();

}
