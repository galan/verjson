package de.galan.verjson.jackson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.fge.jackson.JacksonUtils;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class Poly2Test extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		JacksonUtils JacksonUtils = new JacksonUtilsImpl();

		Collection<Base> data = new LinkedBlockingQueue<Base>();
		data.add(new ConcreteA());
		data.add(new ConcreteB());
		data.add(new ConcreteC());

		String json = JacksonUtils.marshallIntoString(data);

		System.out.println(json);

		Collection<? extends Adapter> adapters = JacksonUtils.unmarshall(json, new TypeReference<ArrayList<Adapter>>() {});

		for (Adapter adapter: adapters) {
			System.out.println(adapter.getClass().getName());
		}
	}

}
