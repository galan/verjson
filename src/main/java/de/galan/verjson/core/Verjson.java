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
import com.google.common.base.Preconditions;

import de.galan.verjson.access.MetaMapper;
import de.galan.verjson.step.ProcessStepException;
import de.galan.verjson.step.Step;
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

	MetaMapper meta;


	//private Function<JsonNode, Long> versionReader;

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
		meta = versions.getMetaMapper();
		includeTimestamp = versions.isIncludeTimestamp();
		mapper = new ObjectMapperFactory().create(versions);
		steps = createStepSequencer().sequence(versions.getSteps(), getMetaMapper());
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


	protected MetaMapper getMetaMapper() {
		return meta;
	}


	//TODO own exception
	//TODO check only Validation and Transformation (for now)
	/** Serializes the given object to a String */
	public String write(T obj) {
		Date ts = includeTimestamp ? Date.from(now()) : null;
		JsonNode mappedNode = getMapper().valueToTree(obj);
		JsonNode result = getMetaMapper().getPostMappingFunction().postMapNode(mappedNode, getHighestSourceVersion(), getNamespace(), ts);
		return result.toString();
	}


	/**
	 * Serializes the given object without any metadata to a String. This is basically the raw serialized object from
	 * the data element. Since the metadata is missing, this Json can not be read using the read(..) methods, except the
	 * readPlain-method - if the version is manually passed.
	 */
	public String writePlain(T obj) throws JsonProcessingException {
		return getMapper().writeValueAsString(obj);
	}


	/*
	protected JsonNode wrapPlainNode(JsonNode node, long version) {
		ObjectNode wrapper = JsonNodeFactory.instance.objectNode();
		wrapper.put(DefaultMetaMapper.ID_VERSION, version);
		wrapper.put(DefaultMetaMapper.ID_DATA, node);
		return wrapper;
	}


	public T readPlain(JsonNode node, long version) throws VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		return read(wrapPlainNode(node, version));
	}
	 */

	public JsonNode readTree(String json) throws IOException {
		return getMapper().readTree(json);
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
			steps.get(jsonVersion).process(node, getMetaMapper());
			JsonNode data = getMetaMapper().getDataReader().apply(node);
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
		//Long sourceVersion = versionReader.apply(node);
		Long sourceVersion = getMetaMapper().getVersionReader().apply(node);
		if (sourceVersion > getHighestSourceVersion()) {
			throw new VersionNotSupportedException(getHighestSourceVersion(), sourceVersion, getValueClass());
		}
		return sourceVersion;
	}


	protected String verifyNamespace(JsonNode node) throws NamespaceMismatchException {
		// verify namespace
		String ns = getMetaMapper().getNamespaceReader().apply(node);
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
