<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container2">
<h1>Add New Candidate</h1>
<form action="SaveC" method="post">
<table class="table table-hover table-sm">
<tr>
	<td class="td-width2">Sukunimi:</td>
	<td><input type="text" name="Sukunimi" required/></td>
</tr>
<tr>
	<td class="td-width2">Etunimi:</td>
	<td><input type="text" name="Etunimi" required/></td>
</tr>
<tr>
	<td class="td-width2">Puolue:</td>
	<td><input type="text" name="Puolue" required/></td>
</tr>
<tr>
	<td class="td-width2">Kotipaikkakunta:</td>
	<td><input type="text" name="Kotipaikkakunta" required/></td>
</tr>
<tr>
	<td class="td-width2">Ika:</td>
	<td><input type="text" name="Ika" required/></td>
</tr>
</table>
<table>
	<tr>
		<td><input type="submit" value="Save Candidate"/></td>
		<td style="margin-left : 20px;">${success}</td>
	</tr>
</table>
</form>

<br/>
	<form action="/ViewC" method="get">
		<input type='submit' name='ok' value='Back'/>
	</form>
</div>
</body>
</html>