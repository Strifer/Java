
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
<h1>Index</h1>
<a href='/aplikacija2/colors.jsp'>Background color chooser</a>
<br>
<a href='/aplikacija2/trigonometric?a=0&b=90'>Trigonometric table</a>
<br>
<a href='/aplikacija2/stories/funny.jsp'>Funny story</a>
<br>
<a href='/aplikacija2/report.jsp'>OS usage</a>
<br>
<a href='/aplikacija2/powers?a=1&b=100&n=3'>Create excel file</a>
<br>
<a href='/aplikacija2/appinfo.jsp'>Running time</a>
<br>
<a href='/aplikacija2/glasanje'>Favorite band poll</a>
</body>


</html>