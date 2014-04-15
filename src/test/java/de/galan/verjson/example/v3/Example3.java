package de.galan.verjson.example.v3;

import static de.galan.commons.time.DateDsl.*;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;


/**
 * Third version of class hierarchy
 * 
 * @author daniel
 */
public class Example3 {

	public Long segundo;
	public Boolean third;
	public Date forth;
	public String fifth;
	public List<Example3Element> sixth;
	public Example3Sub subA;
	public Example3Sub subB;
	public String filled; // renamed "empty"


	public static Example3 createSampleV3() {
		Example3 result = new Example3();
		result.segundo = 44L;
		result.third = true;
		result.forth = date("2014-02-20 14:34:18");
		result.fifth = "ghi";

		Example3Element element = new Example3Element();
		element.uno = 666L;
		element.three = "Three";
		result.sixth = Lists.newArrayList(element);

		Example3SubA subA = new Example3SubA();
		subA.aaa = "AAA3";
		subA.parent = "Aparent";
		result.subA = subA;
		Example3SubB subB = new Example3SubB();
		subB.bbb = "BBB3";
		subB.parent = "Bparent";
		result.subB = subB;
		return result;
	}


	public static Example3 createSampleV2() {
		Example3 result = new Example3();
		result.segundo = 44L;
		result.third = true;
		result.forth = date("2014-02-20 09:22:53");
		result.fifth = "def";

		Example3Element element = new Example3Element();
		element.uno = 666L;
		element.three = "Three";
		result.sixth = Lists.newArrayList(element);

		Example3SubA subA = new Example3SubA();
		subA.aaa = "AAA";
		subA.parent = "Aparent";
		result.subA = subA;
		Example3SubB subB = new Example3SubB();
		subB.bbb = "CCC";
		subB.parent = "Bparent";
		result.subB = subB;
		return result;
	}


	public static Example3 createSampleV1() {
		Example3 result = new Example3();
		result.segundo = 222L;
		result.third = true;
		result.forth = date("2014-02-19 17:29:12");
		result.fifth = "abc";
		Example3Element element = new Example3Element();
		element.uno = 11L;
		result.sixth = Lists.newArrayList(element);
		Example3SubA subA = new Example3SubA();
		subA.aaa = "AAA";
		subA.parent = "Aparent";
		result.subA = subA;
		Example3SubB subB = new Example3SubB();
		subB.bbb = "BBB";
		subB.parent = "Bparent";
		result.subB = subB;
		return result;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fifth == null) ? 0 : fifth.hashCode());
		result = prime * result + ((filled == null) ? 0 : filled.hashCode());
		result = prime * result + ((forth == null) ? 0 : forth.hashCode());
		result = prime * result + ((segundo == null) ? 0 : segundo.hashCode());
		result = prime * result + ((sixth == null) ? 0 : sixth.hashCode());
		result = prime * result + ((subA == null) ? 0 : subA.hashCode());
		result = prime * result + ((subB == null) ? 0 : subB.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
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
		Example3 other = (Example3)obj;
		if (fifth == null) {
			if (other.fifth != null) {
				return false;
			}
		}
		else if (!fifth.equals(other.fifth)) {
			return false;
		}
		if (filled == null) {
			if (other.filled != null) {
				return false;
			}
		}
		else if (!filled.equals(other.filled)) {
			return false;
		}
		if (forth == null) {
			if (other.forth != null) {
				return false;
			}
		}
		else if (!forth.equals(other.forth)) {
			return false;
		}
		if (segundo == null) {
			if (other.segundo != null) {
				return false;
			}
		}
		else if (!segundo.equals(other.segundo)) {
			return false;
		}
		if (sixth == null) {
			if (other.sixth != null) {
				return false;
			}
		}
		else if (!sixth.equals(other.sixth)) {
			return false;
		}
		if (subA == null) {
			if (other.subA != null) {
				return false;
			}
		}
		else if (!subA.equals(other.subA)) {
			return false;
		}
		if (subB == null) {
			if (other.subB != null) {
				return false;
			}
		}
		else if (!subB.equals(other.subB)) {
			return false;
		}
		if (third == null) {
			if (other.third != null) {
				return false;
			}
		}
		else if (!third.equals(other.third)) {
			return false;
		}
		return true;
	}

}
