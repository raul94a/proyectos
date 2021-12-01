package controlador;

import dao.*;
import mensajeria.Comunicaciones;
import modelo.Producto;
import modelo.Trato;
import modelo.Usuario;
import utils.*;
import java.util.ArrayList;


public class Gestion {
    public Gestion() {
    }

    /* generadores */
    public static int generarIdAleatoria() {
        return (int) (Math.random() * 1000000) + 1;
    }

    public static String generarToken() {
        String token = "";
        for (int i = 0; i < 6; i++) {
            token = token.concat(Utils.generarCaracter());
        }
        return token;
    }
    ///////////////////////////////////////////

    /** FUNCIONES DE USUARIO **/
    public static boolean registrarUsuario(Usuario u) {
        //generamos el token
        u.setToken(Gestion.generarToken());
        if (UsuarioDAO.insertar(u) > 0) {

            return Comunicaciones.enviarToken(u);
        }
        return false;
    }
    public static boolean existeUsuarioRegistrado(String email){
        return UsuarioDAO.buscarSiEmailExiste(email);
    }
    public static boolean existeIdEnUsuarios(int id){
        return UsuarioDAO.buscarSiIDestaRegistrada(id);
    }
    public static int validarUsuario(String correo, String token){
        return UsuarioDAO.validarUsuario(correo, token);
    }
    public static int getIdUsuario(String correo){
        return UsuarioDAO.buscarIdUsuario(correo);
    }
    public static Usuario login(String email, String pass){
        return UsuarioDAO.buscarUsuarioLogin(email, pass);
    }
    public static boolean isUsuarioValidado(Usuario u){
        return UsuarioDAO.buscarSiUsuarioEstaValidado(u.getEmail());
    }
   public static Usuario getUsuario(int id){
       return UsuarioDAO.getUsuario(id);
   }
   /**FUNCIONES DE PRODUCTOS**/
   /* * 
     * @param id => es el id del usuario que tenga iniciada la sesión en la app
   
    **/
    public static ArrayList<Producto> getProductosAjenos(int id){
       return ProductoDAO.seleccionarProductosDeOtrosUsuarios(id);
    }
    public static ArrayList<Producto> getProductosBuscador(int id, String term){
        return ProductoDAO.seleccionarPorTexto(id, term);
    }
    public static Producto getProducto(int id){
        return ProductoDAO.seleccionarProductoPorId(id);
    }
    public static boolean existeIdEnProductos(int id){
        return ProductoDAO.seleccionarProductoPorId(id)== null;
    }
    public static int actualizarInteresado(int id, boolean existeAlguienInteresado){
        return ProductoDAO.actualizarInteresado(id, existeAlguienInteresado);
    }
    public static void actualizarProductoAVendido(int idProd){
        ProductoDAO.actualizarProductoAVendido(idProd);
    }
    
    
    /** FUNCIONES DE TRATOS **/
    
    
    /**
     * Esta función genera un trato de tipo compra con los mismos datos que el trato de venta,
     * a excepción del id.
     *
     * @param t es el trato de VENTA que ha sido generado previamente para insertarse en la base de datos.
     *          Cuando esta función es llamada el trato aún no ha sido insertado
     * @return objeto Trato de tipo compra
     * @uso Es llamada por la función insertarTratosSQL()
     */
    public static Trato generarTratoDeCompra(Trato t) {
        int id = 0;
        do {
            id = generarIdAleatoria();
        } while (TratoDAO.existeIdEnTratos(id));

        return Trato.generarTrato(id, t);
    }

    public static void sumarCompraVentaSQL(Trato venta) {
        int comprador = venta.getIdOtroUser();
        int vendedor = venta.getProducto().getVendedor();
        EstadisticaDAO.actualizarNumeroDeComprasUsuario(comprador);
        EstadisticaDAO.actualizarNumeroDeVentasUsuario(vendedor);
    }
    public static void updateValoracion(String comentario, float valoracion, int trato){
        TratoDAO.updateValoracion(comentario, valoracion, trato);
    }
    public static void borrarTratosTemporales(int idProd){
        TratoDAO.borrarTratosTemporales(idProd);
    }
    public static Trato seleccionarTratoTemporal(int user, int comprador, int idProd){
        return TratoDAO.seleccionarTratoTemporal(user, comprador, idProd);
    }
    public static void borrarTratoTemporal(int comprador, int idProd){
        TratoDAO.borrarTratoTemporal(comprador, idProd);
    }
    /**
     * Esta función insertará los tratos de tipo venta y compra para los mismos usuarios y el mismo producto
     * en la base de datos
     *
     * @param venta es el trato de tipo venta que se ha generado para insertarse en la tabla tratos de la BD
     * @return
     */
    public static int insertarTratosSQL(Trato venta) {
       if (TratoDAO.insertarTrato(venta,false) > 0 ){
            Trato compra = generarTratoDeCompra(venta);
            if(TratoDAO.insertarTrato(compra,false) > 0){
                sumarCompraVentaSQL(venta);
                return 1;
            }
        }
        return 0;
    }
        public static int insertarTrato(Trato trato, boolean temporal){
        return TratoDAO.insertarTrato(trato, temporal);
    }
    
    public static ArrayList<Trato> seleccionarTratosValorados(int usuario){
        return TratoDAO.seleccionarTratosValorados(usuario);
    }
    
    /** CONVERSACION Y CHAT **/
    public static int crearConversacion(int comprador, int vendedor, int producto){
        return ConversacionDAO.insertar(comprador, vendedor, producto);
    }
    public static boolean existeConversacion(int comprador, int vendedor, int producto){
        return ConversacionDAO.existeConversacionConUsuariosYProducto(comprador, vendedor, producto);
    }
    public static void borrarConversacion(int id){
        
        ConversacionDAO.borrarConversacion(id);
    }
    public static void insertarMensaje(String mensaje, int emisor, int receptor, int conversacion){
        ConversacionDAO.insertarMensaje(mensaje, emisor, receptor, conversacion);
    }
    /* modificacion menu invitado */
    public static void cambiarMenuInvitado(boolean cerrar){
        Fichero.cambiarPropiedad("menu_invitado", String.valueOf(cerrar), 4);
    }
    /* ESTADISTICAS */
    public static void actualizarEstadisticas(float valoracion, int usuario){
        EstadisticaDAO.actualizarEstadisticas(valoracion, usuario);
    }
}


