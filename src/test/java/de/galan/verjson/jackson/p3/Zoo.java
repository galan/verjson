package de.galan.verjson.jackson.p3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/** x */
public class Zoo {

	public String name;
	public String city;
	public List<Animal> animals;


	@JsonCreator
	public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
		this.name = name;
		this.city = city;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}


	@Override
	public String toString() {
		return "Zoo [name=" + name + ", city=" + city + ", animals=" + animals + "]";
	}

}
