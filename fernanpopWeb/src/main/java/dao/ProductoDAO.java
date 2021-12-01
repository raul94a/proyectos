package dao;

import modelo.Producto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProductoDAO {

    private final static String SQL_INSERTAR_PRODUCTO = "insert into productos(id, nombre, precio, descripcion, estado, img, vendedor) values (?,?,?,?,?,?,?)";

    private final static String SQL_BUSCAR_TEXTO = "select * from productos where vendido <> 1 and vendedor <> ? and (nombre like ? or descripcion like ?)";
    private final static String SQL_SELECCIONAR_PRODUCTOS_EN_VENTA_USUARIO =
            "select * from productos where vendedor = ? and vendido <> 1 order by precio;";
    private final static String SQL_SELECCIONAR_PRODUCTOS_POR_ID = "select * from productos where id = ?";
    private final static String SQL_EXISTE_ID_PRODUCTOS = "select id from productos where id = ?";
    private final static String SQL_SELECCIONAR_PRODUCTOS_AJENOS = "select * from productos where vendedor <> ? and vendido <> 1 order by precio";
    private final static String SQL_SELECCIONAR_PRODUCTOS_APP = "select * from productos where vendido <> 1 order by precio;";
    private final static String SQL_SELECCIONAR_NUMERO_INTERESADOS =
            "SELECT COUNT(*) as 'numero' FROM tratosTemporales where producto = ?";
   
    /**update**/
    private final static String SQL_UPDATE_ALGUIEN_INTERESADO_A_TRUE = "update productos set alguienInteresado = 1 where id = ?";
    private final static String SQL_UPDATE_ALGUIEN_INTERESADO_A_FALSE = "update productos set alguienInteresado = 0 where id = ?";
    private final static String SQL_UPDATE_PRODUCTO_VENDIDO = "update productos set vendido = 1, alguienInteresado = 0 where id = ?";
    private final static String SQL_UPDATE_PRODUCTO = "update productos set nombre = ?, precio = ?, descripcion = ?, estado = ? where id = ?";
    
    /* delete */
    private final static String SQL_DELETE_PRODUCTO = "delete from productos where id = ?";

    public ProductoDAO() {
    }

    /*SQL INSERT*/
    public static int insertarProducto(Producto p){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERTAR_PRODUCTO);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getNombre());
            stmt.setFloat(3, p.getPrecio());
            stmt.setString(4,p.getDescripcion());
            stmt.setString(5,p.getEstado());
            stmt.setString(6, p.getImagen());
            stmt.setInt(7, p.getVendedor());
            /*push a la base de datos*/
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

    /* SQL SELECT */
    public static int contarInteresadosEnProducto(int producto){
        int numero = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_NUMERO_INTERESADOS);
            stmt.setInt(1, producto);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                numero = rs.getInt("numero");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return numero;
    }
    public static ArrayList<Producto> seleccionarPorTexto(int idUser, String term) {
        term = "%" + term + "%";
        ArrayList<Producto> productos = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_TEXTO);
            stmt.setInt(1, idUser);
            stmt.setString(2, term);
            stmt.setString(3, term);
            rs = stmt.executeQuery();
            productos = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int vendedor = rs.getInt("vendedor");
                String imagen = rs.getString("img");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Producto p = new Producto(id, nombre, precio, descripcion, estado, imagen, fecha, vendedor);
                productos.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return productos;
    }
    public static ArrayList<Producto> seleccionarProductosEnVentaUsuario(int idUser){
        ArrayList<Producto> productos = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_PRODUCTOS_EN_VENTA_USUARIO);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            productos = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int vendedor = rs.getInt("vendedor");
                String imagen = rs.getString("img");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Producto p = new Producto(id, nombre, precio, descripcion, estado, imagen, fecha, vendedor);
                productos.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return productos;
    }
    public static Producto seleccionarProductoPorId(int id){

        Producto producto = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_PRODUCTOS_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int vendedor = rs.getInt("vendedor");
                String imagen = rs.getString("img");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                producto = new Producto(id, nombre, precio, descripcion, estado, imagen, fecha, vendedor);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return producto;
    }
    public static boolean existeIdEnProductos(int identificador){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_EXISTE_ID_PRODUCTOS);
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
    public static ArrayList<Producto> seleccionarProductosDeOtrosUsuarios(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Producto> prods = null;
        try{
            prods = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_PRODUCTOS_AJENOS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int vendedor = rs.getInt("vendedor");
                String imagen = rs.getString("img");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Producto p = new Producto(identificador, nombre, precio, descripcion, estado, imagen, fecha,vendedor);
                prods.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return prods;
    }
    public static ArrayList<Producto> seleccionarProductosDeLaApp(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Producto> prods = null;
        try{
            prods = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_PRODUCTOS_APP);
            rs = stmt.executeQuery();
            while(rs.next()){
                int identificador = rs.getInt("id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int vendedor = rs.getInt("vendedor");
                String imagen = rs.getString("img");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Producto p = new Producto(identificador, nombre, precio, descripcion, estado, imagen, fecha,vendedor);
                prods.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return prods;
    }
    public static boolean isProductoVendido(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean vendido = false;
        try{
            conn = Conexion.getConnection(  );
            stmt = conn.prepareCall("select vendido from productos where id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next       ()){
                vendido = rs.getInt("vendido") == 1;
            }
       
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return vendido;
    }
    
    
    
    /**SQL UPDATE **/
    public static int actualizarInteresado(int id, boolean hayAlguienInteresado){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareCall(hayAlguienInteresado ? 
                    SQL_UPDATE_ALGUIEN_INTERESADO_A_TRUE : SQL_UPDATE_ALGUIEN_INTERESADO_A_FALSE);
            stmt.setInt(1, id);
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
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
    public static int actualizarProductoAVendido(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_PRODUCTO_VENDIDO);
            stmt.setInt(1, id);
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
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
     public static int actualizarProducto(Producto p){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_PRODUCTO);
            stmt.setString(1, p.getNombre());
            stmt.setFloat(2, p.getPrecio());
            stmt.setString(3, p.getDescripcion());
            stmt.setString(4, p.getEstado());
            stmt.setInt(5, p.getId());
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
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
     
     public static int borrarProducto(int id){
         Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_PRODUCTO);
            stmt.setInt(1, id);
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
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

}
