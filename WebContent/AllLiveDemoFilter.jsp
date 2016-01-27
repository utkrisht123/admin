<%@page import="com.LiveDemoDAO"%>
<%@page import="com.LiveDemo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.sql.*" %>
     <%@ page import="java.util.*" %>
     <%@ page import="java.text.*" %>
   
<div class="table-scrollable">
							<table class="table table-striped table-bordered table-hover" >
							<thead>
							<tr>
								
								
								<th scope="col">S.No.</th>
								<th scope="col">Student Id</th>
								<th scope="col">Student Name</th>
								<th scope="col">Email</th>
								<th scope="col">Phone Number</th>
								<th scope="col">Course</th>
								<th scope="col">Admin Response</th>
								<th scope="col">Date</th>
								
								<th scope="col">Attended</th>
								<th scope="col">Pickup</th>
								<th scope="col" style="width:160px !important">Interest Level</th>
							</tr>
							</thead>
							<tbody>
<%
LiveDemoDAO dao = new LiveDemoDAO();
String date="",date2="";
List<com.LiveDemo> list=null;
DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
df.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
 java.util.Date  date4=new java.util.Date();
 java.util.Date  date3 = df.parse(date4.toString());
 DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
 String ld_date=df2.format(date3);
 String attend="",pickup="",course="";
String jEventName=request.getParameter("jEventName");
System.out.println(jEventName);
	date=request.getParameter("d");
	date2=request.getParameter("d2");
	attend=request.getParameter("attend");
	pickup=request.getParameter("pickup");
	course=request.getParameter("course");
	
	
	if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	{
		//21
		
		list=dao.viewAllLiveDemoByCourseDatePickupAndAttend(course,date, date2,pickup,attend);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	{
		//22
		
		list=dao.viewAllLiveDemoByCourseDateAndPickup(course,date,date2,pickup);
	}
	else if(date.equals("") && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	{
		//23
		
		list=dao.viewAllLiveDemoByCoursePickupAndAttend(course,pickup, attend);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !date2.equals("") && !attend.equals("") && !pickup.equals(""))
	{
		//24
		
		list=dao.viewAllLiveDemo(date, date2, pickup, attend);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	{
		//25
		
		list=dao.viewAllLiveDemoByAttendCourseAndDate(attend,course,date, date2);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && pickup.equals(""))
	{
		//26
		
		list=dao.viewAllLiveDemoByCourseAndDate(course,date, date2);
	}
	else if(date.equals("") && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	{
		//27
		
		list=dao.viewAllLiveDemoByCourseAndPickup(course,pickup);
	}
	else if(date.equals("") && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	{
		//28
		
		list=dao.viewAllLiveDemoByCourseAndAttend(course,attend);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && attend.equals("") && !pickup.equals(""))
	{
		//29
		
		list=dao.viewAllLiveDemoByDateAndPickup(date, date2, pickup);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !attend.equals("") && pickup.equals(""))
	{
		//30
		
		list=dao.viewAllLiveDemoByDateAndAttend(date, date2, attend);
	}
	
	else if(date.equals("") && course.equals("") && !attend.equals("") && !pickup.equals(""))
	{
		//31
		
		list=dao.viewAllPickupAndAttendLiveDemo(pickup, attend);
	}
	else if(date.equals("") && !course.equals("")  && attend.equals("") && pickup.equals(""))
	{
		//32
		
		list=dao.viewAllLiveDemoByCourse(course);
	}
	else if((!date.equals("") && course.equals("") && !date2.equals("")) && attend.equals("") && pickup.equals(""))
	{
		//33
		
		list=dao.viewAllLiveDemoByDate(date, date2);
	}
	else if(date.equals("") && course.equals("") && attend.equals("") && !pickup.equals(""))
	{
		//34
		
		list=dao.viewAllPickupLiveDemo(pickup);
	}
	else if(date.equals("") && course.equals("") && !attend.equals("") && pickup.equals(""))
	{
		//35
		
		list = dao.viewAllAttendedLiveDemo(attend);
	}
	else
	{
		
		list=new ArrayList<com.LiveDemo>();
	}
	
for(int i=0;i<list.size();i++)
{
%>
<tr class="odd gradeX">
<td><%=i+1 %></td>
<td><%=list.get(i).getStudentId() %></td>
<%
if(list.get(i).getQuery()!=null && list.get(i).getQuery().equals("Request For Live Demo send by Admin"))
{
%>
<td><a class='fancybox fancybox.iframe' style="color: purple;" href="LoginServlet?jEventName=S_Info&id=<%=list.get(i).getQueryId() %>&lp_id=<%=list.get(i).getLpId() %>&cookie_id=<%=list.get(i).getCookieId() %>&interest=<%=list.get(i).getInterestedLevel() %>" ><%=list.get(i).getStudentName() %></a></td>
<%
}
else
{
	%>
	<td><a class='fancybox fancybox.iframe'  href="LoginServlet?jEventName=S_Info&id=<%=list.get(i).getQueryId() %>&lp_id=<%=list.get(i).getLpId() %>&cookie_id=<%=list.get(i).getCookieId() %>&interest=<%=list.get(i).getInterestedLevel() %>" ><%=list.get(i).getStudentName() %></a></td>
	<%	
}

if(list.get(i).getECorrect().equals("y"))
{
%>
<td><span class="label label-sm label-success"><%=list.get(i).getEmail() %></span></td>
<%
}
else if(list.get(i).getECorrect().equals("n"))
{
	%>
	<td><span class="label label-sm label-danger"><%=list.get(i).getEmail() %></span></td>
	<% 
}
else
{
	%>
	<td><%=list.get(i).getEmail() %></td>
	<% 
}
if(list.get(i).getMCorrect().equals("y"))
{
%>
<td><span class="label label-sm label-success"><%=list.get(i).getPhone() %></span></td>
<%
}
else if(list.get(i).getMCorrect().equals("n"))
{
	%>
	<td><span class="label label-sm label-danger"><%=list.get(i).getPhone() %></span></td>
	<% 
}
else
{
	%>
	<td><%=list.get(i).getPhone() %></td>
	<% 
}
%>
<td><%=list.get(i).getCourse()%></td>
<td><%
if(list.get(i).getLastQuery()==null)
	out.println(list.get(i).getPickup());
else if(list.get(i).getLastQuery()!=null && list.get(i).getLastQuery().equals(""))
	out.println(list.get(i).getPickup());
else
	out.println(list.get(i).getLastQuery());


%></td>
<td><%=list.get(i).getDateTime() %></td>
<%
if(list.get(i).getAttended().equals("y"))
{
%>
<td><span class="label label-sm label-success">Attended</span></td>
<%
}
else
{
%>
<td><span class="label label-sm label-danger">Not Attended</span></td>
<%
}
%>
<td><%
if(list.get(i).getPickup().equalsIgnoreCase("Pickup"))
{
	%>
	<span class="label label-sm label-success"><%=list.get(i).getPickup() %></span>
	<%
}
else
{
	%>
	<span class="label label-sm label-warning"><%=list.get(i).getPickup() %></span>
	<%
}

%></td>
<td ><%

int k=Integer.parseInt(list.get(i).getInterestedLevel());
for(int x=1;x<=5;x++)
{
	if(x<=k)
	{
		%>
		<img src="img/s1.png" height="25px" width="25px" />
		<%
	}
	else
	{
		%>
		<img src="img/s3.png" height="25px" width="25px" />
		<%
	}
}


%></td>


</tr>
<%
}
%>
</table>
</div>