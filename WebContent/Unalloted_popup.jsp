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
String id="";
String lpId="",ip="",cookie_id="",interest="";
String country="",pickup="",course="",date="",pagename="";
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
else if(jEvent != null && jEvent.equalsIgnoreCase("Un_Info")){
	
	//al2 = (ArrayList<String>)request.getAttribute("lp_source");
	ss="View Student All Queries";
	id=(String)request.getAttribute("ID");
	name=(String)request.getAttribute("name");
	email=(String)request.getAttribute("email");
	phone=(String)request.getAttribute("phone");
	//lpId=(String)request.getAttribute("lp_id");
	//pickup=(String)request.getAttribute("pickup");
	//cookie_id=(String)request.getAttribute("cookie");
	ip=(String)request.getAttribute("IP");
	//course=(String)request.getAttribute("course");
	//date=(String)request.getAttribute("date");
	interest=(String)request.getAttribute("interest");
	//pagename=(String)request.getAttribute("page");
	
	System.out.println(ip);
	
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

String inte;

 if(interest.equals("n"))
 {
	 inte="n";
 }
 else if(interest.equals("y"))
 {
	 inte="y";
 }
 else
 {
	 inte="y";
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
						<td style="width:45%">Login</td>
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
							 Phone Number
						</td>
						<td>
							<a href="#" id="phone" name="phone" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required" data-original-title="Enter phone number">
							<%=phone %>
							</a>
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
						<td >
							Course
						</td>
						<td>
							<a href="#" id="co" data-type="text" data-pk="1" data-original-title="Enter username">
							<%=pagename %> </a>
							<input type="hidden" name="date" id="date" value="<%=date %>" />
							<input type="hidden" name="studentname" id="studentname" value="<%=name %>" />
						</td>
						<td>
							Interested
						</td>
						<td>
							<a href="#" id="callinterest" data-type="select" data-pk="1" data-value=<%=inte%> data-original-title="Select">
							
							</a>
							<input type="hidden" name="attended" id="attended" value="Not Attended" />
						</td>
					</tr>
					<tr>
					
						<td>
							 Student Query
						</td>
						<td>
							
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
							 Country
						</td>
						<td>
							<div id="coun"></div>
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
							 Geo Location
						</td>
						<td>
							<div id="zeo"></div>
						</td>
						<td colspan="3">
						<div id="" style="margin-bottom:8px;">
							<select class="form-control input-inline" id="custom_mail" name="custom_mail" >
								<%
								ArrayList<String> aCours=c2.getCourse();
									for(int i=0;i<aCours.size();i++)
										{
											String s=aCours.get(i);
											String s2[]=s.split("/");
											String msg="";
											if(course.equalsIgnoreCase(s2[1]))
													msg="selected";
									%>
									<option value="<%=s2[1] %>" <%=msg %>><%=s2[1] %></option>
										<%} %>
								</select>
											&nbsp;&nbsp;
						</div>
						<input type="button" name="b1" class="btn btn-circle blue" value="Custom Email" onclick="sendCustomEmail();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="b1" class="btn btn-circle grey-cascade" value="Demo Email" onclick="sendDemoEmail();" />
							
						</td>
						
						
						</tr>
					<tr>
					<td>
							 IP Address
						</td>
						<td>
							<%=ip %>
						</td>
						<td colspan="2">	
						<div id="youtubelink">
						<select class="form-control input-small input-inline" id="youtubecourse" name="youtubecourse" >
													<%
													ArrayList<String> aCourse=c2.getCourse();
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
						<input type="button" name="b1" class="btn btn-circle red" value="self Email" onclick="sendSelfEmail();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="b2" class="btn btn-circle red" value="Instructor Email" onclick="sendInstructorEmail();" />
						
						<%--<div id="coursedetail">
							<select class="form-control input-small input-inline" id="detailcourse" name="batchdetailcourse" >
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
							<input type="button" name="b1" class="btn btn-circle red" value="Batch Detail" onclick="sendBatchDetail();" /></div>
							<div id="batchdetail_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="detail"></div>--%>
						
						</td>
						
					
						
					</tr>
					<tr>
					<td>
							Interest Level
						</td>
						<td>
						 <%

						int k=0;
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
						<input type="button" name="b1" class="btn btn-circle purple" value="Syllabus"  onclick="sendSyllabus();" />
						</div>
							<div id="syllabus_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="syllabus"></div>
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
							<br><label class="radio-inline">
							<input type="radio" name="optionsRadios" class="emailaction" id="optionsRadios6" value="special"> Special Demo </label>
							
							<input type="hidden" name="adminquery" id="adminquery" value="" />
						</td>
						
						<td colspan="2">	
						<div id="courseCampaign">
						<select class="form-control input-small input-inline" id="Campaign" name="Campaign" >
													<%
														for(int i=0;i<aCourse.size();i++)
															{
																String s=aCourse.get(i);
																String s2[]=s.split("/");
																String msg="";
																if(course.equalsIgnoreCase(s2[1]))
																		msg="selected";
														if(s2[1].equals("Hadoop")|| s2[1].equals("Python")|| s2[1].equals("MongoDB")|| s2[1].equals("Business Analytics With R"))
																{
														%>
														<option value="<%=s2[1] %>" <%=msg %>"><%=s2[1] %></option>
														
															<%}else{%>
																<option value="All" <%=msg %>"><%=s2[1] %></option>
															
															<%}}%>
											</select>
											&nbsp;&nbsp;
						<input type="button" name="b1" class="btn btn-circle purple" value="Add to Campaign"  onclick="updateEmailCampaign();" />
						</div>
							<div id="Campaign_loading" style="display: none;">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div id="campaign"></div>
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
													<option value="Will request on their own">Will request on their own</option>
													<option value="Doesn&apos;t know english">Doesn&apos;t know english</option>
								</select>
							
							</div>
							
							<div id="custom" style="display :none;"><a href="#" id="query" data-type="textarea" data-pk="1" data-placeholder="Your query here..." data-original-title="Enter Query">
							
							</a></div>
					
							
							<div id="special_demo" style="display :none;">
							<div id="specialDemoCourse">
								<select class="form-control input-small input-inline" id="special_course" name="special_course" >
														<%
														ArrayList<String> aCourse1=c2.getInstructorLedCourse();
															for(int i=0;i<aCourse1.size();i++)
																{
																	String s=aCourse1.get(i);
																	String s2[]=s.split("/");
																	String msg="";
																	if(course.equalsIgnoreCase(s2[1]))
																			msg="selected";
															%>
															<option value="<%=s2[1] %>" <%=msg %>><%=s2[1] %></option>
																<%} %>
								</select>
								</div>
									<br><div class="col-md-4">
										<input type="text" name="datetimepicker_"  id="datetimepicker_" placeholder="Empty" value="" style="color:#4DA3DA;border:0; border-bottom:2px dashed #DE2450;" />
									</div>
								</div>
							<input type="hidden" name="adminquery" id="adminquery" value="" />
							<input type="hidden" name="special_demo_hint" id="special_demo_hint" value="no" />
							
						</td>
							<td colspan="3" style="background-color:#FFFFFF;">	
							<div id="courseview" style="margin-bottom:8px;">
								<select class="form-control input-inline" id="view" name="view" >
									<%
											for(int i=0;i<aCourse.size();i++)
												{
													String s=aCourse.get(i);
													String s2[]=s.split("/");
													String msg="";
													if(course.equalsIgnoreCase(s2[1]))
															msg="selected";
											%>
											<option value="<%=s2[1]%>" <%=msg %>"><%=s2[1] %></option>
									<%} %>
								</select>
						</div>
							<a  href="LoginServlet?jEventName=G_BatchDetail&subject=Hadoop&id=<%=id %>&lp_id=<%=lpId %>&cookie_id=<%=cookie_id %>&jevent=Un_Info" id="view_batch"><input type="button" name="b2" class="btn btn-circle blue-madison" value="View Batch Detail"  /></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
							<a  href="LoginServlet?jEventName=R_Student&email=<%=email %>&name=<%=name %>&phone=<%=phone %>" target="_blank" ><input type="button" name="b2" class="btn btn-circle yellow-crusta" value="Register"  /></a>				&nbsp;&nbsp;
						</td>
						</tr>
						<tr>
						<td>Send SMS</td>
							<td>	
									<a href="#" id="sendMessage" data-type="textarea"  data-pk="1" data-placeholder="Your your message here..." data-original-title="Enter Message">
									</a> 
									<input type="hidden" id="phoneNumber" value="<%=phone %>" />
							</td>
						</tr>
						<tr>
						<td></td>
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
						<div class="alert alert-danger" id="datealert" hidden>
								<button class="close" data-close="alert"></button>
								Please Select the Special Demo Date and Time.
						</div>
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
<script src="scripts/Unalloted_editable.js"></script>

<!-- END PAGE LEVEL SCRIPTS -->
<script>
var d = new Date();
var n = d.getDate();
$("#date").val(n);
jQuery(document).ready(function() {    
	
	
   Metronic.init(); // init metronic core componets
   //formEditable.init();
 
   Layout.init(); // init layout
   QuickSidebar.init(); // init quick sidebar
   Demo.init(); // init demo features 
   UNEditable.init();
   
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
   	
       $.get("Unalloted",
       {
       	email: $('#email_id').val(),
       	date: n,
       	time: time,
          jEventName: "Call_Interest"
       },
       function(data,status){
    	
    
       });
   };
   setCallInterest();
   var getLiveDemoDetail=function ()
   {
   	
       $.get("Unalloted",
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
var ccc=null;

var saveLocation=function(id,ip,city,regionName,country,countryCode,timezone,status,lat,lon)
{
	$.get("Unalloted",
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
		             
		             //document.getElementById("country");
		            ccc=countryCode;
		             $('#zeo').html(city+', '+regionName+', '+timezone);
		             $('#coun').html(country);
		             saveLocation(id,ip,city,regionName,country,countryCode,timezone,status,lat,lon);
		             }
	         });
}
updateAccount();
var getLiveDemoResponse=function ()
{
	$('#response_loading').show();
    $.get("Unalloted",
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
var star=0;
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
	
	if($('#special_demo_hint').val()=="yes"){
		var date=$('#datetimepicker_').val();
		if(date.trim()==""){
			$('#response_loading').hide();
			$('#datealert').show();
		}
		
		else{
			$('#adminquery').val("Special Demo for "+$('#special_course').val()+" on "+$('#datetimepicker_').val());
			 $.get("callback",
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
		}
		
	}
	else{
		$.get("callback",
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
		
	}
};
getLiveDemoResponse();
var sendDemoEmail=function ()
{
	
	var phone='<%=phone%>';
	var id='<%=id %>';
	<%-- var studentId='<%=studentId %>';--%>
	var email=$('#email_id').val();
	var country=ccc;
	var name='<%=name %>';
    var date=$('#date').val();
    var	course=$('#custom_mail').val();
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
    window.open("SendDemoEmail.jsp?name="+name+"&email="+email+"&phone="+phone+"&country="+country+"&id="+id+"&date="+date+"&course="+course+"&time="+time+"&interest_level="+star,"_blank");
};
var sendCustomEmail=function ()
{
	
    	var phone='<%=phone%>';
    	var id='<%=id %>';
    	<%--var studentId='<%=studentId %>';--%>
    	var email=$('#email_id').val();
    	var country=ccc;
    	var name='<%=name %>';
    	window.open("Send_Custom_Email.jsp?name="+name+"&email="+email+"&phone="+phone+"&country="+country+"&id="+id+"&interest_level="+star,"_blank");
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
{console.log(star);
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


var sendSelfEmail=function ()
{
	
    	var id='<%=id %>';
    	var email=$('#email_id').val();
    	var country=ccc;
    	var name='<%=name %>';
    	window.open("Self_Paced_Email.jsp?name="+name+"&id="+id+"&email="+email+"&country="+country,"_blank");
};


var sendInstructorEmail=function ()
{
  	
      	var id='<%=id %>';
      	var email=$('#email_id').val();
      	var country=ccc;
      	var name='<%=name %>';
      	var interest_level= star
      	window.open("InstructorLed_Email.jsp?name="+name+"&id="+id+"&email="+email+"&country="+country+"&interest="+interest_level,"_blank");
};

<%--
var sendBatchDetail=function ()
{
	$('#batchdetail_loading').show();
    $.get("EmailServlet",
    {
    	id: '<%=id%>',
    	email: $('#email_id').val(),
    	country:ccc,
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
	 	$("#special_demo").css({
			  display: "none",
			  visibility: "hidden"
			});
	 	 $('#special_demo_hint').val("no");
	 	$('#datealert').hide();
	}
	 else if($(this).attr('id')=="optionsRadios6") 
	 {
		 $("#special_demo").css({
			  display: "block",
			  visibility: "visible"
			});
		 $("#custom").css({
			  display: "none",
			  visibility: "visible"
			});
	 	$("#default").css({
		  display: "none",
		  visibility: "hidden"
		});
	 	
	$('#adminquery').val("Special Demo for "+$('#special_course').val()+" on "+$('#datetimepicker_').val());
	
	$('#special_demo_hint').val("yes");
	$('#datealert').hide();
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
	 	$("#special_demo").css({
			  display: "none",
			  visibility: "hidden"
			});
	 	$('#adminquery').val($('#response').val());
	 	 $('#special_demo_hint').val("no");
	 	$('#datealert').hide();
		 }
	  
});
$('#response').change(function(){
	$('#adminquery').val($('#response').val());
	
});
var updateEmailCampaign=function(email)
{
	$('#Campaign_loading').show();
	console.log($('#Campaign').val());
	
	$.get("Unalloted",
	         {
				email: email,
				course: $('#Campaign').val(),
				name: $('#studentname').val(),
	            jEventName: "Update_Campaign"
	         },
	         function(data,status){
	         	$('#Campaign_loading').hide();
	         	$('#courseCampaign').hide();
	         	$('#campaign').show();
	         	$('#campaign').html('Campaign Add successfully.');
	         	//$('#response_loading').hide();
	         	//$('#hero').show();
	         	//$('#hero').html(data);
	      
	         });
	
}

$("#view").change(function(){
	
	var course=$("#view").val();
	
	var link="LoginServlet?jEventName=G_BatchDetail&subject="+course+"&id=<%=id %>&lp_id=<%=lpId %>&cookie_id=<%=cookie_id %>&jevent=Un_Info";
	
	$("#view_batch").attr("href",link);
	
});
</script>

<script>
jQuery('#datetimepicker_').datetimepicker();
</script>

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>