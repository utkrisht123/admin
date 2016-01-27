/**
 * @author Administrator Binay Gaur
 *
 */
package com;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

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
import java.util.List;
import java.util.TimeZone;

public class SendEmail extends HttpServlet{
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	  ArrayList<String> list =null;
	  String username=null;
    public SendEmail() {
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
	 * @param jMessage 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		 
	      response.setContentType("text/html"); 
	      PrintWriter out = response.getWriter();
	    
	      String jEventName=request.getParameter("jEventName");
	      //System.out.println(jEventName);
	      HttpSession session=request.getSession();
	      String user=(String)session.getAttribute("user");
	      username=user;
	      if(jEventName!=null && jEventName.equals("BulkEmail")) /// Send Bulk Email
	      {
	    	  list =new ArrayList<String> ();
	    	  String course=request.getParameter("course");
	    	  String optionsRadios=request.getParameter("optionsRadios");
	    	  String htmlfile=request.getParameter("htmlfile");
	    	  //System.out.println(optionsRadios);
	    	  String emailuser=request.getParameter("emailuser");
	    	  String allemail="";
	    	  if(optionsRadios!=null && optionsRadios.equalsIgnoreCase("default")) // Send Bulk Email by default
	    	  {
	    		  if(course!=null && course.equalsIgnoreCase("All"))
	    				list=getLiveDemoEmail();
	    			else
	    				list=getLiveDemoEmail(course);
	    		  for(int i=0;i<list.size();i++)
	    		  {
	    			  if(i==0)
		    		  		allemail=list.get(i);
		    		  	else
	    			  allemail=allemail+","+list.get(i);
	    			  
	    		  }
	    	  }
	    	  else if(optionsRadios!=null && optionsRadios.equalsIgnoreCase("custom")) //// Send Bulk Email by custom
	    	  {
	    		  String toEmails[]=request.getParameterValues("emails");
		    	  for(int i=0;i<toEmails.length;i++)
		    	  {
		    		  	list.add(toEmails[i]);
		    		  	if(i==0)
		    		  		allemail=toEmails[i];
		    		  	else
		    		  	   allemail=allemail+","+toEmails[i];
		    	  }
	    	  }
	    	  else if(optionsRadios!=null && optionsRadios.equalsIgnoreCase("inputemail")) // Send Bulk Email by input email
	    	  {
	    		  String email=request.getParameter("email");
		    	  String toEmails[]=email.split(",");
		    	  for(int i=0;i<toEmails.length;i++)
		    	  {
		    		  	list.add(toEmails[i]);
		    		  	if(i==0)
		    		  		allemail=toEmails[i];
		    		  	else
		    		  	   allemail=allemail+","+toEmails[i];
		    	  }
	    	  }
	    	  String emailSubject=request.getParameter("subject");
	    	  String mailBody=request.getParameter("message");
	    	  String s=getUserDetail(emailuser);
	    	  String s2[]=s.split("/");
	    	  String host=s2[0];
	    	  String pass=s2[1];
	    	  setBulkEmail(user,emailSubject,htmlfile,course,allemail);
	    	  		//
	    	  if(bulkEmail(list,emailSubject,mailBody,emailuser,pass,host))
	    	  {
	    		  request.setAttribute("jMessage", "Email has been send  successfully to all selected email");
	    		  request.setAttribute("jEventName", jEventName);
	    		  request.getRequestDispatcher("BulkEmail.jsp").forward(request, response);
	    	  }
	    	  else
	    	  {
	    		  request.setAttribute("jMessage", "Email has not send please contact to admin");
	    		  request.setAttribute("jEventName", jEventName);
	    		  request.getRequestDispatcher("BulkEmail.jsp").forward(request, response);
	    	  }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("SingleEmail")) // Send Single Email
	      {
	    	  String course=request.getParameter("course");
	    	  String email=request.getParameter("email");
	    	  String htmlfile=request.getParameter("htmlfile");
	    	  String emailSubject=request.getParameter("subject");
	    	  String emailuser=request.getParameter("emailuser");
	    	 
	    	  String mailBody=request.getParameter("message");
	    	  String s=getUserDetail(emailuser);
	    	  String s2[]=s.split("/");
	    	  String host=s2[0];
	    	  String pass=s2[1];
	    	  setSingleEmail(user,emailSubject,htmlfile,course,email);
	    	  
	    	  if(singleEmail(email,emailSubject,mailBody,emailuser,pass,host))
	    	  {
	    		  request.setAttribute("jMessage", "Email has been send successfully");
	    		  request.setAttribute("jEventName", jEventName);
	    		  request.getRequestDispatcher("CustomEmail.jsp").forward(request, response);
	    	  }
	    	  else
	    	  {
	    		  request.setAttribute("jMessage", "Email has not send please contact to admin");request.setAttribute("jEventName", jEventName);
	        	  request.getRequestDispatcher("CustomEmail.jsp").forward(request, response);  
	    	  }
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Send_Syllabus"))//// Send Syllabus Email
	      {
	    	 
	    	  String course=request.getParameter("course");
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  String date=request.getParameter("date");
	    	  String interest_level=request.getParameter("interest_level");
	    	  if(interest_level==null)
	    		  interest_level="0";
	    	  //ArrayList<String> al2=new  ArrayList<String>();
	    	  //al2=getBatchDetail(course, date);
	    	  boolean b=sendCourseSyllabus(email, name, course);
	    	  int id=Integer.parseInt(request.getParameter("id"));
	    	  ActionServlet sm=new ActionServlet();
	    	  if(b)
	    	  sm.setAdminResponse(id,user,email,"Pickup",course+" course syllabus sent",interest_level,"");
	    	  ArrayList<String> al=sm.getLiveDemoInfo(email);
	    	  
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
	      else if(jEventName!=null && jEventName.equals("Send_YouTube"))// Send You Tube Email
	      {
	    	  String course=request.getParameter("course");
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  String date=request.getParameter("date");
	    	  String interest_level=request.getParameter("interest_level");
	    	  if(interest_level==null)
	    		  interest_level="0";
	    	  ArrayList<String> al2=new  ArrayList<String>();
	    	  //al2=getBatchDetail(course, date);
	    	  boolean b=sendYouTubeEmail(email, name, course,al2);
	    	  ActionServlet sm=new ActionServlet();
	    	  int id=Integer.parseInt(request.getParameter("id"));
	    	  if(b)
	    	  sm.setAdminResponse(id,user,email,"Pickup",course+" course youtube link sent",interest_level,"");
	    	  ArrayList<String> al=sm.getLiveDemoInfo(email);
	    	  
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
	      else if(jEventName!=null && jEventName.equals("Send_BatchDetail"))// Send Batch Detail Email
	      {
	    	  String course=request.getParameter("course");
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  String qry_type=request.getParameter("type");
	    	  String country=request.getParameter("country");
	    	  
	    	  String interest_level=request.getParameter("interest_level");
	    	  if(interest_level==null)
	    		  interest_level="0";
	    	 
	    	  ArrayList<String> al2=new  ArrayList<String>();
	    	  al2=getBatchDetail(course,country);
	    	  boolean b=sendBatchDetail(email, name, course,al2);
	    	  
	    	  ActionServlet sm=new ActionServlet();
	    	  int id=Integer.parseInt(request.getParameter("id"));
	    	  boolean set=false;
	    	  if(b)
	    		  set=sm.setAdminResponse(id,user,email,"Pickup",course+" course batch detail sent",interest_level,"");
	    	  
	    	  /*ArrayList<String> al=sm.getLiveDemoInfo(email);
	    	  
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
	    	 
	    	  out.println("</tbody></table>");*/
	    	  if(set)
	    	  {
	    		 
	    		  request.setAttribute("response", set);
	    		  request.setAttribute("jEventName", "InstructorEmail");
	    		  request.setAttribute("jMessage", "your email has been send");
	    		  request.getRequestDispatcher("InstructorLed_Email.jsp").forward(request, response);
	    	  }else
	    	  {
	    		  request.setAttribute("jEventName", "Error");
	    		  request.setAttribute("jMessage", "error in your mail");
	    		  request.getRequestDispatcher("InstructorLed_Email.jsp").forward(request, response);
	    	  }
	    	  
	    	  
	      }
	      else if(jEventName!=null && jEventName.equals("Send_SelfEmail"))// Send Batch Detail Email
	      {
	    	  
	    	  String course=request.getParameter("course");
	    	  String email=request.getParameter("email");
	    	  String name=request.getParameter("name");
	    	  String country=request.getParameter("country");
	    	  String qry_type=request.getParameter("type");
	    	  System.out.println(qry_type);
	    	  String interest_level=request.getParameter("interest_level");
	    	  if(interest_level==null)
	    		  interest_level="0";
	    	  
	    	  ArrayList<String> al2=new  ArrayList<String>();
	    	  al2=selfBatchDetail(course,country);
	    	  
	    	  boolean b=sendSelfEmail(email, name, course,al2);
	    	  boolean set=false;
	    	  ActionServlet sm=new ActionServlet();
	    	  String id=request.getParameter("id");
	    	  int id1=0;
	    	  if(id.equals("null"))
	    	  {
	    		  id1=0;
	    	  }else
	    	  {
	    		  id1=Integer.parseInt(id);
	    	  }
	    	  if(b)
	    		  set=sm.setSelfEmailResp(id1,user,email,qry_type,"Pickup",course+" course batch detail sent",interest_level,"");
	    	  
	    	  ArrayList<String> al=sm.getLiveDemoInfo(email);
	    	  if(set)
	    	  {
	    		 
	    		  request.setAttribute("response", set);
	    		  request.setAttribute("jEventName", "SelfEmail");
	    		  request.setAttribute("jMessage", "your email has been send");
	    		  request.getRequestDispatcher("Self_Paced_Email.jsp").forward(request, response);
	    	  }else
	    	  {
	    		  request.setAttribute("jEventName", "Error");
	    		  request.setAttribute("jMessage", "error in your mail");
	    		  request.getRequestDispatcher("Self_Paced_Email.jsp").forward(request, response);
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
		public boolean bulkEmail(ArrayList<String> al,String emailSubject,String emailBody,String emailuser,String pass,String host) {
			boolean flag=false;
			try
			{
				//String host="mail.gyansha.com";  
    	        final String user=emailuser;
    	        final String password=pass; 
    	        

   	         //Get the session object  
   	        Properties props = new Properties();  
   	        props.put("mail.smtp.host",host);  
	        props.put("mail.smtp.port", "587"); //TLS Port
	      	props.put("mail.smtp.auth", "true"); //enable authentication
	      	props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           for (int i = 0; i < al.size(); i++) {
   	        	message.addRecipient(Message.RecipientType.BCC, new InternetAddress(al.get(i)));
   	           }
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	           message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	           //System.out.println("All EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		public boolean sendLMSUrl(String toEmail,String name) {
			boolean flag=false;
			String emailBody="";
			String emailSubject="Learning Management System (LMS) Link | Easylearning.Guru";
			try
			{
				//String host="mail.gyansha.com";  
				
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1";  
    	        emailBody="<!DOCTYPE html><html ><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><title>Learning Management System (LMS) Link | Easylearning.Guru</title><meta http-equiv='X-UA-Compatible' content='IE=EDGE'><meta name='viewport' content='' id='viewport'></head><body > <div style='border-bottom: 1px solid #7b7979; margin: 20px 0 20px 0'> <table class='header-container' id='topheader' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td id='td_main_links' align='left'> <table id='header_linkstable' style='margin-left: 5%' cellpadding='0' cellspacing='0'><tbody><tr id='tr_links'><th id='header_logo'><a href='http://easylearning.guru/' target='_blank' rel='noreferrer'><img width='300' src='http://student.easylearning.guru/mailerimage/logo.png' alt='headerLogo' style='border: none'></a></th> <th width='60%'>&nbsp;</th> <th align='right'> <a class='' href='http://www.facebook.com/Easylearning.guru' target='_blank' rel='noreferrer'><img src='http://student.easylearning.guru/mailerimage/fb.png' alt='' style='border: none'></a></th> <th width='10%'>&nbsp;</th> <th align='right'><a href='https://twitter.com/intent/follow?original_referer=&region=follow_link&screen_name=easylearninguru&tw_p=followbutton&variant=2.0' target='_blank' rel='noreferrer'><img src='http://student.easylearning.guru/mailerimage/tw.png' alt='' style='border: none'></a></th> <th width='20%'>&nbsp;</th> </tr></tbody></table></td></tr></tbody></table><div>&nbsp;</div></div><table id='table_011' align='center' cellpadding='0' cellspacing='0'><tbody><tr><td> <div style='padding: 10px'> <p>Dear <b> "+name+",</b></p><p>Thank you for enrolling with Easylearning.Guru<br></p><p> You can access the Learning Management System through the link mentioned below: <br></p><p align='center'><b><a href='http://student.easylearning.guru'>student.easylearning.guru</a></b><br></p><p align='left'>You will have to enter your registered Email Id&nbsp; and password to access the LMS<br>Thank you<br></p><p align='center'>Your queries are always welcome<br>You can send your query, comments and feedback to: <b>contact@easylearning.guru</b></p></div></td></tr></tbody></table><br><br><div class='footer-menu' align='center'> <table style='border: none'><tbody><tr><td>You are receiving this email because you registered on our website.</td></tr><tr style='font-weight: bolder'><td align='center'><b>&copy; Copyright 2015</b> <a>Easylearning.guru</a></td></tr><tr></tr></tbody></table></div></body></html>";

   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	         props.put("mail.smtp.port", "587"); //TLS Port
   	      	props.put("mail.smtp.auth", "true"); //enable authentication
   	      	props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	           message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		
		public boolean sendRegisterStudent(String toEmail,String name,String studentPassword) {
			boolean flag=false;
			String emailBody="";
			String emailSubject="Learning Management System (LMS) Link | Easylearning.Guru";
			try
			{
				//String host="mail.gyansha.com";  
				
				final String user="support@easylearning.guru";
    	   	    final String password="Facebook.com1";  
    	        emailBody="<!DOCTYPE html><html ><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><title>Login Details | Easylearning.Guru</title><meta http-equiv='X-UA-Compatible' content='IE=EDGE'><meta name='viewport' content='' id='viewport'></head><body > <div style='border-bottom: 1px solid #7b7979; margin: 20px 0 20px 0'> <table class='header-container' id='topheader' cellpadding='0' cellspacing='0' width='100%'><tbody><tr><td id='td_main_links' align='left'> <table id='header_linkstable' style='margin-left: 5%' cellpadding='0' cellspacing='0'><tbody><tr id='tr_links'><th id='header_logo'><a href='http://easylearning.guru/' target='_blank' rel='noreferrer'><img width='300' src='http://student.easylearning.guru/mailerimage/logo.png' alt='headerLogo' style='border: none'></a></th> <th width='60%'>&nbsp;</th> <th align='right'> <a class='' href='http://www.facebook.com/Easylearning.guru' target='_blank' rel='noreferrer'><img src='http://student.easylearning.guru/mailerimage/fb.png' alt='' style='border: none'></a></th> <th width='10%'>&nbsp;</th> <th align='right'><a href='https://twitter.com/intent/follow?original_referer=&region=follow_link&screen_name=easylearninguru&tw_p=followbutton&variant=2.0' target='_blank' rel='noreferrer'><img src='http://student.easylearning.guru/mailerimage/tw.png' alt='' style='border: none'></a></th> <th width='20%'>&nbsp;</th> </tr></tbody></table></td></tr></tbody></table><div>&nbsp;</div></div><table id='table_011' align='center' cellpadding='0' cellspacing='0'><tbody><tr><td> <div style='padding: 10px'> <p>Dear <b> '+name+',</b></p><p>You are registered on Easylearning.Guru as a Learner<br></p><p> Username: "+toEmail+" <br></p><p> Password: "+studentPassword+" <br></p><p align='left'>You will have to You will have to enter the above email id and password to access the content on Easylearning.Guru.<br></p><p align='left'>Note: We suggest you to change your password on our website Easylearning.Guru<br><br>Thank you<br></p><p align='center'>Your queries are always welcome<br>You can send your query, comments and feedback to: <b>contact@easylearning.guru</b></p></div></td></tr></tbody></table><br><br><div class='footer-menu' align='center'> <table style='border: none'><tbody><tr><td>You are receiving this email because you registered on our website.</td></tr><tr style='font-weight: bolder'><td align='center'><b>© Copyright 2015</b> <a>Easylearning.guru</a></td></tr><tr></tr></tbody></table></div></body></html>";
    	        		
    	        	
   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","mail.gyansha.com");  
	   	      props.put("mail.smtp.port", "587"); //TLS Port
	          props.put("mail.smtp.auth", "true"); //enable authentication
	          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS    
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	           message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		public boolean singleEmail(String toEmail,String emailSubject,String emailBody,String emailuser,String pass,String host) {
			boolean flag=false;
			try
			{
				//String host="mail.gyansha.com";  
    	        final String user=emailuser;
    	        final String password=pass; 
    	        

   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host",host);  
   	         props.put("mail.smtp.port", "587"); //TLS Port
   	         props.put("mail.smtp.auth", "true"); //enable authentication
   	         props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	        message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		public boolean sendTeacherEmail(String toEmail,String emailSubject,String classDate,String batch,String email,String pwd,String name,String url) {
			boolean flag=false;
			try
			{
				//String host="mail.gyansha.com";  
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1";  
    	        
    	        String emailBody="";
    	        emailBody="<!DOCTYPE html>"+
"<html class=' js chrome webkit'><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"+
"<title>Easylearning.guru</title>"+
"<meta http-equiv='X-UA-Compatible' content='IE=EDGE'>"+
"<meta name='viewport' content='' id='viewport'>"+
"<link rel='shortcut icon' href='http://student.easylearning.guru/mailerimage/logo2.png'/>"+
"</head>"+
"<body >"+
"<div marginwidth='0' marginheight='0' style='background-color: #ffffff; width: 100%; min-height: 100%; margin: 0; padding: 0; background: none;'>"+
"<table width='100%' border='0' height='200' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td style=' background-repeat: no-repeat; background-color: #58ACFA;'>"+
		"<div>"+
			"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%'>"+
						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='0'></td>"+
							"</tr><tr><td width='100%' valign='middle'>	"+
									"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='left' style='border-collapse: collapse;'><tbody><tr><td height='60' valign='middle' width='100%' style='text-align: center;'>"+
												"<a href='http://easylearning.guru/' style='color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img width='300' src='http://student.easylearning.guru/mailerimage/logo3.png' alt='' border='0' style='width: 292px; min-height: auto;'></a>"+
											"</td>"+
										"</tr></tbody></table><div><span></span></div>"+
									"<table border='0' cellpadding='0' cellspacing='0' align='right' style='border-collapse: collapse;'><tbody><tr><td><div><span></span></div>"+
										"</td></tr></tbody><tbody><tr><td valign='right' align='right'>	"+
											"</td>"+
										"</tr></tbody></table><table width='40' border='0' cellpadding='0' cellspacing='0' align='right' style='border-collapse: collapse;'><tbody><tr><td width='100%' height='0'></td>"+
										"</tr></tbody></table></td>"+
							"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='0'></td>"+
							"</tr><tr><td width='100%' valign='middle'>"+
									"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='text-align: center; border-collapse: collapse;'><tbody><tr><td valign='middle' width='100%' style='text-align: center; font-family: &#39;proxima; font-size: 24px; color: #fff; line-height: 37px; font-weight: normal;'><div style='text-align: center;'><span style='color: #f0fff0;'>"+
"</span></div>"+
"<p style='margin-bottom: 0cm; line-height: 100%; text-align: center; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;font-size: 22px;'><br><span style='color: #f0fff0;  '>Webinar Class Information</span></p>"+
"</td></tr><tr><td width='100%' height='10'></td>"+
										"</tr><tr><td align='center'>"+
												"<table border='0' cellpadding='0' cellspacing='0' align='center'></table></td>"+										
							"</tr></tbody></table></td></tr></tbody></table></td>"+
							"</tr></tbody></table></div></td>"+
				"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'><div>"+
						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+	
						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='30'></td>"+
							"</tr></tbody></table></td>"+
				"</tr></tbody></table></div>"+
		"</td>"+
	"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'>"+
		"<div>"+
			"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+
						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='15'></td>"+
							"</tr></tbody></table><table width='600' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%'>"+
									"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='border-collapse: collapse;'><tbody><tr><td valign='middle' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 21px; color: rgb(48,48,48); line-height: 32px; font-weight: bold;'><span style='font-family: proxima; font-weight: normal;'>Hi "+name+",</span></td>"+
										"</tr><tr><td width='100%' height=''></td>"+
										"</tr><tr><td><table width='100' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100' align='center' height='1' style='border-bottom: 1px  solid  #fff;'></td>"+
													"</tr></tbody></table></td></tr><tr><td width='100%' height='20'></td>"+
										"</tr><tr><td width='100%' height='15'></td>"+
										"</tr><tr><td valign='middle' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 15px; color: #bababa; line-height: 22px;'>"+
"<p style='margin-bottom: 0.35cm; line-height: 115%; direction: ltr; color: rgb(0,0,10); text-align: left; font-family: &quot;Calibri&quot;,serif; font-size: 11pt; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
"<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>Your next webinar class details are given below :</p>&nbsp;"+
"<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px; font-size-22px;'><em><strong>Class Date : </strong><em> <strong>"+classDate+"</strong><br>&nbsp;</p>"+
"<p style='text-align: justify; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Batch : </strong></em> <strong> "+batch+" </strong><br>&nbsp;</p>"+
"<p style='text-align: justify; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Email Id : </strong></em> <strong> "+email+"</strong></p>"+
"<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>&nbsp;</p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Password : </strong></em> <strong> "+pwd+"</strong></p>"+
"<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>&nbsp;</p>"+
"<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>URL : </strong></em> <strong> "+url+"</strong></p>"+
"<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>&nbsp;</p>"+
"</td></tr><tr><td width='100%' height='20'></td>"+					
										"</tr></tbody></table></td>"+
							"</tr></tbody></table></td>"+
				"</tr></tbody></table></div>"+
		"</td>"+
	"</tr></tbody></table>	<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='text-align: center; border-collapse: collapse;'><tbody><tr><td valign='middle' width='100%' style='text-align: center; font-family: &#39;proxima; font-size: 24px; color: #fff; line-height: 37px; font-weight: normal;'><div style='text-align: center;'><span style='color: #f0fff0;'>"+
"</span></div>"+
"<p style='margin-bottom: 0cm; line-height: 100%; text-align: center; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;font-size: 32px;'><br><span style='color: #B43104;  '><b>PLEASE DON'T FORGET TO START RECORDING !</b></span></p>"+
"</td></tr><tr><td width='100%' height='10'></td>"+
										"</tr><tr><td align='center'>"+
												"<table border='0' cellpadding='0' cellspacing='0' align='center'></table></td>"+										
							"</tr></tbody></table>"+
	"</div></div><br></div></div></div>"+
"</div></div></div></div>"+
"</body></html>";
   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	      props.put("mail.smtp.port", "587"); //TLS Port
          props.put("mail.smtp.auth", "true"); //enable authentication
          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	           message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		public boolean sendYouTubeEmail(String toEmail,String name,String course,ArrayList<String> al) {
			boolean flag=false;
			String emailSubject="";
			try
			{
				//String host="mail.gyansha.com";  
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1"; 
    	        
    	        String emailBody="";
    	       String url="";
    	        if(course!=null && course.equalsIgnoreCase("Hadoop"))
    	        {
    	        	
    	        	
    	        	url="http://easylearning.guru/course/big-data-hadoop-training?YoutubeVideo=show";
    	        
					
    	        }
    	        else if(course!=null && course.equalsIgnoreCase("Python"))
    	        {
    	        	
    	        	url="http://easylearning.guru/course/python-django?YoutubeVideo=show";
    	        	
    	        }
    	        else if(course!=null && course.equalsIgnoreCase("MongoDB"))
    	        {
    	        	
    	        	url="http://easylearning.guru/course/mongodb-online-training?YoutubeVideo=show";
    	        	
    	        }
    	        else if(course!=null && course.equalsIgnoreCase("Business Analytics With R"))
    	        {
    	        	
    	        	url="http://easylearning.guru/course/business-analytics-with-r-training?YoutubeVideo=show";
    	        	
    	        }
    	        emailSubject=course+" Live Demo Video | Easylearning.Guru";
    	        emailBody="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>"+emailSubject+"</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content='*Subject Name* Live Demo Video | Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>"+course+" Live Demo Video</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the&nbsp;"+course+" Live Demo Video&nbsp; is given below.</p></td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <div class='btn' style='Margin-bottom: 27px;text-align: center'> <a style='border: 0;border-radius: 4px;display: inline-block;font-size: 14px;font-weight: 700;line-height: 21px;padding: 9px 22px 8px 22px;text-align: center;text-decoration: none;color: #fff;background-color: #444;box-shadow: 0 3px 0 #363636;font-family: sans-serif' href='"+url+"' target='_blank'>"+course+" Live Demo Video</a> </div></td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center></body></html>";
   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	      props.put("mail.smtp.port", "587"); //TLS Port
          props.put("mail.smtp.auth", "true"); //enable authentication
          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	        message.setSentDate(new java.util.Date()); 
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        if(flag)
	           {
	        	  Connection connection=getConnection();
	        	  String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
	        	  PreparedStatement ps7 = connection.prepareStatement(sql2);
	        	  ps7.setString(1, username);
	        	  ps7.setString(2, "SYL");
	        	  ps7.executeUpdate();
	        	  connection.close();
	           }
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		
		public boolean sendBatchDetail(String toEmail,String name,String course,ArrayList<String> al) {
			boolean flag=false;
			String emailSubject="";
			try
			{
				//String host="mail.gyansha.com";  
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1";   
    	        
    	        String emailBody="",batchDetailsData="",last="";
    	     /*   String weekend="",week="",last="";
    	        String wsd="",wstime="",wprice="",sd="",stime="",price="";
	     	    String wduration="",duration="";
	        	String s[]=al.get(0).split("abczxy");
	        	String s2[]=al.get(1).split("abczxy");
	        	wduration=s[1];
	        	wsd=s[2];
	        	wstime=s[3];
	        	wprice=s[4];
	        	duration=s2[1];
	        	sd=s2[2];
	        	stime=s2[3];
	        	price=s2[4];
	        	String url="";*/
    	       int size=al.size();
    	       if(size==0)
    	       {
    	    	   emailBody="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>"+emailSubject+"</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content=' "+course+" Batch Details &amp; Course Features| Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>Batch Details & Course Features</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left;border-top: 1px solid #E6E6E6;'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the <strong style='font-weight: bold'>Batch Details and Features of &nbsp;"+course+" Training</strong> are mentioned below.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 20px'> <strong style='font-weight: bold'>Features:</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 25px'><ul><li style='font-weight: bold; color:#000000;'> Live instructor led classes</li><li style='font-weight: bold;color:#000000;'> Hands-on Sessions</li><li style='font-weight: bold; color:#000000;'> Doubt Clearing Sessions</li><li style='font-weight: bold;color:#000000;'> Lifetime access to study material and class videos</li><li style='font-weight: bold; color:#000000;'> Live project after completion of course</li><li style='font-weight: bold;color:#000000;'> Certification</li><li style='font-weight: bold;color:#000000;'> Life time On-demand support</li></ul></p><p style='Margin-top: 24px;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: -10px'><strong>Batch Details:</strong></p><p style='Margin-top: 24px;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 20px'>Coming soon...</p>";
    	    	   				
					last="</td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'></td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center></body></html>";
    	       }else
    	       {
    	        emailBody="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>"+emailSubject+"</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content=' "+course+" Batch Details &amp; Course Features| Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>Batch Details & Course Features</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left;border-top: 1px solid #E6E6E6;'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the <strong style='font-weight: bold'>Batch Details and Features of &nbsp;"+course+" Training</strong> are mentioned below.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 20px'> <strong style='font-weight: bold'>Features:</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 25px'><ul><li style='font-weight: bold; color:#000000;'> Live instructor led classes</li><li style='font-weight: bold;color:#000000;'> Hands-on Sessions</li><li style='font-weight: bold; color:#000000;'> Doubt Clearing Sessions</li><li style='font-weight: bold;color:#000000;'> Lifetime access to study material and class videos</li><li style='font-weight: bold; color:#000000;'> Live project after completion of course</li><li style='font-weight: bold;color:#000000;'> Certification</li><li style='font-weight: bold;color:#000000;'> Life time On-demand support</li></ul></p><p style='Margin-top: 24px;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 10px'><strong>Batch Details:</strong></p>";
	     					for(int i=0;i<al.size();i++)
	     					{
	     						String data1[]=al.get(i).split("abczxy");
	     						batchDetailsData+="<p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'> <strong style='font-weight: bold'>Batch Type :</strong>&nbsp; "+data1[0]+" <br><strong style='font-weight: bold'>Starting Date :</strong>&nbsp; "+data1[1]+" <br><strong style='font-weight: bold'>Timing :</strong>&nbsp;"+data1[2]+"<br><strong style='font-weight: bold'>Duration :</strong>&nbsp;"+data1[8];
	     						if(data1[9].equals("INR"))
	     						{
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Base Price :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[3]));
	     							if(!(String.format("%.2f", Float.parseFloat(data1[6])).equals("0.00")))
	     								batchDetailsData+="<br><strong style='font-weight: bold'>Discount Price("+String.format("%.2f", Float.parseFloat(data1[6]))+"%) :</strong> - &#8377; "+String.format("%.2f", Float.parseFloat(data1[4]));
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Service Tax ("+String.format("%.2f", Float.parseFloat(data1[7]))+"%) :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[5]))+"<br><strong style='font-weight: bold'>Total Price :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[11]))+"</p>";
	     						}
	     						else if(data1[9].equals("USD"))
	     						{
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Base Price :</strong> $ "+String.format("%.2f", Float.parseFloat(data1[3]));
	     							if(!(String.format("%.2f", Float.parseFloat(data1[6])).equals("0.00")))
	     								batchDetailsData+="<br><strong style='font-weight: bold'>Discount Price("+String.format("%.2f", Float.parseFloat(data1[6]))+"%) :</strong> - $ "+String.format("%.2f", Float.parseFloat(data1[4]));
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Total Price :</strong> $ "+String.format("%.2f", Float.parseFloat(data1[11]))+"</strong></p>";
	     						}
	     					}
	     					String url[]=al.get(0).split("abczxy");
	     					last="</td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <div class='btn' style='Margin-bottom: 27px;text-align: center'> <a style='border: 0;border-radius: 4px;display: inline-block;font-size: 14px;font-weight: 700;line-height: 21px;padding: 9px 22px 8px 22px;text-align: center;text-decoration: none;color: #fff;background-color: #444;box-shadow: 0 3px 0 #363636;font-family: sans-serif' href='http://easylearning.guru"+url[10]+"'>Enroll Now</a></div></td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center></body></html>";
    	       }
    	       if(size==0)
    	       {
    	    	   emailBody=emailBody+last;
    	       }else
    	       {
    	    	   emailBody=emailBody+batchDetailsData+last;   
    	       }
    	        emailSubject=course+" Batch Details & Course Features | Easylearning.Guru";
   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	      props.put("mail.smtp.port", "587"); //TLS Port
          props.put("mail.smtp.auth", "true"); //enable authentication
          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	        message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        if(flag)
	           {
	        	  Connection connection=getConnection();
	        	  String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
	        	  PreparedStatement ps7 = connection.prepareStatement(sql2);
	        	  ps7.setString(1, username);
	        	  ps7.setString(2, "SBD");
	        	  ps7.executeUpdate();
	        	  connection.close();
	           }
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		
		
		public boolean sendSelfEmail(String toEmail,String name,String course,ArrayList<String> al) {
			boolean flag=false;
			String emailSubject="";
			try
			{
			
				//String host="mail.gyansha.com";  
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1";   
    	        
    	        String emailBody="",batchDetailsData="",last="";
    	     /*   String weekend="",week="",last="";
    	        String wsd="",wstime="",wprice="",sd="",stime="",price="";
	     	    String wduration="",duration="";
	        	String s[]=al.get(0).split("abczxy");
	        	String s2[]=al.get(1).split("abczxy");
	        	wduration=s[1];
	        	wsd=s[2];
	        	wstime=s[3];
	        	wprice=s[4];
	        	duration=s2[1];
	        	sd=s2[2];
	        	stime=s2[3];
	        	price=s2[4];
	        	String url="";*/
    	       
    	        emailBody="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>"+emailSubject+"</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content=' "+course+" Batch Details &amp; Course Features| Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>"+course+"</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left;border-top: 1px solid #E6E6E6;'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the <strong style='font-weight: bold'>features of  the self paced course of &nbsp;"+course+" </strong> are mentioned below.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 20px'> <strong style='font-weight: bold'>Features:</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 25px'><ul><li style='font-weight: bold; color:#000000;'> Learning Management System Account with lifetime access</li><li style='font-weight: bold;color:#000000;'> Live project after completion of course</li><li style='font-weight: bold; color:#000000;'> Certification</li><li style='font-weight: bold;color:#000000;'> Life time On-demand support</li></ul></p><p style='Margin-top: 24px;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 10px'><strong>Learning Management System Account Features :-</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 25px'><ul><li style='font-weight: bold; color:#000000;'> Sessions in the form of recorded videos</li><li style='font-weight: bold;color:#000000;'> Books</li><li style='font-weight: bold; color:#000000;'> Assignments</li><li style='font-weight: bold;color:#000000;'> Tutorials</li><li style='font-weight: bold;color:#000000;'> Installation guides</li></ul></p>";
	     					for(int i=0;i<al.size();i++)
	     					{
	     						String data1[]=al.get(i).split("abczxy");
	     						batchDetailsData+="<p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>";
	     						if(data1[5].equals("INR"))
	     						{
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Base Price :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[0]));
	     							if(!(String.format("%.2f", Float.parseFloat(data1[3])).equals("0.00")))
	     								batchDetailsData+="<br><strong style='font-weight: bold'>Discount Price("+String.format("%.2f", Float.parseFloat(data1[3]))+"%) :</strong> - &#8377; "+String.format("%.2f", Float.parseFloat(data1[1]));
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Service Tax ("+String.format("%.2f", Float.parseFloat(data1[4]))+"%) :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[2]))+"<br><strong style='font-weight: bold'>Total Price :</strong> &#8377; "+String.format("%.2f", Float.parseFloat(data1[7]))+"</p>";
	     						}
	     						else if(data1[5].equals("USD"))
	     						{
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Base Price :</strong> $ "+String.format("%.2f", Float.parseFloat(data1[0]));
	     							if(!(String.format("%.2f", Float.parseFloat(data1[3])).equals("0.00")))
	     								batchDetailsData+="<br><strong style='font-weight: bold'>Discount Price("+String.format("%.2f", Float.parseFloat(data1[3]))+"%) :</strong> - $ "+String.format("%.2f", Float.parseFloat(data1[1]));
	     							batchDetailsData+="<br><strong style='font-weight: bold'>Total Price :</strong> $ "+String.format("%.2f", Float.parseFloat(data1[7]))+"</strong></p>";
	     						}
	     					}
	     					String url[]=al.get(0).split("abczxy");
	     					last="</td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <div class='btn' style='Margin-bottom: 27px;text-align: center'> <a style='border: 0;border-radius: 4px;display: inline-block;font-size: 14px;font-weight: 700;line-height: 21px;padding: 9px 22px 8px 22px;text-align: center;text-decoration: none;color: #fff;background-color: #444;box-shadow: 0 3px 0 #363636;font-family: sans-serif' href='http://easylearning.guru"+url[6]+"'>Enroll Now</a></div></td></tr></tbody></table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center></body></html>";
    	        emailBody=emailBody+batchDetailsData+last;
    	        emailSubject=course+" Batch Details & Course Features | Easylearning.Guru";
   	         //Get the session object  
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	         props.put("mail.smtp.port", "587"); //TLS Port
   	         props.put("mail.smtp.auth", "true"); //enable authentication
   	         props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject);  
   	           message.setContent(emailBody,"text/html"); 
   	        message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	        if(flag)
	           {
	        	  Connection connection=getConnection();
	        	  String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
	        	  PreparedStatement ps7 = connection.prepareStatement(sql2);
	        	  ps7.setString(1, username);
	        	  ps7.setString(2, "SBD");
	        	  ps7.executeUpdate();
	        	  connection.close();
	           }
   	        
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		
		public boolean sendCourseSyllabus(String toEmail,String name,String course) {
			boolean flag=false;
			String emailSubject=course+" Course Curriculum | Easylearning.Guru";
			try
			{
				//String host="mail.gyansha.com";  
				final String user="support@easylearning.guru";
		   		final String password="Facebook.com1";   
    	        
    	        String emailBody="<!DOCTYPE html><html><head> <meta content='text/html; charset=utf-8' http-equiv='Content-Type'> <title>"+emailSubject+"</title> <style type='text/css'> body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta content='noindex,nofollow' name='robots'> <meta content='"+course+" Course Curriculum | Easylearning.Guru'></head><body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'> body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <div class='wrapper' style='-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #fff; display: table; min-width: 620px; table-layout: fixed; text-align: center; width: 100%'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody> <tr> <td style='padding: 0;vertical-align: top'> <table align='right' style='border-collapse: collapse;border-spacing: 0'> <tbody> <tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'> &nbsp; </div><div class='title'> "+course+" Course Curriculum </div></td></tr></tbody> </table> <table align='left' style='border-collapse: collapse;border-spacing: 0'> <tbody> <tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div align='center' class='logo-center' id='emb-email-header' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center'> <img alt='Easylearning.Guru' height='72' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' width='278'></div></td></tr></tbody> </table> </td></tr></tbody> </table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody> <tr> <td style='padding: 0;vertical-align: top'></td></tr></tbody> </table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody> <tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'> </td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody> <tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div> <div class='column-top' style='font-size: 50px;line-height: 50px'> &nbsp; </div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody> <tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'> Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'> Thank you for talking to our representative. As requested, the&nbsp;"+course+" Course Curriculum is attached in this mail. </p></td></tr></tbody> </table> <table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody> <tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'> Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody> </table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'> &nbsp; </div></td></tr></tbody> </table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'> </td></tr></tbody> </table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody> <tr> <td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody> </table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody> <tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <div style='text-align: center'> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody> <tr> <td></td></tr></tbody> </table> <div class='address' style='Margin-bottom: 19px'> <strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India </div><div class='permission' style='Margin-bottom: 10px'> <strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose. </div></div></td></tr></tbody> </table> </div></body></html>";
   	         //Get the session object  
    	     
   	         Properties props = new Properties();  
   	         props.put("mail.smtp.host","smtp.gmail.com");  
   	      props.put("mail.smtp.port", "587"); //TLS Port
          props.put("mail.smtp.auth", "true"); //enable authentication
          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
   	           
   	         Session sess = Session.getInstance(props,
   	          new javax.mail.Authenticator() {  
   	            protected PasswordAuthentication getPasswordAuthentication() {  
   	          return new PasswordAuthentication(user,password);  
   	            }  
   	          });  
   	        // String subject = "Request for live demo";
   	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
   	         //Compose the message  
   	           MimeMessage message = new MimeMessage(sess);  
   	           message.setFrom(new InternetAddress(user)); 
   	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
   	           //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   	           message.setSubject(emailSubject); 
   	        BodyPart messageBodyPart = new MimeBodyPart();
	   	       messageBodyPart.setContent(emailBody,"text/html");
	   	       Multipart multipart = new MimeMultipart();
	   	       multipart.addBodyPart(messageBodyPart);
	   	       messageBodyPart = new MimeBodyPart();
	   	       String filename = "/var/lib/tomcat6/files/course-curriculum/"+course+".pdf";
	   	       DataSource source = (DataSource) new FileDataSource(filename);
	   	       messageBodyPart.setDataHandler(new DataHandler(source));
	   	       messageBodyPart.setFileName(course+".pdf");
	   	       multipart.addBodyPart(messageBodyPart);
   	           message.setContent(multipart); 
   	        message.setSentDate(new java.util.Date());
   	          //send the message  
   	           Transport.send(message);  
   	           flag=true;
   	           if(flag)
   	           {
   	        	  Connection connection=getConnection();
   	        	  String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
   	        	  PreparedStatement ps7 = connection.prepareStatement(sql2);
   	        	  ps7.setString(1, username);
   	        	  ps7.setString(2, "SCS");
   	        	  ps7.executeUpdate();
   	        	  connection.close();
   	           }
   	           //System.out.println("EMail Send Successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			return flag;
		}
		public ArrayList<String> getLiveDemoEmail()
		{
	    String query = "select EMAIL from lms_live_demo where E_CORRECT!=? group by email order by qry_date desc";
	    ArrayList<String> al = new ArrayList<String>();
	    PreparedStatement ps=null;
	    Connection connection=null;
	    
	    try {
	        connection = getConnection();
	        ps = connection.prepareStatement(query);
	        ps.setString(1, "n");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	        	 al.add(rs.getString("EMAIL"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return al;
	}	
		public  ArrayList<String> getLiveDemoEmail(String course)
		{
	    String query = "select EMAIL from lms_live_demo where COURSE=? and E_CORRECT!=? group by email order by qry_date desc";
	    ArrayList<String> al = new ArrayList<String>();
	    PreparedStatement ps=null;
	    Connection connection=null;
	    try {
	        connection = getConnection();
	        ps = connection.prepareStatement(query);
	        ps.setString(1, course);
	        ps.setString(2, "n");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
			            al.add(rs.getString("EMAIL"));
			            
			           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return al;
	}
		public  boolean setSingleEmail(String user,String emailSubject,String htmlfile,String course,String email)
		{
	    String query = "insert into seo_customemail values(?,?,?,?,?,?,?)";
	    PreparedStatement ps=null;
	    Connection connection=null;
	    int value=0;
	    try {
	        connection = getConnection();
	        ps = connection.prepareStatement(query);
	        ps.setString(1, null);
	        ps.setString(2, user);
	        ps.setString(3, email);
	        ps.setString(4, emailSubject);
	        ps.setString(5, course);
	        ps.setString(6, htmlfile);
	        ps.setString(7, null);
	        value=ps.executeUpdate();
	        if(value!=0)
	        {
	        	 	String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps3 = connection.prepareStatement(sql2);
					ps3.setString(1, user);
					ps3.setString(2, "SCE");
					ps3.executeUpdate();	
	        }
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    if(value!=0)
	    	return true;
	    else
	    	return false;
	}
		public  boolean setBulkEmail(String user,String emailSubject,String htmlfile,String course,String allemail)
		{
			String query = "insert into seo_bulkemail(USER,SUBJECT,FILE,COURSE) VALUES(?,?,?,?)";
		    PreparedStatement ps=null;
		    Connection connection=null;
		    int value=0;
		    try {
		        connection = getConnection();
		        ps = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
		        ps.setString(1, user);
		        ps.setString(2, emailSubject);
		        ps.setString(3, htmlfile);
		        ps.setString(4, course);
		        value=ps.executeUpdate();
		        if(value!=0)
		        {
		        
		        	int id=0;
		        	ResultSet rs = ps.getGeneratedKeys();
		        	while (rs.next())
		        	    id=rs.getInt(1);
		        	String query2 = "insert into seo_bulkemailmap(ID,EMAILS) VALUES(?,?)";
				   
		        	PreparedStatement ps2 = connection.prepareStatement(query2);
			        ps2.setInt(1, id);
			        ps2.setString(2, allemail);
			        ps2.executeUpdate();
			        String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps3 = connection.prepareStatement(sql2);
					ps3.setString(1, user);
					ps3.setString(2, "SBE");
					ps3.executeUpdate();	
					
		        }
		        	
		        
		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		   finally
		    {
		        try {
		            if(ps != null)
		                ps.close();
		            if(connection != null)
		                connection.close();
		            } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    if(value!=0)
		    	return true;
		    else
		    	return false;
		}
		public String getUserDetail(String email)
		{
	    String query = "select HOST,PASSWORD from admin_emailuser where EMAIL=?";
	   String s="";
	    PreparedStatement ps=null;
	    Connection connection=null;
	    try {
	        connection = getConnection();
	        ps = connection.prepareStatement(query);
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
			            
			            s=rs.getString("HOST")+"/"+rs.getString("PASSWORD");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return s;
	}	
		public  ArrayList<String> getBatchDetail(String course, String country)
		{
	 
	    ArrayList<String> al = new ArrayList<String>();
	    Connection con=null;
	   // String wsd=" ",wstime=" ",wprice=" ",sd=" ",stime=" ",price=" ",duration=" ";
	    try {
			SimpleDateFormat dayName = new SimpleDateFormat("EEE");
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			
			SimpleDateFormat cd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			SimpleDateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			
			SimpleDateFormat cdfus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cdfus.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
			
			SimpleDateFormat cdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			SimpleDateFormat cdf1 = new SimpleDateFormat("hh:mm a"); //date
	        con = getConnection();
	        String sql="select BATCH_TYPE,START_DATE,START_TIME,DURATION,BASE_PRICE,DISCOUNT,S_TAX,TRAINING,URL,DAYS from lms_batch as l left join subjects as s on l.SUBJECT_ID=s.SUBJECT_ID where SUBJECT_NAME=? AND l.DISPLAY=? AND l.VERIFIED=? having case when (Month(SYSDATE()))>9 OR (Month(SYSDATE())<3) OR (( Month(SYSDATE()))=3 AND (DAY(SYSDATE())<8)) then ((START_DATE > DATE(DATE_ADD(sysdate(),INTERVAL 630 MINUTE))) OR (START_DATE = DATE(DATE_ADD(sysdate(),INTERVAL 630 MINUTE)) AND START_TIME > TIME(DATE_ADD(sysdate(),INTERVAL 630 MINUTE)))) else ((START_DATE > DATE(DATE_ADD(sysdate(),INTERVAL 630 MINUTE))) OR (START_DATE = DATE(DATE_ADD(sysdate(),INTERVAL 630 MINUTE)) AND START_TIME > TIME(DATE_ADD(sysdate(),INTERVAL 570 MINUTE)))) end ORDER BY BATCH_TYPE,START_DATE ASC";
     		PreparedStatement ps4=con.prepareStatement(sql);
     		ps4.setString(1, course);
     		ps4.setString(2, "y");
     		ps4.setString(3, "y");
     		ResultSet rs4=ps4.executeQuery();
     		while(rs4.next())
     		{
					java.util.Date  inrDate = cd.parse(rs4.getString("START_DATE")+" "+rs4.getString("START_TIME"));
					java.util.Date  inrToUsDate = cdf.parse(rs4.getString("START_DATE")+" "+rs4.getString("START_TIME"));
					String dateus = cdfus.format(inrToUsDate);
					java.util.Date  usDate = cdf2.parse(dateus);
					
					Calendar calendar = Calendar.getInstance();
					
					// Calculation for Price
					float discount=Float.parseFloat(rs4.getString("DISCOUNT"));
					float price=Float.parseFloat(rs4.getString("BASE_PRICE"));
					float tax=Float.parseFloat(rs4.getString("S_TAX"));
					float p1=0.956f,p2=0.31f;
					
					//Indian price
					float inrFinal=((price*100)/(100-discount));
					float finalDisPrice=0.0f;
					float finalTaxPrice=(price*tax)/100;
					float sendPrice=0.0f;
					
					//us price
					float p3=(1-(tax/100));
					float usPrice=((((inrFinal/p3)/61)-p2)/p1);
					float usDis=(usPrice*discount)/100;
					float basePrice=0.00f;
					String showFor="";
					String sendTime="",sendDate="";
					if(country.equalsIgnoreCase("")||country.equalsIgnoreCase("Hogwarts")||country.equalsIgnoreCase("India")||country.equalsIgnoreCase("IN")||country.equalsIgnoreCase("HO"))
					{//Get INR time and date
						calendar.setTime(inrDate);	
						sendDate=df.format(calendar.getTime());
						sendTime=""+cdf1.format(calendar.getTime());
						calendar.add(Calendar.MINUTE, Integer.parseInt(rs4.getString("DURATION")));
						sendTime+=" - "+cdf1.format(calendar.getTime())+" IST";
						showFor="INR";
						basePrice=inrFinal;
						finalDisPrice=inrFinal-price;
						sendPrice=basePrice-finalDisPrice+finalTaxPrice;
					}
					else
					{
						/*
						 *	US Date and Time 
						 */
						calendar.setTime(usDate);	
						sendDate=df.format(calendar.getTime());
						sendTime=cdf1.format(calendar.getTime());
						calendar.add(Calendar.MINUTE, Integer.parseInt(rs4.getString("DURATION")));
						sendTime+=" - "+cdf1.format(calendar.getTime())+" PST";
						showFor="USD";
						basePrice=usPrice;
						finalDisPrice=usDis;
						sendPrice=usPrice-usDis;
					}
					String duration="";
	     			if(rs4.getString("BATCH_TYPE")!=null && rs4.getString("BATCH_TYPE").equalsIgnoreCase("Weekend"))
	     			{
	     				duration=""+(int)(Math.ceil(Float.parseFloat(rs4.getString("DAYS"))/2))+" Weekends";
						if(country.equalsIgnoreCase("")||country.equalsIgnoreCase("Hogwarts")||country.equalsIgnoreCase("India")||country.equalsIgnoreCase("IN")||country.equalsIgnoreCase("HO"))
						{						
							//if india
							calendar.setTime(inrDate);
							String first=dayName.format(calendar.getTime());
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							duration+=" ("+first+"-"+dayName.format(calendar.getTime())+")";
						}
						else
						{
							//if us
							calendar.setTime(usDate);
							String first=dayName.format(calendar.getTime());
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							duration+=" ("+first+"-"+dayName.format(calendar.getTime())+")";
						}
	     			}
	     			else if(rs4.getString("BATCH_TYPE")!=null && rs4.getString("BATCH_TYPE").equalsIgnoreCase("Weekday"))
					{	
	     				duration=rs4.getString("DAYS")+" Weekdays";
	     				if(country.equals("")||country.equalsIgnoreCase("Hogwarts")||country.equalsIgnoreCase("India")||country.equalsIgnoreCase("IN")||country.equalsIgnoreCase("HO"))
						{						
							calendar.setTime(inrDate);
							String first=dayName.format(calendar.getTime());
							calendar.add(Calendar.DAY_OF_WEEK, 4);
							duration+=" ("+first+"-"+dayName.format(calendar.getTime())+")";
						}
	     				else	
	     				{
	     					calendar.setTime(usDate);
							String first=dayName.format(calendar.getTime());
							calendar.add(Calendar.DAY_OF_WEEK, 4);
							duration+=" ("+first+"-"+dayName.format(calendar.getTime())+")";
						}
					}
	     			
	     			al.add(rs4.getString("BATCH_TYPE")+"abczxy"+sendDate+"abczxy"+sendTime+"abczxy"+basePrice+"abczxy"+finalDisPrice+"abczxy"+finalTaxPrice+"abczxy"+rs4.getString("DISCOUNT")+"abczxy"+rs4.getString("S_TAX")+"abczxy"+duration+"abczxy"+showFor+"abczxy"+rs4.getString("URL")+"abczxy"+sendPrice);
     			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	   finally
	    {
	        try {
	            
	            if(con != null)
	                con.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return al;
	}
		
		
		public  ArrayList<String> selfBatchDetail(String course, String country)
		{
			
	    ArrayList<String> al = new ArrayList<String>();
	    Connection con=null;
	    try {
	    	
	        con = getConnection();
	        String sql="select BASE_PRICE,DISCOUNT,S_TAX,TRAINING,URL from lms_batch as l left join subjects as s on l.SUBJECT_ID=s.SUBJECT_ID where SUBJECT_NAME=? AND l.DISPLAY=? AND l.VERIFIED=? ORDER BY BATCH_TYPE,START_DATE ASC";
     		PreparedStatement ps4=con.prepareStatement(sql);
     		ps4.setString(1, course);
     		ps4.setString(2, "y");
     		ps4.setString(3, "y");
     		ResultSet rs4=ps4.executeQuery();
     		while(rs4.next())
     		{
					
					float discount=Float.parseFloat(rs4.getString("DISCOUNT"));
					float price=Float.parseFloat(rs4.getString("BASE_PRICE"));
					float tax=Float.parseFloat(rs4.getString("S_TAX"));
					float p1=0.956f,p2=0.31f;
					
					//Indian price
					float inrFinal=((price*100)/(100-discount));
					float finalDisPrice=0.0f;
					float finalTaxPrice=(price*tax)/100;
					float sendPrice=0.0f;
					
					//us price
					float p3=(1-(tax/100));
					float usPrice=((((inrFinal/p3)/61)-p2)/p1);
					float usDis=(usPrice*discount)/100;
					float basePrice=0.00f;
					String showFor="";
					String sendTime="",sendDate="";
					if(country.equalsIgnoreCase("")||country.equalsIgnoreCase("Hogwarts")||country.equalsIgnoreCase("India")||country.equalsIgnoreCase("IN")||country.equalsIgnoreCase("HO"))
					{//Get INR time and date
						showFor="INR";
						basePrice=inrFinal;
						finalDisPrice=inrFinal-price;
						sendPrice=basePrice-finalDisPrice+finalTaxPrice;
					}
					else
					{
						/*
						 *	US Date and Time 
						 */
						showFor="USD";
						basePrice=usPrice;
						finalDisPrice=usDis;
						sendPrice=usPrice-usDis;
					}
					
					
				
	     			al.add(basePrice+"abczxy"+finalDisPrice+"abczxy"+finalTaxPrice+"abczxy"+rs4.getString("DISCOUNT")+"abczxy"+rs4.getString("S_TAX")+"abczxy"+showFor+"abczxy"+rs4.getString("URL")+"abczxy"+sendPrice);
     			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	   finally
	    {
	        try {
	            
	            if(con != null)
	                con.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	   
	    return al;
	}
		
		

}
