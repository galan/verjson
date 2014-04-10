package de.galan.oldverjson.example.v3;

/**
 * Base class for subtypes
 * 
 * @author daniel
 */
public abstract class Example3Sub {

	public String parent;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Example3Sub other = (Example3Sub)obj;
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		}
		else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}

}
