/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Raul
 */
public class ServletReserva extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
       int mesa = Integer.parseInt(req.getParameter("mesa"));
       //CUANDO SE RESERVA UNA MESA TENGO QUE CCREAR UNA FACTURA AUTOMATICAMENTE
       //LA FACTURA NO TENDRÁ FECHA, POR AHORA. TAMPOCO ESTARÁ PAGADA
       //LA FACTUR ESTARÁ ASOCIADA A UNA MESA
       //SOLO CUANDO LA FACTURA ES PAGADA LA MESA SE LIBERA Y SE SETEA LA FECHA DE FACTURA.
    }
    
}
