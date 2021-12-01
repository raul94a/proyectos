/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Usuario;
import seguridad.Cifrado;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet("/ServletRegistro")
public class ServletRegistro extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
                              
        if(req.getParameter("btnRegistro") != null){
            String nombre = req.getParameter("nombre");
            int telefono = Integer.parseInt(req.getParameter("movil"));
            String email = req.getParameter("email");
            char sexo = req.getParameter("sexo").charAt(0);
            String pass = req.getParameter("password");
            String passRep = req.getParameter("password-rep");
            if(Gestion.existeUsuarioRegistrado(email)){
                req.setAttribute("existeEmail", true);
               // Fichero.registroLog("INTENTO DE REGISTRO CON EMAIL YA REGISTRADO", "EMAIL:" + email, false);

                try {
                    req.getRequestDispatcher("registro.jsp").forward(req,res);
                    res.sendRedirect("registro.jsp");
                } catch (IOException | ServletException ex) {
                    Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                if(pass.equals(passRep)){
                    //En este punto sabemos que el email no está registrado en la BD y las contraseñas son iguales
                    //Hay que registrar al usuario!!!!!!!
                    //pero primero generaremos la ID
                    int id = 0;
                    do{
                        id = Gestion.generarIdAleatoria();
                    }while(Gestion.existeIdEnUsuarios(id));
                    //ciframos la password
                    
                    String passCifrada = Cifrado.cifrar(pass);
                    
                    //creamos el usuario
                    
                    Usuario u = new Usuario(id,telefono,nombre,email,passCifrada,sexo);
                    //lo registramos en la base de datos
                    if(Gestion.registrarUsuario(u)){
                        //success nos permite visualizar el jsp de validar
                         req.setAttribute("success", true);
                         //email permitirá que el email del form del jsp de validar se cargue automaticamente
                         req.setAttribute("correoUsuario",email);
                       // Fichero.registroLog("NUEVO USUARIO REGISTRADO", "email:" + email, false);

                        try { 
                            req.getRequestDispatcher("validar.jsp").forward(req, res);
                            //res.sendRedirect("validar.jsp");
                        } catch (IOException | ServletException ex) {
                            Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        req.setAttribute("error","Ha ocurrido un error. Por favor, vuelva a intentar registrarse.");
                       // Fichero.registroLog("ERROR AL REGISTRARSE", "email:" + email, false);
                        try {
                            req.getRequestDispatcher("registro.jsp").forward(req, res);
                            res.sendRedirect("registro.jsp");
                        } catch (ServletException | IOException ex) {
                            Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }else{
                    req.setAttribute("success", 0);
                    //Fichero.registroLog("ERROR AL REGISTRARSE", "contraseñas mal introducidas", false);

                    try {
                        req.getRequestDispatcher("registro.jsp").forward(req, res);
                        res.sendRedirect("registro.jsp");
                    } catch (ServletException | IOException ex) {
                        Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            
        }else{
            //Fichero.registroLog("INTENTO DE ACCESO NO PERMITIDO AL SERVLET DE REGISTRO", "", false);

            try {
                res.sendRedirect("index.jsp");
            } catch (IOException ex) {
                Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
    }
}
