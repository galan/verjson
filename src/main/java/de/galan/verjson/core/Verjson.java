package de.galan.verjson.core;

import static de.galan.commons.time.Instants.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;

import de.galan.verjson.step.ProcessStepException;
import de.galan.verjson.step.Step;
import de.galan.verjson.util.MetaWrapper;
import de.galan.verjson.util.ReadException;


/**
 * Versionized transformable/evolvable objectgraphs<br/>
 * TODO documentation
 *
 * @author daniel
 * @param <T> Type of Objects to be transformed
 */
public class Verjson<T> {

	/** Optional user-defined namespace to distinguish between different types */
	String namespace;

	/** Type of the serialized objects */
	Class<T> valueClass;

	Map<Long, ? extends Step> steps;

	ObjectMapper mapper;

	/** Highest version available in added transformers, starting with 1. */
	long highestSourceVersion;

	/** Include the creational timestamp in each serialized object */
	boolean includeTimestamp;


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
		includeTimestamp = versions.isIncludeTimestamp();
		mapper = new ObjectMapperFactory().create(versions);
		//mapper.registerModule(new JSR310Module());
		//mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		steps = createStepSequencer().sequence(versions.getSteps());
		highestSourceVersion = determineHighestSourceVersion();
	}


	protected long determineHighestSourceVersion() {
		Preconditions.checkNotNull(steps, "Steps can not be null, at least one NoopStep has to exist");
		Set<Long> keys = steps.keySet();
		Preconditions.checkElementIndex(0, keys.toArray().length, "Steps can not be empty, at least one NoopStep has to exist");
		return Collections.max(keys);
	}


	protected StepSequencer createStepSequencer() {
		return new DefaultStepSequencer();
	}


	protected String getNamespace() {
		return namespace;
	}


	protected ObjectMapper getMapper() {
		return mapper;
	}


	//TODO own exception
	//TODO check only Validation and Transformation (for now)
	/** Serializes the given object to a String */
	public String write(T obj) throws JsonProcessingException {
		Date ts = includeTimestamp ? Date.from(now()) : null;
		MetaWrapper wrapper = new MetaWrapper(getHighestSourceVersion(), getNamespace(), obj, ts);
		return getMapper().writeValueAsString(wrapper);
	}


	/**
	 * Serializes the given object without any metadata to a String. This is basically the raw serialized object from
	 * the data element. Since the metadata is missing, this Json can not be read using the read(..) methods, except the
	 * readPlain-method - if the version is manually passed.
	 */
	public String writePlain(T obj) throws JsonProcessingException {
		return getMapper().writeValueAsString(obj);
	}


	public JsonNode readTree(String json) throws IOException {
		return getMapper().readTree(json);
	}


	protected JsonNode wrapPlainNode(JsonNode node, long version) {
		ObjectNode wrapper = new ObjectNode(JsonNodeFactory.instance);
		wrapper.put(MetaWrapper.ID_VERSION, version);
		wrapper.put(MetaWrapper.ID_DATA, node);
		return wrapper;
	}


	public T readPlain(JsonNode node, long version) throws VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		return read(wrapPlainNode(node, version));
	}


	public T read(String json) throws VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		T result = null;
		try {
			result = read(readTree(json));
		}
		catch (IOException ex) {
			throw new IOReadException("Reading json failed: " + ex.getMessage(), ex);
		}
		return result;
	}


	public T read(JsonNode node) throws VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		T result = null;
		try {
			verifyNamespace(node);
			Long jsonVersion = verifyVersion(node);
			steps.get(jsonVersion).process(node);
			JsonNode data = MetaWrapper.getData(node);
			result = getMapper().treeToValue(data, getValueClass());
		}
		catch (ReadException ex) {
			throw ex;
		}
		catch (IOException ex) {
			throw new IOReadException("Reading json failed: " + ex.getMessage(), ex);
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
