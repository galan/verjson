package de.galan.verjson.examples.polymorphism;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.core.Verjson;


/**
 * Demonstrates working with polymorh types. Bean contains the abstract base-class ParentClass, which has to childs:
 * ChildA and ChildB. They are registered in PolymorphVersions, Verjson then knows how to serialize them and adds a
 * "$type" field with the specified typeName.
 *
 * @author daniel
 */
public class PolymorphismTest extends AbstractTestParent {

	@Test
	public void polymorphFields() throws Exception {
		// construct test bean
		Bean bean = new Bean();
		ChildB childB = new ChildB();
		childB.valueFromParent = "parentB";
		childB.valueFromB = "valueB";
		bean.parent = childB; // set ChildB object to parent
		ChildA childA = new ChildA();
		childA.valueFromParent = "parentA";
		childA.valueFromA = "valueA";
		bean.parents = Lists.newArrayList(childA, childB); // add ChildA and ChildB objects to collection

		// serialize and deserialize bean
		Verjson<Bean> verjson = Verjson.create(Bean.class, new PolymorphismVersions());
		String written = verjson.write(bean);
		Bean read = verjson.read(written);

		// assertions
		assertThat(read).isNotNull();
		// field
		assertThat(read.parent).isInstanceOf(ChildB.class);
		assertThat(read.parent.valueFromParent).isEqualTo("parentB");
		assertThat(((ChildB)read.parent).valueFromB).isEqualTo("valueB");
		// collection
		assertThat(read.parents).hasSize(2);
		assertThat(read.parents.get(0)).isInstanceOf(ChildA.class);
		assertThat(read.parents.get(0).valueFromParent).isEqualTo("parentA");
		assertThat(((ChildA)read.parents.get(0)).valueFromA).isEqualTo("valueA");
		assertThat(read.parents.get(1)).isInstanceOf(ChildB.class);
		assertThat(read.parents.get(1).valueFromParent).isEqualTo("parentB");
		assertThat(((ChildB)read.parents.get(1)).valueFromB).isEqualTo("valueB");
	}

}
