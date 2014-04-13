package de.galan.verjson.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class OtherTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		assertTrue(List.class.isAssignableFrom(ArrayList.class));
		assertFalse(ArrayList.class.isAssignableFrom(List.class));
	}

}
