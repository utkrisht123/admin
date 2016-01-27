/**
 * @author Administrator Binay Gaur
 *
 */
package com;
import java.sql.*;
public class MyConnection {
Connection c;
 public Connection getConnection()
 {
	 try
	 {
	 	Class.forName("com.mysql.jdbc.Driver");
	 	
		//c= DriverManager.getConnection("jdbc:mysql://localhost/admin_final_teacher","root","");
	 	c= DriverManager.getConnection("jdbc:mysql://168.144.175.32/admin_final_teacher","root","#6CM341Lpmy@");
	 	//c= DriverManager.getConnection("jdbc:mysql://192.168.1.66/admin_final_teacher","root","Utkrisht123");
	 }
	 catch(Exception e)
	 {
		e.printStackTrace(); 
	 }
		return c;
 }
	
}

