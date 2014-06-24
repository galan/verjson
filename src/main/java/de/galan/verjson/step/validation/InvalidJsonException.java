package de.galan.verjson.step.validation;

import de.galan.verjson.step.ProcessStepException;


/**
 * JSON does not validate agains JSON Schema.
 *
 * @author daniel
 */
public class InvalidJsonException extends ProcessStepException {

	public InvalidJsonException(String message, Throwable cause) {
		super(message, cause);
	}


	public InvalidJsonException(String message) {
		super(message);
	}

}
