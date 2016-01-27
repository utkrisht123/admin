/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

public class ConcurrentUserTracker implements HttpSessionListener {
  private static int users = 0;

  public void sessionCreated(HttpSessionEvent e) {
    users++;
    HttpSession s=e.getSession();
    String user=(String)s.getAttribute("User");
    //System.out.println("Session created "+user+" "+users);
  }
  public void sessionDestroyed(HttpSessionEvent e) {
	  
    users--;
    //System.out.println("Session removed "+users);
    //logPresent(user);
  }
  public static int getConcurrentUsers() {
    return users;
  }
  private Connection getConnection(){
	  Connection con= null;
	  try{
		 
		  con=new MyConnection().getConnection();
	  	} catch(Exception e){
		  //System.out.println(e);
		  e.printStackTrace();
	  	}
	  return con;
  }
  private void closeConnection(Connection con){
	  try{
		  if(con != null){
			con.close();
		  }
	  }catch(SQLException sqe){
		  System.out.println(sqe);
		  sqe.printStackTrace();
	  }
  }
  private boolean logPresent(String user){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  String sql = "update lms_main_salesperson set PRESENT=? WHERE USERNAME= ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, "no");
				ps.setString(2, user);
				count=ps.executeUpdate();
				
			
	  }
	  catch(SQLException sqe){
		//System.out.println(sqe);
		  sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
  }
}