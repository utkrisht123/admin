<%@page import="com.ConcurrentUserTracker"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>GyanSha Infotech | Admin</title>
<link rel="shortcut icon" href="img/logo2.png"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="robots" content="noindex">
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
<link href="plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
<link href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
<link href="plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
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
String user=(String)session.getAttribute("user");
String nature=(String)session.getAttribute("nature");
if(session.getAttribute("user")==null)
{
	String jMessage2 = "Your Session has expired";
	  request.setAttribute("jMessage", jMessage2);
	  request.setAttribute("jEventName", "AdminLogin");
	  request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
}

%>
<!-- BEGIN HEADER -->

<jsp:include page="header.jsp" ></jsp:include>

<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	
	<jsp:include page="sidebar.jsp" ></jsp:include>
	
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
			Gyansha Admin <small>dashboard & statistics </small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="AdminMain.jsp">Home</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">Dashboard</a>
					</li>
				</ul>
				<div class="page-toolbar">
					<div id="dashboard-report-range" class="pull-right tooltips btn btn-fit-height grey-salt" data-placement="top" data-original-title="Change dashboard date range">
						<i class="icon-calendar"></i>&nbsp; 
						<span class="thin uppercase visible-lg-inline-block"></span>&nbsp; 
						<i class="fa fa-angle-down"></i>
					</div>
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<div class="row">
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat yellow-lemon">
						<div class="visual">
							<i class="fa fa-comments"></i>
						</div>
						<div class="details">
							<div id="hadoop_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="hadoop">
								
							</div>
							<div class="desc">
								 Hadoop Request
							</div>
						</div>
						<a id="hadoopurl"class="more" href="livedemo.do?jEventName=V_L_Course&course=Hadoop">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red-intense">
						<div class="visual">
							<i class="fa fa-bar-chart-o"></i>
						</div>
						<div class="details">
							<div id="python_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="python">
								
							</div>
							<div class="desc">
								 Python Request
							</div>
						</div>
						<a id="pythonurl"class="more" href="livedemo.do?jEventName=V_L_Course&course=Python">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat green-haze">
						<div class="visual">
							<i class="fa fa-shopping-cart"></i>
						</div>
						<div class="details">
							<div id="mongo_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="mongo" >
								  
							</div>
							<div class="desc">
								 MongoDB Request
							</div>
						</div>
						<a id="mongourl" class="more" href="livedemo.do?jEventName=V_L_Course&course=MongoDB">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue-madison">
						<div class="visual">
							<i class="fa fa-bullhorn"></i>
						</div>
						<div class="details">
							<div id="r_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="r" >
								  
							</div>
							<div class="desc">
								 R Analytics Request
							</div>
						</div>
						<a id="rurl" class="more" href="livedemo.do?jEventName=V_L_Course&course=Business Analytics With R">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue-madison">
						<div class="visual">
							<i class="fa fa-bullhorn"></i>
						</div>
						<div class="details">
							<div id="scala_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="scala" >
								  
							</div>
							<div class="desc">
								 Spark & Scala Request
							</div>
						</div>
						<a id="scalaurl" class="more" href="livedemo.do?jEventName=V_L_Course&course=Apache Spark and Scala">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat purple-plum">
						<div class="visual">
							<i class="fa fa-globe"></i>
						</div>
						<div class="details">
							<div id="total_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="total">
								
							</div>
							<div class="desc">
								 Total Request
							</div>
						</div>
						<a id="allurl" class="more" href="livedemo.do?jEventName=LiveDemo">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				
			</div>
			
				<div class="row">
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="hadoopstat">
						<div class="visual">
							<i class="fa fa-comments"></i>
						</div>
						<div class="details">
							<div id="hadoopnew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="hadoopnew">
								
							</div>
							<div class="desc">
								 Hadoop New Request
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=Hadoop">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="pythonstat">
						<div class="visual">
							<i class="fa fa-bar-chart-o"></i>
						</div>
						<div class="details">
							<div id="pythonnew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="pythonnew">
								
							</div>
							<div class="desc">
								 Python New Request
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=Python">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="mongostat">
						<div class="visual">
							<i class="fa fa-shopping-cart"></i>
						</div>
						<div class="details">
							<div id="mongonew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="mongonew" >
								  
							</div>
							<div class="desc">
								 MongoDB New Request
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=MongoDB">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="rstat">
						<div class="visual">
							<i class="fa fa-bullhorn"></i>
						</div>
						<div class="details">
							<div id="rnew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="rnew" >
								  
							</div>
							<div class="desc">
								 R Analytics New Request 
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=Business Analytics With R">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="scalastat">
						<div class="visual">
							<i class="fa fa-bullhorn"></i>
						</div>
						<div class="details">
							<div id="scalanew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="scalanew" >
								  
							</div>
							<div class="desc">
								Spark & Scala New Request
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=Apache Spark and Scala">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
					<div class="dashboard-stat red" id="totalstat">
						<div class="visual">
							<i class="fa fa-globe"></i>
						</div>
						<div class="details">
							<div id="totalnew_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
							<div class="number" id="totalnew">
								
							</div>
							<div class="desc">
								 Total New Request
							</div>
						</div>
						<a class="more" href="livedemo.do?jEventName=N_L_DEMO&course=All">
						View more <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				
			</div>
			
			<!-- END DASHBOARD STATS -->
			
				<div class="tiles">
				<div class="tile double-down bg-blue-hoki" id="livedemo" onclick="tileActions();">
					<div class="tile-body">
						<i class="fa fa-bell-o"></i>
					</div>
					<div class="tile-object" >
						<div class="name">
							 Live Demo
						</div>
						<div class="number" id="livedemonumber">
							 
						</div>
					</div>
				</div>
				<div class="tile bg-red-sunglo" id="callback">
					<div class="tile-body">
						<i class="fa fa-phone"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Call Back
						</div>
						<div class="number">
							 
						</div>
					</div>
				</div>
				<div class="tile double selected bg-green-turquoise" id="contact">
					<div class="corner">
					</div>
					<div class="check">
					</div>
					<div class="tile-body">
						<h4>Contact Request</h4>
						<p>
							 Get all contact request.
						</p>
						
					</div>
					<div class="tile-object">
						<div class="name">
							<i class="fa fa-envelope"></i>
						</div>
						<div class="number">
							
						</div>
					</div>
				</div>
				<div class="tile selected bg-purple" id="chatquery">
					<div class="corner">
					</div>
					<div class="tile-body">
						<i class="fa fa-wechat"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Chat Queries
						</div>
						
					</div>
				</div>
				<div class="tile double bg-blue-madison" id="bulkemail">
					<div class="tile-body">
						<img src="media/profile/photo1.jpg" alt="">
						<h4>Live Demo Query</h4>
						<p>
							 Admin create live demo query of students.
						</p>
					</div>
					<div class="tile-object">
						<div class="name">
							<i class="fa fa-envelope-square"></i>
						</div>
						<div class="number" id="emaildate">
							 
						</div>
					</div>
				</div>
				<div class="tile bg-green" id="payment">
					<div class="tile-body">
						<i class="fa fa-money"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Payment
						</div>
						<div class="number" id="paymentnumber">
						</div>
					</div>
				</div>
				<div class="tile bg-purple-studio" id="newtask">
					<div class="tile-body">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Task
						</div>
						<div class="number" id="newtasks">
							 
						</div>
					</div>
				</div>
				
				<div class="tile bg-green-meadow" id="webinar" >
					<div class="tile-body">
						<i class="fa fa-comments"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Webinar
						</div>
						<div class="number">
							 
						</div>
					</div>
				</div>
				
				
				<div class="tile double bg-grey-cascade" id="demobach">
					<div class="tile-body">
						<img src="media/profile/photo2.jpg" alt="" class="pull-right">
						<h3>Demo Batch</h3>
						<p>
							 Create new demo batch. I look forward to check the next release!
						</p>
					</div>
					<div class="tile-object">
						<div class="name">
							<i class="fa fa-group"></i>
						</div>
						<div class="number" id="demobatchdate">
							 
						</div>
					</div>
				</div>
				<div class="tile bg-blue-steel" id="batch">
					<div class="tile-body">
						<i class="fa fa-briefcase"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Batch
						</div>
						<div class="number">
							 
						</div>
					</div>
				</div>
				<div class="tile selected bg-yellow-saffron" id="student">
					<div class="corner">
					</div>
					<div class="tile-body">
						<i class="fa fa-user"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Student
						</div>
						<div class="number">
							 
						</div>
					</div>
				</div>
				<div class="tile bg-red-intense" id="teacher">
					<div class="tile-body">
						<i class="fa fa-user-md"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Teacher
						</div>
						<div class="number">
							 
						</div>
					</div>
				</div>
				
				
				
				<div class="tile double selected bg-green-turquoise" id="timeconversion">
					<div class="corner">
					</div>
					<div class="check">
					</div>
					<div class="tile-body">
						<h4>Time Conversion</h4>
						
					</div>
					<div class="tile-object">
						<div class="name">
							<i class="fa fa-clock-o"></i>
						</div>
						<div class="number" id="timeconv">
							
						</div>
					</div>
				</div>
				<div class="tile bg-yellow-lemon selected" id="instantphone">
					<div class="corner">
					</div>
					<div class="check">
					</div>
					<div class="tile-body">
						<i class="fa fa-mobile-phone"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Instant Phone Number
						</div>
					</div>
				</div>
				<div class="tile bg-red-sunglo" id="instantemail">
					<div class="tile-body">
						<i class="fa fa-plane"></i>
					</div>
					<div class="tile-object">
						<div class="name">
							 Instant Email Id
						</div>
						<div class="number">
							
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix">
			</div>
			<!-- BEGIN DASHBOARD STATS -->
			
			
		
		
		
			<div class="row ">
				<div class="col-md-6 col-sm-6">
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-bell-o"></i>Recent Activities
							</div>
							<div class="actions">
								<div class="btn-group">
									<a class="btn btn-sm btn-default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
									Filter By <i class="fa fa-angle-down"></i>
									</a>
									<div class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
										<label><input type="checkbox"/> Finance</label>
										<label><input type="checkbox" checked=""/> Membership</label>
										<label><input type="checkbox"/> Customer Support</label>
										<label><input type="checkbox" checked=""/> HR</label>
										<label><input type="checkbox"/> System</label>
									</div>
								</div>
							</div>
						</div>
						<div class="portlet-body" >
							<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible="0">
								<ul class="feeds" id="activity">
									
								</ul>
								<div id="activity_loading">
									<img src="img/loading.gif" alt="loading"/>
									</div>
							</div>
							<div class="scroller-footer">
								<div class="btn-arrow-link pull-right">
									<a href="#">See All Records</a>
									<i class="icon-arrow-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="portlet box green-haze tasks-widget">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-check"></i>Tasks
							</div>
							<div class="tools">
								<a href="#portlet-config" data-toggle="modal" class="config">
								</a>
								<a href="" class="reload">
								</a>
							</div>
							<div class="actions">
								<div class="btn-group">
									<a class="btn btn-default btn-sm" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
									More <i class="fa fa-angle-down"></i>
									</a>
									<ul class="dropdown-menu pull-right">
										<li>
											<a href="#">
											<i class="i"></i> All Project </a>
										</li>
										<li class="divider">
										</li>
										<li>
											<a href="#">
											Pending <span class="badge badge-danger">
											4 </span>
											</a>
										</li>
										<li>
											<a href="#">
											Completed <span class="badge badge-success">
											12 </span>
											</a>
										</li>
										<li>
											<a href="#">
											Overdue <span class="badge badge-warning">
											9 </span>
											</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="portlet-body">
									
								<div class="task-content" >
								<div class='scroller' style='height: 305px;' data-always-visible='1' data-rail-visible1='1'>
								<ul class='task-list' id="showing_task">
								
								</ul>
								<div id="task_loading">
									<img src="img/loading.gif" alt="loading"/>
									</div>
								</div>
									
								</div>
							<div class="task-footer">
								<div class="btn-arrow-link pull-right">
									<a href="MyTask.jsp">See All Records</a>
									<i class="icon-arrow-right"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix">
			</div>
			
			
			<div class="row ">
				<div class="col-md-12 col-sm-12">
					<!-- BEGIN PORTLET-->
					<div class="portlet box blue-madison calendar">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-calendar"></i>Calendar
							</div>
						</div>
						<div class="portlet-body light-grey">
							
							<div id="calendar">
							</div>
							<div id="calender_loading">
								<img src="img/loading.gif" alt="loading"/>
							</div>
						</div>
					</div>
					<!-- END PORTLET-->
				</div>
				
				
				
			</div>
			
		</div>
	</div>
	<!-- END CONTENT -->

</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->

<jsp:include page="footer.jsp" ></jsp:include>

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
<script src="plugins/jqvmap/jqvmap/jquery.vmap.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>
<script src="plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js" type="text/javascript"></script>
<script src="plugins/flot/jquery.flot.min.js" type="text/javascript"></script>
<script src="plugins/flot/jquery.flot.resize.min.js" type="text/javascript"></script>
<script src="plugins/flot/jquery.flot.categories.min.js" type="text/javascript"></script>
<script src="plugins/jquery.pulsate.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>
<!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
<script src="plugins/fullcalendar/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
<script src="plugins/jquery-easypiechart/jquery.easypiechart.min.js" type="text/javascript"></script>
<script src="plugins/jquery.sparkline.min.js" type="text/javascript"></script>
<script src="plugins/gritter/js/jquery.gritter.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="scripts/metronic.js" type="text/javascript"></script>
<script src="scripts/layout.js" type="text/javascript"></script>
<script src="scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="scripts/demo.js" type="text/javascript"></script>
<script src="scripts/index.js" type="text/javascript"></script>
<script src="scripts/tasks.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN NEW CHART THEMES -->
		<script src="amcharts/amcharts.js" type="text/javascript"></script>
		<script type="text/javascript" src="amcharts/pie.js"></script>
		<script src="amcharts/serial.js" type="text/javascript"></script>
		<script src="amcharts/amstock.js" type="text/javascript"></script>
		<script type="text/javascript" src="amcharts/ammap.js"></script>
		<script type="text/javascript" src="amcharts/worldlow.js"></script>
		<script type="text/javascript" src="amcharts/light.js"></script>
		
<!-- END NEW CHART THEMES -->
<script>
jQuery(document).ready(function() { 
	 
	
   Metronic.init(); // init metronic core componets
   Layout.init(); // init layout
   QuickSidebar.init(); // init quick sidebar
   Demo.init(); // init demo features 
   Index.init(); 
   var latestActivity=function ()
	{
		$('#activity_loading').show();
	    $.get("ServletAction",
	    {
	      jEventName: "Latest_Activity"
	    },
	    function(data,status){
	    	 $('#activity_loading').hide();
            $('#activity').show();
	    	$('#activity').html(data);
	    });
	};
	latestActivity();
   var showTask=function ()
	{
		 $('#task_loading').show();
		 $.get("ServletAction",
	        	    {
	             	      jEventName: "Main_Task"
	        	    },
	        	    function(data,status){
	        	      //alert("Data: " + data + "\nStatus: " + status);
	        	    	//alert(data);
	        	    	
	        	    	 $('#task_loading').hide();
	                     $('#showing_task').show();
	        	    	$('#showing_task').html(data);
	        	    });
	};
	showTask(); 
   Index.initCalendar(); 
   Index.initDashboardDaterange();
   Tasks.initDashboardWidget();
  // Index.initJQVMAP(); // init index page's custom scripts
	// init index page's custom scripts
   //Index.initCharts(); // init index page's custom scripts
   //Index.initChat();
   //Index.initMiniCharts();
   //Index.initIntro();
  
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
	    	$('#livedemonumber').html(data);
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
    var today1 = dd+'/'+mm+'/'+yyyy;
    $('#emaildate').html(today1);
    $('#demobatchdate').html(today1);
	
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
	    	$('#tasks').html(data);
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
	    	$('#newtasks').html(data);
	    	showTaskNotification(today);
	    });
	};
	show();
	taskNotification(today);
	setInterval(show, 50000);
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
	
	
	  
});
</script>
<script>
$("#livedemo").click(function(){
	//alert($(this).attr('id'));
    location.replace("livedemo.do?jEventName=LiveDemo");
});
$("#chatquery").click(function(){

    location.replace("ChatQuery.jsp");
});
$("#callback").click(function(){

    location.replace("View_CallBack.jsp");
});
$("#contact").click(function(){

    location.replace("V_Contact.jsp");
});
$("#student").click(function(){

    location.replace("View_Student.jsp");
});
$("#bulkemail").click(function(){

    location.replace("LiveDemoQuery.jsp");
});
$("#demobatch").click(function(){

    location.replace("C_D_Batch.jsp");
});
$("#batch").click(function(){

    location.replace("C_Batch.jsp");
});
$("#teacher").click(function(){

    location.replace("View_Teacher.jsp");
});
$("#newtask").click(function(){

    location.replace("MyTask.jsp");
});
$("#webinar").click(function(){

    location.replace("LoginServlet?jEventName=G_W_Account");
});
$("#timeconversion").click(function(){

    location.replace("Time_Conversion.jsp");
});
$("#instantphone").click(function(){

    location.replace("InstantPhone.jsp");
});
$("#instantemail").click(function(){

    location.replace("InstantEmail.jsp");
});
</script>

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>