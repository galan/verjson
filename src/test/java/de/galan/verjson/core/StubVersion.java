package de.galan.verjson.core;

import com.google.gson.JsonElement;

import de.galan.verjson.transformation.AbstractVersion;


/**
 * Stub for Version
 * 
 * @author daniel
 */
public class StubVersion extends AbstractVersion {

	long targetversion;


	public StubVersion(long targetversion) {
		this.targetversion = targetversion;
	}


	@Override
	public long getTargetVersion() {
		return targetversion;
	}


	@Override
	public void transform(JsonElement element) {
		// nada
	}

}
