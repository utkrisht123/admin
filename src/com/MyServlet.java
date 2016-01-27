/**
 * Author Name					Date							Discription
 * ---------------------------------------------------------------------------------------------------------------
 * Binay Gaur			23-Nov-2014						Controller Servlet for Notification
 * ---------------------------------------------------------------------------------------------------------------
 */
package com;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import java.io.*;
/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	String driver,url,user,password;
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				//driver= getServletContext().getInitParameter("driver");
			    //url= getServletContext().getInitParameter("url");
			    //user= getServletContext().getInitParameter("user");
			    //password= getServletContext().getInitParameter("password");
			    String jEventName=request.getParameter("jEventName");
			      //System.out.println(jEventName);
			    HttpSession session=request.getSession();
		    	  String username=(String)session.getAttribute("user");
		    	 // System.out.println(username);
			    if(jEventName!=null && jEventName.equals("LiveDemo_Notification")) // View LiveDemo Notification number
			      {
			    	  int l=liveDemo();
			    	  int c=contact();
			    	  int cb=callBack();
			    	  int n=l+c+cb;
			    	  if(n>0)
			    	  out.println(n);
			      }	
			    else if (jEventName!=null && jEventName.equals("ViewLiveDemo_Notification")) // View Live Demo Notification 
			      {
			    	 ArrayList<String> al=new ArrayList<String>();
			    	 int n=contact();
			    	 int cb=callBack();
			    	 int l=al.size();
			    	 int t=n+cb+l;
			    	 String s3="";
			    	 if(t>0)
			    		 s3=""+t;
			    	 else
			    		 s3="no";
			    	 al=getLiveDemo();
			    	  out.println("<li><p>You have "+s3+" new notifications</p></li>");
			    	  out.println("<li><ul class='dropdown-menu-list scroller' style='height: 250px;'>");
			    	  int h=0,p=0,m=0,r=0;
			    	  for(int i=0;i<al.size();i++)
			    	  {
			    		  String s=al.get(i);
			    		  if(s.equalsIgnoreCase("Hadoop"))
			    		  {
			    			 h++;
			    		  }
			    		  else if(s.equalsIgnoreCase("Python"))
			    		  {
			    			 p++;
			    		  }
			    		  else if(s.equalsIgnoreCase("MongoDB"))
			    		  {
			    			  m++;
			    		  }
			    		  else if(s.equalsIgnoreCase("Business Analytics With R"))
			    		  {
			    			  r++;
			    		  }
			    	  }
			    		  out.println("<li><a href='livedemo.do?jEventName=N_L_DEMO&course=Hadoop'><span class='label label-sm label-icon label-warning'><i class='fa fa-plus'></i></span> Hadoop Request     <span class='time'>"+h+" </span></a></li>");
			    		  out.println("<li><a href='livedemo.do?jEventName=N_L_DEMO&course=Python'><span class='label label-sm label-icon label-info'><i class='fa fa-bolt'></i></span>Python Request     <span class='time'>"+p+" </span></a></li>");
			    		  out.println("<li><a href='livedemo.do?jEventName=N_L_DEMO&course=MongoDB'><span class='label label-sm label-icon label-success'><i class='fa fa-bullhorn'></i></span>MongoDB Request    <span class='time'>"+m+" </span></a></li>");
			    		  out.println("<li><a href='livedemo.do?jEventName=N_L_DEMO&course=Business Analytics With R'><span class='label label-sm label-icon label-success'><i class='fa fa-briefcase'></i></span>R Business Analytics<span class='time'>"+r+" </span></a></li>");
			    		  out.println("<li><a href='View_CallBack.jsp'><span class='label label-sm label-icon label-danger'><i class='fa fa-bell-o'></i></span>Call Back   <span class='time'>"+cb+" </span></a></li>");
			    		  out.println("<li><a href='LoginServlet?jEventName=N_Contact'><span class='label label-sm label-icon label-default'><i class='fa fa-briefcase'></i></span>Contact Request    <span class='time'>"+n+" </span></a></li>");
			    	  
			    	  out.println("</ul></li>");
			    	  out.println("<li class='external'><a href='livedemo.do?jEventName=N_L_DEMO&course=All'>See all live demo notifications <i class='m-icon-swapright'></i></a></li>");
			      }
			    else if(jEventName!=null && jEventName.equals("Task_Notification")) // View Task Notification number
			      {
			    	String date=request.getParameter("date");
			    	  int n=todayTask(username,date);
			    	  if(n>0)
			    	  out.println(n);
			      }	
			    else if (jEventName!=null && jEventName.equals("ViewTask_Notification")) // View Task Notification
			      {
			    		String date=request.getParameter("date");
			    		ArrayList<String> al=new ArrayList<String>();
			    		al=getTodayTask(username,date);
			    	  out.println("<li><p>You have "+al.size()+" today tasks</p></li>");
			    	  out.println("<li><ul class='dropdown-menu-list scroller' style='height: 250px;'>");
			    	  for(int i=0;i<al.size();i++)
			    	  {
			    		  String s=al.get(i);
			    		  String s2[]=s.split("/");
			    		  out.println("<li><a href='#'><span class='task'><span class='desc'>"+s2[0]+"</span><span class='percent'>"+s2[1]+"</span></span></a></li>");
			    	  }
			    		 
			    		 
			    	  out.println("</ul></li>");
			    	  out.println("<li class='external'><a href='MyTask.jsp'>See all tasks <i class='m-icon-swapright'></i></a></li>");
			    	  
			      }
			    			
				
	}
	private Connection getConnection(){
		  Connection con= null;
		  try{
			 
			 con= new MyConnection().getConnection();
			 //con= DriverManager.getConnection("jdbc:mysql://168.144.175.32/admin_final_teacher","root","12345");
		  	} catch(Exception e){
			  System.out.println(e);
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
	  private int liveDemo(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con=getConnection();
			  String sql = "SELECT count(*) FROM lms_live_demo where NOTIFY=? and QUERY!=? group by email,date(qry_date),course";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "n");
				ps.setString(2, "Request For Live Demo send by Admin");
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						
						count++;
					}
					rs.close();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return count;
	  }
	  private ArrayList<String> getLiveDemo(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con=getConnection();
			  String sql = "SELECT COURSE FROM lms_live_demo where NOTIFY=? and QUERY!=? group by email,date(qry_date),course";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "n");
				ps.setString(2, "Request For Live Demo send by Admin");
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						
						
						al.add(rs.getString("COURSE"));
					}
					rs.close();
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
	  }
	  private int callBack(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con=getConnection();
			  String sql5 = "SELECT count(*) FROM callback where CALLBACK=?";
				PreparedStatement ps5 = con.prepareStatement(sql5);
				ps5.setString(1, "n");
					ResultSet rs5=ps5.executeQuery();
					while(rs5.next()){
						
						count=rs5.getInt(1);
					}
					rs5.close();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return count;
	  }
	 
	  private int contact(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con=getConnection();
			  String sql4 = "SELECT count(*) FROM lms_contact where NOTIFY=?";
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.setString(1, "n");
					ResultSet rs4=ps4.executeQuery();
					while(rs4.next()){
						
						count=rs4.getInt(1);
					}
					rs4.close();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return count;
	  }
	  private int todayTask(String username,String date){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con=getConnection();
			  String sql4 = "SELECT count(*) FROM seo_task where USER=? and (dateDIFF(?,date(FROM_DATE))>=0 AND DATEDIFF(date(TO_DATE),?)>=0)";
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.setString(1, username);
				ps4.setString(2, date);
				ps4.setString(3, date);
					ResultSet rs4=ps4.executeQuery();
					while(rs4.next()){
						
						count=rs4.getInt(1);
					}
					rs4.close();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return count;
	  }
	  private ArrayList<String> getTodayTask(String username,String date){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con=getConnection();
			  String sql4 = "SELECT TITLE,STATUS FROM seo_task where USER=? and (dateDIFF(?,date(FROM_DATE))>=0 AND DATEDIFF(date(TO_DATE),?)>=0)";
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.setString(1, username);
				ps4.setString(2, date);
				ps4.setString(3, date);
					ResultSet rs4=ps4.executeQuery();
					while(rs4.next()){
						
						al.add(rs4.getString("TITLE")+"/"+rs4.getString("STATUS"));
					}
					rs4.close();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
	  }
	  
	  

}
