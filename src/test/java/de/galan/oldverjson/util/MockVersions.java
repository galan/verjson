package de.galan.oldverjson.util;

import de.galan.oldverjson.transformation.Versions;


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
