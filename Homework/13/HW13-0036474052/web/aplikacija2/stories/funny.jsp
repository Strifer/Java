<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Random"%>
 
<html>
<%
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>

<%
Random rand = new Random();
int r = rand.nextInt(256);
int g = rand.nextInt(256);
int b = rand.nextInt(256);
String rgb = "rgb("+r+","+g+","+b+")";
%>

<html>
<body bgcolor="<%=pickedColor%>">
<a href='/aplikacija2/index'>Click here to return to main page.</a>
<center>
<br>
<CENTER><H1><font color="<%=rgb%>">A Cat's Diary</H1></CENTER>

<font color="<%=rgb%>">Day 751: My captors continue to torment me with bizarre dangling objects.  They eat lavish meals in my presence while I am forced to subsist on dry cereal.  The only thing that keeps me going is the hope of eventual escape -- that, and the satisfaction I get from occasionally ruining some piece of their furniture.  <BR>
<BR>
<font color="<%=rgb%>">I fear I may be going insane.  Yesterday, I ate a houseplant. Tomorrow I may eat another.<BR>
<BR>
<BR>

</center>
<br>
<br>
<center>

</body>
</html>