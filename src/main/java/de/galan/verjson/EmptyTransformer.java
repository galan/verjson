package de.galan.verjson;

import com.google.gson.JsonElement;


/**
 * A no-op Transformer which can be used when no changes need to be applied between versions.
 * 
 * @author daniel
 */
public class EmptyTransformer implements Transformer {

	private long sourceVersion;


	public EmptyTransformer(long sourceVersion) {
		this.sourceVersion = sourceVersion;
	}


	@Override
	public void transform(JsonElement element) {
		// nada
	}


	@Override
	public long getSourceVersion() {
		return sourceVersion;
	}

}
