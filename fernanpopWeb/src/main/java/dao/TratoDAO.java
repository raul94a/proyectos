package dao;

import modelo.Producto;
import modelo.Trato;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TratoDAO {

    private static final String SQL_INSERTAR_TRATO = 
            "insert into tratos(id,vendedor,comprador,producto,tipo,precio) values(?,?,?,?,?,?)";
    
     private static final String SQL_INSERTAR_TRATO_TEMPORAL = 
             "insert into tratosTemporales(id,vendedor,comprador,producto,tipo,precio) values(?,?,?,?,?,?)";

    private static final String SQL_BUSCAR_SI_TRATO_ESTA_CALIFICADO = "select id from tratos where id = ? and tratoCalificado = 1";
     
    private static final String SQL_BUSCAR_SI_TRATO_TEMPORAL_YA_EXISTE = 
            "select id from tratosTemporales where vendedor = ? and comprador = ? and producto = ?";
    private static final String SQL_SELECCIONAR_TRATO_TEMPORAL =
            "select * from tratosTemporales where vendedor = ? and comprador = ? and producto = ?";
    
    private static final String SQL_SELECCIONAR_COMPRADOR_TRATO =
            "select comprador from tratos where id = ?";
     
    private static final String SQL_SELECCIONAR_VENTAS_USUARIO =
            "select * from tratos where vendedor = ? and tipo = 'venta' order by fecha";
    private static final String SQL_SELECCIONAR_COMPRAS_USUARIO =
            "select * from tratos where comprador = ? and tipo = 'compra' order by fecha";

    private static final String SQL_SELECCIONAR_TRATOS_VALORABLES_USUARIO = 
            "select * from tratos ((comprador = ? and vendedor <> ? and tipo = 'venta')"
            + " or (comprador <> ? and vendedor = ? and tipo = 'compra')) " +
            " order by fecha";
    private static final String SQL_SELECCIONAR_ID_TRATOS_VALORABLES_USUARIO =
            "select id from tratos where tratoCalificado <> 1 and "
            + "((comprador = ? and vendedor <> ? and tipo = 'venta') " +
            " or (comprador <> ? and vendedor = ? and tipo = 'compra')) " +
            " order by fecha";
    private static final String SQL_SELECCIONAR_TRATOS_SIN_VALORAR =
            "select * from tratos where tratoCalificado = 0 and " +
                    "((comprador = ? and vendedor <> ? and tipo = 'venta') " +
                    "or (comprador <> ? and vendedor = ? and tipo = 'compra')) " +
                    "order by fecha";
    private static final String SQL_SELECCIONAR_ID_TRATOS_USUARIO_SIN_VALORAR =
            "select id from tratos where tratoCalificado = 0 and "
            +  "((comprador = ? and vendedor <> ? and tipo = 'venta') " +
                    "or (comprador <> ? and vendedor = ? and tipo = 'compra')) " +
                    "order by fecha";
    private static final String SQL_SELECCIONAR_CALIFICACIONES_RECIBIDAS_USER =
            "select * from tratos where tratoCalificado = 1 and ((vendedor = ? and comprador <> ? "
            + "and tipo = 'venta') or ( vendedor <> ? and comprador = ? and tipo = 'compra'))";

    private static final String SQL_EXISTE_ID_TRATO =
            "select id from tratos where id = ?";

    private static final String SQL_SELECCIONAR_PRECIO_TRATO =
            "select precio from tratos where id = ?";
    
    private static final String SQL_SELECCIONAR_TRATOS_VALORADOS_DE_USUARIO =
            "select * from tratos where tratoCalificado = 1 and ((comprador = ? and tipo = 'compra') or (vendedor = ? and tipo = 'venta'))";
    private static final String SQL_SELECCIONAR_ID_TRATO_COMPRA =
            "select id from tratos where comprador = ? and vendedor = ? and producto = ? and tipo = 'compra'";
     private static final String SQL_SELECCIONAR_ID_TRATO_VENTA =
            "select id from tratos where comprador = ? and vendedor = ? and producto = ? and tipo = 'venta'";
     private static final String SQL_SELECCIONAR_TRATO_POR_ID = "select * from tratos where id = ?";
    
    /**UPDATE**/
    private static final String SQL_UPDATE_TRATO_VALORACION = 
            "update tratos set tratoCalificado = 1, comentario = ?, valoracion = ? where id = ?";
    
    /** delete **/
    
    private static final String SQL_DELETE_TRATOS_TEMPORALES = 
            "delete from tratosTemporales where producto = ?";
    
    private static final String SQL_BORRAR_UN_TRATO_TEMPORAL = 
            "delete from tratosTemporales where comprador = ? and producto = ?";

    public TratoDAO(){}

    /*insert*/
    public static int insertarTrato(Trato t, boolean temporal){
        int idTrato = t.getId();
        int idVendedor = t.getProducto().getVendedor();
        int idComprador = t.getIdOtroUser();
        int idProducto = t.getProducto().getId();
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = temporal ? SQL_INSERTAR_TRATO_TEMPORAL : SQL_INSERTAR_TRATO;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement( query );
            stmt.setInt(1, idTrato);
            stmt.setInt(2, idVendedor);
            stmt.setInt(3, idComprador);
            stmt.setInt(4,idProducto);
            stmt.setString(5,t.getTipo());
            stmt.setFloat(6, t.getPrecio());
            registros = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;
    }

    /*select*/
    public static boolean isTratoCalificado(int id){
          Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer ide = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_TRATO_ESTA_CALIFICADO);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()){
                ide = rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try {
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return ide != null;
    }
    public static ArrayList<Trato> seleccionarVentasUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> ventas = null;

        try{
            ventas = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_VENTAS_USUARIO);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt("comprador");
                float valoracion = rs.getFloat("valoracion");
                String comentario = rs.getString("comentario");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p, comentario==null?"":comentario,valoracion, tratoCalificado);
                ventas.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return ventas;
    }
    public static ArrayList<Trato> seleccionarComprasUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> compras = null;

        try{
            compras = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_COMPRAS_USUARIO);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt("vendedor");
                float valoracion = rs.getFloat("valoracion");
                String comentario = rs.getString("comentario");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p, comentario==null?"":comentario,valoracion, tratoCalificado);
                compras.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return compras;
    }
    public static ArrayList<Trato> getTratosCalificadosUsuario(int id){
          Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> tratosValorados = null;

        try{
            tratosValorados = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_CALIFICACIONES_RECIBIDAS_USER);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            stmt.setInt(4, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt(tipo.equals("compra")?"vendedor":"comprador");
                float precio = rs.getFloat("precio");
                float valoracion = rs.getFloat("valoracion");
                String comentario = rs.getString("comentario");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p, comentario, valoracion, tratoCalificado);
                tratosValorados.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tratosValorados;
    }
    public static ArrayList<Trato> seleccionarTratosSinValorarUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> tratosSinValorar = null;

        try{
            tratosSinValorar = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_TRATOS_SIN_VALORAR);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            stmt.setInt(4, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt("comprador");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p,tratoCalificado);
                tratosSinValorar.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tratosSinValorar;
    }
    
    public static ArrayList<Trato> seleccionarTodosTratosValorablesUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> tratosSinValorar = null;

        try{
            tratosSinValorar = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_TRATOS_VALORABLES_USUARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            stmt.setInt(4, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt("comprador");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p,tratoCalificado);
                tratosSinValorar.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tratosSinValorar;
    }
    
    public static ArrayList<Integer> seleccionarIdentificadoresTratosUsuarioSinValorar(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> tratosSinValorar = null;

        try{
            tratosSinValorar = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_TRATOS_USUARIO_SIN_VALORAR);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            stmt.setInt(4, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                tratosSinValorar.add(identificador);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tratosSinValorar;
    }
    public static ArrayList<Integer> seleccionarIdentificadoresTratosValorablesUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> tratosSinValorar = null;

        try{
            tratosSinValorar = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_TRATOS_VALORABLES_USUARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            stmt.setInt(4, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                tratosSinValorar.add(identificador);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tratosSinValorar;
    }
    public static boolean existeIdEnTratos(int identificador){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_EXISTE_ID_TRATO);
            stmt.setInt(1,identificador);
            rs = stmt.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try {
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return id != null;
    }
    public static ArrayList<Trato> seleccionarTratosValorados(int id){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Trato> ventas = null;

        try{
            ventas = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_TRATOS_VALORADOS_DE_USUARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                Date fecha = rs.getDate("fecha");
                int idOtroUser = rs.getInt(tipo.equals("venta")?"comprador":"vendedor");
                float valoracion = rs.getFloat("valoracion");
                String comentario = rs.getString("comentario");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                boolean tratoCalificado = rs.getInt("tratoCalificado") == 1;
                Trato t = new Trato(identificador,tipo,fecha,idOtroUser,precio,p, comentario,valoracion, tratoCalificado);
                ventas.add(t);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return ventas;
    }
    public static boolean existeTratoTemporal(int vendedor, int comprador, int producto){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_TRATO_TEMPORAL_YA_EXISTE);
            stmt.setInt(1,vendedor);
            stmt.setInt(2,comprador); 
            stmt.setInt(3,producto);
             
            rs = stmt.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try {
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return id != null;
    }
    public static Trato seleccionarTratoTemporal(int vendedor, int comprador, int producto){
           Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trato venta = null;

        try{
            
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_TRATO_TEMPORAL);
            stmt.setInt(1, vendedor);
            stmt.setInt(2, comprador);
            stmt.setInt(3, producto);
            
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String tipo = rs.getString("tipo");
                int idOtroUser = rs.getInt("comprador");
                float precio = rs.getFloat("precio");
                Producto p = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                venta  = new Trato(identificador,tipo,idOtroUser,precio,p);
               
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return venta;
    }
    
    public static int getComprador(int id){
            Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trato venta = null;
        int comprador = 0;
        try{
            
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement( SQL_SELECCIONAR_COMPRADOR_TRATO);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            while(rs.next()){
                comprador = rs.getInt("comprador");
               
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return comprador;
    }
    
    public static int getIdTratoVenta(int comprador, int vendedor, int producto){
           Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        try{
            
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_TRATO_VENTA);
            stmt.setInt(1, comprador);
            stmt.setInt(2, vendedor);
            stmt.setInt(3, producto);
            
            rs = stmt.executeQuery();
            while(rs.next()){
               id = rs.getInt("id");
               
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }
    public static int getIdTratoCompra(int comprador, int vendedor, int producto){
           Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        try{
            
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_TRATO_COMPRA);
            stmt.setInt(1, comprador);
            stmt.setInt(2, vendedor);
            stmt.setInt(3, producto);
            
            rs = stmt.executeQuery();
            while(rs.next()){
               id = rs.getInt("id");
               
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( rs != null ){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }
    /**UPDATE**/
    public static int updateValoracion(String comentario, float valoracion, int id){
          Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_TRATO_VALORACION);
            stmt.setString(1, comentario);
            stmt.setFloat(2, valoracion);
            stmt.setInt(3, id);
            registros = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        System.out.println("REGISTROS: " + registros);
        return registros;
    }
   
    /**DELETE**/
    public static int borrarTratosTemporales(int producto){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_TRATOS_TEMPORALES);
            stmt.setInt(1, producto);
            stmt.execute();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( stmt != null ){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }   
        return registros;
    }
    public static int borrarTratoTemporal(int comprador, int producto){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BORRAR_UN_TRATO_TEMPORAL);
            stmt.setInt(1, comprador);
            stmt.setInt(2, producto);
            stmt.execute();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if ( stmt != null ){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }   
        return registros;
    }
    
    public static Trato getTrato(int id){
        Connection conn = null;
        Trato t = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_TRATO_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                String tipo = rs.getString("tipo");
                float valoracion = rs.getFloat("valoracion");
                Producto producto = ProductoDAO.seleccionarProductoPorId(rs.getInt("producto"));
                t = new Trato(tipo, valoracion, producto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(rs != null){
                try {
                    conn.close();
                    stmt.close();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TratoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        return t;
    }

}