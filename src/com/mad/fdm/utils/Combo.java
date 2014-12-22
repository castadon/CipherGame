package com.mad.fdm.utils;

import java.util.Enumeration;

/*
 * Utility class that given a range - hi, lo - and the number of elements generates all the possible combinations 
 */

public class Combo implements Enumeration<Object> {
	 
		private int lo, hi, n;
		private int[] c= null;
		
		private int[] init() {
			
			c= new int[n];
			
			for (int i= 0, j= lo; i < n; c[i++]= j++);
				
			return c;
		}
		
		private int findPivot() {
			
			if (c == null) return 0;
				
			for (int i= n; --i >= 0; )
				if (c[i] <= hi-(n-i)) return i;
				
			return -1;
		}
		
		public Combo(int lo, int hi, int n) {
			this.lo= lo;
			this.hi= hi;
			this.n = n;
		}
		
		public boolean hasMoreElements() { return findPivot() >= 0; }
		
		public Object nextElement() {
			
			if (c == null) return init();
				
			int i= findPivot();
			
			if (i < 0) return null;
				
			for (int j= c[i]; i < n; c[i++]= ++j);
	 
			return c;
		}
	} 
