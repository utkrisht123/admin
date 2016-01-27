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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
public class LiveDemoDAO {

	Connection connection;
	PreparedStatement ps;
    private int noOfRecords;
	public LiveDemoDAO() {
		// TODO Auto-generated constructor stub
	}
	private static Connection getConnection() 
            throws SQLException, ClassNotFoundException
    {
        Connection con =new MyConnection().getConnection();
        return con;
    }
	public int getNotificationClassLink(){	
	    //System.out.println("list :  "+list);
		Connection con = null;
		int n=0;
		try{
			DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			df.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			Date date=new Date();
			 java.util.Date  date3 = df.parse(date.toString());
			 //System.out.println("canada util date="+date3);
			 DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			 String time=df2.format(date3);
			 java.util.Date date4=df2.parse(time);
			 //System.out.println("util date="+date4);
			 java.sql.Date d=new java.sql.Date(date4.getTime());
			 //System.out.println("sql date="+d);
			con = getConnection(); 
			String sql = "select count(*) from lms_teacher_session where SESSION_DATE=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1,d);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					n=rs.getInt(1);
				
		}catch(SQLException sqe){
			//System.out.println(sqe);
			sqe.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e);
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
		return n;
	  }
	public List<LiveDemo> viewLiveDemo(int offset, int noOfRecords)
	{
		
		String query="select SQL_CALC_FOUND_ROWS * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) group by L.email,date(qry_date),course order by qry_date desc limit "
		 + offset + ", " + noOfRecords;
            
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
		ps.setString(1, "n");
		ps.setString(2, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
            ld.setStudentId(id);
            ld.setStudentName(rs.getString("Name"));
            ld.setEmail(rs.getString("L.Email"));
            ld.setPhone(rs.getString("Phone_No"));
            ld.setQuery(rs.getString("L.Query"));
            ld.setLastQuery(rs.getString("Q.Query"));
            ld.setDateTime(ld_date+" "+time);
            ld.setQueryId(rs.getString("Q_ID"));
            ld.setECorrect(rs.getString("E_CORRECT"));
            ld.setMCorrect(rs.getString("M_CORRECT"));
            ld.setAttended(rs.getString("ATTENDED"));
           
            ld.setPickup(rs.getString("L.PICKUP"));
            if(rs.getString("Q.INTEREST_LEVEL")==null)
            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
            else
            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
            ld.setLpId(rs.getString("LP_ID"));
            ld.setCountry(rs.getString("COUNTRY"));
            ld.setCourse(rs.getString("COURSE"));
            ld.setCookieId(rs.getString("COOKIE_ID"));
            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if(rs.next())
            this.noOfRecords = rs.getInt(1);
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemo()
	{
    String query = "select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID  order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
		            ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	
	public List<LiveDemo> getLiveDemoEmail()
	{
    String query = "select Q_ID,NAME,EMAIL from lms_live_demo where E_CORRECT!=? group by email order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
			        
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setStudentName(rs.getString("NAME"));
		            ld.setEmail(rs.getString("EMAIL"));
		            
		            list.add(ld);
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
	public ArrayList<String> getEmailUser()
	{
    String query = "select EMAIL from admin_emailuser";
    ArrayList<String> al=new  ArrayList<String>();
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
           
		            
		            al.add(rs.getString("EMAIL"));
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
    return al;
}	
	public List<LiveDemo> getLiveDemoEmail(String course)
	{
    String query = "select Q_ID,NAME,EMAIL from lms_live_demo where COURSE=? and E_CORRECT!=? group by email order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, course);
        ps.setString(2, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            		ld = new LiveDemo();
			        
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setStudentName(rs.getString("NAME"));
		            ld.setEmail(rs.getString("EMAIL"));
		            
		            list.add(ld);
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
	public List<LiveDemo> viewNotificationLiveDemo()
	{
    String query = "select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where NOTIFY=? and L.QUERY!=?  group by L.email,date(qry_date),course order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, "n");
		ps.setString(2, "Request For Live Demo send by Admin");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
         
     
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewNotificationLiveDemo(String course)
	{
    String query = "select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where NOTIFY=? and L.QUERY!=? and COURSE=?  group by L.email,date(qry_date),course order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, "n");
		ps.setString(2, "Request For Live Demo send by Admin");
		ps.setString(3, course);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
         
     
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDate(String d,String td) //13
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		           // ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            {
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            	
		            }
		            else
		            {
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            	
		            }
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDateSearch(String d,String td,String nameemail) //13
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, "%"+nameemail+"%");
		 ps.setString(6, "%"+nameemail+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            {
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            	
		            }
		            else
		            {
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            	
		            }
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByDate(String d,String td) //33
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?)  order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            {
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            	
		            }
		            else
		            {
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            	
		            }
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	
	public List<LiveDemo> viewAttendedLiveDemo(String attended) //15
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, attended);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAttendedLiveDemoSearch(String attended,String nameemail) //15
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.ATTENDED=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, attended);
         ps.setString(2, "%"+nameemail+"%");
         ps.setString(3, "%"+nameemail+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllAttendedLiveDemo(String attended) //35
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.ATTENDED=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, attended);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		           // ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewPickupLiveDemo(String pickup) //14
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, pickup);
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		           // ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewPickupLiveDemoSearch(String pickup,String nameemail) //14
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.PICKUP=?  and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, pickup);
         ps.setString(2, "%"+nameemail+"%");
         ps.setString(3, "%"+nameemail+"%");
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllPickupLiveDemo(String pickup) //34
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.PICKUP=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, pickup);
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewPickupAndAttendLiveDemo(String pickup,String attend) //11
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.PICKUP=? and L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, pickup);
         ps.setString(2, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllPickupAndAttendLiveDemo(String pickup,String attend) //31
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.PICKUP=? and L.ATTENDED=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, pickup);
         ps.setString(2, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemo(String d,String td,String pickup,String attend) //4
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? AND L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
		 ps.setString(6, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoSearch(String d,String td,String pickup,String attend,String nameemail) //4
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? AND L.ATTENDED=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
		 ps.setString(6, attend);
		 ps.setString(7, "%"+nameemail+"%");
		 ps.setString(8, "%"+nameemail+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemo(String d,String td,String pickup,String attend) //24
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? AND L.ATTENDED=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
		 ps.setString(6, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDateAndPickup(String d,String td,String pickup) //9
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDateAndPickupSearch(String d,String td,String pickup,String nameemail) //9
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
		 ps.setString(6, "%"+nameemail+"%");
		 ps.setString(7, "%"+nameemail+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByDateAndPickup(String d,String td,String pickup) //29
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.PICKUP=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, pickup);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            ld.setPickup(rs.getString("L.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         

    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	
	public List<LiveDemo> viewLiveDemoByCourseDatePickupAndAttend(String course,String d, String td,String pickup,String attend) //1
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
			 ps.setString(7, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseDatePickupAndAttendSearch(String course,String d, String td,String pickup,String attend,String nameemail) //1
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
			 ps.setString(7, pickup);
			 ps.setString(8, "%"+nameemail+"%");
			 ps.setString(9, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourseDatePickupAndAttend(String course,String d, String td,String pickup,String attend) //21
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
			 ps.setString(7, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	
	public List<LiveDemo> viewLiveDemoByCourseDateAndPickup(String course,String d, String td,String pickup) //2
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseDateAndPickupSearch(String course,String d, String td,String pickup,String nameemail) //2
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=?  and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, pickup);
			 ps.setString(7, "%"+nameemail+"%");
			 ps.setString(8, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourseDateAndPickup(String course,String d, String td,String pickup) //22
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCoursePickupAndAttend(String course,String pickup,String attend) //3
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			 ps.setString(5, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCoursePickupAndAttendSearch(String course,String pickup,String attend,String nameemail) //3
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=?  and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			 ps.setString(5, pickup);
			 ps.setString(6, "%"+nameemail+"%");
			 ps.setString(7, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCoursePickupAndAttend(String course,String pickup,String attend) //23
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? AND L.PICKUP=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			 ps.setString(5, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByAttendCourseAndDate(String attend,String course,String d, String td) //5
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByAttendCourseAndDateSearch(String attend,String course,String d, String td,String nameemail) //5
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
			 ps.setString(7, "%"+nameemail+"%");
			 ps.setString(8, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByAttendCourseAndDate(String attend,String course,String d, String td) //25
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, attend);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndDate(String course,String d, String td) //6
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndDateSearch(String course,String d, String td,String nameemail) //6
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
			 ps.setString(6, "%"+nameemail+"%");
			 ps.setString(7, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourseAndDate(String course,String d, String td) //26
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, d2+" "+r);
			 ps.setString(2, d3+" "+r2);
			 ps.setString(3, "n");
			 ps.setString(4, "n");
			 ps.setString(5, course);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndPickup(String course,String pickup) //7
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where  (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndPickupSearch(String course,String pickup,String nameemail) //7
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where  (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, pickup);
			 ps.setString(5, "%"+nameemail+"%");
			 ps.setString(6, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourseAndPickup(String course,String pickup) //27
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where  (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? AND L.PICKUP=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, pickup);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndAttend(String course,String attend) //8
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseAndAttendSearch(String course,String attend,String nameemail) //8
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			 ps.setString(5, "%"+nameemail+"%");
			 ps.setString(6, "%"+nameemail+"%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			           // ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourseAndAttend(String course,String attend) //28
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and L.ATTENDED=? order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, attend);
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourse(String course) //12
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and course=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
		
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        rs = ps.executeQuery("SELECT FOUND_ROWS()");
	        if(rs.next())
	            this.noOfRecords = rs.getInt(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourseSearch(String course,String nameemail) //12
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and (NAME like ? or L.EMAIL like ? ) group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, "%"+nameemail+"%");
			 ps.setString(5, "%"+nameemail+"%");
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        rs = ps.executeQuery("SELECT FOUND_ROWS()");
	        if(rs.next())
	            this.noOfRecords = rs.getInt(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByCourse(String course) //32
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=?  order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
			            ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        rs = ps.executeQuery("SELECT FOUND_ROWS()");
	        if(rs.next())
	            this.noOfRecords = rs.getInt(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourse(String d,String td) 
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and QRY_DATE>=? and QRY_DATE<=?  group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, d2+" "+r);
			 ps.setString(4, d3+" "+r2);
			
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        rs = ps.executeQuery("SELECT FOUND_ROWS()");
	        if(rs.next())
	            this.noOfRecords = rs.getInt(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByCourse(String course,String d,String td) 
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where (E_CORRECT!=? or M_CORRECT!=?) and L.COURSE=? and QRY_DATE>=? and QRY_DATE<=?  group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
			 ps.setString(1, "n");
			 ps.setString(2, "n");
			 ps.setString(3, course);
			 ps.setString(4, d2+" "+r);
			 ps.setString(5, d3+" "+r2);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            ld = new LiveDemo();
	            id=" ";
				if(rs.getString("STUDENT_ID")!=null)
					id=rs.getString("STUDENT_ID");   
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
				java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
				DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
				String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
				        if(rs.getString("Name")=="")
				        {
				        	ld.setStudentName("Empty");
				        }else
				        {
				        	ld.setStudentName(rs.getString("Name"));
				        }
			            //ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            if(rs.getString("Q.PICKUP")==null)
			            	ld.setPickup(rs.getString("L.PICKUP"));
			            else
			            	ld.setPickup(rs.getString("Q.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
	            list.add(ld);
	        }
	        rs.close();
	         
	        rs = ps.executeQuery("SELECT FOUND_ROWS()");
	        if(rs.next())
	            this.noOfRecords = rs.getInt(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDateAndAttend(String d,String td,String attend) //10
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewLiveDemoByDateAndAttendSearch(String d,String td,String attend,String nameemail) //10
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.ATTENDED=? and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, attend);
		 ps.setString(6, "%"+nameemail+"%");
		 ps.setString(7, "%"+nameemail+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewAllLiveDemoByDateAndAttend(String d,String td,String attend) //30
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and L.ATTENDED=? order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, attend);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
        rs.close();
         
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	
	
	public List<LiveDemo> viewTodayLiveDemo()
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	
    	
    		java.util.Date date4=getToday();
	
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date4);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date4);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date4.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		           // ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
       
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewTodayLiveDemo(String nameemail)
	{
		String query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and (NAME like ? or L.EMAIL like ?) group by L.email,date(qry_date),course order by QRY_DATE DESC";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
    	
    	
    		java.util.Date date4=getToday();
	
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date4);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date4);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date4.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "n");
		 ps.setString(5, "%"+nameemail+"%");
		 ps.setString(6, "%"+nameemail+"%");
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
       
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public List<LiveDemo> viewTodayLiveDemoTask(String user,String d,String td)
	{
		String query="select * from seo_task where (date(FROM_DATE)>=? and date(FROM_DATE)<=? ) and USER=? and TITLE like '%Webinar Link%'";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    LiveDemo ld = null;
    String id=" ";
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setDate(1, d2);
         ps.setDate(2, d3);
		 ps.setString(3, user);
		 
		 ResultSet rs2=null;
		 PreparedStatement ps2=null;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        			ld = new LiveDemo();
        			String title=rs.getString("TITLE");
        			ld.setQuery(title);
        			String s[]=title.split(" ");
        			String countryCode="",name="",phone="",email=s[s.length-1],course="";
        			id=" ";
            		ps2 = connection.prepareStatement("select Q_ID,STUDENT_ID,NAME,PHONE_NO,COUNTRY_CODE,COURSE from lms_live_demo where EMAIL=? order by QRY_DATE DESC limit 1");
           		 	ps2.setString(1, email);
           		 
           		
                   rs2 = ps2.executeQuery();
                   while (rs2.next()) {
                   
                	   name=rs2.getString("NAME");
                	   phone=rs2.getString("PHONE_NO");
                	   countryCode=rs2.getString("COUNTRY_CODE");
                	   course=rs2.getString("COURSE");
                	   if(rs2.getString("STUDENT_ID")!=null)
           				id=rs2.getString("STUDENT_ID");
                	   ld.setLpId(rs2.getString("Q_ID"));
                   }
                   if(ps2 != null)
                       ps2.close();
                   rs2.close();
            		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		java.util.Date  dt2 = format1.parse(rs.getString("FROM_DATE"));
            		DateFormat df3 = new SimpleDateFormat("dd-MMM-yyyy");
            		String ld_date="";
            		ld_date= df3.format(dt2);
            		
		            ld.setStudentName(name);
		            ld.setEmail(email);
		            ld.setPhone(phone);
		            ld.setStudentId(id);
		            ld.setQueryId(rs.getString("ID"));
		            ld.setDateTime(ld_date);
		            ld.setCountryCode(countryCode);
		            ld.setCourse(course);
		            if(rs.getString("STATUS").equalsIgnoreCase("Completed"))
		            	 ld.setAttended("y");
		            else
		            	 ld.setAttended("n");
		            
            list.add(ld);
        }
       
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	public boolean updateTask(String id)
	{
		String query="update seo_task set STATUS=? where ID=? ";
		int count=-1;
    try {
    	
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, "Completed");
         ps.setString(2, id);
         count=ps.executeUpdate();
		 
		 
       
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
    if(count!=-1)
    	return true;
    else
    	return false;
}
	public List<LiveDemo> viewAdminLiveDemo()
	{
		String query="SELECT * FROM `lms_live_demo` as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where L.QUERY=? group by L.email,date(qry_date),course order by qry_date desc";
    List<LiveDemo> list = new ArrayList<LiveDemo>();
    String id="";
    LiveDemo ld = null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
     	ps.setString(1, "Request For Live Demo send by Admin");
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            ld = new LiveDemo();
            id=" ";
			if(rs.getString("STUDENT_ID")!=null)
				id=rs.getString("STUDENT_ID");   
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
			java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
			DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
			String time="",ld_date="";
				 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				 ld_date= df2.format(date2);
			        DateFormat df3 = new SimpleDateFormat("HH:mm");
			        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			        time=df3.format(date2);
			        ld.setStudentId(id);
			        if(rs.getString("Name")=="")
			        {
			        	ld.setStudentName("Empty");
			        }else
			        {
			        	ld.setStudentName(rs.getString("Name"));
			        }
		            //ld.setStudentName(rs.getString("Name"));
		            ld.setEmail(rs.getString("L.Email"));
		            ld.setPhone(rs.getString("Phone_No"));
		            ld.setQuery(rs.getString("L.Query"));
		            ld.setLastQuery(rs.getString("Q.Query"));
		            ld.setDateTime(ld_date+" "+time);
		            ld.setQueryId(rs.getString("Q_ID"));
		            ld.setECorrect(rs.getString("E_CORRECT"));
		            ld.setMCorrect(rs.getString("M_CORRECT"));
		            ld.setAttended(rs.getString("ATTENDED"));
		            if(rs.getString("Q.PICKUP")==null)
		            	ld.setPickup(rs.getString("L.PICKUP"));
		            else
		            	ld.setPickup(rs.getString("Q.PICKUP"));
		            if(rs.getString("Q.INTEREST_LEVEL")==null)
		            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
		            else
		            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
		            ld.setLpId(rs.getString("LP_ID"));
		            ld.setCountry(rs.getString("COUNTRY"));
		            ld.setCourse(rs.getString("COURSE"));
		            ld.setCookieId(rs.getString("COOKIE_ID"));
		            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
	
	
	public ArrayList<String> getCourse()
	{
		String query="select SUBJECT_ID,SUBJECT_NAME from subjects";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("SUBJECT_ID")+"/"+rs.getString("SUBJECT_NAME"));
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
	
	public ArrayList<String> getSelfPacedCourse()
	{
		String query="select SUBJECT_ID,SUBJECT_NAME from subjects where Training='Self-Paced'";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("SUBJECT_ID")+"/"+rs.getString("SUBJECT_NAME"));
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
	
	

	public ArrayList<String> getInstructorLedCourse()
	{
		String query="select SUBJECT_ID,SUBJECT_NAME from subjects where TRAINING=? ";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1,"Instructor Led");
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("SUBJECT_ID")+"/"+rs.getString("SUBJECT_NAME"));
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
	
	public ArrayList<String> getCountry()
	{
		String query="select COUNTRY from seo_geolocation where COUNTRY!='' group by COUNTRY";
		ArrayList<String> list = new ArrayList<String>();
		list.add("All");
		list.add("Hogwarts");
		try {
         connection = getConnection();
         ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("COUNTRY"));
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
	
	
	public ArrayList<String> getBatch()
	{
		String query="select BATCH_ID FROM lms_batch  order by START_DATE desc";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
	
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("BATCH_ID"));
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
	public ArrayList<String> getBatch(String subjectId)
	{
		String query="select BATCH_ID FROM lms_batch where SUBJECT_ID=? order by START_DATE desc";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, subjectId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("BATCH_ID"));
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
	public ArrayList<LiveDemo> getInstantEmail()
	{
		String query="selectEMAIL,CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME from lms_live_demo where e_correct!=? group by EMAIL";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
    	
        connection = getConnection();
        ps = connection.prepareStatement(query);
		 ps.setString(1, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setStudentName(rs.getString("NAME"));
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	public ArrayList<LiveDemo> getInstantEmailByDate(String d,String td)
	{
		String query="select EMAIL,CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME from lms_live_demo where QRY_DATE>=? and QRY_DATE<=? and e_correct!=? group by EMAIL";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
    	
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setStudentName(rs.getString("NAME"));
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	public ArrayList<LiveDemo> getInstantEmailByCourse(String course)
	{
		String query="select EMAIL,CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME from lms_live_demo where course=? and e_correct!=? group by EMAIL";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, course);
         ps.setString(2, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setStudentName(rs.getString("NAME"));
            list.add(ld);
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
	public ArrayList<LiveDemo> getInstantEmailByDateCourse(String d,String td,String course)
	{
		String query="select EMAIL,CONCAT(UCASE(MID(name,1,1)),MID(name,2)) as NAME  from lms_live_demo where QRY_DATE>=? and QRY_DATE<=? and course=? and e_correct!=? group by EMAIL";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
  
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
       connection = getConnection();
       ps = connection.prepareStatement(query);
       ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
         ps.setString(3, course);
         ps.setString(4, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setStudentName(rs.getString("NAME"));
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	
	
	public ArrayList<LiveDemo> getInstantPhone()
	{
		String query="select distinct(phone_no) from lms_live_demo where m_correct!=? and country=?";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
    	
        connection = getConnection();
        ps = connection.prepareStatement(query);
		 ps.setString(1, "n");
		 ps.setString(2, "India");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	String s=rs.getString("phone_no");
        	if(s!=null && !s.equals(""))
        	{
        		String s2[]=s.split("");
        		String s3=s2[0]+s2[1];
        		if(s3.equals("94"))
        		{
        			if(s2.length==10)
        			{	
        				ld.setPhone(s);
        			}
        				
        		}
        		else if(s2.length>=10)
        		{
        			int count=1;
        			String s4="";
        			for(int i=s2.length-1;i>=0;i--)
        			{
        				if(count<11)
        				{
        					if(s4.equals(""))
        						s4=s2[i];
        					else
        						s4=s2[i]+s4;
        				}
        				count++;
        			}
        			ld.setPhone(s4);
        		}
        	}
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	public ArrayList<LiveDemo> getInstantPhoneByDate(String d,String td)
	{
		String query="select distinct(phone_no) from lms_live_demo where QRY_DATE>=? and QRY_DATE<=? and m_correct!=? and country=?";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
    	
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
        connection = getConnection();
        ps = connection.prepareStatement(query);
        ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
		 ps.setString(3, "n");
		 ps.setString(4, "India");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	String s=rs.getString("phone_no");
        	if(s!=null && !s.equals(""))
        	{
        		String s2[]=s.split("");
        		String s3=s2[0]+s2[1];
        		if(s3.equals("94"))
        		{
        			if(s2.length==10)
        			{
        				ld.setPhone(s);
        			}
        				
        		}
        		else if(s2.length>=10)
        		{
        			int count=1;
        			String s4="";
        			for(int i=s2.length-1;i>=0;i--)
        			{
        				if(count<11)
        				{
        					if(s4.equals(""))
        						s4=s2[i];
        					else
        						s4=s2[i]+s4;
        				}
        				count++;
        			}
        			ld.setPhone(s4);
        		}
        	}
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	public ArrayList<LiveDemo> getInstantPhoneByCourse(String course)
	{
		String query="select distinct(phone_no) from lms_live_demo where course=? and m_correct!=? and country=?";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, course);
         ps.setString(2, "n");
         ps.setString(3, "India");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld = new LiveDemo();
        	String s=rs.getString("phone_no");
        	if(s!=null && !s.equals(""))
        	{
        		String s2[]=s.split("");
        		String s3=s2[0]+s2[1];
        		if(s3.equals("94") )
        		{
        			if(s2.length==10)
        			{
        				ld.setPhone(s);
        			}
        				
        		}
        		else if(s2.length>=10)
        		{
        			int count=1;
        			String s4="";
        			for(int i=s2.length-1;i>=0;i--)
        			{
        				if(count<11)
        				{
        					if(s4.equals(""))
        						s4=s2[i];
        					else
        						s4=s2[i]+s4;
        				}
        				count++;
        			}
        			ld.setPhone(s4);
        		}
        	}
        	//ld.setPhone(rs.getString("phone_no"));
            list.add(ld);
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
	public ArrayList<LiveDemo> getInstantPhoneByDateCourse(String d,String td,String course)
	{
		String query="select distinct(phone_no) from lms_live_demo where QRY_DATE>=? and QRY_DATE<=? and course=? and m_correct!=? and country=?";
		ArrayList<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld=null;
    try {
  
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
       connection = getConnection();
       ps = connection.prepareStatement(query);
       ps.setString(1, d2+" "+r);
		 ps.setString(2, d3+" "+r2);
         ps.setString(3, course);
         ps.setString(4, "n");
         ps.setString(5, "India");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	ld = new LiveDemo();
        	String s=rs.getString("phone_no");
        	if(s!=null && !s.equals(""))
        	{
        		String s2[]=s.split("");
        		String s3=s2[0]+s2[1];
        		if(s3.equals("94"))
        		{
        			if(s2.length==10)
        			{
        				ld.setPhone(s);
        			}
        				
        		}
        		else if(s2.length>=10)
        		{
        			int count=1;
        			String s4="";
        			for(int i=s2.length-1;i>=0;i--)
        			{
        				if(count<11)
        				{
        					if(s4.equals(""))
        						s4=s2[i];
        					else
        						s4=s2[i]+s4;
        				}
        				count++;
        			}
        			ld.setPhone(s4);
        		}
        	}
            list.add(ld);
        }
      
    } catch (Exception e) {
        e.printStackTrace();
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
    return list;
}
	
	public java.util.Date getToday()
	{
		String query="SELECT now()";
    
		 java.util.Date dd=null;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          	df.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
          	 java.util.Date  date3 = df.parse(rs.getString(1));
          	 DateFormat df3 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
          	 df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
          	 String datetime=df3.format(date3);
          	 java.util.Date date4=df3.parse(datetime);
          	 Calendar cal=Calendar.getInstance();
          	 cal.setTime(date4);
          	 if(cal.get(Calendar.HOUR_OF_DAY)==18)
          	 {
          		if(cal.get(Calendar.MINUTE)>29)
          			cal.add(Calendar.DATE, 1);
          	 }
          	 else if(cal.get(Calendar.HOUR_OF_DAY)>18)
          		 cal.add(Calendar.DATE, 1);
          		 
          dd=cal.getTime();
        }
      
         
 
    } catch (SQLException e) {
        e.printStackTrace();
    }catch (ParseException e) {
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
    return dd;
}
	public String getRequests(String d,String td)
	{
		String query="select count(*) from lms_live_demo where course=? and QRY_DATE>=? and QRY_DATE<=? and NOTIFY!=?  group by email,date(qry_date),course";
		String query2="select count(*) from lms_live_demo where course=? and QRY_DATE>=? and QRY_DATE<=? and NOTIFY!=?  group by email,date(qry_date),course";
		String query3="select count(*) from lms_live_demo  where course=? and QRY_DATE>=? and QRY_DATE<=? and NOTIFY!=?  group by email,date(qry_date),course";
		String query4="select count(*) from lms_live_demo  where course=? and QRY_DATE>=? and QRY_DATE<=? and NOTIFY!=?  group by email,date(qry_date),course";
		String query5="select count(*) from lms_live_demo  where course=? and QRY_DATE>=? and QRY_DATE<=? and NOTIFY!=?  group by email,date(qry_date),course";
		
		 int n=0,m=0,k=0,ra=0,t=0,p=0;
		 String s="0";
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
			 cal.add(Calendar.DATE, -1);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
				String r="09:00:00";
				String r2="09:00:00";
				if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
				{
					r="08:00:00";
					
				}
				if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
				{
					r2="08:00:00";
					
				}
			 
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, "Hadoop");
         ps.setString(2, d2+" "+r);
         ps.setString(3, d3+" "+r2);
         ps.setString(4, "n");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	n++;
        	
        }
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setString(1, "Python");
        ps2.setString(2, d2+" "+r);
        ps2.setString(3, d3+" "+r2);
        ps2.setString(4, "n");
       ResultSet rs2 = ps2.executeQuery();
       while (rs2.next()) {
       	m++;
       	
       }
       PreparedStatement ps3 = connection.prepareStatement(query3);
       ps3.setString(1, "MongoDB");
       ps3.setString(2, d2+" "+r);
       ps3.setString(3, d3+" "+r2);
       ps3.setString(4, "n");
      ResultSet rs3 = ps3.executeQuery();
      while (rs3.next()) {
      	k++;
      	
      }
      PreparedStatement ps4 = connection.prepareStatement(query4);
      ps4.setString(1, "Business Analytics With R");
      ps4.setString(2, d2+" "+r);
      ps4.setString(3, d3+" "+r2);
      ps4.setString(4, "n");
     ResultSet rs4 = ps4.executeQuery();
     while (rs4.next()) {
     	ra++;
     	
     }
     PreparedStatement ps5 = connection.prepareStatement(query5);
     ps5.setString(1, "Apache Spark and Scala");
     ps5.setString(2, d2+" "+r);
     ps5.setString(3, d3+" "+r2);
     ps5.setString(4, "n");
    ResultSet rs5 = ps5.executeQuery();
    while (rs5.next()) {
    	p++;
    	
    }
   
     t=n+m+k+p+ra;
      
     s=n+"/"+m+"/"+k+"/"+ra+"/"+p+"/"+t;    
 
    } catch (Exception e) {
        e.printStackTrace();
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
    return s;
}
	public String getNewRequests(String d,String td)
	{
		String query="SELECT COURSE FROM lms_live_demo where NOTIFY=? and QUERY!=? group by email,date(qry_date),course";
		
		
		 int n=0,m=0,k=0,t=0,r=0,p=0;
		 String s="0";
    try {
    	
				
				  connection = getConnection();
			         ps = connection.prepareStatement(query);
			         ps.setString(1, "n");
			         ps.setString(2, "Request For Live Demo send by Admin");
			        ResultSet rs = ps.executeQuery();
			        while (rs.next()) {
			        	String s2=rs.getString("COURSE");
			    		  if(s2.equalsIgnoreCase("Hadoop"))
			    		  {
			    			 n++;
			    		  }
			    		  else if(s2.equalsIgnoreCase("Python"))
			    		  {
			    			 m++;
			    		  }
			    		  else if(s2.equalsIgnoreCase("MongoDB"))
			    		  {
			    			  k++;
			    		  }
			    		  else if(s2.equalsIgnoreCase("Business Analytics With R"))
			    		  {
			    			  r++;
			    		  }
			    		  else if(s2.equalsIgnoreCase("Apache Spark and Scala"))
			    		  {
			    			  p++;
			    		  }
			        	
			        }
			       
     t=n+m+k+r+p;
      
     s=n+"/"+m+"/"+k+"/"+r+"/"+p+"/"+t;    
 
    } catch (Exception e) {
        e.printStackTrace();
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
    return s;
}
	
	public ArrayList<String> getWebinarEmail()
	{
		String query="select EMAIL_ID from webinar_account";
		ArrayList<String> list = new ArrayList<String>();
  
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
            list.add(rs.getString("EMAIL_ID"));
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
	public List<LiveDemo> getChatQuery()
	{
		String query="select * from chat_history order by TIMESTAMP DESC";
		List<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld=new LiveDemo();
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
			DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
			String ld_date= df2.format(date2);
        	ld.setQueryId(rs.getString("ID"));
        	ld.setStudentName(rs.getString("NAME"));
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setPhone(rs.getString("PHONE_NO"));
        	ld.setCourse(rs.getString("COURSE"));
        	ld.setQryType(rs.getString("TYPE"));
        	ld.setCountry(rs.getString("COUNTRY"));
        	ld.setDateTime(ld_date);
        	ld.setCity(rs.getString("USER"));
        	ld.setSource(rs.getString("SOURCE"));
        	ld.setPageUrl(rs.getString("LOCATION"));
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e2) {
        e2.printStackTrace();
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
    return list;
}
	public List<LiveDemo> getChatQuery(String course)
	{
		String query="select * from chat_history where course=? order by TIMESTAMP DESC";
		List<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld;
    try {
  
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, course);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld=new LiveDemo();
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
			DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
			String ld_date= df2.format(date2);
        	ld.setQueryId(rs.getString("ID"));
        	ld.setStudentName(rs.getString("NAME"));
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setPhone(rs.getString("PHONE_NO"));
        	ld.setCourse(rs.getString("COURSE"));
        	ld.setQryType(rs.getString("TYPE"));
        	ld.setCountry(rs.getString("COUNTRY"));
        	ld.setDateTime(ld_date);
        	ld.setSource(rs.getString("SOURCE"));
        	ld.setPageUrl(rs.getString("LOCATION"));
        	ld.setCity(rs.getString("USER"));
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e2) {
        e2.printStackTrace();
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
    return list;
}
	public List<LiveDemo> getChatQuery(String d,String td)
	{
		String query="select * from chat_history where date(timestamp)>=? and date(timestamp)<=? order by TIMESTAMP DESC";
		List<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setDate(1, d2);
         ps.setDate(2, d3);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld=new LiveDemo();
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
			DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
			String ld_date= df2.format(date2);
        	ld.setQueryId(rs.getString("ID"));
        	ld.setStudentName(rs.getString("NAME"));
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setPhone(rs.getString("PHONE_NO"));
        	ld.setCourse(rs.getString("COURSE"));
        	ld.setQryType(rs.getString("TYPE"));
        	ld.setCountry(rs.getString("COUNTRY"));
        	ld.setDateTime(ld_date);
        	ld.setSource(rs.getString("SOURCE"));
        	ld.setPageUrl(rs.getString("LOCATION"));
        	ld.setCity(rs.getString("USER"));
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e2) {
        e2.printStackTrace();
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
    return list;
}
	public List<LiveDemo> getChatQuery(String d,String td,String course)
	{
		String query="select * from chat_history where course=? and date(timestamp)>=? and date(timestamp)<=? order by TIMESTAMP DESC";
		List<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld;
    try {
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		  java.util.Date  date = df.parse(d);
		  java.util.Date  date3 = df.parse(td);
		  Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			 Calendar cal2 = Calendar.getInstance();
			 cal2.setTime(date3);
				java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
				java.sql.Date d3=new java.sql.Date(date3.getTime());
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, course);
         ps.setDate(2, d2);
         ps.setDate(3, d3);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld=new LiveDemo();
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
			DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
			String ld_date= df2.format(date2);
        	ld.setQueryId(rs.getString("ID"));
        	ld.setStudentName(rs.getString("NAME"));
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setPhone(rs.getString("PHONE_NO"));
        	ld.setCourse(rs.getString("COURSE"));
        	ld.setQryType(rs.getString("TYPE"));
        	ld.setCountry(rs.getString("COUNTRY"));
        	ld.setDateTime(ld_date);
        	ld.setSource(rs.getString("SOURCE"));
        	ld.setPageUrl(rs.getString("LOCATION"));
        	ld.setCity(rs.getString("USER"));
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e2) {
        e2.printStackTrace();
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
    return list;
}
	public List<LiveDemo> getChatQueryDetail(String id)
	{
		String query="select * from chat_history where ID=?";
		List<LiveDemo> list = new ArrayList<LiveDemo>();
		LiveDemo ld;
    try {
    	
         connection = getConnection();
         ps = connection.prepareStatement(query);
         ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	
        	ld=new LiveDemo();
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date  date2 = format1.parse(rs.getString("TIMESTAMP"));
			DateFormat df2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
			String ld_date= df2.format(date2);
        	ld.setQueryId(rs.getString("ID"));
        	ld.setStudentName(rs.getString("NAME"));
        	ld.setEmail(rs.getString("EMAIL"));
        	ld.setPhone(rs.getString("PHONE_NO"));
        	ld.setCourse(rs.getString("COURSE"));
        	ld.setQryType(rs.getString("TYPE"));
        	ld.setCountry(rs.getString("COUNTRY"));
        	ld.setSource(rs.getString("SOURCE"));
        	ld.setPageUrl(rs.getString("LOCATION"));
        	ld.setDateTime(ld_date);
        	
        	
            list.add(ld);
        }
      
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e2) {
        e2.printStackTrace();
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
    return list;
}
	public boolean createChatQuery(LiveDemo ld,String user) {
		String query="insert into chat_history(NAME,EMAIL,PHONE_NO,COURSE,TYPE,COUNTRY,TIMESTAMP,USER,SOURCE,LOCATION) values(?,?,?,?,?,?,?,?,?,?)";
		boolean flag=false;
		int value = -1;
		try {
			  
			DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
		     Date date=formatter2.parse(ld.getDateTime());
		     Calendar cal=Calendar.getInstance();
		     cal.setTime(date);
		     String time=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
		     java.sql.Date d=new java.sql.Date(date.getTime());
			
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, ld.getStudentName());
	         ps.setString(2, ld.getEmail());
	         ps.setString(3, ld.getPhone());
	         ps.setString(4, ld.getCourse());
	         ps.setString(5, ld.getQryType());
	         ps.setString(6, ld.getCountry());
	         ps.setString(7, d+" "+time);
	         ps.setString(8, user);
	         ps.setString(9, ld.getSource());
	         ps.setString(10, ld.getPageUrl());
	         value=ps.executeUpdate();
	         if(value!=-1)
	        	 flag=true;
	         else
	        	 flag=false;
		}
		catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    catch (Exception e2) {
	        e2.printStackTrace();
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
		return flag;
        
    }
	public boolean updateChatQuery(LiveDemo ld) {
		String query="update chat_history set NAME=?,EMAIL=?,PHONE_NO=?,COURSE=?,TYPE=?,COUNTRY=?,TIMESTAMP=?,SOURCE=?,LOCATION=? WHERE ID=?";
		boolean flag=false;
		int value = -1;
		try {
			DateFormat formatter2 = new SimpleDateFormat("dd MMMM yyyy - HH:mm");
		     Date date=formatter2.parse(ld.getDateTime());
		     Calendar cal=Calendar.getInstance();
		     cal.setTime(date);
		     String time=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
		     java.sql.Date d=new java.sql.Date(date.getTime());
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, ld.getStudentName());
	         ps.setString(2, ld.getEmail());
	         ps.setString(3, ld.getPhone());
	         ps.setString(4, ld.getCourse());
	         ps.setString(5, ld.getQryType());
	         ps.setString(6, ld.getCountry());
	         ps.setString(7, d+" "+time);
	         ps.setString(8, ld.getSource());
	         ps.setString(9, ld.getPageUrl());
	         ps.setString(10, ld.getQueryId());
	         
	         value=ps.executeUpdate();
	         if(value!=-1)
	        	 flag=true;
	         else
	        	 flag=false;
		}
		catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    catch (Exception e2) {
	        e2.printStackTrace();
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
		return flag;
        
    }
	public boolean deleteChatQuery(String id) {
		String query="delete from chat_history where ID=?";
		boolean flag=false;
		int value = -1;
		try {
			  
	         connection = getConnection();
	         ps = connection.prepareStatement(query);
	         ps.setString(1, id);
	         value=ps.executeUpdate();
	         if(value!=-1)
	        	 flag=true;
	         else
	        	 flag=false;
		}
		catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    catch (Exception e2) {
	        e2.printStackTrace();
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
		return flag;
        
    }
	
	//Country
	public List<LiveDemo> viewDemoByCountry(String d, String td,String country) //1
	{
		String query="";
		if(country.equalsIgnoreCase("India"))
			query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
		else
			query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
					connection = getConnection();
		         ps = connection.prepareStatement(query);
		         ps.setString(1, d2+" "+r);
				 ps.setString(2, d3+" "+r2);
				 ps.setString(3, "n");
				 ps.setString(4, "n");
				 ps.setString(5, "India");
				 ResultSet rs = ps.executeQuery();
				 while (rs.next()) {
		            ld = new LiveDemo();
		            id=" ";
					if(rs.getString("STUDENT_ID")!=null)
						id=rs.getString("STUDENT_ID");   
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
			            ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
			            list.add(ld);
				 }
	        rs.close();
	         
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	
	//Country ,course
	public List<LiveDemo> viewDemoByCountryCourse(String course,String d, String td,String country) //1
	{
		String query="";
		if(country.equalsIgnoreCase("India"))
			query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and COURSE=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
		else
			query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and COURSE=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
	    List<LiveDemo> list = new ArrayList<LiveDemo>();
	    String id="";
	    LiveDemo ld = null;
	    try {
	    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			  java.util.Date  date = df.parse(d);
			  java.util.Date  date3 = df.parse(td);
			  Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date3);
				 cal.add(Calendar.DATE, -1);
					java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
					java.sql.Date d3=new java.sql.Date(date3.getTime());
					String r="09:00:00";
					String r2="09:00:00";
					if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
					{
						r="08:00:00";
						
					}
					if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
					{
						r2="08:00:00";
						
					}
					connection = getConnection();
		         ps = connection.prepareStatement(query);
		         ps.setString(1, d2+" "+r);
				 ps.setString(2, d3+" "+r2);
				 ps.setString(3, "n");
				 ps.setString(4, "n");
				 ps.setString(5, "India");
				 ps.setString(6, course);
				 ResultSet rs = ps.executeQuery();
				 while (rs.next()) {
		            ld = new LiveDemo();
		            id=" ";
					if(rs.getString("STUDENT_ID")!=null)
						id=rs.getString("STUDENT_ID");   
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
					java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
					DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
					String time="",ld_date="";
					 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					 ld_date= df2.format(date2);
				        DateFormat df3 = new SimpleDateFormat("HH:mm");
				        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				        time=df3.format(date2);
				        ld.setStudentId(id);
			            ld.setStudentName(rs.getString("Name"));
			            ld.setEmail(rs.getString("L.Email"));
			            ld.setPhone(rs.getString("Phone_No"));
			            ld.setQuery(rs.getString("L.Query"));
			            ld.setLastQuery(rs.getString("Q.Query"));
			            ld.setDateTime(ld_date+" "+time);
			            ld.setQueryId(rs.getString("Q_ID"));
			            ld.setECorrect(rs.getString("E_CORRECT"));
			            ld.setMCorrect(rs.getString("M_CORRECT"));
			            ld.setAttended(rs.getString("ATTENDED"));
			            ld.setPickup(rs.getString("L.PICKUP"));
			            if(rs.getString("Q.INTEREST_LEVEL")==null)
			            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
			            else
			            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
			            ld.setLpId(rs.getString("LP_ID"));
			            ld.setCountry(rs.getString("COUNTRY"));
			            ld.setCourse(rs.getString("COURSE"));
			            ld.setCookieId(rs.getString("COOKIE_ID"));
			            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
			            list.add(ld);
				 }
	        rs.close();
	         
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }catch (ParseException e) {
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
	
	//Country ,attended
		public List<LiveDemo> viewDemoByCountryAttended(String attended,String d, String td,String country) //1
		{
			String query="";
			if(country.equalsIgnoreCase("India"))
				query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and  L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
			else
				query="select * from (lms_live_demo as L left outer join (select * from query_phonecall_response order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL) where QRY_DATE>=? and QRY_DATE<=?  and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.ATTENDED=? group by L.email,date(qry_date),course order by QRY_DATE DESC";
		    List<LiveDemo> list = new ArrayList<LiveDemo>();
		    String id="";
		    LiveDemo ld = null;
		    try {
		    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
				 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				  java.util.Date  date = df.parse(d);
				  java.util.Date  date3 = df.parse(td);
				  Calendar cal = Calendar.getInstance();
					 cal.setTime(date);
					 Calendar cal2 = Calendar.getInstance();
					 cal2.setTime(date3);
					 cal.add(Calendar.DATE, -1);
						java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
						java.sql.Date d3=new java.sql.Date(date3.getTime());
						String r="09:00:00";
						String r2="09:00:00";
						if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
						{
							r="08:00:00";
							
						}
						if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
						{
							r2="08:00:00";
							
						}
						connection = getConnection();
			         ps = connection.prepareStatement(query);
			         ps.setString(1, d2+" "+r);
					 ps.setString(2, d3+" "+r2);
					 ps.setString(3, "n");
					 ps.setString(4, "n");
					 ps.setString(5, "India");
					 ps.setString(6, attended);
					 ResultSet rs = ps.executeQuery();
					 while (rs.next()) {
			            ld = new LiveDemo();
			            id=" ";
						if(rs.getString("STUDENT_ID")!=null)
							id=rs.getString("STUDENT_ID");   
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
						java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
						DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
						String time="",ld_date="";
						 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						 ld_date= df2.format(date2);
					        DateFormat df3 = new SimpleDateFormat("HH:mm");
					        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
					        time=df3.format(date2);
					        ld.setStudentId(id);
				            ld.setStudentName(rs.getString("Name"));
				            ld.setEmail(rs.getString("L.Email"));
				            ld.setPhone(rs.getString("Phone_No"));
				            ld.setQuery(rs.getString("L.Query"));
				            ld.setLastQuery(rs.getString("Q.Query"));
				            ld.setDateTime(ld_date+" "+time);
				            ld.setQueryId(rs.getString("Q_ID"));
				            ld.setECorrect(rs.getString("E_CORRECT"));
				            ld.setMCorrect(rs.getString("M_CORRECT"));
				            ld.setAttended(rs.getString("ATTENDED"));
				            ld.setPickup(rs.getString("L.PICKUP"));
				            if(rs.getString("Q.INTEREST_LEVEL")==null)
				            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
				            else
				            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
				            ld.setLpId(rs.getString("LP_ID"));
				            ld.setCountry(rs.getString("COUNTRY"));
				            ld.setCourse(rs.getString("COURSE"));
				            ld.setCookieId(rs.getString("COOKIE_ID"));
				            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
				            list.add(ld);
					 }
		        rs.close();
		         
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }catch (ParseException e) {
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
		
		//Country ,pickup
				public List<LiveDemo> viewDemoByCountryPickup(String pickup,String d, String td,String country) //1
				{
					String query="";
					if(country.equalsIgnoreCase("India"))
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and L.PICKUP=? order by QRY_DATE DESC";
					else
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.PICKUP=? order by QRY_DATE DESC";
				    List<LiveDemo> list = new ArrayList<LiveDemo>();
				    String id="";
				    LiveDemo ld = null;
				    try {
				    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
						 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						  java.util.Date  date = df.parse(d);
						  java.util.Date  date3 = df.parse(td);
						  Calendar cal = Calendar.getInstance();
							 cal.setTime(date);
							 Calendar cal2 = Calendar.getInstance();
							 cal2.setTime(date3);
							 cal.add(Calendar.DATE, -1);
								java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
								java.sql.Date d3=new java.sql.Date(date3.getTime());
								String r="09:00:00";
								String r2="09:00:00";
								if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
								{
									r="08:00:00";
									
								}
								if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
								{
									r2="08:00:00";
									
								}
								connection = getConnection();
					         ps = connection.prepareStatement(query);
					         ps.setString(1, d2+" "+r);
							 ps.setString(2, d3+" "+r2);
							 ps.setString(3, "n");
							 ps.setString(4, "n");
							 ps.setString(5, "India");
							 ps.setString(6, pickup);
							 ResultSet rs = ps.executeQuery();
							 while (rs.next()) {
					            ld = new LiveDemo();
					            id=" ";
								if(rs.getString("STUDENT_ID")!=null)
									id=rs.getString("STUDENT_ID");   
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
								String time="",ld_date="";
								 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								 ld_date= df2.format(date2);
							        DateFormat df3 = new SimpleDateFormat("HH:mm");
							        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							        time=df3.format(date2);
							        ld.setStudentId(id);
						            ld.setStudentName(rs.getString("Name"));
						            ld.setEmail(rs.getString("L.Email"));
						            ld.setPhone(rs.getString("Phone_No"));
						            ld.setQuery(rs.getString("L.Query"));
						            ld.setLastQuery(rs.getString("Q.Query"));
						            ld.setDateTime(ld_date+" "+time);
						            ld.setQueryId(rs.getString("Q_ID"));
						            ld.setECorrect(rs.getString("E_CORRECT"));
						            ld.setMCorrect(rs.getString("M_CORRECT"));
						            ld.setAttended(rs.getString("ATTENDED"));
						            ld.setPickup(rs.getString("L.PICKUP"));
						            if(rs.getString("Q.INTEREST_LEVEL")==null)
						            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
						            else
						            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
						            ld.setLpId(rs.getString("LP_ID"));
						            ld.setCountry(rs.getString("COUNTRY"));
						            ld.setCourse(rs.getString("COURSE"));
						            ld.setCookieId(rs.getString("COOKIE_ID"));
						            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
						            list.add(ld);
							 }
				        rs.close();
				         
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }catch (ParseException e) {
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
	
				//Country ,pickup,Course
				public List<LiveDemo> viewDemoByCountryPickupCourse(String course,String pickup,String d, String td,String country) //1
				{
					String query="";
					if(country.equalsIgnoreCase("India"))
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and L.PICKUP=? and COURSE=? order by QRY_DATE DESC";
					else
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.PICKUP=? and COURSE=? order by QRY_DATE DESC";
				    List<LiveDemo> list = new ArrayList<LiveDemo>();
				    String id="";
				    LiveDemo ld = null;
				    try {
				    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
						 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						  java.util.Date  date = df.parse(d);
						  java.util.Date  date3 = df.parse(td);
						  Calendar cal = Calendar.getInstance();
							 cal.setTime(date);
							 Calendar cal2 = Calendar.getInstance();
							 cal2.setTime(date3);
							 cal.add(Calendar.DATE, -1);
								java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
								java.sql.Date d3=new java.sql.Date(date3.getTime());
								String r="09:00:00";
								String r2="09:00:00";
								if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
								{
									r="08:00:00";
									
								}
								if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
								{
									r2="08:00:00";
									
								}
								connection = getConnection();
					         ps = connection.prepareStatement(query);
					         ps.setString(1, d2+" "+r);
							 ps.setString(2, d3+" "+r2);
							 ps.setString(3, "n");
							 ps.setString(4, "n");
							 ps.setString(5, "India");
							 ps.setString(6, pickup);
							 ps.setString(7, course);
							 ResultSet rs = ps.executeQuery();
							 while (rs.next()) {
					            ld = new LiveDemo();
					            id=" ";
								if(rs.getString("STUDENT_ID")!=null)
									id=rs.getString("STUDENT_ID");   
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
								String time="",ld_date="";
								 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								 ld_date= df2.format(date2);
							        DateFormat df3 = new SimpleDateFormat("HH:mm");
							        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							        time=df3.format(date2);
							        ld.setStudentId(id);
						            ld.setStudentName(rs.getString("Name"));
						            ld.setEmail(rs.getString("L.Email"));
						            ld.setPhone(rs.getString("Phone_No"));
						            ld.setQuery(rs.getString("L.Query"));
						            ld.setLastQuery(rs.getString("Q.Query"));
						            ld.setDateTime(ld_date+" "+time);
						            ld.setQueryId(rs.getString("Q_ID"));
						            ld.setECorrect(rs.getString("E_CORRECT"));
						            ld.setMCorrect(rs.getString("M_CORRECT"));
						            ld.setAttended(rs.getString("ATTENDED"));
						            ld.setPickup(rs.getString("L.PICKUP"));
						            if(rs.getString("Q.INTEREST_LEVEL")==null)
						            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
						            else
						            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
						            ld.setLpId(rs.getString("LP_ID"));
						            ld.setCountry(rs.getString("COUNTRY"));
						            ld.setCourse(rs.getString("COURSE"));
						            ld.setCookieId(rs.getString("COOKIE_ID"));
						            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
						            list.add(ld);
							 }
				        rs.close();
				         
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }catch (ParseException e) {
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
	
				//Country ,pickup,Attended
				public List<LiveDemo> viewDemoByCountryPickupAttended(String attended,String pickup,String d, String td,String country) //1
				{
					String query="";
					if(country.equalsIgnoreCase("India"))
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and L.PICKUP=? and L.ATTENDED=? order by QRY_DATE DESC";
					else
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.PICKUP=? and L.ATTENDED=? order by QRY_DATE DESC";
				    List<LiveDemo> list = new ArrayList<LiveDemo>();
				    String id="";
				    LiveDemo ld = null;
				    try {
				    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
						 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						  java.util.Date  date = df.parse(d);
						  java.util.Date  date3 = df.parse(td);
						  Calendar cal = Calendar.getInstance();
							 cal.setTime(date);
							 Calendar cal2 = Calendar.getInstance();
							 cal2.setTime(date3);
							 cal.add(Calendar.DATE, -1);
								java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
								java.sql.Date d3=new java.sql.Date(date3.getTime());
								String r="09:00:00";
								String r2="09:00:00";
								if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
								{
									r="08:00:00";
									
								}
								if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
								{
									r2="08:00:00";
									
								}
								connection = getConnection();
					         ps = connection.prepareStatement(query);
					         ps.setString(1, d2+" "+r);
							 ps.setString(2, d3+" "+r2);
							 ps.setString(3, "n");
							 ps.setString(4, "n");
							 ps.setString(5, "India");
							 ps.setString(6, pickup);
							 ps.setString(7, attended);
							 ResultSet rs = ps.executeQuery();
							 while (rs.next()) {
					            ld = new LiveDemo();
					            id=" ";
								if(rs.getString("STUDENT_ID")!=null)
									id=rs.getString("STUDENT_ID");   
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
								String time="",ld_date="";
								 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								 ld_date= df2.format(date2);
							        DateFormat df3 = new SimpleDateFormat("HH:mm");
							        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							        time=df3.format(date2);
							        ld.setStudentId(id);
						            ld.setStudentName(rs.getString("Name"));
						            ld.setEmail(rs.getString("L.Email"));
						            ld.setPhone(rs.getString("Phone_No"));
						            ld.setQuery(rs.getString("L.Query"));
						            ld.setLastQuery(rs.getString("Q.Query"));
						            ld.setDateTime(ld_date+" "+time);
						            ld.setQueryId(rs.getString("Q_ID"));
						            ld.setECorrect(rs.getString("E_CORRECT"));
						            ld.setMCorrect(rs.getString("M_CORRECT"));
						            ld.setAttended(rs.getString("ATTENDED"));
						            ld.setPickup(rs.getString("L.PICKUP"));
						            if(rs.getString("Q.INTEREST_LEVEL")==null)
						            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
						            else
						            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
						            ld.setLpId(rs.getString("LP_ID"));
						            ld.setCountry(rs.getString("COUNTRY"));
						            ld.setCourse(rs.getString("COURSE"));
						            ld.setCookieId(rs.getString("COOKIE_ID"));
						            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
						            list.add(ld);
							 }
				        rs.close();
				         
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }catch (ParseException e) {
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
				//Country ,pickup,course,Attended
				public List<LiveDemo> viewDemoByCountryPickupCourseAttended(String course,String attended, String pickup,String d, String td,String country) //1
				{
					String query="";
					if(country.equalsIgnoreCase("India"))
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and L.PICKUP=? and L.ATTENDED=? and COURSE=? order by QRY_DATE DESC";
					else
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.PICKUP=? and L.ATTENDED=? and COURSE=? order by QRY_DATE DESC";
				    List<LiveDemo> list = new ArrayList<LiveDemo>();
				    String id="";
				    LiveDemo ld = null;
				    try {
				    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
						 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						  java.util.Date  date = df.parse(d);
						  java.util.Date  date3 = df.parse(td);
						  Calendar cal = Calendar.getInstance();
							 cal.setTime(date);
							 Calendar cal2 = Calendar.getInstance();
							 cal2.setTime(date3);
							 cal.add(Calendar.DATE, -1);
								java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
								java.sql.Date d3=new java.sql.Date(date3.getTime());
								String r="09:00:00";
								String r2="09:00:00";
								if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
								{
									r="08:00:00";
									
								}
								if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
								{
									r2="08:00:00";
									
								}
								connection = getConnection();
					         ps = connection.prepareStatement(query);
					         ps.setString(1, d2+" "+r);
							 ps.setString(2, d3+" "+r2);
							 ps.setString(3, "n");
							 ps.setString(4, "n");
							 ps.setString(5, "India");
							 ps.setString(6, pickup);
							 ps.setString(7, attended);
							 ps.setString(8, course);
							 ResultSet rs = ps.executeQuery();
							 while (rs.next()) {
					            ld = new LiveDemo();
					            id=" ";
								if(rs.getString("STUDENT_ID")!=null)
									id=rs.getString("STUDENT_ID");   
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
								String time="",ld_date="";
								 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								 ld_date= df2.format(date2);
							        DateFormat df3 = new SimpleDateFormat("HH:mm");
							        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							        time=df3.format(date2);
							        ld.setStudentId(id);
						            ld.setStudentName(rs.getString("Name"));
						            ld.setEmail(rs.getString("L.Email"));
						            ld.setPhone(rs.getString("Phone_No"));
						            ld.setQuery(rs.getString("L.Query"));
						            ld.setLastQuery(rs.getString("Q.Query"));
						            ld.setDateTime(ld_date+" "+time);
						            ld.setQueryId(rs.getString("Q_ID"));
						            ld.setECorrect(rs.getString("E_CORRECT"));
						            ld.setMCorrect(rs.getString("M_CORRECT"));
						            ld.setAttended(rs.getString("ATTENDED"));
						            ld.setPickup(rs.getString("L.PICKUP"));
						            if(rs.getString("Q.INTEREST_LEVEL")==null)
						            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
						            else
						            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
						            ld.setLpId(rs.getString("LP_ID"));
						            ld.setCountry(rs.getString("COUNTRY"));
						            ld.setCourse(rs.getString("COURSE"));
						            ld.setCookieId(rs.getString("COOKIE_ID"));
						            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
						            list.add(ld);
							 }
				        rs.close();
				         
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }catch (ParseException e) {
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
				
				//Country ,course attended
				public List<LiveDemo> viewDemoByCountryCourseAttended(String course,String attended,String d, String td,String country) //1
				{
					String query="";
					if(country.equalsIgnoreCase("India"))
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY=? and L.ATTENDED=? and COURSE=? order by QRY_DATE DESC";
					else
						query="select * from lms_live_demo as L left outer join (select * from query_phonecall_response group by RESPONSE_ID order by RSP_DATE desc) as Q on Q.EMAIL=L.EMAIL where QRY_DATE>=? and QRY_DATE<=? and (E_CORRECT!=? or M_CORRECT!=?) and COUNTRY!=? and L.ATTENDED=? and COURSE=? order by QRY_DATE DESC";
				    List<LiveDemo> list = new ArrayList<LiveDemo>();
				    String id="";
				    LiveDemo ld = null;
				    try {
				    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
						 // df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						  java.util.Date  date = df.parse(d);
						  java.util.Date  date3 = df.parse(td);
						  Calendar cal = Calendar.getInstance();
							 cal.setTime(date);
							 Calendar cal2 = Calendar.getInstance();
							 cal2.setTime(date3);
							 cal.add(Calendar.DATE, -1);
								java.sql.Date d2=new java.sql.Date(cal.getTime().getTime());
								java.sql.Date d3=new java.sql.Date(date3.getTime());
								String r="09:00:00";
								String r2="09:00:00";
								if((cal.get(Calendar.MONTH)==10 && cal.get(Calendar.DATE)>1) || (cal.get(Calendar.MONTH)==2 && cal.get(Calendar.DATE)<9) || cal.get(Calendar.MONTH)>10 || cal.get(Calendar.MONTH)<2)
								{
									r="08:00:00";
									
								}
								if((cal2.get(Calendar.MONTH)==10 && cal2.get(Calendar.DATE)>1) || (cal2.get(Calendar.MONTH)==2 && cal2.get(Calendar.DATE)<9) || cal2.get(Calendar.MONTH)>10 || cal2.get(Calendar.MONTH)<2)
								{
									r2="08:00:00";
									
								}
								connection = getConnection();
					         ps = connection.prepareStatement(query);
					         ps.setString(1, d2+" "+r);
							 ps.setString(2, d3+" "+r2);
							 ps.setString(3, "n");
							 ps.setString(4, "n");
							 ps.setString(5, "India");
							 ps.setString(6, attended);
							 ps.setString(7, course);
							 ResultSet rs = ps.executeQuery();
							 while (rs.next()) {
					            ld = new LiveDemo();
					            id=" ";
								if(rs.getString("STUDENT_ID")!=null)
									id=rs.getString("STUDENT_ID");   
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								format1.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));
								java.util.Date  date2 = format1.parse(rs.getString("QRY_DATE"));
								DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy EEE");
								String time="",ld_date="";
								 df2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
								 ld_date= df2.format(date2);
							        DateFormat df3 = new SimpleDateFormat("HH:mm");
							        df3.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
							        time=df3.format(date2);
							        ld.setStudentId(id);
						            ld.setStudentName(rs.getString("Name"));
						            ld.setEmail(rs.getString("L.Email"));
						            ld.setPhone(rs.getString("Phone_No"));
						            ld.setQuery(rs.getString("L.Query"));
						            ld.setLastQuery(rs.getString("Q.Query"));
						            ld.setDateTime(ld_date+" "+time);
						            ld.setQueryId(rs.getString("Q_ID"));
						            ld.setECorrect(rs.getString("E_CORRECT"));
						            ld.setMCorrect(rs.getString("M_CORRECT"));
						            ld.setAttended(rs.getString("ATTENDED"));
						            ld.setPickup(rs.getString("L.PICKUP"));
						            if(rs.getString("Q.INTEREST_LEVEL")==null)
						            	ld.setInterestedLevel(rs.getString("L.INTEREST_LEVEL"));
						            else
						            	ld.setInterestedLevel(rs.getString("Q.INTEREST_LEVEL"));
						            ld.setLpId(rs.getString("LP_ID"));
						            ld.setCountry(rs.getString("COUNTRY"));
						            ld.setCourse(rs.getString("COURSE"));
						            ld.setCookieId(rs.getString("COOKIE_ID"));
						            ld.setCountryCode(rs.getString("L.COUNTRY_CODE"));
						            list.add(ld);
							 }
				        rs.close();
				         
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }catch (ParseException e) {
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
				
	
	 public int getNoOfRecords() {
	        return noOfRecords;
	    }

}
