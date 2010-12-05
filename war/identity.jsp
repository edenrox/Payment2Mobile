<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.rhok.payout2mobile.controllers.*" %>
<%@ page import="org.rhok.payout2mobile.model.*" %>
<%@ page import="java.util.*" %>

<%
String stype = request.getParameter("type");
IdentityType type = IdentityType.Customer;
if ((stype != null) || (stype.length() > 0)) {
	type = IdentityType.valueOf(stype);
}
%>

<html>
 <head>
  <title>RHoK:P2M - <%= type.toString() %> List</title>
  <link type="text/css" rel="stylesheet" href="css/style.css" />
 </head>
  <body>

<h2>List of <%= type.toString() %>s</h2>

<table cellspacing="0">
 <thead>
  <tr><th>Name</th><th>Phone Number</th><th>Role</th></tr>
 </thead>
 <tbody>
<% 

Vector<Identity> items = CC.get().identity().list(type);
for(Identity item : items) { %>
  <tr>
   <td><a href="identity-view?id=<%= item.getPhoneNumber() %>"><%= item.getName() %></a></td>
   <td><%= item.getPhoneNumber() %></td>
   <td><%= item.getType().toString() %></td>
  </tr>
<% } %>
 </tbody>
</table>

  </body>
</html>