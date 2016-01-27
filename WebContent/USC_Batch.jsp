<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.sql.*" %>
    <%@ page import="java.util.*" %>
 	<%@ page import="java.text.*" %>
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
<!-- BEGIN CONTAINER -->


<!-- END HEADER -->

<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	
	
	
	<!-- END SIDEBAR -->
	<!-- BEGIN SIDEBAR -->
	<%
	String id="",email="",course="",date="",module="",meetingId="",url="",batch="";
	course=(String)request.getAttribute("subject");
	batch=(String)request.getAttribute("batch");
	id=(String)request.getAttribute("sessionID");
	module=(String)request.getAttribute("module");
	date=(String)request.getAttribute("sessionDate");
	email=(String)request.getAttribute("email");
	meetingId=(String)request.getAttribute("meeting_id");
	url=(String)request.getAttribute("url");
	String jEvent = (String)request.getAttribute("jEventName");
	if(!(jEvent != null))	
		jEvent = "Intial";
	%>
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
		<h3 class="page-title">
			Update Schedule Batch
			</h3>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			
			<div class="row">
				<div class="col-md-12">
						<!-- BEGIN VALIDATION STATES-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i>Reshedule Schedule Batch
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
							<form action="LoginServlet" method="post" id="form_sample_3" class="form-horizontal">
								<div class="form-body">
									
									<div class="alert alert-danger display-hide">
										<button class="close" data-close="alert"></button>
										You have some form errors. Please check below.
									</div>
									<div class="alert alert-success display-hide">
										<button class="close" data-close="alert"></button>
										Your form validation is successful!
									</div>
									<% 
									if(jEvent != null && jEvent.equalsIgnoreCase("Update_ScheduleBatch")){
	
										%>
											<div class="alert alert-success">
												<strong><%=request.getAttribute("jMessage") %></strong> 
											</div>
										<%
										}
									%>
									
									<div class="form-group">
										<label class="control-label col-md-3">Select Course <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<select class="form-control input-big input-inline" id="subject_id" name="subject_id" >
													<option value="" >Select Course</option>
													<%
														ArrayList<String> aCourse=c2.getCourse();
														for(int i=0;i<aCourse.size();i++)
															{
																String s=aCourse.get(i);
																String s2[]=s.split("/");
																String msg="";
																if(course!=null && course.equals(s2[0]))
																	msg="selected";
																
														%>
														<option value="<%=s2[0] %>" <%=msg %>><%=s2[1] %></option>
															<%} %>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Batch <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<div class="input-group">
												<select class="form-control input-small select2_category" name="batch" id="batch" >
												<option value="" >Select Batch</option>
													<%
														ArrayList<String> batchID=c2.getBatch(course);
														for(int i=0;i<batchID.size();i++)
															{
																
																String msg="";
																if(batch!=null && batch.equals(batchID.get(i)))
																	msg="selected";
																
														%>
														<option value="<%=batchID.get(i) %>" <%=msg %>><%=batchID.get(i) %></option>
															<%} %>
															</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">Module <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<input type="text" name="module" id="module" data-required="1" class="form-control" value="<%=module %>"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">Date <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<input class="form-control form-control-inline input-small date-picker" type="text" name="date" id="date" value="<%=date %>" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">Email Address <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<div class="input-group">
												<select class="form-control input-small input-inline" id="email" name="email" >
													<option value="" >Select Email</option>
													<%
														ArrayList<String> emAl=c2.getWebinarEmail();
														for(int i=0;i<emAl.size();i++)
															{
															String msg="";
															if(email!=null && email.equals(emAl.get(i)))
																msg="selected";
														%>
														<option value="<%=emAl.get(i) %>" <%=msg %>><%=emAl.get(i) %></option>
															<%} %>
											</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">Meeting Id <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<input class="form-control form-control-inline input-small" type="text" name="meeting_id" id="meeting_id" value="<%=meetingId %>" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">URL <span class="required">
										* </span>
										</label>
										<div class="col-md-4">
											<input class="form-control form-control-inline input-big"  type="text" name="url" id="url" value="<%=url %>" />
										</div>
									</div>
									
								</div>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green">Update</button>
											<input type="hidden" name="jEventName" value="Update_ScheduleBatch"/>
											<input type="hidden" name="id" value="<%=id %>"/>
										</div>
									</div>
								</div>
								
							</form>
							<!-- END FORM-->
						</div>
						<!-- END VALIDATION STATES-->
					</div>
				
				
				</div>
				
			</div>
		</div>
	</div>
	<!-- BEGIN FOOTER -->

</div>


<!-- END FOOTER -->
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
<script type="text/javascript" src="plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
<script type="text/javascript" src="plugins/bootstrap-markdown/lib/markdown.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
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
<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="plugins/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="plugins/fancybox/source/jquery.fancybox.js?v=2.1.3"></script>
	

	<!-- Add Button helper (this is optional) -->
	
	<script type="text/javascript" src="plugins/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	
	<script type="text/javascript" src="plugins/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="plugins/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.5"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			/*
			 *  Simple image gallery. Uses default settings
			 */

			$('.fancybox').fancybox();

			/*
			 *  Different effects
			 */

			// Change title type, overlay closing speed
			$(".fancybox-effects-a").fancybox({
				helpers: {
					title : {
						type : 'outside'
					},
					overlay : {
						speedOut : 0
					}
				}
			});

			// Disable opening and closing animations, change title type
			$(".fancybox-effects-b").fancybox({
				openEffect  : 'none',
				closeEffect	: 'none',

				helpers : {
					title : {
						type : 'over'
					}
				}
			});

			// Set custom style, close if clicked, change title type and overlay color
			$(".fancybox-effects-c").fancybox({
				wrapCSS    : 'fancybox-custom',
				closeClick : true,

				openEffect : 'none',

				helpers : {
					title : {
						type : 'inside'
					},
					overlay : {
						css : {
							'background' : 'rgba(238,238,238,0.85)'
						}
					}
				}
			});

			// Remove padding, set opening and closing animations, close if clicked and disable overlay
			$(".fancybox-effects-d").fancybox({
				padding: 0,

				openEffect : 'elastic',
				openSpeed  : 150,

				closeEffect : 'elastic',
				closeSpeed  : 150,

				closeClick : true,

				helpers : {
					overlay : null
				}
			});

			/*
			 *  Button helper. Disable animations, hide close button, change title type and content
			 */

			$('.fancybox-buttons').fancybox({
				openEffect  : 'none',
				closeEffect : 'none',

				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,

				helpers : {
					title : {
						type : 'inside'
					},
					buttons	: {}
				},

				afterLoad : function() {
					this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
				}
			});


			/*
			 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
			 */

			$('.fancybox-thumbs').fancybox({
				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,
				arrows    : false,
				nextClick : true,

				helpers : {
					thumbs : {
						width  : 50,
						height : 50
					}
				}
			});

			/*
			 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
			*/
			$('.fancybox-media')
				.attr('rel', 'media-gallery')
				.fancybox({
					openEffect : 'none',
					closeEffect : 'none',
					prevEffect : 'none',
					nextEffect : 'none',

					arrows : false,
					helpers : {
						media : {},
						buttons : {}
					}
				});

			/*
			 *  Open manually
			 */

			$("#fancybox-manual-a").click(function() {
				$.fancybox.open('1_b.jpg');
			});

			$("#fancybox-manual-b").click(function() {
				$.fancybox.open({
					href : 'iframe.html',
					type : 'iframe',
					padding : 5
				});
			});

			$("#fancybox-manual-c").click(function() {
				$.fancybox.open([
					{
						href : '1_b.jpg',
						title : 'My title'
					}, {
						href : '2_b.jpg',
						title : '2nd title'
					}, {
						href : '3_b.jpg'
					}
				], {
					helpers : {
						thumbs : {
							width: 75,
							height: 50
						}
					}
				});
			});


		});
	</script>
<!-- BEGIN PAGE LEVEL STYLES -->
<script src="scripts/metronic.js" type="text/javascript"></script>
<script src="scripts/layout.js" type="text/javascript"></script>
<script src="scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="scripts/demo.js" type="text/javascript"></script>
<script src="scripts/form-validation.js"></script>
<script src="scripts/components-pickers.js"></script>
<script src="scripts/table-managed.js"></script>
<script src="scripts/components-pickers.js"></script>
<!-- END PAGE LEVEL STYLES -->
<script>
jQuery(document).ready(function() {   
   // initiate layout and plugins
  
   Metronic.init(); // init metronic core components
Layout.init(); // init current layout
QuickSidebar.init(); // init quick sidebar
Demo.init(); // init demo features
   //FormValidation.init();
//TableManaged.init();
ComponentsPickers.init();
  
});
</script>
<script>
$('#subject_id').on('change',function(){
	
	if($('#subject_id').val()=='')
	{
		
	}
	else
		{
	$.get("StudentAction",
    	    {
					subject_id: $('#subject_id').val(),
         	      jEventName: "BatchBySubject"
    	    },
    	    function(data,status){
    	     	
    	    	$('#batch').html(data);
    	    });
		}
});
</script>
</body>
</html>