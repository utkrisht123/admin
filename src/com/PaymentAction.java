/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class ForgotPassword
 */
public class PaymentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	String driver,url,user,password;
    public PaymentAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String id=request.getParameter("id");
		 //driver= getServletContext().getInitParameter("driver");
	      //url= getServletContext().getInitParameter("url");
	      //user= getServletContext().getInitParameter("user");
	      //password= getServletContext().getInitParameter("password");
		  byte s3[]=org.apache.commons.codec.binary.Base64.decodeBase64(id);
		  String s=new String(s3);
		  String s4[]=s.split("=");
		  request.setAttribute("email_id", s4[1]);
		  for(int i=0;i<s4.length;i++)
		  System.out.println(s4[i]);
		  ArrayList<String> al=getStudent(s4[1]);
		  String studentId=al.get(0);
		  String studentName=al.get(1);
		  HttpSession session=request.getSession();
		  session.setAttribute("Student_Id", studentId);
		  session.setAttribute("Student_Name", studentName);
		  
		  
		  response.sendRedirect("http://192.168.2.21:8081/newsite/payment.jsp");
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
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
	  private ArrayList<String> getStudent(String email)
	  {
		  Connection con = null;
		  ArrayList<String> al=new ArrayList<String>();
		  
		  try
		  {
			  con = getConnection();
			  String sql = "select STUDENT_ID,STUDENT_NAME from student_login where EMAIL_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
					ps.setString(1, email);
					
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						al.add(rs.getString("STUDENT_ID"));
						al.add(rs.getString("STUDENT_NAME"));
					}
				
				
		  }
		  catch(Exception sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}finally{closeConnection(con);}
		  
			  return al;
	  }

}

