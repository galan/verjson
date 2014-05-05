package de.galan.verjson.samples.v1;

/**
 * Sample implementation for subtype
 * 
 * @author daniel
 */
public class Example1SubB extends Example1Sub {

	public String bbb;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bbb == null) ? 0 : bbb.hashCode());
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
		Example1SubB other = (Example1SubB)obj;
		if (bbb == null) {
			if (other.bbb != null) {
				return false;
			}
		}
		else if (!bbb.equals(other.bbb)) {
			return false;
		}
		return true;
	}

}
