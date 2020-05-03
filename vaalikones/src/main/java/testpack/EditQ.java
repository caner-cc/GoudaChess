package testpack;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;

import persist.Kysymykset;

/**
 * Servlet implementation class EditQ
 */
public class EditQ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
						
		String deleteId = request.getParameter("deleteId");	
		if (deleteId != null) {
			if(deleteQ(deleteId)) {
				System.out.println("Delete successful");
			}
		}
		
		String uri = "http://127.0.0.1:8080/rest/QuestionService/getall";
		
		Client client = ClientBuilder.newClient();
		WebTarget wt = client.target(uri);
		Builder b = wt.request();
		
		GenericType<List<Kysymykset>> generList = new GenericType<List<Kysymykset>>() {};
		
		List<Kysymykset> returnedList = b.get(generList);
		request.setAttribute("kysymykset", returnedList);
		request.getRequestDispatcher("/editQ.jsp").forward(request, response);
	}
	
	private boolean deleteQ(String deleteId) {		
		String uri = "http://127.0.0.1:8080/rest/QuestionService/delete/" + deleteId;
		Client client = ClientBuilder.newClient();
		WebTarget wt = client.target(uri);
		Builder b = wt.request();

		boolean ok = b.delete(Boolean.class);
		return ok;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String kysymys = request.getParameter("question");
		System.out.println("[EditQ: doPost] kysymys "+ kysymys);

		if(!"".equals(kysymys)){
			String uri = "http://127.0.0.1:8080/rest/QuestionService/saveQ";
			Kysymykset k = new Kysymykset(kysymys);
			Client client = ClientBuilder.newClient();
			WebTarget wt = client.target(uri);
			Builder b = wt.request();		
			Entity<Kysymykset> e = Entity.entity(k, MediaType.APPLICATION_JSON);
			
			b.post(e);	
		}

		doGet(request, response);
	}

}
