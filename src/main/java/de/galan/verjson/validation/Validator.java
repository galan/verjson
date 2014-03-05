package de.galan.verjson.validation;

/**
 * Validates the content against a schema, throws a InvalidJsonException (RuntimeException) when not valid.
 * 
 * @author daniel
 */
public interface Validator {

	public void validate(String content);

}
