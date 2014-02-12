package de.galan.verjson.example;

import com.google.gson.JsonElement;

import de.galan.verjson.transformer.AbstractTransformer;


/**
 * Mocking transformer
 * 
 * @author daniel
 */
public class MockTransformer extends AbstractTransformer {

	private long sourceVersion;
	private long count = 0L;


	public MockTransformer(long sourceVersion) {
		this.sourceVersion = sourceVersion;
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
	public long getSourceVersion() {
		return sourceVersion;
	}

}
