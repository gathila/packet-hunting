/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import jpcap.*;
import jpcap.packet.*;
import jpcap.NetworkInterface;
import java.util.Scanner;


public class mainclass {
    
    public static void main(String [] args){
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int choise;
		
		System.out.println("");
		System.out.println("**************************************************************************");
		System.out.println("			CO-227  EASY PACKET CAPTURE");
		System.out.println("**************************************************************************");
		System.out.println("");
		
		
		System.out.println("Available interfaces: ");
		for(int i=0; i<devices.length; i++){
			System.out.println(i +": " + devices[i].name + ": "+ devices[i].description + ": "+devices[i].datalink_name );
			System.out.println();
										
		}
		System.out.println("__________________________________________________________________________");
		
		Scanner sv = new Scanner(System.in);
		System.out.print("Enter the interface that you want to capture packets: ");
		choise = sv.nextInt();
		
		interface2 cap1 = new interface2();
		cap1.capture(choise, devices);
	}
    
}
