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
					"<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css' integrity='sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh' crossorigin='anonymous'>\n" +
					"<script type='text/javascript' src='index.js' ></script>\n" +
					"</head>");
		out.println("<div id='container2'>");
		out.println("<table style='width: 98%'><tr>" +
					"<td><h1 style='margin-top: 5px;'>Candidates List</h1><td>" + 
					"<td><form action='Table2.jsp' method='get'>\n" + 
				    "    <input id='submitnappi' type='submit' name='ok' value='Add new Candidate'/>\n" + 
				    "</form></td>" + 
				    "<td><form action='/EditQ' method='get'>\n" + 
			    	"    <input id='submitnappi' type='submit' name='ok' value='Edit questions'/>\n" + 
			    	"</form></td>" +
				    "<td style='padding-right: 100px; padding-left: 20px;'>\n" + 
			    	"    <input type='text' id='filter' onkeyup='candidateFilter()' placeholder='Search for names...'>\n" + 
			    	"</td>" +
				    "<td><form action='index.html' method='get'>\n" + 
			    	"    <input id='submitnappi' type='submit' name='ok' value='Back to main'/>\n" + 
			    	"</form></td>" +
			    	"</tr></table>");		
		List<Ehdokkaat> list=CandidateMethods.getAllCandidates();
		
		out.print("<table class='table table-hover' id='myTable'>");
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
