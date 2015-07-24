package de.galan.verjson.core;

import org.junit.Before;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.DummyTransformation;
import de.galan.verjson.DummyValidation;
import de.galan.verjson.access.DefaultMetaMapper;
import de.galan.verjson.access.MetaMapper;
import de.galan.verjson.step.IncrementVersionStep;
import de.galan.verjson.step.NoopStep;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * Abstract parent for StepSequencer tests
 *
 * @author daniel
 */
public class AbstractStepSequencerParent extends AbstractTestParent {

	DefaultStepSequencer ss;
	Validation validate;
	Transformation transform;
	IncrementVersionStep increment;
	NoopStep noop;
	MetaMapper metaMapper;


	@Before
	public void before() {
		ss = new DefaultStepSequencer();
		metaMapper = new DefaultMetaMapper();
		validate = new DummyValidation(null);
		transform = new DummyTransformation();
		increment = new IncrementVersionStep();
		noop = new NoopStep();
	}


	protected ProxyStep p(int sourceVersion, Step step) {
		return new ProxyStep(Integer.valueOf(sourceVersion).longValue(), step);
	}


	protected ProxyStep t(int sourceVersion) {
		return p(sourceVersion, transform);
	}


	protected ProxyStep v(int sourceVersion) {
		return p(sourceVersion, validate);
	}


	protected ProxyStep i(int sourceVersion) {
		return p(sourceVersion, increment);
	}


	protected ProxyStep n(int sourceVersion) {
		return p(sourceVersion, noop);
	}

}
