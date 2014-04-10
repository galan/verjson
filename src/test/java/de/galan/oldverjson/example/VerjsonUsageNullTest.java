package de.galan.oldverjson.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.oldverjson.core.Verjson;
import de.galan.oldverjson.example.v1.Example1;
import de.galan.oldverjson.example.v1.Example1Versions;
import de.galan.oldverjson.example.v2.Example2;
import de.galan.oldverjson.example.v2.Example2Versions;
import de.galan.oldverjson.example.v3.Example3;
import de.galan.oldverjson.example.v3.Example3Versions;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class VerjsonUsageNullTest extends AbstractTestParent {

	private Verjson<Example1> v1;
	private Verjson<Example2> v2;
	private Verjson<Example3> v3;


	@Before
	public void before() {
		v1 = Verjson.create(Example1.class, new Example1Versions());
		v2 = Verjson.create(Example2.class, new Example2Versions());
		v3 = Verjson.create(Example3.class, new Example3Versions());
	}


	@Test
	public void nullValue() throws Exception {
		String written1 = v1.write(new Example1());
		assertThat(written1).isEqualTo("{\"$v\":1,\"$d\":{}}");
		assertThat(v1.read(written1)).isEqualTo(new Example1());
		assertThat(v2.read(written1)).isEqualToComparingFieldByField(new Example2());
		assertThat(v2.read(written1)).isEqualTo(new Example2());
		assertThat(v3.read(written1)).isEqualTo(new Example3());
	}

}
