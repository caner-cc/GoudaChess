<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Add New Candidate</h1>
<form action="SaveC" method="post">
<table>
<tr><td>Sukunimi:</td><td><input type="text" name="Sukunimi"/></td></tr>
<tr><td>Etunimi:</td><td><input type="text" name="Etunimi"/></td></tr>
<tr><td>Puolue:</td><td><input type="text" name="Puolue"/></td></tr>
<tr><td>Kotipaikkakunta:</td><td><input type="text" name="Kotipaikkakunta"/></td></tr>
<tr><td>Ika:</td><td><input type="text" name="Ika"/></td></tr>
<tr><td colspan="2"><input type="submit" value="Save Candidate"/></td></tr>
</table>
</form>

<br/>
<a href="ViewServlet">View Candidates</a>

</body>
</html>