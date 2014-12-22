package com.mad.fdm;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FDM extends JFrame {
    
    protected static final double margin_top_limit = 1000;
	protected static final double margin_below_limit = 100;
	protected static final double margin_nums = 10;
	protected static final int[] special_number_possiblities = {10,25,50,75,100};
	private static final int first_nums_amount = 5;

	public FDM() {
        
        setTitle("FDM Coding Example");
               
        Container container = getContentPane();
        container.setLayout( new GridLayout(3,0) );
        /* Three row layout: one for the objective number, one for the numbers,
         * and one for the "Solve" and "Generate numbers" Buttons.
         */
        
        JPanel objective_panel = new JPanel();
        JLabel label = new JLabel("Objective :");
        objective_panel.add(label);
        
        final JFormattedTextField objective_tf = new JFormattedTextField(NumberFormat.getIntegerInstance());
        objective_tf.setColumns(3);
        objective_panel.add(objective_tf);
        
        Action generate_random_num_Action = new AbstractAction("Generate Random Objective") {
		      public void actionPerformed(ActionEvent evt) {
		    	  double generated_objective = 0;
		    	  while (generated_objective < margin_below_limit)
		    		  { generated_objective = Math.floor(Math.random()*margin_top_limit);
		    		  // We need a number between the top_limit and the below limit
		    		  }
		    	  objective_tf.setText( new Integer( (int)generated_objective ).toString() );
		      }
		 };//This is the action associated to the button. It generates a random number an places it into objective_tf
        JButton generate_random_num_button = new JButton(generate_random_num_Action);
        objective_panel.add(generate_random_num_button);
        container.add(objective_panel);
        
        JPanel numbers_panel = new JPanel();
        numbers_panel.setLayout( new FlowLayout() );
        JLabel nums = new JLabel("Numbers :");
        numbers_panel.add(nums);
        
        final ArrayList<JFormattedTextField> first_nums = new ArrayList<JFormattedTextField>();
        for (int i=0; i<first_nums_amount; i++){
        	JFormattedTextField jftf = new JFormattedTextField(NumberFormat.getIntegerInstance());
        	jftf.setColumns(2);
        	numbers_panel.add(jftf);
        	first_nums.add(jftf);
        }
        JLabel separator = new JLabel(" | ");
        numbers_panel.add(separator);
        final JFormattedTextField num_special_tf = new JFormattedTextField(NumberFormat.getIntegerInstance());
        num_special_tf.setColumns(2);
        numbers_panel.add(num_special_tf);
        container.add(numbers_panel);
        
        
        JPanel buttons_panel = new JPanel();
        Action gen_6_nums_Action = new AbstractAction("Generate random numbers") {
		      public void actionPerformed(ActionEvent evt) {  
		    	  for (int i=0; i<first_nums.size(); i++){
		    		  first_nums.get(i).setText( new Integer( (int)Math.ceil(Math.random()*margin_nums)).toString() );
		    	  }
		    	  //The special number has to be one random element of the array "special_number_possiblities"
		    	  int special_number = special_number_possiblities[(int)Math.floor(Math.random()*special_number_possiblities.length)];
		    	  num_special_tf.setText( new Integer(special_number).toString() );
		      }
		 };//This is the action associated to the button. It generates random numbers an places them into the text_fields
        JButton gen_6_nums_button = new JButton(gen_6_nums_Action);
        buttons_panel.add(gen_6_nums_button);
        
        Action solve_Action = new AbstractAction("Solve") {
		      public void actionPerformed(ActionEvent evt) {  
		    	  //First we validate that the data is valid
		    	  if (!valid_objective(objective_tf.getText()))
		    		  JOptionPane.showMessageDialog(null,
		    				  "The objective number is not valid.\nPlease enter a number bigger than "
		    				  + (int)margin_below_limit + " and less than" + (int)margin_top_limit + ".",
		    				  "Error", JOptionPane.ERROR_MESSAGE);
		    	  else if (!valid_all_five_numbers(first_nums))
		    		  JOptionPane.showMessageDialog(null,
		    				  "The first five numbers must be greater than 0 and less than " +
		    				  (int)margin_nums + ".\nPlease enter valid numbers.",
		    				  "Error", JOptionPane.ERROR_MESSAGE);
		    	  else if (!valid_special_number(num_special_tf.getText())) {
		    		  StringBuffer sb = new StringBuffer();
		    		  for (int i = 0; i < special_number_possiblities.length; i++) {
		    			 sb.append(special_number_possiblities[i]+" ");
		    		  }
		    		  JOptionPane.showMessageDialog(null,
		    				  "The selected number must be one of these: \n" + sb,
		    				  "Error", JOptionPane.ERROR_MESSAGE);
		    	  }
		    	  else {//everything is fine, then we call the solve method we choose
		    		  int[] numbers = new int[first_nums.size()+1];
		    		  for (int i = 0; i < first_nums.size(); i++)
		    			  numbers[i] = (int)new Integer(first_nums.get(i).getText());
		    		  numbers[first_nums.size()] = (int)new Integer(num_special_tf.getText());
		    		  Solve_Method_A.solve((int)new Integer(objective_tf.getText()), numbers);
		    	  }
		      }
		 };//This is the action associated to the button. It checks the input data and if it is correct calls "solve" method
		 JButton solve_button = new JButton(solve_Action);
		 buttons_panel.add(solve_button);
		 container.add(buttons_panel);
	        
		 this.setSize(400, 200);
		 setVisible(true);
    }

	protected boolean valid_objective(String v) {
		// This will validate that the number is between low_margin and top_margin
		if (v==null || v.equals("")) return false;
		int value = new Integer(v.replace(".", ""));
		if (margin_below_limit<value &&  value<margin_top_limit) return true;
		else return false;
	}
	
	protected boolean valid_all_five_numbers(ArrayList<JFormattedTextField> first_nums) {
		// This will validate that the number is between 0 and margin_nums
		for (int i=0; i<first_nums.size(); i++) {
			int value = new Integer(first_nums.get(i).getText().replace(".", "")).intValue();
			if (value<=0 || value>margin_nums) return false;
		}
		return true;
	}
	
	protected boolean valid_special_number(String text) {
		// This will validate that the number is one of the numbers that the array special_number_possiblities contains
		if (text==null || text.equals("")) return false;
		int value = new Integer(text.replace(".", "")).intValue();
		for (int i = 0; i < special_number_possiblities.length; i++) {
			if (value==special_number_possiblities[i]) return true;
		}
		return false;
	}

	public static void main(String[] args) {
    	FDM fdm = new FDM();
    	fdm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	fdm.setVisible(true);
    }
}