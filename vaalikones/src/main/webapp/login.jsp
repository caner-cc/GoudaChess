<%-- 
    Document   : vastaus
    Created on : 09-Apr-2015, 12:50:47
    Author     : Jonne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,vaalikone.Vaalikone,persist.*"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Diginide Vaalikone 2.0</title>

<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	
		<img id="headerimg" src="Logo.png" width="500" height="144" alt="" />
		<div class="kysymys">
			<h1>Login</h1>
			<p style="color:red; text-align: center">${message}</p>
		</div>

		<form style="margin-top: 20px" id="vastausformi" method="post" action="Login">
			<label class="txt">Username</label><br />
			<input id="username" name="username" required> <br /><br />
			<label class="txt">Password</label><br />
			<input type="password" id="password" name="password" required> <br /><br />
			<input type="submit" value="Login" id="submitnappi"> <br /><br />
		</form>
		
		<form action="index.html">
			<input style="margin-top:110px" id="submitnappi" type="submit" value="Back" name="btnAloita" />
		</form>
	</div>
</body>
</html>