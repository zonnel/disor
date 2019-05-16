/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import controladores.PalabraJpaController;
import controladores.TipoJpaController;
import controladores.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Palabra;
import modelos.Tipo;

/**
 *
 * @author eliel
 */
@WebServlet(name = "logicapalabra", urlPatterns = {"/logicapalabra"})
public class logicapalabra extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession(true);
        String palabra = (String)sesion.getAttribute("pal");
        String edita;
        int elimina; 
        try{
          edita = request.getParameter("edita");
            
        }catch(Exception e){
            edita=null;
        }
        try{
            elimina=Integer.parseInt(request.getParameter("elimina"));
        }catch(Exception e){
            elimina=0;
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("juegoPU");
        PalabraJpaController jpala = new PalabraJpaController(emf);
        
        if (elimina != 0){
         jpala.destroy(elimina);
         List<Palabra> palabras = (List<Palabra>)jpala.findPalabra2(palabra);
         sesion.setAttribute("palabras", palabras);
         response.sendRedirect("eliminaeditapalabra.jsp");
        }else if( edita != null){
            TipoJpaController tip = new TipoJpaController(emf);
            
            
            String palab = request.getParameter("palabra");
            int tipoe = Integer.parseInt(request.getParameter("tipo"));
            int nivel = Integer.parseInt(request.getParameter("nivel"));
            int ide = Integer.parseInt(request.getParameter("id"));
            palab = palab.toUpperCase();
            
            Tipo tipedit = tip.findTipo(tipoe);
            
            Palabra pedit = new Palabra();
            pedit.setIdpalabra(ide);
            pedit.setNivel(nivel);
            pedit.setPalabra(palab);
            pedit.setTipo(tipedit);
            
            jpala.edit(pedit);
            
            List<Palabra> palabras = (List<Palabra>)jpala.findPalabra2(palabra);
            sesion.setAttribute("palabras", palabras);
            response.sendRedirect("eliminaeditapalabra.jsp");
        }
        
        
        
        /*PrintWriter out = response.getWriter();
        try{
            
        out.println("<h1>Logicapalabra</h1>");
            HttpSession sesion = request.getSession(true);
        List<Palabra> palabras = (List<Palabra>)sesion.getAttribute("palabras");
        String palabra = (String)sesion.getAttribute("pal");
        out.println("<h1>"+palabra+"</h1>");
        //LOGICA
            Integer eliminar = Integer.parseInt(request.getParameter("eliminar"));
        
        out.println("<h1>"+eliminar+"</h1>");
        String editar = request.getParameter("editar");
        
        //out.println("<h1>"+eliminar+"</h1>");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("juegoPU");
        PalabraJpaController jpala = new PalabraJpaController(emf);
        
            //out.println("<h1>"+eliminar+"</h1>");
            if(eliminar != 0){
                //out.println("<h1>"+eliminar+"</h1>");
                jpala.destroy(eliminar);
                palabras = (List<Palabra>)jpala.findPalabra2(palabra);
                sesion.setAttribute("palabras", palabras);
                response.sendRedirect("eliminaeditapalabra.jsp");
            }else
            if(editar != null){
                String datos[] = editar.split("+");
                int id =Integer.parseInt(""+editar.charAt(6));
                sesion.setAttribute("id", id);
                out.println("<h1>"+editar+"</h1>");
                TipoJpaController tip = new TipoJpaController(emf);
                String dato = request.getParameter("dato"+id);
                int tipo = Integer.parseInt(request.getParameter("tipo"+id));
                int nivel = Integer.parseInt(request.getParameter("nivel"+id));
                out.println("<h1>"+dato+"</h1>");
                out.println("<h1>"+tipo+"</h1>");
                out.println("<h1>"+nivel+"</h1>");
                Tipo tipoeditar = tip.findTipo(tipo);
                Palabra editarp = new Palabra();
                editarp.setNivel(nivel);
                editarp.setPalabra(dato);
                editarp.setTipo(tipoeditar);
                jpala.edit(editarp);
                palabras = (List<Palabra>)jpala.findPalabra2(palabra);
                sesion.setAttribute("palabras", palabras);
                response.sendRedirect("eliminaeditapalabra.jsp");
            }
            //response.sendRedirect("buscarpalabra.jsp");
        }finally{
        out.close();
        }
        */
        //VISTA
        /*
        PrintWriter out = response.getWriter();
        try {
            out.println("<h1>"+palabra+"</h1>");
            out.println("<h1>PAlABRAS encontradas</h1>");
            out.println("<h1>encontrados :"+palabras.size()+"</h1>");
            
            out.println("<center>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<ID>");
            out.println("<PALABRA>");
            out.println("<TIPO>");
            out.println("<NIVEL>");
            out.println("<opc. 1>");
            out.println("<opc. 2>");
            out.println("</tr>");
            
            for(int i=0;i<palabras.size();i++){
              out.println("<tr>");
              out.println("<td><input type='number' value='"+palabras.get(i).getIdpalabra()+"' name=''> </td>");
              out.println("<td><input type='text' value='"+palabras.get(i).getPalabra()+"'> </td>");
              out.println("<td><input type='number' value='"+palabras.get(i).getTipo()+"'> </td>");
              out.println("<td><input type='number' value='"+palabras.get(i).getNivel()+"'> </td>");
              out.println("<td><a href=logicapalabra?eliminar="+palabras.get(i).getIdpalabra()+">Eliminar</a> </td>");
              out.println("<td><a href=logicapalabra?editar="+palabras.get(i).getIdpalabra()+">Editar</a> </td>");
              out.println("</tr>");
            }
            
            
            out.println("</table>");
            out.println("<a href='buscarpalabra.jsp'>Regresar</a>");
            
            out.println("</center>");
        }finally{
        out.close();
        }*/
        
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(logicapalabra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(logicapalabra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
