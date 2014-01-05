package de.galan.verjson;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.google.gson.JsonElement;

import de.galan.commons.logging.Logr;


/**
 * Contains a List of Transformer for a specific source version. Transformation starts on one Container, iterates over
 * all Transformer in that Container, and continues on it's successor. This way clients don't have to know which further
 * transformations are required. The only requirement is that the correct Container for a specific source version is
 * called.
 * 
 * @author daniel
 */
public class TransformerContainer {

	private static final Logger LOG = Logr.get();

	private Long sourceVersion;
	private Long targetVersion;
	private TransformerContainer successor;
	private List<Transformer> transformers;
	private String valueClassName;


	public TransformerContainer(Long sourceVersion, Long targetVersion, String valueClassName) {
		this.sourceVersion = sourceVersion;
		this.targetVersion = targetVersion;
		this.valueClassName = valueClassName;
		transformers = new ArrayList<>();
	}


	public Long getSourceVersion() {
		return sourceVersion;
	}


	public Long getTargetVersion() {
		return targetVersion;
	}


	public TransformerContainer getSuccessor() {
		return successor;
	}


	public void setSuccessor(TransformerContainer successor) {
		this.successor = successor;
	}


	public void addTransformer(Transformer transformer) {
		getTransformers().add(transformer);
	}


	protected List<Transformer> getTransformers() {
		return transformers;
	}


	public void transform(JsonElement element) {
		//TODO add version validation?
		LOG.info("Transforming {} from {} to {}", valueClassName, getSourceVersion(), getTargetVersion());
		for (int i = 0; i < getTransformers().size(); i++) {
			getTransformers().get(i).transform(element.getAsJsonObject().get("data"));
		}

		// update version with target version
		element.getAsJsonObject().addProperty("version", getTargetVersion());

		if (getSuccessor() != null) {
			getSuccessor().transform(element);
		}
	}

}
