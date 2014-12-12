/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;import java.util.List;

public class SortbysourceIP implements Comparator<save_in_mem> {
    
    @Override
		public int compare(save_in_mem arg0, save_in_mem arg1) {
		
			String args0 = String.format("%3s.%3s.%3s.%3s", arg0.getsrcadress()[0], arg0.getsrcadress()[1], arg0.getsrcadress()[2],arg0.getsrcadress()[3]);
			String args1 = String.format("%3s.%3s.%3s.%3s", arg1.getsrcadress()[0], arg1.getsrcadress()[1], arg1.getsrcadress()[2], arg1.getsrcadress()[3]);
			
			return args0.compareTo(args1);
		}
    
}
