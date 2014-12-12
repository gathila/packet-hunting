/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import jpcap.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import jpcap.packet.Packet;


public class interface2 {
    
    public static JpcapCaptor captor;
	public String file = "test.java"; 
	
	public static List<save_in_mem> pacsave = new ArrayList<save_in_mem>();
	
	public void capture(int choise, NetworkInterface [] devices){
		System.out.println("");
		
		

		try{
			captor=JpcapCaptor.openDevice(devices[choise], 65535, false, 20);
			 
			
			JpcapWriter writer = JpcapWriter.openDumpFile(captor,file);
			
                        System.out.println("____________________________________________________________________________________");
			System.out.println("Press '1' to save packets directly to the database:");
			System.out.println("Press '2' to save packets direstly to the arraylist:");
                        System.out.println();
			System.out.println("You will be facilitated if you need to save packets to a database:");
			
			Scanner var = new Scanner(System.in);
			Scanner var2 = new Scanner(System.in);
			int cond = var.nextInt();
			
			if(cond == 1){
                                System.out.println("____________________________________________________________________________________");
				System.out.println("");
                                System.out.println("Saving Packets in a Database.");
                                
                                System.out.println("____________________________________________________________________________________");
                                save_in_database save1 = new save_in_database();
				
				System.out.println("Enter '1' to save packets in new database:");
				System.out.println("Enter '2' to save packets in an existing database:");
				int savedb = var2.nextInt();
				
				if(savedb == 1)	{
                                    save1.saveTonewDB();
                                    System.out.println("____________________________________________________________________________________");
                                    System.out.println("");
                                    System.out.println("Saving Packets to a new database.");
                                    System.out.println("____________________________________________________________________________________");
                                }
				if(savedb == 2) {
                                    save1.saveTogivenDB();
                                    System.out.println("____________________________________________________________________________________");
                                    System.out.println("");
                                    System.out.println("Saving Packets to an existing database.");
                                    System.out.println("____________________________________________________________________________________");
                                }
				
				System.out.print("Enter the time in seconds to capture packets: ");
				
                                
                                int minutes = var.nextInt();
                                
                                System.out.println("____________________________________________________________________________________");
				System.out.println("");
				
				int pacNo = 0;
				int length;
				String si ;
				String di ;
				String type;
				int srcport;
				int dstport;
				
				for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(minutes);stop>System.nanoTime();) {
					Packet packet = captor.getPacket();
					save_in_mem obj = new save_in_mem(packet);
					si = obj.getsrcstring();
					di = obj.getdeststring();
					length = obj.getlength();
					pacNo = save_in_mem.allpac + save_in_database.latestPacketNo;
					type = obj.pactype;
					srcport = obj.getSPort();
					dstport = obj.getdstport();
					save_in_database.save_to_table(pacNo, si, di, length, type, srcport, dstport);
				}
				
				System.out.println("Packets are successfully saved to the database\n");
				System.out.println(save_in_mem.allpac+" packets saved in database");
				System.out.println("____________________________________________________________________________________");
				System.out.println("");
                                
				save_in_database.queringdb();
				System.exit(0);
			}
			
			else{
				int counter = 0;
			
				System.out.println("");
				System.out.print("Enter the time in secons to capture packets:");
			
				Scanner z = new Scanner(System.in);
				int number = z.nextInt();
			
				save_in_mem.setnum(number);
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				for(long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(number);stop>System.nanoTime();){
					Packet packet = captor.getPacket();
		
					if(packet != null){
						 pacsave.add( new save_in_mem(packet));
						 System.out.printf("%d     ", (counter+1));
						 pacsave.get(counter).display();
						 counter++;
						 writer.writePacket(packet);
					}
				} 
			
				System.out.println("__________________________________________________________________________");
				System.out.println("Successfully captured Total number of packets:	 "+(counter));// TCP packets were captured Successfully... ");
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
				System.out.println("");
			}
			captor.close();

			
				
		}
		catch(IOException ioe) { 
			ioe.printStackTrace(); 
		}	
		
		//Collections.sort(pacsave, new SortbysourceIP());
		
		
		while(true){
		System.out.println("__________________________________________________________________________");
		System.out.println("");
		System.out.println("				 PACKET QUERING");
		System.out.println("				 --------------");
		
		System.out.println("	1:	Sort By length");
		System.out.println("	2:	Source IP address");
		System.out.println("	3:	Destination IP address");
		System.out.println("	4: 	Source Port");
		System.out.println("	5:	Destination Port");
		System.out.println("	6:	TCP Packets");
		System.out.println("	7:	UDP Packets");
                System.out.println("	8:	ARP Packets");
		System.out.println("	9:	ICMP Packets");
                System.out.println("	10:	IP Packets");
		System.out.println("	11:	Capture Packets again");
		System.out.println("	12:	Quit");
		System.out.println("");
		System.out.print("Enter the query type number you want:");
		
		
		Scanner x = new Scanner(System.in);
		Scanner y = new Scanner(System.in);
			int decision = x.nextInt();
			
			//System.out.println("");
			
			if(decision == 4){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets using the source port");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				System.out.print("Enter the source Port No:");
				int port = x.nextInt();
				
				save_in_mem.setze();
				save_in_mem.setzero();
                                
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				
				for(int i=0; i<save_in_mem.numOfPks; i++){

					if(port==(pacsave.get(i).getSPort())){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
                                        if((pacsave.get(i).pactype)=="ARP") save_in_mem.arpno++;
                                        if((pacsave.get(i).pactype)=="IP") save_in_mem.ipno++;
                                        if((pacsave.get(i).pactype)=="ICMP") save_in_mem.icmp++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
			
			if(decision == 2){
			System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets using the source IP address");
				System.out.println();
				System.out.print("	Enter the source IP address:");
				String ip = y.nextLine();
				
				
				save_in_mem.setzero();
				save_in_mem.setze();
                                
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
		
				for(int i=0 ; i<save_in_mem.numOfPks ;i++){
					if(ip.equals(pacsave.get(i).getsrcstring())){
						System.out.printf("%d.       " , save_in_mem.show());
						pacsave.get(i).display();
						if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
						if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
						save_in_mem.increse();
						}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				//System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno);
			}
			
			if(decision == 3){
			System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets using the destination IP address");
				System.out.println();
				System.out.print("	Enter the destination IP address:");
				String ip = y.nextLine();
				
				save_in_mem.setzero();
				save_in_mem.setze();
                                
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
                                
                                //System.out.println("");
                                
                               // System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                //System.out.println("______________________________________________________________________________________________________________________");
			
				
				for(int i=0 ; i<save_in_mem.numOfPks ;i++){
					if(ip.equals(pacsave.get(i).getdeststring())){
						System.out.printf("%d.       ",save_in_mem.show());
						pacsave.get(i).display();
						save_in_mem.increse();
						if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
						if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
						}
				}
				System.out.println();
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				//System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno);
			}
			
			if(decision == 1){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets using length");
				System.out.println();
				Collections.sort(pacsave, new Sortebylength());
				
				System.out.println("	1:	Retrieve packets greater than a given size");
				System.out.println("	2:	Retrieve packets lesser than a given size");
				System.out.println("	3:	Retrieve packets of a given size");
				System.out.println("	Press any other key to Return");
				System.out.println("");
				System.out.print("Enter the type number you want:");
				
				int d = x.nextInt();				
				
				int size; //= x.nextInt();
				
				if(d == 1){
					System.out.print("Enter the smallest packet size:");
					size = x.nextInt();
					
					save_in_mem.setzero();//save_in_mem.counter5=0;
					save_in_mem.setze();
                                        
                                        System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
					
					for(int i=0; i<save_in_mem.numOfPks; i++){
						if(pacsave.get(i).getlength() >= size){
							System.out.printf("%d.       ", save_in_mem.show());
							//System.out.println(count);
							pacsave.get(i).display();
							if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
							if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
							save_in_mem.increse();//save_in_mem.count5++;
							}
					}
					System.out.println();
					System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
					System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
				}
                                
				
				if(d == 2){
					System.out.println("__________________________________________________________________________");
					System.out.print("Enter the largest packet size:");
					size = x.nextInt();
					
					save_in_mem.setzero();
					save_in_mem.setze();
                                        
                                        System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
					
					for(int i=0; i<save_in_mem.numOfPks; i++){
						if(pacsave.get(i).getlength() <= size){
							System.out.printf("%d.      ", save_in_mem.show());
							pacsave.get(i).display();
							if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
							if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
							save_in_mem.increse();
						}
					}
					System.out.println();
					System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
					System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
				}
				
				if(d == 3){
					System.out.println("__________________________________________________________________________");
					System.out.print("Enter the packet size:");
					size = x.nextInt();
					
					save_in_mem.setzero();
					save_in_mem.setze();
                                        
                                        System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
					
					
					for(int i=0; i<save_in_mem.numOfPks; i++){
						if(pacsave.get(i).getlength() == size){
							System.out.printf("%d.       ", save_in_mem.show());
							pacsave.get(i).display();
							if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
							if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
							save_in_mem.increse();
						}
					}
					System.out.println();
					System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
					System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
				
			}
                        }
                        
			
			if(decision == 5){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets using the destination port");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				System.out.print("Enter the destination Port No:");
				int port = x.nextInt();
				
				save_in_mem.setzero();
				save_in_mem.setze();
                                
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if(port==(pacsave.get(i).getdstport())){ 
					System.out.printf("%d.      " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			
			
			
			}
			
			if(decision == 11){
				save_in_mem.setzero();
				save_in_mem.setze();
				
				String [] arg = {};
				mainclass.main(arg);
			}
			
			
			if(decision == 12){
				System.out.println("__________________________________________________________________________");
				System.out.print("Quit from Easy packet capture... ");
				break;
			}
			if(decision == 6){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets of the TCP Protocol");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				save_in_mem.setzero();
				save_in_mem.setze();
                                
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if((pacsave.get(i).pactype=="TCP")){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
			
                        
			if(decision == 7){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets of the UDP Protocol");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				save_in_mem.setzero();
				save_in_mem.setze();
				
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if((pacsave.get(i).pactype=="UDP")){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="TCP") save_in_mem.tcpno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
                        
                        if(decision == 8){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets of the ARP Protocol");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				save_in_mem.setzero();
				save_in_mem.setze();
				
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if((pacsave.get(i).pactype=="ARP")){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="ARP") save_in_mem.arpno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
                                        
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
                        
                        if(decision == 9){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets of the ICMP Protocol");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				save_in_mem.setzero();
				save_in_mem.setze();
				
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if((pacsave.get(i).pactype=="ICMP")){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="ICMP") save_in_mem.icmp++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
                        
                        if(decision == 10){
				System.out.println("__________________________________________________________________________");
				System.out.println("Retrieve packets of the IP Protocol");
				System.out.println();
				//System.out.print("	Enter the source port:");
				Collections.sort(pacsave, new Sorting());
				
				save_in_mem.setzero();
				save_in_mem.setze();
				
                                System.out.println("");
                                
                                System.out.println("No\t\tSrc IP\t\tSrc Port\tDest IP\t\t\tDest Port\t\tLength\t\tType");
                                System.out.println("______________________________________________________________________________________________________________________");
			
				for(int i=0; i<save_in_mem.numOfPks; i++){
					if((pacsave.get(i).pactype=="IP")){ 
					System.out.printf("%d.       " , save_in_mem.show());
					pacsave.get(i).display();
					if((pacsave.get(i).pactype)=="IP") save_in_mem.ipno++;
					if((pacsave.get(i).pactype)=="UDP") save_in_mem.udpno++;
					save_in_mem.increse();
					}
				}
				System.out.println();
				System.out.println("Total number of packets to this query is " + (save_in_mem.show()-1));
				System.out.println("Number of TCP Packets	:"+save_in_mem.tcpno+"	Number of UDP packets:	"+save_in_mem.udpno + "     Number of ARP Packets    :"+save_in_mem.arpno + "   Number of ICMP Packets  :" + save_in_mem.icmp + "   Number of IP packets   :" + save_in_mem.ipno);
			}
		}
	}        
    
}

