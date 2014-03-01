package de.galan.verjson.core;

import static org.apache.commons.lang3.StringUtils.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.galan.commons.logging.Logr;
import de.galan.verjson.adapter.GsonDateAdapter;
import de.galan.verjson.transformation.EmptyVersion;
import de.galan.verjson.transformation.Version;
import de.galan.verjson.transformation.Versions;
import de.galan.verjson.validation.Validator;
import de.galan.verjson.validation.ValidatorFactory;


/**
 * TODO write doc
 * 
 * @author daniel
 * @param <T> ...
 */
public class Verjson<T> {

	private static final Logger LOG = Logr.get();

	private static final Object DATE_ADAPTER = new GsonDateAdapter();

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

	private ValidatorFactory validatorFactory;


	public static <T> Verjson<T> create(Class<T> valueClass, Versions versions) {
		return new Verjson<T>(valueClass, versions);
	}


	public Verjson(Class<T> valueClass, Versions versions) {
		this.valueClass = Preconditions.checkNotNull(valueClass, "valueClass can not be null");
		Versions vs = (versions != null) ? versions : new Versions();
		configure(vs);
	}


	protected void configure(Versions versions) {
		versions.configure();
		this.namespace = versions.getNamespace();
		GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
		builder.registerTypeAdapter(Date.class, DATE_ADAPTER);
		parser = new JsonParser();
		containers = Maps.newTreeMap();

		for (Version version: versions.getVersions()) {
			appendVersion(version);
		}
		for (Entry<Type, Object> entry: versions.getTypeAdapter().entrySet()) {
			builder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}

		gson = builder.create();
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
			Preconditions.checkArgument(version.getTargetVersion() > 1, "Targetversion has to be greater then 1");
			long targetVersion = version.getTargetVersion();
			long sourceVersion = targetVersion - 1L;
			VersionContainer container = getContainers().get(sourceVersion);
			if (container == null) {
				container = new VersionContainer(version, getValueClass().getSimpleName());
				container.setValidator(constructValidator(version.getSchema()));
				VersionContainer pre = null; // container with version greater then the sourceVersion
				VersionContainer suc = null; // container with version lower then the sourceVersion
				// The following steps will determine the container before and after the current sourceVersion and put the new container in between
				for (VersionContainer it: getContainers().values()) {
					if (isContainerPredecessor(it, sourceVersion, pre)) {
						pre = it;
					}
					if (isContainerSuccessor(it, sourceVersion, suc)) {
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
				// throw new com.google.common.
				// TODO version defined multiple times .. ?
				LOG.warn("Version with target version '" + version.getTargetVersion() + "' is already defined, ignoring");
			}
			//container.addTransformer(transformer);
			highestTargetVersion = Math.max(targetVersion, highestTargetVersion);
		}
		else {
			LOG.warn("Version is null, ignoring");
		}
	}


	boolean isContainerPredecessor(VersionContainer container, long sourceVersion, VersionContainer found) {
		return container.getSourceVersion() < sourceVersion && (found == null || container.getSourceVersion() > found.getSourceVersion());
	}


	boolean isContainerSuccessor(VersionContainer container, long sourceVersion, VersionContainer found) {
		return container.getSourceVersion() > sourceVersion && (found == null || container.getSourceVersion() < found.getSourceVersion());
	}


	private Validator constructValidator(String schema) {
		Validator result = null;
		if (isNotBlank(schema)) {
			if (validatorFactory == null) {
				validatorFactory = new de.galan.verjson.validation.fge.JsonSchemaValidatorFactory();
			}
			result = validatorFactory.create(schema);
		}
		return result;
	}


	protected void fillVersionGaps() {
		VersionContainer successor = null;
		for (long sourceVersion = getHighestTargetVersion() - 1L; sourceVersion > 0L; sourceVersion--) {
			VersionContainer found = getContainers().get(sourceVersion);
			if (found == null) {
				VersionContainer container = new VersionContainer(new EmptyVersion(sourceVersion + 1L), getValueClass().getSimpleName());
				container.setSuccessor(successor);
				getContainers().put(sourceVersion, container);
			}
			else {
				successor = found;
			}
		}
	}


	public String write(T obj) {
		MetaWrapper wrapper = new MetaWrapper(getHighestTargetVersion(), getNamespace(), obj);
		return gson.toJson(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException {
		return read(parse(json));
	}


	public T read(JsonElement element) throws VersionNotSupportedException, NamespaceMismatchException {
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
			throw new VersionNotSupportedException(getHighestTargetVersion(), version, getValueClass());
		}
		// deserialize
		return gson.fromJson(MetaUtil.getData(element), getValueClass());
	}


	public JsonElement parse(String json) {
		return parser.parse(json);
	}


	protected void transform(JsonElement element) {
		Preconditions.checkNotNull(element, "Root element was null");
		// get current version
		long version = MetaUtil.getVersion(element);
		VersionContainer container = getContainers().get(version);
		// TODO ignore version 1? (nope -> json schema for version 1)
		if (container != null) {
			// apply transformation
			container.transform(element);
		}
	}


	protected long getHighestTargetVersion() {
		return highestTargetVersion;
	}

}
