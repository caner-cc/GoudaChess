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
		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		EntityManagerFactory emf = null;
		EntityManager em = null;
		emf = Persistence.createEntityManagerFactory("vaalikones");
		em = emf.createEntityManager();
		Query q = em.createQuery("SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId ='" + id + "'");
		
		List<Vastaukset> vastaukset = q.getResultList();
	
		Query q2 = em.createQuery("SELECT k FROM Kysymykset k");
		List<Kysymykset> kysymykset = q2.getResultList();
		
		request.setAttribute("vastaukset", vastaukset);
		request.setAttribute("kysymykset", kysymykset);
		request.getRequestDispatcher("/editC.jsp").forward(request, response);
	}
}
