/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDAO;
import dao.TratoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mensajeria.Comunicaciones;
import modelo.Trato;
import utils.Fichero;

@WebServlet("/ServletVender")
public class ServletVender extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        HttpSession sesion = req.getSession();
        boolean aceptar = Boolean.parseBoolean(req.getParameter("retrieve"));
        int idProd = Integer.parseInt(req.getParameter("prod"));
        int comprador = Integer.parseInt(req.getParameter("c"));
        
        if(aceptar){
            
            int user = Integer.parseInt(req.getParameter("user"));
            //1. Obtenemos el trato que vamos a insertar definitivamente en la tabla de tratos
            Trato t = new Trato(Gestion.seleccionarTratoTemporal(user, comprador, idProd));
            //2. Insertamos el trato en la base de datos, generando además el trato correspondiente de venta
            Gestion.insertarTratosSQL(t);
            //3. El producto ya ha sido vendido, por tanto, habrá que borrar todos los tratos temporales con la id del producto
            Gestion.borrarTratosTemporales(idProd);
            //4. Actualizamos el producto a vendido
            Gestion.actualizarProductoAVendido(idProd);
            //5. ENVIAR MENSAJE A VENDEDOR
            Comunicaciones.enviarProductoAComprador(ProductoDAO.seleccionarProductoPorId(idProd), UsuarioDAO.seleccionarUsuarioPorId(comprador));
            //6. ENVIAR MENSAJE A COMPRADOR
           // Fichero.registroLog("PRODUCTO VENDIDO", "comprador:" + comprador + ";Vendedor:" + user, false);

            try {
                sesion.setAttribute("mensaje", "El producto ha sido vendido con éxito");
                res.sendRedirect("index.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletVender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            Gestion.borrarTratoTemporal(comprador,idProd);
            //Fichero.registroLog("VENTA RECHAZADA", "comprador:" + comprador, false);

            try {
                sesion.setAttribute("mensaje", "Has rechazado la venta");
                res.sendRedirect("index.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletVender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
