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
import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

public class MediaImage extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        try {
        	
       	  	String ID="";
       	  	
       	  		ID=request.getParameter("ID");
            Connection con = new MyConnection().getConnection();
            response.setContentType("image/jpeg");
            PreparedStatement ps = con.prepareStatement("select MEDIA_IMAGE from Media_Entry where ID= ?");
            ps.setString(1,ID);
            ResultSet rs = ps.executeQuery();
            int count=0;
            while(rs.next())
            {
            	count=1;
            	
            Blob  b = rs.getBlob("MEDIA_IMAGE");
           
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
