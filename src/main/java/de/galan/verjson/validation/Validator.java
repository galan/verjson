package de.galan.verjson.validation;

/**
 * Validates the content against a schema.
 * 
 * @author daniel
 */
public interface Validator {

	public boolean validate(String content);

}
