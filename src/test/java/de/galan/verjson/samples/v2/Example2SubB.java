package de.galan.verjson.samples.v2;

/**
 * Sample implementation for subtype
 * 
 * @author daniel
 */
public class Example2SubB extends Example2Sub {

	public String ccc; // renamed bbb


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccc == null) ? 0 : ccc.hashCode());
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
		Example2SubB other = (Example2SubB)obj;
		if (ccc == null) {
			if (other.ccc != null) {
				return false;
			}
		}
		else if (!ccc.equals(other.ccc)) {
			return false;
		}
		return true;
	}

}
