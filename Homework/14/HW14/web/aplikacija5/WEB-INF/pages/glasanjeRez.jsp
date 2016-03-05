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
		<a href='/aplikacija5/index'>Click here to return to main page.</a>
		<br>
        <h1>Voting results</h1>
        <p>Here are the voting results</p>
        <table border="1" cellspacing="0" class="rez">
                <thead>
                        <tr>
                                <th>Candidate</th>
                                <th>Number of votes</th>
                        </tr>
                </thead>
                <tbody>
                        <c:forEach var="sortedOptions" items="${ sortedOptions }">
                                <tr><td>${ sortedOptions.optionTitle }<td>${ sortedOptions.votesCount }</tr>
                        </c:forEach>
                </tbody>
        </table>
        <h2>Graphical representation of the results</h2>
			<img alt="Pie-chart" src="/aplikacija5/glasanje-grafika" width="600" height="400" />
		
		<h2>Results in XLS format</h2>
			<p>Results in XLS format are available <a href="/aplikacija5/glasanje-xls">here</a></p>

		<h2>Extras</h2>
			<p>Here are the winning example links:</p>
			<ol>
        		<c:forEach var="topOptions" items="${ topOptions }">
        		<li><a href="${ topOptions.optionLink }" target="_blank">${topOptions.optionTitle }</a></li>
        		</c:forEach>
        	</ol>
</body>
 
</html>