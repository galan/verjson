package de.galan.verjson.examples.polymorphism;

import de.galan.verjson.core.Versions;


/**
 * Beside the transformations the types could be registered here.
 *
 * @author daniel
 */
public class PolymorphismVersions extends Versions {

	@Override
	public void configure() {
		registerSubclass(ParentClass.class, ChildA.class, "typeA");
		registerSubclass(ParentClass.class, ChildB.class, "typeB");
		setIncludeTimestamp(false); // ignore in demo
	}

}
