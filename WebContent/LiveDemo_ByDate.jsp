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
								<th scope="col">Dashboard</th>
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

if(jEventName!=null && jEventName.equals("V_L_DEMO"))
{
	date=request.getParameter("d");
	date2=request.getParameter("d2");
	list=dao.viewLiveDemoByDate(date, date2);
	
}
else if(jEventName!=null && jEventName.equals("V_L_Attend"))
{
	attend=request.getParameter("attend");
	list=dao.viewAttendedLiveDemo(attend);
	
}
else if(jEventName!=null && jEventName.equals("V_L_Pickup"))
{
	pickup=request.getParameter("pickup");
	list = dao.viewPickupLiveDemo(pickup);
}
else if(jEventName!=null && jEventName.equals("All_LiveDemo"))
{
	
	list = dao.viewAllLiveDemo();
}
else if(jEventName!=null && jEventName.equals("LiveDemo_Status"))
{
	date=request.getParameter("d");
	date2=request.getParameter("d2");
	attend=request.getParameter("attend");
	pickup=request.getParameter("pickup");
	course=request.getParameter("course");
	String country=request.getParameter("country");
	
	System.out.println("ok");
	if(((!date.equals("") && !date2.equals(""))||date.equals("")) && course.equals("")  && attend.equals("") && pickup.equals("") && !country.equals("All"))
	{
		//0.1 -> Date and Country
		 if(date.equals(""))
			 date=date2;
		list=dao.viewDemoByCountry(date,date2,country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && !course.equals("")  && attend.equals("") && pickup.equals("") && !country.equals("All"))
	{
		//0.2 -> Date, Country and Course
		 if(date.equals(""))
			 date=date2;
		list=dao.viewDemoByCountryCourse(course,date, date2,country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && course.equals("")  && !attend.equals("") && pickup.equals("") && !country.equals("All"))
	{
		//0.3 -> Date, Country and Attended
		 if(date.equals(""))
			 date=date2;
		list=dao.viewDemoByCountryAttended(attend,date, date2,country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && course.equals("")  && attend.equals("") && !pickup.equals("") && !country.equals("All"))
	{
		//0.4 -> Date, Country and Pickup
		 if(date.equals(""))
			 date=date2;
		list=dao.viewDemoByCountryPickup(pickup,date, date2,country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && !course.equals("")  && !attend.equals("") && pickup.equals("") && !country.equals("All"))
	{
		if(date.equals(""))
			 date=date2;
		//0.5 -> Date, Country, Course, attended
		list=dao.viewDemoByCountryCourseAttended(course, attend, date, date2, country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && !course.equals("")  && attend.equals("") && !pickup.equals("") && !country.equals("All"))
	{
		if(date.equals(""))
			 date=date2;
		//0.6 -> Date, Country, Course and Pickup
		list=dao.viewDemoByCountryPickupCourse(course, pickup, date, date2, country);
	}
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && course.equals("")  && !attend.equals("") && !pickup.equals("") && !country.equals("All"))
	{
		if(date.equals(""))
			 date=date2;
		//0.7 -> Date, Country, Pickup and Attended
		list=dao.viewDemoByCountryPickupAttended(attend, pickup, date, date2, country);
	}
	
	else if(((!date.equals("") && !date2.equals(""))||date.equals("")) && !course.equals("")  && !attend.equals("") && !pickup.equals("") && !country.equals("All"))
	{
		if(date.equals(""))
			 date=date2;
		//0.8 -> Date, Country, Course,Pickup and Attended
		list=dao.viewDemoByCountryPickupCourseAttended(course, attend, pickup, date, date2, country);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
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
		
		list=new ArrayList<com.LiveDemo>();
	}
	
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