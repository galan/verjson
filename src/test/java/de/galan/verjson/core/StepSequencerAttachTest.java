package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.verjson.step.IncrementVersionStep;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class StepSequencerAttachTest extends AbstractStepSequencerParent {

	@Test
	public void attach() throws Exception {
		List<ProxyStep> proxies = Lists.newArrayList(v(1), i(1), i(2), t(3), i(3));
		Map<Long, ProxyStep> map = ss.attachVersions(proxies);

		ProxyStep proxy1 = map.get(1L);
		assertThat(Validation.class).isAssignableFrom(proxy1.getStep().getClass());

		ProxyStep proxy2 = map.get(2L);
		assertThat(IncrementVersionStep.class).isAssignableFrom(proxy2.getStep().getClass());

		ProxyStep proxy3 = map.get(3L);
		assertThat(Transformation.class).isAssignableFrom(proxy3.getStep().getClass());

		assertThat(map.get(4L)).isNull();
		assertThat(map).hasSize(3);
	}

}
