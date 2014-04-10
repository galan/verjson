package de.galan.oldverjson.core;

import static org.apache.commons.lang3.StringUtils.*;


/**
 * The namespace of the element to process is not equal to the namespace defined for this verjson instance.
 * 
 * @author daniel
 */
public class NamespaceMismatchException extends ReadException {

	private String namespaceDefined;
	private String namespaceGiven;


	public NamespaceMismatchException(String namespaceDefined, String namespaceGiven) {
		super(generateMessage(namespaceDefined, namespaceGiven));
		this.namespaceDefined = namespaceDefined;
		this.namespaceGiven = namespaceGiven;
	}


	protected static String generateMessage(String namespaceDefined, String namespaceGiven) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Verjson only supports ");
		if (isBlank(namespaceDefined)) {
			buffer.append("empty namespace");
		}
		else {
			buffer.append("namespace '" + namespaceDefined + "'");
		}
		buffer.append(", element has ");
		if (isBlank(namespaceGiven)) {
			buffer.append("no namespace");
		}
		else {
			buffer.append("namespace '" + namespaceGiven + "'");
		}
		return buffer.toString();
	}


	public String getNamespaceDefined() {
		return namespaceDefined;
	}


	public String getNamespaceGiven() {
		return namespaceGiven;
	}

}
