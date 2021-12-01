

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modelo.Conversacion;
import modelo.Mensaje;

public class ConversacionDAO {
    
    /**insert**/
    private static final String SQL_INSERT_CONVERSACION = "insert into conversaciones(comprador, vendedor,producto) values(?,?,?)";
    private static final String SQL_INSERT_CONVERSACION_PRIVADA = "insert into conversaciones(comprador, vendedor) values(?,?)";

    /**select**/
    private static final String SQL_BUSCAR_SI_DATOS_CONVER_EXISTE =
            "select id from conversaciones where comprador = ? and vendedor = ? and producto = ?";
    private static final String SQL_BUSCAR_SI_USUARIO_PERTENECE_A_CONVER = "select id from conversaciones where id = ? and (comprador = ? or vendedor = ?)";
    private static final String SQL_SELECCIONAR_ID_CONVERSACION = "select id from conversaciones where comprador = ? and vendedor = ? and producto = ?";
    private static final String SQL_BUSCAR_SI_USUARIO_ES_COMPRADOR = "select comprador from conversaciones where comprador = ? and producto = ?";
    private static final String SQL_SELECCIONAR_CONVERSACIONES_USUARIO = 
            "select * from conversaciones where (comprador = ? or vendedor = ?)";
    private static final String SQL_SELECCIONAR_CONVERSACIONES_PRIVADAS = "select * from conversaciones where (comprador = ? or vendedor = ?) and producto = 0";
    private static final String SQL_SELECCIONAR_MENSAJES_CONVERSACION = 
            "select * from mensajes where conversacion = ?";
    
    private static final String SQL_INSERTAR_MENSAJE = "insert into mensajes(contenido, emisor, receptor, conversacion) values (?,?,?,?)";
    
    private static final String SQL_ACTUALIZAR_MENSAJES_VISTOS = "update mensajes set visto = 1 where id = ? and receptor = ?";
    /**update**/
    
    /**delete**/
    private static final String SQL_DELETE_MENSAJE = "update mensajes set borrado = 1 where id = ?";
    private static final String SQL_DELETE_MENSAJE_PROPIO = "update mensajes set borradoParaEmisor = 1 where id = ?";
    private static final String SQL_DELETE_MENSAJE_AJENO = "update mensajes set borradoParaReceptor = 1 where id = ?";
    private static final String SQL_DELETE_CONVERSACION = 
            "delete from conversaciones where id = ?";
    /**inserts¡**/
    public static int insertar(int comprador, int vendedor, int producto){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_CONVERSACION);
            stmt.setInt(1, comprador);
            stmt.setInt(2,vendedor);
            stmt.setInt(3,producto);
          
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
    public static int insertar(int comprador, int vendedor){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_CONVERSACION_PRIVADA);
            stmt.setInt(1, comprador);
            stmt.setInt(2,vendedor);
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
    
    public static int insertarMensaje(String contenido, int emisor, int receptor, int conversacion){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERTAR_MENSAJE);
            stmt.setString(1, contenido);
            stmt.setInt(2, emisor);
            stmt.setInt(3, receptor);
            stmt.setInt(4, conversacion);
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;
    }
    /**selelect**/
   
    public static boolean usuarioPerteneceAConversacion(int conversacion, int usuario){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer n = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_USUARIO_PERTENECE_A_CONVER);
            stmt.setInt(1, conversacion);
            stmt.setInt(2, usuario);
            stmt.setInt(3, usuario);
            
           rs = stmt.executeQuery();
           while(rs.next()){
               n = rs.getInt("id");
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
        return n != null;
    }
    public static int getIdConversacion(int c, int v, int p){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer n = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_ID_CONVERSACION);
            stmt.setInt(1, c);
            stmt.setInt(2, v);
            stmt.setInt(3, p);
            rs = stmt.executeQuery();
            while(rs.next()){
                n = rs.getInt("id");
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
        if(n == null) n = 0;
        return n ;
    }
     public static boolean existeConversacionConUsuariosYProducto(int comprador, int vendedor, int producto){
        int i = ConversacionDAO.getIdConversacion(comprador, vendedor, producto);
        System.out.println(i);
       
        return i != 0;
    }
     public static ArrayList<Conversacion> seleccionarConversacionesUsuario(int id){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Conversacion> conversaciones = null;
        try{
            conversaciones = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_CONVERSACIONES_USUARIO);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
     
           rs = stmt.executeQuery();
           while(rs.next()){
               int n = rs.getInt("id");
               int comp = rs.getInt("comprador");
               int vend = rs.getInt("vendedor");
               int prod = rs.getInt("producto");
               Conversacion c = new Conversacion(n,comp,vend,prod);
               conversaciones.add(c);
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
        return conversaciones;
     }
      public static ArrayList<Conversacion> seleccionarConversacionesPrivadas(int id){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Conversacion> conversaciones = null;
        try{
            conversaciones = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_CONVERSACIONES_PRIVADAS);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
     
           rs = stmt.executeQuery();
           while(rs.next()){
               int n = rs.getInt("id");
               int comp = rs.getInt("comprador");
               int vend = rs.getInt("vendedor");
               
               Conversacion c = new Conversacion(n,comp,vend);
               conversaciones.add(c);
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
        return conversaciones;
     }
     public static boolean isUsuarioComprador(int id, int producto){
           Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer n = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR_SI_USUARIO_ES_COMPRADOR);
            stmt.setInt(1, id);
            stmt.setInt(2, producto);
         
            
           rs = stmt.executeQuery();
           while(rs.next()){
               n = rs.getInt("comprador");
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
        return n != null;
     }
     public static int borrarConversacion(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_CONVERSACION);
            stmt.setInt(1, id);
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
public static int borrarMensaje(int id, boolean borrarParaReceptor){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(borrarParaReceptor ? SQL_DELETE_MENSAJE_AJENO : SQL_DELETE_MENSAJE_PROPIO);
            stmt.setInt(1, id);
            stmt.executeUpdate();
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
     public static ArrayList<Mensaje> seleccionarMensajesConversacion(int id){
       
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Mensaje> mensajes = null;
        try{
            mensajes = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(  SQL_SELECCIONAR_MENSAJES_CONVERSACION);
            stmt.setInt(1, id);
           
     
           rs = stmt.executeQuery();
           while(rs.next()){
               int n = rs.getInt("id");
               String contenido = rs.getString("contenido");
               Date date = rs.getTime("fecha");
  
               int emisor = rs.getInt("emisor");
               int receptor = rs.getInt("receptor");
               boolean visto = rs.getInt("visto") == 1;
               boolean borradoParaReceptor = rs.getInt("borradoParaReceptor") == 1;
               boolean borradoParaEmisor = rs.getInt("borradoParaEmisor") == 1;
               Mensaje mensaje = new Mensaje(n,contenido,date,emisor,receptor,visto,borradoParaEmisor,borradoParaReceptor);
               mensajes.add(mensaje);
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
        return mensajes;
     }
     public static int actualizarMensajesVistos(int id, int emisor){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_MENSAJES_VISTOS);
            stmt.setInt(1, id);
            stmt.setInt(2, emisor);
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
     public static boolean hayMensajesSinLeer(int conver, int usuario){
         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         ArrayList<Integer> ids = null;
         try{
             ids = new ArrayList<>();
             conn = Conexion.getConnection();
             stmt = conn.prepareStatement("select id from mensajes where conversacion = ? and receptor = ? and visto = 0");
             stmt.setInt(1, conver);
             stmt.setInt(2, usuario);
             rs = stmt.executeQuery();
             while(rs.next()) {
                 ids.add(rs.getInt("id"));
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
         if(ids != null){
            return !ids.isEmpty();
         }
         return false;
     }
     public static boolean hayAlgunMensajeSinLeer(int usuario){
      Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         ArrayList<Integer> ids = null;
         try{
             ids = new ArrayList<>();
             conn = Conexion.getConnection();
             stmt = conn.prepareStatement("select id from mensajes where receptor = ? and visto = 0");
             stmt.setInt(1, usuario);
             rs = stmt.executeQuery();
             while(rs.next()) {
                 ids.add(rs.getInt("id"));
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
         if(ids != null){
            return !ids.isEmpty();
         }
         return false;
     }
    
}
