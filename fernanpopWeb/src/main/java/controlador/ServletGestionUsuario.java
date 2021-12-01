
package controlador;

import dao.UsuarioDAO;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mensajeria.Comunicaciones;
import modelo.Usuario;
import seguridad.Cifrado;
import utils.Fichero;


@WebServlet(name = "ServletGestionUsuario", urlPatterns = {"/ServletGestionUsuario"})
public class ServletGestionUsuario extends HttpServlet {
    
    private String param(HttpServletRequest req, String param){
        return req.getParameter(param);
    }
   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
     
        String tipo = param(req, "tipo");
        int idUsuario = Integer.parseInt(param(req, "usuario"));
        Usuario u = Gestion.getUsuario(idUsuario);
        HttpSession s = req.getSession();
        boolean usuarioBorrado = false;
        switch(tipo){
            case "editarPerfil":
                String nombre = param(req, "nombre");
                String email = param(req, "email");
                //comprobamos el email. Si ya está registrado no se puede cambiar.
                //Si no está registrado se cambia y se madna un nuevo token de validacion. 
                //Si es el mismo que el del usuario no hacemos nada
                if(Gestion.existeUsuarioRegistrado(email)){
                    //NO PODEMOS CAMBIAR EL EMAIL PORQUE EL EMAIL YA ESTA REGISTRADO
                    //O BIEN EL EMAIL ESCRITO ES EL MISMO QUE EL QUE YA SE POSEIA
                    UsuarioDAO.actualizarNombreEmail(nombre, u.getEmail(), idUsuario);
                    s.setAttribute("mensaje", "El nombre ha sido actualizado. Sin embargo, ha sido imposible actualizar el email: el introducido ya se encuentra registrado en el sistema");
                    //Fichero.registroLog("PERFIL USUARIO EDITADO", "ANTIGUO NOMBRE:" + u.getNombre() +";Nombre actual:" + nombre + 
                    //         ";Email Antiguo: " +u.getEmail() + ";Email nuevo:" + email, false);
                } 
                else{
                    //cambiamos el email y MANDAMOS TOKEN DE VALIDACION
                     UsuarioDAO.actualizarNombreEmail(nombre, email, idUsuario);
                     //GENERAMOS TOKEN
                     String token = Gestion.generarToken();
                     //ACTUALIZAMOS
                     UsuarioDAO.actualizarToken(token, idUsuario);
                    // Fichero.registroLog("PERFIL USUARIO EDITADO", "ANTIGUO NOMBRE:" + u.getNombre() +";Nombre actual:" + nombre + 
                     //        ";Email Antiguo: " +u.getEmail() + ";Email nuevo:" + email, false);
                     //SETEAMOS
                     u.setNombre(nombre);
                     u.setEmail(email);
                     u.setToken(token);
                     //ENVIAMOS X EMAIL
                     Comunicaciones.enviarToken(u);
                      s.setAttribute("mensaje", "El nombre y el email han sido modificados con éxito. Se le ha enviado un token de validación a su nuevo correo.");
                }
                break;
            case "editarPass":
                String passAntigua = param(req, "pass");
                String passUser = Cifrado.descifrar(u.getPassword());
                
                if(passAntigua.equals(passUser)){
                     String pass = param(req, "passNueva");
                     String passRep = param(req, "passNuevaRep");
                     if(pass.equals(passRep)){
                         UsuarioDAO.actualizarPassword(Cifrado.cifrar(pass), idUsuario);
                         s.setAttribute("mensaje", "La contraseña ha sido modificada con éxito");
                       //  Fichero.registroLog("PASS USUARIO MODIFICADA", "ID:" + idUsuario, false);
                     }else{
                        s.setAttribute("mensaje", "Error. Las contraseñas no coinciden");

                     }
                }else{
                    s.setAttribute("mensaje", "Error. La contraseña original es incorrecta");
                }
               
                
                break;
            case "borrar":
                usuarioBorrado = UsuarioDAO.borrarUsuario(idUsuario) > 0;
                s.setAttribute("mensaje", "Tu cuenta ha sido borrado con éxito.");
                s.removeAttribute("usuario");
               // Fichero.registroLog("USUARIO BORRADO / DESACTIVADO", "ID:" + idUsuario, false);
                break;
        }
        if(!usuarioBorrado){
            res.sendRedirect("cuenta.jsp");

        }else{
            res.sendRedirect("index.jsp");
        }
        
        
        
        
        
    }


}
