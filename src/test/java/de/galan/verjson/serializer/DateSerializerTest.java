package de.galan.verjson.serializer;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.core.Verjson;


/**
 * CUT DateSerializer, DateDeserializer
 *
 * @author daniel
 */
public class DateSerializerTest extends AbstractTestParent {

	private Verjson<ClassWithDate> verjson;
	private ClassWithDate input;
	private final static String SERIALIZED = "{\"$v\":1,\"$ts\":\"2014-05-06T06:42:28.000Z\",\"$d\":{\"value\":\"2014-02-17T10:30:07.000Z\"}}";
	private final static Date DATE = dateUtc("2014-02-17T10:30:07Z");


	@Before
	public void before() {
		verjson = Verjson.create(ClassWithDate.class, null);
		input = new ClassWithDate();
		input.value = DATE;
		ApplicationClock.setUtc("2014-05-06T06:42:28Z");
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
