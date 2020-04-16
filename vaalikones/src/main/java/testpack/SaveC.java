package testpack;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveC extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String Sukunimi=request.getParameter("Sukunimi");
		String Etunimi=request.getParameter("Etunimi");
		String Puolue=request.getParameter("Puolue");
		String Kotipaikkakunta=request.getParameter("Kotipaikkakunta");
		String Ika=request.getParameter("Ika");
		
		Candidate e=new Candidate();
		e.setSukunimi(Sukunimi);
		e.setEtunimi(Etunimi);
		e.setPuolue(Puolue);
		e.setKotipaikkakunta(Kotipaikkakunta);
		e.setIka(Ika);
		
		int status=CandidateMethods.save(e);
		if(status>0){
			out.print("<p>Record saved successfully!</p>");
			out.print("<form action=\"ViewC\">\r\n" + 
					"			<input style=\"margin-top:110px\" id=\"submitnappi\" type=\"submit\" value=\"Back\" name=\"btnAloita\" />\r\n" + 
					"		</form>");
			request.getRequestDispatcher("/ViewC").include(request, response);
			
		}else{
			out.println("Sorry! unable to save record");
		}
		
		out.close();
	}

}
