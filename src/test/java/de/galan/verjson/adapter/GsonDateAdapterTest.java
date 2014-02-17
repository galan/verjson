package de.galan.verjson.adapter;

import static de.galan.commons.time.DateDsl.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.v2.Verjson;


/**
 * CUT GsonDateAdapter
 * 
 * @author daniel
 */
public class GsonDateAdapterTest extends AbstractTestParent {

	private Verjson<ClassWithDate> verjson;
	private ClassWithDate input;
	private final static String SERIALIZED = "{\"$v\":1,\"$d\":{\"value\":\"2014-02-17T10:30:07Z\"}}";
	private final static Date DATE = dateIso("2014-02-17T10:30:07Z");


	@Before
	public void before() {
		verjson = Verjson.create(ClassWithDate.class, null);
		input = new ClassWithDate();
		input.value = DATE;
	}


	@Test
	public void write() throws Exception {
		String written = verjson.write(input);
		assertThat(written).isEqualTo(SERIALIZED);
	}


	@Test
	public void read() throws Exception {
		ClassWithDate read = verjson.read(SERIALIZED);
		assertThat(read.value).isEqualTo(DATE);
	}

}


/** Dummy class */
class ClassWithDate {

	Date value;
	Date empty;
	String otherEmtpy;

}
