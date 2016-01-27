/**
 * @author Administrator Binay Gaur
 *
 */
package com;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.internet.*;
import javax.activation.*;
import javax.net.ssl.*;
import javax.mail.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.sun.mail.iap.Response;

public class PaymentMail extends HttpServlet { 
	
	String driver,url,user,password;
	  @Override
	  public void init() throws ServletException{ /* Do required initialization*/ } 
	  ArrayList<String> list =null;
	  @Override
	  public void doGet(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException {
		  doPost(request,response);
	  }
	  @Override
	  public void doPost(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException {
	      String jMessage = null;
	     
	      response.setContentType("text/html"); 
	      
	      PrintWriter out = response.getWriter();
	      String jEventName = request.getParameter("jEventName");
		  //System.out.println("jEventName: " + jEventName);
		  HttpSession session=request.getSession();
		 	 String username=(String)session.getAttribute("user");
		  if(jEventName != null && jEventName.equalsIgnoreCase("C_Payment")){//add code for Custom Payment
	    	    String email=request.getParameter("email");
	    	    String name=request.getParameter("name");
	    	    String datetime[]=request.getParameter("datetime").split(" ");
	    	    String s_date = datetime[0];
	    	    String s_time=datetime[1];
	    	    String subjectId=request.getParameter("subject_id");
	    	    //String s_date = request.getParameter("s_date");
	    	    //String s_time =request.getParameter("s_time");
	    	    String paymentIn=request.getParameter("payment_in");
	    	    String finalPrice=request.getParameter("final_price");
	    	   String subjectName=getCourse(subjectId);
	    	    list = new ArrayList<String>();
	    	    list.add(0, email);
	    	    list.add(1,subjectId);
	    	    list.add(2, s_date);
	    	    list.add(3,s_time);
	    	    list.add(4, paymentIn);
	    	    list.add(5, finalPrice);
	    	    String batch=getBatch();
	    	    if(!isEmail(email))
	    	    {
	    	    	jMessage="Provided Email Id not exist in student table";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Custom_Payment.jsp").forward(request, response);
	    	    }
	    	    else if(batch.equals(""))
	    	    {
	    	    	
	    	    	jMessage="Batch not found enter Start date and time for this course";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Custom_Payment.jsp").forward(request, response);
	    	    	
	    	    }
	    	    else
	    	    {
	    	    	
	    	    	float p=0;
	    	    	String fp="";
	    	    	 String currency="&#8377;";
	    	    	try
	    	    	{
	    	    			String msg="";
	    	    			setPayment(name,email,username);
	    	    			//String host="smtp.gmail.com";  
	    	    			final String user="support@easylearning.guru";
	    	    	   	    final String password="Facebook.com1"; 
	    	          
	    	    			String to=email;
	    	    			/*if(paymentIn.equalsIgnoreCase("INR"))
	    	    			{
	    	    				p=Float.parseFloat(finalPrice);
	    	    				fp=String.format("%.2f", p);
	    	    				currency="&#8377;";
	    	    				msg="";
	    	    			}
	    	    			else
	    	    			{
	    	    				float p1=0.956f,p2=0.31f;
	    	        			float p3=(1-(14.0f/100));
	    	        			float usd=((((Float.parseFloat(finalPrice)/p3)/61)-p2)/p1);
	    	    				p=usd;
	    	    				fp=String.format("%.2f", p);
	    	    				msg="(USD)";
	    	    				currency="&#36;";
	    	    			}
	    	    			*/
	    	        
	    	        	
	    	        String s="http://easylearning.guru/payment="+email+"="+batch+"="+paymentIn+"="+finalPrice;
	    	        String s2="http://easylearning.guru/payment-action.do?id=";
	    	        String s3=org.apache.commons.codec.binary.Base64.encodeBase64String(s.getBytes());
	    	        
	    	        String contmess="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>Transaction Link for "+subjectName+" Training | Easylearning.Guru</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content='Transaction Link for "+subjectName+" Training | Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>Transaction Link for "+subjectName+" Training</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the transaction link is mentioned below. Click on the link to be directed to the payment page.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px;text-align: center'><strong style='font-weight: bold'><a href='"+s2+s3+"'>"+s3+"</a></strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br><strong style='font-weight: bold'>Easylearning.Guru<br>Making Learning Easy for You</strong></p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center> <img style='border: 0 !important;-ms-interpolation-mode: bicubic;visibility: hidden !important;display: block !important;height: 1px !important;width: 1px !important;margin: 0 !important;padding: 0 !important' src='./custompayment_files/o.gif' width='1' height='1' border='0' alt=''></body></html>";
	                
	    	        
	    	        Properties props = new Properties();  
	      	         props.put("mail.smtp.host","smtp.googlemail.com");  
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
	    	           message.setSubject("Transaction Link for "+subjectName+" Training | Easylearning.Guru");  
	    	           message.setContent(contmess,"text/html"); 
	    	           message.setSentDate(new java.util.Date());
	    	          //send the message  
	    	           Transport.send(message);  
	    	    	
	    	    	jMessage="Payment link send successfully";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Custom_Payment.jsp").forward(request, response);
	    	    	
	    	    }
	    	    	catch(Exception e){
	    				  System.out.println(e);
	    				  e.printStackTrace();
	    			  	}
	    	    }
	  	  
	  			
	    }
	      
		  else if(jEventName != null && jEventName.equalsIgnoreCase("A_Payment")){//add code for Automatic Payment
	    	    String email=request.getParameter("email");
	    	    String name=request.getParameter("name");
	    	    String subjectId=request.getParameter("subject_id");
	    	    String datetime[]=request.getParameter("datetime").split(" ");
	    	    String s_date = datetime[0];
	    	    String s_time=datetime[1];
	    	    String subjectName=getCourse(subjectId);
	    	    String paymentIn=request.getParameter("payment_in");
	    	    String finalPrice=request.getParameter("final_price");
	    	    //System.out.println("fp="+finalPrice);
	    	    list = new ArrayList<String>();
	    	    list.add(0, email);
	    	    list.add(1,subjectId);
	    	    list.add(2, s_date);
	    	    list.add(3,s_time);
	    	    list.add(4, paymentIn);
	    	    list.add(5, finalPrice);
	    	    String batch=getBatch();
	    	    if(!isEmail(email))
	    	    {
	    	    	jMessage="Provided Email Id not exist in student table";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Automatic_Payment.jsp").forward(request, response);
	    	    }
	    	    else if(batch.equals(""))
	    	    {
	    	    	
	    	    	jMessage="Batch not found enter date and time for this subject";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Automatic_Payment.jsp").forward(request, response);
	    	    	
	    	    }
	    	    else
	    	    {
	    	    	float p=0;
	    	    	String fp="";
	    	    	 String currency="&#8377;";
	    	    	try
	    	    	{
	    	    			String msg="";
	    	    			setPayment(name,email,username);
	    	    			//String host="mail.gyansha.com";  
	    	    			final String user="support@easylearning.guru";
	    	    			 final String password="Facebook.com1"; 
	    	          
	    	    			String to=email;
	    	    			/*if(paymentIn.equalsIgnoreCase("INR"))
	    	    			{
	    	    				p=Float.parseFloat(finalPrice);
	    	    				fp=String.format("%.2f", p);
	    	    				currency="&#8377;";
	    	    				msg="";
	    	    			}
	    	    			else
	    	    			{
	    	    				float p1=0.956f,p2=0.31f;
	    	        			float p3=(1-(14.0f/100));
	    	        			float usd=((((Float.parseFloat(finalPrice)/p3)/61)-p2)/p1);
	    	    				p=usd;
	    	    				fp=String.format("%.2f", p);
	    	    				msg="(USD)";
	    	    				currency="&#36;";
	    	    			}
	    	        */
	    	        
	    	        /*	
	    	        String s="http://easylearning.guru/payment="+email+"="+batch+"="+paymentIn+"="+finalPrice;
	    	        String s2="http://easylearning.guru/PaymentAction?id=";
	    	        String s3=org.apache.commons.codec.binary.Base64.encodeBase64String(s.getBytes());*/
	    	    			 String s="http://easylearning.guru/payment="+email+"="+batch+"="+paymentIn+"="+finalPrice;
	    		    	        String s2="http://easylearning.guru/payment-action.do?id=";
	    		    	        String s3=org.apache.commons.codec.binary.Base64.encodeBase64String(s.getBytes());
	    	        
	    	        String contmess="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title>Transaction Link for "+subjectName+" Training | Easylearning.Guru</title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content='Transaction Link for "+subjectName+" Training | Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>Transaction Link for "+subjectName+" Training</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, the transaction link is mentioned below. Click on the link to be directed to the payment page.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px;text-align: center'><strong style='font-weight: bold'><a href='"+s2+s3+"'>"+s3+"</a></strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br><strong style='font-weight: bold'>Easylearning.Guru<br>Making Learning Easy for You</strong></p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center> <img style='border: 0 !important;-ms-interpolation-mode: bicubic;visibility: hidden !important;display: block !important;height: 1px !important;width: 1px !important;margin: 0 !important;padding: 0 !important' src='./custompayment_files/o.gif' width='1' height='1' border='0' alt=''></body></html>";
	                
	    	        
	    	         //Get the session object  
	    	        Properties props = new Properties();  
	    	        props.put("mail.smtp.host", "smtp.googlemail.com"); //SMTP Host
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
	    	           message.setSubject("Transaction Link for "+subjectName+" Training | Easylearning.Guru");  
	    	           message.setContent(contmess,"text/html"); 
	    	           message.setSentDate(new java.util.Date()); 
	    	          //send the message  
	    	           Transport.send(message);  
	    	    	
	    	    	jMessage="Automatic Payment link send successfully";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Automatic_Payment.jsp").forward(request, response);
	    	    	
	    	    }
	    	    	catch(Exception e){
	    				  System.out.println(e);
	    				  e.printStackTrace();
	    			  	}
	    	    }
	  	  
	  			
	    }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("Payment_Coupon")){//add code for Payment Coupon
	    	    String email=request.getParameter("email");
	    	    String name=request.getParameter("name");
	    	    String subjectId=request.getParameter("subject_id");
	    	    String batchId=request.getParameter("batch");
	    	    String bPrice=request.getParameter("base_price");
	    	    String priceType=request.getParameter("p_type");
	    	    String discount=request.getParameter("discount");
	    	    String discountPrice=request.getParameter("discounted_price");
	    	    String finalPrice=request.getParameter("final_price");
	    	    String tPrice=request.getParameter("t_price");
	    	    
	    	    String pType = "EXCL";
	            String sTax = request.getParameter("tax");
	            double basePrice=0;
	            double fPrice=Double.parseDouble(tPrice);
	            double tax=0;
	            
	          	  basePrice=fPrice/(Double.parseDouble(sTax)/100+1);
	          	  tax=fPrice-basePrice;
	    	    String studentId=getStudent(email);
	    	    list = new ArrayList<String>();
	    	    list.add(0, username);
	    	    list.add(1, studentId);
	    	    list.add(2, email);
	    	    list.add(3, batchId);
	    	    list.add(4,""+basePrice);
	    	    list.add(5, discount);
	    	    list.add(6, discountPrice);
	    	    list.add(7, finalPrice);
	    	    list.add(8, sTax);
	            list.add(9, ""+tax);
	            list.add(10, pType);
	    	    String coupon=getCoupan(batchId);
	    	    if(!isEmail(email))
	    	    {
	    	    	jMessage="Provided Email Id not exist in student table";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Payment_Coupon.jsp").forward(request, response);
	    	    }
	    	    else if(studentId.equals(""))
	    	    {
	    	    	
	    	    	jMessage="Student Id not found please register first";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Payment_Coupon.jsp").forward(request, response);
	    	    	
	    	    }
	    	    else
	    	    {
	    	    	 //System.out.println("hello");
	    	    	if(sendCoupan(coupon,priceType))
	    	    	{
	    	    	try
	    	    	{
	    	    	//String host="mail.gyansha.com";  
	    	    	final String user="support@easylearning.guru";
	    	   	    final String password="Facebook.com1";  
	    	          
	    	        String to=email;
	    	        
	    	        String contmess="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'> <title></title> <style type='text/css'>body{margin: 0; mso-line-height-rule: exactly; padding: 0; min-width: 100%;}table{border-collapse: collapse; border-spacing: 0;}td{padding: 0; vertical-align: top;}.spacer,.border{font-size: 1px; line-height: 1px;}.spacer{width: 100%;}img{border: 0; -ms-interpolation-mode: bicubic;}.image{font-size: 12px; Margin-bottom: 24px; mso-line-height-rule: at-least;}.image img{display: block;}.logo{mso-line-height-rule: at-least;}.logo img{display: block;}strong{font-weight: bold;}h1,h2,h3,p,ol,ul,li{Margin-top: 0;}ol,ul,li{padding-left: 0;}blockquote{Margin-top: 0; Margin-right: 0; Margin-bottom: 0; padding-right: 0;}.column-top{font-size: 50px; line-height: 50px;}.column-bottom{font-size: 26px; line-height: 26px;}.column{text-align: left;}.contents{table-layout: fixed; width: 100%;}.padded{padding-left: 50px; padding-right: 50px; word-break: break-word; word-wrap: break-word;}.wrapper{display: table; table-layout: fixed; width: 100%; min-width: 620px; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}table.wrapper{table-layout: fixed;}.one-col,.two-col,.three-col{Margin-left: auto; Margin-right: auto; width: 600px;}.centered{Margin-left: auto; Margin-right: auto;}.two-col .image{Margin-bottom: 21px;}.two-col .column-bottom{font-size: 29px; line-height: 29px;}.two-col .column{width: 300px;}.two-col .first .padded{padding-left: 50px; padding-right: 25px;}.two-col .second .padded{padding-left: 25px; padding-right: 50px;}.three-col .image{Margin-bottom: 18px;}.three-col .column-bottom{font-size: 32px; line-height: 32px;}.three-col .column{width: 200px;}.three-col .first .padded{padding-left: 50px; padding-right: 10px;}.three-col .second .padded{padding-left: 30px; padding-right: 30px;}.three-col .third .padded{padding-left: 10px; padding-right: 50px;}.wider{width: 400px;}.narrower{width: 200px;}@media only screen and (min-width: 0){.wrapper{text-rendering: optimizeLegibility;}}@media only screen and (max-width: 620px){[class=wrapper]{min-width: 320px !important; width: 100% !important;}[class=wrapper] .one-col, [class=wrapper] .two-col, [class=wrapper] .three-col{width: 320px !important;}[class=wrapper] .column, [class=wrapper] .gutter{display: block; float: left; width: 320px !important;}[class=wrapper] .padded{padding-left: 20px !important; padding-right: 20px !important;}[class=wrapper] .block{display: block !important;}[class=wrapper] .hide{display: none !important;}[class=wrapper] .image{margin-bottom: 24px !important;}[class=wrapper] .image img{height: auto !important; width: 100% !important;}}.wrapper h1{font-weight: 400;}.wrapper h2{font-weight: 700;}.wrapper h3{font-weight: 400;}.wrapper blockquote p,.wrapper blockquote ol,.wrapper blockquote ul{font-style: italic;}td.border{width: 1px;}tr.border{background-color: #e3e3e3; height: 1px;}tr.border td{line-height: 1px;}.sidebar{width: 600px;}.first.wider .padded{padding-left: 50px; padding-right: 30px;}.second.wider .padded{padding-left: 30px; padding-right: 50px;}.first.narrower .padded{padding-left: 50px; padding-right: 10px;}.second.narrower .padded{padding-left: 10px; padding-right: 50px;}.divider{Margin-bottom: 24px;}.wrapper h1{font-size: 40px; Margin-bottom: 20px;}.wrapper h2{font-size: 24px; Margin-bottom: 16px;}.wrapper h3{font-size: 18px; Margin-bottom: 12px;}.wrapper a{text-decoration: none;}.wrapper a:hover{border-bottom: 0; text-decoration: none;}.wrapper h1 a,.wrapper h2 a,.wrapper h3 a{border: none;}.wrapper p,.wrapper ol,.wrapper ul{font-size: 15px;}.wrapper ol,.wrapper ul{Margin-left: 20px;}.wrapper li{padding-left: 2px;}.wrapper blockquote{Margin: 0; padding-left: 18px;}.btn{Margin-bottom: 27px;}.btn a{border: 0; border-radius: 4px; display: inline-block; font-size: 14px; font-weight: 700; line-height: 21px; padding: 9px 22px 8px 22px; text-align: center; text-decoration: none;}.btn a:hover{Position: relative; top: 3px;}.one-col,.two-col,.three-col,.sidebar{background-color: #ffffff; table-layout: fixed;}.one-col .column table:nth-last-child(2) td h1:last-child,.one-col .column table:nth-last-child(2) td h2:last-child,.one-col .column table:nth-last-child(2) td h3:last-child,.one-col .column table:nth-last-child(2) td p:last-child,.one-col .column table:nth-last-child(2) td ol:last-child,.one-col .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px;}.wrapper .two-col .column table:nth-last-child(2) td h1:last-child,.wrapper .wider .column table:nth-last-child(2) td h1:last-child,.wrapper .two-col .column table:nth-last-child(2) td h2:last-child,.wrapper .wider .column table:nth-last-child(2) td h2:last-child,.wrapper .two-col .column table:nth-last-child(2) td h3:last-child,.wrapper .wider .column table:nth-last-child(2) td h3:last-child,.wrapper .two-col .column table:nth-last-child(2) td p:last-child,.wrapper .wider .column table:nth-last-child(2) td p:last-child,.wrapper .two-col .column table:nth-last-child(2) td ol:last-child,.wrapper .wider .column table:nth-last-child(2) td ol:last-child,.wrapper .two-col .column table:nth-last-child(2) td ul:last-child,.wrapper .wider .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 21px;}.wrapper .two-col h1,.wrapper .wider h1{font-size: 28px; Margin-bottom: 18px;}.wrapper .two-col h2,.wrapper .wider h2{font-size: 20px; Margin-bottom: 14px;}.wrapper .two-col h3,.wrapper .wider h3{font-size: 17px; Margin-bottom: 10px;}.wrapper .two-col p,.wrapper .wider p,.wrapper .two-col ol,.wrapper .wider ol,.wrapper .two-col ul,.wrapper .wider ul{font-size: 13px;}.wrapper .two-col blockquote,.wrapper .wider blockquote{padding-left: 16px;}.wrapper .two-col .divider,.wrapper .wider .divider{Margin-bottom: 21px;}.wrapper .two-col .btn,.wrapper .wider .btn{Margin-bottom: 24px;}.wrapper .two-col .btn a,.wrapper .wider .btn a{font-size: 12px; line-height: 19px; padding: 6px 17px 6px 17px;}.wrapper .three-col .column table:nth-last-child(2) td h1:last-child,.wrapper .narrower .column table:nth-last-child(2) td h1:last-child,.wrapper .three-col .column table:nth-last-child(2) td h2:last-child,.wrapper .narrower .column table:nth-last-child(2) td h2:last-child,.wrapper .three-col .column table:nth-last-child(2) td h3:last-child,.wrapper .narrower .column table:nth-last-child(2) td h3:last-child,.wrapper .three-col .column table:nth-last-child(2) td p:last-child,.wrapper .narrower .column table:nth-last-child(2) td p:last-child,.wrapper .three-col .column table:nth-last-child(2) td ol:last-child,.wrapper .narrower .column table:nth-last-child(2) td ol:last-child,.wrapper .three-col .column table:nth-last-child(2) td ul:last-child,.wrapper .narrower .column table:nth-last-child(2) td ul:last-child{Margin-bottom: 18px;}.wrapper .three-col h1,.wrapper .narrower h1{font-size: 24px; Margin-bottom: 16px;}.wrapper .three-col h2,.wrapper .narrower h2{font-size: 18px; Margin-bottom: 12px;}.wrapper .three-col h3,.wrapper .narrower h3{font-size: 15px; Margin-bottom: 8px;}.wrapper .three-col p,.wrapper .narrower p,.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{font-size: 12px;}.wrapper .three-col ol,.wrapper .narrower ol,.wrapper .three-col ul,.wrapper .narrower ul{Margin-left: 14px;}.wrapper .three-col li,.wrapper .narrower li{padding-left: 1px;}.wrapper .three-col blockquote,.wrapper .narrower blockquote{padding-left: 12px;}.wrapper .three-col .divider,.wrapper .narrower .divider{Margin-bottom: 18px;}.wrapper .three-col .btn,.wrapper .narrower .btn{Margin-bottom: 21px;}.wrapper .three-col .btn a,.wrapper .narrower .btn a{font-size: 10px; line-height: 16px; padding: 5px 17px 5px 17px;}.wrapper .wider .column-bottom{font-size: 29px; line-height: 29px;}.wrapper .wider .image{Margin-bottom: 21px;}.wrapper .narrower .column-bottom{font-size: 32px; line-height: 32px;}.wrapper .narrower .image{Margin-bottom: 18px;}.header{Margin-left: auto; Margin-right: auto; width: 600px;}.header .logo{padding-bottom: 40px; padding-top: 40px; width: 280px;}.header .logo div{font-size: 24px; font-weight: 700; line-height: 30px;}.header .logo div a{text-decoration: none;}.header .logo div.logo-center{text-align: center;}.header .logo div.logo-center img{Margin-left: auto; Margin-right: auto;}.header .preheader{padding-bottom: 40px; padding-top: 40px; text-align: right; width: 280px;}.preheader,.footer{letter-spacing: 0.01em; font-style: normal; line-height: 17px; font-weight: 400;}.preheader a,.footer a{letter-spacing: 0.03em; font-style: normal; font-weight: 700;}.preheader,.footer,.footer .social a{font-size: 11px;}.footer{Margin-right: auto; Margin-left: auto; padding-top: 50px; padding-bottom: 40px; width: 602px;}.footer table{Margin-left: auto; Margin-right: auto;}.footer .social{text-transform: uppercase;}.footer .social span{mso-text-raise: 4px;}.footer .social td{padding-bottom: 30px; padding-left: 20px; padding-right: 20px;}.footer .social a{display: block; transition: opacity 0.2s;}.footer .social a:hover{opacity: 0.75;}.footer .address{Margin-bottom: 19px;}.footer .permission{Margin-bottom: 10px;}@media only screen and (max-width: 620px){[class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h1:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h2:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td h3:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td p:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ol:last-child, [class=wrapper] .one-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .two-col .column:last-child table:nth-last-child(2) td ul:last-child, [class=wrapper] .three-col .column:last-child table:nth-last-child(2) td ul:last-child{Margin-bottom: 24px !important;}[class=wrapper] .header, [class=wrapper] .preheader, [class=wrapper] .logo, [class=wrapper] .footer, [class=wrapper] .sidebar{width: 320px !important;}[class=wrapper] .header .logo{padding-bottom: 32px !important; padding-top: 12px !important; padding-left: 10px !important; padding-right: 10px !important;}[class=wrapper] .header .logo img{max-width: 280px !important; height: auto !important;}[class=wrapper] .header .preheader{padding-top: 3px !important; padding-bottom: 22px !important;}[class=wrapper] .header .title{display: none !important;}[class=wrapper] .header .webversion{text-align: center !important;}[class=wrapper] .footer .address, [class=wrapper] .footer .permission{width: 280px !important;}[class=wrapper] h1{font-size: 40px !important; Margin-bottom: 20px !important;}[class=wrapper] h2{font-size: 24px !important; Margin-bottom: 16px !important;}[class=wrapper] h3{font-size: 18px !important; Margin-bottom: 12px !important;}[class=wrapper] .column p, [class=wrapper] .column ol, [class=wrapper] .column ul{font-size: 15px !important;}[class=wrapper] ol, [class=wrapper] ul{Margin-left: 20px !important;}[class=wrapper] li{padding-left: 2px !important;}[class=wrapper] blockquote{border-left-width: 4px !important; padding-left: 18px !important;}[class=wrapper] .btn, [class=wrapper] .two-col .btn, [class=wrapper] .three-col .btn, [class=wrapper] .narrower .btn, [class=wrapper] .wider .btn{Margin-bottom: 27px !important;}[class=wrapper] .btn a, [class=wrapper] .two-col .btn a, [class=wrapper] .three-col .btn a, [class=wrapper] .narrower .btn a, [class=wrapper] .wider .btn a{display: block !important; font-size: 14px !important; letter-spacing: 0.04em !important; line-height: 21px !important; padding: 9px 22px 8px 22px !important;}[class=wrapper] table.border{width: 320px !important;}[class=wrapper] .divider{margin-bottom: 24px !important;}[class=wrapper] .column-bottom{font-size: 26px !important; line-height: 26px !important;}[class=wrapper] .first .column-bottom, [class=wrapper] .second .column-top, [class=wrapper] .three-col .second .column-bottom, [class=wrapper] .third .column-top{display: none;}[class=wrapper] .social td{display: block !important; text-align: center !important;}}@media only screen and (max-width: 320px){td[class=border]{display: none;}}@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi){.one-col ul{border-left: 30px solid #ffffff;}}</style> <meta name='robots' content='noindex,nofollow'><meta property='og:title' content='Coupon Code | Easylearning.Guru'></head> <body style='margin: 0;mso-line-height-rule: exactly;padding: 0;min-width: 100%;background-color: #fff'><style type='text/css'>body,.wrapper,.emb-editor-canvas{background-color:#fff}.border{background-color:#e6e6e6}h1{color:#3b3e42}.wrapper h1{}.wrapper h1{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h1{font-family:Avenir,sans-serif !important}}h1{}.one-col h1{line-height:46px}.two-col h1,.wider h1{line-height:36px}.three-col h1,.narrower h1{line-height:30px}@media only screen and (max-width: 620px){h1{line-height:46px !important}}h2{color:#3b3e42}.wrapper h2{}.wrapper h2{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h2{font-family:Avenir,sans-serif !important}}h2{}.one-col h2{line-height:30px}.two-col h2,.wider h2{line-height:26px}.three-col h2,.narrower h2{line-height:24px}@media only screen and (max-width: 620px){h2{line-height:30px !important}}h3{color:#3b3e42}.wrapper h3{}.wrapper h3{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper h3{font-family:Avenir,sans-serif !important}}h3{}.one-col h3{line-height:26px}.two-col h3,.wider h3{line-height:23px}.three-col h3,.narrower h3{line-height:21px}@media only screen and (max-width: 620px){h3{line-height:26px !important}}p,ol,ul{color:#000}.wrapper p,.wrapper ol,.wrapper ul{}.wrapper p,.wrapper ol,.wrapper ul{font-family:sans-serif}p,ol,ul{}.one-col p,.one-col ol,.one-col ul{line-height:24px;Margin-bottom:24px}.two-col p,.two-col ol,.two-col ul,.wider p,.wider ol,.wider ul{line-height:21px;Margin-bottom:21px}.three-col p,.three-col ol,.three-col ul,.narrower p,.narrower ol,.narrower ul{line-height:18px;Margin-bottom:18px}@media only screen and (max-width: 620px){p,ol,ul{line-height:24px !important;Margin-bottom:24px !important}}.image{color:#000}.image{font-family:sans-serif}.wrapper a{color:#1c8bc7}.wrapper a:hover{color:#166c9a !important}.wrapper .btn a{color:#fff;background-color:#444;box-shadow:0 3px 0 #363636}.wrapper .btn a{font-family:sans-serif}.wrapper .btn a:hover{box-shadow:inset 0 1px 2px #363636 !important;color:#fff !important}.wrapper p a,.wrapper ol a,.wrapper ul a{border-bottom:1px dotted #1c8bc7}.wrapper blockquote{border-left:4px solid #fff}.wrapper .three-col blockquote,.wrapper .narrower blockquote{border-left:2px solid #fff}.logo div{color:#555}.wrapper .logo div{}.wrapper .logo div{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .logo div{font-family:Avenir,sans-serif !important}}.logo div a{color:#555}.logo div a:hover{color:#555 !important}.preheader,.footer{color:#000}.preheader,.footer{font-family:sans-serif}@media only screen and (min-width: 0){.preheader,.footer{font-family:Avenir,sans-serif !important}}.wrapper .preheader a,.wrapper .footer a{color:#000}.wrapper .preheader a:hover,.wrapper .footer a:hover{color:#000 !important}.footer .social a{}.wrapper .footer .social a{}.wrapper .footer .social a{font-family:sans-serif}@media only screen and (min-width: 0){.wrapper .footer .social a{font-family:Avenir,sans-serif !important}}.footer .social a{}.footer .social a{font-weight:600}</style> <center class='wrapper' style='display: table;table-layout: fixed;width: 100%;min-width: 620px;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;background-color: #fff'> <table class='header centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px'> <tbody><tr> <td style='padding: 0;vertical-align: top'> <table style='border-collapse: collapse;border-spacing: 0' align='right'> <tbody><tr> <td class='preheader' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;color: #000;font-family: sans-serif;padding-bottom: 40px;padding-top: 40px;text-align: right;width: 280px'> <div class='spacer' style='font-size: 1px;line-height: 2px;width: 100%'>&nbsp;</div><div class='title'>Coupon Code</div></td></tr></tbody></table> <table style='border-collapse: collapse;border-spacing: 0' align='left'> <tbody><tr> <td class='logo' style='padding: 0;vertical-align: top;mso-line-height-rule: at-least;padding-bottom: 40px;padding-top: 40px;width: 280px'> <div class='logo-center' style='color: #555;font-size: 24px;font-weight: 700;line-height: 30px;font-family: sans-serif;text-align: center' align='center' id='emb-email-header'><a style='text-decoration: none;color: #555' href='http://easylearning.guru'><img style='border: 0;-ms-interpolation-mode: bicubic;display: block;Margin-left: auto;Margin-right: auto;max-width: 417px' src='http://gyansha.com/mailtemplate/Easylearningguru-logo.png' alt='Easylearning.Guru' width='278' height='72'></a></div></td></tr></tbody></table> </td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'></td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td><td style='padding: 0;vertical-align: top'> <table class='one-col' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;width: 600px;background-color: #ffffff;table-layout: fixed'> <tbody><tr> <td class='column' style='padding: 0;vertical-align: top;text-align: left'> <div><div class='column-top' style='font-size: 50px;line-height: 50px'>&nbsp;</div></div><table class='contents' style='border-collapse: collapse;border-spacing: 0;table-layout: fixed;width: 100%'> <tbody><tr> <td class='padded' style='padding: 0;vertical-align: top;padding-left: 50px;padding-right: 50px;word-break: break-word;word-wrap: break-word'> <p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Dear "+name+",</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for talking to our representative. As requested, Coupon Code is&nbsp;mentioned below.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'><strong style='font-weight: bold'>Coupon Code : "+coupon+"</strong></p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px;text-align: center'><strong style='font-weight: bold'>Note:</strong> This code is valid for <strong style='font-weight: bold'>15 minutes</strong> only. Please copy and paste the code as it is and delete spaces if any to redeem the coupon.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Thank you for showing interest in Easylearning.Guru. We hope you have a memorable learning experience.</p><p style='Margin-top: 0;color: #000;font-size: 15px;font-family: sans-serif;line-height: 24px;Margin-bottom: 24px'>Regards,<br>Easylearning.Guru<br>Making Learning Easy for You</p></td></tr></tbody></table> <div class='column-bottom' style='font-size: 26px;line-height: 26px'>&nbsp;</div></td></tr></tbody></table> </td><td class='border' style='padding: 0;vertical-align: top;font-size: 1px;line-height: 1px;background-color: #e6e6e6;width: 1px'></td></tr></tbody></table> <table class='border' style='border-collapse: collapse;border-spacing: 0;font-size: 1px;line-height: 1px;background-color: #e6e6e6;Margin-left: auto;Margin-right: auto' width='602'> <tbody><tr><td style='padding: 0;vertical-align: top'>&nbsp;</td></tr></tbody></table> <table class='centered' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto'> <tbody><tr> <td class='footer' style='padding: 0;vertical-align: top;letter-spacing: 0.01em;font-style: normal;line-height: 17px;font-weight: 400;font-size: 11px;Margin-right: auto;Margin-left: auto;padding-top: 50px;padding-bottom: 40px;width: 602px;color: #000;font-family: sans-serif'> <center> <table class='social' style='border-collapse: collapse;border-spacing: 0;Margin-left: auto;Margin-right: auto;text-transform: uppercase'> <tbody><tr> </tr></tbody></table> <div class='address' style='Margin-bottom: 19px'><strong style='font-weight: bold'>Copyright © 2015 Easylearning.Guru, All rights reserved.<br>Ph:&nbsp;+91 124 4763660<br>Email:&nbsp;contact@easylearning.guru</strong><br><br>Our mailing address is:&nbsp;Easylearning.Guru,&nbsp;Plot No.97,&nbsp;Sector 44,&nbsp;Gurgaon,&nbsp;India</div><div class='permission' style='Margin-bottom: 10px'><strong style='font-weight: bold'>Disclaimer:</strong> This is a system generated information and does not require any signature. If you observe any discrepancy, kindly inform us within 14 days of receiving this alert. Please do not reply to this message. This e-mail is confidential and may also be privileged. If you are not the intended recipient, please notify us immediately and do not disclose its contents to any other person nor copy or use it for any purpose.</div></center> </td></tr></tbody></table> </center></body></html>";
	                
	    	        
	    	         //Get the session object  
	    	         Properties props = new Properties();  
	    	         props.put("mail.smtp.host","smtp.gmail.com");  
	    	         props.put("mail.smtp.port", "587"); //TLS Port
	    	           props.put("mail.smtp.auth", "true"); //enable authentication
	    	           props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS  
	    	           
	    	         Session sess = Session.getInstance(props,
	    	          new javax.mail.Authenticator() {  
	    	            protected PasswordAuthentication getPasswordAuthentication() {  
	    	          return new PasswordAuthentication(user,password);  
	    	            }  
	    	          });  
	    	        // String subject = "Request for live demo";
	    	       //  String messageText ="Thank you for your request for live demo. Our representative contact you shortly.";
	    	         //Compose the message  
	    	           MimeMessage message = new MimeMessage(sess);  
	    	           message.setFrom(new InternetAddress(user));  
	    	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	    	           message.setSubject("Coupon Code | EasyLearning.Guru");  
	    	           message.setContent(contmess,"text/html"); 
	    	           message.setSentDate(new java.util.Date());
	    	          //send the message  
	    	           Transport.send(message);  
	    	    	
	    	    	jMessage="Coupon Payment send successfully";
	    	    	request.setAttribute("jMessage", jMessage);
	    	    	request.setAttribute("jEventName", jEventName);
	    			request.getRequestDispatcher("Payment_Coupon.jsp").forward(request, response);
	    	    	
	    	    }
	    	    	catch(Exception e){
	    				  System.out.println(e);
	    				  e.printStackTrace();
	    			  	}
	    	    	}
	    	   
	    	    }
	      
	  	  
	  			
	    }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("DateBySubject")){//add code for Date By Subject
	    	  String subject_id=request.getParameter("subject_id");
	    	  list=new ArrayList<String>();
	    	  list=getDateTime(subject_id);
	    	  out.println("<select class='form-control select2_category' name='datetime' id='datetime' >");
	        	 out.println("<option value=''>Select Date Time</option>");
	        	 for(int i=0;i<list.size();i++)
	        	 {
	        	  out.println("<option value='"+list.get(i)+"'>"+list.get(i)+"</option>");
	        	 }
	        	 out.println("</select>");
	    	    
	      }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("DateTime")){//add code for Date By Subject
	    	  String subject_id=request.getParameter("subject_id");
	    	  list=new ArrayList<String>();
	    	  list=getDateAndTime(subject_id);
	    	  out.println("<select class='form-control select2_category' name='datetime' id='datetime' >");
	        	 out.println("<option value=''>Select Date Time</option>");
	        	 for(int i=0;i<list.size();i++)
	        	 {
	        	  out.println("<option value='"+list.get(i)+"'>"+list.get(i)+"</option>");
	        	 }
	        	 out.println("</select>");
	    	    
	      }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("FinalPriceByDate")){//add code for Final Price By Date
	    	  String subject_id=request.getParameter("subject_id");
	    	  String datetime=request.getParameter("datetime"); 
	    	  String s[]=datetime.split(" ");
	    	  String date=s[0];
	    	  String time=s[1];
	    	  //System.out.println(date);
	    	  //System.out.println(time);
	    	  String s2=getPrice(subject_id, date, time);
	    	  out.println(s2);
	      }
	      else if(jEventName != null && jEventName.equalsIgnoreCase("BatchDetailBySubjectAndBatch"))
	        {
	        	String subjectId=request.getParameter("subject_id");
	        	String batchId=request.getParameter("batch");
	        	//System.out.println(subjectId);
	        	//System.out.println(batchId);
	        	 ArrayList<String> al=new ArrayList<String>();
	        
	        	 al=viewBatchDetail(subjectId, batchId);		 
	        	 out.println(al.get(0)+"abczxy"+al.get(1)+"abczxy"+al.get(2)+"abczxy"+al.get(3));
	 	        
	        }
	      
	  }
	  @Override
	  public void destroy(){ /* do nothing.*/ }  
	  private Connection getConnection(){
		  Connection con= null;
		  try{
			 
			  con= new MyConnection().getConnection();
			  
		  	} catch(Exception e){
			  System.out.println(e);
			  e.printStackTrace();
		  	}
		  return con;
	  }
	  private void closeConnection(Connection con){
		  try{
			  if(con != null){
				con.close();
			  }
		  }catch(SQLException sqe){
			  System.out.println(sqe);
			  sqe.printStackTrace();
		  }
	  }
	  private boolean isEmail(String email){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "SELECT COUNT(1) FROM student_login WHERE EMAIL_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, email);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						count= rs.getInt(1);
					}
				//System.out.println(count);
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private String getCourse(String subjectId){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		String subject="";
		  try
		  {
			  con = getConnection(); 
			  String sql = "SELECT SUBJECT_NAME FROM subjects WHERE SUBJECT_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, subjectId);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						subject= rs.getString("SUBJECT_NAME");
					}
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return subject;
	  }
	  private String getStudent(String email){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  String studentId="";
		  try
		  {
			  con = getConnection(); 
			  String sql = "SELECT STUDENT_ID FROM student_login WHERE EMAIL_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, email);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						studentId= rs.getString(1);
					}
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return studentId;
	  }
	  private String getBatch(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  String batch="";
		  try
		  {
			  con = getConnection(); 
			  if(!list.isEmpty())
				{
			  DateFormat df4 = new SimpleDateFormat("dd-MMM-yyyy");
			  java.util.Date  date4 = df4.parse(list.get(2));
			  java.sql.Date d3=new java.sql.Date(date4.getTime());
			  DateFormat df = new SimpleDateFormat("HH:mm");
			  java.util.Date  date= df.parse(list.get(3));
			  java.sql.Time time=new java.sql.Time(date.getTime());
			  String sql = "select BATCH_ID from lms_batch where SUBJECT_ID=? and START_DATE=? and START_TIME=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
					ps.setString(1, list.get(1));
					ps.setDate(2, d3);
					ps.setTime(3, time);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						batch=rs.getString("BATCH_ID");
					}
				}
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return batch;
	  }
	  private boolean sendCoupan(String coupon,String price_type){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count= 0;
		 
		  try
		  {
			  con = getConnection(); 
				
				
			  if(price_type.equals("discount"))
			  {
			  String sql = "insert into payment_coupon(COUPON,SENDER_NAME,STUDENT_ID,STUDENT_EMAIL,BATCH,BASE_PRICE,DISCOUNT,FINAL_PRICE,S_TAX,S_TAX_PRICE,P_TYPE) values(?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, coupon);
					ps.setString(2, list.get(0));
					ps.setString(3, list.get(1));
					ps.setString(4, list.get(2));
					ps.setString(5, list.get(3));
					ps.setFloat(6, Float.parseFloat(list.get(4)));
					ps.setFloat(7, Float.parseFloat(list.get(5)));
					ps.setFloat(8, Float.parseFloat(list.get(7)));
					ps.setFloat(9, Float.parseFloat(list.get(8)));
					ps.setFloat(10, Float.parseFloat(list.get(9)));
					ps.setString(11, list.get(10));
					
					count=ps.executeUpdate();
			  }
			  else
			  {
				  String sql = "insert into payment_coupon(COUPON,SENDER_NAME,STUDENT_ID,STUDENT_EMAIL,BATCH,BASE_PRICE,DISCOUNTED_PRICE,FINAL_PRICE,S_TAX,S_TAX_PRICE,P_TYPE) values(?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, coupon);
						ps.setString(2, list.get(0));
						ps.setString(3, list.get(1));
						ps.setString(4, list.get(2));
						ps.setString(5, list.get(3));
						ps.setFloat(6, Float.parseFloat(list.get(4)));
						ps.setFloat(7, Float.parseFloat(list.get(6)));
						ps.setFloat(8, Float.parseFloat(list.get(7)));
						ps.setFloat(9, Float.parseFloat(list.get(8)));
						ps.setFloat(10, Float.parseFloat(list.get(9)));
						ps.setString(11, list.get(10));
						count=ps.executeUpdate();
				  
			  }
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  if(count != 0)
			  return true;
		  else 
			  return false;
	  }
	  private String getCoupan(String batch){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  String coupon="",timestamp="";
		  
		  try
		  {
			  con = getConnection(); 
			  String sql = "select now()";
				PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						timestamp= rs.getString(1);
					}
					coupon=String.valueOf(batch.charAt(0));
					coupon+=batch.replaceAll("[^0-9]", "");
					coupon+=timestamp.replaceAll("[-: ./]", "");
					
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return coupon;
	  }
	  
	  private boolean setPayment(String name,String email,String user){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  int count=0;
		  try
		  {
			  con = getConnection(); 
			  String sql = "insert into v_sendpaymentlink(S_NAME,R_NAME,R_EMAILID) values(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, user);
					ps.setString(2, name);
					ps.setString(3, email);
					count=ps.executeUpdate();
						
				
		  }
		  catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			 if(count!=0)
				 return true;
			 else
				 return false;
	  }
	  public ArrayList<String> getPaymentDetail(){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  String sql = "select * from v_sendpaymentlink order by TIMESTAMP desc";
				PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
						java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
						DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE HH:mm");
						String ld_date="";
							 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							 ld_date= df2.format(date2);
						al.add(rs.getString("S_NAME")+"abczxy"+rs.getString("R_NAME")+"abczxy"+rs.getString("R_EMAILID")+"abczxy"+ld_date);
					}
					
					
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
	  }
	  private ArrayList<String> getDateTime(String subject_id){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  con = getConnection(); 
			  String sql = "Select START_DATE,START_TIME from lms_batch where SUBJECT_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, subject_id);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date  date2 = format1.parse(rs.getString("START_DATE")+" "+rs.getString("START_TIME"));
						DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
						DateFormat df3 = new SimpleDateFormat("HH:mm");
						String date="",time="";
						date= df2.format(date2);
						time= df3.format(date2);
						al.add(date+" "+time);
					}
						
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
				 return al;
	  }
	  
	  private ArrayList<String> getDateAndTime(String subject_id){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  try
		  {
			  System.out.println(subject_id);
			  con = getConnection(); 
			  String sql = "Select START_DATE,START_TIME from lms_batch where SUBJECT_ID=? and START_DATE>=DATE_ADD(NOW(), INTERVAL -3 MONTH) ";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, subject_id);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date  date2 = format1.parse(rs.getString("START_DATE")+" "+rs.getString("START_TIME"));
						DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
						DateFormat df3 = new SimpleDateFormat("HH:mm");
						String date="",time="";
						date= df2.format(date2);
						time= df3.format(date2);
						al.add(date+" "+time);
					}
						
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
				 return al;
	  }
	  private String getPrice(String subject_id,String date,String time){
		  //System.out.println("username :  "+username +"  password :  "+password);	 
		  Connection con = null;
		  String s=new String();
		  try
		  {
			  con = getConnection(); 
			  DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
			  DateFormat df3 = new SimpleDateFormat("HH:mm");
			  java.util.Date  date2 =df2.parse(date);
			  java.util.Date  date3 =df3.parse(time);
			  java.sql.Date d=new java.sql.Date(date2.getTime());
			  java.sql.Time t=new java.sql.Time(date3.getTime());
			  String sql = "Select BASE_PRICE from lms_batch where SUBJECT_ID=? and START_DATE=? and START_TIME=?";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, subject_id);
					ps.setDate(2, d);
					ps.setTime(3, t);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						s=rs.getString("BASE_PRICE");
					}
						
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
				 return s;
	  }
	  public ArrayList<String> viewBatchDetail(String subjectId,String batchId)
		{
	    String query = "SELECT * from lms_batch where batch_id=? and subject_id=?";
	    ArrayList<String> list = new ArrayList<String>();
	    Connection con = null;
	    try {
	    	con = getConnection();
	    	PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, batchId);
	        ps.setString(2, subjectId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date  date2 = format1.parse(rs.getString("START_DATE")+" "+rs.getString("START_TIME"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
				String time="",ld_date="";
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        time=df3.format(date2);
				        list.add(ld_date);
				        list.add(time);
				        list.add(rs.getString("BASE_PRICE"));
				        list.add(rs.getString("S_TAX"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }finally
	    {
	        try {
	            
	            if(con != null)
	                con.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return list;
	}

}
