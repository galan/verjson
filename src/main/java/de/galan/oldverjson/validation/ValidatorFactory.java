package de.galan.oldverjson.validation;

/**
 * Creates Validators for a given JSON Schema
 * 
 * @author daniel
 */
public interface ValidatorFactory {

	public Validator create(String schema, String description);

}
