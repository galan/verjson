package de.galan.verjson.jackson.p2;

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
public class Poly2Test extends AbstractTestParent {

	private final static Logger LOG = Logr.get();


	@Test
	public void testName() throws Exception {
		Example1 e1 = Example1.createSample();
		e1.first = "aaa\nbbbßäöü";
		ObjectMapper mapper = new ObjectMapper();

		//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "$type")
		//@JsonSubTypes({@Type(value = Lion.class, name = "lion"), @Type(value = Elephant.class, name = "elephant")})
		//ClassPool pool = ClassPool.getDefault();
		//CtClass ct = pool.getCtClass(Example1.class.getName());
		//ConstPool cp = ct.getConstPool();
		//AnnotationsAttribute attr = new AnnotationsAttribute(pool, AnnotationsAttribute.visibleTag);

		String output = mapper.writeValueAsString(e1);
		LOG.info(output);
		Example1 read = mapper.readValue(output, Example1.class);
		LOG.info("" + read);
	}

}
