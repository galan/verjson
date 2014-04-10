package de.galan.oldverjson.example.v2;

/**
 * Sample implementation for subtype
 * 
 * @author daniel
 */
public class Example2SubA extends Example2Sub {

	public String aaa;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aaa == null) ? 0 : aaa.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Example2SubA other = (Example2SubA)obj;
		if (aaa == null) {
			if (other.aaa != null) {
				return false;
			}
		}
		else if (!aaa.equals(other.aaa)) {
			return false;
		}
		return true;
	}

}
