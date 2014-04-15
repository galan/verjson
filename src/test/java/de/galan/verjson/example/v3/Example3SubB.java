package de.galan.verjson.example.v3;

/**
 * Sample implementation for subtype
 * 
 * @author daniel
 */
public class Example3SubB extends Example3Sub {

	public String bbb; // renamed back


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bbb == null) ? 0 : bbb.hashCode());
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
		Example3SubB other = (Example3SubB)obj;
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
