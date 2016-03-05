<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
<%
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>

<body bgcolor="<%=pickedColor%>">
		<a href='/aplikacija2/index'>Click here to return to main page.</a>
		<br>
        <h1>Error:</h1>
        <p>An error has occurred. Please check your attributes and try again.</p>
        </body>
</html>