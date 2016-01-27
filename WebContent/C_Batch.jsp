<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <jsp:useBean id="c2" class="com.LiveDemoDAO"></jsp:useBean>
       <%@ page import="java.util.ArrayList" %>
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
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>Easylearning.guru | Admin</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
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
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-select/bootstrap-select.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/select2/select2.css"/>
<!-- END PAGE LEVEL SCRIPTS -->

<!-- Spinner -->
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-switch/css/bootstrap-switch.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/jquery-tags-input/jquery.tagsinput.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<!-- End Spinner -->

<!-- Date -->
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="css/components.css" rel="stylesheet" type="text/css"/>
<link href="css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="css/themes/default.css" rel="stylesheet" type="text/css"/>
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
<body class="page-header-fixed page-quick-sidebar-over-content ">
<!-- BEGIN HEADER -->
<%

if(session.getAttribute("user")==null)
{
	String jMessage = "Your Session has expired";
	  request.setAttribute("jMessage", jMessage);
	  request.setAttribute("jEventName", "AdminLogin");
	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
}
String jEvent = (String)request.getAttribute("jEventName");
String jMessage="";
String jEventName="C_Batch";
if(!(jEvent != null))	
	jEvent = "Intial";

String subject_id = ""; 
String batch_id = ""; 

String dt="",btype="",duration="02:00",days="",thr="",bprice="0",discount="0",fprice="",tax="",stax="",display="";
	
if(jEvent != null && jEvent.equalsIgnoreCase("C_Batch")){
	jMessage=(String)request.getAttribute("jMessage");
	 dt=(String)request.getAttribute("dt");
	 btype=(String)request.getAttribute("btype");
	 display=(String)request.getAttribute("display");
	 duration=(String)request.getAttribute("duration");
	 days=(String)request.getAttribute("days");
	 thr=(String)request.getAttribute("thr");
	 bprice=(String)request.getAttribute("bprice");
	 discount=(String)request.getAttribute("discount");
	 fprice=(String)request.getAttribute("fprice");
	 stax=(String)request.getAttribute("stax");
	 tax=(String)request.getAttribute("tax");
	 subject_id=(String)request.getAttribute("subject_id");
	 batch_id=(String)request.getAttribute("batch_id");
}
String m1="",m2="";
if(btype!=null && btype.equalsIgnoreCase("Weekday"))
	m1="selected";
else if(btype!=null && btype.equalsIgnoreCase("Weekend"))
	m2="selected";
String n1="",n2="";
if(display!=null && display.equalsIgnoreCase("y"))
	n1="selected";
else if(display!=null && display.equalsIgnoreCase("n"))
	n2="selected";
%>
<jsp:include page="header.jsp" ></jsp:include>

<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	
		<div class="page-sidebar-wrapper">
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			
			<ul class="page-sidebar-menu " data-auto-scroll="true" data-slide-speed="200">
				<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
				<li class="sidebar-toggler-wrapper">
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler">
					</div>
					<!-- END SIDEBAR TOGGLER BUTTON -->
				</li>
				<!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
				
				<li >
					<a href="AdminMain.jsp">
					<i class="icon-home"></i>
					<span class="title">Home</span>
		
					<span class="arrow"></span>
					</a>
				</li>
					<li>
					<a href="javascript:;">
					<i class="fa fa-bell"></i>
					<span class="title">Live Demo</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="livedemo.do?jEventName=LiveDemo">
							<i class="fa fa-bell"></i>
							Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=Today_LiveDemo">
							<i class="icon-basket"></i>
							Today Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=All_LiveDemo">
							<i class="fa fa-beer"></i>
							All Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=Admin_LiveDemo">
							<i class="fa fa-bullhorn"></i>
							Admin Live Demo Request</a>
						</li>
						<li>
							<a href="Self_Paced_Email.jsp">
							<i class="fa fa-envelope"></i>
							Self Paced Email</a>
						</li>
						<li>
							<a href="InstructorLed_Email.jsp">
							<i class="fa fa-envelope"></i>
							Instructor Led Email</a>
						</li>
						<li>
							<a href="LiveDemoQuery.jsp">
							<i class="icon-tag"></i>
							Live Query</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-envelope"></i>
					<span class="title">Contacts</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
						 
							<a href="View_CallBack.jsp">
							<i class="fa fa-phone"></i>
							Call Back Request</a>
						</li>
						
						
						
						<li>
							<a href="V_Contact.jsp">
							<i class="fa fa-envelope"></i>
							
							Contact Request</a>
						</li>
						
						
					</ul>
				</li>
				
				<li>
					<a href="javascript:;">
					<i class="fa fa-users"></i>
					<span class="title">Demo Batch</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="C_D_Batch.jsp">
							<i class="fa fa-users"></i>
							Create Demo Batch</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=G_D_Batch">
							<i class="icon-basket"></i>
							View Demo Batch</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-plus"></i>
					<span class="title">Webinar</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="W_Acount.jsp">
							<i class="icon-home"></i>
							Create Webinar</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=G_W_Account">
							<i class="fa fa-plus-square"></i>
							View Webinar</a>
						</li>
						
					</ul>
				</li>
				<li class="start active open">
					<a href="javascript:;">
					<i class="icon-briefcase"></i>
					<span class="title">Batch</span>
					<span class="selected"></span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li class="active">
							<a href="C_Batch.jsp">
							<i class="icon-home"></i>
							Create Batch</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=G_Batch">
							<i class="icon-briefcase"></i>
							View Batch</a>
						</li>
						<li>
							<a href="SC_Batch.jsp">
							<i class="fa fa-retweet"></i>
							Schedule Batch</a>
						</li>
						<li>
							<a href="Send_Webinar.jsp">
							<i class="fa fa-mail-forward"></i>
							Send Webinar Link</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-user"></i>
					<span class="title">Student Dash Board</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="S_Create.jsp">
							<i class="icon-home"></i>
							Create Student</a>
						</li>
						<li>
							<a href="S_Allot.jsp">
							<i class="fa fa-wheelchair"></i>
							Allot Batch</a>
						</li>
						<li>
							<a href="View_Student.jsp">
							<i class="fa fa-user"></i>
							View Student</a>
						</li>
						
						<li>
							<a href="Student_Profile.jsp">
							<i class="fa fa-male"></i>
							Student Profiles</a>
						</li>
						<li>
							<a href="StudentAction?jEventName=Unalloted_Student">
							<i class="fa fa-child"></i>
							Unalloted Student</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-user-md"></i>
					<span class="title">Teacher Dash Board</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="T_Create.jsp">
							<i class="icon-home"></i>
							Create Teacher</a>
						</li>
						<li>
							<a href="T_Allot.jsp">
							<i class="fa fa-stethoscope"></i>
							Allot Batch</a>
						</li>
						<li>
							<a href="View_Teacher.jsp">
							<i class="fa fa-user-md"></i>
							View Teacher</a>
						</li>
						<li>
							<a href="Teacher_Profile.jsp">
							<i class="fa fa-medkit"></i>
							Teacher Profiles</a>
						</li>
						
					</ul>
				</li>
				<%
				String nature=(String)session.getAttribute("nature");
				if(nature!=null && nature.equalsIgnoreCase("admin head"))
				{
				%>
				<li>
					<a href="javascript:;">
					<i class="fa fa-money"></i>
					<span class="title">Payment Board</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="PaymentServlet?jEventName=CCA_Payment">
							<i class="fa fa-rupee"></i>
							CCAvenue Payment</a>
						</li>
						<li>
							<a href="PaymentServlet?jEventName=PayPal_Payment">
							<i class="fa fa-usd"></i>
							PayPal Payment</a>
						</li>
						<li>
							<a href="PaymentServlet?jEventName=CCA_Request">
							<i class="fa fa-inr"></i>
							CCAvenue Request Only</a>
						</li>
						<li>
							<a href="PaymentServlet?jEventName=PayPal_Request">
							<i class="fa fa-dollar"></i>
							PayPal Request only</a>
						</li>
						<li>
							<a href="Custom_Payment.jsp">
							<i class="fa fa-money"></i>
							Custom Payment</a>
						</li>
						<li>
							<a href="Payment_Coupon.jsp">
							<i class="fa fa-jpy"></i>
							Payment Coupon</a>
						</li>
						<li>
							<a href="Automatic_Payment.jsp">
							<i class="fa fa-eur"></i>
							Automatic Payment Link</a>
						</li>
						<li>
							<a href="V_AutomaticPayment.jsp">
							<i class="fa fa-male"></i>
							View Send Payment Link</a>
						</li>
					</ul>
				</li>
				<%} %>
				<li>
					<a href="javascript:;">
					<i class="fa fa-plus"></i>
					<span class="title">FAQ</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="CreateFAQ.jsp">
							<i class="icon-home"></i>
							Create FAQ</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=V_FAQ">
							<i class="fa fa-plus-square"></i>
							View FAQ</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-plus"></i>
					<span class="title">Press Release</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="enter-press.jsp">
							<i class="icon-home"></i>
							Create Press</a>
						</li>
						<li>
							<a href="view_press.jsp">
							<i class="fa fa-plus-square"></i>
							View Press</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-plus"></i>
					<span class="title">Media Release</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="Create-Media.jsp">
							<i class="icon-home"></i>
							Create Media</a>
						</li>
						<li>
							<a href="View_Media.jsp">
							<i class="fa fa-plus-square"></i>
							View Media</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-plus"></i>
					<span class="title">Self Paced Video</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="Vedio_Details.jsp">
							<i class="icon-home"></i>
							video detail</a>
						</li>
						<li>
							<a href="Free_Trial_Details.jsp">
							<i class="fa fa-plus-square"></i>
							Free Trail Detail</a>
						</li>
						<li >
							<a href="Page_Details.jsp">
							<i class="icon-home"></i>
							Page Detail</a>
						</li>
						<li>
							<a href="Student_Visited_Pages.jsp">
							<i class="fa fa-plus-square"></i>
							Student Visit Page</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="icon-present"></i>
					<span class="title">Extra</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
					<li>
							<a href="Live_Demo_search.jsp">
							Search</a>
						</li>
						<li>
							<a href="Downloads.jsp">
							Downloads</a>
						</li>
						<li>
							<a href="ViewSubscribe.jsp">
							Subscribe</a>
						</li>
						<li>
							<a href="ViewDirectPayment.jsp">
							Direct Payment</a>
						</li>
						<li>
							<a href="ViewLoginRequest.jsp">
							Login Not Request</a>
						</li>
						<li>
							<a href="ServletAction?jEventName=ViewFeedback">
							View Feed Back</a>
						</li>
					</ul>
				</li>
				<li class="heading">
					<h3 class="uppercase">More</h3>
				</li>
				<li class="last ">
					<a href="InstantEmail.jsp">
					<i class="fa fa-envelope-square"></i>
					<span class="title">Instant Email ID</span>
					</a>
				</li>
				<li class="last">
					<a href="InstantPhone.jsp">
					<i class="fa fa-mobile-phone"></i>
					<span class="title">Instant Phone Number</span>
					</a>
				</li>
				<li class="last ">
					<a href="CustomEmail.jsp">
					<i class="fa fa-envelope"></i>
					<span class="title">Custom Email</span>
					</a>
				</li>
				<li class="last ">
					<a href="BulkEmail.jsp">
					<i class="fa fa-envelope-o"></i>
					<span class="title">Bulk Email</span>
					</a>
				</li>
				<li class="last">
					<a href="LoginServlet?jEventName=G_Template">
					<i class="fa fa-upload"></i>
					<span class="title">Template</span>
					</a>
				</li>
				<li class="last ">
					<a href="MyTask.jsp">
					<i class="fa fa-tasks"></i>
					<span class="title">Task</span>
					</a>
				</li>
				<li class="last">
					<a href="Time_Conversion.jsp">
					<i class="fa fa-calendar"></i>
					<span class="title">Time Conversion</span>
					</a>
				</li>
			</ul>
			
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">Modal title</h4>
						</div>
						<div class="modal-body">
							 Widget settings form goes here
						</div>
						<div class="modal-footer">
							<button type="button" class="btn blue">Save changes</button>
							<button type="button" class="btn default" data-dismiss="modal">Close</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN STYLE CUSTOMIZER -->
			<div class="theme-panel hidden-xs hidden-sm">
				<div class="toggler">
				</div>
				<div class="toggler-close">
				</div>
				<div class="theme-options">
					<div class="theme-option theme-colors clearfix">
						<span>
						THEME COLOR </span>
						<ul>
							<li class="color-default current tooltips" data-style="default" data-container="body" data-original-title="Default">
							</li>
							<li class="color-darkblue tooltips" data-style="darkblue" data-container="body" data-original-title="Dark Blue">
							</li>
							<li class="color-blue tooltips" data-style="blue" data-container="body" data-original-title="Blue">
							</li>
							<li class="color-grey tooltips" data-style="grey" data-container="body" data-original-title="Grey">
							</li>
							<li class="color-light tooltips" data-style="light" data-container="body" data-original-title="Light">
							</li>
							<li class="color-light2 tooltips" data-style="light2" data-container="body" data-html="true" data-original-title="Light 2">
							</li>
						</ul>
					</div>
					<div class="theme-option">
						<span>
						Layout </span>
						<select class="layout-option form-control input-small">
							<option value="fluid" selected="selected">Fluid</option>
							<option value="boxed">Boxed</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Header </span>
						<select class="page-header-option form-control input-small">
							<option value="fixed" selected="selected">Fixed</option>
							<option value="default">Default</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Mode</span>
						<select class="sidebar-option form-control input-small">
							<option value="fixed">Fixed</option>
							<option value="default" selected="selected">Default</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Menu </span>
						<select class="sidebar-menu-option form-control input-small">
							<option value="accordion" selected="selected">Accordion</option>
							<option value="hover">Hover</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Style </span>
						<select class="sidebar-style-option form-control input-small">
							<option value="default" selected="selected">Default</option>
							<option value="light">Light</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Position </span>
						<select class="sidebar-pos-option form-control input-small">
							<option value="left" selected="selected">Left</option>
							<option value="right">Right</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Footer </span>
						<select class="page-footer-option form-control input-small">
							<option value="fixed">Fixed</option>
							<option value="default" selected="selected">Default</option>
						</select>
					</div>
				</div>
			</div>
			<!-- END STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			New Batch <small> Create</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="index.html">Home</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">Batch</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="C_Batch.jsp">Create Batch</a>
					</li>
				</ul>
				<div class="page-toolbar">
					<div class="btn-group pull-right">
						<button type="button" class="btn btn-fit-height grey-salt dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
						Actions <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right" role="menu">
							<li>
								<a href="#">Action</a>
							</li>
							<li>
								<a href="#">Another action</a>
							</li>
							<li>
								<a href="#">Something else here</a>
							</li>
							<li class="divider">
							</li>
							<li>
								<a href="#">Separated link</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box blue" id="form_wizard_2">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i> Create New Batch - <span class="step-title">
								Step 1 of 3 </span>
							</div>
							<div class="tools hidden-xs">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="#portlet-config" data-toggle="modal" class="config">
								</a>
								<a href="javascript:;" class="reload">
								</a>
								<a href="javascript:;" class="remove">
								</a>
							</div>
						</div>
						<div class="portlet-body form">
								
							<form action="LoginServlet" class="form-horizontal" id="batch_form" method="POST" name="batch_form">
								<div class="form-wizard">
									<div class="form-body">
										<ul class="nav nav-pills nav-justified steps">
											<li>
												<a href="#tab1" data-toggle="tab" class="step">
												<span class="number">
												1 </span>
												<span class="desc">
												<i class="fa fa-check"></i> Batch Details</span>
												</a>
											</li>
											<li>
												<a href="#tab2" data-toggle="tab" class="step">
												<span class="number">
												2 </span>
												<span class="desc">
												<i class="fa fa-check"></i> Confirm </span>
												</a>
											</li>
											<li>
												<a href="#tab3" data-toggle="tab" class="step">
												<span class="number">
												3 </span>
												<span class="desc">
												<i class="fa fa-check"></i> Finished </span>
												</a>
											</li>
										</ul>
										<div id="bar" class="progress progress-striped" role="progressbar">
											<div class="progress-bar progress-bar-success">
											</div>
										</div>
										<div class="tab-content">
											<div class="alert alert-danger display-none">
												<button class="close" data-dismiss="alert"></button>
												You have some form errors. Please check below.
											</div>
											<div class="alert alert-success display-none">
												<button class="close" data-dismiss="alert"></button>
												Your form validation is successful!
											</div>
											<div class="tab-pane active" id="tab1">
												<h3 class="block">Provide your batch details</h3>
												<% 
													if(jMessage != null && !jMessage.equals("")){
	
															%>
														<div class="alert alert-success">
														<strong><%=jMessage %></strong> 
														</div>
													<%
														}
														%>
												<div class="form-group">
													<label class="control-label col-md-3">Subject <span class="required">
													* </span></label>
													<div class="col-md-4">
														<select class="form-control select2me" data-placeholder="Select..." name="subject_id">
															<option value=""></option>
															<%
														ArrayList<String> aCourse=c2.getCourse();
														for(int i=0;i<aCourse.size();i++)
															{
																String s=aCourse.get(i);
																String s2[]=s.split("/");
																String msg="";
																if(subject_id!=null && subject_id.equals(s2[0]))
																	msg="selected";
														%>
														<option value="<%=s2[0] %>" <%=msg %>><%=s2[1] %></option>
															<%} %>
														</select>
														<span class="help-block">
														Please Select Course </span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Starting Date and Time <span class="required">
													* </span></label>
													<div class="col-md-4">
														<div>
															<div class="input-group date form_datetime" data-date="2015-01-21T15:25:00Z">
																<input type="text" size="16" readonly class="form-control" name="startingdt" value="<%=dt%>">
																<span class="input-group-btn">
																<button class="btn default date-reset" type="button"><i class="fa fa-times"></i></button>
																</span>
																<span class="input-group-btn">
																<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																</span>
															</div>
														</div>
														<!-- /input-group -->
														<span class="help-block">
														Please Select Batch Date and Timing </span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Duration of Class <span class="required">
													* </span></label>
													<div class="col-md-4">
														<div class="input-group">
															<input type="text" class="form-control timepicker timepicker-24 hrs" name="duration" value="<%=duration%>">
															<span class="input-group-btn">
															<button type="button" class="btn default"><i class="fa fa-clock-o"></i></button>
															</span>
														</div>
														<span class="help-block">
														Please Select Class Duration</span>
													</div>
												</div>
												<div class="form-group">
														<label class="control-label col-md-3">Number of days <span class="required">
													* </span></label>
														<div class="col-md-9">
															<div id="spinner1">
																<div class="input-group input-small">
																	<input type="text" readonly="" maxlength="3" class="spinner-input form-control" name="days" value="<%=days%>">
																	<div class="spinner-buttons input-group-btn btn-group-vertical">
																		<button class="btn spinner-up btn-xs blue hrs1" type="button">
																		<i class="fa fa-angle-up"></i>
																		</button>
																		<button class="btn spinner-down btn-xs blue hrs1" type="button">
																		<i class="fa fa-angle-down"></i>
																		</button>
																	</div>
																</div>
															</div>
															<span class="help-block">
															Number of days </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Toal Number of Hours</label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input readonly="" type="text" value="0" name="totalhr" class="form-control" id="totalhr">
															</div>
															<span class="help-block">
															Total Hours For this Course</span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Batch Type <span class="required">
														* </span></label>
														<div class="col-md-4">
															<select class="form-control select2me" data-placeholder="Select..." name="btype">
																<option value=""></option>
																<option value="Weekday" <%=m1 %>>Weekday</option>
																<option value="Weekend" <%=m2 %>>Weekend</option>
															</select>
															<span class="help-block">
															Please Select Batch Type </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Display<span class="required">
														* </span></label>
														<div class="col-md-4">
															<select class="form-control select2me" data-placeholder="Select..." name="display">
																<option value="y" <%=n1 %>>Yes</option>
																<option value="n" <%=n2 %>>No</option>
															</select>
															<span class="help-block">
															Please Select display for showing batch on website </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Batch Base Price <span class="required">
														* </span></label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input id="touchspin_demo1" type="text" value="0" name="bprice" class="form-control amount" >
																<input id="bpp" type="hidden" value="0" name="price" value='<%=bprice%>'>
															</div>
															<span class="help-block">
															basic price </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Discount in %age <span class="required">
														* </span></label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input id="touchspin_demo2" type="text" value="0" name="discount" class="form-control amount">
															</div>
															<span class="help-block">
															Enter discount in %age </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Service Tax in %age <span class="required">
														* </span></label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input id="touchspin_demo3" type="text" value="14.50" name="tax" class="form-control amount">
															</div>
															<span class="help-block">
															Enter service tax in %age </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Service Tax in INR</label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input type="text" value="0" readonly="readonly" name="taxinr" id="taxinr" class="form-control">
															</div>
															<span class="help-block">
															Enter service tax in %age </span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Total Pirce in INR</label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input readonly="" type="text" value="0" name="totalinr" class="form-control" id="totalinr">
															</div>
															<span class="help-block">
															Total Price in INR</span>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3">Total Pirce in USD</label>
														<div class="col-md-9">
															<div class="input-inline input-medium">
																<input readonly="" type="text" value="0" name="totalusd" class="form-control" id="totalusd">
															</div>
															<span class="help-block">
															Total Price in USD</span>
														</div>
													</div>
											</div>
											<div class="tab-pane" id="tab2">
												<h3 class="block">Confirm your batch details</h3>
												<h4 class="form-section">Batch Deatils</h4>
												<div class="form-group">
													<label class="control-label col-md-3">Course Name</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="subject_id">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Starting Batch Date and Time</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="startingdt">
														</p>
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">Duration of Course</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="duration">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Number of Days</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="days">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Total Course Duration in Minutes</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="totalhr">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Batch Type</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="btype">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Display</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="display">
														</p>
													</div>
												</div>
												<h4 class="form-section">Price Details</h4>
												<div class="form-group">
													<label class="control-label col-md-3">Base Price</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="bprice">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Discount in %age</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="discount">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Tax in %age</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="tax">
														</p>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">Tax in INR</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="taxinr">
														</p>
													</div>
												</div> 
												<div class="form-group">
													<label class="control-label col-md-3">Price in INR</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="totalinr">
														</p>
													</div>
												</div> 
												<div class="form-group">
													<label class="control-label col-md-3">Price in USD</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="totalusd">
														</p>
													</div>
												</div> 
											</div>
											<div class="tab-pane" id="tab3">
												<h3 class="block text-info">Send Batch Information to the Super Admin for verify</h3>
												<label class="control-label col-md-3 info"></label>
											</div>
										</div>
									</div>
									<div class="form-actions">
										<div class="row">
											<div class="col-md-offset-3 col-md-9">
												<a href="javascript:;" class="btn default button-previous">
												<i class="m-icon-swapleft"></i> Back </a>
												<a href="javascript:;" class="btn blue button-next">
												Continue <i class="m-icon-swapright m-icon-white"></i>
												</a>
												<a href="javascript:;" class="btn green button-submit">
												Send For Verify <i class="m-icon-swapright m-icon-white"></i>
												</a>
											</div>
										</div>
									</div>
								</div>
								<input type="hidden" name="jEventName" value="C_Batch">
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
	<!-- BEGIN QUICK SIDEBAR -->
	
	<!-- END QUICK SIDEBAR -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="footer.jsp" ></jsp:include>

<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="plugins/respond.min.js"></script>
<script src="plugins/excanvas.min.js"></script> 
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
<script type="text/javascript" src="plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="plugins/jquery-validation/js/additional-methods.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="plugins/bootstrap-select/bootstrap-select.min.js"></script>
<script type="text/javascript" src="plugins/select2/select2.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->

<!-- Date -->
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>

<!-- End Date -->

<!-- Spinner -->
<script type="text/javascript" src="plugins/fuelux/js/spinner.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-fileinput/bootstrap-fileinput.js"></script>
<script type="text/javascript" src="plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script type="text/javascript" src="plugins/jquery.input-ip-address-control-1.0.min.js"></script>
<script src="plugins/bootstrap-pwstrength/pwstrength-bootstrap.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
<script src="plugins/typeahead/handlebars.min.js" type="text/javascript"></script>
<script src="plugins/typeahead/typeahead.bundle.min.js" type="text/javascript"></script>

<!-- end -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="scripts/metronic.js" type="text/javascript"></script>
<script src="scripts/layout.js" type="text/javascript"></script>
<script src="scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="scripts/demo.js" type="text/javascript"></script>
<script src="js/form-wizard.js"></script>
<script src="js/components-dropdowns.js"></script>
<script src="js/components-form-tools.js"></script>
	<script src="scripts/components-pickers.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {       
   // initiate layout and plugins
   	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	QuickSidebar.init(); // init quick sidebar
	Demo.init(); // init demo features
   	FormWizard.init();
   	ComponentsDropdowns.init();	//drop down
   	ComponentsPickers.init();	//date
   	ComponentsFormTools.init(); //spinnder
   	var x1='<%=days%>';
	var x2='<%=duration%>'.split(":");
	var x3=(x1*(parseInt(x2[0]*60)+parseInt(x2[1])));
	x3=x3/60;
	$("#totalhr").val(x3);
   	var bp=parseFloat('<%=bprice%>');
	var dis=parseFloat('<%=discount%>');
	var tax=parseFloat(document.batch_form.tax.value);
	var disprice=((bp*dis)/100);
	$('#bpp').val(bp);
	var dp=bp+disprice;
	$('#touchspin_demo1').val(dp);
	$('#touchspin_demo2').val(dis);
	var taxprice=((bp*tax)/100);
	
	$('#taxinr').val(taxprice.toFixed(2));
	$('#totalinr').val((bp+taxprice).toFixed(2));
	$('#taxinr').val(taxprice.toFixed(2));
	var p1=0.956,p2=0.31;
	var p3=(1-(tax/100));
	var usd=((((bp/p3)/61)-p2)/p1);
	$('#totalusd').val(usd.toFixed(2));
   	var showNotification=function ()
	{
		
	    $.get("Notification",
	    {
	      jEventName: "ViewLiveDemo_Notification"
	    },
	    function(data,status){
	    	//alert(data);
	    	//alert(status);
	    	//document.getElementById("noti").value=data;
	    	$('#showing_notification').html(data);
	    });
	};
	var show=function ()
	{
		
	    $.get("Notification",
	    {
	      jEventName: "LiveDemo_Notification"
	    },
	    function(data,status){
	    	$('#noti').html(data);
	    	showNotification();
	    });
	};
	var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!

  var yyyy = today.getFullYear();
  if(dd<10){
      dd='0'+dd
  } 
  if(mm<10){
      mm='0'+mm
  } 
  var today = yyyy+'-'+mm+'-'+dd;
  
	
	var showTaskNotification=function (today)
	{
		
	    $.get("Notification",
	    {
	    	date:today,
	      jEventName: "ViewTask_Notification"
	    },
	    function(data,status){
	    	//alert(data);
	    	//alert(status);
	    	//document.getElementById("noti").value=data;
	    	$('#task_notification').html(data);
	    });
	};
	var taskNotification=function (today)
	{
		
	    $.get("Notification",
	    {
	    	date:today,
	      jEventName: "Task_Notification"
	    },
	    function(data,status){
	    	$('#task_noti').html(data);
	    	$('#taskid').html(data);
	    	showTaskNotification(today);
	    });
	};
	
	if('<%=nature%>' =="admin head")
	{
	var showPaymentNotification=function ()
	{
		
	    $.get("PaymentServlet",
	    {
	      jEventName: "ViewPayment_Notification"
	    },
	    function(data,status){
	    	//alert(data);
	    	//alert(status);
	    	//document.getElementById("noti").value=data;
	    	$('#p_notification').html(data);
	    });
	};
	var showPayment=function ()
	{
		
	    $.get("PaymentServlet",
	    {
	      jEventName: "View_Notification"
	    },
	    function(data,status){
	    	$('#p_noti').html(data);
	    	$('#paymentnumber').html(data);
	    	showPaymentNotification();
	    });
	};
	
	showPayment();
	
	setInterval(showPayment, 60000);
	}
	show();
	taskNotification(today);
	setInterval(show, 50000);
	
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>