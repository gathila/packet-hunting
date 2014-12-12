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


public class Filter {
    
    public void filtertype(String s){
		try {
			if(s.equals("tcp"))
				interface2.captor.setFilter("ip and tcp", true);
			if(s.equals("udp"))
				interface2.captor.setFilter("ip and udp", true);
			if(s.equals("http"))
				interface2.captor.setFilter("port 80", true);
			if(s.equals("pop"))
				interface2.captor.setFilter("port 110 or port 995", true);
			if(s.equals("ftpdata"))
				interface2.captor.setFilter("port 20", true);
			if(s.equals("ftpcontrol"))
				interface2.captor.setFilter("port 21", true);
			if(s.equals("ssh"))
				interface2.captor.setFilter("port 22", true);
			if(s.equals("telnet"))
				interface2.captor.setFilter("port 23", true);
			if(s.equals("lhp"))  //login host protocol
				interface2.captor.setFilter("port 29", true);
			if(s.equals("dns"))
				interface2.captor.setFilter("port 53", true);
			if(s.equals("mail"))
				interface2.captor.setFilter("port 25", true);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    
}
