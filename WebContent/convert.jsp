<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.util.*" %>
       <%@ page import="java.text.*" %>
<%
String date=request.getParameter("s_date");
String t=request.getParameter("time");
String timezone=request.getParameter("timezone");

if(timezone!=null)
{
	
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	format1.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
	java.util.Date  date2 = format1.parse(date+" "+t);
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy EEE");
		
		DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm:ss");
		 df2.setTimeZone(TimeZone.getTimeZone("UTC"));
		 String utc=df2.format(date2);
		String time="",b_date="";
		
			 df.setTimeZone(TimeZone.getTimeZone(timezone));
			 b_date= df.format(date2);
		        DateFormat df3 = new SimpleDateFormat("HH:mm:ss a");
		        df3.setTimeZone(TimeZone.getTimeZone(timezone));
		        time=df3.format(date2);
%>
<h3 align="center">Time Zone -  <%=timezone %></h3>
<h3 align="center">Local Date -  <%=b_date %></h3>
<h4 align="center">Local Time -  <%=time %></h4>
<h4 align="center">UTC Date Time - <%=utc %></h4>
<%} %>
