<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<%
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>

<body bgcolor="<%=pickedColor%>">
<h4>PICK COLOR</h4>

<a href='/aplikacija2/index'>Back to main page.</a>
<br>
<p><%=pickedColor%></p>
<ul>
<li><a href='/aplikacija2/setColor?color=white'>WHITE</a>
<li><a href='/aplikacija2/setColor?color=black'>BLACK</a>
<li><a href='/aplikacija2/setColor?color=green'>GREEN</a>
<li><a href='/aplikacija2/setColor?color=cyan'>CYAN</a>
</ul>
</body>


</html>

