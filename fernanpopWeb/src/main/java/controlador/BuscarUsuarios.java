/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

/**
 *
 * @author Raul
 */
@WebServlet(name = "BuscarUsuarios", urlPatterns = {"/BuscarUsuarios"})
public class BuscarUsuarios extends HttpServlet {

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
          String str = request.getParameter("buscador");
          int user = Integer.parseInt(request.getParameter("usuario"));
          ArrayList<Usuario> usuarios = UsuarioDAO.getUsuarios();
          
          ArrayList<Usuario> usuariosFiltrados = new ArrayList<>();
          for(Usuario u : usuarios){
              if(u.getId() != user && (u.getNombre().toLowerCase().contains(str) 
                      || u.getEmail().toLowerCase().contains(str))){
                  usuariosFiltrados.add(u);
              }
          }
          HttpSession session = request.getSession();
          if(!usuariosFiltrados.isEmpty()){
              session.setAttribute("buscarUsuarios", true);
              session.setAttribute("usuarios", usuariosFiltrados);
              response.sendRedirect("buzon.jsp");
          }
        
    }
     
       

}
