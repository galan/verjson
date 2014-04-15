package de.galan.verjson.example.v2;

/**
 * Element inside Example2
 * 
 * @author daniel
 */
public class Example2Element {

	public Long uno; // renamed
	//public String two; // deleted
	public String three; // new


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
		Example2Element other = (Example2Element)obj;
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
