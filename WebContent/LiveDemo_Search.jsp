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
 String attend="",pickup="",course="",nameemail="";
 
	date=request.getParameter("d");
	date2=request.getParameter("d2");
	attend=request.getParameter("attend");
	pickup=request.getParameter("pickup");
	course=request.getParameter("course");
	nameemail=request.getParameter("nameemail");
	
	
	if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	{
		//1
		
		list=dao.viewLiveDemoByCourseDatePickupAndAttendSearch(course,date, date2,pickup,attend,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	{
		//2
		
		list=dao.viewLiveDemoByCourseDateAndPickupSearch(course,date,date2,pickup,nameemail);
	}
	else if(date.equals("") && !course.equals("")  && !attend.equals("") && !pickup.equals(""))
	{
		//3
		
		list=dao.viewLiveDemoByCoursePickupAndAttendSearch(course,pickup, attend,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !date2.equals("") && !attend.equals("") && !pickup.equals(""))
	{
		//4
		
		list=dao.viewLiveDemoSearch(date, date2, pickup, attend,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	{
		//5
		
		list=dao.viewLiveDemoByAttendCourseAndDateSearch(attend,course,date, date2,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && !course.equals("")  && attend.equals("") && pickup.equals(""))
	{
		//6
		
		list=dao.viewLiveDemoByCourseAndDateSearch(course,date, date2,nameemail);
	}
	else if(date.equals("") && !course.equals("")  && attend.equals("") && !pickup.equals(""))
	{
		//7
		
		list=dao.viewLiveDemoByCourseAndPickupSearch(course,pickup,nameemail);
	}
	else if(date.equals("") && !course.equals("")  && !attend.equals("") && pickup.equals(""))
	{
		//8
		
		list=dao.viewLiveDemoByCourseAndAttendSearch(course,attend,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && attend.equals("") && !pickup.equals(""))
	{
		//9
		
		list=dao.viewLiveDemoByDateAndPickupSearch(date, date2, pickup,nameemail);
	}
	else if((!date.equals("") && !date2.equals("")) && course.equals("") && !attend.equals("") && pickup.equals(""))
	{
		//10
		
		list=dao.viewLiveDemoByDateAndAttendSearch(date, date2, attend,nameemail);
	}
	
	else if(date.equals("") && course.equals("") && !attend.equals("") && !pickup.equals(""))
	{
		//11
		
		list=dao.viewPickupAndAttendLiveDemo(pickup, attend);
	}
	else if(date.equals("") && !course.equals("")  && attend.equals("") && pickup.equals(""))
	{
		//12
		
		list=dao.viewLiveDemoByCourseSearch(course,nameemail);
	}
	else if((!date.equals("") && course.equals("") && !date2.equals("")) && attend.equals("") && pickup.equals(""))
	{
		//13
		
		list=dao.viewLiveDemoByDateSearch(date, date2,nameemail);
	}
	else if(date.equals("") && course.equals("") && attend.equals("") && !pickup.equals(""))
	{
		//14
		
		list=dao.viewPickupLiveDemoSearch(pickup,nameemail);
	}
	else if(date.equals("") && course.equals("") && !attend.equals("") && pickup.equals(""))
	{
		//15
		
		list = dao.viewAttendedLiveDemoSearch(attend,nameemail);
	}
	else
	{
		//16
		list=dao.viewTodayLiveDemo(nameemail);
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