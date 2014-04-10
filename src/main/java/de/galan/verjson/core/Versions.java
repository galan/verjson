package de.galan.verjson.core;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;

import de.galan.verjson.transformation.Step;


/**
 * Acts as a container for all Steps (transformations and schema validations), does define the optional namespace.
 * Additional type de/serializers can be registered here.
 * 
 * @author daniel
 */
public class Versions {

	private ListMultimap<Long, Step> steps;
	private String namespace;
	private Map<Class<?>, JsonSerializer<?>> serializers;
	private Map<Class<?>, JsonDeserializer<?>> deserializers;


	public Versions() {
		this(null);
	}


	public Versions(String namespace) {
		setNamespace(namespace);
		steps = ArrayListMultimap.create();
		serializers = Maps.newHashMap();
		deserializers = Maps.newHashMap();
	}


	protected void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String getNamespace() {
		return namespace;
	}


	public void configure() {
		// can be overriden
	}


	public Versions add(Long sourceVersion, Step step) {
		if (step != null) {
			getSteps().put(sourceVersion, step);
		}
		return this;
	}


	public <T> Versions registerSerializer(JsonSerializer<T> serializer) {
		if (serializer != null) {
			serializers.put(serializer.getClass(), serializer);
		}
		return this;
	}


	public <T> Versions registerDeserializer(JsonDeserializer<T> deserializer) {
		if (deserializer != null) {
			deserializers.put(deserializer.getClass(), deserializer);
		}
		return this;
	}


	public ListMultimap<Long, Step> getSteps() {
		return steps;
	}


	public Collection<JsonSerializer<?>> getSerializer() {
		return serializers.values();
	}


	public Collection<JsonDeserializer<?>> getDeserializer() {
		return deserializers.values();
	}
}
