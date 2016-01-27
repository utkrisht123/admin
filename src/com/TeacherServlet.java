/**
 * @author Administrator Binay Gaur
 *
 */
package com;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class TeacherServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
	 doPost(request, response);
 }
 public void doPost(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
 

        String jEventName = request.getParameter("jEventName");
        //System.out.println(jEventName);
        String jMessage=null;
        StudentDAO dao = new StudentDAO();
        List<Student> list=null;
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession();
   	  	String user=(String)session.getAttribute("user");
        if(jEventName != null && jEventName.equalsIgnoreCase("View_Teacher")) // View Teacher
        {
        	 list = dao.viewStudent();
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
	        	 out.println("<tr class='odd gradeX'>");
	        	 out.println("<td>"+j+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentName()+"</td>");
	        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
	        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
	        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
	        	 out.println("<td><a href='S_Profile.jsp?student_id="+list.get(i).getStudentId()+"&batch_id="+list.get(i).getBatch()+"&subject_id="+list.get(i).getSubjectId()+"'>View</a></td>");
	        	 out.println("<td><a href='LoginServlet?jEventName=Student_Email&email="+list.get(i).getEmail()+"&batch_id="+list.get(i).getBatch()+"&name="+list.get(i).getStudentName()+"&time="+list.get(i).getStartTime()+"'>Send Next Webinar Link</a></td>");
	        	 out.println("</tr");
        	 }
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("View_TeacherBySubject")) //// View Teacher by subject
        {
        	String subjectId=request.getParameter("subject_id");
        	 list = dao.viewStudent(subjectId);
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
	        	 out.println("<tr class='odd gradeX'>");
	        	 out.println("<td>"+j+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentName()+"</td>");
	        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
	        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
	        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
	        	 out.println("<td><a href='S_Profile.jsp?student_id="+list.get(i).getStudentId()+"&batch_id="+list.get(i).getBatch()+"&subject_id="+list.get(i).getSubjectId()+"'>View</a></td>");
	        	 out.println("<td><a href='LoginServlet?jEventName=Student_Email&email="+list.get(i).getEmail()+"&batch_id="+list.get(i).getBatch()+"&name="+list.get(i).getStudentName()+"&time="+list.get(i).getStartTime()+"'>Send Next Webinar Link</a></td>");
	        	 out.println("</tr");
        	 }
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("View_TeacherBySubjectAndBatch")) // View Teacher by batch
        {
        	String subjectId=request.getParameter("subject_id");
        	String batch=request.getParameter("batch");
        	 list = dao.viewStudent(subjectId, batch);
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
	        	 out.println("<tr class='odd gradeX'>");
	        	 out.println("<td>"+j+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentName()+"</td>");
	        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
	        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
	        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
	        	 out.println("<td><a href='S_Profile.jsp?student_id="+list.get(i).getStudentId()+"&batch_id="+list.get(i).getBatch()+"&subject_id="+list.get(i).getSubjectId()+"'>View</a></td>");
	        	 out.println("<td><a href='LoginServlet?jEventName=Student_Email&email="+list.get(i).getEmail()+"&batch_id="+list.get(i).getBatch()+"&name="+list.get(i).getStudentName()+"&time="+list.get(i).getStartTime()+"'>Send Next Webinar Link</a></td>");
	        	 out.println("</tr");
        	 }
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("TeacherProfile")) // View Teacher profile
        {
        	
        	 list = dao.viewStudentProfile();
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
	        	 out.println("<tr class='odd gradeX'>");
	        	 out.println("<td>"+j+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentName()+"</td>");
	        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
	        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
	        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
	        	 out.println("<td><a href='LoginServlet?student_id="+list.get(i).getStudentId()+"&student_name="+list.get(i).getStudentName()+"&jEventName=S_Profile' target='_blank'>View Profile</a></td>");
	        	 out.println("</tr");
        	 }
        	
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("TeacherProfileBySubject")) // View Teacher profile by subject
        {
        	String subjectId=request.getParameter("subject_id");
        	 list = dao.viewStudentProfile(subjectId);
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
	        	 out.println("<tr class='odd gradeX'>");
	        	 out.println("<td>"+j+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
	        	 out.println("<td>"+list.get(i).getStudentName()+"</td>");
	        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
	        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
	        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
	        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
	        	 out.println("<td><a href='LoginServlet?student_id="+list.get(i).getStudentId()+"&student_name="+list.get(i).getStudentName()+"&jEventName=S_Profile' target='_blank'>View Profile</a></td>");
	        	 out.println("</tr");
        	 }
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("TeacherProfileBySubjectAndBatch")) //// View Teacher profile by batch
        {
        	String subjectId=request.getParameter("subject_id");
        	String batch=request.getParameter("batch");
        	
        	 list = dao.viewStudentProfile(subjectId, batch);
        	 for(int i=0;i<list.size();i++)
        	 {
        		 int j=i+1;
        		 out.println("<tr class='odd gradeX'>");
        		 out.println("<td>"+j+"</td>");
        		 out.println("<td>"+list.get(i).getStudentId()+"</td>");
        		 out.println("<td>"+list.get(i).getStudentName()+"</td>");
        		 out.println("<td>"+list.get(i).getEmail()+"</td>");
        		 out.println("<td>"+list.get(i).getPhone()+"</td>");
        		 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
        		 out.println("<td>"+list.get(i).getBatch()+"</td>");
        		 out.println("<td><a href='LoginServlet?student_id="+list.get(i).getStudentId()+"&student_name="+list.get(i).getStudentName()+"&jEventName=S_Profile' target='_blank'>View Profile</a></td>");
        		 out.println("</tr");
        	 }
 	        
        }
        // for creation of teacher
        else if(jEventName != null && jEventName.equalsIgnoreCase("T_Create"))// View Create Teacher
        {
      	  String teacherName = request.getParameter("fullname");
            String fatherName = request.getParameter("fname");
            String dob= request.getParameter("dob");
            String sex = request.getParameter("gender");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String mobile= request.getParameter("phone");
            String password=request.getParameter("rpassword");
            Teacher ts=new Teacher();
            ts.setTeacherName(teacherName);
            ts.setUser(email);
            ts.setEmail(email);
            ts.setDOB(dob);
            ts.setSex(sex);
            ts.setAddress(address);
            ts.setFatherName(fatherName);
            ts.setPassword(password);
            ts.setPhone(mobile);
            
            TeacherDAO to=new TeacherDAO();
            if(to.checkTeacherUser(email))
            {
            	
          	  jMessage = "Provided teacher email id is already exist...please choose another email id";
          	  request.setAttribute("jEventName", jEventName); 
          	  request.setAttribute("jMessage", jMessage);
          	  request.getRequestDispatcher("T_Create.jsp").forward(request, response);
          	  }
            else
            { 
            	if(to.sendTeacher(ts,user))
            	{ 
            		jMessage = "New teacher successfully created"; 
            		request.setAttribute("jEventName", jEventName);
            		request.setAttribute("jMessage", jMessage);
            		request.getRequestDispatcher("T_Create.jsp").forward(request, response);
            	}
            	else
            	{
            		jMessage = "New teacher successfully created"; 
            		request.setAttribute("jEventName", jEventName);
            		request.setAttribute("jMessage", jMessage);
            		request.getRequestDispatcher("T_Create.jsp").forward(request, response);
            	}
            }
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("U_T_Profile")) //Update Teacher Profile
        {
      	  	String teacherName = request.getParameter("t_name");
            String fatherName = request.getParameter("f_name");
            String dob= request.getParameter("dob");
            String sex = request.getParameter("sex");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String oldEmail = request.getParameter("old_email");
            String mobile= request.getParameter("phone");
            String t_id=request.getParameter("t_id");
            Teacher ts=new Teacher();
            ts.setTeacherName(teacherName);
            ts.setUser(email);
            ts.setEmail(email);
            ts.setDOB(dob);
            ts.setSex(sex);
            ts.setAddress(address);
            ts.setFatherName(fatherName);
            ts.setTeacherId(t_id);
            ts.setPhone(mobile);
            TeacherDAO to=new TeacherDAO();
          
       	 List<Teacher> list3 = new ArrayList<Teacher>();
       	 
       	 
       	 list3=to.viewTeacherAllotedBatch(t_id);
       	 request.setAttribute("teacherName", teacherName);
       	 request.setAttribute("teacherId", t_id);
       	 request.setAttribute("fatherName", fatherName);
       	 request.setAttribute("dob", dob);
       	 request.setAttribute("sex", sex);
       	 request.setAttribute("address", address);
       	 request.setAttribute("email", email);
       	 request.setAttribute("mobile", mobile);
       	 request.setAttribute("data", list3);
            
            if(to.checkTeacherUser(email,oldEmail)){
            	
          	  jMessage = "Provided teacher email id is already exist...please choose another email id";
          	  request.setAttribute("jEventName", jEventName);
          	  request.setAttribute("jMessage", jMessage);
          	  request.getRequestDispatcher("Teacher_Detail.jsp").forward(request, response); 
          	  }
            else{ 
            		if(to.updateTeacher(ts,user))
            		{ 
	            		jMessage = "Teacher has been updated successfully."; 
	            		request.setAttribute("jEventName", jEventName);
	            		request.setAttribute("jMessage", jMessage);
	          	  		request.getRequestDispatcher("Teacher_Detail.jsp").forward(request, response);
          	  		}
            		else
            		{
            			jMessage = "Teacher has not updated please contact to technical department"; 
                		request.setAttribute("jEventName", jEventName);
                		request.setAttribute("jMessage", jMessage);
              	  		request.getRequestDispatcher("Teacher_Detail.jsp").forward(request, response);
            		}
            	}
            	
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("D_T_Batch")) //Delete Teacher Batch
        {
        	
            	String t_id=request.getParameter("t_id");
            	String batch=request.getParameter("batch");
            	//System.out.println(teacherId);
       	 		List<Teacher> list2 = new ArrayList<Teacher>();
       	 		List<Teacher> list3 = new ArrayList<Teacher>();
       	 		TeacherDAO tdao=new TeacherDAO();
       	 		list2=tdao.viewTeacherDetail(t_id);
       	 		list3=tdao.viewTeacherAllotedBatch(t_id);
       	 
       	 		String teacherName = list2.get(0).getTeacherName();
       	 		String fatherName = list2.get(0).getFatherName();
       	 		String dob= list2.get(0).getDOB();
       	 		String sex = list2.get(0).getSex();
       	 		String address = list2.get(0).getAddress();
       	 		String email = list2.get(0).getEmail();
       	 		String mobile= list2.get(0).getPhone();
       	 		request.setAttribute("jEventName", jEventName);
       	 		request.setAttribute("teacherName", teacherName);
       	 		request.setAttribute("teacherId", t_id);
       	 		request.setAttribute("fatherName", fatherName);
       	 		request.setAttribute("dob", dob);
       	 		request.setAttribute("sex", sex);
       	 		request.setAttribute("address", address);
       	 		request.setAttribute("email", email);
       	 		request.setAttribute("mobile", mobile);
       	 		request.setAttribute("data", list3);
       	 		request.setAttribute("data", list3);
       	 		if(tdao.deleteTeacherBatch(t_id,batch,user))
          		  	jMessage = "Teacher "+batch+" deleted successfully"; 
       	 		else
          		  	jMessage = "Teacher "+batch+" has not deleted. Please contact to technical department"; 
          	  
    		 request.setAttribute("jEventName", jEventName);
    		 request.setAttribute("jMessage", jMessage);
  	  		 request.getRequestDispatcher("Teacher_Detail.jsp").forward(request, response);
        	 
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("Batch")) //Get Batch
        {
        	
        	 ArrayList<String> al=new ArrayList<String>();
        	 al=new LiveDemoDAO().getBatch();
        	 out.println("<select class='form-control select2_category' name='batch' id='batch'>");
        	 for(int i=0;i<al.size();i++)
        	 {
        	  out.println("<option value='"+al.get(i)+"'>"+al.get(i)+"</option>");
        	 }
        	 out.println("</select>");
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("BatchBySubject"))// Get Batch By Subject
        {
        	String subjectId=request.getParameter("subject_id");
        	//System.out.println(subjectId);
        	 ArrayList<String> al=new ArrayList<String>();
        	 al=new LiveDemoDAO().getBatch(subjectId);
        	 out.println("<select class='form-control select2_category' name='batch' id='batch' >");
        	 for(int i=0;i<al.size();i++)
        	 {
        	  out.println("<option value='"+al.get(i)+"'>"+al.get(i)+"</option>");
        	 }
        	 out.println("</select>");
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("BatchDetailBySubjectAndBatch")) // Batch Detail BySubject And Batch
        {
        	String subjectId=request.getParameter("subject_id");
        	String batchId=request.getParameter("batch");
        	//System.out.println(subjectId);
        	//System.out.println(batchId);
        	 ArrayList<String> al=new ArrayList<String>();
        	 TeacherDAO to=new TeacherDAO();
        	 al=to.viewBatchDetail(subjectId, batchId);		 
        	 out.println(al.get(0)+"abczxy"+al.get(1)+"abczxy"+al.get(2)+"abczxy"+al.get(3)+"abczxy"+al.get(4)+"abczxy"+al.get(5));
 	        
        }
        else if(jEventName != null && jEventName.equalsIgnoreCase("Teacher_Detail"))// get Teacher Detail
        {
        	String teacherId = request.getParameter("teacher_id");
        	//System.out.println(teacherId);
        	 List<Teacher> list2 = new ArrayList<Teacher>();
        	 List<Teacher> list3 = new ArrayList<Teacher>();
        	 TeacherDAO tdao=new TeacherDAO();
        	 list2=tdao.viewTeacherDetail(teacherId);
        	 list3=tdao.viewTeacherAllotedBatch(teacherId);
        	 
      	  	String teacherName = list2.get(0).getTeacherName();
            String fatherName = list2.get(0).getFatherName();
            String dob= list2.get(0).getDOB();
            String sex = list2.get(0).getSex();
            String address = list2.get(0).getAddress();
            String email = list2.get(0).getEmail();
            String mobile= list2.get(0).getPhone();
            request.setAttribute("jEventName", jEventName);
          	request.setAttribute("teacherName", teacherName);
          	request.setAttribute("teacherId", teacherId);
          	request.setAttribute("fatherName", fatherName);
          	request.setAttribute("dob", dob);
          	request.setAttribute("sex", sex);
          	request.setAttribute("address", address);
          	request.setAttribute("email", email);
          	request.setAttribute("mobile", mobile);
          	request.setAttribute("data", list3);
          	request.getRequestDispatcher("Teacher_Detail.jsp").forward(request, response);
            
 	        
        }
        
 }

}
