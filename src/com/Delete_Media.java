package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Delete_Media
 */
public class Delete_Media extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete_Media() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ID=request.getParameter("ID");
		String ss=Delete_media(ID);
		//request.setAttribute("jMessage", ss);
       // request.setAttribute("jEventName", "entermedia");
        response.sendRedirect("View_Media.jsp");//request.getRequestDispatcher("Create-Media.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected String Delete_media(String ID){
		System.out.println("ID");
		Connection con=null;
		String jMessage ="";
		try{
		  con=new MyConnection().getConnection();
         PreparedStatement ps = con.prepareStatement("Delete from Media_Entry where ID=?");
         ps.setString(1,ID );
        int n= ps.executeUpdate();
         if(n>0)
         {
         	jMessage = "success";
         }
         
		}
		catch(Exception ex) {
		  	 ex.printStackTrace();
		  	jMessage = "error";
		  }
		  finally {
		      if (con != null) {
		          // closes the database connection
		          try {
		              con.close();
		          } catch (SQLException ex) {
		              ex.printStackTrace();
		          }
		      }
		      
		  }
		return jMessage;
	}
}
