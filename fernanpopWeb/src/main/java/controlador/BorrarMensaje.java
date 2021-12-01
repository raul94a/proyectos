
package controlador;

import dao.ConversacionDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "BorrarMensaje", urlPatterns = {"/BorrarMensaje"})
public class BorrarMensaje extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        boolean borrarParaReceptor = Boolean.parseBoolean(req.getParameter("borrarReceptor"));
        
        int id = Integer.parseInt(req.getParameter("mensaje"));
        int comprador = Integer.parseInt(req.getParameter("comprador"));
        int vendedor = Integer.parseInt(req.getParameter("vendedor"));
        int producto = Integer.parseInt(req.getParameter("producto"));
        
        ConversacionDAO.borrarMensaje(id, borrarParaReceptor);
        
        response.sendRedirect("conversacion.jsp?c="+comprador+"&v="+vendedor+"&p="+producto);
    }

    
}
