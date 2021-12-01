/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    public Servlet(){}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        
 
        //Comprobamos donde hemos hecho click
        HttpSession s = req.getSession();
        s.setAttribute("mensaje", "");
        
        if(req.getParameter("uno") != null){
            try {
                //cargamos la pagina uno -> MESAS
                res.sendRedirect(".?i=1");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (req.getParameter("dos") != null){
            //cargamos la pagina dos -> LIBERAR MESA => PAGOs
            try{
            res.sendRedirect(".?i=2");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (req.getParameter("tres") != null){
             //cargamos la pagina tres -> PEDIDOS--->cargamos la pagina de mesas
            try{
            res.sendRedirect(".?i=3");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         else if (req.getParameter("cuatro") != null){
             //cargamos la pagina tres -> PEDIDOS--->cargamos nuestros productos
            try{
            res.sendRedirect(".?i=4");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         else if (req.getParameter("cinco") != null){
             //cargamos la pagina tres -> PEDIDOS
            try{
            res.sendRedirect(".?i=5");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         else if (req.getParameter("seis") != null){
             //cargamos la pagina tres -> PEDIDOS
            try{
                res.sendRedirect(".?i=6");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         else if (req.getParameter("diez") != null){
             try{
                  res.sendRedirect(".?i=10");
            } catch (IOException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
             
         }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        
        
        //Comprobamos donde hemos hecho click
        String param = req.getParameter("a");
        try {
            res.sendRedirect(".?i=" + param);   
        } catch (IOException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public static int toInt (String str){
        return Integer.parseInt(str);
    }
}
