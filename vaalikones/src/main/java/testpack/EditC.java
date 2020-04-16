package testpack;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditC extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<h1>Update Ehdokkaat</h1>");
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		
		Candidate e=CandidateMethods.getEmployeeById(id);
		
		out.print("<form action='EditC2' method='post'>");
		out.print("<table>");
		out.print("<tr><td></td><td><input type='hidden' name='id' value='"+e.getId()+"'/></td></tr>");
		out.print("<tr><td>Sukunimi:</td><td><input type='text' name='Sukunimi' value='"+e.getSukunimi()+"'/></td></tr>");
		out.print("<tr><td>Etunimi:</td><td><input type='text' name='Etunimi' value='"+e.getEtunimi()+"'/></td></tr>");
		out.print("<tr><td>Puolue:</td><td><input type='text' name='Puolue' value='"+e.getPuolue()+"'/></td></tr>");
		out.print("<tr><td>Kotipaikkakunta:</td><td><input type='text' name='Kotipaikkakunta' value='"+e.getKotipaikkakunta()+"'/></td></tr>");
		out.print("<tr><td>Ika:</td><td><input type='text' name='Ika' value='"+e.getIka()+"'/></td></tr>");		
		out.print("</td></tr>");
		out.print("<tr><td colspan='2'><input type='submit' value='Edit &amp; Save '/></td></tr>");
		out.print("</table>");
		out.print("</form>");
		
		out.close();
	}
}
