/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.LoteDAO;
import dao.UsuarioDAO;
import dao.VacunaDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;

@WebServlet("/ServletVacunacion")
public class ServletVacunacion extends HttpServlet {
    public String getParam (HttpServletRequest req, String param){
        return req.getParameter(param);
    }
    public int getParamInt(HttpServletRequest req, String param){
        return Integer.parseInt(getParam(req, param));
    }
    public void modificarAtributoSesion(HttpServletRequest req, String att, String value){
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
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        String continuar = getParam(req, "continue");
        String dni = getParam(req, "dni");
        int numeroVacunaciones = UsuarioDAO.contarVacunaciones(dni);
        if(continuar == null){
            if(UsuarioDAO.contarVacunaciones(dni) == 2 || UsuarioDAO.vacunaAdministrada(dni).equals("Janssen")){
                modificarAtributoSesion(req, "dni", null);
                modificarAtributoSesion(req, "mensaje", "El usuario posee ya la pauta completa contra el COVID-19.");
                redireccionar(res);
            }else{
                //AQUI ENTRAMOS SI EL USUARIO INTRODUCIDO NO TIENE NINGUNA PAUTA DE LA VACUNA O LA TIENE INCOMPLETA
                modificarAtributoSesion(req, "dni", dni);
                modificarAtributoSesion(req, "mensaje","El paciente con dni " + dni  + " no ha completado la pauta de vacunación" );
                redireccionar(res);
            }
           
        }else{
            
            if(numeroVacunaciones == 0){
                String nombre = getParam(req, "nombre");
                String apellidos = getParam(req, "apellidos");
                int idVacuna = getParamInt(req, "vacuna");
                Vacuna v = VacunaDAO.selectVacuna(idVacuna);
                String municipio = getParam(req, "municipio");
                int lote = LoteDAO.vacunaAdministrada(idVacuna);
                Usuario u = new Usuario(dni, nombre,apellidos,  v, lote, municipio);
                UsuarioDAO.insertar(u);
                LoteDAO.disminuirCantidad(lote);
                modificarAtributoSesion(req, "dni", null);
                modificarAtributoSesion(req, "mensaje", "La vacunación de " +  nombre + " " + apellidos +
                        " ha sido registrada con éxito en el sistema. " + " Vacuna administrada: " + v.getNombre());
                redireccionar(res);
            }else if (numeroVacunaciones == 1 && !UsuarioDAO.vacunaAdministrada(dni).equals("Janssen")){
                String nombre = getParam(req, "nombre");
                String apellidos = getParam(req, "apellidos");
                int idVacuna = getParamInt(req, "vacuna");
                Vacuna v = VacunaDAO.selectVacuna(idVacuna);
                String municipio = getParam(req, "municipio");
                int lote = LoteDAO.vacunaAdministrada(idVacuna);
                Usuario u = new Usuario(dni, nombre,apellidos,  v, lote, municipio);
                UsuarioDAO.insertar(u);
                LoteDAO.disminuirCantidad(lote);
                modificarAtributoSesion(req, "dni", null);
                modificarAtributoSesion(req, "mensaje", nombre + " " + apellidos +
                        " ha completado la pauta de vacunación con éxito. " + " Vacuna administrada: " + v.getNombre());
                redireccionar(res);
            
            }
         }
    }
}