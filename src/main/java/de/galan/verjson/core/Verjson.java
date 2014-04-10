package de.galan.verjson.core;

import java.util.Map;

import org.slf4j.Logger;

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

	/** Optional namespace to distinguish between different types */
	String namespace;

	/** Type of the serialized objects */
	private Class<T> valueClass;

	private Map<Long, Step> steps;

}
