package de.galan.oldverjson.util;

import de.galan.oldverjson.validation.Validator;


/**
 * Validator for tests
 * 
 * @author daniel
 */
public class MockValidator implements Validator {

	String last;

	boolean validates = true;


	@Override
	public void validate(String content) {
		last = content;
	}


	public MockValidator validates(boolean builderValidates) {
		validates = builderValidates;
		return this;
	}


	public String getLastContent() {
		return last;
	}

}
