package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Servlet implementation class Update_Media
 */

public class Update_Media extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 String heading="";
     String des="";
     String d ="";
     String display="";
     String user="";
     String media_name="";
     String date="";
     String ID="";
     String datec="";
     String title="";
     String alt="";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update_Media() {
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
		  if(jEventName!=null && jEventName.equals("edit")){
				 int id=Integer.parseInt(request.getParameter("id"));
				String ss= UpdateMedia(id);
				out.println(ss);
				 
			 }
			 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
             if(datec.equals(d)){
				  date=d;
			  }
			  
			  else{
				  String da[]=d.split("-");
				   date=da[2]+"-"+da[1]+"-"+da[0];
			  }
             
	            con = new MyConnection().getConnection();
	            PreparedStatement ps = con.prepareStatement("update Media_Entry set HEADING=?, MEDIA_NAME=?, DESCRIPTION=?, DISPLAY=?, USER=?, DATE=?,IMAGE_TITLE=?, IMAGE_ALT=?, MEDIA_IMAGE=? where ID=?");
	            ps.setString(1,heading );
	            ps.setString(2,media_name);
	            ps.setString(3,des );
	            ps.setString(4,display);
	            ps.setString(5,user );
	            ps.setString(6,date);
	            ps.setString(7,title);
	            ps.setString(8,alt);
	            ps.setBinaryStream(9, file.getInputStream(), (int) file.getSize());
	            ps.setString(10,ID);
	            
	            int n=ps.executeUpdate();
	            
	            if(n>0)
	            {
	            	jMessage = "success";
	            }
	           
	          response.sendRedirect("View_Media.jsp"); // request.getRequestDispatcher("View_Media.jsp").forward(request, response);
	            }
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

	protected String UpdateMedia(int id){
		
		 JSONObject json1 = new JSONObject();
			JSONArray json2 = new JSONArray();
		 try{
			
				JSONObject json = null;
			Connection con = new MyConnection().getConnection();
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery("select * from Media_Entry where ID="+id); 
			if(rs.next())
			{
				
				json=new JSONObject();
				json.put("ID",rs.getInt("ID"));
				json.put("user",rs.getString("USER"));
				json.put("heading",rs.getString("HEADING"));
				json.put("description",rs.getString("DESCRIPTION"));
				json.put("display",rs.getString("DISPLAY"));
				json.put("Date",rs.getString("DATE"));
				json.put("media_name",rs.getString("MEDIA_NAME"));
				json.put("title",rs.getString("IMAGE_TITLE"));
				json.put("alt",rs.getString("IMAGE_ALT"));
				json2.add(json);
			
			}else
			{
				/*json=new JSONObject();
				json.put("Response","No Data");
				json2.add(json);*/
				
				return "error";
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		json1.put("records", json2);
		//System.out.println(json1.toString());
		return json1.toString();

			
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
	        else if (item.getFieldName().equals("ID")) {
	        	ID = item.getString();
	        }
	        else if (item.getFieldName().equals("datec")) {
	        	 datec=item.getString();;
	        }
	        else if (item.getFieldName().equals("title")) {
	        	 title=item.getString();;
	        }
	        else if (item.getFieldName().equals("alt")) {
	        	 alt=item.getString();;
	        }
	       
	    }
	
	
	
}
