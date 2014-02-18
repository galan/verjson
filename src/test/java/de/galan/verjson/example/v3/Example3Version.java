package de.galan.verjson.example.v3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.galan.verjson.transformation.AbstractVersion;


/**
 * Sample transformer to migrate version 2 to version 3
 * 
 * @author daniel
 */
public class Example3Version extends AbstractVersion {

	@Override
	public void transform(JsonElement element) {
		JsonObject jo = obj(element);
		rename(jo, "empty", "filled");
	}


	@Override
	public long getTargetVersion() {
		return 3L;
	}

}
