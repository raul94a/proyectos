package controlador;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    
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
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        String param = getParam(req, "a");
        modificarAtributoSesion(req, "a", !param.equals("back") ? param : null);
        redireccionar(res);
    }
    
}
