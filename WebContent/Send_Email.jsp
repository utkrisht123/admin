<%@page import="java.awt.image.SampleModel"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
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
  <%
  	String name=null;
  	String email=null;
  	String last=null,batch_id=null;
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8"> 
<link rel="icon" href="img/favicon.png" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.png" type="image/x-icon" />
<meta name="description" content="Insturctor Led Online Traning">
<meta name="keywords" content="Big Data">
<meta name="author" content="Mayank Gupta">
<title>Easylearning.guru</title>
</head>
<body>
    <%
   
		
		
   		String meeting_id=null,url=null,subject=null,date=null,time=null,curd=null,curt=null,timing=null,course=null,country=null,subject_id=null;
		String phone=null,query=null;
		String d=null,t=null;
		String student_id=null;
		String sessionDate=(String)request.getAttribute("session_date");
		String module=(String)request.getAttribute("module");
		ArrayList<String> al2=(ArrayList<String>)request.getAttribute("student");
		String meetingId=(String)request.getAttribute("meeting_id");
		url=(String)request.getAttribute("url");
		url=url.trim();
		String lid[]=url.split("/");
			last=lid[lid.length-1];
			//System.out.println(last);
			
	   		
	   		
	   		meeting_id=request.getParameter("module");
		for(int i=0;i<al2.size();i++)
		{
			
	 			String student[]=al2.get(i).split("abczxy");
	 			name=student[0];
	 			email=student[1];
	   			
	   	
	   	 try
	     {  
	   	// String host="mail.gyansha.com";  
	   	final String user="support@easylearning.guru";
		 final String password="Facebook.com1"; 
	       
	     String to=email;
	     
	     String contmess="<!DOCTYPE html>"+
	    		 "<html class=' js chrome webkit'><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"+
	    		 "<title>Easylearning.guru</title>"+
	    		 "<meta http-equiv='X-UA-Compatible' content='IE=EDGE'>"+
	    		 "<meta name='viewport' content='' id='viewport'>"+
	    		 "<link rel='shortcut icon' href='http://student.easylearning.guru/mailerimage/logo2.png'/>"+
	    		 "</head>"+
	    		 "<body >"+
	    		 "<div marginwidth='0' marginheight='0' style='background-color: #ffffff; width: 100%; min-height: 100%; margin: 0; padding: 0; background: none;'>"+
	    		 "<table width='100%' border='0' height='200' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td style=' background-repeat: no-repeat; background-color: #58ACFA;'>"+
	    		 		"<div>"+
	    		 			"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%'>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='0'></td>"+
	    		 							"</tr><tr><td width='100%' valign='middle'>	"+
	    		 									"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='left' style='border-collapse: collapse;'><tbody><tr><td height='60' valign='middle' width='100%' style='text-align: center;'>"+
	    		 												"<a href='http://easylearning.guru/' style='color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img width='300' src='http://student.easylearning.guru/mailerimage/logo3.png' alt='' border='0' style='width: 292px; min-height: auto;'></a>"+
	    		 											"</td>"+
	    		 										"</tr></tbody></table><div><span></span></div>"+
	    		 									"<table border='0' cellpadding='0' cellspacing='0' align='right' style='border-collapse: collapse;'><tbody><tr><td><div><span></span></div>"+
	    		 										"</td></tr></tbody><tbody><tr><td valign='right' align='right'>	"+
	    		 											"</td>"+
	    		 										"</tr></tbody></table><table width='40' border='0' cellpadding='0' cellspacing='0' align='right' style='border-collapse: collapse;'><tbody><tr><td width='100%' height='0'></td>"+
	    		 										"</tr></tbody></table></td>"+
	    		 							"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='0'></td>"+
	    		 							"</tr><tr><td width='100%' valign='middle'>"+
	    		 									"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='text-align: center; border-collapse: collapse;'><tbody><tr><td valign='middle' width='100%' style='text-align: center; font-family: &#39;proxima; font-size: 24px; color: #fff; line-height: 37px; font-weight: normal;'><div style='text-align: center;'><span style='color: #f0fff0;'>"+
	    		 "</span></div>"+
	    		 "<p style='margin-bottom: 0cm; line-height: 100%; text-align: center; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;font-size: 22px;'><br><span style='color: #f0fff0;  '>Webinar Class Information</span></p>"+
	    		 "</td></tr><tr><td width='100%' height='10'></td>"+
	    		 										"</tr><tr><td align='center'>"+
	    		 												"<table border='0' cellpadding='0' cellspacing='0' align='center'></table></td>	"+									
	    		 							"</tr></tbody></table></td></tr></tbody></table></td>"+
	    		 							"</tr></tbody></table></div></td>"+
	    		 				"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'><div>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>	"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='30'></td>"+
	    		 							"</tr></tbody></table></td>"+
	    		 				"</tr></tbody></table></div>"+
	    		 		"</td>"+
	    		 	"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'>"+
	    		 		"<div>"+
	    		 			"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='15'></td>"+
	    		 							"</tr></tbody></table><table width='600' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%'>"+
	    		 									"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='border-collapse: collapse;'><tbody><tr><td valign='middle' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 21px; color: rgb(48,48,48); line-height: 32px; font-weight: bold;'><span style='font-family: proxima; font-weight: normal;'>Hi "+name+",</span></td>"+
	    		 										"</tr><tr><td width='100%' height=''></td>"+
	    		 										"</tr><tr><td><table width='100' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100' align='center' height='1' style='border-bottom: 1px  solid  #fff;'></td>"+
	    		 													"</tr></tbody></table></td></tr><tr><td width='100%' height='20'></td>"+
	    		 										"</tr><tr><td width='100%' height='15'></td>"+
	    		 										"</tr><tr><td valign='middle' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 15px; color: #bababa; line-height: 22px;'>"+
	    		" <p style='margin-bottom: 0.35cm; line-height: 115%; direction: ltr; color: rgb(0,0,10); text-align: left; font-family: &quot;Calibri&quot;,serif; font-size: 11pt; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
	    		" <p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
	    		 "<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>Your next webinar class details are given below :</p>&nbsp;"+
	    		 "<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
	    		 "<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Class Date & Time : </strong><em> <strong>"+sessionDate+" IST</strong><br>&nbsp;</p>"+
	    		 "<p style='text-align: justify; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
	    	    "<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Module Name : </strong></em> <strong> "+module+" </strong>.<br>&nbsp;</p>"+
	    	    "<p style='text-align: justify; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'></p>"+
	    		 "<p style='text-align: center; margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'><em><strong>Meeting Id : </strong></em> <strong>"+meetingId+"</strong>.</p>"+
	    		 "<p style='margin-bottom: 0px; line-height: 120%; direction: ltr; color: rgb(0,0,10); text-align: left; padding: 0; margin-top: 0px; margin-right: 0px; margin-left: 0px;'>&nbsp;</p>"+
	    		 "</td></tr><tr><td width='100%' height='20'></td>	"+				
	    		 										"</tr></tbody></table></td>"+
	    		 							"</tr></tbody></table></td>"+
	    		 				"</tr></tbody></table></div>"+
	    		 		"</td>"+
	    		 	"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'>"+
	    		 		"<div>"+
	    		 			"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='10'></td>"+
	    		 							"</tr></tbody></table></td>"+
	    		 				"</tr></tbody></table></div>"+
	    		 		"</td>"+
	    		 	"</tr></tbody></table><table width='600' style='margin: 0  auto;' align='center'><tbody><tr><td width='100%' height='5'></td>"+
	    		 													"</tr><tr><td>"+
	    		                                                "<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='border-collapse: collapse;'><tbody><tr><td valign='middle' align='center' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 14px; color: #1a1a1a; line-height: 22px;'><span style='font-family: proxima; font-weight: normal; text-align: center;'></span><span style='font-family: proxima;'>You will receive a webinar link in few minutes.</span><br><br>&nbsp;</td>"+
	    		 										"</tr></tbody></table></td>"+
	    		 													"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'>"+
	    		 		"<div>"+
	    		 			"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='10'></td>"+
	    		 							"</tr><tr><td width='100%' align='center' valign='middle'>"+
	    		 									"<table width='138' border='0' cellpadding='0' cellspacing='0' align='center' style='border-collapse: collapse;'><tbody><tr><td height='60' align='center' valign='middle' width='100%' style='text-align: center;'>"+
	    		 												"<a href='http://easylearning.guru/courses' style='color: rgb(0,0,255);'></a><a href='http://easylearning.guru/courses' style='color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img src='http://student.easylearning.guru/mailerimage/logo.png' alt='Easylearning.guru' border='0' style='margin: 0; padding: 0; width: 312px; min-height: auto;'></a>"+
	    		 											"</td>"+
	    		 										"</tr></tbody></table></td>"+
	    		 							"</tr></tbody></table></td>"+
	    		 				"</tr></tbody></table></div>"+
	    		 		"</td>"+
	    		 	"</tr></tbody></table><table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' valign='top'>"+
	    		 		"<div>"+
	    		 			"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td>"+
	    		 						"<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%' height='25'></td>"+
	    		 							"</tr></tbody></table><table width='600' border='0' cellpadding='0' cellspacing='0' align='center'><tbody><tr><td width='100%'>	"+		
	    		 									"<table width='600' border='0' cellpadding='0' cellspacing='0' align='center' style='border-collapse: collapse;'><tbody><tr><td valign='top' width='100%' style='text-align: center; font-family: Helvetica,Arial,sans-serif; font-size: 14px; color: #bababa; line-height: 22px;'><a href='https://www.facebook.com/Easylearning.guru' style='text-decoration: none; color: rgb(0,0,255);'><img alt='' border='0' height='auto' src='http://student.easylearning.guru/mailerimage/fb.png' width='20'></a> &nbsp;&nbsp; <a href='https://plus.google.com/u/0/b/112145106629165659518/' style='text-decoration: none; color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img alt='' border='0' height='auto' src='http://student.easylearning.guru/mailerimage/gplus.png' width='20'></a> &nbsp;&nbsp; <a href='https://twitter.com/easylearninguru' style='text-decoration: none; color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img alt='' border='0' height='auto' src='http://student.easylearning.guru/mailerimage/tw.png' width='20'></a> &nbsp;&nbsp; <a href='https://www.linkedin.com/company/easylearning' style='text-decoration: none; color: rgb(0,0,255);' target='_blank' rel='noreferrer'><img alt='' border='0' height='auto' src='http://student.easylearning.guru/mailerimage/in.png' width='20'></a> &nbsp;&nbsp; <a href='https://plus.google.com/u/0/b/112145106629165659518/' style='text-decoration: none; color: rgb(0,0,255);'><img alt='' border='0' height='auto' src='http://student.easylearning.guru/mailerimage/yt.png' width='20'></a></td>"+
	    		 										"</tr><tr><td width='100%' height='25'></td>"+
	    		 										"</tr></tbody></table></td>"+
	    		 							"</tr></tbody></table></td>"+
	    		 				"</tr></tbody></table></div>"+
	    		 		"</td>"+
	    		 	"</tr></tbody></table>"+
	    		 	"<center>"+
	    		                 "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #ffffff; border-top: 1px  solid  #e5e5e5;'><tbody><tr><td align='center' valign='top' style='padding-top: 20px; padding-bottom: 20px;'>"+
	    		                             "<table border='0' cellpadding='0' cellspacing='0'><tbody><tr><td align='center' valign='top' style='color: #606060; font-family: Helvetica,Arial,sans-serif; font-size: 11px; line-height: 150%; padding-right: 20px; padding-bottom: 5px; padding-left: 20px; text-align: center;'>"+
	    		                                        " You can send your query, comments and feedback to: <b>contact@easylearning.guru</b>"+
	    		                                        " <br>&nbsp;"+
	    		 										"<br>"+
	    		 										"This is an automatically generated email. You are receiving this email because you registered on our website."+
	    		 										"<br>"+
	    		 										"<br>"+
	    		                                       " &copy; Copyright 2015 </b> <a href='http://easylearning.guru'>Easylearning.guru</a>"+
	    		                                         "<br></td>"+
	    		                                 "</tr></tbody></table></td>"+
	    		                     "</tr></tbody></table></center></div></div><br></div></div></div>"+
	    		 "</div></div></div></div>"+
	    		 "</body></html>";
	     
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
	        message.setSubject("Next Webinar Class Link");  
	        message.setContent(contmess,"text/html"); 
	        message.setSentDate(new java.util.Date());
	       //send the message  
	        Transport.send(message);  
	   	      
       			
	   	
       		
    		}
    		catch(Exception e)
    		{
    			System.out.println(e);
    			//response.sendRedirect("error.html");
    			
    			
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
     			 alert('Email has Send successfully');
     			 location.replace("Send_Webinar.jsp");
     		    }
     		
     		  }
     		xmlhttp.open("POST",x,true);
     		xmlhttp.send();
     		}
       		</script>
       		<%} 
		
		  
       		%>
</body>
</html>
