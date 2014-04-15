package de.galan.verjson.example.v3;

/**
 * Element inside Example3
 * 
 * @author daniel
 */
public class Example3Element {

	public Long uno;
	public String three;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((three == null) ? 0 : three.hashCode());
		result = prime * result + ((uno == null) ? 0 : uno.hashCode());
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
		Example3Element other = (Example3Element)obj;
		if (three == null) {
			if (other.three != null) {
				return false;
			}
		}
		else if (!three.equals(other.three)) {
			return false;
		}
		if (uno == null) {
			if (other.uno != null) {
				return false;
			}
		}
		else if (!uno.equals(other.uno)) {
			return false;
		}
		return true;
	}

}
