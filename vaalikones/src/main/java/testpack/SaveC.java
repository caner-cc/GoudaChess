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
			out.print("<p>Record saved successfully!</p>");
			out.print("<form action='ViewC'>\r\n" + 
					  "    <input style='margin-top:110px' type='submit' value='Back' name='btnAloita' />\r\n" + 
					  "</form>");
			request.getRequestDispatcher("/ViewC").include(request, response);
			
		}else{
			out.println("Sorry! unable to save record:");
			out.println("<br>");
			out.println(exception);
		}
		
		out.close();
	}

}
