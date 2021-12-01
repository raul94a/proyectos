/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Producto;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet(name = "ServletGestionProducto", urlPatterns = {"/ServletGestionProducto"})
public class ServletGestionProducto extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(req.getParameter("producto"));
        HttpSession s = req.getSession();
        
        if(req.getParameter("editar") != null){
            String nombre = req.getParameter("nombre");
            float precio = Float.parseFloat(req.getParameter("euros"));
            String descripcion = req.getParameter("descripcion");
            String estado = req.getParameter("estado");
            ProductoDAO.actualizarProducto(new Producto(id, nombre, precio, estado, descripcion));
            s.setAttribute("mensaje", "El producto " + nombre + " ha sido modificado con éxito.");
            //Fichero.registroLog("PRODUCTO MODIFICADO", "ID:" + id, false);

        }else{
            ProductoDAO.borrarProducto(id);
            s.setAttribute("mensaje", "El producto ha sido borrado con éxito");
           // Fichero.registroLog("PRODUCTO BORRADO", "ID-borrada-:" + id  , false);


        }
        res.sendRedirect("productos-en-venta.jsp");
        
    }

}
