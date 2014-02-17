package de.galan.verjson.v2;

/**
 * The namespace of the element to process is not equal to the namespace defined for this verjson instance.
 * 
 * @author daniel
 */
public class NamespaceMismatchException extends ReadException {

	private String namespaceDefined;
	private String namespaceGiven;


	public NamespaceMismatchException(String namespaceDefined, String namespaceGiven) {
		super("Verjson only supports namespace '" + namespaceDefined + "', element has namespace '" + namespaceGiven + "'.");
		this.namespaceDefined = namespaceDefined;
		this.namespaceGiven = namespaceGiven;
	}


	public String getNamespaceDefined() {
		return namespaceDefined;
	}


	public String getNamespaceGiven() {
		return namespaceGiven;
	}

}
