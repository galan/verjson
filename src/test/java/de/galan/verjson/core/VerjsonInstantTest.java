package de.galan.verjson.core;

import static de.galan.commons.test.Tests.*;
import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.StrictAssertions.*;

import java.time.Instant;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;


/**
 * Testing Instant
 *
 * @author daniel
 */
public class VerjsonInstantTest extends AbstractTestParent {

	@Test
	public void testInstant() throws Exception {
		Verjson<Instant> verjson = Verjson.create(Instant.class, null);
		ApplicationClock.setUtc("2014-01-02T08:23:10.987Z");
		String written = verjson.write(now());
		assertThat(written).isEqualTo(readFile(getClass(), "serializer-instant.json"));
	}

}
