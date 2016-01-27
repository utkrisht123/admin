<%@page import="com.ActionServlet"%>
<%@page import="com.LiveDemoDAO"%>
<%@page import="com.TeacherDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.awt.image.SampleModel"%>
<%@ page import="java.util.*" %>
<%@page import="java.util.Date"%>
<%@ page import="javax.mail.*" %>
<%@ page import="java.util.Map.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<%@ page import="javax.net.ssl.*" %>
      <%@ page import="java.util.*" %>
       <%@ page import="java.text.*" %>
<%@ page import="java.sql.*" %>
 <jsp:useBean id="c" class="com.MyConnection"></jsp:useBean>
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
   //FormEditable.init();
   //TableAdvanced.init();
});
</script>

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

<!-- BEGIN HEADER -->

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
				<li class="start active open">
					<a href="javascript:;">
					<i class="icon-briefcase"></i>
					<span class="title">Live Demo</span>
					<span class="selected"></span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						<li >
							<a href="livedemo.do?jEventName=LiveDemo">
							<i class="icon-home"></i>
							Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=Today_LiveDemo">
							<i class="icon-basket"></i>
							Today Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=All_LiveDemo">
							<i class="icon-tag"></i>
							All Live Demo Request</a>
						</li>
						<li>
							<a href="livedemo.do?jEventName=Admin_LiveDemo">
							<i class="icon-tag"></i>
							Admin Live Demo Request</a>
						</li>
						<li>
							<a href="LiveDemoQuery.jsp">
							<i class="icon-tag"></i>
							Live Query</a>
						</li>
						<li class="active">
							<a href="Send_Custom_Email.jsp">
							<i class="fa fa-envelope"></i>
							Custom Email</a>
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
						<li >
							<a href="TodayLiveDemoWebinar.jsp">
							<i class="icon-tag"></i>
							Reshedule Today Webinar Task</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="icon-briefcase"></i>
					<span class="title">Contacts</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="View_CallBack.jsp">
							Call Back Request</a>
						</li>
						
						
						
						<li>
							<a href="V_Contact.jsp">
							Contact Request</a>
						</li>
						
						
					</ul>
				</li>
				
				<li>
					<a href="javascript:;">
					<i class="icon-basket"></i>
					<span class="title">Demo Batch</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="C_D_Batch.jsp">
							<i class="icon-home"></i>
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
					<i class="icon-basket"></i>
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
							<i class="icon-basket"></i>
							View Webinar</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="icon-basket"></i>
					<span class="title">Batch</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="C_Batch.jsp">
							<i class="icon-home"></i>
							Create Batch</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=G_Batch">
							<i class="icon-basket"></i>
							View Batch</a>
						</li>
						<li>
							<a href="SC_Batch.jsp">
							<i class="icon-basket"></i>
							Schedule Batch</a>
						</li>
						<li>
							<a href="Send_Webinar.jsp">
							<i class="icon-basket"></i>
							Send Webinar Link</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="icon-basket"></i>
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
							<i class="icon-tag"></i>
							Allot Batch</a>
						</li>
						<li>
							<a href="View_Student.jsp">
							<i class="icon-basket"></i>
							View Student</a>
						</li>
						
						<li>
							<a href="Student_Profile.jsp">
							<i class="icon-tag"></i>
							Student Profiles</a>
						</li>
						
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="icon-basket"></i>
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
							<i class="icon-tag"></i>
							Allot Batch</a>
						</li>
						<li>
							<a href="View_Teacher.jsp">
							<i class="icon-basket"></i>
							View Teacher</a>
						</li>
						<li>
							<a href="Teacher_Profile.jsp">
							<i class="icon-tag"></i>
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
				<li class="heading">
					<h3 class="uppercase">More</h3>
				</li>
			
				<li class="last ">
					<a href=".jsp">
					<i class="icon-envelope-open"></i>
					<span class="title">Custom Email</span>
					</a>
				</li>
				<li class="last ">
					<a href="BulkEmail.jsp">
					<i class="icon-envelope-open"></i>
					<span class="title">Bulk Email</span>
					</a>
				</li>
				<li class="last">
					<a href="UploadTemplate.jsp">
					<i class="fa fa-upload"></i>
					<span class="title">Upload Template</span>
					</a>
				</li>
				<li class="last ">
					<a href="MyTask.jsp">
					<i class="fa fa-tasks"></i>
					<span class="title">Task</span>
					</a>
				</li>
				<li class="last">
					<a href="MyCalender.jsp">
					<i class="fa fa-calendar"></i>
					<span class="title">Calender</span>
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
			Live Demo <small>View</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="AdminMain.jsp">Home</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="livedemo.do?jEventName=LiveDemo">Live Demo Request</a>
						
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
			 <%
			 String jMessage="Link For Live Demo send successfully";
			 String name=null;
			  	String email=null;
			  	String last=null,batch_id=null;
    String meeting_id=null,url=null,subject=null,date=null,time=null,curd=null,curt=null,timing=null,course=null,country=null,subject_id=null,countryCode=null,q_id=null,id=null,interest_level="0";
	String phone=null,query=null;
	String d=null,t=null;
	String student_id=null;
	String price="",sd="",stime="",wsd="",wstime="",wprice="",wdprice="";
	int sdd=0,wsdd=0;
	boolean flag=false;
	String username=(String)session.getAttribute("user");
    try
    {
		
		Connection con=c.getConnection();
   		Statement st=con.createStatement();
   		interest_level=request.getParameter("interest_level");
   		if(interest_level==null)
   			interest_level="0";
   		time=request.getParameter("time");
		name=request.getParameter("name");
 		phone=request.getParameter("phone");
		email=request.getParameter("email");
   	 	student_id=request.getParameter("student_id");
   	 	id=request.getParameter("id");
   	 	batch_id=request.getParameter("batch_id");
   		course=request.getParameter("course");
   	 	countryCode=request.getParameter("country");
   	 	q_id=request.getParameter("q_id");
	   	
   	 	if(q_id!=null)
   	 	{
   	 		new LiveDemoDAO().updateTask(q_id);
   	 	}
   	  	Map<String, String> countries = new HashMap<String, String>();
	    for (String iso : Locale.getISOCountries()) {
	        Locale l = new Locale("", iso);
	        countries.put(iso,l.getDisplayCountry());
	    }
	    country=countries.get(countryCode);
   	 	query="Request For Live Demo send by Admin";
   	 
   	 		String ld_date=request.getParameter("date");
   	 		
   	 		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		  	java.util.Date  dd = df.parse(ld_date);
		  	java.sql.Date d2=new java.sql.Date(dd.getTime());
		  	
			PreparedStatement ps=con.prepareStatement("select SUBJECT_ID from subjects  where SUBJECT_NAME=?");
     		ps.setString(1, course);
     		ResultSet rs2=ps.executeQuery();
     		while(rs2.next())
     			subject_id=rs2.getString(1);
   	 		PreparedStatement ps1=con.prepareStatement("select DEMO_BATCH_ID from lms_batch_demo where START_DATE=? and SUBJECT_ID=?");
     		ps1.setDate(1, d2);
     		ps1.setString(2, subject_id);
     		ResultSet rs=ps1.executeQuery();
     		while(rs.next())
     			batch_id=rs.getString(1);
     		
     		if(batch_id==null)
     		{
     			
     			jMessage="Provided date is not match any demo batch please enter another date";
     		}
     		else
     		{
     			
     			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    			format2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    			java.util.Date  date2 = format2.parse(ld_date+" "+time);
    			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String ttime="",tdate="";
    				 df2.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
    				 ttime= df2.format(date2);
    				 DateFormat df3 = new SimpleDateFormat("dd-MMM-yyyy");
    				 tdate=df3.format(date2);
    				
    				 int qId=0;
    	     			if(id!=null)
    	     			{	try
    	     				{
    	     					qId=Integer.parseInt(id);
    	     				}catch(NumberFormatException e)
    	     				{
    	     					qId=0;
    	     				}
    	     			}
    	     			if(qId==0)
    	     			{
    	     				PreparedStatement ps4=con.prepareStatement("insert into lms_live_demo(NAME,PHONE_NO,EMAIL,QUERY,COURSE,COUNTRY,E_CORRECT,M_CORRECT,QRY_DATE,COUNTRY_CODE) values(?,?,?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
    	     				ps4.setString(1, name);
    	     				ps4.setString(2, phone);
    	     				ps4.setString(3, email);
    	     				ps4.setString(4, query);
    	     				ps4.setString(5, course);
    	     				ps4.setString(6, country);
    	         			ps4.setString(7, "y");
    	         			ps4.setString(8, "y");
    	         			ps4.setString(9, ttime);
    	         			ps4.setString(10, countryCode);
    	     				int n1=ps4.executeUpdate();
    	     				ResultSet rs1=ps4.getGeneratedKeys();
    	     				while(rs1.next())
    	     				{
    	     					qId=rs1.getInt(1);
    	     				}
    	     			}
    	     			new ActionServlet().setAdminResponse(qId, username, email, "Pickup", "Custom Email sent for "+tdate, interest_level, "");
   	 			int n=0;
   	 			if(student_id==null||student_id.equals("")||student_id.equals(" ")||student_id.equals("null"))
   	 			{
   	 	 			PreparedStatement ps2=con.prepareStatement("insert into lms_live_demo(NAME,PHONE_NO,EMAIL,QUERY,COURSE,COUNTRY,E_CORRECT,M_CORRECT,QRY_DATE,COUNTRY_CODE,DEMO_ID) values(?,?,?,?,?,?,?,?,?,?,?)");
         			ps2.setString(1, name);
         			ps2.setString(2, phone);
         			ps2.setString(3, email);
         			ps2.setString(4, query);
         			ps2.setString(5, course);
         			ps2.setString(6, country);
         			ps2.setString(7, "y");
         			ps2.setString(8, "y");
         			ps2.setString(9, ttime);
         			ps2.setString(10, countryCode);
         			ps2.setString(11,batch_id);
     				n=ps2.executeUpdate();   	
   	 			}
   	 			else
   	 			{
   	 	 			PreparedStatement ps3=con.prepareStatement("insert into lms_live_demo(NAME,PHONE_NO,EMAIL,QUERY,STUDENT_ID,COURSE,COUNTRY,E_CORRECT,M_CORRECT,QRY_DATE,COUNTRY_CODE,DEMO_ID) values(?,?,?,?,?,?,?,?,?,?,?,?)");
         			ps3.setString(1, name);
         			ps3.setString(2, phone);
         			ps3.setString(3, email);
         			ps3.setString(4, query);
         			ps3.setString(5, student_id);
         			ps3.setString(6, course);
         			ps3.setString(7, country);
         			ps3.setString(8, "y");
         			ps3.setString(9, "y");
         			ps3.setString(10, ttime);
         			ps3.setString(11, countryCode);
         			ps3.setString(11,batch_id);
     			 n=ps3.executeUpdate();   	
   	 			}
   	 			if(n>0)
   	 			{
   	 			String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
				PreparedStatement ps7 = con.prepareStatement(sql2);
				ps7.setString(1, username);
				ps7.setString(2, "SLDE");
				ps7.executeUpdate();	
   	 			}
		    	//Default Date format according to IST
				DateFormat tf = new SimpleDateFormat("hh:mm a");
		

			//Date format for different time
			DateFormat df1 = new SimpleDateFormat("EEE, MMM dd, yyyy");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			ResultSet rs3=st.executeQuery("SELECT S.SUBJECT_NAME,D.START_TIME,D.MEETING_ID,D.URL,D.START_DATE FROM (subjects as S JOIN lms_batch_demo as D ON S.SUBJECT_ID=D.SUBJECT_ID) where DEMO_BATCH_ID='"+batch_id+"'");
		   		while (rs3.next())
	   		{
	   			subject=rs3.getString(1);
	   			timing=tf.format(rs3.getTime(2));
	   			meeting_id=rs3.getString(3);
	   			url=rs3.getString(4);
	   			date=df.format(rs3.getDate(5));
	   		}
	   		
	   		url=url.trim();
	   		
	   		//String host="mail.gyansha.com";  
	   	    final String user="support@easylearning.guru";
	 		final String password="Facebook.com1";    
	   	      
	   	    String to=email;
	   	    String emailsub="";
	        String contmess="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head> <title></title> <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style><!--[if gte mso 9]> <style>.column-top{mso-line-height-rule: exactly !important;}</style><![endif]--> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content='"+course+" Live Demo | Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>"+course+" Live Demo</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' id='emb-email-header' align='center'><a style='text-decoration: none;color: #555' href='http://easylearning.guru/?utm_source=LiveDemoEmail&utm_campaign=LiveDemoEmail'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' title='Easylearning.guru' height='72' width='278'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for registering for the<strong style='font-weight: bold'> "+course+" Live Demo.</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Your Live Demo has been scheduled on <strong style='font-weight: bold'>"+date+" "+timing+" IST</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>You will receive another email containing the link to attend the Live Demo in a few minutes. We suggest you click on the link <strong>15 minutes</strong> before to attend the Demo for your convenience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br><strong style='font-weight: bold'>Easylearning.Guru<br>Making Learning Easy for You</strong></p><p style='Margin-top: 0;color: #000;font-size: 11px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px;text-align: center'>For further queries you can mail us on contact@easylearning.guru or Call us on +124-4763660</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center> <img style='border: 0 !important;-ms-interpolation-mode: bicubic;visibility: hidden !important;display: block !important;height: 1px !important;width: 1px !important;margin: 0 !important;padding: 0 !important' src='Livedemo_files/o.gif' alt='' border='0' height='1' width='1'></body></html>";

	   	 if(course.equalsIgnoreCase("Hadoop"))
	 	{
	   		 emailsub="Big Data & Hadoop Live Demo | Easylearning.Guru";
	 	}
	 	else if(course.equalsIgnoreCase("Python"))
	 	{
	 		emailsub="Python with Django Live Demo | Easylearning.Guru";
	 	}
	     
	 	else if(course.equalsIgnoreCase("MongoDB"))
	 	{
	 		emailsub="MongoDB Live Demo | Easylearning.Guru";
	 	}
	 	else if(course.equalsIgnoreCase("Business Analytics With R"))
	 	{
	 		emailsub="Business Analytics With R Live Demo | Easylearning.Guru";
	 	}
	   	  
	   	     //Get the session object  
	   	     Properties props = new Properties();  
     	props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
     Session sess = Session.getInstance(props,
      new javax.mail.Authenticator() {  
        protected PasswordAuthentication getPasswordAuthentication() {  
      return new PasswordAuthentication(user,password);  
        }  
      });  

     //Compose the message  
       MimeMessage message = new MimeMessage(sess);  
       message.setFrom(new InternetAddress(user));  
       message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));   
	   	       message.setSubject(emailsub);  
	   	       message.setContent(contmess,"text/html"); 
	   	    	message.setSentDate(new java.util.Date()); 
	   	      //send the message  
	   	       Transport.send(message);  
	   	      
       			String lid[]=url.split("/");
  				last=lid[lid.length-1];
	   				flag=true;
     		}
       		con.close();
    		}
    		catch(Exception e)
    		{
    			System.out.println(e);
    			
    			
    			
    			}  		%>
    			
    			<script>
    			var x="https://attendee.gotowebinar.com/registration.tmpl?registrant.source= &registrant.timeZone=Asia/Calcutta&webinar=<%=last%>&registrant.givenName=<%=name%>&registrant.surname= &registrant.email=<%=email%>&registration.submit.button=Register";
     			run();
     		function run()
     		{
     		if (window.XMLHttpRequest)
     		  {// code for IE7+, Firefox, Chrome, Opera, Safari
     		  xmlhttp=new XMLHttpRequest();
     		  }
     		else
     		  {// code for IE6, IE5
     		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
     		  }
     		xmlhttp.onreadystatechange=function()
     		  {
     		  if(xmlhttp.readyState==4)
     		    {
     			 
     			 	
     		    }
     		
     		  }
     		xmlhttp.open("POST",x,true);
     		xmlhttp.send();
     		}
       		</script>
			<h3 class="page-title">
			Email Confimation Page
			</h3>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			
			
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN VALIDATION STATES-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i>Confirm Email
							</div>
							<div class="tools">
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
							<!-- BEGIN FORM-->
							<form action="SendDemoEmail.jsp" method="post" id="form_sample_3" class="form-horizontal">
								<div class="form-body">
									
							
									
											<div class="alert alert-success">
												<button class="close" data-close="alert"></button>
												<strong><%=jMessage %></strong> 
											</div>
										
									
									
									<div class="form-group">
										<label class="control-label col-md-2">Course </label>
										<label class="control-label col-md-2"><%=course %></label>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">Student Name </label>
										<label class="control-label col-md-2"><%=name %></label>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">Email</label>
										<label class="control-label col-md-2"><%=email %></label>
									</div>
									
									
									<div class="form-group">
										<label class="control-label col-md-2">Live Demo Date </label>
										<label class="control-label col-md-2"><%=date %></label>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">Live Demo Time </label>
										<label class="control-label col-md-2"><%=timing %></label>
									</div>
								</div>
								
								
							</form>
							<!-- END FORM-->
						</div>
						<!-- END VALIDATION STATES-->
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
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->


<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>