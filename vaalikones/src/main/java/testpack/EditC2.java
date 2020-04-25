package testpack;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditC2 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		String Sukunimi=request.getParameter("Sukunimi");
		String Etunimi=request.getParameter("Etunimi");
		String Puolue=request.getParameter("Puolue");
		String Kotipaikkakunta=request.getParameter("Kotipaikkakunta");
		String Ika=request.getParameter("Ika");
		
		Candidate e=new Candidate();
		e.setId(id);
		e.setSukunimi(Sukunimi);
		e.setEtunimi(Etunimi);
		e.setPuolue(Puolue);
		e.setKotipaikkakunta(Kotipaikkakunta);
		e.setIka(Ika);
		
		CandidateMethods.update(e);
		Map<String, String> updateResult = CandidateMethods.update(e);
		int status = Integer.parseInt(updateResult.get("status"));
		String exception = updateResult.get("exception");
		
		if(status > 0){
			response.sendRedirect("ViewC");
		}else{
			out.println("Sorry! unable to update record:");
			out.println("<br>");
			out.println(exception);
		}
		
		out.close();
	}

}
