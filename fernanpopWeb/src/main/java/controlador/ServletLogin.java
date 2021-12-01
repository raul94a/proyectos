/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import seguridad.Cifrado;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet{
    private String parsearFecha(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        if(req.getParameter("btnLogin") != null){
            String email = (String)req.getParameter("email");
            String password = (String)req.getParameter("password");
            HttpSession session = req.getSession();
            if(Gestion.existeUsuarioRegistrado(email)){
                //TRAEMOS EL USUARIO CON EL MAIL
                Usuario u = UsuarioDAO.getUsuario(email);
                if(u != null){
                    //DESCIFRAMOS LA PASS DE LA BD
                    String passDescubierta = Cifrado.descifrar(u.getPassword());
                    if(passDescubierta.equals(password)){
                        if(Gestion.isUsuarioValidado(u)){

                            /*
                            String comprobarPropertiesUltimaConexion = Fichero.getPropiedadUser(u.getEmail());
                            String ultimaConexion = comprobarPropertiesUltimaConexion != null ? "Ultima conexion: " +  comprobarPropertiesUltimaConexion : "";*/

                            session.setAttribute("usuario", u.getId());

                            session.setAttribute("mensaje", "Bienvenido, " + u.getNombre() /*+ "  " + ultimaConexion*/);
                            
                            try {
                                res.sendRedirect("index.jsp");
                            } catch (IOException ex) {
                                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{
                            req.setAttribute("success",true);
                            req.setAttribute("correoUsuario", u.getEmail());
                           
                            try {

                                res.sendRedirect("validar.jsp");
                            } catch (IOException ex) {
                                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }else{
                        session.setAttribute("success", false);
                       // Fichero.registroLog("INTENTO FALLIDO DE INICIO DE SESION","USUARIO:" + email, false);

                        try {

                            res.sendRedirect("login.jsp");
                        } catch (IOException ex) {
                            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else{
                    session.setAttribute("mensaje", "El usuario no existe en el sistema, por favor, regístrese");
                    try {
                        res.sendRedirect("index.jsp");
                    } catch (IOException ex) {
                        Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

               
        }else{
           session.setAttribute("success", false);
            //Fichero.registroLog("INTENTO DE ACCESO AL SERVLET DESDE URL","acceso restringido", false);

            try {
                res.sendRedirect("login.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    }   
}
