<%-- 
    Document   : acceso
    Created on : 8/05/2019, 10:49:46 PM
    Author     : eliel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="bootstrap.min.css" type="text/css">
<script  src="bootstrap.min.js"></script>
    </head>
    <body>
        <<center style="margin-top: 10%;">
        <h1>ACCEDER</h1>
        <h2>Bienvenido <%=request.getParameter("nombre")%></h2>
        <%
            session = request.getSession();
            session.setAttribute("maxint",request.getParameter("nintento"));
        %>
        <h3>¿Te gustaria jugar?
            <a href="index.jsp" class="btn btn-outline-secondary">NO</a>
            <a href="Ahorcado" class="btn btn-outline-primary">SI</a>
        </h3>
    </center>
    </body>
</html>
