package de.galan.verjson.usage;

import org.junit.Test;

import com.google.gson.JsonElement;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.example.v2.Example2;
import de.galan.verjson.transformation.AbstractVersion;
import de.galan.verjson.transformation.Versions;
import de.galan.verjson.v2.Verjson;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class UsageTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		Verjson<Example2> ver = Verjson.create(Example2.class, new ExampleVersions());
	}

}


/** x */
class ExampleVersions extends Versions {

	@Override
	public void configure() {
		add(new ExampleVersion2());
	}

}


/** x */
class ExampleVersion2 extends AbstractVersion {

	@Override
	public long getTargetVersion() {
		return 2;
	}


	@Override
	public void transform(JsonElement element) {
		// nada
	}

}
