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


@WebServlet("/ServletSubirProducto")
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ServletSubirProducto extends HttpServlet {
    
    private String getParam(HttpServletRequest req, String parametro){
        return req.getParameter(parametro);
    }
    private int getParamInt(HttpServletRequest req, String parametro){
        return Integer.parseInt(getParam(req,parametro));
    }
    private float getParamFloat(HttpServletRequest req, String parametro){
        return Float.parseFloat(getParam(req, parametro));
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
         String vendedor = getParam(request, "vendedor");
       
        Part filePart = null;
        try {
            filePart = request.getPart("foto");
        } catch (IOException | ServletException ex) {
            Logger.getLogger(ServletSubirProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

      //get the InputStream to store the file somewhere
        InputStream fileInputStream = filePart.getInputStream();

     
        
        //CAPTURAMOS EL NOMBRE DEL ARCHIVO SUBIDO
        String nombreImagen = filePart.getSubmittedFileName();
        //Y lo copiamos en el directorio donde vamos a servir las imágenes
        File fileToSave = new File(/*Fichero.getPropiedadApp("img_path")*/ "C:/Users/Raul/Desktop/DesarrolloApp/java/proyectos/fernanpopWeb/src/main/webapp/img/" + nombreImagen);
        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        //Recogida del resto de datos del formulario
        String nombre = getParam(request, "nombre");
        float precio = getParamFloat(request, "euros");
        String estado = getParam(request, "estado");
        String descripcion = getParam(request, "descripcion");

        /* Subimos los datos del producto a la base de datos!!!!!!! */
        int id = 0;
       // do{
           id = Gestion.generarIdAleatoria();
       // }while(Gestion.existeIdEnProductos(id));
        Producto p = new Producto(id, nombre, precio, descripcion, estado, nombreImagen, vendedor);
        


        if(ProductoDAO.insertarProducto(p) > 0 ){
            Comunicaciones.enviarProducto(p, UsuarioDAO.seleccionarUsuarioPorId(Integer.parseInt(vendedor)));
        }
        HttpSession sesion = request.getSession();
        sesion.setAttribute("mensaje","¡El producto " + nombre + " ha sido subido con éxito a FernanPop!");
        response.sendRedirect("index.jsp");
       // Fichero.registroLog("NUEVO PRODUCTO SUBIDO POR EL USUARIO", "Usuario:" + vendedor + ";Producto:" + id, false);

      
    }
        

  
}
