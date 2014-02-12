package de.galan.verjson.core;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.galan.verjson.adapter.GsonDateAdapter;
import de.galan.verjson.transformer.Transformer;


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

	/** Flag to indicate that configuration phase is finished */
	boolean configured = false;

	/** Thread safe JsonParser */
	JsonParser parser;

	SortedMap<Long, TransformerContainer> containers;

	/** Highest version available in added transformers, starting with 1. */
	long highestTargetVersion = 1L;

	/** Optional namespace to distinguish between different types */
	String namespace;


	public Verjson() {
		builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(Date.class, new GsonDateAdapter());
		parser = new JsonParser();
		containers = new TreeMap<>();
		configure();
		configured = true;
		gson = builder.create();
		builder = null;
	}


	protected abstract void configure();


	protected abstract Class<T> getValueClass();


	protected String getNamespace() {
		return namespace;
	}


	protected void setNamespace(String namespace) {
		checkFinished();
		this.namespace = namespace;
	}


	protected SortedMap<Long, TransformerContainer> getContainers() {
		return containers;
	}


	protected void appendTransformer(Transformer transformer) {
		checkFinished();
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
					if (it.getSourceVersion() < sourceVersion && (pre == null || it.getSourceVersion() > pre.getSourceVersion())) {
						pre = it;
					}
					if (it.getSourceVersion() > sourceVersion && (suc == null || it.getSourceVersion() < suc.getSourceVersion())) {
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
		checkFinished();
		builder.registerTypeAdapter(type, typeAdapter);
	}


	public String write(T obj) {
		MetaWrapper wrapper = new MetaWrapper(getCurrentVersion(), getNamespace(), obj);
		return gson.toJson(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException {
		JsonElement element = parser.parse(json);
		transform(element);

		long version = element.getAsJsonObject().get("version").getAsLong();
		if (version != getHighestTargetVersion()) {
			throw new VersionNotSupportedException(getHighestTargetVersion(), version);
		}

		JsonElement elementNs = element.getAsJsonObject().get("ns");
		String ns = (elementNs == null) ? null : elementNs.getAsString();
		if (!StringUtils.equals(ns, getNamespace())) {
			throw new NamespaceMismatchException(getNamespace(), ns);
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


	protected long getCurrentVersion() {
		return getHighestTargetVersion();
	}


	protected long getHighestTargetVersion() {
		return highestTargetVersion;
	}


	protected void checkFinished() {
		if (configured) {
			throw new VerjsonAlreadyConfiguredException();
		}
	}

}
