package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import com.createsend.util.ApiKeyAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.models.clients.ClientBasics;
import com.createsend.models.subscribers.*;
import com.createsend.Clients;
import com.createsend.General;
import com.createsend.Subscribers;
/**
 * Servlet implementation class ActionServlet
 */
/**
 * @author Administrator Binay Gaur
 *
 */
public class Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String adminuser;
	ArrayList<String> list =null;
    /**
     * @see HttpServlet#HttpServlet()
     */
	String driver,url,user,password;
    public Contact() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		 
	      response.setContentType("text/html"); 
	      PrintWriter out = response.getWriter();
	      //driver= getServletContext().getInitParameter("driver");
	      //url= getServletContext().getInitParameter("url");
	      //user= getServletContext().getInitParameter("user");
	      //password= getServletContext().getInitParameter("password");
	      String jEventName=request.getParameter("jEventName");
	      //System.out.println(jEventName);
	      String jMessage=null;
	      HttpSession session=request.getSession();
    	  String username=(String)session.getAttribute("user");
    	  adminuser=username;
    	  
	       if(jEventName!=null && jEventName.equals("U_Name"))
	      {
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  System.out.println(name+" "+email);
	    	  updateName(email,name);
			  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_Email"))
	      {
	    	  String email_id=request.getParameter("email_id");
	    	  String email=request.getParameter("email");
	    	  updateEmail(email,email_id);
			 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Update_Campaign"))
	      {
	    	  String email=request.getParameter("email");
	    	  String course=request.getParameter("course");
	    	  String name=request.getParameter("name");
	    	  System.out.println("Name "+course);
	    	  try
	    	  {
	    	  ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
	    	            "fadb5e402a1facb2eb3e657092ec16df");
	    				  
	    	       General general = new General(auth);
	    	        ClientBasics[] clients = general.getClients();
	    	        for (ClientBasics cl : clients) {
	    	            Clients cls = new Clients(auth, cl.ClientID);
	    	            for(int i=0;i<(cls.lists()).length;i++)
	    	            {	//System.out.printf("List: %s\n", cls.lists()[i].ListID);
	    	            	//System.out.printf("List: %s\n", cls.lists()[i].Name);
	    	            	
	    	            	
	    	            	if(course!=null && course.equalsIgnoreCase("Hadoop") && (cls.lists()[i].ListID).equals("8fcf635537c1b34229ad65dc34b0d50f"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("Python") && (cls.lists()[i].ListID).equals("fb85eb4a43310b6ffd9a3165e954bd89"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("MongoDB") && (cls.lists()[i].ListID).equals("8987dc24b319992065658dbc41997c08"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("Business Analytics With R") && (cls.lists()[i].ListID).equals("a36cba80de5b5391f9acdf2ca5f53b9e"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("All") && (cls.lists()[i].ListID).equals("d1612eec4be17001428e1b5e7aa5a6b8"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	
	    	            }
	    	        }
			 
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  System.out.println(e);
	    	  }
	      }
	      else if(jEventName!=null && jEventName.equals("Update_CampaignLogin"))
	      {
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  //System.out.println("Course "+course);
	    	  //System.out.println("Name "+name);
	    	  try
	    	  {
	    	  ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
	    	            "fadb5e402a1facb2eb3e657092ec16df");
	    				  
	    	       General general = new General(auth);
	    	        ClientBasics[] clients = general.getClients();
	    	        for (ClientBasics cl : clients) {
	    	            Clients cls = new Clients(auth, cl.ClientID);
	    	            for(int i=0;i<(cls.lists()).length;i++)
	    	            {	//System.out.printf("List: %s\n", cls.lists()[i].ListID);
	    	            	//System.out.printf("List: %s\n", cls.lists()[i].Name);
	    	            	
	    	            	
	    	            	if( (cls.lists()[i].ListID).equals("d1612eec4be17001428e1b5e7aa5a6b8"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	
	    	            	
	    	            }
	    	        }
			 
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  System.out.println(e);
	    	  }
	      }
	      else if(jEventName!=null && jEventName.equals("Update_CampaignPayment"))
	      {
	    	  String email=request.getParameter("email");
	    	  String course=request.getParameter("course");
	    	  String name=request.getParameter("name");
	    	  if(updateDirectPayment(email))
	    		  updateCampaign(course, email, name);
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Update_CampaignDownload"))
	      {
	    	  String email=request.getParameter("email");
	    	  String course=request.getParameter("course");
	    	  String name=request.getParameter("name");
	    	  if(updateDownloads(email))
	    		  updateCampaign(course, email, name);
	    	  
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Update_CampaignSubscriber"))
	      {
	    	  String email=request.getParameter("email");
	    	  String course=request.getParameter("course");
	    	  String name=request.getParameter("name");
	    	  if(updateSubscriber(email))
	    		  updateCampaign(course, email, name);
	    	 
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_Phone"))
	      {
	    	  String email_id=request.getParameter("email");
	    	  String phone=request.getParameter("phone");
	    	  updatePhone(email_id,phone);
			  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("A_Response"))
	      {
	    	  int id=Integer.parseInt(request.getParameter("id"));
	    	  String user=request.getParameter("user");
	    	  String email_id=request.getParameter("email");
	    	  String pick=request.getParameter("pick");
	    	  String query=request.getParameter("query");
	    	  String attended=request.getParameter("attended");
	    	  String interest_level=request.getParameter("interest_level");
	    	  if(pick!=null && !pick.equals(""))
	    		  //updateLiveDemoPick(email_id,pick);
	    	  setAdminResponse(id,user,email_id,pick,query,interest_level,attended);
	    	  ArrayList<String> al=getLiveDemoInfo(email_id);
	    	  
	    	  out.println("<table class='table table-striped table-bordered table-hover' id='sample_2'><thead><tr><th>S.No.</th><th>Admin Name</th><th>Admin Response</th><th>Pickup</th><th>Interest Level</th><th>Attend</th><th>Response Date</th></tr></thead><tbody>");
				int i=0,j=0;
				for( i=0;i<al.size();i++)
				{
					j=i+1;
						String s=al.get(i);
						String s2[]=s.split("abczxy");
						 out.println("<tr>");
						 out.println("<td>"+j+"</td>");
						 out.println("<td>"+s2[0]+"</td>");
						 out.println("<td>"+s2[1]+"</td>");
						 out.println("<td>"+s2[2]+"</td>");
						 out.print("<td>");
				int k=Integer.parseInt(s2[3]);
				for(int x=1;x<=5;x++)
				{
					if(x<=k)
					{
						 out.println("<img src='img/s1.png' height='25px' width='25px' />");
					}
					else
					{
						out.println("<img src='img/s3.png' height='25px' width='25px' />");
					}
				}
				out.println("</td>");

				 out.println("<td>"+s2[4]+"</td>");
				 out.println("<td>"+s2[5]+"</td>");
						out.println("</tr>");
				
				}
	    	 
	    	  out.println("</tbody></table>");
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("G_Response"))
	      {
	    	 
	    	  String email=request.getParameter("email");
	    	  ArrayList<String> al=getLiveDemoInfo(email);
	    	  
	    	  out.println("<table class='table table-striped table-bordered table-hover' id='sample_2'><thead><tr><th>S.No.</th><th>Admin Name</th><th>Admin Response</th><th>Pickup</th><th>Interest Level</th><th>Attend</th><th>Response Date</th></tr></thead><tbody>");
				int i=0,j=0;
				for( i=0;i<al.size();i++)
				{
					j=i+1;
						String s=al.get(i);
						String s2[]=s.split("abczxy");
						 out.println("<tr>");
						 out.println("<td>"+j+"</td>");
						 out.println("<td>"+s2[0]+"</td>");
						 out.println("<td>"+s2[1]+"</td>");
						 out.println("<td>"+s2[2]+"</td>");
						 out.print("<td>");
				int k=Integer.parseInt(s2[3]);
				for(int x=1;x<=5;x++)
				{
					if(x<=k)
					{
						 out.println("<img src='img/s1.png' height='25px' width='25px' />");
					}
					else
					{
						out.println("<img src='img/s3.png' height='25px' width='25px' />");
					}
				}
				out.println("</td>");
				 out.println("<td>"+s2[4]+"</td>");
				 out.println("<td>"+s2[5]+"</td>");
						out.println("</tr>");
				
				}
	    	 
	    	  out.println("</tbody></table>");
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_Work"))
	      {
	    	  String email=request.getParameter("email");
	    	  String work=request.getParameter("work");
	    	  boolean b=checkLiveDemoDetail(email);
	    	  if(!b)
	    		  updateLiveDemoWork(email, work);
	      }
	      else if(jEventName!=null && jEventName.equals("U_Company"))
	      {
	    	  String email=request.getParameter("email");
	    	  String company=request.getParameter("company");
	    	  boolean b=checkLiveDemoDetail(email);
	    	  if(!b)
	    		  updateLiveDemoCompany(email, company);
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_NextTimeCall"))
	      {
	    	  String email=request.getParameter("email");
	    	  String nextCall=request.getParameter("nextcall");
	    	  boolean b=checkLiveDemoDetail(email);
	    	  if(!b)
	    		  updateLiveDemoNextCall(email, nextCall);
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_NextWebinar"))
	      {
	    	  String email=request.getParameter("email");
	    	  String nextwebinar=request.getParameter("nextwebinar");
	    	  String time=request.getParameter("time");
	    	  String id=request.getParameter("id");
	    	  if(time==null)
	    		  time="02:00:00";
	    	  String date2=nextwebinar+" "+time;
//	    	  /System.out.println(date2);
	    	  boolean b=checkLiveDemoDetail(email);
	    	  
	    	  if(!b)
	    		  updateLiveDemoNextWebinar(email, date2);
	    	  if(id!=null)
	    	  {
	    		  int rid=Integer.parseInt(id);
	    		  setAdminResponse(rid, adminuser, email, "Pickup", "Reschedule Webinar Link", "0", "");
	    		 // updateLiveDemoPick(email,"Pickup");
	    	  }
	    	  
	    	  ArrayList<String> al=getLiveDemoInfo(email);
	    	  out.println("<table class='table table-striped table-bordered table-hover' id='sample_2'><thead><tr><th>S.No.</th><th>Admin Name</th><th>Admin Response</th><th>Pickup</th><th>Interest Level</th><th>Attend</th><th>Response Date</th></tr></thead><tbody>");
				int i=0,j=0;
				for( i=0;i<al.size();i++)
				{
					j=i+1;
						String s=al.get(i);
						String s2[]=s.split("abczxy");
						 out.println("<tr>");
						 out.println("<td>"+j+"</td>");
						 out.println("<td>"+s2[0]+"</td>");
						 out.println("<td>"+s2[1]+"</td>");
						 out.println("<td>"+s2[2]+"</td>");
						 out.print("<td>");
				int k=Integer.parseInt(s2[3]);
				for(int x=1;x<=5;x++)
				{
					if(x<=k)
					{
						 out.println("<img src='img/s1.png' height='25px' width='25px' />");
					}
					else
					{
						out.println("<img src='img/s3.png' height='25px' width='25px' />");
					}
				}
				out.println("</td>");
				 out.println("<td>"+s2[4]+"</td>");
				 out.println("<td>"+s2[5]+"</td>");
						out.println("</tr>");
				
				}
	    	 
	    	  out.println("</tbody></table>");
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_Interest"))
	      {
	    	  String email=request.getParameter("email");
	    	  String interested=request.getParameter("interested");
	    	  boolean b=checkLiveDemoDetail(email);
	    	  
	    	  if(!b)
	    		  updateLiveDemoInterest(email, interested);
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Call_Interest"))
	      {
	    	  String email=request.getParameter("email");
	    	  String date=request.getParameter("date");
	    	  String time=request.getParameter("time");
	    	  String date2=date+" "+time;
	    	  boolean b=checkLiveDemoDetail(email);
	    	  if(b)
	    		  setLiveDemoDetail(email, " ", " ",date2, null,"y");
	    	 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("G_LiveDemoDetail"))
	      {
	    	  String email=request.getParameter("email");
	    	  ArrayList<String> al=new ArrayList<String>();
	    	  	al=getLiveDemoDetail(email);
	    	  	if(!al.isEmpty())
	    	  	{
	    	  		out.println(al.get(0)+"abczxy"+al.get(1)+"abczxy"+al.get(2)+"abczxy"+al.get(3)+"abczxy"+al.get(4));
	    	  		
	    	  	}
	    	  	else
	    	  		out.println(" abczxy abczxy abczxy abczxy abczxy");
	    	  	
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_IP"))
	      {
	    	  String lpId=request.getParameter("lp_id");
	    	  String ip=request.getParameter("ip");
	    	  //System.out.println("lp Id"+lpId);
	    	  //System.out.println("IP"+ip);
	    	  boolean b=updateIP(lpId,ip);
	    	  if(b)
			  out.println("IP Address update successful");
	    	  else
	    		  out.println("IP Address not update");
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("U_Country"))
	      {
	    	  String email_id=request.getParameter("email");
	    	  String countryCode=request.getParameter("country");
	    	  //System.out.println(countryCode);
	    	  Map<String, String> countries = new HashMap<String, String>();
	    	    for (String iso : Locale.getISOCountries()) {
	    	        Locale l = new Locale("", iso);
	    	        countries.put(iso,l.getDisplayCountry());
	    	    }
	    	    String country=countries.get(countryCode);
	    	    //System.out.println(country);
	    	    updateCountry(email_id,country,countryCode);
	    	  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("G_Task")) // Update Country
	      {
	    	 String s=getCalenderTask(username);
	    	 out.println(s);
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("C_Task")) // Create Task
	      {
	    	  String title=request.getParameter("title");
	    	  String description=request.getParameter("description");
	    	  String fromDate=request.getParameter("from");
	    	  String todate=request.getParameter("to");
	    	  String status=request.getParameter("status");
	    	  list = new ArrayList<String>();
	          list.add(0, username);
	          list.add(1, title);
	          list.add(2, description);
	          list.add(3, status);
	          list.add(4, fromDate);
	          list.add(5, todate);
	          if(saveTask())
	          {
	        	  jMessage = "Task has been added successfully";  
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("jMessage", jMessage);
	        	  request.getRequestDispatcher("MyTask.jsp").forward(request, response);
	        	  
	          }
	          else
	          {
	        	  jMessage = "Task has not added please contact to admin";  
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("jMessage", jMessage)
	        	  ;request.getRequestDispatcher("MyTask.jsp").forward(request, response);
	          }
	    	 
	    	  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Main_Task")) // Create Task
	      {
	    	  
	    	  ArrayList<String> al=new ArrayList<String>();
				
				al=getLatestTask(username);
				for(int i=0;i<al.size();i++)
				{
					String s=al.get(i);
					String s2[]=s.split("abczxy");
				
				
					out.println("<li><div class='task-title'><span class='task-title-sp'>"+s2[0]+"</span>");
					out.println("<span class='todo-tasklist-date'>From : <i class='fa fa-calendar'></i>"+s2[3]+"</span><span class='todo-tasklist-date'>-To : <i class='fa fa-calendar'></i> "+s2[4]+"</span> <span class='task-bell'><i class='fa fa-bell-o'></i></span><span class='label label-sm label-success'>"+s2[2]+"</span></div>");
					
					out.println("<div class='task-config'><div class='task-config-btn btn-group'><a class='btn btn-xs default' href='#' data-toggle='dropdown' data-hover='dropdown' data-close-others='true'><i class='fa fa-cog'></i><i class='fa fa-angle-down'></i></a>");
					out.println("<ul class='dropdown-menu pull-right'><li><a href='#'<i class='fa fa-check'></i> Complete </a></li><li><a href='#'><i class='fa fa-pencil'></i> Edit </a></li><li><a href='#'><i class='fa fa-trash-o'></i> Cancel </a></li>");
					out.println("</ul></div></div></li>");		
							
					
				}
				 
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Latest_Activity")) // Create Task
	      {
	    	  
	    	  ArrayList<String> al=new ArrayList<String>();
				
				al=getLatestActivity(username);
				String s3[]={"label label-sm label-info","label label-sm label-success","label label-sm label-danger","label label-sm label-default"};
				String s4[]={"fa fa-check","fa fa-bar-chart-o","fa fa-user","fa fa-shopping-cart","fa fa-bell-o","fa fa-briefcase"};
				int j=0,k=0;
				
				for(int i=0;i<al.size();i++)
				{
					String s=al.get(i);
					String s2[]=s.split("/");
					
					if(j>s3.length-1)
						j=0;
					if(k>s4.length-1)
						k=0;
					out.println("<li><div class='col1'><div class='cont'><div class='cont-col1'><div class='"+s3[j]+"'><i class='"+s4[k]+"'></i></div></div><div class='cont-col2'><div class='desc'>"+s2[0]+" <span class='label label-sm label-warning '></span></div></div></div></div>");
					out.println("<div class='col2'><div class='date'>"+s2[1]+"</div></div></li>");
					j++;
					k++;
				}
				
				
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("SaveLocation"))
	      {
	    	 
	    	  String id=request.getParameter("id");
	    	  String ip=request.getParameter("ip");
	    	  String city=request.getParameter("city");
	    	  String regionName=request.getParameter("regionName");
	    	  String country=request.getParameter("country");
	    	  String countryCode=request.getParameter("countryCode");
	    	  String timezone=request.getParameter("timezone");
	    	  String status=request.getParameter("status");
	    	  String lat=request.getParameter("lat");
	    	  String lon=request.getParameter("lon");
	    	  list = new ArrayList<String>();
	          list.add(0,ip);
	          list.add(1,city);
	          list.add(2,regionName);
	          list.add(3,country);
	          list.add(4,countryCode);
	          list.add(5,timezone);
	          list.add(6,status);
	          list.add(7,lat);
	          list.add(8,lon);
	          list.add(9,id);
	         
	    	  String s=saveLocation();
	    	  out.println(s);
	    	  
	    	  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("ViewFeedback")) // View Feedback
	      {
	    	  
	    	  list = new ArrayList<String>();
	    	  list=getFeedback();
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("data", list);
	        	  request.getRequestDispatcher("ViewFeedback.jsp").forward(request, response);
	    	  
	      }
	      
	      else if(jEventName!=null && jEventName.equals("StudentStory")) // View Student Story
	      {
	    	  
	    	  list = new ArrayList<String>();
	    	  list=getStudentStory();
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("data", list);
	        	  request.getRequestDispatcher("ViewStudentStory.jsp").forward(request, response);
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("ViewDirectPayment")) // Create Task
	      {
	    	  
	    	  	 list = new ArrayList<String>();
	    	  	 list=getDirectPayment();
	    	  	 for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 out.println("<td>"+s[2]+"</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 out.println("<td>"+s[4]+"</td>");
		        	 if(s[5].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+s[3]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("DirectPaymentByFilter")) // Create Task
	      {
	    	  String date=request.getParameter("d");
	    	  String date2=request.getParameter("d2");
	    	  String course=request.getParameter("subject");
	    	  list = new ArrayList<String>();
	    	  if((!date.equals("") && !date2.equals("")) && !course.equals("") )
	        	{
	        		//1
	        		
	    		  list=getDirectPayment(date,date2,course);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("")  )
	        	{
	        		//2
	        		
	        		 list=getDirectPayment(date,date2);
	        	}
	        	else if(date.equals("") && !course.equals(""))
	        	{
	        		 list=getDirectPayment(course);
	        	}
	        	else
	        	{
	        		 list=getDirectPayment();
	        	}
	    	  	 
	    	  	
	    	  	 for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 out.println("<td>"+s[2]+"</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 out.println("<td>"+s[4]+"</td>");
		        	 if(s[5].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+s[3]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("ViewDownloads")) // Create Task
	      {
	    	  
	    	  	 list = new ArrayList<String>();
	    	  	 list=getDownloads();
	    	  	 for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 out.println("<td>"+s[2]+"</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 out.println("<td>"+s[4]+"</td>");
		        	 out.println("<td>"+s[5]+"</td>");
		        	 if(s[6].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+s[3]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("DownloadsByFilter")) // Create Task
	      {
	    	  String date=request.getParameter("d");
	    	  String date2=request.getParameter("d2");
	    	  String course=request.getParameter("subject");
	    	  String type=request.getParameter("type");
	    	  list = new ArrayList<String>();
	    	  if((!date.equals("") && !date2.equals("")) && !course.equals("") && !type.equals(""))
	        	{
	        		//1
	        		
	    		  list=getDownloads(date, date2, course, type);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && type.equals(""))
	        	{
	        		//2
	        		
	        		 list=getDownloads(date, date2, course);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("")  && !type.equals(""))
	        	{
	        		//3
	        		
	        		 list=getDownloadsDateType(date, date2, type);
	        	}
	        	else if(date.equals("") && !course.equals("") && !type.equals(""))
	        	{
	        		//4
	        		 list=getDownloadsCourseType(course, type);
	        	}
	        	else if(date.equals("") && !course.equals("") && type.equals(""))
	        	{
	        		//5
	        		 list=getDownloads(course);
	        	}
	        	else if(date.equals("") && course.equals("") && !type.equals(""))
	        	{
	        		//6
	        		 list=getDownloadsType(type);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") && type.equals(""))
	        	{
	        		//7
	        		 list=getDownloads(date, date2);
	        	}
	        	else
	        	{
	        		//8
	        		 list=getDownloads();
	        	}
	    	  	 
	    	  	
	    	  	 for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 out.println("<td>"+s[2]+"</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 out.println("<td>"+s[4]+"</td>");
		        	 out.println("<td>"+s[5]+"</td>");
		        	 if(s[6].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+s[3]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      	
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("ViewSubscribe")) // Create Task
	      {
	    	  
	    	  	 list = new ArrayList<String>();
	    	  	 list=getSubscriber();
	    	  	for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 
		        	 if(s[2].equalsIgnoreCase("y"))
		        		 out.println("<td>Yes</td>");
		        	 else
		        		 out.println("<td>No</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 if(s[4].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;Learner&#39;,&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("SubscribeByFilter")) // Create Task
	      {
	    	  String date=request.getParameter("d");
	    	  String date2=request.getParameter("d2");
	    	  String course=request.getParameter("subject");
	    	  list = new ArrayList<String>();
	    	  if((!date.equals("") && !date2.equals("")) && !course.equals("") )
	        	{
	        		//1
	        		
	    		  list=getSubscriber(date, date2, course);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("")  )
	        	{
	        		//2
	        		
	        		 list=getSubscriber(date,date2);
	        	}
	        	else if(date.equals("") && !course.equals(""))
	        	{
	        		 list=getSubscriber(course);
	        	}
	        	else
	        	{
	        		 list=getSubscriber();
	        	}
	    	  	 
	    	  	
	    	  for(int i=0;i<list.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 String s[]=list.get(i).split("abczxy");
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+s[0]+"</td>");
		        	 out.println("<td>"+s[1]+"</td>");
		        	 
		        	 if(s[2].equalsIgnoreCase("y"))
		        		 out.println("<td>Yes</td>");
		        	 else
		        		 out.println("<td>No</td>");
		        	 out.println("<td>"+s[3]+"</td>");
		        	 if(s[4].equalsIgnoreCase("y"))
		        		 out.println("<td>Added</td>");
		        	 else
		        		 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;Learner&#39;,&#39;"+s[0]+"&#39;,&#39;"+s[1]+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("ViewLoginRequest")) // View Login Request
	      {
	    	  
	    	  List<Student> al = new ArrayList<Student>();
	    	  	 al=getLoginRequest();
	    	  	for(int i=0;i<al.size();i++)
	        	 {
	    	  		 
	        		 int j=i+1;
	        		 
	        		
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+al.get(i).getStudentId()+"</td>");
		        	 out.println("<td>"+al.get(i).getStudentName()+"</td>");
		        	 out.println("<td>"+al.get(i).getEmail()+"</td>");
		        	 out.println("<td>"+al.get(i).getPhone()+"</td>");
		        	 out.println("<td><input type='button' id ='b"+j+"' name='b1' class='btn btn-circle purple' value='Add To Campaign'  onclick='setCampaign(&#39;"+al.get(i).getStudentName()+"&#39;,&#39;"+al.get(i).getEmail()+"&#39;,&#39;"+j+"&#39;);' /></div><div id='loading"+j+"' style='display: none;'><img src='img/loading.gif' alt='loading'/></div><div id='campaign"+j+"'></div></td>");
				      
		        	 
		        	 out.println("</tr");
	        	 }
	    	  
	      }
	    
	      
	}
	private Connection getConnection(){
		  Connection con= null;
		  try{
			  //Class.forName("com.mysql.jdbc.Driver");
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
	  private boolean updateEmail(String email,String email_id){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lms_contact set EMAIL=? WHERE EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email_id);
				ps.setString(2, email);
					count=ps.executeUpdate();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private boolean updateName(String email,String name){

		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lms_contact set NAME=? WHERE EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, email);
					count=ps.executeUpdate();
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private boolean updatePhone(String email,String phone){
		 // System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lms_contact set PHONE_NO=? WHERE EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, phone);
				ps.setString(2, email);
					count=ps.executeUpdate();
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  
	 /* public boolean  updateLiveDemoPick(String email_id,String pick)
	  {
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lms_live_demo set PICKUP=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, pick);
				ps.setString(2, email_id);
				count=ps.executeUpdate();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
		  
	  }*/
	  public boolean  setAdminResponse(int id,String user,String email_id,String pick,String query,String interest_level,String attended)
	  {
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "insert into query_phonecall_response(RESPONSE_ID,USER,EMAIL,QRY_TYPE,QUERY,PICKUP,INTEREST_LEVEL,Attended) values(?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, user);
				ps.setString(3, email_id);
				ps.setString(4, "Contact Request");
				ps.setString(5, query);
				ps.setString(6, pick);
				ps.setString(7, interest_level);
				ps.setString(8, attended);
				count=ps.executeUpdate();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
		  
	  }
	  public ArrayList<String>  getLiveDemoInfo(String email){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  String sql="select * from query_phonecall_response  WHERE EMAIL=? order by RSP_DATE DESC";
			  //String sql="select L.EMAIL,L.QUERY,Q.QRY_TYPE,Q.QUERY,Q.PICKUP,L.QRY_DATE from lms_live_demo as L left OUTER join query_phonecall_response as Q on Q.RESPONSE_ID=L.Q_ID  WHERE L.EMAIL=? order by L.QRY_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, email);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
					 
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
						java.util.Date  date2 = format1.parse(rs.getString("RSP_DATE"));
						DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
						String time="",ld_date="";
							 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							 ld_date= df2.format(date2);
						        DateFormat df3 = new SimpleDateFormat("HH:mm");
						        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						        time=df3.format(date2);
						        
						al.add(rs.getString("USER")+"abczxy"+rs.getString("QUERY")+"abczxy"+rs.getString("PICKUP")+"abczxy"+rs.getString("INTEREST_LEVEL")+"abczxy"+rs.getString("ATTENDED")+"abczxy"+ld_date+" "+time);
					
					}	
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
	}
	  private boolean updateIP(String lpId,String ip){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lp_source set IP_ADDRESS=? WHERE ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, ip);
				ps.setString(2, lpId);
					count=ps.executeUpdate();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private boolean updateCountry(String email,String country,String countryCode){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "update lms_live_demo set COUNTRY_CODE=?,COUNTRY=? WHERE EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, countryCode);
				ps.setString(2, country);
				ps.setString(3, email);
					count=ps.executeUpdate();
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private boolean  saveTask()
	  {
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  if(!list.isEmpty())
			  {
				     DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy - hh:mm");
				     Date date=formatter2.parse(list.get(4));
				     Date date2=formatter2.parse(list.get(5));
				     Calendar cal=Calendar.getInstance();
				     cal.setTime(date);
				     Calendar cal2=Calendar.getInstance();
				     cal2.setTime(date2);
				     String f_time=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
				     String t_time=cal2.get(Calendar.HOUR_OF_DAY)+":"+cal2.get(Calendar.MINUTE);
				     java.sql.Date d=new java.sql.Date(date.getTime());
				     java.sql.Date d2=new java.sql.Date(date2.getTime());
			  String sql = "insert into seo_task(USER,TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE) values(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, list.get(2));
				ps.setString(4, list.get(3));
				ps.setString(5,d+" "+f_time);
				ps.setString(6, d2+" "+t_time);
				count=ps.executeUpdate();
				if(count!=0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, list.get(0));
					ps2.setString(2, "CTS");
					ps2.executeUpdate();
				}
			  }
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
		  
	  }
	  public ArrayList<String>  getTask(String user)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  
				  
			  String sql = "select TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE from seo_task where user=? order by FROM_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date  date= format1.parse(rs.getString("FROM_DATE"));
					java.util.Date  date2 = format1.parse(rs.getString("TO_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm");
					String f_date="",t_date;
						 f_date= df2.format(date);
						 t_date= df2.format(date2);
					al.add(rs.getString("TITLE")+"abczxy"+rs.getString("DESCRIPTION")+"abczxy"+rs.getString("STATUS")+"abczxy"+f_date+"abczxy"+t_date);
				}
		
			  
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
		  
	  }
	  public ArrayList<String> getTodayTask(String user)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  String sql = "select TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE from seo_task where user=? AND DATE(FROM_DATE)=case when (Month(SYSDATE()))>9 OR (Month(SYSDATE())<3) OR (( Month(SYSDATE()))=3 AND (DAY(SYSDATE())<8)) then DATE(DATE_ADD(SYSDATE(),INTERVAL 630 MINUTE)) else DATE(DATE_ADD(SYSDATE(),INTERVAL 570 MINUTE)) end";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date  date= format1.parse(rs.getString("FROM_DATE"));
					java.util.Date  date2 = format1.parse(rs.getString("TO_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm");
					String f_date="",t_date;
						 f_date= df2.format(date);
						 t_date= df2.format(date2);
					al.add(rs.getString("TITLE")+"abczxy"+rs.getString("DESCRIPTION")+"abczxy"+rs.getString("STATUS")+"abczxy"+f_date+"abczxy"+t_date);
				}
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
		  
	  }
	  public ArrayList<String>  getLatestTask(String user)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  
				  
			  String sql = "select TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE from seo_task where user=? order by FROM_DATE DESC limit 10";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date  date= format1.parse(rs.getString("FROM_DATE"));
					java.util.Date  date2 = format1.parse(rs.getString("TO_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm");
					String f_date="",t_date;
						 f_date= df2.format(date);
						 t_date= df2.format(date2);
						 al.add(rs.getString("TITLE")+"abczxy"+rs.getString("DESCRIPTION")+"abczxy"+rs.getString("STATUS")+"abczxy"+f_date+"abczxy"+t_date);
				}
		
			  
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
		  
	  }
	  public String getCalenderTask(String user)
	  {
		  Connection con = null;
		  String s="";
		  try
		  {
			  con = getConnection(); 
			  
				  
			  String sql = "select TITLE,FROM_DATE,TO_DATE from seo_task where user=? order by FROM_DATE DESC limit 10";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date  date= format1.parse(rs.getString("FROM_DATE"));
					java.util.Date  date2 = format1.parse(rs.getString("TO_DATE"));
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String f_date="",t_date;
						 f_date= df2.format(date);
						 t_date= df2.format(date2);
					if(s.equals(""))
						s=rs.getString("TITLE")+"abczxy"+f_date+"abczxy"+t_date;
					else
						s=s+"abcdef"+rs.getString("TITLE")+"abczxy"+f_date+"abczxy"+t_date;
				}
		
			  
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return s;
		  
	  }
	  public ArrayList<String>  getLastHistoryTask(String user)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			 
				  
				     String sql = "select DETAILS,DATE FROM seo_logs as L left outer join seo_logsmap as LM on L.SUBJECT=LM.SUBJECT WHERE USER=? and  LM.SUBJECT like '_TS' order by DATE DESC limit 5";
				     PreparedStatement ps = con.prepareStatement(sql);
				     ps.setString(1, user);
				     ResultSet rs=ps.executeQuery();
				     while(rs.next())
				     {
				    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
							java.util.Date  date2 = format1.parse(rs.getString("DATE"));
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
							df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							String ld_date="";
								 ld_date= df2.format(date2);
				    	    al.add(rs.getString("DETAILS")+"abczxy"+ld_date);
				     }
				
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		 
			  return al;
		  
	  }
	  public ArrayList<String>  getLatestActivity(String user)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			 
				  
				     String sql = "select DETAILS,DATE FROM seo_logs as L left outer join seo_logsmap as LM on L.SUBJECT=LM.SUBJECT WHERE USER=?  order by DATE DESC limit 15";
				     PreparedStatement ps = con.prepareStatement(sql);
				     ps.setString(1, user);
				     ResultSet rs=ps.executeQuery();
				     while(rs.next())
				     {
				    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
							java.util.Date  date2 = format1.parse(rs.getString("DATE"));
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
							df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							String ld_date="";
								 ld_date= df2.format(date2);
				    	    al.add(rs.getString("DETAILS")+"/"+ld_date);
				     }
				
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		 
			  return al;
		  
	  }
	  private String saveLocation(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  String code="";
		  try
		  {
			  con = getConnection();
			  if(!list.isEmpty())
			  {
				  String sql = "SELECT COUNTRY_CODE FROM seo_geolocation WHERE EMAIL= ?";
				  PreparedStatement ps = con.prepareStatement(sql);
				  ps.setString(1, list.get(9));
				  ResultSet rs = ps.executeQuery();
				  while(rs.next()){
					//System.out.println("countTID:  "+countTID);
					count = 1;
					code=rs.getString("COUNTRY_CODE");
				  	}
				  if(count==0)
				  {
					  String sql2 = "insert into seo_geolocation(IP_ADDRESS,CITY,REGION_NAME,COUNTRY,COUNTRY_CODE,TIMEZONE,STATUS,LAT,LON,EMAIL) values(?,?,?,?,?,?,?,?,?,?)";
					  PreparedStatement ps2 = con.prepareStatement(sql2);
					  ps2.setString(1, list.get(0));
					  ps2.setString(2, list.get(1));
					  ps2.setString(3, list.get(2));
					  ps2.setString(4, list.get(3));
					  ps2.setString(5, list.get(4));
					  ps2.setString(6, list.get(5));
					  ps2.setString(7, list.get(6));
					  ps2.setString(8, list.get(7));
					  ps2.setString(9, list.get(8));
					  ps2.setString(10, list.get(9));
					  ps2.executeUpdate();
					  String sql4 = "update lms_live_demo set COUNTRY=?,COUNTRY_CODE=? where EMAIL=?";
					  PreparedStatement ps4 = con.prepareStatement(sql4);
					  ps4.setString(1, list.get(3));
					  ps4.setString(2, list.get(4));
					  ps4.setString(3, list.get(9));
					  ps4.executeUpdate();
					  code=list.get(4);
				  }
			  }
		  }
		  catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			  
				  return code;
	  }
	  public boolean checkLiveDemoDetail(String email){
			Connection con = null;
			ResultSet rs = null;
			int countTID = 0;
			try{
				con = getConnection(); 
				String sql = "SELECT COUNT(1) FROM livedemo_detail WHERE EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				rs = ps.executeQuery();
				while(rs.next()){
					//System.out.println("countTID:  "+countTID);
					countTID = rs.getInt(1);
				}
			}catch(SQLException sqe){
				System.out.println(sqe);
				sqe.printStackTrace();
			}
			catch(Exception sqe){
				System.out.println(sqe);
				sqe.printStackTrace();
			}
			finally{closeConnection(con);}
			if(countTID == 0)return true;else return false;
		  }
		public boolean setLiveDemoDetail(String email,String work,String company,String nextcall,String nextwebinar,String interest){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				java.util.Date  date2 = format1.parse(nextcall);
				DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date="";
					 date= df2.format(date2);
				String sql = "INSERT INTO livedemo_detail(EMAIL,WORK,COMPANY,NEXTTIMECALL,NEXTWEBINAR,INTERESTED) VALUES(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				
				     
					ps.setString(1, email);
					ps.setString(2, work);
					ps.setString(3, company);
					ps.setString(4, date);
					ps.setString(5, nextwebinar);
					ps.setString(6, interest);
					value = ps.executeUpdate();	
					if(value!=-1)
					{
						String sql2 = "INSERT INTO seo_task(USER,TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE) VALUES(?,?,?,?,?,?)";
						PreparedStatement ps2 = con.prepareStatement(sql2);
						
						     
							ps2.setString(1, adminuser);
							ps2.setString(2, "Call To "+email);
							ps2.setString(3, "New Live Demo Call");
							ps2.setString(4, "Important");
							ps2.setString(5, date);
							ps2.setString(6, date);
							ps2.executeUpdate();	
					}
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateLiveDemoWork(String email,String work){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "update livedemo_detail set WORK=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, work); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
				
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateLiveDemoCompany(String email,String company){	
			Connection con = null;
			int value = -1;
			try{
				con = getConnection(); 
				String sql = "update livedemo_detail set COMPANY=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, company); 
					ps.setString(2, email);
					
					value = ps.executeUpdate();	
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateLiveDemoNextCall(String email,String nextcall){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "update livedemo_detail set NEXTTIMECALL=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, nextcall); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
					if(value!=-1)
					{
						String sql2 = "INSERT INTO seo_task(USER,TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE) VALUES(?,?,?,?,?,?)";
						PreparedStatement ps2 = con.prepareStatement(sql2);
						
						     
							ps2.setString(1, adminuser);
							ps2.setString(2, "Call To "+email);
							ps2.setString(3, "Update Free Trial data");
							ps2.setString(4, "Important");
							ps2.setString(5, nextcall);
							ps2.setString(6, nextcall);
							ps2.executeUpdate();	
					}
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateLiveDemoNextWebinar(String email,String nextwebinar){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				
				String sql = "update livedemo_detail set NEXTWEBINAR=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, nextwebinar); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
					if(value!=-1)
					{
						
						String sql2 = "INSERT INTO seo_task(USER,TITLE,DESCRIPTION,STATUS,FROM_DATE,TO_DATE) VALUES(?,?,?,?,?,?)";
						PreparedStatement ps2 = con.prepareStatement(sql2);
						     
							ps2.setString(1, adminuser);
							ps2.setString(2, "Reschedule Webinar Link To "+email);
							ps2.setString(3, "Reschedule Webinar link on this date");
							ps2.setString(4, "Important");
							ps2.setString(5, nextwebinar);
							ps2.setString(6, nextwebinar);
							ps2.executeUpdate();	
					}
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		
		public boolean updateLiveDemoInterest(String email,String interest){	
			Connection con = null;
			int value = -1;
			try{
				con = getConnection(); 
				if(interest.equals("n"))
				{
				String sql = "update livedemo_detail set INTERESTED=?,NEXTTIMECALL=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, interest); 
					ps.setString(2, null);
					ps.setString(3, email);
					
					value = ps.executeUpdate();	
					if(value!=-1 )
					{
							String sql2 = "delete from seo_task where TITLE like ?";
							PreparedStatement ps2 = con.prepareStatement(sql2); 
							ps2.setString(1, "%"+email+"%");
							
							ps2.executeUpdate();	
					}
				}
				else if(interest!=null && interest.equals("y"))
				{
					String sql = "update livedemo_detail set INTERESTED=? where EMAIL=?";
					PreparedStatement ps = con.prepareStatement(sql);
					
					    ps.setString(1, interest); 
						ps.setString(2, email);
						value = ps.executeUpdate();
				}
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		
		public ArrayList<String> getLiveDemoDetail(String email){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			ArrayList<String> al=new ArrayList<String>();
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "select * from livedemo_detail  where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
					ps.setString(1, email);
					
					
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						String ld_date=" ",next_webinar=" ";
						String work=" ",company=" ";
						
						if(rs.getString("NEXTTIMECALL")!=null)
						{
							SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							java.util.Date  date2 = format1.parse(rs.getString("NEXTTIMECALL"));
							 ld_date= df2.format(date2);
							
						}
						if(rs.getString("NEXTWEBINAR")!=null)
						{
							SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
							java.util.Date  date3 = format1.parse(rs.getString("NEXTWEBINAR"));
							 next_webinar=df2.format(date3);
						}
						if(rs.getString("WORK")!=null)
							work=rs.getString("WORK");
						if(rs.getString("COMPANY")!=null)
							company=rs.getString("COMPANY");
						al.add(work);
						al.add(company);
						al.add(ld_date);
						al.add(next_webinar);
						al.add(rs.getString("INTERESTED"));
						
					}
				
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			return al;
		  }
		public ArrayList<String>  getFeedback()
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,F.PHONE,S.SUBJECT_NAME,COMPANY_NAME,DESIGNATION,RATING,SOURCE,EXPERIENCE,SUGGESTION,F.STATUS,YEAR,COLLEGE from feedback as F join student_login as L on F.EMAIL=L.EMAIL_ID JOIN lms_student_alloted as A on L.STUDENT_ID=A.STUDENT_ID LEFT OUTER JOIN subjects AS S ON S.SUBJECT_ID=A.SUBJECT_ID";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    
					    	    al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("F.PHONE")+"abczxy"+rs.getString("S.SUBJECT_NAME")+"abczxy"+rs.getString("COMPANY_NAME")+"abczxy"+rs.getString("DESIGNATION")+"abczxy"+rs.getString("RATING")+"abczxy"+rs.getString("SOURCE")+"abczxy"+rs.getString("EXPERIENCE")+"abczxy"+rs.getString("SUGGESTION")+"abczxy"+rs.getString("F.STATUS")+"abczxy"+rs.getString("YEAR")+"abczxy"+rs.getString("COLLEGE"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getStudentStory()
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select STUDENT_ID,STORY,IP_ADDRESS,TIMESTAMP from student_story";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
					    	    
					    	    al.add(rs.getString("STUDENT_ID")+"abczxy"+rs.getString("STORY")+"abczxy"+rs.getString("IP_ADDRESS")+"abczxy"+ld_date);
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDirectPayment()
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE_NO,COURSE,TIMESTAMP,CAMPAIGNED from direct_payment order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
					    	    al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("COURSE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDirectPayment(String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE_NO,COURSE,TIMESTAMP,CAMPAIGNED from direct_payment where course=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("COURSE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDirectPayment(String d,String td)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
							
					     String sql = "select NAME,EMAIL,PHONE_NO,COURSE,TIMESTAMP,CAMPAIGNED from direct_payment where TIMESTAMP>=? and TIMESTAMP<=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("COURSE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDirectPayment(String d,String td,String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select NAME,EMAIL,PHONE_NO,COURSE,TIMESTAMP,CAMPAIGNED from direct_payment where TIMESTAMP>=? and TIMESTAMP<=? and course=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ps.setString(3, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("COURSE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloads()
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
					    	    al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloads(String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where course=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloadsType(String type)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TYPE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, type);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloadsCourseType(String course,String type)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TYPE=? and COURSE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, type);
					     ps.setString(2, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloads(String d,String td)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TIMESTAMP>=? and TIMESTAMP<=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloads(String d,String td,String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TIMESTAMP>=? and TIMESTAMP<=? and COURSE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ps.setString(3, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloadsDateType(String d,String td,String type)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TIMESTAMP>=? and TIMESTAMP<=? and TYPE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ps.setString(3, type);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getDownloads(String d,String td,String course,String type)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select NAME,EMAIL,PHONE,COURSE,TYPE,TIMESTAMP,CAMPAIGNED from downloads where TIMESTAMP>=? and TIMESTAMP<=? and COURSE=? and TYPE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ps.setString(3, course);
					     ps.setString(4, type);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("TYPE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getSubscriber()
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select EMAIL,COURSE,SUBSCRIBE,TIMESTAMP,CAMPAIGNED from subscribe order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
					    	    al.add(rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("SUBSCRIBE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getSubscriber(String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select EMAIL,COURSE,SUBSCRIBE,TIMESTAMP,CAMPAIGNED from subscribe where and COURSE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("SUBSCRIBE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getSubscriber(String d,String td)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					  
					     String sql = "select EMAIL,COURSE,SUBSCRIBE,TIMESTAMP,CAMPAIGNED from subscribe where TIMESTAMP>=? and TIMESTAMP<=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					    
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("SUBSCRIBE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public ArrayList<String>  getSubscriber(String d,String td,String course)
		  {
			  Connection con = null;
			  ArrayList<String> al=new ArrayList<String>();
			  try
			  {
				  con = getConnection(); 
				 
				  DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(d);
					  java.util.Date  date3 = df.parse(td);
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
						 Calendar cal2 = Calendar.getInstance();
						 cal2.setTime(date3);
						 cal.add(Calendar.DATE, -1);
							java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
							java.sql.Date d3=new java.sql.Date(date3.getTime());
							String r="14:30:00";
							String r2="14:30:00";
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								r="13:30:00";
								
							}
							if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
							{
								r2="13:30:00";
								
							}
					     String sql = "select EMAIL,COURSE,SUBSCRIBE,TIMESTAMP,CAMPAIGNED from subscribe where TIMESTAMP>=? and TIMESTAMP<=? and COURSE=? order by TIMESTAMP DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ps.setString(1, d2+" "+r);
						 ps.setString(2, d3+" "+r2);
					     ps.setString(3, course);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    	    format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm a");
								df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								String ld_date="";
									 ld_date= df2.format(date2);
									 al.add(rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("SUBSCRIBE")+"abczxy"+ld_date+"abczxy"+rs.getString("CAMPAIGNED"));
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return al;
			  
		  }
		public List<Student>  getLoginRequest()
		  {
			  Connection con = null;
			  List<Student> list = new ArrayList<Student>();
			    Student sd = null;
			  try
			  {
				  con = getConnection(); 
				 
					  
					     String sql = "select STUDENT_ID,STUDENT_NAME,EMAIL_ID,PHONE from student_login where EMAIL_ID not in (select email from lms_live_demo group by email)  order by  STUDENT_ID DESC";
					     PreparedStatement ps = con.prepareStatement(sql);
					     ResultSet rs=ps.executeQuery();
					     while(rs.next())
					     {
					    	  sd = new Student();
					          
					            sd.setStudentId(rs.getString("STUDENT_ID"));
					            sd.setStudentName(rs.getString("STUDENT_NAME"));
					            sd.setEmail(rs.getString("EMAIL_ID"));
					            sd.setPhone(rs.getString("PHONE"));
					            list.add(sd);
					     }
					
						
					
			  }
			  catch(Exception sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}finally{closeConnection(con);}
			 
				  return list;
			  
		  }
		public boolean updateDirectPayment(String email){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "update direct_payment set CAMPAIGNED=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, "y"); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
				
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateDownloads(String email){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "update downloads set CAMPAIGNED=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, "y"); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
				
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateSubscriber(String email){	
			  //System.out.println("list :  "+list);
			Connection con = null;
			int value = -1;
			//int n=-1;
			try{
				con = getConnection(); 
				String sql = "update subscribe set CAMPAIGNED=? where EMAIL=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				    ps.setString(1, "y"); 
					ps.setString(2, email);
					
					
					value = ps.executeUpdate();	
				
				
			}catch(SQLException sqe){
				System.out.println(sqe);sqe.printStackTrace();
			}
			catch(Exception e){
				System.out.println(e);e.printStackTrace();
			}
			finally{closeConnection(con);}
			if(value != -1){return true;}else return false;
		  }
		public boolean updateCampaign(String course,String email,String name)
		{
			//System.out.printf("Hello "+course);
			try
	    	  {
	    	  ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
	    	            "fadb5e402a1facb2eb3e657092ec16df");
	    				  
	    	       General general = new General(auth);
	    	        ClientBasics[] clients = general.getClients();
	    	        for (ClientBasics cl : clients) {
	    	            Clients cls = new Clients(auth, cl.ClientID);
	    	            for(int i=0;i<(cls.lists()).length;i++)
	    	            {	
	    	            	
	    	            	
	    	            	if(course!=null && course.equalsIgnoreCase("Hadoop") && (cls.lists()[i].ListID).equals("8fcf635537c1b34229ad65dc34b0d50f"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("Python") && (cls.lists()[i].ListID).equals("fb85eb4a43310b6ffd9a3165e954bd89"))
	    	            	{
	    	            		//System.out.printf("Hi "+course);
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("MongoDB") && (cls.lists()[i].ListID).equals("8987dc24b319992065658dbc41997c08"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("Business Analytics With R") && (cls.lists()[i].ListID).equals("a36cba80de5b5391f9acdf2ca5f53b9e"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	else if(course!=null && course.equalsIgnoreCase("All") && (cls.lists()[i].ListID).equals("d1612eec4be17001428e1b5e7aa5a6b8"))
	    	            	{
	    	                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
	    	                    SubscriberToAdd sm= new SubscriberToAdd();
	    	                    sm.Name=name;
	    	                    sm.EmailAddress=email;
	    	                    String sms=ss.add(sm);
	    	                    //System.out.print(sms);
	    	            	}
	    	            	
	    	            }
	    	        }
			 
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  System.out.println(e);
	    	  }
			return true;
		}
		

}
