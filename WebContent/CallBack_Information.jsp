<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
     <jsp:useBean id="c" class="com.MyConnection"></jsp:useBean>
    <%@ page import="java.sql.*" %>
    <%@ page import="java.util.*" %>
 	<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<title>GyanSha Infotech | Admin</title>
<link rel="shortcut icon" href="img/logo2.png"/>
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
<link rel="stylesheet" type="text/css" href="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>

<link href="plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
<link href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
<link href="plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
<!-- Date Picker -->
<link rel="stylesheet" type="text/css" href="plugins/clockface/css/clockface.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-datepicker/css/datepicker3.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-colorpicker/css/colorpicker.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"/>
<link rel="stylesheet" type="text/css" href="plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>
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
</head>
<%

String jEvent = (String)request.getAttribute("jEventName");
String jMessage =  null;
ArrayList<String> al = new ArrayList<String>();
String email="";
if(!(jEvent != null))	
	jEvent = "Intial";
else if(jEvent != null && jEvent.equalsIgnoreCase("CallBack_Info")){
	al = (ArrayList<String>)request.getAttribute("callback");
	email=(String)request.getAttribute("email");
}
//System.out.println("jEvent: "+jEvent);
%>

<body>
	<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box purple">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>Call Back Information
							</div>
							<div class="actions">
								<a href="#" class="btn btn-default btn-sm">
								<i class="fa fa-plus"></i> Add </a>
								<a href="#" class="btn btn-default btn-sm">
								<i class="fa fa-print"></i> Print </a>
							</div>
						</div>
						<div class="portlet-body">

<table class="table table-striped table-bordered table-hover" id="sample_3">
							<thead>
							<tr>
<th>S.No.</th>
<th>Name</th>
<th>Email</th>
<th>Phone</th>
<th>Query</th>
<th>Best Time To Call</th>
<th>Call Back</th>
<th>Date</th>

</tr>
</thead>
<tbody>
<%
for(int i=0;i<al.size();i++)
{
	
	String s=al.get(i);
	String s2[]=s.split("abczxy");
%>
<tr class="odd gradeX">
<td><%=i+1 %></td>
<td><%=s2[1] %></td>
<td><%=s2[2] %></td>
<td><%=s2[3] %></td>
<td><%=s2[4] %></td>
<td><%=s2[5] %></td>

<%if(s2[7].equals("n"))
{
%>
<td><a href="#">Not Call Backed</a></td>
<% 
}
else
{
%>
<td><a href="#">Call Backed</a></td>
<%	
}
	%>
	<td><%=s2[6] %></td>
</tr>
<%
}
%>
</tbody>
</table>

</div>
</div>
</div>
</div>
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
<script type="text/javascript" src="plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>

<!-- date picker -->
<script type="text/javascript" src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="plugins/clockface/js/clockface.js"></script>
<script type="text/javascript" src="plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="scripts/metronic.js" type="text/javascript"></script>
<script src="scripts/layout.js" type="text/javascript"></script>
<script src="scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="scripts/demo.js" type="text/javascript"></script>
<script src="scripts/table-managed.js"></script>
<script src="scripts/components-pickers.js"></script>
<script>
jQuery(document).ready(function() { 
	
   Metronic.init(); // init metronic core components
Layout.init(); // init current layout
QuickSidebar.init(); // init quick sidebar
Demo.init(); // init demo features
   //TableEditable.init();
   ComponentsPickers.init();
   TableManaged.init();
});
</script>
</body>
</html>