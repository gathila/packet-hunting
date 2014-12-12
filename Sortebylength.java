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
import java.util.List;

public class Sortebylength implements Comparator<save_in_mem> {
    
    @Override
		public int compare(save_in_mem arg0, save_in_mem arg1) {
			if(arg0.getlength() > arg1.getlength())
				return 1 ;
			else if(arg0.getlength() < arg1.getlength())	
				return -1 ;
			else 
				return 0;
		}	
    
}
