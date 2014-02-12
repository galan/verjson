package de.galan.verjson.adapter;

import java.lang.reflect.Type;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * This Serializer/Deserializer makes bindings of subclasses to abstract names possible. Example if you have an abstract
 * baseclass Animal and subclasses like CatAnimal and MouseAnimal, you can write the following:<br/>
 * <br/>
 * <code>
 * GenericSubclassAdapter<Animal> adapter = new GenericSubclassAdapter<>();
 * adapter.registerType("cat", CatAnimal.class);
 * adapter.registerType("mouse", MouseAnimal.class);
 * Gson gson = new GsonBuilder().registerTypeAdapter(Result.class, adapter).create();
 * </code> <br/>
 * <br/>
 * Created json will look like the following (List of Animal with Cat and Mouse):<br>
 * <code>
 * [{"type": "cat", "noise": "meow"}, {"type": "mouse", "likes": "cheese"}]
 * </code>
 * 
 * @author daniel
 * @param <T> Type of the baseclass
 */
public class GenericSubclassAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	private String id = "type";

	BiMap<String, Class<?>> map = HashBiMap.create();


	public void registerType(String name, Class<? extends T> clazz) {
		map.put(name, clazz);
	}


	public String getIdentifierName() {
		return id;
	}


	public void setIdentifierName(String id) {
		this.id = id;
	}


	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		JsonElement elem = context.serialize(src, src.getClass());
		if (elem.isJsonObject()) {
			JsonObject jo = (JsonObject)elem;
			String mappedName = map.inverse().get(src.getClass());
			jo.addProperty(getIdentifierName(), mappedName);
		}
		return elem;
	}


	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive)jsonObject.get(getIdentifierName());
		String typeName = prim.getAsString();
		Class<?> class1 = map.get(typeName);
		return context.deserialize(jsonObject, class1);
	}

}
