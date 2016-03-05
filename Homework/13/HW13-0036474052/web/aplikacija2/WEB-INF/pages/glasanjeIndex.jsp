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
        <h1>Glasanje za omiljeni bend:</h1>
        <p>Od sljedećih bendova, koji vam je bend najbolji? Kliknite na link kako biste glasali!</p>
        <ol>
        	<c:forEach var="zapis" items="${ bands }">
        	<li><a href="glasanje-glasaj?id=${ zapis.index }">${zapis.name }</a></li>
        	</c:forEach>
        </ol>
</body>
 
</html>