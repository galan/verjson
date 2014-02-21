package de.galan.verjson.util;

import com.google.gson.JsonElement;

import de.galan.verjson.transformation.AbstractVersion;


/**
 * Mocked Noop Version
 * 
 * @author daniel
 */
public class MockVersion extends AbstractVersion {

	private long targetVersion;
	private long count = 0L;


	public MockVersion(long targetVersion) {
		this.targetVersion = targetVersion;
	}


	@Override
	public void transform(JsonElement element) {
		count++;
	}


	public long getCount() {
		return count;
	}


	public boolean hasTransformed() {
		return count > 0L;
	}


	@Override
	public long getTargetVersion() {
		return targetVersion;
	}

}
