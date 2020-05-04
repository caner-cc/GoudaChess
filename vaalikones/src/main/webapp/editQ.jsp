<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,vaalikone.Vaalikone,persist.*,testpack.CandidateMethods, java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="style.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>Edit Questions</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
	function sendData() {
		var qAll = document.getElementsByClassName('makeWhite'); 
		console.log("this is qAll");
		console.log(qAll);
		var kysymyksetList = [];
		
		for(var i=0; i < qAll.length; i++){
			var kysymykset = new Object;
			kysymykset.kysymysId = qAll[i].id;	//kysymysId
			kysymykset.kysymys = qAll[i].innerText.trim(); //kysymys

			kysymyksetList.push(kysymykset);
		}
		console.log("kysymyksetList");
		console.log(kysymyksetList);
		var jsonKysymykset = JSON.stringify(kysymyksetList);
		var xhttp = new XMLHttpRequest();	
		
		xhttp.onreadystatechange = function() {
	        if (this.readyState == 4) {
		        document.getElementById("response").innerHTML = 'Your changes have been saved!';
	        }
		}
		
		xhttp.open("PUT", "/rest/QuestionService/setAll", true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send(jsonKysymykset);
		
		console.log("jsonKysymykset");
		console.log(jsonKysymykset);
	}
</script>
</head>
<body>
	<div id="container2">
		<table>
			<tr>
				<h1>Update Questions</h1>
				<td>
					<h4>Click on the question to edit!</h4><br />
				</td>
			</tr>
		<%
		List<Kysymykset> kysymykset = (List<Kysymykset>) request.getAttribute("kysymykset");
		System.out.println("hello");
		System.out.println(kysymykset);
		%>
			<table class="table table-hover table-sm">
				<%for(Kysymykset k : kysymykset) {
					String kysymys = k.getKysymys(); 
					int kysymysId = k.getKysymysId();%>
					<tr>
						<td>
							<p class='makeWhite' contenteditable='true' id='<%=kysymysId%>'><%=kysymys%><p/>
						</td>
						<td>
							<a href='./EditQ?deleteId=<%=kysymysId%>'>Remove</a>
						</td>
					</tr>
				<%}%>
			</table>
			<table>
					<tr>
						<td style='width: 70%;'>
							<form name="newQuestion" method="POST" action="/EditQ">
								<input style='width: 100%;' type='text' name='question' placeholder='New question here'/>
							</form>
						</td>
						<td style='width: 10%; padding-left: 15px;'>
							<a href='javascript:document.newQuestion.submit()'>Add new question</a>
						</td>
					</tr>
			</table><br />
			<table>
				<tr>
					<td><input type='button' name='ok' value='Save edited questions' onclick='sendData();' /></td>
					<td id='response'></td>
				</tr>
			</table>
	<form action="/ViewC" method="get">
		<input type='submit' name='ok' value='Back'/>
	</form>
</div>
</body>
</html>