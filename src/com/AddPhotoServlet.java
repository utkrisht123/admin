/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AddPhotoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
   	  	String user=(String)session.getAttribute("user");
        String jMessage=null;
        Connection con=null;
        try {
            // Apache Commons-Fileupload library classes
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);

            if (! ServletFileUpload.isMultipartContent(request)) {
                jMessage = "sorry. No photo uploaded.";
            }
            else
            {
            // parse request
            List items = sfu.parseRequest(request);
            // get uploaded file
            FileItem file = (FileItem) items.get(0);
                        
            // Connect to Oracle
         
            con = new MyConnection().getConnection();
            int count=0;
            String sql2 = "SELECT COUNT(1) FROM user_photo WHERE USER_NAME= ?";
    		PreparedStatement ps2 = con.prepareStatement(sql2);
    		ps2.setString(1, user);
    		ResultSet rs = ps2.executeQuery();
    		while(rs.next()){
    			//System.out.println("countTID:  "+countTID);
    			count = rs.getInt(1);
    			}
    		//System.out.println("count "+ count);
    			if(count!=0)
    			{
    				    PreparedStatement ps = con.prepareStatement("update user_photo set PHOTO=? where USER_NAME=?");
    		          
    		            // size must be converted to int otherwise it results in error
    		            ps.setBinaryStream(1, file.getInputStream(), (int) file.getSize());
    		            ps.setString(2, user);
    		            int n=ps.executeUpdate();
    		           
    		            if(n>0)
    		            {
    		            	jMessage = "Photo has been updated successfully.";
    		            }
    			}
    			else
    			{
    				    PreparedStatement ps = con.prepareStatement("insert into user_photo(USER_NAME,PHOTO) values(?,?)");
    		            ps.setString(1, user);
    		            // size must be converted to int otherwise it results in error
    		            ps.setBinaryStream(2, file.getInputStream(), (int) file.getSize());
    		            int n=ps.executeUpdate();
    		            
    		            if(n>0)
    		            {
    		            	jMessage = "Photo has been changed successfully.";
    		            }
    			}
           
            }
        }
        
        catch (SQLException ex) {
            jMessage = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        }
        catch(Exception ex) {
        	jMessage = "Error --> " + ex.getMessage();
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
       request.setAttribute("jMessage", jMessage);
       request.setAttribute("jEventName", "UploadPhoto");
       request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
    } 
}
