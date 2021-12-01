/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import mensajeria.Comunicaciones;
import modelo.Producto;
import utils.Fichero;

/**
 *
 * @author Raul
 */
@WebServlet(name = "ServletImg", urlPatterns = {"/ServletImg"})
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ServletImg extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
     
          
        
            Part filePart = null;
            try {
                filePart = request.getPart("foto");
            } catch (IOException | ServletException ex) {
                Logger.getLogger(ServletSubirProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
		
		//llamamos al inputstream
	    InputStream fileInputStream = filePart.getInputStream();
	    
	  
            
            //CAPTURAMOS EL NOMBRE DEL ARCHIVO SUBIDO
            String nombreImagen = filePart.getSubmittedFileName();
            //Y lo copiamos en el directorio donde vamos a servir las imágenes
	    File fileToSave = new File(Fichero.getPropiedadApp("img_path") + nombreImagen);
            Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
            //acutalizamos la direccion de la foto
            int usuario = Integer.parseInt(request.getParameter("usuario"));
            UsuarioDAO.actualizarImagen(usuario, nombreImagen);
           // Fichero.registroLog("IMAGEN PERFIL CAMBIADA", "USUARIO:" + usuario + ";img:" + nombreImagen, false);

           HttpSession s = request.getSession();
           s.setAttribute("mensaje", "La imagen de perfil ha sido cambiada con éxito.");
           response.sendRedirect("index.jsp");
           
       
        }
  //  }
  
}

