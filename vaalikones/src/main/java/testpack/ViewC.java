package testpack;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persist.Ehdokkaat;

public class ViewC extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<head>\n" + 
					"<link href='style.css' rel='stylesheet' type='text/css'>\n" + 
					"<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css' integrity='sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh' crossorigin='anonymous'>" +
					"</head>");
		out.println("<div id='container2'>");
		out.println("<table><tr>" +
					"<td><h1 style='padding-right: 30px;'>Candidates List</h1><td>" + 
					"<td><form style='padding-right: 30px;' action='Table2.jsp' method='get'>\n" + 
				    "    <input type='submit' name='ok' value='Add new Candidate'/>\n" + 
				    "</td></form>" + 
				    "<td><form action='/EditQ' method='get'>\n" + 
			    	"    <input type='submit' name='ok' value='Edit questions'/>\n" + 
			    	"</td></form>" +
			    	"</tr></table>");		
		List<Ehdokkaat> list=CandidateMethods.getAllCandidates();
		
		out.print("<table class='table table-hover'>");
		out.print("<tr><th>Id</th><th>Sukunimi</th><th>Etunimi</th><th>Puolue</th><th>Kotipaikkakunta</th><th>Ika</th><th>Edit</th><th>Delete</th></tr>");
		for(Ehdokkaat e:list){
			out.print("<tr><td>"+e.getEhdokasId()+"</td><td>"+e.getSukunimi()+"</td><td>"+e.getEtunimi()+"</td><td>"+e.getPuolue()+"</td><td>"+e.getKotipaikkakunta()+"</td><td>"+e.getIka()+"</td><td><a href='EditC?id="+e.getEhdokasId()+"'>edit</a></td><td><a href='DeleteC?id="+e.getEhdokasId()+"'>delete</a></td></tr>");
		}
		out.print("</table>");
		
		out.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String updateStatus = (String) request.getAttribute("updateStatus");
		response.sendRedirect("ViewC");
	}
}
