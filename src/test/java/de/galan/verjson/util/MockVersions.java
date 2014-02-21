package de.galan.verjson.util;

import de.galan.verjson.transformation.Versions;


/**
 * Mock
 * 
 * @author daniel
 */
public class MockVersions extends Versions {

	boolean configured;


	@Override
	public void configure() {
		configured = true;
	}


	public boolean isConfigured() {
		return configured;
	}

}
