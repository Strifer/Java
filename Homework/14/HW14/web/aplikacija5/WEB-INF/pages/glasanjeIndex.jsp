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
		<a href='/aplikacija5/index'>Click here to return to main page.</a>
		<br>
        <h1 >${ specifiedPoll.title }</h1>
        <p>${ specifiedPoll.message }</p>
        <ol>
        	<c:forEach var="pollList" items="${ pollList }">
        	<li><a href="glasanje-glasaj?id=${ pollList.id }">${pollList.optionTitle }</a></li>
        	</c:forEach>
        </ol>
</body>
 
</html>