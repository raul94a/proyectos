/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mensajeria.Comunicaciones;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet(name = "RecordarPass", urlPatterns = {"/RecordarPass"})
public class RecordarPass extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String nuevaPass = Gestion.generarToken();
        String mensaje = "Su nueva contraseña es: " + nuevaPass;
        UsuarioDAO.actualizarPasswordByEmail(nuevaPass, email);
        Comunicaciones.enviaMail(email, "FERNANPOP: NUEVA CONTRASEÑA", mensaje);
        
        HttpSession s = request.getSession();
        s.setAttribute("mensaje", "Se ha enviado un email con la nueva contraseña a la dirección de correo indicada.");
        s.setAttribute("email", email);
       // Fichero.registroLog("CONTRASEÑA RECORDADA", "USUARIO:" + email + ";CONTRASEÑA:" + nuevaPass, false);
        response.sendRedirect("login.jsp");
      
    }

   

}
