<%-- 
    Document   : buscarpalabra
    Created on : 12/05/2019, 05:54:18 PM
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
        <label>Buscar palabra</label>
        <form action="buscarpalabra" method="post" >
            <input type="text" name="pala" value="" require="">
            <input type="submit" name="buscar" value="buscar" class="btn btn-outline-success">
            <a href="palabra.jsp" class="btn btn-secondary">Regresar</a>
        </form>
    </center>
    </body>
</html>
