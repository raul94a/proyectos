package dao;

import modelo.Usuario;
import utils.Utils;

import java.rmi.ConnectIOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modelo.Estadistica;
import seguridad.Cifrado;

public class UsuarioDAO {

    private static final String SQL_INSERT = "insert into usuarios" +
            "(id,nombre,email,password,movil,token,fechaRegistro,sexo, img)" +
            "VALUES (?,?,?,?,?,?,?,?, 'misteryman.png')";

    private static final String SQL_SELECCIONAR_USUARIO_POR_ID = "select * from usuarios where id  = ? ";
    private static final String SQL_SELECCIONAR_ID_USUARIO_POR_CORREO = "select id from usuarios where email = ?";
    private static final String SQL_BUSCAR_USER_CORREO_PASS = "select * from usuarios where email = ? and password = ?";
    private static final String SQL_BUSCAR_SI_USER_ESTA_VALIDADO = "select id from usuarios where email = ? and validado <> 0";
    private static final String SQL_BUSCAR_SI_EMAIL_EXISTE = "select id from usuarios where email = ?";
    private static final String SQL_BUSCAR_SI_ID_EXISTE = "select id from usuarios where id = ?";
    private static final String SQL_BUSCAR_SI_USUARIO_ESTA_ACTIVO = "select activo from usuarios where email = ?";
    private static final String SQL_ACTIVAR_USUARIO = "update usuarios set activo = 1 where email = ? and activo <> 1";
     private static final String SQL_VALIDAR_USER = "update usuarios set validado = 1 where email = ? and token = ?";
     private static final String SQL_ACTUALIZAR_IMG = "update usuarios set img = ? where id = ?";
     private static final String SQL_ACTUALIZAR_NOMBRE_EMAIL = "update usuarios set nombre = ?, email = ? where id = ?";
     private static final String SQL_SELECCIONAR_DATOS_USUARIO = "select * from usuarios where id = ?";
     private static final String SQL_ACTUALIZAR_TOKEN = "update usuarios set token = ?, validado = 0 where id  = ?";
     private static final String SQL_ACTUALIZAR_PASS = "UPDATE usuarios set password = ? where email = ?";
     
     private static String SQL_BORRAR_USUARIO = "delete from usuarios where id = ?";
     


    public UsuarioDAO(){}

    /*INSERT SQL*/
    public static int insertar(Usuario u){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, u.getId());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPassword());
            stmt.setInt(5, u.getTelefono());
            stmt.setString(6, u.getToken());
            stmt.setString(7, Utils.formatDateSQL(u.getFechaRegistro()));
            stmt.setString(8, String.valueOf(u.getSexo()));
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
    /*SELECT SQL*/
    public static Usuario buscarUsuarioLogin(String correo, String password){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_USER_CORREO_PASS);
            stmt.setString(1, correo);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int rol = rs.getInt("rol");
                int telefono = rs.getInt("movil");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String token = rs.getString("token");
                Date fechaRegistro = rs.getDate("fechaRegistro");
                char sexo = rs.getString("sexo").charAt(0);
                boolean validado = rs.getBoolean("validado");
                boolean activo = rs.getBoolean("activo");
                String img = rs.getString("img");
                u = new Usuario (id,rol,telefono,nombre,email,pass,token,fechaRegistro,sexo,validado,activo,img == null?"":img);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return u;
    }
    
      public static Usuario getUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_USUARIO_POR_ID);
            stmt.setInt(1, id);
          
            rs = stmt.executeQuery();
            while(rs.next()) {
         
                int rol = rs.getInt("rol");
                int telefono = rs.getInt("movil");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String token = rs.getString("token");
                Date fechaRegistro = rs.getDate("fechaRegistro");
                char sexo = rs.getString("sexo").charAt(0);
                boolean validado = rs.getBoolean("validado");
                boolean activo = rs.getBoolean("activo");
                String img = rs.getString("img");
                u = new Usuario (id,rol,telefono,nombre,email,pass,token,fechaRegistro,sexo,validado,activo,img == null?"":img);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return u;
    }
    
    
    public static boolean buscarSiUsuarioEstaValidado(String correo){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        boolean isUsuarioValidado = false;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_USER_ESTA_VALIDADO);
            stmt.setString(1, correo);
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
            }

            isUsuarioValidado = id != null;
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return isUsuarioValidado;
    }
    public static boolean buscarSiEmailExiste(String email){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_EMAIL_EXISTE);
            stmt.setString(1, email);
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
        return id != null;
    }
    public static boolean buscarSiIDestaRegistrada(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer identificador = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_ID_EXISTE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                identificador = rs.getInt("id");
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
        return identificador != null;
    }
    public static boolean buscarSiUsuarioEstaActivo(String email){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean activo = false;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_USUARIO_ESTA_ACTIVO);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while(rs.next()){
                activo = rs.getInt("activo") == 1;
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
        return activo;
    }
    public static int buscarIdUsuario(String email){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer identificador = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_USUARIO_POR_CORREO);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while(rs.next()){
                identificador = rs.getInt("id");
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
        return identificador;
    }
    public static Usuario seleccionarUsuarioPorId(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_DATOS_USUARIO);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int rol = rs.getInt("rol");
                int telefono = rs.getInt("movil");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String token = rs.getString("token");
                Date fechaRegistro = rs.getDate("fechaRegistro");
                char sexo = rs.getString("sexo").charAt(0);
                boolean validado = rs.getBoolean("validado");
                boolean activo = rs.getBoolean("activo");
                String img = rs.getString("img");
                if(img ==null){
                    img = "";
                }
                Estadistica estadisticas = EstadisticaDAO.seleccionarEstadisticaUsuario(id);
                u = new Usuario (id,rol,telefono,nombre,email,pass,token,fechaRegistro,sexo,validado,activo,img == null?"":img, estadisticas);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return u;
    }

    public static Usuario getUsuario(String correo){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("select * from usuarios where email = ? and activo = 1");
            stmt.setString(1, correo);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
               // int rol = rs.getInt("rol");
                int telefono = rs.getInt("movil");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                //String token = rs.getString("token");
              //  Date fechaRegistro = rs.getDate("fechaRegistro");
                char sexo = rs.getString("sexo").charAt(0);
               // boolean validado = rs.getBoolean("validado");
               // boolean activo = rs.getBoolean("activo");
            /*    String img = rs.getString("img");
                if(img ==null){
                    img = "";
                }*/ 
                u = new Usuario (id, telefono, nombre,email,pass,sexo);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return u;
    }

      public static Usuario getUsuarioAdmin(String usuario){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = null;

        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("select * from usuarios where email = ? and rol = 1");
            stmt.setString(1, usuario);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int rol = rs.getInt("rol");
                String pass = rs.getString("password");
                String email = rs.getString("email");
               
              
                u = new Usuario (id,rol,pass);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return u;
    }

    /*UPDATE SQL*/
    public static int validarUsuario(String correo, String token){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_VALIDAR_USER);
            stmt.setString(1, correo);
            stmt.setString(2, token);
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
    public static int activarUsuario(String correo){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTIVAR_USUARIO);
            stmt.setString(1, correo);
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
    /*UPDATE SQL*/
    public static int actualizarImagen(int id, String imagen){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_IMG);
            stmt.setString(1, imagen);
            stmt.setInt(2, id);
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
     public static int actualizarNombreEmail(String nombre, String email, int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_NOMBRE_EMAIL);
            stmt.setString(1, nombre);
            stmt.setString(2, email);
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
        
        return registros;
    }
      public static int actualizarToken(String token, int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_TOKEN);
            stmt.setString(1, token);
            stmt.setInt(2, id);
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
       public static int actualizarPassword(String password, int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("update usuarios set password = ? where id = ?");
            stmt.setString(1, password);
            stmt.setInt(2, id);
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
       public static int actualizarPasswordByEmail(String password, String email){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("update usuarios set password = ? where email = ?");
            stmt.setString(1, Cifrado.cifrar(password));
            stmt.setString(2, email);
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
    public static int borrarUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BORRAR_USUARIO);
        
            stmt.setInt(1, id);
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
    
    public static ArrayList<Usuario> getUsuarios(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Usuario> usuarios = null;

        try{
            usuarios = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("select * from usuarios where rol = 0");
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int rol = rs.getInt("rol");
                int telefono = rs.getInt("movil");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String token = rs.getString("token");
                Date fechaRegistro = rs.getDate("fechaRegistro");
                char sexo = rs.getString("sexo").charAt(0);
                boolean validado = rs.getBoolean("validado");
                boolean activo = rs.getBoolean("activo");
                String img = rs.getString("img");
                Usuario u = new Usuario (id,rol,telefono,nombre,email,pass,token,fechaRegistro,sexo,validado,activo,img == null?"":img);
                usuarios.add(u);
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return usuarios;
    }
    
    
    

}



