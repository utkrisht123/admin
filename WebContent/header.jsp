<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="AdminMain.jsp">
			<img src="img/logo.png" height='43' alt="logo" class="logo-default"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide">
				<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul class="nav navbar-nav pull-right">
				<!-- BEGIN NOTIFICATION DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="icon-bell"></i>
					<span class="badge badge-default" id="noti">
					 </span>
					</a>
					<ul class="dropdown-menu" id="showing_notification">
						
					</ul>
				</li>
				
				<!-- END NOTIFICATION DROPDOWN -->
				
				
				
				
				<!-- BEGIN TODO DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="icon-calendar"></i>
					<span class="badge badge-default" id="task_noti">
					 </span>
					</a>
					<ul class="dropdown-menu extended tasks" id="task_notification">
						
					</ul>
				</li>
				<!-- END TODO DROPDOWN -->
				<%
				String nature=(String)session.getAttribute("nature");
				if(nature!=null && nature.equalsIgnoreCase("admin head"))
				{
				%>
				<!-- BEGIN INBOX DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="fa fa-money"></i>
					<span class="badge badge-default" id="p_noti">
					 </span>
					</a>
					<ul class="dropdown-menu" id="p_notification">
						
						
					</ul>
				</li>
				<%
				}
				%>
				<!-- END INBOX DROPDOWN -->
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown dropdown-user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img alt="" class="img-circle hide1" src="displayphoto"/>
					<span class="username username-hide-on-mobile">
					<%=session.getAttribute("user") %> </span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="MyProfile.jsp">
							<i class="icon-user"></i> My Profile </a>
						</li>
						<li>
							<a href="MyTask.jsp">
							<i class="icon-calendar"></i> My Tasks <span class="badge badge-success" id="taskid">
							</span>
							</a>
						</li>
						
						<li class="divider">
						</li>
						<li>
							<a href="LoginServlet?jEventName=Lock&user=<%=session.getAttribute("user") %>">
							<i class="icon-lock"></i> Lock Screen </a>
						</li>
						<li>
							<a href="LoginServlet?jEventName=logout">
							<i class="icon-key"></i> Log Out </a>
						</li>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
				<!-- BEGIN QUICK SIDEBAR TOGGLER -->
				<li class="dropdown dropdown-quick-sidebar-toggler">
					<a href="javascript:;" class="dropdown-toggle">
					<i class="icon-logout"></i>
					</a>
				</li>
				<!-- END QUICK SIDEBAR TOGGLER -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->