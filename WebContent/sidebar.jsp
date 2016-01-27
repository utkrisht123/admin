<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<!-- BEGIN SIDEBAR MENU -->
			<!-- Begin SIDEBAR --->
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
				
				<li class="start active open">
					<a href="AdminMain.jsp">
					<i class="icon-home"></i>
					<span class="title">Home</span>
					<span class="selected"></span>
					<span class="arrow open"></span>
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
							<a href="LiveDemoQuery.jsp">
							<i class="icon-tag"></i>
							Live Query</a>
						</li>
						<li>
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
						<li>
							<a href="TodayLiveDemoWebinar.jsp">
							<i class="icon-tag"></i>
							Reshedule Today Webinar Task</a>
						</li>
						<li>
							<a href="ChatQuery.jsp">
							<i class="fa fa-wechat"></i>
							Chat Query</a>
						</li>
						<li>
							<a href="ViewChatQuery.jsp">
							<i class="fa fa-weixin"></i>
							View Chat Query</a>
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
				<li>
					<a href="javascript:;">
					<i class="icon-briefcase"></i>
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
				<li>
					<a href="javascript:;">
					<i class="icon-briefcase"></i>
					<span class="title">Subjects</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li>
							<a href="Category.jsp">
							<i class="fa fa-plus-square"></i>
							Category</a>
						</li>
						<li>
							<a href="Technology.jsp">
							<i class="fa fa-plus-square"></i>
							Technology</a>
						</li>
						<li>
							<a href="CreateSubject.jsp">
							<i class="icon-briefcase"></i>
							Create Subject</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=G_Subject">
							<i class="icon-briefcase"></i>
							View Subject</a>
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
						<li >
							<a href="CreateMainFAQ.jsp">
							<i class="icon-home"></i>
							Create Main FAQ</a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=V_MainFAQ">
							<i class="fa fa-plus-square"></i>
							View Main FAQ</a>
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
							<a href="Become_instructor.jsp">
							Become Instructor</a>
					</li>
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
						<li >
							<a href="ServletAction?jEventName=StudentStory">
							View Student Story</a>
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
	<!-- END SIDEBAR --->
			