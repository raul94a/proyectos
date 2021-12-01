/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ConversacionDAO;
import dao.ProductoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Producto;

/**
 *
 * @author Raul
 */
@WebServlet("/ServletMensajes")
public class ServletMensajes extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        if(req.getParameter("btnMsj") != null){
            try {
               
                int conversacion = Integer.parseInt(req.getParameter("conversacion"));
                int emisor = Integer.parseInt(req.getParameter("emisor"));
                int receptor = Integer.parseInt(req.getParameter("receptor"));
                int producto = Integer.parseInt(req.getParameter("producto"));
                Producto p = Gestion.getProducto(producto);
                int vendedor = p.getVendedor();
                String mensaje = req.getParameter("msj");
                Gestion.insertarMensaje(mensaje, emisor, receptor, conversacion);
                //Si el vendedor es el emisor del mensaje, entonces el comprador es el receptor del mismo. Si no, el comprador es el que emite el mensaje 
                //gestionado en este método 
                int comprador = 
                        vendedor == emisor 
                        ? receptor
                        : emisor;
              
                    res.sendRedirect("conversacion.jsp?c=" + comprador + "&v=" + vendedor + "&p=" + producto);

               
           
               
            } catch (IOException ex) {
                Logger.getLogger(ServletMensajes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
