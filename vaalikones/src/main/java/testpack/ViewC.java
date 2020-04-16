package testpack;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewC extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<a href='Table2.jsp'>Add New Candidate</a>");
		out.println("<h1>Candidates List</h1>");
		
		List<Candidate> list=CandidateMethods.getAllEmployees();
		
		out.print("<table border='1' width='100%'");
		out.print("<tr><th>Id</th><th>Sukunimi</th><th>Etunimi</th><th>Puolue</th><th>Kotipaikkakunta</th><th>Ika</th><th>Edit</th><th>Delete</th></tr>");
		for(Candidate e:list){
			out.print("<tr><td>"+e.getId()+"</td><td>"+e.getSukunimi()+"</td><td>"+e.getEtunimi()+"</td><td>"+e.getPuolue()+"</td><td>"+e.getKotipaikkakunta()+"</td><td>"+e.getIka()+"</td><td><a href='EditC?id="+e.getId()+"'>edit</a></td><td><a href='DeleteC?id="+e.getId()+"'>delete</a></td></tr>");
		}
		out.print("</table>");
		
		out.close();
	}
}
