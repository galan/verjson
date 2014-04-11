package de.galan.verjson.core;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;

import de.galan.commons.logging.Logr;
import de.galan.verjson.step.Step;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ProxyStep implements Step {

	private static final Logger LOG = Logr.get();

	Long sourceVersion;
	Step step;
	Step successor;


	public ProxyStep(Long sourceVersion, Step step) {
		Preconditions.checkNotNull(sourceVersion, "SourceVersion could not be null");
		Preconditions.checkNotNull(step, "Step could not be null");
		this.sourceVersion = sourceVersion;
		this.step = step;
	}


	@Override
	public void process(JsonNode node) {
		LOG.info("Processing {}/{}", getSourceVersion(), getStep().getClass()); // TODO Idea - name annotation for steps for better debugging
		getStep().process(node);
		if (successor != null) {
			successor.process(node);
		}
	}


	public Long getSourceVersion() {
		return sourceVersion;
	}


	protected Step getStep() {
		return step;
	}

}
