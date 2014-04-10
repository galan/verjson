package de.galan.verjson.core;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Preconditions;

import de.galan.commons.logging.Logr;
import de.galan.verjson.transformation.Step;


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
		mapper = constructMapper(versions);

		/*
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
		*/
	}


	protected ObjectMapper constructMapper(Versions versions) {
		ObjectMapper result = new ObjectMapper();
		SimpleModule module = new SimpleModule("VerjsonModule");
		for (JsonSerializer<?> serializer: versions.getSerializer()) {
			module.addSerializer(serializer);
		}
		for (JsonDeserializer deserializer: versions.getDeserializer()) {
			Method method = null;
			try {
				method = deserializer.getClass().getMethod("deserialize", JsonParser.class, DeserializationContext.class);
				module.addDeserializer(method.getReturnType(), deserializer);
			}
			catch (NullPointerException | NoSuchMethodException | SecurityException ex) {
				String methodName = (method == null) ? "null" : method.getName();
				String returnTypeName = (method == null || method.getReturnType() == null) ? "null" : method.getReturnType().toString();
				LOG.error("Unable to register deserializer for Class<" + returnTypeName + ">." + methodName + "(..)", ex);
			}
		}
		return result;
	}

}
