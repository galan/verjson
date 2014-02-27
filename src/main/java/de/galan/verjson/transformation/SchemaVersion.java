package de.galan.verjson.transformation;

/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class SchemaVersion extends EmptyVersion {

	private String schema;


	public SchemaVersion(long targetVersion, String schema) {
		super(targetVersion);
		this.schema = schema;
	}


	@Override
	public String getSchema() {
		return schema;
	}

}
