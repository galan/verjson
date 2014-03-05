package de.galan.verjson.validation;

/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public interface ValidatorFactory {

	public Validator create(String schema, String description);

}
