
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Producto;

@WebServlet("/ServletConversacion")
public class ServletConversacion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        int comprador = Integer.parseInt(req.getParameter("c"));
        int vendedor = Integer.parseInt(req.getParameter("v"));
        int producto = Integer.parseInt(req.getParameter("p"));
        if(!Gestion.existeConversacion(comprador, vendedor, producto)){
            if(Gestion.crearConversacion(comprador, vendedor, producto) > 0){
                try {

                res.sendRedirect("conversacion.jsp?c=" + comprador +"&v=" + vendedor + "&p=" + producto);

                } catch (IOException ex) {
                    Logger.getLogger(ServletProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    res.sendRedirect("index.jsp");
                } catch (IOException ex) {
                    Logger.getLogger(ServletConversacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }else{
             try {
                      res.sendRedirect("conversacion.jsp?c=" + comprador +"&v=" + vendedor + "&p=" + producto);
                } catch (IOException ex) {
                    Logger.getLogger(ServletConversacion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        
    }
    
}
