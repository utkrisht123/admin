/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class LiveDemoServlet extends HttpServlet  {

	public LiveDemoServlet() {
		super();
	}
	public void doPost(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
		doGet(request,response);
	}
	 public void doGet(HttpServletRequest request, 
	            HttpServletResponse response) 
	            throws ServletException, IOException {
	        int page = 1;
	        int recordsPerPage = 50;
	        int noOfRecords=0;
	        String jEventName = request.getParameter("jEventName");
	        //System.out.print(jEventName);
	        HttpSession session=request.getSession();
	        String username=(String)session.getAttribute("user");
	        String jNotification = (String)session.getAttribute("jNotification");
	        
	        if(jNotification==null) // Live Demo Notification
	        {
	  	  		if(new LiveDemoDAO().getNotificationClassLink()<=0)
	  	  		{
	  	  			jNotification="Send class link of today";
	  	  			session.setAttribute("jNotification", jNotification);
	  	  		}
	  	  		else
	  	  		{
	  		  
	  	  			jNotification="";
	  	  			session.setAttribute("jNotification", jNotification);
	  	  		}
	        }
	        if(request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));
	        LiveDemoDAO dao = new LiveDemoDAO();
	        List<LiveDemo> list=null;
	        PrintWriter out=response.getWriter();
	        if(jEventName != null && jEventName.equalsIgnoreCase("LiveDemo")) // get Live Demo
	        {
	        	 list = dao.viewLiveDemo((page-1)*recordsPerPage,recordsPerPage);
	 	         noOfRecords = dao.getNoOfRecords();
	 	         int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	 	         request.setAttribute("noOfPages", noOfPages);
	 	         request.setAttribute("currentPage", page);
	 	        request.setAttribute("jEventName", jEventName);
		        request.setAttribute("livedemo", list);
		        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
		        view.forward(request, response);
	 	        
	        }
	        else if(jEventName!=null && jEventName.equals("V_L_Course")) // get Live Demo by course date
	        {
	        	
	        	String course=request.getParameter("course");
	        	String d=request.getParameter("d");
	        	String td=request.getParameter("td");
	        	if(course.equalsIgnoreCase("All"))
	        	 list = dao.viewLiveDemoByCourse(d,td);
	        	else
	        	 list = dao.viewLiveDemoByCourse(course,d,td);
	        	 request.setAttribute("course", course);
	        	 request.setAttribute("d", d);
	        	 request.setAttribute("td", td);
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	       System.out.println(request.getParameter("course"));
	 	        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
	 	       System.out.println("dsvfsvc");
	 	        view.forward(request, response);
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("All_LiveDemo")) // get All Live Demo
	        {
	        	list = dao.viewAllLiveDemo();
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("V_AllLiveDemo.jsp");
	 	        view.forward(request, response);
	 	       
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("N_L_DEMO")) // get Live Demo Notification Page
	        {
	        	String course=request.getParameter("course");
	        	if(course.equalsIgnoreCase("All"))
	        		list = dao.viewNotificationLiveDemo();
	        	else
	        		list = dao.viewNotificationLiveDemo(course);
	        	 request.setAttribute("jEventName", jEventName);
	        	 request.setAttribute("course", course);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("LiveDemo_Notification.jsp");
	 	        view.forward(request, response);
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("V_L_DEMO")) // get Live Demo by date
	        {
	        	String d=request.getParameter("d");
	        	String d2=request.getParameter("d2");
	        	list = dao.viewLiveDemoByDate(d,d2);
	 	        request.setAttribute("date", d);
		        request.setAttribute("date2", d2); 
		        request.setAttribute("jEventName", jEventName);
		        request.setAttribute("livedemo", list);
		        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
		        view.forward(request, response);
	        }
	        else if(jEventName!=null && jEventName.equals("V_L_Attend")) // get Live Demo by attend
	        {
	        	String attended=request.getParameter("attended");
	        	list=dao.viewAttendedLiveDemo(attended);
	        	 request.setAttribute("attended", attended);
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
	 	        view.forward(request, response);
	        	
	        }
	        else if(jEventName!=null && jEventName.equals("V_L_Pickup")) // get Live Demo by pickup
	        {
	        	String pickup=request.getParameter("pickup");
	        	list=dao.viewPickupLiveDemo(pickup);
	        	 request.setAttribute("pickup", pickup);
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
	 	        view.forward(request, response);
	        }
	        
	        else if(jEventName!=null && jEventName.equals("Admin_LiveDemo")) // get Live Demo of admin
	        {
	        	
	        	list=dao.viewAdminLiveDemo();
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("V_A_LiveDemo.jsp");
	 	        view.forward(request, response);
	        	
	        }
	        else if(jEventName!=null && jEventName.equals("Today_LiveDemo")) // get Today Live Demo
	        {
	        	
	        	list=dao.viewTodayLiveDemo();
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("Today_LiveDemoRequest.jsp");
	 	        view.forward(request, response);
	        }
	        else if(jEventName!=null && jEventName.equals("Interval_LiveDemo")) // get Live Demo Interval
	        {
	        	
	        	list=dao.viewTodayLiveDemo();
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("IntervalLiveDemo.jsp");
	 	        view.forward(request, response);
	        }
	        else if(jEventName!=null && jEventName.equals("Requests")) // get Live Demo total request by date
	        {
	        	
	        	String d=request.getParameter("d");
	        	String td=request.getParameter("td");
	        	String n=dao.getRequests(d, td);
	        	out.println(n);
	        }
	        else if(jEventName!=null && jEventName.equals("NewRequests")) // get Live Demo not read total request by date
	        {
	        	
	        	String d=request.getParameter("d");
	        	String td=request.getParameter("td");
	        	String n=dao.getNewRequests(d, td);
	        	out.print(n);
	        	//System.out.println(n);
	        }
	        else if(jEventName!=null && jEventName.equals("LiveDemo_Status")) // get Live Demo status give multiple filtration result
	        {
	        	
	        	
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	String attend=request.getParameter("attended");
	        	String pickup=request.getParameter("pickup");
	        	String course=request.getParameter("course");
	        	//changes
	        	if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	        	{
	        		//1
	        		list=dao.viewLiveDemoByCourseDatePickupAndAttend(course,date, date2,pickup,attend);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	        	{
	        		//2
	        		
	        		list=dao.viewLiveDemoByCourseDateAndPickup(course,date,date2,pickup);
	        	}
	        	else if(date.equals("") && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	        	{
	        		//3
	        		
	        		list=dao.viewLiveDemoByCoursePickupAndAttend(course,pickup, attend);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !date2.equals("") && !attend.equals("") && !pickup.equals(""))
	        	{
	        		//4
	        		
	        		list=dao.viewLiveDemo(date, date2, pickup, attend);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	        	{
	        		//5
	        		
	        		list=dao.viewLiveDemoByAttendCourseAndDate(attend,course,date, date2);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && pickup.equals(""))
	        	{
	        		//6
	        		
	        		list=dao.viewLiveDemoByCourseAndDate(course,date, date2);
	        	}
	        	else if(date.equals("") && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	        	{
	        		//7
	        		
	        		list=dao.viewLiveDemoByCourseAndPickup(course,pickup);
	        	}
	        	else if(date.equals("") && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	        	{
	        		//8
	        		
	        		list=dao.viewLiveDemoByCourseAndAttend(course,attend);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") && attend.equals("") && !pickup.equals(""))
	        	{
	        		//9
	        		
	        		list=dao.viewLiveDemoByDateAndPickup(date, date2, pickup);
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !attend.equals("") && pickup.equals(""))
	        	{
	        		//10
	        		
	        		list=dao.viewLiveDemoByDateAndAttend(date, date2, attend);
	        	}
	        	
	        	else if(date.equals("") && course.equals("") && !attend.equals("") && !pickup.equals(""))
	        	{
	        		//11
	        		
	        		list=dao.viewPickupAndAttendLiveDemo(pickup, attend);
	        	}
	        	else if(date.equals("") && !course.equals("")  && attend.equals("") && pickup.equals(""))
	        	{
	        		//12
	        		
	        		list=dao.viewLiveDemoByCourse(course);
	        	}
	        	else if((!date.equals("") && course.equals("") && !date2.equals("")) && attend.equals("") && pickup.equals(""))
	        	{
	        		//13
	        		
	        		list=dao.viewLiveDemoByDate(date, date2);
	        	}
	        	else if(date.equals("") && course.equals("") && attend.equals("") && !pickup.equals(""))
	        	{
	        		//14
	        		
	        		list=dao.viewPickupLiveDemo(pickup);
	        	}
	        	else if(date.equals("") && course.equals("") && !attend.equals("") && pickup.equals(""))
	        	{
	        		//15
	        		
	        		list = dao.viewAttendedLiveDemo(attend);
	        	}
	        	else
	        	{
	        		//System.out.println("none");
	        		list=new ArrayList<com.LiveDemo>();
	        	}
	        	 request.setAttribute("date", date);
			     request.setAttribute("date2", date2); 
			     request.setAttribute("attended", attend);
			     request.setAttribute("pickup", pickup);
			     request.setAttribute("course", course);
	        	 request.setAttribute("jEventName", jEventName);
	 	        request.setAttribute("livedemo", list);
	 	        RequestDispatcher view = request.getRequestDispatcher("V_LiveDemo.jsp");
	 	        view.forward(request, response);
	        }
	        else if(jEventName!=null && jEventName.equals("Instant_Email"))  // // get Live Demo Instant email
	        {
	        	
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	String course=request.getParameter("course");
	        	//System.out.println("d="+date);
	        	//System.out.println("d2="+date2);
	        	//System.out.println("course="+course);
	        	ArrayList<LiveDemo> al=new ArrayList<LiveDemo>();
	        	if((!date.equals("") && !date2.equals("")) && !course.equals(""))
	        	{
	        		//1
	        		al=dao.getInstantEmailByDateCourse(date, date2, course);
	        		
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") )
	        	{
	        		//2
	        		al=dao.getInstantEmailByDate(date, date2);
	        		
	        	}
	        	else if(date.equals("") && !course.equals(""))
	        	{
	        		//3
	        		al=dao.getInstantEmailByCourse(course);
	        		
	        	}
	        	else if(date.equals("") && course.equals(""))
	        	{
	        		//4
	        		
	        		al=dao.getInstantEmail();
	        	}
	        
	        	out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' id='sample_3'>");
	        	out.println("<thead><tr><th scope='col'>S.No.</th><th scope='col'>Email</th><th>Name</th></tr></thead><tbody >");
	        	int j=1;
	        	for(int i=0;i<al.size();i++)
	        	{
	        		j=i+1;
	        		out.println("<tr class='odd gradeX'>");
		        	out.println("<td>"+j+"</td>");
		        	out.println("<td>"+al.get(i).getEmail()+"</td>");
		        	out.println("<td>"+al.get(i).getStudentName()+"</td>");
		        	out.println("</tr>");
	        	}
	        	
	        	out.println("</tbody></table></div>");
	        }
	        else if(jEventName!=null && jEventName.equals("Instant_Phone")) //// get Live Demo Instant Phone 
	        {
	        	
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	String course=request.getParameter("course");
	        	ArrayList<LiveDemo> al=new ArrayList<LiveDemo>();
	        	if((!date.equals("") && !date2.equals("")) && !course.equals(""))
	        	{
	        		//1
	        		al=dao.getInstantPhoneByDateCourse(date, date2, course);
	        		
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") )
	        	{
	        		//2
	        		al=dao.getInstantPhoneByDate(date, date2);
	        		
	        	}
	        	else if(date.equals("") && !course.equals(""))
	        	{
	        		//3
	        		al=dao.getInstantPhoneByCourse(course);
	        		
	        	}
	        	else if(date.equals("") && course.equals(""))
	        	{
	        		//4
	        		al=dao.getInstantPhone();
	        		
	        	}
	        	
	        	/*
	        	 * Send SMS using Instant Phone Number
	        	 */
	        	out.println("<div class='table-scrollable'><form name='sendSMS' id='sendSMS' action='SendSMS.jsp' method='post'><table class='table table-striped table-bordered table-hover' id='sample_3'>");
	        	out.println("<thead><tr><th><input type='checkbox' class='group-checkable' data-set='#sample_3 .checkboxes' checked='checked' id='deselectall'/></th><th scope='col'>Phone Number</th></tr></thead><tbody><tr class='odd gradeX'><th>Message</th><td>");
	        	if(course.equals(""))
	        		out.println("<textarea rows='3' class='form-control' maxlength='159' placeholder='Enter Your Message' name='smsmessage'></textarea>");
	        	else if(course.equals("Hadoop"))
	        		out.println("<textarea rows='3' class='form-control' maxlength='159' placeholder='Enter Your Message' name='smsmessage'>Dear Learner, your Live Demo for Hadoop will be starting in 5 minutes. Easylearning.Guru</textarea>");
	        	else if(course.equals("Python"))
	        		out.println("<textarea rows='3' class='form-control' maxlength='159' placeholder='Enter Your Message' name='smsmessage'>Dear Learner, your Live Demo for Python Django will be starting in 5 minutes. Easylearning.Guru</textarea>");
	        	else if(course.equals("MongoDB"))
	        		out.println("<textarea rows='3' class='form-control' maxlength='159' placeholder='Enter Your Message' name='smsmessage'>Dear Learner, your Live Demo for MongoDB will be starting in 5 minutes. Easylearning.Guru</textarea>");
	        	else if(course.equals("Business Analytics With R"))
	        		out.println("<textarea rows='3' class='form-control' maxlength='159' placeholder='Enter Your Message' name='smsmessage'>Dear Learner, your Live Demo for Business Analytics With R will be starting in 5 minutes. Easylearning.Guru</textarea>");
	        	
	        	out.println("</td></tr>");
	        	int j=1;
	        	for(int i=0;i<al.size();i++)
	        	{
	        		j=i+1;
	        		out.println("<tr class='odd gradeX'><td><input type='checkbox' class='checkboxes' name='c' value='"+al.get(i).getPhone()+"' checked='checked'/></td>");
		        	out.println("<td>"+al.get(i).getPhone()+"</td>");
		        	out.println("</tr>");
	        	}
	        	
	        	out.println("<tr><th scope='col' colspan='2' class='text-center'><input type='submit' class='btn btn-danger' value='Send SMS'/></th></tr></tbody></table><input type='hidden' value='SendSMS' name='jEventName'></form></div>");
	        	out.println("<script>$(document).ready(function(){;$('#deselectall').click(function(){var x1=$('#deselectall').attr('checked');;if(x1=='checked'){ $('input:checkbox').attr('checked','checked'); } else  { $('input:checkbox').removeAttr('checked'); }	});});</script>");
	        }
	        else if(jEventName!=null && jEventName.equals("Webinar_Task")) // // get Live Demo webinar task
	        {
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	list=dao.viewTodayLiveDemoTask(username, date, date2);
	        	
	        	out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' id='sample_3'>");
	        	out.println("<thead><tr><th scope='col'>S.No.</th><th scope='col'>Task</th><th scope='col'>Student Name</th><th scope='col'>Email</th><th scope='col'>Phone Number</th><th scope='col'>Course</th><th scope='col'>Date</th><th scope='col'>Status</th></tr></thead><tbody >");
	        	int j=1;
	        	for(int i=0;i<list.size();i++)
	        	{
	        		j=i+1;
	        		out.println("<tr class='odd gradeX'>");
		        	out.println("<td>"+j+"</td>");
		        	out.println("<td>"+list.get(i).getQuery()+"</td>");
		        	out.println("<td>"+list.get(i).getStudentName()+"</td>");
		        	out.println("<td>"+list.get(i).getEmail()+"</td>");
		        	out.println("<td>"+list.get(i).getPhone()+"</td>");
		        	out.println("<td>"+list.get(i).getCourse()+"</td>");
		        	out.println("<td>"+list.get(i).getDateTime()+"</td>");
		        	if(list.get(i).getAttended().equals("y"))
		        	{
		        		out.println("<td><span class='label label-sm label-success'>Live Demo Link Sent</span></td>");
		        	}
		        	else
		        	{
		        		out.println("<td><a href='Send_Custom_Email.jsp?name="+list.get(i).getStudentName()+"&email="+list.get(i).getEmail()+"&phone="+list.get(i).getPhone()+"&country="+list.get(i).getCountryCode()+"&student_id="+list.get(i).getStudentId()+"&id="+list.get(i).getLpId()+"&q_id="+list.get(i).getQueryId()+"&date="+list.get(i).getDateTime()+"&course="+list.get(i).getCourse()+"' target='_blank'>Send Live Demo Link</a></td>");
		        	}
		        	out.println("</tr>");
	        	}
	        	
	        	out.println("</tbody></table></div>");
	        	/*request.setAttribute("livedemo", list);
		        RequestDispatcher view = request.getRequestDispatcher("TodayLiveDemoWebinar.jsp");
		        view.forward(request, response);*/
	        	
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("C_ChatQuery")){//add code for Create ChatQuery
	        	 
	      	  	String name = request.getParameter("name"); 
	      	  	String email = request.getParameter("email");
	      	  	String phone = request.getParameter("phone");
	      	  	String course = request.getParameter("course");
	            String country = request.getParameter("country");
	            String type = request.getParameter("type");
	            String datetime = request.getParameter("datetime");
	            String source = request.getParameter("source");
	            String url = request.getParameter("url");
	            String jMessage="";
	            LiveDemo ld=new LiveDemo();
	            ld.setStudentName(name);
	            ld.setEmail(email);
	            ld.setPhone(phone);
	            ld.setCourse(course);
	            ld.setQryType(type);
	            ld.setDateTime(datetime);
	            ld.setCountry(country);
	            ld.setSource(source);
	            ld.setPageUrl(url);
	            LiveDemoDAO ldao=new LiveDemoDAO();
	          	  if(ldao.createChatQuery(ld,username))
	          	  {
	          		request.setAttribute("name", "");
	  	        	  request.setAttribute("email", "");
	  	        	  request.setAttribute("phone", "");
	  	        	  request.setAttribute("course", "");
	  	        	  request.setAttribute("country", "");
	  	        	  request.setAttribute("type", "");
	  	        	  request.setAttribute("datetime", "");
	  	        	  request.setAttribute("source", "");
	  	        	  request.setAttribute("url", "");
	  	        	  jMessage = "Chat Query successfully inserted";
	  	        	  request.setAttribute("jEventName", jEventName);
	  	        	  request.setAttribute("jMessage", jMessage);
	  	        	  request.getRequestDispatcher("ChatQuery.jsp").forward(request, response);
	          	  } 
	          	  else
	          	  {
	          		  request.setAttribute("name", name);
	  	        	  request.setAttribute("email", email);
	  	        	  request.setAttribute("phone", phone);
	  	        	  request.setAttribute("course", course);
	  	        	  request.setAttribute("country", country);
	  	        	  request.setAttribute("type", type);
	  	        	  request.setAttribute("datetime", datetime);
	  	        	  request.setAttribute("source", source);
	  	        	  request.setAttribute("url", url);
	          		  jMessage = "ChatQuery has not inserted ... please check the value";
	              	  request.setAttribute("jEventName", jEventName);
	              	  request.setAttribute("jMessage", jMessage);
	              	  request.getRequestDispatcher("ChatQuery.jsp").forward(request, response);
	          	  }
	            
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("U_ChatQuery")){//add code for  Update ChatQuery 
	          	String name = request.getParameter("name");
	      	  	String email = request.getParameter("email");
	      	  	String phone = request.getParameter("phone");
	      	  	String course = request.getParameter("course");
	            String country = request.getParameter("country");
	            String type = request.getParameter("type");
	            String datetime = request.getParameter("datetime");
	            String source = request.getParameter("source");
	            String url = request.getParameter("url");
	            String id=request.getParameter("id");
	            String jMessage="";
	            LiveDemo ld=new LiveDemo();
	            ld.setStudentName(name);
	            ld.setEmail(email);
	            ld.setPhone(phone);
	            ld.setCourse(course);
	            ld.setQryType(type);
	            ld.setDateTime(datetime);
	            ld.setCountry(country);
	            ld.setQueryId(id);
	            ld.setSource(source);
	            ld.setPageUrl(url);
	            LiveDemoDAO ldao=new LiveDemoDAO();
	          	  if(ldao.updateChatQuery(ld))
	          	  {
	          		 
	  	  			  jMessage = "Chat Query successfully updated";
	  	  			  request.setAttribute("jEventName", jEventName);
	  	  			  request.setAttribute("jMessage", jMessage);
	  	  			  request.getRequestDispatcher("ViewChatQuery.jsp").forward(request, response);
	          	  }
	          	  else
	          	  {
	          		 request.setAttribute("name", name);
	  	        	  request.setAttribute("email", email);
	  	        	  request.setAttribute("phone", phone);
	  	        	  request.setAttribute("course", course);
	  	        	  request.setAttribute("country", country);
	  	        	  request.setAttribute("type", type);
	  	        	  request.setAttribute("datetime", datetime);
	  	        	  request.setAttribute("id", id);
	  	        	 request.setAttribute("source", source);
	  	        	  request.setAttribute("url", url);
	  	        	  jMessage = "Chat Query has not updated ... please check the value you enter";
	  	        	  request.setAttribute("jEventName", jEventName);
	  	        	  request.setAttribute("jMessage", jMessage);
	  	        	  request.getRequestDispatcher("ChatQuery.jsp").forward(request, response);
	      		  
	          	  }
	            
	      	  
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("E_ChatQuery")){//add code for  Edit ChatQuery 
	        	String id=request.getParameter("id");
	        	LiveDemoDAO ldao=new LiveDemoDAO();
	        	list=ldao.getChatQueryDetail(id);
	        	String name = list.get(0).getStudentName(); 
	      	  	String email = list.get(0).getEmail();
	      	  	String phone = list.get(0).getPhone();
	      	  	String course = list.get(0).getCourse();
	            String country = list.get(0).getCountry();
	            String type = list.get(0).getQryType();
	            String datetime = list.get(0).getDateTime();
	            String source = list.get(0).getSource();
	            String url = list.get(0).getPageUrl();
	          	  request.setAttribute("jEventName", jEventName);
	          	  request.setAttribute("name", name);
 	        	  request.setAttribute("email", email);
 	        	  request.setAttribute("phone", phone);
 	        	  request.setAttribute("course", course);
 	        	  request.setAttribute("country", country);
 	        	  request.setAttribute("type", type);
 	        	  request.setAttribute("datetime", datetime);
 	        	 request.setAttribute("source", source);
 	        	  request.setAttribute("url", url);
 	        	  request.setAttribute("id", id);
	          	  request.getRequestDispatcher("ChatQuery.jsp").forward(request, response);
	      	  
	      	  
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("D_ChatQuery")){//add code for DELETE ChatQuery 
	      	  String id=request.getParameter("id");
	      	  String jMessage;
	      	  LiveDemoDAO ldao=new LiveDemoDAO();
	      	  if(ldao.deleteChatQuery(id))
	      	  {
	      		  
	    	      
	      		  jMessage = "Chat Query successfully deleted";
	          	  request.setAttribute("jEventName", jEventName);
	          	  request.setAttribute("jMessage", jMessage);
	          	  request.getRequestDispatcher("ViewChatQuery.jsp").forward(request, response);
	      	  }
	      	  else
	      	  {
	      		  		
	    	  			jMessage = "Chat Query has not deleted due to some internal problem";
	    	  			request.setAttribute("jEventName", jEventName);
	    	  			request.setAttribute("jMessage", jMessage);
	    	  			request.getRequestDispatcher("ViewChatQuery.jsp").forward(request, response);
	      	  }
	      	  
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("ChatQuery")){//add code for ChatQuery by filtration
		      	  LiveDemoDAO ldao=new LiveDemoDAO();
		      	 
		      		 
		    	      list=ldao.getChatQuery();
		    	      out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' id='sample_3'>");
			        	out.println("<thead><tr><th scope='col'>S.No.</th><th  scope='col'>User</th><th  scope='col'>Name</th><th  scope='col'>Email</th><th  scope='col'>Phone</th><th  scope='col'>Course</th><th  scope='col'>Query Type</th><th  scope='col'>Country</th><th  scope='col'>Source</th><th  scope='col'>URL</th><th  scope='col'>DateTime</th><th  scope='col'>Edit</th><th  scope='col'>Delete</th><th  scope='col'>Send Custom Email</th></tr></thead><tbody >");
			        	int j=1;
			        	for(int i=0;i<list.size();i++)
			        	{
			        		j=i+1;
			        		out.println("<tr class='odd gradeX'>");
				        	out.println("<td>"+j+"</td>");
				        	out.println("<td>"+list.get(i).getCity()+"</td>");
				        	out.println("<td><a class='fancybox fancybox.iframe' style='color: purple;' href='LoginServlet?jEventName=Chat_Info&ID="+list.get(i).getQueryId()+"'>"+list.get(i).getStudentName()+"</a></td>");
				        	out.println("<td>"+list.get(i).getEmail()+"</td>");
				        	out.println("<td>"+list.get(i).getPhone()+"</td>");
				        	out.println("<td>"+list.get(i).getCourse()+"</td>");
				        	out.println("<td>"+list.get(i).getQryType()+"</td>");
				        	out.println("<td>"+list.get(i).getCountry()+"</td>");
				        	out.println("<td>"+list.get(i).getSource()+"</td>");
				        	out.println("<td>"+list.get(i).getPageUrl()+"</td>");
				        	out.println("<td>"+list.get(i).getDateTime()+"</td>");
				        	out.println("<td><a  href='livedemo.do?jEventName=E_ChatQuery&id="+list.get(i).getQueryId()+"'>Edit</a></td>");
				        	out.println("<td><a  href='livedemo.do?jEventName=D_ChatQuery&id="+list.get(i).getQueryId()+"' onclick='return check();'>Delete</a></td>");
				        	out.println("<td><a href='Send_Custom_Email.jsp?name="+list.get(i).getStudentName()+"&email="+list.get(i).getEmail()+"&phone="+list.get(i).getPhone()+"&country=IN&student_id="+list.get(i).getStudentId()+"&id=0&q_id=0&date= &course="+list.get(i).getCourse()+"' target='_blank'>Send Live Demo Link</a></td>");
				        	out.println("</tr>");
			        	}
			        	
			        	out.println("</tbody></table></div>");
		      	 
		      	  
		        }
	        else if(jEventName!=null && jEventName.equals("ChatQueryByFilter"))
	        {
	        	
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	String course=request.getParameter("course");
	        	
	        	if((!date.equals("") && !date2.equals("")) && !course.equals(""))
	        	{
	        		//1
	        		list=dao.getChatQuery(date, date2, course);
	        		
	        	}
	        	else if((!date.equals("") && !date2.equals("")) && course.equals("") )
	        	{
	        		//2
	        		list=dao.getChatQuery(date, date2);
	        		
	        	}
	        	else if(date.equals("") && !course.equals(""))
	        	{
	        		//3
	        		list=dao.getChatQuery(course);
	        		
	        	}
	        	else if(date.equals("") && course.equals(""))
	        	{
	        		//4
	        		
	        		list=dao.getChatQuery();
	        	}
	        
	        	
	    	      out.println("<div class='table-scrollable'><table class='table table-striped table-bordered table-hover' id='sample_3'>");
	    	  		out.println("<thead><tr><th scope='col'>S.No.</th><th  scope='col'>User</th><th  scope='col'>Name</th><th  scope='col'>Email</th><th  scope='col'>Phone</th><th  scope='col'>Course</th><th  scope='col'>Query Type</th><th  scope='col'>Country</th><th  scope='col'>Source</th><th  scope='col'>URL</th><th  scope='col'>DateTime</th><th  scope='col'>Edit</th><th  scope='col'>Delete</th><th  scope='col'>Send Custom Email</th></tr></thead><tbody >");
	    	  		int j=1;
		        	for(int i=0;i<list.size();i++)
		        	{
		        		j=i+1;
		        		out.println("<tr class='odd gradeX'>");
			        	out.println("<td>"+j+"</td>");
			        	out.println("<td>"+list.get(i).getCity()+"</td>");
			        	out.println("<td>"+list.get(i).getStudentName()+"</td>");
			        	out.println("<td>"+list.get(i).getEmail()+"</td>");
			        	out.println("<td>"+list.get(i).getPhone()+"</td>");
			        	out.println("<td>"+list.get(i).getCourse()+"</td>");
			        	out.println("<td>"+list.get(i).getQryType()+"</td>");
			        	out.println("<td>"+list.get(i).getCountry()+"</td>");
			        	out.println("<td>"+list.get(i).getSource()+"</td>");
			        	out.println("<td>"+list.get(i).getPageUrl()+"</td>");
			        	out.println("<td>"+list.get(i).getDateTime()+"</td>");
			        	out.println("<td><a  href='livedemo.do?jEventName=E_ChatQuery&id="+list.get(i).getQueryId()+"'>Edit</a></td>");
			        	out.println("<td><a  href='livedemo.do?jEventName=D_ChatQuery&id="+list.get(i).getQueryId()+"' onclick='return check();'>Delete</a></td>");
			        	out.println("<td><a href='Send_Custom_Email.jsp?name="+list.get(i).getStudentName()+"&email="+list.get(i).getEmail()+"&phone="+list.get(i).getPhone()+"&country=IN&student_id="+list.get(i).getStudentId()+"&id=0&q_id=0&date= &course="+list.get(i).getCourse()+"' target='_blank'>Send Live Demo Link</a></td>");
			        	out.println("</tr>");
		        	}
		        	
		        	out.println("</tbody></table></div>");
	        }
	        
	        
	        
	       
	    }

}
