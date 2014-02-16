package de.galan.verjson.transformation;

import com.google.gson.JsonElement;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class EmptyVersion implements Version {

	private long targetVersion;


	public EmptyVersion(long targetVersion) {
		this.targetVersion = targetVersion;
	}


	@Override
	public void transform(JsonElement element) {
		// noop
	}


	@Override
	public long getTargetVersion() {
		return targetVersion;
	}


	@Override
	public String getSchema() {
		return null;
	}

}