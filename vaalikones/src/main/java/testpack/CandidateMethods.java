package testpack;
import java.util.*;
import java.sql.*;

public class CandidateMethods {

	public static Connection getConnection(){
		Connection con=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaalikone","root","root");
		}catch(Exception e){System.out.println(e);}
		return con;
	}
	public static int save(Candidate e){
		int status=0;
		try{
			Connection con=CandidateMethods.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into ehdokkaat(Sukunimi,Etunimi,Puolue,Kotipaikkakunta,Ika) values (?,?,?,?,?)");
			ps.setString(1,e.getSukunimi());
			ps.setString(2,e.getEtunimi());
			ps.setString(3,e.getPuolue());
			ps.setString(4,e.getKotipaikkakunta());
			ps.setString(5,e.getIka());			
			status=ps.executeUpdate();			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	public static int update(Candidate e){
		int status=0;
		try{
			Connection con=CandidateMethods.getConnection();
			PreparedStatement ps=con.prepareStatement("update ehdokkaat set Sukunimi=?,Etunimi=?,Puolue=?,Kotipaikkakunta=?, Ika=? where ehdokas_id=?");
			ps.setString(1,e.getSukunimi());
			ps.setString(2,e.getEtunimi());
			ps.setString(3,e.getPuolue());
			ps.setString(4,e.getKotipaikkakunta());
			ps.setString(5,e.getIka());
			ps.setInt(6,e.getId());
			
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	public static int delete(int id){
		int status=0;
		try{
			Connection con=CandidateMethods.getConnection();
			PreparedStatement ps=con.prepareStatement("delete from ehdokkaat where ehdokas_id=?");
			ps.setInt(1,id);
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return status;
	}
	public static Candidate getEmployeeById(int id){
		Candidate e=new Candidate();
		
		try{
			Connection con=CandidateMethods.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from ehdokkaat where ehdokas_id=?");
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				e.setId(rs.getInt(1));
				e.setSukunimi(rs.getString(2));
				e.setEtunimi(rs.getString(3));
				e.setPuolue(rs.getString(4));
				e.setKotipaikkakunta(rs.getString(5));
				e.setIka(rs.getString(6));
			}
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return e;
	}
	public static List<Candidate> getAllEmployees(){
		List<Candidate> list=new ArrayList<Candidate>();
		
		try{
			Connection con=CandidateMethods.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from ehdokkaat");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Candidate e=new Candidate();
				e.setId(rs.getInt(1));
				e.setSukunimi(rs.getString(2));
				e.setEtunimi(rs.getString(3));
				e.setPuolue(rs.getString(4));
				e.setKotipaikkakunta(rs.getString(5));
				e.setIka(rs.getString(6));
				list.add(e);
			}
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return list;
	}
}
