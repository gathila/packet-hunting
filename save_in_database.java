/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prabhath
 */

import java.sql.*;
import java.util.Scanner;
import java.lang.String	;
import com.mysql.jbdc.*;
//import COM.ibm.db2.jdbc.app.*;

public class save_in_database {
    
    private static final String DB_URL = "jdbc:mysql://localhost/";
	private static String username = "root";
	private static String password = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static String tablename;
	public static int latestPacketNo=0;
	static String length ;
	static String sIP ;
	static String dIP ;
	static String pNO ;
	static String type ;
	static String srcport ;
	static String dstport ;
	
	
	public static void saveTonewDB(){
		
		String sql;
		

			//register the jdbc driver
			try {
				Scanner var = new Scanner(System.in);
				Class.forName("com.mysql.jdbc.Driver");
			
			/*	open a connection to database   */
				System.out.println("Connecting to database ...");
			
				conn = DriverManager.getConnection(DB_URL, username, password);
				System.out.println("Create new database");
				stmt = conn.createStatement();
				
				sql = "create database packetsv";
				stmt.executeUpdate(sql);	
				
				
				conn = DriverManager.getConnection(DB_URL+"packetsv", username, password);
				stmt = conn.createStatement();
				
				//System.out.println("Enter a name to your new table: ");				
				String tabname = "save"; //var.nextLine();
				sql = "create table "+tabname+"("+
						"pacNo INT(10) not NULL, "+
						"sourceIP VARCHAR(20), "+
						"destIP VARCHAR(20), "+
						"length INT(10), "+
						"pac_type VARCHAR(5), "+
						"sourcePort INT(5), "+
						"destPort INT(5), "+
						"primary key(pacNo))";
				stmt.executeUpdate(sql);
				
				
				/* Assign the created table to variable 'tablename' to access the table for other classes */
				tablename = tabname;
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	/*_______________________________________________________________________________________________*/
	
	public static void saveTogivenDB(){
	
		String sql;
		Scanner var = new Scanner(System.in);
		
		
		try {
			/*   register the jdbc driver   */
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("Show databases");
			showdb();
			
			System.out.print("Enter the database name: ");
			String dbname = var.nextLine();
			
			/*	open a connection to database   */
			System.out.println("Connecting to database ...");		
			conn = DriverManager.getConnection(DB_URL+dbname, username, password);
			stmt = conn.createStatement();

			
			ResultSet rs = conn.getMetaData().getTables(null,null,"%",null);
			while(rs.next())
				tablename = rs.getString(3);
			
			getTablenames();
			
			sql = "select MAX("+pNO+") from "+tablename;
			PreparedStatement statement = conn.prepareStatement(sql);
			
			System.out.println(sql);

			ResultSet rs2 = statement.executeQuery();

			while(rs2.next()){
				latestPacketNo = Integer.parseInt(rs2.getString(1));
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/***************************************************************************************************************/
	
	public static void showdb(){
		
		ResultSet rs;
		try {
			conn = DriverManager.getConnection(DB_URL, username, password);
			rs = conn.getMetaData().getCatalogs();
			while (rs.next()) {
			    System.out.println(rs.getString("TABLE_CAT") );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	// save data to a table
	/**************************************************************************************************************/
	
	public static void save_to_table(int pacNo, String si, String di, int length, String type, int srcp, int dstp){
		
		String insertsql = "insert into "+tablename+" values(?,?,?,?,?,?,?)"; 
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement(insertsql);
			statement.setInt(1, pacNo);
			statement.setString(2, si);
			statement.setString(3, di);
			statement.setInt(4, length);
			statement.setString(5, type);
			statement.setInt(6, srcp);
			statement.setInt(7, dstp);


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*******************************************************************************************************/
	//  Querying the database.
	
	public static void queringdb(){
		
		System.out.println("1:   Retrieve packets using length ");
		System.out.println("2:   Using source IP address");
		System.out.println("3:   Using destination IP address");
		System.out.println("4:   Source Port");
		System.out.println("5:	 Destination Port");
		System.out.println("6:	 TCP Packets");
		System.out.println("7:	 UDP Packets");
		System.out.println("8:	 Capture Packets again");
		System.out.println("9:	 Quit");
		System.out.println("");
		System.out.print("Enter the query type number you want:");
		
		
		Scanner sv = new Scanner(System.in);
		Scanner sv2 = new Scanner(System.in);
		
		int decision ;
		String sql;
		
		/* Implemented queries for your decision are bottom  */
		
		while(true){
			decision = sv.nextInt();
			try {
				ResultSet rs = stmt.executeQuery("SELECT * FROM save");
				ResultSetMetaData rsmd = rs.getMetaData();
				String length = rsmd.getColumnName(4);
				String sIP = rsmd.getColumnName(2);
				String dIP = rsmd.getColumnName(3);
				String pNO = rsmd.getColumnName(1);
				String type = rsmd.getColumnName(5);
				String srcport = rsmd.getColumnName(6);
				String dstport = rsmd.getColumnName(7);
				
		
			if(decision == 1){
				
				System.out.println("	1:	Retrieve packets greater than a given size");
				System.out.println("	2:	Retrieve packets lesser than a given size");
				System.out.println("	3:	Retrieve packets of a given size");
				System.out.println("	Press any other key to Return");
				System.out.println("");
				System.out.print("Enter the type number you want:");
				
				int decision2 = sv2.nextInt();
				
				System.out.println("Enter the size: ");
				int size = sv2.nextInt();
				
				System.out.println("pacNo | SourceIP               | DestinationIP          | length   | type");
				
				if(decision2 == 1){
					ResultSet rs2 = stmt.executeQuery("select * from "+tablename+" where "+length+">"+size);
					
					while(rs2.next()){
						System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
					}
				}
				
				if(decision2 == 2){
					ResultSet rs2 = stmt.executeQuery("select * from "+tablename+" where "+length+"<"+size);
					
					while(rs2.next()){
						System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
					}
				}
				
				if(decision2 == 3){
					ResultSet rs2 = stmt.executeQuery("select * from "+tablename+" where "+length+"="+size);
					
					while(rs2.next()){
						System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
					}
				}
			}
			
			
			
			if(decision == 2){
				
				System.out.println("Retrieve packets using the source IP address");
				System.out.println();
				System.out.print("	Enter the source IP address : ");			
				String sip = sv2.nextLine();
				System.out.println(sip);
				sql = "select * from "+tablename+" where "+sIP+"=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, sip);
				ResultSet rs2 = statement.executeQuery();
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}
			}
			
			
			if(decision == 3){
				
				System.out.println("Retrieve packets using the destination IP address");
				System.out.println();
				System.out.print("	Enter the destination IP address : ");			
				String dip = sv2.nextLine();
				
				sql = "select * from "+tablename+" where "+sIP+"=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, dip);
				ResultSet rs2 = statement.executeQuery();
				
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}
			}
			
			
			if(decision == 4){
				
				System.out.println("Retrieve packets using source port");
				System.out.print("Eneter the port number to get packet: ");
				int port = sv2.nextInt();
				
				sql = "select * from "+tablename+" where "+srcport+"=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, port);
				ResultSet rs2 = statement.executeQuery();
				
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}				
			}
			
			
			if(decision == 5){
				
				System.out.println("Retrieve packets using destination port");
				System.out.print("Eneter the port number to get packet: ");
				int port = sv2.nextInt();
				System.out.println();
				
				sql = "select * from "+tablename+" where "+dstport+"=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, port);
				ResultSet rs2 = statement.executeQuery();
				
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}				
			}
			
			
			if(decision == 6){
				
				System.out.println("Retrieve TCP pakets");
				
				sql = "select * from "+tablename+" where "+type+"=?";			
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, "TCP");
				ResultSet rs2 = statement.executeQuery();
				
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}	
			}
			
			
			
			if(decision == 7){
				
				System.out.println("Retrieve UDP packets");
				
				sql = "select * from "+tablename+" where "+type+"=?";		
				sql = "select * from "+tablename+" where "+type+"=?";			
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, "UDP");
				ResultSet rs2 = statement.executeQuery();				
				while(rs2.next()){
					System.out.println(rs2.getInt(pNO)+"\t"+rs2.getString(sIP)+"\t\t"+rs2.getString(dIP)+"\t\t "+rs2.getInt(length)+"\t"+rs2.getString(type)+"\t"+rs2.getInt(srcport)+"\t"+rs2.getInt(dstport));
				}	
			}
			
			
			
			if(decision == 8){
				String [] args = {};
				mainclass.main(args);
			}
			
			
			if(decision == 9) break;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*********************************************************************************************/
	// This method can be used after the table is created. Then table's column names can be get
	
	public static void getTablenames(){
		
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT * FROM save");
			ResultSetMetaData rsmd = rs.getMetaData();
			length = rsmd.getColumnName(4);
			sIP = rsmd.getColumnName(2);
			dIP = rsmd.getColumnName(3);
			pNO = rsmd.getColumnName(1);
			type = rsmd.getColumnName(5);
			srcport = rsmd.getColumnName(6);
			dstport = rsmd.getColumnName(7);
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	} 
    
}
