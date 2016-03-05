<%@page import="hr.fer.zemris.java.hw13.servlets.StaticTimeFactory"%>
<%@page import="java.time.Period"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<%
ServletContext sc = request.getSession().getServletContext();
long startTime = (long)sc.getAttribute("startTime");
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>

<body bgcolor="<%=pickedColor%>">
<a href='/aplikacija2/index'>Click here to return to main page.</a>
<h4>Running time</h4>
<%
long millis = System.currentTimeMillis() - startTime;
String time = StaticTimeFactory.getDurationBreakdown(millis);
%>
<p><%=time%></p>
</body>


</html>