package de.galan.verjson.core;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;

import de.galan.oldverjson.transformation.Transformation;
import de.galan.verjson.step.Step;
import de.galan.verjson.step.validation.Validation;


/**
 * daniel should have written a comment here.
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
		int result = ObjectUtils.compare(s1.getSourceVersion(), s1.getSourceVersion());
		if (result == 0) {
			Class<? extends Step> c1 = s1.getStep().getClass();
			Class<? extends Step> c2 = s2.getStep().getClass();
			if (!c1.equals(c2)) {
				if (c1.isAssignableFrom(Validation.class)) {
					result = 1;
				}
				else if (c2.isAssignableFrom(Validation.class)) {
					result = -1;
				}
				else if (c1.isAssignableFrom(Transformation.class)) {
					result = 1;
				}
				else if (c1.isAssignableFrom(Transformation.class)) {
					result = -1;
				}
			}
		}
		return result;
	}

}
