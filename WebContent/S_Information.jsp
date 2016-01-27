<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.sql.*" %>
    <%@ page import="java.util.*" %>
 	<%@ page import="java.text.*" %>
   <jsp:useBean id="c2" class="com.LiveDemoDAO"></jsp:useBean>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.2.0
Version: 3.3.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>Gyansha Infotech | Admin Dashboard</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
<link href="plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link rel="stylesheet" type="text/css" href="plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-datepicker/css/datepicker.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-editable/inputs-ext/address/address.css"/>

<link rel="stylesheet" type="text/css" href="plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>

<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN PAGE STYLES -->
<link href="css/tasks.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="css/components.css" rel="stylesheet" type="text/css"/>
<link href="css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="css/layout.css" rel="stylesheet" type="text/css"/>
<link href="css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="img/logo2.png"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed page-quick-sidebar-over-content">

<%
if(session.getAttribute("user")==null)
{
	String jMessage2 = "Your Session has expired";
	  request.setAttribute("jMessage", jMessage2);
	  request.setAttribute("jEventName", "AdminLogin");
	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
}

%>
<!-- BEGIN HEADER -->


<!-- END HEADER -->

<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN STYLE CUSTOMIZER -->
			
			<!-- END STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<%
boolean flag=false;
String f=null;
String ss="View All Student Contact Request";
String name="",email="",phone="";
String studentId="";
String id="";
String e_correct="";
String m_correct="";
String attend="";
String query="";
String lpId="",ip="",cookie_id="",sex="",qryType="",interest="0",disclaimer="y";
String country="",pickup="",course="",date="";
String user=(String)session.getAttribute("user");
if(session.getAttribute("user")==null)
{
	String jMessage = "Your Session has expired";
	  request.setAttribute("jMessage", jMessage);
	  request.setAttribute("jEventName", "AdminLogin");
	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
}


String jNotification = (String)session.getAttribute("jNotification");
if(jNotification==null)
	jNotification="";
String jEvent = (String)request.getAttribute("jEventName");
String jMessage =  null;
ArrayList<String> al2 = new ArrayList<String>();
if(!(jEvent != null))	
	jEvent = "Intial";
//System.out.println("jEvent: "+jEvent);
else if(jEvent != null && jEvent.equalsIgnoreCase("S_Info")){
	
	al2 = (ArrayList<String>)request.getAttribute("lp_source");
	ss="View Student All Queries";
	id=(String)request.getAttribute("id");
	studentId=(String)request.getAttribute("student_id");
	name=(String)request.getAttribute("name");
	email=(String)request.getAttribute("email");
	phone=(String)request.getAttribute("phone");
	e_correct=(String)request.getAttribute("e_correct");
	m_correct=(String)request.getAttribute("m_correct");
	attend=(String)request.getAttribute("attend");
	query=(String)request.getAttribute("query");
	lpId=(String)request.getAttribute("lp_id");
	country=(String)request.getAttribute("country");
	pickup=(String)request.getAttribute("pickup");
	cookie_id=(String)request.getAttribute("cookie_id");
	ip=(String)request.getAttribute("ip");
	course=(String)request.getAttribute("course");
	date=(String)request.getAttribute("date");
	sex=(String)request.getAttribute("sex");
	qryType=(String)request.getAttribute("qry_type");
	interest=(String)request.getAttribute("interest");
	disclaimer=(String)request.getAttribute("disclaimer");

}

String source="",page_url="",content="",compaign_type="";
if(!al2.isEmpty())
{
	ip=al2.get(0);
	source=al2.get(1);
	page_url=al2.get(2);
	content=al2.get(3);
	compaign_type=al2.get(4);
}
String sx1="",sx2="";
if(sex!=null && sex.equalsIgnoreCase("Male"))
	sx1="checked";
else if(sex!=null && sex.equalsIgnoreCase("Female"))
	sx2="checked";
String m1="",e1="",m2="",e2="",a="",a2="";
if(m_correct.equals("n"))
	m2="checked";
else if(m_correct.equals("y"))
	m1="checked";
if(e_correct.equals("n"))
	e2="checked";
else if(e_correct.equals("y"))
	e1="checked";
if(attend.equals("n"))
{
	a="n";
	a2="Not Attended";
}
else if(attend.equals("y"))
{
	a="y";
	a2="Attended";
}
else
{
	a="";
	a2=" ";
}

%>
			<h3 class="page-title">
			Student <small> Information</small>
			</h3>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			
			<div class="row">
				<div class="col-md-12">
					<table id="user" class="table table-bordered table-striped">
					<tbody>
					
					<tr>
						<td style="width:15%">
							 Student Name
						</td>
						<td style="width:25%">
							<a href="#" id="name"  data-type="text" data-pk="1" data-original-title="Enter username">
							<%=name %> </a>
							
						</td>
						<td style="width:15%">Query Type </td>
						<td style="width:45%"><%=qryType %></td>
					</tr>
					<tr>
						<td>
							 Email
						</td>
						<td>
							<a href="#" id="email" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="Enter email">
							<%=email %>
							<input type="hidden" name="email_id" id="email_id" value="<%=email %>" />
							</a>
							</td>
							<td  >
							
							Email Status
							 </td>
							 <td  >
							 <%---- 
							 <a href="#" id="ecorrect" data-type="select" data-pk="1" data-value="<%=e %>" data-original-title="Select"></a>
							--%>
						
							<label class="radio-inline" style="color: green;">
							<input type="radio" name="ee" class="emailstatus" id="email1" value="y" <%=e1 %>> Correct </label>
							<label class="radio-inline" style="color: red;">
							<input type="radio" name="ee" class="emailstatus" id="email2" value="n" <%=e2 %>> Incorrect </label>
							
							
						</td>
						
					</tr>
					<tr>
						<td>
							 Phone Number
						</td>
						<td>
							<a href="#" id="phone" name="phone" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="Enter phone number">
							<%=phone %>
							</a>
							</td>
							<td>
							Phone Status
							</td>
							<td>
							<%---- 
							<a href="#" id="mcorrect" data-type="select" data-pk="1" data-value="<%=m %>" data-original-title="Select">
							</a>
							--%>
							<label class="radio-inline" style="color: green;">
							<input type="radio" name="phone_no" class="phonestatus" id="phone1" value="y" <%=m1 %>> Correct </label>
							<label class="radio-inline" style="color: red;">
							<input type="radio" name="phone_no" class="phonestatus" id="phone2" value="n" <%=m2 %>> Incorrect </label>
						</td>
						
					</tr>
					<tr>
						<td >
							 Course
						</td>
						<td >
							<a href="#" id="co" data-type="text" data-pk="1" data-original-title="Enter username">
							<%=course %> </a>
							<input type="hidden" name="date" id="date" value="<%=date %>" />
							<input type="hidden" name="courseid" id="courseid" value="<%=course %>" />
							<input type="hidden" name="studentname" id="studentname" value="<%=name %>" />
						</td>
					<td >
							 Sex
						</td>
						<td >
						<%--- 
							<a href="#" id="sex" data-type="select" data-pk="1" data-value="<%=sex %>" data-original-title="Select sex">
							</a>
							--%>
							<label class="radio-inline" style="color: green;">
							<input type="radio" name="ss" class="genderstatus" id="sex1" value="Male" <%=sx1 %>> Male </label>
							<label class="radio-inline" style="color: blue;">
							<input type="radio" name="ss" class="genderstatus" id="sex2" value="Female" <%=sx2 %>> Female </label>
						</td>
					</tr>
					
					<tr>
					<td>
							 Attended
						</td>
						<td>
							<a href="#" id="attend" data-type="select" data-pk="1" data-value="<%=a %>" data-original-title="Select Attended Type">
							
							</a>
							<input type="hidden" name="attended" id="attended" value="<%=a2 %>" />
						</td>
					<td>
							Designation
						</td>
						<td>
							<a href="#" id="work" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="What does?">
							
							</a>
						</td>
					</tr>
					<tr>
					
						<td>
							 Student Query
						</td>
						<td>
							<%=query %>
						</td>
					<td>
							 Company Name
						</td>
						<td>
							<a href="#" id="company" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="Enter Company Name">
							
							</a>
						</td>
					</tr>
					<tr>
					<td>
							 Geo Location
						</td>
						<td>
							<div id="zeo"></div>
						</td>
						<td>
							Interested
						</td>
						<td>
							<a href="#" id="callinterest" data-type="select" data-pk="1" data-value="y" data-original-title="Select">
							
							</a>
						</td>
					</tr>
					<tr>
					<td>
							 Country
						</td>
						<td>
							<a href="#" id="country" data-type="select2" data-pk="1" data-value="<%=country %>" data-original-title="Select country">
						
							</a>
							<input type="hidden" name="country" id="count" value="<%=country %>">
						</td>
						<td>
						Next Time To Call</td>
						<td>
						
							<a href="#" id="meeting_start" data-type="datetime" data-pk="1" data-url="/post" data-placement="right" title="Set date & time">
							 </a>
							
						</td>
					</tr>
					<tr>
					<td>
							 IP Address
						</td>
						<td>
							
							<%=ip %>
						
						</td>
						<td>
						Next Webinar</td>
						<td>
							<a href="#" id="vacation" data-type="date" data-viewformat="dd.mm.yyyy" data-pk="1" data-placement="right" data-original-title="Schedule Webinar">
							 </a>
							 <input type="hidden" name="responseid" id="responseid" value="<%=id %>" />
						</td>
						</tr>
					<tr>
					<td>
							 Pickup
						</td>
						<td>
						
						<select class="form-control input-big input-inline" name="pickup" id="pickup"  >
													<option value="Pickup" style="color: green;"  >Pickup</option>
													<option value="Not Pickup" style="color: red;" >Not Pickup</option>
													<option value="Not Reachable" style="color: red;"  >Not Reachable</option>
													<option value="Switched Off" style="color: red;"   >Switched Off</option>
													<option value="Busy" style="color: red;"   >Busy</option>
													<option value="Call Waiting" style="color: red;" >Call Waiting</option>
													<option value="Temporarily Out of Service" style="color: red;" >Temporarily Out of Service</option>
											</select>
						
						
						</td>
						<td colspan="2">
						
						<input type="button" name="b1" class="btn btn-circle blue" value="Custom Email" onclick="sendCustomEmail();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="b1" class="btn btn-circle grey-cascade" value="Demo Email" onclick="sendDemoEmail();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" name="b1" class="btn btn-circle grey-cascade" value="Self Email" onclick="sendSelfEmail();" />
						</td>
						
					</tr>
					<tr>
					<td>
							Interest Level
						</td>
						<td>
						 <%

						int k=Integer.parseInt(interest);
						for(int x=1;x<=5;x++)
						{
							if(x<=k)
							{
								%>
								<img src="img/s1.png" height="25px" width="25px"  id="<%=x %>" onclick="changeStar(this.id);" />
							<%
							}
							else
							{
							%>
							<img src="img/s3.png" height="25px" width="25px"  id="<%=x %>" onclick="changeStar(this.id);" />
							<%
							}
						}


					%>
							<%--- <img src="img/s3.png" height="25px" width="25px" id="1" onclick="changeStar(this.id);"/>
							<img src="img/s3.png" height="25px" width="25px" id="2" onclick="changeStar(this.id);"/>
							<img src="img/s3.png" height="25px" width="25px" id="3" onclick="changeStar(this.id);"/>
							<img src="img/s3.png" height="25px" width="25px" id="4" onclick="changeStar(this.id);" />
							<img src="img/s3.png" height="25px" width="25px" id="5" onclick="changeStar(this.id);" />--%>
						</td>
						<td colspan="2">	
						<div id="youtubelink">
						<select class="form-control input-small input-inline" id="youtubecourse" name="youtubecourse" >
													
													<% ArrayList<String> aCourse=c2.getCourse();
														for(int i=0;i<aCourse.size();i++)
															{
																String s=aCourse.get(i);
																String s2[]=s.split("/");
																String msg="";
																if(course.equalsIgnoreCase(s2[1]))
																		msg="selected";
														%>
														<option value="<%=s2[1] %>" <%=msg %>><%=s2[1] %></option>
															<%} %>
											</select>
											&nbsp;&nbsp;
						<input type="button" name="b1" class="btn btn-circle yellow" value="YouTube Link" onclick="sendYouTubeLink();" /></div>
							<div id="youtube_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="youtube"></div>
						
						
						
						
						</td>
					</tr>
					<tr>
						<td>
							
							&nbsp;
						</td>
						<td >
					
							<label class="radio-inline">
							<input type="radio" name="optionsRadios" class="emailaction" id="optionsRadios4" value="default" checked> Default </label>
							<label class="radio-inline">
							<input type="radio" name="optionsRadios" class="emailaction" id="optionsRadios5" value="custom"> Custom </label>
					
							
							<input type="hidden" name="adminquery" id="adminquery" value="" />
						</td>
						<td colspan="2">	
						<div id="coursedetail">
							<input type="button" name="b1" class="btn btn-circle red" value="Instructor Led Email" onclick="sendBatchDetail();" /></div>
							<div id="batchdetail_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="detail"></div>
						
						</td>
						</tr>
						<tr>
						<td>
							Admin Response
							
						</td>
						<td>
							
							<div id="default">
								<select class="form-control input-big input-inline" name="response" id="response"  >
													<option value="" >Select</option>
													<option value="Interested"   >Interested</option>
													<option value="Not Interested"  >Not Interested</option>
													<option value="Will Attend"   >Will Attend</option>
													<option value="Disconnected"   >Disconnected</option>
													<option value="Might Attend"   >Might Attend</option>
													<option value="Will request on their own"   >Will request on their own</option>
													<option value="Doesn&apos;t know english"   >Doesn&apos;t know english</option>
								</select>
							
							</div>
							
							<div id="custom" style="display :none;"><a href="#" id="query" data-type="textarea" data-pk="1" data-placeholder="Your query here..." data-original-title="Enter Query">
							
							</a></div>
					
							
							<input type="hidden" name="adminquery" id="adminquery" value="" />
							
						</td>
						<td colspan="2">	
						<div id="coursesyllabus">
						<select class="form-control input-small input-inline" id="syllabuscourse" name="syllabuscourse" >
													<%
														for(int i=0;i<aCourse.size();i++)
															{
																String s=aCourse.get(i);
																String s2[]=s.split("/");
																String msg="";
																if(course.equalsIgnoreCase(s2[1]))
																		msg="selected";
														%>
														<option value="<%=s2[1] %>" <%=msg %>><%=s2[1] %></option>
															<%} %>
											</select>
											&nbsp;&nbsp;
						<input type="button" name="b1" class="btn btn-circle purple" value="Syllabus"  onclick="sendSyllabus();" /></div>
							<div id="syllabus_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="syllabus"></div>
						</td>
						</tr>
						<tr>
							<td>Send SMS</td>
							<td>	
									<a href="#" id="sendMessage" data-type="textarea"  data-pk="1" data-placeholder="Your your message here..." data-original-title="Enter Message">
									</a> 
									<input type="hidden" id="phoneNumber" value="<%=phone %>" />
							</td>
							<td colspan="2">	
							
							
							
								<a  href="LoginServlet?jEventName=G_BatchDetail&subject=<%=course %>&id=<%=id %>&lp_id=<%=lpId %>&cookie_id=<%=cookie_id %>&interest=<%=interest %>" ><input type="button" name="b2" class="btn btn-circle blue-madison" value="View Batch Detail"  /></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
									<a  href="LoginServlet?jEventName=R_Student&email=<%=email %>&name=<%=name %>&phone=<%=phone %>" target="_blank" ><input type="button" name="b2" class="btn btn-circle yellow-crusta" value="Register"  /></a>
							
							
							</td>
						</tr>
						<tr>
						<td ></td>
						<td >
							<input type="button" name="b1" class="btn btn btn-circle green-meadow" value="Submit" onclick="setLiveDemoResponse();" />
						</td>
						</tr>
						
						
					</tbody>
					</table>
				</div>
			</div>
			
			
			
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>Admin Response
							</div>
							<div class="tools">
								<a href="javascript:;" class="reload">
								</a>
								<a href="javascript:;" class="remove">
								</a>
							</div>
						</div>
						<div class="portlet-body">
						<div id="response_loading">
								<img src="img/loading.gif" alt="loading"/>
						</div>
						<div id="hero">
						
						
						</div>
							
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
			
			
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
	<!-- BEGIN QUICK SIDEBAR -->
	
	<!-- END QUICK SIDEBAR -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->


<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="plugins/moment.min.js"></script>
<script type="text/javascript" src="plugins/jquery.mockjax.js"></script>
<script type="text/javascript" src="plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
<script type="text/javascript" src="plugins/bootstrap-editable/inputs-ext/address/address.js"></script>
<script type="text/javascript" src="plugins/bootstrap-editable/inputs-ext/wysihtml5/wysihtml5.js"></script>


<script type="text/javascript" src="plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
<script type="text/javascript" src="plugins/datatables/extensions/ColReorder/js/dataTables.colReorder.min.js"></script>
<script type="text/javascript" src="plugins/datatables/extensions/Scroller/js/dataTables.scroller.min.js"></script>
<script type="text/javascript" src="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>


<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="scripts/metronic.js" type="text/javascript"></script>
<script src="scripts/layout.js" type="text/javascript"></script>
<script src="scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="scripts/demo.js" type="text/javascript"></script>
<script src="scripts/index.js" type="text/javascript"></script>
<script src="scripts/tasks.js" type="text/javascript"></script>
<script src="scripts/components-pickers.js"></script>
<script src="scripts/form-editable.js"></script>

<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {    
	
	
   Metronic.init(); // init metronic core componets
   
   Layout.init(); // init layout
   QuickSidebar.init(); // init quick sidebar
   Demo.init(); // init demo features 
   FormEditable.init();
   //TableAdvanced.init();
    var today = new Date();
    var hour    = today.getHours();
    var minute  = today.getMinutes();
    var second  = today.getSeconds();
    
    
    if(hour.toString().length == 1) {
        var hour = '0'+hour;
    }
    if(minute.toString().length == 1) {
        var minute = '0'+minute;
    }
    if(second.toString().length == 1) {
        var second = '0'+second;
    } 
    var time=hour+':'+minute+':'+second;
   var setCallInterest=function ()
   {
   	
       $.get("ServletAction",
       {
       	email: $('#email_id').val(),
       	date: '<%=date %>',
       	time: time,
          jEventName: "Call_Interest"
       },
       function(data,status){
    	
    
       });
   };
   setCallInterest();
   var getLiveDemoDetail=function ()
   {
   	
       $.get("ServletAction",
       {
       	email: $('#email_id').val(),
          jEventName: "G_LiveDemoDetail"
       },
       function(data,status){
      		var x=data.split("abczxy");
       	if(x[0]!=' ')
       		$('#work').html(x[0]);
       	if(x[1]!=' ')
       		$('#company').html(x[1]);
       	if(x[2]!=' ')
       		$('#meeting_start').html(x[2]);
       	if(x[3]!=' ')
       		$('#vacation').html(x[3]);
       });
   };
   
   getLiveDemoDetail();
});
</script>
<script>
var saveLocation=function(id,ip,city,regionName,country,countryCode,timezone,status,lat,lon)
{
	$.get("ServletAction",
	         {
				id: id,
				ip: ip,
				city: city,
				regionName: regionName,
				country: country,
				countryCode: countryCode,
				timezone: timezone,
				status: status,
				lat: lat,
				lon: lon,
	           jEventName: "SaveLocation"
	         },
	         function(data,status){
	        	
	         	
	      
	         });
	
}
var updateAccount=function()
{
	var ip1='<%=ip%>';
	$.ajax({
			url:'http://freegeoip.net/json/'+ip1,
			dataType: 'json',
			type: 'GET',
			success: function(data){ 
	        	
	        	 var x=data;
	        	
	             var ip='<%=ip %>';
	             var id='<%=email %>';
	             var city=x.city;
	   
	             var regionName=x['region_name'];
	             var country=x['country_name'];
	             var countryCode=x['country_code'];
	             var timezone=x['time_zone'];
	             var status="success";
	             var lat=x['latitude'];
	             var lon=x['longitude'];
		             document.getElementById('country').innerHTML=country;
		            $('#zeo').html(city+', '+regionName+', '+timezone);
		            saveLocation(id,ip,city,regionName,country,countryCode,timezone,status,lat,lon);
		             }
	         });
}
var checkCountry="<%=country%>";
if(checkCountry=="HO")
	updateAccount();
var getLiveDemoResponse=function ()
{
	$('#response_loading').show();
    $.get("ServletAction",
    {
    	email: '<%=email%>',
      jEventName: "G_Response"
    },
    function(data,status){
    	$('#response_loading').hide();
    	$('#hero').show();
    	$('#hero').html(data);
 
    });
};
var star='<%=interest%>';
function changeStar(id)
{
	var x=id,i=0;
	var y=document.getElementById("1").src;
	star=id;
	for(i=1;i<=5;i++)
	{
		if(i<=x)
			document.getElementById(i).src="img/s1.png";
		else
			document.getElementById(i).src="img/s3.png";
		if(id==1 && y.match("s1"))
		{
			star=0;
			document.getElementById("1").src="img/s3.png";
		}
		else
		{
			document.getElementById("1").src="img/s1.png";
		}
	}
	//alert(star);
	
}
var setLiveDemoResponse=function ()
{
	$('#response_loading').show();
    $.get("ServletAction",
    {
    	id: '<%=id%>',
    	user: '<%=session.getAttribute("user")%>',
    	email: $('#email_id').val(),
    	pick: $('#pickup').val(),
    	attended: $('#attended').val(),
    	interest_level: star,
    	query: $('#adminquery').val(),
       jEventName: "A_Response"
    },
    function(data,status){
    	$('#response_loading').hide();
    	$('#hero').show();
    	$('#hero').html(data);
 
    });
};
getLiveDemoResponse();
var sendDemoEmail=function ()
{
	
	var phone='<%=phone%>';
	var id='<%=id %>';
	var studentId='<%=studentId %>';
	var email=$('#email_id').val();
	var country='<%=country%>';
	var name='<%=name %>';
    var date=$('#date').val();
    var	course='<%=course%>';
    var today = new Date();
    var hour    = today.getHours();
    var minute  = today.getMinutes();
    var second  = today.getSeconds();
   
    
    if(hour.toString().length == 1) {
        var hour = '0'+hour;
    }
    if(minute.toString().length == 1) {
        var minute = '0'+minute;
    }
    if(second.toString().length == 1) {
        var second = '0'+second;
    } 
    var time=hour+':'+minute+':'+second;
    window.open("SendDemoEmail.jsp?name="+name+"&email="+email+"&phone="+phone+"&country="+country+"&id="+id+"&student_id="+studentId+"&date="+date+"&course="+course+"&time="+time+"&interest_level="+star,"_blank");
};
var sendCustomEmail=function ()
{
	
    	var phone='<%=phone%>';
    	var id='<%=id %>';
    	var studentId='<%=studentId %>';
    	var email=$('#email_id').val();
    	var country='<%=country%>';
    	var name='<%=name %>';
    	window.open("Send_Custom_Email.jsp?name="+name+"&email="+email+"&phone="+phone+"&country="+country+"&id="+id+"&student_id="+studentId+"&interest_level="+star,"_blank");
};

var sendSelfEmail=function ()
{
	
    	var phone='<%=phone%>';
    	var id='<%=id %>';
    	var studentId='<%=studentId %>';
    	var email=$('#email_id').val();
    	var country='<%=country%>';
    	var name='<%=name %>';
    	window.open("Self_Paced_Email.jsp?name="+name+"&id="+id+"&email="+email+"&country="+country,"_blank");
};

var sendSyllabus=function ()
{
	$('#syllabus_loading').show();
    $.get("EmailServlet",
    {
    	id: '<%=id%>',
    	email: $('#email_id').val(),
    	date: $('#date').val(),
    	course: $('#syllabuscourse').val(),
    	name: '<%=name %>',
    	date: $('#date').val(),
    	interest_level: star,
    	jEventName: 'Send_Syllabus'
    },
    function(data,status){
    	$('#syllabus_loading').hide();
    	$('#coursesyllabus').hide();
    	$('#syllabus').show();
    	$('#syllabus').html($('#syllabuscourse').val()+' syllabus sent successfully.');
    	$('#response_loading').hide();
    	$('#hero').show();
    	$('#hero').html(data);
 
    });
};
var sendYouTubeLink=function ()
{
	$('#youtube_loading').show();
    $.get("EmailServlet",
    {
    	id: '<%=id%>',
    	email: $('#email_id').val(),
    	course: $('#youtubecourse').val(),
    	name: '<%=name %>',
    	date: $('#date').val(),
    	interest_level: star,
    	jEventName: 'Send_YouTube'
    },
    function(data,status){
    	$('#youtube_loading').hide();
    	$('#youtubelink').hide();
    	$('#youtube').show();
    	$('#youtube').html($('#youtubecourse').val()+' YouTube link sent successfully.');
    	$('#hero').show();
    	$('#hero').html(data);
    });
};




/*$('#country').change(function() {
	alert("dsfdsfd");
   $('#count').val =$("#country").attr("data-value").val();
    });*/



 var sendBatchDetail=function ()
  {
    	
        	var id='<%=id %>';
        	var email=$('#email_id').val();
        	var country='<%=country%>';
        	var name='<%=name %>';
        	var interest_level= star
        	window.open("InstructorLed_Email.jsp?name="+name+"&id="+id+"&email="+email+"&country="+country+"&interest="+interest_level,"_blank");
 };
  

<%--var sendBatchDetail=function ()
{
	
	$('#batchdetail_loading').show();
    $.get("EmailServlet",
    {
    	id: '<%=id%>',
    	email:$('#email_id').val(),
    	country:$('#count').val(),
    	course: $('#detailcourse').val(),
    	name: '<%=name %>',
    	interest_level: star,
    	jEventName: 'Send_BatchDetail'
    },
    function(data,status){
    	$('#batchdetail_loading').hide();
    	$('#coursedetail').hide();
    	$('#detail').show();
    	$('#detail').html($('#detailcourse').val()+" batch detail sent successfully.");
    	$('#hero').show();
    	$('#hero').html(data);
    });
};--%>

$('.emailaction').click(function(){
	  
	 if($(this).attr('id')=="optionsRadios5")
	{
		 $("#custom").css({
			  display: "block",
			  visibility: "visible"
			});
	 	$("#default").css({
		  display: "none",
		  visibility: "hidden"
		});
	}
	 else
		 {
		 $("#custom").css({
			  display: "none",
			  visibility: "hidden"
			});
	 	$("#default").css({
	 		display: "block",
			visibility: "visible"
		});
	 	$('#adminquery').val($('#response').val());
		 }
	  
 });
$('#response').change(function(){
	$('#adminquery').val($('#response').val());
	
});
var updateEmailStatus=function(email,ecorrect)
{
	$.get("ServletAction",
	         {
				email: email,
				ecorrect: ecorrect,
	           jEventName: "U_EmailStatus"
	         },
	         function(data,status){
	        	
	         });
	
}
var updatePhoneStatus=function(email,mcorrect)
{
	$.get("ServletAction",
	         {
				email: email,
				mcorrect: mcorrect,
	           jEventName: "U_PhoneStatus"
	         },
	         function(data,status){
	         	
	         });
	
}
var updateSex=function(email,sex)
{
	$.get("ServletAction",
	         {
				email: email,
				sex: sex,
	           jEventName: "U_Sex"
	         },
	         function(data,status){
	         	
	         });
	
}
var updateEmailCampaign=function(email)
{
	$.get("ServletAction",
	         {
				email: email,
				course: $('#courseid').val(),
				name: $('#studentname').val(),
	            jEventName: "Update_Campaign"
	         },
	         function(data,status){
	        	
	         });
	
}
$('.emailstatus').click(function(){
	 var m;
	  var email=$('#email_id').val();
	if($(this).attr('id')=="email1")
	{
		m='y';
	}
	else
		m='n';
	updateEmailStatus(email,m);
	if('<%=disclaimer%>'!='n')
		updateEmailCampaign(email);
});
$('.phonestatus').click(function(){
	 var m;
	  var email=$('#email_id').val();
	if($(this).attr('id')=="phone1")
		m='y';
	else
		m='n';
	updatePhoneStatus(email,m);
	
});
$('.genderstatus').click(function(){
	 var m;
	  var email=$('#email_id').val();
	if($(this).attr('id')=="sex1")
		m='Male';
	else
		m='Female';
	updateSex(email,m);
	
});
</script>


<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>