/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDAO;
import dao.TratoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Producto;
import modelo.Trato;
import utils.Fichero;
@WebServlet("/ServletComprar")
public class ServletComprar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        
        if(req.getParameter("btnComprar") != null){
           /** VAMOS A INSERTAR UN TRATO TEMPORAL DE TIPO VENTA **/
           /** los datos que necesitamos son los siguientes:
            *   id: generado automaticmaente por Gestion
            *   comprador: lo obtendremos del parametro c
            *  
            *   idProducto: lo obtendremos del parametro p. Utilizando este ID recuperaremos el producto de la bd
            *   tipo: será de venta, puesto que lo que tratamos de hacer aquí es de
            *   proporcionar un sistema en el cual se indexen en la bd los potenciales tratos de tipo venta de un producto en concreto
            */
           
           //LO PRIMERO QUE TENEMOS QUE TENER EN CUENTA ES QUE EL USUARIO PUEDE DAR TODAS LAS VECES QUE QUIERA AL BOTON DE COMPRAR
           //DE TAL FORMA QUE PODEMOS PROCEDER DE DOS FORMAS: HACIENDO DESAPARECER EL BOTON DE COMPRAR O CONTROLANDOLO DESDE AQUÍ.
           //VOY A ESCOGER LA PRIMERA ESTRATEGIA
           
            int id = 0;
            do{
                id = Gestion.generarIdAleatoria();
            }while(TratoDAO.existeIdEnTratos(id));
            
            int comprador = Integer.parseInt(req.getParameter("comprador"));
            int idProducto = Integer.parseInt(req.getParameter("producto"));
            float precio = Float.parseFloat(req.getParameter("precio"));
            
            Producto producto = Gestion.getProducto(idProducto);
            
            Trato tratoTemporal = new Trato(id, "venta", comprador, precio, producto  );
            //insertmos el trato temporal en la tabla correspondiente
       
            if(Gestion.insertarTrato(tratoTemporal, true) > 0 ){
               //a continuación cambiamos el estado del producto a que alguiene stá interesado!!
                Gestion.actualizarInteresado(idProducto, true);
               // Fichero.registroLog("NUEVO TRATO TEMPORAL", "COMPRADOR" + comprador + ";PRODUCTO:" + producto, false);

               
            }
            try {
                   res.sendRedirect("conversacion.jsp?c="+comprador+"&v="+producto.getVendedor()+"&p="+idProducto);
            } catch (IOException ex) {
                Logger.getLogger(ServletComprar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    

    
}
