package de.galan.verjson.core;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import de.galan.verjson.step.NoopStep;
import de.galan.verjson.step.Step;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class StepSequencerTest extends AbstractStepSequencerParent {

	@Test
	public void empty() throws Exception {
		ListMultimap<Long, Step> input = ArrayListMultimap.create();
		Map<Long, ProxyStep> output = ss.sequence(input);
		assertThat(output).hasSize(1);
		assertThat(output.get(1L).getStep().getClass()).isEqualTo(NoopStep.class);
	}

}
