/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import java.io.IOException;

import jpcap.*;
import jpcap.packet.Packet;

public class PacketPrinter {
    
      public void readfile(String file){
		  //System.out.println();
		  
			try {
				JpcapCaptor captor1 = JpcapCaptor.openFile(file);
				
				for(int counter=0; counter<10; counter++){
					
					  //read a packet from the opened file
					  Packet packet=captor1.getPacket();
					  
					  //if some error occurred or EOF has reached, break the loop
					 // if(packet==null || packet==Packet.EOF) break;
					  //otherwise, print out the packet
					  String p = packet.toString();
					  String [] parray = p.split("\\s+") ;
					  
			/*		 for(int i=0; i<parray.length; i++)
						 System.out.printf("%s   ", parray[i]); */
					 	 System.out.println();
					}

					captor1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}	
    
}
