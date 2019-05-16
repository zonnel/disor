<%-- 
    Document   : eliminaeditapalabra
    Created on : 12/05/2019, 09:39:56 PM
    Author     : eliel
--%>

<%@page import="java.util.List"%>
<%@page import="modelos.Palabra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
HttpSession sesionActual = request.getSession(true);
List<Palabra> palabras = (List<Palabra>)sesionActual.getAttribute("palabras");
int id;
try{
 id = (Integer)sesionActual.getAttribute("id");
}catch(Exception e){
    id=0;
}


%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>palabras</title>
        <link rel="stylesheet" href="bootstrap.min.css" type="text/css">
<script  src="bootstrap.min.js"></script>
    </head>
    <body>
        <form>
            <center style="top-margin:10%;" class="container">
        <table style="width:100%" class="table">
            <tr>
              <th>ID</th>
              <th>Palabra</th>
              <th>Tipo</th>
              <th>Nivel</th>
              <th>Opcion 1</th>
              <th>opcion 2</th>
            </tr>
            <%for (int i=0;i< palabras.size();i++) {%>
            <tr>
                <td><input type="number" readonly="true" value="<%=palabras.get(i).getIdpalabra()%>" name="id<%=palabras.get(i).getIdpalabra()%>"></td>
                <td><input readonly="true" type="text" value="<%=palabras.get(i).getPalabra()%>" name="dato<%=palabras.get(i).getIdpalabra()%>"></td>
                <td><input readonly="true" type="text" value="<%=palabras.get(i).getTipo().getDescripcion()%>" name="tipo<%=palabras.get(i).getIdpalabra()%>"></td>
              <td><input readonly="true" type="number" value="<%=palabras.get(i).getNivel()%>" name="nivel<%=palabras.get(i).getIdpalabra()%>"></td>
              <td><a class="btn btn-outline-danger" href="EliminaEdita.jsp?eliminar=<%=palabras.get(i).getIdpalabra()%>&editar=0">Eliminar</a></td>
              <td><a class="btn btn-outline-warning" href="EliminaEdita.jsp?editar=<%=palabras.get(i).getIdpalabra()%>&eliminar=0 ">Editar</a></td>
            </tr>
            <%}%>
            
          </table> 
            
        <a href="buscarpalabra.jsp" class="btn btn-light btn-outline-info">Regresar</a>
    </center>
        </form>
            
            
    
         
    </body>
</html>
