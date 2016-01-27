package com;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Servlet implementation class Press
 */

public class Press extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Press() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String jEventName=request.getParameter("jEventName");	
		 response.setContentType("application/json");
		  PrintWriter out = response.getWriter();
	 if(jEventName!=null && jEventName.equals("view")){
			  String d =showPress();
			  out.println(d);
			  
		  }
	 
	 else if(jEventName!=null && jEventName.equals("delete")){
		 String id=request.getParameter("id");
		 
		 deletePress(id);
		 
	 }
	 
	 else if(jEventName!=null && jEventName.equals("edit")){
		 int id=Integer.parseInt(request.getParameter("id"));
		String ss= editPress(id);
		
		out.println(ss);
		 
	 }
	 
	
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String jEventName=request.getParameter("jEventName");
		 
		  if(jEventName!=null && jEventName.equals("enterpress")){
			  String heading=request.getParameter("name");
			  String des=request.getParameter("editor1");
			  String d =request.getParameter("datepicker");
			  String display=request.getParameter("options2");
			  String user=request.getParameter("user");
			  
			  String da[]=d.split("-");
			  String date=da[2]+"-"+da[1]+"-"+da[0];
			  String ss= InsertPress(heading,des,date,display,user);
			 request.setAttribute("jEventName", "enterpress");
			 request.setAttribute("jMessage", ss); 
       	  request.getRequestDispatcher("enter-press.jsp").forward(request, response);
		  }
		  
		  else if(jEventName!=null && jEventName.equals("updatePress")){
				 String date="";
			  	String id=request.getParameter("id");
				 String heading=request.getParameter("name");
				  String des=request.getParameter("editor1");
				  String d =request.getParameter("datepicker");
				  String display=request.getParameter("options2");
				  String user=request.getParameter("user");
				  String datec=request.getParameter("datec");
				  if(datec.equals(d)){
					  date=d;
				  }
				  
				  else{
					  String da[]=d.split("-");
					   date=da[2]+"-"+da[1]+"-"+da[0];
				  }
				  
				String ss= updatePress(heading,des,date,display,user,id);
				
				if(ss.equals("success")){
					 response.sendRedirect("view_press.jsp");
				}
				
				else if(ss.equals("error")){
					 request.setAttribute("jEventName", "editpress");
					 request.setAttribute("jMessage", ss); 
					 request.setAttribute("ide", id); 
		       	  request.getRequestDispatcher("edit-press.jsp").forward(request, response);
				}
				 
			 }
	}

	private String InsertPress(String heading,String des,String date,String display,String user){
		try{
		Connection con = new MyConnection().getConnection(); 
		String sql="insert into press_release_entry (Date,heading,description,display,user) values(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,date);
		ps.setString(2,heading);
		ps.setString(3,des);
		ps.setString(4,display);
		ps.setString(5,user);
		ps.executeUpdate();
		return "success";
		}
		catch(Exception e){
			
			System.out.println(e);
			return "error";
		}
		
	}
	
	 private String showPress(){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select * from press_release_entry"); 
			if(rs.next())
			{
				do
				{
				json=new JSONObject();
				json.put("ID",rs.getInt("ID"));
				json.put("user",rs.getString("user"));
				json.put("heading",rs.getString("heading"));
				json.put("description",rs.getString("description"));
				json.put("display",rs.getString("display"));
				json.put("Date",rs.getString("Date"));
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
	 
	private void  deletePress(String id){
		try{
			
			Connection con = new MyConnection().getConnection();
			String sql="delete from press_release_entry where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			}
			catch(Exception e){
				System.out.println(e);
			}
		 
	 }
	
	 private String editPress(int id){
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select * from press_release_entry where id="+id); 
			if(rs.next())
			{
				 
				 
				do
				{
				json=new JSONObject();
				json.put("ID",rs.getInt("ID"));
				json.put("user",rs.getString("user"));
				json.put("heading",rs.getString("heading"));
				json.put("description",rs.getString("description"));
				json.put("display",rs.getString("display"));
				json.put("Date",rs.getString("Date"));
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
		System.out.println(json1.toString());
		return json1.toString();

			
		 }
	 

	private String  updatePress(String heading,String des,String date,String display,String user,String id){
		try{
			Connection con = new MyConnection().getConnection();
			String sql=" update press_release_entry SET Date=?, heading=?, description=?, display=?, user=? where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, heading);
			ps.setString(3, des);
			ps.setString(4, display);
			ps.setString(5, user);
			ps.setInt(6, Integer.parseInt(id));
			ps.executeUpdate();
			return "success";
			}
			catch(Exception e){
				System.out.println(e);
				return "error";
			}
		 
	 }

	
}
