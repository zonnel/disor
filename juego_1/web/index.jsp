<%-- 
    Document   : index
    Created on : 8/05/2019, 10:47:40 PM
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
    <center class="container" style="margin-top: 10%;">
        <form method="post" action="acceso.jsp">
            Participante:
            <input type="text" name="nombre" required="" autocomplete="off">
            Num. Intentos:
            <input type="number" name="nintento" required="" autocomplete="off">
            <br/>
            <input style="margin-top: 5px;" type="submit" value="continuar" class="btn btn-light btn-outline-success">
        </form>
    </center>
    
    <center class="container" style="margin-top: 5px;">
        <a href="palabra.jsp" class="btn btn-light btn-outline-secondary">Menu palabra</a>
    </center>
    
    </body>
</html>
