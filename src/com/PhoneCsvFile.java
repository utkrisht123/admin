/**
 * @author Administrator Binay Gaur
 *
 */

package com;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class PhoneCsvFile extends HttpServlet {
	
	 public void doGet(HttpServletRequest request, 
	            HttpServletResponse response) 
	            throws ServletException, IOException {
	     
	  
	        	String filename = "Phone.csv"; // get rid of leading `/`
	        	 response.setContentType("text/csv");
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		        PrintWriter writer = response.getWriter();
	        	String date=request.getParameter("d");
	        	String date2=request.getParameter("d2");
	        	String course=request.getParameter("course");
	        	ArrayList<LiveDemo> al=new ArrayList<LiveDemo>();
	        	LiveDemoDAO dao=new LiveDemoDAO();
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
	        	writer.append("Phone");
	        	writer.append('\n');
      		for(int i=0;i<al.size();i++)
      		{
      			
      			writer.append(al.get(i).getPhone());
      			writer.append('\n');
      		}
      		
      		writer.flush();
      		writer.close();
      		
	  }
}
