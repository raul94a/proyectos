/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.PedidoDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ServletDespacho")
public class ServletDespacho extends HttpServlet {
    public String getParam (HttpServletRequest req, String param){
        return req.getParameter(param);
    }
    public int getParamInt(HttpServletRequest req, String param){
        return Integer.parseInt(getParam(req, param));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        int pedido = getParamInt(req, "pedido");
        PedidoDAO.finalizarPedido(pedido);
        HttpSession s = req.getSession();
        try{
            s.setAttribute("mensaje", "¡El pedido número " + pedido + " ha sido despachado con éxito!");
            res.sendRedirect(".");
        }catch(IOException e){
            e.printStackTrace(System.out);
        }
    }
}
