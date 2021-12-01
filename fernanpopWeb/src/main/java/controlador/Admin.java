
package controlador;

import dao.UsuarioDAO;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import seguridad.Cifrado;

@WebServlet(name = "Admin", urlPatterns = {"/Admin"})
public class Admin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
         req.setCharacterEncoding("UTF-8");
         String usuario = req.getParameter("usuario");
         String pass = req.getParameter("password");
         Usuario u = UsuarioDAO.getUsuarioAdmin(usuario);
         String passDescifrada = Cifrado.descifrar(u.getPassword());
         HttpSession s = req.getSession();
         if(passDescifrada.equals(pass)){
             
             
             s.setAttribute("admin", true);
             res.sendRedirect("admin.jsp");
          
         
         }else{
             s.setAttribute("mensaje", "pass incorrecta");
             res.sendRedirect("index.jsp");
         }
        
        
    }

    
}
