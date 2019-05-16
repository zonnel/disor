<%-- 
    Document   : palabra
    Created on : 12/05/2019, 02:52:05 PM
    Author     : eliel
--%>

<%@page import="java.util.List"%>
<%@page import="modelos.Tipo"%>
<%@page import="controladores.TipoJpaController"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="controladores.PalabraJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("juegoPU");
    PalabraJpaController jpala = new PalabraJpaController(emf);
    TipoJpaController tip = new TipoJpaController(emf);
    
    List<Tipo> tipos = tip.findTipoEntities();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>palabras</title>
        <link rel="stylesheet" href="bootstrap.min.css" type="text/css">
<script  src="bootstrap.min.js"></script>
    </head>
    <body>
    <center class="container" style="margin-top: 10%;">
        <h3>Palabras</h3>
        <form method="POST" action="palabra">
            <div class="container">
                <label>ID</label>
                <input type="number" name="id" value="" readonly="">
            </div>
            <div class="container">
                <label>Palabra</label>
                <input type="text" name="pala" value="" autocomplete="off">
            </div>
            <div class="container">
                <label>Tipo</label>
                <select name="tipo">
                    <% for(int i = 0;i< tipos.size();i++){%>
                    <option value="<%=tipos.get(i).getId()%>"> <%=tipos.get(i).getDescripcion()%></option>
                    <%}%>
                </select>
                <!--<input type="number" value="" name="tipo">-->
            </div>
            <div class="container">
                <label>Nivel</label>
                <input type="number" value="" name="nivel" autocomplete="off">
            </div>

            <input type="submit" value="agregar" class="btn btn-outline-success">
            <a href="buscarpalabra.jsp" class="btn btn-danger">ELIMINAR</a>
            <a href="buscarpalabra.jsp" class="btn btn-outline-secondary">EDITAR</a>
            <a href="index.jsp" class="btn btn-outline-secondary">REGRESAR</a>
            
        </form>
    </center>
    </body>
</html>
