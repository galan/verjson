package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.step.IncrementVersionStep;
import de.galan.verjson.step.NoopStep;


/**
 * CUT ProxyStep
 * 
 * @author daniel
 */
public class ProxyStepTest extends AbstractTestParent {

	@Test
	public void toStringTest() throws Exception {
		ProxyStep ps1 = new ProxyStep(1L, new NoopStep());
		assertThat(ps1.toString()).isEqualTo("ProxyStep 1/NoopStep");
		ProxyStep ps2 = new ProxyStep(2L, new NoopStep());
		assertThat(ps2.toString()).isEqualTo("ProxyStep 2/NoopStep");
		ProxyStep ps3 = new ProxyStep(1L, new IncrementVersionStep());
		assertThat(ps3.toString()).isEqualTo("ProxyStep 1/IncrementVersionStep");
		ProxyStep ps4 = new ProxyStep(2L, new IncrementVersionStep());
		assertThat(ps4.toString()).isEqualTo("ProxyStep 2/IncrementVersionStep");
	}

}
