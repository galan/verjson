package de.galan.verjson.test;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * Sample bean
 * 
 * @author daniel
 */
public class TestBean {

	public String content;
	public Long number;
	public Object unrecognized;


	public TestBean content(String value) {
		content = value;
		return this;
	}


	public TestBean number(Long value) {
		number = value;
		return this;
	}


	public TestBean unrecognized(Object obj) {
		unrecognized = obj;
		return this;
	}


	@Override
	public int hashCode() {
		return Objects.hash(content, number);
	}


	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

}
