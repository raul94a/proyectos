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
import seguridad.Cifrado;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet(name = "ServletAdmin", urlPatterns = {"/ServletAdmin"})
public class ServletAdmin extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
       
       String modo = req.getParameter("modo");
       HttpSession s = req.getSession();
       
       switch(modo){
           case "menuInvitado":
               boolean menuInvitado = Boolean.parseBoolean(req.getParameter("invitado"));
               Gestion.cambiarMenuInvitado(menuInvitado);
               s.setAttribute("mensaje", "Se ha " + (menuInvitado ? "activado " : "bloqueado ") + "el menú de invitado.");
               res.sendRedirect("admin.jsp");

               break;
               
           case "pass":
               String nuevaPass = Cifrado.generarPassword(
                       Integer.parseInt(
                               Fichero.getPropiedadApp("longitud_max_pass_admin")
                       )
               );
               String nuevaPassCifrada = Cifrado.cifrar(nuevaPass);
               String mail = Fichero.getPropiedadApp("admin_mail");
               //VAMOS A LA BASE DE DATOS
               UsuarioDAO.actualizarPasswordByEmail(nuevaPassCifrada, "admin");
               Comunicaciones.enviaMail(mail, "NUEVA CONTRASEÑA ADMINISTRADOR", "La nueva contraseña es " + nuevaPass);
               s.setAttribute("mensaje", "Se ha enviado un mensaje al correo de administrador con la nueva contraseña");
                      res.sendRedirect("admin.jsp");

               break;
               
           case "excel":
               Fichero.enviarExcel(Fichero.getPropiedadApp("admin_mail"));
               res.sendRedirect("admin.jsp");
               break;
               
           case "cerrar":
               s.removeAttribute("admin");
               res.sendRedirect("index.jsp");
               s.setAttribute("mensaje", "Sesión cerrada.");

               break;
       }
        
        
    }

}
