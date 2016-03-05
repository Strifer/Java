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
        <h1>Trigonometric table:</h1>
        <table>
                <thead>
                        <tr>
                                <th>x°</th>
                                <th>Sin(x)</th>
                                <th>Cos(x)</th>
                        </tr>
                </thead>
                <tbody>
                        <c:forEach var="zapis" items="${ results }">
                                <tr><td>${ zapis.degrees }<td>${ zapis.sin }<td>${ zapis.cos }</tr>
                        </c:forEach>
                </tbody>
        </table>
</body>
 
</html>