<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.*,vaalikone.Vaalikone,persist.*, testpack.Candidate, testpack.CandidateMethods, java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Candidate</title>
</head>
<body>
	<h1>Update Ehdokkaat</h1>
	<%
		String sid = request.getParameter("id");
	int id = Integer.parseInt(sid);
	Candidate e = CandidateMethods.getEmployeeById(id);
	%>
	<h3>Personal Information:</h3>
	<form action='EditC2' method='post'>
		<input type='hidden' name='id' value='<%=e.getId()%>' required /><br>
		Sukunimi: <input type='text' name='Sukunimi' value='<%=e.getSukunimi()%>' required /><br> 
		Etunimi: <input type='text' name='Etunimi' value='<%=e.getEtunimi()%>' required /><br>
		Puolue: <input type='text' name='Puolue' value='<%=e.getPuolue()%>' required /><br> 
		Kotipaikkakunta: <input type='text' name='Kotipaikkakunta' value='<%=e.getKotipaikkakunta()%>' required /><br>
		Ika: <input type='text' name='Ika' value=' <%=e.getIka()%>' required /><br>
		<h3>Answers:</h3>
		<%
		List<Kysymykset> kysymykset = (List<Kysymykset>) request.getAttribute("kysymykset");
		List<Vastaukset> vastaukset = (List<Vastaukset>) request.getAttribute("vastaukset");

		Iterator<Vastaukset> vastaukset_iterator = vastaukset.iterator();
		Iterator<Kysymykset> kysymykset_iterator = kysymykset.iterator();
		
		// We can assume kysymys_ID will always start from 1.
		String kysymys = new String();
		while(kysymykset_iterator.hasNext()) {
			kysymys = kysymykset_iterator.next().getKysymys();
			if (vastaukset_iterator.hasNext()) {
				System.out.println("[if] kysymys was: " + kysymys); %>
				<h3><%= kysymys %></h3> <%
				int vastaus = vastaukset_iterator.next().getVastaus();
				System.out.println("vastaus was: " + vastaus); %> 
				<form> <%
				for(int i = 1; i < 6; i++) {
		        	if(vastaus == i){
		        		%><label><%=i%></label><input type="radio" name="vastaus" value="<%=i%>" checked/><% }
			        else {
			        	%><label><%=i%></label><input type="radio" name="vastaus" value="<%=i%>"/><%
			        }
		        } %>
		        </form><br /><%
			}
			else {
				System.out.println("[else] kysymys was: " + kysymys); %>
				<h3><%= kysymys %></h3>
				<form> <%
				for(int i = 1; i < 6; i++) { 
					%><label><%=i%></label><input type="radio" name="vastaus" value="<%=i%>"/><%
			    } %>
			    </form><br /><%
		    }
		} %>
		<input type='submit' value='Edit &amp; Save' /><br>
	</form>
</body>
</html>