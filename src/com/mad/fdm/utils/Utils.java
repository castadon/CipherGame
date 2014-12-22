package com.mad.fdm.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Utils {

	/*
	 * It returns an Array with all the possible operations that can be done between two numbers 
	 */
	public static ArrayList<Integer> possibleOperationsBetweenTwoNumbers(Integer a, Integer b){
		ArrayList<Integer> rta = new ArrayList<Integer>();
		rta.add(a+b);
		if (a-b!=0) rta.add((a>b)? a-b : b-a);	
		if (a!=1 && b!=1) { 
			rta.add(a*b);
			if (a>b) { if (a%b==0 && a/b>0) { 
				rta.add(a/b); 
			} }
			else /*b>a*/if (b%a==0 && b/a>0) { 
				rta.add(b/a);
			}
		}
		return rta;
	}
	
	/*
	 * It returns a String object that represents the operation made
	 */
	public static String operationToString(Integer a, Integer b, Integer obj){
		if (a+b == obj) return a+"+"+b+" = "+obj;
		if (a-b == obj) return a+"-"+b+" = "+obj;
		if (b-a == obj) return b+"-"+a+" = "+obj;
		if (a*b == obj) return a+"*"+b+" = "+obj;
		if (a/b == obj) return a+"/"+b+" = "+obj;
		return b+"/"+a+" = "+obj;
	}
	
	/*
	 * It returns a String object that represents the concatenation of steps done to achieve the solution
	 */
	public static StringBuffer Print_Followed_Steps(ArrayList<Stack<String>> steps) {
		
		StringBuffer rta = new StringBuffer();
		for (int i=0; i < steps.size(); i++) {
			if (!steps.get(i).empty()) rta.append(steps.get(i).pop()+"\n");
		}
		if (rta.length()==0) rta = new StringBuffer("It's one of the original values");
		return rta;
	}
	
	/*
	 * It returns an Array with all the possible combinations between the elements of two different Arrays 
	 */
	public static ArrayList<ArrayList<Integer>> CartesianProduct(ArrayList<Integer> combs, ArrayList<Integer> remaining) {

		ArrayList<ArrayList<Integer>> rta = new ArrayList<ArrayList<Integer>>();

		for (Iterator<Integer> iterCombs = combs.iterator(); iterCombs.hasNext();) {
			
			ArrayList<Integer> candidate = new ArrayList<Integer>();
			Integer in = iterCombs.next();
			candidate.add(in);
			for (Iterator<Integer> iterRemaining = remaining.iterator(); iterRemaining.hasNext();)
			{ 
				Integer inR = iterRemaining.next();
				candidate.add(inR);
			}
			rta.add(candidate);
		}
		
		return rta;
	}
}
