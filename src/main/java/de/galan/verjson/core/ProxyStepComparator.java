package de.galan.verjson.core;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Lists;

import de.galan.verjson.step.Step;
import de.galan.verjson.step.transformation.Transformation;
import de.galan.verjson.step.validation.Validation;


/**
 * Sorts {@link ProxyStep}s, small sourceVersions before larger. Order inside a sourceVersion: Validation,
 * Transformation, other.
 *
 * @author daniel
 */
public class ProxyStepComparator implements Comparator<ProxyStep> {

	List<Class<?>> order = Lists.newArrayList();


	public ProxyStepComparator() {
		order.add(Transformation.class);
		order.add(Validation.class);
	}


	@Override
	public int compare(ProxyStep s1, ProxyStep s2) {
		int result = ObjectUtils.compare(s1.getSourceVersion(), s2.getSourceVersion());
		if (result == 0) {
			Class<? extends Step> c1 = s1.getStep().getClass();
			Class<? extends Step> c2 = s2.getStep().getClass();
			if (!c1.equals(c2)) {
				if (Validation.class.isAssignableFrom(c1)) {
					result = -1;
				}
				else if (Validation.class.isAssignableFrom(c2)) {
					result = 1;
				}
				else if (Transformation.class.isAssignableFrom(c1)) {
					result = -1;
				}
				//else if (Transformation.class.isAssignableFrom(c2)) {
				else {
					result = 1;
				}
			}
		}
		return result;
	}

}
