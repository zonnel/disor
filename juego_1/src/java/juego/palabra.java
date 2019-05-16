/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import controladores.PalabraJpaController;
import controladores.TipoJpaController;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.console;
import static java.lang.System.out;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Palabra;
import modelos.Tipo;

/**
 *
 * @author eliel
 */
@WebServlet(name = "palabra", urlPatterns = {"/palabra"})
public class palabra extends HttpServlet {
    private EntityManagerFactory emf;
    private EntityManager em;
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        emf = Persistence.createEntityManagerFactory("juegoPU");
        //em = emf.createEntityManager();
        
        Palabra  pal = new Palabra();
        PalabraJpaController jpalabra = new PalabraJpaController(emf);
        
        int tipo = Integer.parseInt(request.getParameter("tipo"));
        
        Tipo tip = new Tipo();
        TipoJpaController jtipo = new TipoJpaController(emf);
        tip = jtipo.findTipo(tipo);
        
        String agregar = (String)request.getParameter("agregar");
        
        PrintWriter out= response.getWriter();
        
            
            String palabra = request.getParameter("pala");
            int nivel =Integer.parseInt(request.getParameter("nivel"));
            palabra = palabra.toUpperCase();
            
            pal.setPalabra(palabra);
            pal.setNivel(nivel);
            pal.setTipo(tip);
            
            boolean res=jpalabra.create(pal);
            
            //response.sendRedirect("palabra.jsp");
            
        try{
            if(res){
                out.println("<center>");
            out.println("<div>");
            out.println("<h2>");
            out.println("palabra : "+pal.getPalabra());
            out.println("nivel : "+pal.getNivel());
            out.println("tipo : "+tip.getDescripcion());
            out.println("</h2>");
            out.println("</div>");
            out.println("</center>");
            
            out.println("<a href='palabra.jsp'>OK</a>");
            }else{
                out.println("<center>");
            out.println("<div>");
            out.println("<h2>");
            out.println("no se agrego la plabara");
            out.println("</h2>");
            out.println("</div>");
            out.println("</center>");
            out.println("<a href='palabra.jsp'>OK</a>");
            }
            
        }finally{
            out.close();
            response.sendRedirect("palabra.jsp");
        }
                
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
        processRequest(request, response);
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
        processRequest(request, response);
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
