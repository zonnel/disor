/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import controladores.PalabraJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Palabra;

/**
 *
 * @author eliel
 */
@WebServlet(name = "Ahorcado", urlPatterns = {"/Ahorcado"})
public class Ahorcado extends HttpServlet {
    //private final static String[] PALABRAS={"AHORITA","ACERTIJO","OAXACA","HAMACA"};
    private static int inten=5;

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
        //logica
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("juegoPU");
        PalabraJpaController jpapal = new PalabraJpaController(emf);
        
        HttpSession sesion= request.getSession();
        int maxint = Integer.parseInt((String)sesion.getAttribute("maxint"));
        String palabra= (String) sesion.getAttribute("palabra");
        String aciertos;
        String errados;
        
        if(palabra==null){
            List<Palabra> listapalabras = jpapal.findPalabraEntities();
            String PALABRAS[] = new String[listapalabras.size()];
            
            for(int i=0;i<listapalabras.size();i++){
                PALABRAS[i]=listapalabras.get(i).getPalabra();
            }
            
            Random oran= new Random();
            palabra= PALABRAS[oran.nextInt(PALABRAS.length)];
            aciertos="";
            errados="";
            sesion.setAttribute("palabra", palabra);
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
            
            
        }
        
        else{
            aciertos=(String)sesion.getAttribute("aciertos");
            errados=(String)sesion.getAttribute("errados");
            String letra= request.getParameter("letra");
            if(palabra.indexOf(letra)>=0)
                aciertos+=letra;
            else
                errados+=letra;
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
            
           
        }
        
        //vista
        
        PrintWriter out= response.getWriter();
        try{
            out.println(palabra);
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ahorcado</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h2>Juego </h2>");
            out.println("<h3>Selecciona una letra </h3>");
            boolean terminado= true;
            out.println("<h2>");
            
            for(int i=0;i<palabra.length();i++){
                String letra = palabra.substring(i, i+1);
                if(aciertos.indexOf(letra)>=0)
                    out.println(""+letra);
                else
                {
                    out.println(""+"_");
                    terminado=false;
                }
                    
            }
            out.println("</h2>");
            if(maxint>errados.length())
            {
                out.println("<br/><br/><br/>");
                for(char ca='A' ; ca<='Z' ; ca++){
                    if(aciertos.indexOf(ca)==-1 && errados.indexOf(ca)==-1)
                        out.println("<a href=Ahorcado?letra="+ ca +">" + ca + "</a>");
                }
                out.println("<br/><h2>"+ "Oportunidades de errar:" + (maxint-errados.length())+"</h2>" );
            }
            else
            {
                sesion.invalidate();
                out.println("<br/><h3>JUEGO TERMINADO</h3>");
                out.println("<br/><a href='index.jsp'>regresar</a>");
                
            }
            
            if(terminado){
                sesion.invalidate();
                out.println("<br/><h3>JUEGO COMPLETO</h3>");
                out.println("<br/><a href='index.jsp'>regresar</a>");
            }
            
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
            
            
                  
        }
        finally{
            out.close();
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
