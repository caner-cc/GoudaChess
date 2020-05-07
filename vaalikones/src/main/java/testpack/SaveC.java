package testpack;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persist.Ehdokkaat;

public class SaveC extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		// Change back the message to "none" after getting error message caused by authentication failure
		request.getSession().setAttribute("success", "");
		request.getRequestDispatcher("Table.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String Sukunimi=request.getParameter("Sukunimi");
		String Etunimi=request.getParameter("Etunimi");
		String Puolue=request.getParameter("Puolue");
		String Kotipaikkakunta=request.getParameter("Kotipaikkakunta");
		Integer Ika = Integer.parseInt(request.getParameter("Ika"));
		
		Ehdokkaat e=new Ehdokkaat();
		e.setSukunimi(Sukunimi);
		e.setEtunimi(Etunimi);
		e.setPuolue(Puolue);
		e.setKotipaikkakunta(Kotipaikkakunta);
		e.setIka(Ika);
		
		Map<String, String> updateResult = CandidateMethods.save(e);
		int status = Integer.parseInt(updateResult.get("status"));
		String exception = updateResult.get("exception");

		if(status>0){
			request.getSession().setAttribute("success", "Record saved successfully!");
			request.getRequestDispatcher("/Table2.jsp").forward(request, response);
			//out.print("<p>Record saved successfully!</p>");
		}else{
			request.getSession().setAttribute("success", "Unable to save record!");
			request.getRequestDispatcher("/Table2.jsp").forward(request, response);
			out.println(exception);
		}
		
		out.close();
		doGet(request, response);
	}

}