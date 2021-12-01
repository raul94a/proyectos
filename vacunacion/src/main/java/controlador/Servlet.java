/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Usuario;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    public String getParam (HttpServletRequest req, String param){
        return req.getParameter(param);
    }
    public int getParamInt(HttpServletRequest req, String param){
        return Integer.parseInt(getParam(req, param));
    }
    public void modificarAtributoSesion(HttpServletRequest req, String att, Object value){
        req.getSession().setAttribute(att, value);
    }
    public void redireccionar(HttpServletResponse res){
        try{
            res.sendRedirect(".");
        }catch(IOException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        String param = getParam(req, "a");
        modificarAtributoSesion(req, "a", !param.equals("back") ? param : null);
        modificarAtributoSesion(req, "dni", null);
        modificarAtributoSesion(req, "mensaje", null);
        modificarAtributoSesion(req, "unsuccessful", null);


        redireccionar(res);
    }
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        //comprobamos QUIÉN se ha logeado. Es decir, discriminamos entre ciudadano y sanitario
        String modo = getParam(req, "modo");
        String id = getParam(req, "id");
        modificarAtributoSesion(req, "mensaje","");
        if(modo.equals("s")){
            //ENTRAMOS EN MODO SANITARIO
            Integer sanitario = UsuarioDAO.isSanitario(id);
            if(sanitario > 0){
                modificarAtributoSesion(req, "a", "sanitario");
             
                redireccionar(res);
            }else{
                //mensaje de error unsuccessful
                modificarAtributoSesion(req, "unsuccessful", "Los datos son incorrectos.");
                redireccionar(res);
            }
            
        }else{
            //MODO CIUDADANO
            //contamos las vacunaciones y si tiene mas que cero sacamos qué vacuna se ha puesto
            int numeroVacunas = UsuarioDAO.contarVacunaciones(id);
            ArrayList<Usuario> vacunaciones = UsuarioDAO.datosVacunacion(id);
            modificarAtributoSesion(req, "vacunaciones", vacunaciones);
          /*  try {
                //AQUI CREARIAMOS EL CERTIFRICADO
                //Operaciones.crearCertificado(vacunaciones);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            if(numeroVacunas == 2 || UsuarioDAO.vacunaAdministrada(id).equals("Janssen")){
                modificarAtributoSesion(req, "certificado", true);
            }
            modificarAtributoSesion(req,"a", "ciudadano");
            modificarAtributoSesion(req, "dni", id);
            redireccionar(res);
            
        }
       
    }
    
}