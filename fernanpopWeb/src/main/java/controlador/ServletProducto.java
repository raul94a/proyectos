
package controlador;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Producto;

@WebServlet("/ServletProducto")
public class ServletProducto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        int idProducto = Integer.parseInt(req.getParameter("prod"));
        Producto p = Gestion.getProducto(idProducto);
        HttpSession s = req.getSession();
        s.setAttribute("producto", p);
        res.sendRedirect("producto.jsp");
        
    }
    
}
