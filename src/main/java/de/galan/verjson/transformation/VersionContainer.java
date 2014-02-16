package de.galan.verjson.transformation;

import org.slf4j.Logger;

import com.google.gson.JsonElement;

import de.galan.commons.logging.Logr;
import de.galan.verjson.v2.MetaUtil;


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


	public VersionContainer(Version version, String valueClassName) {
		targetVersion = version.getTargetVersion();
		sourceVersion = targetVersion - 1L;
		this.valueClassName = valueClassName;
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


	public void transform(JsonElement element) {
		//TODO add version validation by schema
		LOG.info("Transforming {} from {} to {}", valueClassName, getSourceVersion(), getTargetVersion());
		getVersion().transform(MetaUtil.getData(element));

		// update version with target version
		MetaUtil.setVersion(element, getTargetVersion());

		if (getSuccessor() != null) {
			getSuccessor().transform(element);
		}
	}
}
