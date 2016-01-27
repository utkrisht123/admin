package com;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Servlet implementation class Page_Details
 */

public class Page_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Page_Details() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String jEventName=request.getParameter("jEventName");
		 response.setContentType("application/json");
		 PrintWriter out = response.getWriter();
		 
		  if(jEventName!=null && jEventName.equals("page")){
			
			  String ss= pageDetails();
			  out.println(ss);
		  }
		  
		  else if(jEventName!=null && jEventName.equals("student")){
			  String ss= studentDetails();
			  out.println(ss);
		  }
		  else if(jEventName!=null && jEventName.equals("video")){
			  String ss= videoDetails();
			  out.println(ss);
		  }
		  else if(jEventName!=null && jEventName.equals("free")){
			  String ss= freeDetails();
			  out.println(ss);
		  }else if(jEventName!=null && jEventName.equals("detailByFilter")){
			  String d=request.getParameter("d");
			  String d2=request.getParameter("d2");
			
			  String ss=FilterByDate(d,d2);
			  out.println(ss);
		  }else if(jEventName!=null && jEventName.equals("videoByFilter")){
			  String d=request.getParameter("d");
			  String d2=request.getParameter("d2");
			 
			  String ss=videoByDate(d,d2);
			  out.println(ss);
		  }else if(jEventName!=null && jEventName.equals("search")){
			  String d=request.getParameter("email");
			 
			  String ss=liveDemosearch(d);
			  out.println(ss);
		  }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		// TODO Auto-generated method stub
	}

	protected String pageDetails(){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select * from pages_details"); 
			if(rs.next())
			{
				do
				{
				json=new JSONObject();
				json.put("PAGE_ID",rs.getInt("ID"));
				json.put("VISITED_PAGE",rs.getString("PAGE_NAME"));
				json2.add(json);
			}while(rs.next());
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String studentDetails(){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select *,if(ALLOTED_ID is null,'Un_Info','Stu_Info') as JEVENT from ((page_visited as p right JOIN easylearning_cookie as e on p.COOKIE_ID=e.COOKIE_VALUE)LEFT JOIN student_login as s on s.STUDENT_ID=e.STUDENT_ID) left join lms_student_alloted as lsa on lsa.STUDENT_ID=s.Student_Id where e.STUDENT_ID!=3 and s.Student_Name is not null AND VISITED_PAGE like '%/7%' ORDER BY VISITED_DATE DESC limit 100");
			if(rs.next())
			{
				do
				{
				json=new JSONObject();
				json.put("Student_Name",rs.getString("Student_Name"));
				json.put("VISITED_PAGE",rs.getString("VISITED_PAGE"));
				json.put("IP_ADDRRESS",rs.getString("IP_ADDRESS"));
				json.put("Email_Id",rs.getString("Email_Id"));
				json.put("Phone",rs.getString("Phone"));
				json.put("Student_id",rs.getString("STUDENT_ID"));
				json.put("Visited_date",rs.getString("VISITED_DATE"));
				json.put("jEventName",rs.getString("JEVENT"));
				json2.add(json);
			}while(rs.next());
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String videoDetails(){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("SELECT s.ID,s.NAME,s.EMAIL, s.IP_ADDRESS,s.PHONE , s.ENTERED_DATE,if(p.PAGE_NAME is null,s.PAGE_NUMBER,p.PAGE_NAME) as PAGE_NUMBER FROM self_paced_videos_user s left join pages_details p on s.PAGE_NUMBER=p.ID ORDER BY ENTERED_DATE DESC"); 
			if(rs.next())
			{
				
				do
				{
					/*String sql = "select PAGE_NAME from pages_details where ID=? ";
				     PreparedStatement ps = con.prepareStatement(sql);
				     ps.setString(1, rs.getString("PAGE_NUMBER"));
				     ResultSet rs1=ps.executeQuery();*/
				     
				json=new JSONObject();
				json.put("ID",rs.getString("ID"));
				json.put("NAME",rs.getString("NAME"));
				json.put("EMAIL",rs.getString("EMAIL"));
				json.put("IP_ADDRESS",rs.getString("IP_ADDRESS"));
				json.put("PHONE",rs.getString("PHONE"));
				json.put("PAGE_NUMBER",rs.getString("PAGE_NUMBER"));
				json.put("ENTERED_DATE",rs.getString("ENTERED_DATE"));
				json2.add(json);
				     
			}while(rs.next());
			     
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String videoByDate(String d,String d4){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
			
			java.sql.Date d2=null;
			java.sql.Date d3=null;
			String r=null;
			String r1=null;
			String r2=null;
			String r3=null;
		 try{
			
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(d4);
			Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal2.add(Calendar.DATE, +1);
			 d2=new java.sql.Date(date.getTime());
			 d3=new java.sql.Date(cal2.getTime().getTime());
				 r="00:00:00";
				r1="00:00:00";
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			PreparedStatement ps6 = con.prepareStatement("SELECT s.ID,s.NAME,s.EMAIL, s.IP_ADDRESS,s.PHONE , s.ENTERED_DATE,if(p.PAGE_NAME is null,s.PAGE_NUMBER,p.PAGE_NAME) as PAGE_NUMBER FROM self_paced_videos_user s left join pages_details p on s.PAGE_NUMBER=p.ID  where ENTERED_DATE>=? and ENTERED_DATE<=?  ORDER BY ENTERED_DATE DESC");
			ps6.setString(1, d2+" "+r);
			ps6.setString(2, d3+" "+r1);
			ResultSet rs=ps6.executeQuery(); 
			if(rs.next())
			{
				do
				{
					/*String sql = "select PAGE_NAME from pages_details where ID=? ";
				     PreparedStatement ps = con.prepareStatement(sql);
				     ps.setString(1, rs.getString("PAGE_NUMBER"));
				     ResultSet rs1=ps.executeQuery();*/
				     
				json=new JSONObject();
				json.put("ID",rs.getString("ID"));
				json.put("NAME",rs.getString("NAME"));
				json.put("EMAIL",rs.getString("EMAIL"));
				json.put("IP_ADDRESS",rs.getString("IP_ADDRESS"));
				json.put("PHONE",rs.getString("PHONE"));
				json.put("PAGE_NUMBER",rs.getString("PAGE_NUMBER"));
				json.put("ENTERED_DATE",rs.getString("ENTERED_DATE"));
				json2.add(json);
				
			}while(rs.next());
			     
			}else
			{
				/*
				json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String freeDetails(){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select ID,STUDENT_NAME,EMAIL_ID,PHONE,SUBJECT_NAME,st.IP_ADDRESS,START_DATE,st.LP_ID,COOKIE_ID,case when ((datediff(END_DATE,sysdate()))<0) then 'expire' else datediff(END_DATE,sysdate()) end as expire from start_free_trial as st left join (select * from subjects) as sub on sub.SUBJECT_ID=st.SUBJECT_ID left join (select * from student_login) as sl on sl.STUDENT_ID=st.STUDENT_ID ORDER BY Start_DATE DESC"); 
			if(rs.next())
			{
				do
				{
				json=new JSONObject();
				json.put("ID",rs.getString("ID"));
				json.put("NAME",rs.getString("STUDENT_NAME"));
				json.put("EMAIL",rs.getString("EMAIL_ID"));
				json.put("IP_ADDRESS",rs.getString("IP_ADDRESS"));
				json.put("PHONE",rs.getString("PHONE"));
				json.put("SUBJECT_NAME",rs.getString("SUBJECT_NAME"));
				json.put("START_DATE",rs.getString("START_DATE"));
				json.put("expire",rs.getString("expire"));
				json.put("LP_ID",rs.getString("LP_ID"));
				json.put("COOKIE_ID",rs.getString("COOKIE_ID"));
				json2.add(json);
			}while(rs.next());
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String FilterByDate(String d,String d4){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
			java.sql.Date d2=null;
			java.sql.Date d3=null;
			String r=null;
			String r1=null;
			String r2=null;
			String r3=null;
		 try{
			
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(d4);
			Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal2.add(Calendar.DATE, +1);
			 d2=new java.sql.Date(date.getTime());
			 d3=new java.sql.Date(cal2.getTime().getTime());
				 r="00:00:00";
				r1="00:00:00";
					
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			PreparedStatement ps7 = con.prepareStatement("select ID,STUDENT_NAME,EMAIL_ID,PHONE,SUBJECT_NAME,st.IP_ADDRESS,START_DATE,st.LP_ID,COOKIE_ID,case when ((datediff(END_DATE,sysdate()))<0) then 'expire' else datediff(END_DATE,sysdate()) end as expire from start_free_trial as st left join (select * from subjects) as sub on sub.SUBJECT_ID=st.SUBJECT_ID left join (select * from student_login) as sl on sl.STUDENT_ID=st.STUDENT_ID where START_DATE>=? and START_DATE<=? ORDER BY Start_DATE DESC");
			ps7.setString(1, d2+" "+r);
			ps7.setString(2, d3+" "+r1);
			ResultSet rs=ps7.executeQuery(); 
			if(rs.next())
			{
				do
				{
				json=new JSONObject();
				json.put("ID",rs.getString("ID"));
				json.put("NAME",rs.getString("STUDENT_NAME"));
				json.put("EMAIL",rs.getString("EMAIL_ID"));
				json.put("IP_ADDRESS",rs.getString("IP_ADDRESS"));
				json.put("PHONE",rs.getString("PHONE"));
				json.put("SUBJECT_NAME",rs.getString("SUBJECT_NAME"));
				json.put("START_DATE",rs.getString("START_DATE"));
				json.put("expire",rs.getString("expire"));
				json.put("LP_ID",rs.getString("LP_ID"));
				json.put("COOKIE_ID",rs.getString("COOKIE_ID"));
				json2.add(json);
			}while(rs.next());
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		
		return json1.toString();

			
		 }
	
	protected String liveDemosearch(String email){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.EMAIL like ? order by QRY_DATE DESC";
			PreparedStatement st =con.prepareStatement(query);
			 st.setString(1,"%"+email+"%");
			ResultSet rs=st.executeQuery(); 
			 while (rs.next()) {
		           String id=" ";
					if(rs.getString("STUDENT_ID")!=null)
						id=rs.getString("STUDENT_ID");   
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        json=new JSONObject();
					        json.put("id",id);
					        json.put("name",rs.getString("Name"));
					        json.put("email",rs.getString("L.Email"));
					        json.put("phone",rs.getString("Phone_No"));
					        json.put("query",rs.getString("L.Query"));
					        json.put("last_query",rs.getString("Q.Query"));
					        json.put("date_time",ld_date+" "+time);
					        json.put("query_ID",rs.getString("Q_ID"));
					        json.put("E_correct",rs.getString("E_CORRECT"));
					        json.put("M_correct",rs.getString("M_CORRECT"));
					        json.put("Attended",rs.getString("ATTENDED"));
				            if(rs.getString("Q.PICKUP")==null)
				            {
				            	json.put("pickUp",rs.getString("L.PICKUP"));
				            	
				            }
				            else
				            {
				            	json.put("pickUp",rs.getString("Q.PICKUP"));
				            	
				            }
				            if(rs.getString("Q.INTEREST_LEVEL")==null)
				            	json.put("Interest",rs.getString("L.INTEREST_LEVEL"));
				            else
				            	json.put("Interest",rs.getString("Q.INTEREST_LEVEL"));
				            
				            json.put("LP_ID",rs.getString("LP_ID"));
				            json.put("Country",rs.getString("COUNTRY"));
				            json.put("course",rs.getString("COURSE"));
				            json.put("cookie_id",rs.getString("COOKIE_ID"));
				            json.put("country_code",rs.getString("L.COUNTRY_CODE"));
				            json2.add(json);;
		        }
			 json1.put("records", json2);
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return json1.toString();

			
		 }
	
}
