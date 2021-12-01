/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DataProducto;

/**
 *
 * @author Raul
 */
@WebServlet("/ServletMesa")

public class ServletMesa extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        
            String mesa = req.getParameter("mesa");
           
            try {
                res.sendRedirect(".?i=4&m=" + mesa);
            } catch (IOException ex) {
                Logger.getLogger(ServletPedidos.class.getName()).log(Level.SEVERE, null, ex);
            }       
       
    }
}