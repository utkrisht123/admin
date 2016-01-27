/**
 * Author Name					Date							Discription
 * ---------------------------------------------------------------------------------------------------------------
 * Binay Gaur			23-May-2014						Controller Servlet for admin module of LMS
 * ---------------------------------------------------------------------------------------------------------------
 */
package com;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import com.sun.mail.iap.Response;

import java.util.*;
// Extend HttpServlet class

public class AdminServlet extends HttpServlet { 
  @Override
  public void init() throws ServletException{ /* Do required initialization*/ } 
  ArrayList<String> list =null;
  String username=null;
  @Override
  public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
	  doPost(request,response);
  }
  @Override
  public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
      String jMessage = null;
      String jNotification = null;
      response.setContentType("text/html"); 
      PrintWriter out = response.getWriter();
      String jEventName = request.getParameter("jEventName");
      HttpSession session=request.getSession();
 	  String user=(String)session.getAttribute("user");
 	 username=user;
	  //System.out.println("jEventName: " + jEventName);
	  
	  Date d=new Date();
	  Calendar calendar = new GregorianCalendar();
	  calendar.setTime(d);
	  calendar.add(Calendar.MONTH, 1);
	  int l = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	  int m=calendar.get(Calendar.DATE);
	  SimpleDateFormat month_date = new SimpleDateFormat("MMMMMMMMM");
	  String month_name = month_date.format(calendar.getTime());
	
	  String notification=getNotification(month_name,l);
	  String noti[]=notification.split("/");
	  // for notification
	  if(m>=25 && noti.length==2)
	  {
		 jNotification="Schedule demo classes of subject "+noti[0]+" for the next month (add "+noti[1]+" more demo classes).";
		 session.setAttribute("jNotification", jNotification);
	  }
	  else
	  {
		  jNotification="";
		  session.setAttribute("jNotification", jNotification);
	  }
	  
      if(jEventName != null && jEventName.equalsIgnoreCase("AdminLogin")){//add code for AdminLogin
          String username = request.getParameter("username").trim();
          String password = request.getParameter("password");
          list = new ArrayList<String>();
          list.add(0,username);
          list.add(1,password);
          if(isAllowed()){
        	 session.setAttribute("user", username);
        	
        		 Cookie k1=new Cookie("name", username);
        		 k1.setMaxAge(6*24*60*60);
        		 response.addCookie(k1);
        		 String usertype=getAdminDetail(username);
        		 String nature=userNature(username);
        		 session.setAttribute("user_type", usertype);
        		 session.setAttribute("nature", nature);
         	
        	  request.setAttribute("jEventName", jEventName);
        	  request.getRequestDispatcher("AdminMain.jsp").forward(request, response);
          }else{
        	  jMessage = "Username and/or Password  incorrect";
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("jEventName", jEventName);
        	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
        	  }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("logout")){//add code for Logout
    	  		logout(user);
    	  		
    	  		session.invalidate();
    	  		 Cookie k1=new Cookie("name","");
                 k1.setMaxAge(0);
                 response.addCookie(k1);
        	  jMessage = "You are successfully Log Out";
        	  request.setAttribute("jMessage", jMessage);request.setAttribute("jEventName", jEventName);
        	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Lock")){//add code for Lock
	  		String email=request.getParameter("user");
	  		if(session!=null)
	  			session.invalidate();
	  		Cookie k1=new Cookie("name","");
            k1.setMaxAge(0);
            response.addCookie(k1);
           request.setAttribute("jEventName", jEventName);
           request.setAttribute("user", email);
           request.getRequestDispatcher("Lock.jsp").forward(request, response);
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("LockLogin")){//add code for LockLogin
    	  String username = request.getParameter("username").trim();
          String password = request.getParameter("password");
          list = new ArrayList<String>();
          list.add(0,username);
          list.add(1,password);
          if(isLockAllowed()){
        	     Cookie k1=new Cookie("name", username);
     		     k1.setMaxAge(6*24*60*60);
     		     response.addCookie(k1);
        	  	 session.setAttribute("user", username);
        	  	 String usertype=getAdminDetail(username);
           		 String nature=userNature(username);
           		 session.setAttribute("user_type", usertype);
           		 session.setAttribute("nature", nature);
         	
        	    request.setAttribute("jEventName", jEventName);
        	    request.getRequestDispatcher("AdminMain.jsp").forward(request, response);
          }else{
        	  
        	  jMessage = "Username and/or Password  incorrect";
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("user", username);
        	  request.setAttribute("jEventName", jEventName);
        	  request.getRequestDispatcher("Lock.jsp").forward(request, response);
        	  }
    }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Admin_Profile")){//add code for Admin Profile 
     	 
  		
		  
		  String adminUser=request.getParameter("user");
		  String userType=request.getParameter("userType");
		  session.setAttribute("user", adminUser);
		  session.setAttribute("user_type", userType);
		  request.getRequestDispatcher("AdminMain.jsp").forward(request, response);
	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("ChangePassword")){//add code for Change Password
    	  String cpassword = request.getParameter("cpassword"); 
    	  String password = request.getParameter("rpassword");
    	  if(checkPassword(user,cpassword))
    	  {
	  		if(changePassword(user,password))
	  			jMessage = "Password has been changed successfully.";
	  		else
	  			jMessage = "Password has not changed.. Please contact to technical department.";
    	  }
    	  else
    	  {
    		  jMessage = "Password has not changed.Please enter correct current password.";
    	  }
           request.setAttribute("jMessage", jMessage);
           request.setAttribute("jEventName", jEventName);
           request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("MyProfile")){//add code for My Profile
    	  	String usertype=getAdminDetail(user);
           request.setAttribute("user_type", usertype);
           request.setAttribute("jEventName", jEventName);
           request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Create")){//add code for Create Teacher
    	  String teacherId = request.getParameter("t_id"); 
    	  String teacherName = request.getParameter("t_name");
    	 
          String fatherName = request.getParameter("f_name");
          String dob= request.getParameter("dob");
          String sex = request.getParameter("sex");
          String address = request.getParameter("address");
          String email = request.getParameter("email");
          String mobile= request.getParameter("mobile");
          
          list = new ArrayList<String>();
          list.add(0, teacherId);
          list.add(1, teacherName);
          list.add(2, user);
          list.add(3, fatherName);
          list.add(4, dob);
          list.add(5, sex);
          list.add(6, address);
          list.add(7, email);
          list.add(8, mobile);
          if(checkTeacherUser(user)){
        	  jMessage = "Provided Teacher User name is already exist...please choose another user name";  request.setAttribute("jEventName", jEventName); request.setAttribute("jMessage", jMessage)
        	  ;request.getRequestDispatcher("T_Create.jsp").forward(request, response); }else{ if(sendTeacher()){ jMessage = "New Teacher Successfully created"; request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);request.setAttribute("teacherId", teacherId); request.getRequestDispatcher("Success.jsp").forward(request, response);}
          }
      }else if(jEventName != null && jEventName.equalsIgnoreCase("S_Create")){//add code for Create Student
    	  
    	  String studentName = request.getParameter("fullname");
          String email = request.getParameter("email");
          String mobile= request.getParameter("phone");
          String password = request.getParameter("password");
          
          list = new ArrayList<String>();
          list.add(0, password);
          list.add(1, studentName);
          list.add(2, email);
          list.add(3, mobile);
          if(checkStudentUser(email)){
        	  jMessage = "Provided Student Email Id is already exist...please enter another email id";  request.setAttribute("jEventName", jEventName); request.setAttribute("jMessage", jMessage)
        	  ;request.getRequestDispatcher("S_Create.jsp").forward(request, response); }else{ if(sendStudent()){ jMessage = "Student created successfully"; request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);  request.getRequestDispatcher("S_Create.jsp").forward(request, response);}
          }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("R_Student")){//add code for Create student from live demo
     	  
    	  String studentName = request.getParameter("name");
          String email = request.getParameter("email");
          String mobile= request.getParameter("phone");
          String password = "12345";
          
          list = new ArrayList<String>();
          list.add(0, password);
          list.add(1, studentName);
          list.add(2, email);
          list.add(3, mobile);
          if(checkStudentUser(email)){
        	  jMessage = "Provided student email id is already register.";  
        	  request.setAttribute("jEventName", jEventName); 
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("email", email);
        	  request.getRequestDispatcher("Success.jsp").forward(request, response);
        	  }
          else
          { 
        	  if(sendStudent())
        	  { 
        		  new SendEmail().sendRegisterStudent(email, studentName,password);
        		  jMessage = "Student "+studentName+" has been created successfully.";
        		  request.setAttribute("jEventName", jEventName);
        		  request.setAttribute("jMessage", jMessage);
        		  request.setAttribute("email", email);
        		  request.getRequestDispatcher("Success.jsp").forward(request, response);
        	  }
          }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Query")){//add code for Teacher Query
          String teacherId = request.getParameter("teacher_id");
          String teacherQuery = request.getParameter("teacher_query"); 
          //get earlier queries from the same teacher id starts
          ArrayList<String> queryArrList = new ArrayList<String>();
    	  ArrayList<String> jTeacherInfo = new ArrayList<String>();
          queryArrList = getQueries(teacherId);
          jTeacherInfo = getTeacherById(teacherId);
          //get earlier queries from the same teacher id starts
          jMessage = "Teacher Id you provided is not available"; 
          //System.out.println("teacherId:  "+teacherId); 
          if(checkTeacherId(teacherId)){
              list =  new ArrayList<String>();
              list.add(0, teacherId);
              list.add(1, teacherQuery);   
              //System.out.println("list:  "+list); 
              if(sendQuery()){
            	  request.setAttribute("jTeacherInfo", jTeacherInfo);request.setAttribute("jQueryArrList", queryArrList);request.setAttribute("jEventName", jEventName);request.setAttribute(
            			  "jTeacherId", teacherId); request.getRequestDispatcher("Acknowledge.jsp").forward(request, response) ;}
          }else{request.setAttribute("jEventName", jEventName);request.setAttribute("jMessage", jMessage);request.getRequestDispatcher("T_Query.jsp").forward(request, response);}
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("V_T_Logs")){//add code for Teacher Logs
          String teacherId = request.getParameter("t_id");
       
          //get earlier queries from the same teacher id starts
         
          //get earlier queries from the same teacher id starts
          jMessage = "Teacher Id you provided is not available"; 
          //System.out.println("teacherId:  "+teacherId); 
              list =  new ArrayList<String>();
              list.add(0, teacherId);
              //System.out.println("list:  "+list); 
              ArrayList<String> chapterAL = new ArrayList<String>();
              ArrayList<String> testAL = new ArrayList<String>();
        	  ArrayList<String> assignmentAL = new ArrayList<String>();
        	  ArrayList<String> videoAL = new ArrayList<String>();
        	  ArrayList<String> bookAL = new ArrayList<String>();
        	  chapterAL=getLogChapters();
        	  testAL=getLogTests();
        	  assignmentAL=getLogAssignments();
        	  videoAL=getLogVideos();
        	  bookAL=getLogBooks();
             
            	  request.setAttribute("chapterAL", chapterAL); request.setAttribute("testAL", testAL); request.setAttribute("assignmentAL", assignmentAL); request.setAttribute("videoAL", videoAL); request.setAttribute("bookAL", bookAL);request.setAttribute("jEventName", jEventName);request.setAttribute(
            			  "jTeacherId", teacherId); request.getRequestDispatcher("V_T_log.jsp").forward(request, response) ;
          
      }else if(jEventName != null && jEventName.equalsIgnoreCase("S_Allot")){//code for Allot student batch
    	  
    	  String studentId = request.getParameter("student_id"); 
    	  String batch_id = request.getParameter("batch");
    	  String subjectId = request.getParameter("subject_id");
    	  String name="",email="";
          list = new ArrayList<String>();
          list.add(0, studentId);
          list.add(1, subjectId);
          list.add(2, batch_id);
         
          if(checkSessionId())
          {
        	  jMessage = "Provided Student Id: "+studentId+" is already alloted for this batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName); request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("S_Allot.jsp").forward(request, response);  
          }
          else
          {
        	  if(sendStudentAllot())
        	  {
        		  ArrayList<String> al=getStudentEmailName(studentId);
                	if(!al.isEmpty())
                	{
                		email=al.get(0);
                		name=al.get(1);
                	}
                	new SendEmail().sendLMSUrl(email, name);
        		   jMessage = "Student has been successfully alloted";
        		   request.setAttribute("jEventName", jEventName);
        		   request.setAttribute("jMessage", jMessage);
        		   request.getRequestDispatcher("S_Allot.jsp").forward(request, response); 
        	  }
        	  else
        	  {
        		  jMessage = "Student has not alloted... Please contact to admin";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("S_Allot.jsp").forward(request, response);
        	  }
          }
      }else if(jEventName != null && jEventName.equalsIgnoreCase("S_C_Password")){//add code for Student change Password
    	  String s_id = request.getParameter("s_id");
    	  String password = request.getParameter("password");
    	  String studentName = request.getParameter("s_name"); 
          if(studentPassword(s_id,password))
          {
        	  jMessage = "Password has changed successfully  of student name "+studentName;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_Student.jsp").forward(request, response);   
          }
          else
          {
        	  jMessage = "Password has not changed of student id "+s_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_Student.jsp").forward(request, response); 
          }
      }else if(jEventName != null && jEventName.equalsIgnoreCase("S_Update")){//add code for Student Update
    	  String studentId = request.getParameter("s_id"); 
    	  String studentName = request.getParameter("s_name");
          String email = request.getParameter("email_id");
          String mobile= request.getParameter("mobile");
          String subjectId = request.getParameter("subject_id"); 
    	  String subjectName = request.getParameter("subject_name");
    	  String batchId = request.getParameter("batch_id");
    	  String sessionId = request.getParameter("batch_id"); 
    	  String o_subjectId = request.getParameter("o_subject_id"); 
          list = new ArrayList<String>();
          list.add(0, studentId);
          list.add(1, studentName);
          list.add(2, email);
          list.add(3, mobile);
          list.add(4, subjectId);
          list.add(5, subjectName);
          list.add(6, batchId);
          list.add(7, sessionId);
          list.add(8, o_subjectId);
        if(updateStudent()){ jMessage = "Student "+studentName+" has been Successfully Update"; request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Student.jsp").forward(request, response);}
        else
        {
        	jMessage = "Student "+studentName+" has not Updated ... please internal problem"; request.setAttribute("jEventName", jEventName);
      	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Student.jsp").forward(request, response);
        }
          
      }else if(jEventName != null && jEventName.equalsIgnoreCase("S_Delete")){//add code for Student Delete
    	  
    	  String studentId = request.getParameter("s_id"); 
    	  String studentName = request.getParameter("s_name"); 
    	 
          if(deleteStudent(studentId)){ jMessage = "Student "+studentName+" has been successfully Deleted"; request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Student.jsp").forward(request, response);}
    	  
      }
      
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_C_Password")){//add code for Teacher Change Password
    	  String t_id = request.getParameter("t_id");
    	  String password = request.getParameter("password");
    	  String teacherName = request.getParameter("t_name"); 
          if(teacherPassword(t_id,password))
          {
        	  jMessage = "Password has changed successfully  of Teacher "+teacherName;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_Teacher.jsp").forward(request, response);   
          }
          else
          {
        	  jMessage = "Password has not changed of Teacher "+teacherName;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_Teacher.jsp").forward(request, response); 
          }
      }else if(jEventName != null && jEventName.equalsIgnoreCase("T_Update")){//add code for Teacher Update
    	  String teacherId = request.getParameter("t_id"); 
    	  String teacherName = request.getParameter("t_name");
    	  
          String fatherName = request.getParameter("f_name");
          String dob= request.getParameter("dob");
          String sex = request.getParameter("sex");
          String address = request.getParameter("address");
          String email = request.getParameter("email_id");
          String mobile= request.getParameter("mobile");
         
          list = new ArrayList<String>();
          list.add(0, teacherId);
          list.add(1, teacherName);
          list.add(2, user);
          list.add(3, fatherName);
          list.add(4, dob);
          list.add(5, sex);
          list.add(6, address);
          list.add(7, email);
          list.add(8, mobile);
        if(updateTeacher()){ jMessage = "Teacher "+teacherName+" has been successfully Updated"; request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Teacher.jsp").forward(request, response);}
        else
        {
        	jMessage = "Teacher "+teacherName+" has not Updated ... please check the format of dob"; request.setAttribute("jEventName", jEventName);
      	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Student.jsp").forward(request, response);
        }
          
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_T_Batch")){//add code for Update Teacher Batch
    	  String teacherId = request.getParameter("t_id"); 
    	  String subjectId = request.getParameter("subject_id"); 
    	  String batchId = request.getParameter("batch_id");
    	  String o_batchId = request.getParameter("o_batch_id"); 
    	  list = new ArrayList<String>();
    	  list.add(0, teacherId);
          list.add(1, subjectId);
          list.add(2, batchId);
          list.add(3, o_batchId);
          if(checkTeacherBatch())
          {
        	  jMessage = "Batch "+batchId+" is already alloted";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_Teacher.jsp").forward(request, response); 
          }
          else
          {
          if(updateTeacherBatch()){ 
        	  jMessage = "Batch has been successfully Changed"; request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Teacher.jsp").forward(request, response);
    	  }
          }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Delete")){//add code for Teacher Delete
    	  
    	  String teacherId = request.getParameter("t_id"); 
    	  String teacherName = request.getParameter("t_name"); 
    	 
          if(deleteTeacher(teacherId)){ jMessage = "Teacher "+teacherName+" has been Successfully Deleted"; request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("View_Teacher.jsp").forward(request, response);}
    	  
      }
      
     
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Allot")){//add code for Allot teacher batch
    	  String subject_id = request.getParameter("subject_id"); 
    	  String t_id = request.getParameter("teacher_id");
    	  String batch_id = request.getParameter("batch");
    	  list = new ArrayList<String>();
          list.add(0, subject_id);
          list.add(1, t_id);
          list.add(2, batch_id);
          if(checkTeacherBatch())
          {
        	  jMessage = "Teacher id "+t_id+" is already exist for batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("T_Allot.jsp").forward(request, response); 
          }
          else
          {
        	  if(allotBatch())
        	  {
        	  jMessage = "Teacher successfully alloted for batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("T_Allot.jsp").forward(request, response);
        	  } 
        	  else
        	  {
        		  jMessage = "Teacher is not alloted due to internal problem";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("T_Allot.jsp").forward(request, response);
        	  }
        	  
        	  
          }
    	  
      }else if(jEventName != null && jEventName.equalsIgnoreCase("C_Session")){//add code for Create Session
    	  String subjectId = request.getParameter("subject_id"); 
    	  String batchId = request.getParameter("batch");
    	  String module = request.getParameter("module");
    	  String sessionDate = request.getParameter("date");
    	  String email = request.getParameter("email");
    	  String meeting_id = request.getParameter("meeting_id");
    	  String url = request.getParameter("url");
    	  int tId = checkTeacher(batchId, subjectId);
    	  list = new ArrayList<String>();
          list.add(0, user);
          list.add(1, ""+tId);
          list.add(2, subjectId);
          list.add(3, batchId);
          list.add(4, module);
          list.add(5, sessionDate);
          list.add(6, email);
          list.add(7, meeting_id);
          list.add(8, url);
    	  if(tId==0)
    	  {
    		  ArrayList<String> al=new ArrayList<String>();
    		   al= getWebinarScheduleBatch(batchId,subjectId);
    		   	request.setAttribute("subject", subjectId);
         	  	request.setAttribute("batch", batchId);
         	  	request.setAttribute("data", al);
         	  	
    		  jMessage = "Teacher has not alloted for this batch "+batchId;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    	  
    		  if(checkSessionDate(tId,batchId,sessionDate))
    		  {
    			  ArrayList<String> al=new ArrayList<String>();
       		   al= getWebinarScheduleBatch(batchId,subjectId);
       		   	request.setAttribute("subject", subjectId);
            	  	request.setAttribute("batch", batchId);
            	  	request.setAttribute("data", al);
        	     jMessage = "Provided session date "+sessionDate+" already schedule for batch "+batchId;
        	     request.setAttribute("jEventName", jEventName);
        	     request.setAttribute("jMessage", jMessage);
        	     request.getRequestDispatcher("SC_Batch.jsp").forward(request, response); 
    		  }
    		  else
    		  {
    			  if(scheduleBatch())
    			  {
    				  jMessage = "Session created successfully ";
    				  ArrayList<String> al=new ArrayList<String>();
    		    	  al=getWebinarScheduleBatch(batchId,subjectId);
    		    		request.setAttribute("subject", subjectId);
    	         	  	request.setAttribute("batch", batchId);
    		    	  request.setAttribute("data", al);
    				  request.setAttribute("jEventName", jEventName);
    				  request.setAttribute("jMessage", jMessage);
    				  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    			  } 
    			  else
    			  {
    				  ArrayList<String> al=new ArrayList<String>();
    	    		   al= getWebinarScheduleBatch(batchId,subjectId);
    	    		   	request.setAttribute("subject", subjectId);
    	         	  	request.setAttribute("batch", batchId);
    	         	  	request.setAttribute("data", al);
    				  jMessage = "Session has not created ... please contact to admin";
    				  request.setAttribute("jEventName", jEventName);
    				  request.setAttribute("jMessage", jMessage);
    				  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    			  }
    		  }
    	  }
    	  
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Edit_ScheduleBatch")){//add code for Edit Schedule Batch
    	  String subjectId = request.getParameter("subject_id"); 
    	  String batchId = request.getParameter("batch");
    	  String module = request.getParameter("module");
    	  String sessionDate = request.getParameter("date");
    	  String email = request.getParameter("email");
    	  String meeting_id = request.getParameter("meeting_id");
    	  String url = request.getParameter("url");
    	  String sessionID = request.getParameter("id");
    	  	request.setAttribute("subject", subjectId);
	  		request.setAttribute("batch", batchId);
	  		request.setAttribute("sessionID", sessionID);
	  		request.setAttribute("module", module);
	  		request.setAttribute("sessionDate", sessionDate);
	  		request.setAttribute("email", email);
	  		request.setAttribute("meeting_id", meeting_id);
	  		request.setAttribute("url", url);
	  		request.setAttribute("jEventName", jEventName);
	  		request.getRequestDispatcher("USC_Batch.jsp").forward(request, response); 
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Update_ScheduleBatch")){//add code for Update Schedule Batch
    	  String subjectId = request.getParameter("subject_id"); 
    	  String batchId = request.getParameter("batch");
    	  String module = request.getParameter("module");
    	  String sessionDate = request.getParameter("date");
    	  String email = request.getParameter("email");
    	  String meeting_id = request.getParameter("meeting_id");
    	  String url = request.getParameter("url");
    	  String sessionID = request.getParameter("id");
    	  list = new ArrayList<String>();
          list.add(0, user);
          list.add(1, sessionID);
          list.add(2, subjectId);
          list.add(3, batchId);
          list.add(4, module);
          list.add(5, sessionDate);
          list.add(6, email);
          list.add(7, meeting_id);
          list.add(8, url);
    
    		  if(checkSessionDate(batchId,sessionDate))
    		  {
            	  	jMessage = "Provided session date "+sessionDate+" already schedule for batch "+batchId;
            	  	
    		  }
    		  else
    		  {
    			  if(updateScheduleBatch())
    			  {
    				   jMessage = "Schedule batch update successfully ";
    			  } 
    			  else
    			  {
    				   jMessage = "Schedule batch has not updated ... please contact to admin";
    				  
    			  }
    		  }
    		  	request.setAttribute("subject", subjectId);
      	  		request.setAttribute("batch", batchId);
      	  		request.setAttribute("sessionID", sessionID);
      	  		request.setAttribute("module", module);
      	  		request.setAttribute("sessionDate", sessionDate);
      	  		request.setAttribute("email", email);
      	  		request.setAttribute("meeting_id", meeting_id);
      	  		request.setAttribute("url", url);
      	  		request.setAttribute("jEventName", jEventName);
      	  		request.setAttribute("jMessage", jMessage);
      	  		request.getRequestDispatcher("USC_Batch.jsp").forward(request, response); 
    	  
    	  
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Delete_ScheduleBatch")){//add code for Delete Schedule Batch
    	
    	  String sessionID = request.getParameter("id");
    	  String subjectId = request.getParameter("subject_id"); 
    	  String batchId = request.getParameter("batch");
    			  if(deleteScheduleBatch(sessionID))
    			  {
    				   jMessage = "Schedule batch has been deleted successfully. ";
    			  } 
    			  else
    			  {
    				   jMessage = "Schedule batch has not deleted ... please contact to admin";
    				  
    			  }
    			  ArrayList<String> al=new ArrayList<String>();
       		   	al= getWebinarScheduleBatch(batchId,subjectId);
       		   	request.setAttribute("data", al);
    		  	request.setAttribute("subject", subjectId);
      	  		request.setAttribute("batch", batchId);
      	  		request.setAttribute("sessionID", sessionID);
      	  		request.setAttribute("jEventName", jEventName);
      	  		request.setAttribute("jMessage", jMessage);
      	  		request.getRequestDispatcher("SC_Batch.jsp").forward(request, response); 
    	  
    	  
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_D_Session")){//add code for Create demo Session
    	  String subject_id = request.getParameter("subject_id"); 
    	  String t_id = request.getParameter("t_id");
    	  String batch_id = request.getParameter("batch_id");
    	  String date= request.getParameter("date");
    	  String module= "Demo Class";
    	  list = new ArrayList<String>();
          list.add(0, subject_id);
          list.add(1, t_id);
          list.add(2, batch_id);  
          list.add(3, date); 
          list.add(4, module); 
          if(checkSession())
          {
        	  jMessage = "Teacher id "+t_id+" is already exist for this session of demo batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("C_D_Session.jsp").forward(request, response); 
          }
          else
          {
          if(createSession())
    	  {
    	  jMessage = "Teacher id  "+t_id+"  successfully created session of batch  "+batch_id;
    	 
    	  request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage);
    	  request.getRequestDispatcher("Success.jsp").forward(request, response);
    	  } 
    	  else
    	  {
    		  jMessage = "Session is not created ... please check the format of date";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("C_D_Session.jsp").forward(request, response);
    	  }
          }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_Batch")){//add code for Create Batch
      	 
    	  String subject_id = request.getParameter("subject_id"); 
    	  String datetime = request.getParameter("startingdt");
    	  String t[]=datetime.split("-");
    	  String s_time =t[1].trim(),s_date=t[0].trim();
    	  String duration = request.getParameter("duration");
    	 
          String days = request.getParameter("days");
          String t_hours= request.getParameter("totalhr");
          String batch_type = request.getParameter("btype");
          String display = request.getParameter("display");
          String b_price = request.getParameter("price");
          String price = request.getParameter("bprice");
          String discount = request.getParameter("discount");
          String f_price = request.getParameter("totalinr");
          String pType = "EXCL";
          String sTax = request.getParameter("tax");
          String tax = request.getParameter("taxinr");
         // String username = request.getParameter("username");
         // String password = request.getParameter("password");
         
          list = new ArrayList<String>();
          list.add(0, subject_id);
          list.add(1, s_date);
          list.add(2, s_time);
          list.add(3, duration);
          list.add(4, days);
          list.add(5, t_hours);
          list.add(6, batch_type);
          list.add(7, display);
          list.add(8, b_price);
          list.add(9, discount);
          list.add(10, f_price);
          list.add(11, sTax);
          list.add(12, tax);
          list.add(13, pType);
          
         // if(checkSuperAdmin(username,password))
          //{
        	  if(checkBatch())
        	  {
        		  jMessage = "Provided batch is already exist of date "+s_date+" , time "+s_time+" and batch type="+batch_type+"... please choose another date and time";
        		  request.setAttribute("jEventName", jEventName);
        		  request.setAttribute("jMessage", jMessage);
        		  request.setAttribute("dt", datetime);
        		  request.setAttribute("btype", batch_type);
        		  request.setAttribute("duration", duration);
        		  request.setAttribute("days", days);
        		  request.setAttribute("thr", t_hours);
        		  request.setAttribute("bprice", price);
        		  request.setAttribute("discount", discount);
        		  request.setAttribute("fprice", f_price);
        		  request.setAttribute("subject_id", subject_id);
        		  request.getRequestDispatcher("C_Batch.jsp").forward(request, response); 
        	  }
        	  else
        	  {
        		  if(createBatch())
        		  {
        			  jMessage = "Batch successfully created and send for verify from super admin";
        			  request.setAttribute("jEventName", jEventName);
        			  request.setAttribute("jMessage", jMessage);
        			  request.setAttribute("dt", datetime);
        			  request.setAttribute("btype", batch_type);
        			  request.setAttribute("display", display);
        			  request.setAttribute("duration", duration);
        			  request.setAttribute("days", days);
        			  request.setAttribute("thr", t_hours);
        			  request.setAttribute("bprice", price);
        			  request.setAttribute("discount", discount);
        			  request.setAttribute("fprice", f_price);
        			  request.setAttribute("subject_id", subject_id);
        			  request.getRequestDispatcher("Success.jsp").forward(request, response);
        		  } 
        		  else
        		  {
        			  request.setAttribute("dt", datetime);
        			  request.setAttribute("btype", batch_type);
        			  request.setAttribute("display", display);
        			  request.setAttribute("duration", duration);
        			  request.setAttribute("days", days);
        			  request.setAttribute("thr", t_hours);
        			  request.setAttribute("bprice", price);
        			  request.setAttribute("discount", discount);
        			  request.setAttribute("fprice", f_price);
        			  request.setAttribute("subject_id", subject_id);
        			  jMessage = "Batch is not created ... please contact to admin";
        			  request.setAttribute("jEventName", jEventName);
        			  request.setAttribute("jMessage", jMessage);
        			  request.getRequestDispatcher("C_Batch.jsp").forward(request, response);
        		  }
        	  }
       /*   }
          else
          {
        	  request.setAttribute("dt", datetime);
			  request.setAttribute("btype", batch_type);
			  request.setAttribute("duration", duration);
			  request.setAttribute("days", days);
			  request.setAttribute("thr", t_hours);
			  request.setAttribute("bprice", price);
			  request.setAttribute("discount", discount);
			  request.setAttribute("fprice", f_price);
			  request.setAttribute("subject_id", subject_id);
			  jMessage = "Provided super admin username and/or incorrect.";
			  request.setAttribute("jEventName", jEventName);
			  request.setAttribute("jMessage", jMessage);
			  request.getRequestDispatcher("C_Batch.jsp").forward(request, response);
          }*/
      }
      
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("E_Batch")){//add code for Edit Batch
    	  
    	  String subject_id = request.getParameter("subject_id"); 
    	  String batch_id = request.getParameter("batch_id"); 
    	  ArrayList<String> al = new ArrayList<String>();
    	  al=getBatchDetail(batch_id, subject_id);
    	  String dt="",btype="",duration="",days="",thr="",bprice="",discount="",fprice="",tax="",stax="",display="";
    		if(!al.isEmpty())
    		{
    			dt=al.get(0)+"-"+al.get(1);
    			btype=al.get(2);
    			display=al.get(3);
    			duration=al.get(4);
    			days=al.get(5);
    			thr=al.get(6);
    			bprice=al.get(7);
    			discount=al.get(8);
    			fprice=al.get(9);
    			stax=al.get(10);
    			tax=al.get(11);
    		}
    		//System.out.println("base price="+bprice);
    		 request.setAttribute("dt", dt);
    		 request.setAttribute("btype", btype);
    		 request.setAttribute("display", display);
    		 request.setAttribute("duration", duration);
    		 request.setAttribute("days", days);
    		 request.setAttribute("thr", thr);
    		 request.setAttribute("bprice", bprice);
    		 request.setAttribute("discount", discount);
    		 request.setAttribute("fprice", fprice);
    		 request.setAttribute("stax", stax);
    		 request.setAttribute("tax", tax);
    		 request.setAttribute("subject_id", subject_id);
    		 request.setAttribute("batch_id", batch_id);
    		 request.setAttribute("jEventName", jEventName);
      	 
    		 request.getRequestDispatcher("U_Batch.jsp").forward(request, response); 
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_Batch")){//add code for Update Batch
    	   String batchId=request.getParameter("batch_id");
    	  String subject_id = request.getParameter("subject_id"); 
    	  String datetime = request.getParameter("startingdt");
    	  String t[]=datetime.split("-");
    	  String s_time =t[1].trim(),s_date=t[0].trim();
    	  String duration = request.getParameter("duration");
    	 
          String days = request.getParameter("days");
          String t_hours= request.getParameter("totalhr");
          String batch_type = request.getParameter("btype");
          String display =request.getParameter("display");
          String price = request.getParameter("bprice");
          String b_price = request.getParameter("price");
         
          String discount = request.getParameter("discount");
          String f_price = request.getParameter("totalinr");
          String pType = "EXCL";
          String sTax = request.getParameter("tax");
          String tax = request.getParameter("taxinr");
          
         
          list = new ArrayList<String>();
          list.add(0, batchId);
          list.add(1, s_date);
          list.add(2, s_time);
          list.add(3, duration);
          list.add(4, days);
          list.add(5, t_hours);
          list.add(6, batch_type);
          list.add(7, display);
          list.add(8, b_price);
          list.add(9, discount);
          list.add(10, f_price);
          list.add(11, sTax);
          list.add(12, tax);
          list.add(13, pType);
          if(checkBatch(batchId))
          {
        	  jMessage = "Provided batch is already exist in start date "+s_date+" ,start time "+s_time+" and batch type "+batch_type;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("dt", datetime);
     		 request.setAttribute("btype", batch_type);
     		 request.setAttribute("display", display);
     		 request.setAttribute("duration", duration);
     		 request.setAttribute("days", days);
     		 request.setAttribute("thr", t_hours);
     		 request.setAttribute("bprice", price);
     		 request.setAttribute("discount", discount);
     		 request.setAttribute("fprice", f_price);
     		 request.setAttribute("stax", sTax);
     		 request.setAttribute("tax", tax);
     		 request.setAttribute("subject_id", subject_id);
     		 request.setAttribute("batch_id", batchId);
        	  request.getRequestDispatcher("U_Batch.jsp").forward(request, response); 
        	  
          }
          else
          {
        	  if(updateBatch())
        	  {
        		  
        		  jMessage = "Batch Updated successfully batch and send For verify from super admin";
        		  request.setAttribute("jEventName", jEventName);
        		  request.setAttribute("jMessage", jMessage);
        		  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.setAttribute("dt", datetime);
         		  request.setAttribute("btype", batch_type);
         		 request.setAttribute("display", display);
         		  request.setAttribute("duration", duration);
         		  request.setAttribute("days", days);
         		  request.setAttribute("thr", t_hours);
         		  request.setAttribute("bprice", price);
         		  request.setAttribute("discount", discount);
         		  request.setAttribute("fprice", f_price);
         		  request.setAttribute("batch_id", batchId);
        		  request.getRequestDispatcher("Success.jsp").forward(request, response); 
        	  }
        	  else
        	  {
        		  jMessage = "Batch is not updated ... please contact to admin";
        		  request.setAttribute("jEventName", jEventName);
        		  request.setAttribute("jMessage", jMessage);
        		  request.setAttribute("dt", datetime);
          		 request.setAttribute("btype", batch_type);
          		 request.setAttribute("display", display);
          		 request.setAttribute("duration", duration);
          		 request.setAttribute("days", days);
          		 request.setAttribute("thr", t_hours);
          		 request.setAttribute("bprice", price);
          		 request.setAttribute("discount", discount);
          		 request.setAttribute("fprice", f_price);
          		 request.setAttribute("stax", sTax);
          		 request.setAttribute("tax", tax);
          		 request.setAttribute("subject_id", subject_id);
          		 request.setAttribute("batch_id", batchId);
        		  request.getRequestDispatcher("U_Batch.jsp").forward(request, response);  
        	  }
          }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_Batch")){//add code for Delete Batch
    	  
    	  String batch_id = request.getParameter("batch_id"); 
    	  list = new ArrayList<String>();
          list.add(0, batch_id);
          if(deleteBatch())
          {
        	  ArrayList<String> al = new ArrayList<String>();
        	  al=getBatch();
        	  request.setAttribute("data", al);
        	  jMessage = "Batch Deleted successfully of batch  "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Batch.jsp").forward(request, response); 
          }
          else
          {
        	  ArrayList<String> al = new ArrayList<String>();
        	  al=getBatch();
        	  request.setAttribute("data", al);
        	  jMessage = "Batch is not deleted due to internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Batch.jsp").forward(request, response);  
          }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("G_Batch")){//add code for get batch 
    	  
	  		list = new ArrayList<String>();
	  		list=getBatch();
	  		request.setAttribute("data", list);
	  		request.setAttribute("jEventName", jEventName);
	  		request.getRequestDispatcher("V_Batch.jsp").forward(request, response);
	  
	 
}
 else if(jEventName != null && jEventName.equalsIgnoreCase("G_BatchDetail")){//add code for Get Batch Detail from live demo
    	  
    	  	String subject=request.getParameter("subject");
    	  	String id=request.getParameter("id");
    	  	String lpid=request.getParameter("lp_id");
    	  	String cookie_id=request.getParameter("cookie_id");
    	  	String jevent=request.getParameter("jevent");
    	  	String interest=request.getParameter("interest");
    	  	if(interest==null){
    	  		interest="0";
    	  	}
    	  	list=getBatchDetail(subject);
    	  	request.setAttribute("data", list);
    	  	request.setAttribute("id",id);
    	  	request.setAttribute("subject",subject);
    		request.setAttribute("lp_id",lpid);
    		request.setAttribute("jevent",jevent);
    		request.setAttribute("cookie_id",cookie_id);
    		request.setAttribute("interest",interest);
    		request.getRequestDispatcher("NextBatchDetail.jsp").forward(request, response);  
    	  
      }
else if(jEventName != null && jEventName.equalsIgnoreCase("G_BatchByFilter")){//add code for Get Batch By Filter 
	  String subjectId=request.getParameter("subject_id");
	  String sd=request.getParameter("d");
	  String td=request.getParameter("d2");
	  list = new ArrayList<String>();
	  if(subjectId.equals("0")&& sd.equals(""))
	  {
		  list=getBatch();
		  
	  }
	  if(!subjectId.equals("0")&& sd.equals(""))
	  {
		  list=getBatch(subjectId);
		 
	  }
	  else if(subjectId.equals("0")&& !sd.equals(""))
	  {
		  list=getBatch(sd, td);
		 
	  }
	  else if(!subjectId.equals("0")&& !sd.equals(""))
	  {
		  list=getBatch(subjectId,sd, td);
		 
	  }
		
	  int j=0;
	  out.println("<div class='table-scrollable'>");
		out.println("<table class='table table-striped table-bordered table-hover' >");
		out.println("<thead><tr><th scope='col'>S.No.</th><th scope='col'>Subject</th><th scope='col'>Batch Id</th><th scope='col'>Start Date</th><th scope='col'>Start Time</th><th scope='col'>Batch Type</th><th scope='col'>Display</th><th scope='col'>Duration</th><th scope='col'>Base Price</th><th scope='col'>Discount</th><th scope='col'>Final Price</th><th scope='col'>Verified</th><th scope='col'>Edit</th><th scope='col'>Delete</th></tr></thead><tbody>");
		for(int i=0;i<list.size();i++)
		{
			j=i+1;
			String s[]=list.get(i).split("abczxy");
			out.println("<tr class='odd gradeX'>");
			out.println("<td>"+j+"</td>");
	  		out.println("<td>"+s[1]+"</td>");
	  		out.println("<td>"+s[2]+"</td>");
	  		out.println("<td>"+s[3]+"</td>");
	  		out.println("<td>"+s[4]+"</td>");
	  		out.println("<td>"+s[5]+"</td>");
	  		out.println("<td>"+s[6]+"</td>");
	  		out.println("<td>"+s[7]+"</td>");
	  		out.println("<td>"+s[8]+"</td>");
	  		out.println("<td>"+s[9]+"</td>");
	  		out.println("<td>"+s[10]+"</td>");
	  		out.println("<td>"+s[11]+"</td>");
	  		if(s[11].equals("y"))
			{
	
	  			out.println("<td><span class='label label-sm label-success'>Verified</span></td>");
				
			}
			else
			{
				out.println("<td><span class='label label-sm label-danger'>Not Verified</span></td>");
			}
	  		out.println("<td><a class='fancybox fancybox.iframe' href='LoginServlet?jEventName=E_Batch&subject_id="+s[0]+"&batch_id="+s[2]+"' >Edit</a></td>");
	  		out.println("<td><a  href='LoginServlet?jEventName=D_Batch&batch_id="+s[2]+"' onclick='return check();'>Delete</a></td>");
	  		out.println("</tr>");
		}
		out.println("</tbody></table>");
		 out.println("</div>");
}
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_Category")){//add code for Add Category
    	  
    	  String categoryName = request.getParameter("name");
         
          if(checkCategory(categoryName))
          {
        	  jMessage = "Category name already exist";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("Category.jsp").forward(request, response);  
          }
          else
          { 
        	  if(addCategory(categoryName))
              {
            	  jMessage = "Category has been added successfully";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
              }
        	  else
        	  {
        		  jMessage = "Category has not added due to technical problem";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
        	  }
          }
    	  
      }
      	else if(jEventName != null && jEventName.equalsIgnoreCase("E_Category")){//add code for edit Category
    	  
		    	  String categoryName = request.getParameter("name");
		    	  String categoryId = request.getParameter("id");
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("categoryName", categoryName);
            	  request.setAttribute("categoryId", categoryId);
            	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
      	}
	   else if(jEventName != null && jEventName.equalsIgnoreCase("U_Category")){//add code for update Category
	    	  
				   	String categoryName = request.getParameter("name");
				   	String categoryNameOld = request.getParameter("old_name");
			 	  	String categoryId = request.getParameter("id");
			 	  	
			 	  	if(checkCategory(categoryName,categoryNameOld))
			          {
			        	  jMessage = "Category name already exist";
			        	  request.setAttribute("jEventName", jEventName);
			        	  request.setAttribute("jMessage", jMessage);
			        	  request.getRequestDispatcher("Category.jsp").forward(request, response);  
			          }
			 	  	else
			 	  	{
			        	  if(updateCategory(categoryId,categoryName))
			              {
			            	  jMessage = "Category has been updated successfully.";
			            	  request.setAttribute("jEventName", jEventName);
			            	  request.setAttribute("jMessage", jMessage);
			            	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
			              }
			        	  else
			        	  {
			        		  jMessage = "Category has not updated.";
			            	  request.setAttribute("jEventName", jEventName);
			            	  request.setAttribute("jMessage", jMessage);
			            	  request.setAttribute("categoryName", categoryName);
			            	  request.setAttribute("categoryId", categoryId);
			            	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
			        		  
			        	  }
			 	  	}
	        
	    	  
	      }
		   else if(jEventName != null && jEventName.equalsIgnoreCase("D_Category")){//add code for delete Category
		 	  
		 	  String categoryId = request.getParameter("id");
		      
		     	  if(deleteCategory(categoryId))
		           {
		         	  jMessage = "Category has been deleted successfully.";
		         	  request.setAttribute("jEventName", jEventName);
		         	  request.setAttribute("jMessage", jMessage);
		         	  request.getRequestDispatcher("Category.jsp").forward(request, response); 
		           }
		     
		 	  
		   }
		   else if(jEventName != null && jEventName.equalsIgnoreCase("C_Technology")){//add code for Add Technology
		    	  
		    	  String technologyName = request.getParameter("name");
		         
		          if(checkTechnology(technologyName))
		          {
		        	  jMessage = "Technology name already exist";
		        	  request.setAttribute("jEventName", jEventName);
		        	  request.setAttribute("jMessage", jMessage);
		        	  request.getRequestDispatcher("Technology.jsp").forward(request, response);  
		          }
		          else
		          { 
		        	  if(addTechnology(technologyName))
		              {
		            	  jMessage = "Technology has been added successfully";
		            	  request.setAttribute("jEventName", jEventName);
		            	  request.setAttribute("jMessage", jMessage);
		            	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
		              }
		        	  else
		        	  {
		        		  jMessage = "Technology has not added due to technical problem";
		            	  request.setAttribute("jEventName", jEventName);
		            	  request.setAttribute("jMessage", jMessage);
		            	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
		        	  }
		          }
		    	  
		      }
		      	else if(jEventName != null && jEventName.equalsIgnoreCase("E_Technology")){//add code for edit Technology
		    	  
				    	  String technologyName = request.getParameter("name");
				    	  String technologyId = request.getParameter("id");
		            	  request.setAttribute("jEventName", jEventName);
		            	  request.setAttribute("technologyName", technologyName);
		            	  request.setAttribute("technologyId", technologyId);
		            	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
		      	}
			   else if(jEventName != null && jEventName.equalsIgnoreCase("U_Technology")){//add code for update Technology
			    	  
						   	String technologyName = request.getParameter("name");
						   	String technologyNameOld = request.getParameter("old_name");
					 	  	String technologyId = request.getParameter("id");
					 	  	//System.out.println(technologyId+technologyName);
					 	  	if(checkTechnology(technologyName, technologyNameOld))
					          {
					        	  jMessage = "Technology name already exist";
					        	  request.setAttribute("jEventName", jEventName);
					        	  request.setAttribute("jMessage", jMessage);
					        	  request.getRequestDispatcher("Technology.jsp").forward(request, response);  
					          }
					 	  	else
					 	  	{
					        	  if(updateTechnology(technologyId, technologyName))
					              {
					            	  jMessage = "Technology has been updated successfully.";
					            	  request.setAttribute("jEventName", jEventName);
					            	  request.setAttribute("jMessage", jMessage);
					            	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
					              }
					        	  else
					        	  {
					        		  jMessage = "Technology has not updated.";
					            	  request.setAttribute("jEventName", jEventName);
					            	  request.setAttribute("jMessage", jMessage);
					            	  request.setAttribute("technologyName", technologyName);
					            	  request.setAttribute("technologyId", technologyId);
					            	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
					        		  
					        	  }
					 	  	}
			        
			    	  
			      }
				   else if(jEventName != null && jEventName.equalsIgnoreCase("D_Technology")){//add code for delete Technology
				 	  
				 	  String technologyId = request.getParameter("id");
				      
				     	  if(deleteTechnology(technologyId))
				           {
				         	  jMessage = "Technology has been deleted successfully.";
				         	  request.setAttribute("jEventName", jEventName);
				         	  request.setAttribute("jMessage", jMessage);
				         	  request.getRequestDispatcher("Technology.jsp").forward(request, response); 
				           }
				     
				 	  
				   }
		   else if(jEventName != null && jEventName.equalsIgnoreCase("C_Subject")){//add code for Add Subject
		 	  
		 	  String subjectName = request.getParameter("name");
		 	  String displaySubjectName = request.getParameter("display_name");
		 	  String subjectDescription = request.getParameter("description");
		 	  String training = request.getParameter("training");
		 	  String free = request.getParameter("free");
		 	  String url = request.getParameter("url");
		 	  String display = request.getParameter("display");
		 	  String level = request.getParameter("level");
		 	  String image = request.getParameter("image");
		 	  String project = request.getParameter("project");
		 	  String status = request.getParameter("status");
		 	  String category[] = request.getParameterValues("category");
		 	  String technology[] = request.getParameterValues("technology");
		 	  
		 	  list = new ArrayList<String>();
		       list.add(0, subjectName);
		       list.add(1, subjectDescription);
		       list.add(2, training);
		       list.add(3, free);
		       list.add(4, level);
		       list.add(5, display);
		       list.add(6, url);
		       list.add(7, image);
		       list.add(8, project);
		       list.add(9, status);
		       list.add(10, displaySubjectName);
		       if(checkSubject(subjectName))
		       {
		     	  jMessage = "Subject name "+subjectName+" already exist";
		     	  request.setAttribute("jEventName", jEventName);
		     	  request.setAttribute("jMessage", jMessage);
		     	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response);  
		       }
		       else
		       { 
		     	  if(addSubject(category,technology))
		           {
		         	  jMessage = "Subject has been added successfully";
		         	  request.setAttribute("jEventName", jEventName);
		         	  request.setAttribute("jMessage", jMessage);
		         	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response); 
		           }
		     	  else
		     	  {
		     		  jMessage = "Subject has not added due to technical problem";
		         	  request.setAttribute("jEventName", jEventName);
		         	  request.setAttribute("jMessage", jMessage);
		         	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response); 
		     	  }
		       }
		 	  
		   }
		   else if(jEventName != null && jEventName.equalsIgnoreCase("E_Subject")){//add code for  edit Subject
			 	  
			 	  String subjectId = request.getParameter("id");
			 	 String subjectName="",displaySubjectName="",subjectDescription="",training="",level="",category="",technology="",free="",url="",display="",image="",project="",status="";
			 	 List<Batch> list=new ArrayList<Batch>();
			      list=getSubjectDetail(subjectId);
			      subjectName=list.get(0).getSubjectName();
			      subjectDescription=list.get(0).getSubjectDescription();
			      training=list.get(0).getTraining();
			      level=list.get(0).getLevelName();
			      category=list.get(0).getCategoryName();
			      technology=list.get(0).getTechnologyName();
			      free=list.get(0).getFree();
			      url=list.get(0).getUrl();
			      display=list.get(0).getDisplay();
			      image=list.get(0).getImage();
			      project=list.get(0).getProject();
			      status=list.get(0).getStatus();
			      displaySubjectName=list.get(0).getDisplaySubjectName();
			     	 
			         	  
			         	  request.setAttribute("jEventName", jEventName);
			         	  request.setAttribute("subjectId", subjectId);
			         	  request.setAttribute("subjectName", subjectName);
			         	 request.setAttribute("displaySubjectName", displaySubjectName);
				         	request.setAttribute("subjectDescription", subjectDescription);
				         	request.setAttribute("training", training);
				         	request.setAttribute("level", level);
				         	request.setAttribute("category", category);
				         	request.setAttribute("technology", technology);
				         	request.setAttribute("free", free);
				         	request.setAttribute("url", url);
				         	request.setAttribute("display", display);
				         	request.setAttribute("image", image);
				         	request.setAttribute("project", project);
				         	request.setAttribute("status", status);
			         	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response); 
			     
			 	  
			   }
			else if(jEventName != null && jEventName.equalsIgnoreCase("U_Subject")){//add code for  update Subject
			 	  
			 	System.out.println("sacsacsc");
			 	String subjectId = request.getParameter("id");
			 	  String subjectNameOld = request.getParameter("old_name");
			 	 String subjectName = request.getParameter("name");
			 	 String displaySubjectName = request.getParameter("display_name");
			 	  String subjectDescription = request.getParameter("description");
			 	  String training = request.getParameter("training");
			 	  String free = request.getParameter("free");
			 	  String url = request.getParameter("url");
			 	  String display = request.getParameter("display");
			 	  String level = request.getParameter("level");
			 	 String image = request.getParameter("image");
			 	  String project = request.getParameter("project");
			 	 String status = request.getParameter("status");
			 	  String category[] = request.getParameterValues("category");
			 	  String technology[] = request.getParameterValues("technology");
			 	  
			 	  list = new ArrayList<String>();
			       list.add(0, subjectName);
			       list.add(1, subjectDescription);
			       list.add(2, training);
			       list.add(3, free);
			       list.add(4, level);
			       list.add(5, display);
			       list.add(6, url);
			       list.add(7, image);
			       list.add(8, project);
			       list.add(9, status);
			       list.add(10, displaySubjectName);
			      if(checkSubject(subjectName,subjectNameOld))
			      {
			    	  List<Batch> list=new ArrayList<Batch>();
				      list=getSubjectDetail(subjectId);
				      subjectName=list.get(0).getSubjectName();
				      subjectDescription=list.get(0).getSubjectDescription();
				      training=list.get(0).getTraining();
				      level=list.get(0).getLevelName();
				      String category2=list.get(0).getCategoryName();
				      String technology2=list.get(0).getTechnologyName();
				      free=list.get(0).getFree();
				      url=list.get(0).getUrl();
				      display=list.get(0).getDisplay();
				      image=list.get(0).getImage();
				      project=list.get(0).getProject();
				      status=list.get(0).getStatus();
				      displaySubjectName=list.get(0).getDisplaySubjectName();
				      request.setAttribute("subjectId", subjectId);
		         	  request.setAttribute("subjectName", subjectName);
			         	request.setAttribute("subjectDescription", subjectDescription);
			         	request.setAttribute("training", training);
			         	request.setAttribute("level", level);
			         	request.setAttribute("category", category2);
			         	request.setAttribute("technology", technology2);
			         	request.setAttribute("free", free);
			         	request.setAttribute("url", url);
			         	request.setAttribute("display", display);
			         	request.setAttribute("image", image);
			         	request.setAttribute("project", project);
			         	request.setAttribute("status", status);
			         	 request.setAttribute("displaySubjectName", displaySubjectName);
			    	  jMessage = "Subject name "+subjectName+" already exist";
			     	  request.setAttribute("jEventName", jEventName);
			     	  request.setAttribute("jMessage", jMessage);
			     	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response);
			      }
			      else
			      {
			     	  if(updateSubject(subjectId,category,technology))
			           {
			     		 List<Batch> list=new ArrayList<Batch>();
			     		  list=getSubject();
			         	  jMessage = "Subject "+subjectName+" has been updated successfully";
			         	  request.setAttribute("jEventName", jEventName);
			         	  request.setAttribute("jMessage", jMessage);
			         	 request.setAttribute("data", list);
			         	  request.getRequestDispatcher("ViewSubject.jsp").forward(request, response); 
			           }
			     	  else
			     	  {
			     		 List<Batch> list=new ArrayList<Batch>();
				    	  list=getSubjectDetail(subjectId);
					      
				    	  list=getSubjectDetail(subjectId);
					      subjectName=list.get(0).getSubjectName();
					      subjectDescription=list.get(0).getSubjectDescription();
					      training=list.get(0).getTraining();
					      level=list.get(0).getLevelName();
					      String category2=list.get(0).getCategoryName();
					      String technology2=list.get(0).getTechnologyName();
					      free=list.get(0).getFree();
					      url=list.get(0).getUrl();
					      display=list.get(0).getDisplay();
					      image=list.get(0).getImage();
					      project=list.get(0).getProject();
					      status=list.get(0).getStatus();
					      displaySubjectName=list.get(0).getDisplaySubjectName();
					      request.setAttribute("subjectId", subjectId);
			         	  request.setAttribute("subjectName", subjectName);
				         	request.setAttribute("subjectDescription", subjectDescription);
				         	request.setAttribute("training", training);
				         	request.setAttribute("level", level);
				         	request.setAttribute("category", category2);
				         	request.setAttribute("technology", technology2);
				         	request.setAttribute("free", free);
				         	request.setAttribute("url", url);
				         	request.setAttribute("display", display);
				         	request.setAttribute("image", image);
				         	request.setAttribute("project", project);
				         	request.setAttribute("status", status);
				         	 request.setAttribute("displaySubjectName", displaySubjectName);
			     		 jMessage = "Subject "+subjectName+" has not updated";
				     	  request.setAttribute("jEventName", jEventName);
				     	  request.setAttribute("jMessage", jMessage);
				     	  request.getRequestDispatcher("CreateSubject.jsp").forward(request, response);
			     	  }
			      }
			     
			 	  
			   }
			else if(jEventName != null && jEventName.equalsIgnoreCase("D_Subject")){//add code for delete Subject
				  
				  String subjectId = request.getParameter("id");
			   
			  	  if(deleteSubject(subjectId))
			        {
			  		  List<Batch> list=new ArrayList<Batch>();
			  		  list=getSubject();
			      	  jMessage = "Subject has been deleted successfully.";
			      	  request.setAttribute("jEventName", jEventName);
			      	  request.setAttribute("jMessage", jMessage);
			      	  request.setAttribute("data", list);
			      	  request.getRequestDispatcher("ViewSubject.jsp").forward(request, response); 
			        }
			  	  else
			  	  {
			  		List<Batch> list=new ArrayList<Batch>();
			  		list=getSubject();
			  		  jMessage = "Subject has not deleted due to technical problem.";
			      	  request.setAttribute("jEventName", jEventName);
			      	  request.setAttribute("jMessage", jMessage);
			      	request.setAttribute("data", list);
			      	  request.getRequestDispatcher("ViewSubject.jsp").forward(request, response); 
			  	  }
			  
				  
			}
			else if(jEventName != null && jEventName.equalsIgnoreCase("G_Subject")){//add code for get Subject
				 List<Batch> list=new ArrayList<Batch>();
				  		list=getSubject();
			      	  request.setAttribute("jEventName", jEventName);
			      	  request.setAttribute("data", list);
			      	  request.getRequestDispatcher("ViewSubject.jsp").forward(request, response); 
			 
				  
			}
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_S_Session")){//add code for Create Student Session
    	  
    	  String subject_id = request.getParameter("subject_id"); 
    	  String batch_id = request.getParameter("batch_id");
    	  String session_id= request.getParameter("session_id");
    	  String students= request.getParameter("students");
    	  if(checkSession(session_id))
          {
        	  jMessage = "Provided Session "+session_id+" already exist for subject id "+subject_id+" and batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("C_S_Session.jsp").forward(request, response); 
          }
          else
          {
        	  if(sendSession(session_id,students))
        	  {
        		  jMessage = "Session Successfully added";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("C_S_Session.jsp").forward(request, response); 
        	  }
        	  else
        	  {
        		  jMessage = "Session is not add please check the value you insert"+session_id;
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("C_S_Session.jsp").forward(request, response); 
        	  }
        	  
          }
    	  
      }
 else if(jEventName != null && jEventName.equalsIgnoreCase("C_DB_Session")){//add code for Create demo Session
    	  
    	  String subject_id = request.getParameter("subject_id"); 
    	  String batch_id = request.getParameter("batch_id");
    	  String session_id= request.getParameter("session_id");
    	  String students= request.getParameter("students");
    	  if(checkSession(session_id))
          {
        	  jMessage = "Provided Session "+session_id+" already exist for subject id "+subject_id+" and batch "+batch_id;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("DB_Session.jsp").forward(request, response); 
          }
          else
          {
        	  if(sendSession(session_id,students))
        	  {
        		  jMessage = "Session Successfully added";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("DB_Session.jsp").forward(request, response); 
        	  }
        	  else
        	  {
        		  jMessage = "Session is not add please check the value you insert"+session_id;
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("C_S_Session.jsp").forward(request, response); 
        	  }
        	  
          }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Audit_Subject")){//add code for Teacher Audit Subject
    	  ArrayList<String> al=new ArrayList<String>();
    	  ArrayList<String> al2=new ArrayList<String>();
    	  String subject_id = request.getParameter("subject_id");
    	  al=getTeacherAudit(subject_id);
    	  al2=getTeacherAuditDemo(subject_id);
    	  request.setAttribute("teacherAudit", al);
    	  request.setAttribute("teacherAudit2", al2);
    	  request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage);
    	  request.getRequestDispatcher("T_Audit.jsp").forward(request, response); 
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Audit_Date")){//add code for Teacher Audit Date
    	  
    	  ArrayList<String> al=new ArrayList<String>();
    	  ArrayList<String> al2=new ArrayList<String>();
    	  String date = request.getParameter("date");
    	  al=getTeacherAuditByDate(date);
    	  al2=getTeacherAuditByDateDemo(date);
    	  request.setAttribute("teacherAudit", al);
    	  request.setAttribute("teacherAudit2", al2);
    	  request.setAttribute("jEventName", jEventName);
    	  request.setAttribute("jMessage", jMessage);
    	  request.getRequestDispatcher("T_Audit.jsp").forward(request, response); 
    	  
      }
 else if(jEventName != null && jEventName.equalsIgnoreCase("T_Audit_Teacher")){//add code for Teacher Audit
    	  
    	  ArrayList<String> al=new ArrayList<String>();
    	  ArrayList<String> al2=new ArrayList<String>();
    	  String teacherId = request.getParameter("teacher_id");
    	  if(checkTeacherId(teacherId))
    	  {
    		  al=getTeacherAuditByTeacher(teacherId);
    		  al2=getTeacherAuditByTeacherDemo(teacherId);
	    	  request.setAttribute("teacherAudit", al);
	    	  request.setAttribute("teacherAudit2", al2);
	    	  request.setAttribute("jEventName", jEventName);
	    	  request.getRequestDispatcher("T_Audit.jsp").forward(request, response);    
    	  }
    	  else
    	  {
    			
    	    	  
    	    	  jMessage = "Provided teacher Id is not exist";
    	    	  request.setAttribute("teacherAudit", al);
    	    	  request.setAttribute("teacherAudit2", al2);
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("T_Audit.jsp").forward(request, response);
    	  }
    	 
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_Sales")){//add code for Create Sales 
    	  
    	  String salesId = request.getParameter("sales_id"); 
    	  String salesName = request.getParameter("sales_name");
    	
          String address = request.getParameter("address");
          String email = request.getParameter("email");
          String mobile= request.getParameter("mobile");
          
          list = new ArrayList<String>();
          list.add(0, salesId);
          list.add(1, salesName);
          list.add(2, user);
          list.add(3, address);
          list.add(4, email);
          list.add(5, mobile);
          if(checkSalesUser(user)){
        	  jMessage = "Provided Sales User Name is already exist...please choose another User Name";  request.setAttribute("jEventName", jEventName); request.setAttribute("jMessage", jMessage)
        	  ;request.getRequestDispatcher("C_Sales.jsp").forward(request, response); }else{ if(sendSales()){ jMessage = "New Sales Profile Successfully created"; request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage); request.getRequestDispatcher("Success.jsp").forward(request, response);}
          }  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("T_Profile")){//add code for Teacher Profile 
    	  
    	  	String f1=request.getParameter("teacher_id");
    		response.sendRedirect("http://teacher.easylearning.guru/TeacherAction?jEventName=Profile&f1="+f1);
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("S_Profile")){//add code for Student Profile 
    	  String studentId=request.getParameter("student_id");
    	  String name=request.getParameter("student_name");
    	  if(studentProfile(studentId))
    	  { 
    		 
    		  session.setAttribute("Student_Id", studentId);
              session.setAttribute("Student_Name", name);
    		  response.sendRedirect("http://student.easylearning.guru/Student_LMS/LoginServlet?jEventName=S_Profile&id="+studentId+"&name="+name);
    	  }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_D_Batch")){//add code for Create Demo Batch
     	 
    	  String subject_id = request.getParameter("subject_id"); 
    	  String s_date = request.getParameter("s_date");
    	  String s_time = request.getParameter("s_time");
    	  String meeting_id = request.getParameter("meeting_id");
          String url = request.getParameter("url");
          list = new ArrayList<String>();
          list.add(0, subject_id);
          list.add(1, s_date);
          list.add(2, s_time);
          list.add(3, meeting_id);
          list.add(4, url);
          if(checkDemoBatch(subject_id,s_date,s_time))
          {
        	  jMessage = "Provided subject demo batch is already exist for  date="+s_date+" and time "+s_time;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("subject_id", subject_id);
        	  request.setAttribute("s_date", s_date);
        	  request.setAttribute("s_time", s_time);
        	  request.setAttribute("meeting_id", meeting_id);
        	  request.setAttribute("url", url);
        	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response); 
          }
          else
          {
        	  if(createDemoBatch())
        	  {
        		  request.setAttribute("subject_id", "");
	        	  request.setAttribute("s_date", "");
	        	  request.setAttribute("s_time", "");
	        	  request.setAttribute("meeting_id", "");
	        	  request.setAttribute("url", "");
	        	  jMessage = "Demo Batch successfully created";
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("jMessage", jMessage);
	        	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response);
        	  } 
        	  else
        	  {
        		  request.setAttribute("subject_id", subject_id);
	        	  request.setAttribute("s_date", s_date);
	        	  request.setAttribute("s_time", s_time);
	        	  request.setAttribute("meeting_id", meeting_id);
	        	  request.setAttribute("url", url);
        		  jMessage = "Demo Batch is not created ... please check the format of date";
            	  request.setAttribute("jEventName", jEventName);
            	  request.setAttribute("jMessage", jMessage);
            	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response);
        	  }
          }
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_D_Batch")){//add code for  Update Demo Batch 
    	  String subjectId = request.getParameter("subject_id");
    	  String dBatchId=request.getParameter("d_batch_id");
    	  String sDate=request.getParameter("s_date");
    	  String sTime=request.getParameter("s_time");
    	  String meetingId=request.getParameter("meeting_id");
    	  String url=request.getParameter("url");
    	  list = new ArrayList<String>();
          list.add(0, dBatchId);
          list.add(1, sDate);
          list.add(2, sTime);
          list.add(3, meetingId);
          list.add(4, url);
          if(checkDemoBatch(subjectId,sDate,sTime,dBatchId))
          {
        	  
        	  jMessage = "Provided subject demo batch is already exist for  date "+sDate+" and time "+sTime;
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.setAttribute("subject_id", subjectId);
        	  request.setAttribute("d_batch_id", dBatchId);
        	  request.setAttribute("s_date", sDate);
        	  request.setAttribute("s_time", sTime);
        	  request.setAttribute("meeting_id", meetingId);
        	  request.setAttribute("url", url);
        	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response); 
          }
          else
          {
        	  if(updateDBatch())
        	  {
        		  	ArrayList<String> al = new ArrayList<String>();
	  				al=getDBatch();
	  				request.setAttribute("data", al);
	  			 
	  				jMessage = "Demo Batch successfully updated";
	  				request.setAttribute("jEventName", jEventName);
	  				request.setAttribute("jMessage", jMessage);
	  				request.getRequestDispatcher("V_D_Batch.jsp").forward(request, response);
        	  }
        	  else
        	  {
        		  request.setAttribute("subject_id", subjectId);
	        	  request.setAttribute("d_batch_id", dBatchId);
	        	  request.setAttribute("s_date", sDate);
	        	  request.setAttribute("s_time", sTime);
	        	  request.setAttribute("meeting_id", meetingId);
	        	  request.setAttribute("url", url);
	        	  jMessage = "Demo Batch has not updated ... please check the value you enter";
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("jMessage", jMessage);
	        	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response);
    		  
        	  }
          }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("E_D_Batch")){//add code for  Edit Demo Batch 
    	  String subjecId=request.getParameter("subject_id");
    	  String dBatchId=request.getParameter("d_batch_id");
    	  String sDate=request.getParameter("s_date");
    	  String sTime=request.getParameter("s_time");
    	  String meetingId=request.getParameter("meeting_id");
    	  String url=request.getParameter("url");
    	
    		
    		  
        	  request.setAttribute("jEventName", jEventName);
        	 
        	  request.setAttribute("subject_id", subjecId);
        	  request.setAttribute("d_batch_id", dBatchId);
        	  request.setAttribute("s_date", sDate);
        	  request.setAttribute("s_time", sTime);
        	  request.setAttribute("meeting_id", meetingId);
        	  request.setAttribute("url", url);
        	  request.getRequestDispatcher("C_D_Batch.jsp").forward(request, response);
    	  
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_D_Batch")){//add code for Delete Demo Batch 
    	  String dBatchId=request.getParameter("d_batch_id");
    	  if(deleteDBatch(dBatchId))
    	  {
    		  list = new ArrayList<String>();
  	  		list=getDBatch();
  	  		request.setAttribute("data", list);
    		  jMessage = "Demo Batch successfully deleted";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_D_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  	list = new ArrayList<String>();
  	  			list=getDBatch();
  	  			request.setAttribute("data", list);
  	  			jMessage = "Demo Batch has not deleted due to some internal problem";
  	  			request.setAttribute("jEventName", jEventName);
  	  			request.setAttribute("jMessage", jMessage);
  	  			request.getRequestDispatcher("V_D_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("G_D_Batch")){//add code for get Demo Batch 
    	  
    	  		list = new ArrayList<String>();
    	  		list=getDBatch();
    	  		request.setAttribute("data", list);
    	  		request.setAttribute("jEventName", jEventName);
    	  		request.getRequestDispatcher("V_D_Batch.jsp").forward(request, response);
    	  
    	 
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("G_D_BatchByFilter")){//add code for get Demo Batch By Filter 
    	  String subjectId=request.getParameter("subject_id");
    	  String sd=request.getParameter("d");
    	  String td=request.getParameter("d2");
    	  list = new ArrayList<String>();
    	  if(subjectId.equals("0")&& sd.equals(""))
    	  {
    		  list=getDBatch();
    		  
    	  }
    	  if(!subjectId.equals("0")&& sd.equals(""))
    	  {
    		  list=getDBatch(subjectId);
    		 
    	  }
    	  else if(subjectId.equals("0")&& !sd.equals(""))
    	  {
    		  list=getDBatch(sd, td);
    		 
    	  }
    	  else if(!subjectId.equals("0")&& !sd.equals(""))
    	  {
    		  list=getDBatch(subjectId,sd, td);
    		 
    	  }
	  		
    	  int j=0;
    	  out.println("<div class='table-scrollable'>");
	  		out.println("<table class='table table-striped table-bordered table-hover' id='sample_3'>");
	  		out.println("<thead><tr><th scope='col'>S.No.</th><th scope='col'>Subject</th><th scope='col'>Demo Batch Id</th><th scope='col'>Start Date</th><th scope='col'>Start Time</th><th scope='col'>Meeting Id</th><th scope='col'>URL</th><th scope='col'>Edit</th><th scope='col'>Delete</th></tr></thead><tbody>");
	  		for(int i=0;i<list.size();i++)
	  		{
	  			j=i+1;
	  			String s[]=list.get(i).split("abczxy");
	  			out.println("<tr class='odd gradeX'>");
	  			out.println("<td>"+j+"</td>");
		  		out.println("<td>"+s[1]+"</td>");
		  		out.println("<td>"+s[2]+"</td>");
		  		out.println("<td>"+s[3]+"</td>");
		  		out.println("<td>"+s[4]+"</td>");
		  		out.println("<td>"+s[5]+"</td>");
		  		out.println("<td>"+s[6]+"</td>");
		  		out.println("<td><a  href='LoginServlet?jEventName=E_D_Batch&subject_id="+s[0]+"&d_batch_id="+s[2]+"&s_date="+s[3]+"&s_time="+s[4]+"&meeting_id="+s[5]+"&url="+s[6]+"' >Edit</a></td>");
		  		out.println("<td><a  href='LoginServlet?jEventName=D_D_Batch&d_batch_id="+s[2]+"' onclick='return check();'>Delete</a></td>");
		  		out.println("</tr>");
	  		}
	  		out.println("</tbody></table>");
	  		 out.println("</div>");
      }
      
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("Sales_Update")){//add code for  Sales Update 
    	  String salesId=request.getParameter("s_id");
    	  String name=request.getParameter("name");
    	  String userName=request.getParameter("user");
    	  String address=request.getParameter("address");
    	  String emailId=request.getParameter("email_id");
    	  String phone=request.getParameter("mobile");
    	  list = new ArrayList<String>();
          list.add(0, salesId);
          list.add(1, name);
          list.add(2, userName);
          list.add(3, address);
          list.add(4, emailId);
          list.add(5, phone);
    	  if(updateSales())
    	  {
    		
    		  jMessage = "Sales Profile successfully Updated ";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Sales.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Sales Profile has not updated ... due to internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Sales.jsp").forward(request, response);
    		  
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Sales_Delete")){//add code for Sales Delete 
    	  String id=request.getParameter("id");
    	  if(deleteSales(id))
    	  {
    		 
    		  jMessage = "Sales Profile has been successfully deleted";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Sales.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Sales Profile has not deleted due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_Sales.jsp").forward(request, response);
    	  }
    	  
      }
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("Student_Update")){//add code for  Student Update 
    	  String salesId=request.getParameter("s_id");
    	  String name=request.getParameter("s_name");
    	  String emailId=request.getParameter("email_id");
    	  String phone=request.getParameter("mobile");
    	  list = new ArrayList<String>();
          list.add(0, salesId);
          list.add(1, name);
          list.add(2, emailId);
          list.add(3, phone);
    	  if(updateStudent2())
    	  {
    		  jMessage = "Student successfully Updated ";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("U_S_List.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Student has not updated ... please check dob format";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("U_S_List.jsp").forward(request, response);
    		  
    	  }
          }
    	  
      else if(jEventName != null && jEventName.equalsIgnoreCase("Student_Delete")){//add code for Student Delete 
    	  String id=request.getParameter("student_id");
    	  if(deleteStudent2(id))
    	  {
    		 
    		  jMessage = "Student has been successfully deleted";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("U_S_List.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Student has not deleted due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("U_S_List.jsp").forward(request, response);
    	  }
    	  
      }
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_B_Session")){//add code for  Update Batch Session
    	  String sessionId=request.getParameter("session_id");
    	  String sessionDate=request.getParameter("session_date");
    	  String module=request.getParameter("module");
    	  list = new ArrayList<String>();
          list.add(0, sessionId);
          list.add(1, sessionDate);
          list.add(2, module);
    	  if(updateBatchSession())
    	  {
    		  jMessage = "Session has been successfully Updated ";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Session has not updated ... please check session date format";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    		  
    	  }
          }
    	  
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_B_Session")){//add code for Delete Demo Batch Session 
    	  String sessionId=request.getParameter("session_id");
    	  if(deleteBatchSession(sessionId))
    	  {
    		 
    		  jMessage = "Session has been successfully deleted";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Student has not deleted due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("SC_Batch")){//add code for Schedule Batch 
    	  String batchtId=request.getParameter("batch_id");
    	  if(scheduleBatch(batchtId))
    	  {
    		  jMessage = "Batch "+batchtId+" schedule successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Not Schedule due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_Batch.jsp").forward(request, response);
    	  }
    	  
      }
     
      else if(jEventName != null && jEventName.equalsIgnoreCase("G_ScheduleModule")){
    	  String subjectId=request.getParameter("subject_id");
    	  String batchId=request.getParameter("batch");

    	  list=getBatchModule(batchId);
    	  out.println("<select class='form-control select2_category' name='module' id='module'>");
    	  out.println("<option value=''>Select Module</option>");
     	 for(int i=0;i<list.size();i++)
     	 {
     	  out.println("<option value='"+list.get(i)+"'>"+list.get(i)+"</option>");
     	  
     	 }
     	 out.println("</select>");
    	 
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("G_SessionDateURL")){
    	  String subjectId=request.getParameter("subject_id");
    	  String batchId=request.getParameter("batch");
    	  String module=request.getParameter("module");
    	  String s=getClassDetail(batchId,module);
    	  out.println(s);
    	 
    	  
      }
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("Class_Link")){
    	  String subjectId=request.getParameter("subject_id");
    	  String batchId=request.getParameter("batch");
    	  String sessionDate=request.getParameter("session_date");
    	  String url=request.getParameter("url");
    	  String module=request.getParameter("module");
    	  String meeting_id=request.getParameter("meeting_id");
    	  String email=request.getParameter("email");
    	  String password=request.getParameter("password");
    	  //String time="06:30";
    	  SendEmail s=new SendEmail();
    	  ArrayList<String> al = new ArrayList<String>();
    	  al=getTeacher(batchId);
    	  String name=al.get(0);
    	  String toEmail=al.get(1);
    	  s.sendTeacherEmail(toEmail, "Next webinar class detail", sessionDate, batchId, email, password, name,url);
    	  //System.out.println("url="+url);
    	  list = new ArrayList<String>();
    	  list=getStudentDetail(batchId);
 
    	   
          
    	  if(list.isEmpty())
    	  {
    		  jMessage="Student has not alloted for this batch "+batchId;
    		  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("Send_Webinar.jsp").forward(request, response);  
    	  }
    	  else
    	  {
    		  request.setAttribute("url", url);
    		  request.setAttribute("session_date", sessionDate);
    		  request.setAttribute("meeting_id", meeting_id);
    		  request.setAttribute("module", module);
    		  request.setAttribute("student", list);
    		  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("Send_Email.jsp").forward(request, response); 
    	  }
    	
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("SC_D_Batch")){//add code for Schedule Batch Session 
    	  String subjectId=request.getParameter("subject_id");
    	  String year=request.getParameter("year");
    	  String month=request.getParameter("month");
    	  list = new ArrayList<String>();
          list.add(0, subjectId);
          list.add(1, year);
          list.add(2, month);
    	  if(scheduleDemoBatch())
    	  {
    		  jMessage = "Demo batch schedule successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Not Schedule due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_DB_Session")){//add code for Delete Batch Session 
    	  String sessionId=request.getParameter("session_id");
    	  if(deleteBatchSession(sessionId))
    	  {
    		  jMessage = "Demo batch delete successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Not deleted due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_D_B_Session")){//add code for Update batch Session 
    	  String sessionId=request.getParameter("session_id");
    	  String teacherId=request.getParameter("teacher_id");
    	  if(updateDemoBatchSession(teacherId,sessionId))
    	  {
    		  jMessage = "Update Demo batch session successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  else
    	  {
    		  jMessage = "Not Update Demo batch session due to some internal problem";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("SC_D_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_Session")){//add code for Update Session 
    	  String sessionId=request.getParameter("session_id");
    	  String teacherId=request.getParameter("t_id");
    	  String batchId=request.getParameter("batch_id");
    	  String sessionDate=request.getParameter("session_date");
    	  String module=request.getParameter("module");
    	  list = new ArrayList<String>();
          list.add(0, sessionId);
          list.add(1, batchId);
          list.add(2, teacherId);
          list.add(3, sessionDate);
          list.add(4, module);
    	  if(updateSession())
    	  {
    		  jMessage = "Update session successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_S_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_Session")){//add code for Delete Session 
    	  String sessionId=request.getParameter("session_id");
    	  if(deleteSession(sessionId))
    	  {
    		  jMessage = "Update session successfully";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("V_S_Batch.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("CallBack")){//add code for CallBack 
    	  String callBackId=request.getParameter("callback_id");
    	  if(callBack(callBackId))
    	  {
    		  jMessage = "Call backed successful";
        	  request.setAttribute("jEventName", jEventName);
        	  request.setAttribute("jMessage", jMessage);
        	  request.getRequestDispatcher("View_CallBack.jsp").forward(request, response);
    	  }
    	  
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Q_Answer")){//add code for Q_Answer
    	  
    	  
 	     String id = request.getParameter("query_id");
 	     String answer = request.getParameter("answer");
 	     if(queryAnswer(answer,id))
 	     {
 		  					jMessage = "Your Query Answer submitted.";
 						request.setAttribute("jMessage", jMessage);
 						request.setAttribute("jEventName", jEventName);
 					request.getRequestDispatcher("V_S_Query.jsp").forward(request, response);
 	     }
 	     else
 	     {
 	    	 jMessage = "Query has not submitted due to internal error";
 				request.setAttribute("jMessage", jMessage);
 				request.setAttribute("jEventName", jEventName);
 			request.getRequestDispatcher("V_S_Query.jsp").forward(request, response);
 	     }
 	 }
      else if(jEventName != null && jEventName.equalsIgnoreCase("R_LiveDemo")){//add code for Read Live Demo request
    	  
    	  
  	     				String id[]= request.getParameterValues("c");
  	     
  	     				isReadLiveDemo(id);
  	     				String course=request.getParameter("course");
  	     				LiveDemoDAO dao=new LiveDemoDAO();
  	     			  List<LiveDemo> list2=new  ArrayList<LiveDemo>();
  	  	        	if(course.equalsIgnoreCase("All"))
  	  	        		list2 = dao.viewNotificationLiveDemo();
  	  	        	else
  	  	        		list2 = dao.viewNotificationLiveDemo(course);
  	  	        	
  	  	        	 request.setAttribute("course", course);
  	  	 	        request.setAttribute("livedemo", list2);
  		  				jMessage = "You successfully read "+course+" Live Demo Notifications";
  						request.setAttribute("jMessage", jMessage);
  						request.setAttribute("jEventName", jEventName);
  						request.getRequestDispatcher("LiveDemo_Notification.jsp").forward(request, response);
  	    
  	 }
      else if(jEventName != null && jEventName.equalsIgnoreCase("R_Contact")){//add code for Read Contact request 
    	  
    	  
    	  				String id[]= request.getParameterValues("c");
   	     
     	   				isReadContact(id);
     	   			
     	   				ArrayList<String> al=getContact();
     	   				request.setAttribute("contact", al);
   		  				jMessage = "You Successfully read contact Notification";
   						request.setAttribute("jMessage", jMessage);
   						request.setAttribute("jEventName", jEventName);
   						request.getRequestDispatcher("V_Contact.jsp").forward(request, response);
   	     
   	 }
    
      else if(jEventName != null && jEventName.equalsIgnoreCase("V_Subject")){//add code for View Subject
    	  
    	  			ArrayList<String> al=getSubjects();
    	  			request.setAttribute("subjects", al);
				request.setAttribute("jEventName", jEventName);
				request.getRequestDispatcher("A_Subject.jsp").forward(request, response);
      		}
      else if(jEventName != null && jEventName.equalsIgnoreCase("V_Contact")){//add code for View Contact
    	  String date=request.getParameter("d");
    	  String date2=request.getParameter("d2");
			ArrayList<String> al=getContactByDate(date,date2);
			out.println("<div class='table-scrollable'>");
			out.println("<table class='table table-striped table-bordered table-hover' id='sample_2'><thead><tr><th scope=col'>S.No</th><th scope=col'>Name</th><th scope=col'>Phone</th><th scope=col'>Email</th><th scope=col'>Course</th><th scope=col'> Message</th><th scope=col'>Date Time</th><th scope=col'>Ip Address</th><th scope=col'>Organization</th><th scope=col'>Send Custom Email</th></tr></thead><tbody>");
			int i=0,j=0;
			for( i=0;i<al.size();i++)
			{
				j=i+1;
					String s=al.get(i);
					String s2[]=s.split("abczxy");
					 out.println("<tr class='odd gradeX'>");
					 out.println("<td>"+j+"</td>");
					 out.println("<td>"+s2[1]+"</td>");
					 out.println("<td>"+s2[2]+"</td>");
					 out.println("<td>"+s2[3]+"</td>");
					 out.println("<td>"+s2[4]+"</td>");
					 out.println("<td>"+s2[5]+"</td>");
					 out.println("<td>"+s2[6]+"</td>");
					 out.println("<td>"+s2[8]+"</td>");
					 out.println("<td>"+s2[9]+"</td>");
					 out.println("<td><a href='Send_Custom_Email.jsp?name="+s2[1]+"&email="+s2[3]+"&phone="+s2[2]+"&country=IN&id=0&student_id= &interest_level=0'>Send</a> </td>");
					out.println("</tr>");
			
			}
    	 
    	  out.println("</tbody></table>");
    	  out.println("</div>");
	}
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("All_C_Request")){//add code for All Contact Request
    
			ArrayList<String> al=getAllContact();
			out.println("<div class='table-scrollable'>");
			out.println("<table class='table table-striped table-bordered table-hover' id='sample_2'><thead><tr><th scope=col'>S.No</th><th scope=col'>Name</th><th scope=col'>Phone</th><th scope=col'>Email</th><th scope=col'>Course</th><th scope=col'> Message</th><th scope=col'>Date Time</th><th scope=col'>IP Address</th><th scope=col'>Organization</th><th scope=col'>Send Custom Email</th></tr></thead><tbody>");
			int i=0,j=0;
			for( i=0;i<al.size();i++)
			{
				j=i+1;
					String s=al.get(i);
					String s2[]=s.split("abczxy");
					 out.println("<tr class='odd gradeX'>");
					 out.println("<td>"+j+"</td>");
					 out.println("<td>"+s2[1]+"</td>");
					 out.println("<td>"+s2[2]+"</td>");
					 out.println("<td>"+s2[3]+"</td>");
					 out.println("<td>"+s2[4]+"</td>");
					 out.println("<td>"+s2[5]+"</td>");
					 out.println("<td>"+s2[6]+"</td>");
					 out.println("<td>"+s2[8]+"</td>");
					 out.println("<td>"+s2[9]+"</td>");
					 out.println("<td><a href='Send_Custom_Email.jsp?name="+s2[1]+"&email="+s2[3]+"&phone="+s2[2]+"&country=IN&id=0&student_id= &interest_level=0'>Send</a> </td>");
					out.println("</tr>");
			
			}
    	 
    	  out.println("</tbody></table>");
    	  out.println("</div>");

	}
      else if(jEventName != null && jEventName.equalsIgnoreCase("N_Contact")){//add code for Notification Contact
    	  
			ArrayList<String> al=getContactNotification();
			request.setAttribute("contact", al);
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("V_Contact.jsp").forward(request, response);
	}
      else if(jEventName != null && jEventName.equalsIgnoreCase("V_CallBack")){//add code for View Callback by date
    	  String date=request.getParameter("d");
    	  String date2=request.getParameter("d2");
			ArrayList<String> al=getCallBackByDate(date,date2);
			out.println("<div class='table-scrollable'>");
			out.println("<table class='table table-striped table-bordered table-hover' id='sample_3'><thead><tr><th scope=col'>S.No.</th><th scope=col'>Name</th><th scope=col'>Email</th><th scope=col'>Phone</th><th scope=col'>Query</th><th scope=col'>Best Time To Call</th><th scope=col'>Date</th><th scope=col'>IP Address</th><th scope=col'>Call Back</th></tr></thead><tbody>");
			int i=0,j=0;
			for( i=0;i<al.size();i++)
			{
				j=i+1;
					String s=al.get(i);
					String s2[]=s.split("abczxy");
					 out.println("<tr class='odd gradeX'>");
					 out.println("<td>"+j+"</td>");
					 out.println("<td><a class='fancybox fancybox.iframe' href='LoginServlet?jEventName=CallBack_Info&email="+s2[2]+"'>"+s2[1]+"</a></td>");
					 out.println("<td>"+s2[2]+"</td>");
					 out.println("<td>"+s2[3]+"</td>");
					 out.println("<td>"+s2[4]+"</td>");
					 out.println("<td>"+s2[5]+"</td>");
					 out.println("<td>"+s2[6]+"</td>");
					 out.println("<td>"+s2[8]+"</td>");
					 if(s2[7].equals("n"))
						 out.println("<td><a href='LoginServlet?jEventName=CallBack&callback_id="+s2[0]+"'><input type='button'  class='btn green' value='Call Back' /></a> </td>");
					 else
						 out.println("<td><a href='#'>Call Backed</a> </td>");
					out.println("</tr>");
					 out.println("</div>");
			}
    	 
    	  out.println("</tbody></table>");
    	  out.println("</div>");
	}
      else if(jEventName != null && jEventName.equalsIgnoreCase("CallBack_Info")){//add code for View callBack by email
    	  String email=request.getParameter("email");
    
			ArrayList<String> al=getCallBackByEmail(email);
			request.setAttribute("callback", al);
			request.setAttribute("email", email);
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("CallBack_Information.jsp").forward(request, response);
		
	}
      
      else if(jEventName != null && jEventName.equalsIgnoreCase("All_CallBack")){//add code for All View callBack 
    
			ArrayList<String> al=getAllCallBack();
			out.println("<div class='table-scrollable'>");
			out.println("<table class='table table-striped table-bordered table-hover' id='sample_3'><thead><tr><th scope=col'>S.No.</th><th scope=col'>Name</th><th scope=col'>Email</th><th scope=col'>Phone</th><th scope=col'>Query</th><th scope=col'>Best Time To Call</th><th scope=col'>Date</th><th scope=col'>IP Address</th><th scope=col'>Call Back</th></tr></thead><tbody>");
			int i=0,j=0;
			for( i=0;i<al.size();i++)
			{
				j=i+1;
					String s=al.get(i);
					String s2[]=s.split("abczxy");
					 out.println("<tr class='odd gradeX'>");
					 out.println("<td>"+j+"</td>");
					 out.println("<td><a class='fancybox fancybox.iframe' href='LoginServlet?jEventName=CallBack_Info&email="+s2[2]+"'>"+s2[1]+"</a></td>");
					 out.println("<td>"+s2[2]+"</td>");
					 out.println("<td>"+s2[3]+"</td>");
					 out.println("<td>"+s2[4]+"</td>");
					 out.println("<td>"+s2[5]+"</td>");
					 out.println("<td>"+s2[6]+"</td>");
					 out.println("<td>"+s2[8]+"</td>");
					 if(s2[7].equals("n"))
						 out.println("<td><a href='LoginServlet?jEventName=CallBack&callback_id="+s2[0]+"'><input type='button'  class='btn green' value='Call Back' /></a> </td>");
					 else
						 out.println("<td><a href='#'>Call Backed</a> </td>");
					out.println("</tr>");
					 out.println("</div>");
			}
    	 
    	  out.println("</tbody></table>");
    	  out.println("</div>");
			
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("S_Info")){//add code for Student Live demo Info
    	    String email="",name="",phone="", studentId="",course="",pickup="",mcorrect="",ecorrect="",country="",query="",attend="",date="",sex="",qryType="",disclaimer="";
    	    String id=request.getParameter("id");
    		String lpId=request.getParameter("lp_id");
  	    	String cookieId=request.getParameter("cookie_id");
  	    	String interest=request.getParameter("interest");
			
			 list = new ArrayList<String>();
			 list=getLiveDemo(id);
			 name=list.get(0);
			 email=list.get(1);
			 phone=list.get(2);
			 query=list.get(3);
			 studentId=list.get(4);
			 ecorrect=list.get(5);
			 mcorrect=list.get(6);
			 attend=list.get(7);
			 pickup=list.get(8);
			 course=list.get(9);
			 country=list.get(10);
			 date=list.get(11);
			 sex=list.get(12);
			 qryType=list.get(13);
			 disclaimer=list.get(14);
			
			 ArrayList<String> al2=getLpSource(lpId);
				String ip="";
				if(al2.isEmpty())
					ip=getIP(cookieId);
			request.setAttribute("lp_source", al2);
			request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			request.setAttribute("student_id", studentId);
			request.setAttribute("id", id);
			request.setAttribute("attend", attend);
			request.setAttribute("query", query);
			request.setAttribute("e_correct", ecorrect);
			request.setAttribute("m_correct", mcorrect);
			request.setAttribute("lp_id", lpId);
			request.setAttribute("country", country);
			request.setAttribute("pickup", pickup);
			request.setAttribute("cookie_id", cookieId);
			request.setAttribute("course", course);
			request.setAttribute("date", date);
			request.setAttribute("ip", ip);
			request.setAttribute("sex", sex);
			request.setAttribute("interest", interest);
			request.setAttribute("qry_type", qryType);
			request.setAttribute("disclaimer", disclaimer);
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("S_Information.jsp").forward(request, response);
    }else if(jEventName != null && jEventName.equalsIgnoreCase("V_Info")){
    	String email="",name="",phone="",course="",attend="",date="",IP="",cookie="";
    	String id=request.getParameter("ID");
    	 list = new ArrayList<String>();
		 list=getVideoDetail(id);
		 name=list.get(0);
		 email=list.get(1);
		 phone=list.get(2);
		 date=list.get(3);
		 IP=list.get(4);
		 cookie=list.get(5);
		 ArrayList<String> al2=getInterest(email);
		 
		 	request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			request.setAttribute("date", date);
			request.setAttribute("IP", IP);
			request.setAttribute("ID", id);
			request.setAttribute("cookie", cookie);
			request.setAttribute("interest", al2.get(0));
			request.setAttribute("page", list.get(6));
			request.setAttribute("country", list.get(7));
			
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("Video_popup.jsp").forward(request, response);
  }
      else if(jEventName != null && jEventName.equalsIgnoreCase("F_Info")){
      	String email="",name="",phone="",date="",IP="",cookie="";
      	String id=request.getParameter("ID");
      	 list = new ArrayList<String>();
  		 list=getFreeDetail(id);
  		 name=list.get(0);
  		 email=list.get(1);
  		 phone=list.get(2);
  		 date=list.get(3);
  		 IP=list.get(4);
  		 cookie=list.get(5);
  		 ArrayList<String> al2=getFreeInterest(email);
  		 
  		 	request.setAttribute("email", email);
  			request.setAttribute("name", name);
  			request.setAttribute("phone", phone);
  			request.setAttribute("date", date);
  			request.setAttribute("IP", IP);
  			request.setAttribute("ID", id);
  			request.setAttribute("cookie", cookie);
  			request.setAttribute("interest", al2.get(0));
  			request.setAttribute("page", list.get(6));
  			request.setAttribute("student_id", list.get(7));
  			request.setAttribute("country", list.get(8));
  			request.setAttribute("country_code", list.get(9));
  			
  		request.setAttribute("jEventName", jEventName);
  		request.getRequestDispatcher("Free_Trial_popup.jsp").forward(request, response);
    }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_Info")){
      	String email="",name="",phone="",date="",IP="";
      	String id=request.getParameter("ID");
      	 list = new ArrayList<String>();
  		 list=getCallDetail(id);
  		 name=list.get(0);
  		 email=list.get(1);
  		 phone=list.get(2);
  		 date=list.get(3);
  		 IP=list.get(4);
  		 //cookie=list.get(5);
  		 ArrayList<String> al2=getCallInterest(email);
  		 
  		 	request.setAttribute("email", email);
  			request.setAttribute("name", name);
  			request.setAttribute("phone", phone);
  			request.setAttribute("date", date);
  			request.setAttribute("IP", IP);
  			request.setAttribute("ID", id);
  			//request.setAttribute("cookie", cookie);
  			request.setAttribute("interest", al2.get(0));
  			//request.setAttribute("page", list.get(6));
  			
  		request.setAttribute("jEventName", jEventName);
  		request.getRequestDispatcher("CallBack_popup.jsp").forward(request, response);
    }
    
    else if(jEventName != null && jEventName.equalsIgnoreCase("Co_Info")){
    	String email="",name="",phone="",date="",IP="";
    	String id=request.getParameter("ID");
    	 list = new ArrayList<String>();
		 list=getContactDetail(id);
		 name=list.get(0);
		 email=list.get(1);
		 phone=list.get(2);
		 date=list.get(3);
		 IP=list.get(4);
		 //cookie=list.get(5);
		 ArrayList<String> al2=getContactInterest(email);
		 
		 	request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			request.setAttribute("date", date);
			request.setAttribute("IP", IP);
			request.setAttribute("ID", id);
			//request.setAttribute("cookie", cookie);
			request.setAttribute("interest", al2.get(0));
			request.setAttribute("page", list.get(5));
			
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("Contact_popup.jsp").forward(request, response);
  }
    else if(jEventName != null && jEventName.equalsIgnoreCase("Chat_Info")){
    	String email="",name="",phone="",date="",IP="",country="";
    	String id=request.getParameter("ID");
    	 list = new ArrayList<String>();
		 list=getChatDetail(id);
		 name=list.get(0);
		 email=list.get(1);
		 phone=list.get(2);
		 date=list.get(3);
		// IP=list.get(4);
		 country=list.get(4);
		 ArrayList<String> al2=getFreeInterest(email);
		 
		 	request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			request.setAttribute("date", date);
			//request.setAttribute("IP", IP);
			request.setAttribute("ID", id);
			//request.setAttribute("cookie", cookie);
			request.setAttribute("interest", al2.get(0));
			request.setAttribute("page", list.get(5));
			request.setAttribute("Type", list.get(6));
			request.setAttribute("country", country);
			
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("ChatQuery_popup.jsp").forward(request, response);
  }
  else if(jEventName != null && jEventName.equalsIgnoreCase("Stu_Info")){
  	String email="",name="",phone="",date="",IP="",country="",country_code="";
  	String id=request.getParameter("ID");
  	 list = new ArrayList<String>();
		 list=getViewStudentDetail(id);
		 name=list.get(0);
		 email=list.get(1);
		 phone=list.get(2);
		 //date=list.get(3);
		 IP=list.get(4);
		 ArrayList<String> al2=getFreeInterest(email);
		 
		 	request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			//request.setAttribute("date", date);
			request.setAttribute("IP", IP);
			request.setAttribute("ID", id);
			//request.setAttribute("cookie", cookie);
			request.setAttribute("interest", al2.get(0));
			request.setAttribute("page", list.get(3));
			//request.setAttribute("Type", list.get(6));
			
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("ViewStudent_popup.jsp").forward(request, response);
}
  else if(jEventName != null && jEventName.equalsIgnoreCase("Un_Info")){
    	String email="",name="",phone="",date="",IP="",country="";
    	String id=request.getParameter("ID");
    	 list = new ArrayList<String>();
		 list=getUnallotedtDetail(id);
		 name=list.get(0);
		 email=list.get(1);
		 phone=list.get(2);
		 //date=list.get(3);
		 IP=list.get(3);
		// country=list.get(4);
		 ArrayList<String> al2=getFreeInterest(email);
		 
		 	request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("phone", phone);
			//request.setAttribute("date", date);
			request.setAttribute("IP", IP);
			request.setAttribute("ID", id);
			//request.setAttribute("cookie", cookie);
			request.setAttribute("interest", al2.get(0));
			//request.setAttribute("page", list.get(3));
			//request.setAttribute("Type", list.get(6));
			//request.setAttribute("country", country);
			
		request.setAttribute("jEventName", jEventName);
		request.getRequestDispatcher("Unalloted_popup.jsp").forward(request, response);
  }
      else if(jEventName != null && jEventName.equalsIgnoreCase("W_Class")){//add code for Webinar Class
  	    
	    String batch=request.getParameter("batch_id");
  	    String days=request.getParameter("days");
  	    int n=Integer.parseInt(days);
  	    String sessionId[]=new String[n];
  	    String email[]=new String[n];
  	    String meetingId[]=new String[n];
  	    String classLink[]=new String[n];
  	    for(int i=1;i<=n;i++)
  	    {
  	    	sessionId[i-1]=request.getParameter("session_id"+i);
  	    	email[i-1]=request.getParameter("email_id"+i);
  	  		meetingId[i-1]=request.getParameter("meeting_id"+i);
  	  		classLink[i-1]=request.getParameter("class_link"+i);
  	    }
  	
	    if(isClassDate(batch,sessionId))
	    {
	    	jMessage="Webinar Class already exist for this time and date";
    		request.setAttribute("jEventName", jEventName);
    		request.setAttribute("jMessage", jMessage);
    		request.getRequestDispatcher("C_W_Class.jsp").forward(request, response);
	    	
	    }
	    else
	    {
	    	if(setWebinarClass(batch,user,sessionId,email,meetingId,classLink))
	    	{
	    		jMessage="Webinar Class Successfully Created";
	    		request.setAttribute("jEventName", jEventName);
	    		request.setAttribute("jMessage", jMessage);
	    		request.getRequestDispatcher("C_W_Class.jsp").forward(request, response);
	    	}
	    }
			
  }
      else if(jEventName != null && jEventName.equalsIgnoreCase("U_W_Class")){//add code for Update webinar Class
    	  	String cId=request.getParameter("class_id");
    	    String email=request.getParameter("email_id");
    	    String batch=request.getParameter("batch_id");
    		String meetingId=request.getParameter("meeting_id");
    		String classLink=request.getParameter("class_link");
    		list = new ArrayList<String>();
    	    list.add(0,email);
    	    list.add(1, batch);
    	    list.add(2, meetingId);
    	    list.add(3, classLink);
    	    list.add(4, cId);
  	   
  	    	if(updateWebinarClass())
  	    	{
  	    		jMessage="Webinar Class Successfully Updated";
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("V_W_Class.jsp").forward(request, response);
  	    	}
  	    
  			
    }
      else if(jEventName != null && jEventName.equalsIgnoreCase("D_W_Class")){//add code for Delete webinar Class
  	  	
    	  String cId=request.getParameter("class_id");
	    	if(deleteWebinarClass(cId))
	    	{
	    		jMessage="Class Successfully Deleted";
	    		request.setAttribute("jEventName", jEventName);
	    		request.setAttribute("jMessage", jMessage);
	    		request.getRequestDispatcher("V_W_Class.jsp").forward(request, response);
	    	}
	    
			
  }
      else if(jEventName != null && jEventName.equalsIgnoreCase("C_W_Account")){//add code for Create webinar Account
    	    
    	  String email=request.getParameter("email");
    	  String pwd=request.getParameter("password");
    	
    	  String expiryDate=request.getParameter("expiry_date");
    	  String createdDate=request.getParameter("created_date");
    	  String status=request.getParameter("status");
    	  list=new ArrayList<String>();
    	  list.add(0,email);
    	  list.add(1,pwd);
    	  list.add(2,user);
    	  list.add(3,expiryDate);
    	  list.add(4,createdDate);
    	  list.add(5,status);
  	    if(isWebinarEmail(email))
  	    {
  	    	jMessage="This Webinar Account Already Exist";
      		request.setAttribute("jEventName", jEventName);
      		request.setAttribute("jMessage", jMessage);
      		request.getRequestDispatcher("W_Acount.jsp").forward(request, response);
  	    	
  	    }
  	    else
  	    {
  	    	if(setWebinarAccount())
  	    	{
  	    		jMessage="Webinar Account Successfully Created";
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("W_Acount.jsp").forward(request, response);
  	    	}
  	    }
  			
    }
        else if(jEventName != null && jEventName.equalsIgnoreCase("U_W_Account")){//add code for Update Webinar Account
        	 String email=request.getParameter("email");
       	  String pwd=request.getParameter("password");
       	  String expiryDate=request.getParameter("expiry_date");
       	  String createdDate=request.getParameter("created_date");
       	  String oldEmail=request.getParameter("o_email");
       	
       	  list=new ArrayList<String>();
       	  list.add(0,email);
       	  list.add(1,pwd);
       	  list.add(2,user);
       	  list.add(3,expiryDate);
       	  list.add(4,createdDate);
       	  list.add(5,oldEmail);
       	  
    	    if(updateWebinarAccount())
    	    {
    	    		jMessage="Webinar Account Successfully Updated";
    	    		ArrayList<String> al=new ArrayList<String>();
      	            al=getWebinarAccount();
      	            request.setAttribute("data", al);
    	    		request.setAttribute("jEventName", jEventName);
    	    		request.setAttribute("jMessage", jMessage);
    	    		request.getRequestDispatcher("VW_Acount.jsp").forward(request, response);
    	    }
    	    else
    	    {
    	    	jMessage="Changing Email Id Already Exist or enter date is not supported";
    	    	ArrayList<String> al=new ArrayList<String>();
  	            al=getWebinarAccount();
  	            request.setAttribute("data", al);
          		request.setAttribute("jEventName", jEventName);
          		request.setAttribute("jMessage", jMessage);
          		request.getRequestDispatcher("VW_Acount.jsp").forward(request, response);
    	    }
       	  
    			
      }
        else if(jEventName != null && jEventName.equalsIgnoreCase("D_W_Account")){//add code for Delete Webinar Account
      	  	
        	String email=request.getParameter("email");
  	    	if(deleteWebinarAccount(email))
  	    	{
  	    		jMessage="Webinar Account  deleted successfully";
  	    		ArrayList<String> al=new ArrayList<String>();
  	            al=getWebinarAccount();
  	            request.setAttribute("data", al);
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("VW_Acount.jsp").forward(request, response);
  	    	}
  	    	else
  	    	{
  	    		jMessage="Webinar Account has not  Deleted";
  	    		ArrayList<String> al=new ArrayList<String>();
  	            al=getWebinarAccount();
  	            request.setAttribute("data", al);
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("VW_Acount.jsp").forward(request, response);
  	    	}
  	    
  			
    }
 else if(jEventName != null && jEventName.equalsIgnoreCase("G_W_Account")){//add code for Delete Webinar Account
      	  	
        	
        	ArrayList<String> al=new ArrayList<String>();
	            al=getWebinarAccount();
	            request.setAttribute("data", al);
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.getRequestDispatcher("VW_Acount.jsp").forward(request, response);
        	
 }
        else if(jEventName != null && jEventName.equalsIgnoreCase("G_WebinarScheduleBatch")){//add code for Get Webinar Batch Session
        	String subjectId=request.getParameter("subject_id");
        	String batch=request.getParameter("batch");
      	
        	list = getWebinarScheduleBatch(batch,subjectId);
        	out.println("<div class='table-scrollable'>");
        	out.println("<table class='table table-striped table-bordered table-hover' id='sample_3'><thead><tr><th scope='col'>S.No.</th><th scope='col'>Created By</th><th scope='col'>Batch</th><th scope='col'>Module</th><th scope='col'>Date</th><th scope='col'>Webinar Email</th><th scope='col'>Meeting Id</th><th scope='col'>URL</th><th scope='col'>Edit</th><th scope='col'>Delete</th></tr></thead>");
        	out.println("<tbody>");
        	for(int i=0;i<list.size();i++)
        	{
      		 int j=i+1;
      		 String s[]=list.get(i).split("abczxy");
      		 out.println("<tr class='odd gradeX'>");
      		 out.println("<td>"+j+"</td>");
      		 out.println("<td>"+s[2]+"</td>");
      		 out.println("<td>"+s[3]+"</td>");
      		 out.println("<td>"+s[4]+"</td>");
      		 out.println("<td>"+s[5]+"</td>");
      		 out.println("<td>"+s[6]+"</td>");
      		 out.println("<td>"+s[7]+"</td>");
      		 out.println("<td>"+s[8]+"</td>");
      		 out.println("<td><a class='fancybox fancybox.iframe' href='LoginServlet?jEventName=Edit_ScheduleBatch&id="+s[0]+"&subject_id="+s[9]+"&batch="+s[3]+"&module="+s[4]+"&email="+s[6]+"&date="+s[5]+"&meeting_id="+s[7]+"&url="+s[8]+"'>Edit</a></td>");
      		 out.println("<td><a  href='LoginServlet?jEventName=Delete_ScheduleBatch&id="+s[0]+"&subject_id="+s[9]+"&batch="+s[3]+"' onclick='return check();'>Delete</a></td>");
      		 out.println("</tr");
        	}
        	out.println("</tbody></table>");
        	out.println("</div>");
  			
    }
      
     
      
        else if(jEventName != null && jEventName.equalsIgnoreCase("Batch_Email")){//add code for Batch Email
      	  	
        	String batchId=request.getParameter("batch_id");
        	String time=request.getParameter("time");
        	ArrayList<String> al=getStudentBatch(batchId);
  	    	if(al.isEmpty())
  	    	{
  	    		jMessage="There is no more session created for this batch "+batchId;
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("V_Batch.jsp").forward(request, response);
  	    	}
  	    	else
  	    	{
  	    		String s[]=al.get(0).split("/");
  	    		String sessionId=s[0];
  	    		String sessionDate=s[1];
  	    		String module=s[2];
  	    		String s2[]=getWebinar(sessionId);
  	    		
  	    		if(s2[0]==null || s2[0].equals("null"))
  	    		{
  	    			jMessage="There is no more Webinar link for this batch"+batchId;
  	  	    		request.setAttribute("jEventName", jEventName);
  	  	    		request.setAttribute("jMessage", jMessage);
  	  	    		request.getRequestDispatcher("V_Batch.jsp").forward(request, response);
  	    		}
  	    		else
  	    		{
  	    			String meetingId=s2[0];
  	    			String url=s2[1];
  	    			ArrayList<String> al3=getStudentDetail(batchId);
  	    			if(al3.isEmpty())
  	    			{
  	    				jMessage="There is no student alloted for this batch"+batchId;
  	  	  	    		request.setAttribute("jEventName", jEventName);
  	  	  	    		request.setAttribute("jMessage", jMessage);
  	  	  	    		request.getRequestDispatcher("V_Batch.jsp").forward(request, response);
  	    			}
  	    			else
  	    			{
  	    				request.setAttribute("time", time);
  	    				request.setAttribute("session_date", sessionDate);
  	    				request.setAttribute("module", module);
  	    				request.setAttribute("meeting_id", meetingId);
  	    				request.setAttribute("url", url);
  	    				request.setAttribute("student", al3);
  	    				request.setAttribute("jEventName", jEventName);
  	    				request.getRequestDispatcher("BatchEmail.jsp").forward(request, response);
  	    			}
  	    		}
  	    		
  	    		
  	    	}		
    }
else if(jEventName != null && jEventName.equalsIgnoreCase("Student_Email")){//add code for Student Email
      	  	
        	String batchId=request.getParameter("batch_id");
        	String email=request.getParameter("email");
        	String name=request.getParameter("name");
        	String time=request.getParameter("time");
        	ArrayList<String> al=getStudentBatch(batchId);
  	    	if(al.isEmpty())
  	    	{
  	    		jMessage="There is no more session created for this batch "+batchId;
  	    		request.setAttribute("jEventName", jEventName);
  	    		request.setAttribute("jMessage", jMessage);
  	    		request.getRequestDispatcher("View_Student.jsp").forward(request, response);
  	    	}
  	    	else
  	    	{
  	    		String s[]=al.get(0).split("/");
  	    		String sessionId=s[0];
  	    		String sessionDate=s[1];
  	    		String module=s[2];
  	    		String s2[]=getWebinar(sessionId);
  	    		
  	    		if(s2[0]==null || s2[0].equals("null"))
  	    		{
  	    			jMessage="There is no more Webinar link for this batch"+batchId;
  	  	    		request.setAttribute("jEventName", jEventName);
  	  	    		request.setAttribute("jMessage", jMessage);
  	  	    		request.getRequestDispatcher("View_Student.jsp").forward(request, response);
  	    		}
  	    		else
  	    		{
  	    			String meetingId=s2[0];
  	    			String url=s2[1];
  	    			ArrayList<String> al3=new ArrayList<String>();
  	    			al3.add(0,name+"/"+email);
  	    				request.setAttribute("session_date", sessionDate);
  	    				request.setAttribute("time", time);
  	    				request.setAttribute("module", module);
  	    				request.setAttribute("meeting_id", meetingId);
  	    				request.setAttribute("url", url);
  	    				request.setAttribute("student", al3);
  	    				request.setAttribute("jEventName", jEventName);
  	    				request.getRequestDispatcher("BatchEmail.jsp").forward(request, response);
  	    			
  	    		}
  	    		
  	    		
  	    	}
  	    
  			
    }
		else if(jEventName != null && jEventName.equalsIgnoreCase("super_admin")){//add code for Check Super Admin
				String email=request.getParameter("username");
				String password=request.getParameter("password");
				boolean check=checkSuperAdmin(email,password);
				if(check)
				{
					//System.out.println("done");
					out.println("done");
				}
				else
				{
					//System.out.println("error");
					out.println("error");
				}
	
			}
		else if(jEventName != null && jEventName.equalsIgnoreCase("Upload_Template")){//add code for Upload Template
	  	    
		    String title=request.getParameter("title");
	  	    String description=request.getParameter("description");
	  	    String url=request.getParameter("url");
	  	  
	  	
	  	   
	  	
		    if(setTemplate(title, description, url))
		    {
		    	list=getTemplate();
		    	request.setAttribute("data", list);
		    	jMessage="Template has been created successfull. ";
	    		request.setAttribute("jEventName", jEventName);
	    		request.setAttribute("jMessage", jMessage);
	    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
		    	
		    }
		    else
		    {
		    	list=getTemplate();
		    	request.setAttribute("data", list);
		    		jMessage="Template has not created";
		    		request.setAttribute("jEventName", jEventName);
		    		request.setAttribute("jMessage", jMessage);
		    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
		    	
		    }
				
	  }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("Update_Template")){//add code for Update Template
	    	    String title=request.getParameter("title");
		  	    String description=request.getParameter("description");
		  	    String url=request.getParameter("url");
		  	    String id=request.getParameter("id");
	  	    	if(updateTemplate(id, title, description, url))
	  	    	{
	  	    		list=getTemplate();
			    	request.setAttribute("data", list);
	  	    		jMessage="Template has been updated successful.";
	  	    		request.setAttribute("jEventName", jEventName);
	  	    		request.setAttribute("jMessage", jMessage);
	  	    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
	  	    	}
	  	    	else
	  	    	{
	  	    		list=getTemplate();
			    	request.setAttribute("data", list);
	  	    		jMessage="Template has not updated";
	  	    		request.setAttribute("jEventName", jEventName);
	  	    		request.setAttribute("jMessage", jMessage);
	  	    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
	  	    	}
	  	    
	  			
	    }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("Edit_Template")){//add code for Edit Template
	    	  	
	    	  	String title=request.getParameter("title");
		  	    String description=request.getParameter("description");
		  	    String url=request.getParameter("url");
		  	    String id=request.getParameter("id");
	  	    	
	  	    		request.setAttribute("jEventName", jEventName);
	  	    		request.setAttribute("id", id);
	  	    		request.setAttribute("title", title);
	  	    		request.setAttribute("description", description);
	  	    		request.setAttribute("url", url);
	  	    		list=getTemplate();
			    	request.setAttribute("data", list);
	  	    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
	  	    	
	  	    
	  			
	    }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("Delete_Template")){//add code for Delete Template
	  	  	
	    	  	String id=request.getParameter("id");
		    	if(deleteTemplate(id))
		    	{
		    		list=getTemplate();
			    	request.setAttribute("data", list);
		    		jMessage="Template has been deleted successful.";
		    		request.setAttribute("jEventName", jEventName);
		    		request.setAttribute("jMessage", jMessage);
		    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
		    	}
		    	else
		    	{
		    		list=getTemplate();
			    	request.setAttribute("data", list);
		    		jMessage="Template has not deleted.";
		    		request.setAttribute("jEventName", jEventName);
		    		request.setAttribute("jMessage", jMessage);
		    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
		    	}
		    
				
	      }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("G_Template")){//add code for Get Template
		  	  	
	    		list=getTemplate();
		    	request.setAttribute("data", list);
	    		request.setAttribute("jEventName", jEventName);
	    		request.getRequestDispatcher("UploadTemplate.jsp").forward(request, response);
	      }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("C_FAQ")){//add code for Create FAQ
		  	    
			    String course=request.getParameter("course");
		  	    String question=request.getParameter("question");
		  	    String answer=request.getParameter("answer");
		  	    String faq_type=request.getParameter("faq_type");
		  	    String title=request.getParameter("title");
		  	    String keyword=request.getParameter("keyword");
		  	    String description=request.getParameter("description");
		  	    Faq fq=new Faq();
		  	    fq.setQuestionName(question);
		  	    fq.setAnswer(answer);
		  	    fq.setCourse(course);
		  	    fq.setFaqType(faq_type);
		  	    fq.setTitle(title);
		  	    fq.setKeyword(keyword);
		  	    fq.setDescription(description);
		  	
		  	   if(checkFAQ(question))
		  	   {
		  		   	jMessage="This question is already exist please give different.";
		    		request.setAttribute("jEventName", jEventName);
		    		request.setAttribute("jMessage", jMessage);
		    		request.setAttribute("course", course);
		    		request.setAttribute("question", question);
		    		request.setAttribute("answer", answer);
		    		request.setAttribute("faq_type", faq_type);
		    		request.setAttribute("title", title);
		    		request.setAttribute("keyword", keyword);
		    		request.setAttribute("description", description);
		    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
		  	   }
		  	   else
		  	   {
		  	
				    if(setFAQ(fq))
				    {
				    	jMessage="FAQ has been created successfully. ";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.setAttribute("course", "");
			    		request.setAttribute("question", "");
			    		request.setAttribute("answer", "");
			    		request.setAttribute("faq_type", "");
			    		request.setAttribute("title", "");
			    		request.setAttribute("keyword", "");
			    		request.setAttribute("description", "");
			    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
				    	
				    }
				    else
				    {
				    		jMessage="FAQ has not created check the value you insert.";
				    		request.setAttribute("jEventName", jEventName);
				    		request.setAttribute("jMessage", jMessage);
				    		request.setAttribute("course", course);
				    		request.setAttribute("question", question);
				    		request.setAttribute("answer", answer);
				    		request.setAttribute("faq_type", faq_type);
				    		request.setAttribute("title", title);
				    		request.setAttribute("keyword", keyword);
				    		request.setAttribute("description", description);
				    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
				    	
				    }
		  	   }
					
		  }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("U_FAQ")){//add code for U_FAQ
		    	    
			  	    String id=request.getParameter("id");
			  	    String course=request.getParameter("course");
			  	    String question=request.getParameter("question");
			  	    String oldQuestion=request.getParameter("oldQuestion");
			  	    String answer=request.getParameter("answer");
			  	    String faq_type=request.getParameter("faq_type");
			  	    String title=request.getParameter("title");
			  	    String keyword=request.getParameter("keyword");
			  	    String description=request.getParameter("description");
			  	    Faq fq=new Faq();
			  	    fq.setQuestionName(question);
			  	    fq.setAnswer(answer);
			  	    fq.setCourse(course);
			  	    fq.setFaqType(faq_type);
			  	    fq.setTitle(title);
			  	    fq.setKeyword(keyword);
			  	    fq.setDescription(description);
			  	    fq.setQuestionId(id);
			  	    if(checkFAQ(question, oldQuestion))
			  	    {
			  	    	jMessage="This question is already exist please give different.";
		  	    		request.setAttribute("jEventName", jEventName);
		  	    		request.setAttribute("jMessage", jMessage);
		  	    		request.setAttribute("course", course);
			    		request.setAttribute("question", question);
			    		request.setAttribute("answer", answer);
			    		request.setAttribute("faq_type", faq_type);
			    		request.setAttribute("id", id);
			    		request.setAttribute("title", title);
			    		request.setAttribute("keyword", keyword);
			    		request.setAttribute("description", description);
		  	    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
			  	    }
			  	    else
			  	    {
				  	    	if(updateFAQ(fq))
				  	    	{
				  	    		List<Faq> list=new ArrayList<Faq>();
				  	    		list=getFAQ();
						    	request.setAttribute("data", list);
				  	    		jMessage="FAQ has been updated successfully.";
				  	    		request.setAttribute("jEventName", jEventName);
				  	    		request.setAttribute("jMessage", jMessage);
				  	    		request.getRequestDispatcher("ViewFAQ.jsp").forward(request, response);
				  	    	}
				  	    	else
				  	    	{
				  	    		jMessage="FAQ has not updated please check the value you entered";
				  	    		request.setAttribute("jEventName", jEventName);
				  	    		request.setAttribute("jMessage", jMessage);
				  	    		request.setAttribute("course", course);
					    		request.setAttribute("question", question);
					    		request.setAttribute("answer", answer);
					    		request.setAttribute("faq_type", faq_type);
					    		request.setAttribute("id", id);
					    		request.setAttribute("title", title);
					    		request.setAttribute("keyword", keyword);
					    		request.setAttribute("description", description);
				  	    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
				  	    	}
			  	    }
		  	    
		  			
		    }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("E_FAQ")){//add code for E_FAQ
		    	  	
		    	   
			  	    String id=request.getParameter("id");
			  	    List<Faq> list=new ArrayList<Faq>();
			  	    list=getFAQDetail(id);
			  	    
			  	    String question=list.get(0).getQuestionName();
			  	    String answer=list.get(0).getAnswer();
			  	    String course=list.get(0).getCourse();
			  	    String faq_type=list.get(0).getFaqType();
			  	    String title=list.get(0).getTitle();
			  	    String keyword=list.get(0).getKeyword();
			  	    String description=list.get(0).getDescription();
		  	    	
		  	    		request.setAttribute("jEventName", jEventName);
		  	    		request.setAttribute("id", id);
		  	    		request.setAttribute("course", course);
			    		request.setAttribute("question", question);
			    		request.setAttribute("answer", answer);
			    		request.setAttribute("faq_type", faq_type);
			    		request.setAttribute("title", title);
			    		request.setAttribute("keyword", keyword);
			    		request.setAttribute("description", description);
		  	    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
		  	    	
		  	    
		  			
		    }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("Delete_FAQ")){//add code for Delete_FAQ
		  	  	
		    	  	String id=request.getParameter("id");
		    	  	List<Faq> list=new ArrayList<Faq>();
			    	if(deleteFAQ(id))
			    	{
			    		
			    		list=getFAQ();
				    	request.setAttribute("data", list);
			    		jMessage="FAQ has been deleted successful.";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.getRequestDispatcher("ViewFAQ.jsp").forward(request, response);
			    	}
			    	else
			    	{
			    		list=getFAQ();
				    	request.setAttribute("data", list);
			    		jMessage="FAQ has not deleted.";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.getRequestDispatcher("ViewFAQ.jsp").forward(request, response);
			    	}
			    
					
		  }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("V_FAQ")){//add code for V_FAQ
	    	  List<Faq> list=new ArrayList<Faq>();
		    		list=getFAQ();
			    	request.setAttribute("data", list);
		    		request.setAttribute("jEventName", jEventName);
		    		request.getRequestDispatcher("ViewFAQ.jsp").forward(request, response);
	      	}
      
	      else if(jEventName != null && jEventName.equalsIgnoreCase("FAQ_ByCourse")){//add code for FAQ_ByCourse
		  	  	String course=request.getParameter("course");
		  	  List<Faq> list=new ArrayList<Faq>();
		  	  	if(course!=null && course.equals(""))
		  	  	  list=getFAQ();
		  	  	else 
	    		  list=getFAQ(course);
		    	request.setAttribute("data", list);
	    		out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' ><thead>");
	    		out.println("<tr><th scope='col'>S.No.</th><th scope='col'>Question No.</th><th scope='col'>Question</th><th scope='col'>Answer</th><th scope='col'>Course</th><th scope='col'>FAQ Type</th><th scope='col'>Title</th><th scope='col'>Keyword</th><th scope='col'>Description</th><th scope='col'>Date</th><th scope='col'>View Review</th><th scope='col'>Edit</th><th scope='col'>Delete</th></tr>");
	    		out.println("</thead><tbody>");
	    		for(int i=0;i<list.size();i++)
				{
						int j=i+1;
						out.println("<tr class='odd gradeX'>");
						out.println("<td>"+j+"</td>");
						out.println("<td>"+list.get(i).getQuestionId()+"</td>");
						out.println("<td>"+list.get(i).getQuestionName()+"</td>");
						out.println("<td>"+list.get(i).getAnswer()+"</td>");
						out.println("<td>"+list.get(i).getCourse()+"</td>");
						out.println("<td>"+list.get(i).getFaqType()+"</td>");
						out.println("<td>"+list.get(i).getTitle()+"</td>");
						out.println("<td>"+list.get(i).getKeyword()+"</td>");
						out.println("<td>"+list.get(i).getDescription()+"</td>");
						out.println("<td>"+list.get(i).getDate()+"</td>");
						out.println("<td><a  href='LoginServlet?jEventName=FAQ_Review&id="+list.get(i).getQuestionId()+"'>View Reviews</a></td>");
						out.println("<td><a  href='LoginServlet?jEventName=E_FAQ&id="+list.get(i).getQuestionId()+"'>Edit</a></td>");
						out.println("<td><a  href='LoginServlet?jEventName=Delete_FAQ&id="+list.get(i).getQuestionId()+"' onclick='return check();'>Delete</a></td>");
						out.println("</tr>");
				}
	    		out.println("</tbody></table></div>");
	    		
    	}
	      else if(jEventName != null && jEventName.equalsIgnoreCase("FAQ_Review")){//add code for View FAQ Review
		  	  	
	    	  	String id=request.getParameter("id");
	    	  	List<Faq> list=new ArrayList<Faq>();
	    		list=getFAQInfo(id);
	    		 List<LiveDemo> al = new ArrayList<LiveDemo>();
	    		al=getFAQReviews(id);
		    	request.setAttribute("data", list);
		    	request.setAttribute("data2", al);
	    		request.setAttribute("jEventName", jEventName);
	    		request.getRequestDispatcher("FAQReviews.jsp").forward(request, response);
    	}
	      else if(jEventName != null && jEventName.equalsIgnoreCase("C_MainFAQ")){//add code for Create Main FAQ
		  	    
			    String course=request.getParameter("course");
		  	    String name=request.getParameter("name");
		  	    String designation=request.getParameter("designation");
		  	    String star=request.getParameter("star");
		  	    String review=request.getParameter("review");
		  	    String image=request.getParameter("image");
		  	   
		  	    Faq fq=new Faq();
		  	 
		  	    fq.setCourse(course);
		  	    fq.setDesignation(designation);
		  	    fq.setName(name);
		  	    fq.setReview(review);
		  	    fq.setImage(image);
		  	    fq.setStar(star);
		  	
				    if(setMainFAQ(fq))
				    {
				    	jMessage="FAQ has been created successfully. ";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.setAttribute("course", "");
			    		request.setAttribute("name", "");
			    		request.setAttribute("designation", "");
			    		request.setAttribute("star", "");
			    		request.setAttribute("review", "");
			    		request.setAttribute("image", "");
			    		request.getRequestDispatcher("CreateMainFAQ.jsp").forward(request, response);
				    	
				    }
				    else
				    {
				    		jMessage="FAQ has not created check the value you insert.";
				    		request.setAttribute("jEventName", jEventName);
				    		request.setAttribute("jMessage", jMessage);
				    		request.setAttribute("course", course);
				    		request.setAttribute("name", name);
				    		request.setAttribute("designation", designation);
				    		request.setAttribute("star", star);
				    		request.setAttribute("review", review);
				    		request.setAttribute("image", image);
				    		request.getRequestDispatcher("CreateMainFAQ.jsp").forward(request, response);
				    	
				    }
					
		  }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("U_MainFAQ")){//add code for Update Main FAQ
		    	    
			  	    String id=request.getParameter("id");
			  	    String course=request.getParameter("course");
			  	    String name=request.getParameter("name");
			  	    String designation=request.getParameter("designation");
			  	    String star=request.getParameter("star");
			  	    String review=request.getParameter("review");
			  	    String image=request.getParameter("image");
			  	   
			  	    Faq fq=new Faq();
			  	 
			  	    fq.setCourse(course);
			  	    fq.setDesignation(designation);
			  	    fq.setName(name);
			  	    fq.setReview(review);
			  	    fq.setImage(image);
			  	    fq.setStar(star);
			  	    fq.setFaqId(id);
			  	
				  	    	if(updateMainFAQ(fq))
				  	    	{
				  	    		List<Faq> list=new ArrayList<Faq>();
				  	    		list=getMainFAQ();
						    	request.setAttribute("data", list);
				  	    		jMessage="FAQ has been updated successfully.";
				  	    		request.setAttribute("jEventName", jEventName);
				  	    		request.setAttribute("jMessage", jMessage);
				  	    		request.getRequestDispatcher("ViewMainFAQ.jsp").forward(request, response);
				  	    	}
				  	    	else
				  	    	{
				  	    		jMessage="FAQ has not updated please check the value you entered";
				  	    		request.setAttribute("jEventName", jEventName);
				  	    		request.setAttribute("jMessage", jMessage);
				  	    		
					    		request.setAttribute("id", id);
					    		request.setAttribute("course", course);
					    		request.setAttribute("name", name);
					    		request.setAttribute("designation", designation);
					    		request.setAttribute("star", star);
					    		request.setAttribute("review", review);
					    		request.setAttribute("image", image);
				  	    		request.getRequestDispatcher("CreateFAQ.jsp").forward(request, response);
				  	    	}
		  	    
		  			
		    }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("E_MainFAQ")){//add code for Edit Main FAQ
		    	  	
		    	   
			  	    String id=request.getParameter("id");
			  	    List<Faq> list=new ArrayList<Faq>();
			  	    list=getMainFAQDetail(id);
			  	    
			  	    String name=list.get(0).getName();
			  	    String designation=list.get(0).getDesignation();
			  	    String course=list.get(0).getCourse();
			  	    String star=list.get(0).getStar();
			  	    String review=list.get(0).getReview();
			  	    String image=list.get(0).getImage();
		  	    	
		  	    		request.setAttribute("jEventName", jEventName);
		  	    		request.setAttribute("id", id);
		  	    		request.setAttribute("course", course);
			    		request.setAttribute("name", name);
			    		request.setAttribute("designation", designation);
			    		request.setAttribute("star", star);
			    		request.setAttribute("review", review);
			    		request.setAttribute("image", image);
		  	    		request.getRequestDispatcher("CreateMainFAQ.jsp").forward(request, response);
		  	    	
		  	    
		  			
		    }
		      else if(jEventName != null && jEventName.equalsIgnoreCase("Delete_MainFAQ")){//add code for Delete Main FAQ
		  	  	
		    	  	String id=request.getParameter("id");
		    	  	List<Faq> list=new ArrayList<Faq>();
			    	if(deleteMainFAQ(id))
			    	{
			    		
			    		list=getMainFAQ();
				    	request.setAttribute("data", list);
			    		jMessage="FAQ has been deleted successful.";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.getRequestDispatcher("ViewMainFAQ.jsp").forward(request, response);
			    	}
			    	else
			    	{
			    		list=getMainFAQ();
				    	request.setAttribute("data", list);
			    		jMessage="FAQ has not deleted.";
			    		request.setAttribute("jEventName", jEventName);
			    		request.setAttribute("jMessage", jMessage);
			    		request.getRequestDispatcher("ViewMainFAQ.jsp").forward(request, response);
			    	}
			    
					
		  }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("V_MainFAQ")){//add code for View Main FAQ
	    	  List<Faq> list=new ArrayList<Faq>();
		    		list=getMainFAQ();
			    	request.setAttribute("data", list);
		    		request.setAttribute("jEventName", jEventName);
		    		request.getRequestDispatcher("ViewMainFAQ.jsp").forward(request, response);
	      	}
    
	      else if(jEventName != null && jEventName.equalsIgnoreCase("MainFAQ_ByCourse")){//add code for Main FAQ By Course
		  	  	String course=request.getParameter("course");
		  	  List<Faq> list=new ArrayList<Faq>();
		  	  	if(course!=null && course.equals(""))
		  	  	  list=getMainFAQ();
		  	  	else 
	    		  list=getMainFAQ(course);
		    	request.setAttribute("data", list);
	    		out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' ><thead>");
	    		out.println("<tr><th scope='col'>S.No.</th><th scope='col'>Name</th><th scope='col'>Designation</th><th scope='col'>Course</th><th scope='col'>Review</th><th scope='col'>Star</th><th scope='col'>Image</th><th scope='col'>Edit</th><th scope='col'>Delete</th></tr>");
	    		out.println("</thead><tbody>");
	    		for(int i=0;i<list.size();i++)
				{
						int j=i+1;
						out.println("<tr class='odd gradeX'>");
						out.println("<td>"+j+"</td>");
						out.println("<td>"+list.get(i).getName()+"</td>");
						out.println("<td>"+list.get(i).getDesignation()+"</td>");
						out.println("<td>"+list.get(i).getCourse()+"</td>");
						out.println("<td>"+list.get(i).getReview()+"</td>");
						out.println("<td>"+list.get(i).getStar()+"</td>");
						out.println("<td>"+list.get(i).getImage()+"</td>");
						out.println("<td><a  href='LoginServlet?jEventName=E_MainFAQ&id="+list.get(i).getFaqId()+"'>Edit</a></td>");
						out.println("<td><a  href='LoginServlet?jEventName=Delete_MainFAQ&id="+list.get(i).getFaqId()+"' onclick='return check();'>Delete</a></td>");
						out.println("</tr>");
				}
	    		out.println("</tbody></table></div>");
	    		
  	}
	 else if(jEventName != null && jEventName.equalsIgnoreCase("NewBook")){//add code for AdminLogin
          String bookId = request.getParameter("book_id").trim();
          String bookName = request.getParameter("book_name");
          int createdId=addNewBooks(bookId,bookName);
		  if(createdId==-1)
		  {
			  request.setAttribute("jEventName", jEventName);
			  request.setAttribute("jMessage", "error");
        	  request.getRequestDispatcher("add_book.jsp").forward(request, response);
		  }
		  else if(createdId==1)
		  {
			  request.setAttribute("jEventName", jEventName);
			  request.setAttribute("jMessage", "success");
        	  request.getRequestDispatcher("add_book.jsp").forward(request, response);
		  }
		  else
		  {
			  request.setAttribute("jEventName", jEventName);
			  request.setAttribute("jMessage", "exist");
        	  request.getRequestDispatcher("add_book.jsp").forward(request, response);
		  }
      }
   } 
  @Override
  public void destroy(){ /* do nothing.*/ }  
  private Connection getConnection(){
	  Connection con= null;
	  try{
		  con=new MyConnection().getConnection();
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
  private boolean isAllowed(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  String pass="";
		  String sql = "SELECT PASSWORD FROM lms_main_admin WHERE USER_NAME= ? and PASSWORD=? and (USER_TYPE=? or USER_TYPE=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, "admin");
				ps.setString(4, "super admin");
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					pass= rs.getString("PASSWORD");
				}
				if(pass.equals(list.get(1)))
					count=1;
				if(count != 0){
					
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, list.get(0));
					ps2.setString(2, "LN");
					ps2.executeUpdate();	
					}
			}
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
  }
  public String userNature(String user){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	 
	  String nature="";
	  try
	  {
		  con = getConnection(); 
		  
		  String sql = "SELECT NATURE FROM admin_nature WHERE USER_NAME= ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, user);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					nature= rs.getString("NATURE");
				}
				if(nature.equalsIgnoreCase(""))
					nature="admin";
				

			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return nature;
  }
  private boolean isLockAllowed(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  String pass="";
		  String sql = "SELECT PASSWORD FROM lms_main_admin WHERE USER_NAME= ? and PASSWORD=? and (USER_TYPE=? or USER_TYPE=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, "admin");
				ps.setString(4, "super admin");
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					pass= rs.getString("PASSWORD");
				}
				if(pass.equals(list.get(1)))
					count=1;
			}
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
  }
  private boolean logout(String user){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= -1;
	  try
	  {
		  con = getConnection(); 
				
					
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, user);
					ps2.setString(2, "LO");
					count=ps2.executeUpdate();	
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
  }
  private boolean checkPassword(String user,String cpassword){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
				
					
					String sql2 = "select count(1) from lms_main_admin where PASSWORD=? and USER_NAME=? ";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, cpassword);
					ps2.setString(2, user);
					ResultSet rs=ps2.executeQuery();
					while(rs.next())
					{
					count=rs.getInt(1);	
					}
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
  }
  private boolean changePassword(String user,String password){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= -1;
	  try
	  {
		  con = getConnection(); 
				
					
					String sql2 = "update lms_main_admin set PASSWORD=? where USER_NAME=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, password);
					ps2.setString(2, user);
					
					count=ps2.executeUpdate();	
					if(count!=-1)
					{
						String sql = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, user);
						ps.setString(2, "CP");
						ps.executeUpdate();	
					}
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != -1)
		  return true;
	  else 
		  return false;
  }
  public String getAdminDetail(String user){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  String s="";
	  try
	  {
		  con = getConnection(); 
		  String sql2 = "select USER_TYPE from lms_main_admin where USER_NAME=?";
		  PreparedStatement ps2 = con.prepareStatement(sql2);
		  ps2.setString(1, user);
					
			ResultSet rs=ps2.executeQuery();
			while(rs.next())
			{
				
				s=rs.getString("USER_TYPE");
			}
					
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return s;
  }
  public int getStudentId(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int n= 0;
	  try
	  {
		  con = getConnection(); 
		  String sql = "SELECT STUDENT_ID FROM student_login ORDER BY STUDENT_ID DESC limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					n= rs.getInt(1);
				}
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  n++;
	 return n;
  }
  public ArrayList<String> getStudentEmailName(String studentId)
  {
	  ArrayList<String> al= new ArrayList<String>();
	  Connection con = null;
	  try
	  {
		  con = getConnection(); 
		  String sql = "SELECT Email_Id,Student_Name FROM student_login where STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, studentId);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					al.add(rs.getString("Email_Id"));
					al.add(rs.getString("Student_Name"));
				}
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	 return al;
	  
  }
  public int getTeacherId(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int n= 0;
	  try
	  {
		  con = getConnection(); 
		  String sql = "SELECT TEACHER_ID FROM lms_main_teacher ORDER BY TEACHER_ID DESC limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					n= rs.getInt(1);
				}
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  n++;
	 return n;
  }
  public int getSalesId(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int n= 0;
	  try
	  {
		  con = getConnection(); 
		  String sql = "SELECT SALES_PERSON_ID FROM lms_main_salesperson ORDER BY SALES_PERSON_ID DESC limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					n= rs.getInt(1);
				}
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  n++;
	 return n;
  }
  private boolean sendTeacher(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	//int n=-1;
	try{
		con = getConnection(); 
		String sql = "INSERT INTO lms_main_teacher VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			 DateFormat formatter ; 
		     formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(list.get(4));
		     java.sql.Date d=new java.sql.Date(date.getTime());
		     
			ps.setString(1, list.get(0));
			ps.setString(2, list.get(1));
			ps.setString(3, list.get(2));
			ps.setString(4, list.get(2));
			ps.setString(5, list.get(3));
			ps.setDate(6, d);
			ps.setString(7, list.get(5));
			ps.setString(8, list.get(6));
			ps.setString(9, list.get(7));
			ps.setString(10, list.get(8));
			
			value = ps.executeUpdate();	
			if(value>0)
			{
				String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setString(1, username);
				ps2.setString(2, "CP");
				ps2.executeUpdate();	
			}
		
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
  private boolean sendStudent(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	//int n=-1;
	try{
		con = getConnection(); 
		String sql = "INSERT INTO student_login(STUDENT_NAME,STUDENT_PASSWORD,EMAIL_ID,PHONE) VALUES(?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1, list.get(1)); 
			ps.setString(2, list.get(0));
			ps.setString(3, list.get(2));
			ps.setString(4, list.get(3));
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
  private boolean sendQuery(){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	try{
        //changing timestamp zone starts 
			//java.util.Date date = new java.util.Date();				    	        
		 	//java.util.Date newDate = new java.util.Date(timestamp.getTime() );
		 //changing timestamp zone ends 
		con = getConnection(); 
		String sql = "INSERT INTO lms_teacher_query(TEACHER_ID, QUERY_STRING) VALUES(?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1, list.get(0));
			ps.setString(2, list.get(1));
			value = ps.executeUpdate();				
		}
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != -1)return true;else return false;
  }
  private boolean checkTeacherId(String teacherId){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_main_teacher WHERE TEACHER_ID= ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, teacherId);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean checkTeacherUser(String user){
	Connection con = null;
	ResultSet rs = null;
	int countTID = 0;
	try{
		con = getConnection(); 
		String sql = "SELECT COUNT(1) FROM lms_main_teacher WHERE USERNAME= ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, user);
		rs = ps.executeQuery();
		while(rs.next()){
			//System.out.println("countTID:  "+countTID);
			countTID = rs.getInt(1);
		}
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(countTID != 0)return true;else return false;
  }
  private boolean checkStudentUser(String email){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM student_login WHERE EMAIL_ID = ?";
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
		}finally{closeConnection(con);}
	
		if(countTID != 0)return true;else return false;
	  }
  private ArrayList<String> getQueries(String teacherId){
	ArrayList<String> aList = new ArrayList<String>();	
	Connection con = null;
	ResultSet rs = null;
	try{
		con = getConnection(); 
		String sql = "SELECT QUERY_STRING, ACK_QUERY,QUERY_TIMESTAMP FROM lms_teacher_query WHERE TEACHER_ID = ? ORDER BY QUERY_TIMESTAMP DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, teacherId);
		rs = ps.executeQuery();
		int i=0;
		while(rs.next()){
			aList.add(i,rs.getInt(2)+"/"+rs.getString(1)+"/"+rs.getString(3));
			i++;
		}		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}	
	return aList;
  }
  private ArrayList<String> getTeacherById(String teacherId){
	  ArrayList<String> aList = new ArrayList<String>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql = "SELECT NAME, USERNAME, SUBJECT FROM lms_main_teacher WHERE TEACHER_ID = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, teacherId);
			rs = ps.executeQuery();
			while(rs.next()){
				aList.add(0,rs.getString(1));
				aList.add(1,rs.getString(2));
				aList.add(2,rs.getString(3));
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList;
	  
  }
  private ArrayList<String> getLogChapters()
  {
	  ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql="SELECT N.CHAPTER_ID,N.CHAPTER_NAME,N.DESCRIPTIONS, B.BATCH_ID, S.SUBJECT_NAME FROM (((chapter_added AS CA JOIN new_chapter AS N ON N.CHAPTER_ID = CA.CHAPTER_ID)	JOIN subjects AS S ON CA.SUBJECT_ID = S.SUBJECT_ID) JOIN lms_teacher_alloted AS B ON  B.BATCH_ID = CA.BATCH_ID) WHERE CA.Teacher_Id =?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,list.get(0));
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString("B.BATCH_ID")+"/"+rs.getString("S.SUBJECT_NAME")+"/"+rs.getString("N.CHAPTER_ID")+"/"+rs.getString("N.CHAPTER_NAME")+"/"+rs.getString("N.DESCRIPTIONS"));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList; 
  }
  private ArrayList<String> getLogTests()
  {
	  ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql="SELECT DISTINCT T.TEST_ID, T.CHAPTER_ID, C.TEST_NAME,C.No_of_section, N.CHAPTER_ID,N.CHAPTER_NAME, B.BATCH_ID, S.SUBJECT_NAME, C.TEST_TIME, C.CREATED_DATE FROM (((((chapter_test AS T JOIN create_test AS C ON T.TEST_ID = C.TEST_ID ) JOIN chapter_added AS CA ON CA.CHAPTER_ID = T.CHAPTER_ID) JOIN new_chapter AS N ON N.CHAPTER_ID = CA.CHAPTER_ID)	JOIN subjects AS S ON CA.SUBJECT_ID = S.SUBJECT_ID) JOIN lms_teacher_alloted AS B ON  B.BATCH_ID = B.BATCH_ID) WHERE CA.Teacher_Id =? ORDER BY CREATED_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,list.get(0));
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString("B.BATCH_ID")+"/"+rs.getString("S.SUBJECT_NAME")+"/"+rs.getString("N.CHAPTER_ID")+"/"+rs.getString("N.CHAPTER_NAME")+"/"+rs.getString("T.TEST_ID")+"/"+rs.getString("C.TEST_NAME")+"/"+rs.getString("C.CREATED_DATE")+"/"+rs.getString("C.TEST_TIME"));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList; 
  }
  private ArrayList<String> getLogAssignments()
  {
	  ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql = "SELECT DISTINCT T.ASSIGNMENT_ID, T.CHAPTER_ID,N.CHAPTER_ID, N.CHAPTER_NAME, B.BATCH_ID, S.SUBJECT_NAME,S.SUBJECT_ID,C.ASSIGNMENT_TYPE,C.FILE_NAME,C.UPLOAD_DATE FROM (((((chapter_assignment AS T JOIN create_assignment AS C ON T.ASSIGNMENT_ID = C.ASSIGNMENT_ID ) JOIN chapter_added AS CA ON CA.CHAPTER_ID = T.CHAPTER_ID) JOIN new_chapter AS N ON N.CHAPTER_ID = CA.CHAPTER_ID)	JOIN subjects AS S ON CA.SUBJECT_ID = S.SUBJECT_ID)  JOIN lms_teacher_alloted AS B ON B.BATCH_ID = B.BATCH_ID) WHERE CA.Teacher_Id =? ORDER BY UPLOAD_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,list.get(0));
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString("B.BATCH_ID")+"/"+rs.getString("S.SUBJECT_NAME")+"/"+rs.getString("N.CHAPTER_ID")+"/"+rs.getString("N.CHAPTER_NAME")+"/"+rs.getString("T.ASSIGNMENT_ID")+"/"+rs.getString("C.FILE_NAME")+"/"+rs.getString("C.UPLOAD_DATE"));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList; 
  }
  private ArrayList<String> getLogVideos()
  {
	  ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql="SELECT Video_Id,Video_Name,Video_Description,File_Name,NC.Chapter_Name,CA.CHAPTER_ID,C.BATCH_ID,S.SUBJECT_NAME from ((((chapter_added AS CA JOIN videos AS B ON B.Chapter_Id=CA.Chapter_Id)JOIN lms_teacher_alloted AS C ON CA.BATCH_ID=C.BATCH_ID)JOIN subjects AS S ON S.SUBJECT_ID=CA.SUBJECT_ID)JOIN new_chapter AS NC ON CA.CHAPTER_ID=NC.CHAPTER_ID) WHERE B.TEACHER_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,list.get(0));
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString("C.BATCH_ID")+"/"+rs.getString("S.SUBJECT_NAME")+"/"+rs.getString("CA.CHAPTER_ID")+"/"+rs.getString("NC.CHAPTER_NAME")+"/"+rs.getString("Video_Id")+"/"+rs.getString("Video_Name")+"/"+rs.getString("Video_Description")+"/"+rs.getString("File_Name"));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList; 
  }
  private ArrayList<String> getLogBooks()
  {
	  ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql="SELECT BOOK_ID,BOOK_NAME,DESCRIPTION,BOOK_TYPE,NAME_URL,NC.CHAPTER_NAME,C.CHAPTER_ID,CL.BATCH_ID,S.SUBJECT_NAME FROM ((((chapter_added AS C JOIN books AS B ON B.Chapter_Id=C.Chapter_Id)JOIN new_chapter AS NC ON NC.CHAPTER_ID=C.CHAPTER_ID)JOIN subjects AS S ON C.SUBJECT_ID=S.SUBJECT_ID)JOIN lms_teacher_alloted AS CL ON CL.BATCH_ID=C.BATCH_ID)  WHERE B.TEACHER_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,list.get(0));
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString("CL.BATCH_ID")+"%"+rs.getString("S.SUBJECT_NAME")+"%"+rs.getString("C.CHAPTER_ID")+"%"+rs.getString("NC.CHAPTER_NAME")+"%"+rs.getString("BOOK_ID")+"%"+rs.getString("BOOK_NAME")+"%"+rs.getString("DESCRIPTION")+"%"+rs.getString("NAME_URL")+"%"+rs.getString("BOOK_TYPE"));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList; 
  }
  private boolean checkSessionId(){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_student_alloted WHERE STUDENT_ID = ? AND BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(0));
			ps.setString(2, list.get(2));
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean sendStudentAllot(){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = -1;
		try{
	        
			con = getConnection(); 
			String sql = "INSERT INTO lms_student_alloted(STUDENT_ID,BATCH_ID,SUBJECT_ID) VALUES(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(2));
				ps.setString(3,  list.get(1));	
				value = ps.executeUpdate();				
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }

  private boolean studentPassword(String studentId,String pass){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "update student_login set STUDENT_PASSWORD=? where STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, pass);
				ps.setString(2, studentId);
				value = ps.executeUpdate();				
		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean updateStudent(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	int n=-1;
	//int n=-1;
	try{
		con = getConnection(); 
		String sql = "Update student_login set STUDENT_NAME=?,EMAIL_ID=?,PHONE=? where STUDENT_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1, list.get(1));
			ps.setString(2, list.get(2));
			ps.setString(3, list.get(3));
			ps.setString(4, list.get(0));
			value = ps.executeUpdate();	
			
				String sql2 = "UPDATE lms_student_alloted set BATCH_ID=?,SUBJECT_ID=? where STUDENT_ID=? AND SUBJECT_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setString(1, list.get(6));
				ps2.setString(2, list.get(4));
				ps2.setString(3, list.get(0));
				ps2.setString(4, list.get(8));
				n= ps2.executeUpdate();	
			
		}
	}catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}
	catch(Exception e){
		System.out.println(e);e.printStackTrace();
	}
	finally{closeConnection(con);}
	if(value != -1 || n!=-1){return true;}else return false;
  }
  private boolean deleteStudent(String student_id){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "delete from student_login where STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, student_id);
				value = ps.executeUpdate();				
			
			String sql2 = "delete from lms_student_alloted where STUDENT_ID=?";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			
				ps2.setString(1, student_id);
				ps2.executeUpdate();				
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  
  
  private boolean teacherPassword(String teacherId,String pass){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "update lms_main_teacher set PASSWORD=? where TEACHER_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, pass);
				ps.setString(2, teacherId);
				value = ps.executeUpdate();				
		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private boolean updateTeacher(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	try{
		con = getConnection(); 
		String sql = "Update lms_main_teacher set NAME=?,USERNAME=?,FATHER_NAME=?,DOB=?,SEX=?,ADDRESS=?,EMAIL_ID=?,MOBILE_NUMBER=? where TEACHER_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			 DateFormat formatter ; 
		     formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(list.get(4));
		     java.sql.Date d=new java.sql.Date(date.getTime());
		     
			
			ps.setString(1, list.get(1));
			ps.setString(2, list.get(2));
			ps.setString(3, list.get(3));
			ps.setDate(4, d);
			ps.setString(5, list.get(5));
			ps.setString(6, list.get(6));
			ps.setString(7, list.get(7));
			ps.setString(8, list.get(8));
			ps.setString(9, list.get(0));
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

private boolean updateTeacherBatch(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	try{
		con = getConnection(); 
			
				String sql2 = "UPDATE lms_teacher_alloted set BATCH_ID=?,SUBJECT_ID=? where TEACHER_ID=? AND BATCH_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				if(!list.isEmpty()){
				ps2.setString(1, list.get(2));
				ps2.setString(2, list.get(1));
				ps2.setString(3, list.get(0));
				ps2.setString(4, list.get(3));
				value= ps2.executeUpdate();	
			
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
private boolean deleteTeacher(String teacher_id){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "delete from lms_main_teacher where Teacher_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, teacher_id);
				value = ps.executeUpdate();				
			
			String sql2 = "delete from lms_teacher_alloted where Teacher_ID=?";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			
				ps2.setString(1, teacher_id);
				ps2.executeUpdate();				
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }

  
  private boolean checkBatch(){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
			
			 DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy");
		     Date date=formatter2.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_batch WHERE SUBJECT_ID = ? AND START_DATE=? and START_TIME=? AND BATCH_TYPE=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(0));
			ps.setDate(2, d);
			ps.setTime(3, sqltime);
			ps.setString(4, list.get(6));
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean checkBatch(String batch){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
			
			 DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy");
		     Date date=formatter2.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_batch WHERE SUBJECT_ID = ? AND START_DATE=? and START_TIME=? AND BATCH_TYPE=? AND BATCH_ID!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(0));
			ps.setDate(2, d);
			ps.setTime(3, sqltime);
			ps.setString(4, list.get(6));
			ps.setString(5, batch);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  
  private boolean  allotBatch(){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			String sql = "INSERT INTO lms_teacher_alloted(TEACHER_ID,BATCH_ID,SUBJECT_ID) VALUES(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, list.get(1));
				ps.setString(2, list.get(2));
				ps.setString(3, list.get(0));
				value = ps.executeUpdate();				
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  
  private boolean checkTeacherBatch(){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_teacher_alloted WHERE BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(2));
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean  scheduleBatch(){
		Connection con = null;
		int value = 0;
		int n=0;
		try{
			con = getConnection(); 
			if(!list.isEmpty())
			{
			 DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(list.get(5));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "INSERT INTO lms_teacher_session(TEACHER_ID,BATCH_ID,SESSION_DATE,MODULE) VALUES(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			
				ps.setString(1, list.get(1));
				ps.setString(2, list.get(3));
				ps.setDate(3, d);
				ps.setString(4, list.get(4));
				value = ps.executeUpdate();
				ResultSet rs=ps.getGeneratedKeys();
				while(rs.next())
					n=rs.getInt(1);
				//System.out.println(n);
				if(n!=0)
				{
					String sql2 = "INSERT INTO webinar_class(SESSION_ID,EMAIL_ID,USER,BATCH,MEETING_ID,CLASS_LINK) VALUES(?,?,?,?,?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					
					
						ps2.setInt(1, n);
						ps2.setString(2, list.get(6));
						ps2.setString(3, list.get(0));
						ps2.setString(4, list.get(2));
						ps2.setString(5, list.get(7));
						ps2.setString(6, list.get(8));
						ps2.executeUpdate();
				}
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "SC");
					ps2.executeUpdate();	
				}
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  createSession(){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
		
			 DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			
		     Date date=formatter.parse(list.get(3));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "INSERT INTO lms_teacher_session(TEACHER_ID,BATCH_ID,SESSION_DATE,MODULE) VALUES(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			
				ps.setString(1, list.get(1));
				ps.setString(2, list.get(2));
				ps.setDate(3, d);
				ps.setString(4, list.get(4));
				value = ps.executeUpdate();
			 
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  
  private boolean  updateScheduleBatch(){
		Connection con = null;
		int value = -1;
		try{
			con = getConnection(); 
		
			 DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			
		     Date date=formatter.parse(list.get(5));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "update lms_teacher_session set BATCH_ID=?,SESSION_DATE=?,MODULE=? where SESSION_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			
				ps.setString(1, list.get(3));
				
				ps.setDate(2, d);
				ps.setString(3, list.get(4));
				ps.setString(4, list.get(1));
				value = ps.executeUpdate();
				if(value!=-1)
				{
					String sql2 = "update webinar_class set MEETING_ID=?,CLASS_LINK=?,EMAIL_ID=? where SESSION_ID=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					
					
						ps2.setString(1, list.get(7));
						ps2.setString(2, list.get(8));
						ps2.setString(3, list.get(6));
						ps2.setString(4, list.get(1));
						ps2.executeUpdate();
				}
			 
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }
  private boolean  deleteScheduleBatch(String sessionId){
		Connection con = null;
		int value = -1;
		try{
			con = getConnection(); 
		
			
			String sql = "delete from lms_teacher_session where SESSION_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			
				ps.setString(1, sessionId);
				
				
				value = ps.executeUpdate();
				if(value!=-1)
				{
					String sql2 = "delete from webinar_class where SESSION_ID=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					
					
						ps2.setString(1, sessionId);
			
						ps2.executeUpdate();
				}
			 
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }
  private int  checkTeacher(String batchId,String subjectId){
		Connection con = null;
		ResultSet rs = null;
		int TID = 0;
		try{
			con = getConnection(); 
			
			String sql = "SELECT COUNT(1) FROM lms_teacher_alloted WHERE BATCH_ID=? and SUBJECT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, batchId);
			ps.setString(2, subjectId);
			rs = ps.executeQuery();
			while(rs.next())
				TID = rs.getInt(1);
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return TID;
	  }
  private boolean checkSessionDate(String batchId,String sessionDate){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			 DateFormat formatter ; 
		     formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(sessionDate);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_teacher_session WHERE BATCH_ID=? AND SESSION_DATE=? AND SESSION_DATE!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, batchId);
			ps.setDate(2, d);
			ps.setDate(3, d);
			rs = ps.executeQuery();
			while(rs.next())
				countTID = rs.getInt(1);
	
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean checkSessionDate(int tId,String batchId,String sessionDate){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			 DateFormat formatter ; 
		     formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(sessionDate);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_teacher_session WHERE TEACHER_ID = ? AND BATCH_ID=? AND SESSION_DATE=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tId);
			ps.setString(2, batchId);
			ps.setDate(3, d);
			rs = ps.executeQuery();
			while(rs.next())
				countTID = rs.getInt(1);
	
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean checkSession(){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_teacher_session WHERE TEACHER_ID = ? AND BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(1));
			ps.setString(2, list.get(2));
		
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean createBatch(){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = -1;
		try{
			 String b_name="";
		        String s_name="";
		        int n=0;
		        boolean flag=false;
				con = getConnection(); 
				String sql2 = "SELECT BATCH_ID,SUBJECT_NAME FROM lms_batch as B join subjects as S on B.SUBJECT_ID=S.SUBJECT_ID  WHERE B.SUBJECT_ID =? order by CREATED_DATE desc limit 1";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				if(!list.isEmpty())
				ps2.setString(1, list.get(0));
				
				ResultSet rs=ps2.executeQuery();
				while(rs.next())
				{
					b_name=rs.getString(1);
					s_name=rs.getString(2);
					flag=true;
				}
				
				if(flag==true)
				{
					
				String s[]=b_name.split(s_name);
				n=Integer.parseInt(s[1]);
				}
				else
				{
					
				String sql3 = "SELECT SUBJECT_NAME FROM subjects WHERE SUBJECT_ID =?";
				PreparedStatement ps3 = con.prepareStatement(sql3);
				if(!list.isEmpty())
				ps3.setString(1, list.get(0));
				ResultSet rs2=ps3.executeQuery();
				while(rs2.next())
				{
					s_name=rs2.getString(1);
				}
				
				}
				n++;
				b_name=s_name+n;
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
			String dr = list.get(3);
	    	  String drn[]=dr.split(":");
	    	  int duration=Integer.parseInt(drn[0])*60+Integer.parseInt(drn[1]);
			 DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy");
		     Date date=formatter2.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
		    
			String sql = "INSERT INTO lms_batch(BATCH_ID,SUBJECT_ID,START_DATE,START_TIME,DURATION,DAYS,TOTAL_HOUR,BATCH_TYPE,DISPLAY,BASE_PRICE,DISCOUNT,FINAL_PRICE,S_TAX,S_TAX_PRICE,P_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, b_name);
				ps.setString(2, list.get(0));
				ps.setDate(3,  d);
				ps.setTime(4,  sqltime);
				ps.setInt(5, duration);
				ps.setInt(6, Integer.parseInt(list.get(4)));
				ps.setFloat(7,  Float.parseFloat(list.get(5)));
				ps.setString(8, list.get(6));
				ps.setString(9, list.get(7));
				ps.setFloat(10,  Float.parseFloat(list.get(8)));
				ps.setFloat(11, Float.parseFloat(list.get(9)));
				ps.setFloat(12, Float.parseFloat(list.get(10)));
				ps.setFloat(13, Float.parseFloat(list.get(11)));
				ps.setFloat(14, Float.parseFloat(list.get(12)));
				ps.setString(15, list.get(13));
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql3 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps5 = con.prepareStatement(sql3);
					ps5.setString(1, username);
					ps5.setString(2, "CB");
					ps5.executeUpdate();	
				}
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }
  private boolean  updateBatch(){
		Connection con = null;
		int value = -1;
		try{
			con = getConnection(); 
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
			String dr = list.get(3);
	    	  String drn[]=dr.split(":");
	    	  int duration=Integer.parseInt(drn[0])*60+Integer.parseInt(drn[1]);
			 DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy");
		     Date date=formatter2.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "update lms_batch set START_DATE=?,START_TIME=?,DURATION=?,DAYS=?,TOTAL_HOUR=?,BATCH_TYPE=?,BASE_PRICE=?,DISCOUNT=?,FINAL_PRICE=?,S_TAX=?,S_TAX_PRICE=?,VERIFIED='n',DISPLAY=? WHERE BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setDate(1, d);
				ps.setTime(2,  sqltime);
				ps.setInt(3, duration);
				ps.setInt(4, Integer.parseInt(list.get(4)));
				ps.setFloat(5,  Float.parseFloat(list.get(5)));
				ps.setString(6, list.get(6));
				ps.setFloat(7,  Float.parseFloat(list.get(8)));
				ps.setFloat(8, Float.parseFloat(list.get(9)));
				ps.setFloat(9, Float.parseFloat(list.get(10)));
				
				ps.setFloat(10, Float.parseFloat(list.get(11)));
				ps.setFloat(11, Float.parseFloat(list.get(12)));
				ps.setString(12, list.get(7));
				ps.setString(13, list.get(0));
				
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "UB");
					ps2.executeUpdate();	
				}
			}
		}
		catch(ParseException parseEx){
			
	    }
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }
  private boolean  deleteBatch(){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "delete from lms_batch WHERE BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			if(!list.isEmpty()){
				ps.setString(1, list.get(0));
				
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "DB");
					ps2.executeUpdate();	
				}
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private ArrayList<String> getBatchDetail(String batch,String subjectId){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			String sql = "select * from lms_batch where BATCH_ID=? and SUBJECT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, batch);
			ps.setString(2, subjectId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy");
				java.util.Date  date = format1.parse(rs.getString("START_DATE"));
				String  date2=format2.format(date);
				SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");
				SimpleDateFormat format4 = new SimpleDateFormat("HH:mm");
				java.util.Date  date4 = format3.parse(rs.getString("START_TIME"));
				String  sqltime=format4.format(date4);
				int dr=Integer.parseInt(rs.getString("DURATION"));
				int d=dr/60;
				int r=dr%60;
				String duration=d+":"+r;
				al.add(date2);
				al.add(sqltime);
				al.add(rs.getString("BATCH_TYPE"));
				al.add(rs.getString("DISPLAY"));
				al.add(duration);
				al.add(rs.getString("DAYS"));
				al.add(rs.getString("TOTAL_HOUR"));
				al.add(rs.getString("BASE_PRICE"));
				al.add(rs.getString("DISCOUNT"));
				al.add(rs.getString("FINAL_PRICE"));
				al.add(rs.getString("S_TAX"));
				al.add(rs.getString("S_TAX_PRICE"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
  private ArrayList<String> getBatch(){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			String sql = "select * from lms_batch as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID order by START_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("DISPLAY")+"abczxy"+rs.getString("DURATION")+"abczxy"+rs.getString("BASE_PRICE")+"abczxy"+rs.getString("DISCOUNT")+"abczxy"+rs.getString("FINAL_PRICE")+"abczxy"+rs.getString("VERIFIED"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
  private ArrayList<String> getBatchDetail(String subject){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			String sql = "select * from lms_batch as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where S.SUBJECT_NAME=? and START_DATE>=date(now())";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subject);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("DURATION")+"abczxy"+rs.getString("TOTAL_HOUR")+"abczxy"+rs.getString("DAYS")+" "+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("BASE_PRICE")+"abczxy"+rs.getString("DISCOUNT")+"abczxy"+rs.getString("FINAL_PRICE"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
	private ArrayList<String> getBatch(String subjectid){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			String sql = "select * from lms_batch as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where B.SUBJECT_ID=? order by START_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectid);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("DISPLAY")+"abczxy"+rs.getString("DURATION")+"abczxy"+rs.getString("BASE_PRICE")+"abczxy"+rs.getString("DISCOUNT")+"abczxy"+rs.getString("FINAL_PRICE")+"abczxy"+rs.getString("VERIFIED"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
	private ArrayList<String> getBatch(String subjectid,String sd,String td){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			
			java.util.Date  date3 = df.parse(sd);
			java.util.Date  date4 = df.parse(td);
			java.sql.Date d=new java.sql.Date(date3.getTime());
			java.sql.Date d2=new java.sql.Date(date4.getTime());
			String sql = "select * from lms_batch as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where B.SUBJECT_ID =? AND START_DATE>=? and START_DATE<=? order by START_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectid);
			ps.setDate(2, d);
			ps.setDate(3, d2);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("DISPLAY")+"abczxy"+rs.getString("DURATION")+"abczxy"+rs.getString("BASE_PRICE")+"abczxy"+rs.getString("DISCOUNT")+"abczxy"+rs.getString("FINAL_PRICE")+"abczxy"+rs.getString("VERIFIED"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
	private ArrayList<String> getBatch(String sd,String td){
		Connection con = null;
		 ArrayList<String> al=new  ArrayList<String>();
		try{
			con = getConnection(); 
			SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			
			java.util.Date  date3 = df.parse(sd);
			java.util.Date  date4 = df.parse(td);
			java.sql.Date d=new java.sql.Date(date3.getTime());
			java.sql.Date d2=new java.sql.Date(date4.getTime());
			String sql = "select * from lms_batch as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where START_DATE>=? and START_DATE<=? order by START_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setDate(2, d2);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("BATCH_TYPE")+"abczxy"+rs.getString("DISPLAY")+"abczxy"+rs.getString("DURATION")+"abczxy"+rs.getString("BASE_PRICE")+"abczxy"+rs.getString("DISCOUNT")+"abczxy"+rs.getString("FINAL_PRICE")+"abczxy"+rs.getString("VERIFIED"));
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		 return al;
	  }
 
  private boolean checkCategory(String categoryName){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM category WHERE CATEGORY_NAME=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, categoryName);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean checkCategory(String categoryName,String categoryNameOld){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM category WHERE CATEGORY_NAME=? and CATEGORY_NAME!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, categoryName);
			ps.setString(2, categoryNameOld);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  private boolean  addCategory(String categoryName){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "insert into category(CATEGORY_NAME) values(?)";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, categoryName);
				value = ps.executeUpdate();			
				
			
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  updateCategory(String categoryId,String categoryName){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "update category set Category_NAME=? where ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, categoryName);
				ps.setString(2, categoryId);
				value = ps.executeUpdate();				
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  deleteCategory(String categoryId){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "delete from category where ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, categoryId);
				value = ps.executeUpdate();
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean checkTechnology(String technologyName){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM technology WHERE TECHNOLOGY_NAME=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, technologyName);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
private boolean checkTechnology(String technologyName,String technologyNameOld){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM technology WHERE TECHNOLOGY_NAME=? and TECHNOLOGY_NAME!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, technologyName);
			ps.setString(2, technologyNameOld);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
private boolean  addTechnology(String technologyName){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "insert into technology(TECHNOLOGY_NAME) values(?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, technologyName);
				value = ps.executeUpdate();			
				
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private boolean  updateTechnology(String technologyId,String technologyName){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			String sql = "update technology set TECHNOLOGY_NAME=? where ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, technologyName);
				ps.setString(2, technologyId);
				value = ps.executeUpdate();				
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private boolean  deleteTechnology(String technologyId){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			;
			String sql = "delete from technology where ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, technologyId);
				value = ps.executeUpdate();
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  public ArrayList<String>  getCategory(){
		Connection con = null;
		ArrayList<String> al=new ArrayList<String>();
		try{
			con = getConnection(); 
			;
			String sql = "select * from category";
			PreparedStatement ps = con.prepareStatement(sql);
		
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					al.add(rs.getString("ID")+"abczxy"+rs.getString("CATEGORY_NAME"));
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return al;
	  }
  public ArrayList<String>  getSkillLevel(){
		Connection con = null;
		ArrayList<String> al=new ArrayList<String>();
		try{
			con = getConnection(); 
			;
			String sql = "select * from skill_level";
			PreparedStatement ps = con.prepareStatement(sql);
		
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					al.add(rs.getString("ID")+"abczxy"+rs.getString("LEVEL"));
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return al;
	  }
 
  public ArrayList<String>  getTechnology(){
		Connection con = null;
		ArrayList<String> al=new ArrayList<String>();
		try{
			con = getConnection();
			String sql = "select * from technology";
			PreparedStatement ps = con.prepareStatement(sql);
		
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					al.add(rs.getString("ID")+"abczxy"+rs.getString("TECHNOLOGY_NAME"));
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return al;
	  }
  private boolean checkSubject(String subjectName,String subjectNameOld)
  {
	  Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM subjects WHERE SUBJECT_NAME=? and SUBJECT_NAME!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectName);
			ps.setString(2, subjectNameOld);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  
  }
  private boolean checkSubject(String subject_name){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM subjects WHERE SUBJECT_NAME=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subject_name);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
  public List<Batch>  getSubject(){
		Connection con = null;
		List<Batch> list=new ArrayList<Batch>();
		Batch b;
		try{
			con = getConnection();
			String sql = "select S.SUBJECT_ID,SUBJECT_NAME,DISPLAY_NAME,SUBJECT_DESCRIPTION,TRAINING,LEVEL,PROJECT,IMAGE,STATUS,GROUP_CONCAT(DISTINCT TECHNOLOGY_NAME) as TECHNOLOGY_NAME,GROUP_CONCAT(distinct CATEGORY_NAME) as CATEGORY_NAME,FREE,URL,DISPLAY  from ((((subjects as S join skill_level as SL on S.LEVEL_ID=SL.ID) right join subject_category as C on C.SUBJECT_ID=S.SUBJECT_ID) left join category as CT on CT.ID=C.CATEGORY_ID) left join subject_technology as ST on ST.SUBJECT_ID=S.SUBJECT_ID) left join technology as T on T.ID=ST.TECHNOLOGY_ID group BY S.SUBJECT_ID";
			PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					b=new Batch();
					b.setSubjectId(rs.getString("S.SUBJECT_ID"));
					b.setSubjectName(rs.getString("SUBJECT_NAME"));
					b.setSubjectDescription(rs.getString("SUBJECT_DESCRIPTION"));
					b.setTraining(rs.getString("TRAINING"));
					b.setLevelName(rs.getString("LEVEL"));
					b.setTechnologyName(rs.getString("TECHNOLOGY_NAME"));
					b.setCategoryName(rs.getString("CATEGORY_NAME"));
					b.setUrl(rs.getString("URL"));
					b.setFree(rs.getString("FREE"));
					b.setImage(rs.getString("IMAGE"));
					b.setProject(rs.getString("PROJECT"));
					b.setStatus(rs.getString("STATUS"));
					b.setDisplay(rs.getString("DISPLAY"));
					b.setDisplaySubjectName(rs.getString("DISPLAY_NAME"));
					list.add(b);
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return list;
	  }
 
  public List<Batch>  getSubjectDetail(String subjectId){
		Connection con = null;
		List<Batch> list=new ArrayList<Batch>();
		Batch b;
		try{
			con = getConnection();
			String sql = "select S.SUBJECT_ID,SUBJECT_NAME,DISPLAY_NAME,SUBJECT_DESCRIPTION,TRAINING,LEVEL,PROJECT,IMAGE,STATUS,GROUP_CONCAT(DISTINCT TECHNOLOGY_NAME) as TECHNOLOGY_NAME,GROUP_CONCAT(distinct CATEGORY_NAME) as CATEGORY_NAME,FREE,URL,DISPLAY from ((((subjects as S join skill_level as SL on S.LEVEL_ID=SL.ID) right join subject_category as C on C.SUBJECT_ID=S.SUBJECT_ID) left join category as CT on CT.ID=C.CATEGORY_ID) left join subject_technology as ST on ST.SUBJECT_ID=S.SUBJECT_ID) left join technology as T on T.ID=ST.TECHNOLOGY_ID where S.SUBJECT_ID=? group BY S.SUBJECT_ID";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, subjectId);
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					b=new Batch();
					b.setSubjectId(rs.getString("S.SUBJECT_ID"));
					b.setSubjectName(rs.getString("SUBJECT_NAME"));
					b.setSubjectDescription(rs.getString("SUBJECT_DESCRIPTION"));
					b.setTraining(rs.getString("TRAINING"));
					b.setLevelName(rs.getString("LEVEL"));
					b.setTechnologyName(rs.getString("TECHNOLOGY_NAME"));
					b.setCategoryName(rs.getString("CATEGORY_NAME"));
					b.setUrl(rs.getString("URL"));
					b.setFree(rs.getString("FREE"));
					b.setImage(rs.getString("IMAGE"));
					b.setProject(rs.getString("PROJECT"));
					b.setStatus(rs.getString("STATUS"));
					b.setDisplay(rs.getString("DISPLAY"));
					b.setDisplaySubjectName(rs.getString("DISPLAY_NAME"));
					list.add(b);
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		return list;
	  }
  private boolean  addSubject(String category[],String technology[]){
		Connection con = null;
		int value = 0;
		int subjectId=0;
		try{
			con = getConnection();
			String sql = "insert into subjects(SUBJECT_NAME,SUBJECT_DESCRIPTION,TRAINING,FREE,LEVEL_ID,DISPLAY,URL,IMAGE,PROJECT,STATUS,DISPLAY_NAME) values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			if(!list.isEmpty()){
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, list.get(2));
				ps.setString(4, list.get(3));
				ps.setString(5, list.get(4));
				ps.setString(6, list.get(5));
				ps.setString(7, list.get(6));
				ps.setString(8, list.get(7));
				ps.setString(9, list.get(8));
				ps.setString(10, list.get(9));
				ps.setString(11, list.get(10));
				value = ps.executeUpdate();
				
				if(value>0)
				{
					ResultSet rs=ps.getGeneratedKeys();
					while(rs.next())
						subjectId=rs.getInt(1);
					addCategoryTechnology(subjectId, category, technology);
					
				}
				
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  updateSubject(String subjectId,String category[],String technology[]){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			String sql = "update subjects set SUBJECT_NAME=?,SUBJECT_DESCRIPTION=?,TRAINING=?,FREE=?,LEVEL_ID=?,DISPLAY=?,URL=?,IMAGE=?,PROJECT=?,STATUS=?,DISPLAY_NAME=? where SUBJECT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, list.get(0));
			ps.setString(2, list.get(1));
			ps.setString(3, list.get(2));
			ps.setString(4, list.get(3));
			ps.setString(5, list.get(4));
			ps.setString(6, list.get(5));
			ps.setString(7, list.get(6));
			ps.setString(8, list.get(7));
			ps.setString(9, list.get(8));
			ps.setString(10, list.get(9));
			ps.setString(11, list.get(10));
			ps.setString(12, subjectId);
				value = ps.executeUpdate();	
				if(value>0)
				{
					deleteCategoryTechnology(subjectId);
					int subject=Integer.parseInt(subjectId);
					addCategoryTechnology(subject, category, technology);
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  deleteSubject(String subjectId){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			String sql = "delete from subjects where SUBJECT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, subjectId);
				value = ps.executeUpdate();				
				if(value>0)
				{
					deleteCategoryTechnology(subjectId);
				}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  addCategoryTechnology(int subjectId, String category[],String technology[]){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
					String sql2 = "insert into subject_category(SUBJECT_ID,CATEGORY_ID) values";
					for(int i=0;i<category.length;i++)
					{
						if(i==0)
							sql2=sql2+"("+subjectId+","+category[i]+")";
						else
							sql2=sql2+",("+subjectId+","+category[i]+")";
					}
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.executeUpdate();
					ps2.close();
					String sql3 = "insert into subject_technology(SUBJECT_ID,TECHNOLOGY_ID) values";
					for(int i=0;i<technology.length;i++)
					{
						if(i==0)
							sql3=sql3+"("+subjectId+","+technology[i]+")";
						else
							sql3=sql3+",("+subjectId+","+technology[i]+")";	
					}
					ps2 = con.prepareStatement(sql3);
					value=ps2.executeUpdate();
					ps2.close();
					
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean  deleteCategoryTechnology(String subjectId){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
					String sql2 = "delete from subject_category where SUBJECT_ID=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, subjectId);
					ps2.executeUpdate();
					ps2.close();
					String sql3 = "delete from subject_technology where SUBJECT_ID=?";
					ps2 = con.prepareStatement(sql3);
					ps2.setString(1, subjectId);
					ps2.executeUpdate();
					ps2.close();
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
  private boolean checkSession(String session_id){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_student_session WHERE SESSION_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, session_id);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
private boolean  sendSession(String session_id,String students){
		Connection con = null;
		int value = 0;
		try{
			con = getConnection(); 
			String sql = "insert into lms_student_session values(?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, session_id);
				ps.setInt(2, Integer.parseInt(students));
				value = ps.executeUpdate();				
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private ArrayList<String> getTeacherAudit(String subjectId){
	ArrayList<String> aList = new ArrayList<String>();	
	Connection con = null;
	ResultSet rs = null;
	try{
		con = getConnection(); 
		String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT,B.BATCH_TYPE FROM lms_teacher_session as T join lms_teacher_alloted as TA ON TA.BATCH_ID=T.BATCH_ID JOIN lms_batch as B on B.BATCH_ID=TA.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=TA.SUBJECT_ID WHERE TA.SUBJECT_ID = ? ORDER BY T.SESSION_DATE DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, subjectId);
		rs = ps.executeQuery();
		int i=0;
		while(rs.next()){
			aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9)+"/"+rs.getString(10));
			i++;
		}		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}	
	return aList;
 }
 private ArrayList<String> getTeacherAuditByDate(String date2){
		ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection();

			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(date2);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT,B.BATCH_TYPE FROM lms_teacher_session as T join lms_teacher_alloted as TA ON TA.BATCH_ID=T.BATCH_ID JOIN lms_batch as B on B.BATCH_ID=TA.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=TA.SUBJECT_ID WHERE T.SESSION_DATE = ? ORDER BY T.SESSION_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, d);
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9)+"/"+rs.getString(10));
				i++;
			}		
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList;
	 }
 private ArrayList<String> getTeacherAuditByTeacher(String teacherId){
		ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection();
			String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT,B.BATCH_TYPE FROM lms_teacher_session as T join lms_teacher_alloted as TA ON TA.BATCH_ID=T.BATCH_ID JOIN lms_batch as B on B.BATCH_ID=TA.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=TA.SUBJECT_ID WHERE T.TEACHER_ID = ? ORDER BY T.SESSION_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, teacherId);
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9)+"/"+rs.getString(10));
				i++;
			}		
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList;
	 }
 
 
 private ArrayList<String> getTeacherAuditDemo(String subjectId){
		ArrayList<String> aList = new ArrayList<String>();	
		Connection con = null;
		ResultSet rs = null;
		try{
			con = getConnection(); 
			String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT FROM lms_teacher_session as T JOIN lms_batch_demo as B on B.DEMO_BATCH_ID=T.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID WHERE S.SUBJECT_ID = ? ORDER BY T.SESSION_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectId);
			rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9));
				i++;
			}		
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}	
		return aList;
	 }
	 private ArrayList<String> getTeacherAuditByDateDemo(String date2){
			ArrayList<String> aList = new ArrayList<String>();	
			Connection con = null;
			ResultSet rs = null;
			try{
				con = getConnection();

				DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			     Date date=formatter.parse(date2);
			     java.sql.Date d=new java.sql.Date(date.getTime());
				String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT FROM lms_teacher_session as T join lms_batch_demo as B on B.DEMO_BATCH_ID=T.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID WHERE T.SESSION_DATE = ? ORDER BY T.SESSION_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setDate(1, d);
				rs = ps.executeQuery();
				int i=0;
				while(rs.next()){
					aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9));
					i++;
				}		
			}catch(Exception sqe){
				System.out.println(sqe);
				sqe.printStackTrace();
			}finally{closeConnection(con);}	
			return aList;
		 }
	 private ArrayList<String> getTeacherAuditByTeacherDemo(String teacherId){
			ArrayList<String> aList = new ArrayList<String>();	
			Connection con = null;
			ResultSet rs = null;
			try{
				con = getConnection();
				String sql = "SELECT  T.SESSION_ID,T.TEACHER_ID,MT.NAME,S.SUBJECT_ID,S.SUBJECT_NAME,T.BATCH_ID,T.SESSION_DATE,T.MODULE,SS.TOTAL_STUDENT FROM lms_teacher_session as T JOIN lms_batch_demo as B on B.DEMO_BATCH_ID=T.BATCH_ID JOIN lms_student_session as SS ON T.SESSION_ID=SS.SESSION_ID JOIN lms_main_teacher as MT ON T.TEACHER_ID=MT.TEACHER_ID JOIN subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID WHERE T.TEACHER_ID = ? ORDER BY T.SESSION_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, teacherId);
				rs = ps.executeQuery();
				int i=0;
				while(rs.next()){
					aList.add(i,rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9));
					i++;
				}		
			}catch(Exception sqe){
				System.out.println(sqe);
				sqe.printStackTrace();
			}finally{closeConnection(con);}	
			return aList;
		 }
 private boolean checkSalesUser(String user){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_main_salesperson WHERE USERNAME = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
	
		if(countTID != 0)return true;else return false;
	  }
 private boolean sendSales(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	//int n=-1;
	try{
		con = getConnection(); 
		String sql = "INSERT INTO lms_main_salesperson(SALES_PERSON_ID,NAME,USERNAME,PASSWORD,ADDRESS,EMAIL,PHONE) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
		     
			ps.setString(1, list.get(0));
			ps.setString(2, list.get(1));
			ps.setString(3, list.get(2));
			ps.setString(4, "SALES@123");
			ps.setString(5, list.get(3));
			ps.setString(6, list.get(4));
			ps.setString(7, list.get(5));
			
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
 private boolean teacherProfile(String teacherID){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_main_teacher WHERE TEACHER_ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, teacherID);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
	
		if(countTID != 0)return true;else return false;
	  }
 private boolean studentProfile(String studentID){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM student_login WHERE STUDENT_ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentID);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
	
		if(countTID != 0)return true;else return false;
	  }
 private boolean checkDemoBatch(String subjectId,String startDate,String time){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			DateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter2.parse(startDate);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_batch_demo WHERE SUBJECT_ID = ? AND START_DATE=? and START_TIME=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectId);
			ps.setDate(2, d);
			ps.setString(3, time);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
 private boolean checkDemoBatch(String subjectId,String startDate,String time,String dBatchId){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			DateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter2.parse(startDate);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "SELECT COUNT(1) FROM lms_batch_demo WHERE SUBJECT_ID = ? AND START_DATE=? and START_TIME=? and DEMO_BATCH_ID!=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectId);
			ps.setDate(2, d);
			ps.setString(3, time);
			ps.setString(4, dBatchId);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(countTID != 0)return true;else return false;
	  }
private boolean createDemoBatch(){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = -1;
		try{
	        String b_name="";
	        String s_name="";
	        int n=0;
	        boolean flag=false;
			con = getConnection(); 
			String sql2 = "SELECT DEMO_BATCH_ID FROM lms_batch_demo WHERE SUBJECT_ID =? order by CREATED_DATE desc limit 1";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			if(!list.isEmpty())
			ps2.setString(1, list.get(0));
			
			ResultSet rs=ps2.executeQuery();
			while(rs.next())
			{
				b_name=rs.getString(1);
				flag=true;
			}
			
			if(flag==true)
			{
			String s[]=b_name.split("demo");
			n=Integer.parseInt(s[1]);
				s_name=s[0]+"demo";
			}
			else
			{
				
			String sql3 = "SELECT SUBJECT_NAME FROM subjects WHERE SUBJECT_ID =?";
			PreparedStatement ps3 = con.prepareStatement(sql3);
			if(!list.isEmpty())
			ps3.setString(1, list.get(0));
			ResultSet rs2=ps3.executeQuery();
			while(rs2.next())
			{
				s_name=rs2.getString(1)+"demo";
			}
			
			}
			n++;
			b_name=s_name+n;
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
			 DateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter2.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			String sql = "INSERT INTO lms_batch_demo(DEMO_BATCH_ID,SUBJECT_ID,START_DATE,START_TIME,MEETING_ID,URL) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(!list.isEmpty()){
				ps.setString(1, b_name);
				ps.setString(2, list.get(0));
				ps.setDate(3,  d);
				ps.setTime(4,  sqltime);
			
				ps.setString(5, list.get(3));
				ps.setString(6, list.get(4));
				value = ps.executeUpdate();				
			}
		}catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != -1)return true;else return false;
	  }
private boolean updateDBatch(){
	Connection con = null;
	int countTID = 0;
	try{
		con = getConnection(); 
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		java.sql.Time sqltime = new java.sql.Time(formatter.parse(list.get(2)).getTime());
		 DateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
	     Date date=formatter2.parse(list.get(1));
	     java.sql.Date d=new java.sql.Date(date.getTime());
		String sql = "update lms_batch_demo set START_DATE=?,START_TIME=?,MEETING_ID=?,URL=? where DEMO_BATCH_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setDate(1,  d);
			ps.setTime(2,  sqltime);
			ps.setString(3, list.get(3));
			ps.setString(4, list.get(4));
			ps.setString(5, list.get(0));
			countTID = ps.executeUpdate();				
		}
		
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(countTID != 0)return true;else return false;
  }
private boolean deleteDBatch(String s){
	Connection con = null;
	int countTID = 0;
	try{
		con = getConnection(); 
		String sql = "delete from lms_batch_demo where DEMO_BATCH_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  s);
			countTID = ps.executeUpdate();	
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(countTID != 0)return true;else return false;
  }
private ArrayList<String> getDBatch(){
	Connection con = null;
	 ArrayList<String> al=new  ArrayList<String>();
	try{
		con = getConnection(); 
		String sql = "select * from lms_batch_demo as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID order by START_DATE DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date  date = format1.parse(rs.getString("START_DATE"));
		String  date2=format2.format(date);
		al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("DEMO_BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("MEETING_ID")+"abczxy"+rs.getString("URL"));
		}
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	 return al;
  }
private ArrayList<String> getDBatch(String subjectid){
	Connection con = null;
	 ArrayList<String> al=new  ArrayList<String>();
	try{
		con = getConnection(); 
		String sql = "select * from lms_batch_demo as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where B.SUBJECT_ID=? order by START_DATE DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, subjectid);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date  date = format1.parse(rs.getString("START_DATE"));
		String  date2=format2.format(date);
		al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("DEMO_BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("MEETING_ID")+"abczxy"+rs.getString("URL"));
		}
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	 return al;
  }
private ArrayList<String> getDBatch(String subjectid,String sd,String td){
	Connection con = null;
	 ArrayList<String> al=new  ArrayList<String>();
	try{
		con = getConnection(); 
		SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		
		java.util.Date  date3 = df.parse(sd);
		java.util.Date  date4 = df.parse(td);
		java.sql.Date d=new java.sql.Date(date3.getTime());
		java.sql.Date d2=new java.sql.Date(date4.getTime());
		String sql = "select * from lms_batch_demo as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where B.SUBJECT_ID =? AND START_DATE>=? and START_DATE<=? order by START_DATE DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, subjectid);
		ps.setDate(2, d);
		ps.setDate(3, d2);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("DEMO_BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("MEETING_ID")+"abczxy"+rs.getString("URL"));
		}
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	 return al;
  }
private ArrayList<String> getDBatch(String sd,String td){
	Connection con = null;
	 ArrayList<String> al=new  ArrayList<String>();
	try{
		con = getConnection(); 
		SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		
		java.util.Date  date3 = df.parse(sd);
		java.util.Date  date4 = df.parse(td);
		java.sql.Date d=new java.sql.Date(date3.getTime());
		java.sql.Date d2=new java.sql.Date(date4.getTime());
		String sql = "select * from lms_batch_demo as B join subjects as S ON S.SUBJECT_ID=B.SUBJECT_ID where START_DATE>=? and START_DATE<=? order by START_DATE DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDate(1, d);
		ps.setDate(2, d2);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date  date = format1.parse(rs.getString("START_DATE"));
			String  date2=format2.format(date);
			al.add(rs.getString("B.SUBJECT_ID")+"abczxy"+rs.getString("SUBJECT_NAME")+"abczxy"+rs.getString("DEMO_BATCH_ID")+"abczxy"+date2+"abczxy"+rs.getString("START_TIME")+"abczxy"+rs.getString("MEETING_ID")+"abczxy"+rs.getString("URL"));
		}
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	 return al;
  }
private boolean updateSales(){
	Connection con = null;
	int countTID = 0;
	try{
		con = getConnection(); 
		String sql = "update lms_main_salesperson set NAME=?,ADDRESS=?,EMAIL=?,PHONE=? where SALES_PERSON_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1,  list.get(1));
			ps.setString(2,  list.get(3));
			ps.setString(3, list.get(4));
			ps.setString(4, list.get(5));
			ps.setString(5, list.get(0));
			countTID = ps.executeUpdate();				
		}
		
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(countTID != 0)return true;else return false;
  }
private boolean deleteSales(String s){
	Connection con = null;
	int countTID = 0;
	try{
		con = getConnection(); 
		String sql = "delete from lms_main_salesperson where SALES_PERSON_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  s);
			countTID = ps.executeUpdate();	
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(countTID != 0)return true;else return false;
  }
private boolean updateStudent2(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	try{
		con = getConnection(); 
		String sql = "Update student_login set STUDENT_NAME=?,EMAIL_ID=?,PHONE=? where STUDENT_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
		     
			
			ps.setString(1, list.get(1));
			ps.setString(2, list.get(2));
			ps.setString(3, list.get(3));
			ps.setString(4, list.get(0));
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
private boolean deleteStudent2(String student_id){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "delete from student_login where STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, student_id);
				value = ps.executeUpdate();					
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private String getNotification(String month,int m){	
    //System.out.println("list :  "+list);
	Connection con = null;
	String s="";
	int n=0;
	try{
        
		con = getConnection(); 
		int subject_id=0;
		String subject_name="";
		String sql2 = "SELECT * FROM subjects";
		PreparedStatement ps2 = con.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next())
			{
				subject_id=rs2.getInt("SUBJECT_ID");
				subject_name=rs2.getString("SUBJECT_NAME");
		String sql = "SELECT count(*) FROM lms_batch_demo WHERE MONTHNAME( START_DATE ) =? and SUBJECT_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,month);
		ps.setInt(2,subject_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				n=rs.getInt(1);
			if(m!=n)
			{
				int l=m-n;
				s=subject_name+"/"+l;
				break;
			}
			}
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	return s;
  }

private boolean updateBatchSession(){	
	  //System.out.println("list :  "+list);
	Connection con = null;
	int value = -1;
	try{
		con = getConnection(); 
		String sql = "Update lms_teacher_session set SESSION_DATE=?,MODULE=? where SESSION_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			 DateFormat formatter ; 
		     formatter = new SimpleDateFormat("dd-MMM-yyyy");
		     Date date=formatter.parse(list.get(1));
		     java.sql.Date d=new java.sql.Date(date.getTime());
			ps.setDate(1, d);
			ps.setString(2, list.get(2));
			ps.setString(3, list.get(0));
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
private boolean deleteBatchSession(String sessionId){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int value = 0;
		try{
	        
			con = getConnection(); 
			String sql = "delete from lms_teacher_session where SESSION_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				ps.setString(1, sessionId);
				value = ps.executeUpdate();					
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally{closeConnection(con);}
		if(value != 0)return true;else return false;
	  }
private boolean scheduleDemoBatch(){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = 0;
	try{
        
		con = getConnection(); 
		String sql = "update lms_batch_demo set SCHEDULE=? where SUBJECT_ID=? and year(START_DATE) = ? AND monthname(START_DATE) = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1, "y");
			ps.setString(2, list.get(0));
			ps.setString(3, list.get(1));
			ps.setString(4, list.get(2));
			value = ps.executeUpdate();	
		}
		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != 0)return true;else return false;
  }
private boolean updateDemoBatchSession(String teacherId,String sessionId){	
//System.out.println("list :  "+list);
Connection con = null;
int value = 0;
try{
    
	con = getConnection(); 
	String sql = "update lms_teacher_session set TEACHER_ID=? where SESSION_ID=?";
	PreparedStatement ps = con.prepareStatement(sql);
	
		ps.setString(1, teacherId);
		ps.setString(2, sessionId);
		value = ps.executeUpdate();	
	
}catch(SQLException sqe){
	System.out.println(sqe);
	sqe.printStackTrace();
}finally{closeConnection(con);}
if(value != 0)return true;else return false;
}

private boolean scheduleBatch(String batchtId){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = 0;
	try{
        
		con = getConnection(); 
		String sql = "update lms_batch set SCHEDULE=? where BATCH_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		if(!list.isEmpty()){
			ps.setString(1, "y");
			ps.setString(2, batchtId);
			value = ps.executeUpdate();	
		}
		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != 0)return true;else return false;
  }

private boolean updateSession(){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = 0;
	try{
        
		con = getConnection(); 
			if(!list.isEmpty())
			{
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				java.util.Date  date = df.parse(list.get(3));
				java.sql.Date d3=new java.sql.Date(date.getTime());
				String sql = "update lms_teacher_session set BATCH_ID=?,TEACHER_ID=?,SESSION_DATE=?,MODULE=? where SESSION_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, list.get(1));
				ps.setString(2, list.get(2));
				ps.setDate(3, d3);
				ps.setString(4, list.get(4));
				ps.setString(5, list.get(0));
				value = ps.executeUpdate();	
			}
		
	}catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != 0)return true;else return false;
  }
private boolean deleteSession(String sessionId){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = 0;
	try{
        
				con = getConnection(); 
				String sql = "delete from lms_teacher_session where SESSION_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sessionId);
				value = ps.executeUpdate();	
		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != 0)return true;else return false;
  }
private boolean callBack(String callBackId){	
    //System.out.println("list :  "+list);
	Connection con = null;
	int value = 0;
	try{
        
		con = getConnection(); 
		String sql = "update callback set CALLBACK=? where CALLBACK_ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "y");
			ps.setString(2, callBackId);
			value = ps.executeUpdate();	
		
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	if(value != 0)return true;else return false;
  }
private boolean  queryAnswer(String ans,String qId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  String sql = "update lms_sales_query set ANSWER=? where QUERY_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, ans);
				ps.setString(2, qId);
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
private boolean  isReadLiveDemo(String s[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  for(int i=0;i<s.length;i++)
		  {
		  String sql = "update lms_live_demo set NOTIFY=? where EMAIL=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "y");
				ps.setString(2, s[i]);
				count=ps.executeUpdate();
		  }
				
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
}
private boolean  isReadContact(String s[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  for(int i=0;i<s.length;i++)
		  {
		  String sql = "update lms_contact set NOTIFY=? where CONTACT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "y");
				ps.setString(2, s[i]);
				count=ps.executeUpdate();
		  }
				
			
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count != 0)
		  return true;
	  else 
		  return false;
}
private ArrayList<String>  getSubjects(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql = "select SUBJECT_ID,SUBJECT_NAME from subjects";
			PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					al.add(rs.getString(1)+"/"+rs.getString(2));
				}	
	  }
	  catch(SQLException sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}

private ArrayList<String>  getContactByDate(String d,String td){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
			
		  String r="09:00:00";
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) ||(cal.get(Calendar.MONTH)>10)||(cal.get(Calendar.MONTH)<2) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9))
					{
						r="08:00:00";
						
						
					}
				String sql="select * from lms_contact where CONTACT_DATE>=? and CONTACT_DATE<=? group by EMAIL,date(CONTACT_DATE) order by CONTACT_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, d2+" "+r);
		         ps.setString(2, d3+" "+r);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CONTACT_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CONTACT_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("MESSAGE")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("NOTIFY")+"abczxy"+rs.getString("IP_ADDRESS")+"abczxy"+rs.getString("ORGANISATION_NAME")+"abczxy"+rs.getString("CONTACT_TYPE"));
							
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getContactNotification(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select * from lms_contact where NOTIFY=? order by CONTACT_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			 ps.setString(1, "n");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CONTACT_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CONTACT_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("MESSAGE")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("NOTIFY")+"abczxy"+rs.getString("IP_ADDRESS")+"abczxy"+rs.getString("ORGANISATION_NAME")+"abczxy"+rs.getString("CONTACT_TYPE"));
							
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
public ArrayList<String>  getContact(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select * from lms_contact group by EMAIL,date(CONTACT_DATE) order by CONTACT_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CONTACT_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CONTACT_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("MESSAGE")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("NOTIFY")+"abczxy"+rs.getString("IP_ADDRESS")+"abczxy"+rs.getString("ORGANISATION_NAME")+"abczxy"+rs.getString("CONTACT_TYPE")+"abczxy"+rs.getString("CAMPAIGNED"));
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getAllContact(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select * from lms_contact order by CONTACT_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CONTACT_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CONTACT_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("PHONE_NO")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("MESSAGE")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("NOTIFY")+"abczxy"+rs.getString("IP_ADDRESS")+"abczxy"+rs.getString("ORGANISATION_NAME")+"abczxy"+rs.getString("CONTACT_TYPE"));
							
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getCallBackByDate(String d,String td){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
			
		  String r="09:00:00";
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) ||(cal.get(Calendar.MONTH)>10)||(cal.get(Calendar.MONTH)<2) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9))
					{
						r="08:00:00";
						
						
					}
				String sql="select * from callback where CALLBACK_DATE>=? and CALLBACK_DATE<=? order by CALLBACK_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, d2+" "+r);
		         ps.setString(2, d3+" "+r);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CALLBACK_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CALLBACK_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("BEST_TIME")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("CALLBACK")+"abczxy"+rs.getString("IP_ADDRESS"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getCallBackByEmail(String email){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
				String sql="select * from callback where EMAIL=? order by CALLBACK_DATE DESC";
				PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CALLBACK_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CALLBACK_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("BEST_TIME")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("CALLBACK")+"abczxy"+rs.getString("IP_ADDRESS"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
public ArrayList<String>  getCallBack(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select * from callback order by CALLBACK_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CALLBACK_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CALLBACK_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("BEST_TIME")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("CALLBACK")+"abczxy"+rs.getString("IP_ADDRESS"));
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getAllCallBack(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select * from callback order by CALLBACK_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("CALLBACK_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					        al.add(rs.getString("CALLBACK_ID")+"abczxy"+rs.getString("NAME")+"abczxy"+rs.getString("EMAIL")+"abczxy"+rs.getString("PHONE")+"abczxy"+rs.getString("COURSE")+"abczxy"+rs.getString("BEST_TIME")+"abczxy"+ld_date+" "+time+"abczxy"+rs.getString("CALLBACK")+"abczxy"+rs.getString("IP_ADDRESS"));
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getLiveDemoInfo(String email){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select USER,QUERY,PICKUP,INTEREST_LEVEL,RSP_DATE from query_phonecall_response  WHERE EMAIL=? order by RSP_DATE DESC";
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
					
					
					al.add(rs.getString("USER")+"/"+rs.getString("QUERY")+"/"+rs.getString("PICKUP")+"/"+rs.getString("INTEREST_LEVEL")+"/"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getLpSource(String lpId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select * from lp_source  WHERE ID=?";
		  //String sql="select L.EMAIL,L.QUERY,Q.QRY_TYPE,Q.QUERY,Q.PICKUP,L.QRY_DATE from lms_live_demo as L left OUTER join query_phonecall_response as Q on Q.RESPONSE_ID=L.Q_ID  WHERE L.EMAIL=? order by L.QRY_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, lpId);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
					        
					al.add(rs.getString("IP_ADDRESS"));
					al.add(rs.getString("SOURCE"));
					al.add(rs.getString("PAGE_URL"));
					al.add(rs.getString("CONTENT"));
					al.add(rs.getString("COMPAIGN_TYPE"));
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getLiveDemo(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  //String sql="select CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME,EMAIL,PHONE_NO,QUERY,STUDENT_ID,E_CORRECT,M_CORRECT,ATTENDED,PICKUP,COURSE,COUNTRY_CODE,SEX,QRY_TYPE,now() as TD,DISCLAIMER from lms_live_demo  WHERE Q_ID=?";
		  String sql="select CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME,EMAIL,PHONE_NO,QUERY,STUDENT_ID,E_CORRECT,M_CORRECT,ATTENDED,PICKUP,COURSE,if(COUNTRY='Hogwarts','HO',COUNTRY_CODE) as COUNTRY_CODE,SEX,QRY_TYPE,now() as TD,DISCLAIMER from lms_live_demo  WHERE Q_ID=?";
		  //String sql="select L.EMAIL,L.QUERY,Q.QRY_TYPE,Q.QUERY,Q.PICKUP,L.QRY_DATE from lms_live_demo as L left OUTER join query_phonecall_response as Q on Q.RESPONSE_ID=L.Q_ID  WHERE L.EMAIL=? order by L.QRY_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					  java.util.Date  date = df.parse(rs.getString("TD"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
					if(rs.getString("NAME")==null)
					{
						al.add("Empty");
					}else
					{
					al.add(rs.getString("NAME"));
					}
					al.add(rs.getString("EMAIL"));
					al.add(rs.getString("PHONE_NO"));
					al.add(rs.getString("QUERY"));
					al.add(rs.getString("STUDENT_ID"));
					al.add(rs.getString("E_CORRECT"));
					al.add(rs.getString("M_CORRECT"));
					al.add(rs.getString("ATTENDED"));
					al.add(rs.getString("PICKUP"));
					al.add(rs.getString("COURSE"));
					al.add(rs.getString("COUNTRY_CODE"));
					al.add(ld_date);
					al.add(rs.getString("SEX"));
					al.add(rs.getString("QRY_TYPE"));
					al.add(rs.getString("DISCLAIMER"));
					
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}

private String  getIP(String cookieId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  String ip="";
	  try
	  {
		  con = getConnection(); 
		  String sql="select IP_ADDRESS from easylearning_cookie WHERE COOKIE_ID=?";
		  PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, cookieId);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
					ip=rs.getString(1);
				
					        
				
				
				
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return ip;
}

private ArrayList<String>  getVideoDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT s.ID,s.NAME,s.EMAIL, s.IP_ADDRESS,s.PHONE ,COOKIE_ID, s.ENTERED_DATE,if(p.PAGE_NAME is null,s.PAGE_NUMBER,p.PAGE_NAME) as PAGE_NUMBER ,if(s.COUNTRY_CODE is null,'IN',s.COUNTRY_CODE) as COUNTRY_CODE FROM self_paced_videos_user s left join pages_details p on s.PAGE_NUMBER=p.ID  where s.ID=? ORDER BY ENTERED_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  java.util.Date  date = df.parse(rs.getString("ENTERED_DATE"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
					al.add(rs.getString("NAME"));
					al.add(rs.getString("EMAIL"));
					al.add(rs.getString("PHONE"));
					al.add(ld_date);
					al.add(rs.getString("IP_ADDRESS"));
					al.add(rs.getString("COOKIE_ID"));
					al.add(rs.getString("PAGE_NUMBER"));
					al.add(rs.getString("COUNTRY_CODE"));
					
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getInterest(String email){
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT INTERESTED FROM livedemo_detail where EMAIL=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					al.add(rs.getString(1));
				}	
				else{
					al.add("");
					
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getFreeDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select ID,if(st.COUNTRY_CODE is null,'IN',st.COUNTRY_CODE) as COUNTRY_CODE,st.COUNTRY,sl.STUDENT_ID,STUDENT_NAME,EMAIL_ID,PHONE,SUBJECT_NAME,st.IP_ADDRESS,START_DATE,st.LP_ID,COOKIE_ID,case when ((datediff(END_DATE,sysdate()))<0) then 'expire' else datediff(END_DATE,sysdate()) end as expire from start_free_trial as st left join (select * from subjects) as sub on sub.SUBJECT_ID=st.SUBJECT_ID left join (select * from student_login) as sl on sl.STUDENT_ID=st.STUDENT_ID where ID=? ORDER BY Start_DATE DESC ";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  java.util.Date  date = df.parse(rs.getString("START_DATE"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
					al.add(rs.getString("STUDENT_NAME"));
					al.add(rs.getString("EMAIL_ID"));
					al.add(rs.getString("PHONE"));
					al.add(ld_date);
					al.add(rs.getString("IP_ADDRESS"));
					al.add(rs.getString("COOKIE_ID"));
					al.add(rs.getString("SUBJECT_NAME"));
					al.add(rs.getString("STUDENT_ID"));
					al.add(rs.getString("COUNTRY"));
					al.add(rs.getString("COUNTRY_CODE"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getFreeInterest(String email){
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT INTERESTED FROM livedemo_detail where EMAIL=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					al.add(rs.getString(1));
				}	
				else{
					al.add("");
					
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getCallDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select * from callback where CALLBACK_ID=? order by CALLBACK_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  java.util.Date  date = df.parse(rs.getString("CALLBACK_DATE"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
					al.add(rs.getString("NAME"));
					al.add(rs.getString("EMAIL"));
					al.add(rs.getString("PHONE"));
					al.add(ld_date);
					al.add(rs.getString("IP_ADDRESS"));
					//al.add(rs.getString("COOKIE_ID"));
					//al.add(rs.getString("SUBJECT_NAME"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getCallInterest(String email){
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT INTERESTED FROM livedemo_detail where EMAIL=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					al.add(rs.getString(1));
				}	
				else{
					al.add("");
					
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getContactDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select * from lms_contact  where CONTACT_ID=? group by EMAIL,date(CONTACT_DATE) order by CONTACT_DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  java.util.Date  date = df.parse(rs.getString("CONTACT_DATE"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
					al.add(rs.getString("NAME"));
					al.add(rs.getString("EMAIL"));
					al.add(rs.getString("PHONE_NO"));
					al.add(ld_date);
					al.add(rs.getString("IP_ADDRESS"));
					//al.add(rs.getString("COOKIE_ID"));
					al.add(rs.getString("COURSE"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}
private ArrayList<String>  getChatDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="select * from chat_history where ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  java.util.Date  date = df.parse(rs.getString("TIMESTAMP"));
					  
					  Calendar cal = Calendar.getInstance();
						 cal.setTime(date);
							
							if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=8)
								 cal.add(Calendar.DATE, 1);
								
							} 
							else
							{
								if(cal.get(Calendar.HOUR_OF_DAY)>=9)
									 cal.add(Calendar.DATE, 1);
							}
							DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");   
							String ld_date= df2.format(cal.getTime());
							//System.out.println(ld_date);
							/*Map<String, String> countries = new HashMap<String, String>();
							for (String iso : Locale.getISOCountries()) {
							    Locale l = new Locale("", iso);
							    countries.put(l.getDisplayCountry(),iso);
							}
							String dd=countries.get(rs.getString("COUNTRY"));
							System.out.println(dd);*/

					al.add(rs.getString("NAME"));
					al.add(rs.getString("EMAIL"));
					al.add(rs.getString("PHONE_NO"));
					al.add(ld_date);
					//al.add(rs.getString("IP_ADDRESS"));
					al.add(rs.getString("COUNTRY"));
					al.add(rs.getString("COURSE"));
					al.add(rs.getString("TYPE"));
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}

private ArrayList<String>  getViewStudentDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID,B.BATCH_TYPE,B.START_TIME,CERTIFICATE_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN lms_batch as B ON B.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=B.SUBJECT_ID left outer join (select * from student_certificate group by STUDENT_ID) as C on C.STUDENT_ID=A.STUDENT_ID where M.STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps1 = con.prepareStatement("select IP_ADDRESS from easylearning_cookie where STUDENT_ID=?");
				ps.setString(1, id);
				ps1.setString(1, id);
				
				ResultSet rs=ps.executeQuery();
				
				while(rs.next())
				{
					ResultSet rs1=ps1.executeQuery();
					al.add(rs.getString("STUDENT_NAME"));
					al.add(rs.getString("EMAIL_ID"));
					al.add(rs.getString("PHONE"));
					al.add(rs.getString("SUBJECT_NAME"));
					if(rs1.next()){
						al.add(rs1.getString("IP_ADDRESS"));
					}
					else{
						al.add("");
					}
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}
private ArrayList<String>  getUnallotedtDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT * FROM student_login WHERE STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps1 = con.prepareStatement("select IP_ADDRESS from easylearning_cookie where STUDENT_ID=?");
				ps.setString(1, id);
				ps1.setString(1, id);
				
				ResultSet rs=ps.executeQuery();
				
				while(rs.next())
				{
					ResultSet rs1=ps1.executeQuery();
					al.add(rs.getString("STUDENT_NAME"));
					al.add(rs.getString("EMAIL_ID"));
					al.add(rs.getString("PHONE"));
					//al.add(rs.getString("COUNTRY"));
					//al.add(rs.getString("SUBJECT_NAME"));
					if(rs1.next()){
						al.add(rs1.getString("IP_ADDRESS"));
					}
					else{
						al.add("");
					}
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}
private ArrayList<String>  getContactInterest(String email){
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  String sql="SELECT INTERESTED FROM livedemo_detail where EMAIL=?";
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					al.add(rs.getString(1));
				}	
				else{
					al.add("");
					
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
		  return al;
}
private boolean  setLiveDemo(String query[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  if(!list.isEmpty())
		  { 
				 String sql="insert into query_phonecall_response(RESPONSE_ID,USER,EMAIL,QRY_TYPE,QUERY,PICKUP,INTEREST_LEVEL) values(?,?,?,?,?,?,?)";
				 String sql2="update lms_live_demo set PICKUP=?,INTEREST_LEVEL=? where EMAIL=?";
				 PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, list.get(2));
					ps2.setString(2, list.get(10));
					ps2.setString(3, list.get(0));
					ps2.executeUpdate();
				 for(int i=0;i<query.length;i++)
				 {
		 
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, list.get(3));
				ps.setString(2, list.get(9));
				ps.setString(3, list.get(0));
				ps.setString(4, list.get(1));
				ps.setString(5, query[i]);
				ps.setString(6, list.get(2));
				ps.setString(7, list.get(10));
				
				count=ps.executeUpdate();
				 }
		  }
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  setWebinarClass(String batch,String user,String sessionId[],String email[],String meetingId[],String classLink[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  for(int i=0;i<sessionId.length;i++)
		  {
				String sql="insert into webinar_class(SESSION_ID,EMAIL_ID,USER,BATCH,MEETING_ID,CLASS_LINK) values(?,?,?,?,?,?)";
							
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sessionId[i]);
				ps.setString(2, email[i]);
				ps.setString(3, user);
				ps.setString(4, batch);
				ps.setString(5, meetingId[i]);
				ps.setString(6, classLink[i]);
				count=ps.executeUpdate();
		  }
	  }
		  
	  
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  updateWebinarClass(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="update webinar_class set EMAIL_ID=?,BATCH=?,MEETING_ID=?,CLASS_LINK=? where CLASS_ID=?";
				 if(!list.isEmpty())
				  {
							
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, list.get(2));
				ps.setString(4, list.get(3));
				ps.setString(5, list.get(4));
				count=ps.executeUpdate();
				 }
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  deleteWebinarClass(String classId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="delete from webinar_class  where CLASS_ID=?";
							
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, classId);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  isClassDate(String batch,String sessionId[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  for(int i=0;i<sessionId.length;i++)
		  {
				 String sql="select count(1) from webinar_class WHERE SESSION_ID=? AND BATCH=?";
				 PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sessionId[i]);
				ps.setString(2, batch);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
					count=rs.getInt(1); 
				if(count!=0)
					break;
		  }
	  }
	  catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  return false;
}
private boolean  isWebinarEmail(String email){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  		con = getConnection(); 
				String sql="select count(1) from webinar_account WHERE EMAIL_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
					count=rs.getInt(1);  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  return false;
}
private boolean  setWebinarAccount(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="insert into webinar_account (EMAIL_ID,PASSWORD,USER,EXPIRY_DATE,CREATED_DATE,STATUS) values (?,?,?,?,?,?)";
				
				 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 if(!list.isEmpty())
				 {
				java.util.Date  date = df.parse(list.get(3)); 
				java.sql.Date d2=new java.sql.Date(date.getTime());	
				java.util.Date  date2 = df.parse(list.get(4)); 
				java.sql.Date d3=new java.sql.Date(date2.getTime());	
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setString(3, list.get(2));
				ps.setDate(4, d2);
				ps.setDate(5, d3);
				ps.setString(6, list.get(5));
				count=ps.executeUpdate();
				if(count>0)
				{

					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "CWA");
					ps2.executeUpdate();	
				}
				 }
				
	  }
	  catch(Exception sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  return false;
}
private boolean  updateWebinarAccount(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="update webinar_account set EMAIL_ID=?,PASSWORD=?,EXPIRY_DATE=?,CREATED_DATE=? where EMAIL_ID=?";
				 if(!list.isEmpty()){
					 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 java.util.Date  date = df.parse(list.get(3)); 
					 java.sql.Date d2=new java.sql.Date(date.getTime());
					 java.util.Date  date2 = df.parse(list.get(4)); 
					 java.sql.Date d3=new java.sql.Date(date2.getTime());
							
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, list.get(0));
				ps.setString(2, list.get(1));
				ps.setDate(3, d2);
				ps.setDate(4, d3);
				ps.setString(5, list.get(5));	
				count=ps.executeUpdate();
				if(count>0)
				{

					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "UWA");
					ps2.executeUpdate();	
				}
				 }
				
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  deleteWebinarAccount(String email){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="delete from webinar_account  where EMAIL_ID=?";
							
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				count=ps.executeUpdate();
				if(count>0)
				{

					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, username);
					ps2.setString(2, "DWA");
					ps2.executeUpdate();	
				}
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
public ArrayList<String>  getWebinarAccount(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from webinar_account";	
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
					java.util.Date  date = format1.parse(rs.getString("EXPIRY_DATE"));
					String  date2=format2.format(date);
					
					
					java.util.Date  date4 = format1.parse(rs.getString("CREATED_DATE"));
					String date3= format2.format(date4);
					java.util.Date d=new Date();
					long n = date.getTime() - d.getTime();
					n=n / 1000L / 60L / 60L / 24L;
					al.add(rs.getString("EMAIL_ID")+"abczxy"+rs.getString("PASSWORD")+"abczxy"+rs.getString("USER")+"abczxy"+date3+"abczxy"+date2+"abczxy"+n+"abczxy"+rs.getString("STATUS"));
				}
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  
		  return al;
}
public ArrayList<String>  getWebinarScheduleBatch(String batch,String subjectId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from lms_teacher_session as S join webinar_class as WC on S.SESSION_ID=WC.SESSION_ID where BATCH_ID=? order by SESSION_DATE asc";	
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, batch);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
					java.util.Date  date = format1.parse(rs.getString("SESSION_DATE"));
					
					String  date2=format2.format(date);
					al.add(rs.getString("S.SESSION_ID")+"abczxy"+rs.getString("CLASS_ID")+"abczxy"+rs.getString("USER")+"abczxy"+rs.getString("BATCH_ID")+"abczxy"+rs.getString("MODULE")+"abczxy"+date2+"abczxy"+rs.getString("EMAIL_ID")+"abczxy"+rs.getString("MEETING_ID")+"abczxy"+rs.getString("CLASS_LINK")+"abczxy"+subjectId);
				}
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  
		  return al;
}
private ArrayList<String>  getStudentBatch(String batchId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  	con = getConnection(); 
		 
		  	DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		  	df.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
		  	 java.util.Date  date2=new java.util.Date();
		  	 java.util.Date  date3 = df.parse(date2.toString());
		  	 DateFormat df3 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		  	 df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  	 String time=df3.format(date3);
		  	 java.util.Date date4=df3.parse(time);
			java.sql.Date dd=new java.sql.Date(date4.getTime());
			String sql3="select count(1) FROM lms_teacher_session where BATCH_ID=? AND SESSION_DATE>=?";
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps3.setString(1, batchId);
			ps3.setDate(2, dd);
			ResultSet rs3=ps3.executeQuery();
			while(rs3.next())
			{
			
				String sql="select SESSION_ID,SESSION_DATE,MODULE FROM lms_teacher_session where BATCH_ID=? AND SESSION_DATE>=? limit 1";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, batchId);
				ps2.setDate(2, dd);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{
					SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					java.util.Date  date = format3.parse(rs2.getString("SESSION_DATE"));
					String  sdate=format2.format(date);
					
					al.add(rs2.getString("SESSION_ID")+"/"+sdate+"/"+rs2.getString("MODULE"));
				
				}
			}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private String[]  getWebinar(String sessionId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  String s[]=new String[2];
	  try
	  {
		  	    con = getConnection();
				String sql="select MEETING_ID,CLASS_LINK FROM webinar_class where SESSION_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, sessionId);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{        
					s[0]=rs2.getString("MEETING_ID");
					s[1]=rs2.getString("CLASS_LINK");
				
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return s;
}
private ArrayList<String>  getStudentDetail(String batchId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  	    con = getConnection();
				String sql="select STUDENT_NAME,EMAIL_ID FROM lms_student_alloted as SA join student_login as S on S.STUDENT_ID=SA.STUDENT_ID where BATCH_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, batchId);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{        
					al.add(rs2.getString("STUDENT_NAME")+"abczxy"+rs2.getString("EMAIL_ID"));
				
				}
				if(!al.isEmpty())
				{
					
						String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
						PreparedStatement ps = con.prepareStatement(sql2);
						ps.setString(1, username);
						ps.setString(2, "SCL");
						ps.executeUpdate();	
					
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private ArrayList<String>  getTeacher(String batchId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  	    con = getConnection();
				String sql="select NAME,EMAIL_ID FROM lms_teacher_alloted as TA join lms_main_teacher as T on T.TEACHER_ID=TA.TEACHER_ID where BATCH_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, batchId);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{        
					al.add(rs2.getString("NAME"));
					al.add(rs2.getString("EMAIL_ID"));
				
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private String  getClassDetail(String batchId,String module){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	 String s="";
	  try
	  {
		  	    con = getConnection();
				String sql="select SESSION_DATE,MEETING_ID,CLASS_LINK,AC.EMAIL_ID,AC.PASSWORD from lms_teacher_session as TS join webinar_class as WC on TS.SESSION_ID=WC.SESSION_ID join webinar_account as AC on AC.EMAIL_ID=WC.EMAIL_ID  WHERE TS.BATCH_ID=? AND MODULE=?";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, batchId);
				ps2.setString(2, module);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{    
					SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					java.util.Date  date = format3.parse(rs2.getString("SESSION_DATE"));
					String  sdate=format2.format(date);
					s=sdate+"abczxy"+rs2.getString("MEETING_ID")+"abczxy"+rs2.getString("CLASS_LINK")+"abczxy"+rs2.getString("AC.EMAIL_ID")+"abczxy"+rs2.getString("AC.PASSWORD");
				
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return s;
}

private ArrayList<String>  getBatchModule(String batchId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  	    con = getConnection();
		  		String sql="select MODULE from lms_teacher_session  WHERE BATCH_ID=?";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setString(1, batchId);
				
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{        
					al.add(rs2.getString("MODULE"));
				
				}
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
private boolean checkSuperAdmin(String email, String password){
	Connection con = null;
	ResultSet rs = null;
	String pass = "";
	int countTID=0;
	try{
		con = getConnection(); 
		String sql = "SELECT PASSWORD FROM lms_main_admin WHERE USER_NAME = ? AND USER_TYPE = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, email);
		ps.setString(2, "super admin");
		rs = ps.executeQuery();
		while(rs.next()){
			//System.out.println("countTID:  "+countTID);
			pass = rs.getString(1);
		}
		if(pass.equals(password))
		{
			countTID=1;
			System.out.println("super");
		}
	}catch(SQLException sqe){
		System.out.println(sqe);
		sqe.printStackTrace();
	}finally{closeConnection(con);}

	if(countTID != 0)
		return true;
	else 
		return false;
  }
private boolean  setTemplate(String title,String description,String url){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="insert into upload_template(title,description,url) values(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, title);
				ps.setString(2, description);
				ps.setString(3, url);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  updateTemplate(String id,String title,String description,String url){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="update upload_template set title=?,description=?,url=? where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, title);
				ps.setString(2, description);
				ps.setString(3, url);
				ps.setString(4, id);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  deleteTemplate(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="delete from upload_template where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, id);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private ArrayList<String>  getTemplate(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  upload_template";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					al.add(rs.getString("ID")+"abczxy"+rs.getString("TITLE")+"abczxy"+rs.getString("DESCRIPTION")+"abczxy"+rs.getString("URL"));
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private boolean  checkFAQ(String question){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select count(1) from faq_question_answer where QUESTION=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, question);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
					count=rs.getInt(1);
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  return false;
}
private boolean  checkFAQ(String question,String oldQuestion){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select count(1) from faq_question_answer where QUESTION=? and QUESTION!=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, question);
				ps.setString(2, oldQuestion);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
					count=rs.getInt(1);
				   
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  return false;
}
private boolean  setFAQ(Faq fq){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="insert into faq_question_answer(COURSE,QUESTION,ANSWER,FAQ_TYPE,TITLE,KEYWORD,DESCRIPTION) values(?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fq.getCourse());
				ps.setString(2, fq.getQuestionName());
				ps.setString(3, fq.getAnswer());
				ps.setString(4, fq.getFaqType());
				ps.setString(5, fq.getTitle());
				ps.setString(6, fq.getKeyword());
				ps.setString(7, fq.getDescription());
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  updateFAQ(Faq fq){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="update faq_question_answer set COURSE=?,QUESTION=?,ANSWER=?,FAQ_TYPE=?,TITLE=?,KEYWORD=?,DESCRIPTION=? where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fq.getCourse());
				ps.setString(2, fq.getQuestionName());
				ps.setString(3, fq.getAnswer());
				ps.setString(4, fq.getFaqType());
				ps.setString(5, fq.getTitle());
				ps.setString(6, fq.getKeyword());
				ps.setString(7, fq.getDescription());
				ps.setString(8, fq.getQuestionId());
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  deleteFAQ(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="delete from faq_question_answer where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, id);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private List<Faq>  getFAQ(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq_question_answer";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm:ss");
					String ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
						 fq=new Faq();
						 fq.setQuestionId(rs.getString("ID"));
						 fq.setQuestionName(rs.getString("QUESTION"));
						 fq.setAnswer(rs.getString("ANSWER"));
						 fq.setCourse(rs.getString("COURSE"));
						 fq.setFaqType(rs.getString("FAQ_TYPE"));
						 fq.setTitle(rs.getString("TITLE"));
						 fq.setKeyword(rs.getString("KEYWORD"));
						 fq.setDate(ld_date);
						 fq.setDescription(rs.getString("DESCRIPTION"));
					al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<Faq>  getFAQDetail(String questionId){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq_question_answer where ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, questionId);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					fq=new Faq();
					 fq.setQuestionId(rs.getString("ID"));
					 fq.setQuestionName(rs.getString("QUESTION"));
					 fq.setAnswer(rs.getString("ANSWER"));
					 fq.setCourse(rs.getString("COURSE"));
					 fq.setFaqType(rs.getString("FAQ_TYPE"));
					 fq.setTitle(rs.getString("TITLE"));
					 fq.setKeyword(rs.getString("KEYWORD"));
					 fq.setDescription(rs.getString("DESCRIPTION"));
				al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<Faq>  getFAQ(String course){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq_question_answer where course=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, course);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm:ss");
					String ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
						 fq=new Faq();
						 fq.setQuestionId(rs.getString("ID"));
						 fq.setQuestionName(rs.getString("QUESTION"));
						 fq.setAnswer(rs.getString("ANSWER"));
						 fq.setCourse(rs.getString("COURSE"));
						 fq.setFaqType(rs.getString("FAQ_TYPE"));
						 fq.setTitle(rs.getString("TITLE"));
						 fq.setKeyword(rs.getString("KEYWORD"));
						 fq.setDate(ld_date);
						 fq.setDescription(rs.getString("DESCRIPTION"));
					al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private boolean  setMainFAQ(Faq fq){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="insert into faq(NAME,DESIGNATION,COURSE,REVIEW,STAR,IMAGE) values(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fq.getName());
				ps.setString(2, fq.getDesignation());
				ps.setString(3, fq.getCourse());
				ps.setString(4, fq.getReview());
				ps.setString(5, fq.getStar());
				ps.setString(6, fq.getImage());
			
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  updateMainFAQ(Faq fq){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="update faq set NAME=?,DESIGNATION=?,COURSE=?,REVIEW=?,STAR=?,IMAGE=? where ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fq.getName());
				ps.setString(2, fq.getDesignation());
				ps.setString(3, fq.getCourse());
				ps.setString(4, fq.getReview());
				ps.setString(5, fq.getStar());
				ps.setString(6, fq.getImage());
				ps.setString(7, fq.getFaqId());
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private boolean  deleteMainFAQ(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="delete from faq where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, id);
				count=ps.executeUpdate();
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  if(count!=0)
		  return true;
	  else
		  
		  return false;
}
private List<Faq>  getMainFAQ(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					
		
						 fq=new Faq();
						 fq.setFaqId(rs.getString("ID"));
						 fq.setName(rs.getString("NAME"));
						 fq.setDesignation(rs.getString("DESIGNATION"));
						 fq.setCourse(rs.getString("COURSE"));
						 fq.setReview(rs.getString("REVIEW"));
						 fq.setStar(rs.getString("STAR"));
						 fq.setImage(rs.getString("IMAGE"));
					al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<Faq>  getMainFAQDetail(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq where ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					 fq=new Faq();
					 fq.setFaqId(rs.getString("ID"));
					 fq.setName(rs.getString("NAME"));
					 fq.setDesignation(rs.getString("DESIGNATION"));
					 fq.setCourse(rs.getString("COURSE"));
					 fq.setReview(rs.getString("REVIEW"));
					 fq.setStar(rs.getString("STAR"));
					 fq.setImage(rs.getString("IMAGE"));
				al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<Faq>  getMainFAQ(String course){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq where course=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, course);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					 fq=new Faq();
					 fq.setFaqId(rs.getString("ID"));
					 fq.setName(rs.getString("NAME"));
					 fq.setDesignation(rs.getString("DESIGNATION"));
					 fq.setCourse(rs.getString("COURSE"));
					 fq.setReview(rs.getString("REVIEW"));
					 fq.setStar(rs.getString("STAR"));
					 fq.setImage(rs.getString("IMAGE"));
				al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<Faq>  getFAQInfo(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<Faq> al=new ArrayList<Faq>();
	  Faq fq;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq_question_answer where ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm:ss");
					String ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
						 fq=new Faq();
						 fq.setQuestionId(rs.getString("ID"));
						 fq.setQuestionName(rs.getString("QUESTION"));
						 fq.setAnswer(rs.getString("ANSWER"));
						 fq.setCourse(rs.getString("COURSE"));
						 fq.setFaqType(rs.getString("FAQ_TYPE"));
						 fq.setTitle(rs.getString("TITLE"));
						 fq.setKeyword(rs.getString("KEYWORD"));
						 fq.setDate(ld_date);
						 fq.setDescription(rs.getString("DESCRIPTION"));
					al.add(fq);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return al;
}
private List<LiveDemo>  getFAQReviews(String id){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String sid=" ",name=" ",email=" ";
	    LiveDemo ld = null;
	  try
	  {
		 
		  con = getConnection(); 
		  
				 String sql="select * from  faq_reviews as F left outer join student_login as L on F.STUDENT_ID=L.STUDENT_ID  where Q_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					sid=" ";
					name=" ";
					email=" ";
					 ld = new LiveDemo();
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm:ss");
					String ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
						 	if(rs.getString("L.Email_ID")!=null)
						 		email=rs.getString("L.Email_ID");
						 	if(rs.getString("L.Student_Name")!=null)
						 		name=rs.getString("L.Student_Name");
						 	if(rs.getString("F.STUDENT_ID")!=null && rs.getString("F.STUDENT_ID")!="")
						 		sid=rs.getString("F.STUDENT_ID");
						 	ld.setStudentId(sid);
				            ld.setStudentName(name);
				            ld.setEmail(email);
				            ld.setQuery(rs.getString("REVIEW"));
				            ld.setDateTime(ld_date);
				            ld.setQueryId(rs.getString("ID"));
				            ld.setIpAddress(rs.getString("IP_ADDRESS"));
				           
				            list.add(ld);
				}
				 
		  
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  return list;
}

//Add New Books
private int addNewBooks(String bookId, String bookName)
{
	Connection con = null;
	int response=-1;
	  try
	  {
		  con = getConnection(); 
		  PreparedStatement ps=con.prepareStatement("select count(*) from management_book where BOOK_ID=?");
		  ps.setString(1, bookId);
		  ResultSet rs=ps.executeQuery();
		  while(rs.next())
		  {
			  response=rs.getInt(1);
		  }
		  
		  if(response==0)
		  {
			  PreparedStatement ps1=con.prepareStatement("insert into management_book(BOOK_ID,BOOK_NAME) values(?,?)");
			  ps1.setString(1, bookId);
			  ps1.setString(2, bookName);
			  response=ps1.executeUpdate();
		  }
		  else
			  response=0;
	  }
	  catch(Exception sqe){
		  response=-1;
		  sqe.printStackTrace();
		}finally{closeConnection(con);}
	return response;
}
//end of the controller 
}