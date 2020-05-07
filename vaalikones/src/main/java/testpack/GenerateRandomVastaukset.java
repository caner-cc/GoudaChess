package testpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persist.Ehdokkaat;
import persist.Kysymykset;
import java.sql.*;

public class GenerateRandomVastaukset extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		Connection con = CandidateMethods.getConnection();
		
		int status;
		// Delete all previous answers
		try {
			PreparedStatement ps1 = con.prepareStatement("delete from vastaukset");
			status = ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Get a list of candidates
		List<Ehdokkaat> ehdokkaat = new ArrayList<Ehdokkaat>();
		try {
			PreparedStatement ps2 = con.prepareStatement("select * from ehdokkaat");
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				Ehdokkaat e = new Ehdokkaat();
				e.setEhdokasId(rs2.getInt(1));
				e.setSukunimi(rs2.getString(2));
				e.setEtunimi(rs2.getString(3));
				e.setPuolue(rs2.getString(4));
				e.setKotipaikkakunta(rs2.getString(5));
				e.setIka(rs2.getInt(6));
				ehdokkaat.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Get a list of questions
		List<Kysymykset> kysymykset = new ArrayList<Kysymykset>();
		try {
			PreparedStatement ps3 = con.prepareStatement("select * from kysymykset");
			ResultSet rs3 = ps3.executeQuery();
			while (rs3.next()) {
				Kysymykset k = new Kysymykset();
				k.setKysymysId(rs3.getInt(1));
				k.setKysymys(rs3.getString(2));
				kysymykset.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Insert random answer for every candidate for every question
		try {
			Random r = new Random();
			int ehdokas_id, kysymys_id, randomVastaus;
			PreparedStatement ps4 = con.prepareStatement(
					"insert into vastaukset(ehdokas_id, kysymys_id, vastaus, kommentti) values (?,?,?,?)");

			// For all candidates
			for (int i = 0; i < ehdokkaat.size(); i++) {
				ehdokas_id = ehdokkaat.get(i).getEhdokasId();
				// For all questions
				for (int j = 0; j < kysymykset.size(); j++) {
					kysymys_id = kysymykset.get(j).getKysymysId();
					randomVastaus = r.nextInt(5) + 1; 
					
					// Set a comment based on the values above
					String kommentti = "ehdokkaan " + String.valueOf(ehdokas_id) 
										+ " vastaus kysymykseen "
									    + String.valueOf(kysymys_id);
					
					// Set the values for the question marks in ps4 PreparedStatement (?,?,?,?)
					ps4.setInt(1, ehdokas_id); 
					ps4.setInt(2, kysymys_id);
					ps4.setInt(3, randomVastaus);
					ps4.setString(4, kommentti);
					 
					ps4.addBatch();
				}
			}
			
			// Execute batch and close connection to database;
			ps4.executeBatch();
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Reload index.html at the end
		response.sendRedirect("index.html");
	}
}
