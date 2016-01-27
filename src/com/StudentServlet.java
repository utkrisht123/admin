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
public class StudentServlet extends HttpServlet  {

	public StudentServlet() {
		super();
	}
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
	        Student sd = null;
	        HttpSession session=request.getSession();
	   	  String user=(String)session.getAttribute("user");
	        if(jEventName != null && jEventName.equalsIgnoreCase("View_Student")) // View Student
	        {
	        	 list = dao.viewStudent();
	        	 for(int i=0;i<list.size();i++)
	        	 {
	        		 int j=i+1;
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
		        	 out.println("<td><a class='fancybox fancybox.iframe' style='color: purple;' href='LoginServlet?jEventName=Stu_Info&ID="+list.get(i).getStudentId()+"'>"+list.get(i).getStudentName()+"</a></td>");
		        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
		        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
		        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
		        	 if(list.get(i).getCertificateId()==null)
		        		 out.println("<td><a href='StudentAction?jEventName=Generate_Certificate&student_id="+list.get(i).getStudentId()+"&subject_id="+list.get(i).getSubjectId()+"'>Generate Certificate</a></td>");
		        	 else
		        		 out.println("<td>"+list.get(i).getCertificateId()+"</td>");
		        	 out.println("<td><a href='S_Allot.jsp?s_id="+list.get(i).getStudentId()+"'>Allot Other Batch</a></td>");
		        	 out.println("<td><a href='StudentAction?jEventName=Student_Detail&student_id="+list.get(i).getStudentId()+"'>View Detail</a></td>");
		        	 out.println("</tr");
	        	 }
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("View_StudentBySubject"))// // View Student by course 
	        {
	        	String subjectId=request.getParameter("subject_id");
	        	 list = dao.viewStudent(subjectId);
	        	 for(int i=0;i<list.size();i++)
	        	 {
	        		 int j=i+1;
		        	 out.println("<tr class='odd gradeX'>");
		        	 out.println("<td>"+j+"</td>");
		        	 out.println("<td>"+list.get(i).getStudentId()+"</td>");
		        	 out.println("<td><a class='fancybox fancybox.iframe' style='color: purple;' href='LoginServlet?jEventName=Stu_Info&ID="+list.get(i).getStudentId()+"'>"+list.get(i).getStudentName()+"</a></td>");
		        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
		        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
		        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
		        	 if(list.get(i).getCertificateId()==null)
		        		 out.println("<td><a href='StudentAction?jEventName=Generate_Certificate&student_id="+list.get(i).getStudentId()+"&subject_id="+list.get(i).getSubjectId()+"'>Generate Certificate</a></td>");
		        	 else
		        		 out.println("<td>"+list.get(i).getCertificateId()+"</td>");
		        	 out.println("<td><a href='S_Allot.jsp?s_id="+list.get(i).getStudentId()+"'>Allot Other Batch</a></td>");
		        	 out.println("<td><a href='StudentAction?jEventName=Student_Detail&student_id="+list.get(i).getStudentId()+"'>View Detail</a></td>");
		        	 out.println("</tr");
	        	 }
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("View_StudentBySubjectAndBatch")) // // View Student by batch 
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
		        	 out.println("<td><a class='fancybox fancybox.iframe' style='color: purple;' href='LoginServlet?jEventName=Stu_Info&ID="+list.get(i).getStudentId()+"'>"+list.get(i).getStudentName()+"</a></td>");
		        	 out.println("<td>"+list.get(i).getEmail()+"</td>");
		        	 out.println("<td>"+list.get(i).getPhone()+"</td>");
		        	 out.println("<td>"+list.get(i).getSubjectName()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatch()+"</td>");
		        	 out.println("<td>"+list.get(i).getBatchType()+"</td>");
		        	 if(list.get(i).getCertificateId()==null)
		        		 out.println("<td><a href='StudentAction?jEventName=Generate_Certificate&student_id="+list.get(i).getStudentId()+"&subject_id="+list.get(i).getSubjectId()+"'>Generate Certificate</a></td>");
		        	 else
		        		 out.println("<td>"+list.get(i).getCertificateId()+"</td>");
		        	 out.println("<td><a href='S_Allot.jsp?s_id="+list.get(i).getStudentId()+"'>Allot Other Batch</a></td>");
		        	 out.println("<td><a href='StudentAction?jEventName=Student_Detail&student_id="+list.get(i).getStudentId()+"'>View Detail</a></td>");
		        	 out.println("</tr");
	        	 }
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("StudentProfile")) // // View Student Profiles 
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
	        else if(jEventName != null && jEventName.equalsIgnoreCase("Unalloted_Student"))  // View Unalloted Student
	        {
	        	
	        	 list = dao.viewUnallotedStudent();
	        	  request.setAttribute("jEventName", jEventName);
	        	  request.setAttribute("data", list);
	        	  request.getRequestDispatcher("Unalloted_Student.jsp").forward(request, response);
	        	
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("StudentProfileBySubject")) // View Student profile by subject
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
	        else if(jEventName != null && jEventName.equalsIgnoreCase("StudentProfileBySubjectAndBatch"))// View Student profile by batch
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
	        else if(jEventName != null && jEventName.equalsIgnoreCase("S_Create")) // Create Student
	        {
	        	 String studentName = request.getParameter("fullname");
	             String email = request.getParameter("email");
	             String mobile= request.getParameter("phone");
	             String password = request.getParameter("password");
	             
	             
	             sd = new Student();
	             sd.setPassword(password);
	             sd.setStudentName(studentName);
	             sd.setEmail(email);
	             sd.setPhone(mobile);
	             if(dao.checkStudentUser(email))
	             {
	           	  jMessage = "Provided Student Email Id is already exist...please enter another email id";
	           	 
	           	  }
	             else
	             { 
	            	 if(dao.sendStudent(sd,user))
	            	 { 
	            		 jMessage = "Student created successfully";
	            		 new SendEmail().sendRegisterStudent(email, studentName,password);
	            	 }
	            		 
	            	 else
	            	 {
	            		 jMessage = "Student has not created.Please contact to technical department.";
	            	 }
	             }
	             	request.setAttribute("jEventName", jEventName);
	           	  	request.setAttribute("jMessage", jMessage);
	           	  	request.getRequestDispatcher("S_Create.jsp").forward(request, response);
		        
	 	        
	        }
	       
	        else if(jEventName != null && jEventName.equalsIgnoreCase("U_S_Profile"))  // Update Student Profile
	        {
	        	 String studentName = request.getParameter("s_name");
	             String email = request.getParameter("email");
	             String oldEmail = request.getParameter("old_email");
	             String mobile= request.getParameter("phone");
	             String s_id = request.getParameter("s_id");
	             
	             
	             sd = new Student();
	             sd.setStudentId(s_id);
	             sd.setStudentName(studentName);
	             sd.setEmail(email);
	             sd.setPhone(mobile);
	             if(dao.checkStudentUser(email,oldEmail))
	             {
	           	  jMessage = "Provided Student Email Id is already exist...please enter another email id";
	           	  }
	             else
	             { 
	            	 if(dao.updateStudent(sd,user))
	            	 { 
	            		 	jMessage = "Student profile has been updated successfully";
	           	  	 }
	            	 else
	            	 {
	            		 jMessage = "Student profile has not updated.Please contact to technical department";
	            	 }
	             }
	             	List<Student> list3 = new ArrayList<Student>();
	             	StudentDAO sdao=new StudentDAO();
	        	
	             	list3=sdao.viewStudentAllotedBatch(s_id);
	            	request.setAttribute("data", list3);
	             	request.setAttribute("studentName",studentName);
	             	request.setAttribute("studentId",s_id);
	             	request.setAttribute("email",email);
	             	request.setAttribute("mobile",mobile);
	             	request.setAttribute("jEventName", jEventName); 
	           	  	request.setAttribute("jMessage", jMessage);
	           	  	request.getRequestDispatcher("Student_Detail.jsp").forward(request, response);
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("LMS_Link")) // // Send LMS Link to student
	        {
	        	 String studentName = request.getParameter("name");
	             String email = request.getParameter("email");
	             String studentId = request.getParameter("s_id");
	             String phone = request.getParameter("phone");
	            
	             SendEmail s=new SendEmail();
	           
	            	 if(s.sendLMSUrl(email, studentName))
	            	 { 
	            		 	jMessage = "LMS link has been send successfully.";
	           	  	 }
	            	 else
	            	 {
	            		 jMessage = "LMS link has not sent";
	            	 }
	             
	             	List<Student> list3 = new ArrayList<Student>();
	             	StudentDAO sdao=new StudentDAO();
	        	
	             	list3=sdao.viewStudentAllotedBatch(studentId);
	            	request.setAttribute("data", list3);
	             	request.setAttribute("studentName",studentName);
	             	request.setAttribute("studentId",studentId);
	             	request.setAttribute("email",email);
	             	request.setAttribute("mobile",phone);
	             	request.setAttribute("jEventName", jEventName); 
	           	  	request.setAttribute("jMessage", jMessage);
	           	  	request.getRequestDispatcher("Student_Detail.jsp").forward(request, response);
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("D_S_Batch"))  // Delete Student Batch
	        {
	        	String studentId = request.getParameter("s_id");
	        	String batch = request.getParameter("batch");
	        	//System.out.println(teacherId);
	        	 List<Student> list2 = new ArrayList<Student>();
	        	 List<Student> list3 = new ArrayList<Student>();
	        	 StudentDAO sdao=new StudentDAO();
	        	 list2=sdao.viewStudentDetail(studentId);
	        	 list3=sdao.viewStudentAllotedBatch(studentId);
	        	 
	      	  	String studentName = list2.get(0).getStudentName();
	            String email = list2.get(0).getEmail();
	            String mobile= list2.get(0).getPhone();
	            request.setAttribute("jEventName", jEventName);
	          	request.setAttribute("studentName", studentName);
	          	request.setAttribute("studentId", studentId);
	          	request.setAttribute("email", email);
	          	request.setAttribute("mobile", mobile);
	          	request.setAttribute("data", list3);
	          	if(sdao.deleteStudentBatch(studentId,batch,user))
          		  	jMessage = "Student "+batch+" deleted successfully"; 
       	 		else
          		  	jMessage = "Student "+batch+" has not deleted. Please contact to technical department"; 
	        	request.setAttribute("jMessage", jMessage);
	          	request.getRequestDispatcher("Student_Detail.jsp").forward(request, response);
	            
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("Student_Detail"))// get Student Detail
	        {
	        	String studentId = request.getParameter("student_id");
	        	//System.out.println(teacherId);
	        	 List<Student> list2 = new ArrayList<Student>();
	        	 List<Student> list3 = new ArrayList<Student>();
	        	 StudentDAO sdao=new StudentDAO();
	        	 list2=sdao.viewStudentDetail(studentId);
	        	 list3=sdao.viewStudentAllotedBatch(studentId);
	        	 
	      	  	String studentName = list2.get(0).getStudentName();
	            String email = list2.get(0).getEmail();
	            String mobile= list2.get(0).getPhone();
	            request.setAttribute("jEventName", jEventName);
	          	request.setAttribute("studentName", studentName);
	          	request.setAttribute("studentId", studentId);
	          	request.setAttribute("email", email);
	          	request.setAttribute("mobile", mobile);
	          	request.setAttribute("data", list3);
	          	request.getRequestDispatcher("Student_Detail.jsp").forward(request, response);
	            
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("ChangePassword"))// Change Password
	        {
	        	String studentId = request.getParameter("student_id");
	        	String password = request.getParameter("rpassword");
	        	//System.out.println(teacherId);
	        	StudentDAO sdao=new StudentDAO();
	        	if(sdao.updateStudentPassword(studentId, password))
	        		jMessage="Student password has been changed successfully.";
	        	else
	        		jMessage="Student password has not changed.Please contact to technical department.";
	            request.setAttribute("jEventName", jEventName);
	            request.setAttribute("jMessage", jMessage);
	          	request.setAttribute("studentId", studentId);
	          	request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
	            
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("Batch"))// get Batch
	        {
	        	
	        	 ArrayList<String> al=new ArrayList<String>();
	        	 al=new LiveDemoDAO().getBatch();
	        	 out.println("<select class='form-control select2_category' name='batch' id='batch'>");
	        	 out.println("<option value=''>Select Batch</option>");
	        	 for(int i=0;i<al.size();i++)
	        	 {
	        	  out.println("<option value='"+al.get(i)+"'>"+al.get(i)+"</option>");
	        	 }
	        	 out.println("</select>");
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("BatchBySubject")) // Get Batch by Subject
	        {
	        	String subjectId=request.getParameter("subject_id");
	        	//System.out.println(subjectId);
	        	 ArrayList<String> al=new ArrayList<String>();
	        	 al=new LiveDemoDAO().getBatch(subjectId);
	        	 out.println("<select class='form-control select2_category' name='batch' id='batch' >");
	        	 out.println("<option value=''>Select Batch</option>");
	        	 for(int i=0;i<al.size();i++)
	        	 {
	        	  out.println("<option value='"+al.get(i)+"'>"+al.get(i)+"</option>");
	        	 }
	        	 out.println("</select>");
	 	        
	        }
	        else if(jEventName != null && jEventName.equalsIgnoreCase("Generate_Certificate"))// CGenerate Certificate
	        {
	        	String studentId = request.getParameter("student_id");
	        	String subjectId = request.getParameter("subject_id");
	        	//System.out.println(teacherId);
	        	StudentDAO sdao=new StudentDAO();
	        	if(sdao.checkCertificate(studentId, subjectId))
	        	{
	        		jMessage="Certificate already exist for student id "+studentId;
		            request.setAttribute("jEventName", jEventName);
		            request.setAttribute("jMessage", jMessage);
		          	request.getRequestDispatcher("View_Student.jsp").forward(request, response);
	        	}
	        	else
	        	{
	        		List<Student> al=new ArrayList<Student>();
	        		al=sdao.getStudentDetail(studentId, subjectId);
	        		String subjectName=al.get(0).getSubjectName();
	        		String studentName=al.get(0).getStudentName();
	        		String batch=al.get(0).getBatch();
	        		String certificateId=("C"+batch+studentId).toUpperCase();
	        		if(sdao.generateCertificate(studentId, subjectId, certificateId))
	        		{
	        			
	        			jMessage="Certificate of student "+studentName+" has been successfully generated.Certificate Id is "+certificateId;
			            request.setAttribute("jEventName", jEventName);
			            request.setAttribute("jMessage", jMessage);
			          	request.setAttribute("studentId", studentId);
			          	request.getRequestDispatcher("View_Student.jsp").forward(request, response);
	        		}
	        		else
	        		{
	        			jMessage="Certificate has not generated for student "+studentName+" please contact technical department";
			            request.setAttribute("jEventName", jEventName);
			            request.setAttribute("jMessage", jMessage);
			          	request.getRequestDispatcher("View_Student.jsp").forward(request, response);
	        		}
	        		
	        	}
	        		
	        		
	            
	 	        
	        }
	 }
}