/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
public class StudentDAO {

	Connection connection;
	PreparedStatement ps;
	public StudentDAO() {
		// TODO Auto-generated constructor stub
	}
	private static Connection getConnection() 
            throws SQLException, ClassNotFoundException
    {
        Connection con =new MyConnection().getConnection();
        return con;
    }
	public List<Student> viewStudent()
	{
    String query = "SELECT M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID,B.BATCH_TYPE,B.START_TIME,CERTIFICATE_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN lms_batch as B ON B.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=B.SUBJECT_ID left outer join (select * from student_certificate group by STUDENT_ID) as C on C.STUDENT_ID=A.STUDENT_ID";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            sd = new Student();
          
            sd.setStudentId(rs.getString("M.STUDENT_ID"));
            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
            sd.setEmail(rs.getString("M.EMAIL_ID"));
            sd.setPhone(rs.getString("M.PHONE"));
            sd.setBatch(rs.getString("A.BATCH_ID"));
            sd.setBatchType(rs.getString("B.BATCH_TYPE"));
            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
            sd.setStartTime(rs.getString("B.START_TIME"));
		    sd.setCertificateId(rs.getString("CERTIFICATE_ID"));     
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> viewUnallotedStudent()
	{
    String query = "SELECT * FROM student_login WHERE STUDENT_ID NOT IN (SELECT STUDENT_ID FROM lms_student_alloted)";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            sd = new Student();
          
            sd.setStudentId(rs.getString("STUDENT_ID"));
            sd.setStudentName(rs.getString("STUDENT_NAME"));
            sd.setEmail(rs.getString("EMAIL_ID"));
            sd.setPhone(rs.getString("PHONE"));
		           
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> getStudent()
	{
    String query = "SELECT STUDENT_ID,STUDENT_NAME,EMAIL_ID FROM student_login WHERE STUDENT_ID";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            sd = new Student();
          
            sd.setStudentId(rs.getString("STUDENT_ID"));
            sd.setStudentName(rs.getString("STUDENT_NAME"));
            sd.setEmail(rs.getString("EMAIL_ID"));
		           
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> viewStudent(String subjectId)
	{
    String query = "SELECT M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID,B.BATCH_TYPE,B.START_TIME,CERTIFICATE_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN lms_batch as B ON B.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=B.SUBJECT_ID left outer join (select * from student_certificate group by STUDENT_ID) as C on C.STUDENT_ID=A.STUDENT_ID WHERE S.SUBJECT_ID=?";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, subjectId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            sd = new Student();
            		sd.setStudentId(rs.getString("M.STUDENT_ID"));
		            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
		            sd.setEmail(rs.getString("M.EMAIL_ID"));
		            sd.setPhone(rs.getString("M.PHONE"));
		            sd.setBatch(rs.getString("A.BATCH_ID"));
		            sd.setBatchType(rs.getString("B.BATCH_TYPE"));
		            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
		            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
		            sd.setStartTime(rs.getString("B.START_TIME"));
		            sd.setCertificateId(rs.getString("CERTIFICATE_ID"));     
		           
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> viewStudent(String subjectId,String batch)
	{
    String query = "SELECT M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID,B.BATCH_TYPE,B.START_TIME,CERTIFICATE_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN lms_batch as B ON B.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=B.SUBJECT_ID left outer join (select * from student_certificate group by STUDENT_ID) as C on C.STUDENT_ID=A.STUDENT_ID WHERE A.BATCH_ID=? AND S.SUBJECT_ID=?";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, batch);
        ps.setString(2, subjectId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            sd = new Student();
          
            sd.setStudentId(rs.getString("M.STUDENT_ID"));
            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
            sd.setEmail(rs.getString("M.EMAIL_ID"));
            sd.setPhone(rs.getString("M.PHONE"));
            sd.setBatch(rs.getString("A.BATCH_ID"));
            sd.setBatchType(rs.getString("B.BATCH_TYPE"));
            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
            sd.setStartTime(rs.getString("B.START_TIME"));
            sd.setCertificateId(rs.getString("CERTIFICATE_ID"));     
		           
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> viewStudentProfile()
	{
		    String query = "SELECT M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN subjects as S on S.SUBJECT_ID=A.SUBJECT_ID ";
		    List<Student> list = new ArrayList<Student>();
		    Student sd = null;
		    try {
		        connection = getConnection();
		        ps = connection.prepareStatement(query);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		        	
		            sd = new Student();
		          
		            sd.setStudentId(rs.getString("M.STUDENT_ID"));
		            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
		            sd.setEmail(rs.getString("M.EMAIL_ID"));
		            sd.setPhone(rs.getString("M.PHONE"));
		            sd.setBatch(rs.getString("A.BATCH_ID"));
		            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
		            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
		    
				           
		            list.add(sd);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }finally
		    {
		        try {
		            if(ps != null)
		                ps.close();
		            if(connection != null)
		                connection.close();
		            } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return list;
		}	
	public List<Student> viewStudentProfile(String subjectId)
	{
		    String query = "SELECT distinct M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN subjects as S on S.SUBJECT_ID=A.SUBJECT_ID WHERE S.SUBJECT_ID=?";
		    List<Student> list = new ArrayList<Student>();
		    Student sd = null;
		    try {
		        connection = getConnection();
		        ps = connection.prepareStatement(query);
		      
		        ps.setString(1, subjectId);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		        	
		            sd = new Student();
		          
		            sd.setStudentId(rs.getString("M.STUDENT_ID"));
		            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
		            sd.setEmail(rs.getString("M.EMAIL_ID"));
		            sd.setPhone(rs.getString("M.PHONE"));
		            sd.setBatch(rs.getString("A.BATCH_ID"));
		            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
		            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
				           
		            list.add(sd);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }finally
		    {
		        try {
		            if(ps != null)
		                ps.close();
		            if(connection != null)
		                connection.close();
		            } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return list;
		}	
	public List<Student> viewStudentProfile(String subjectId,String batch)
	{
		    String query = "SELECT distinct M.STUDENT_ID,M.STUDENT_NAME,M.EMAIL_ID,M.PHONE,S.SUBJECT_ID,S.SUBJECT_NAME,A.BATCH_ID FROM student_login as M join lms_student_alloted as A on A.STUDENT_ID=M.STUDENT_ID JOIN subjects as S on S.SUBJECT_ID=A.SUBJECT_ID WHERE A.BATCH_ID=? AND S.SUBJECT_ID=?";
		    List<Student> list = new ArrayList<Student>();
		    Student sd = null;
		    try {
		        connection = getConnection();
		        ps = connection.prepareStatement(query);
		        ps.setString(1, batch);
		        ps.setString(2, subjectId);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		        	
		            sd = new Student();
		          
		            sd.setStudentId(rs.getString("M.STUDENT_ID"));
		            sd.setStudentName(rs.getString("M.STUDENT_NAME"));
		            sd.setEmail(rs.getString("M.EMAIL_ID"));
		            sd.setPhone(rs.getString("M.PHONE"));
		            sd.setBatch(rs.getString("A.BATCH_ID"));
		            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));
		            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
				           
		            list.add(sd);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }finally
		    {
		        try {
		            if(ps != null)
		                ps.close();
		            if(connection != null)
		                connection.close();
		            } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return list;
		}		
	public boolean checkStudentUser(String email){
		Connection connection = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			connection = getConnection(); 
			String sql = "SELECT COUNT(1) FROM student_login WHERE EMAIL_ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
		if(countTID != 0)return true;else return false;
	  }
	public boolean checkStudentUser(String email,String oldEmail){
		Connection connection = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			connection = getConnection(); 
			String sql = "SELECT COUNT(1) FROM student_login WHERE EMAIL_ID = ? and EMAIL_ID!=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, oldEmail);
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println("countTID:  "+countTID);
				countTID = rs.getInt(1);
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
		if(countTID != 0)return true;else return false;
	  }
	public boolean sendStudent(Student sd,String user){	
		  //System.out.println("list :  "+list);
		Connection connection = null;
		int value = -1;
		//int n=-1;
		try{
			connection = getConnection(); 
			String sql = "INSERT INTO student_login(STUDENT_NAME,STUDENT_PASSWORD,EMAIL_ID,PHONE) VALUES(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			
				ps.setString(1, sd.getStudentName()); 
				ps.setString(2, sd.getPassword());
				ps.setString(3, sd.getEmail());
				ps.setString(4, sd.getPhone());
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = connection.prepareStatement(sql2);
					ps2.setString(1, user);
					ps2.setString(2, "CS");
					ps2.executeUpdate();	
				}
		}catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e);e.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(value != -1){return true;}else return false;
	  }
	public boolean updateStudent(Student sd,String user){	
		  //System.out.println("list :  "+list);
		Connection connection = null;
		int value = -1;
		//int n=-1;
		try{
			connection = getConnection(); 
			String sql = "update student_login set STUDENT_NAME=?,EMAIL_ID=?,PHONE=? where STUDENT_ID=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			
				ps.setString(1, sd.getStudentName()); 
				
				ps.setString(2, sd.getEmail());
				ps.setString(3, sd.getPhone());
				ps.setString(4, sd.getStudentId());
				value = ps.executeUpdate();	
				String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ps2.setString(1, user);
				ps2.setString(2, "USP");
				ps2.executeUpdate();
		}catch(SQLException sqe){
			System.out.println(sqe);sqe.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e);e.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(value != -1){return true;}else return false;
	  }
	public List<Student> viewStudentDetail(String studentId)
	{
    String query = "SELECT STUDENT_NAME,EMAIL_ID,PHONE FROM student_login where STUDENT_ID=?";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, studentId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            sd = new Student();
            sd.setStudentName(rs.getString("STUDENT_NAME"));
            sd.setEmail(rs.getString("EMAIL_ID"));
            sd.setPhone(rs.getString("PHONE")); 
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    catch (Exception e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public List<Student> viewStudentAllotedBatch(String studentId)
	{
    String query = "SELECT S.SUBJECT_NAME,S.SUBJECT_ID,A.BATCH_ID,A.START_DATE,A.START_TIME,A.BATCH_TYPE FROM lms_student_alloted as SA JOIN lms_batch as A ON SA.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=A.SUBJECT_ID WHERE SA.STUDENT_ID=?";
    List<Student> list = new ArrayList<Student>();
    Student sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, studentId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			java.util.Date  date2 = format1.parse(rs.getString("A.START_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
			String date="";
				 date= df2.format(date2);
            sd = new Student();
            
            sd.setSubjectId(rs.getString("S.SUBJECT_ID"));;
            sd.setSubjectName(rs.getString("S.SUBJECT_NAME"));
            sd.setBatch(rs.getString("A.BATCH_ID"));;
            sd.setStartTime(rs.getString("A.START_TIME"));
            sd.setBatchType(rs.getString("A.BATCH_TYPE")); 
            sd.setStartDate(date);
            list.add(sd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    catch (Exception e) {
        e.printStackTrace();
    }finally
    {
        try {
            if(ps != null)
                ps.close();
            if(connection != null)
                connection.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return list;
}
	public boolean deleteStudentBatch(String studentId,String batch,String user){
		Connection con = null;
		int countTID = -1;
		try{
			con = getConnection(); 
			String sql = "delete from lms_student_alloted where STUDENT_ID=? and BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ps.setString(2, batch);
			countTID=ps.executeUpdate();
			if(countTID>0)
			{
				String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ps2.setString(1, user);
				ps2.setString(2, "DSB");
				ps2.executeUpdate();
			}
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(countTID != -1)return true;else return false;
	  }
	public boolean updateStudentPassword(String studentId,String password){
		Connection con = null;
		int countTID = -1;
		try{
			con = getConnection(); 
			String sql = "update student_login set STUDENT_PASSWORD=? where STUDENT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, studentId);
			
			countTID=ps.executeUpdate();
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(countTID != -1)return true;else return false;
	  }
	public boolean checkCertificate(String studentId,String subjectId){
		Connection con = null;
		int count = 0;
		try{
			con = getConnection(); 
			String sql = "select count(1) from student_certificate where STUDENT_ID=? and SUBJECT_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, studentId);
			ps.setString(2, subjectId);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
				count=rs.getInt(1);
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(count != -0)return true;else return false;
	  }
	public List<Student> getStudentDetail(String studentId,String subjectId){
		Connection con = null;
		List<Student> al=new ArrayList<Student>();
		Student st=null;
		try{
			con = getConnection(); 
			String sql = "select SUBJECT_NAME,STUDENT_NAME,A.BATCH_ID from student_login as L join lms_student_alloted as A on L.STUDENT_ID=A.STUDENT_ID join subjects as S on S.SUBJECT_ID=A.SUBJECT_ID where S.SUBJECT_ID=? AND L.STUDENT_ID=? order by ALLOTED_ID desc limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, subjectId);
			ps.setString(2, studentId);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				st=new Student();
				st.setStudentName(rs.getString("STUDENT_NAME"));
				st.setSubjectName(rs.getString("SUBJECT_NAME"));
				st.setBatch(rs.getString("A.BATCH_ID"));
				al.add(st);
			}
				
			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return al;
	  }
	public boolean generateCertificate(String studentId,String subjectId,String certificate){
		Connection con = null;
		int count = 0;
		try{
			con = getConnection(); 
			String sql = "insert into student_certificate(CERTIFICATE_ID,STUDENT_ID,SUBJECT_ID) values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, certificate);
			ps.setString(2, studentId);
			ps.setString(3, subjectId);
			
			count=ps.executeUpdate();

			
		}catch(SQLException sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception sqe){
			System.out.println(sqe);
			sqe.printStackTrace();
		}
		finally
	    {
	        try {
	            if(ps != null)
	                ps.close();
	            if(connection != null)
	                connection.close();
	            } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		if(count != -0)return true;else return false;
	  }
}