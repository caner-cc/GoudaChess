package testpack;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persist.Kysymykset;
import persist.Vastaukset;

public class EditC extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		// Get candidate id as int from ViewC.java (Candidate table) when 'edit' link is clicked
		int id = Integer.parseInt(request.getParameter("id"));
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		emf = Persistence.createEntityManagerFactory("vaalikones");
		em = emf.createEntityManager();
		
		// Get list of answers for current candidate 
		Query q = em.createQuery("SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId ='" + id + "'");
		List<Vastaukset> vastaukset = q.getResultList();
	
		// Get all questions
		Query q2 = em.createQuery("SELECT k FROM Kysymykset k");
		List<Kysymykset> kysymykset = q2.getResultList();
		
		// Forward lists to editC.jsp
		request.setAttribute("vastaukset", vastaukset);
		request.setAttribute("kysymykset", kysymykset);
		request.getRequestDispatcher("/editC.jsp").forward(request, response);
	}
}
