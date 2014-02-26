package de.galan.verjson.transformation;

/**
 * Good starting point to write own an own Version.
 * 
 * @author daniel
 */
public abstract class AbstractVersion extends AbstractTransformation implements Version {

	@Override
	public String getSchema() {
		return null;
	}

}
