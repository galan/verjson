package de.galan.verjson.example.v2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.galan.verjson.AbstractTransformer;


/**
 * Sample transformer to migrate version 1 to version 2
 * 
 * @author daniel
 */
public class Example2Transformer extends AbstractTransformer {

	@Override
	public void transform(JsonElement element) {
		JsonObject jo = obj(element);
		// remove first
		remove(jo, "first");
		// rename second
		rename(jo, "second", "segundo");
		// keep third and fourth
		// convert fifth
		JsonArray fifth = getArrayAndRemove(jo, "fifth");
		StringBuilder builder = new StringBuilder();
		for (JsonElement elem: fifth) {
			builder.append(elem.getAsString());
		}
		jo.addProperty("fifth", builder.toString());
		// convert sixth
		JsonObject sixth = getObjAndRemove(jo, "sixth");
		rename(sixth, "one", "uno");
		remove(sixth, "two");
		jo.add("sixth", createArray(sixth));
	}


	@Override
	public long getSourceVersion() {
		return 1L;
	}

}
