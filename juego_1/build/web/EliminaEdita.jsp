<%-- 
    Document   : EliminaEdita
    Created on : 12/05/2019, 11:33:05 PM
    Author     : eliel
--%>

<%@page import="java.util.List"%>
<%@page import="modelos.Tipo"%>
<%@page import="controladores.TipoJpaController"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="modelos.Palabra"%>
<%@page import="controladores.PalabraJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("juegoPU");
    PalabraJpaController jpa = new PalabraJpaController(emf);
int elimina = Integer.parseInt(request.getParameter("eliminar"));
int edita = Integer.parseInt(request.getParameter("editar"));
Palabra p;
    TipoJpaController tjpa = new TipoJpaController(emf);
    List <Tipo> listatip = tjpa.findTipoEntities();

if(elimina != 0){
     p = jpa.findPalabra(elimina);
}else{
     p = jpa.findPalabra(edita);
}

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="bootstrap.min.css" type="text/css">
        <script  src="bootstrap.min.js"></script>
    </head>
    <body>
    <center class="container" style="margin-top: 10%;">
        <form action="logicapalabra" method="post">
            <%if(elimina != 0){%>
        <h1>Eliminar Palabra</h1>
        <div>
            <label>ID</label>
            <input value="<%=p.getIdpalabra()%>" name="id" readonly="">
            <label>Palabra</label>
            <input type="text" name="palabra" value="<%=p.getPalabra()%>">
            <lable>Tipo</lable>
            <input type="number" name="tipo" value="<%=p.getTipo().getId()%>">
            <label>Nivel</label>
            <input type="number" name="nivel" value="<%=p.getNivel()%>">
            
            
        </div>
            <div>
                <a class="btn btn-outline-danger" href="logicapalabra?elimina=<%=p.getIdpalabra()%>">OK</a>
            <a class="btn btn-outline-success" href="eliminaeditapalabra.jsp">CANCELAR</a>
            </div>
         <%}else{%>
         <h1>Editar palabra</h1>
             <div>
            <label>ID</label>
            <input type="number"  readonly="" value="<%=p.getIdpalabra()%>" name="id">
            <label>Palabra</label>
            <input type="text" name="palabra" value="<%=p.getPalabra()%>">
            <lable>Tipo</lable>
            <select name="tipo" >
                <%for(int i = 0;i<listatip.size();i++) {
                    if(p.getTipo().getId() == listatip.get(i).getId()){%>
                    <option selected="selected" value="<%=p.getTipo().getId()%>"> <%=p.getTipo().getDescripcion()%> </option>
                    <%}%>
                    <option value="<%=listatip.get(i).getId()%>"> <%=listatip.get(i).getDescripcion()%> </option>
                <%}%>
                
            </select>
            <label>Nivel</label>
            <input type="number" name="nivel" value="<%=p.getNivel()%>">
            
            
        </div>
            <div>
                <input class="btn btn-outline-danger" type="submit" value="edita" name="edita">
            <a class="btn btn-outline-success" href="eliminaeditapalabra.jsp">CANCELAR</a>
            </div>
        <%}%>
        </form>
    </center>
        
    </body>
</html>
