/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Producto;

/**
 *
 * @author Raul
 */
@WebServlet("/ServletCarta")
public class ServletCarta extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res){ 
            String mode = req.getParameter("mode");
            HttpSession s = req.getSession();
            switch(mode){
                case "1": //añadir
                    String nombre = req.getParameter("nombre");
                    String tipo = req.getParameter("tipo");
                    float precio = Float.parseFloat(req.getParameter("precio"));
                    Producto p = new Producto(nombre,tipo,precio);
                    ProductoDAO.insert(p);
                
                    try {
                        s.setAttribute("mensaje", "Se ha añadido " + nombre + " a la carta");
                        res.sendRedirect(".?i=7");
                    } catch (IOException ex) {
                        Logger.getLogger(ServletCarta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    break;

                case "2": //desactivar
                   //TO DO
                    int id = Integer.parseInt(req.getParameter("id"));
                     ProductoDAO.updateActivo(id, true);
                
                    try {
                         s.setAttribute("mensaje", "El producto ha sido eliminado de la carta.");
                   
                            res.sendRedirect(".?i=7");
                    } catch (IOException ex) {
                        Logger.getLogger(ServletCarta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "3": //modificar
                    //TO DO
                    id = Integer.parseInt(req.getParameter("id"));
                    nombre = req.getParameter("nombre");
                    tipo = req.getParameter("tipo");
                    precio = Float.parseFloat(req.getParameter("precio"));
                    p = new Producto(id, tipo, nombre, precio);
                    ProductoDAO.updateProducto(p, id);
                
                    try {
                        s.setAttribute("mensaje", "Se ha modificado el producto " + nombre);

                             res.sendRedirect(".?i=7");
                    } catch (IOException ex) {
                        Logger.getLogger(ServletCarta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    break;

                    
            }
        }

}
