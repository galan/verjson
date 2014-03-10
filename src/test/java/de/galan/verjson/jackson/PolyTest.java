package de.galan.verjson.jackson;

import org.junit.Test;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.example.v1.Example1;


/**
 * Tests for jackson
 * 
 * @author daniel
 */
public class PolyTest extends AbstractTestParent {

	private final static Logger LOG = Logr.get();


	@Test
	public void testName() throws Exception {
		Example1 e1 = Example1.createSample();
		ObjectMapper mapper = new ObjectMapper();
		String output = mapper.writeValueAsString(e1);
		LOG.info(output);
	}

}
