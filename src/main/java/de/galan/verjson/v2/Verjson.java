package de.galan.verjson.v2;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.galan.verjson.adapter.GsonDateAdapter;
import de.galan.verjson.transformation.EmptyVersion;
import de.galan.verjson.transformation.Version;
import de.galan.verjson.transformation.VersionContainer;
import de.galan.verjson.transformation.Versions;


/**
 * TODO write doc
 * 
 * @author daniel
 * @param <T> ...
 */
public class Verjson<T> {

	/** Configured gson */
	Gson gson;

	/** Thread safe JsonParser */
	JsonParser parser;

	SortedMap<Long, VersionContainer> containers;

	/** Highest version available in added transformers, starting with 1. */
	long highestTargetVersion = 1L;

	/** Optional namespace to distinguish between different types */
	String namespace;

	/** Type of the serialized objects */
	private Class<T> valueClass;


	public static <T> Verjson<T> create(Class<T> valueClass, Versions versions) {
		return new Verjson<T>(valueClass, versions);
	}


	public Verjson(Class<T> valueClass, Versions versions) {
		this.valueClass = Preconditions.checkNotNull(valueClass, "valueClass can not be null");
		Versions vs = (versions != null) ? versions : new Versions();
		this.namespace = vs.getNamespace();
		configure(vs);
	}


	protected void configure(Versions versions) {
		versions.configure();
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(Date.class, new GsonDateAdapter());
		parser = new JsonParser();
		containers = Maps.newTreeMap();

		for (Version version: versions.getVersions()) {
			appendVersion(version);
		}
		for (Entry<Type, Object> entry: versions.getTypeAdapter().entrySet()) {
			builder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}

		gson = builder.create();
		builder = null;
		fillVersionGaps();
	}


	protected Class<T> getValueClass() {
		return valueClass;
	}


	protected String getNamespace() {
		return namespace;
	}


	protected SortedMap<Long, VersionContainer> getContainers() {
		return containers;
	}


	protected void appendVersion(Version version) {
		if (version != null) {
			// TODO validate targetVersion > 1
			long targetVersion = version.getTargetVersion();
			long sourceVersion = targetVersion - 1L;
			VersionContainer container = getContainers().get(sourceVersion);
			if (container == null) {
				container = new VersionContainer(version, getValueClass().getSimpleName());
				VersionContainer pre = null; // container with version greater then the sourceVersion
				VersionContainer suc = null; // container with version lower then the sourceVersion
				// The following steps will determine the container before and after the current sourceVersion and put the new container in between
				for (VersionContainer it: getContainers().values()) {
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
			else {
				// TODO version defined multiple times .. ?
			}
			//container.addTransformer(transformer);
			highestTargetVersion = Math.max(targetVersion, highestTargetVersion);
		}
	}


	protected void fillVersionGaps() {
		VersionContainer successor = null;
		for (long i = getHighestTargetVersion(); i > 1; i--) {
			VersionContainer found = getContainers().get(i);
			if (found == null) {
				VersionContainer container = new VersionContainer(new EmptyVersion(i), getValueClass().getSimpleName());
				container.setSuccessor(successor);
			}
			else {
				successor = found;
			}
		}
	}


	public String write(T obj) {
		MetaWrapper wrapper = new MetaWrapper(getCurrentVersion(), getNamespace(), obj);
		return gson.toJson(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException {
		JsonElement element = parser.parse(json);
		// verify namespace
		String ns = MetaUtil.getNamespace(element);
		if (!StringUtils.equals(ns, getNamespace())) {
			throw new NamespaceMismatchException(getNamespace(), ns);
		}
		// transform object
		transform(element);
		// verify version
		long version = MetaUtil.getVersion(element);
		if (version != getHighestTargetVersion()) {
			throw new VersionNotSupportedException(getHighestTargetVersion(), version);
		}
		// deserialize
		return gson.fromJson(MetaUtil.getData(element), getValueClass());
	}


	protected void transform(JsonElement element) {
		if (element != null) {
			// get current version
			long version = MetaUtil.getVersion(element);
			VersionContainer container = getContainers().get(version);
			// TODO ignore version 1, log if container == null 
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

}
