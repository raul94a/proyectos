/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import dao.DataProductoDAO;
import dao.FacturaDAO;
import dao.ProductoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DataProducto;
/**
 *
 * @author Raul
 */
@WebServlet("/ServletPedidos")
public class ServletPedidos extends HttpServlet {
    public ServletPedidos(){}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        //nos devuelve un JSON
       String json = req.getParameter("json");
       int mesa = Integer.parseInt(req.getParameter("mesa"));
       Type DataProductoListType = new TypeToken<ArrayList<DataProducto>>(){}.getType();
       ArrayList<DataProducto> dp = new Gson().fromJson(json, DataProductoListType);
       
       //primero vamos a realizar una actuaalización del precio del pedido. Para ello el primer paso será seleccionar la ID de la factura
       int factura = FacturaDAO.seleccionarId(mesa);
       float precioFactura = 0;
       //ahora se inserta cada producto en la base de datos, en la tabla factura_producto (factura, producto, cantidad)
       //aprovecho para calcular cuanto vale cada producto y sumarlo para actualizar la factura
       for(DataProducto d:dp){
           //¿Existe la id del producto en la intertabla?
            if(DataProductoDAO.selectProducto(d.getIdentificador(), factura) != null){
                DataProductoDAO.updateCantidad(d.getCantidad(), factura, d.getIdentificador());
            }else{
                DataProductoDAO.insertar(factura, d);
            }
            precioFactura += ProductoDAO.seleccionarPrecio(d.getIdentificador()) * d.getCantidad();
       }
       //se inserta el precio en la factura!
       FacturaDAO.actualizarPrecio(precioFactura, factura);
       HttpSession s = req.getSession();
       try {
           //¿A donde redirigimos? a index.jsp con simple
           s.setAttribute("mensaje","¡El pedido de la mesa " + mesa + " ha sido gestionado con éxito!");
           res.sendRedirect(".");
       } catch (IOException ex) {
           Logger.getLogger(ServletPedidos.class.getName()).log(Level.SEVERE, null, ex);
       }



    }
    
    private int toInt(String str){
        return Integer.parseInt(str);
    }
    
    
}
