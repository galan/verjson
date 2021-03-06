package de.galan.verjson.samples.v3;

/**
 * Sample implementation for subtype
 * 
 * @author daniel
 */
public class Example3SubA extends Example3Sub {

	public String aaa;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aaa == null) ? 0 : aaa.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Example3SubA other = (Example3SubA)obj;
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
