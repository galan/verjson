package de.galan.oldverjson.transformation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * Acts as a container for all Version implementations, does define the optional namespace. Additional type adapters can
 * be registered here.
 * 
 * @author daniel
 */
public class Versions {

	private List<Version> versions;
	private String namespace;
	private Map<Type, Object> typeAdapters;
	private String initialSchema;


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


	public Versions add(Version version) {
		if (version != null) {
			getVersions().add(version);
		}
		return this;
	}


	public Versions registerTypeAdapter(Type type, Object typeAdapter) {
		getTypeAdapter().put(type, typeAdapter);
		return this;
	}


	public Versions registerInitialSchema(String jsonschema) {
		initialSchema = jsonschema;
		return this;
	}


	public String getInitialSchema() {
		return initialSchema;
	}


	public List<Version> getVersions() {
		return versions;
	}


	public Map<Type, Object> getTypeAdapter() {
		return typeAdapters;
	}

}
