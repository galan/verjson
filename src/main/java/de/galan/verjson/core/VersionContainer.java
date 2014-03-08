package de.galan.verjson.core;

import org.slf4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;

import de.galan.commons.logging.Logr;
import de.galan.verjson.transformation.Version;
import de.galan.verjson.validation.Validator;


/**
 * Contains a List of Transformer for a specific source version. Transformation starts on one Container, iterates over
 * all Transformer in that Container, and continues on it's successor. This way clients don't have to know which further
 * transformations are required. The only requirement is that the correct Container for a specific source version is
 * called.
 * 
 * @author daniel
 */
public class VersionContainer {

	private static final Logger LOG = Logr.get();

	private Long sourceVersion;
	private Long targetVersion;
	private VersionContainer successor;
	private Version version;
	private String valueClassName;
	private Validator validator;


	public VersionContainer(Version version, String valueClassName) {
		Preconditions.checkNotNull(version, "Version could not be null");
		targetVersion = version.getTargetVersion();
		sourceVersion = targetVersion - 1L;
		this.valueClassName = valueClassName;
		setVersion(version);
	}


	public Long getSourceVersion() {
		return sourceVersion;
	}


	public Long getTargetVersion() {
		return targetVersion;
	}


	public VersionContainer getSuccessor() {
		return successor;
	}


	public void setSuccessor(VersionContainer successor) {
		this.successor = successor;
	}


	public void setVersion(Version version) {
		this.version = version;
	}


	protected Version getVersion() {
		return version;
	}


	public Validator getValidator() {
		return validator;
	}


	public void setValidator(Validator validator) {
		this.validator = validator;
	}


	public void transform(JsonElement element) {
		LOG.info("Transforming {} from {} to {}", valueClassName, getSourceVersion(), getTargetVersion());//TODO fix log for 0>1
		//if (getTargetVersion() > 1L) {
		getVersion().transform(MetaUtil.getData(element));
		//}
		// update version with target version
		MetaUtil.setVersion(element, getTargetVersion());

		//add version validation by schema
		if (getValidator() != null) {
			getValidator().validate(MetaUtil.getData(element).toString());
		}

		// invoke chain of successor transformations
		if (getSuccessor() != null) {
			getSuccessor().transform(element);
		}
	}


	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("targetVersion", getTargetVersion()).toString();
	}

}
