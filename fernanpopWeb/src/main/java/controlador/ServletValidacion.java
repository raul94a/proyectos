
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Fichero;


@WebServlet("/ServletValidacion")
public class ServletValidacion extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
                        
          String email = req.getParameter("email");
          
          String token = req.getParameter("token");
 
          if(Gestion.validarUsuario(email, token) > 0){
              //System.out.println("El usuario ha sido validado");
              HttpSession session = req.getSession(); 
              
              session.setAttribute("usuario", Gestion.getIdUsuario(email));
              session.setAttribute("mensaje", "¡Enhorabuena! Tu cuenta ha sido validada con éxito");
              //Fichero.registroLog("USUARIO VALIDADO CON EXITO", "email:" + email, false);

              try {
                  res.sendRedirect("index.jsp");
              } catch (IOException ex) {
                  Logger.getLogger(ServletValidacion.class.getName()).log(Level.SEVERE, null, ex);
              }
          }else{
             // System.out.println("Problemas a la hora de validar");
              req.setAttribute("success", false);
              req.setAttribute("correoUsuario",email);
              //Fichero.registroLog("FALLO EN LA VALIDACIÓN DEL USUARIO", "email:" + email, false);

              try {
                  req.getRequestDispatcher("validar.jsp").forward(req, res);
                  res.sendRedirect("validar.jsp");
              } catch (IOException | ServletException ex) {
                  Logger.getLogger(ServletValidacion.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
     
    }
}
