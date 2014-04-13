package de.galan.verjson.core;

import java.io.IOException;
import java.util.Map;

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
	private Class<T> valueClass;

	private Map<Long, Step> steps;

	private ObjectMapper mapper;

	/** Highest version available in added transformers, starting with 1. */
	long largestTargetVersion = 1L;


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
		steps = new StepSequencer().sequence(versions.getSteps());
	}


	protected String getNamespace() {
		return namespace;
	}


	//TODO own exception
	public String write(T obj) throws JsonProcessingException {
		MetaWrapper wrapper = new MetaWrapper(getHighestTargetVersion(), getNamespace(), obj);
		return mapper.writeValueAsString(wrapper);
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException {
		T result = null;
		try {
			JsonNode node = mapper.readTree(json);
			Long sourceVersion = MetaWrapper.getVersion(node);
			steps.get(sourceVersion).process(node);
			result = mapper.treeToValue(node, valueClass);
		}
		catch (IOException ex) {
			LOG.warn("TODO", ex); // TODO
		}
		return result;
	}


	protected long getHighestTargetVersion() {
		return largestTargetVersion;
	}

}
