package de.galan.verjson.example.v2;

import static de.galan.commons.time.DateDsl.*;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;


/**
 * Second version of class hierarchy
 * 
 * @author daniel
 */
public class Example2 {

	// public String first; // deleted
	public Long segundo; // renamed (second)
	public Boolean third;
	public Date forth;
	public String fifth; // become concated
	public List<Example2Element> sixth; // became list
	public Example2Sub subA;
	public Example2Sub subB;
	public String empty;


	public static Example2 createSample() {
		Example2 result = new Example2();
		result.segundo = 44L;
		result.third = true;
		result.forth = date("2014-02-20 09:22:53");
		result.fifth = "def";

		Example2Element element = new Example2Element();
		element.uno = 666L;
		element.three = "Three";
		result.sixth = Lists.newArrayList(element);

		Example2SubA subA = new Example2SubA();
		subA.aaa = "AAA";
		subA.parent = "Aparent";
		result.subA = subA;
		Example2SubB subB = new Example2SubB();
		subB.ccc = "CCC";
		subB.parent = "Bparent";
		result.subB = subB;
		return result;
	}


	public static Example2 createSampleV1() {
		Example2 result = new Example2();
		result.segundo = 222L;
		result.third = true;
		result.forth = date("2014-02-19 17:29:12");
		result.fifth = "abc";
		Example2Element element = new Example2Element();
		element.uno = 11L;
		result.sixth = Lists.newArrayList(element);
		Example2SubA subA = new Example2SubA();
		subA.aaa = "AAA";
		subA.parent = "Aparent";
		result.subA = subA;
		Example2SubB subB = new Example2SubB();
		subB.ccc = "BBB";
		subB.parent = "Bparent";
		result.subB = subB;
		return result;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empty == null) ? 0 : empty.hashCode());
		result = prime * result + ((fifth == null) ? 0 : fifth.hashCode());
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
		Example2 other = (Example2)obj;
		if (empty == null) {
			if (other.empty != null) {
				return false;
			}
		}
		else if (!empty.equals(other.empty)) {
			return false;
		}
		if (fifth == null) {
			if (other.fifth != null) {
				return false;
			}
		}
		else if (!fifth.equals(other.fifth)) {
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
