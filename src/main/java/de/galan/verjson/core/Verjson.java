package de.galan.verjson.core;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import de.galan.commons.logging.Logr;
import de.galan.verjson.step.Step;
import de.galan.verjson.util.MetaWrapper;


/**
 * TODO documentation
 * 
 * @author daniel
 * @param <T> ...
 */
public class Verjson<T> {

	private static final Logger LOG = Logr.get();

	/** Optional user-defined namespace to distinguish between different types */
	String namespace;

	/** Type of the serialized objects */
	Class<T> valueClass;

	Map<Long, ? extends Step> steps;

	ObjectMapper mapper;

	/** Highest version available in added transformers, starting with 1. */
	long highestSourceVersion;


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
		mapper = new ObjectMapperFactory().create(versions);
		steps = createStepSequencer().sequence(versions.getSteps());
		highestSourceVersion = determineHighestSourceVersion();
	}


	protected long determineHighestSourceVersion() {
		Long result = 1L;
		if (steps != null && !steps.isEmpty()) {
			result = Collections.max(steps.keySet());
		}
		return result;
	}


	protected StepSequencer createStepSequencer() {
		return new DefaultStepSequencer();
	}


	protected String getNamespace() {
		return namespace;
	}


	//TODO own exception
	public String write(T obj) throws JsonProcessingException {
		MetaWrapper wrapper = new MetaWrapper(getHighestSourceVersion(), getNamespace(), obj);
		return mapper.writeValueAsString(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException {
		T result = null;
		String jsonNamespace = null;
		Long jsonVersion = null;
		try {
			JsonNode node = mapper.readTree(json);
			jsonNamespace = verifyNamespace(node);
			jsonVersion = verifyVersion(node);
			steps.get(jsonVersion).process(node);
			JsonNode data = MetaWrapper.getData(node);
			result = mapper.treeToValue(data, getValueClass());
		}
		catch (VersionNotSupportedException | NamespaceMismatchException ex) {
			throw ex;
		}
		catch (IOException ex) {
			LOG.error("Processing json failed for {}/{}", jsonNamespace, jsonVersion);
			LOG.error("Exception", ex);
		}
		return result;
	}


	protected Long verifyVersion(JsonNode node) throws VersionNotSupportedException {
		Long sourceVersion = MetaWrapper.getVersion(node);
		if (sourceVersion > getHighestSourceVersion()) {
			throw new VersionNotSupportedException(getHighestSourceVersion(), sourceVersion, getValueClass());
		}
		return sourceVersion;
	}


	protected String verifyNamespace(JsonNode node) throws NamespaceMismatchException {
		// verify namespace
		String ns = MetaWrapper.getNamespace(node);
		if (!StringUtils.equals(ns, getNamespace())) {
			throw new NamespaceMismatchException(getNamespace(), ns);
		}
		return ns;
	}


	protected Class<T> getValueClass() {
		return valueClass;
	}


	protected long getHighestSourceVersion() {
		return highestSourceVersion;
	}

}
