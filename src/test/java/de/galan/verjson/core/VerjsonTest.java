package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.test.TestBean;


/**
 * CUT Verjson
 *
 * @author daniel
 */
public class VerjsonTest extends AbstractTestParent {

	@Test
	public void highestSourceVersionEmpty() throws Exception {
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, null);
		assertThat(verjson.getHighestSourceVersion()).isEqualTo(1L);
	}


	@Test
	public void readBroken() throws Exception {
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, null);
		try {
			verjson.read("askl las jdl");
			fail("should fail");
		}
		catch (IOReadException ex) {
			String msg = "Reading json failed: Unexpected character ('a' (code 97)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')";
			assertThat(ex.getMessage()).startsWith(msg);
		}
	}


	@Test
	public void writePlain() throws Exception {
		Verjson<TestBean> verjson = Verjson.create(TestBean.class, null);
		TestBean bean = new TestBean().content("abc").number(123L);
		String plain = verjson.writePlain(bean);
		assertThat(plain).isEqualTo("{\"content\":\"abc\",\"number\":123}");
	}

}
