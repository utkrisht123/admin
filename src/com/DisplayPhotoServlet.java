/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import javax.servlet.*;
public class DisplayPhotoServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        try {
        	HttpSession session=request.getSession();
       	  	String user=(String)session.getAttribute("user");
       	  	if(user==null)
       	  		user=request.getParameter("user");
            Connection con = new MyConnection().getConnection();
            response.setContentType("image/jpeg");
            PreparedStatement ps = con.prepareStatement("select * from user_photo where USER_NAME= ?");
            ps.setString(1,user);
            ResultSet rs = ps.executeQuery();
            int count=0;
            while(rs.next())
            {
            	count=1;
            	
            Blob  b = rs.getBlob("photo");
           
            response.setContentLength( (int) b.length());
            InputStream is = b.getBinaryStream();
            OutputStream os = response.getOutputStream();
            byte buf[] = new byte[(int) b.length()];
            is.read(buf);
            os.write(buf);
            os.close();
            
            }
            
        }
        catch(Exception ex) {
             System.out.println(ex.getMessage());
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
