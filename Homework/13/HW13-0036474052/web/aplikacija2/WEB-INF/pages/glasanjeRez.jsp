<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
<%
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>
<head>
<style type="text/css">
	table.rez td {text-align: center;}
</style>
</head>
<body bgcolor="<%=pickedColor%>">
		<a href='/aplikacija2/index'>Click here to return to main page.</a>
		<br>
        <h1>Rezultati glasanja</h1>
        <p>Ovo su rezultati glasanja</p>
        <table border="1" cellspacing="0" class="rez">
                <thead>
                        <tr>
                                <th>Bend</th>
                                <th>Broj glasova</th>
                        </tr>
                </thead>
                <tbody>
                        <c:forEach var="zapis" items="${ voteResults }">
                                <tr><td>${ zapis.band.name }<td>${ zapis.votes }</tr>
                        </c:forEach>
                </tbody>
        </table>
        <h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="/aplikacija2/glasanje-grafika" width="600" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/aplikacija2/glasanje-xls">ovdje</a></p>

		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ol>
        	<c:forEach var="zapis" items="${ topBands }">
        	<li><a href="${ zapis.band.link }" target="_blank">${zapis.band.name }</a></li>
        	</c:forEach>
        </ol>
</body>
 
</html>