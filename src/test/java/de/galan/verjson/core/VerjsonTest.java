package de.galan.verjson.core;

import static de.galan.commons.test.Tests.*;
import static de.galan.commons.time.DateDsl.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.collect.Lists;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.example.MockTransformer;
import de.galan.verjson.example.v1.Example1;
import de.galan.verjson.example.v1.Example1Element;
import de.galan.verjson.example.v1.Example1SubA;
import de.galan.verjson.example.v1.Example1SubB;
import de.galan.verjson.example.v1.Example1Verjson;
import de.galan.verjson.example.v2.Example2;
import de.galan.verjson.example.v2.Example2Element;
import de.galan.verjson.example.v2.Example2Transformer;
import de.galan.verjson.example.v2.Example2Verjson;


/**
 * CUT Verjson
 * 
 * @author daniel
 */
@Ignore
public class VerjsonTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	@Test
	public void v1WriteReadWithoutTransformer() throws Exception {
		Verjson<Example1> v1 = new Example1Verjson();
		Example1 orig1 = createV1();
		String v1String = v1.write(orig1);
		LOG.info("Created: {}", v1String);
		assertFileEqualsToString("example1.json", VerjsonTest.class, v1String);
		Example1 read1 = v1.read(v1String);

		assertEquals(orig1.first, read1.first);
		assertEquals(orig1.second, read1.second);
		assertEquals(orig1.third, read1.third);
		assertEquals(orig1.forth, read1.forth);
		assertThat(orig1.fifth, containsInOrder("a", "b", "c"));
		assertEquals(orig1.sixth.one, read1.sixth.one);
		assertEquals(orig1.sixth.two, read1.sixth.two);
		assertEquals(orig1.subA.parent, read1.subA.parent);
		assertEquals(((Example1SubA)orig1.subA).aaa, ((Example1SubA)read1.subA).aaa);
		assertEquals(orig1.subB.parent, read1.subB.parent);
		assertEquals(((Example1SubB)orig1.subB).bbb, ((Example1SubB)read1.subB).bbb);
		assertNull(orig1.empty);
	}


	@Test
	public void v2WriteReadWithTransformer() throws Exception {
		Verjson<Example2> v2 = new Example2Verjson();
		MockTransformer mockTransformer = new MockTransformer(1L);
		v2.appendTransformer(mockTransformer); // appending to increase target version
		Example2 orig2 = createV2();
		String v2String = v2.write(orig2);
		LOG.info("Created: {}", v2String);
		assertFileEqualsToString("example2.json", VerjsonTest.class, v2String);

		v2.appendTransformer(mockTransformer); // appending to increase target version
		Example2 read2 = v2.read(v2String);
		assertFalse(mockTransformer.hasTransformed()); // no transformation was required
		assertEquals(orig2.segundo, read2.segundo);
		assertEquals(orig2.third, read2.third);
		assertEquals(orig2.forth, read2.forth);
		assertEquals(orig2.fifth, read2.fifth);
		assertEquals(orig2.sixth.get(0).uno, read2.sixth.get(0).uno);
		assertEquals(orig2.sixth.get(0).three, read2.sixth.get(0).three);
		assertEquals(orig2.sixth.get(1).uno, read2.sixth.get(1).uno);
		assertEquals(orig2.sixth.get(1).three, read2.sixth.get(1).three);
		assertEquals(2, read2.sixth.size());
	}


	@Test
	public void oldClientReadsNewerVersion() throws Exception {
		String v2String = readFile(VerjsonTest.class, "example2.json");
		try {
			new Example1Verjson().read(v2String); // using an older client without transformer
			fail("Version should not be supported");
		}
		catch (VersionNotSupportedException ex) {
			assertEquals(1L, ex.getVersionSupported());
			assertEquals(2L, ex.getVersionRequired());
			assertEquals("Verjson only supports version '1', required version is '2'.", ex.getMessage());
		}
	}


	@Test
	public void transformV1ToV2() throws Exception {
		String v1String = readFile(VerjsonTest.class, "example1.json");
		Example2Verjson v2 = new Example2Verjson();
		v2.appendTransformer(new Example2Transformer());
		Example2 read2 = v2.read(v1String);

		assertEquals(2L, read2.segundo.longValue());
		assertEquals(true, read2.third);
		assertEquals(date("2013-12-01 16:40:00"), read2.forth);
		assertEquals("abc", read2.fifth);
		assertEquals(1L, read2.sixth.get(0).uno.longValue());
		assertNull(read2.sixth.get(0).three);
	}


	protected Example1 createV1() {
		Example1 result = new Example1();
		result.first = "1";
		result.second = 2L;
		result.third = true;
		result.forth = date("2013-12-01 16:40:00");
		result.fifth = Lists.newArrayList("a", "b", "c");
		Example1Element element = new Example1Element();
		element.one = 1L;
		element.two = "2";
		result.sixth = element;
		Example1SubA subA = new Example1SubA();
		subA.parent = "parentA";
		subA.aaa = "aaa";
		result.subA = subA;
		Example1SubB subB = new Example1SubB();
		subB.parent = "parentB";
		subB.bbb = "bbb";
		result.subB = subB;
		return result;
	}


	protected Example2 createV2() {
		Example2 result = new Example2();
		result.segundo = 2L;
		result.third = false;
		result.forth = date("2013-12-01 21:00:00");
		result.fifth = "abc";
		Example2Element element1 = new Example2Element();
		element1.uno = 1L;
		element1.three = "3";
		Example2Element element2 = new Example2Element();
		element2.uno = 2L;
		element2.three = "6";
		result.sixth = Lists.newArrayList(element1, element2);
		return result;
	}

}
