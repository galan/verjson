package de.galan.verjson.serializer;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.core.Verjson;


/**
 * CUT ZonedDateTimeSerializer, ZonedDateTimeDeserializer
 *
 * @author daniel
 */
public class ZonedDateTimeSerializerTest extends AbstractTestParent {

	private Verjson<ClassWithZdt> verjson;
	private ClassWithZdt input;
	private final static String SERIALIZED = "{\"$v\":1,\"$ts\":\"2014-05-06T06:42:28.000Z\",\"$d\":{\"value\":\"2014-10-20T13:09:58.000Z\"}}";
	private final static ZonedDateTime ZDT = ZonedDateTime.parse("2014-10-20T13:09:58Z");


	@Before
	public void before() {
		verjson = Verjson.create(ClassWithZdt.class, null);
		input = new ClassWithZdt();
		input.value = ZDT;
		ApplicationClock.setUtc("2014-05-06T06:42:28Z");
	}


	@Test
	public void write() throws Exception {
		String written = verjson.write(input);
		assertThat(written).isEqualTo(SERIALIZED);
	}


	@Test
	public void read() throws Exception {
		ClassWithZdt read = verjson.read(SERIALIZED);
		assertThat(read.value).isEqualTo(ZDT);
	}


	@Test
	public void writeNonUtc() throws Exception {
		input.value = ZonedDateTime.of(2014, 10, 20, 15, 9, 58, 0, ZONE_LOCAL);
		String written = verjson.write(input);
		assertThat(written).isEqualTo(SERIALIZED);
	}

}


/** Dummy class */
class ClassWithZdt {

	ZonedDateTime value;
	ZonedDateTime empty;
	String otherEmtpy;

}
