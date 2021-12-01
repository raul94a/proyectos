
package controlador;

import dao.ConversacionDAO;
import dao.EstadisticaDAO;
import dao.TratoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mensajeria.Comunicaciones;
import modelo.Trato;
import utils.Fichero;

@WebServlet("/ServletValorar")
public class ServletValorar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        if(req.getParameter("btnVal") != null){
            int trato = Integer.parseInt(req.getParameter("trato"));
            float valoracion = Float.parseFloat(req.getParameter("val"));
            int usuario = Integer.parseInt(req.getParameter("usuarioValorado"));
            String comentario = req.getParameter("com");
            //UPDATEAMOS LA VALORACION
            Gestion.updateValoracion(comentario, valoracion, trato);
            //AUMENTAMOS EL NÚMERO DE VALORACIONES RECIBIDAS EN ESTADISTICAS
            Gestion.actualizarEstadisticas(valoracion, usuario);
            //ENVIAMOS EL CORREO
            Trato t = TratoDAO.getTrato(trato);
            Comunicaciones.enviarCalificacion(UsuarioDAO.seleccionarUsuarioPorId(usuario), t);
            
            req.setAttribute("success", true);
           // Fichero.registroLog("USUARIO VALORADO", "ID:" + usuario + "VALORACION:" + valoracion + "COMENTARIO:" + comentario, false);

            try {
                req.getRequestDispatcher("pendientes-de-valorar.jsp").forward(req, res);
            } catch (ServletException ex) {
                Logger.getLogger(ServletValorar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServletValorar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
