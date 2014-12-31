package de.galan.verjson.util;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.ApplicationClock;
import de.galan.verjson.core.Verjson;
import de.galan.verjson.test.TestBean;


/**
 * CUT MetaWrapper
 *
 * @author daniel
 */
public class MetaWrapperTest extends AbstractTestParent {

	@Test
	public void ts() throws Exception {
		Date fixedDate = dateUtc("2014-05-06T06:42:28Z");
		ApplicationClock.setUtc(fixedDate);
		TestBean bean = new TestBean().content("abc");
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, null);
		String written = verjson.write(bean);
		JsonNode node = verjson.readTree(written);
		Date ts = MetaWrapper.getTimestamp(node);
		assertThat(ts).isEqualTo(fixedDate);
	}

}
