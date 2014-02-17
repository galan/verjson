package de.galan.verjson.transformation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class Versions {

	private List<Version> versions;
	private String namespace;
	private Map<Type, Object> typeAdapters;


	public Versions() {
		this(null);
	}


	public Versions(String namespace) {
		setNamespace(namespace);
		versions = Lists.newArrayList();
		typeAdapters = Maps.newHashMap();
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


	public void add(Version version) {
		getVersions().add(version);
	}


	public void registerTypeAdapter(Type type, Object typeAdapter) {
		getTypeAdapter().put(type, typeAdapter);
	}


	public List<Version> getVersions() {
		return versions;
	}


	public Map<Type, Object> getTypeAdapter() {
		return typeAdapters;
	}

}
