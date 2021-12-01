
package controlador;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Producto;

@WebServlet("/ServletBuscador")
public class ServletBuscador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        if(req.getParameter("btnBuscar") != null){
            String buscador = req.getParameter("buscador");
            if (buscador == null) {
                buscador = "";
            }
            System.out.println(buscador);
            Integer id = (Integer) req.getSession().getAttribute("usuario");
            //dependiendo de si haya sessión o no, se establecerá un id que no pueda tener ningún usuario o se eligirá el id del usuario
            //con el fin de que sus productos no le sean mostrados
            int idUser = id == null ? 0 : id;
            
            ArrayList<Producto> prods = Gestion.getProductosBuscador(idUser, buscador);
            req.setAttribute("prods", prods);
            try {
                req.getRequestDispatcher("index.jsp").forward(req, res);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ServletBuscador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
