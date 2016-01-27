/**
 * @author Administrator Binay Gaur
 *
 */
package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TeacherDAO {
	
	Connection connection;
	PreparedStatement ps;
	public TeacherDAO() {
		// TODO Auto-generated constructor stub
	}
	private static Connection getConnection() 
            throws SQLException, ClassNotFoundException
    {
        Connection con =new MyConnection().getConnection();
        return con;
    }
	public List<Teacher> viewTeacher()
	{
    String query = "SELECT M.TEACHER_ID,M.USERNAME,M.NAME,M.USERNAME,M.FATHER_NAME,M.DOB,M.SEX,M.ADDRESS,M.EMAIL_ID,M.MOBILE_NUMBER FROM lms_main_teacher as M";
    List<Teacher> list = new ArrayList<Teacher>();
    Teacher sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	sd = new Teacher();
        	 sd.setTeacherId(rs.getString("M.TEACHER_ID"));
            sd.setTeacherName(rs.getString("M.NAME"));
            sd.setUser("M.USERNAME");
            sd.setEmail(rs.getString("M.EMAIL_ID"));
            sd.setPhone(rs.getString("M.MOBILE_NUMBER"));    
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
	public boolean checkTeacherUser(String email){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_main_teacher WHERE EMAIL_ID= ?";
			PreparedStatement ps = con.prepareStatement(sql);
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
		if(countTID != 0)return true;else return false;
	  }
	
	public boolean checkTeacherUser(String email,String oldEmail){
		Connection con = null;
		ResultSet rs = null;
		int countTID = 0;
		try{
			con = getConnection(); 
			String sql = "SELECT COUNT(1) FROM lms_main_teacher WHERE EMAIL_ID= ? and EMAIL_ID!=?";
			PreparedStatement ps = con.prepareStatement(sql);
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
		if(countTID != 0)return true;else return false;
	  }
	public boolean sendTeacher(Teacher ts,String user){	
		  //System.out.println("list :  "+list);
		Connection con = null;
		int value = -1;
		//int n=-1;
		try{
			con = getConnection(); 
			String sql = "INSERT INTO lms_main_teacher(NAME,USERNAME,PASSWORD,EMAIL_ID,MOBILE_NUMBER,DOB,FATHER_NAME,SEX,ADDRESS) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
				 DateFormat formatter ; 
			     formatter = new SimpleDateFormat("dd-MMM-yyyy");
			     Date date=formatter.parse(ts.getDOB());
			     java.sql.Date d=new java.sql.Date(date.getTime());
			     
				ps.setString(1, ts.getTeacherName());
				ps.setString(2, ts.getEmail());
				ps.setString(3, ts.getPassword());
				ps.setString(4, ts.getEmail());
				ps.setString(5, ts.getPhone());
				ps.setDate(6, d);
				ps.setString(7, ts.getFatherName());
				ps.setString(8, ts.getSex());
				ps.setString(9, ts.getAddress());
				
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, user);
					ps2.setString(2, "CT");
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
	public boolean updateTeacher(Teacher ts,String user){	
		  //System.out.println("list :  "+list);
		Connection con = null;
		int value = -1;
		//int n=-1;
		try{
			con = getConnection(); 
			String sql = "update lms_main_teacher set NAME=?,EMAIL_ID=?,MOBILE_NUMBER=?,DOB=?,FATHER_NAME=?,SEX=?,ADDRESS=? where TEACHER_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			
				 DateFormat formatter ; 
			     formatter = new SimpleDateFormat("dd-MMM-yyyy");
			     Date date=formatter.parse(ts.getDOB());
			     java.sql.Date d=new java.sql.Date(date.getTime());
			     
				ps.setString(1, ts.getTeacherName());
				ps.setString(2, ts.getEmail());
				ps.setString(3, ts.getPhone());
				ps.setDate(4, d);
				ps.setString(5, ts.getFatherName());
				ps.setString(6, ts.getSex());
				ps.setString(7, ts.getAddress());
				ps.setString(8, ts.getTeacherId());
				value = ps.executeUpdate();	
				if(value>0)
				{
					String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setString(1, user);
					ps2.setString(2, "UTP");
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
	public boolean deleteTeacherBatch(String teacherId,String batch,String user){
		Connection con = null;
		int countTID = -1;
		try{
			con = getConnection(); 
			String sql = "delete from lms_teacher_alloted where TEACHER_ID=? and BATCH_ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, teacherId);
			ps.setString(2, batch);
			countTID=ps.executeUpdate();
			if(countTID>0)
			{
				String sql2 = "INSERT INTO seo_logs(USER,SUBJECT) VALUES(?,?)";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setString(1, user);
				ps2.setString(2, "DTB");
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
	public ArrayList<String> viewBatchDetail(String subjectId,String batchId)
	{
    String query = "SELECT * from lms_batch where batch_id=? and subject_id=?";
    ArrayList<String> list = new ArrayList<String>();
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, batchId);
        ps.setString(2, subjectId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("START_DATE")+" "+rs.getString("START_TIME"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        time=df3.format(date2);
			        list.add(ld_date);
			        list.add(time);
			        list.add(rs.getString("BATCH_TYPE"));
			        list.add(rs.getString("BASE_PRICE"));
			        list.add(rs.getString("DISCOUNT"));
			        list.add(rs.getString("FINAL_PRICE"));
        }
    } catch (SQLException e) {
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
	public List<Teacher> viewTeacherDetail(String teacherId)
	{
    String query = "SELECT M.TEACHER_ID,M.NAME,M.FATHER_NAME,M.DOB,M.SEX,M.ADDRESS,M.EMAIL_ID,M.MOBILE_NUMBER FROM lms_main_teacher as M where M.TEACHER_ID=?";
    List<Teacher> list = new ArrayList<Teacher>();
    Teacher sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, teacherId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			java.util.Date  date2 = format1.parse(rs.getString("M.DOB"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
			String dob="";
				 dob= df2.format(date2);
            sd = new Teacher();
            
            sd.setTeacherId(rs.getString("M.TEACHER_ID"));
            sd.setTeacherName(rs.getString("M.NAME"));
            sd.setEmail(rs.getString("M.EMAIL_ID"));
            sd.setPhone(rs.getString("M.MOBILE_NUMBER")); 
            sd.setSex(rs.getString("M.SEX"));
            sd.setDOB(dob);
            sd.setAddress(rs.getString("M.ADDRESS"));
            sd.setFatherName(rs.getString("M.FATHER_NAME"));
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
	public List<Teacher> viewTeacherAllotedBatch(String teacherId)
	{
    String query = "SELECT S.SUBJECT_NAME,S.SUBJECT_ID,A.BATCH_ID,A.START_DATE,A.START_TIME,A.BATCH_TYPE FROM lms_teacher_alloted as SA JOIN lms_batch as A ON SA.BATCH_ID=A.BATCH_ID  JOIN subjects as S on S.SUBJECT_ID=A.SUBJECT_ID WHERE SA.TEACHER_ID=?";
    List<Teacher> list = new ArrayList<Teacher>();
    Teacher sd = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, teacherId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			java.util.Date  date2 = format1.parse(rs.getString("A.START_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
			String date="";
				 date= df2.format(date2);
            sd = new Teacher();
            
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
}
