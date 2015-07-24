package de.galan.verjson.util;

import static de.galan.verjson.util.Transformations.*;

import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * daniel should have written a comment here.
 *
 * @author galan
 */
public class VersionReaderUtil {

	/** Returns the source version from a wrapped JsonNode */
	private static Function<JsonNode, Long> getVersionByRootElement(String elementName) {
		return (node) -> obj(node).get(elementName).asLong();
	}

}
