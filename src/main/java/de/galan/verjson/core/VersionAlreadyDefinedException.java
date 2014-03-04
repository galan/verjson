package de.galan.verjson.core;

/**
 * The Version was added twice from the user. Splitting transformation logic into steps is possible with the
 * VersionTransformationComposition class.
 * 
 * @author daniel
 */
public class VersionAlreadyDefinedException extends RuntimeException {

	public VersionAlreadyDefinedException(long targetVersion) {
		super("Targetversion '" + targetVersion
				+ "' already defined'. (Splitting transformation logic into steps is possible with the VersionTransformationComposition class)");
	}

}
