/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ConversacionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Raul
 */
@WebServlet(name = "ConversacionPrivada", urlPatterns = {"/ConversacionPrivada"})
public class ConversacionPrivada extends HttpServlet {

   

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       int usuario = Integer.parseInt(request.getParameter("own"));
       int vendedor = Integer.parseInt(request.getParameter("user"));
       if(!Gestion.existeConversacion(usuario, vendedor, 0)){
            ConversacionDAO.insertar(usuario,vendedor);
       }
     
       HttpSession session = request.getSession();
       session.removeAttribute("buscarUsuarios");
       response.sendRedirect("conversacionPrivada.jsp?v="+vendedor+"&c="+usuario);
       
    }

  
}
