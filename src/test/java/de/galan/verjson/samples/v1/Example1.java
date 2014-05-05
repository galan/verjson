package de.galan.verjson.samples.v1;

import static de.galan.commons.time.DateDsl.*;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;


/**
 * First version of class hierarchy
 * 
 * @author daniel
 */
public class Example1 {

	public String first;
	public Long second;
	public Boolean third;
	public Date forth;
	public List<String> fifth;
	public Example1Element sixth;
	public Example1Sub subA;
	public Example1Sub subB;
	public String empty;


	public static Example1 createSample() {
		Example1 result = new Example1();
		result.first = "First";
		result.second = 222L;
		result.third = true;
		result.forth = date("2014-02-19 17:29:12");
		result.fifth = Lists.newArrayList("a", "b", "c");
		result.sixth = new Example1Element();
		result.sixth.one = 11L;
		result.sixth.two = "Two";
		Example1SubA subA = new Example1SubA();
		subA.aaa = "AAA";
		subA.parent = "Aparent";
		result.subA = subA;
		Example1SubB subB = new Example1SubB();
		subB.bbb = "BBB";
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
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((forth == null) ? 0 : forth.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		Example1 other = (Example1)obj;
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
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		}
		else if (!first.equals(other.first)) {
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
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		}
		else if (!second.equals(other.second)) {
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
