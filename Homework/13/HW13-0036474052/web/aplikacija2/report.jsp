<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
<a href='/aplikacija2/index'>Click here to return to main page.</a>
<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed.</p>
<br>
<img src="/aplikacija2/reportImage" alt="OS usage">
</body>
</html>