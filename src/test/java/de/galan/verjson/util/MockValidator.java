package de.galan.verjson.util;

import de.galan.verjson.validation.Validator;


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
