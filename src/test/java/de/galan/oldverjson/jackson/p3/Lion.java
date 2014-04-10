package de.galan.oldverjson.jackson.p3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/** x */
public class Lion extends Animal {

	private String name;


	@JsonCreator
	public Lion(@JsonProperty("name") String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public String getSound() {
		return "Roar";
	}


	public String getType() {
		return "carnivorous";
	}


	public boolean isEndangered() {
		return true;
	}


	@Override
	public String toString() {
		return "Lion [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()=" + getType() + ", isEndangered()="
				+ isEndangered() + "]";
	}

}
