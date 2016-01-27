package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Media_ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 String heading="";
     String des="";
     String d ="";
     String display="";
     String user="";
     String media_name="";
     String date="";
     String alt="";
     String title="";
    public Media_() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		
	
		
		 Connection con = null;	// connection to the database
		 String jMessage = "";
        FileItem file = null;
        
		  try {
	            // Apache Commons-Fileupload library classes
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            ServletFileUpload sfu  = new ServletFileUpload(factory);

	            if (! ServletFileUpload.isMultipartContent(request)) {
	            	//System.out.println("notworking");
	               jMessage = "error";
	            }
	            else
	            {
	            	//System.out.println("It working");
	            // parse request
	            List items = sfu.parseRequest(request);
	            // get uploaded file
	            Iterator<FileItem> iter = items.iterator();
              while (iter.hasNext()) {
                  FileItem fileItem = iter.next();
                  if (fileItem.isFormField()) {
                      processFormField(fileItem);
                  } else {
                  	file = fileItem;
                  }
              }
	            //FileItem file = (FileItem) items.get(0);
	                        
	            // Connect to Oracle
          	//System.out.println("des");
              String da[]=d.split("-");
			  date=da[2]+"-"+da[1]+"-"+da[0];
	            con = new MyConnection().getConnection();
	            PreparedStatement ps = con.prepareStatement("INSERT INTO Media_Entry (HEADING, MEDIA_NAME, DESCRIPTION, DISPLAY,USER,DATE,IMAGE_ALT,IMAGE_TITLE,MEDIA_IMAGE) values (?, ?, ?, ?, ?, ?, ?,?,?)");
	            ps.setString(1,heading );
	            ps.setString(2,media_name);
	            ps.setString(3,des );
	            ps.setString(4,display);
	            ps.setString(5,user );
	            ps.setString(6,date);
	            ps.setString(7,alt);
	            ps.setString(8,title);
	            ps.setBinaryStream(9, file.getInputStream(), (int) file.getSize());
	            
	            int n=ps.executeUpdate();
	            
	            if(n>0)
	            {
	            	jMessage = "success";
	            }
	            request.setAttribute("jMessage", jMessage);
	            request.setAttribute("jEventName", "entermedia");
	            request.getRequestDispatcher("Create-Media.jsp").forward(request, response);
	          
	}

		  
	  
	
		// message will be sent back to client
	
	
		// connects to the database
		


}
 
  catch(Exception ex) {
  	 ex.printStackTrace();
  	//jMessage = "Error --> " + ex.getMessage();
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
	 }
  
	  private void processFormField(FileItem item) {
	        //String na = item.getFieldName();
	        if (item.getFieldName().equals("name")) {
	        	heading = item.getString();
	        } else if (item.getFieldName().equals("editor1")) {
	        	des = item.getString();
	        } else if (item.getFieldName().equals("datepicker")) {
	            d = item.getString();
	        }
	        else if (item.getFieldName().equals("options2")) {
	        	display = item.getString();
	        }
	        else if (item.getFieldName().equals("user")) {
	        	user = item.getString();
	        }
	        else if (item.getFieldName().equals("media_name")) {
	        	media_name = item.getString();
	        }
	        else if (item.getFieldName().equals("title")) {
	        	title = item.getString();
	        }
	        else if (item.getFieldName().equals("alt")) {
	        	alt = item.getString();
	        }
	    }
}
