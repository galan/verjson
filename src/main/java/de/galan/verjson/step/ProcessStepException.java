package de.galan.verjson.step;

import de.galan.verjson.util.ReadException;


/**
 * Exception that can be thrown by a Step while processing.
 *
 * @author daniel
 */
public class ProcessStepException extends ReadException {

	public ProcessStepException(String message) {
		super(message);
	}


	public ProcessStepException(String message, Throwable cause) {
		super(message, cause);
	}

}
