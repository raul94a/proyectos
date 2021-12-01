/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ConversacionDAO;
import dao.EstadisticaDAO;
import dao.ProductoDAO;
import dao.TratoDAO;
import dao.UsuarioDAO;
import java.util.ArrayList;
import modelo.*;
import utils.Fichero;
/**
 *
 * @author Raul
 */

public class JsltOperations {
    
    public JsltOperations(){}
    /* MENU DE INVITADO */
    public boolean menuInvitado(){
        return Boolean.parseBoolean(Fichero.getPropiedadApp("menu_invitado"));
    }
    /**funciones producto **/
    public ArrayList<Producto> productosAjenos(String id){
        return ProductoDAO.seleccionarProductosDeOtrosUsuarios(Integer.parseInt(id));
    }
    public boolean productosAjenosIsEmpty(String id){
        return productosAjenos(id).isEmpty();
    }
    public ArrayList<Producto> productosApp(){
        return ProductoDAO.seleccionarProductosDeLaApp();
    }
    public Producto getProducto(String id){
        int producto = Integer.parseInt(id);
        return ProductoDAO.seleccionarProductoPorId(producto);
    }
     public Producto getProducto(int id){
       
        return ProductoDAO.seleccionarProductoPorId(id);
    }
     public boolean isProductoVendido(String id){
         return ProductoDAO.isProductoVendido(Integer.parseInt(id));
     }
     public ArrayList<Producto> productosEnVenta(String usuario){
         return ProductoDAO.seleccionarProductosEnVentaUsuario(Integer.parseInt(usuario));
     }
     public int contarInteresadosEnProducto(int producto){
         return ProductoDAO.contarInteresadosEnProducto(producto);
     }
     public int contarArrayList(ArrayList<Object> obj){
         return obj.size();
     }
    /** funciones usuario
     * @param id del usuario en sesión
     **/
    public Usuario datosUsuario(String id){
        return UsuarioDAO.seleccionarUsuarioPorId(Integer.parseInt(id));
    }
    public Usuario datosUsuario(int id){
        return UsuarioDAO.seleccionarUsuarioPorId(id);
    }
    public float valoracionMedia(String id){
        return EstadisticaDAO.seleccionarValoracionMedia(Integer.parseInt(id));
    }
    public int toInt(String usuario){
        return Integer.parseInt(usuario);
    }
    public int numeroValoraciones(String id){
        return EstadisticaDAO.seleccionarEstadisticaUsuario(Integer.parseInt(id)).getValoracionesRecibidas();
    }
    /** funciones de tratos **/
    public Trato getTratoTemporal(String vendedor, String comprador, String producto){
        int v = toInt(vendedor);
        int c = toInt(comprador);
        int p = toInt(producto);
        return TratoDAO.seleccionarTratoTemporal(v,c,p);
    }
    public float getPrecioTratoTemporal(String vendedor, String comprador, String producto){
        int v = toInt(vendedor);
        int c = toInt(comprador);
        int p = toInt(producto);
        return TratoDAO.seleccionarTratoTemporal(v, c, p).getPrecio();
    }
    public boolean existeTratoTemporalRegistrado(String vendedor, String comprador, String producto){
        int v = Integer.parseInt(vendedor);
        int c = Integer.parseInt(comprador);
        int p = Integer.parseInt(producto);
        return TratoDAO.existeTratoTemporal(v, c, p);
    }
    public ArrayList<Trato> seleccionarTratos(String usuario){
        int id = Integer.parseInt(usuario);
        return TratoDAO.seleccionarTratosValorados(id);
    }
    public ArrayList<Trato> seleccionarTratosValorables(String usuario){
        int id = Integer.parseInt(usuario);
        return TratoDAO.seleccionarTratosSinValorarUsuario(id);
    }
    public ArrayList<Trato> getVentasUsuario(String usuario){
        return TratoDAO.seleccionarVentasUsuario(Integer.parseInt(usuario));
    }
     public ArrayList<Trato> getComprasUsuario(String usuario){
        return TratoDAO.seleccionarComprasUsuario(Integer.parseInt(usuario));
    }
    public int getComprador(int id){
       return TratoDAO.getComprador(id);
    }
     public int getIdCompra(int comprador, int vendedor, int producto){
         return TratoDAO.getIdTratoCompra(comprador, vendedor, producto);
     }
     public int getIdVenta(int comprador, int vendedor, int producto){
         return TratoDAO.getIdTratoVenta(comprador,vendedor,producto);
     }
     public boolean isTratoCalificado(int id){
         return TratoDAO.isTratoCalificado(id);
     }
    public ArrayList<Trato> getTratosCalificadosUsuario(String id){
        return TratoDAO.getTratosCalificadosUsuario(Integer.parseInt(id));
    }
    public boolean existeTratoSinValorar(String usuario){
        return TratoDAO.seleccionarTratosSinValorarUsuario(toInt(usuario)).size() > 0;
    }
    /** funciones de Conversacion **/
    public ArrayList<Conversacion> getConversacionesUsuario(int id){
       return ConversacionDAO.seleccionarConversacionesUsuario(id);
    }
    
    public int getIdConversacion(String c, String v, String p){
        int co = Integer.parseInt(c);
        int ve = Integer.parseInt(v);
       
      
         int pr = Integer.parseInt(p); 
        
   
        return ConversacionDAO.getIdConversacion(co,ve, pr);
    }
    public boolean usuarioPerteneceAConversacion(String comprador, String v, String p, String usuario){
      
        int u = Integer.parseInt(usuario);
        int id = getIdConversacion(comprador,v,p);
        return ConversacionDAO.usuarioPerteneceAConversacion(id, u);
    }
    public boolean isUsuarioComprador(int i, int producto){
        
        return ConversacionDAO.isUsuarioComprador(i, producto);
    }
    
    /** mensajes **/
    public ArrayList<Mensaje> getMensajes(int conversacion){
        return ConversacionDAO.seleccionarMensajesConversacion(conversacion);
    }
    public void actualizarMsjVistos(int mensaje, int emisor){
      
       ConversacionDAO.actualizarMensajesVistos(mensaje, emisor);
        
    }
    public boolean hayMensajesSinLeer(int conver, String usuario){
        return ConversacionDAO.hayMensajesSinLeer(conver, toInt(usuario));
    }
    public boolean hayAlgunMensajeSinLeer(String usuario){
        return ConversacionDAO.hayAlgunMensajeSinLeer(toInt(usuario));
    }
    /**Componentes de la App */
    public String getEstrellas(float valoracion){
        if(valoracion >= 0 && valoracion < 0.5){
            return "<i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>";
        }
        else if(valoracion >= 0.5 && valoracion < 1){
            return "<i class='fas fa-star-half-alt'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>";
        }
        else if(valoracion >= 1 && valoracion < 1.5){
            return "<i class='fas fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 1.5 && valoracion < 2){
            return "<i class='fas fa-star'></i><i class='fas fa-star-half-alt'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 2 && valoracion < 2.5){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 2.5 && valoracion < 3){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star-half-alt'></i><i class='far fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 3 && valoracion < 3.5){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='far fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 3.5 && valoracion < 4){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star-half-alt'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 4 && valoracion < 4.5){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='far fa-star'></i>\n";
        }
        else if(valoracion >= 4.5 && valoracion < 5){
            return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star-half-alt'></i>\n";
        }
        else return "<i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i><i class='fas fa-star'></i>\n";
    }
    
   
}
