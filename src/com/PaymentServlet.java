/**
 * @author Administrator Binay Gaur
 *
 */
package com;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
// Extend HttpServlet class

public class PaymentServlet extends HttpServlet { 
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
      String jNotification = null;
      response.setContentType("text/html"); 
      PrintWriter out = response.getWriter();
      String jEventName = request.getParameter("jEventName");
	  //System.out.println("jEventName: " + jEventName);
	 
      if(jEventName != null && jEventName.equalsIgnoreCase("CCA_Payment")){//add code for CCA Payment
         
          ArrayList<String> al=getPaymentInfo(); 
          request.setAttribute("data", al);
        
          request.setAttribute("jEventName", jEventName);
  		  request.getRequestDispatcher("CCAvenue_Payment.jsp").forward(request, response);
         
	}
      if(jEventName != null && jEventName.equalsIgnoreCase("PayPal_Payment")){//add code for PayPal Payment
          
          ArrayList<String> al2= getPaymentPaypal();
          request.setAttribute("data", al2);
          request.setAttribute("jEventName", jEventName);
  		  request.getRequestDispatcher("PayPal_Payment.jsp").forward(request, response);
         
	}
      else if(jEventName != null && jEventName.equalsIgnoreCase("CCA_Request"))//add code for CCA Request only
      {
    	  ArrayList<String> al=getCCAVenueRequest(); 
          request.setAttribute("data", al);
          request.setAttribute("jEventName", jEventName);
  		request.getRequestDispatcher("CCA_Request.jsp").forward(request, response); 
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("PayPal_Request"))//add code for PayPal Request only
      {
    	  
    	  ArrayList<String> al2= getPaypalRequest();
          
          request.setAttribute("data", al2);
          request.setAttribute("jEventName", jEventName);
  		request.getRequestDispatcher("PayPal_Request.jsp").forward(request, response); 
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Payment_Notification")){//add code for Payment Notification
          
          ArrayList<String> al=getCCAvenueNotification(); 
          ArrayList<String> al2= getPaymentPaypalNotification();
          request.setAttribute("data", al);
          request.setAttribute("data2", al2);
          request.setAttribute("jEventName", jEventName);
  		  request.getRequestDispatcher("Payment_Notification.jsp").forward(request, response);
         
	}
      else if(jEventName != null && jEventName.equalsIgnoreCase("CCA_Read")){ //add code for CCA Read notification
    	  String id[]= request.getParameterValues("c");
 	     
			if(isReadCCANotification(id))
			{
					jMessage = "You Successfully Read Selected CCA Payment Notification.";
					ArrayList<String> al=getCCAvenueNotification(); 
					ArrayList<String> al2= getPaymentPaypalNotification();
					request.setAttribute("data", al);
					request.setAttribute("data2", al2);
					request.setAttribute("jMessage", jMessage);
					request.setAttribute("jEventName", jEventName);
					request.getRequestDispatcher("Payment_Notification.jsp").forward(request, response);
    
			}
			else
			{
				jMessage = "Some thing error, not read CCAvenue notification !";
				ArrayList<String> al=getCCAvenueNotification(); 
				ArrayList<String> al2= getPaymentPaypalNotification();
				request.setAttribute("data", al);
				request.setAttribute("data2", al2);
				request.setAttribute("jMessage", jMessage);
				request.setAttribute("jEventName", jEventName);
				request.getRequestDispatcher("Payment_Notification.jsp").forward(request, response);
			}
      }
      else if(jEventName != null && jEventName.equalsIgnoreCase("Paypal_Read")){ ////add code for Paypal read notification
    	  	String id[]= request.getParameterValues("c2");
 	     	
			if(isReadPaypalNotification(id))
			{
				jMessage = "You Successfully Read Selected Paypal Payment Notification.";
				ArrayList<String> al=getCCAvenueNotification(); 
				ArrayList<String> al2= getPaymentPaypalNotification();
				request.setAttribute("data", al);
				request.setAttribute("data2", al2);
				request.setAttribute("jMessage", jMessage);
				request.setAttribute("jEventName", jEventName);
				request.getRequestDispatcher("Payment_Notification.jsp").forward(request, response);
			}
			else
			{
				jMessage = "Some thing error, not read paypal notification !";
				ArrayList<String> al=getCCAvenueNotification(); 
				ArrayList<String> al2= getPaymentPaypalNotification();
				request.setAttribute("data", al);
				request.setAttribute("data2", al2);
				request.setAttribute("jMessage", jMessage);
				request.setAttribute("jEventName", jEventName);
				request.getRequestDispatcher("Payment_Notification.jsp").forward(request, response);
			}
    
    	  
      }
      if(jEventName!=null && jEventName.equals("View_Notification")) // View All Payment Notification in number
      {
    	
    	  int c=viewCCAvenueNotification();
    	  int p=viewPaymentPaypalNotification();
    	  int n=c+p;
    	  if(n>0)
    	  out.println(n);
      }	
    else if (jEventName!=null && jEventName.equals("ViewPayment_Notification")) // View All Payment Notification
      {
    	 int c=viewCCAvenueNotification();
   	  	int p=viewPaymentPaypalNotification();
   	  	int n=c+p;
    	 String s3="";
    	 if(n>0)
    		 s3=""+n;
    	 else
    		 s3="no";
    	  out.println("<li><p>You have "+s3+" new notifications</p></li>");
    	  
    	  out.println("<li><ul class='dropdown-menu-list scroller' style='height: 250px;'>");
    	  
    		  out.println("<li><a href='PaymentServlet?jEventName=Payment_Notification'><span class='subject'><span class='from'>CCAvenue</span><span class='time'>"+c+"</span></span><span class='message'>New CCAvenue Payment Notification </span></a></li>");
    		  out.println("<li><a href='PaymentServlet?jEventName=Payment_Notification'><span class='subject'><span class='from'>PayPal</span><span class='time'>"+p+"</span></span><span class='message'>New PayPal Payment Notification </span></a></li>");
    	  
    	  out.println("</ul></li>");
    	  out.println("<li class='external'><a href='PaymentServlet?jEventName=Payment_Notification'>See all payment notifications <i class='m-icon-swapright'></i></a></li>");
      }
      
  }
  @Override
  public void destroy(){ /* do nothing.*/ }  
  private Connection getConnection(){
	  Connection con= null;
	  try{
		
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  //con= DriverManager.getConnection("jdbc:mysql://localhost/payment","root","");
		  con= DriverManager.getConnection("jdbc:mysql://168.144.175.32/payment","easylearning","Hit@0TsFH115@51123");
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
  
  public ArrayList<String>  getPaymentInfo(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select RQ.STUDENT_ID,RQ.BILLING_NAME,RQ.BILLING_EMAIL,RQ.BILLING_TEL,RQ.BATCH_ID,RQ.ORDER_ID,RS.TRACKING_ID,RQ.AMOUNT,RS.ORDER_STATUS,RS.FAILURE_MESSAGE,RS.PAYMENT_MODE,RS.CARD_NAME,RS.STATUS_CODE,RS.STATUS_MESSAGE,RQ.CURRENCY,RQ.DATE from ccavenue_request as RQ join ccavenue_response as RS on RQ.ORDER_ID=RS.ORDER_ID order by RQ.DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				 
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("RQ.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					al.add(rs.getString("RQ.STUDENT_ID")+"abczxy"+rs.getString("RQ.BILLING_NAME")+"abczxy"+rs.getString("RQ.BILLING_EMAIL")+"abczxy"+rs.getString("RQ.BILLING_TEL")+"abczxy"+rs.getString("RQ.BATCH_ID")+"abczxy"+rs.getString("RQ.ORDER_ID")+"abczxy"+rs.getString("RS.TRACKING_ID")+"abczxy"+rs.getString("RQ.AMOUNT")+"abczxy"+rs.getString("RS.ORDER_STATUS")+"abczxy"+rs.getString("RS.FAILURE_MESSAGE")+"abczxy"+rs.getString("RS.PAYMENT_MODE")+"abczxy"+rs.getString("RS.CARD_NAME")+"abczxy"+rs.getString("RS.STATUS_CODE")+"abczxy"+rs.getString("RS.STATUS_MESSAGE")+"abczxy"+rs.getString("RQ.CURRENCY")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  public ArrayList<String>  getPaymentPaypal(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select R.STUDENT_ID,R.NAME,R.BUYER_MAIL,R.BATCH_ID,R.COURSE_NAME,R.FEE,R.PAYMENT_TYPE,R.CURRENCY_CODE,R.BILLING_TYPE,R.BILLING_AGREEMENT,R.COUNTRY_CODE,RS.TRANSACTION_ID,RS.BILLING_AGREEMENT_ID,RS.ACK,RS.TOKEN,RS.PAYER_ID,R.DATE from paypal_request as R join paypal_response as RS on R.ID=RS.PAYMENT_ID";
			PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("R.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);   
					al.add(rs.getString("R.STUDENT_ID")+"abczxy"+rs.getString("R.NAME")+"abczxy"+rs.getString("R.BUYER_MAIL")+"abczxy"+rs.getString("R.BATCH_ID")+"abczxy"+rs.getString("R.COURSE_NAME")+"abczxy"+rs.getString("R.FEE")+"abczxy"+rs.getString("R.PAYMENT_TYPE")+"abczxy"+rs.getString("R.CURRENCY_CODE")+"abczxy"+rs.getString("RS.TRANSACTION_ID")+"abczxy"+rs.getString("R.BILLING_TYPE")+"abczxy"+rs.getString("R.BILLING_AGREEMENT")+"abczxy"+rs.getString("RS.BILLING_AGREEMENT_ID")+"abczxy"+rs.getString("RS.ACK")+"abczxy"+rs.getString("RS.TOKEN")+"abczxy"+rs.getString("RS.PAYER_ID")+"abczxy"+rs.getString("R.COUNTRY_CODE")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  public ArrayList<String>  getCCAVenueRequest(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select RQ.STUDENT_ID,RQ.BILLING_NAME,RQ.BILLING_EMAIL,RQ.BILLING_TEL,RQ.BATCH_ID,RQ.ORDER_ID,RQ.AMOUNT,RQ.CURRENCY,RQ.DATE from ccavenue_request as RQ where RQ.ORDER_ID NOT IN (SELECT ORDER_ID FROM ccavenue_response) order by RQ.DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				 
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("RQ.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					al.add(rs.getString("RQ.STUDENT_ID")+"abczxy"+rs.getString("RQ.BILLING_NAME")+"abczxy"+rs.getString("RQ.BILLING_EMAIL")+"abczxy"+rs.getString("RQ.BILLING_TEL")+"abczxy"+rs.getString("RQ.BATCH_ID")+"abczxy"+rs.getString("RQ.ORDER_ID")+"abczxy"+rs.getString("RQ.AMOUNT")+"abczxy"+rs.getString("RQ.CURRENCY")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  public ArrayList<String>  getPaypalRequest(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select R.STUDENT_ID,R.NAME,R.BUYER_MAIL,R.BATCH_ID,R.COURSE_NAME,R.FEE,R.PAYMENT_TYPE,R.CURRENCY_CODE,R.BILLING_TYPE,R.BILLING_AGREEMENT,R.COUNTRY_CODE,R.DATE from paypal_request as R where R.ID not in(select PAYMENT_ID from paypal_response)";
			PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("R.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);          
					al.add(rs.getString("R.STUDENT_ID")+"abczxy"+rs.getString("R.NAME")+"abczxy"+rs.getString("R.BUYER_MAIL")+"abczxy"+rs.getString("R.BATCH_ID")+"abczxy"+rs.getString("R.COURSE_NAME")+"abczxy"+rs.getString("R.FEE")+"abczxy"+rs.getString("R.PAYMENT_TYPE")+"abczxy"+rs.getString("R.CURRENCY_CODE")+"abczxy"+rs.getString("R.BILLING_TYPE")+"abczxy"+rs.getString("R.BILLING_AGREEMENT")+"abczxy"+rs.getString("R.COUNTRY_CODE")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  private ArrayList<String>  getCCAvenueNotification(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select RQ.STUDENT_ID,RQ.BILLING_NAME,RQ.BILLING_EMAIL,RQ.BILLING_TEL,RQ.BATCH_ID,RQ.ORDER_ID,RS.TRACKING_ID,RQ.AMOUNT,RS.ORDER_STATUS,RS.FAILURE_MESSAGE,RS.PAYMENT_MODE,RS.CARD_NAME,RS.STATUS_CODE,RS.STATUS_MESSAGE,RQ.CURRENCY,RQ.DATE from ccavenue_request as RQ left outer join ccavenue_response as RS on RQ.ORDER_ID=RS.ORDER_ID where RQ.NOTIFY=? order by RQ.DATE DESC";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "n");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				 
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("RQ.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        
					al.add(rs.getString("RQ.STUDENT_ID")+"abczxy"+rs.getString("RQ.BILLING_NAME")+"abczxy"+rs.getString("RQ.BILLING_EMAIL")+"abczxy"+rs.getString("RQ.BILLING_TEL")+"abczxy"+rs.getString("RQ.BATCH_ID")+"abczxy"+rs.getString("RQ.ORDER_ID")+"abczxy"+rs.getString("RS.TRACKING_ID")+"abczxy"+rs.getString("RQ.AMOUNT")+"abczxy"+rs.getString("RS.ORDER_STATUS")+"abczxy"+rs.getString("RS.FAILURE_MESSAGE")+"abczxy"+rs.getString("RS.PAYMENT_MODE")+"abczxy"+rs.getString("RS.CARD_NAME")+"abczxy"+rs.getString("RS.STATUS_CODE")+"abczxy"+rs.getString("RS.STATUS_MESSAGE")+"abczxy"+rs.getString("RQ.CURRENCY")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  private ArrayList<String>  getPaymentPaypalNotification(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  ArrayList<String> al=new ArrayList<String>();
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select R.ID,R.STUDENT_ID,R.NAME,R.BUYER_MAIL,R.BATCH_ID,R.COURSE_NAME,R.FEE,R.PAYMENT_TYPE,R.CURRENCY_CODE,R.BILLING_TYPE,R.BILLING_AGREEMENT,R.COUNTRY_CODE,RS.TRANSACTION_ID,RS.BILLING_AGREEMENT_ID,RS.ACK,RS.TOKEN,RS.PAYER_ID,R.DATE from paypal_request as R left outer join paypal_response as RS on RS.PAYMENT_ID=R.ID where R.NOTIFY=? order by R.DATE desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "n");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("R.DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);          
					al.add(rs.getString("R.STUDENT_ID")+"abczxy"+rs.getString("R.NAME")+"abczxy"+rs.getString("R.BUYER_MAIL")+"abczxy"+rs.getString("R.BATCH_ID")+"abczxy"+rs.getString("R.COURSE_NAME")+"abczxy"+rs.getString("R.FEE")+"abczxy"+rs.getString("R.PAYMENT_TYPE")+"abczxy"+rs.getString("R.CURRENCY_CODE")+"abczxy"+rs.getString("RS.TRANSACTION_ID")+"abczxy"+rs.getString("R.BILLING_TYPE")+"abczxy"+rs.getString("R.BILLING_AGREEMENT")+"abczxy"+rs.getString("RS.BILLING_AGREEMENT_ID")+"abczxy"+rs.getString("RS.ACK")+"abczxy"+rs.getString("RS.TOKEN")+"abczxy"+rs.getString("RS.PAYER_ID")+"abczxy"+rs.getString("R.COUNTRY_CODE")+"abczxy"+rs.getString("R.ID")+"abczxy"+ld_date+" "+time);
				
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return al;
}
  private int  viewCCAvenueNotification(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	int count=0;
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select count(*) from ccavenue_request as RQ left outer join ccavenue_response as RS on RQ.ORDER_ID=RS.ORDER_ID where RQ.NOTIFY=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "n");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				 
					count=rs.getInt(1);
					        
					
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return count;
}
  private int  viewPaymentPaypalNotification(){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count=0;
	  try
	  {
		  con = getConnection(); 
		  
		  String sql="select count(*) from paypal_request as R left outer join paypal_response as RS on RS.PAYMENT_ID=R.ID where R.NOTIFY=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "n");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				 	        
					count=rs.getInt(1);
				}	
	  }
	  catch(Exception sqe){
		System.out.println(sqe);sqe.printStackTrace();
	}finally{closeConnection(con);}
	  
		  return count;
}
  
  private boolean  isReadCCANotification(String s[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  for(int i=0;i<s.length;i++)
		  {
			  	String sql = "update ccavenue_request set NOTIFY=? where ORDER_ID=?";
			  	PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "y");
				ps.setString(2, s[i]);
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
  private boolean  isReadPaypalNotification(String s[]){
	  //System.out.println("username :  "+username +"  password :  "+password);	 
	  Connection con = null;
	  int count= 0;
	  try
	  {
		  con = getConnection(); 
		  for(int i=0;i<s.length;i++)
		  {
			  String sql = "update paypal_request set NOTIFY=? where ID=?";
			  PreparedStatement ps = con.prepareStatement(sql);
			  ps.setString(1, "y");
			  ps.setString(2, s[i]);
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
}
