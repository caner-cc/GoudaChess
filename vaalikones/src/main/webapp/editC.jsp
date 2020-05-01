<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.*,vaalikone.Vaalikone,persist.*,testpack.CandidateMethods, java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="style.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>Edit Candidate</title>
<script>
	function sendData(form) {
		var radios = document.getElementsByClassName('radio');
		
		var vastauksetList = [];
		for(var i = 0; i < radios.length; i++) {
			if(radios[i].checked) {
				
				var values = [];
				values.push(document.getElementById("ehdokasId").value); //ehdokas_id
				values.push(radios[i].name);							 //kysymys_id
				values.push(radios[i].value);							 //vastaus
				
				vastauksetList.push(values);
			}
		}
		console.log(vastauksetList);
		
		var ehdokkaat = new Object();
		ehdokkaat.ehdokasId = document.getElementById("ehdokasId").value;
		ehdokkaat.sukunimi = document.getElementById("Sukunimi").value;
		ehdokkaat.etunimi = document.getElementById("Etunimi").value;
		ehdokkaat.puolue = document.getElementById("Puolue").value;
		ehdokkaat.kotipaikkakunta = document.getElementById("Kotipaikkakunta").value;
		ehdokkaat.ika = document.getElementById("Ika").value;
		
		console.log(ehdokkaat);
		
		var jsonVastaukset = JSON.stringify(vastauksetList);
		var jsonEhdokkaat = JSON.stringify(ehdokkaat);
		var xhttp = new XMLHttpRequest();		
		xhttp.open("PUT", "/rest/CandidateService/setV", true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send(jsonVastaukset);
		console.log(jsonVastaukset);

		var xhttp2 = new XMLHttpRequest();
		xhttp2.onreadystatechange = function() {
	        if (this.readyState == 4) {
		        document.getElementById("response").innerHTML = 'Your changes have been saved!';
	        }
		}
		xhttp2.open("PUT", "/rest/CandidateService/setC", true);
		xhttp2.setRequestHeader("Content-type", "application/json");
		xhttp2.send(jsonEhdokkaat);
		console.log(jsonEhdokkaat);
	}
</script>
</head>
<body>
<div id="container2">
	<h1>Update Ehdokkaat</h1>
	<%
	String sid = request.getParameter("id");
	int id = Integer.parseInt(sid);
	Ehdokkaat e = CandidateMethods.getCandidateById(id);
	%>
	<h3>Personal Information:</h3>
	<form>
	<input id='ehdokasId' type='hidden' name='id' value='<%=e.getEhdokasId()%>' />
		<table class="table table-hover table-sm">
			<tr>
				<td class="td-width2">Sukunimi:</td>
				<td><input id='Sukunimi' type='text' name='Sukunimi' value='<%=e.getSukunimi()%>' required /></td>
			</tr>
			<tr>
				<td class="td-width2">Etunimi:</td>
				<td><input id='Etunimi' type='text' name='Etunimi' value='<%=e.getEtunimi()%>' required /></td>
			</tr>
			<tr>
				<td class="td-width2">Puolue:</td>
				<td><input id='Puolue' type='text' name='Puolue' value='<%=e.getPuolue()%>' required /></td>
			</tr>
			<tr>
				<td class="td-width2">Kotipaikkakunta:</td>
				<td><input id='Kotipaikkakunta' type='text' name='Kotipaikkakunta' value='<%=e.getKotipaikkakunta()%>' required /></td>
			</tr>
			<tr>
				<td class="td-width2">Ika:</td>
				<td><input id='Ika' type='text' name='Ika' value=' <%=e.getIka()%>' required /></td>
			</tr>
		</table>
		<h3>Answers:</h3>
		<pre style="font-size: 75%; font-family: Gotham;">1=Täysin eri mieltä  2=Osittain eri mieltä  3=En osaa sanoa  4=Osittain samaa mieltä  5=Täysin samaa mieltä</pre>
		<%
		List<Kysymykset> kysymykset = (List<Kysymykset>) request.getAttribute("kysymykset");
		List<Vastaukset> vastaukset = (List<Vastaukset>) request.getAttribute("vastaukset");

		Iterator<Vastaukset> vastaukset_iterator = vastaukset.iterator();
		Iterator<Kysymykset> kysymykset_iterator = kysymykset.iterator();
		
		Kysymykset kysymys = new Kysymykset();
		Vastaukset vastaus = new Vastaukset();
		int kysymysId;
		int vastaus_kysymysId;
		int vastaus_value;
		%><table class="table table-hover table-sm"><%
		while(kysymykset_iterator.hasNext()){
			// Get next question
			kysymys = kysymykset_iterator.next();
			kysymysId = kysymys.getKysymysId();

			// Loop through vastaukset if there were any
			if (vastaukset_iterator.hasNext()) {
				// Get next answer
				vastaus = vastaukset_iterator.next();
				vastaus_kysymysId = vastaus.getVastauksetPK().getKysymysId();
				vastaus_value = vastaus.getVastaus();
				
				// Check if ids match, if they do, also print out a checked radio button.
				if (kysymysId == vastaus_kysymysId) {
					System.out.println("match: kysymysId was: " + String.valueOf(kysymysId) + " <-> " + String.valueOf(vastaus_kysymysId) + " vastaus_kysymysId was");
					%><tr>
					<td class="td-width"><%=kysymysId + ". " + kysymys.getKysymys() %></td>
					<td><fieldset id='<%=kysymysId%>'> <%
					for(int i = 1; i < 6; i++) {
			        	if(vastaus_value == i){
			        		%><label class='plabel'><%=i%></label><input class='radio' type='radio' name='<%=kysymysId%>' value='<%=i%>' checked/><% }
				        else {
				        	%><label class='plabel'><%=i%></label><input class='radio' type="radio" name='<%=kysymysId%>' value='<%=i%>'/><%
				        }
			        }%></fieldset></td></tr><%
				}
				else {
					// Run an inner while loop to fill in empty questions and match up with an answer
					while (kysymysId < vastaus_kysymysId) {
						System.out.println("nested while: kysymysId was: " + String.valueOf(kysymysId) + " <-> " + String.valueOf(vastaus_kysymysId) + " vastaus_kysymysId was");
						// ids didn't match, so print out questions without answers until we actually match again.
						%><tr>
						<td class="td-width"><%=kysymysId + ". " + kysymys.getKysymys() %></td>
						<td><fieldset id='<%=kysymysId%>'> <%
						for(int i = 1; i < 6; i++) {
							%><label class='plabel'><%=i%></label><input class='radio' type="radio" name='<%=kysymysId%>' value='<%=i%>'/><%
						}%></fieldset></td></tr><%

						// Get next question, and hopefully reach a match
						kysymys = kysymykset_iterator.next();
						kysymysId = kysymys.getKysymysId();
					}
					System.out.println("after nested while: kysymysId was: " + String.valueOf(kysymysId) + " <-> " + String.valueOf(vastaus_kysymysId) + " vastaus_kysymysId was");
					// After nested while loop finished and we've reached a match, print a checked radio button
					%><tr>
					<td class="td-width"><%=kysymysId + ". " + kysymys.getKysymys() %></td>
					<td><fieldset id='<%=kysymysId%>'> <%
					for(int i = 1; i < 6; i++) {
			        	if(vastaus_value == i){
			        		%><label class='plabel'><%=i%></label><input class='radio' type='radio' name='<%=kysymysId%>' value='<%=i%>' checked/><% }
				        else {
				        	%><label class='plabel'><%=i%></label><input class='radio' type="radio" name='<%=kysymysId%>' value='<%=i%>'/><%
				        }
			        }%></fieldset></td></tr><%
				}
			}
			else {
				// We either didn't have any vastaukset at all, or we've run out of them
				// so from this point on, print out questions without any checked radio buttons
				System.out.println("No vastaus anymore: kysymysId was: " + String.valueOf(kysymysId));
				%><tr>
				<td class="td-width"><%=kysymysId + ". " + kysymys.getKysymys() %></td>
				<td><fieldset id='<%=kysymysId%>'> <%
				for(int i = 1; i < 6; i++) {
					%><label class='plabel'><%=i%></label><input class='radio' type="radio" name='<%=kysymysId%>' value='<%=i%>'/><%
				}
				%></fieldset></td></tr><%
			}
		}%>
		</table>
		
		<table>
			<tr>
				<td><input type='button' name='ok' value='Edit &amp; Save' onclick='sendData();' /></td>
				<td id='response'></td>
			</tr>
		</table>
	</form>
	<form action="/ViewC" method="get">
		<input type='submit' name='ok' value='Back'/>
	</form>
</div>
</body>
</html>