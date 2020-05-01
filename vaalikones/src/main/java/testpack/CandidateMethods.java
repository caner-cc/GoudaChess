package testpack;

import java.util.*;

import persist.Ehdokkaat;

import java.sql.*;

public class CandidateMethods {

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaalikone", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

		/**
		 * This method records new candidates in the database, given by the user in Table2.jsp
		 * @param e, this is an Ehdokkaat object what will be created in SaveC.java
		 *           according to the data from the Table2.jsp
		 */
	public static Map<String, String> save(Ehdokkaat e) {
		Integer status = 0;
		String exception = "";
		try {
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into ehdokkaat(Sukunimi,Etunimi,Puolue,Kotipaikkakunta,Ika) values (?,?,?,?,?)");
			ps.setString(1, e.getSukunimi());
			ps.setString(2, e.getEtunimi());
			ps.setString(3, e.getPuolue());
			ps.setString(4, e.getKotipaikkakunta());
			ps.setInt(5, e.getIka());
			status = ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			exception = ex.getMessage();
		}

		Map<String, String> updateResult = new HashMap<String, String>();
		updateResult.put("status", status.toString());
		updateResult.put("exception", exception);
		return updateResult;
	}

		/**
		 * This method records the changes in the database, given by the user in editC.jsp
		 * @param e, this is an Ehdokkaat object, it will be replaced 
		 * 			 in CandidateService.java with the new Ehdokkaat object, 
		 * 			 what will be recieved from the editC.jsp
		 */
	public static Map<String, String> update(Ehdokkaat e) {
			Integer status = 0;
		String exception = "";
		try {
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"update ehdokkaat set Sukunimi=?,Etunimi=?,Puolue=?,Kotipaikkakunta=?, Ika=? where ehdokas_id=?");
			ps.setString(1, e.getSukunimi());
			ps.setString(2, e.getEtunimi());
			ps.setString(3, e.getPuolue());
			ps.setString(4, e.getKotipaikkakunta());
			ps.setInt(5, e.getIka());
			ps.setInt(6, e.getEhdokasId());

			status = ps.executeUpdate();

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			exception = ex.getMessage();
		}

		Map<String, String> updateResult = new HashMap<String, String>();
		updateResult.put("status", status.toString());
		updateResult.put("exception", exception);
		System.out.println(updateResult);
		return updateResult;
	}

		/**
		 * This method will update and insert into the database the answers given by the user in editC.jsp form,
		 * according to the arraylist recieved
	     * @param v, this is a multidimension array which contains ehdokkaasId, kysymysId, vastaus in it's arrays,
		 *           what will be recieved from editC.jsp
	     *           this method is called in the CandidateService.java
		 */
	public static Map<String, String> updateV(ArrayList<List<Integer>> values) {
		Integer status = 0;
		String exception = "";
		try {
			int ehdokas_id = values.get(0).get(0);
			
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps1 = con.prepareStatement("delete from vastaukset where ehdokas_Id=?");
			ps1.setInt(1, ehdokas_id);
			status = ps1.executeUpdate();
			System.out.println("Deleted all vastaukset for ehdokas_id " + String.valueOf(ehdokas_id));
			
			PreparedStatement ps2 = con.prepareStatement(
					"insert into vastaukset(ehdokas_id, kysymys_id, vastaus, kommentti) values (?,?,?,?)");
			for (int i = 0; i < values.size(); i++) {
				int kysymys_id = values.get(i).get(1);
				int vastaus = values.get(i).get(2);
				String kommentti = "ehdokkaan " + String.valueOf(ehdokas_id) + " vastaus kysymykseen " + String.valueOf(kysymys_id); 
				
				ps2.setInt(1, ehdokas_id);
				ps2.setInt(2, kysymys_id);
				ps2.setInt(3, vastaus);
				ps2.setString(4, kommentti);
				ps2.addBatch();
			}
			try {
				ps2.executeBatch();
				System.out.println("successfully inserted into database");
				con.close();
			} catch (Exception ex) {
				con.close();
				ex.printStackTrace();
				exception = ex.getMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			exception = ex.getMessage();
		}

		Map<String, String> updateResult = new HashMap<String, String>();
		updateResult.put("status", status.toString());
		updateResult.put("exception", exception);
		System.out.println(updateResult);
		return updateResult;
	}

		/**
		 * 
		 * @param id, this is the id of the candidate, that will be removed from the database
		 */ 
	public static int delete(int id) {
		int status = 0;
		try {
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from ehdokkaat where ehdokas_id=?");
			ps.setInt(1, id);
			status = ps.executeUpdate();

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

		/**
		 * 
		 * Returns the proper candidate by the id given as parameter
		 * @param id
		 */
	public static Ehdokkaat getCandidateById(int id) {
		Ehdokkaat e = new Ehdokkaat();

		try {
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ehdokkaat where ehdokas_id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				e.setEhdokasId(rs.getInt(1));
				e.setSukunimi(rs.getString(2));
				e.setEtunimi(rs.getString(3));
				e.setPuolue(rs.getString(4));
				e.setKotipaikkakunta(rs.getString(5));
				e.setIka(rs.getInt(6));
			}
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return e;
	}

		/**
		 * This method is used to return list (id, name, party, city, age) of all the candidates 
		 * can be found in database, this returned list is processed by ViewC.java
		 */ 
	public static List<Ehdokkaat> getAllCandidates() {
		List<Ehdokkaat> list = new ArrayList<Ehdokkaat>();

		try {
			Connection con = CandidateMethods.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ehdokkaat");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Ehdokkaat e = new Ehdokkaat();
				e.setEhdokasId(rs.getInt(1));
				e.setSukunimi(rs.getString(2));
				e.setEtunimi(rs.getString(3));
				e.setPuolue(rs.getString(4));
				e.setKotipaikkakunta(rs.getString(5));
				e.setIka(rs.getInt(6));
				list.add(e);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
