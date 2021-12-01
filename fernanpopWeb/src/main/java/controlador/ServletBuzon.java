
package controlador;

import dao.ConversacionDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletBuzon")
public class ServletBuzon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        int idConver = Integer.parseInt(req.getParameter("id"));
        Gestion.borrarConversacion(idConver);
        
        try {
            res.sendRedirect("buzon.jsp");
        } catch (IOException ex) {
            Logger.getLogger(ServletBuzon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
