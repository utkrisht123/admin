<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="c2" class="com.LiveDemoDAO"></jsp:useBean>
       <%@ page import="java.util.List" %>

<%
List<com.LiveDemo> list=null;
String course=request.getParameter("course");
//System.out.println(course);
if(course!=null && course.equalsIgnoreCase("All"))
	list=c2.getLiveDemoEmail();
else
	list=c2.getLiveDemoEmail(course);


%>
			
							<%
							for(int i=0;i<list.size();i++)
							{
							
							%>
							<tr class="odd gradeX">
								<td>
									<input type="checkbox" class="checkboxes" name="emails" value="<%=list.get(i).getEmail()%>"/>
								</td>
								<td>
									 <%=list.get(i).getStudentName() %>
								</td>
								<td>
									
									<%=list.get(i).getEmail()%>
								</td>
								
							</tr>
							<%} %>
							