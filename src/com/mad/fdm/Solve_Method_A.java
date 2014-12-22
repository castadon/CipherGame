package com.mad.fdm;

import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

import com.mad.fdm.utils.Combo;
import com.mad.fdm.utils.Utils;

public class Solve_Method_A {

	static int first_alternative;
	static int second_alternative;
	
	
	public static void solve(int objetive, int[] numbers){
		
		if (!Has_Solution(objetive, numbers)) {//We can't get the desired number so we must try to reach the closer ones
			 JOptionPane.showMessageDialog(null,
   				"We can't get the desired number so we must try to reach the closer ones", "FDM message",
   	            JOptionPane.INFORMATION_MESSAGE);
			first_alternative = objetive+1;
			second_alternative = objetive-1;
			while (!Has_Solution(first_alternative, numbers) && !Has_Solution(second_alternative, numbers) )
			{
				first_alternative++; second_alternative--;
			}
		}
	}//solve
	
	static boolean Has_Solution(int objective, int[] numbers){
		/*
		 * Follows this approach: (3 5 6 8 9 25)--->{[(8 6 8 9 25),"(3+5)"], [(2 6 8 9 25),"(5-3)"], [(15 6 8 9 25),"(3*5)"],...}
		 * We reduce the numbers combining them with all possible operations, rolling back if we find a dead end.
		 * With each iteration we reduce in one the number of ciphers to combine.
		 * Finally we will return true when one combination returns the objective number:  
		 * if it were 107 then when [(107 25),"(3+5=15)(15*6=90)(90+8=98)(98+9=107)"] it'd stop,
		 * or return false when there were no more possible combinations I could apply.
		 */
		
		ArrayList<Integer> original_pool_of_numbers = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++) {
			original_pool_of_numbers.add(new Integer(numbers[i]));
		}
		
		Stack<ArrayList<Integer>> stack_of_pools = new Stack<ArrayList<Integer>>();
		stack_of_pools.push(original_pool_of_numbers);
		
		ArrayList<Stack<String>> stack_of_steps = new ArrayList<Stack<String>>();
		for (int i = 0; i < numbers.length-1; i++) {
			stack_of_steps.add(new Stack<String>());
		}
		
		while (!stack_of_pools.empty()) {
			
			ArrayList<Integer> current_pool = stack_of_pools.pop();

			if (current_pool.contains(new Integer(objective))) {
				JOptionPane.showMessageDialog(null,
		   				"We can get the number " + objective +" without using all numbers", "FDM message",
		   	            JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null,
		   				"Solved with the following steps: \n\n"+Utils.Print_Followed_Steps(stack_of_steps));
				return true; }
			
			else if (current_pool.size()>2) {
				
				if (!stack_of_steps.get(original_pool_of_numbers.size()-current_pool.size()).empty()) 
					Erase(original_pool_of_numbers.size()-current_pool.size(), stack_of_steps);
				/*
				 * Combo is an auxiliary class that generates all possible combinations given a range and a group size.
				 * "Combinations starting from ${first_arg} to ${second_arg} in groups of ${third_arg} elements"
				 * For arguments (0,2,2) it'd return (0,1)(1,2)(0,2) 
				 */
				Combo cc = new Combo(0, current_pool.size()-1, 2);
				while (cc.hasMoreElements()) {
					int[] c = (int[])cc.nextElement();
					
					Integer a = current_pool.get(c[0]); Integer b = current_pool.get(c[1]);
					ArrayList<Integer> combs = Utils.possibleOperationsBetweenTwoNumbers(a, b);
					
					ArrayList<Integer> remaining = new ArrayList<Integer>();
					for (int i = 0; i < current_pool.size(); i++) {
						if (c[0]!=i && c[1]!=i) { remaining.add( current_pool.get(i) ); }
					}
					
					ArrayList<ArrayList<Integer>> cartesianProduct = Utils.CartesianProduct(combs, remaining);
					
					for (int i = 0; i < cartesianProduct.size(); i++) {
						stack_of_pools.push(cartesianProduct.get(i));
						stack_of_steps.get(original_pool_of_numbers.size()-current_pool.size())
							.push(Utils.operationToString(a, b, combs.get(i)));
					}
				}//while
			}
			else {
				Integer a = current_pool.get(0); Integer b = current_pool.get(1);
				ArrayList<Integer> combs = Utils.possibleOperationsBetweenTwoNumbers(a, b);
				if (combs.contains(new Integer(objective))) { 
					JOptionPane.showMessageDialog(null,
			   				"We can get the number " + objective +", but we need all the numbers provided", "FDM message",
			   	            JOptionPane.INFORMATION_MESSAGE);
					stack_of_steps.get(original_pool_of_numbers.size()-current_pool.size())
						.push(Utils.operationToString(a, b, objective));
					JOptionPane.showMessageDialog(null,
			   				"Solved with the following steps: \n\n"+Utils.Print_Followed_Steps(stack_of_steps));
					return true;}
				else if (!stack_of_steps.get(original_pool_of_numbers.size()-current_pool.size()-1).empty()) 
					Erase(original_pool_of_numbers.size()-current_pool.size()-1, stack_of_steps);
			}
		}
		return false;
	}
	
	/*
	* Auxiliary Method of Has_Solution: cleans the steps followed stack, deleting the ones that don't lead to a solution
 	*/
	private static void Erase(int i, ArrayList<Stack<String>> stack_of_steps) {
		if (i>=0){
		Stack<String> stack = stack_of_steps.get(i);
		stack.pop();
		if (stack.empty()) Erase(i-1, stack_of_steps);}
	}
	
}	
