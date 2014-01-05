package de.galan.verjson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


/**
 * TODO write doc
 * 
 * @author daniel
 * @param <T> ...
 */
public abstract class Verjson<T> {

	/** Configured gson */
	Gson gson;

	/** Builder used only during creation (configuration) */
	GsonBuilder builder;

	/** Thread safe JsonParser */
	JsonParser parser;

	SortedMap<Long, TransformerContainer> containers;

	/** Highest version available in added transformers, starting with 1. */
	long highestTargetVersion = 1L;


	public Verjson() {
		builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(Date.class, new GsonDateAdapter());
		parser = new JsonParser();
		containers = new TreeMap<>();
		configure();
		gson = builder.create();
		builder = null;
	}


	protected abstract void configure();


	protected SortedMap<Long, TransformerContainer> getContainers() {
		return containers;
	}


	protected void appendTransformer(Transformer transformer) {
		if (transformer != null) {
			long sourceVersion = transformer.getSourceVersion();
			long targetVersion = sourceVersion + 1;
			TransformerContainer container = getContainers().get(sourceVersion);
			if (container == null) {
				container = new TransformerContainer(sourceVersion, targetVersion, getValueClass().getSimpleName());
				TransformerContainer pre = null; //container with version greater then the transformer
				TransformerContainer suc = null; // container with version lower then the transformer
				// The following steps will determine the container before and after the current sourceVersion and put the new container in between
				for (TransformerContainer it: getContainers().values()) {
					if (it.getSourceVersion() < transformer.getSourceVersion() && (pre == null || it.getSourceVersion() > pre.getSourceVersion())) {
						pre = it;
					}
					if (it.getSourceVersion() > transformer.getSourceVersion() && (suc == null || it.getSourceVersion() < suc.getSourceVersion())) {
						suc = it;
					}
				}
				if (pre != null) {
					pre.setSuccessor(container);
				}
				if (suc != null) {
					container.setSuccessor(suc);
				}
				getContainers().put(sourceVersion, container);
			}
			container.addTransformer(transformer);
			highestTargetVersion = Math.max(targetVersion, highestTargetVersion);
		}
	}


	protected void registerTypeAdapter(Type type, Object typeAdapter) {
		builder.registerTypeAdapter(type, typeAdapter);
	}


	public String write(T obj) {
		VersionWrapper wrapper = new VersionWrapper(getCurrentVersion(), obj);
		return gson.toJson(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException {
		JsonElement element = parser.parse(json);
		transform(element);

		long version = element.getAsJsonObject().get("version").getAsLong();
		if (version != getHighestTargetVersion()) {
			throw new VersionNotSupportedException(getHighestTargetVersion(), version);
		}

		JsonElement data = element.getAsJsonObject().get("data");
		return gson.fromJson(data, getValueClass());
	}


	protected void transform(JsonElement element) {
		if (element != null) {
			// get current version
			long version = element.getAsJsonObject().get("version").getAsLong();
			TransformerContainer container = getContainers().get(version);
			if (container != null) {
				// apply transformation
				container.transform(element);
			}
		}
	}


	protected abstract Class<T> getValueClass();


	protected long getCurrentVersion() {
		return getHighestTargetVersion();
	}


	protected long getHighestTargetVersion() {
		return highestTargetVersion;
	}

}
