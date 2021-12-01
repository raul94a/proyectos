/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import dao.FacturaDAO;
import dao.MesaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
@WebServlet("/ServletGestion")
public class ServletGestion extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        String tipo = req.getParameter("tipo");
        int mesa = Integer.parseInt(req.getParameter("mesa"));
         HttpSession session = req.getSession();
        switch(tipo){
            case "reserva":
                //logica de reserva -> reservvar la mesa elegida y generar una factura!
                MesaDAO.updateReservaMesa(mesa, false);
                //ahora generamos la factura!!! con MESA unicamente
                FacturaDAO.insertar(mesa);
                //debería redirigir primero a una pagina donde te indique que la mesa se ha reservado correctamente
               
                try {
                    
                    session.setAttribute("mensaje", "La mesa " + mesa + " ha sido ocupada / reservada con éxito!!!");
                    res.sendRedirect(".?i=0");
                } catch (IOException ex) {
                    Logger.getLogger(ServletGestion.class.getName()).log(Level.SEVERE, null, ex);
                    session.setAttribute("mensaje", "Ha ocurrido algún error al intentar resevar la mesa número " + mesa);
                }
            
                break;

                
            case "pago":
                //logica de pago -> dejar la mesa libre
                int factura = FacturaDAO.seleccionarId(mesa);
                FacturaDAO.cerrarFactura(factura);
                MesaDAO.updateReservaMesa(mesa, true);
                //DEBERIA REDIRIGIR A LA FACTURA
                try {
                    //logica del pedido redireccion a index con parametro de add.jsp
                    session.setAttribute("mensaje", "La mesa " + mesa + " ha sido liberada con éxito!!!");
                    res.sendRedirect(".?i=8&f="+factura);
                } catch (IOException ex) {
                    Logger.getLogger(ServletGestion.class.getName()).log(Level.SEVERE, null, ex);
                    session.setAttribute("mensaje", "Ha ocurrido algún error al intentar liberar la mesa número " + mesa);

                }
                break;
                
            case "pedido":
                try {
                    //logica del pedido redireccion a index con parametro de add.jsp
                    session.setAttribute("mensaje", "¡Vas a gestionar el pedido de la mesa " + mesa + "!");

                    res.sendRedirect(".?i=4&m=" + mesa);
                } catch (IOException ex) {
                   session.setAttribute("mensaje", "Ha ocurrido un problema al gestionar el pedido.");

                    Logger.getLogger(ServletGestion.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            
        }
    }

}