/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import java.net.InetAddress;
import java.net.Socket;
import java.lang.Object;
import java.lang.Object;

import jpcap.*;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;


public class save_in_mem {
    
    private InetAddress source_ip;
	private InetAddress dest_ip;
	private short protocol;
	private int src_port;
	private int dest_port;
	private int length;
	private byte [] data;
	private byte [] header;
	private long sequnce_no;
	private byte [] dst_address;
	private byte [] src_address;
	public static int counter5 = 1;
	public static int numOfPks;
	public String pactype;
	public static int tcpno=0;
	public static int udpno=0;
        public static int arpno=0;
        public static int ipno=0;
        public static int icmp=0;
	public static int allpac = 0;
	
	TCPPacket pac;
	
	public save_in_mem(Packet packet){
		if(packet instanceof TCPPacket){ 
		TCPPacket pac = (TCPPacket)packet;
		source_ip = pac.src_ip;
		src_address = pac.src_ip.getAddress();
		dest_ip = pac.dst_ip;
		dst_address = pac.dst_ip.getAddress();
		src_port = pac.src_port;
		dest_port = pac.dst_port;
		length = pac.len;
		data = pac.data;
		header = pac.header; 
		pactype = "TCP";
		tcpno++;
		allpac++;
		}
		
		else if(packet instanceof UDPPacket){
		UDPPacket pac = (UDPPacket)packet;
		source_ip = pac.src_ip;
		src_address = pac.src_ip.getAddress();
		dest_ip = pac.dst_ip;
		dst_address = pac.dst_ip.getAddress();
		src_port = pac.src_port;
		dest_port = pac.dst_port;
		length = pac.len;
		data = pac.data;
		header = pac.header;  
		pactype = "UDP";
		udpno++;
		allpac++;
		}
                
                else if(packet instanceof ARPPacket){
		ARPPacket pac = (ARPPacket)packet;
		source_ip =null; //pac.src_ip;
		src_address =null; //pac.src_ip.getAddress();
		dest_ip =null; //pac.dst_ip;
		dst_address =null; // pac.dst_ip.getAddress();
		src_port =0; // pac.src_port;
		dest_port =0; //pac.dst_port;
		length = pac.len;
		data = pac.data;
		header = pac.header;  
		pactype = "ARP";
		arpno++;
		allpac++;
		}
		
                else if(packet instanceof IPPacket){
		IPPacket pac = (IPPacket)packet;
		source_ip = pac.src_ip;
		src_address = pac.src_ip.getAddress();
		dest_ip = pac.dst_ip;
		dst_address = pac.dst_ip.getAddress();
		src_port =0; // pac.src_port;
		dest_port =0; // pac.dst_port;
		length = pac.len;
		data = pac.data;
		header = pac.header;  
		pactype = "IP";
		ipno++;
		allpac++;
		}
                
                else if(packet instanceof ICMPPacket){
		ICMPPacket pac = (ICMPPacket)packet;
		source_ip = pac.src_ip;
		src_address = pac.src_ip.getAddress();
		dest_ip = pac.dst_ip;
		dst_address = pac.dst_ip.getAddress();
		src_port =0; // pac.src_port;
		dest_port =0; // pac.dst_port;
		length = pac.len;
		data = pac.data;
		header = pac.header;  
		pactype = "ICMP";
		icmp++;
		allpac++;
		}
                
                
		else {
			length = 0;
			//source_ip = /255;
			//dest_ip = 255.255.255.255;
			data = packet.data;
			header = packet.header;
			allpac++;
			pactype = "Null";
		}
		
	}
	
	
	public void display(){
		//System.out.println("____________");
		System.out.println("\t"+source_ip+"\t\t"+ src_port+"\t\t" + dest_ip + "\t\t" + dest_port +"\t\t\t"+length+"\t\t"+pactype);
		//System.out.println("Destination:	IP: "+dest_ip+"   	Port: "+dest_port);
		//System.out.println("Packet type:	"+pactype);
		//System.out.println("Length: "+length);
		
		//System.out.println("Destnination port: "+dest_port);
		//System.out.println("sequence_no: "+sequnce_no);
		System.out.println();
		
	}
	
	public int getlength(){
		return this.length;
	}
	
	public InetAddress getsourceIP(){
		return this.source_ip;
	}
	
	public InetAddress getdestIP(){
		return this.dest_ip;
	}
	
	public long getseq_no(){
		return this.sequnce_no;
	}
	
	public byte [] getsrcadress(){
		return src_address;
	}
	
	public byte [] getdstaddress(){
		return dst_address;
	}
	
	public int getdstport(){
		return dest_port;
	}
	
	public byte [] getheder(){
		return header;
	}
	
	public byte [] getdata(){
		return data;
	}
	public static void setzero(){
		counter5=1;
	}
	
	public static void increse(){
		counter5++;
	}
	
	public static int show(){
		return counter5;
	}
	
	public String getsrcstring(){
		return ""+source_ip;
	}
	public String getdeststring(){
		return ""+dest_ip;
	}
	
	public int getSPort(){
		return src_port;
	}
	
	public String getpactype(){
		return pactype;
	}
	
	public static void setnum(int a){
		numOfPks=a;
	}
	
	public static void setze(){
		tcpno=0;
		udpno=0;
                arpno=0;
                icmp=0;
                ipno=0;
	}
    
}
